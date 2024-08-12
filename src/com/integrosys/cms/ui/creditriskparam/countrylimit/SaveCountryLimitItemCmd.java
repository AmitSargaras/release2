/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimit;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.OBCountryLimit;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveCountryLimitItemCmd extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"CountryLimitItemForm", "java.lang.Object", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"errorEvent", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE},
                {"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
                {"origItem", "com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimit", SERVICE_SCOPE},

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
        try {
            String event = (String) (map.get("event"));
            String from_event = (String) map.get("fromEvent");
            ICountryLimitTrxValue countryLimitTrxObj = (ICountryLimitTrxValue) map.get("countryLimitTrxObj");
            ICountryLimitParam curCountryLimit = countryLimitTrxObj.getStagingCountryLimitParam();
            //DefaultLogger.debug(this,"SaveCountryLimitItemCmd, before curCountryLimit : "+curCountryLimit);
            CountryLimitUIHelper helper = new CountryLimitUIHelper();
            
            DefaultLogger.debug(this, "Event : " + event);
            DefaultLogger.debug(this, "From Event : " + from_event);
            ICountryLimit item = (ICountryLimit) (map.get("CountryLimitItemForm"));
            //ICountryLimit origItem = (ICountryLimit)(map.get("origItem"));
            //DefaultLogger.debug(this,"SaveCountryLimitItemCmd, original item : "+origItem);
            //DefaultLogger.debug(this,"SaveCountryLimitItemCmd,item : "+item);

            String indexID = (String) map.get("indexID");
            ICountryLimit[] itemList =  curCountryLimit.getCountryLimitList();

            HashMap existingLimit = new HashMap();
            for (int i = 0; i < itemList.length; i++) {
                if (StringUtils.isNotBlank(indexID) && itemList[i].getCountryLimitID() != Long.parseLong(indexID))
                    existingLimit.put(itemList[i].getCountryCode(), null);
            }

            if (existingLimit.containsKey(item.getCountryCode()))
                exceptionMap.put("duplicateEntryError", new ActionMessage("error.countryLimit.duplicate"));

            if (exceptionMap.size() == 0) {
                DefaultLogger.debug(this, "SaveCountryLimitItemCmd, no exception ");
                if (StringUtils.isEmpty(indexID)) {
                    int arrayLength = (itemList == null ? 0 : itemList.length);
                    ICountryLimit[] newArray = new ICountryLimit[arrayLength + 1];
                    if (arrayLength != 0) {
                        System.arraycopy(itemList, 0, newArray, 0, arrayLength);
                    }

                    newArray[arrayLength] = item;
                    itemList = newArray;
                    curCountryLimit.setCountryLimitList(itemList);
                } else {
                    ICountryLimit curItem = helper.getCurWorkingCountryLimitItem(from_event, Long.parseLong(indexID), countryLimitTrxObj);
                    curItem.setCountryCode(item.getCountryCode());
                    curItem.setCountryRatingCode(item.getCountryRatingCode());
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

}
