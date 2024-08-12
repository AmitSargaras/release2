/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryRating;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.OBCountryRating;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareCountryRatingCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{ 
			{"event", "java.lang.String", REQUEST_SCOPE},			
			{"fromEvent", "java.lang.String", REQUEST_SCOPE},
			{"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{			
			{"CountryRatingForm", "java.lang.Object", FORM_SCOPE},
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try
		{
			CountryLimitUIHelper helper = new CountryLimitUIHelper();
			String event = (String)(map.get("event"));
			String from_event = (String)(map.get("fromEvent"));				

			ICountryLimitTrxValue countryLimitTrxObj = (ICountryLimitTrxValue)map.get("countryLimitTrxObj");
			ICountryRating curItem = null;
			
			if (EventConstant.EVENT_PREPARE_UPDATE.equals(event))
			{				
				ICountryLimitParam curCountryLimit = countryLimitTrxObj.getStagingCountryLimitParam();
				
				result.put("CountryRatingForm", curCountryLimit.getCountryRatingList());
			}	        
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
