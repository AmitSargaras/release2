package com.integrosys.cms.app.feed.bus.gold;

import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IGoldFeedGroup extends java.io.Serializable, IValueObject {
	IGoldFeedEntry[] getFeedEntries();

	Set getFeedEntriesSet();

	long getGoldFeedGroupID();

	String getSubType();

	String getType();

	long getVersionTime();

	void setFeedEntries(IGoldFeedEntry[] param);

	/**
	 * <p>
	 * Persistence field to deal with feed entries array. <b>Not</b> supposed to
	 * be called in application.
	 * 
	 * <p>
	 * To be called in two places at most.
	 * <ol>
	 * <li>when doing replication to create staging copy
	 * <li>retrieving data from persistence storage.
	 * </ol>
	 * 
	 * @param feedEntriesSet feed entries set
	 */
	void setFeedEntriesSet(Set feedEntriesSet);

	void setGoldFeedGroupID(long param);

	void setSubType(String param);

	void setType(String param);

	void setVersionTime(long versionTime);
}
