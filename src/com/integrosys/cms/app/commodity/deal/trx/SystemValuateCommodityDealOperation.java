/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/SystemValuateCommodityDealOperation.java,v 1.1 2004/06/17 02:34:08 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation class is invoked by system to valuate commodity deal.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/17 02:34:08 $ Tag: $Name: $
 */
public class SystemValuateCommodityDealOperation extends AbstractCommodityDealTrxOperation {
	/**
	 * Default constructor.
	 */
	public SystemValuateCommodityDealOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_VALUATE_DEAL;
	}

	/**
	 * This method defines the process that should initialised values that would
	 * be required in the <code>performProcess</code> method
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue object that has been initialised with required values
	 * @throws TrxOperationException on errors
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		// this preProcess is to maintain fromState so that it will not be
		// overwritten by the
		// current status which is rejected. i.e. we don't want to have a
		// scenario where we have
		// from state: rejected, to state: rejected too.
		try {
			String status = value.getStatus();
			if (status.equals(ICMSConstant.STATE_REJECTED)) {
				String fromState = value.getFromState();
				DefaultLogger.debug(this, "From State: " + fromState);
				// then do super.preProcess
				value = super.preProcess(value);
				// now set back the from state to overwrite what was created in
				// preProcess
				ICMSTrxValue trxValue = getCMSTrxValue(value);
				trxValue.setFromState(fromState);
				return trxValue;
			}
			else {
				return super.preProcess(value);
			}
		}
		catch (TransactionException e) {
			throw new TrxOperationException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TrxOperationException("Caught Unknown Exception!", e);
		}
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICommodityDealTrxValue trxValue = super.getCommodityDealTrxValue(value);

			trxValue = super.updateTransaction(trxValue);

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}
