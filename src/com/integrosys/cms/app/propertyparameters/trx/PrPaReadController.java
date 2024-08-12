package com.integrosys.cms.app.propertyparameters.trx;

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
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 9:59:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrPaReadController extends AbstractTrxController implements ITrxOperationFactory {

	/**
	 * Default Constructor
	 */
	public PrPaReadController() {
	}

	/**
	 * Return instance name
	 * @return String - the instance name
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_AUTO_VAL_PARAM;
	}

	/**
	 * This operate method invokes the operation for a read operation.
	 * 
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxResult - the trx result
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException,
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
			throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
		}
	}

	/**
	 * Get the ITrxOperation
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws TrxParameterException on errors
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		String action = param.getAction();
		if (action != null) {
			if (action.equals(ICMSConstant.ACTION_READ_PRPA)) {
				return new ReadPrPaOperation();
			}

			if (action.equals(ICMSConstant.ACTION_READ_PRPA_ID)) {
				return new ReadPrPaIDOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + ".");
		}
		throw new TrxParameterException("Action is null!");
	}
}
