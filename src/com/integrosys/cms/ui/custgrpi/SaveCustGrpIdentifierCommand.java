package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.bus.*;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.app.customer.bus.*;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import org.apache.struts.action.ActionMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveCustGrpIdentifierCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"customerID", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
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
        IGroupSubLimit[] stagingGrpSubLimit = null;
        IGroupMember[] stagingGrpMember = null;

        try {
            if (formObj.getMasterGroupInd() && formObj.getMasterGroupEntityID() != ICMSConstant.LONG_INVALID_VALUE) {
                ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
                List groupIDList = new ArrayList();
                try {
                    groupIDList.add(new Long(formObj.getMasterGroupEntityID()).toString());
                    List bankGroupMasterListSearchResult = proxy.retrieveMasterGroupBySubGroupID(groupIDList);
                    if (bankGroupMasterListSearchResult.size() > 0) {
                        exceptionMap.put("masterGroupInd", new ActionMessage("error.group.mastergroup.subgroup", formObj.getGroupName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            Map chkMap = checkGroupByName(trxValue, formObj);
            if (!chkMap.isEmpty()) {
                exceptionMap.put("errorGrpNameExist", new ActionMessage("error.group.name.exist", chkMap.get("name"), chkMap.get("id")));
            }

            if (exceptionMap.isEmpty()) {
                ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();

                trxContext.setCustomer(getCustomerGroup(formObj));
                trxContext.setLimitProfile(null);
                //trxContext.setTrxCountryOrigin(getTrxCountryOrigin(inputMap));
                trxContext.setTrxCountryOrigin(formObj.getGroupCounty());


                if (trxValue.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
                    Debug("status is Rejected");
                    ICustGrpIdentifier staging = formObj;
                    resultValue = proxy.makerSaveCustGrpIdentifier(trxContext, trxValue, staging);
                    Debug("CurrentTrxHistoryID " + resultValue.getCurrentTrxHistoryID());
                } else {

                    ICustGrpIdentifier staging = trxValue.getStagingCustGrpIdentifier();
                    if (staging != null) {
                        stagingGrpCreditGrade = staging.getGroupCreditGrade();
                        stagingGrpSubLimit = staging.getGroupSubLimit();
                        stagingGrpMember = staging.getGroupMember();
                    }

                    staging = formObj;
                    if (staging == null) {
                        staging = new OBCustGrpIdentifier();
                    }

                    // set all the Child records
                    staging.setGroupCreditGrade(stagingGrpCreditGrade);
                    staging.setGroupSubLimit(stagingGrpSubLimit);
                    staging.setGroupMember(stagingGrpMember);


                    resultValue = proxy.makerSaveCustGrpIdentifier(trxContext, trxValue, staging);
                    Debug("CurrentTrxHistoryID " + resultValue.getCurrentTrxHistoryID());
                }

                resultMap.put("request.ITrxValue", resultValue);
            }

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        DefaultLogger.debug(this, "Going out of doExecute()");

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }

    /*private String getTrxCountryOrigin (HashMap inputMap){
           String country =null;
           ICommonUser user = (ICommonUser)inputMap.get(IGlobalConstant.USER);
           ITeam userTeam = (ITeam)inputMap.get(IGlobalConstant.USER_TEAM);
           TOP_LOOP: for(int i=0;i<userTeam.getTeamMemberships().length;i++){//parse team membership to validate user first
               for(int j=0; j<userTeam.getTeamMemberships()[i].getTeamMembers().length;j++){  //parse team memebers to get the team member first..
                   if(userTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID()==user.getUserID()){
                       if(userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID()== ICMSConstant.TEAM_TYPE_GEMS_AM_MAKER){
                           country = user.getCountry() ;
                           break TOP_LOOP;
                       }
                   }
               }
           }
           return country;
       }*/


    /**
     * Used to get the customer group
     *
     * @param formObj
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

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"SubmitCustGrpIdentifierCommand = " + msg);
    }

    private Map checkGroupByName(ICustGrpIdentifierTrxValue trxVal, ICustGrpIdentifier formObj) {
        Map returnMap = new HashMap();
        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
        ICustGrpIdentifier details = formObj;//trxVal.getStagingCustGrpIdentifier();

        if (details == null) {
            //details = formObj;
            details = trxVal.getStagingCustGrpIdentifier();
        }

        if (details == null || details.getGroupName() == null) {
            DefaultLogger.debug(this, "Staging ICustGrpIdentifier or Group Name  null  ");
            return returnMap;
        }

        CustGrpIdentifierSearchCriteria criteria = new CustGrpIdentifierSearchCriteria();
        criteria.setGroupName(details.getGroupName());
        criteria.setSearchType("ByGroup");
        criteria.setStartIndex(0);
        criteria.setNItems(20);
        criteria.setExactSearch(true);
        // DefaultLogger.debug(this,"checkGroupByName  Group Name = " + criteria.getGroupName());
        // DefaultLogger.debug(this,"checkGroupByName  actual.getGrpNo  = " + actual.getGrpNo());
        try {
            SearchResult result = proxy.searchGroup(criteria);

            if (result != null && result.getResultList() != null) {
                List v = (List) result.getResultList();
                if (v != null) {
                    for (int i = 0; i < v.size(); i++) {
                        OBCustomerSearchResult obj = (OBCustomerSearchResult) v.get(i);
                        if (obj != null && obj.getGrpNo() != null) {
                            if (Long.valueOf(obj.getGrpNo()).longValue() != details.getGrpNo()) {
                                returnMap.put("name", obj.getGroupName());
                                returnMap.put("id", obj.getGrpNo());
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;

    }

}