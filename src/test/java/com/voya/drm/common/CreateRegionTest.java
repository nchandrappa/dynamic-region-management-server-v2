package com.voya.drm.common;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;

public class CreateRegionTest {

	private CreateRegion regionCreator = new CreateRegion();
	  private static final String SUCCESSFUL = "successful";
	  private static final String ALREADY_EXISTS = "alreadyExists";

		@BeforeClass
		public static void init() {
	        CacheFactory cf = new CacheFactory();
	        cf.set("cache-xml-file", "./grid/config/serverCache.xml");
	        cf.set("locators", "gemhost[10334]");
	        Cache cache = cf.create();
		}

		@Test
	public void testCreateOrRetrieveRegionBadOption() {
	      Random r = new Random(System.currentTimeMillis());
	      String regionName = "Test" + r.nextInt(1000);
	      Map<String, String> regionOptions = new HashMap<String, String>() {{
	    	  put("type", "PARTITION_REDUNDANT_HEAP_LRU");
	    	  put("badOption", "badValue");
	      }};
	      String remoteRegionCreationStatus = null;
	  	  try {
			remoteRegionCreationStatus = regionCreator.createOrRetrieveRegion(regionName,
			        regionOptions);
	  	    fail("This should have failed on a bad option");
		} catch (IllegalAccessException | InvocationTargetException
				| RuntimeException e) {
			// TODO Auto-generated catch block
			assertTrue(true);
		}
	}
}
