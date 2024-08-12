/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;

/**
 * This operation class is invoked by a checker to approve GMRA deal updated by
 * a maker.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class CheckerApproveUpdateGMRADealOperation extends AbstractGMRADealTrxOperation {
	/**
	 * Default constructor.
	 */
	public CheckerApproveUpdateGMRADealOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_GMRA_DEAL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update actual GMRA deal record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IGMRADealTrxValue trxValue = super.getGMRADealTrxValue(value);

			IGMRADeal actual = trxValue.getGMRADeal();
			if (actual == null) {
				trxValue = super.createActualGMRADeal(trxValue);
			}
			else {
				trxValue = super.updateActualGMRADeal(trxValue);
			}
			trxValue = super.updateTransaction(trxValue);

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}
}
