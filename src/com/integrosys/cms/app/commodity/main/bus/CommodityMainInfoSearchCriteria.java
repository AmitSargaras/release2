/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/CommodityMainInfoSearchCriteria.java,v 1.2 2004/06/04 04:52:40 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Created by IntelliJ IDEA. User: dayanand Date: Mar 24, 2004 Time: 11:15:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommodityMainInfoSearchCriteria extends SearchCriteria {

	public static final String SEARCH_BY_ID = "searchByID";

	public static final String SEARCH_BY_GROUP_ID = "searchByGroupID";

	// ICommodityMainInfo.INFO_TYPE_TITLEDOC ,
	// ICommodityMainInfo.INFO_TYPE_TITLEDOC
	private String infoType;

	private boolean includeDeleted;

	private Long infoID;

	private Long groupID;

	private String searchBy;

	public CommodityMainInfoSearchCriteria(String infoType) {
		this.infoType = infoType;
		includeDeleted = true;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public boolean isIncludeDeleted() {
		return includeDeleted;
	}

	public void setIncludeDeleted(boolean includeDeleted) {
		this.includeDeleted = includeDeleted;
	}

	public Long getInfoID() {
		return infoID;
	}

	public void setInfoID(Long infoID) {
		this.infoID = infoID;
	}

	public Long getGroupID() {
		return groupID;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
