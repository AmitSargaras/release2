/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/MakerUpdateCCCOperation.java,v 1.1 2003/08/05 11:32:06 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to update a cc certificate
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/05 11:32:06 $ Tag: $Name: $
 */
public class MakerUpdateCCCOperation extends AbstractCCCTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerUpdateCCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_GENERATE_CCC;
	}

	/**
	 * Process the transaction 1. Create staging record 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCCertificateTrxValue trxValue = createStagingCCCertificate(getCCCertificateTrxValue(anITrxValue));
		trxValue = updateCCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}