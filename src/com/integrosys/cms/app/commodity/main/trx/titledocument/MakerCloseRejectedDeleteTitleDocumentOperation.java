/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/MakerCloseRejectedDeleteTitleDocumentOperation.java,v 1.2 2004/06/04 04:54:12 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to close a rejected commodity transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:12 $ Tag: $Name: $
 */
public class MakerCloseRejectedDeleteTitleDocumentOperation extends AbstractTitleDocumentTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedDeleteTitleDocumentOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_DELETE_CLOSE_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITitleDocumentTrxValue trxValue = getTitleDocumentTrxValue(anITrxValue);
		// trxValue.setStagingTitleDocument(trxValue.getTitleDocument());
		// trxValue = createStagingTitleDocument(trxValue);
		trxValue = updateTitleDocumentTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}