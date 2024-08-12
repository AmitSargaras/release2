/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.trx.marketfactor;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to update a MF Template.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MakerUpdateMFTemplateOperation extends AbstractMFTemplateTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerUpdateMFTemplateOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_MF_TEMPLATE;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging MF Template record 2. update transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IMFTemplateTrxValue trxValue = super.getMFTemplateTrxValue(value);

			trxValue = super.createStagingMFTemplate(trxValue);

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
