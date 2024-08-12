package com.integrosys.cms.app.collaborationtask.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Mar 29, 2006 Time: 1:57:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class CheckerApproveRejectedCollateralTaskOperation extends AbstractCollateralTaskTrxOperation {

	public CheckerApproveRejectedCollateralTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_REJECTED_COLLATERAL_TASK;
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
		ICollateralTaskTrxValue trxValue = getCollateralTaskTrxValue(anITrxValue);
		trxValue = createStagingCollateralTask(trxValue);
		trxValue = updateActualCollateralTask(trxValue);
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
	 * Update the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return ICollateralTaskTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ICollateralTaskTrxValue updateActualCollateralTask(ICollateralTaskTrxValue anICollateralTaskTrxValue)
			throws TrxOperationException {
		try {
			ICollateralTask staging = anICollateralTaskTrxValue.getStagingCollateralTask();
			ICollateralTask actual = anICollateralTaskTrxValue.getCollateralTask();
			ICollateralTask updActual = (ICollateralTask) CommonUtil.deepClone(staging);
			updActual = mergeCollateralTask(actual, updActual);
			ICollateralTask actualColTask = getSBCollaborationTaskBusManager().updateCollateralTask(updActual);
			anICollateralTaskTrxValue.setCollateralTask(actualColTask);
			anICollateralTaskTrxValue.setReferenceID(String.valueOf(actualColTask.getTaskID()));
			return anICollateralTaskTrxValue;
		}
		catch (CollaborationTaskException ex) {
			throw new TrxOperationException(ex);
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualCollateralTask(): " + ex.toString());
		}
	}
}
