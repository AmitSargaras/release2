/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerCloseRejectedUpdateDocItemOperation.java,v 1.6 2003/09/22 11:56:23 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.AbstractDocItemTrxOperation;

/**
 * This operation allows a maker to close a rejected doc item transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/09/22 11:56:23 $ Tag: $Name: $
 */
public class MakerCloseRejectedDeleteDocItemOperation extends AbstractDocItemTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedDeleteDocItemOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DELETE_DOC_ITEM;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDocumentItemTrxValue trxValue = getDocumentItemTrxValue(anITrxValue);
		// trxValue.setStagingDocumentItem(trxValue.getDocumentItem());
		// trxValue = createStagingDocItem(trxValue);
		trxValue = updateDocumentItemTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}