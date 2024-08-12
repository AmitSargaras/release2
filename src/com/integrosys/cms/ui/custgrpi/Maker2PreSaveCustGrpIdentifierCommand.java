package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.bus.*;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.HashMap;

public class Maker2PreSaveCustGrpIdentifierCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"currencyCD", "java.lang.String", REQUEST_SCOPE},
                {"groupLmt", "java.lang.String", REQUEST_SCOPE},
                {"groupLmt", "java.lang.String", REQUEST_SCOPE},
                {"lastReviewDt", "java.lang.String", REQUEST_SCOPE},
                {"approvedBy", "java.lang.String", REQUEST_SCOPE},

                {"event", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
        }
        );
    }

    public String[][] getResultDescriptor() {
        return new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
        };
    }


    public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside of doExecute()");
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String event = (String) inputMap.get("event");
        String from_event = (String) inputMap.get("from_event");

        Debug(" event =" + event);
        Debug(" from_event= " + from_event);

        ICustGrpIdentifier formObj = (ICustGrpIdentifier) inputMap.get(CustGroupUIHelper.form_custGrpIdentifierObj);
        ICustGrpIdentifier formObj1 = getMGEL (inputMap);
        ICustGrpIdentifierTrxValue trxValue = (ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue);
        Debug(" getMGEL, GroupCurrency = " + formObj1.getGroupCurrency());

        if (trxValue == null){
            trxValue = new OBCustGrpIdentifierTrxValue();
        } else {
            Debug("trxValue is not null");
        }

        this.mergeMGEL(trxValue.getStagingCustGrpIdentifier(), formObj);
        resultMap.put("request.ITrxValue", trxValue);

        DefaultLogger.debug(this, "Going out of doExecute()");

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }


    private void mergeMGEL(ICustGrpIdentifier staging, ICustGrpIdentifier formObj) {
        if (formObj == null){
            Debug("formObj = null");
            return  ;
        }else if (staging == null) {
             Debug("staging = null");
            return  ;
        }

        staging.setGroupCurrency(formObj.getGroupCurrency());
        staging.setBusinessUnit(formObj.getBusinessUnit());
        staging.setLastReviewDt(formObj.getLastReviewDt());
        staging.setGroupLmt(formObj.getGroupLmt());
        staging.setApprovedBy(formObj.getApprovedBy());
        staging.setGroupRemarks(formObj.getGroupRemarks());


    }

     private ICustGrpIdentifier getMGEL(HashMap inputMap ) {
         ICustGrpIdentifier formObj =new OBCustGrpIdentifier();

         String currencyCD = (String) inputMap.get("currencyCD");
         String approvedBy = (String) inputMap.get("approvedBy");

         formObj.setGroupCurrency(currencyCD);
         formObj.setApprovedBy(approvedBy);

         return  formObj;

    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"Maker2PreSaveCustGrpIdentifierCommand = " + msg);
    }

}
