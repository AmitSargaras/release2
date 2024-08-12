package com.integrosys.cms.app.cci.proxy;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.rmi.RemoteException;
import java.util.HashMap;


public class CCICustomerProxyImpl implements ICCICustomerProxy {

     /**
     * Used in ListCounterpartyCommand  for
     *  search CCI customer and customer
     * @param criteria
     * @return
     * @throws CCICounterpartyDetailsException
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria) throws CCICounterpartyDetailsException {
        try {
            return getCCIProxy().searchCCICustomer(criteria);
        } catch (Exception e) {
            DefaultLogger.error(this, "Caught Exception!", e);
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }



      /* (non-Javadoc)
	 * @see com.integrosys.cms.app.cci.proxy.ICCICustomerProxy#searchExternalCustomer(com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria)
	 */
    public String searchExternalCustomer(CounterpartySearchCriteria criteria) throws CCICounterpartyDetailsException {
        try {
            return getCCIProxy().searchExternalCustomer(criteria);
        } catch (Exception e) {
            DefaultLogger.error(this, "Caught Exception!", e);
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }



    public String  searchCustomer(long lmt_profile_id) throws CCICounterpartyDetailsException{
          try {
            return getCCIProxy().searchCustomer(lmt_profile_id);
        } catch (Exception e) {
            DefaultLogger.error(this, "Caught Exception!", e);
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }

      public HashMap  isExistCCICustomer(long groupCCINo, String[]   cciObj) throws CCICounterpartyDetailsException{
          try {
            return getCCIProxy().isExistCCICustomer(groupCCINo, cciObj);
        } catch (Exception e) {
            DefaultLogger.error(this, "Caught Exception!", e);
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }






    /**
     *
     * @param groupCCINo
     * @return
     * @throws CCICounterpartyDetailsException
     */

    public ICCICounterpartyDetails getCCICounterpartyDetails(String groupCCINo)
            throws CCICounterpartyDetailsException {
        try {
            return getCCIProxy().getCCICounterpartyDetails(groupCCINo);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("RemoteException", e);
        }
    }

    public ICCICounterpartyDetailsTrxValue getCCICounterpartyDetailsByTrxID(ITrxContext ctx, String trxID) throws CCICounterpartyDetailsException {
         try {
             return getCCIProxy().getCCICounterpartyDetailsByTrxID(ctx, trxID);          }
         catch (CCICounterpartyDetailsException e) {
             DefaultLogger.error (this, "", e);
             throw e;
         } catch (Exception e) {
             DefaultLogger.error (this, "", e);
             throw new CCICounterpartyDetailsException ("Exception caught at getCCICounterpartyDetailsByTrxID: " + e.toString());
         }

    }

      public ICCICounterpartyDetailsTrxValue getCCICounterpartyDetailsByGroupCCINo (ITrxContext ctx, String groupCCINo) throws CCICounterpartyDetailsException {
         try {
             return getCCIProxy().getCCICounterpartyDetailsByGroupCCINo(ctx, groupCCINo);          }
         catch (CCICounterpartyDetailsException e) {
             DefaultLogger.error (this, "", e);
             throw e;
         } catch (Exception e) {
             DefaultLogger.error (this, "", e);
             throw new CCICounterpartyDetailsException ("Exception caught at getCCICounterpartyDetailsByTrxID: " + e.toString());
         }

    }

    /**
     *
     * @param anITrxContext
     * @param aTrxValue
     * @param counterpartyDetails
     * @return
     * @throws CCICounterpartyDetailsException
     */
    public ICCICounterpartyDetailsTrxValue makerSubmitICCICustomer(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue, ICCICounterpartyDetails counterpartyDetails)
            throws CCICounterpartyDetailsException {
        try {
            return getCCIProxy().makerSubmitICCICustomer( anITrxContext, aTrxValue, counterpartyDetails);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new CCICounterpartyDetailsException("RemoteException", e);
        }
    }


     public ICCICounterpartyDetailsTrxValue makerDeleteICCICustomer(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue, ICCICounterpartyDetails counterpartyDetails)
            throws CCICounterpartyDetailsException {
        try {
            return getCCIProxy().makerDeleteICCICustomer( anITrxContext, aTrxValue, counterpartyDetails);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new CCICounterpartyDetailsException("RemoteException", e);
        }
    }

    /**
     *
     * @param anITrxContext
     * @param aTrxValue
     * @return
     * @throws CCICounterpartyDetailsException
     */
    public ICCICounterpartyDetailsTrxValue checkerApproveUpdateCCI(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue)
            throws CCICounterpartyDetailsException {
        try {
            return getCCIProxy().checkerApproveUpdateCCI( anITrxContext, aTrxValue);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new CCICounterpartyDetailsException("RemoteException", e);
        }
    }


    /**
        *
        * @param anITrxContext
        * @param aTrxValue
        * @return
        * @throws CCICounterpartyDetailsException
        */
       public ICCICounterpartyDetailsTrxValue checkerRejectUpdateCCI(
               ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue)
               throws CCICounterpartyDetailsException {
           try {
               return getCCIProxy().checkerRejectUpdateCCI( anITrxContext, aTrxValue);
           } catch (RemoteException e) {
               DefaultLogger.error(this, "", e);
               throw  new CCICounterpartyDetailsException("RemoteException", e);
           }
       }


    /**
        *
        * @param anITrxContext
        * @param aTrxValue
        * @return
        * @throws CCICounterpartyDetailsException
        */
       public ICCICounterpartyDetailsTrxValue makerCancelUpdateCCI(
               ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue)
               throws CCICounterpartyDetailsException {
           try {
               return getCCIProxy().makerCancelUpdateCCI( anITrxContext, aTrxValue);
           } catch (RemoteException e) {
               DefaultLogger.error(this, "", e);
               throw  new CCICounterpartyDetailsException("RemoteException", e);
           }
       }



    private SBCounterpartyDetailsProxy getCCIProxy() throws CCICounterpartyDetailsException {
        SBCounterpartyDetailsProxy home = (SBCounterpartyDetailsProxy) BeanController.getEJB(
                ICMSJNDIConstant.SB_COUNTERPARTY_DETAILS_PROXY_JNDI,
                SBCounterpartyDetailsProxyHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new CCICounterpartyDetailsException("SBCounterpartyDetailsProxy is null!");
        }
    }


}
