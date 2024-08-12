/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/proxy/SBCustomerProxy.java,v 1.12 2006/09/07 10:23:28 jychong Exp $
 */
package com.integrosys.cms.app.customer.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.GroupSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This is the remote interface to the SBCustomerProxy session bean.
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.12 $
 * @since $Date: 2006/09/07 10:23:28 $ Tag: $Name: $
 */
public interface SBCustomerProxy extends EJBObject {
	/**
	 * Create a customer record, without dual control.
	 * 
	 * @param value is of type ICMSCustomer
	 * @return ICMSTrxResult
	 * @throws CustomerException, RemoteException on errors
	 */
	public ICMSTrxResult createCustomer(ICMSCustomer value) throws CustomerException, RemoteException;
	
	

	
	/**
	 * Create a customer record, without dual control.
	 * 
	 * @param value is of type ICMSCustomerTrxValue
	 * @return ICMSTrxResult
	 * @throws CustomerException, RemoteException on errors
	 */
	public ICMSCustomerTrxValue createCustomer(ICMSCustomerTrxValue value) throws CustomerException, RemoteException;
	
	public ICMSCustomerTrxValue createCustomerBrmaker(ICMSCustomerTrxValue value , String loginUser) throws CustomerException, RemoteException;
	
	public ICMSCustomerTrxValue createDraftCustomer(ICMSCustomerTrxValue value) throws CustomerException, RemoteException;
	
	public ICMSCustomerTrxValue createDraftCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException, RemoteException;
	
	public ICMSCustomerTrxValue saveCustomer(ICMSCustomerTrxValue value) throws CustomerException, RemoteException;
	
	public ICMSCustomerTrxValue saveCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException, RemoteException;
	
	public ICMSCustomerTrxValue saveEditCustomer(ICMSCustomerTrxValue value) throws CustomerException, RemoteException;
	
	
	public ICMSCustomerTrxValue makerResubmitCreateCustomer(ICMSCustomerTrxValue value) throws CustomerException, RemoteException;
	
	public ICMSCustomerTrxValue makerResubmitCreateCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException, RemoteException;
	//changed

	/**
	 * Update a Customer given a customer ob
	 * 
	 * @param aCMSCustomer is of type ICMSCustomer
	 * @return ICMSCustomer if it can be found, or null if the customer does not
	 *         exist.
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer updateCustomer(ICMSCustomer aCMSCustomer) throws CustomerException, RemoteException;

	/**
	 * Get a Customer given a customer's ID
	 * 
	 * @param customerID is of type long
	 * @return ICMSCustomer if it can be found, or null if the customer does not
	 *         exist.
	 * @throws CustomerException, RemoteException on errors
	 */
	public ICMSCustomer getCustomer(long customerID) throws CustomerException, RemoteException;

	/**
	 * Get a customer transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ICMSCustomerTrxValue
	 * @throws CustomerException, RemoteException on errors
	 */
	public ICMSCustomerTrxValue getTrxCustomer(String trxID) throws CustomerException, RemoteException;

	/**
	 * Get a Customer Transaction value given a customer's ID
	 * 
	 * @param customerID is of type long
	 * @return ICMSCustomerTrxValue if it can be found, or null if the customer
	 *         does not exist.
	 * @throws CustomerException, RemoteException; on errors
	 */
	public ICMSCustomerTrxValue getTrxCustomer(long customerID) throws CustomerException, RemoteException;

	/**
	 * Allows the host to update the customer details
	 * 
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException, RemoteException on errors
	 */
	public ICMSTrxResult hostUpdateCustomer(ICMSCustomerTrxValue trxValue) throws CustomerException, RemoteException;

	/**
	 * Allows the host to delete the customer details
	 * 
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 * @throws RemoteException on error during remote method call
	 */
	public ICMSTrxResult hostDeleteCustomer(ICMSCustomerTrxValue trxValue) throws CustomerException, RemoteException;

	/**
	 * Search customer
	 * 
	 * @param criteria is of type CustomerSearchCriteria
	 * @return SearchResult containing ICustomerSearchResult objects
	 * @throws SearchDAOException, RemoteException on errors
	 */
	public SearchResult searchCustomer(CustomerSearchCriteria criteria) throws SearchDAOException, RemoteException;

	/**
     * Search Customer Information Only
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException, RemoteException on errors
     */
     public SearchResult searchCustomerInfoOnly(CustomerSearchCriteria criteria) throws SearchDAOException, RemoteException;

    /**
	 * Search group
	 * 
	 * @param criteria is of type GroupSearchCriteria
	 * @return SearchResult containing IGroupSearchResult objects
	 * @throws SearchDAOException, RemoteException on errors
	 */
	public SearchResult searchGroup(GroupSearchCriteria criteria) throws SearchDAOException, RemoteException;

