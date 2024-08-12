/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * MakerUpdateRejectEarMarkOperation
 *
 * Created on 4:23:57 PM
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
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Apr 2, 2007 Time: 4:23:57 PM
 */
public class MakerUpdateRejectEarMarkOperation extends AbstractPreDealTrxOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_EAR_MARK;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		super.performProcess(anITrxValue);

		DefaultLogger.debug(this, "In performProcess");

		IPreDealTrxValue trxValue = super.getPreDealTrxValue(anITrxValue);

		DefaultLogger.debug(this, "trxValue.getReferenceID () : " + trxValue.getReferenceID());

		trxValue = updateStagingPreDeal(trxValue);

		DefaultLogger.debug(this, "trxValue.getReferenceID () after staging : " + trxValue.getReferenceID());

		trxValue = updatePreDealTransaction(trxValue);

		return super.prepareResult(trxValue);
	}

}