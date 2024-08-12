/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/unittrust/OBUnitTrustFeedGroup.java,v 1.2 2003/08/08 04:26:15 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.unittrust;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/08 04:26:15 $ Tag: $Name: $
 * 
 */
public class OBUnitTrustFeedGroup implements IUnitTrustFeedGroup {

	// private IUnitTrustFeedEntry[] _feedEntries;

	private String _type;

	private String _subType;

	private String _stockType;

	private long _unitTrustFeedGroupID;

	private long _versionTime = 0;

	private IUnitTrustFeedEntry[] feedEntries;

	private Set feedEntriesSet;

	public IUnitTrustFeedEntry[] getFeedEntries() {
		return this.feedEntries;
	}

	public Set getFeedEntriesSet() {
		return feedEntriesSet;
	}

	public String getStockType() {
		return _stockType;
	}

	public String getSubType() {
		return this._subType;
	}

	public String getType() {
		return this._type;
	}

	public long getUnitTrustFeedGroupID() {
		return this._unitTrustFeedGroupID;
	}

	public long getVersionTime() {
		return this._versionTime;
	}

	public void setFeedEntries(IUnitTrustFeedEntry[] param) {
		this.feedEntries = param;

		this.feedEntriesSet = (param == null) ? new HashSet(0) : new HashSet(Arrays.asList(param));
	}

	public void setFeedEntriesSet(Set feedEntriesSet) {
		this.feedEntriesSet = feedEntriesSet;

		this.feedEntries = (feedEntriesSet == null) ? null : (IUnitTrustFeedEntry[]) feedEntriesSet
				.toArray(new IUnitTrustFeedEntry[0]);
	}

	public void setStockType(String stockType) {
		this._stockType = stockType;
	}

	public void setSubType(String param) {
		this._subType = param;
	}

	public void setType(String param) {
		this._type = param;
	}

	public void setUnitTrustFeedGroupID(long param) {
		this._unitTrustFeedGroupID = param;
	}

	public void setVersionTime(long versionTime) {
		this._versionTime = versionTime;
	}

}