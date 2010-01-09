package net.balubk.flickr.weekly;

import net.balubk.flickr.weekly.HtmlUtils;

public class TestHtmlUtils {

	public static void main(String[] args)
	{
		String content = HtmlUtils.getUrlContentsAsString("http://www.google.com");
		System.out.println(content);
	}
}
