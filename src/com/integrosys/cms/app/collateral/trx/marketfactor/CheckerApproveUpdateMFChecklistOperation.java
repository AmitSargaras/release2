/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.marketfactor;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by a checker to approve MF Checklist updated
 * by a maker.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class CheckerApproveUpdateMFChecklistOperation extends AbstractMFChecklistTrxOperation {
	/**
	 * Default constructor.
	 */
	public CheckerApproveUpdateMFChecklistOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_MF_CHECKLIST;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update actual MF Checklist record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IMFChecklistTrxValue trxValue = super.getMFChecklistTrxValue(value);

			IMFChecklist actual = trxValue.getMFChecklist();
			if (actual == null) {
				trxValue = super.createActualMFChecklist(trxValue);
			}
			else {
				trxValue = super.updateActualMFChecklist(trxValue);
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
