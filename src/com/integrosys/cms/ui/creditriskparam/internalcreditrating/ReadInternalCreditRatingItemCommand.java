package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.HashMap;
import java.util.List;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class ReadInternalCreditRatingItemCommand extends AbstractCommand {
public class ReadInternalCreditRatingItemCommand extends InternalCreditRatingCommand {
	public String[][] getParameterDescriptor() {
		return new String[][]{
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"indexID", "java.lang.String", REQUEST_SCOPE},
			{"fromEvent", "java.lang.String", REQUEST_SCOPE},
			{"errorEvent", "java.lang.String", REQUEST_SCOPE},
      {"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][]{	
			{"InternalCreditRatingItemForm", "java.lang.Object", FORM_SCOPE},
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		    HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
			//CountryLimitUIHelper helper = new CountryLimitUIHelper();
			IInternalCreditRatingTrxValue internalCreditRatingTrxObj = (IInternalCreditRatingTrxValue)map.get("internalCreditRatingTrxObj");
			String event = (String)map.get("event");
      int index = Integer.parseInt((String) map.get("indexID"));
            DefaultLogger.debug(this, "event is:::: " + event);
            DefaultLogger.debug(this, "index is ::::" + index);
			String errorEvent = (String)(map.get("errorEvent"));

        	// from_event will be "read" at maker view item list
        	// from_event will be "process" at checker review item list
        	// from_event will be "close" at maker close rejected item detail
        	// from_event will be null at maker update item list
        	// only for "read" case, item should be populated from the actual object
        	// for the other 3 cases item should be populated from the staging object
        	String from_event = (String) map.get("fromEvent");
        	
        	IInternalCreditRating curItem = getCurWorkingCreditRatingItem(event, from_event, index, internalCreditRatingTrxObj);
        	DefaultLogger.debug(this, "curItem is ::::" + curItem);
        	result.put("InternalCreditRatingItemForm", curItem);
		}
		catch(Exception ex)
		{
            throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
	    temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	    return temp;
	}	
	
	public IInternalCreditRating getCurWorkingCreditRatingItem(String event, String fromEvent, int index, IInternalCreditRatingTrxValue trxValue)
	{
		DefaultLogger.debug(this, "Inside Here!!!");
		DefaultLogger.debug(this, "event Here::::"+event);
		List template = null;
		if ("read".equals(fromEvent))
		{
			template = (List)trxValue.getActualICRList();
		}
		else
		{
			template = (List)trxValue.getStagingICRList();
		}
		if (template != null)
		{
			return (IInternalCreditRating)template.get(index);
		}
		return null;
	}
}
