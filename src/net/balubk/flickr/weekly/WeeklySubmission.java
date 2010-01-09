package net.balubk.flickr.weekly;

public final class WeeklySubmission {

	private String userName;
	
	private String imageUrl;
	
	private int numReplies;
	
	private int numKeepers;
	
	private int numFreeflights;

	private String discussionPageUrl;

	public WeeklySubmission(String userName, String imageUrl, int numReplies, int numKeepers, int numFreeflights, String discussionPageUrl)
	{
		this.userName = userName;
		this.imageUrl = imageUrl;
		this.numReplies = numReplies;
		this.numKeepers = numKeepers;
		this.numFreeflights = numFreeflights;
		this.discussionPageUrl = discussionPageUrl;
	}

	public String getUserName() {
		return userName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public int getNumReplies() {
		return numReplies;
	}

	public int getNumKeepers() {
		return numKeepers;
	}

	public int getNumFreeflights() {
		return numFreeflights;
	}

	public String getDiscussionPageUrl() {
		return discussionPageUrl;
	}
}
