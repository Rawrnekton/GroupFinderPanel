package lib;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Debug {
	
	/**
	 * A Function that will print out a simple Debug message without its origination or additional Data.
	 * 
	 * @param originDebugMessage The String that will be displayed in the console.
	 */
	public static void debug(String originDebugMessage) {
		debug(null, originDebugMessage, false);
	}
	
	/**
	 * A Function that will print out a simple Debug message with its origin in the debug message.
	 * 
	 * @param origin The class of the instance of the class the message is originating from.
	 * @param originDebugMessage The string that will be displayed in the console.
	 */
	public static void debug(Object origin, String originDebugMessage) {
		debug(origin, originDebugMessage, true);
	}
	
	public static void debug(Object origin, String originDebugMessage, boolean showOrigin) {
		if (!Config.getDebugMode()) {
			return;
		}
		
		Date messageDate = new Date();
		SimpleDateFormat messageDateFormat = new SimpleDateFormat("H:mm:ss");
		String debugMessage = "";
		
		/*
		 * e.g.: [13:37]: ayyy lamo -- Handler
		 */

		debugMessage += "[" + messageDateFormat.format(messageDate) + "]: " + originDebugMessage;

		if (showOrigin) {
			debugMessage += "	-- " + origin.getClass();
		}
		
		System.out.println(debugMessage);
	}
}
