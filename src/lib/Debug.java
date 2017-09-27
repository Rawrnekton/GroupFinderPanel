package lib;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Debug {
	
	public static void debug(String originDebugMessage) {
		debug(null, originDebugMessage, false);
	}
	
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
		
		
		/**
		 * e.g.: [13:37]: ayyy lamo -- Handler
		 */
		
		debugMessage += "[" + messageDateFormat.format(messageDate) + "]: " + originDebugMessage;

		if (showOrigin) {
			debugMessage += "	-- " + origin.getClass();
		}
		
		
		System.out.println(debugMessage);
	}
}
