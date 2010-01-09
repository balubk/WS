package net.balubk.flickr.weekly;

import net.balubk.flickr.weekly.TitleUtils;

public class TestTitleUtils {

	public static void main(String[] args) {
		System.out.println("user name = " + TitleUtils.getUserName("Week 211 (20th of July - 26th of July) - Bill(iudshi8uf)"));
		System.out.println("user name = " + TitleUtils.getUserName("Closed - Week 209 (06th of July - 12th of July) - Nathaniel S. Madore (-,-)"));
		System.out.println("user name = " + TitleUtils.getUserName("Voting closed - Week one (3rd of July to 10th of July) - mahtab"));
		System.out.println("user name = " + TitleUtils.getUserName("Closed - Week 18th (6th of November - 13th of November) – Stitch"));
		System.out.println("user name = " + TitleUtils.getUserName("Closed - Week 15th (16th of october - 23rd of october) – howiefowler"));
		System.out.println("user name = " + TitleUtils.getUserName("Closed - Week 15th (16th of october - 23rd of october) – automat"));
		System.out.println("user name = " + TitleUtils.getUserName("Voting closed - Week one (3rd of July to 10th of July) - |5ubxt@nc 3|"));
		System.out.println("Week = " + TitleUtils.getWeek("Voting closed - Week one (3rd of July to 10th of July) - Frederico Mendes"));
	}
}
