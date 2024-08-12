/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.trx.marketfactor;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;

/**
 * This operation class is invoked by a checker to approve MF Template updated
 * by a maker.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class CheckerApproveUpdateMFTemplateOperation extends AbstractMFTemplateTrxOperation {
	/**
	 * Default constructor.
	 */
	public CheckerApproveUpdateMFTemplateOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_MF_TEMPLATE;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update actual MF Template record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IMFTemplateTrxValue trxValue = super.getMFTemplateTrxValue(value);

			IMFTemplate actual = trxValue.getMFTemplate();
			if (actual == null) {
				trxValue = super.createActualMFTemplate(trxValue);
			}
			else {
				trxValue = super.updateActualMFTemplate(trxValue);
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
