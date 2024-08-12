/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/SystemCloseCheckListOperation.java,v 1.3 2003/12/02 02:03:41 hltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/12/02 02:03:41 $ Tag: $Name: $
 */
public class SystemCloseCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemCloseCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_CHECKLIST;
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
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		ICheckList checkList = trxValue.getStagingCheckList();
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
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		trxValue = createStagingCheckList(trxValue);
		if (trxValue.getCheckList() != null) {
			trxValue = updateActualCheckList(trxValue);
		}
		trxValue = super.updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	/*
	 * private ICheckListTrxValue updateActualCheckList(ICheckListTrxValue
	 * anICheckListTrxValue) throws TrxOperationException { try { ICheckList
	 * staging = anICheckListTrxValue.getStagingCheckList(); ICheckList actual =
	 * anICheckListTrxValue.getCheckList(); ICheckList updActual =
	 * (ICheckList)CommonUtil.deepClone(staging); DefaultLogger.debug(this,
	 * "Befire Clone: " + updActual); updActual = mergeCheckList(actual,
	 * updActual); DefaultLogger.debug(this, "After Clone: " + updActual);
	 * ICheckList actualCheckList =
	 * getSBCheckListTemplateBusManager().update(updActual);
	 * anICheckListTrxValue.setCheckList(updActual); return
	 * anICheckListTrxValue; } catch(ConcurrentUpdateException ex) { throw new
	 * TrxOperationException(ex); } catch(CheckListException ex) { throw new
	 * TrxOperationException(ex); } catch(Exception ex) { throw new
	 * TrxOperationException("Exception in updateActualCheckList(): " +
	 * ex.toString()); } }
	 */
}