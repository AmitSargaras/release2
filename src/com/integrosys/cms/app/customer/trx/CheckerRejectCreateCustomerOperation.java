package com.integrosys.cms.app.customer.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 29-03-2011
	 *
	 */

public class CheckerRejectCreateCustomerOperation extends AbstractCustomerTrxOperation{

	public CheckerRejectCreateCustomerOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	
	public String getOperationName() {		
		return ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTOMER;
	}

	/**
	 * The Tasks of this method is to  
	 * Create a Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICMSCustomerTrxValue trxValue = super.getCustomerTrxValue(value);

//		trxValue = super.createTransaction(trxValue);
		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);
	}

}
