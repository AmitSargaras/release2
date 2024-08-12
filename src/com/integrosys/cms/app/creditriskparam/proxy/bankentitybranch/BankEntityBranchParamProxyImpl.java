package com.integrosys.cms.app.creditriskparam.proxy.bankentitybranch;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.BankEntityBranchParamException;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParamBusManager;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.BankEntityBranchTrxControllerFactory;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.OBBankEntityBranchTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 3, 2008
 * Time: 9:57:54 AM
 * Desc: Bank entity branch param proxy implementation
 */
//public class BankEntityBranchParamProxyImpl extends AbstractBankEntityBranchParamProxy {
public class BankEntityBranchParamProxyImpl implements IBankEntityBranchParamProxy {

    private IBankEntityBranchParamBusManager bankEntityBranchParamBusManager;

    private IBankEntityBranchParamBusManager stagingBankEntityBranchParamBusManager;

    private ITrxControllerFactory bkEntityBrchPrmTrxControllerFactory;

    public IBankEntityBranchParamBusManager getBankEntityBranchParamBusManager() {
        return bankEntityBranchParamBusManager;
    }

    public void setBankEntityBranchParamBusManager(IBankEntityBranchParamBusManager bankEntityBranchParamBusManager) {
        this.bankEntityBranchParamBusManager = bankEntityBranchParamBusManager;
    }

    public IBankEntityBranchParamBusManager getStagingBankEntityBranchParamBusManager() {
        return stagingBankEntityBranchParamBusManager;
    }

    public void setStagingBankEntityBranchParamBusManager(IBankEntityBranchParamBusManager stagingBankEntityBranchParamBusManager) {
        this.stagingBankEntityBranchParamBusManager = stagingBankEntityBranchParamBusManager;
    }

    public ITrxControllerFactory getBkEntityBrchPrmTrxControllerFactory() {
        return bkEntityBrchPrmTrxControllerFactory;
    }

    public void setBkEntityBrchPrmTrxControllerFactory(ITrxControllerFactory bkEntityBrchPrmTrxControllerFactory) {
        this.bkEntityBrchPrmTrxControllerFactory = bkEntityBrchPrmTrxControllerFactory;
    }

