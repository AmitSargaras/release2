/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CreditRiskParamUpdateRejectedOperation
 *
 * Created on 5:36:29 PM
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
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 5:36:29 PM
 */
public class CreditRiskParamUpdateRejectedOperation extends AbstractCreditRiskParamOperation {
	public String getOperationName() {
		return ICMSConstant.ACTION_CREDIT_RISK_PARAM_MAKER_UPDATE_REJECTED;
	}

	public ITrxResult performProcess(ITrxValue iTrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "In performProcess");

		ICreditRiskParamGroupTrxValue trxValue = getCreditRiskParamGroupTrxValue(iTrxValue);

		DefaultLogger.debug(this, "trxValue.getReferenceID () : " + trxValue.getReferenceID());

		trxValue = createStagingData(trxValue);
		trxValue = updateTransactionData(trxValue);

		return prepareResult(trxValue);
	}

}
