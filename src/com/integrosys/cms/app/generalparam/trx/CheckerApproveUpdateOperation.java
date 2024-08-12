/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/CheckerApproveUpdateOperation.java,v 1.3 2005/08/30 09:48:02 hshii Exp $
 */
package com.integrosys.cms.app.generalparam.trx;

// java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.GeneralParamGroupException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class CheckerApproveUpdateOperation extends AbstractGeneralParamTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_GENERAL_PARAM_GROUP;
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
		IGeneralParamGroupTrxValue trxValue = getGeneralParamGroupTrxValue(anITrxValue);
		//trxValue = createStagingGeneralParamGroup(trxValue);
		trxValue = updateActualGeneralParamGroup(trxValue);
		trxValue = updateGeneralParamGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIGeneralParamGroupTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IGeneralParamGroupTrxValue updateActualGeneralParamGroup(IGeneralParamGroupTrxValue anIGeneralParamGroupTrxValue)
			throws TrxOperationException {
		try {
			IGeneralParamGroup staging = anIGeneralParamGroupTrxValue.getStagingGeneralParamGroup();
			IGeneralParamGroup actual = anIGeneralParamGroupTrxValue.getGeneralParamGroup();

			IGeneralParamGroup updatedFeedGroup = getGeneralParamBusManager().updateToWorkingCopy(actual, staging);
			anIGeneralParamGroupTrxValue.setGeneralParamGroup(updatedFeedGroup);
			return anIGeneralParamGroupTrxValue;
		}
		/*
		 * catch(ConcurrentUpdateException ex) { throw new
		 * TrxOperationException(ex); }
		 */catch (GeneralParamGroupException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualGeneralParamGroup(): " + ex.toString());
		}
	}
}