/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/OBCollateralSubTypeTrxValue.java,v 1.5 2003/08/14 07:48:27 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Contains actual and staging collateral subtypes for transaction usage.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/14 07:48:27 $ Tag: $Name: $
 */
public class OBCollateralSubTypeTrxValue extends OBCMSTrxValue implements ICollateralSubTypeTrxValue {
	private ICollateralSubType[] actual;

	private ICollateralSubType[] staging;

	private String collateralTypeCode;

	/**
	 * Default constructor.
	 */
	public OBCollateralSubTypeTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_COL_SUBTYPE);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICollateralSubTypeTrxValue
	 */
	public OBCollateralSubTypeTrxValue(ICollateralSubTypeTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBCollateralSubTypeTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Gets the actual collateral subtype objects in this transaction.
	 * 
	 * @return The actual collateral subtype objects
	 */
	public ICollateralSubType[] getCollateralSubTypes() {
		return actual;
	}

	/**
	 * Sets the actual collateral subtype objects for this transaction.
	 * 
	 * @param subTypes the actual collateral subtype objects
	 */
	public void setCollateralSubTypes(ICollateralSubType[] subTypes) {
		actual = subTypes;
	}

	/**
	 * Gets the staging collateral subtype objects in this transaction.
	 * 
	 * @return the staging collateral subtype objects
	 */
	public ICollateralSubType[] getStagingCollateralSubTypes() {
		return staging;
	}

	/**
	 * Sets the staging collateral subtype objects for this transaction.
	 * 
	 * @param subTypes the staging collateral subtype objects
	 */
	public void setStagingCollateralSubTypes(ICollateralSubType[] subTypes) {
		staging = subTypes;
	}

	/**
	 * Get collateral type code.
	 * 
	 * @return String
	 */
	public String getCollateralTypeCode() {
		return collateralTypeCode;
	}

	/**
	 * Set collateral type code.
	 * 
	 * @param collateralTypeCode of type String
	 */
	public void setCollateralTypeCode(String collateralTypeCode) {
		this.collateralTypeCode = collateralTypeCode;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
