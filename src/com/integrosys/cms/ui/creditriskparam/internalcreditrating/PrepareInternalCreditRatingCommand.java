package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.OBInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class PrepareInternalCreditRatingCommand extends AbstractCommand {
public class PrepareInternalCreditRatingCommand extends InternalCreditRatingCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},			
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
			String event = (String)(map.get("event"));
		
			IInternalCreditRatingTrxValue trxValue = (IInternalCreditRatingTrxValue)(map.get("internalCreditRatingTrxObj"));
			//IInternalCreditRatingParam curList = this.getInternalCreditRatingParam(trxValue, event);		
			List curlist = null;
		
			//if("credit_rating_list".equals(event))
		//	{
			//	curlist = trxValue.getActualICRList();
		//	}else
		//	{
		//		curlist = trxValue.getStagingICRList();
		//	}
			
			IInternalCreditRatingParam creditRatingList = new OBInternalCreditRatingParam();
      creditRatingList.setInternalCreditRatingList(trxValue.getStagingICRList());

      result.put("InternalCreditRatingForm", creditRatingList);
			
			//result.put("InternalCreditRatingForm", curlist);					
			
		}
        catch(Exception ex)
		{
			ex.printStackTrace();
            throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
	   	temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	    return temp;
	}
	
}
