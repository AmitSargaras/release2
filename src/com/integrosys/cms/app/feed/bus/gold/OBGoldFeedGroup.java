package com.integrosys.cms.app.feed.bus.gold;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class OBGoldFeedGroup implements IGoldFeedGroup {

	private String type;

	private String subType;

	private long goldFeedGroupID;

	private long versionTime = 0;

	private Set feedEntriesSet;

	private IGoldFeedEntry[] feedEntries;

	public IGoldFeedEntry[] getFeedEntries() {
		return feedEntries;
	}

	public Set getFeedEntriesSet() {
		return feedEntriesSet;
	}

	public long getGoldFeedGroupID() {
		return goldFeedGroupID;
	}

	public String getSubType() {
		return subType;
	}

	public String getType() {
		return type;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setFeedEntries(IGoldFeedEntry[] feedEntries) {
		this.feedEntries = feedEntries;

		this.feedEntriesSet = (feedEntries == null) ? new HashSet(0) : new HashSet(Arrays.asList(feedEntries));
	}

	public void setFeedEntriesSet(Set feedEntriesSet) {
		this.feedEntriesSet = feedEntriesSet;

		feedEntries = (feedEntriesSet == null) ? null : (IGoldFeedEntry[]) feedEntriesSet
				.toArray(new IGoldFeedEntry[0]);
	}

	public void setGoldFeedGroupID(long goldFeedGroupID) {
		this.goldFeedGroupID = goldFeedGroupID;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	
	public String toString() {
		return "\n"+super.toString()+"\ntype:"+type+"\nsubType:"+subType+"\ngoldFeedGroupID:"+goldFeedGroupID+
		"\nversionTime:"+versionTime+"\nfeedEntriesSet:"+feedEntriesSet+"\nfeedEntries:"+feedEntries;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		final OBGoldFeedGroup other = (OBGoldFeedGroup) obj;

		if (goldFeedGroupID != other.goldFeedGroupID) {
			return false;
		}
		
		if (versionTime != other.versionTime) {
			return false;
		}

		if (type == null) {
			if (other.type != null) {
				return false;
			}
		}
		else if (!type.equals(other.type)) {
			return false;
		}

		if (subType == null) {
			if (other.subType != null) {
				return false;
			}
		}
		else if (!subType.equals(other.subType)) {
			return false;
		}

		if (feedEntriesSet == null) {
			if (other.feedEntriesSet != null) {
				return false;
			}
		}
		else if (!feedEntriesSet.equals(other.feedEntriesSet)) {
			return false;
		}

		if (feedEntries == null) {
			if (other.feedEntries != null) {
				return false;
			}
		}
		else if (!feedEntries.equals(other.feedEntries)) {
			return false;
		}
		return true;
	}
}
