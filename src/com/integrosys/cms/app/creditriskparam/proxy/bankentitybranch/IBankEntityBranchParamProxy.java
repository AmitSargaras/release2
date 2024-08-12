package com.integrosys.cms.app.creditriskparam.proxy.bankentitybranch;

import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.BankEntityBranchParamException;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 2, 2008
 * Time: 11:58:48 PM
 * Desc: Proxy interface for bank entity branch param proxy
 */
public interface IBankEntityBranchParamProxy {

    public IBankEntityBranchTrxValue getBankEntityBranchTrxValue(ITrxContext ctx)
            throws BankEntityBranchParamException;

    public IBankEntityBranchTrxValue getBankEntityBranchTrxValueByTrxID(ITrxContext ctx, String trxID)
            throws BankEntityBranchParamException;

    public IBankEntityBranchTrxValue makerUpdateBankEntityBranch(ITrxContext ctx, IBankEntityBranchTrxValue trxVal, Collection bankEntityBranch)
            throws BankEntityBranchParamException;

    public IBankEntityBranchTrxValue makerCloseBankEntityBranch(ITrxContext ctx, IBankEntityBranchTrxValue trxVal) throws BankEntityBranchParamException;

    public IBankEntityBranchTrxValue checkerApproveBankEntityBranch(
            ITrxContext ctx, IBankEntityBranchTrxValue trxVal) throws BankEntityBranchParamException;

    public IBankEntityBranchTrxValue checkerRejectBankEntityBranch(
            ITrxContext ctx, IBankEntityBranchTrxValue trxVal) throws BankEntityBranchParamException;

}
