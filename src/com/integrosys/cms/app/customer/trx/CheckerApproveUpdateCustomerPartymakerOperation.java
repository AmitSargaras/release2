/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/trx/CheckerApproveUpdateCustomerOperation.java,v 1.3 2003/07/23 07:49:31 kllee Exp $
 */
package com.integrosys.cms.app.customer.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve a customer update
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/23 07:49:31 $ Tag: $Name: $
 */
public class CheckerApproveUpdateCustomerPartymakerOperation extends AbstractCustomerTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateCustomerPartymakerOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER_PARTY;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update Actual customer record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICMSCustomerTrxValue trxValue = super.getCustomerTrxValue(value);

		trxValue = super.updateActualCustomer(trxValue);
		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);
	}
}