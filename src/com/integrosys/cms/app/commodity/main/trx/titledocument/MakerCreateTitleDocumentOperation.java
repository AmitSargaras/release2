/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/MakerCreateTitleDocumentOperation.java,v 1.4 2004/11/03 09:08:06 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation creates a pending document item
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/11/03 09:08:06 $ Tag: $Name: $
 */
public class MakerCreateTitleDocumentOperation extends AbstractTitleDocumentTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateTitleDocumentOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITitleDocumentTrxValue trxValue = super.getTitleDocumentTrxValue(anITrxValue);
		trxValue = createStagingTitleDocument(trxValue);

		if (anITrxValue.getTransactionID() == null) {
			trxValue = super.createTitleDocumentTransaction(trxValue);
		}
		else {
			trxValue = super.updateTitleDocumentTransaction(trxValue);
		}

		return super.prepareResult(trxValue);
	}
}