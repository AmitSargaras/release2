/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerCreateDocItemOperation.java,v 1.4 2003/07/23 07:32:14 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.OBDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.AbstractDocItemTrxOperation;

/**
 * This operation creates a pending document item
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/23 07:32:14 $ Tag: $Name: $
 */
public class MakerCreateDocItemOperation extends AbstractDocItemTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateDocItemOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_DOC_ITEM;
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDocumentItemTrxValue trxValue = getDocumentItemTrxValue(anITrxValue);
		trxValue = prepareChildValue(trxValue);
		trxValue = createStagingDocItem(trxValue);
		trxValue = createDocItemTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a document item transaction
	 * @param anITrxValue - ITrxValue
	 * @return OBDocumentItemTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private IDocumentItemTrxValue createDocItemTransaction(IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws TrxOperationException {
		try {
			anIDocumentItemTrxValue = prepareTrxValue(anIDocumentItemTrxValue);
			ICMSTrxValue trxValue = createTransaction(anIDocumentItemTrxValue);
			OBDocumentItemTrxValue docItemTrxValue = new OBDocumentItemTrxValue(trxValue);
			docItemTrxValue.setStagingDocumentItem(anIDocumentItemTrxValue.getStagingDocumentItem());
			docItemTrxValue.setDocumentItem(anIDocumentItemTrxValue.getDocumentItem());
			return docItemTrxValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

}