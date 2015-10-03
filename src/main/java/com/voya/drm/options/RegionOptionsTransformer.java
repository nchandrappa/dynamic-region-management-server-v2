package com.voya.drm.options;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import com.voya.drm.domain.RegionOption;

/**
 * Converts region option values to their respective types and returns the converted values to the caller
 * @author Pivotal
 */
public class RegionOptionsTransformer {

	/**
	 * Converts region option values to their respective types and returns the converted values to the caller
	 * @param regionOptions
	 * @return map of region option keys and converted values
	 */
	public RegionOption mapRegionOption(Map<String, String> regionOptions) {

	  String optionName = "";
	  Object value = null;
	  RegionOption regionOption = new RegionOption();
      try {
    	  optionName = "type";
		if (regionOptions.containsKey(optionName)) {
			value = regionOptions.get(optionName);
			regionOption.setType((String) value);
		}
			
  	    optionName = "template-region";
		if (regionOptions.containsKey(optionName)) {
			value = regionOptions.get(optionName);
			regionOption.setTemplateRegion((String) value);
		}
			
		if (regionOptions.containsKey("group")) {
			regionOption.setGroups(regionOptions.get("group").split(","));
		}
			
		if (regionOptions.containsKey("skip-if-exists")) {
			regionOption.setSkipIfExists(Boolean.parseBoolean(regionOptions.get("skip-if-exists")));
		}
			
		if (regionOptions.containsKey("key-constraint")) {
			regionOption.setKeyConstraint(regionOptions.get("key-constraint"));
		}
			
		if (regionOptions.containsKey("value-constraint")) {
			regionOption.setValueConstraint(regionOptions.get("value-constraint"));
		}

		if (regionOptions.containsKey("enable-statistics")) {
			regionOption.setEnableStatistics(Boolean.parseBoolean(regionOptions.get("enable-statistics")));
		}
			
		if (regionOptions.containsKey("entry-idle-time-expiration")) {
			regionOption.setEntryIdleTimeExpiration(Integer.parseInt(regionOptions.get("entry-idle-time-expiration")));
		}
			
		if (regionOptions.containsKey("entry-idle-time-expiration-action")) {
			regionOption.setEntryIdleTimeExpirationAction(regionOptions.get("entry-idle-time-expiration-action"));
		}
			
		if (regionOptions.containsKey("entry-time-to-live-expiration")) {
			regionOption.setEntryTimeToLiveExpiration(Integer.parseInt(regionOptions.get("entry-time-to-live-expiration")));
		}
			
		if (regionOptions.containsKey("entry-time-to-live-expiration-action")) {
			regionOption.setEntryTimeToLiveExpirationAction(regionOptions.get("entry-time-to-live-expiration-action"));
		}
			
		if (regionOptions.containsKey("entry-time-to-live-expiriation-action")) {
			regionOption.setEntryTimeToLiveExpirationAction(regionOptions.get("entry-time-to-live-expiriation-action"));
		}
			
		if (regionOptions.containsKey("region-idle-time-expiration")) {
			regionOption.setRegionIdleTimeExpiration(Integer.parseInt(regionOptions.get("region-idle-time-expiration")));
		}
			
		if (regionOptions.containsKey("region-idle-time-expiration-action")) {
			regionOption.setRegionIdleTimeExpirationAction(regionOptions.get("region-idle-time-expiration-action"));
		}
			
		if (regionOptions.containsKey("region-time-to-live-expiration")) {
			regionOption.setRegionTimeToLiveExpiration(Integer.parseInt(regionOptions.get("region-time-to-live-expiration")));
		}
			
		if (regionOptions.containsKey("region-time-to-live-expiration-action")) {
			regionOption.setRegionTimeToLiveExpirationAction(regionOptions.get("region-time-to-live-expiration-action"));
		}
		return regionOption;
	  }
	  catch (NumberFormatException ex) {
		 throw new RuntimeException(optionName + ": Could not convert " + value + ". Is it a number?");
	  }
	  catch (PatternSyntaxException ex) {
	     throw new RuntimeException(optionName + ": Could not convert " + value + ". Missing a comma?");
	  }
	}
}
