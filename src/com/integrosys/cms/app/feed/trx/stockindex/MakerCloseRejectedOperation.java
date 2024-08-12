/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stockindex/MakerCloseRejectedOperation.java,v 1.1 2003/08/18 10:13:16 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.stockindex;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to update a checklist
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 10:13:16 $ Tag: $Name: $
 */
public class MakerCloseRejectedOperation extends AbstractStockIndexTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_STOCK_INDEX_FEED_GROUP;
	}

	/**
	 * Process the transaction This method close a REJECTED state to active. It
	 * is different from CLOSE from DRAFT because it has to revert the staging
	 * to make it mirror the actual. Easier for audit reporting. Otherwise could
	 * have shared the same operation as the Close Draft.
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IStockIndexFeedGroupTrxValue trxValue = super.getStockIndexFeedGroupTrxValue(anITrxValue);
		//trxValue.setStagingStockIndexFeedGroup(trxValue.getStockIndexFeedGroup
		// ());
		// trxValue = createStagingStockIndexFeedGroup(trxValue);
		trxValue = updateStockIndexFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
