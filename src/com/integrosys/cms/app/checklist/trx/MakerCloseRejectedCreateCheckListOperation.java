/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerCloseRejectedCreateCheckListOperation.java,v 1.4 2006/11/23 12:42:41 jychong Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to close a rejected doc item creation
 * transaction
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.4 $
 * @since $Date: 2006/11/23 12:42:41 $ Tag: $Name: $
 */
public class MakerCloseRejectedCreateCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedCreateCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREATE_CHECKLIST;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICheckListTrxValue trxValue = super.getCheckListTrxValue(anITrxValue);
		trxValue = updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To reset the customer CCC
	 * status for non borrower
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		result = super.postProcess(result);

		ICheckListTrxValue trxValue = (ICheckListTrxValue) result.getTrxValue();
		ICheckList staging = trxValue.getStagingCheckList();

		if (staging.getCheckListOwner() instanceof ICCCheckListOwner) {
			ICCCheckListOwner owner = (ICCCheckListOwner) staging.getCheckListOwner();
			if ((owner.getSubOwnerID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
					&& (owner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_NON_BORROWER))) {
				super.updateCustomerStatus(owner.getSubOwnerID(), ICMSConstant.STATE_ACTIVE);
			}
		}
		return result;
	}
}