package net.balubk.flickr.weekly;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleUtils {

	public static final String TITLE_REGEX = "(Voting)?\\s?[Cc]losed\\s-\\sWeek\\s(\\d*\\w*)\\s\\((\\d*\\w* of \\w*)\\s?(-|to)\\s(\\d*\\w* of \\w*)\\)\\s*(–|-)\\s(.*)";

	public static String getWeek(String title) {
		return getGroup(title, 2);
	}

	public static String getEndOfWeek(String title) {
		return getGroup(title, 5);
	}

	public static String getStartOfWeek(String title) {
		return getGroup(title, 3);
	}

	public static String getUserName(String title) {
		return getGroup(title, 7);
	}

	public static String getGroup(String title, int index) {
		Pattern p = Pattern.compile(TITLE_REGEX);
		Matcher m = p.matcher(title);
		boolean found = m.find();
		if (found) {
			return m.group(index);
		}
		return null;
	}

}
