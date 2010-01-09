package net.balubk.flickr.weekly;

import java.util.ArrayList;
import java.util.List;

public final class WeeklyWeek {

	private String week;
	
	private String startOfWeek;
	
	private String endOfWeek;
	
	private List<WeeklySubmission> submissions = new ArrayList<WeeklySubmission>();
	
	public int getNumSubmissions()
	{
		return submissions.size();
	}

	public int getNumKeepers()
	{
		int count = 0;
		for (WeeklySubmission submission : submissions)
		{
			if (submission.getNumKeepers() >= submission.getNumFreeflights())
			{
				count++;
			}
		}
		return count;
	}

	public int getNumFreeflights()
	{
		int count = 0;
		for (WeeklySubmission submission : submissions)
		{
			if (submission.getNumFreeflights() > submission.getNumKeepers())
			{
				count++;
			}
		}
		return count;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getStartOfWeek() {
		return startOfWeek;
	}

	public void setStartOfWeek(String startOfWeek) {
		this.startOfWeek = startOfWeek;
	}

	public String getEndOfWeek() {
		return endOfWeek;
	}

	public void setEndOfWeek(String endOfWeek) {
		this.endOfWeek = endOfWeek;
	}

	public List<WeeklySubmission> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<WeeklySubmission> submissions) {
		this.submissions = submissions;
	}
	
	public void addSubmission(WeeklySubmission photo)
	{
		submissions.add(photo);
	}
}
