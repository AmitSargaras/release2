/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CheckerApproveEarMarkOperation
 *
 * Created on 10:36:58 AM
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
import com.integrosys.cms.app.predeal.bus.IPreDeal;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 27, 2007 Time: 10:36:58 AM
 */
public class CheckerApproveEarMarkOperation extends AbstractPreDealTrxOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_EAR_MARK;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		super.performProcess(anITrxValue);

		IPreDealTrxValue trxValue = super.getPreDealTrxValue(anITrxValue);

		DefaultLogger.debug(this, "trxValue.getToState () : " + trxValue.getToState());
		DefaultLogger.debug(this, "trxValue.getPreviousState () : " + trxValue.getPreviousState());

		if (ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getPreviousState())) {
			trxValue = createActualPreDeal(trxValue);
		}
		else {
			trxValue = updateActualPreDeal(trxValue);
		}

		trxValue = updatePreDealTransaction(trxValue);

		return super.prepareResult(trxValue);
	}

	private IPreDealTrxValue createActualPreDeal(IPreDealTrxValue trxValue) throws TrxOperationException {
		IPreDeal staging = trxValue.getStagingPreDeal();

		try {
			staging = getSBPreDealBusManager().createNewEarMark(staging);

			trxValue.setPreDeal(staging);
			trxValue.setReferenceID(String.valueOf(staging.getEarMarkId()));
		}
		catch (Exception e) {
			// e.printStackTrace ();

			throw new TrxOperationException(e);
		}

		return trxValue;
	}

}
