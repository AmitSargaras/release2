/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/OBStockFeedGroup.java,v 1.2 2003/08/07 08:34:09 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/07 08:34:09 $ Tag: $Name: $
 * 
 */
public class OBStockFeedGroup implements IStockFeedGroup {

	private Set feedEntriesSet;

	private IStockFeedEntry[] feedEntries;

	private String _type;

	private String _subType;

	private String _stockType;

	private long _stockFeedGroupID;

	private long _versionTime = 0;

	public IStockFeedEntry[] getFeedEntries() {
		return feedEntries;
	}

	public Set getFeedEntriesSet() {
		return feedEntriesSet;
	}

	public long getStockFeedGroupID() {
		return this._stockFeedGroupID;
	}

	public String getStockType() {
		return this._stockType;
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

	public void setFeedEntries(IStockFeedEntry[] param) {
		this.feedEntries = param;
		this.feedEntriesSet = (param == null) ? new HashSet(0) : new HashSet(Arrays.asList(param));
	}

	public void setFeedEntriesSet(Set feedEntriesSet) {
		this.feedEntriesSet = feedEntriesSet;

		this.feedEntries = (feedEntriesSet == null) ? null : (IStockFeedEntry[]) this.feedEntriesSet
				.toArray(new IStockFeedEntry[0]);
	}

	public void setStockFeedGroupID(long param) {
		this._stockFeedGroupID = param;
	}

	public void setStockType(String param) {
		this._stockType = param;
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
