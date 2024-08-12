/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/unittrust/MakerCloseDraftOperation.java,v 1.1 2005/01/13 02:33:18 hshii Exp $
 */

package com.integrosys.cms.app.feed.trx.unittrust;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to update a checklist
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.1 $
 * @since $Date: 2005/01/13 02:33:18 $ Tag: $Name: $
 */
public class MakerCloseDraftOperation extends AbstractUnitTrustTrxOperation {

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
		return ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_UNIT_TRUST_FEED_GROUP;
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
		IUnitTrustFeedGroupTrxValue trxValue = super.getUnitTrustFeedGroupTrxValue(anITrxValue);
		trxValue.setStagingUnitTrustFeedGroup(trxValue.getUnitTrustFeedGroup());
//		trxValue = createStagingUnitTrustFeedGroup(trxValue);
		trxValue = updateUnitTrustFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
