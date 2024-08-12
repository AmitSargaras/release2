/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/LimitTrxControllerFactory.java,v 1.2 2005/09/07 06:56:05 lyng Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create Limit related controllers.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/07 06:56:05 $ Tag: $Name: $
 */
public class LimitTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public LimitTrxControllerFactory() {
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
			return new LimitReadController();
		}
		else {
			return new LimitTrxController();
		}
	}

	/**
	 * To check if the action requires a read operation or not
	 * @param anAction - String
	 * @return boolean - true if requires a read operation and false otherwise
	 */
	private boolean isReadOperation(String anAction) {
		if (anAction.equals(ICMSConstant.ACTION_READ_LIMIT) || anAction.equals(ICMSConstant.ACTION_READ_LIMIT_ID)
				|| anAction.equals(ICMSConstant.ACTION_READ_LIMIT_BY_LPID)) {
			return true;
		}
		else {
			return false;
		}
	}
}