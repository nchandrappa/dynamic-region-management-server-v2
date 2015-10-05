package com.voya.drm.common;

//import io.pivotal.drm.common.options.CloningEnabledOption;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.shell.event.ParseResult;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.TimeoutException;
import com.gemstone.gemfire.management.cli.CommandProcessingException;
import com.gemstone.gemfire.management.internal.cli.CommandManager;
import com.gemstone.gemfire.management.internal.cli.GfshParser;
import com.gemstone.gemfire.management.internal.cli.result.CommandResult;

public class CreateRegionHelper {

    private Cache cache;
    private LogWriter logWriter;

    private CommandManager commandManager;
    private GfshParser gfshParser;

    public CreateRegionHelper(Cache cache) {
         this.cache = cache;
         logWriter = cache.getLogger();

        try {
	      commandManager = CommandManager.getInstance();
	    } catch (ClassNotFoundException | IOException e) {
	      // TODO Auto-generated catch block
	      logWriter.error("Error instantiating a GemFire Command Manager. Internal error. Check logs", e);
	      throw new RuntimeException("Error instantiating a GemFire Command Manager. Internal error. Check logs", e);
	    }
        gfshParser = new GfshParser(commandManager);
    }

    /*  Don't throw exceptions to a listener event method, as they're
     * not passed back to the client that triggered the event. Logging
     * an error will be sufficient.
     */
    public void createRegion(String regionName, Map<String, String> regionOptions) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      logWriter.fine("Generating GFSH Command for dynamic region creation");
      String gfshCommand = generateGfshCommand(regionName, regionOptions);

      try {
          logWriter.fine("GfshParser: Parsing the GFSH Command");
          ParseResult parseResult = gfshParser.parse(gfshCommand);
          logWriter.fine("Executing the GFSH command");
          CommandResult commandResult = (CommandResult) parseResult.getMethod()
        		  .invoke(parseResult.getInstance(), parseResult.getArguments());
          dumpResult(commandResult);
      } catch (CommandProcessingException e) {
            throw new RuntimeException("Gfsh could not execute your command.\n" +
              "Carefully check the spelling of your options and verify that the option is valid for the command.\n", e);
      }
    }

    private String generateGfshCommand(String regionName, Map<String, String> regionOptions) {

      StringBuffer sb = new StringBuffer("create region --name=" + regionName);
      for (Entry<String, String> option : regionOptions.entrySet()) {
        // Appending each region option to gfsh command
        sb.append(" --");
        sb.append(option.getKey());
        sb.append("=");
        sb.append(option.getValue());
      }
      String command = sb.toString();
      logWriter.info("GFSH create region command: " + command);
      return command;
    }

    private void dumpResult(CommandResult commandResult) {
      StringBuilder builder = new StringBuilder();
      commandResult.resetToFirstLine();
      while (commandResult.hasNextLine()) {
        builder
        .append(commandResult.nextLine())
        .append(System.getProperty("line.separator"));
      }
      CacheFactory.getAnyInstance().getLogger()
        .fine("Gfsh CommandResult:" + builder.toString());
    }

    public void afterDestroy(EntryEvent<String, Map<String, String>> event) {
        destroyRegion(event.getKey());
    }

    public void destroyRegion(final String regionName) {
      destroyRegionOnServer(regionName);
    }

    private void destroyRegionOnServer(final String regionName) {
      /* Destroy may fail to start or may fail to finish, but for different
       * reasons on client and server.
       *
       * On the server, the destroy uses Region.destroyRegion() which is
       * a distributed operation. So it's very likely that one of the
       * other servers may have deleted the region just at the very
       * instant this method starts. So if the region is missing it's
       * very trivial, so give a minor log message.
       *
       * Once the destroy starts, it may collide with another started
       * for the same region on a different server at the exact same
       * time, or it may fail due to errors in the API. Try to separate
       * these out in terms of which get minor log messages and which
       * are worth a higher log level message.
       */
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
