/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/ExemptFacilityBusManagerImpl.java,v 1.2 2003/08/11 06:36:51 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

import java.rmi.RemoteException;

/**
NOT USED
 */
public class ExemptFacilityBusManagerImpl implements IExemptFacilityBusManager {

    public IExemptFacilityGroup getExemptFacilityGroup()
            throws ExemptFacilityException {
        try {
            return getSbFeedBusManager().getExemptFacilityGroup();
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new ExemptFacilityException("RemoteException", e);
        }
    }

    public IExemptFacilityGroup createExemptFacilityGroup(IExemptFacilityGroup liq) throws ExemptFacilityException {
    try {
            return getSbFeedBusManager().createExemptFacilityGroup(liq);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new ExemptFacilityException("RemoteException", e);
        }
    }

 public IExemptFacilityGroup updateExemptFacilityGroup(IExemptFacilityGroup group)
            throws ExemptFacilityException {
        try {
            return getSbFeedBusManager().updateExemptFacilityGroup(group);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new ExemptFacilityException("RemoteException", e);
        }
    }

    public IExemptFacilityGroup getExemptedFacilityGroupByGroupID(long groupID) throws ExemptFacilityException {
     try {
            return getSbFeedBusManager().getExemptFacilityGroup();
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new ExemptFacilityException("RemoteException", e);
        }
    }

/*
    public IExemptFacility deleteExemptFacility(IExemptFacility group)
            throws ExemptFacilityException {
        try {
            return getSbFeedBusManager().deleteExemptFacility(group);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new ExemptFacilityException("RemoteException", e);
        }
    }
*/


    /**
     * Helper method to get the bus manager remote interface.
     * Will be overridden in subclass.
     * @return The bus manager remote interface.
     */
    protected SBExemptFacilityBusManager getSbFeedBusManager() {
        return (SBExemptFacilityBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_EXEMPT_FACILITY_BUS_MANAGER_JNDI,
                SBExemptFacilityBusManagerHome.class.getName());
    }
}
