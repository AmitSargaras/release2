/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/MakerCloseDraftOperation.java,v 1.1 2005/01/12 02:49:03 hshii Exp $
 */

package com.integrosys.cms.app.feed.trx.mutualfunds;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to update a checklist
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MakerCloseDraftOperation extends AbstractMutualFundsTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerCloseDraftOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_MUTUAL_FUNDS_FEED_GROUP;
	}

	/**
	 * Process the transaction This method close a DRAFT state to active. It has
	 * to revert the staging to make it mirror the actual. Easier for audit
	 * reporting. Otherwise could have shared the same operation as the Close
	 * Draft.
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IMutualFundsFeedGroupTrxValue trxValue = super.getMutualFundsFeedGroupTrxValue(anITrxValue);
		// trxValue.setStagingMutualFundsFeedGroup(trxValue.getMutualFundsFeedGroup());
		// trxValue = createStagingMutualFundsFeedGroup(trxValue);
		trxValue = updateMutualFundsFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
