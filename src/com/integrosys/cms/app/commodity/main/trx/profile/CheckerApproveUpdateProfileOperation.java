/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/CheckerApproveUpdateProfileOperation.java,v 1.5 2004/08/17 06:52:17 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/17 06:52:17 $ Tag: $Name: $
 */
public class CheckerApproveUpdateProfileOperation extends AbstractProfileTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateProfileOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN;
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

		DefaultLogger.debug(this, "$$$Debug::: trxValue");
		IProfileTrxValue trxValue = getProfileTrxValue(anITrxValue);
		trxValue = updateActualProfile(trxValue);
		trxValue = super.updateProfileTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}