/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/proxy/CustomerProxyImpl.java,v 1.12 2006/09/07 10:23:28 jychong Exp $
 */
package com.integrosys.cms.app.customer.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.GroupSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class defines the services that are available in for use in the
 * interaction with Customer and Legal Entity.
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.12 $
 * @since $Date: 2006/09/07 10:23:28 $ Tag: $Name: $
 */
public class CustomerProxyImpl implements ICustomerProxy {
	/**
	 * Create a customer record, without dual control.
	 * 
	 * @param value is of type ICMSCustomer
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult createCustomer(ICMSCustomer value) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.createCustomer(value);
			
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer; throwing root cause", e.getCause());
		}
	}

	/**
	 * Create a customer record, without dual control.
	 * 
	 * @param value is of type ICMSCustomerTrxValue
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue createCustomer(ICMSCustomerTrxValue value ) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.createCustomer(value);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}
	
	public ICMSCustomerTrxValue createCustomerBrmaker(ICMSCustomerTrxValue value , String loginUser) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.createCustomerBrmaker(value,loginUser);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}
	
	public ICMSCustomerTrxValue createDraftCustomer(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.createDraftCustomer(value);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}
	public ICMSCustomerTrxValue createDraftCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.createDraftCustomerBrmaker(value);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}
	
	public ICMSCustomerTrxValue saveCustomer(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.saveCustomer(value);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}
	public ICMSCustomerTrxValue saveCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.saveCustomerBrmaker(value);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}
	
	public ICMSCustomerTrxValue saveEditCustomer(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.saveEditCustomer(value);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}
	
	public ICMSCustomerTrxValue makerResubmitCreateCustomer(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.makerResubmitCreateCustomer(value);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}
	
	public ICMSCustomerTrxValue makerResubmitCreateCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.makerResubmitCreateCustomerBrmaker(value);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}
	/**
	 * Update a Customer given a customer ob
	 * 
	 * @param aCMSCustomer is of type ICMSCustomer
	 * @return ICMSCustomer if it can be found, or null if the customer does not
	 *         exist.
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer updateCustomer(ICMSCustomer aCMSCustomer) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.updateCustomer(aCMSCustomer);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to update customer via workflow; throwing root cause", e.getCause());
		}
	}

	/**
	 * Get a Customer given a customer's ID
	 * 
	 * @param customerID is of type long
	 * @return ICMSCustomer if it can be found, or null if the customer does not
	 *         exist.
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomer(long customerID) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.getCustomer(customerID);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve customer, customer id [" + customerID
					+ "]; throwing root cause", e.getCause());
		}
	}
	
	//sandeep shinde
	
	public ICMSCustomerTrxValue updateCustomer(ICMSCustomerTrxValue aCMSCustomer) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.updateCustomer(aCMSCustomer);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to update customer via workflow; throwing root cause", e.getCause());
		}
	}

	/**
	 * Get a customer transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ICMSCustomerTrxValue
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue getTrxCustomer(String trxID) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.getTrxCustomer(trxID);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve customer from workflow, trx id [" + trxID
					+ "]; throwing root cause", e.getCause());
		}
	}

	/**
	 * Get a Customer Transaction value given a customer's ID
	 * 
	 * @param customerID is of type long
	 * @return ICMSCustomerTrxValue if it can be found, or null if the customer
	 *         does not exist.
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue getTrxCustomer(long customerID) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.getTrxCustomer(customerID);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve customer from workflow, customer id [" + customerID
					+ "]; throwing root cause", e.getCause());
		}
	}

	/**
	 * Search customer
	 * 
	 * @param criteria is of type CustomerSearchCriteria
	 * @return SearchResult containing ICustomerSearchResult objects
	 * @throws SearchDAOException on errors
	 */
	public SearchResult searchCustomer(CustomerSearchCriteria criteria) throws SearchDAOException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.searchCustomer(criteria);
		}
		catch (RemoteException e) {
			throw new SearchDAOException("Failed to search customer; throwing root cause", e.getCause());
		}
	}
	
	public SearchResult searchCustomerImageUpload(CustomerSearchCriteria criteria) throws SearchDAOException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.searchCustomerImageUpload(criteria);
		}
		catch (RemoteException e) {
			throw new SearchDAOException("Failed to search customer; throwing root cause", e.getCause());
		}
	}

	/**
     * Search Customer Information Only
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException on errors
     */
    public SearchResult searchCustomerInfoOnly(CustomerSearchCriteria criteria) throws SearchDAOException {
        try {
            SBCustomerProxy proxy = getProxy();
            return proxy.searchCustomerInfoOnly(criteria);
        }
        catch (SearchDAOException e) {
            throw e;
        }
        catch (Exception e) {
            throw new SearchDAOException("Caught Exception!", e);
        }
    }

    /**
	 * Search group
	 * 
	 * @param criteria is of type GroupSearchCriteria
	 * @return SearchResult containing IGroupSearchResult objects
	 * @throws SearchDAOException on errors
	 */
	public SearchResult searchGroup(GroupSearchCriteria criteria) throws SearchDAOException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.searchGroup(criteria);
		}
		catch (RemoteException e) {
			throw new SearchDAOException("Failed to search group; throwing root cause", e.getCause());
		}
	}

	/**
	 * Get map of mailing details for a list of limit profile IDs. One official
	 * address per limit profile ID if available.
	 * 
	 * @param sciLimitProfileIDList - List
	 * @return Map - (limitProfileID, OBCustomerMailingDetails)
	 * @throws SearchDAOException if errors
	 */
	public Map getCustomerMailingDetails(List sciLimitProfileIDList) throws SearchDAOException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.getCustomerMailingDetails(sciLimitProfileIDList);
		}
		catch (RemoteException e) {
			throw new SearchDAOException(
					"Failed to retrieve customer mailing details using sci limit profile id list; "
							+ "throwing root cause", e.getCause());
		}
	}

	public Map getFamcodeCustNameByCustomer(List sciLimitProfileIDList) throws SearchDAOException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.getFamcodeCustNameByCustomer(sciLimitProfileIDList);
		}
		catch (RemoteException e) {
			throw new SearchDAOException(
					"Failed to retrieve fam code and customer name using sci limit profile id list; "
							+ "throwing root cause", e.getCause());
		}
	}

	/**
	 * Allows the host to update the customer details
	 * 
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult hostUpdateCustomer(ICMSCustomerTrxValue trxValue) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.hostUpdateCustomer(trxValue);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to update customer from host via workflow; throwing root cause", e
					.getCause());
		}
	}

	/**
	 * Allows the host to delete the customer details
	 * 
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult hostDeleteCustomer(ICMSCustomerTrxValue trxValue) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.hostDeleteCustomer(trxValue);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to delete customer from host via workflow; throwing root cause", e
					.getCause());
		}
	}

	/**
	 * Maker update customer details
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @param customer is the ICMSCustomer object to be updated
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult makerUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
			throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.makerUpdateCustomer(context, trxValue, customer);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to update customer via workflow; throwing root cause", e.getCause());
		}
	}

	
	public ICMSTrxResult makerUpdateDraftCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
	throws CustomerException {
try {
	SBCustomerProxy proxy = getProxy();
	return proxy.makerUpdateDraftCustomer(context, trxValue, customer);
}
catch (RemoteException e) {
	throw new CustomerException("Failed to update customer via workflow; throwing root cause", e.getCause());
}
}
	
	public ICMSTrxResult makerEditDraftCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
	throws CustomerException {
try {
	SBCustomerProxy proxy = getProxy();
	return proxy.makerEditDraftCustomer(context, trxValue, customer);
}
catch (RemoteException e) {
	throw new CustomerException("Failed to update customer via workflow; throwing root cause", e.getCause());
}
}
	/**
	 * Maker Resubmit customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @param customer is the ICMSCustomer object to be updated
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult makerResubmitUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue,
			ICMSCustomer customer) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.makerResubmitUpdateCustomer(context, trxValue, customer);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to re submit customer update via workflow; throwing root cause", e
					.getCause());
		}
	}

	/**
	 * Maker Cancel customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult makerCancelUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.makerCancelUpdateCustomer(context, trxValue);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to cancel customer update via workflow; throwing root cause", e
					.getCause());
		}
	}

	/**
	 * Checker Approve customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	
	/*	@return ICMSTrxResult have been changed to ICMSCustomerTrxValue by Sandeep Shinde on 30-11-2011	*/
	
	public ICMSCustomerTrxValue checkerApproveUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.checkerApproveUpdateCustomer(context, trxValue);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to approve customer update via workflow; throwing root cause", e
					.getCause());
		}
	}

	/**
	 * Checker Reject customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	
	/*	@return ICMSTrxResult have been changed to ICMSCustomerTrxValue by Sandeep Shinde on 30-11-2011	*/
	
	public ICMSCustomerTrxValue checkerRejectUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.checkerRejectUpdateCustomer(context, trxValue);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to reject customer update via workflow; throwing root cause", e
					.getCause());
		}
	}

	/**
	 * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public long searchCustomerID(long leid, long subProfileID) throws CustomerException, SearchDAOException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.searchCustomerID(leid, subProfileID);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to search customer id, using le id [" + leid + "], sub profile id ["
					+ subProfileID + "]; throwing root cause", e.getCause());
		}
	}

	/**
	 * Retrieve the CMS Customer ID, given the CIF and Source system id If not
	 * found in local DB, will trigger search to host.
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomerByCIFSource(String cifNumber, String sourceSystemId) throws CustomerException,
			SearchDAOException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.getCustomerByCIFSource(cifNumber, sourceSystemId);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve customer via host, using cif [" + cifNumber
					+ "], source system id [" + sourceSystemId + "]; throwing root cause", e.getCause());
		}
	}

	public List getCustomerByCIFSource(String cifNumber, String sourceSystemId, String partyName, String partyId) throws CustomerException,
	SearchDAOException {
try {
	SBCustomerProxy proxy = getProxy();
	List cust = proxy.getCustomerByCIFSource(cifNumber, sourceSystemId, partyName,partyId);
	return cust;
}
catch (RemoteException e) {
	throw new CustomerException("Failed to retrieve customer via host, using cif [" + cifNumber
			+ "], source system id [" + sourceSystemId + "]; throwing root cause", e.getCause());
}
}
	
	/**
	 * Retrieve the CMS Customer ID, given the CIF and Source system id
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomerByCIFSourceFromDB(String cifNumber, String sourceSystemId) throws CustomerException,
			SearchDAOException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.getCustomerByCIFSourceFromDB(cifNumber, sourceSystemId);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve customer, using cif [" + cifNumber
					+ "], source system id [" + sourceSystemId + "]; throwing root cause", e.getCause());
		}
	}

	public ArrayList getMBlistByCBleId(long cbLeId) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.getMBlistByCBleId(cbLeId);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve main borrower list by co borrower le id [" + cbLeId
					+ "]; throwing root cause", e.getCause());
		}

	}

	/**
	 * Get a Customer given a customer's ID
	 * 
	 * @param customerID is of type long
	 * @return ICMSCustomer if it can be found, or null if the customer does not
	 *         exist.
	 * @throws CustomerException on errors
	 * 
	 *         public ICMSCustomer getCustomer(long customerID) throws
	 *         CustomerException;
	 */

	// **************** Private Method ************
	/**
	 * Get the SB reference proxy bean
	 * 
	 * @return SBCustomerProxyBean
	 * @throws CustomerException on error
	 */
	private SBCustomerProxy getProxy() throws CustomerException {
		SBCustomerProxy home = (SBCustomerProxy) BeanController.getEJB(ICMSJNDIConstant.SB_CUSTOMER_PROXY_JNDI,
				SBCustomerProxyHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("Failed to retrieve customerproxy remote home using jndi name ["
					+ ICMSJNDIConstant.SB_CUSTOMER_PROXY_JNDI + "]");
		}
	}

	public List getJointBorrowerList(long limitProfileId) throws Exception {
		return getProxy().getJointBorrowerList(limitProfileId);
	}

    public List getOnlyJointBorrowerList(long limitProfileId) throws Exception {
        return getProxy().getOnlyJointBorrowerList(limitProfileId);
    }

    public SBCustomerManager getSBCustomerManager() throws Exception {
        SBCustomerManager remote = (SBCustomerManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI, SBCustomerManagerHome.class.getName());
	    return remote;
	}

    /**
     * Method Added by Sandeep Shinde on 17-03-2011 so as to avoid Maker-Checker Operation
     * @param aCMSCustomer
     * @return ICMSCustomer
     * @throws CustomerException
     * @throws RemoteException
     */
    
	public ICMSCustomer createCustomerInfo(ICMSCustomer aCMSCustomer)
			throws CustomerException, RemoteException {
		DefaultLogger.debug(this,"Inside CustomerProxyImpl");
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.createCustomerInfo(aCMSCustomer);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}

	public void deleteCustomerInfo(ICMSCustomer aCMSCustomer)throws CustomerException ,RemoteException{
		DefaultLogger.debug(this,"Inside CustomerProxyImpl");
		try {
			SBCustomerProxy proxy = getProxy();
			proxy.deleteCustomerInfo(aCMSCustomer);
		}
		catch (CustomerException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}		
	}

	 /**
     * Following Method Added by Sandeep Shinde on 29-03-2011 for purpose of Maker-Checker Operation
     * @param aCMSCustomer
     * @return ICMSCustomer
     * @throws CustomerException
     * @throws RemoteException
     */
	
	public ICMSCustomerTrxValue checkerApproveCreateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException {
		DefaultLogger.debug(this,"Inside CustomerProxyImpl");
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.checkerApproveCreateCustomer(context, trxValue);
		}
		catch (CustomerException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
/*		catch (TrxParameterException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
		catch (TransactionException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
*/	}

	public ICMSCustomerTrxValue checkerRejectCreateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException{
		DefaultLogger.debug(this,"Inside CustomerProxyImpl");
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.checkerRejectCreateCustomer(context, trxValue);
		}
		catch (CustomerException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
/*		catch (TrxParameterException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
		catch (TransactionException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
*/	}

	//shinde 
	public ICMSCustomerTrxValue getCustomerTrxValue(long id)throws CustomerException, RemoteException {
		// TODO Auto-generated method stub
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.getCustomerTrxValue(id);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve customer, customer id [" + id
					+ "]; throwing root cause", e.getCause());
		}
	}

	public ICMSCustomerTrxValue checkerApproveDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.checkerApproveDeleteCustomer(context, trxValue);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to approve customer update via workflow; throwing root cause", e
					.getCause());
		}
	}

	public ICMSCustomerTrxValue checkerRejectDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.checkerRejectDeleteCustomer(context, trxValue);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to reject customer update via workflow; throwing root cause", e
					.getCause());
		}		
	}

	public ICMSTrxResult prepareDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer) throws CustomerException,RemoteException {

		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.prepareDeleteCustomer(context, trxValue, customer);
		}
		catch (CustomerException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}	
	}
	
	public ICMSTrxResult makerResubmitDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer) throws CustomerException,RemoteException {

		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.makerResubmitDeleteCustomer(context, trxValue, customer);
		}
		catch (CustomerException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}	
	}

	public ICMSCustomerTrxValue makerCloseRejectedCustomer(ITrxContext context,
			ICMSCustomerTrxValue trxValue) throws CustomerException,
			RemoteException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.makerCloseRejectedCustomer(context, trxValue);
		}
		catch (CustomerException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}	
		
		
	}

	public ICMSCustomerTrxValue makerCloseDraftCustomer(ITrxContext context,
			ICMSCustomerTrxValue trxValue) throws CustomerException,
			RemoteException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.makerCloseDraftCustomer(context, trxValue);
		}
		catch (CustomerException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}	
		
		
	}


	public ICMSCustomer getCustomerByLimitProfileId(long limitProfileId) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return proxy.getCustomerByLimitProfileId(limitProfileId);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve customer, limitProfileId [" + limitProfileId
					+ "]; throwing root cause", e.getCause());
		}
	}

	public ICMSCustomerTrxValue createCustomerWithApprovalThroughWsdl(ITrxContext context, ICMSCustomerTrxValue trxValue) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.createCustomerWithApprovalThroughWsdl(context,trxValue);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}
	public ICMSCustomerTrxValue updateCustomerWithApprovalThroughWsdl(ITrxContext context, ICMSCustomerTrxValue trxValue,ICMSCustomer obCMSCustomer) throws CustomerException {
		try {
			SBCustomerProxy proxy = getProxy();
			return (ICMSCustomerTrxValue) proxy.updateCustomerWithApprovalThroughWsdl(context, trxValue, obCMSCustomer);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to create customer via workflow; throwing root cause", e.getCause());
		}
	}

}