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
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class ReturnCountryLimitCmd extends AbstractCommand {
public class ReturnCountryLimitCmd extends CountryLimitCommand {
	public String[][] getParameterDescriptor() {
        return (new String[][]{
        	{"fromEvent", "java.lang.String", REQUEST_SCOPE},				
        	{"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"fromEvent", "java.lang.String", REQUEST_SCOPE},			
        	{"CountryLimitForm", "java.lang.Object", FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
			ICountryLimitTrxValue countryLimitTrxValue = (ICountryLimitTrxValue)(map.get("countryLimitTrxObj"));
			String fromEvent = (String)map.get("fromEvent");
            // for read event render form from original object
	        // otherwise reder form from staging object
	        ICountryLimitParam curCountryLimit = null;
			
	        if (EventConstant.EVENT_READ.equals(fromEvent))
	        {
	        	curCountryLimit = countryLimitTrxValue.getCountryLimitParam();
	        }
	        else
	        {
	        	curCountryLimit = countryLimitTrxValue.getStagingCountryLimitParam();
	        }
			result.put("CountryLimitForm", curCountryLimit);
			if (fromEvent != null && !fromEvent.trim().equals(""))
			{
				result.put("fromEvent", fromEvent);
			}				
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
