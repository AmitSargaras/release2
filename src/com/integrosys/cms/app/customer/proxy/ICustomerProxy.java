/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/proxy/ICustomerProxy.java,v 1.12 2006/09/07 10:23:28 jychong Exp $
 */
package com.integrosys.cms.app.customer.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.GroupSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the services that are available in for use in the
 * interaction with Customer and Legal Entity.
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.12 $
 * @since $Date: 2006/09/07 10:23:28 $ Tag: $Name: $
 */
public interface ICustomerProxy extends java.io.Serializable {
	/**
	 * Create a customer record, without dual control.
	 * 
	 * @param value is of type ICMSCustomer
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult createCustomer(ICMSCustomer value) throws CustomerException;

	/**
	 * Create a customer record, without dual control.
	 * 
	 * @param value is of type ICMSCustomerTrxValue
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue createCustomer(ICMSCustomerTrxValue value) throws CustomerException;//changed
	
	public ICMSCustomerTrxValue createCustomerBrmaker(ICMSCustomerTrxValue value , String loginUser) throws CustomerException;//changed
	
	public ICMSCustomerTrxValue createDraftCustomer(ICMSCustomerTrxValue value) throws CustomerException;//changed
	
	public ICMSCustomerTrxValue createDraftCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException;//changed
	
	public ICMSCustomerTrxValue saveCustomer(ICMSCustomerTrxValue value) throws CustomerException;//changed	
	
	public ICMSCustomerTrxValue saveCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException;//changed	
	
	public ICMSCustomerTrxValue saveEditCustomer(ICMSCustomerTrxValue value) throws CustomerException;//changed	
	
	public ICMSCustomerTrxValue makerResubmitCreateCustomer(ICMSCustomerTrxValue value) throws CustomerException;//changed	
	
	public ICMSCustomerTrxValue makerResubmitCreateCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException;//changed	
	/**
	 * Update a Customer given a customer ob
	 * 
	 * @param aCMSCustomer is of type ICMSCustomer
	 * @return ICMSCustomer if it can be found, or null if the customer does not
	 *         exist.
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer updateCustomer(ICMSCustomer aCMSCustomer) throws CustomerException;

	/**
	 * Get a Customer given a customer's ID
	 * 
	 * @param customerID is of type long
	 * @return ICMSCustomer if it can be found, or null if the customer does not
	 *         exist.
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomer(long customerID) throws CustomerException;

	/**
	 * Get a customer transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ICMSCustomerTrxValue
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue getTrxCustomer(String trxID) throws CustomerException;

	/**
	 * Get a Customer Transaction value given a customer's ID
	 * 
	 * @param customerID is of type long
	 * @return ICMSCustomerTrxValue if it can be found, or null if the customer
	 *         does not exist.
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue getTrxCustomer(long customerID) throws CustomerException;

	/**
	 * Search customer
	 * 
	 * @param criteria is of type CustomerSearchCriteria
	 * @return SearchResult containing ICustomerSearchResult objects
	 * @throws SearchDAOException on errors
	 */
	public SearchResult searchCustomer(CustomerSearchCriteria criteria) throws SearchDAOException;

	/**
     * Search Customer Information Only
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException on errors
     */
     public SearchResult searchCustomerInfoOnly(CustomerSearchCriteria criteria) throws SearchDAOException;

    /**
	 * Search group
	 * 
	 * @param criteria is of type GroupSearchCriteria
	 * @return SearchResult containing IGroupSearchResult objects
	 * @throws SearchDAOException on errors
	 */
	public SearchResult searchGroup(GroupSearchCriteria criteria) throws SearchDAOException;

	/**
	 * Get map of mailing details for a list of limit profile IDs. One official
	 * address per limit profile ID if available.
	 * 
	 * @param sciLimitProfileIDList - List
	 * @return Map - (limitProfileID, OBCustomerMailingDetails)
	 * @throws SearchDAOException if errors
	 */
	public Map getCustomerMailingDetails(List sciLimitProfileIDList) throws SearchDAOException;

	public Map getFamcodeCustNameByCustomer(List sciLimitProfileIDList) throws SearchDAOException;

