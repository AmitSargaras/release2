package com.integrosys.cms.ui.custgrpi.groupcreditgrade;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.manualinput.aa.RatingList;
import com.integrosys.cms.ui.manualinput.aa.RatingTypeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 8, 2007
 * Time: 6:50:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrepareGroupCreditGradeCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"typeCD", "java.lang.String", REQUEST_SCOPE},
        });

    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},

                {"currencyCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"currencyLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                //{"creditGradeTypeCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                //{"creditGradeTypeLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"ratingCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"ratingLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"creditGradeType", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE},

        });

    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside of doExecute()");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        ICustGrpIdentifierTrxValue itrxValue = ((ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue));
        String event = (String) map.get("from_event");
        String typeCD = (String) map.get("typeCD");

        // get the currency list
        getCommonCodes("currencyCodes", "currencyLabels", ICMSUIConstant.ISO_CUR, result);


        //get internal rating
        getCommonCodes("ratingCodes", "ratingLabels", ICMSUIConstant.CREDIT_GRADE_RATING, result);

        //Ratings
        //getCommonCodes("ratingCodes", "ratingLabels", ICMSUIConstant.RATING_TYPE, result);
        //getRatingCodes("ratingCodes", "ratingCodes", typeCD, result);

        result.put("from_event", event);
        result.put(CustGroupUIHelper.service_groupTrxValue, itrxValue);

        //Andy Wong, 2 July 2008: default credit grade to Internal Credit Rating according to FS
        result.put("creditGradeType", "INTERNAL");

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Going out of doExecute()");
        return temp;
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

    //getRatingCodes
    //getRatingCodes
//    private void getRatingCodes(String nameValue, String namelabels, String codeType, HashMap result){
//         Collection values = null;
//         Collection labels = null;
//         if(codeType != null){
//            RatingList ratingCpt = RatingList.getInstance (codeType);
//            values = ratingCpt.getRatingListID();
//            labels = ratingCpt.getRatingListValue ();
//         }
//         if (values == null) {
//            values = new ArrayList();
//        }
//        if (labels == null) {
//            labels = new ArrayList();
//        }
//         result.put(nameValue, values);
//         result.put(namelabels, labels);
//    }

    //getCountryCodes
    private void getCountryCodes(String nameValue, String namelabels, String codeType, HashMap result) {
        CountryList countryList = CountryList.getInstance();
        ArrayList labels = new ArrayList(countryList.getCountryLabels());
        ArrayList values = new ArrayList(countryList.getCountryValues());
        result.put(nameValue, values);
        result.put(namelabels, labels);

    }
}
