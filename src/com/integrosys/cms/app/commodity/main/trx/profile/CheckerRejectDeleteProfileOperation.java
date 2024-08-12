/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/CheckerRejectDeleteProfileOperation.java,v 1.2 2004/06/04 04:54:03 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:03 $ Tag: $Name: $
 */
public class CheckerRejectDeleteProfileOperation extends AbstractProfileTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectDeleteProfileOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		// Delete : does softDeletion. Is nothing but updates with DELETED state
		IProfileTrxValue trxValue = getProfileTrxValue(anITrxValue);
		trxValue = updateActualProfile(trxValue);
		trxValue = super.updateProfileTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}