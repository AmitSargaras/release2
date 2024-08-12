/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/DDNTrxControllerFactory.java,v 1.2 2005/06/08 06:33:54 htli Exp $
 */
package com.integrosys.cms.app.ddn.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create ddn related controllers.
 * 
 * @author $Author: htli $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/06/08 06:33:54 $ Tag: $Name: $
 */
public class DDNTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public DDNTrxControllerFactory() {
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
			return new DDNReadController();
		}
		return new DDNTrxController();
	}

	/**
	 * To check if the action requires a read operation or not
	 * @param anAction - String
	 * @return boolean - true if requires a read operation and false otherwise
	 */
	private boolean isReadOperation(String anAction) {
		if (anAction.equals(ICMSConstant.ACTION_READ_DDN) || anAction.equals(ICMSConstant.ACTION_READ_DDN_ID)
				|| anAction.equals(ICMSConstant.ACTION_READ_PREVIOUS_DDN)) {
			return true;
		}
		return false;
	}
}