/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CreditRiskParamUpdateOperation
 *
 * Created on 5:28:29 PM
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
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 5:28:29 PM
 */
public class CreditRiskParamUpdateOperation extends AbstractCreditRiskParamOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_CREDIT_RISK_PARAM_MAKER_UPDATE;
	}

	public ITrxResult performProcess(ITrxValue iTrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "In performProcess");

		ICreditRiskParamGroupTrxValue trxValue = super.getCreditRiskParamGroupTrxValue(iTrxValue);

		DefaultLogger.debug(this, "trxValue.getReferenceID () : " + trxValue.getReferenceID());

		trxValue = createStagingData(trxValue);

		DefaultLogger.debug(this, "trxValue.getReferenceID () after staging : " + trxValue.getReferenceID());

		trxValue = updateTransactionData(trxValue);

		return super.prepareResult(trxValue);
	}
}
