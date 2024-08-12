package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PrepareCustGrpIdentifierSearchCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
        }
        );
    }

    public String[][] getResultDescriptor() {
        return new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},

                {"countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},

                {"leTypeValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"leTypeLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},

        };
    }


    public HashMap doExecute(HashMap inputMap) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
        DefaultLogger.debug(this, " - BEGIN. ");
        HashMap resultMap = new HashMap();
        HashMap returnMap = new HashMap();
        HashMap exceptionMap = new HashMap();


        CountryList list = CountryList.getInstance();
        ArrayList countryLabels = new ArrayList(list.getCountryLabels());
        ArrayList countryValues = new ArrayList(list.getCountryValues());
        resultMap.put("countryLabels", countryLabels);
        resultMap.put("countryValues", countryValues);

//        CommonCodeList commonCode = CommonCodeList .getInstance(null, null, ICMSUIConstant.CCI_LE_ID_TYPE, null);
        CommonCodeList commonCode = CommonCodeList.getInstance(null, null, ICMSUIConstant.COMMON_CODE_REF_LE_ID_TYPE, null);
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

        ICustGrpIdentifierTrxValue itrxValue = ((ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue));
        resultMap.put(CustGroupUIHelper.service_groupTrxValue, itrxValue);

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        DefaultLogger.debug(this, " - END. ");
        return returnMap;
    }


}
