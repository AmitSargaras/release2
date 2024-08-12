/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/SystemResetPSCCOperation.java,v 1.1 2003/11/06 02:08:03 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows system to reset pscc trx
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/11/06 02:08:03 $ Tag: $Name: $
 */
public class SystemResetPSCCOperation extends AbstractPSCCTrxOperation {
	/**
	 * Default Constructor
	 */
	public SystemResetPSCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_RESET_PSCC;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPartialSCCertificateTrxValue trxValue = getPartialSCCertificateTrxValue(anITrxValue);
		trxValue = updatePartialSCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}