/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CreditRiskParamUnitTrustReadController
 *
 * Created on 2:12:00 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.trx.unittrust;

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
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 2:12:00 PM
 */
public class CreditRiskParamUnitTrustReadController extends AbstractTrxController implements ITrxOperationFactory {

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_UNIT_TRUST;
	}

	public ITrxResult operate(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException,
			TrxControllerException, TransactionException {
		if (null == iTrxValue) {
			throw new TrxParameterException("ITrxValue is null!");
		}
		if (null == iTrxParameter) {
			throw new TrxParameterException("ITrxParameter is null!");
		}

		iTrxValue = setInstanceName(iTrxValue);

		DefaultLogger.debug(this, "Instance Name: " + iTrxValue.getInstanceName());

		ITrxOperation op = getOperation(iTrxValue, iTrxParameter);

		DefaultLogger.debug(this, "From state " + iTrxValue.getFromState());

		CMSReadTrxManager mgr = new CMSReadTrxManager();

		ITrxResult result = null;

		try {
			result = mgr.operateTransaction(op, iTrxValue);
			return result;
		}
		catch (TransactionException te) {
			throw te;
		}
		catch (Exception re) {
			throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
		}
	}

	public ITrxOperation getOperation(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException {
		if (iTrxParameter == null) {
			throw new TrxParameterException("ITrxParameter is null!");
		}

		String action = iTrxParameter.getAction();

		if (ICMSConstant.ACTION_CREDIT_RISK_PARAM_READ.equals(action)) {
			return new ReadCreditRiskParamUnitTrustOperation();
		}
		else if (ICMSConstant.ACTION_CREDIT_RISK_PARAM_READBY_TRXID.equals(action)) {
			return new ReadCreditRiskParamUnitTrustByTrxIdOperation();
		}

		throw new TrxParameterException("Unknow Action : " + action);
	}
}
