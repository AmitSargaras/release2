/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryRating;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadCountryRatingCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][]{
			{"event", "java.lang.String", REQUEST_SCOPE},			
			{"fromEvent", "java.lang.String", REQUEST_SCOPE},
            {"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][]{	
			{"CountryRatingForm", "java.lang.Object", FORM_SCOPE},
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
			CountryLimitUIHelper helper = new CountryLimitUIHelper();
			ICountryLimitTrxValue countryLimitTrxObj = (ICountryLimitTrxValue)map.get("countryLimitTrxObj");
			String event = (String)map.get("event");
        	//int index = Integer.parseInt((String) map.get("indexID"));
            
        	// from_event will be "read" at maker view item list
        	// from_event will be "process" at checker review item list
        	// from_event will be "close" at maker close rejected item detail
        	// from_event will be null at maker update item list
        	// only for "read" case, item should be populated from the actual object
        	// for the other 3 cases item should be populated from the staging object
        	String from_event = (String) map.get("fromEvent");
        	ICountryRating[] curItem = helper.getCurWorkingCountryRating(from_event, countryLimitTrxObj);
        	result.put("CountryRatingForm", curItem);
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
