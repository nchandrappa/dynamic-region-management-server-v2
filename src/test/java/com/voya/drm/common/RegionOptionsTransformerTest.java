package com.voya.drm.common;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.Assert;

import com.voya.drm.options.RegionOptionsTransformer;

public class RegionOptionsTransformerTest {

	private RegionOptionsTransformer regionOptionsTransformer = new RegionOptionsTransformer();
	
	@Test
	public void testValidateUserOptionGoodInt() {

//		   Map<String, String> userDefinedRegionOptions = new HashMap<String, String>();
//		   userDefinedRegionOptions.put("entry-idle-time-expiration", "1");
//		   Map<String, Object> convertedOptions = regionOptionsTransformer.mapRegionOptions(userDefinedRegionOptions);
//		   Object convertedObject = convertedOptions.get("entry-idle-time-expiration");
//		   try{
//			   Integer convertedValue = (Integer) convertedObject;
//			   Assert.isTrue(convertedValue == 1);
//		   }
//		   catch (Exception e) {
//			   fail("The transformation should have returned an integer");
//		   }
	}
	
	@Test
	public void testValidateUserOptionBadInt() {

//		   Map<String, String> userDefinedRegionOptions = new HashMap<String, String>();
//		   userDefinedRegionOptions.put("entry-idle-time-expiration", "1x");
//		   try{
//			   Map<String, Object> convertedOptions = regionOptionsTransformer.mapRegionOptions(userDefinedRegionOptions);
//			   Object convertedObject = convertedOptions.get("entry-idle-time-expiration");
//			   Integer convertedValue = (Integer) convertedObject;
//			   fail("The transformation should not have returned an integer " + convertedValue);
//		   }
//		   catch (Exception e) {
//			   Assert.isTrue(true);
//		   }
	}

	
	@Test
	public void testValidateUserOptionGoodStrings() {

//		   Map<String, String> userDefinedRegionOptions = new HashMap<String, String>();
//		   userDefinedRegionOptions.put("cache-listener", "xxx,yyy,zzz");
//		   Map<String, Object> convertedOptions = regionOptionsTransformer.mapRegionOptions(userDefinedRegionOptions);
//		   Object convertedObject = convertedOptions.get("cache-listener");
//		   try{
//			   String[] convertedValue = (String[]) convertedObject;
//			   Assert.isTrue(convertedValue.length == 3);
//		   }
//		   catch (Exception e) {
//			   fail("The transformation should have returned a String[]");
//		   }
	}
	
	@Test
	public void testValidateUserOptionBadStrings() {

//		   Map<String, String> userDefinedRegionOptions = new HashMap<String, String>();
//		   userDefinedRegionOptions.put("cache-listener", "xxx.yyy.zzz");
//		   try{
//			   Map<String, Object> convertedOptions = regionOptionsTransformer.mapRegionOptions(userDefinedRegionOptions);
//			   Object convertedObject = convertedOptions.get("cache-listener");
//			   String[] convertedValue = (String[]) convertedObject;
//			   fail("The transformation should not have returned a String[] " + convertedValue);
//		   }
//		   catch (Exception e) {
//			   Assert.isTrue(true);
//		   }
	}

}
