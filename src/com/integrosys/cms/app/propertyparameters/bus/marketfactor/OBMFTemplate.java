/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds MF Template.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBMFTemplate extends OBCommonMFTemplate implements IMFTemplate {
	private String mFTemplateStatus;

	private String secTypeID;

	private IMFTemplateSecSubType[] secSubTypeList;

	private IMFItem[] mFItemList;

	/**
	 * Default Constructor.
	 */
	public OBMFTemplate() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IMFTemplate
	 */
	public OBMFTemplate(IMFTemplate obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#getMFTemplateStatus
	 */
	public String getMFTemplateStatus() {
		return this.mFTemplateStatus;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#setMFTemplateStatus
	 */
	public void setMFTemplateStatus(String mFTemplateStatus) {
		this.mFTemplateStatus = mFTemplateStatus;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#getSecurityTypeID
	 */
	public String getSecurityTypeID() {
		return this.secTypeID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#setSecurityTypeID
	 */
	public void setSecurityTypeID(String secTypeID) {
		this.secTypeID = secTypeID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#getSecuritySubTypeList
	 */
	public IMFTemplateSecSubType[] getSecuritySubTypeList() {
		return this.secSubTypeList;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#setSecuritySubTypeList
	 */
	public void setSecuritySubTypeList(IMFTemplateSecSubType[] secSubTypeList) {
		this.secSubTypeList = secSubTypeList;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#getMFItemList
	 */
	public IMFItem[] getMFItemList() {
		return this.mFItemList;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#setMFItemList
	 */
	public void setMFItemList(IMFItem[] mFItemList) {
		this.mFItemList = mFItemList;
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
		else if (!(obj instanceof OBMFTemplate)) {
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