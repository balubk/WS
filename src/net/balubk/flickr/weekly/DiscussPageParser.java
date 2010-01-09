package net.balubk.flickr.weekly;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
import nu.xom.XPathContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Class to parse a discuss page from the flickr WEEKLY group.
 * 
 * @author Bala
 * 
 */
public class DiscussPageParser {

	public static final String RESULT_TEXT_REGEX = ".*(voting is closed).*(Survivor|Keeper):.*?(\\d+).*Free\\s?[Ff]light:.*?(\\d+).*";

	public static WeeklySubmission getSubmission(String url)
			throws ValidityException, ParsingException, IOException {
		String discussionId = getDiscussionId(url);
		String fileName = discussionId + ".html";
		fileName = FilenameUtils.concat("WEEKLYdiscuss", fileName);
		String page = "";
		File f = new File(fileName);
		if (f.exists())
		{
			page = FileUtils.readFileToString(f);
		}
		else
		{
			page = HtmlUtils.getUrlContentsAsString(url);
			savePage(page, discussionId, fileName);
		}
		Builder xomBuilder = XmlUtils.getNonValidatingBuilder();
		StringReader reader = new StringReader(page);
		Document doc = xomBuilder.build(reader);
		XPathContext context = new XPathContext("xhtml",
				"http://www.w3.org/1999/xhtml");
		Element root = doc.getRootElement();
		Nodes h2Elements = root.query("//xhtml:td[@id='GoodStuff']/xhtml:h2",
				context);
		Element h2Elem = (Element) h2Elements.get(0);
		String title = h2Elem.getValue();
		// System.out.println(title);
		String userName = TitleUtils.getUserName(title);
		// System.out.println("userName = " + userName);
		Nodes imageUrls = root
				.query(
						"//xhtml:td[@id='GoodStuff']/xhtml:div/xhtml:table/xhtml:tr/xhtml:td/xhtml:p/xhtml:a",
						context);
		if (imageUrls.size() < 1)
		{
			return null;
		}
		Element aNode = (Element) imageUrls.get(0);
		String imageUrl = aNode.getAttributeValue("href");
		// System.out.println("imageUrl = " + imageUrl);
		int numReplies = root.query(
				"//xhtml:table[@class='TopicReply']/xhtml:tr", context).size() - 1;
		Nodes pNodes = root
				.query(
						"//xhtml:table[@class='TopicReply']/xhtml:tr/xhtml:td[@class='Said']/xhtml:p",
						context);
		// System.out.println("pNodes.size = " + pNodes.size());
		int numKeepers = 0;
		int numFreeflights = 0;
		for (int i = 0; i < pNodes.size(); i++) {
			Element pNode = (Element) pNodes.get(i);
			// System.out.println("pNode.getChldCount = " +
			// pNode.getChildCount());
			String text = pNode.toXML();
			if (isDecisionPost(text)) {

				// System.out.println(text);
				// we have the text we want. Parse it!

				numFreeflights = getNumFreeflights(text);
				if (numFreeflights == -1) {
					numFreeflights = 1;
				}

				numKeepers = getNumKeepers(text);
				if (numKeepers == -1) {
					numKeepers = 1;
				}

				break;
			}
		}
		return new WeeklySubmission(userName, imageUrl, numReplies, numKeepers,
				numFreeflights, url);
	}

