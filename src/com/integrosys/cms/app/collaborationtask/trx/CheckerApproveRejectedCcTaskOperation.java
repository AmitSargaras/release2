package com.integrosys.cms.app.collaborationtask.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Mar 23, 2006 Time: 2:34:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class CheckerApproveRejectedCcTaskOperation extends AbstractCCTaskTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveRejectedCcTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_REJECTED_CC_TASK;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
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
