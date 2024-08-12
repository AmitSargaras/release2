/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/MakerCloseRejectedUpdateSCCOperation.java,v 1.2 2003/09/22 12:06:23 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to close a rejected sc certificate transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/22 12:06:23 $ Tag: $Name: $
 */
public class MakerCloseRejectedUpdateSCCOperation extends AbstractSCCTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedUpdateSCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_GENERATE_SCC;
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
		ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(anITrxValue);
		// trxValue.setStagingSCCertificate(trxValue.getSCCertificate());
		// trxValue = createStagingSCCertificate(trxValue);
		trxValue = updateSCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}