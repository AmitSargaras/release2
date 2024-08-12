/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/ExemptFacilityProxyImpl.java,v 1.15 2005/01/12 06:36:33 hshii Exp $
*/
package com.integrosys.cms.app.creditriskparam.proxy.exemptFacility;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.ExemptFacilityException;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.rmi.RemoteException;

public class ExemptFacilityProxyImpl
        extends AbstractExemptFacilityProxy {

    protected void rollback()
            throws ExemptFacilityException {
    }

    public IExemptFacilityGroupTrxValue getExemptFacilityTrxValue (ITrxContext ctx)
        throws ExemptFacilityException
    {
        try {
            SBExemptFacilityProxy proxy = getSbExemptFacilityProxy();
            return proxy.getExemptFacilityTrxValue (ctx);
        }
        catch (ExemptFacilityException e) {
            DefaultLogger.error (this, "", e);
            throw e;
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new ExemptFacilityException ("Exception caught at getExemptedInstTrxValue: " + e.toString());
        }
    }

   public IExemptFacilityGroupTrxValue getExemptFacilityTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws ExemptFacilityException
    {
        try {
            SBExemptFacilityProxy proxy = getSbExemptFacilityProxy();
            return proxy.getExemptFacilityTrxValueByTrxID (ctx, trxID);
        }
        catch (ExemptFacilityException e) {
            DefaultLogger.error (this, "", e);
            throw e;
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new ExemptFacilityException ("Exception caught at getExemptFacilityTrxValueByTrxID: " + e.toString());
        }
    }

    /**
     * @param anITrxContext access context required for routing, approval
     * @param aFeedGroupTrxValue transaction wrapper for the ExemptFacilityGroup object
     * @param aFeedGroup - IExemptFacilityGroup, this could have been passed in the trx value, but the intention
     * is that the caller should not have modified the trxValue, as the caller does not need to know
     * about staging settings et al.
     * @return IExemptFacilityGroupTrxValue the saved trxValue
     */
    public IExemptFacilityGroupTrxValue makerUpdateExemptFacility(ITrxContext anITrxContext,IExemptFacilityGroupTrxValue aFeedGroupTrxValue,
            IExemptFacilityGroup aFeedGroup)
            throws ExemptFacilityException {
        try {
            return getSbExemptFacilityProxy().makerUpdateExemptFacility(
                    anITrxContext, aFeedGroupTrxValue, aFeedGroup);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new ExemptFacilityException("RemoteException", e);
        }
    }

    public IExemptFacilityGroupTrxValue makerCloseExemptFacilityGroup(ITrxContext anITrxContext,IExemptFacilityGroupTrxValue aTrxValue)
            throws ExemptFacilityException {
        try {
            return getSbExemptFacilityProxy().makerCloseExemptFacility(
                    anITrxContext, aTrxValue);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new ExemptFacilityException("RemoteException", e);
        }
    }


    /**
     * @param anITrxContext access context required for routing, approval
     * @param aTrxValue transaction wrapper for the ExemptFacilityGroup object
     */
    public IExemptFacilityGroupTrxValue checkerApproveExemptFacilityGroup(ITrxContext anITrxContext,IExemptFacilityGroupTrxValue aTrxValue)
            throws ExemptFacilityException {
        try {
            return getSbExemptFacilityProxy().checkerApproveExemptFacility(anITrxContext,
                    aTrxValue);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new ExemptFacilityException("RemoteException", e);
        }
    }

    /**
     * @param anITrxContext access context required for routing, approval
     * @param aTrxValue transaction wrapper for the ExemptFacilityGroup object
     */
    public IExemptFacilityGroupTrxValue checkerRejectExemptFacilityGroup(ITrxContext anITrxContext,IExemptFacilityGroupTrxValue aTrxValue)
            throws ExemptFacilityException {
        try {
            return getSbExemptFacilityProxy().checkerRejectExemptFacility(anITrxContext,
                    aTrxValue);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw  new ExemptFacilityException("RemoteException", e);
        }
    }

    private SBExemptFacilityProxy getSbExemptFacilityProxy() {

        return (SBExemptFacilityProxy)BeanController.getEJB(
                ICMSJNDIConstant.SB_EXEMPT_FACILITY_PROXY_JNDI,
                SBExemptFacilityProxyHome.class.getName());
    }
}
