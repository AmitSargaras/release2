package com.integrosys.cms.app.commoncode.trx;

import com.integrosys.base.businfra.transaction.AbstractTrxController;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxOperationFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CommonCodeTypeReadController extends AbstractTrxController implements ITrxOperationFactory {
	/**
	 * Default Constructor
	 */
	public CommonCodeTypeReadController() {
	}

	/**
	 * Return instance name
	 * @return String - the instance name
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COMMON_CODE_TYPE_LIST;
	}

	/**
	 * This operate method invokes the operation for a read operation.
	 * 
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxResult - the trx result
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
		ITrxOperation op = getOperation(value, param);
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
			if (action.equals(ICMSConstant.ACTION_READ_COMMON_CODE_TYPE)) {
				return new ReadCommonCodeTypeOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + ".");
		}
		throw new TrxParameterException("Action is null!");
	}
}
