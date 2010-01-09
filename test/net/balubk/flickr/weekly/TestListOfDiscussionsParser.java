package net.balubk.flickr.weekly;

import java.util.Map;

import net.balubk.flickr.weekly.ListOfDiscussionsParser;
import net.balubk.flickr.weekly.WeeklyWeek;

public class TestListOfDiscussionsParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Map<String, WeeklyWeek> weeklyWeek;
		weeklyWeek = ListOfDiscussionsParser.getPageSummary("http://www.flickr.com/groups/weekly/discuss/page462/");
		printStatistics(weeklyWeek);
		weeklyWeek = ListOfDiscussionsParser.getPageSummary("http://www.flickr.com/groups/weekly/discuss/page2/");
		printStatistics(weeklyWeek);
	}

	public static void printStatistics(Map<String, WeeklyWeek> weeklyWeek)
	{
		for(Map.Entry<String, WeeklyWeek> entry : weeklyWeek.entrySet())
		{
			System.out.println("Week " + entry.getValue().getWeek() + ": " + entry.getValue().getNumSubmissions() + " Submissions; " + entry.getValue().getNumKeepers() + " Keepers; " + entry.getValue().getNumFreeflights() + " Free flights");
		}
	}
}
