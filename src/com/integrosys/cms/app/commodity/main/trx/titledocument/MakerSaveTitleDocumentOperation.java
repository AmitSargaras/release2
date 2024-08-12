/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/MakerSaveTitleDocumentOperation.java,v 1.2 2004/08/13 04:16:22 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to save title document type.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/08/13 04:16:22 $ Tag: $Name: $
 */
public class MakerSaveTitleDocumentOperation extends AbstractTitleDocumentTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerSaveTitleDocumentOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging title document type record 2. create/update Transaction
	 * record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ITitleDocumentTrxValue trxValue = super.getTitleDocumentTrxValue(value);

			trxValue = super.createStagingTitleDocument(trxValue);

			if (trxValue.getTransactionID() == null) {
				trxValue = (ITitleDocumentTrxValue) super.createTransaction(trxValue);
			}
			else {
				trxValue = (ITitleDocumentTrxValue) super.updateTitleDocumentTransaction(trxValue);
			}

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
