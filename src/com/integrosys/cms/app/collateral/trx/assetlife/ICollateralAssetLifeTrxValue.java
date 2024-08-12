/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.assetlife;

import com.integrosys.cms.app.collateral.bus.ICollateralAssetLife;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual collateral assetlife and staging collateral assetlife for
 * transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ICollateralAssetLifeTrxValue extends ICMSTrxValue {
	/**
	 * Gets the actual collateral assetlife objects in this transaction.
	 * 
	 * @return The actual collateral assetlife objects
	 */
	public ICollateralAssetLife[] getCollateralAssetLifes();

	/**
	 * Sets the actual collateral assetlife objects for this transaction.
	 * 
	 * @param assetLifes the actual collateral assetlife objects
	 */
	public void setCollateralAssetLifes(ICollateralAssetLife[] assetLifes);

	/**
	 * Gets the staging collateral assetlife objects in this transaction.
	 * 
	 * @return the staging collateral assetlife objects
	 */
	public ICollateralAssetLife[] getStagingCollateralAssetLifes();

	/**
	 * Sets the staging collateral assetlife objects for this transaction.
	 * 
	 * @param assetLifes the staging collateral assetlife objects
	 */
	public void setStagingCollateralAssetLifes(ICollateralAssetLife[] assetLifes);

}
