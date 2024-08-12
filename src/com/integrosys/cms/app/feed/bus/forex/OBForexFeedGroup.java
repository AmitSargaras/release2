/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/OBForexFeedGroup.java,v 1.1 2003/07/23 11:11:44 phtan Exp $
 */
package com.integrosys.cms.app.feed.bus.forex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author $Author: phtan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/23 11:11:44 $ Tag: $Name: $
 * 
 */
public class OBForexFeedGroup implements IForexFeedGroup {

	private String type;

	private String subType;

	private long forexFeedGroupID;

	private long versionTime = 0;

	private Set feedEntriesSet;

	private IForexFeedEntry[] feedEntries;

	public IForexFeedEntry[] getFeedEntries() {
		return feedEntries;
	}

	public Set getFeedEntriesSet() {
		return feedEntriesSet;
	}

	public long getForexFeedGroupID() {
		return forexFeedGroupID;
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

	public void setFeedEntries(IForexFeedEntry[] feedEntries) {
		this.feedEntries = feedEntries;

		this.feedEntriesSet = (feedEntries == null) ? new HashSet(0) : new HashSet(Arrays.asList(feedEntries));
	}

	public void setFeedEntriesSet(Set feedEntriesSet) {
		this.feedEntriesSet = feedEntriesSet;

		feedEntries = (feedEntriesSet == null) ? null : (IForexFeedEntry[]) feedEntriesSet
				.toArray(new IForexFeedEntry[0]);
	}

	public void setForexFeedGroupID(long forexFeedGroupID) {
		this.forexFeedGroupID = forexFeedGroupID;
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

	@Override
	public void setParentGroupCodeList(Set parentGroupCodeList) {
		// TODO Auto-generated method stub
		
	}
}
