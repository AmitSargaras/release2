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

/**
 * This class implements command
 */
public class SubmitCustGrpIdentifierCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"customerID", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
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
        IGroupSubLimit[] stagingGrpSubLimit = null;
        IGroupMember[] stagingGrpMember = null;

        try {

            //trxContext.setCustomer(getCustomerType(ICategoryEntryConstant.COMMON_CODE_LE_ID_TYPE_GCIF, value));
            trxContext.setCustomer(getCustomerGroup(formObj));

            trxContext.setLimitProfile(null);
            //trxContext.setTrxCountryOrigin(getTrxCountryOrigin(inputMap));
            trxContext.setTrxCountryOrigin(formObj.getGroupCounty());

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

            Map chkMap = checkGroupByName(value, formObj);
            if (!chkMap.isEmpty()) {
                exceptionMap.put("errorGrpNameExist", new ActionMessage("error.group.name.exist", chkMap.get("name"), chkMap.get("id")));
            }

            if (exceptionMap.isEmpty()) {

                ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
                if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
                    Debug("status is Rejected");

                    ICustGrpIdentifier staging_rec = value.getStagingCustGrpIdentifier();
                    if (staging_rec != null) {
                        stagingGrpCreditGrade = staging_rec.getGroupCreditGrade();
                        stagingGrpSubLimit = staging_rec.getGroupSubLimit();
                        stagingGrpMember = staging_rec.getGroupMember();
                    }

                    // set all the Child records
                    ICustGrpIdentifier staging = formObj;
                    staging.setGroupCreditGrade(stagingGrpCreditGrade);
                    staging.setGroupSubLimit(stagingGrpSubLimit);
                    staging.setGroupMember(stagingGrpMember);

                    if (description != null && !"".equals(description.trim())) {
                        value.setRemarks(description);
                        trxContext.setRemarks(description);
                    }

                    resultValue = proxy.makerSubmitCustGrpIdentifier(trxContext, value, staging);
                    Debug("CurrentTrxHistoryID " + resultValue.getCurrentTrxHistoryID());
                } else {

                    ICustGrpIdentifier staging = value.getStagingCustGrpIdentifier();
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


                    CustGroupUIHelper.printChildMembersAct(value);
                    CustGroupUIHelper.printChildMembersStg(value);

                    resultValue = proxy.makerSubmitCustGrpIdentifier(trxContext, value, staging);
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

    /**
     * Used to get the customer id from the Group member
     *
     * @param CustomerType
     * @param value
     * @return
     * @throws Exception
     */

    /*private ICMSCustomer getCustomerType(String CustomerType, ICustGrpIdentifierTrxValue value) throws Exception {
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

    }*/


    /**
     * @return
     * @throws Exception
     */

    /*private ICMSCustomer getCustomer(long customerID) throws Exception {
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
           }
    */
    private void Debug(String msg) {
    	DefaultLogger.debug(this,"SubmitCustGrpIdentifierCommand = " + msg);
    }

    private Map checkGroupByName(ICustGrpIdentifierTrxValue trxVal, ICustGrpIdentifier formObj) {
        Map returnMap = new HashMap();
        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
        ICustGrpIdentifier details = formObj;//trxVal.getStagingCustGrpIdentifier();

        if (details == null) {
            details = trxVal.getStagingCustGrpIdentifier();
        }

        if (details == null || details.getGroupName() == null) {
            return returnMap;
        }

        CustGrpIdentifierSearchCriteria criteria = new CustGrpIdentifierSearchCriteria();
        criteria.setGroupName(details.getGroupName());
        criteria.setSearchType("ByGroup");
        criteria.setStartIndex(0);
        criteria.setNItems(20);
        criteria.setExactSearch(true);
//        DefaultLogger.debug(this,"checkGroupByName - Group Name = " + criteria.getGroupName());
        try {
            SearchResult result = proxy.searchGroup(criteria);

            if (result != null && result.getResultList() != null) {
                List v = (List) result.getResultList();
                if (v != null) {
                    for (int i = 0; i < v.size(); i++) {
                        OBCustomerSearchResult obj = (OBCustomerSearchResult) v.get(i);
                        if (obj != null && obj.getGrpNo() != null) {
                            if (Long.valueOf(obj.getGrpNo()).longValue() != details.getGrpNo()) {
//                                DefaultLogger.debug(this,"obj.getGroupName() = " + obj.getGroupName());
//                                DefaultLogger.debug(this,"obj.getGrpNo() = " + obj.getGrpNo());
//                                DefaultLogger.debug(this,"details.getGrpNo() = " + details.getGrpNo());
                                returnMap.put("name", obj.getGroupName());
                                returnMap.put("id", obj.getGrpNo());
                            }
                        }
                    }
                }
            }


/*
            if (result ==null || result.getResultList() ==null){
                DefaultLogger.debug(this,", result/result.getResultList() = null");
                return returnMap;
            }

            List v = (List) result.getResultList() ;
            if (v==null || v.size() ==0 ){
                  DefaultLogger.debug(this,", List =0 ");
                  return returnMap;
            }

            DefaultLogger.debug(this,"OBCustomerSearchResult  " +
                    "Compare Record getGrpNo()  = " + details.getGrpNo() +
                    ",  getGroupName()  = " + details.getGroupName());

            for (int i=0; i < v.size();i++){
                 OBCustomerSearchResult obj = (OBCustomerSearchResult)  v.get(i);
                 if (obj ==null )  {
                     DefaultLogger.debug(this,"First time the Group is going to Create ");
                     return returnMap;
                 }
                 if ( obj.getGrpNo() != null){

                     if (Long.valueOf(obj.getGrpNo()).longValue() == details.getGrpNo()) {
                        // DefaultLogger.debug(this,"The Group is Updating.. so No need to check ");
                     }else{
                         DefaultLogger.debug(this," Group name Exist ," +
                                        " GrpNo  = " + Long.valueOf(obj.getGrpNo()).longValue() +
                                        ",  GroupName()  = " + details.getGroupName());

                         returnMap.put("name",details.getGroupName());
                         returnMap.put("id",details.getGrpNo());
                         return returnMap;
                     }
                 }
            }
*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;

    }

}