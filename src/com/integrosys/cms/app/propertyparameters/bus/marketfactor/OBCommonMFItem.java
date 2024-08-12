/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Data model holds common values for Marketability Factor item.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBCommonMFItem implements ICommonMFItem, Comparable {

	private static final long serialVersionUID = 4871505341143168547L;

	private long mFItemID = ICMSConstant.LONG_INVALID_VALUE;

	private long mFParentID = ICMSConstant.LONG_INVALID_VALUE;

	private String factorDescription;

	private double weightPercentage;

	private long mFItemRef = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default Constructor.
	 */
	public OBCommonMFItem() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICommonMFItem
	 */
	public OBCommonMFItem(ICommonMFItem obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem#getMFItemID
	 */
	public long getMFItemID() {
		return this.mFItemID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem#setMFItemID
	 */
	public void setMFItemID(long mFItemID) {
		this.mFItemID = mFItemID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem#getMFParentID
	 */
	public long getMFParentID() {
		return this.mFParentID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem#setMFParentID
	 */
	public void setMFParentID(long mFParentID) {
		this.mFParentID = mFParentID;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem#getFactorDescription
	 */
	public String getFactorDescription() {
		return this.factorDescription;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem#setFactorDescription
	 */
	public void setFactorDescription(String factorDescription) {
		this.factorDescription = factorDescription;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem#getWeightPercentage
	 */
	public double getWeightPercentage() {
		return this.weightPercentage;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem#setWeightPercentage
	 */
	public void setWeightPercentage(double weightPercentage) {
		this.weightPercentage = weightPercentage;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem#getMFItemRef
	 */
	public long getMFItemRef() {
		return this.mFItemRef;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem#setMFItemRef
	 */
	public void setMFItemRef(long mFItemRef) {
		this.mFItemRef = mFItemRef;
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
		else if (!(obj instanceof OBCommonMFItem)) {
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

	public int compareTo(Object other) {
		String otherFactorDescription = (other == null) ? null : ((ICommonMFItem) other).getFactorDescription();

		if (this.factorDescription == null) {
			return (otherFactorDescription == null) ? 0 : -1;
		}

		return (otherFactorDescription == null) ? 1 : this.factorDescription.compareTo(otherFactorDescription);
	}

}