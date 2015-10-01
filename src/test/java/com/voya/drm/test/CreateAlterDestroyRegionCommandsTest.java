package com.voya.drm.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.springframework.shell.event.ParseResult;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.management.internal.cli.CommandManager;
import com.gemstone.gemfire.management.internal.cli.GfshParser;
import com.gemstone.gemfire.management.internal.cli.result.CommandResult;

public class CreateAlterDestroyRegionCommandsTest {

  @Test
  public void test() {
    
        CacheFactory cf = new CacheFactory();
        cf.set("cache-xml-file", "/Users/wwilliams/Documents/git/dynamic-region-management-server-v2/grid/config/serverCache.xml");
        cf.set("locators", "gemhost[10334]");
        Cache cache = cf.create();

    String regionName = "xxx";
    
//    String command = "create region --name=" + regionName + " --type=PARTITION";
//    String command = "destroy region --name=" + regionName;
//    String command = "alter region --name=" + regionName + " --entry-time-to-live-expiration=20";
    String command = "describe region --name=" + regionName;
    
      CommandManager commandManager;
    try {
      commandManager = CommandManager.getInstance();
        GfshParser gfshParser = new GfshParser(commandManager);
        ParseResult parseResult = gfshParser.parse(command);
        
        System.out.println(parseResult.toString());
        
        CommandResult commandResult = (CommandResult) parseResult.getMethod().invoke(parseResult.getInstance(), parseResult.getArguments());
        dumpResult(commandResult);
    } catch (ClassNotFoundException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
     
    private static void dumpResult(CommandResult commandResult) {
      StringBuilder builder = new StringBuilder();
      commandResult.resetToFirstLine();
      while (commandResult.hasNextLine()) {
        builder.append(commandResult.nextLine()).append(System.getProperty("line.separator"));
      }
      System.out.println(builder.toString());      
    }
}
