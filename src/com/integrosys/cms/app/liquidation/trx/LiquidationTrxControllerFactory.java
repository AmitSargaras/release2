/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This factory creates transaction controller for liquidation LIQUIDATION given
 * its transaction parameters.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public LiquidationTrxControllerFactory() {
		super();
	}

	/**
	 * Get the ITrxController given the ITrxValue and ITrxParameter objects.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return security parameter transaction controller
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException if
	 *         any error occurs
	 */
	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (isReadOperation(param.getAction())) {
			return new LiquidationReadController();
		}
		return new LiquidationTrxController();
	}

	/**
	 * Helper method to check if the action requires a read operation or not.
	 * 
	 * @param anAction of type String
	 * @return boolean true if requires a read operation, otherwise false
	 */
	private boolean isReadOperation(String anAction) {
		if (anAction.equals(ICMSConstant.ACTION_READ_LIQUIDATION)
				|| anAction.equals(ICMSConstant.ACTION_READ_LIQUIDATION_BY_TRXREFID)
				|| anAction.equals(ICMSConstant.ACTION_READ_LIQUIDATION_BY_TRXID)) {
			return true;
		}
		else {
			return false;
		}
	}
}
