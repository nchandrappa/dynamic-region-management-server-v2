package com.voya.drm.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.shell.event.ParseResult;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.management.internal.cli.CommandManager;
import com.gemstone.gemfire.management.internal.cli.GfshParser;
import com.gemstone.gemfire.management.internal.cli.i18n.CliStrings;
import com.gemstone.gemfire.management.internal.cli.result.CommandResult;

public class CreateAlterDestroyRegionCommandsTest {

	@BeforeClass
	public static void init() {
        CacheFactory cf = new CacheFactory();
        cf.set("cache-xml-file", "/Users/wwilliams/Documents/git/dynamic-region-management-server-v2/grid/config/serverCache.xml");
        cf.set("locators", "gemhost[10334]");
        Cache cache = cf.create();
	}

  @Test
  public void testParseResult() {
    String regionName = "xxx";
    
    String command = "create region --name=" + regionName + " --type=PARTITION";
//    String command = "destroy region --name=" + regionName;
//    String command = "alter region --name=" + regionName + " --entry-time-to-live-expiration=20";
//    String command = "describe region --name=" + regionName;
    
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
 

  @Test
  public void testCreateAlterDestroyCommand() {
	  Random r = new Random(System.currentTimeMillis());
	  
    String regionName = "xxx" + r.nextInt(10000);
    
    String createCommand = "create region --name=" + regionName + " --type=PARTITION --" + CliStrings.CREATE_REGION__STATISTICSENABLED + "=true";

    String destroyCommand = "destroy region --name=" + regionName;
    String alterCommand = "alter region --name=" + regionName + " --entry-time-to-live-expiration=20";
    String describeCommand = "describe region --name=" + regionName;
    
      CommandManager commandManager;
      GfshParser gfshParser;
      try {
		commandManager = CommandManager.getInstance();
	     gfshParser = new GfshParser(commandManager);
	} catch (ClassNotFoundException | IOException e1) {
		// TODO Auto-generated catch block
		throw new RuntimeException(e1);
	}

      // create
    CommandResult commandResult = null;
    ParseResult parseResult;
	try {
	    parseResult = gfshParser.parse(createCommand);
	    System.out.println(parseResult.toString());   
		commandResult = (CommandResult) parseResult.getMethod().invoke(parseResult.getInstance(), parseResult.getArguments());
	    dumpResult(commandResult);
	} catch (IllegalAccessException | IllegalArgumentException
			| InvocationTargetException e) {
		e.printStackTrace();
	}
	
	// alter
	try {
	    parseResult = gfshParser.parse(alterCommand);
	    System.out.println(parseResult.toString());   
		commandResult = (CommandResult) parseResult.getMethod().invoke(parseResult.getInstance(), parseResult.getArguments());
	    dumpResult(commandResult);
	} catch (IllegalAccessException | IllegalArgumentException
			| InvocationTargetException e) {
		e.printStackTrace();
	}
	
	// destroy
	try {
	    parseResult = gfshParser.parse(destroyCommand);
	    System.out.println(parseResult.toString());   
		commandResult = (CommandResult) parseResult.getMethod().invoke(parseResult.getInstance(), parseResult.getArguments());
	    dumpResult(commandResult);
	} catch (IllegalAccessException | IllegalArgumentException
			| InvocationTargetException e) {
		e.printStackTrace();
	}
}


    private void dumpResult(CommandResult commandResult) {
      StringBuilder builder = new StringBuilder();
      commandResult.resetToFirstLine();
      while (commandResult.hasNextLine()) {
        builder.append(commandResult.nextLine()).append(System.getProperty("line.separator"));
      }
      System.out.println(builder.toString());      
    }
}
