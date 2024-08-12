/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/CreateLimitProfileOperation.java,v 1.7 2003/09/28 13:55:17 slong Exp $
 */
package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
 * This operation allows a create of limit profile
 * 
 * @author $Author: slong $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/09/28 13:55:17 $ Tag: $Name: $
 */
public class CreateLimitProfileOperation extends AbstractLimitProfileTrxOperation {

	private static final long serialVersionUID = -8830877412333633470L;

	/**
	 * Defaulc Constructor
	 */
	public CreateLimitProfileOperation() {
		super();
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * customer transaction ID to be appended as trx parent ref
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		value = super.preProcess(value);

		// get the customer ID trx to be appended to this trx as a parent trx
		ILimitProfileTrxValue trxValue = getLimitProfileTrxValue(value);

		// get from staging limit profile
		ILimitProfile profile = trxValue.getStagingLimitProfile();

		// set the create date
		profile.setBCACreateDate(DateUtil.getDate());

		long customerID = profile.getCustomerID();
		if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == customerID) {
			throw new TrxOperationException("Invalid customer id [" + customerID
					+ "] found for the limit profile, id [" + profile.getLimitProfileID() + "], aa ["
					+ profile.getBCAReference() + "]");
		}

		try {
			ICustomerProxy proxy = CustomerProxyFactory.getProxy();
			ICMSCustomerTrxValue custTrx = proxy.getTrxCustomer(customerID);
			trxValue.setTrxReferenceID(custTrx.getTransactionID());

			return trxValue;
		}
		catch (CustomerException e) {
			throw new TrxOperationException("Failed to retrieve customer workflow object using customer id ["
					+ customerID + "]", e);
		}
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CREATE_LIMIT_PROFILE;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Create Staging Record 2. Create Actual Record 3. Create Transaction
	 * Record
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ILimitProfileTrxValue trxValue = super.getLimitProfileTrxValue(value);

		trxValue = super.createTransaction(trxValue);

		return super.prepareResult(trxValue);
	}
}