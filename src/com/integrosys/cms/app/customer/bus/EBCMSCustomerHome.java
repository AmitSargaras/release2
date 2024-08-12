/*
* Copyright Integro Technologies Pte Ltd
* $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSCustomerHome.java,v 1.5 2006/08/01 02:55:25 jzhai Exp $
*/
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Home interface to the EBCMSCustomer Entity Bean
 *
 * @author $Author: jzhai $
 * @version $Revision: 1.5 $
 * @since $Date: 2006/08/01 02:55:25 $
 *        Tag: $Name:  $
 */
public interface EBCMSCustomerHome extends EJBHome {
    /**
     * Create a Customer
     *
     * @param value is the ICMSCustomer object
     * @return EBCMSCustomer
     * @throws CreateException, RemoteException
     */
    public EBCMSCustomer create(ICMSCustomer value) throws CreateException, RemoteException;

    /**
     * Find by primary Key, the customer ID
     *
     * @param pk is the Long value of the primary key
     * @return EBSMECreditApplication
     * @throws FinderException on error
     * @throws RemoteException
     */
    public EBCMSCustomer findByPrimaryKey(Long pk) throws FinderException, RemoteException;

    /**
     * Find by CIF number, the customer ID
     *
     * @param cifNumber is the String value of the cifNumber
     * @return EBCMSCustomer
     * @throws FinderException on error
     * @throws RemoteException
     */
    public long getCustomerByCIFNumber(String cifNumber, String sourceId) throws CustomerException, SearchDAOException, FinderException, RemoteException;

    
    public List getCustomerByCIFNumber(String cifNumber, String sourceId,String partyName, String partyId) throws CustomerException, SearchDAOException, FinderException, RemoteException;
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
     * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
     *
     * @return long
     * @throws SearchDAOException if no records found
     * @throws CustomerException  on errors
     */
    public long searchCustomerID(long leid, long subProfileID) throws CustomerException, SearchDAOException, RemoteException;


    public ArrayList getMBlistByCBleId(long leid) throws CustomerException, SearchDAOException, RemoteException;

    /**
     * Search searchCCICustomer
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException, RemoteException on errors
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria) throws SearchDAOException, RemoteException;

	public SearchResult searchCustomerImageUpload(CustomerSearchCriteria criteria) throws SearchDAOException, RemoteException;

}