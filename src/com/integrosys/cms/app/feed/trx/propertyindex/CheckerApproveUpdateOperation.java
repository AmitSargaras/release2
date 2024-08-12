/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/propertyindex/CheckerApproveUpdateOperation.java,v 1.3 2005/08/30 09:48:35 hshii Exp $
 */
package com.integrosys.cms.app.feed.trx.propertyindex;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/30 09:48:35 $ Tag: $Name: $
 */
public class CheckerApproveUpdateOperation extends AbstractPropertyIndexTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_PROPERTY_INDEX_FEED_GROUP;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPropertyIndexFeedGroupTrxValue trxValue = getPropertyIndexFeedGroupTrxValue(anITrxValue);
		trxValue = createStagingPropertyIndexFeedGroup(trxValue);
		trxValue = updateActualPropertyIndexFeedGroup(trxValue);
		trxValue = updatePropertyIndexFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIPropertyIndexFeedGroupTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IPropertyIndexFeedGroupTrxValue updateActualPropertyIndexFeedGroup(
			IPropertyIndexFeedGroupTrxValue anIPropertyIndexFeedGroupTrxValue) throws TrxOperationException {
		try {
			IPropertyIndexFeedGroup staging = anIPropertyIndexFeedGroupTrxValue.getStagingPropertyIndexFeedGroup();
			IPropertyIndexFeedGroup actual = anIPropertyIndexFeedGroupTrxValue.getPropertyIndexFeedGroup();
			IPropertyIndexFeedGroup updActual = (IPropertyIndexFeedGroup) CommonUtil.deepClone(staging);
			DefaultLogger.debug(this, "Before Clone: " + updActual);
			updActual = mergePropertyIndexFeedGroup(actual, updActual); // set
																		// versionTime
																		// and
																		// ID
			DefaultLogger.debug(this, "After Clone: " + updActual);

			IPropertyIndexFeedGroup actualPropertyIndexFeedGroup = getSBPropertyIndexFeedBusManager()
					.updatePropertyIndexFeedGroup(updActual);
			anIPropertyIndexFeedGroupTrxValue.setPropertyIndexFeedGroup(updActual);
			return anIPropertyIndexFeedGroupTrxValue;
		}
		/*
		 * catch(ConcurrentUpdateException ex) { throw new
		 * TrxOperationException(ex); }
		 */catch (PropertyIndexFeedGroupException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualPropertyIndexFeedGroup(): " + ex.toString());
		}
	}
}