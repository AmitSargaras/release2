/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/CommodityDealReadController.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

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
 * This trx controller is to be used in reading Commodity Deal.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public class CommodityDealReadController extends AbstractTrxController implements ITrxOperationFactory {
	/**
	 * Default constructor.
	 */
	public CommodityDealReadController() {
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table.
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COMMODITY_DEAL;
	}

	/**
	 * This operate method invokes the operation for a read operation.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return transaction result
	 * @throws TrxParameterException if the transaction value and param is
	 *         invalid
	 * @throws TransactionException on error operating the transaction
	 * @throws TrxControllerException on any other errors encountered
	 */
	public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TrxParameterException,
			TrxControllerException, TransactionException {
		if (value == null) {
			throw new TrxParameterException("ITrxValue is null!");
		}
		if (param == null) {
			throw new TrxParameterException("ITrxParameter is null!");
		}

		value = setInstanceName(value);
		DefaultLogger.debug(this, "Instance Name: " + value.getInstanceName());
		ITrxOperation op = getOperation(value, param);
		CMSReadTrxManager mgr = new CMSReadTrxManager();

		try {
			ITrxResult result = mgr.operateTransaction(op, value);
			return result;
		}
		catch (TransactionException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxControllerException("Caught Unknown Exception: " + e.toString());
		}
	}

	/**
	 * Get operation for the transaction given the value and param.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return transaction operation
	 * @throws TrxParameterException if the transaction parameter is invalid
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (param == null) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		String action = param.getAction();

		DefaultLogger.debug(this, "Action is " + param.getAction());

		if (action != null) {
			if (action.equals(ICMSConstant.ACTION_READ_DEAL_BY_TRXID)) {
				return new ReadCommodityDealByTrxIDOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_READ_DEAL_BY_DEALID)) {
				return new ReadCommodityDealByDealIDOperation();
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
