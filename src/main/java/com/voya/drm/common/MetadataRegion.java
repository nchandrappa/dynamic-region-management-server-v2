package com.voya.drm.common;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;

public class MetadataRegion {
  private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz".toLowerCase()
                      + "abcdefghijklmnopqrstuvwxyz".toUpperCase();
  private static final String LETTERS_AND_NUMBERS = LETTERS + "0123456789";

    private static Cache cache = null;

    /* Subregions are not yet supported.
     *
     * Space and full stop are allowed, means OQL needs escape with single quote.
     */
    private static final char[] RESERVED_CHARS = { Region.SEPARATOR_CHAR };

    public static void validateRegionName(final Object key) throws Exception {

      if (key==null || !(key instanceof String) || ((String) key).length() == 0) {
        throw new Exception("Region name must be non-empty String");
      }

      String regionName = (String) key;

      for (char c : RESERVED_CHARS) {
        if (regionName.indexOf(c)>=0) {
          throw new Exception("Region name '" + regionName + "' cannot include reserved char '" + c + "'");
        }
      }

    if (regionName.startsWith("__")) {
      throw new Exception("Region name '" + regionName + "' cannot begin '__', reserved for Gemfire");
    }

    // Issue at most warning on region name
    String regionNameClean = cleanRegionName(regionName);
    if (!regionName.equals(regionNameClean)) {
      cache.getLogger().warning("Region name '" + regionName + "' contains special characters");
    } else {
      if (LETTERS.indexOf(regionNameClean.charAt(0)) == -1) {
        cache.getLogger().warning("Region name '" + regionName + "' should begin with a letter");
      } else {
        if (regionName.length()>256) {
          cache.getLogger().warning("Region name '" + regionName + "' too long, at " + regionName.length() + " chars");
        }
      }
    }
    }

    /** <P>Return a version of the region name with special characters stripped,
     * only letters and numbers from the original.
     * </P>
     *
     * @param regionName
     * @return  - A String, not null but possibly an empty String.
     */
  public static String cleanRegionName(/*@NonNull*/ final String regionName) {
    StringBuffer sb = new StringBuffer();

    for(int i=0 ; i<regionName.length() ; i++) {
      char c = regionName.charAt(i);

      if(LETTERS_AND_NUMBERS.indexOf(c)!=-1) {
        sb.append(c);
      }
    }

    return sb.toString();
  }
}
