package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.*;

public class PrepareCustGrpIdentifierCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
        };
    }

    /**
     * Defines a two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},

                {"countryCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},

                {"groupTypeCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"groupTypeLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},

                {"accountMgmtCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"accountMgmtLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},

//                {"groupAccountMgrCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
//                {"groupAccountMgrLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},

                {"currencyCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"currencyLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},

                {"businessUnitCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"businessUnitLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},

                {"memberRelCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"memberRelLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},

                {"relationCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"relationLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},

                {"internalLmtCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"internalLmtLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
        }
        );
    }


    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param inputMap is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap inputMap) throws CommandValidationException, CommandProcessingException, AccessDeniedException {

        DefaultLogger.debug(this, "Inside  doExecute()");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String event = (String) inputMap.get("event");

        ICustGrpIdentifierTrxValue groupTrxValue = (ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue);
        //For Group
        //getGroupAccountMgrCodes("groupAccountMgrCodes", "groupAccountMgrLabels", result);

        getCommonCodes("groupTypeCodes", "groupTypeLabels", ICMSUIConstant.GRP_TYPE, result);
        getCountryCodes("countryCodes", "countryLabels", result);
        getCommonCodes("accountMgmtCodes", "accountMgmtLabels", ICMSUIConstant.ACC_MGNT, result);

        //for MGEL
        getCommonCodes("currencyCodes", "currencyLabels", ICMSUIConstant.ISO_CUR, result);
        getCommonCodes("businessUnitCodes", "businessUnitLabels", ICMSUIConstant.BIZ_UNIT, result);
        getCommonCodes("internalLmtCodes", "internalLmtLabels", ICMSUIConstant.INT_LMT_APPLY, result);

        //for Group Members
        getCommonCodes("memberRelCodes", "memberRelLabels", ICMSUIConstant.GENT_REL, result);
        getCommonCodes("relationCodes", "relationLabels", ICMSUIConstant.ENT_REL, result);

        result.put("event", event);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");
        return returnMap;
    }


    //getCommonCodes
    private void getCommonCodes(String codeValue, String namelabels, String codeType, HashMap result) {
        CommonCodeList commonCode = CommonCodeList .getInstance(null, null, codeType, null);
        Collection values = commonCode.getCommonCodeValues();
        Collection labels = commonCode.getCommonCodeLabels();
        if (values == null) {
            values = new ArrayList();
        }
        if (labels == null) {
            labels = new ArrayList();
        }
        result.put(codeValue, values);
        result.put(namelabels, labels);

    }


    //getCountryCodes
    private void getCountryCodes(String nameValue, String namelabels, HashMap result) {
        CountryList countryList = CountryList.getInstance();
        Collection values = countryList.getCountryValues();
        Collection labels = countryList.getCountryLabels();
        if (values == null) {
            values = new ArrayList();
        }
        if (labels == null) {
            labels = new ArrayList();
        }
        result.put(nameValue, values);
        result.put(namelabels, labels);
    }

    //getgroupAccountMgrCodes
//    private void getGroupAccountMgrCodes(String nameValue, String namelabels, HashMap result) {
//        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
//        Map map = new HashMap();
//        Collection labels = null;
//        Collection values = null;
//        map.put("teamType", ICMSConstant.TEAM_TYPE_GEMS_AM_MAKER+"");
//        try {
//            map = proxy.getGroupAccountMgrCodes(map);
//            if (map != null && !map.isEmpty()) {
//                labels = (List) map.get("labels");
//                values = (List) map.get("values");
//            }
//            if (values == null) {
//                values = new ArrayList();
//            }
//            if (labels == null) {
//                labels = new ArrayList();
//            }
//            result.put(nameValue, values);
//            result.put(namelabels, labels);
//
//        } catch (Exception e) {
//        }
//    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"PrepareCustGrpIdentifierCommand = " + msg);
    }


}
