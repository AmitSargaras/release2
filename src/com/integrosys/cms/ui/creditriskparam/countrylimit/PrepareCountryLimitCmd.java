/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.OBCountryLimitParam;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareCountryLimitCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},			
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{			
			{"CountryLimitForm", "java.lang.Object", FORM_SCOPE},			
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
        try
		{	
			String event = (String)(map.get("event"));
			
			CountryLimitUIHelper helper = new CountryLimitUIHelper();
			Locale locale = (Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
			ICountryLimitTrxValue trxValue = (ICountryLimitTrxValue)(map.get("countryLimitTrxObj"));
			ICountryLimitParam curCountryLimit = this.getCountryLimitParam(trxValue, event);		
			
			result.put("CountryLimitForm", curCountryLimit);					
			
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
	
	private ICountryLimitParam getCountryLimitParam(ICountryLimitTrxValue trxValue, String event)
	{
		if (trxValue == null)
		{
			return new OBCountryLimitParam();
		}
		if (EventConstant.EVENT_READ.equals(event))
		{
			return trxValue.getCountryLimitParam();
		}
		else
		{
			ICountryLimitParam curCountryLimit = trxValue.getStagingCountryLimitParam();
			if( curCountryLimit == null ) {
				curCountryLimit = new OBCountryLimitParam();
			}
			return curCountryLimit;
		}
	}
}
