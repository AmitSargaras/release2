package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchDAOException;

import java.io.Serializable;
import java.util.HashMap;


public interface ICCICounterpartyDetailsBusManager extends Serializable {


    public ICCICounterpartyDetails createCCICounterpartyDetails(ICCICounterpartyDetails details)
            throws CCICounterpartyDetailsException;


    public ICCICounterpartyDetails updateCCICounterpartyDetails(ICCICounterpartyDetails details)
            throws CCICounterpartyDetailsException;


    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINo(long groupCCINo)
            throws CCICounterpartyDetailsException;


    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINoRef(long groupCCINoRef)
            throws CCICounterpartyDetailsException;

     /**
     * Used in ListCounterpartyCommand  for
     *  search CCI customer and customer
     * @param criteria
     * @return
     * @throws CCICounterpartyDetailsException
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria)
            throws CCICounterpartyDetailsException;


     /**
     * Used in ListCounterpartyCommand  for
     *  search CCI customer and customer
     * @param lmt_profile_id
     * @return
     * @throws CCICounterpartyDetailsException
     */
      public String searchCustomer(long lmt_profile_id)
              throws CCICounterpartyDetailsException;


    /**
     *  This helper method used to set address for the  customer in external search..
     * @param value
     * @return
     * @throws CCICounterpartyDetailsException
     */
      public OBCustomerAddress getCustomerAddress(ICCICounterparty value)
              throws CCICounterpartyDetailsException;

    /**
     *
     * @param cciObj
     * @return
     * @throws CCICounterpartyDetailsException
     */

      public HashMap  isExistCCICustomer(long groupCCINo, String[]  cciObj)
              throws CCICounterpartyDetailsException;

}
