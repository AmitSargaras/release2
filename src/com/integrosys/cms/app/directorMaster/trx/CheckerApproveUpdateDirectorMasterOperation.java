package com.integrosys.cms.app.directorMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;

/**
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateDirectorMasterOperation extends AbstractDirectorMasterTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateDirectorMasterOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_DIRECTOR_MASTER;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDirectorMasterTrxValue trxValue = getDirectorMasterTrxValue(anITrxValue);
		trxValue = updateActualDirectorMaster(trxValue);
		trxValue = updateDirectorMasterTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IDirectorMasterTrxValue updateActualDirectorMaster(IDirectorMasterTrxValue anICCDirectorMasterTrxValue)
			throws TrxOperationException {
		try {
			IDirectorMaster staging = anICCDirectorMasterTrxValue.getStagingDirectorMaster();
			IDirectorMaster actual = anICCDirectorMasterTrxValue.getDirectorMaster();

			IDirectorMaster updatedDirectorMaster = getDirectorMasterBusManager().updateToWorkingCopy(actual, staging);
			anICCDirectorMasterTrxValue.setDirectorMaster(updatedDirectorMaster);

			return anICCDirectorMasterTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