    /**
     * Helper method to contruct transaction value.
     *
     * @param ctx of type ITrxContext
     * @param trxValue of type ITrxValue
     * @return transaction value
     */
    private IBankEntityBranchTrxValue constructTrxValue(ITrxContext ctx,
    	IBankEntityBranchTrxValue trxValue)
    {
		trxValue.setTrxContext(ctx);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_BANK_ENTITY_BRANCH_PARAM);
        return trxValue;
    }

    /**
     * Helper method to operate transactions.
     */
    private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws BankEntityBranchParamException {
        if (trxVal == null) {
            throw new BankEntityBranchParamException("IBankEntityBranchTrxValue is null!");
        }

        try {
            ITrxController controller = null;

            if (trxVal instanceof IBankEntityBranchTrxValue) {
                controller = getBkEntityBrchPrmTrxControllerFactory().getController(trxVal, param);
            }

            if (controller == null) {
                throw new BankEntityBranchParamException("ITrxController is null!");
            }

            ITrxResult result = controller.operate(trxVal, param);
            ITrxValue obj = result.getTrxValue();
            return obj;
        }
        catch (TransactionException e) {
            e.printStackTrace();
            throw new BankEntityBranchParamException("TransactionException caught! " + e.toString(), e);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BankEntityBranchParamException("Exception caught! " + e.toString(), e);
        }
    }

    /**
     * Gets the bank entity branch trx value.
     */
    public IBankEntityBranchTrxValue getBankEntityBranchTrxValue(ITrxContext ctx)
            throws BankEntityBranchParamException {
//        try {
//            return getProxy().getBankEntityBranchTrxValue(ctx);
//        }
//        catch (BankEntityBranchParamException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new BankEntityBranchParamException("Exception caught at getBankEntityBranchTrxValue: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_BANK_ENTITY_BRANCH);
        OBBankEntityBranchTrxValue trxValue = new OBBankEntityBranchTrxValue();
        return (IBankEntityBranchTrxValue) operate(constructTrxValue(ctx, trxValue), param);
    }

    /**
     * Gets the bank entity branch trx value by transaction id.
     */
    public IBankEntityBranchTrxValue getBankEntityBranchTrxValueByTrxID(ITrxContext ctx, String trxID)
            throws BankEntityBranchParamException {
//        try {
//            return getProxy().getBankEntityBranchTrxValueByTrxID(ctx, trxID);
//        }
//        catch (BankEntityBranchParamException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new BankEntityBranchParamException("Exception caught at getBankEntityBranchTrxValueByTrxID: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_BANK_ENTITY_BRANCH_BY_TRXID);
        OBBankEntityBranchTrxValue trxValue = new OBBankEntityBranchTrxValue();
        trxValue.setTransactionID(trxID);
        return (IBankEntityBranchTrxValue) operate(constructTrxValue(ctx, trxValue), param);
    }

    /**
     * Maker updates a list of bank entity branch param.
     */
    public IBankEntityBranchTrxValue makerUpdateBankEntityBranch(ITrxContext ctx,
                                                                 IBankEntityBranchTrxValue trxVal, Collection bankBranchParams) throws BankEntityBranchParamException {
//        try {
//            return getProxy().makerUpdateBankEntityBranch(ctx, trxVal, bankBranchParams);
//        }
//        catch (BankEntityBranchParamException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new BankEntityBranchParamException("Exception caught at makerUpdateBankEntityBranch: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_BANK_ENTITY_BRANCH);
        trxVal.setStagingBankEntityBranchParam(bankBranchParams);
        return (IBankEntityBranchTrxValue) operate(constructTrxValue(ctx, trxVal), param);
    }

    /**
     * Maker close bank entity branch param updated by him/her, or rejected by a checker.
     */
    public IBankEntityBranchTrxValue makerCloseBankEntityBranch(ITrxContext ctx,
                                                                IBankEntityBranchTrxValue trxVal) throws BankEntityBranchParamException {
//        try {
//            return getProxy().makerCloseBankEntityBranch(ctx, trxVal);
//        }
//        catch (BankEntityBranchParamException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new BankEntityBranchParamException("Exception caught at makerCloseBankEntityBranch: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_BANK_ENTITY_BRANCH);
        return (IBankEntityBranchTrxValue) operate(constructTrxValue(ctx, trxVal), param);
    }

    /**
     * Checker approve bank entity branch param updated by a maker.
     */
    public IBankEntityBranchTrxValue checkerApproveBankEntityBranch(
            ITrxContext ctx, IBankEntityBranchTrxValue trxVal) throws BankEntityBranchParamException {
//        try {
//            return getProxy().checkerApproveBankEntityBranch(ctx, trxVal);
//        }
//        catch (BankEntityBranchParamException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new BankEntityBranchParamException("Exception caught at checkerApproveUpdateBankEntityBranch: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_BANK_ENTITY_BRANCH);
        return (IBankEntityBranchTrxValue) operate(constructTrxValue(ctx, trxVal), param);
    }

    /**
     * Checker reject bank entity branch updated by a maker.
     */
    public IBankEntityBranchTrxValue checkerRejectBankEntityBranch(
            ITrxContext ctx, IBankEntityBranchTrxValue trxVal) throws BankEntityBranchParamException {
//        try {
//            return getProxy().checkerRejectBankEntityBranch(ctx, trxVal);
//        }
//        catch (BankEntityBranchParamException e) {
//            DefaultLogger.error(this, "", e);
//            throw e;
//        }
//        catch (Exception e) {
//            DefaultLogger.error(this, "", e);
//            throw new BankEntityBranchParamException("Exception caught at checkerRejectUpdateBankEntityBranch: " + e.toString());
//        }
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_BANK_ENTITY_BRANCH);
        return (IBankEntityBranchTrxValue) operate(constructTrxValue(ctx, trxVal), param);
    }

    /**
     * Helper method to get ejb object of bank entity branch param proxy session bean.
     *
     * @return bank entity branch param proxy ejb object
     */
//    private SBBankEntityBranchParamProxy getProxy() throws BankEntityBranchParamException {
//        SBBankEntityBranchParamProxy proxy = (SBBankEntityBranchParamProxy) BeanController.getEJB(
//                ICMSJNDIConstant.SB_BANK_ENTITY_BRANCH_PROXY_JNDI, SBBankEntityBranchParamProxyHome.class.getName());
//
//        if (proxy == null) {
//            throw new BankEntityBranchParamException("SBBankEntityBranchParamProxy is null!");
//        }
//        return proxy;
//    }

    /**
     * Method to rollback a transaction. Not implemented at online proxy level.
     */
//    protected void rollback() throws BankEntityBranchParamException
//    {}

}
