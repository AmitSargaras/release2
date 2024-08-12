package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.base.businfra.currency.Amount;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Dec 18, 2007
 * Time: 10:30:53 AM
 * To change this template use File | Settings | File Templates.
 */


public class CustGrpIdentifierBusManagerImpl implements ICustGrpIdentifierBusManager {

    /**
     * Default Constructor
     */
    CustGrpIdentifierBusManagerImpl() {
    }


    public ICustGrpIdentifier deleteCustGrpIdentifier(ICustGrpIdentifier obj) throws CustGrpIdentifierException {

        SBCustGrpIdentifierBusManager theEjb = getBusManager();
       try {
           Debug("Before CustGrpIdentifierBusManagerImpl return theEjb.deleteCustGrpIdentifier (obj)");
           return theEjb.deleteCustGrpIdentifier(obj);
       }
       catch (RemoteException e) {
           DefaultLogger.error(this, "", e);
           throw new CustGrpIdentifierException("RemoteException caught!" + e.toString());
       }

    }

    public ICustGrpIdentifier updateCustGrpIdentifier(ICustGrpIdentifier obj)
           throws CustGrpIdentifierException {
       SBCustGrpIdentifierBusManager theEjb = getBusManager();
       try {
           Debug("Before CustGrpIdentifierBusManagerImpl return theEjb.updateCustGrpIdentifier (details)");
           return theEjb.updateCustGrpIdentifier(obj);
       }
       catch (RemoteException e) {
           DefaultLogger.error(this, "", e);
           throw new CustGrpIdentifierException("RemoteException caught!" + e.toString());
       }

   }


    public ICustGrpIdentifier getCustGrpIdentifierByTrxIDRef(long trxIDRef)
            throws CustGrpIdentifierException {
         Debug("Before SBCustGrpIdentifierBusManager getBusManager()");
         SBCustGrpIdentifierBusManager theEjb = getBusManager();
        try {
            return theEjb.getCustGrpIdentifierByTrxIDRef (trxIDRef);
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            throw new CustGrpIdentifierException ("RemoteException caught!" + e.toString());
        }


    }



    

    public ICustGrpIdentifier createCustGrpIdentifier(ICustGrpIdentifier obj)
            throws CustGrpIdentifierException {
        SBCustGrpIdentifierBusManager theEjb = getBusManager();
        try {
            Debug("Before CustGrpIdentifierBusManagerImpl return theEjb.createCustGrpIdentifier (details)");
            return theEjb.createCustGrpIdentifier(obj);
        }
        catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException caught!" + e.toString());
        }

    }


	public ICustGrpIdentifier[] getCustGrpByInternalLimitType(String internalLimitType) throws CustGrpIdentifierException {
        SBCustGrpIdentifierBusManager theEjb = getBusManager();
        try {
            
            return theEjb.getCustGrpByInternalLimitType(internalLimitType);
        }
        catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException caught!" + e.toString());
        }

    }
	
	public void updateCustGrpLimitAmount(ICustGrpIdentifier[] grpObjList) throws CustGrpIdentifierException {
        SBCustGrpIdentifierBusManager theEjb = getBusManager();
        try {
            
            theEjb.updateCustGrpLimitAmount(grpObjList);
        }
        catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException caught!" + e.toString());
        }

    }
	
	public void updateCustGrpLimitAmount(List grpIDList, Amount lmtAmt) throws CustGrpIdentifierException {
        SBCustGrpIdentifierBusManager theEjb = getBusManager();
        try {
            
            theEjb.updateCustGrpLimitAmount(grpIDList, lmtAmt);
        }
        catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException caught!" + e.toString());
        }

    }	
	
	public ICustGrpIdentifier getCustGrpIdentifierByGrpID(Long grpID) throws CustGrpIdentifierException {
        SBCustGrpIdentifierBusManager theEjb = getBusManager();
        try {
            
            return theEjb.getCustGrpIdentifierByGrpID( grpID );
        }
        catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException caught!" + e.toString());
        }
    }

		
    /**
     * helper method to get an ejb object to CustGrpIdentifierBusManager business manager session bean.
     *
     * @return SBCustGrpIdentifierBusManagerBusManager ejb object
     * @throws CustGrpIdentifierException
     *          on errors encountered
     */

    protected SBCustGrpIdentifierBusManager getBusManager() throws CustGrpIdentifierException {
        SBCustGrpIdentifierBusManager theEjb = (SBCustGrpIdentifierBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CUST_GRP_IDENTIFIER_BUS_MANAGER_JNDI, SBCustGrpIdentifierBusManagerHome .class.getName());

        if (theEjb == null)
            throw new CustGrpIdentifierException("SBCustGrpIdentifierBusManager for Staging is null!");

        return theEjb;
    }




      private void Debug(String msg) {
    	  DefaultLogger.debug(this,"CustGrpIdentifierBusManagerImpl = " + msg);
    }


}
