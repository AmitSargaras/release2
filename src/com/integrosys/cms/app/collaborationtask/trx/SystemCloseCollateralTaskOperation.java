/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/SystemCloseCollateralTaskOperation.java,v 1.2 2004/04/08 12:52:44 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This operation allows system to close a collaboration task
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/04/08 12:52:44 $ Tag: $Name: $
 */
public class SystemCloseCollateralTaskOperation extends AbstractCollateralTaskTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemCloseCollateralTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_COLLATERAL_TASK;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance To approve
	 * custodian doc trxs spawned at the Collateral task item level
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		ICollateralTaskTrxValue trxValue = getCollateralTaskTrxValue(anITrxValue);
		ICollateralTask colTask = trxValue.getStagingCollateralTask();
		colTask.setIsDeletedInd(true);
		trxValue.setStagingCollateralTask(colTask);
		return trxValue;
	}

	/**
	 * Process the transaction 1. Create Staging record 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICollateralTaskTrxValue trxValue = createStagingCollateralTask(getCollateralTaskTrxValue(anITrxValue));
		trxValue = updateActualCollateralTask(trxValue);
		trxValue = updateCollateralTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual collateral task from the staging collateral task
	 * @param anICollateralTaskTrxValue - ICollateralTaskTrxValue
	 * @return ICollateralTaskTrxValue - the Collateral task trx value
	 * @throws TrxOperationException on errors
	 */
	private ICollateralTaskTrxValue updateActualCollateralTask(ICollateralTaskTrxValue anICollateralTaskTrxValue)
			throws TrxOperationException {
		try {
			ICollateralTask staging = anICollateralTaskTrxValue.getStagingCollateralTask();
			ICollateralTask actual = anICollateralTaskTrxValue.getCollateralTask();
			if (actual != null) {
				ICollateralTask updActual = (ICollateralTask) CommonUtil.deepClone(staging);
				updActual = mergeCollateralTask(actual, updActual);
				ICollateralTask actualCollateralTask = getSBCollaborationTaskBusManager().updateCollateralTask(
						updActual);
				anICollateralTaskTrxValue.setCollateralTask(updActual);
			}
			return anICollateralTaskTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (CollaborationTaskException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualCollateralTask(): " + ex.toString());
		}
	}
}