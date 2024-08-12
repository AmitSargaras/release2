/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/SBCustomerManager.java,v 1.10 2006/08/01 02:57:14 jzhai Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.host.eai.customer.SearchDetailResult;
import com.integrosys.cms.host.eai.customer.SearchHeader;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the remote interface to the SBCustomerManager
 * session bean.
 *
 * @author $Author: jzhai $
 * @version $Revision: 1.10 $
 * @since $Date: 2006/08/01 02:57:14 $
 *        Tag:     $Name:  $
 */
public interface SBCustomerManager extends EJBObject {
    /**
     * Create a new Customer. This method will create legal entity if it does not already exist
     *
     * @param obj is the ICMSCustomer to be created
     * @return ICMSCustomer containing the newly created customer records
     * @throws CustomerException on errors
     * @throws RemoteException   on remote errors
     */
    public ICMSCustomer createCustomer(ICMSCustomer obj) throws CustomerException, RemoteException;

    /**
     * Update Customer detalis. This method does not update legal entity information.
     *
     * @param obj is the ICMSCustomer to be updated
     * @return ICMSCustomer containing the newly updated customer records
     * @throws CustomerException on errors
     * @throws RemoteException   on remote errors
     */
    public ICMSCustomer updateCustomer(ICMSCustomer obj) throws CustomerException, RemoteException;

    /**
     * Delete Customer. This method does not delete the Legal Entity
     *
     * @param customerID is of type long
     * @throws CustomerException, RemoteException on errors
     */
    public void deleteCustomer(long customerID) throws CustomerException, RemoteException;

    /**
     * Delete Legal Entity. This method does not cascade delete customers. If any customer
     * details still exist, the delete will fail.
     *
     * @param legalID is of type long
     * @throws CustomerException, RemoteException on errors
     */
    public void deleteLegalEntity(long legalID) throws CustomerException, RemoteException;

    /**
     * Get a Customer given a customer's ID
     *
     * @param customerID is of type long
     * @return ICMSCustomer if it can be found, or null if the customer
     *         does not exist.
     * @throws CustomerException, RemoteException on errors
     */
    public ICMSCustomer getCustomer(long customerID) throws CustomerException, RemoteException;

    /**
     * Get a Customer given a customer's ID
     *
     * @param cifNumber      is of type String
     * @param sourceSystemId is of type String
     * @return ICMSCustomer if it can be found, or null if the customer
     *         does not exist.
     * @throws CustomerException, RemoteException on errors
     */
    public ICMSCustomer getCustomerByCIFNumber(String cifNumber, String sourceSystemId) throws CustomerException, RemoteException;

    public List getCustomerByCIFNumber(String cifNumber, String sourceSystemId, String partyName, String partyId) throws CustomerException, RemoteException;
    /**
     * Get a Customer given a customer's ID
     *
     * @param cifNumber      is of type String
     * @param sourceSystemId is of type String
     * @return ICMSCustomer if it can be found, or null if the customer
     *         does not exist.
     * @throws CustomerException, RemoteException on errors
     */
    public ICMSCustomer getCustomerByCIFNumberFromDB(String cifNumber, String sourceSystemId) throws CustomerException, RemoteException;

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
     * Search Group
     *
     * @param criteria is of type GroupSearchCriteria
     * @return SearchResult containing IGroupSearchResult objects
     * @throws SearchDAOException, RemoteException on errors
     */
    public SearchResult searchGroup(GroupSearchCriteria criteria) throws SearchDAOException, RemoteException;

    /**
     * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
     *
     * @return long
     * @throws SearchDAOException if no records found
     * @throws CustomerException, RemoteException on errors
     */
    public long searchCustomerID(long leid, long subProfileID) throws CustomerException, SearchDAOException, RemoteException;


    public ArrayList getMBlistByCBleId(long leid) throws CustomerException, RemoteException;

    /**
     * Get map of mailing details for a list of limit profile IDs.
     * One official address per limit profile ID if available.
     *
     * @param sciLimitProfileIDList - List
     * @return Map - (limitProfileID, OBCustomerMailingDetails)
     * @throws SearchDAOException, RemoteException on errors
     */
    public Map getCustomerMailingDetails(List sciLimitProfileIDList) throws SearchDAOException, RemoteException;

    /**
     * Get map of fam code and customer name by a list of (le_id/lsp_id)
     *
     * @param sciLimitProfileIDList - List
     * @return Map - (limitProfileID, array of customername, famcode)
     * @throws SearchDAOException, RemoteException on errors
     */
    public Map getFamcodeCustNameByCustomer(List sciLimitProfileIDList)
            throws SearchDAOException, RemoteException;

    /**
     * Search CCIcustomer
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException, RemoteException on errors
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria)
            throws SearchDAOException, RemoteException;

    public ICCICounterpartyDetails getCCICounterpartyDetails(String groupCCINo)
            throws SearchDAOException, RemoteException;

    /**
     * To retrieve Main Profile By CIFID , CIF Source , if record not found ,
     * create dummy record
     *
     * @param cifId
     * @param cifSource
     * @return
     * @throws SearchDAOException
     * @throws RemoteException
     */

    public MainProfile getMainProfileOrCreateDummy(String cifId,
                                                   String cifSource, MainProfile mirrorMainProfile) throws SearchDAOException,
            RemoteException;

    public SearchHeader getSearchCustomerMultipleHeader(String msgRefNo)
            throws Exception, RemoteException;

    public SearchDetailResult[] getSearchCustomerMultipleResults(String msgRefNo)
			throws Exception, RemoteException;

    /**
     * Method Added by Sandeep Shinde on 18-03-2011 so as to make Customer Status INACTIVE
     * @param aCMSCustomer
     * @return ICMSCustomer
     * @throws CustomerException
     * @throws RemoteException
     */
    
    public ICMSCustomerTrxValue getCustomerTrxValue(long id)throws CustomerException,RemoteException;

	public SearchResult searchCustomerImageUpload(CustomerSearchCriteria criteria) throws SearchDAOException, RemoteException;
}