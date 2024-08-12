/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/MakerUpdateOperation.java,v 1.2 2003/08/06 05:42:09 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.forex;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to update a checklist
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/06 05:42:09 $ Tag: $Name: $
 */
public class MakerUpdateOperation extends AbstractForexTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerUpdateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_FOREX_FEED_GROUP;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IForexFeedGroupTrxValue trxValue = createStagingForexFeedGroup(getForexFeedGroupTrxValue(anITrxValue));
		trxValue = updateForexFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}