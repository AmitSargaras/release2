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
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParam;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.struts.action.ActionMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 5, 2008
 * Time: 4:53:34 PM
 * Desc: Maker update bank entity branch param command
 */
//public class MakerUpdateBankEntityBranchParamCmd extends AbstractCommand {
public class MakerUpdateBankEntityBranchParamCmd extends BankEntityBranchParamCmd {
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

            //validate against submission without item, not applicable for complete item deletion
            if ((trxValue.getStagingBankEntityBranchParam()!=null && trxValue.getStagingBankEntityBranchParam().size()>0)
                    || (trxValue.getBankEntityBranchParam()!=null && trxValue.getBankEntityBranchParam().size()>0)) {
//                IBankEntityBranchParamProxy proxy = BankEntityBranchParamProxyFactory.getProxyManager();
                IBankEntityBranchParamProxy proxy = getBankEntityBranchParamProxy();
                IBankEntityBranchTrxValue res = proxy.makerUpdateBankEntityBranch(ctx, trxValue, paramList.getBankEntityBranchParams());
                result.put("request.ITrxValue", res);
            } else {
                exceptionMap.put("errorMsg", new ActionMessage("error.bank.entity.branch.param.empty"));
            }

        }
        catch (Exception ex) {
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}