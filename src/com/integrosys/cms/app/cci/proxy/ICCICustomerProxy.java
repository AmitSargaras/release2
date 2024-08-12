package com.integrosys.cms.app.cci.proxy;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.util.HashMap;


public interface ICCICustomerProxy extends java.io.Serializable {


    public ICCICounterpartyDetailsTrxValue makerSubmitICCICustomer(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue,
            ICCICounterpartyDetails counterpartyDetails)
            throws CCICounterpartyDetailsException;

    public ICCICounterpartyDetailsTrxValue makerDeleteICCICustomer(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue,
            ICCICounterpartyDetails counterpartyDetails)
            throws CCICounterpartyDetailsException;

    public ICCICounterpartyDetailsTrxValue checkerApproveUpdateCCI(ITrxContext anITrxContext,
                                                                   ICCICounterpartyDetailsTrxValue aTrxValue) throws CCICounterpartyDetailsException;

    public ICCICounterpartyDetailsTrxValue checkerRejectUpdateCCI(ITrxContext anITrxContext,
                                                                  ICCICounterpartyDetailsTrxValue aTrxValue) throws CCICounterpartyDetailsException;

    public ICCICounterpartyDetailsTrxValue makerCancelUpdateCCI(ITrxContext anITrxContext,
                                                                ICCICounterpartyDetailsTrxValue aTrxValue) throws CCICounterpartyDetailsException;


    public ICCICounterpartyDetails getCCICounterpartyDetails(String groupCCINo) throws CCICounterpartyDetailsException;

    public ICCICounterpartyDetailsTrxValue getCCICounterpartyDetailsByTrxID(ITrxContext anITrxContext, String trxID) throws CCICounterpartyDetailsException;

    public ICCICounterpartyDetailsTrxValue getCCICounterpartyDetailsByGroupCCINo(ITrxContext anITrxContext, String groupCCINo) throws CCICounterpartyDetailsException;


    /**
     * Used in ListCounterpartyCommand  for
     * search CCI customer and customer
     *
     * @param criteria
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria) throws Exception;

    public String searchExternalCustomer(CounterpartySearchCriteria criteria) throws Exception;

    public String  searchCustomer(long lmt_profile_id) throws CCICounterpartyDetailsException;

    public HashMap  isExistCCICustomer(long groupCCINo,String[]  cciObj) throws CCICounterpartyDetailsException;


}
