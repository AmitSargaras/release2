/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/RMApproveCreateDeferralRequestOperation.java,v 1.2 2003/09/19 09:31:07 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.trx;

//java
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generatereq.bus.GenerateRequestException;
import com.integrosys.cms.app.generatereq.bus.IDeferralRequest;
import com.integrosys.cms.app.generatereq.bus.IDeferralRequestItem;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation allows a RM to approve a Deferral request create
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/19 09:31:07 $ Tag: $Name: $
 */
public class RMApproveCreateDeferralRequestOperation extends AbstractDeferralRequestTrxOperation {
	/**
	 * Default Constructor
	 */
	public RMApproveCreateDeferralRequestOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_RM_APPROVE_GENERATE_DEFERRAL_REQ;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent transaction ID to be appended as trx parent ref
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		IDeferralRequestTrxValue trxValue = getDeferralRequestTrxValue(anITrxValue);
		IDeferralRequest staging = trxValue.getStagingDeferralRequest();
		try {
			if (staging != null) {
				if (staging.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(
							String.valueOf(staging.getLimitProfileID()), ICMSConstant.INSTANCE_LIMIT_PROFILE);
					trxValue.setTrxReferenceID(parentTrx.getTransactionID());
				}
				fireCheckListTrx(staging);
			}
			return trxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in preProcess: " + ex.toString());
		}
	}

	private void fireCheckListTrx(IDeferralRequest anIDeferralRequest) throws TrxOperationException {
		IDeferralRequestItem[] itemList = anIDeferralRequest.getDeferralRequestItemList();
		ArrayList checklist = new ArrayList();
		if ((itemList == null) || (itemList.length == 0)) {
			return;
		}

		try {
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListTrxValue checkListTrxValue = null;
			for (int ii = 0; ii < itemList.length; ii++) {
				if (!checklist.contains(new Long(itemList[ii].getCheckListID()))) {
					checklist.add(new Long(itemList[ii].getCheckListID()));
					checkListTrxValue = proxy.getCheckList(itemList[ii].getCheckListID());
					proxy.systemApproveGenerateCheckListDeferral(checkListTrxValue);
				}
			}
		}
		catch (CheckListException ex) {
			throw new TrxOperationException("CheckListException in fireCheckListTrx", ex);
		}
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDeferralRequestTrxValue trxValue = getDeferralRequestTrxValue(anITrxValue);
		trxValue = createActualDeferralRequest(trxValue);
		trxValue = updateDeferralRequestTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual Deferral request
	 * @param anITrxValue of ITrxValue type
	 * @return IDeferralRequestTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IDeferralRequestTrxValue createActualDeferralRequest(IDeferralRequestTrxValue anIDeferralRequestTrxValue)
			throws TrxOperationException {
		try {
			IDeferralRequest deferralReq = anIDeferralRequestTrxValue.getStagingDeferralRequest();
			IDeferralRequest actualDeferralReq = getSBGenerateRequestBusManager().createRequest(deferralReq);
			anIDeferralRequestTrxValue.setDeferralRequest(actualDeferralReq);
			anIDeferralRequestTrxValue.setReferenceID(String.valueOf(actualDeferralReq.getRequestID()));
			return anIDeferralRequestTrxValue;
		}
		catch (GenerateRequestException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualDeferralRequest(): " + ex.toString());
		}
	}
}