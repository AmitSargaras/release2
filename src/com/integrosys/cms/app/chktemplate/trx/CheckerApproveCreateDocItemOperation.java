/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerApproveCreateDocItemOperation.java,v 1.10 2003/07/23 07:32:14 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.cms.app.documentlocation.bus.OBDocumentAppTypeItem;

import java.rmi.RemoteException;
import java.util.Iterator;

/**
 * This operation allows a checker to approve a document item create
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.10 $
 * @since $Date: 2003/07/23 07:32:14 $ Tag: $Name: $
 */
public class CheckerApproveCreateDocItemOperation extends AbstractDocItemTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateDocItemOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_DOC_ITEM;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the staging
	 * with the actual data 3. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDocumentItemTrxValue trxValue = getDocumentItemTrxValue(anITrxValue);
		//trxValue = prepareChildValue(trxValue);
		trxValue = createActualDocItem(trxValue);
		trxValue = updateStagingDocumentItem(trxValue);
		trxValue = updateDocumentItemTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IDocumentItemTrxValue createActualDocItem(IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws TrxOperationException {
		try {
			IDocumentItem docItem = anIDocumentItemTrxValue.getStagingDocumentItem();
			IDocumentItem actualDocItem = getSBCheckListTemplateBusManager().create(docItem);			
			anIDocumentItemTrxValue.setDocumentItem(actualDocItem);
			anIDocumentItemTrxValue.setReferenceID(String.valueOf(actualDocItem.getItemID()));
			

			
			
			return anIDocumentItemTrxValue;
		}
		catch (CheckListTemplateException cex) {
			throw new TrxOperationException(cex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualDocumentItem(): " + ex.toString());
		}
	}

	/**
	 * Update the staging data due to the system generated document code.
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the trx value with the updated staging
	 *         document item
	 * @throws TrxOperationException on errors
	 */
	private IDocumentItemTrxValue updateStagingDocumentItem(IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws TrxOperationException {
		try {
			IDocumentItem staging = anIDocumentItemTrxValue.getStagingDocumentItem();
			IDocumentItem actual = anIDocumentItemTrxValue.getDocumentItem();
			IDocumentItem updStaging = (IDocumentItem) CommonUtil.deepClone(actual);
			updStaging.setItemID(staging.getItemID());
			updStaging.setVersionTime(staging.getVersionTime());
			staging = getSBStagingCheckListTemplateBusManager().update(updStaging);
			anIDocumentItemTrxValue.setStagingDocumentItem(updStaging);
			return anIDocumentItemTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (CheckListTemplateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateStagingDocumentItem(): " + ex.toString());
		}
	}
}