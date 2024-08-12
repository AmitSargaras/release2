package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;

//public class DeleteInternalCreditRatingItemCommand extends AbstractCommand {
public class DeleteInternalCreditRatingItemCommand extends InternalCreditRatingCommand {    
	public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"internalCreditRatingTrxObj","com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
            {"InternalCreditRatingForm", "java.lang.Object", FORM_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"InternalCreditRatingForm", "java.lang.Object", FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
            IInternalCreditRatingTrxValue trxValue = (IInternalCreditRatingTrxValue)map.get("internalCreditRatingTrxObj");
            IInternalCreditRatingParam paramList = (IInternalCreditRatingParam) map.get("InternalCreditRatingForm");

            trxValue.setStagingICRList(paramList.getInternalCreditRatingList());
            result.put("InternalCreditRatingForm", paramList);
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