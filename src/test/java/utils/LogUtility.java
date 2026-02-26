package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtility {
    public static Logger logger = LogManager.getLogger();

    // log care logheaza inceputul testului
    public static void startTest(String testName) {
        logger.info("========== Execution started: " + testName + " ==========");
    }

    //log care logheaza informatii
    public static void infoLog(String message) {
        logger.info(message);
    }

    //log care logheaza erori
    public static void errorLog(String message) {
        logger.error(message);
    }

    // log care logheaza sfarsitul testului
    public static void finishTest(String testName) {
        logger.info("========== Execution ended: " + testName + " ==========");
    }
}