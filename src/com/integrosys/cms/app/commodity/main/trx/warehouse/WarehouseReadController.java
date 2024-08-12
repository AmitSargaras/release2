/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/WarehouseReadController.java,v 1.6 2005/04/27 08:17:05 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

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
 * This controller controls document item related read-operations.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2005/04/27 08:17:05 $ Tag: $Name: $
 */
public class WarehouseReadController extends AbstractTrxController implements ITrxOperationFactory {
	/**
	 * Default Constructor
	 */
	public WarehouseReadController() {
	}

	/**
	 * Return instance name
	 * @return String - the instance name
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COMMODITY_MAIN_WAREHOUSE;
	}

	/**
	 * This operate method invokes the operation for a read operation.
	 * 
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxResult - the trx result
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException ,
	 *         TrxControllerException, TransactionException on errors
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
			re.printStackTrace();
			throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
		}
	}

	/**
	 * Get the ITrxOperation
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException on
	 *         errors
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		String action = param.getAction();
		if (action != null) {
			if (action.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID)) {
				return new ReadWarehouseTrxIDOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_ID)) {
				return new ReadWarehouseIDOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_COUNTRY)) {
				return new ReadWarehouseCountryOperation();
			}

			// // More read operations go here
			throw new TrxParameterException("Unknow Action:: " + action + ".");
		}
		throw new TrxParameterException("Action is null!");
	}
}
