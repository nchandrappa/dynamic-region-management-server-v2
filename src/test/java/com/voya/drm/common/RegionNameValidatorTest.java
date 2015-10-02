package com.voya.drm.common;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;

public class RegionNameValidatorTest {
	
	private static RegionNameValidator regionNameValidator;
	
	@BeforeClass
	public static void init() {
        CacheFactory cf = new CacheFactory();
        cf.set("cache-xml-file", "/Users/wwilliams/Documents/git/dynamic-region-management-server-v2/grid/config/serverCache.xml");
        cf.set("locators", "gemhost[10334]");
        Cache cache = cf.create();
        regionNameValidator = new RegionNameValidator(cache);
	}
	
	@Test
	public void testValidateRegionName() {
 		String regionName = "Account";
		try {
			regionNameValidator.validateRegionName(regionName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("region name " + regionName + " should have passed");
		}
		org.junit.Assert.assertTrue(true);
	}
	
	@Test
	public void testInvalidRegionNameWIthReservedChar() {
 		String regionName = "Accou/nt";
		try {
			regionNameValidator.validateRegionName(regionName);
			fail("region name " + regionName + " should not have passed");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(true);
		}
	}

}
