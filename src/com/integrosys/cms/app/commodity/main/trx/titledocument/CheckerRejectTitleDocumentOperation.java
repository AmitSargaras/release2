/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/CheckerRejectTitleDocumentOperation.java,v 1.2 2004/06/04 04:54:12 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This operation allows a checker to reject a checklist transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:12 $ Tag: $Name: $
 */
public abstract class CheckerRejectTitleDocumentOperation extends AbstractTitleDocumentTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectTitleDocumentOperation() {
		super();
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "$$$Operation : 1 trxValue=" + anITrxValue);
		ITitleDocumentTrxValue trxValue = super.getTitleDocumentTrxValue(anITrxValue);
		trxValue = super.updateTitleDocumentTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}