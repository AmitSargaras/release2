/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerApproveCreateCheckListOperation.java,v 1.3 2005/10/13 03:36:25 hshii Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve a checklist create
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/10/13 03:36:25 $ Tag: $Name: $
 */
public class CheckerApproveCreateCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CHECKLIST;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the staging
	 * data with the actual data 3. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		super.checklistOldStatus = ICMSConstant.STATE_CHECKLIST_NEW;

		trxValue = createActualCheckList(trxValue);
		trxValue = updateCheckListTransaction(trxValue);
		super.setSendNotificationIndicator(super.checklistOldStatus, trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anITrxValue - ITrxValue
	 * @return ICheckListTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ICheckListTrxValue createActualCheckList(ICheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			ICheckList checkList = anICheckListTrxValue.getStagingCheckList();
			ICheckList actualCheckList = getSBCheckListBusManager().create(checkList);
			anICheckListTrxValue.setCheckList(actualCheckList);
			anICheckListTrxValue.setReferenceID(String.valueOf(actualCheckList.getCheckListID()));
			return anICheckListTrxValue;
		}
		catch (CheckListException cex) {
			throw new TrxOperationException(cex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualCheckList(): " + ex.toString());
		}
	}
}