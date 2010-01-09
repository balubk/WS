package net.balubk.flickr.weekly;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import com.thoughtworks.xstream.XStream;

public class WeeklyUserActivityStatistics {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParsingException 
	 * @throws ValidityException 
	 */
	public static void main(String[] args) throws ValidityException, ParsingException, IOException {
		final int num_pages = 506;
		Map<String, WeeklyUserActivityWeek> allWeeksMap = new HashMap<String, WeeklyUserActivityWeek>(220);
		for (int i = 1; i <= num_pages; i++) {
			System.out.println("Page " + i);
			Map<String, WeeklyUserActivityWeek> weeklyMap = ListOfDiscussionsParser.getUserActivityPageSummary("http://www.flickr.com/groups/weekly/discuss/page" + i);
			mergeMaps(allWeeksMap, weeklyMap);
		}
		dumpStatistics(allWeeksMap);
	}

	private static void dumpStatistics(Map<String, WeeklyUserActivityWeek> allWeeksMap) throws IOException {
		XStream xs = new XStream();
		FileOutputStream fos = new FileOutputStream("WeeklyUserActivityStatistics.xml");
		xs.toXML(allWeeksMap, fos);
		fos.close();
		// map to store user activity summed over ALL weeks
		Map<String, WeeklyUserActivity> allWeekUserActivityMap = new HashMap<String, WeeklyUserActivity>();
		for(Map.Entry<String, WeeklyUserActivityWeek> entry : allWeeksMap.entrySet()) {
			FileOutputStream fos2 = new FileOutputStream(entry.getKey() + ".xml");
			xs.toXML(entry.getValue(), fos2);
			fos2.close();
			WeeklyUserActivityWeek week = entry.getValue();
			
			fos2 = new FileOutputStream(entry.getKey() + ".csv");
			PrintStream ps2 = new PrintStream(fos2);
			Map<String, WeeklyUserActivity> userActivities = week.getUserActivities();
			for(WeeklyUserActivity userActivity : userActivities.values())
			{
				ps2.println(userActivity.getUserId() + "\t" + userActivity.getNumComments() + "\t" + userActivity.getNumKeepers() + "\t" + userActivity.getNumFreeflights());
				if (allWeekUserActivityMap.containsKey(userActivity.getUserId()))
				{
					WeeklyUserActivity storedActivity = allWeekUserActivityMap.get(userActivity.getUserId());
					storedActivity.setNumComments(storedActivity.getNumComments() + userActivity.getNumComments());
					storedActivity.setNumKeepers(storedActivity.getNumKeepers() + userActivity.getNumKeepers());
					storedActivity.setNumFreeflights(storedActivity.getNumFreeflights() + userActivity.getNumFreeflights());
				}
				else
				{
					allWeekUserActivityMap.put(userActivity.getUserId(), userActivity);
				}
			}
			ps2.close();
			fos2.close();
		}
		fos = new FileOutputStream("WeeklyUserActivityStatistics.csv");
		PrintStream ps = new PrintStream(fos);
		for(WeeklyUserActivity userActivity : allWeekUserActivityMap.values())
		{
			ps.println(userActivity.getUserId() + "\t" + userActivity.getNumComments() + "\t" + userActivity.getNumKeepers() + "\t" + userActivity.getNumFreeflights());
		}
		ps.close();
		fos.close();
	}

	private static void mergeMaps(Map<String, WeeklyUserActivityWeek> allWeeksMap,
			Map<String, WeeklyUserActivityWeek> weeklyMap) {
		for(Map.Entry<String, WeeklyUserActivityWeek> entry : weeklyMap.entrySet()) {
			if (allWeeksMap.containsKey(entry.getKey())) {
				WeeklyUserActivityWeek week = allWeeksMap.get(entry.getKey());
				week.addUserActivities(entry.getValue().getUserActivities());
			} else {
				allWeeksMap.put(entry.getKey(), entry.getValue());
			}
		}
	}

}
