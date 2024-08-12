/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Model to hold MF Template Security SubType.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBMFTemplateSecSubType implements IMFTemplateSecSubType {

	private long templateSubTypeID;

	private long mFTemplateID;

	private String secSubTypeID;

	private String status;

	public OBMFTemplateSecSubType() {
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#getTemplateSubTypeID
	 */
	public long getTemplateSubTypeID() {
		return this.templateSubTypeID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#setTemplateSubTypeID
	 */
	public void setTemplateSubTypeID(long templateSubTypeID) {
		this.templateSubTypeID = templateSubTypeID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#getMFTemplateID
	 */
	public long getMFTemplateID() {
		return this.mFTemplateID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#setMFTemplateID
	 */
	public void setMFTemplateID(long mFTemplateID) {
		this.mFTemplateID = mFTemplateID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#getSecSubTypeID
	 */
	public String getSecSubTypeID() {
		return this.secSubTypeID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#setSecSubTypeID
	 */
	public void setSecSubTypeID(String secSubTypeID) {
		this.secSubTypeID = secSubTypeID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#getStatus
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#setStatus
	 */
	public void setStatus(String status) {
		this.status = status;
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
		else if (!(obj instanceof OBMFTemplateSecSubType)) {
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
