/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParamList;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.OBBankEntityBranchParamList;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.OBBankEntityBranchParam;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 3, 2008
 * Time: 4:53:34 PM
 * Desc: Set param item list context for display
 */
//public class ReturnBankEntityBranchParamCmd extends AbstractCommand {
public class ReturnBankEntityBranchParamCmd extends BankEntityBranchParamCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"bankEntityBranchTrxValue", "com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue", SERVICE_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return new String[][]{// Produce all the feed entries.
                {"BankEntityBranchParamForm", "java.lang.Object", FORM_SCOPE}};
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try {
            IBankEntityBranchTrxValue trxValue = (IBankEntityBranchTrxValue) (map.get("bankEntityBranchTrxValue"));
            IBankEntityBranchParamList branchParamList = new OBBankEntityBranchParamList();
            branchParamList.setBankEntityBranchParams(trxValue.getStagingBankEntityBranchParam());
            branchParamList.setLastActionBy(trxValue.getUserInfo());
            branchParamList.setLastRemarks(trxValue.getRemarks());

            //persist the remarks transfer from add item page
            if(map.get("remarks")!=null)
                branchParamList.setRemarks(map.get("remarks").toString());

            result.put("BankEntityBranchParamForm", branchParamList);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}