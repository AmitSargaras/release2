/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/trx/ReadCustomerIDOperation.java,v 1.4 2003/09/28 13:55:17 slong Exp $
 */
package com.integrosys.cms.app.customer.trx;

//ofa
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for the creation of a customer doc transaction
 * 
 * @author $Author: slong $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/28 13:55:17 $ Tag: $Name: $
 */
public class ReadCustomerIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCustomerIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CUSTOMER_ID;
	}

	/**
	 * This method is used to read a transaction object
	 * 
	 * @param val is the ITrxValue object containing the parameters required for
	 *        retrieving a record, such as the transaction ID.
	 * @return ITrxValue containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		ICMSTrxValue trxValue = super.getCMSTrxValue(val);
		DefaultLogger.debug(this, " THE Reference is :" + trxValue.getReferenceID());
		try {
			trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(),
					ICMSConstant.INSTANCE_CUSTOMER);
		}
		catch (RemoteException e) {
			throw new TrxOperationException("failed to retrieve customer transaction by reference id ["
					+ trxValue.getReferenceID() + "] and transaction type [" + ICMSConstant.INSTANCE_CUSTOMER
					+ "] using transaction remote interface; throwing root cause ", e.getCause());
		}

		OBCMSCustomerTrxValue newValue = new OBCMSCustomerTrxValue(trxValue);

		String actualRef = trxValue.getReferenceID();

		if (null != actualRef) {
			SBCustomerManager mgr = getSBCustomerManager();
			try {
				newValue.setCustomer(mgr.getCustomer(Long.parseLong(actualRef)));
			}
			catch (RemoteException e) {
				throw new TrxOperationException("failed to retrieve customer using primary key [" + actualRef
						+ "] using transaction remote interface; throwing root cause ", e.getCause());
			}
		}

		return newValue;
	}

	/**
	 * Get the SBCustomerManager remote interface
	 * 
	 * @return SBCustomerManager
	 */
	private SBCustomerManager getSBCustomerManager() throws TransactionException {
		SBCustomerManager home = (SBCustomerManager) BeanController.getEJB(ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI,
				SBCustomerManagerHome.class.getName());
		if (null != home) {
			return home;
		}
		else {
			throw new TransactionException("SBCustomerManager is null!");
		}
	}
}