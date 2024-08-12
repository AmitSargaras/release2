/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.proxy.exemptedinst;

import com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.rmi.RemoteException;

/**
 * This class facades the IExemptedInstProxy implementation by delegating
 * the handling of requests to an ejb session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class ExemptedInstProxyImpl extends AbstractExemptedInstProxy
{

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#getExemptedInstTrxValue
    */
    public IExemptedInstTrxValue getExemptedInstTrxValue (ITrxContext ctx)
        throws ExemptedInstException
    {
        try {
            SBExemptedInstProxy proxy = getProxy();
            return proxy.getExemptedInstTrxValue (ctx);
        }
        catch (ExemptedInstException e) {
            DefaultLogger.error (this, "", e);
            throw e;
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new ExemptedInstException ("Exception caught at getExemptedInstTrxValue: " + e.toString());
        }
    }


    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#getExemptedInstTrxValueByTrxID
    */
   public IExemptedInstTrxValue getExemptedInstTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws ExemptedInstException
    {
        try {
            SBExemptedInstProxy proxy = getProxy();
            return proxy.getExemptedInstTrxValueByTrxID (ctx, trxID);
        }
        catch (ExemptedInstException e) {
            DefaultLogger.error (this, "", e);
            throw e;
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new ExemptedInstException ("Exception caught at getExemptedInstTrxValueByTrxID: " + e.toString());
        }
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#makerUpdateExemptedInst
    */
    public IExemptedInstTrxValue makerUpdateExemptedInst (ITrxContext ctx,
        IExemptedInstTrxValue trxVal, IExemptedInst[] exemptInsts) throws ExemptedInstException
    {
        try {
             SBExemptedInstProxy proxy = getProxy();
             return proxy.makerUpdateExemptedInst (ctx, trxVal, exemptInsts);
         }
         catch (ExemptedInstException e) {
             DefaultLogger.error (this, "", e);
             throw e;
         }
         catch (Exception e) {
             DefaultLogger.error (this, "", e);
             throw new ExemptedInstException ("Exception caught at makerUpdateExemptedInst: " + e.toString());
         }
    } 

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#makerCloseExemptedInst
    */
    public IExemptedInstTrxValue makerCloseExemptedInst (ITrxContext ctx,
        IExemptedInstTrxValue trxVal) throws ExemptedInstException
    {
        try {
             SBExemptedInstProxy proxy = getProxy();
             return proxy.makerCloseExemptedInst (ctx, trxVal);
         }
         catch (ExemptedInstException e) {
             DefaultLogger.error (this, "", e);
             throw e;
         }
         catch (Exception e) {
             DefaultLogger.error (this, "", e);
             throw new ExemptedInstException ("Exception caught at makerCloseExemptedInst: " + e.toString());
         }
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#checkerApproveUpdateExemptedInst
    */
    public IExemptedInstTrxValue checkerApproveUpdateExemptedInst (
        ITrxContext ctx, IExemptedInstTrxValue trxVal) throws ExemptedInstException
    {
        try {
             SBExemptedInstProxy proxy = getProxy();
             return proxy.checkerApproveUpdateExemptedInst (ctx, trxVal);
         }
         catch (ExemptedInstException e) {
             DefaultLogger.error (this, "", e);
             throw e;
         }
         catch (Exception e) {
             DefaultLogger.error (this, "", e);
             throw new ExemptedInstException ("Exception caught at checkerApproveUpdateExemptedInst: " + e.toString());
         }
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#checkerRejectUpdateExemptedInst
    */
    public IExemptedInstTrxValue checkerRejectUpdateExemptedInst (
        ITrxContext ctx, IExemptedInstTrxValue trxVal) throws ExemptedInstException
    {
        try {
             SBExemptedInstProxy proxy = getProxy();
             return proxy.checkerRejectUpdateExemptedInst (ctx, trxVal);
         }
         catch (ExemptedInstException e) {
             DefaultLogger.error (this, "", e);
             throw e;
         }
         catch (Exception e) {
             DefaultLogger.error (this, "", e);
             throw new ExemptedInstException ("Exception caught at checkerRejectUpdateExemptedInst: " + e.toString());
         }
    }

    /**
     * Method to rollback a transaction. Not implemented at online proxy level.
     *
     * @throws ExemptedInstException for any errors encountered
     */
    protected void rollback() throws ExemptedInstException
    {}

    /**
     * Helper method to get ejb object of interestrate proxy session bean.
     *
     * @return interestrate proxy ejb object
     */
    private SBExemptedInstProxy getProxy() throws ExemptedInstException
    {
        SBExemptedInstProxy proxy = (SBExemptedInstProxy) BeanController.getEJB (
            ICMSJNDIConstant.SB_EXEMPT_INST_PROXY_JNDI, SBExemptedInstProxyHome.class.getName());

        if (proxy == null) {
            throw new ExemptedInstException ("SBExemptedInstProxy is null!");
        }
        return proxy;
    }
}