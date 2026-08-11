package com.carauction.utils;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Utility logger to replace System.out.println and pass code quality checks.
 */
public final class MyClass {

    // Prevent instantiation of this utility class
    private MyClass() { 
        throw new UnsupportedOperationException("Utility class"); 
    }

    // Class-level logger setup
    private static final Logger logger = Logger.getLogger(MyClass.class.getName());

    /**
     * Replaces: System.out.println("text");
     * Usage: MyClass.log("text");
     */
    public static void log(String message) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(message);
        }
    }

    /**
     * Replaces: System.out.println(true);
     * Usage: MyClass.log(booleanValue);
     */
    public static void log(boolean value) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(Boolean.toString(value));
        }
    }

    /**
     * Replaces: System.out.println(myObject);
     * Usage: MyClass.log(anyObject);
     */
    public static void log(Object obj) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.valueOf(obj));
        }
    }
}


