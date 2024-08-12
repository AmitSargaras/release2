/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParamList;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 5, 2008
 * Time: 2:30:34 AM
 * Desc: Delete item from UI checkbox
 */
//public class DeleteBankEntityBranchItemCmd extends AbstractCommand {
public class DeleteBankEntityBranchItemCmd extends BankEntityBranchParamCmd {
	public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"bankEntityBranchTrxValue","com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue", SERVICE_SCOPE},
            {"BankEntityBranchParamForm", "java.lang.Object", FORM_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"BankEntityBranchParamForm", "java.lang.Object", FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
            IBankEntityBranchTrxValue trxValue = (IBankEntityBranchTrxValue)map.get("bankEntityBranchTrxValue");
            IBankEntityBranchParamList paramList = (IBankEntityBranchParamList) map.get("BankEntityBranchParamForm");

            trxValue.setStagingBankEntityBranchParam(paramList.getBankEntityBranchParams());
            paramList.setLastActionBy(trxValue.getUserInfo());
            paramList.setLastRemarks(trxValue.getRemarks());

            result.put("BankEntityBranchParamForm", paramList);
		}
		catch(Exception ex)
		{
            throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
	    temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	    return temp;
	}
}