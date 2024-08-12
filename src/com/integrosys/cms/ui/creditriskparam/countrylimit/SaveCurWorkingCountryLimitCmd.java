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
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.OBCountryLimitParam;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class SaveCurWorkingCountryLimitCmd extends AbstractCommand {
public class SaveCurWorkingCountryLimitCmd extends CountryLimitCommand {
	public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
			{"CountryLimitForm", "com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam", FORM_SCOPE},            
		});

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{			
        });
    }
    
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
		try
		{
			// just map from form to staging limit and save in trxValue object
			ICountryLimitParam template = (ICountryLimitParam)(map.get("CountryLimitForm"));
			ICountryLimitTrxValue countryLimitTrxObj = (ICountryLimitTrxValue)(map.get("countryLimitTrxObj"));
			countryLimitTrxObj.setStagingCountryLimitParam( template );
			result.put("countryLimitTrxObj", countryLimitTrxObj);		
		
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
