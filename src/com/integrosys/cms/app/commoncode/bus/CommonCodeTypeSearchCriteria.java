package com.integrosys.cms.app.commoncode.bus;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CommonCodeTypeSearchCriteria extends SearchCriteria {

	private int categoryType = -1;

	private boolean maintainRef = false;
	
	private long categoryID = ICMSConstant.LONG_INVALID_VALUE;
	
	String activeStatus;

	/**
	 * Default Constructor
	 */
	public CommonCodeTypeSearchCriteria() {
	}

	/**
	 * Getter Methods
	 */
	public int getCategoryType() {
		return this.categoryType;
	}

	/**
	 * Setter Methods
	 */
	public void setCategoryType(int categoryType) {
		this.categoryType = categoryType;
	}

	public boolean isMaintainRef() {
		return maintainRef;
	}

	public boolean getMaintainRef() {
		return maintainRef;
	}

	public void setMaintainRef(boolean maintainRef) {
		this.maintainRef = maintainRef;
	}

	public long getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}
	
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	public String getActiveStatus() {
		return activeStatus;
	}
}
