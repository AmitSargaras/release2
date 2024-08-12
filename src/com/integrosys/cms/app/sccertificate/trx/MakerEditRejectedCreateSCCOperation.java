/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/MakerEditRejectedCreateSCCOperation.java,v 1.1 2003/08/08 12:45:37 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to edit a rejected sc certificate transaction
 * creation
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 12:45:37 $ Tag: $Name: $
 */
public class MakerEditRejectedCreateSCCOperation extends AbstractSCCTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedCreateSCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_GENERATE_SCC;
	}

	/**
	 * Process the transaction 1. Create Staging record 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ISCCertificateTrxValue trxValue = createStagingSCCertificate(getSCCertificateTrxValue(anITrxValue));
		trxValue = updateSCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}