/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.OBCountryLimitParam;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class ReadCountryLimitCmd extends AbstractCommand {
public class ReadCountryLimitCmd extends CountryLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{			
			{"trxID", "java.lang.String", REQUEST_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{           
			{"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
			{"CountryLimitForm", "java.lang.Object", FORM_SCOPE},
			{"wip", "java.lang.String", REQUEST_SCOPE},			
			{"fromEvent", "java.lang.String", REQUEST_SCOPE},
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			String event = (String)(map.get("event"));
        	CountryLimitUIHelper helper = new CountryLimitUIHelper();
        	Locale locale = (Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

//			ICountryLimitProxy proxy = helper.getCountryLimitProxyManager();
            ICountryLimitProxy proxy = getCountryLimitProxy();
			ICountryLimitTrxValue countryLimitTrxObj = null;

			String trxID = (String)(map.get("trxID"));
			
	        if (EventConstant.EVENT_PREPARE_CLOSE.equals(event) ||
	        	EventConstant.EVENT_PROCESS_UPDATE.equals(event) ||
				EventConstant.EVENT_TRACK.equals(event) ||
				EventConstant.EVENT_PROCESS.equals(event) 
				)
	        {	
				DefaultLogger.debug(this, "********** Trasanction ID IS: " + trxID);
	        	countryLimitTrxObj = proxy.getCountryLimitTrxValueByTrxID(ctx, trxID);
				result.put("fromEvent", "todo");
	        }
	        else
	        {	        	
	        	countryLimitTrxObj = proxy.getCountryLimitTrxValue( ctx );
	        }
			
	        result.put("countryLimitTrxObj", countryLimitTrxObj);
			//DefaultLogger.debug(this, "********** countryLimitTrxObj IS: " + countryLimitTrxObj);

	        if (UIUtil.checkWip(event, countryLimitTrxObj))
	        {
	        	result.put("wip", "wip");
	        }
			if (UIUtil.checkDeleteWip(event, countryLimitTrxObj))
			{
				result.put("wip", "wip");
			}
	        
	        
	        // for read event render form from original object
	        // otherwise reder form from staging object
			// for maker edit limit detail, we need to copy original object to staging
	        if (EventConstant.EVENT_PREPARE_UPDATE.equals(event))
	        {
	        	OBCountryLimitParam stgTemplate = new OBCountryLimitParam(countryLimitTrxObj.getCountryLimitParam());
	        	countryLimitTrxObj.setStagingCountryLimitParam(stgTemplate);				
				
	        }
	        ICountryLimitParam curCountryLimit = null;
	        if (EventConstant.EVENT_READ.equals(event))
	        {
	        	curCountryLimit = countryLimitTrxObj.getCountryLimitParam();
	        }
	        else
	        {
	        	curCountryLimit = countryLimitTrxObj.getStagingCountryLimitParam();				
	        }
			result.put("CountryLimitForm", curCountryLimit);					
			
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
