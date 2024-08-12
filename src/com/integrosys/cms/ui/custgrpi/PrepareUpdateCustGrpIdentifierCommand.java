package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.ArrayList;

public class PrepareUpdateCustGrpIdentifierCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
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
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
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
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String event = (String) inputMap.get("event");
        ICustGrpIdentifier form_custGrpIdentifierObj = (ICustGrpIdentifier) inputMap.get(CustGroupUIHelper.form_custGrpIdentifierObj);
        ICustGrpIdentifierTrxValue trxValue = (ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue);

        try {
            if (trxValue == null) {
                trxValue = new OBCustGrpIdentifierTrxValue();
                form_custGrpIdentifierObj = trxValue.getStagingCustGrpIdentifier();
            }
            if (event != null && event.equals("read")) {
                resultMap.put(CustGroupUIHelper.form_custGrpIdentifierObj, trxValue.getCustGrpIdentifier());
            } else {
                resultMap.put(CustGroupUIHelper.form_custGrpIdentifierObj, trxValue.getStagingCustGrpIdentifier());
            }

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        //Andy Wong, 3 July 2008: calculate BGEL based on internal limit to b applied selection
        String param2 = "";
        if (CustGroupUIHelper.INT_LMT_CREDIT_RATE.equals(form_custGrpIdentifierObj.getInternalLmt())) {
            if (form_custGrpIdentifierObj.getGroupCreditGrade() != null
                    && form_custGrpIdentifierObj.getGroupCreditGrade().length > 0) {
                param2 = form_custGrpIdentifierObj.getGroupCreditGrade()[0].getRatingCD();
                form_custGrpIdentifierObj.setLastReviewDt(form_custGrpIdentifierObj.getGroupCreditGrade()[0].getRatingDt());
            }
        } else {
            param2 = CustGroupUIHelper.INT_LMT_PARAM_BANK_GRP;
        }

        Amount grpLmt = CustGrpIdentifierUIHelper.getGroupLimit(form_custGrpIdentifierObj.getInternalLmt(), param2);
        if (grpLmt.getCurrencyCodeAsObject() == null) {
            grpLmt.setAmountAsBigDecimal(new BigDecimal(0));
            grpLmt.setCurrencyCode("MYR");
        }
        form_custGrpIdentifierObj.setGroupLmt(grpLmt);
        form_custGrpIdentifierObj.setGroupCurrency(grpLmt.getCurrencyCode());

        //derive limit amount for sub limit when GP5 or Capital Fund % selected
        if (!StringUtils.equals(event, "read")
                && !ArrayUtils.isEmpty(trxValue.getStagingCustGrpIdentifier().getGroupSubLimit())
                && (CustGroupUIHelper.INT_LMT_CAP_FUND_PERCENT.equals(form_custGrpIdentifierObj.getInternalLmt())
                || CustGroupUIHelper.INT_LMT_GP5_REQ.equals(form_custGrpIdentifierObj.getInternalLmt()))) {
            IGroupSubLimit [] newList = new IGroupSubLimit[trxValue.getStagingCustGrpIdentifier().getGroupSubLimit().length];
            for (int i = 0; i < trxValue.getStagingCustGrpIdentifier().getGroupSubLimit().length; i++) {
                IGroupSubLimit iGroupSubLimit = trxValue.getStagingCustGrpIdentifier().getGroupSubLimit()[i];
                if(!CustGroupUIHelper.INT_LMT_PARAM_BANK_GRP.equals(iGroupSubLimit.getDescription()))
                {
                    iGroupSubLimit.setLimitAmt(CustGrpIdentifierUIHelper.getGroupLimit(form_custGrpIdentifierObj.getInternalLmt(), iGroupSubLimit.getDescription()));
                }
                newList[i] = iGroupSubLimit;
            }
            trxValue.getStagingCustGrpIdentifier().setGroupSubLimit(newList);
        }

        resultMap.put(CustGroupUIHelper.form_custGrpIdentifierObj, form_custGrpIdentifierObj);
        resultMap.put(CustGroupUIHelper.service_groupTrxValue, trxValue);
        resultMap.put(event, event);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");
        return returnMap;
    }


    private void Debug(String msg) {
    	DefaultLogger.debug(this,"PrepareUpdateCounterpartyCommand = " + msg);
    }


}
