/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimit;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.OBCountryLimit;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;

import java.util.HashMap;

/**
 * @author Administrator
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
//public class PrepareCountryLimitItemCmd extends AbstractCommand {
public class PrepareCountryLimitItemCmd extends CountryLimitCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE},
                {"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"CountryLimitItemForm", "java.lang.Object", FORM_SCOPE},
                {"countryList", "java.util.List", REQUEST_SCOPE},
                {"ratingList", "java.util.List", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try {
            CountryLimitUIHelper helper = new CountryLimitUIHelper();
            String event = (String) (map.get("event"));
            String from_event = (String) (map.get("fromEvent"));
            ICountryLimitTrxValue countryLimitTrxObj = (ICountryLimitTrxValue) map.get("countryLimitTrxObj");
            ICountryLimit curItem = null;

            if (EventConstant.EVENT_PREPARE_CREATE.equals(event)) {
                curItem = new OBCountryLimit();
            } else if (EventConstant.EVENT_PREPARE_UPDATE.equals(event)
                    || EventConstant.EVENT_READ.equals(event)) {
                long index = Long.parseLong((String) map.get("indexID"));
                curItem = helper.getCurWorkingCountryLimitItem(from_event, index, countryLimitTrxObj);
            }

            result.put("CountryLimitItemForm", curItem);
            result.put("countryList", helper.getCountryList());
            result.put("ratingList", helper.getRatingList());
        }
        catch (Exception ex) {
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }


}
