package net.balubk.flickr.weekly;

import java.util.Map;


public class TestDiscussPageParser {

	public static void main(String[] args) throws Exception {
		/*
		printResult("http://www.flickr.com/groups/weekly/discuss/82228/");
		printResult("http://www.flickr.com/groups/weekly/discuss/82226/");
		printKeepersFreeFlights("<b>This voting is closed</b><br />\n<br />\n7 votes in 7 postings<br />\n<br />\n<u>Results:</u><br />\n<br />\nSurvivor: <b>1</b><br />\nFree Flight: <b>6</b><br />\n<br />\n\nThis photo is a <b>Free Flight</b>.<br />\n\n<br />\n--<br />\n<br />\nSorry, i have to take it out of the pool.\n								<br />\n					<small>\n								\n																	Posted 47 months ago.\n																(\n\n								<a href=\"/groups/weekly/discuss/82226/689033/\" class=\"Plain\">permalink</a>\n\n										)\n								<br />\n							</small>	");
		printKeepersFreeFlights("This voting is closed<br />\n<br />\nResults:<br />\nKeeper: 0<br />\nFree Flight: 3<br />\n<br />\nThis photo is a Free Flight.<br />\nSorry, I have to take it out of the pool.\n								<br />\n								<small>\n\n								\n																	Posted 3 hours ago.\n																(\n								<a href=\"/groups/weekly/discuss/72157621558667713/72157621821749212/\" class=\"Plain\">permalink</a>\n										)\n								<br />\n							</small>");
		printKeepersFreeFlights("This voting is closed<br />\n<br />\nResults:<br />\nKeeper: 12<br />\nFree Flight: 1<br />\n<br />\nThis photo is a Keeper.\n								<br />\n								<small>newline								newline																	Posted 19 months newlinego.newline																( newline								<a href=\"/groups/weekly/discuss/72157603488681298/72157603530676153/\" newlinelass=\"Plain\">permalink</a>newline										)newline								<br />newlinenewline							</small>");
		printKeepersFreeFlights("This voting is closed<br />\n<br />\nResults:<br />\n\nKeeper: 4<br />\nFree Flight: 5<br />\n<br />\nThis photo is a Free Flight.<br />\nSorry, I have to take it out of the pool.\n								<br />\n								<small>\n								\n																	Posted 19 months ago.\n																(\n\n								<a href=\"/groups/weekly/discuss/72157603498699147/72157603530668995/\" class=\"Plain\">permalink</a>\n										)\n								<br />\n\n							</small>");
		*/
		//printUserActivity("http://www.flickr.com/groups/weekly/discuss/72157594404819092/");
		//printUserActivity("http://www.flickr.com/groups/weekly/discuss/82228/");
		//printUserActivity("http://www.flickr.com/groups/weekly/discuss/72157620714827862/");
		printUserActivity("http://www.flickr.com/groups/weekly/discuss/72157622342116795/");
		printUserActivity("http://www.flickr.com/groups/weekly/discuss/72157622466066574/");
		printUserActivity("http://www.flickr.com/groups/weekly/discuss/72157622337751671/");
		printUserActivity("http://www.flickr.com/groups/weekly/discuss/72157622351587231/");
		printUserActivity("http://www.flickr.com/groups/weekly/discuss/72157622355127533/");
	}
	
	private static void printUserActivity(String url) throws Exception {
		System.out.println("----------");
		Map<String, WeeklyUserActivity> userActivities = DiscussPageParser.getUserActivity(url);
		for(Map.Entry<String, WeeklyUserActivity> entry : userActivities.entrySet())
		{
			WeeklyUserActivity userActivity = entry.getValue();
			System.out.println(userActivity.getUserId() + "| " + userActivity.getNumComments() + "| " + userActivity.getNumKeepers() + "| " + userActivity.getNumFreeflights());
		}
	}

	public static void printResult(String url) throws Exception {
		if (DiscussPageParser.isKeeper(url))
		{
			System.out.println(url + " is a Keeper");
		} else {
			System.out.println(url + " is a Free flight");
		}
	}
	
	public static void printKeepersFreeFlights(String text) throws Exception {
		System.out.print("Keepers = " + DiscussPageParser.getNumKeepers(text) + "; ");
		System.out.println("Freeflights = " + DiscussPageParser.getNumFreeflights(text));
	}
}
