/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Contains actual and staging Liquidation for transaction usage.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBLiquidationTrxValue extends OBCMSTrxValue implements ILiquidationTrxValue {
	private ILiquidation actual;

	private ILiquidation staging;

	private long collateralID;

	/**
	 * Default constructor.
	 */
	public OBLiquidationTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_LIQUIDATION);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ILiquidationTrxValue
	 */
	public OBLiquidationTrxValue(ILiquidationTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBLiquidationTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue#getLiquidation
	 */
	public ILiquidation getLiquidation() {
		return actual;
	}

	/**
	 * @see com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue#setLiquidation
	 */
	public void setLiquidation(ILiquidation liquidation) {
		actual = liquidation;
	}

	/**
	 * @see com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue#getStagingLiquidation
	 */
	public ILiquidation getStagingLiquidation() {
		return staging;
	}

	/**
	 * @see com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue#setStagingLiquidation
	 */
	public void setStagingLiquidation(ILiquidation liquidation) {
		staging = liquidation;
	}

	public long getCollateralID() {
		return collateralID;
	}

	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
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
