package net.azzerial.shione.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiscUtil {

	public static String listToString(List<String> list, String separator) {
		String str = "";

		if (list.isEmpty()) {
			return (str);
		}
		for (String s : list) {
			str += s + separator;
		}
		return (str.substring(0, str.length() - separator.length()));
	}

	public static List<String> stringToList(String str, String separator) {
		return (new ArrayList<String>(Arrays.asList(str.split(separator))));
	}
}
