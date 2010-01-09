package net.balubk.flickr.weekly;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.balubk.flickr.weekly.WeeklyWeek;

import com.thoughtworks.xstream.XStream;

public class TestReadXml {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		XStream xs = new XStream();
		FileInputStream input = new FileInputStream("WeeklyStatistics.xml");
		Object obj = xs.fromXML(input);
		if (obj instanceof Map)
		{
			Map<String, WeeklyWeek> allWeeks = (Map<String, WeeklyWeek>)obj;
			System.out.println("Got " + allWeeks.size() + " weeks");
			List<String> weekNums = new ArrayList<String>(allWeeks.size());
			weekNums.addAll(allWeeks.keySet());
			Collections.sort(weekNums);
			for(String weekNum : weekNums) {
				WeeklyWeek week = allWeeks.get(weekNum);
				System.out.println("Week " + weekNum + " - " + week.getNumSubmissions() + " submissions, " + week.getNumKeepers() + " keepers and " + week.getNumFreeflights() + " free flights");
			}
		}
		input.close();
	}
}
