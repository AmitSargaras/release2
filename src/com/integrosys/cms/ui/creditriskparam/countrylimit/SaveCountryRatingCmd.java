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
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryRating;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.apache.struts.action.ActionMessage;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveCountryRatingCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"CountryRatingForm", "java.lang.Object", FORM_SCOPE},	
			{"event", "java.lang.String", REQUEST_SCOPE},			
			{"fromEvent", "java.lang.String", REQUEST_SCOPE},
			{"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
		});
	}	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException 
	{
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try
		{
        	String event = (String)(map.get("event"));
        	String from_event = (String) map.get("fromEvent");
        	ICountryLimitTrxValue countryLimitTrxObj = (ICountryLimitTrxValue)map.get("countryLimitTrxObj");
        	ICountryLimitParam curCountryLimit = countryLimitTrxObj.getStagingCountryLimitParam();
			DefaultLogger.debug(this,"Event : "+event);
        	ICountryRating[] itemList = (ICountryRating[])(map.get("CountryRatingForm"));
										
			curCountryLimit.setCountryRatingList(itemList);				
							
			//DefaultLogger.debug(this,"SaveCountryRatingCmd,curCountryLimit : "+curCountryLimit);	
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
