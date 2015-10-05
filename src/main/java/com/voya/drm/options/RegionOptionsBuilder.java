package com.voya.drm.options;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import com.voya.drm.domain.RegionOption;

/**
 * Builds a new RegionOption from a list of string key-value attributes
 * @author Pivotal
 */
public class RegionOptionsBuilder {

	@SuppressWarnings("serial")

	public static final Map<String, String[]> regionOptionsMap = new HashMap<String, String[]>() {{
		put("type", new String[] {"setType", "String"});
		put("template-region", new String[] {"setTemplateRegion", "String"});
		put("group", new String[] {"setGroups", "String[]"});
		put("skip-if-exists", new String[] {"setSkipIfExists", "Boolean"});
		put("key-constraint", new String[] {"setKeyConstraint", "String"});
		put("value-constraint", new String[] {"setValueConstraint", "String"});
		put("enable-statistics", new String[] {"setEnableStatistics", "Boolean"});
		put("entry-idle-time-expiration", new String[] {"setEntryIdleTimeExpiration", "Integer"});
		put("entry-idle-time-expiration-action", new String[] {"setEntryIdleTimeExpirationAction", "String"});
		put("entry-time-to-live-expiration", new String[] {"setEntryTimeToLiveExpiration", "Integer"});
		put("entry-time-to-live-expiriation-action", new String[] {"setEntryTimeToLiveExpirationAction", "String"});
		put("entry-time-to-live-expiration-action", new String[] {"setEntryTimeToLiveExpirationAction", "String"});
		put("region-idle-time-expiration", new String[] {"setRegionIdleTimeExpiration", "Integer"});
		put("region-idle-time-expiration-action", new String[] {"setRegionIdleTimeExpirationAction", "String"});
		put("region-time-to-live-expiration", new String[] {"setRegionTimeToLiveExpiration", "Integer"});
		put("region-time-to-live-expiration-action", new String[] {"setRegionTimeToLiveExpirationAction", "String"});
		put("disk-store", new String[] {"setDiskStore", "String"});
		put("enable-synchronous-disk", new String[] {"setEnableSynchronousDisk", "Boolean"});
		put("enable-async-conflation", new String[] {"setEnableAsyncConflation", "Boolean"});
		put("enable-subscription-conflation", new String[] {"setEnableSubscriptionConflation", "Boolean"});
		put("cache-listener", new String[] {"setCacheListeners", "String[]"});
		put("cache-loader", new String[] {"setCacheLoader", "String"});
		put("cache-writer", new String[] {"setCacheWriter", "String"});
		put("async-event-queue-id", new String[] {"setAsyncEventQueueIds", "String[]"});
		put("gateway-sender-id", new String[] {"setGatewaySenderIds", "String[]"});
		put("enable-concurrency-check", new String[] {"setEnableConcurrencyCheck", "Boolean"});
		put("enable-cloning", new String[] {"setEnableCloning", "Boolean"});
		put("concurrency-level", new String[] {"setConcurrencyLevel", "Integer"});
		put("colocated-with", new String[] {"setColocatedWith", "String"});
		put("local-max-memory", new String[] {"setLocalMaxMemory", "Integer"});
		put("recovery-delay", new String[] {"setRecoveryDelay", "Long"});
		put("redundant-copies", new String[] {"setRedundantCopies", "Integer"});
		put("startup-recovery-delay", new String[] {"setStartupRecoveryDelay", "Long"});
		put("total-max-memory", new String[] {"setTotalMaxMemory", "Long"});
		put("total-num-buckets", new String[] {"setTotalNumBuckets", "Integer"});
		put("compressor", new String[] {"setCompressor", "String"});
	}};

	/**
	 * Builds a RegionOption from a map of key-value attribute names and values.
	 * @param regionOption
	 * @param regionOptions - gfsh create region option names as keys from attributes names and values
	 */
	public RegionOption buildRegionOptions(Map<String, String> regionOptions) {

		RegionOption regionOption = new RegionOption();
		String methodName = null;
		String argType = null;
		Object castedValue = null;
		
		try {
			for (String optionName: regionOptions.keySet()) {
				String[] signatureArgs = regionOptionsMap.get(optionName);
				if (signatureArgs == null) {
				  throw new RuntimeException(optionName + ": I do not recognize this option name");
				}
				methodName = signatureArgs[0];
				argType = signatureArgs[1];

				// cast value and invoke the method
				castedValue = castValue(optionName, regionOptions.get(optionName), argType);
				Method method = regionOption.getClass().getMethod(methodName, castedValue.getClass());
				method.invoke(regionOption, castedValue);
			}

		} catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Server had an error populating method " + methodName + 
					" with value " + castedValue + " and type " + argType);
		}
		return regionOption;
	}
	
	private Object castValue(String optionName, String value, String type) {
		try {
			switch(type) {
				case "Long" :
					return Long.parseLong(value);
				case "Integer" :
					return Integer.parseInt(value);
				case "String[]" :
					return value.split(",");
				case "String" :
					return value;
				case "Boolean" :
					return Boolean.parseBoolean(value);
				default :
					 throw new RuntimeException(optionName + ": I do not recognize type " + type + " for option " + optionName);
			}
		}
		catch (NumberFormatException ex) {
		  throw new RuntimeException(optionName + ": Could not convert " + value + " to " + type + ". Is it a number?");
		}
		catch (PatternSyntaxException ex) {
		  throw new RuntimeException(optionName + ": Could not convert " + value + " to " + type + ". Missing a comma?");
		}
	}
}
