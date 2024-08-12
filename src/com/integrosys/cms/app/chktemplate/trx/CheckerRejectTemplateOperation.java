/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerRejectTemplateOperation.java,v 1.3 2003/07/23 07:32:14 hltan Exp $
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
 * This operation allows a checker to reject a template transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/23 07:32:14 $ Tag: $Name: $
 */
public class CheckerRejectTemplateOperation extends AbstractTemplateTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectTemplateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_TEMPLATE;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITemplateTrxValue trxValue = super.getTemplateTrxValue(anITrxValue);
		trxValue = super.updateTemplateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}