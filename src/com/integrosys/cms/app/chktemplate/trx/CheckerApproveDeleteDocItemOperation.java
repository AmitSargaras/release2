/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerApproveUpdateDocItemOperation.java,v 1.9 2003/07/23 07:32:14 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import com.integrosys.cms.app.chktemplate.trx.AbstractDocItemTrxOperation;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;

/**
 * This operation allows a checker to approve the doc item updating
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.9 $
 * @since $Date: 2003/07/23 07:32:14 $ Tag: $Name: $
 */
public class CheckerApproveDeleteDocItemOperation extends AbstractDocItemTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveDeleteDocItemOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_DOC_ITEM;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDocumentItemTrxValue trxValue = getDocumentItemTrxValue(anITrxValue);
		trxValue = prepareChildValue(trxValue);
		trxValue = updateActualDocumentItem(trxValue);
		trxValue = super.updateDocumentItemTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual document item from the staging document item
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IDocumentItemTrxValue updateActualDocumentItem(IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws TrxOperationException {
		try {
			IDocumentItem staging = anIDocumentItemTrxValue.getStagingDocumentItem();
			IDocumentItem actual = anIDocumentItemTrxValue.getDocumentItem();
			IDocumentItem updActual = (IDocumentItem) CommonUtil.deepClone(staging);
			updActual.setItemID(actual.getItemID());
			updActual.setVersionTime(actual.getVersionTime());
			IDocumentItem actualDocItem = getSBCheckListTemplateBusManager().update(updActual);
			anIDocumentItemTrxValue.setDocumentItem(updActual);
			return anIDocumentItemTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (CheckListTemplateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualDocumentItem(): " + ex.toString());
		}
	}
}