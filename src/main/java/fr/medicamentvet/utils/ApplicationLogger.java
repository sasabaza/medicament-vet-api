package fr.medicamentvet.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class contains methods that can log a Throwable and a custom String
 * message.
 */
public final class ApplicationLogger {

	private static final Logger applicationLogger = Logger.getLogger(ApplicationLogger.class.getName());

	private ApplicationLogger() {
		super();
	}

	public static void throwableLog(Throwable throwable) {
		applicationLogger.log(Level.SEVERE, throwable.getMessage(), throwable);
	}

	public static void log(String message) {
		applicationLogger.log(Level.INFO, message, message);
	}
}