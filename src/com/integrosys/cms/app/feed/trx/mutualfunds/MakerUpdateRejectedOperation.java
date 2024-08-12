/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/MakerUpdateRejectedOperation.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
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
public class MakerUpdateRejectedOperation extends AbstractMutualFundsTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerUpdateRejectedOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_MUTUAL_FUNDS_FEED_GROUP;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IMutualFundsFeedGroupTrxValue trxValue = createStagingMutualFundsFeedGroup(getMutualFundsFeedGroupTrxValue(anITrxValue));
		trxValue = updateMutualFundsFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
