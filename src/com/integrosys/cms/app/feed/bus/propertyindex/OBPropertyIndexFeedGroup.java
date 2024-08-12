/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/OBPropertyIndexFeedGroup.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 * 
 */
public class OBPropertyIndexFeedGroup implements IPropertyIndexFeedGroup {

	private IPropertyIndexFeedEntry[] _feedEntries;

	private String _type;

	private String _subType;

	private long _propertyIndexFeedGroupID;

	private long _versionTime = 0;

	public IPropertyIndexFeedEntry[] getFeedEntries() {
		return this._feedEntries;
	}

	public String getType() {
		return this._type;
	}

	public String getSubType() {
		return this._subType;
	}

	public long getPropertyIndexFeedGroupID() {
		return this._propertyIndexFeedGroupID;
	}

	public void setFeedEntries(IPropertyIndexFeedEntry[] param) {
		this._feedEntries = param;
	}

	public void setType(String param) {
		this._type = param;
	}

	public void setSubType(String param) {
		this._subType = param;
	}

	public void setPropertyIndexFeedGroupID(long param) {
		this._propertyIndexFeedGroupID = param;
	}

	public long getVersionTime() {
		return this._versionTime;
	}

	public void setVersionTime(long versionTime) {
		this._versionTime = versionTime;
	}
}
