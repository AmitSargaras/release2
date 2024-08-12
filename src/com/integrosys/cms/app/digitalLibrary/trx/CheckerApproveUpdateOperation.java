/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/CheckerApproveUpdateOperation.java,v 1.3 2005/08/30 09:48:02 hshii Exp $
 */
package com.integrosys.cms.app.digitalLibrary.trx;

// java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.digitalLibrary.bus.DigitalLibraryException;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/30 09:48:02 $ Tag: $Name: $
 */
public class CheckerApproveUpdateOperation extends AbstractDigitalLibraryTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_DIGITAL_LIBRARY_GROUP;
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
		IDigitalLibraryTrxValue trxValue = getDigitalLibraryGroupTrxValue(anITrxValue);
		//trxValue = createStagingDigitalLibraryGroup(trxValue);
		trxValue = updateActualDigitalLibraryGroup(trxValue);
		trxValue = updateDigitalLibraryGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIDigitalLibraryGroupTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IDigitalLibraryTrxValue updateActualDigitalLibraryGroup(IDigitalLibraryTrxValue anIDigitalLibraryGroupTrxValue)
			throws TrxOperationException {
		try {
			IDigitalLibraryGroup staging = anIDigitalLibraryGroupTrxValue.getStagingDigitalLibraryGroup();
			IDigitalLibraryGroup actual = anIDigitalLibraryGroupTrxValue.getDigitalLibraryGroup();

			IDigitalLibraryGroup updatedFeedGroup = getDigitalLibraryBusManager().updateToWorkingCopy(actual, staging);
			anIDigitalLibraryGroupTrxValue.setDigitalLibraryGroup(updatedFeedGroup);
			return anIDigitalLibraryGroupTrxValue;
		}
		/*
		 * catch(ConcurrentUpdateException ex) { throw new
		 * TrxOperationException(ex); }
		 */catch (DigitalLibraryException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualDigitalLibraryGroup(): " + ex.toString());
		}
	}
}