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

import java.util.*;

/**
 * @author Administrator
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DeleteCountryLimitItemCmd extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
                {"deleteCountryLimit", "java.util.List", FORM_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"CountryLimitForm", "com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam", FORM_SCOPE}
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        try {
            ICountryLimitTrxValue countryLimitTrxObj = (ICountryLimitTrxValue) map.get("countryLimitTrxObj");
            ArrayList deleteItemList = (ArrayList) (map.get("deleteCountryLimit"));
            if (countryLimitTrxObj != null && countryLimitTrxObj.getStagingCountryLimitParam() != null){
                List deleteResult = deleteItem(new ArrayList(Arrays.asList(countryLimitTrxObj.getStagingCountryLimitParam().getCountryLimitList())), deleteItemList);
                countryLimitTrxObj.getStagingCountryLimitParam().setCountryLimitList((ICountryLimit[]) deleteResult.toArray(new OBCountryLimit[0]));      
            }

            result.put("CountryLimitForm", countryLimitTrxObj.getStagingCountryLimitParam());
        }
        catch (Exception ex) {
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    /**
     * Method to delete country limit item based on user checkbox selection
     *
     * @param countryLimitL
     * @param deleteL
     */
    private ArrayList deleteItem(ArrayList countryLimitL, ArrayList deleteL) {
        ArrayList deletedOB = new ArrayList();
        for (Iterator iterator = countryLimitL.iterator(); iterator.hasNext();) {
            ICountryLimit countryLimit = (ICountryLimit) iterator.next();

            for (Iterator iterator1 = deleteL.iterator(); iterator1.hasNext();) {
                long l = Long.parseLong(iterator1.next().toString());
                if (countryLimit.getCountryLimitID() == l) {
                    deletedOB.add(countryLimit);
                }
            }
        }

        if (deletedOB.size() > 0)
            countryLimitL.removeAll(deletedOB);

        return countryLimitL;
    }
}
