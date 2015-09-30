package com.voya.drm.common;

import static com.voya.drm.common.ExceptionHelpers.sendStrippedException;

import com.voya.drm.common.MetadataRegion;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.Function;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.pdx.PdxInstance;

public class CreateRegion implements Function, Declarable {
	private static final long serialVersionUID = 1L;

    private final transient Cache cache;
    private static final String SUCCESSFUL = "successful";
    private static final String ALREADY_EXISTS = "alreadyExists";
    private static final String REGION_ATTRIBUTES_METADATA_REGION = "__regionAttributesMetadata";

    public CreateRegion() {
        this.cache = CacheFactory.getAnyInstance();
    }

    public void execute(FunctionContext context) {
        try {
        	Object arguments = context.getArguments();
            if (arguments==null || !(arguments instanceof List) || ((List<?>)arguments).size() != 2) {
            	throw new Exception("Two arguments required in list");
            }

            Object regionName = ((List<?>) arguments).get(0);
            MetadataRegion.validateRegionName(regionName);

            @SuppressWarnings("unchecked")
			Map<String, String> regionOptions = (Map<String, String>) ((List<?>) arguments).get(1);

            this.cache.getLogger().fine("Received Dyanamic Creation Request for: " + regionName);
            String status = createOrRetrieveRegion((String)regionName, (Map<String, String>)regionOptions);
            context.getResultSender().lastResult(status);

        } catch (Exception exception) {
            sendStrippedException(context, exception);
        }
    }


    private String createOrRetrieveRegion(String regionName, Map<String, String> regionOptions) throws RuntimeException {

    	String result = SUCCESSFUL;
    	Region<?,?> region = this.cache.getRegion(regionName);

        if (region != null) {
        	this.cache.getLogger().info("Region Already Exists: " + regionName);
        	result = ALREADY_EXISTS;
        	return result;
        }

        getMetadataRegionForInsert().put(regionName, regionOptions);
        MetadataRegion.checkLimits();

        // the MetadataRegionCacheListener should fire synchronously for the previous put
        // region should now be populated
        region = this.cache.getRegion(regionName);
        PdxInstance previousMetadata = MetadataRegion.getMetadataRegion().get(regionName);
        if (region == null && previousMetadata != null) {
            this.cache.getLogger().info("Error: Metadata region present but region was not created.");
            removeMetadataEntry(regionName);
            throw new RuntimeException("Error: Metadata region present but region was not created. Check Cache Server logs for more information.");
        } else if (region == null) {
        	removeMetadataEntry(regionName);
        	throw new RuntimeException("Error: Region was not created for some reason.");
        }

        this.cache.getLogger().info("Region Creation Successful: " + regionName);
        return result;
    }

    private Region<String, Map<String, String>> getMetadataRegionForInsert() {
        Region<String, Map<String, String>> metaRegion = this.cache.getRegion(REGION_ATTRIBUTES_METADATA_REGION);
        return metaRegion;
    }

    private void removeMetadataEntry(String regionName) {
    	PdxInstance previousMetadata = MetadataRegion.getMetadataRegion().get(regionName);
    	if (previousMetadata != null) {
    		MetadataRegion.getMetadataRegion().remove(regionName);
    	}
    }

    public String getId() {
        return getClass().getSimpleName();
    }

    public boolean optimizeForWrite() {
        return false;
    }

    public boolean isHA() {
        return true;
    }

    public boolean hasResult() {
        return true;
    }

    public void init(Properties properties) {

    }
}
