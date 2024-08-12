/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.assetlife;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This factory creates transaction controller for security assetlife given its
 * transaction parameters.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class CollateralAssetLifeTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public CollateralAssetLifeTrxControllerFactory() {
		super();
	}

	/**
	 * Get the ITrxController given the ITrxValue and ITrxParameter objects.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return security parameter transaction controller
	 * @throws TrxParameterException if any error occurs
	 */
	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (isReadOperation(param.getAction())) {
			return new CollateralAssetLifeReadController();
		}
		return new CollateralAssetLifeTrxController();
	}

	/**
	 * Helper method to check if the action requires a read operation or not.
	 * 
	 * @param anAction of type String
	 * @return boolean true if requires a read operation, otherwise false
	 */
	private boolean isReadOperation(String anAction) {
		if (anAction.equals(ICMSConstant.ACTION_READ_ASSETLIFE)
				|| anAction.equals(ICMSConstant.ACTION_READ_ASSETLIFE_BY_TRXID)) {
			return true;
		}
		else {
			return false;
		}
	}
}
