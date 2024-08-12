/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/trx/CheckerApproveUpdateCCDocumentLocationOperation.java,v 1.2 2004/04/08 12:52:45 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.documentlocation.bus.DocumentLocationException;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;

/**
 * This operation allows a checker to approve a CC document location update
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/04/08 12:52:45 $ Tag: $Name: $
 */
public class CheckerApproveUpdateCCDocumentLocationOperation extends AbstractCCDocumentLocationTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateCCDocumentLocationOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CC_DOC_LOC;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCDocumentLocationTrxValue trxValue = getCCDocumentLocationTrxValue(anITrxValue);
		trxValue = updateActualCCDocumentLocation(trxValue);
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
	 * Update the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return ICCDocumentLocationTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ICCDocumentLocationTrxValue updateActualCCDocumentLocation(
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws TrxOperationException {
		try {
			ICCDocumentLocation staging = anICCDocumentLocationTrxValue.getStagingCCDocumentLocation();
			ICCDocumentLocation actual = anICCDocumentLocationTrxValue.getCCDocumentLocation();
			ICCDocumentLocation updActual = (ICCDocumentLocation) CommonUtil.deepClone(staging);
			updActual = mergeCCDocumentLocation(actual, updActual);
			ICCDocumentLocation actualCCDocumentLocation = getSBDocumentLocationBusManager().updateCCDocumentLocation(
					updActual);
			anICCDocumentLocationTrxValue.setCCDocumentLocation(actualCCDocumentLocation);
			anICCDocumentLocationTrxValue.setReferenceID(String.valueOf(actualCCDocumentLocation.getDocLocationID()));
			return anICCDocumentLocationTrxValue;
		}
		catch (DocumentLocationException ex) {
			throw new TrxOperationException(ex);
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualCCDocumentLocation(): " + ex.toString());
		}
	}
}