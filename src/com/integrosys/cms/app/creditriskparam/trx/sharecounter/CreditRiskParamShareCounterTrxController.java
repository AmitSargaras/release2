/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CreditRiskParamShareCounterTrxController
 *
 * Created on 2:02:30 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.trx.sharecounter;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.trx.CreditRiskParamApproveOperation;
import com.integrosys.cms.app.creditriskparam.trx.CreditRiskParamCloseOperation;
import com.integrosys.cms.app.creditriskparam.trx.CreditRiskParamRejectOperation;
import com.integrosys.cms.app.creditriskparam.trx.CreditRiskParamUpdateOperation;
import com.integrosys.cms.app.creditriskparam.trx.CreditRiskParamUpdateRejectedOperation;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 2:02:30 PM
 */
public class CreditRiskParamShareCounterTrxController extends CMSTrxController {
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (null == value) {
			throw new TrxParameterException("ITrxValue is null!");
		}

		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}

		String action = param.getAction();
		String toState = value.getToState(); // actually the current state !!!!!
		String fromState = value.getFromState();

		DefaultLogger.debug(this, "Action: " + action);
		DefaultLogger.debug(this, "toState: " + toState);
		DefaultLogger.debug(this, "fromState: " + fromState);

		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}

		// checker's operations
		if (action.equals(ICMSConstant.ACTION_CREDIT_RISK_PARAM_CHECKER_APPROVE)
				&& ICMSConstant.STATE_PENDING_UPDATE.equals(toState)) {
			return new CreditRiskParamApproveOperation();
		}
		else if (action.equals(ICMSConstant.ACTION_CREDIT_RISK_PARAM_CHECKER_REJECT)
				&& ICMSConstant.STATE_PENDING_UPDATE.equals(toState)) {
			return new CreditRiskParamRejectOperation();
		}
		// maker's operation
		else if (action.equals(ICMSConstant.ACTION_CREDIT_RISK_PARAM_MAKER_UPDATE_REJECTED)
				&& ICMSConstant.STATE_REJECTED.equals(toState)) {
			return new CreditRiskParamUpdateRejectedOperation();
		}
		else if (action.equals(ICMSConstant.ACTION_CREDIT_RISK_PARAM_MAKER_UPDATE)
				&& !ICMSConstant.STATE_PENDING_UPDATE.equals(toState)) {
			return new CreditRiskParamUpdateOperation();
		}
		else if (action.equals(ICMSConstant.ACTION_CREDIT_RISK_PARAM_MAKER_CLOSE)
				&& ICMSConstant.STATE_REJECTED.equals(toState)) {
			return new CreditRiskParamCloseOperation();
		}

		throw new TrxParameterException("Unknown operation requirement by arguements passed in !");
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_SHARE_COUNTER;
	}

}
