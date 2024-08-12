/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import java.util.HashMap;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 *
 * TODO To change the ctryLimitParam for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class MakerUpdateCountryLimitCmd extends AbstractCommand {
public class MakerUpdateCountryLimitCmd extends CountryLimitCommand {
	public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
			{"CountryLimitForm", "com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam", FORM_SCOPE},      
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
        	ICountryLimitTrxValue countryLimitTrxObj = (ICountryLimitTrxValue)map.get("countryLimitTrxObj");
			ICountryLimitParam ctryLimitParam = (ICountryLimitParam)(map.get("CountryLimitForm"));
        	CountryLimitUIHelper helper = new CountryLimitUIHelper();
		
//			ICountryLimitProxy proxy = helper.getCountryLimitProxyManager();
            ICountryLimitProxy proxy = getCountryLimitProxy();
								
			ICountryLimitTrxValue res = proxy.makerUpdateCountryLimit(ctx, countryLimitTrxObj, ctryLimitParam);
			
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
