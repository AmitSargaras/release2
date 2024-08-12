package com.integrosys.cms.app.commoncode.bus;

import com.integrosys.component.commondata.app.Constants;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class OBCommonCodeType implements ICommonCodeType {

	long commonCategoryId;

	String commonCategoryCode;

	String commonCategoryName;

	int commonCategoryType;

	String activeStatus;

	long versionTime;

	String refCategoryCode;

	public long getCommonCategoryId() {
		return commonCategoryId;
	}

	public void setCommonCategoryId(long commonCategoryId) {
		this.commonCategoryId = commonCategoryId;
	}

	public String getCommonCategoryCode() {
		return commonCategoryCode;
	}

	public void setCommonCategoryCode(String commonCategoryCode) {
		this.commonCategoryCode = commonCategoryCode;
	}

	public String getCommonCategoryName() {
		return commonCategoryName;
	}

	public void setCommonCategoryName(String commonCategoryName) {
		this.commonCategoryName = commonCategoryName;
	}

	public int getCommonCategoryType() {
		return commonCategoryType;
	}

	public void setCommonCategoryType(int commonCategoryType) {
		this.commonCategoryType = commonCategoryType;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		if (activeStatus == null) {
			activeStatus = Constants.STATUS_INACTIVE;
		}
		this.activeStatus = activeStatus;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getRefCategoryCode() {
		return refCategoryCode;
	}

	public void setRefCategoryCode(String refCategoryCode) {
		this.refCategoryCode = refCategoryCode;
	}

}
