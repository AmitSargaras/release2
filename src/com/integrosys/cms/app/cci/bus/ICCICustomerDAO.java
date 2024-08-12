package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

import java.util.HashMap;


public interface ICCICustomerDAO {

    public String searchCustomer(long lmt_profile_id) throws SearchDAOException;

    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria) throws SearchDAOException;

    public ICCICounterpartyDetails getCCICounterpartyDetails(CounterpartySearchCriteria criteria) throws SearchDAOException;

    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINo(long groupCCINo) throws SearchDAOException;

    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINoRef(long groupCCINoRef) throws SearchDAOException;

    public ICCICounterparty getCustomerDetails(ICCICounterparty aICCICounterparty) throws SearchDAOException;

    public ICCICounterparty getCustomerAddress(ICCICounterparty aICCICounterparty) throws SearchDAOException;


    public HashMap  isExistCCICustomer(long groupCCINo, String[]   cciObj) throws SearchDAOException;
	
	public HashMap getCommonCustomer(long subprofileID) throws SearchDAOException;

}
