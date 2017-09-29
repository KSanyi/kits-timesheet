package hu.kits.timesheet.util;

public class StringUtil {

	public static String capitalize(String string) {
		
		if(string == null || string.isEmpty()) return string;
		
		return Character.toUpperCase(string.charAt(0)) + string.substring(1, string.length());
	}
	
}
