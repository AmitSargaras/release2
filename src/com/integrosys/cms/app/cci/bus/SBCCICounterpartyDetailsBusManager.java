package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.HashMap;


public interface SBCCICounterpartyDetailsBusManager extends EJBObject {


    public ICCICounterpartyDetails createCCICounterpartyDetails(ICCICounterpartyDetails details)
            throws CCICounterpartyDetailsException, RemoteException;


    public ICCICounterpartyDetails updateCCICounterpartyDetails(ICCICounterpartyDetails details)
            throws CCICounterpartyDetailsException, RemoteException;


    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINo(long groupCCINo)
            throws CCICounterpartyDetailsException, RemoteException;


    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINoRef(long groupCCINoRef)
            throws CCICounterpartyDetailsException, RemoteException;


     /**
     * Used in ListCounterpartyCommand  for
     *  search CCI customer and customer
     * @param criteria
     * @return
     * @throws CCICounterpartyDetailsException
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria)
            throws CCICounterpartyDetailsException, RemoteException;


    public String searchCustomer(long lmt_profile_id)
               throws CCICounterpartyDetailsException, RemoteException;

    /**
     * This helper method used to set address for the  customer in external search..
     * @param value
     * @return
     * @throws CCICounterpartyDetailsException
     * @throws RemoteException
     */

     public OBCustomerAddress getCustomerAddress(ICCICounterparty value)
             throws CCICounterpartyDetailsException, RemoteException;


     public HashMap  isExistCCICustomer(long groupCCINo, String[]   cciObj)
               throws CCICounterpartyDetailsException, RemoteException;

}
