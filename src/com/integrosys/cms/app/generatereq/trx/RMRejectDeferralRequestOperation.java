/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/RMRejectDeferralRequestOperation.java,v 1.1 2003/09/12 17:43:20 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a RM to reject a Deferral request transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/12 17:43:20 $ Tag: $Name: $
 */
public class RMRejectDeferralRequestOperation extends AbstractDeferralRequestTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public RMRejectDeferralRequestOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_RM_REJECT_GENERATE_DEFERRAL_REQ;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDeferralRequestTrxValue trxValue = super.getDeferralRequestTrxValue(anITrxValue);
		trxValue = super.updateDeferralRequestTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}