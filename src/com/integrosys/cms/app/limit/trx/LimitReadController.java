/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/LimitReadController.java,v 1.3 2005/09/22 02:03:08 whuang Exp $
 */
package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.businfra.transaction.AbstractTrxController;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxOperationFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * This controller controls limit related read-operations.
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/22 02:03:08 $ Tag: $Name: $
 */
public class LimitReadController extends AbstractTrxController implements ITrxOperationFactory {
	/**
	 * Default Constructor
	 */
	public LimitReadController() {
	}

	/**
	 * Return instance name
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_LIMIT;
	}

	/**
	 * This operate method invokes the operation for a read operation.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return ITrxResult
	 * @throws TrxParameterException
	 * @throws TrxControllerException
	 * @throws TransactionException on errors
	 */
	public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TrxParameterException,
			TrxControllerException, TransactionException {
		if (null == value) {
			throw new TrxParameterException("ITrxValue is null!");
		}
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		value = setInstanceName(value);
		DefaultLogger.debug(this, "Instance Name: " + value.getInstanceName());
		ITrxOperation op = getOperation(value, param);
		DefaultLogger.debug(this, "From state " + value.getFromState());
		CMSReadTrxManager mgr = new CMSReadTrxManager();

		ITrxResult result = null;
		try {
			result = mgr.operateTransaction(op, value);
			return result;
		}
		catch (TransactionException te) {
			throw te;
		}
		catch (Exception re) {
			throw new TrxControllerException("Caught Unknown Exception!", re);
		}
	}

	/**
	 * Get the ITrxOperation
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return ITrxOperation
	 * @throws TrxParameterException on errors
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		String action = param.getAction();

		if (action != null) {
			if (action.equals(ICMSConstant.ACTION_READ_LIMIT)) {
				return new ReadLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_READ_LIMIT_ID)) {
				return new ReadLimitIDOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_READ_LIMIT_BY_LPID)) {
				return new ReadLimitByProfileIDOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + ".");
			}
		}
		else {
			throw new TrxParameterException("Action is null!");
		}
	}

}
