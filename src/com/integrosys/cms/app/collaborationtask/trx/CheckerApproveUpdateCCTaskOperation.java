/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/CheckerApproveUpdateCCTaskOperation.java,v 1.4 2005/09/14 06:45:30 hshii Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This operation allows a checker to approve a CC task update
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/14 06:45:30 $ Tag: $Name: $
 */
public class CheckerApproveUpdateCCTaskOperation extends AbstractCCTaskTrxOperation {
	// private static final String EVENT_CC_TASK_CREATION =
	// "EV_CC_TASK_CREATION";

	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateCCTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CC_TASK;
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
		ICCTaskTrxValue trxValue = getCCTaskTrxValue(anITrxValue);
		trxValue = createStagingCCTask(trxValue);
		trxValue = updateActualCCTask(trxValue);
		trxValue = updateCCTaskTransaction(trxValue);
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
		ICCTaskTrxValue trxValue = getCCTaskTrxValue(result.getTrxValue());
		sendCCNotification(trxValue);
		return result;
	}

	/**
	 * Update the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return ICCTaskTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ICCTaskTrxValue updateActualCCTask(ICCTaskTrxValue anICCTaskTrxValue) throws TrxOperationException {
		try {
			ICCTask staging = anICCTaskTrxValue.getStagingCCTask();
			ICCTask actual = anICCTaskTrxValue.getCCTask();
			ICCTask updActual = (ICCTask) CommonUtil.deepClone(staging);
			updActual = mergeCCTask(actual, updActual);
			ICCTask actualColTask = getSBCollaborationTaskBusManager().updateCCTask(updActual);
			anICCTaskTrxValue.setCCTask(actualColTask);
			anICCTaskTrxValue.setReferenceID(String.valueOf(actualColTask.getTaskID()));
			return anICCTaskTrxValue;
		}
		catch (CollaborationTaskException ex) {
			throw new TrxOperationException(ex);
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualCCTask(): " + ex.toString());
		}
	}

}