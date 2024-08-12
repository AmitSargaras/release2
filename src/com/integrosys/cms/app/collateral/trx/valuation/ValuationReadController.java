/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ValuationReadController.java,v 1.2 2003/07/16 07:08:15 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.valuation;

import java.util.Map;

import org.apache.commons.lang.Validate;

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
 * This controller controls valuation related read-operations.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/16 07:08:15 $ Tag: $Name: $
 */
public class ValuationReadController extends AbstractTrxController implements ITrxOperationFactory {

	private Map nameTrxOperationMap;

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	protected ITrxOperation getTrxOperation(String name) throws TrxParameterException {
		ITrxOperation op = (ITrxOperation) getNameTrxOperationMap().get(name);

		if (op == null) {
			throw new TrxParameterException("trx operation retrieved is null for given name [" + name + "]");
		}

		return op;
	}

	/**
	 * Default Constructor
	 */
	public ValuationReadController() {
	}

	/**
	 * Return instance name
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_VALUATION;
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
		Validate.notNull(value, "transaction value must not be null.");
		Validate.notNull(param, "param must not be null.");

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
		Validate.notNull(param, "param must not be null.");

		String action = param.getAction();

		DefaultLogger.debug(this, "Action is " + param.getAction());

		if (action != null) {
			if (action.equals(ICMSConstant.ACTION_READ_VAL_BY_VALID)) {
				return getTrxOperation("ReadValuationByValIDOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_READ_VAL_BY_TRXREFID)) {
				return getTrxOperation("ReadValuationByTrxRefIDOperation");
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
