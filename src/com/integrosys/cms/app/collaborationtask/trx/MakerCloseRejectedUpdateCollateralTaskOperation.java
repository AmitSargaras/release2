/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/MakerCloseRejectedUpdateCollateralTaskOperation.java,v 1.1 2004/01/14 03:39:04 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to close a rejected collateral task transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/01/14 03:39:04 $ Tag: $Name: $
 */
public class MakerCloseRejectedUpdateCollateralTaskOperation extends AbstractCollateralTaskTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedUpdateCollateralTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_COLLATERAL_TASK;
	}

	/**
	 * Process the transaction 1. Create staging from the actual 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICollateralTaskTrxValue trxValue = getCollateralTaskTrxValue(anITrxValue);
		trxValue = updateCollateralTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}