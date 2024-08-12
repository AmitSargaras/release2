/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * MakerCloseEarMarkOperation
 *
 * Created on 10:41:50 AM
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
 * Created by IntelliJ IDEA. User: Eric Date: Mar 27, 2007 Time: 10:41:50 AM
 */
public class MakerCloseEarMarkOperation extends AbstractPreDealTrxOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_EAR_MARK;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		super.performProcess(anITrxValue);

		IPreDealTrxValue trxValue = super.getPreDealTrxValue(anITrxValue);

		trxValue = updatePreDealTransaction(trxValue);

		return super.prepareResult(trxValue);
	}

}