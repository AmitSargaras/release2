
package com.integrosys.cms.app.creditApproval.trx;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to update a checklist
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 1.0 $
 * @since $Date: 2011/04/07 02:49:03 $ Tag: $Name: $
 */
public class MakerCloseDraftOperation extends AbstractCreditApprovalTrxOperation {

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
		return ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CREDIT_APPROVAL;
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
		ICreditApprovalTrxValue trxValue = super.getCreditApprovalTrxValue(anITrxValue);
		trxValue = updateCreditApprovalTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
