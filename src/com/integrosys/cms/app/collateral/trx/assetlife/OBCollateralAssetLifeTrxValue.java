/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.assetlife;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralAssetLife;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Contains actual and staging collateral assetlifes for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBCollateralAssetLifeTrxValue extends OBCMSTrxValue implements ICollateralAssetLifeTrxValue {
	private ICollateralAssetLife[] actual;

	private ICollateralAssetLife[] staging;

	/**
	 * Default constructor.
	 */
	public OBCollateralAssetLifeTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_COL_ASSETLIFE);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICollateralAssetLifeTrxValue
	 */
	public OBCollateralAssetLifeTrxValue(ICollateralAssetLifeTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBCollateralAssetLifeTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Gets the actual collateral assetlife objects in this transaction.
	 * 
	 * @return The actual collateral assetlife objects
	 */
	public ICollateralAssetLife[] getCollateralAssetLifes() {
		return actual;
	}

	/**
	 * Sets the actual collateral assetlife objects for this transaction.
	 * 
	 * @param assetlifes the actual collateral assetlife objects
	 */
	public void setCollateralAssetLifes(ICollateralAssetLife[] assetLifes) {
		actual = assetLifes;
	}

	/**
	 * Gets the staging collateral assetlife objects in this transaction.
	 * 
	 * @return the staging collateral assetlife objects
	 */
	public ICollateralAssetLife[] getStagingCollateralAssetLifes() {
		return staging;
	}

	/**
	 * Sets the staging collateral assetlife objects for this transaction.
	 * 
	 * @param assetlifes the staging collateral assetlife objects
	 */
	public void setStagingCollateralAssetLifes(ICollateralAssetLife[] assetLifes) {
		staging = assetLifes;
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
