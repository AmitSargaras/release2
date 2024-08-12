/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/CoBorrowerLimitTrxControllerFactory.java,v 1.2 2005/09/22 02:03:08 whuang Exp $
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
 * @author $Author: whuang $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/22 02:03:08 $ Tag: $Name: $
 */
public class CoBorrowerLimitTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public CoBorrowerLimitTrxControllerFactory() {
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
			return new CoBorrowerLimitReadController();
		}
		else {
			return new CoBorrowerLimitTrxController();
		}
	}

	/**
	 * To check if the action requires a read operation or not
	 * @param anAction - String
	 * @return boolean - true if requires a read operation and false otherwise
	 */
	private boolean isReadOperation(String anAction) {
		if (anAction.equals(ICMSConstant.ACTION_READ_CO_LIMIT) || anAction.equals(ICMSConstant.ACTION_READ_CO_LIMIT_ID)
				|| anAction.equals(ICMSConstant.ACTION_READ_CO_BORROWER_LIMIT_BY_LPID)) {
			return true;
		}
		else {
			return false;
		}
	}
}