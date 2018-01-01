package lib;

public final class Config {
	private static final boolean debugMode = true;
	
	public static final String ip = "127.0.0.1";
	public static final int port = 2709;
	
	
	/* ---------- GET-METHODS ---------- */
	public static boolean getDebugMode() {
		return debugMode;
	}
}