	/**
	 * Get map of mailing details for a list of limit profile IDs. One official
	 * address per limit profile ID if available.
	 * 
	 * @param sciLimitProfileIDList - List
	 * @return Map - (limitProfileID, OBCustomerMailingDetails)
	 * @throws SearchDAOException, RemoteException on errors
	 */
	public Map getCustomerMailingDetails(List sciLimitProfileIDList) throws SearchDAOException, RemoteException;

	public Map getFamcodeCustNameByCustomer(List sciLimitProfileIDList) throws SearchDAOException, RemoteException;

	/**
	 * Maker update customer details
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @param customer is the ICMSCustomer object to be updated
	 * @return ICMSTrxResult
	 * @throws CustomerException, RemoteException on errors
	 */
	//changed
	public ICMSTrxResult makerUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
			throws CustomerException, RemoteException;

	
	public ICMSTrxResult makerUpdateDraftCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
	throws CustomerException, RemoteException;

	

	public ICMSTrxResult makerEditDraftCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
	throws CustomerException, RemoteException;
	/**
	 * Maker Resubmit customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @param customer is the ICMSCustomer object to be updated
	 * @return ICMSTrxResult
	 * @throws CustomerException, RemoteException on errors
	 */
	public ICMSTrxResult makerResubmitUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue,
			ICMSCustomer customer) throws CustomerException, RemoteException;

	/**
	 * Maker Cancel customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException, RemoteException on errors
	 */
	public ICMSTrxResult makerCancelUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException, RemoteException;

	/**
	 * Checker Approve customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException, RemoteException on errors
	 */
	public ICMSCustomerTrxValue checkerApproveUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException, RemoteException;

	/**
	 * Checker Reject customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException, RemoteException on errors
	 */
	public ICMSCustomerTrxValue checkerRejectUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException, RemoteException;

	/**
	 * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException, RemoteException on errors
	 */
	public long searchCustomerID(long leid, long subProfileID) throws CustomerException, SearchDAOException,
			RemoteException;

	/**
	 * Retrieve the CMS Customer ID, given the CIF and Source system id If not
	 * found in local DB, will trigger search to host.
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomerByCIFSource(String cifNumber, String sourceSystemId) throws CustomerException,
			SearchDAOException, RemoteException;

	public List getCustomerByCIFSource(String cifNumber, String sourceSystemId, String partyName,String partyId) throws CustomerException,
	SearchDAOException, RemoteException;

	/**
	 * Retrieve the CMS Customer ID, given the CIF and Source system id from
	 * local database.
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomerByCIFSourceFromDB(String cifNumber, String sourceSystemId) throws CustomerException,
			SearchDAOException, RemoteException;

	public ArrayList getMBlistByCBleId(long cbLeId) throws CustomerException, RemoteException;

	public List getJointBorrowerList(long limitProfileId) throws Exception, RemoteException;

    public List getOnlyJointBorrowerList(long limitProfileId) throws Exception, RemoteException;
    
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
    
    public ICMSCustomerTrxValue checkerApproveCreateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException; 
    
    public ICMSCustomerTrxValue checkerRejectCreateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException;

    public ICMSCustomerTrxValue updateCustomer(ICMSCustomerTrxValue aCMSCustomer) throws CustomerException,RemoteException;
    
    public ICMSCustomerTrxValue getCustomerTrxValue(long id)throws CustomerException,RemoteException;
    
    public ICMSTrxResult prepareDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)throws CustomerException,RemoteException;
    
    public ICMSTrxResult makerResubmitDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)throws CustomerException,RemoteException;
    
    public ICMSCustomerTrxValue checkerApproveDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException;

    public ICMSCustomerTrxValue checkerRejectDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException;

    public ICMSCustomerTrxValue makerCloseRejectedCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException;
    
    public ICMSCustomerTrxValue makerCloseDraftCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException;
    
    public ICMSCustomer getCustomerByLimitProfileId(long limitProfileId) throws CustomerException, RemoteException;

    public ICMSCustomerTrxValue createCustomerWithApprovalThroughWsdl(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException; 
    public ICMSCustomerTrxValue updateCustomerWithApprovalThroughWsdl(ITrxContext context, ICMSCustomerTrxValue trxValue,ICMSCustomer obCMSCustomer)throws CustomerException,RemoteException;

    public SearchResult searchCustomerImageUpload(CustomerSearchCriteria criteria) throws SearchDAOException, RemoteException;



}