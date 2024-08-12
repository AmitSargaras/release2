/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/propertyindex/CheckerRejectOperation.java,v 1.1 2003/08/20 10:59:58 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.propertyindex;

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
 * @since $Date: 2003/08/20 10:59:58 $ Tag: $Name: $
 */
public class CheckerRejectOperation extends AbstractPropertyIndexTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_PROPERTY_INDEX_FEED_GROUP;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPropertyIndexFeedGroupTrxValue trxValue = super.getPropertyIndexFeedGroupTrxValue(anITrxValue);
		// trxValue.setStagingPropertyIndexFeedGroup(trxValue.
		// getPropertyIndexFeedGroup());
		// trxValue = createStagingPropertyIndexFeedGroup(trxValue);
		trxValue = updatePropertyIndexFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
