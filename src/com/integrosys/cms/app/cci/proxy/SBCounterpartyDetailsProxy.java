package com.integrosys.cms.app.cci.proxy;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.HashMap;


public interface SBCounterpartyDetailsProxy extends EJBObject {


    public ICCICounterpartyDetailsTrxValue makerSubmitICCICustomer(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue,
            ICCICounterpartyDetails aFeedGroup)
            throws RemoteException, CCICounterpartyDetailsException;


    public ICCICounterpartyDetailsTrxValue makerDeleteICCICustomer(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue,
            ICCICounterpartyDetails aFeedGroup)
            throws RemoteException, CCICounterpartyDetailsException;


    public ICCICounterpartyDetailsTrxValue checkerApproveUpdateCCI(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue)
            throws RemoteException, CCICounterpartyDetailsException;


    public ICCICounterpartyDetailsTrxValue checkerRejectUpdateCCI(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue)
            throws RemoteException, CCICounterpartyDetailsException;

    public ICCICounterpartyDetailsTrxValue makerCancelUpdateCCI(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue)
            throws RemoteException, CCICounterpartyDetailsException;

    public ICCICounterpartyDetailsTrxValue getCCICounterpartyDetailsByTrxID(ITrxContext ctx, String trxRefID)
            throws RemoteException, CCICounterpartyDetailsException;

    public ICCICounterpartyDetailsTrxValue getCCICounterpartyDetailsByGroupCCINo(ITrxContext ctx, String groupCCINo)
            throws RemoteException, CCICounterpartyDetailsException;

    /**
     * Used in ListCounterpartyCommand  for
     * search CCI customer and customer
     *
     * @param criteria
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria)
            throws CCICounterpartyDetailsException, RemoteException;

    public String searchExternalCustomer(CounterpartySearchCriteria criteria) throws Exception, RemoteException;


    public String searchCustomer(long lmt_profile_id)
            throws CCICounterpartyDetailsException, RemoteException;

    public ICCICounterpartyDetails getCCICounterpartyDetails(String cciNO)
            throws RemoteException, CCICounterpartyDetailsException;


   public HashMap  isExistCCICustomer(long groupCCINo, String[]  cciObj)
            throws CCICounterpartyDetailsException, RemoteException;


}
