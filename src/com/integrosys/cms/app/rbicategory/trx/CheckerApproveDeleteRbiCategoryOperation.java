package com.integrosys.cms.app.rbicategory.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;

/**
 * @author Govind.Sahu
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveDeleteRbiCategoryOperation extends AbstractRbiCategoryTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveDeleteRbiCategoryOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_RBI_CATEGORY;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IRbiCategoryTrxValue trxValue = getRbiCategoryTrxValue(anITrxValue);
		trxValue = deleteRbiCategory(trxValue);
		trxValue = updateRbiCategoryTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IRbiCategoryTrxValue deleteRbiCategory(IRbiCategoryTrxValue anICCRbiCategoryTrxValue)
			throws TrxOperationException {
		try {
			IRbiCategory staging = anICCRbiCategoryTrxValue.getStagingRbiCategory();
			IRbiCategory actual = anICCRbiCategoryTrxValue.getRbiCategory();

			IRbiCategory updatedRbiCategory = getRbiCategoryBusManager().deleteRbiCategory(actual);
			anICCRbiCategoryTrxValue.setRbiCategory(updatedRbiCategory);

			return anICCRbiCategoryTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
