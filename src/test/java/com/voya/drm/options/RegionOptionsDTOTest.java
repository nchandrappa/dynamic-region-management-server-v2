package com.voya.drm.options;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.voya.drm.domain.RegionOption;

public class RegionOptionsDTOTest {

	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz".toLowerCase()
              + "abcdefghijklmnopqrstuvwxyz".toUpperCase();
	private static final String NUMBERS = "0123456789";
	private static final byte[] ACCEPTABLE_NAME_BYTES = (LETTERS + NUMBERS).getBytes();

	RegionOptionsBuilder regionOptionsDTO = new RegionOptionsBuilder();

	@Test
	public void testOneOption() {
	  Map<String, String> regionOptions = new HashMap<String, String>();
	  regionOptions.put("entry-idle-time-expiration", "1");

	  RegionOption regionOption = regionOptionsDTO.buildRegionOptions(regionOptions);
	  assertTrue(regionOption.getEntryIdleTimeExpiration() == 1);
	}

	@Test
	public void testAllTypesPositive() {
	  Map<String, String> regionOptions = populateAllOptions();
	  RegionOption regionOption = regionOptionsDTO.buildRegionOptions(regionOptions);
	  Assert.assertNotNull(regionOption);
	}

	private Map<String, String> populateAllOptions() {

		Map<String, String[]> regionOptionsMap = RegionOptionsBuilder.getRegionOptionsMap();
		Map<String, String> regionOptions = new HashMap<String, String>();

		Random r = new Random(System.currentTimeMillis());
		for (String optionName : regionOptionsMap.keySet()) {
			String[] signatureArgs = regionOptionsMap.get(optionName);
			String stringValue = createValue(signatureArgs[1], r);
			if (optionName.equalsIgnoreCase("type")) {
				stringValue = "PARTITION";
			}
			regionOptions.put(optionName, stringValue);
		}
		return regionOptions;
	}

	private String createValue(String type, Random r) {
		byte[] bytes;
		switch(type) {
			case "Long" :
				return Integer.toString(r.nextInt(10000));
			case "Integer" :
				return Integer.toString(r.nextInt(10));
			case "String[]" :
				bytes = new byte[10];
				r.nextBytes(bytes);
				return createRandomString(r, bytes) + "," + createRandomString(r, bytes);
			case "String" :
				bytes = new byte[10];
				return createRandomString(r, bytes);
			case "Boolean" :
				return Boolean.toString(r.nextBoolean());
			default :
				bytes = new byte[10];
				return createRandomString(r, bytes);
		}
	}

	private String createRandomString(Random r, byte[] bytes) {
		for (int i=0; i < bytes.length; i++) {
			int n = r.nextInt(ACCEPTABLE_NAME_BYTES.length);
			bytes[i] = ACCEPTABLE_NAME_BYTES[n];
		}
		return new String(bytes);
	}
}
