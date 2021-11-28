package Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import Pages.Constant;

public class LoggerUtility {

	 static{
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	        System.setProperty("current.date.time", dateFormat.format(new Date()));
	    }

	public static  void LogMessage(String ClassName, String Message) {
		Logger logger= Logger.getLogger(ClassName);
		BasicConfigurator.configure();
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
		logger.info(Message);
		try {
			Constant.scenario.log(Message);
		} catch (Exception e) {
			
		}
		
	}
	
	public static  void LogException(String ClassName, String Message) {
		Logger logger= Logger.getLogger(ClassName);
		BasicConfigurator.configure();
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
		logger.info(Message);
		
		try {
			Constant.scenario.log(Message);
		} catch (Exception e) {
			
		}

	}
	
	
	public static  void LoggerSuite(String ClassName,String message ) {
		Logger logger= Logger.getLogger(ClassName);
		BasicConfigurator.configure();
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
		logger.info("********************************************"+message+"*************************************");
		

	}
	
	public static  void LoggerScenario(String ClassName,String message ) {
		Logger logger= Logger.getLogger(ClassName);
		BasicConfigurator.configure();
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
		logger.info("********************************************"+message+"*************************************");
		

	}
		
	
}
