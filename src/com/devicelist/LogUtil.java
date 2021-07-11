package com.devicelist;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {
	
	private LogUtil(){
		
	}
	
	private static Logger logger = LogManager.getRootLogger();
	
	public static void logInfo(Object message){
		logger.log(Level.INFO, message);
	}
	
	public static void logError(Object message){
		logger.log(Level.ERROR, message);
	}
 
}
