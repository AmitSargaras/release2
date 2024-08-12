/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/SystemDeleteCheckListOperation.java,v 1.4 2006/09/01 02:12:11 hmbao Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//java
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows system to delete checklsit
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/01 02:12:11 $ Tag: $Name: $
 */
public class SystemDeleteCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemDeleteCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_DELETE_CHECKLIST;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance To approve
	 * custodian doc trxs spawned at the checklist item level
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		ICheckList checkList = trxValue.getStagingCheckList();
		checkList.setCheckListStatus(ICMSConstant.STATE_DELETED);
		trxValue.setStagingCheckList(checkList);
		return trxValue;
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
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		trxValue = createStagingCheckList(trxValue);
		trxValue = updateActualCheckList(trxValue);
		trxValue = super.updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation Send deleted notification
	 * 
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	// public ITrxResult postProcess(ITrxResult result) throws
	// TrxOperationException
	// {
	// result = super.postProcess(result);
	//
	// ICheckListTrxValue trxValue = (ICheckListTrxValue)result.getTrxValue();
	// ICheckList staging = trxValue.getStagingCheckList();
	// ICheckList actual = trxValue.getCheckList();
	// ICheckListOwner owner = trxValue.getCheckList().getCheckListOwner();
	// trxValue.setSendNotificationInd(true);
	// if (owner instanceof ICollateralCheckListOwner)
	// {
	// super.sendCollateralNotification(trxValue, true);
	// }
	// else
	// {
	// if ((owner instanceof ICCCheckListOwner) &&
	// (!ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(((ICCCheckListOwner)owner).
	// getSubOwnerType())))
	// {
	// super.sendCCNotification(trxValue, true);
	// }
	// }
	// return result;
	// }
}