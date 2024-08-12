package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;


public interface EBCCICounterpartyDetailsHome extends EJBHome {

   public EBCCICounterpartyDetails create(ICCICounterparty aICCICounterparty) throws CreateException, RemoteException;

   public EBCCICounterpartyDetails findByPrimaryKey(Long pk) throws FinderException, RemoteException;


   public ICCICounterpartyDetails getCCICounterpartyDetails(String groupCCINo) throws SearchDAOException,RemoteException;

   public ICCICounterpartyDetails getCCICounterpartyByGroupCCINo(long groupCCINo) throws SearchDAOException,RemoteException;


   public Collection findByGroupCCINo (long groupCCINo) throws FinderException, RemoteException;

   public Collection findByByGroupCCINoRef(long groupCCINoRef)throws FinderException, RemoteException;


    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria)  throws SearchDAOException, RemoteException;


    public String searchCustomer(long lmt_profile_id)  throws SearchDAOException, RemoteException;

    public OBCustomerAddress getCustomerAddress(ICCICounterparty value)  throws SearchDAOException, RemoteException;

    public  HashMap  isExistCCICustomer(long groupCCINo, String[]   cciObj)  throws SearchDAOException, RemoteException;


}
