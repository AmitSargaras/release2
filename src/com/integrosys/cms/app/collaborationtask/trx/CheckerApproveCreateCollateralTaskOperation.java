/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/CheckerApproveCreateCollateralTaskOperation.java,v 1.8 2005/10/13 03:32:22 hshii Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve a collateral task create
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.8 $
 * @since $Date: 2005/10/13 03:32:22 $ Tag: $Name: $
 */
public class CheckerApproveCreateCollateralTaskOperation extends AbstractCollateralTaskTrxOperation {

	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateCollateralTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_COLLATERAL_TASK;
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
		ICollateralTaskTrxValue trxValue = getCollateralTaskTrxValue(anITrxValue);
		trxValue = createStagingCollateralTask(trxValue);
		trxValue = createActualCollateralTask(trxValue);
		trxValue = updateCollateralTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To sent notification
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		result = super.postProcess(result);
		ICollateralTaskTrxValue trxValue = getCollateralTaskTrxValue(result.getTrxValue());
		sendCollateralNotification(trxValue);
		return result;
	}

	/**
	 * Create the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return ICollateralTaskTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ICollateralTaskTrxValue createActualCollateralTask(ICollateralTaskTrxValue anICollateralTaskTrxValue)
			throws TrxOperationException {
		try {
			ICollateralTask colTask = anICollateralTaskTrxValue.getStagingCollateralTask();
			ICollateralTask actualColTask = getSBCollaborationTaskBusManager().createCollateralTask(colTask);
			anICollateralTaskTrxValue.setCollateralTask(actualColTask);
			anICollateralTaskTrxValue.setReferenceID(String.valueOf(actualColTask.getTaskID()));
			return anICollateralTaskTrxValue;
		}
		catch (CollaborationTaskException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualCollateralTask(): " + ex.toString());
		}
	}
}