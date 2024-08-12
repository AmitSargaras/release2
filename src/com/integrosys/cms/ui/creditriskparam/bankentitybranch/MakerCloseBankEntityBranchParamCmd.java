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
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 7, 2008
 * Time: 4:53:34 PM
 * Desc: Maker close bank entity branch param command
 */
//public class MakerCloseBankEntityBranchParamCmd extends AbstractCommand {
public class MakerCloseBankEntityBranchParamCmd extends BankEntityBranchParamCmd {
	public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"bankEntityBranchTrxValue", "com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue", SERVICE_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
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
        try
		{
        	OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
        	IBankEntityBranchTrxValue trxValue = (IBankEntityBranchTrxValue)map.get("bankEntityBranchTrxValue");
//            IBankEntityBranchParamProxy proxy = BankEntityBranchParamProxyFactory.getProxyManager();
            IBankEntityBranchParamProxy proxy = getBankEntityBranchParamProxy();
            IBankEntityBranchTrxValue res = proxy.makerCloseBankEntityBranch(ctx, trxValue);

			result.put("request.ITrxValue", res);
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