package com.voya.drm.common;

import static com.voya.drm.common.ExceptionHelpers.sendStrippedException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.Function;
import com.gemstone.gemfire.cache.execute.FunctionContext;

public class CreateRegion implements Function, Declarable {
  private static final long serialVersionUID = 1L;

    private final transient Cache cache;
    public static final String SUCCESSFUL = "successful";
    public static final String ALREADY_EXISTS = "alreadyExists";

    private CreateRegionHelper createRegionHelper;
    RegionNameValidator regionNameValidator;

    public CreateRegion() {
        cache = CacheFactory.getAnyInstance();
        regionNameValidator = new RegionNameValidator(cache);
        createRegionHelper = new CreateRegionHelper(cache);
    }

    public void execute(FunctionContext context) {
        try {
          Object arguments = context.getArguments();
            if (arguments == null || !(arguments instanceof List) || ((List<?>)arguments).size() != 2) {
              throw new Exception("Two arguments required in list");
            }

            Object regionName = ((List<?>) arguments).get(0);
            regionNameValidator.validateRegionName(regionName);

            @SuppressWarnings("unchecked")
            Map<String, String> regionOptions = (Map<String, String>) ((List<?>) arguments).get(1);

            this.cache.getLogger().fine("Received Dyanamic Creation Request for: " + regionName);
            String status = createOrRetrieveRegion((String)regionName, (Map<String, String>)regionOptions);
            context.getResultSender().lastResult(status);

        } catch (Exception exception) {
            sendStrippedException(context, exception);
        }
    }


    String createOrRetrieveRegion(String regionName, Map<String, String> regionOptions) throws RuntimeException, IllegalAccessException, InvocationTargetException {

      String result = SUCCESSFUL;
      Region<?,?> region = this.cache.getRegion(regionName);

        if (region != null) {
          this.cache.getLogger().info("Region Already Exists: " + regionName);
          result = ALREADY_EXISTS;
          return result;
        }

        createRegionHelper.createRegion(regionName, regionOptions);
        
        // region should now be populated
        region = this.cache.getRegion(regionName);
        if (region == null) {
          throw new RuntimeException("Error: Region was not created. Check logs for reason.");
        }

        this.cache.getLogger().info("Region Creation Successful: " + regionName);
        return result;
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
