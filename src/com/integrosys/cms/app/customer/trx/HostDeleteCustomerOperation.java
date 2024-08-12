/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/trx/HostDeleteCustomerOperation.java,v 1.1 2004/02/10 10:23:28 lyng Exp $
 */
package com.integrosys.cms.app.customer.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows host to delete the customer
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/10 10:23:28 $ Tag: $Name: $
 */
public class HostDeleteCustomerOperation extends AbstractCustomerTrxOperation {
	/**
	 * Default Constructor.
	 */
	public HostDeleteCustomerOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_HOST_DELETE_CUSTOMER;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICMSCustomerTrxValue trxValue = super.getCustomerTrxValue(value);
		
		trxValue = super.createStagingCustomer(trxValue);
		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);
	}
}