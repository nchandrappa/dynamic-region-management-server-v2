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
    private RegionNameValidator regionNameValidator;

    public CreateRegion() {
        cache = CacheFactory.getAnyInstance();
        regionNameValidator = new RegionNameValidator(cache);
        createRegionHelper = new CreateRegionHelper(cache);
    }

    public void execute(FunctionContext context) {
        try {
          Object arguments = context.getArguments();
            if (arguments == null || !(arguments instanceof List)
              || ((List<?>)arguments).size() != 2) {
              throw new Exception("Two arguments required in list");
            }

            Object regionName = ((List<?>) arguments).get(0);
            regionNameValidator.validateRegionName(regionName);

            @SuppressWarnings("unchecked")
            Map<String, String> regionOptions =
              (Map<String, String>) ((List<?>) arguments).get(1);

            this.cache.getLogger()
              .fine("Received Dyanamic Creation Request for: " + regionName);
            String status = createOrRetrieveRegion(
                (String) regionName, (Map<String, String>) regionOptions);
            context.getResultSender().lastResult(status);

        } catch (Exception exception) {
            sendStrippedException(context, exception);
        }
    }

    String createOrRetrieveRegion(String regionName, Map<String, String> regionOptions)
      throws RuntimeException, IllegalAccessException, InvocationTargetException {

      String result = SUCCESSFUL;
      Region<?, ?> region = this.cache.getRegion(regionName);

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
    
    /*
     * (non-Javadoc)
     * @see com.gemstone.gemfire.cache.execute.Function#getId()
     */
    public String getId() {
        return getClass().getSimpleName();
    }

    /*
     * (non-Javadoc)
     * @see com.gemstone.gemfire.cache.execute.Function#optimizeForWrite()
     */
    public boolean optimizeForWrite() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.gemstone.gemfire.cache.execute.Function#isHA()
     */
    public boolean isHA() {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.gemstone.gemfire.cache.execute.Function#hasResult()
     */
    public boolean hasResult() {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.gemstone.gemfire.cache.Declarable#init(java.util.Properties)
     */
    public void init(Properties properties) {
    }
}
