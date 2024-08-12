
package com.integrosys.cms.app.creditApproval.trx;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to submit an CreditApproval It is the same as
 * the MakerUpdateOperation except that it transits to a different state and
 * routed to checker for approval, both of which is handled by transaction
 * manager As such, this class just returns a different operation name.
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 1.0 $
 * @since $Date: 2011/04/06 11:10:08 $ Tag: $Name: $
 */
public class MakerSubmitOperation extends MakerUpdateOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerSubmitOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SUBMIT_CREDIT_APPROVAL;
	}
	
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		Validate.notNull(value, "transaction value must not be null");

		ICreditApprovalTrxValue trxValue = (ICreditApprovalTrxValue) value;
		trxValue = super.createStagingCreditApproval(trxValue);

		if (StringUtils.isNotBlank(trxValue.getTransactionID())) {
			trxValue = super.updateCreditApprovalTransaction(trxValue);
		}
		else {
			trxValue = super.createTransaction(trxValue);
		}

		return super.prepareResult(trxValue);
	}
	
	

}