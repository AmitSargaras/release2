/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/SystemCloseRecurrentCheckListOperation.java,v 1.1 2003/08/25 12:26:35 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.annexure.trx;

//ofa

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/25 12:26:35 $ Tag: $Name: $
 */
public class SystemCloseAnnexureOperation extends AbstractAnnexureTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemCloseAnnexureOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_ANNEXURE_CHECKLIST;
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
		IRecurrentCheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		IRecurrentCheckList checkList = trxValue.getStagingCheckList();
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
		IRecurrentCheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		trxValue = createStagingCheckList(trxValue);
		trxValue = updateActualCheckList(trxValue);
		trxValue = super.updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIRecurrentCheckListTrxValue - IRecurrentCheckListTrxValue
	 * @return IRecurrentCheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IRecurrentCheckListTrxValue updateActualCheckList(IRecurrentCheckListTrxValue anIRecurrentCheckListTrxValue)
			throws TrxOperationException {
		try {
			IRecurrentCheckList staging = anIRecurrentCheckListTrxValue.getStagingCheckList();
			IRecurrentCheckList actual = anIRecurrentCheckListTrxValue.getCheckList();
			IRecurrentCheckList updActual = (IRecurrentCheckList) CommonUtil.deepClone(staging);
			updActual = mergeCheckList(actual, updActual);
			IRecurrentCheckList actualCheckList = getRecurrentBusManager().update(updActual);
			anIRecurrentCheckListTrxValue.setCheckList(actualCheckList);
			return anIRecurrentCheckListTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RecurrentException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualCheckList(): " + ex.toString());
		}
	}
}