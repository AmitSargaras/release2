/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CheckerRejectEarMarkOperation
 *
 * Created on 10:20:39 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 27, 2007 Time: 10:20:39 AM
 */
public class CheckerRejectEarMarkOperation extends AbstractPreDealTrxOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_EAR_MARK;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		super.performProcess(anITrxValue);

		IPreDealTrxValue trxValue = super.getPreDealTrxValue(anITrxValue);

		trxValue = updatePreDealTransaction(trxValue);

		return super.prepareResult(trxValue);
	}

}