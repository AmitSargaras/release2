/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CollateralReadController.java,v 1.4 2003/06/30 09:21:09 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import java.util.Map;

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
 * This trx controller is to be used in reading Collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/06/30 09:21:09 $ Tag: $Name: $
 */
public class CollateralReadController extends AbstractTrxController implements ITrxOperationFactory {

	private static final long serialVersionUID = 8930839680914003049L;

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
	public CollateralReadController() {
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table.
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COLLATERAL;
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
		return mgr.operateTransaction(op, value);
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
			if (action.equals(ICMSConstant.ACTION_READ_COL_BY_TRXID)) {
				return getTrxOperation("ReadCollateralByTrxIDOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_READ_COL_BY_COLID)) {
				return getTrxOperation("ReadCollateralByColIDOperation");
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
