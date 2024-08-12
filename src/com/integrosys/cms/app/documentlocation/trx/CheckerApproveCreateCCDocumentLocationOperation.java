/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/trx/CheckerApproveCreateCCDocumentLocationOperation.java,v 1.2 2004/04/08 12:52:45 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.documentlocation.bus.DocumentLocationException;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;

/**
 * This operation allows a checker to approve a CC document location create
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/04/08 12:52:45 $ Tag: $Name: $
 */
public class CheckerApproveCreateCCDocumentLocationOperation extends AbstractCCDocumentLocationTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateCCDocumentLocationOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CC_DOC_LOC;
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
		ICCDocumentLocationTrxValue trxValue = getCCDocumentLocationTrxValue(anITrxValue);
		trxValue = createActualCCDocumentLocation(trxValue);
		trxValue = updateCCDocumentLocationTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To reset the cc collaboration
	 * task trx status
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		result = super.postProcess(result);

		ICCDocumentLocationTrxValue trxValue = (ICCDocumentLocationTrxValue) result.getTrxValue();
		ICCDocumentLocation actual = trxValue.getCCDocumentLocation();
		try {
			ICollaborationTaskProxyManager mgr = CollaborationTaskProxyManagerFactory.getProxyManager();
			mgr.systemUpdateCCCollaborationTask(actual.getLimitProfileID(), actual.getDocLocationCategory(), actual
					.getCustomerID());
		}
		catch (CollaborationTaskException ex) {
			throw new TrxOperationException("Caught Exception in postProcess", ex);
		}
		return result;
	}

	/**
	 * Create the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return ICCDocumentLocationTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ICCDocumentLocationTrxValue createActualCCDocumentLocation(
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws TrxOperationException {
		try {
			ICCDocumentLocation colDocumentLocation = anICCDocumentLocationTrxValue.getStagingCCDocumentLocation();
			ICCDocumentLocation actualColDocumentLocation = getSBDocumentLocationBusManager().createCCDocumentLocation(
					colDocumentLocation);
			anICCDocumentLocationTrxValue.setCCDocumentLocation(actualColDocumentLocation);
			anICCDocumentLocationTrxValue.setReferenceID(String.valueOf(actualColDocumentLocation.getDocLocationID()));
			return anICCDocumentLocationTrxValue;
		}
		catch (DocumentLocationException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualCCDocumentLocation(): " + ex.toString());
		}
	}

	/**
	 * To get the remote handler for the checklist session bean
	 * @return SBCheckListBusManager - the remote handler for the checklist
	 *         session bean
	 */
	protected SBCheckListBusManager getSBCheckListBusManager() {
		SBCheckListBusManager remote = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		return remote;
	}
}