package net.balubk.flickr.weekly;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import com.thoughtworks.xstream.XStream;

public class WeeklyStatistics {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParsingException 
	 * @throws ValidityException 
	 */
	public static void main(String[] args) throws ValidityException, ParsingException, IOException {
		final int num_pages = 491;
		Map<String, WeeklyWeek> allWeeksMap = new HashMap<String, WeeklyWeek>(220);
		for (int i = 1; i <= num_pages; i++) {
			System.out.println("Page " + i);
			Map<String, WeeklyWeek> weeklyMap = ListOfDiscussionsParser.getPageSummary("http://www.flickr.com/groups/weekly/discuss/page" + i);
			mergeMaps(allWeeksMap, weeklyMap);
		}
		dumpStatistics(allWeeksMap);
	}

	private static void dumpStatistics(Map<String, WeeklyWeek> allWeeksMap) throws IOException {
		XStream xs = new XStream();
		FileOutputStream fos = new FileOutputStream("WeeklyStatistics.xml");
		xs.toXML(allWeeksMap, fos);
		fos.close();
		fos = new FileOutputStream("WeeklyStatistics.csv");
		PrintStream ps = new PrintStream(fos);
		for(Map.Entry<String, WeeklyWeek> entry : allWeeksMap.entrySet()) {
			FileOutputStream fos2 = new FileOutputStream(entry.getKey() + ".xml");
			xs.toXML(entry.getValue(), fos2);
			fos2.close();
			WeeklyWeek week = entry.getValue();
			ps.println(week.getWeek() + "\t" + week.getStartOfWeek() + "\t" + week.getEndOfWeek() + "\t" + week.getNumSubmissions() + "\t" + week.getNumKeepers() + "\t" + week.getNumFreeflights());
			
			fos2 = new FileOutputStream(entry.getKey() + ".csv");
			PrintStream ps2 = new PrintStream(fos2);
			for(WeeklySubmission sub : week.getSubmissions()) {
				ps2.println(sub.getUserName() + "\t" + sub.getImageUrl() + "\t" + sub.getNumReplies() + "\t" + sub.getNumKeepers() + "\t" + sub.getNumFreeflights() + "\t" + sub.getDiscussionPageUrl());
			}
			ps2.close();
			fos2.close();
		}
		ps.close();
		fos.close();
	}

	private static void mergeMaps(Map<String, WeeklyWeek> allWeeksMap,
			Map<String, WeeklyWeek> weeklyMap) {
		for(Map.Entry<String, WeeklyWeek> entry : weeklyMap.entrySet()) {
			if (allWeeksMap.containsKey(entry.getKey())) {
				WeeklyWeek week = allWeeksMap.get(entry.getKey());
				week.getSubmissions().addAll(entry.getValue().getSubmissions());
			} else {
				allWeeksMap.put(entry.getKey(), entry.getValue());
			}
		}
	}

}
