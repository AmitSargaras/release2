package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Dec 18, 2007
 * Time: 10:30:53 AM
 * To change this template use File | Settings | File Templates.
 */


public class CCICounterpartyDetailsBusManagerImpl implements ICCICounterpartyDetailsBusManager {

    /**
     * Default Constructor
     */
    CCICounterpartyDetailsBusManagerImpl() {

    }


    public ICCICounterpartyDetails createCCICounterpartyDetails(ICCICounterpartyDetails details)
            throws CCICounterpartyDetailsException {
        SBCCICounterpartyDetailsBusManager theEjb = getBusManager();
        try {
            return theEjb.createCCICounterpartyDetails(details);
        }
        catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("RemoteException caught!" + e.toString());
        }

    }


    public ICCICounterpartyDetails updateCCICounterpartyDetails(ICCICounterpartyDetails details)
              throws CCICounterpartyDetailsException {
          SBCCICounterpartyDetailsBusManager theEjb = getBusManager();
          try {
              return theEjb.updateCCICounterpartyDetails(details);
          }
          catch (RemoteException e) {
              DefaultLogger.error(this, "", e);
              throw new CCICounterpartyDetailsException("RemoteException caught!" + e.toString());
          }

      }




    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINoRef(long groupCCINoRef)
            throws CCICounterpartyDetailsException {


         SBCCICounterpartyDetailsBusManager theEjb = getBusManager();

        try {
            return theEjb.getCCICounterpartyByGroupCCINoRef (groupCCINoRef);
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            throw new CCICounterpartyDetailsException ("RemoteException caught!" + e.toString());
        }


    }


    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINo(long groupCCINo)
            throws CCICounterpartyDetailsException {


         SBCCICounterpartyDetailsBusManager theEjb = getBusManager();

        try {
            return theEjb.getCCICounterpartyByGroupCCINo (groupCCINo);
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            throw new CCICounterpartyDetailsException ("RemoteException caught!" + e.toString());
        }


    }

     public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria)
            throws CCICounterpartyDetailsException {
        SBCCICounterpartyDetailsBusManager theEjb = getBusManager();
        try {
            return theEjb.searchCCICustomer(criteria);
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("RemoteException caught!" + e.toString());
        }

    }


     public String searchCustomer(long criteria)
            throws CCICounterpartyDetailsException {
        SBCCICounterpartyDetailsBusManager theEjb = getBusManager();
        try {
            return theEjb.searchCustomer(criteria);
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("RemoteException caught!" + e.toString());
        }

    }


    public OBCustomerAddress getCustomerAddress(ICCICounterparty value)
               throws CCICounterpartyDetailsException {
           SBCCICounterpartyDetailsBusManager theEjb = getBusManager();
           try {
               return theEjb.getCustomerAddress(value);
           } catch (Exception e) {
               DefaultLogger.error(this, "", e);
               throw new CCICounterpartyDetailsException("RemoteException caught!" + e.toString());
           }

       }



     public HashMap  isExistCCICustomer(long groupCCINo,String[]   cciObj)
            throws CCICounterpartyDetailsException {
        SBCCICounterpartyDetailsBusManager theEjb = getBusManager();
        try {
            return theEjb.isExistCCICustomer(groupCCINo, cciObj);
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("RemoteException caught!" + e.toString());
        }

    }



    /**
     * helper method to get an ejb object to CCICounterpartyDetails business manager session bean.
     *
     * @return SBCCICounterpartyDetailsBusManager ejb object
     * @throws CCICounterpartyDetailsException
     *          on errors encountered
     */

    protected SBCCICounterpartyDetailsBusManager getBusManager() throws CCICounterpartyDetailsException {
        SBCCICounterpartyDetailsBusManager theEjb = (SBCCICounterpartyDetailsBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CCI_COUNTERPARTY_DETAILS_BUS_MANAGER_JNDI, SBCCICounterpartyDetailsBusManagerHome .class.getName());

        if (theEjb == null)
            throw new CCICounterpartyDetailsException("SBCCICounterpartyDetailsBusManager for Staging is null!");

        return theEjb;
    }


}
