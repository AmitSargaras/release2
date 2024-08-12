/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CollateralSubTypeReadController.java,v 1.5 2003/08/15 07:14:07 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

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
 * This transaction controller is to be used for reading security subtypes.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/15 07:14:07 $ Tag: $Name: $
 */
public class CollateralSubTypeReadController extends AbstractTrxController implements ITrxOperationFactory {

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
	 * Default constructor.
	 */
	public CollateralSubTypeReadController() {
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table.
	 * 
	 * @return instance of security parameter
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COL_SUBTYPE;
	}

	/**
	 * This operate method invokes the operation for a read operation.
	 * 
	 * @param trxValue is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return transaction result
	 * @throws TrxParameterException if the transaction value and param is
	 *         invalid
	 * @throws TransactionException on error operating the transaction
	 * @throws TrxControllerException on any other errors encountered
	 */
	public ITrxResult operate(ITrxValue trxValue, ITrxParameter param) throws TrxParameterException,
			TrxControllerException, TransactionException {
		Validate.notNull(trxValue, "transaction value must not be null.");
		Validate.notNull(param, "param must not be null.");

		trxValue = setInstanceName(trxValue);
		DefaultLogger.debug(this, "Instance Name: " + trxValue.getInstanceName());
		ITrxOperation op = getOperation(trxValue, param);
		CMSReadTrxManager mgr = new CMSReadTrxManager();

		try {
			ITrxResult result = mgr.operateTransaction(op, trxValue);
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
	 * @param trxVal is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return transaction operation
	 * @throws TrxParameterException if the transaction parameter is invalid
	 */
	public ITrxOperation getOperation(ITrxValue trxVal, ITrxParameter param) throws TrxParameterException {
		Validate.notNull(param, "param must not be null.");
		String action = param.getAction();

		DefaultLogger.debug(this, "Action is " + action);

		Validate.notNull(action, "action must not be null.");

		if (action.equals(ICMSConstant.ACTION_READ_SUBTYPE_BY_TYPECODE)) {
			return getTrxOperation("ReadCollateralSubTypeByTypeCodeOperation");
		}
		else if (action.equals(ICMSConstant.ACTION_READ_SUBTYPE_BY_TRXID)) {
			return getTrxOperation("ReadCollateralSubTypeByTrxIDOperation");
		}
		else {
			throw new TrxParameterException("Unknow Action: " + action + ".");
		}
	}
}
