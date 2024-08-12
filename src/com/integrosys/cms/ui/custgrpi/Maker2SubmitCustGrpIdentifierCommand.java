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
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;

import java.util.HashMap;

/**
 * This class implements command
 */
public class Maker2SubmitCustGrpIdentifierCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"customerID", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                 {"description", "java.lang.String", REQUEST_SCOPE},   // remarks is missing so setting again
        };
    }


    /**
     * Defines a two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return new String[][]{
                {"request.ITrxValue", "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", REQUEST_SCOPE},
        };
    }


    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here reading for Company Borrower is done.
     *
     * @param inputMap is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
        DefaultLogger.debug(this, "Inside of doExecute()");
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        ICustGrpIdentifierTrxValue resultValue = null;
        ICustGrpIdentifier formObj = (ICustGrpIdentifier) inputMap.get(CustGroupUIHelper.form_custGrpIdentifierObj);
        ICustGrpIdentifierTrxValue value = (ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue);
        ITrxContext trxContext = (ITrxContext) inputMap.get("theOBTrxContext");
        String description = (String) inputMap.get("description");

        if (value == null) {
            value = new OBCustGrpIdentifierTrxValue();
        }
        IGroupCreditGrade[] stagingGrpCreditGrade = null;
        IGroupSubLimit[]    stagingGrpSubLimit = null;
        IGroupMember[]      stagingGrpMember = null;

        try {

        	//trxContext.setCustomer(getCustomerType(ICategoryEntryConstant.COMMON_CODE_LE_ID_TYPE_GCIF, value));
            trxContext.setCustomer(getCustomerGroup(formObj));
      
            trxContext.setLimitProfile(null);
            //trxContext.setTrxCountryOrigin(getTrxCountryOrigin(inputMap));
            trxContext.setTrxCountryOrigin(formObj.getGroupCounty());

            ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
            if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
                Debug("status is Rejected");
                ICustGrpIdentifier staging = formObj;
                if (description != null && !"".equals(description.trim())){
                    value.setRemarks(description);
                    trxContext.setRemarks(description) ;
                }
                resultValue = proxy.maker2SubmitCustGrpIdentifier(trxContext, value, staging);
                Debug("CurrentTrxHistoryID " + resultValue.getCurrentTrxHistoryID());
            } else {
                ICustGrpIdentifier staging = value.getStagingCustGrpIdentifier();
                staging.setIsBGEL(formObj.getIsBGEL());
                staging.setInternalLmt(formObj.getInternalLmt());
                staging.setGroupAccountMgrCode(formObj.getGroupAccountMgrCode());
                staging.setGroupRemarks(formObj.getGroupRemarks());
                staging.setLastReviewDt(formObj.getLastReviewDt());
                staging.setGroupCurrency(formObj.getGroupCurrency());

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


                CustGroupUIHelper.printChildMembersAct(value);
                CustGroupUIHelper.printChildMembersStg(value);

                resultValue = proxy.maker2SubmitCustGrpIdentifier(trxContext, value, staging);
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

    /**
     * Used to get the customer id from the Group member
     * @param CustomerType
     * @param value
     * @return
     * @throws Exception
     *//*

    private ICMSCustomer getCustomerType(String CustomerType, ICustGrpIdentifierTrxValue value) throws Exception {
        ICMSCustomer aICMSCustomer = null;
        if (value == null) {
            return aICMSCustomer;
        }
        ICustGrpIdentifier stagingDetails = value.getStagingCustGrpIdentifier();
        if (stagingDetails == null) {
            return aICMSCustomer;
        }

        IGroupMember[]  objList = stagingDetails.getGroupMember();
        if (objList != null && objList.length > 0) {
            for (int i = 0; i < objList.length; i++) {
                if (!CustGroupUIHelper.DELETED.equals(objList[i].getStatus())) {
                    if (CustGroupUIHelper.ENTITY_TYPE_CUSTOMER.equals(objList[i].getEntityType())) {
                        return getCustomer(objList[i].getEntityID());
                    }

                }
            }
        }
        return aICMSCustomer;

    }
*/

    /**
     * @param customerID
     * @return
     * @throws Exception
     *//*

    private ICMSCustomer getCustomer(long customerID) throws Exception {
        ICustomerProxy proxy = CustomerProxyFactory.getProxy();
        ICMSCustomer aICMSCustomer = null;
        if (customerID == ICMSConstant.LONG_INVALID_VALUE) {
            return aICMSCustomer;
        }
        try {
            aICMSCustomer = proxy.getCustomer(customerID);
        } catch (Exception e) {

        }
        return aICMSCustomer;

    }*/


    private void Debug(String msg) {
    	DefaultLogger.debug(this,"Maker2SubmitCustGrpIdentifierCommand = " + msg);
    }


}
