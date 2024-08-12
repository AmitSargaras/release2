/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.trx;

import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.OBLiquidation;

/**
 * A helper class for liquidation, contains common methods shared among
 * liquidation.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationHelper {

	/**
	 * Initial the liquidation value based on the liquidation type and month.
	 * @param liquidationID
	 * 
	 * @return a Liquidation object
	 */
	public static ILiquidation initialLiquidation(long liquidationID) {

		OBLiquidation liq = new OBLiquidation();
		liq.setLiquidationID(new Long(liquidationID));
		liq.setRecoveryExpense(null);
		liq.setRecovery(null);
		return liq;
	}
}
