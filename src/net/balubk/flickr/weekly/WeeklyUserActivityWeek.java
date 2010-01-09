package net.balubk.flickr.weekly;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to store user activity by week.
 * 
 * @author Bala
 */
public final class WeeklyUserActivityWeek {

	private String week;
	
	private String startOfWeek;
	
	private String endOfWeek;
	
	/**
	 * Map of user activity, stored by user name.
	 */
	private Map<String, WeeklyUserActivity> userActivities = new HashMap<String, WeeklyUserActivity>();	

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

	public Map<String, WeeklyUserActivity> getUserActivities() {
		return userActivities;
	}

	public void setUserActivities(Map<String, WeeklyUserActivity> userActivities) {
		this.userActivities = userActivities;
	}

	public void addUserActivities(Map<String, WeeklyUserActivity> activities)
	{
		for(WeeklyUserActivity activity : activities.values())
		{
			addUserActivity(activity);
		}
	}
	public void addUserActivity(WeeklyUserActivity activity)
	{
		if (userActivities.containsKey(activity.getUserId()))
		{
			WeeklyUserActivity storedActivity = userActivities.get(activity.getUserId());
			storedActivity.setNumComments(storedActivity.getNumComments() + activity.getNumComments());
			storedActivity.setNumFreeflights(storedActivity.getNumFreeflights() + activity.getNumFreeflights());
			storedActivity.setNumKeepers(storedActivity.getNumKeepers() + activity.getNumKeepers());
		}
		else
		{
			userActivities.put(activity.getUserId(), activity);
		}
	}
}
