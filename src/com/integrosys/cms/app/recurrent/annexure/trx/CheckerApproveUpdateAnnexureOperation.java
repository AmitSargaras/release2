/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerApproveUpdateRecurrentCheckListOperation.java,v 1.3 2005/08/10 02:38:48 wltan Exp $
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
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/10 02:38:48 $ Tag: $Name: $
 */
public class CheckerApproveUpdateAnnexureOperation extends AbstractAnnexureTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateAnnexureOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_ANNEXURE_CHECKLIST;
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
		trxValue = updateActualCheckList(trxValue);
		trxValue = super.updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anICheckListTrxValue - IRecurrentCheckListTrxValue
	 * @return IRecurrentCheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IRecurrentCheckListTrxValue updateActualCheckList(IRecurrentCheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			IRecurrentCheckList staging = anICheckListTrxValue.getStagingCheckList();
			IRecurrentCheckList actual = anICheckListTrxValue.getCheckList();
			IRecurrentCheckList updActual = (IRecurrentCheckList) CommonUtil.deepClone(staging);
			// DefaultLogger.debug(this, "Befire Clone: " + updActual);
			updActual = mergeCheckList(actual, updActual);
			// DefaultLogger.debug(this, "After Clone: " + updActual);
			IRecurrentCheckList actualCheckList = getRecurrentBusManager().update(updActual);
			anICheckListTrxValue.setCheckList(actualCheckList);
			return anICheckListTrxValue;
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