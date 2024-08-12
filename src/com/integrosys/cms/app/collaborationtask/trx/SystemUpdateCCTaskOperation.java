/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/SystemUpdateCCTaskOperation.java,v 1.1 2004/04/12 02:13:51 hltan Exp $
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
 * This operation allows system to update a collaboration task
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/04/12 02:13:51 $ Tag: $Name: $
 */
public class SystemUpdateCCTaskOperation extends AbstractCCTaskTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemUpdateCCTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_UPDATE_CC_TASK;
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
		ICCTaskTrxValue trxValue = createStagingCCTask(getCCTaskTrxValue(anITrxValue));
		trxValue = updateActualCCTask(trxValue);
		trxValue = updateCCTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual CC task from the staging CC task
	 * @param anICCTaskTrxValue - ICCTaskTrxValue
	 * @return ICCTaskTrxValue - the CC task trx value
	 * @throws TrxOperationException on errors
	 */
	private ICCTaskTrxValue updateActualCCTask(ICCTaskTrxValue anICCTaskTrxValue) throws TrxOperationException {
		try {
			ICCTask staging = anICCTaskTrxValue.getStagingCCTask();
			ICCTask actual = anICCTaskTrxValue.getCCTask();
			ICCTask updActual = (ICCTask) CommonUtil.deepClone(staging);
			updActual = mergeCCTask(actual, updActual);
			ICCTask actualCCTask = getSBCollaborationTaskBusManager().updateCCTask(updActual);
			anICCTaskTrxValue.setCCTask(updActual);
			return anICCTaskTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (CollaborationTaskException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualCCTask(): " + ex.toString());
		}
	}
}