/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/CheckerApproveCreateTitleDocumentOperation.java,v 1.3 2004/08/17 06:52:30 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve a checklist create
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/17 06:52:30 $ Tag: $Name: $
 */
public class CheckerApproveCreateTitleDocumentOperation extends AbstractTitleDocumentTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateTitleDocumentOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the staging
	 * data with the actual data 3. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITitleDocumentTrxValue trxValue = getTitleDocumentTrxValue(anITrxValue);
		trxValue = createActualTitleDocument(trxValue);
		trxValue = updateTitleDocumentTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anITitleDocumentTrxValue - ITrxValue
	 * @return ITitleDocumentTrxValue - the document item trx value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	private ITitleDocumentTrxValue createActualTitleDocument(ITitleDocumentTrxValue anITitleDocumentTrxValue)
			throws TrxOperationException {
		try {
			DefaultLogger.debug(this, "$$$Debug: x1  staging.getRefID="
					+ anITitleDocumentTrxValue.getStagingTitleDocument()[0].getGroupID());
			ITitleDocument[] staging = anITitleDocumentTrxValue.getStagingTitleDocument();

			ITitleDocument[] actualTitleDocument = (ITitleDocument[]) getBusManager().createInfo(staging);

			DefaultLogger.debug(this, "$$$Debug: x2  staging.getRefID=" + staging[0].getTitleDocumentID()
					+ " , actual RefID=" + actualTitleDocument[0].getTitleDocumentID());

			anITitleDocumentTrxValue.setTitleDocument(actualTitleDocument);
			anITitleDocumentTrxValue.setReferenceID(String.valueOf(actualTitleDocument[0].getTitleDocumentID()));

			DefaultLogger.debug(this, "$$$Debug: x3  staging.getRefID=" + staging[0].getTitleDocumentID()
					+ " , actual RefID=" + actualTitleDocument[0].getTitleDocumentID());

			anITitleDocumentTrxValue.setStagingReferenceID(String.valueOf(staging[0].getTitleDocumentID()));

			DefaultLogger.debug(this, "$$$Debug: x4  staging.getRefID=" + staging[0].getTitleDocumentID()
					+ " , actual RefID=" + actualTitleDocument[0].getTitleDocumentID());

			return anITitleDocumentTrxValue;

		}
		catch (CommodityException cex) {
			throw new TrxOperationException(cex);
		}
	}
}