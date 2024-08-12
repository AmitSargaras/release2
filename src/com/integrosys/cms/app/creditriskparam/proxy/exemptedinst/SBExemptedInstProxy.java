/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.proxy.exemptedinst;

import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstException;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * This is the remote interface to the SBExemptedInstProxy
 * session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface SBExemptedInstProxy extends EJBObject
{

    /**
     * Gets the Exempted Institution trx value.
     *
     * @param ctx transaction context
     * @return Exempted Institution transaction value for the type and month
     * @throws ExemptedInstException on errors encountered
     * @throws RemoteException on error during remote method call
     */
    public IExemptedInstTrxValue getExemptedInstTrxValue (ITrxContext ctx)
        throws ExemptedInstException, RemoteException;

    /**
     * Gets the Exempted Institution trx value by transaction id.
     *
     * @param ctx transaction context
     * @param trxID transaction id
     * @return Exempted Institution transaction value
     * @throws ExemptedInstException on errors encountered
     * @throws RemoteException on error during remote method call
     */
    public IExemptedInstTrxValue getExemptedInstTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws ExemptedInstException, RemoteException;

    /**
     * Maker updates a list of Exempted Institution.
     *
     * @param ctx transaction context
     * @param trxVal Exempted Institution transaction value
     * @param exemptInsts a list of Exempted Institution objects to use for updating.
     * @return updated Exempted Institution transaction value
     * @throws ExemptedInstException on errors encountered
     * @throws RemoteException on error during remote method call
     */
    public IExemptedInstTrxValue makerUpdateExemptedInst (ITrxContext ctx,
           IExemptedInstTrxValue trxVal, IExemptedInst[] exemptInsts)
    throws ExemptedInstException, RemoteException;
    

    /**
     * Maker close Exempted Institution rejected by a checker.
     *
     * @param ctx transaction context
     * @param trxVal Exempted Institution transaction value
     * @return the updated Exempted Institution transaction value
     * @throws ExemptedInstException on errors encountered
     * @throws RemoteException on error during remote method call
     */
    public IExemptedInstTrxValue makerCloseExemptedInst (ITrxContext ctx,
           IExemptedInstTrxValue trxVal) throws ExemptedInstException, RemoteException;

    /**
     * Checker approve Exempted Institution updated by a maker.
     *
     * @param ctx transaction context
     * @param trxVal Exempted Institution transaction value
     * @return updated transaction value
     * @throws ExemptedInstException on errors encountered
     * @throws RemoteException on error during remote method call
     */
    public IExemptedInstTrxValue checkerApproveUpdateExemptedInst (
        ITrxContext ctx, IExemptedInstTrxValue trxVal)
    throws ExemptedInstException, RemoteException;

    /**
     * Checker reject Exempted Institution updated by a maker.
     *
     * @param ctx transaction context
     * @param trxVal Exempted Institution transaction value
     * @return updated Exempted Institution transaction value
     * @throws ExemptedInstException on errors encountered
     * @throws RemoteException on error during remote method call
     */
    public IExemptedInstTrxValue checkerRejectUpdateExemptedInst (
        ITrxContext ctx, IExemptedInstTrxValue trxVal)
    throws ExemptedInstException, RemoteException;

}