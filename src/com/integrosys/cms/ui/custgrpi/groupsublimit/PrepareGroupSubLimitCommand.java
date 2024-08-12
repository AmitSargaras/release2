package com.integrosys.cms.ui.custgrpi.groupsublimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import org.apache.commons.lang.StringUtils;

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
public class PrepareGroupSubLimitCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.form_groupSubLimitObj, "com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit", FORM_SCOPE},
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
                {"subLimitTypeCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"subLimitTypeLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"subLimitDescCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"subLimitDescLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"currencyCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"currencyLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
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
        IGroupSubLimit groupObj = (IGroupSubLimit) map.get(CustGroupUIHelper.form_groupSubLimitObj);
        String event = (String) map.get("from_event");

        getCommonCodes("subLimitTypeCodes", "subLimitTypeLabels", ICMSUIConstant.SUB_LIMIT_TYPE, result, null);

        //Andy Wong, 1 July 2008: desc dependent on type selected
        if(StringUtils.isNotBlank(groupObj.getSubLimitTypeCD()))
            getCommonCodes("subLimitDescCodes", "subLimitDescLabels", ICMSUIConstant.SUB_LIMIT_DESC, result, groupObj.getSubLimitTypeCD());
        else
            getCommonCodes("subLimitDescCodes", "subLimitDescLabels", null, result, null);

        getCommonCodes("currencyCodes", "currencyLabels", ICMSUIConstant.ISO_CUR, result, null);

        result.put("from_event", event);
        result.put(CustGroupUIHelper.service_groupTrxValue, itrxValue);

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Going out of doExecute()");
        return temp;
    }

    //getCommonCodes
    private void getCommonCodes(String codeValue, String namelabels, String codeType, HashMap result, String refCode) {
        CommonCodeList commonCode = CommonCodeList.getInstance(null, null, codeType, refCode);
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


}
