/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/CheckerRejectCollateralTaskOperation.java,v 1.2 2005/09/14 06:45:30 hshii Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to reject a collateral task transaction
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/14 06:45:30 $ Tag: $Name: $
 */
public class CheckerRejectCollateralTaskOperation extends AbstractCollateralTaskTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectCollateralTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_TASK;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICollateralTaskTrxValue trxValue = super.getCollateralTaskTrxValue(anITrxValue);
		trxValue = super.createStagingCollateralTask(trxValue);
		trxValue = super.updateCollateralTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}