	/**
	 * Allows the host to update the customer details
	 * 
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult hostUpdateCustomer(ICMSCustomerTrxValue trxValue) throws CustomerException;

	/**
	 * Allows the host to delete the customer details
	 * 
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult hostDeleteCustomer(ICMSCustomerTrxValue trxValue) throws CustomerException;

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
	//changed
	public ICMSTrxResult makerUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
			throws CustomerException;
	
	public ICMSTrxResult makerUpdateDraftCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
	throws CustomerException;
	
	public ICMSTrxResult makerEditDraftCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
	throws CustomerException;

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
			ICMSCustomer customer) throws CustomerException;

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
			throws CustomerException;

	/**
	 * Checker Approve customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue checkerApproveUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException;

	/**
	 * Checker Reject customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue checkerRejectUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException;

	/**
	 * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public long searchCustomerID(long leid, long subProfileID) throws CustomerException, SearchDAOException;

	/**
	 * Retrieve the CMS Customer ID, given the CIF and Source system id If not
	 * found in local DB, will trigger search to host.
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomerByCIFSource(String cifNumber, String sourceSystemId) throws CustomerException,
			SearchDAOException;

	
	public List getCustomerByCIFSource(String cifNumber, String sourceSystemId,String partyName, String partyId) throws CustomerException,
	SearchDAOException;
	/**
	 * Retrieve the CMS Customer ID, given the CIF and Source system id
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomerByCIFSourceFromDB(String cifNumber, String sourceSystemId) throws CustomerException,
			SearchDAOException;

	public ArrayList getMBlistByCBleId(long cbLeId) throws CustomerException;

	public List getJointBorrowerList(long limitProfileId) throws Exception;
    
    public List getOnlyJointBorrowerList(long limitProfileId) throws Exception;

    public SBCustomerManager getSBCustomerManager()throws Exception;
    
    /**
     * Method Added by Sandeep Shinde on 17-03-2011 so as to avoid Maker-Checker Operation
     * @param aCMSCustomer
     * @return ICMSCustomer
     * @throws CustomerException
     * @throws RemoteException
     */
    
    public ICMSCustomer createCustomerInfo(ICMSCustomer aCMSCustomer) throws CustomerException, RemoteException;
    
    public void deleteCustomerInfo(ICMSCustomer aCMSCustomer) throws CustomerException,RemoteException;

    /**
     * Following Method Added by Sandeep Shinde on 29-03-2011 for purpose of Maker-Checker Operation
     * @param aCMSCustomer
     * @return ICMSCustomer
     * @throws CustomerException
     * @throws RemoteException
     */
    
    public ICMSCustomerTrxValue checkerApproveCreateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException ;
    
    public ICMSCustomerTrxValue checkerRejectCreateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException;
        
    public ICMSCustomerTrxValue updateCustomer(ICMSCustomerTrxValue aCMSCustomer) throws CustomerException,RemoteException;
    
    public ICMSCustomerTrxValue getCustomerTrxValue(long id)throws CustomerException,RemoteException;

    public ICMSTrxResult prepareDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)throws CustomerException,RemoteException;
    
    public ICMSTrxResult makerResubmitDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)throws CustomerException,RemoteException;
    
    public ICMSCustomerTrxValue checkerApproveDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException;

    public ICMSCustomerTrxValue checkerRejectDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException;
    
    public ICMSCustomerTrxValue makerCloseRejectedCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException;
    
    public ICMSCustomerTrxValue makerCloseDraftCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException;
    
    public ICMSCustomer getCustomerByLimitProfileId(long limitProfileId) throws CustomerException;
    
    public ICMSCustomerTrxValue createCustomerWithApprovalThroughWsdl(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException;
    public ICMSCustomerTrxValue updateCustomerWithApprovalThroughWsdl(ITrxContext context, ICMSCustomerTrxValue trxValue,ICMSCustomer obCMSCustomer)throws CustomerException;

	public SearchResult searchCustomerImageUpload(CustomerSearchCriteria criteria) throws SearchDAOException;
}