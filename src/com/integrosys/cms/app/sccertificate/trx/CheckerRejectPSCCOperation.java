/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/CheckerRejectPSCCOperation.java,v 1.1 2003/08/11 12:51:02 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to reject a partial sc certificate
 * transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 12:51:02 $ Tag: $Name: $
 */
public class CheckerRejectPSCCOperation extends AbstractPSCCTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectPSCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_PSCC;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPartialSCCertificateTrxValue trxValue = super.getPartialSCCertificateTrxValue(anITrxValue);
		trxValue = super.updatePartialSCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}