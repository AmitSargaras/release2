/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/CheckerApproveCreateDeferralRequestOperation.java,v 1.2 2003/09/12 17:43:20 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve a deferral request create
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/12 17:43:20 $ Tag: $Name: $
 */
public class CheckerApproveCreateDeferralRequestOperation extends AbstractDeferralRequestTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateDeferralRequestOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DEFERRAL_REQ;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDeferralRequestTrxValue trxValue = getDeferralRequestTrxValue(anITrxValue);
		trxValue = updateDeferralRequestTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}