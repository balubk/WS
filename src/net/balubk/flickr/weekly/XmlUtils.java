package net.balubk.flickr.weekly;

import nu.xom.Builder;

import org.ccil.cowan.tagsoup.Parser;

public class XmlUtils {

	public static Builder getNonValidatingBuilder()
	{
		Parser xmlReader = new Parser();
		Builder xomBuilder = new Builder(xmlReader, false);
		return xomBuilder;
	}
}
