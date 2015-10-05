package com.voya.drm.test;

import java.io.File;
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
		
		File x = new File(".");
		System.out.println(x.getAbsolutePath());
		
        CacheFactory cf = new CacheFactory();
        cf.set("cache-xml-file", "./grid/config/serverCache.xml");
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
	String alterAttributes = " --entry-time-to-live-expiration=20";
	doCreateAlterDestroyCommand(regionName, alterAttributes);
  }
  
  @Test
  public void testAllAlterCommands() {
	  
	Random r = new Random(System.currentTimeMillis());
	String regionName = "xxx" + r.nextInt(10000);   
	String alterAttributes = "--entry-idle-time-expiration=20";
	doCreateAlterDestroyCommand(regionName, alterAttributes);

	// entry-time-to-live-expiration has a bug
	
	alterAttributes = "--entry-idle-time-expiration-action=destroy";
	doCreateAlterDestroyCommand(regionName, alterAttributes);

	alterAttributes = "--entry-time-to-live-expiration-action=destroy";
	doCreateAlterDestroyCommand(regionName, alterAttributes);

	alterAttributes = "--region-idle-time-expiration=20";
	doCreateAlterDestroyCommand(regionName, alterAttributes);

	alterAttributes = "--region-idle-time-expiration-action=destroy";
	doCreateAlterDestroyCommand(regionName, alterAttributes);

	alterAttributes = "--region-time-to-live-expiration=20";
	doCreateAlterDestroyCommand(regionName, alterAttributes);

	alterAttributes = "--region-time-to-live-expiration-action=destroy";
	doCreateAlterDestroyCommand(regionName, alterAttributes);

	alterAttributes = "--cache-listener=com.voya.dummy1,com.voya.dummy2";
	doCreateAlterDestroyCommand(regionName, alterAttributes);

	alterAttributes = "--cache-loader=com.voya.dummy.loader";
	doCreateAlterDestroyCommand(regionName, alterAttributes);

	alterAttributes = "--cache-loader=com.voya.dummy.writer";
	doCreateAlterDestroyCommand(regionName, alterAttributes);
	
	alterAttributes = "--async-event-queue-id=111,222";
	doCreateAlterDestroyCommand(regionName, alterAttributes);
	
	alterAttributes = "--gateway-sender-id=111,222";
	doCreateAlterDestroyCommand(regionName, alterAttributes);
	
	alterAttributes = "--enable-cloning=true";
	doCreateAlterDestroyCommand(regionName, alterAttributes);
	
	alterAttributes = "--eviction-max=10";
	doCreateAlterDestroyCommand(regionName, alterAttributes);
	
	  
  }
  
  public void doCreateAlterDestroyCommand(String regionName, String alterAttributes) {
	  
    String createCommand = "create region --name=" + regionName + " --type=PARTITION --" + CliStrings.CREATE_REGION__STATISTICSENABLED + "=true";
    String destroyCommand = "destroy region --name=" + regionName;
    String alterCommand = "alter region --name=" + regionName + alterAttributes;
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