	private static void savePage(String page, String discussionId, String fileName)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		PrintStream ps = new PrintStream(fos);
		ps.println(page);
		ps.close();
		fos.close();
	}

	private static String getDiscussionId(String url) {
		String[] parts = url.split("/");
		return parts[parts.length - 1];
	}

	public static int getResultGroup(String text, int group) {
		String parseText = text.replace("\n", "newline");
		// System.out.println(parseText);
		Pattern p = Pattern.compile(RESULT_TEXT_REGEX);
		Matcher m = p.matcher(parseText);
		boolean found = m.find();
		String numText = "";
		if (found) {
			numText = m.group(group);
			try {
				int result = Integer.parseInt(numText);
				return result;
			} catch (Exception ex) {
				return -1;
			}
		}
		return -1;
	}

	public static int getNumKeepers(String text) {
		return getResultGroup(text, 3);
	}

	public static int getNumFreeflights(String text) {
		return getResultGroup(text, 4);
	}

	public static boolean isKeeper(String url) throws ValidityException,
			ParsingException, IOException {
		WeeklySubmission submission = getSubmission(url);
		if (submission.getNumKeepers() >= submission.getNumFreeflights()) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isDecisionPost(String text)
	{
		if (StringUtils.containsIgnoreCase(text, "voting is closed")
				|| StringUtils.contains(text, "Vote for")
				&& ((StringUtils.containsIgnoreCase(text, "Survivor") || StringUtils
						.containsIgnoreCase(text, "keeper")) && StringUtils
						.containsIgnoreCase(text, "free flight")))
		{
			return true;
		}
		return false;
	}
	
	public static Map<String, WeeklyUserActivity> getUserActivity(String url) throws IOException, ValidityException, ParsingException
	{
		Map<String, WeeklyUserActivity> userActivity = new HashMap<String, WeeklyUserActivity>();
		String discussionId = getDiscussionId(url);
		System.out.println(discussionId);
		String fileName = discussionId + ".html";
		fileName = FilenameUtils.concat("WEEKLYdiscuss", fileName);
		String page = "";
		File f = new File(fileName);
		if (f.exists())
		{
			page = FileUtils.readFileToString(f);
		}
		else
		{
			page = HtmlUtils.getUrlContentsAsString(url);
			savePage(page, discussionId, fileName);
		}
		Builder xomBuilder = XmlUtils.getNonValidatingBuilder();
		StringReader reader = new StringReader(page);
		Document doc = xomBuilder.build(reader);
		XPathContext context = new XPathContext("xhtml",
				"http://www.w3.org/1999/xhtml");
		Element root = doc.getRootElement();
		Nodes imageUrls = root
				.query(
						"//xhtml:td[@id='GoodStuff']/xhtml:div/xhtml:table/xhtml:tr/xhtml:td/xhtml:p/xhtml:a",
						context);
		if (imageUrls.size() < 1)
		{
			return userActivity;
		}
		Nodes tdNodes = root
				.query(
						"//xhtml:table[@class='TopicReply']/xhtml:tr/xhtml:td[@class='Said']",
						context);
		Nodes aNodes = root
				.query(
						"//xhtml:table[@class='TopicReply']/xhtml:tr/xhtml:td[@class='Who']/xhtml:a",
						context);
		//System.out.println(aNodes.size());
		assert(aNodes.size() == tdNodes.size());
		for (int i = 0; i < tdNodes.size(); i++) {
			Element tdNode = (Element) tdNodes.get(i);
			//System.out.println(tdNode.toXML());
			Element aNode = (Element) aNodes.get(i);
			//System.out.println(aNode.toXML());
			String userLink = aNode.getAttributeValue("href");
			if (userLink == null)
			{
				continue; 
			}
			String[] nameParts = userLink.split("\\/");
			String userName = nameParts[2];
			//System.out.println(userName);
			WeeklyUserActivity activity;
			if (userActivity.containsKey(userName))
			{
				activity = userActivity.get(userName);
			}
			else
			{
				activity = new WeeklyUserActivity();
				activity.setUserId(userName);
				userActivity.put(userName, activity);
			}
			String text = tdNode.toXML();
			if (!isDecisionPost(text)) {
				activity.setNumComments(activity.getNumComments() + 1);
				if (StringUtils.containsIgnoreCase(text, "keeper") || StringUtils.containsIgnoreCase(text, "survivor"))
				{
					activity.setNumKeepers(activity.getNumKeepers() + 1);
				}
				if (StringUtils.containsIgnoreCase(text, "free flight") || StringUtils.containsIgnoreCase(text, "freeflight"))
				{
					activity.setNumFreeflights(activity.getNumFreeflights() + 1);
				}
			}
		}
		
		return userActivity;
	}
}
