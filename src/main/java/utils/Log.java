package utils;

public class Log {
	
	public static void log(String tag, LogLevel level, String content) {
		System.out.println(Util.getTimeString() + "  " + tag + "  " + level + "  " + content);
	}

	public static void i(String tag, String content){
		log(tag, LogLevel.INFO, content);
	}

	public static void e(String tag, String content){
		log(tag, LogLevel.ERROR, content);
	}

	public static void w(String tag, String content){
		log(tag, LogLevel.WARNING, content);
	}

}
