package com.integrosys.cms.app.creditriskparam.proxy.internalcreditrating;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingBusManager;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.InternalCreditRatingException;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.OBInternalCreditRatingTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * @author priya
 */
//public class InternalCreditRatingProxyImpl extends AbstractInternalCreditRatingProxy {
public class InternalCreditRatingProxyImpl implements IInternalCreditRatingProxy {

    private IInternalCreditRatingBusManager internalCreditRatingBusManager;

    private IInternalCreditRatingBusManager stagingInternalCreditRatingBusManager;

    private ITrxControllerFactory intCrRtTrxControllerFactory;

    public IInternalCreditRatingBusManager getInternalCreditRatingBusManager() {
        return internalCreditRatingBusManager;
    }

    public void setInternalCreditRatingBusManager(IInternalCreditRatingBusManager internalCreditRatingBusManager) {
        this.internalCreditRatingBusManager = internalCreditRatingBusManager;
    }

    public IInternalCreditRatingBusManager getStagingInternalCreditRatingBusManager() {
        return stagingInternalCreditRatingBusManager;
    }

    public void setStagingInternalCreditRatingBusManager(IInternalCreditRatingBusManager stagingInternalCreditRatingBusManager) {
        this.stagingInternalCreditRatingBusManager = stagingInternalCreditRatingBusManager;
    }

    public ITrxControllerFactory getIntCrRtTrxControllerFactory() {
        return intCrRtTrxControllerFactory;
    }

    public void setIntCrRtTrxControllerFactory(ITrxControllerFactory intCrRtTrxControllerFactory) {
        this.intCrRtTrxControllerFactory = intCrRtTrxControllerFactory;
    }

    private IInternalCreditRatingTrxValue constructTrxValue(ITrxContext ctx, IInternalCreditRatingTrxValue trxValue) {

        trxValue.setTrxContext(ctx);
        trxValue.setTransactionType(ICMSConstant.INSTANCE_INTERNAL_CREDIT_RATING);
        return trxValue;

    }

    private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws InternalCreditRatingException {

        if (trxVal == null) {
            throw new InternalCreditRatingException("IInternalCreditRatingTrxValue is null!");
        }

        try {
            ITrxController controller = null;

            if (trxVal instanceof IInternalCreditRatingTrxValue) {
                controller = getIntCrRtTrxControllerFactory().getController(trxVal, param);
            }

            if (controller == null) {
                throw new InternalCreditRatingException("ITrxController is null!");
            }

            ITrxResult result = controller.operate(trxVal, param);
            ITrxValue obj = result.getTrxValue();
            return obj;
        }
        catch (InternalCreditRatingException e) {
            e.printStackTrace();
            throw e;
        }
        catch (TransactionException e) {
            e.printStackTrace();
            throw new InternalCreditRatingException("TransactionException caught! " + e.toString(), e);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new InternalCreditRatingException("Exception caught! " + e.toString(), e);
        }

    }

    public IInternalCreditRatingTrxValue getInternalCreditRatingTrxValue(ITrxContext ctx) throws InternalCreditRatingException {
//        try {
//            SBInternalCreditRatingProxy proxy = getProxy();
//            return proxy.getInternalCreditRatingTrxValue(ctx);
//        }
//        catch (InternalCreditRatingException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new InternalCreditRatingException("Exception caught at getInternalCreditRatingTrxValue: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_INTERNAL_CREDIT_RATING);
        IInternalCreditRatingTrxValue trxValue = new OBInternalCreditRatingTrxValue();

        return (IInternalCreditRatingTrxValue) operate(constructTrxValue(ctx, trxValue), param);
    }

    public IInternalCreditRatingTrxValue getInternalCreditRatingTrxValueByTrxID(ITrxContext ctx, String trxID) throws InternalCreditRatingException {
//        try {
//            SBInternalCreditRatingProxy proxy = getProxy();
//            return proxy.getInternalCreditRatingTrxValueByTrxID(ctx, trxID);
//        }
//        catch (InternalCreditRatingException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new InternalCreditRatingException("Exception caught at getInternalCreditRatingTrxValueByTrxID: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_INTERNAL_CREDIT_RATING_BY_TRXID);
        IInternalCreditRatingTrxValue trxValue = new OBInternalCreditRatingTrxValue();
        trxValue.setTransactionID(trxID);
        return (IInternalCreditRatingTrxValue) operate(constructTrxValue(ctx, trxValue), param);
    }

