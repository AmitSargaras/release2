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
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.HashMap;

public class Maker2SaveCustGrpIdentifierCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"customerID", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
        }
        );
    }

    public String[][] getResultDescriptor() {
        return new String[][]{
                {"request.ITrxValue", "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", REQUEST_SCOPE},
        };
    }


    public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside of doExecute()");
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        ICustGrpIdentifierTrxValue resultValue = null;
        ICustGrpIdentifier formObj = (ICustGrpIdentifier) inputMap.get(CustGroupUIHelper.form_custGrpIdentifierObj);
        ICustGrpIdentifierTrxValue trxValue = (ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue);
        ITrxContext trxContext = (ITrxContext) inputMap.get("theOBTrxContext");

        //trxContext.setCustomer(null);
        //trxContext.setLimitProfile(null);

        if (trxValue == null) {
            Debug("trxValue is null");
            trxValue = new OBCustGrpIdentifierTrxValue();
        } else {
            Debug("trxValue is not null");
        }

        IGroupCreditGrade[] stagingGrpCreditGrade = null;
        IGroupSubLimit[]    stagingGrpSubLimit = null;
        IGroupMember[]      stagingGrpMember = null;

        try {
        	
        	trxContext.setCustomer(getCustomerGroup(formObj));
            trxContext.setLimitProfile(null);
            //trxContext.setTrxCountryOrigin(getTrxCountryOrigin(inputMap));
            trxContext.setTrxCountryOrigin(formObj.getGroupCounty());
        	
            ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
            if (trxValue.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
                Debug("status is Rejected");
                ICustGrpIdentifier staging = formObj;
                resultValue = proxy.makerSubmitCustGrpIdentifier(trxContext, trxValue, staging);
                Debug("CurrentTrxHistoryID " + resultValue.getCurrentTrxHistoryID());
            } else {

                ICustGrpIdentifier staging = trxValue.getStagingCustGrpIdentifier();
                if (staging != null) {
                    stagingGrpCreditGrade = staging.getGroupCreditGrade();
                    stagingGrpSubLimit = staging.getGroupSubLimit();
                    stagingGrpMember = staging.getGroupMember();
                }

                // staging = formObj;

                if (staging == null) {
                    staging = new OBCustGrpIdentifier();
                }
               // MGEL Info
                this.mergeMGEL(staging,formObj);

                // set all the Child records
                staging.setGroupCreditGrade(stagingGrpCreditGrade);
                staging.setGroupSubLimit(stagingGrpSubLimit);
                staging.setGroupMember(stagingGrpMember);

                resultValue = proxy.makerSaveCustGrpIdentifier(trxContext, trxValue, staging);
                Debug("CurrentTrxHistoryID " + resultValue.getCurrentTrxHistoryID());
            }

            resultMap.put("request.ITrxValue", resultValue);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        DefaultLogger.debug(this, "Going out of doExecute()");

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
    
    /**
     * Used to get the customer group 
     *
     *
     * @param value
     * @return
     * @throws Exception
     */
    
    private ICMSCustomer getCustomerGroup(ICustGrpIdentifier formObj) throws Exception {
        ICMSCustomer aICMSCustomer = null;
        if (formObj == null) {
            return aICMSCustomer;
        }
        
        aICMSCustomer = new OBCMSCustomer();
        
        //aICMSCustomer.setCustomerName(formObj.getGroupName());
        
        ICMSLegalEntity entity = new OBCMSLegalEntity();
        entity.setLegalName(formObj.getGroupName());
        entity.setLEID(formObj.getGrpNo());
        aICMSCustomer.setCMSLegalEntity(entity);
        
        return aICMSCustomer;

    }

     private void mergeMGEL(ICustGrpIdentifier staging, ICustGrpIdentifier formObj) {
        staging.setGroupCurrency(formObj.getGroupCurrency());
        staging.setBusinessUnit(formObj.getBusinessUnit());
        staging.setLastReviewDt(formObj.getLastReviewDt());
        staging.setGroupLmt(formObj.getGroupLmt());
        staging.setApprovedBy(formObj.getApprovedBy());
        staging.setGroupRemarks(formObj.getGroupRemarks());
    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"SubmitCustGrpIdentifierCommand = " + msg);
    }

}
