/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.proxy.entitylimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.EntityLimitException;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimitBusManager;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.EntityLimitTrxControllerFactory;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.OBEntityLimitTrxValue;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingDAO;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingException;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

import java.util.List;

/**
 * This class facades the IEntityLimitProxy implementation by delegating
 * the handling of requests to an ejb session bean.
 *
 * @author  $Author: skchai $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
//public class EntityLimitProxyImpl extends AbstractEntityLimitProxy
public class EntityLimitProxyImpl implements IEntityLimitProxy
{

    private IEntityLimitBusManager entityLimitBusManager;

    private IEntityLimitBusManager stagingEntityLimitBusManager;

    private ITrxControllerFactory entityLimitTrxControllerFactory;

    public IEntityLimitBusManager getEntityLimitBusManager() {
        return entityLimitBusManager;
    }

    public void setEntityLimitBusManager(IEntityLimitBusManager entityLimitBusManager) {
        this.entityLimitBusManager = entityLimitBusManager;
    }

    public IEntityLimitBusManager getStagingEntityLimitBusManager() {
        return stagingEntityLimitBusManager;
    }

    public void setStagingEntityLimitBusManager(IEntityLimitBusManager stagingEntityLimitBusManager) {
        this.stagingEntityLimitBusManager = stagingEntityLimitBusManager;
    }

    public ITrxControllerFactory getEntityLimitTrxControllerFactory() {
        return entityLimitTrxControllerFactory;
    }

    public void setEntityLimitTrxControllerFactory(ITrxControllerFactory entityLimitTrxControllerFactory) {
        this.entityLimitTrxControllerFactory = entityLimitTrxControllerFactory;
    }

    /**
     * Helper method to contruct transaction value.
     *
     * @param ctx of type ITrxContext
     * @param trxValue of type ITrxValue
     * @return transaction value
     */
    private IEntityLimitTrxValue constructTrxValue(ITrxContext ctx,
    	IEntityLimitTrxValue trxValue)
    {
		trxValue.setTrxContext(ctx);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_ENTITY_LIMIT);
        return trxValue;
    }

    /**
     * Helper method to operate transactions.
     *
     * @param trxVal is of type ITrxValue
     * @param param is of type ITrxParameter
     * @throws EntityLimitException on errors encountered
     */
    private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws EntityLimitException
    {
        if (trxVal == null) {
            throw new EntityLimitException("IEntityLimitTrxValue is null!");
        }

        try {
            ITrxController controller = null;

            if (trxVal instanceof IEntityLimitTrxValue) {
                controller = getEntityLimitTrxControllerFactory().getController(trxVal, param);
            }

            if (controller == null) {
                throw new EntityLimitException("ITrxController is null!");
            }

            ITrxResult result = controller.operate(trxVal, param);
            ITrxValue obj = result.getTrxValue();
            return obj;
        }
        catch (EntityLimitException e) {
            e.printStackTrace();
            throw e;
        }
        catch (TransactionException e) {
            e.printStackTrace();
            throw new EntityLimitException("TransactionException caught! " + e.toString(), e);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new EntityLimitException("Exception caught! " + e.toString(), e);
        }
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.entitylimit.IEntityLimitProxy#getEntityLimitTrxValue
    */
    public IEntityLimitTrxValue getEntityLimitTrxValue (ITrxContext ctx)
        throws EntityLimitException
    {
//        try {
//            SBEntityLimitProxy proxy = getProxy();
//            return proxy.getEntityLimitTrxValue (ctx);
//        }
//        catch (EntityLimitException e) {
//            DefaultLogger.error (this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error (this, "", e);
//            throw new EntityLimitException ("Exception caught at getEntityLimitTrxValue: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_ENTITY_LIMIT);
		IEntityLimitTrxValue trxValue = new OBEntityLimitTrxValue();

		return (IEntityLimitTrxValue) operate (constructTrxValue (ctx, trxValue), param);                    
    }


    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.entitylimit.IEntityLimitProxy#getEntityLimitTrxValueByTrxID
    */
   public IEntityLimitTrxValue getEntityLimitTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws EntityLimitException
    {
//        try {
//            SBEntityLimitProxy proxy = getProxy();
//            return proxy.getEntityLimitTrxValueByTrxID (ctx, trxID);
//        }
//        catch (EntityLimitException e) {
//            DefaultLogger.error (this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error (this, "", e);
//            throw new EntityLimitException ("Exception caught at getEntityLimitTrxValueByTrxID: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_ENTITY_LIMIT_BY_TRXID);
		IEntityLimitTrxValue trxValue = new OBEntityLimitTrxValue();
		trxValue.setTransactionID (trxID);
		return (IEntityLimitTrxValue) operate (constructTrxValue (ctx, trxValue), param);
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.entitylimit.IEntityLimitProxy#makerUpdateEntityLimit
    */
    public IEntityLimitTrxValue makerUpdateEntityLimit (ITrxContext ctx,
        IEntityLimitTrxValue trxVal, IEntityLimit[] entityLimits) throws EntityLimitException
    {
//        try {
//             SBEntityLimitProxy proxy = getProxy();
//             return proxy.makerUpdateEntityLimit (ctx, trxVal, entityLimits);
//         }
//         catch (EntityLimitException e) {
//             DefaultLogger.error (this, "", e);
//             throw e;
//         }
//         catch (Exception e) {
//             DefaultLogger.error (this, "", e);
//             throw new EntityLimitException ("Exception caught at makerUpdateEntityLimit: " + e.toString());
//         }
        if( entityLimits == null )
		{
			throw new EntityLimitException("Entity Limit is null");
		}
		if( trxVal == null )
		{
			trxVal = new OBEntityLimitTrxValue();
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if ( trxVal.getStatus().equals(ICMSConstant.STATE_ND) ||
			 trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE))
		{

			param.setAction (ICMSConstant.ACTION_MAKER_CREATE_ENTITY_LIMIT);
		}
		else
		{
			param.setAction (ICMSConstant.ACTION_MAKER_UPDATE_ENTITY_LIMIT);
		}

		trxVal.setStagingEntityLimit (entityLimits);
		return (IEntityLimitTrxValue) operate (constructTrxValue (ctx, trxVal), param);
    } 

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.entitylimit.IEntityLimitProxy#makerCloseEntityLimit
    */
    public IEntityLimitTrxValue makerCloseEntityLimit (ITrxContext ctx,
        IEntityLimitTrxValue trxVal) throws EntityLimitException
    {
//        try {
//             SBEntityLimitProxy proxy = getProxy();
//             return proxy.makerCloseEntityLimit (ctx, trxVal);
//         }
//         catch (EntityLimitException e) {
//             DefaultLogger.error (this, "", e);
//             throw e;
//         }
//         catch (Exception e) {
//             DefaultLogger.error (this, "", e);
//             throw new EntityLimitException ("Exception caught at makerCloseEntityLimit: " + e.toString());
//         }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_ENTITY_LIMIT);
		}
		else {
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_ENTITY_LIMIT);
		}
		return (IEntityLimitTrxValue) operate (constructTrxValue (ctx, trxVal), param);
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.entitylimit.IEntityLimitProxy#checkerApproveUpdateEntityLimit
    */
    public IEntityLimitTrxValue checkerApproveUpdateEntityLimit (
        ITrxContext ctx, IEntityLimitTrxValue trxVal) throws EntityLimitException
    {
//        try {
//             SBEntityLimitProxy proxy = getProxy();
//             return proxy.checkerApproveUpdateEntityLimit (ctx, trxVal);
//         }
//         catch (EntityLimitException e) {
//             DefaultLogger.error (this, "", e);
//             throw e;
//         }
//         catch (Exception e) {
//             DefaultLogger.error (this, "", e);
//             throw new EntityLimitException ("Exception caught at checkerApproveUpdateEntityLimit: " + e.toString());
//         }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_APPROVE_ENTITY_LIMIT);
		return (IEntityLimitTrxValue) operate (constructTrxValue (ctx, trxVal), param);
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.entitylimit.IEntityLimitProxy#checkerRejectUpdateEntityLimit
    */
    public IEntityLimitTrxValue checkerRejectUpdateEntityLimit (
        ITrxContext ctx, IEntityLimitTrxValue trxVal) throws EntityLimitException
    {
//        try {
//             SBEntityLimitProxy proxy = getProxy();
//             return proxy.checkerRejectUpdateEntityLimit (ctx, trxVal);
//         }
//         catch (EntityLimitException e) {
//             DefaultLogger.error (this, "", e);
//             throw e;
//         }
//         catch (Exception e) {
//             DefaultLogger.error (this, "", e);
//             throw new EntityLimitException ("Exception caught at checkerRejectUpdateEntityLimit: " + e.toString());
//         }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_REJECT_ENTITY_LIMIT);
		return (IEntityLimitTrxValue) operate (constructTrxValue(ctx, trxVal), param);
    }

    public List retrieveLimitBookedCustomer(List customerIdList) throws EntityLimitException {
//        try {
//            SBEntityLimitProxy proxy = getProxy();
//            return proxy.retrieveLimitBookedCustomer(customerIdList);
//        }
//        catch (EntityLimitException e) {
//            DefaultLogger.error (this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error (this, "", e);
//            throw new EntityLimitException ("Exception caught at retrieveLimitBookedCustomer: " + e.toString());
//        }
        try {
            LimitBookingDAO dao = new LimitBookingDAO();
            return dao.retrieveLimitBookedCustomer(customerIdList);
        } catch (LimitBookingException e) {
            e.printStackTrace();
            throw new EntityLimitException ("Exception caught at retrieveLimitBookedCustomer: " + e.toString());
        }
    }

    /**
     * Method to rollback a transaction. Not implemented at online proxy level.
     *
     * @throws EntityLimitException for any errors encountered
     */
//    protected void rollback() throws EntityLimitException
//    {}

    /**
     * Helper method to get ejb object of interestrate proxy session bean.
     *
     * @return interestrate proxy ejb object
     */
//    private SBEntityLimitProxy getProxy() throws EntityLimitException
//    {
//        SBEntityLimitProxy proxy = (SBEntityLimitProxy) BeanController.getEJB (
//            ICMSJNDIConstant.SB_ENTITY_LIMIT_PROXY_JNDI, SBEntityLimitProxyHome.class.getName());
//
//        if (proxy == null) {
//            throw new EntityLimitException ("SBEntityLimitProxy is null!");
//        }
//        return proxy;
//    }
}