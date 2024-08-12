/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.proxy.bankentitybranch.IBankEntityBranchParamProxy;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParamList;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 5, 2008
 * Time: 11:32:34 PM
 * Desc: Checker reject bank entity branch param command
 */

//public class CheckerRejectBankEntityBranchParamCmd extends AbstractCommand {
public class CheckerRejectBankEntityBranchParamCmd extends BankEntityBranchParamCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"bankEntityBranchTrxValue", "com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"BankEntityBranchParamForm", "java.lang.Object", FORM_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try {
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            IBankEntityBranchTrxValue trxValue = (IBankEntityBranchTrxValue) map.get("bankEntityBranchTrxValue");
            IBankEntityBranchParamList paramList = (IBankEntityBranchParamList) map.get("BankEntityBranchParamForm");

            trxValue.setRemarks(paramList.getRemarks());
//            IBankEntityBranchParamProxy proxy = BankEntityBranchParamProxyFactory.getProxyManager();
            IBankEntityBranchParamProxy proxy = getBankEntityBranchParamProxy();
            IBankEntityBranchTrxValue res = proxy.checkerRejectBankEntityBranch(ctx, trxValue);
            result.put("request.ITrxValue", res);
        }
        catch (Exception ex) {
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

}