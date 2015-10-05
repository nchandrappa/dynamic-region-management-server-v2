package com.voya.drm.common;

import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gemstone.gemfire.cache.CacheFactory;

public class CreateRegionTest {

  private CreateRegion regionCreator = new CreateRegion();

    @BeforeClass
    public static void init() {
          CacheFactory cf = new CacheFactory();
          cf.set("cache-xml-file", "./grid/config/serverCache.xml");
          cf.set("locators", "gemhost[10334]");
          cf.create();
    }

  @SuppressWarnings("serial")
@Test(expected=RuntimeException.class)
  public void testCreateOrRetrieveRegionBadOption() throws IllegalAccessException, InvocationTargetException, RuntimeException {
      Random r = new Random(System.currentTimeMillis());
      String regionName = "Test" + r.nextInt(1000);
      Map<String, String> regionOptions = new HashMap<String, String>() {{
        put("type", "PARTITION_REDUNDANT_HEAP_LRU");
        put("badOption", "badValue");
      }};
    regionCreator.createOrRetrieveRegion(regionName,
            regionOptions);
        fail("This should have failed on a bad option");
  }
}