    public IInternalCreditRatingTrxValue makerUpdateInternalCreditRating(ITrxContext ctx, IInternalCreditRatingTrxValue trxVal) throws InternalCreditRatingException {
//        try {
//            SBInternalCreditRatingProxy proxy = getProxy();
//            return proxy.makerUpdateInternalCreditRating(ctx, trxVal);
//        }
//        catch (InternalCreditRatingException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new InternalCreditRatingException("Exception caught at makerUpdateInternalCreditRating: " + e.toString());
//        }
        if (trxVal == null) {
            trxVal = new OBInternalCreditRatingTrxValue();
        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        if (trxVal.getStatus().equals(ICMSConstant.STATE_ND) ||
                trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
            param.setAction(ICMSConstant.ACTION_MAKER_CREATE_INTERNAL_CREDIT_RATING);
        } else {
            param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_INTERNAL_CREDIT_RATING);
        }

        return (IInternalCreditRatingTrxValue) operate(constructTrxValue(ctx, trxVal), param);
    }

    public IInternalCreditRatingTrxValue makerCloseInternalCreditRating(ITrxContext ctx, IInternalCreditRatingTrxValue trxVal) throws InternalCreditRatingException {
//        try {
//            SBInternalCreditRatingProxy proxy = getProxy();
//            return proxy.makerCloseInternalCreditRating(ctx, trxVal);
//        }
//        catch (InternalCreditRatingException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new InternalCreditRatingException("Exception caught at makerCloseInternalCreditRating: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();

        if (trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
            param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_INTERNAL_CREDIT_RATING);
        } else {
            param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_INTERNAL_CREDIT_RATING);
        }

        return (IInternalCreditRatingTrxValue) operate(constructTrxValue(ctx, trxVal), param);
    }

    public IInternalCreditRatingTrxValue checkerApproveUpdateInternalCreditRating(ITrxContext ctx, IInternalCreditRatingTrxValue trxVal) throws InternalCreditRatingException {
//        try {
//            SBInternalCreditRatingProxy proxy = getProxy();
//            return proxy.checkerApproveUpdateInternalCreditRating(ctx, trxVal);
//        }
//        catch (InternalCreditRatingException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new InternalCreditRatingException("Exception caught at checkerApproveUpdateInternalCreditRating: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_INTERNAL_CREDIT_RATING);
        return (IInternalCreditRatingTrxValue) operate(constructTrxValue(ctx, trxVal), param);
    }


    public IInternalCreditRatingTrxValue checkerRejectUpdateInternalCreditRating(ITrxContext ctx, IInternalCreditRatingTrxValue trxVal) throws InternalCreditRatingException {
//        try {
//            SBInternalCreditRatingProxy proxy = getProxy();
//            return proxy.checkerRejectUpdateInternalCreditRating(ctx, trxVal);
//        }
//        catch (InternalCreditRatingException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new InternalCreditRatingException("Exception caught at checkerRejectUpdateInternalCreditRating: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_INTERNAL_CREDIT_RATING);
        return (IInternalCreditRatingTrxValue) operate(constructTrxValue(ctx, trxVal), param);
    }

//    protected void rollback() throws InternalCreditRatingException {
//
//    }

//    private SBInternalCreditRatingProxy getProxy() throws InternalCreditRatingException {
//        SBInternalCreditRatingProxy proxy = (SBInternalCreditRatingProxy) BeanController.getEJB(
//                ICMSJNDIConstant.SB_INTERNAL_CREDIT_RATING_PROXY_JNDI, SBInternalCreditRatingProxyHome.class.getName());
//
//        if (proxy == null) {
//            throw new InternalCreditRatingException("SBInternalCreditRatingProxy is null!");
//        }
//        return proxy;
//    }

}

