package net.azzerial.sc2d.utils;

public class MiscUtil {

	public static String secondsToStringFormat(int time, boolean useColon) {
		int seconds = time % 60;
		int minutes = time / 60;
		int hours = minutes / 60;
		minutes = minutes % 60;

		String str = "";
		if (hours != 0) {
			str += (hours < 10 ? "0" + hours : hours);
			str += (useColon ? ":" : "h");
		}
		if (minutes != 0) {
			str += (minutes < 10 ? "0" + minutes : minutes);
			str += (useColon ? ":" : "m");
		} else {
			str += "00";
			str += (useColon ? ":" : "m");
		}
		str += (seconds < 10 ? "0" + seconds : seconds);
		str += (useColon ? "" : "s");
		return (str);
	}
}
