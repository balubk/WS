/**
 * 
 */
package net.balubk.flickr.weekly;

import java.io.Serializable;

/**
 * Class to store the number of replies/votes a given user has made in a week.
 * @author Bala
 */
public class WeeklyUserActivity implements Serializable {

	private static final long serialVersionUID = -1599443667891426391L;

	private String userId;
	
	private int numComments;
	
	private int numKeepers;
	
	private int numFreeflights;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getNumComments() {
		return numComments;
	}

	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}

	public int getNumKeepers() {
		return numKeepers;
	}

	public void setNumKeepers(int numKeepers) {
		this.numKeepers = numKeepers;
	}

	public int getNumFreeflights() {
		return numFreeflights;
	}

	public void setNumFreeflights(int numFreeflights) {
		this.numFreeflights = numFreeflights;
	}
}
