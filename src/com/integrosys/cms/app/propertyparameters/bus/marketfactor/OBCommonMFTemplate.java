/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Data model holds common values for Marketability Factor template.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBCommonMFTemplate implements ICommonMFTemplate {
	private long mFTemplateID = ICMSConstant.LONG_INVALID_VALUE;

	private String mFTemplateName;

	private Date lastUpdateDate;

	private String status;

	private long versionTime;

	/**
	 * Default Constructor.
	 */
	public OBCommonMFTemplate() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICommonMFTemplate
	 */
	public OBCommonMFTemplate(ICommonMFTemplate obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate#getMFTemplateID
	 */
	public long getMFTemplateID() {
		return this.mFTemplateID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate#setMFTemplateID
	 */
	public void setMFTemplateID(long mFTemplateID) {
		this.mFTemplateID = mFTemplateID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate#getMFTemplateName
	 */
	public String getMFTemplateName() {
		return this.mFTemplateName;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate#setMFTemplateName
	 */
	public void setMFTemplateName(String mFTemplateName) {
		this.mFTemplateName = mFTemplateName;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate#getLastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate#setLastUpdateDate
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate#getStatus
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate#setStatus
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate#getVersionTime
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate#setVersionTime
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBCommonMFTemplate)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}