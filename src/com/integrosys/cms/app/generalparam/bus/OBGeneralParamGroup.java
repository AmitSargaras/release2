/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/OBBondFeedGroup.java,v 1.2 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.generalparam.bus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class OBGeneralParamGroup implements IGeneralParamGroup {
	
	private long _generalParamGroupID;
	
	private Set feedEntriesSet;
	
	private String _type;

	private String _subType;

	private String _stockType;

	private long _versionTime = 0;

	private IGeneralParamEntry[] feedEntries;
	

	public IGeneralParamEntry[] getFeedEntries() {
		return feedEntries;
	}

	public Set getFeedEntriesSet() {
		return feedEntriesSet;
	}

	public void setFeedEntries(IGeneralParamEntry[] param) {
		feedEntries = param;

		this.feedEntriesSet = (param == null) ? new HashSet(0) : new HashSet(Arrays.asList(param));
	}

	public void setFeedEntriesSet(Set feedEntriesSet) {
		this.feedEntriesSet = feedEntriesSet;

		this.feedEntries = (feedEntriesSet == null) ? null : (IGeneralParamEntry[]) feedEntriesSet
				.toArray(new IGeneralParamEntry[0]);
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		this._type = type;
	}

	public String getSubType() {
		return _subType;
	}

	public void setSubType(String subType) {
		this._subType = subType;
	}

	public String getStockType() {
		return _stockType;
	}

	public void setStockType(String stockType) {
		this._stockType = stockType;
	}

	public long getVersionTime() {
		return _versionTime;
	}

	public void setVersionTime(long versionTime) {
		this._versionTime = versionTime;
	}

	public long getGeneralParamGroupID() {
		return _generalParamGroupID;
	}

	public void setGeneralParamGroupID(long generalParamGroupID) {
		this._generalParamGroupID = generalParamGroupID;
	}

}
