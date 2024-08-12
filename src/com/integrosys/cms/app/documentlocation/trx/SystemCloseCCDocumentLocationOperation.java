/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/trx/SystemCloseCCDocumentLocationOperation.java,v 1.1 2004/02/17 02:12:37 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.documentlocation.bus.DocumentLocationException;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;

/**
 * This operation allows system to close a document location
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:37 $ Tag: $Name: $
 */
public class SystemCloseCCDocumentLocationOperation extends AbstractCCDocumentLocationTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemCloseCCDocumentLocationOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_CC_DOC_LOC;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance To approve
	 * custodian doc trxs spawned at the CC document location item level
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	/*
	 * public ITrxValue preProcess(ITrxValue anITrxValue) throws
	 * TrxOperationException { anITrxValue = super.preProcess(anITrxValue);
	 * ICCDocumentLocationTrxValue trxValue = getCCDocumentLocationTrxValue
	 * (anITrxValue); ICCDocumentLocation colDocumentLocation =
	 * trxValue.getStagingCCDocumentLocation();
	 * colDocumentLocation.setIsDeletedInd(true);
	 * trxValue.setStagingCCDocumentLocation(colDocumentLocation); return
	 * trxValue; }
	 */

	/**
	 * Process the transaction 1. Create Staging record 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCDocumentLocationTrxValue trxValue = createStagingCCDocumentLocation(getCCDocumentLocationTrxValue(anITrxValue));
		trxValue = updateActualCCDocumentLocation(trxValue);
		trxValue = updateCCDocumentLocationTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual CC document location from the staging CC document
	 * location
	 * @param anICCDocumentLocationTrxValue - ICCDocumentLocationTrxValue
	 * @return ICCDocumentLocationTrxValue - the CC Document Location trx value
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
			anICCDocumentLocationTrxValue.setCCDocumentLocation(updActual);
			return anICCDocumentLocationTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (DocumentLocationException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualCCDocumentLocation(): " + ex.toString());
		}
	}
}