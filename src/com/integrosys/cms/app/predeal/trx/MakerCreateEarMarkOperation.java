/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * MakerCreateEarMarkOperation
 *
 * Created on 3:52:10 PM
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
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 26, 2007 Time: 3:52:10 PM
 */
public class MakerCreateEarMarkOperation extends AbstractPreDealTrxOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_EAR_MARK;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		super.performProcess(anITrxValue);

		IPreDealTrxValue trxValue = super.getPreDealTrxValue(anITrxValue);

		trxValue = createStagingPreDeal(trxValue);
		trxValue = createPreDealTransaction(trxValue);

		return super.prepareResult(trxValue);
	}

	private IPreDealTrxValue createPreDealTransaction(IPreDealTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue trxValue = createTransaction(value);

			OBPreDealTrxValue preDealTrxValue = new OBPreDealTrxValue(trxValue);

			preDealTrxValue.setStagingPreDeal(value.getStagingPreDeal());
			preDealTrxValue.setPreDeal(value.getPreDeal());

			return preDealTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}

}