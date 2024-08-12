/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.proxy.countrylimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitBusManager;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.CountryLimitTrxControllerFactory;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.CountryLimitException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.OBCountryLimitTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

import java.rmi.RemoteException;

/**
 * This class facades the ICountryLimitProxy implementation by delegating
 * the handling of requests to an ejb session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
//public class CountryLimitProxyImpl extends AbstractCountryLimitProxy
public class CountryLimitProxyImpl implements ICountryLimitProxy
{

    private ICountryLimitBusManager countryLimitBusManager;

    private ICountryLimitBusManager stagingCountryLimitBusManager;

    private ITrxControllerFactory ctryLimitTrxControllerFactory;

    public ICountryLimitBusManager getCountryLimitBusManager() {
        return countryLimitBusManager;
    }

    public void setCountryLimitBusManager(ICountryLimitBusManager countryLimitBusManager) {
        this.countryLimitBusManager = countryLimitBusManager;
    }

    public ICountryLimitBusManager getStagingCountryLimitBusManager() {
        return stagingCountryLimitBusManager;
    }

    public void setStagingCountryLimitBusManager(ICountryLimitBusManager stagingCountryLimitBusManager) {
        this.stagingCountryLimitBusManager = stagingCountryLimitBusManager;
    }

    public ITrxControllerFactory getCtryLimitTrxControllerFactory() {
        return ctryLimitTrxControllerFactory;
    }

    public void setCtryLimitTrxControllerFactory(ITrxControllerFactory ctryLimitTrxControllerFactory) {
        this.ctryLimitTrxControllerFactory = ctryLimitTrxControllerFactory;
    }

    /**
     * Helper method to contruct transaction value.
     *
     * @param ctx of type ITrxContext
     * @param trxValue of type ITrxValue
     * @return transaction value
     */
    private ICountryLimitTrxValue constructTrxValue(ITrxContext ctx,
    	ICountryLimitTrxValue trxValue)
    {
		trxValue.setTrxContext(ctx);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_COUNTRY_LIMIT);
        return trxValue;
    }

    /**
     * Helper method to operate transactions.
     *
     * @param trxVal is of type ITrxValue
     * @param param is of type ITrxParameter
     * @throws CountryLimitException on errors encountered
     */
    private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws CountryLimitException
    {
        if (trxVal == null) {
            throw new CountryLimitException("ICountryLimitTrxValue is null!");
        }

        try {
            ITrxController controller = null;

            if (trxVal instanceof ICountryLimitTrxValue) {
//                controller = (new CountryLimitTrxControllerFactory()).getController(trxVal, param);
                controller = getCtryLimitTrxControllerFactory().getController(trxVal, param);
            }

            if (controller == null) {
                throw new CountryLimitException("ITrxController is null!");
            }

            ITrxResult result = controller.operate(trxVal, param);
            ITrxValue obj = result.getTrxValue();
            return obj;
        }
        catch (CountryLimitException e) {
            e.printStackTrace();
//            rollback();
            throw e;
        }
        catch (TransactionException e) {
            e.printStackTrace();
//            rollback();
            throw new CountryLimitException("TransactionException caught! " + e.toString(), e);
        }
        catch (Exception e) {
            e.printStackTrace();
//            rollback();
            throw new CountryLimitException("Exception caught! " + e.toString(), e);
        }
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy#getCountryLimitTrxValue
    */
    public ICountryLimitTrxValue getCountryLimitTrxValue (ITrxContext ctx)
        throws CountryLimitException
    {
//        try {
//            SBCountryLimitProxy proxy = getProxy();
//            return proxy.getCountryLimitTrxValue (ctx);
//        }
//        catch (CountryLimitException e) {
//            DefaultLogger.error (this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error (this, "", e);
//            throw new CountryLimitException ("Exception caught at getCountryLimitTrxValue: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_COUNTRY_LIMIT);
		ICountryLimitTrxValue trxValue = new OBCountryLimitTrxValue();

		return (ICountryLimitTrxValue) operate (constructTrxValue (ctx, trxValue), param);
    }


    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy#getCountryLimitTrxValueByTrxID
    */
   public ICountryLimitTrxValue getCountryLimitTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws CountryLimitException
    {
//        try {
//            SBCountryLimitProxy proxy = getProxy();
//            return proxy.getCountryLimitTrxValueByTrxID (ctx, trxID);
//        }
//        catch (CountryLimitException e) {
//            DefaultLogger.error (this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error (this, "", e);
//            throw new CountryLimitException ("Exception caught at getCountryLimitTrxValueByTrxID: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_COUNTRY_LIMIT_BY_TRXID);
		ICountryLimitTrxValue trxValue = new OBCountryLimitTrxValue();
		trxValue.setTransactionID (trxID);
		return (ICountryLimitTrxValue) operate (constructTrxValue (ctx, trxValue), param);
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy#makerUpdateCountryLimit
    */
    public ICountryLimitTrxValue makerUpdateCountryLimit (ITrxContext ctx,
        ICountryLimitTrxValue trxVal, ICountryLimitParam countryLimit) throws CountryLimitException
    {
//        try {
//             SBCountryLimitProxy proxy = getProxy();
//             return proxy.makerUpdateCountryLimit (ctx, trxVal, countryLimit);
//         }
//         catch (CountryLimitException e) {
//             DefaultLogger.error (this, "", e);
//             throw e;
//         }
//         catch (Exception e) {
//             DefaultLogger.error (this, "", e);
//             throw new CountryLimitException ("Exception caught at makerUpdateCountryLimit: " + e.toString());
//         }
        if( countryLimit == null )
		{
			throw new CountryLimitException("Country Limit is null");
		}
		if( trxVal == null )
		{
			trxVal = new OBCountryLimitTrxValue();
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_MAKER_UPDATE_COUNTRY_LIMIT);

		trxVal.setStagingCountryLimitParam (countryLimit);
		return (ICountryLimitTrxValue) operate (constructTrxValue (ctx, trxVal), param);
    } 

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy#makerCloseCountryLimit
    */
    public ICountryLimitTrxValue makerCloseCountryLimit (ITrxContext ctx,
        ICountryLimitTrxValue trxVal) throws CountryLimitException
    {
//        try {
//             SBCountryLimitProxy proxy = getProxy();
//             return proxy.makerCloseCountryLimit (ctx, trxVal);
//         }
//         catch (CountryLimitException e) {
//             DefaultLogger.error (this, "", e);
//             throw e;
//         }
//         catch (Exception e) {
//             DefaultLogger.error (this, "", e);
//             throw new CountryLimitException ("Exception caught at makerCloseCountryLimit: " + e.toString());
//         }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_COUNTRY_LIMIT);

		return (ICountryLimitTrxValue) operate (constructTrxValue (ctx, trxVal), param);
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy#checkerApproveUpdateCountryLimit
    */
    public ICountryLimitTrxValue checkerApproveUpdateCountryLimit (
        ITrxContext ctx, ICountryLimitTrxValue trxVal) throws CountryLimitException
    {
//        try {
//             SBCountryLimitProxy proxy = getProxy();
//             return proxy.checkerApproveUpdateCountryLimit (ctx, trxVal);
//         }
//         catch (CountryLimitException e) {
//             DefaultLogger.error (this, "", e);
//             throw e;
//         }
//         catch (Exception e) {
//             DefaultLogger.error (this, "", e);
//             throw new CountryLimitException ("Exception caught at checkerApproveUpdateCountryLimit: " + e.toString());
//         }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_APPROVE_COUNTRY_LIMIT);
		return (ICountryLimitTrxValue) operate (constructTrxValue (ctx, trxVal), param);
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy#checkerRejectUpdateCountryLimit
    */
    public ICountryLimitTrxValue checkerRejectUpdateCountryLimit (
        ITrxContext ctx, ICountryLimitTrxValue trxVal) throws CountryLimitException
    {
//        try {
//             SBCountryLimitProxy proxy = getProxy();
//             return proxy.checkerRejectUpdateCountryLimit (ctx, trxVal);
//         }
//         catch (CountryLimitException e) {
//             DefaultLogger.error (this, "", e);
//             throw e;
//         }
//         catch (Exception e) {
//             DefaultLogger.error (this, "", e);
//             throw new CountryLimitException ("Exception caught at checkerRejectUpdateCountryLimit: " + e.toString());
//         }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_REJECT_COUNTRY_LIMIT);
		return (ICountryLimitTrxValue) operate (constructTrxValue(ctx, trxVal), param);
    }

    /**
     * Method to rollback a transaction. Not implemented at online proxy level.
     *
     * @throws CountryLimitException for any errors encountered
     */
//    protected void rollback() throws CountryLimitException
//    {}

    /**
     * Helper method to get ejb object of interestrate proxy session bean.
     *
     * @return interestrate proxy ejb object
     */
//    private SBCountryLimitProxy getProxy() throws CountryLimitException
//    {
//        SBCountryLimitProxy proxy = (SBCountryLimitProxy) BeanController.getEJB (
//            ICMSJNDIConstant.SB_COUNTRY_LIMIT_PROXY_JNDI, SBCountryLimitProxyHome.class.getName());
//
//        if (proxy == null) {
//            throw new CountryLimitException ("SBCountryLimitProxy is null!");
//        }
//        return proxy;
//    }
}