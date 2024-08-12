/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CreditRiskParamRejectOperation
 *
 * Created on 5:33:54 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 5:33:54 PM
 */
public class CreditRiskParamRejectOperation extends AbstractCreditRiskParamOperation {
	public String getOperationName() {
		return ICMSConstant.ACTION_CREDIT_RISK_PARAM_CHECKER_REJECT;
	}

	public ITrxResult performProcess(ITrxValue iTrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "In performProcess");

		ICreditRiskParamGroupTrxValue trxValue = super.getCreditRiskParamGroupTrxValue(iTrxValue);

		trxValue = updateTransactionData(trxValue);

		return super.prepareResult(trxValue);
	}
}
