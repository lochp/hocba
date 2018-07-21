package utils;

public class StringUtils {

	public static boolean isNullOrEmpty(String string) {
		if (string == null || string.trim().equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}
}
