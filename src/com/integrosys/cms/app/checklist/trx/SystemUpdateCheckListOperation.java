/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/SystemUpdateCheckListOperation.java,v 1.4 2004/04/08 12:52:44 hltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/04/08 12:52:44 $ Tag: $Name: $
 */
public class SystemUpdateCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemUpdateCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_UPDATE_CHECKLIST;
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
	 * Create the staging document item doc
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected ICheckListTrxValue createStagingCheckList(ICheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			ICheckList checkList = getSBStagingCheckListBusManager().createCheckList(
					anICheckListTrxValue.getStagingCheckList());
			anICheckListTrxValue.setStagingCheckList(checkList);
			anICheckListTrxValue.setStagingReferenceID(String.valueOf(checkList.getCheckListID()));
			return anICheckListTrxValue;
		}
		catch (CheckListException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	protected ICheckListTrxValue updateActualCheckList(ICheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			ICheckList staging = anICheckListTrxValue.getStagingCheckList();
			ICheckList actual = anICheckListTrxValue.getCheckList();
			if (actual != null) {
				ICheckList updActual = (ICheckList) CommonUtil.deepClone(staging);
				updActual = mergeCheckList(actual, updActual);
				ICheckList actualCheckList = getSBCheckListBusManager().updateCheckList(updActual);
				anICheckListTrxValue.setCheckList(updActual);
			}
			return anICheckListTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (CheckListException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualCheckList(): " + ex.toString());
		}
	}
}