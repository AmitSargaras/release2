/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.exemptedinst.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * @author siew kheat
 *
 */
public class PrepareEICustSearchCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"aCustomerSearchCriteria", "com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria", FORM_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"offset", "java.lang.Integer", SERVICE_SCOPE},
                {"length", "java.lang.Integer", SERVICE_SCOPE}}
        );
    }

    public String[][] getResultDescriptor() {
        return new String[][]{
                {"countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"leTypeLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"leTypeValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"aCustomerSearchCriteria", "com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria", FORM_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},

        };
    }
    
    public HashMap doExecute(HashMap inputMap) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
        DefaultLogger.debug(this, "Map in PrepareCustRelSearchCommand is " + inputMap);
        
        CountryList list = CountryList.getInstance();
        ArrayList countryLabels = new ArrayList(list.getCountryLabels());
        ArrayList countryValues = new ArrayList(list.getCountryValues());

        HashMap resultMap = new HashMap();
        resultMap.put("countryLabels", countryLabels);
        resultMap.put("countryValues", countryValues);

        CommonCodeList commonCode = CommonCodeList .getInstance(null, null, ICMSUIConstant.COMMON_CODE_REF_LE_ID_TYPE, null);
        Collection leTypeValues = commonCode.getCommonCodeValues();
        Collection leTypeLabels = commonCode.getCommonCodeLabels();
        if (leTypeValues == null) {
            leTypeValues = new ArrayList();
        }
        if (leTypeLabels == null) {
            leTypeLabels = new ArrayList();
        }
        resultMap.put("leTypeValues", leTypeValues);
        resultMap.put("leTypeLabels", leTypeLabels);

        ICCICounterpartyDetailsTrxValue trxValue = (ICCICounterpartyDetailsTrxValue) inputMap.get("ICCICounterpartyDetailsTrxValue");

        //reset search criteria for validation failure case.
        resultMap.put("aCustomerSearchCriteria", inputMap.get("aCustomerSearchCriteria"));
        resultMap.put("ICCICounterpartyDetailsTrxValue", trxValue);

        HashMap returnMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        DefaultLogger.debug(this, " - END. ");
        return returnMap;
    }
    
}
