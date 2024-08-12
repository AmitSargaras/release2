/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/MakerCloseRejectedUpdateCCCOperation.java,v 1.2 2003/09/22 12:05:48 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to close a rejected cc certificate transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/22 12:05:48 $ Tag: $Name: $
 */
public class MakerCloseRejectedUpdateCCCOperation extends AbstractCCCTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedUpdateCCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_GENERATE_CCC;
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
		ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(anITrxValue);
		// trxValue.setStagingCCCertificate(trxValue.getCCCertificate());
		// trxValue = createStagingCCCertificate(trxValue);
		trxValue = updateCCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}