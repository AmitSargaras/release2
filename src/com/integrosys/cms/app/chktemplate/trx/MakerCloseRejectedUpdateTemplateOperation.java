/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerCloseRejectedUpdateTemplateOperation.java,v 1.4 2003/09/22 11:56:23 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.chktemplate.trx.AbstractTemplateTrxOperation;

/**
 * This operation allows a maker to close a rejected template transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/22 11:56:23 $ Tag: $Name: $
 */
public class MakerCloseRejectedUpdateTemplateOperation extends AbstractTemplateTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedUpdateTemplateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_TEMPLATE;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITemplateTrxValue trxValue = getTemplateTrxValue(anITrxValue);
		// trxValue.setStagingTemplate(trxValue.getTemplate());
		// trxValue = createStagingTemplate(trxValue);
		trxValue = updateTemplateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}