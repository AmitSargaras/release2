/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CollateralSubTypeTrxControllerFactory.java,v 1.5 2003/08/15 07:14:07 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This factory creates transaction controller for security subtype given its
 * transaction parameters.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/15 07:14:07 $ Tag: $Name: $
 */
public class CollateralSubTypeTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public CollateralSubTypeTrxControllerFactory() {
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
			return new CollateralSubTypeReadController();
		}
		return new CollateralSubTypeTrxController();
	}

	/**
	 * Helper method to check if the action requires a read operation or not.
	 * 
	 * @param anAction of type String
	 * @return boolean true if requires a read operation, otherwise false
	 */
	private boolean isReadOperation(String anAction) {
		if (anAction.equals(ICMSConstant.ACTION_READ_SUBTYPE_BY_TYPECODE)
				|| anAction.equals(ICMSConstant.ACTION_READ_SUBTYPE_BY_TRXID)) {
			return true;
		}
		else {
			return false;
		}
	}
}
