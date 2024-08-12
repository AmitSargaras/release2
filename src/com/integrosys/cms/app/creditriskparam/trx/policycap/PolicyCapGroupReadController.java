/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.policycap;

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
 * Read Controller of PolicyCapGroup
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 30/Aug/2007 $ Tag: $Name: $
 */
public class PolicyCapGroupReadController extends AbstractTrxController implements ITrxOperationFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.integrosys.base.businfra.transaction.AbstractTrxController#
	 * getInstanceName()
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_POLICY_CAP_GROUP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.AbstractTrxController#operate
	 * (com.integrosys.base.businfra.transaction.ITrxValue,
	 * com.integrosys.base.businfra.transaction.ITrxParameter)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperationFactory#getOperation
	 * (com.integrosys.base.businfra.transaction.ITrxValue,
	 * com.integrosys.base.businfra.transaction.ITrxParameter)
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		String action = param.getAction();
		if (action != null) {
			if (action.equals(ICMSConstant.ACTION_READ_POLICY_CAP_GROUP)) {
				return new ReadPolicyCapGroupOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_READ_POLICY_CAP_GROUP_ID)) {
				return new ReadPolicyCapGroupTrxIDOperation();
			}

			throw new TrxParameterException("Unknow Action: " + action + ".");
		}
		throw new TrxParameterException("Action is null!");
	}

}
