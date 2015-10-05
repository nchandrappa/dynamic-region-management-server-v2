package com.voya.drm.common;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;

/**
 * Utilities for cleaning the region name.
 * @author Pivotal
 *
 */
public class RegionNameValidator {

  private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz".toLowerCase()
                      + "abcdefghijklmnopqrstuvwxyz".toUpperCase();
  private static final String NUMBERS = "0123456789";
  private static final String SPECIALS = "#_-.|";
  private static final String ACCEPTABLE_NAME_CHARACTERS =
    LETTERS + NUMBERS + SPECIALS;
  private static final int MAX_REGION_NAME_LENGTH = 256;

  private Cache cache = null;

  /* Subregions are not yet supported.
   *
   * Space and full stop are allowed, means OQL needs escape with single quote.
   */
  private static final char[] RESERVED_CHARS = {Region.SEPARATOR_CHAR};

  /**
   * Must pass an instance of Cache for construction.
   * @param cache
   */
  public RegionNameValidator(final Cache cache) {
    this.cache = cache;
  }

  /**
   * Validates that the region name being created is acceptable to GemFire.
   * @param key
   * @throws Exception
   */
  public void validateRegionName(Object key) throws Exception {

    if (key == null
      || !(key instanceof String)
      || ((String) key).length() == 0) {
        throw new Exception("Region name must be non-empty String");
    }

    String regionName = (String) key;

    if (regionName.startsWith("__")) {
      throw new Exception("Region name '"
        + regionName + "' cannot begin '__', reserved for Gemfire");
    }

    // Issue at most warning on region name
    String regionNameClean = cleanRegionName(regionName);
    if (!regionName.equals(regionNameClean)) {
      cache.getLogger().warning("Region name '" + regionName
       + "' contains invalid special characters");
    } else {
      if (LETTERS.indexOf(regionNameClean.charAt(0)) == -1) {
        cache.getLogger().warning("Region name '" + regionName
          + "' should begin with a letter");
      } else {
        if (regionName.length() > MAX_REGION_NAME_LENGTH) {
          cache.getLogger().warning(
          "Region name '" + regionName + "' too long, at "
            + regionName.length() + " chars");
        }
      }
    }
  }

  /** Return a version of the region name with special characters stripped,
   * only letters and numbers from the original.
   *
   * @param regionName
   * @return  - A String, not null but possibly an empty String.
   * @throws Exception
   */
  public String cleanRegionName(String regionName) throws Exception {
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < regionName.length(); i++) {
      char c = regionName.charAt(i);

      if (ACCEPTABLE_NAME_CHARACTERS.indexOf(c) != -1) {
        sb.append(c);
      }

      for (char cr : RESERVED_CHARS) {
          if (c == cr) {
            cache.getLogger().info("RESERVED_CHARS="
                + new String(RESERVED_CHARS) + ". char=" + String.valueOf(cr));

            throw new Exception("Region name '"
              + regionName + "' cannot include reserved char '" + c + "'");
          }
       }
    }
    return sb.toString();
  }
}
