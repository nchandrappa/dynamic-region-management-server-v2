package com.voya.drm.common;

//import io.pivotal.drm.common.options.CloningEnabledOption;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.TimeoutException;
import com.gemstone.gemfire.management.cli.CommandProcessingException;
import com.gemstone.gemfire.management.cli.Result;
import com.gemstone.gemfire.management.internal.cli.commands.CreateAlterDestroyRegionCommands;
import com.gemstone.gemfire.management.internal.cli.result.CommandResult;
import com.voya.drm.domain.RegionOption;
import com.voya.drm.options.RegionOptionsBuilder;

public class CreateRegionHelper2 {

    private Cache cache;
    private LogWriter logWriter;

    private RegionOptionsBuilder regionOptionsBuilder = new RegionOptionsBuilder();

    public CreateRegionHelper2(Cache cache) {
        this.cache = cache;
        logWriter = cache.getLogger();
    }

    /*  Creating a region this way results in an update to cluster.xml. Let errors
     * throw back to the caller. A function should package the errors for client delivery
     */
    public void createRegion(String regionName, Map<String, String> regionOptions) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
  	   RegionOption convertedOption = regionOptionsBuilder.buildRegionOptions(regionOptions);
       try {
    	  logWriter.fine("About to create region on server");
          CreateAlterDestroyRegionCommands cliCmds = new
        		CreateAlterDestroyRegionCommands();

			Result result = cliCmds.createRegion(
			  regionName, 
			  convertedOption.getType(),
			  convertedOption.getTemplateRegion(),
			  convertedOption.getGroups(),
			  convertedOption.getSkipIfExists(),
			  convertedOption.getKeyConstraint(),
			  convertedOption.getValueConstraint(),
			  convertedOption.getEnableStatistics(),
			  convertedOption.getEntryIdleTimeExpiration(),
			  convertedOption.getEntryIdleTimeExpirationAction(),
			  convertedOption.getEntryTimeToLiveExpiration(),
			  convertedOption.getEntryTimeToLiveExpirationAction(),
			  convertedOption.getRegionIdleTimeExpiration(),
			  convertedOption.getRegionIdleTimeExpirationAction(),
			  convertedOption.getRegionTimeToLiveExpiration(),
			  convertedOption.getRegionIdleTimeExpirationAction(),
			  convertedOption.getDiskStore(),
			  convertedOption.getEnableSynchronousDisk(),
			  convertedOption.getEnableAsyncConflation(),
			  convertedOption.getEnableSubscriptionConflation(),
			  convertedOption.getCacheListeners(),
			  convertedOption.getCacheLoader(),
			  convertedOption.getCacheWriter(),
			  convertedOption.getAsyncEventQueueIds(),
			  convertedOption.getGatewaySenderIds(),
			  convertedOption.getEnableConcurrencyCheck(),
			  convertedOption.getEnableCloning(),
			  convertedOption.getConcurrencyLevel(),
			  convertedOption.getColocatedWith(),
			  convertedOption.getLocalMaxMemory(),
			  convertedOption.getRecoveryDelay(),
			  convertedOption.getRedundantCopies(),
			  convertedOption.getStartupRecoveryDelay(),
			  convertedOption.getTotalMaxMemory(),
			  convertedOption.getTotalNumBuckets(),
			  convertedOption.getCompressor()
			);
          dumpResult((CommandResult) result);
      } catch (CommandProcessingException e) {
            throw new RuntimeException("Gfsh could not execute your command.\n" +
              "Carefully check the spelling of your options and verify that the option is valid for the command.\n", e);
      }
    }


    /*  Creating a region this way results in an update to cluster.xml. Let errors
     * throw back to the caller. A function should package the errors for client delivery
     */
    public void alterRegion(String regionName, Map<String, String> regionOptions) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
  	   RegionOption convertedOption = regionOptionsBuilder.buildRegionOptions(regionOptions);
       try {
    	  logWriter.fine("About to alter region on server");
          CreateAlterDestroyRegionCommands cliCmds = new
        		CreateAlterDestroyRegionCommands();

			Result result = cliCmds.alterRegion(
			  regionName, 
			  convertedOption.getGroups(),
			  convertedOption.getEntryIdleTimeExpiration(),
			  convertedOption.getEntryIdleTimeExpirationAction(),
			  convertedOption.getEntryTimeToLiveExpiration(),
			  convertedOption.getEntryTimeToLiveExpirationAction(),
			  convertedOption.getRegionIdleTimeExpiration(),
			  convertedOption.getRegionIdleTimeExpirationAction(),
			  convertedOption.getRegionTimeToLiveExpiration(),
			  convertedOption.getRegionIdleTimeExpirationAction(),
			  convertedOption.getCacheListeners(),
			  convertedOption.getCacheLoader(),
			  convertedOption.getCacheWriter(),
			  convertedOption.getAsyncEventQueueIds(),
			  convertedOption.getGatewaySenderIds(),
			  convertedOption.getEnableCloning(),
			  convertedOption.getEvictionMax()
			);
          dumpResult((CommandResult) result);
      } catch (CommandProcessingException e) {
            throw new RuntimeException("Gfsh could not execute your command.\n" +
              "Carefully check the spelling of your options and verify that the option is valid for the command.\n", e);
      }
    }

    private void dumpResult(CommandResult commandResult) {
      StringBuilder builder = new StringBuilder();
      commandResult.resetToFirstLine();
      while (commandResult.hasNextLine()) {
        builder
        .append(commandResult.nextLine())
        .append(System.getProperty("line.separator"));
      }
      logWriter.fine("Create region result:\n" + builder.toString());
    }

    /* Destroy may fail to start or may fail to finish, but for different
     * reasons on client and server.
     *
     * On the server, the destroy uses Region.destroyRegion() which is
     * a distributed operation. So it's very likely that one of the
     * other servers may have deleted the region just at the very
     * instant this method starts. So if the region is missing, it's
     * very trivial, so give a minor log message.
     *
     * Once the destroy starts, it may collide with another started
     * for the same region on a different server at the exact same
     * time, or it may fail due to errors in the API. Try to separate
     * these out in terms of which get minor log messages and which
     * are worth a higher log level message.
     */
    public void destroyRegion(final String regionName) {
       Region<?,?> region = cache.getRegion(regionName);
      if (region!=null) {
            logInfo("MetadataRegionCacheListener deleting region named: " + regionName);
            try {
              region.destroyRegion();
            } catch (CacheWriterException | TimeoutException exception1) {
              if (logWriter.errorEnabled()) {
                logWriter
                .error("Distributed Region.destroyRegion() failed on this node for region '" + 
                regionName + "'", exception1);
              }
            } catch (Exception exception2) {
              if (logWriter.fineEnabled()) {
                logWriter
                .fine("Distributed Region.destroyRegion() failed on this node for region '" + 
                  regionName + "'", exception2);
              }
            }
      } else {
        if (logWriter.fineEnabled()) {
          logWriter
            .fine("Distributed Region.destroyRegion() failed on this node for region '" + 
              regionName + "', because it does not exist");
        }
      }
    }

    private void logInfo(String message) {
      logWriter.info(message);
    }

    public void init(Properties properties) {
    }
}
