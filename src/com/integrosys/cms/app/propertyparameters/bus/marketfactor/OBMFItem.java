/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds MF Item.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBMFItem extends OBCommonMFItem implements IMFItem {
	private String status;

	/**
	 * Default Constructor.
	 */
	public OBMFItem() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IMFItem
	 */
	public OBMFItem(IMFItem obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem#getStatus
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem#setStatus
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
		else if (!(obj instanceof OBMFItem)) {
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