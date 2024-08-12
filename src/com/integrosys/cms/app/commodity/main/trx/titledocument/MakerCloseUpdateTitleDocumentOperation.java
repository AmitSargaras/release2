/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/MakerCloseUpdateTitleDocumentOperation.java,v 1.2 2005/01/10 10:24:37 hshii Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to close rejected/draft
 * transactions.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/01/10 10:24:37 $ Tag: $Name: $
 */
public class MakerCloseUpdateTitleDocumentOperation extends AbstractTitleDocumentTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerCloseUpdateTitleDocumentOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging title document type record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ITitleDocumentTrxValue trxValue = getTitleDocumentTrxValue(value);
			// trxValue.setStagingTitleDocument(trxValue.getTitleDocument());
			// trxValue = createStagingTitleDocument(trxValue);
			trxValue = updateTitleDocumentTransaction(trxValue);
			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}
