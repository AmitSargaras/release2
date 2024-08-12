/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/unittrust/CheckerApproveUpdateOperation.java,v 1.3 2005/08/30 09:49:07 hshii Exp $
 */
package com.integrosys.cms.app.feed.trx.unittrust;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.bus.unittrust.UnitTrustFeedGroupException;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/30 09:49:07 $ Tag: $Name: $
 */
public class CheckerApproveUpdateOperation extends AbstractUnitTrustTrxOperation {

	private final static Log logger = LogFactory.getLog(CheckerApproveUpdateOperation.class);

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
		return ICMSConstant.ACTION_CHECKER_APPROVE_UNIT_TRUST_FEED_GROUP;
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
		IUnitTrustFeedGroupTrxValue trxValue = getUnitTrustFeedGroupTrxValue(anITrxValue);
		trxValue = createStagingUnitTrustFeedGroup(trxValue);
		trxValue = updateActualUnitTrustFeedGroup(trxValue);
		trxValue = updateUnitTrustFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIUnitTrustFeedGroupTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IUnitTrustFeedGroupTrxValue updateActualUnitTrustFeedGroup(
			IUnitTrustFeedGroupTrxValue anIUnitTrustFeedGroupTrxValue) throws TrxOperationException {
		try {
			IUnitTrustFeedGroup staging = anIUnitTrustFeedGroupTrxValue.getStagingUnitTrustFeedGroup();
			IUnitTrustFeedGroup actual = anIUnitTrustFeedGroupTrxValue.getUnitTrustFeedGroup();

			IUnitTrustFeedGroup updatedFeedGroup = getUnitTrustFeedBusManager().updateToWorkingCopy(actual, staging);

			anIUnitTrustFeedGroupTrxValue.setUnitTrustFeedGroup(updatedFeedGroup);
			return anIUnitTrustFeedGroupTrxValue;
		}
		catch (UnitTrustFeedGroupException ex) {
			logger.error("error when in 'updateActualUnitTrustFeedGroup'", ex);
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			logger.error("error when in 'updateActualUnitTrustFeedGroup'", ex);
			throw new TrxOperationException(ex);
		}
	}
}