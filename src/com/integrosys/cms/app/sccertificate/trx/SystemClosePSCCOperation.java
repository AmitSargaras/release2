/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/SystemClosePSCCOperation.java,v 1.1 2003/09/04 13:19:51 kllee Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows system to close pscc trx
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/04 13:19:51 $ Tag: $Name: $
 */
public class SystemClosePSCCOperation extends AbstractPSCCTrxOperation {
	/**
	 * Default Constructor
	 */
	public SystemClosePSCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_PSCC;
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