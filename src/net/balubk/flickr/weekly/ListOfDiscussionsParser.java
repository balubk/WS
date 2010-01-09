package net.balubk.flickr.weekly;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
import nu.xom.XPathContext;

public class ListOfDiscussionsParser {

	public static Map<String, WeeklyWeek> getPageSummary(String url) throws ValidityException, ParsingException, IOException
	{
		Map<String, WeeklyWeek> weeklyMap = new HashMap<String, WeeklyWeek>();
		String page = HtmlUtils.getUrlContentsAsString(url);
		Builder xomBuilder = XmlUtils.getNonValidatingBuilder();
		StringReader reader = new StringReader(page);
		Document doc = xomBuilder.build(reader);
		XPathContext context = new XPathContext("xhtml", "http://www.w3.org/1999/xhtml");
		Element root = doc.getRootElement();
		Nodes rows = root.query("//xhtml:table[@class='TopicListing']/xhtml:tr/xhtml:td/xhtml:a", context);
		for (int i = 0; i < rows.size(); i++)
		{
			Element aElem = (Element) rows.get(i);
			String discussLink = aElem.getAttributeValue("href");
			//System.out.println("discussLink = " + "http://www.flickr.com" + discussLink);
			if (discussLink.startsWith("/groups/weekly/discuss"))
			{
				Element bElem = aElem.getFirstChildElement("b", "http://www.w3.org/1999/xhtml");
				//System.out.println(bElem.getValue());
				String title = bElem.getValue();
				String week = TitleUtils.getWeek(title);
				if (week == null)
				{
					continue;
				}
				if (week.substring(0, week.length() - 2).equals(week))
				{
					// test for cases like 18th. 18th.substring.equals(18)
					week = week.substring(0, week.length() - 2);
				}
				WeeklyWeek weeklyWeek;
				if (weeklyMap.containsKey(week))
				{
					weeklyWeek = weeklyMap.get(week);
				} else {
					weeklyWeek = new WeeklyWeek();
					weeklyWeek.setWeek(week);
					weeklyWeek.setStartOfWeek(TitleUtils.getStartOfWeek(title));
					weeklyWeek.setEndOfWeek(TitleUtils.getEndOfWeek(title));
					weeklyMap.put(week, weeklyWeek);
				}
				WeeklySubmission submission = DiscussPageParser.getSubmission("http://www.flickr.com" + discussLink);
				if (submission != null) {
					weeklyWeek.addSubmission(submission);
				}
			}
		} // end for
		return weeklyMap;
	}

	public static Map<String, WeeklyUserActivityWeek> getUserActivityPageSummary(String url) throws ValidityException, ParsingException, IOException
	{
		Map<String, WeeklyUserActivityWeek> weeklyMap = new HashMap<String, WeeklyUserActivityWeek>();
		String page = HtmlUtils.getUrlContentsAsString(url);
		Builder xomBuilder = XmlUtils.getNonValidatingBuilder();
		StringReader reader = new StringReader(page);
		Document doc = xomBuilder.build(reader);
		XPathContext context = new XPathContext("xhtml", "http://www.w3.org/1999/xhtml");
		Element root = doc.getRootElement();
		Nodes rows = root.query("//xhtml:table[@class='TopicListing']/xhtml:tr/xhtml:td/xhtml:a", context);
		for (int i = 0; i < rows.size(); i++)
		{
			Element aElem = (Element) rows.get(i);
			String discussLink = aElem.getAttributeValue("href");
			//System.out.println("discussLink = " + "http://www.flickr.com" + discussLink);
			if (discussLink.startsWith("/groups/weekly/discuss"))
			{
				Element bElem = aElem.getFirstChildElement("b", "http://www.w3.org/1999/xhtml");
				//System.out.println(bElem.getValue());
				String title = bElem.getValue();
				String week = TitleUtils.getWeek(title);
				if (week == null)
				{
					continue;
				}
				if (week.substring(0, week.length() - 2).equals(week))
				{
					// test for cases like 18th. 18th.substring.equals(18)
					week = week.substring(0, week.length() - 2);
				}
				WeeklyUserActivityWeek weeklyWeek;
				if (weeklyMap.containsKey(week))
				{
					weeklyWeek = weeklyMap.get(week);
				} else {
					weeklyWeek = new WeeklyUserActivityWeek();
					weeklyWeek.setWeek(week);
					weeklyWeek.setStartOfWeek(TitleUtils.getStartOfWeek(title));
					weeklyWeek.setEndOfWeek(TitleUtils.getEndOfWeek(title));
					weeklyMap.put(week, weeklyWeek);
				}
				Map<String, WeeklyUserActivity> userActivityMap = DiscussPageParser.getUserActivity("http://www.flickr.com" + discussLink);
				weeklyWeek.addUserActivities(userActivityMap);
			}
		} // end for
		return weeklyMap;
	}

}
