/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/trx/AbstractCustomerTrxOperation.java,v 1.6 2003/07/23 07:49:31 kllee Exp $
 */
package com.integrosys.cms.app.customer.trx;

//ofa
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of customer
 * trx operations
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/07/23 07:49:31 $ Tag: $Name: $
 */
public abstract class AbstractCustomerTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * Create Actual Customer Record
	 * 
	 * @param value is of type ICMSCustomerTrxValue
	 * @return ICMSCustomerTrxValue
	 * @throws TransactionException on errors
	 */
	protected ICMSCustomerTrxValue createActualCustomer(ICMSCustomerTrxValue value) throws TrxOperationException {
		ICMSCustomer cust = value.getStagingCustomer();
		
		SBCustomerManager mgr = getActualCustomerManager();
		try {
			cust = mgr.createCustomer(cust);
		}
		catch (RemoteException e) {
			throw new TrxOperationException("failed to create customer record using staging copy, transaction id ["
					+ value.getTransactionID() + "] using transaction remote interface; throwing root cause ", e
					.getCause());
		}

		value.setCustomer(cust); // set into actual
		return value;
	}

	/**
	 * Create Staging Customer Record
	 * 
	 * @param value is of type ICMSCustomerTrxValue
	 * @return ICMSCustomerTrxValue
	 * @throws TransactionException on errors
	 */
	protected ICMSCustomerTrxValue createStagingCustomer(ICMSCustomerTrxValue value) throws TrxOperationException {
		ICMSCustomer cust = value.getStagingCustomer(); // create get from
		// staging
		
		SBCustomerManager mgr = getStagingCustomerManager();
		try {
			cust = mgr.createCustomer(cust);
		}
		catch (RemoteException e) {
			throw new TrxOperationException("failed to create staging customer record, customer name ["
					+ cust.getCustomerName() + "] using transaction remote interface; throwing root cause ", e
					.getCause());
		}

		value.setStagingCustomer(cust); // set into staging
		return value;
	}

	/**
	 * Update Actual Customer Record
	 * 
	 * @param value is of type ICMSCustomerTrxValue
	 * @return ICMSCustomerTrxValue
	 * @throws TransactionException on errors
	 */
	protected ICMSCustomerTrxValue updateActualCustomer(ICMSCustomerTrxValue value) throws TrxOperationException {
		ICMSCustomer actual = value.getCustomer();
		ICMSCustomer cust = value.getStagingCustomer(); 
		String stageId = String.valueOf(cust.getCustomerID());
		// staging		
		cust.setCustomerID(actual.getCustomerID()); // but maintain actual's
		// pk
		
		//for edit customer new entry was inserted into main profile table ----added by bharat
		cust.getCMSLegalEntity().setLEID(actual.getCMSLegalEntity().getLEID());
		cust.setVersionTime(actual.getVersionTime()); // and actual's
		// version time

		// note: cust is by reference and subsequently we are going to
		// create staging again. however
		// there's no need to set back cust's old values becasue staging is
		// always created, not updated

		SBCustomerManager mgr = getActualCustomerManager();
		try {
			cust = mgr.updateCustomer(cust);
		}
		catch (RemoteException e) {
			throw new TrxOperationException("failed to update customer record, transaction id ["
					+ value.getTransactionID() + "] using transaction remote interface; throwing root cause ", e
					.getCause());
		}

		
		value.setCustomer(cust); 
		ICMSCustomer customer = value.getStagingCustomer(); //added by bharat 13/11/2011
		customer.setCustomerID(Long.parseLong(stageId));
		value.setStagingCustomer(customer);
		return value;
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ICMSCustomerTrxValue
	 * @return ICMSCustomerTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ICMSCustomerTrxValue createTransaction(ICMSCustomerTrxValue value) throws TrxOperationException {
		value = prepareTrxValue(value);

		ICMSTrxValue tempValue = super.createTransaction((ICMSTrxValue) value);

		OBCMSCustomerTrxValue newValue = new OBCMSCustomerTrxValue(tempValue);
		newValue.setCustomer(value.getCustomer());
		newValue.setStagingCustomer(value.getStagingCustomer());
		return newValue;
	}

	/**
	 * Method to update a transaction record
	 * 
	 * @param value is of type ICMSCustomerTrxValue
	 * @return ICMSCustomerTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ICMSCustomerTrxValue updateTransaction(ICMSCustomerTrxValue value) throws TrxOperationException {
		value = prepareTrxValue(value);

		ICMSTrxValue tempValue = super.updateTransaction((ICMSTrxValue) value);

		OBCMSCustomerTrxValue newValue = new OBCMSCustomerTrxValue(tempValue);
		newValue.setCustomer(value.getCustomer());
		newValue.setStagingCustomer(value.getStagingCustomer());
		return newValue;
	}

	/**
	 * Get the SB for the actual storage of Customer
	 * 
	 * @return SBCustomerManager
	 * @throws TransactionException on errors
	 */
	protected SBCustomerManager getActualCustomerManager() throws TrxOperationException {
		SBCustomerManager home = (SBCustomerManager) BeanController.getEJB(ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI,
				SBCustomerManagerHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new TrxOperationException("SBCustomerManager for Actual is null!");
		}
	}

	/**
	 * Get the SB for the actual storage of Customer
	 * 
	 * @return SBCustomerManager
	 * @throws TransactionException on errors
	 */
	protected SBCustomerManager getStagingCustomerManager() throws TrxOperationException {
		SBCustomerManager home = (SBCustomerManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI_STAGING, SBCustomerManagerHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new TrxOperationException("SBCustomerManager for Staging is null!");
		}
	}

	/**
	 * Convert a ITrxValue into a ICMSCustomerTrxValue object
	 * 
	 * @param value is of type ITrxValue
	 * @return ICMSCustomerTrxValue
	 * @throws TransactionException on errors
	 */
	protected ICMSCustomerTrxValue getCustomerTrxValue(ITrxValue value) throws TrxOperationException {
		ICMSCustomerTrxValue trxValue = (ICMSCustomerTrxValue) value;
		return trxValue;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ICMSCustomerTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ICMSCustomerTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	// **************** Private Methods
	/**
	 * Prepares a trx object
	 */
	private ICMSCustomerTrxValue prepareTrxValue(ICMSCustomerTrxValue value) {
		if (null != value) {
			ICMSCustomer actual = value.getCustomer();
			ICMSCustomer staging = value.getStagingCustomer();
			if (null != actual) {
				value.setReferenceID(String.valueOf(actual.getCustomerID()));
			}
			else {
				value.setReferenceID(null);
			}
			if (null != staging) {
				value.setStagingReferenceID(String.valueOf(staging.getCustomerID()));
			}
			else {
				value.setStagingReferenceID(null);
			}
			return value;
		}
		else {
			return null;
		}
	}
	
	/* 	Method Added by Sandeep Shinde on 06-04-2011 for Update When a customer operation is closed on Reject*/
	
	   protected ICMSCustomerTrxValue updateCustomerTrx(ICMSCustomerTrxValue customerTrxValue) throws TrxOperationException {
	        try {
	        	customerTrxValue = prepareTrxValue(customerTrxValue);
	            ICMSTrxValue tempValue = super.updateTransaction(customerTrxValue);
	            OBCMSCustomerTrxValue newValue = new OBCMSCustomerTrxValue(tempValue);
	            newValue.setCustomer(customerTrxValue.getCustomer());
	            newValue.setStagingCustomer(customerTrxValue.getStagingCustomer());
	            return newValue;
	        }
	        
	        catch (Exception ex) {
	            throw new TrxOperationException("General Exception: " + ex.toString());
	        }
	    }
}