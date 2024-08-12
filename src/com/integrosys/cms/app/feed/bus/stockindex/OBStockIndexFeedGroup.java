/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stockindex/OBStockIndexFeedGroup.java,v 1.1 2003/08/18 09:56:42 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stockindex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 09:56:42 $ Tag: $Name: $
 * 
 */
public class OBStockIndexFeedGroup implements IStockIndexFeedGroup {

	private String _type;

	private String _subType;

	private long _stockIndexFeedGroupID;

	private long _versionTime = 0;

	private Set feedEntriesSet;

	private IStockIndexFeedEntry[] feedEntries;

	public IStockIndexFeedEntry[] getFeedEntries() {
		return feedEntries;
	}

	public Set getFeedEntriesSet() {
		return this.feedEntriesSet;
	}

	public long getStockIndexFeedGroupID() {
		return this._stockIndexFeedGroupID;
	}

	public String getSubType() {
		return this._subType;
	}

	public String getType() {
		return this._type;
	}

	public long getVersionTime() {
		return this._versionTime;
	}

	public void setFeedEntries(IStockIndexFeedEntry[] param) {
		this.feedEntries = param;

		this.feedEntriesSet = (param == null) ? new HashSet(0) : new HashSet(Arrays.asList(param));
	}

	public void setFeedEntriesSet(Set feedEntriesSet) {
		this.feedEntriesSet = feedEntriesSet;

		this.feedEntries = (feedEntriesSet == null) ? null : (IStockIndexFeedEntry[]) feedEntriesSet
				.toArray(new IStockIndexFeedEntry[0]);
	}

	public void setStockIndexFeedGroupID(long param) {
		this._stockIndexFeedGroupID = param;
	}

	public void setSubType(String param) {
		this._subType = param;
	}

	public void setType(String param) {
		this._type = param;
	}

	public void setVersionTime(long versionTime) {
		this._versionTime = versionTime;
	}
}
