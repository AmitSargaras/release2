/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.trx;

import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual liquidation and staging liquidation for transaction usage.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ILiquidationTrxValue extends ICMSTrxValue {
	public long getCollateralID();

	public void setCollateralID(long collateralID);

	/**
	 * Gets the actual Liquidation objects in this transaction.
	 * 
	 * @return The actual Liquidation objects
	 */
	public ILiquidation getLiquidation();

	/**
	 * Sets the actual Liquidation objects for this transaction.
	 * 
	 * @param liquidation the actual Liquidation objects
	 */
	public void setLiquidation(ILiquidation liquidation);

	/**
	 * Gets the staging Liquidation objects in this transaction.
	 * 
	 * @return the staging Liquidation objects
	 */
	public ILiquidation getStagingLiquidation();

	/**
	 * Sets the staging Liquidation objects for this transaction.
	 * 
	 * @param liquidation the staging Liquidation objects
	 */
	public void setStagingLiquidation(ILiquidation liquidation);

}
