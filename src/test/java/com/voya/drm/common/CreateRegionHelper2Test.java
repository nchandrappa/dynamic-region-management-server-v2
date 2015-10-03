package com.voya.drm.common;

import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.TimeoutException;
import com.gemstone.gemfire.distributed.LeaseExpiredException;
import com.gemstone.gemfire.management.internal.cli.i18n.CliStrings;

public class CreateRegionHelper2Test {

	private static CreateRegionHelper2 createRegionHelper2;
	private static Cache cache;

	@BeforeClass
	public static void init() {
        CacheFactory cf = new CacheFactory();
        cf.set("cache-xml-file", "/Users/wwilliams/Documents/git/dynamic-region-management-server-v2/grid/config/serverCache.xml");
        cf.set("locators", "gemhost[10334]");
        cache = cf.create();
        createRegionHelper2 = new CreateRegionHelper2(cache);
	}

	@SuppressWarnings("serial")
	@Test
	public void testAlterRegion() {
		
	    Random r = new Random(System.currentTimeMillis());
	    String regionName = "Test" + r.nextInt(1000);
		Map<String, String> regionOptions = new HashMap<String, String>() {{
		      put(CliStrings.CREATE_REGION__REGIONSHORTCUT, "REPLICATE");
		      put(CliStrings.CREATE_REGION__STATISTICSENABLED, "true");
		      put(CliStrings.CREATE_REGION__ENTRYEXPIRATIONTIMETOLIVE, "60");
	    }};

		try {
			createRegionHelper2.createRegion(regionName, regionOptions);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail("unsuccessful create. Maybe a misspelled region option? " + e.getMessage());
		}
		Assert.isTrue(true);
		
		regionOptions = new HashMap<String, String>() {{
		      put(CliStrings.CREATE_REGION__TOTALNUMBUCKETS, "53");
		}};
		try {
			createRegionHelper2.alterRegion(regionName, regionOptions);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail("unsuccessful alter. Maybe a missplled region option? " + e.getMessage());
		}
		Assert.isTrue(true);
		
		doDelete(regionName);
		Assert.isTrue(true);

	}

	@Test
	public void testDeleteRegion() {
	    Random r = new Random(System.currentTimeMillis());
	    String regionName = "Test" + r.nextInt(1000);
		doDelete(regionName);
	}
	
	@SuppressWarnings("serial")
	private void doDelete(String regionName) {
		Map<String, String> regionOptions = new HashMap<String, String>() {{
		      put(CliStrings.CREATE_REGION__REGIONSHORTCUT, "REPLICATE");
		      put(CliStrings.CREATE_REGION__STATISTICSENABLED, "true");
		      put(CliStrings.CREATE_REGION__ENTRYEXPIRATIONTIMETOLIVE, "60");
	    }};

		try {
			createRegionHelper2.createRegion(regionName, regionOptions);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail("unsuccessful create. Maybe a missplled region option? " + e.getMessage());
		}
		Assert.isTrue(true);
		
		Region<?,?> region = cache.getRegion(regionName);
		try {
			region.destroyRegion();
		} catch (CacheWriterException | LeaseExpiredException
				| TimeoutException e) {
			fail("unsuccessful destroy. " + e.getMessage());
		}
		Assert.isTrue(true);

	}
}