/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.OBCommonMFItem;

/**
 * Data model holds MF Checklist Item. This class is a subclass of
 * OBCommonMFItem.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBMFChecklistItem extends OBCommonMFItem implements IMFChecklistItem {
	private double valuerAssignFactor = ICMSConstant.DOUBLE_INVALID_VALUE;

	private double weightScore;

	/**
	 * Default Constructor.
	 */
	public OBMFChecklistItem() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IMFChecklistItem
	 */
	public OBMFChecklistItem(IMFChecklistItem obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICommonMFItem
	 */
	public OBMFChecklistItem(ICommonMFItem obj) {
		super(obj);
	}

	/**
	 * @see com.integrosys.cms.app..collateral.bus.type.property.marketfactor.
	 *      IMFChecklistItem#getValuerAssignFactor
	 */
	public double getValuerAssignFactor() {
		return this.valuerAssignFactor;
	}

	/**
	 * @see com.integrosys.cms.app..collateral.bus.type.property.marketfactor.
	 *      IMFChecklistItem#setValuerAssignFactor
	 */
	public void setValuerAssignFactor(double valuerAssignFactor) {
		this.valuerAssignFactor = valuerAssignFactor;
	}

	/**
	 * @see com.integrosys.cms.app..collateral.bus.type.property.marketfactor.
	 *      IMFChecklistItem#getWeightScore
	 */
	public double getWeightScore() {
		return this.weightScore;
	}

	/**
	 * @see com.integrosys.cms.app..collateral.bus.type.property.marketfactor.
	 *      IMFChecklistItem#setWeightScore
	 */
	public void setWeightScore(double weightScore) {
		this.weightScore = weightScore;
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
		else if (!(obj instanceof OBMFChecklistItem)) {
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