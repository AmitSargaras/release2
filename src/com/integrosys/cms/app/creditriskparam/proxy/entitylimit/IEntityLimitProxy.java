/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.proxy.entitylimit;

import com.integrosys.cms.app.creditriskparam.bus.entitylimit.EntityLimitException;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.util.List;

/**
 * This interface defines the services that are available in CMS with
 * respect to the Entity Limit life cycle.
 *
 * @author  $Author: skchai $<br>
 * @version $Revision: $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface IEntityLimitProxy
{

    /**
     * Gets the Entity Limit trx value.
     *
     * @param ctx transaction context
     * @return Entity Limit transaction value for the type and month
     * @throws EntityLimitException on errors encountered
     */
    public IEntityLimitTrxValue getEntityLimitTrxValue (ITrxContext ctx)
        throws EntityLimitException;

    /**
     * Gets the Entity Transaction trx value by transaction id.
     *
     * @param ctx transaction context
     * @param trxID transaction id
     * @return Entity Limit transaction value
     * @throws EntityLimitException on errors encountered
     */
    public IEntityLimitTrxValue getEntityLimitTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws EntityLimitException;

    /**
     * Maker updates a list of Entity Limit.
     *
     * @param ctx transaction context
     * @param trxVal Entity Limit transaction value
     * @param entityLimits a list of Entity Limit objects to use for updating.
     * @return updated Entity Limit transaction value
     * @throws EntityLimitException on errors encountered
     */
    public IEntityLimitTrxValue makerUpdateEntityLimit (ITrxContext ctx,
           IEntityLimitTrxValue trxVal, IEntityLimit[] entityLimits)
    throws EntityLimitException;
    

    /**
     * Maker close Entity Limit rejected by a checker.
     *
     * @param ctx transaction context
     * @param trxVal Entity Limit transaction value
     * @return the updated Entity Limit transaction value
     * @throws EntityLimitException on errors encountered
     */
    public IEntityLimitTrxValue makerCloseEntityLimit (ITrxContext ctx,
           IEntityLimitTrxValue trxVal) throws EntityLimitException;

    /**
     * Checker approve Entity Limit updated by a maker.
     *
     * @param ctx transaction context
     * @param trxVal Entity Limit transaction value
     * @return updated transaction value
     * @throws EntityLimitException on errors encountered
     */
    public IEntityLimitTrxValue checkerApproveUpdateEntityLimit (
        ITrxContext ctx, IEntityLimitTrxValue trxVal)
    throws EntityLimitException;

    /**
     * Checker reject Entity Limit updated by a maker.
     *
     * @param ctx transaction context
     * @param trxVal Entity Limit transaction value
     * @return updated Entity Limit transaction value
     * @throws EntityLimitException on errors encountered
     */
    public IEntityLimitTrxValue checkerRejectUpdateEntityLimit (
        ITrxContext ctx, IEntityLimitTrxValue trxVal)
    throws EntityLimitException;

    public List retrieveLimitBookedCustomer(List customerIdList) throws EntityLimitException;
}
