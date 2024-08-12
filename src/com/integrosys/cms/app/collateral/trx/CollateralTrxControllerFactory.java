/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/trx/CustomerTrxControllerFactory.java,v 1.2 2003/07/01 10:45:03 kllee Exp $
 */
package com.integrosys.cms.app.collateral.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create Customer related controllers.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/01 10:45:03 $ Tag: $Name: $
 */
public class CollateralTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public CollateralTrxControllerFactory() {
		super();
	}

	/**
	 * Returns an ITrxController given the ITrxValue and ITrxParameter objects
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return ITrxController
	 * @throws TrxParameterException if any error occurs
	 */
	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (isReadOperation(param.getAction())) {
			return new CollateralReadController();
		}
		else {
			return new CollateralTrxController();
		}
	}

	/**
	 * To check if the action requires a read operation or not
	 * @param anAction - String
	 * @return boolean - true if requires a read operation and false otherwise
	 */
	private boolean isReadOperation(String anAction) {
		if (anAction.equals(ICMSConstant.ACTION_READ_CUSTOMER) || anAction.equals(ICMSConstant.ACTION_READ_CUSTOMER_ID)) {

			return true;
		}
		else {
			return false;
		}
	}
}