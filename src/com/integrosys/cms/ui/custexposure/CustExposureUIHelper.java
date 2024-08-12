package com.integrosys.cms.ui.custexposure;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custexposure.bus.ICustExposure;
import com.integrosys.cms.app.custexposure.bus.ICustExposureGroupRelationship;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.bus.IGroupMember;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.proxy.CustRelationshipProxyFactory;
import com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;
import com.integrosys.cms.app.custrelationship.trx.OBCustRelationshipTrxValue;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;
import com.integrosys.cms.app.custrelationship.trx.shareholder.OBCustShareholderTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Feb 7, 2008
 * Time: 2:43:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustExposureUIHelper {

   public static final String DATE_FORMAT = "dd-MM-YYYY";
   public static final int CUSTOMER_EXPUSORE_PAGINATION_NITEMS = 5 ;

    public static String form_CustExposureSearchObj = "form.CustExposureSearchObj";
    public static String service_CustExposureSearchObj = "service.CustExposureSearchObj";

    public static String form_CustExposureSearchListObj = "form.CustExposureSearchListObj";
    public static String service_CustExposureSearchListObj = "service.CustExposureSearchListObj";
    public static String service_CustExposureTrxValue = "service.CustExposureTrxValue";
    public static String service_ExposureTrxValue = "service.ExposureTrxValue";
    






    public static String form_groupResult = "form.groupResult";
    public static String service_groupResult = "service.groupResult";

    public static String form_CustGrpIdentifierResultListObj = "form.CustGrpIdentifierResultListObj";

    public static String form_custGrpIdentifierObj = "form.custGrpIdentifierObj";
    public static String service_groupTrxValue = "service.groupTrxValue";

    public static String popup_form_custGrpIdentifierObj = "popup_form.custGrpIdentifierObj";
    public static String popup_service_groupTrxValue = "popup_service_groupTrxValue";

    public static String GROUPCREDITGRADE = "GROUPCREDITGRADE";
    public static String GROUPSUBLIMIT = "GROUPSUBLIMIT";
    public static String GROUPMEMBER = "GROUPMEMBER";
    public static String GROUPGROUP = "GROUPGROUP";

    public static String form_groupCreditGradeObj = "form.groupCreditGradeObj";
    public static String act_groupCreditGradeObj = "act.groupCreditGradeObj";
    public static String stg_groupCreditGradeObj = "stg.groupCreditGradeObj";


    public static String form_groupSubLimitObj = "form.groupSubLimitObj";
    public static String act_groupSubLimitObj = "act.groupSubLimitObj";
    public static String stg_groupSubLimitObj = "stg.groupSubLimitObj";

    public static String form_searchGroupResult = "form.searchGroupResult";
    public static String service_searchGroupResult = "service.searchGroupResult";



    public static String form_EntitySelectedIDMapper = "form.EntitySelectedIDMapper";
    public static String form_groupmemberObj = "form.groupmemberObj";
    public static String form_groupMemberSearchCriteria = "form.groupMemberSearchCriteria";
    public static String service_groupMemberSearchCriteria = "service.groupMemberSearchCriteria";
    public static String act_groupmemberObj = "act.groupmemberObj";
    public static String stg_groupmemberObj = "stg.groupmemberObj";

    public static String form_groupMemberSearchList = "form.groupMemberSearchList";
    public static String service_groupMemberSearchList = "service.groupMemberSearchList";


    public static String DELETED = ICMSConstant.STATE_DELETED;
    public static String ACTIVE = ICMSConstant.STATE_ACTIVE;


    public static final String ENTITY_TYPE_CUSTOMER = ICMSConstant.ENTITY_TYPE_CUSTOMER;
    public static final String ENTITY_TYPE_GROUP = ICMSConstant.ENTITY_TYPE_GROUP;

     public static final String GROUP_NAME_EXIST = "<font color=\"#FF0000\" size=\"1\">System should not allow user to create two groups with same name.</font>" ;


    /**
     * Helper method to get number of delete item
     */
    public static int getNumberOfDelete(String[] deleteIDList, int oldListLength) {
        if (deleteIDList == null || deleteIDList.length == 0) {
            return 0;
        }
        int numDelete = 0;
        if (deleteIDList.length <= oldListLength) {
            for (int i = 0; i < deleteIDList.length; i++) {
                if (Integer.parseInt(deleteIDList[i]) < oldListLength) {
                    numDelete++;
                }
            }
        }
        return numDelete;
    }

    /**
     * Helper method to delete object list from delete item list
     */
    public static Object[] deleteObjByList(Object[] oldList, Object[] newList, String[] deleteList) {
        int i = 0, j = 0;
        while (i < oldList.length) {
            if (j < deleteList.length && Integer.parseInt(deleteList[j]) == i) {
                j++;
            } else {
                newList[i - j] = oldList[i];
            }
            i++;
        }
        return newList;
    }


    /**
     *
     */

    public static void printDependantsRecords(ICustGrpIdentifier stagingObj) {
        if (stagingObj != null) {
        	DefaultLogger.debug("CustExposureUIHelper","getGroupName = " + stagingObj.getGroupName());
            IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
            IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
            IGroupMember[] list3 = stagingObj.getGroupMember();
            DefaultLogger.debug("CustExposureUIHelper","IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
            DefaultLogger.debug("CustExposureUIHelper","IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
            DefaultLogger.debug("CustExposureUIHelper","IGroupMember.length = " + (list3 == null ? 0 : list3.length));
        } else {
        	DefaultLogger.debug("CustExposureUIHelper","ICustGrpIdentifier is null");
        }
    }

    /**
     *
     * @param trxValue
     */
    public static void printChildMembers(ICustGrpIdentifierTrxValue trxValue) {

        try {
            if (trxValue != null) {
                ICustGrpIdentifier stagingObj = trxValue.getStagingCustGrpIdentifier();
                if (stagingObj != null) {
                    DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName = " + stagingObj.getGroupName());
                    IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
                    IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
                    IGroupMember[] list3 = stagingObj.getGroupMember();
                    DefaultLogger.debug("CustExposureUIHelper","IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
                    DefaultLogger.debug("CustExposureUIHelper","IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
                    DefaultLogger.debug("CustExposureUIHelper","IGroupMember.length = " + (list3 == null ? 0 : list3.length));
                } else {
                    DefaultLogger.debug("CustExposureUIHelper","stagingObj  is null");
                }
            } else {
                DefaultLogger.debug("CustExposureUIHelper","trxValue is  null ");
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param trxValue
     */
    public static void printChildMembersAct(ICustGrpIdentifierTrxValue trxValue) {
        DefaultLogger.debug("CustExposureUIHelper","----------printChildMembersAct ---------------------");
        try {
            if (trxValue != null) {
                ICustGrpIdentifier stagingObj = trxValue.getCustGrpIdentifier();
                if (stagingObj != null) {
                    DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName = " + stagingObj.getGroupName());
                    IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
                    IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
                    IGroupMember[] list3 = stagingObj.getGroupMember();
                    DefaultLogger.debug("CustExposureUIHelper","IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
                    DefaultLogger.debug("CustExposureUIHelper","IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
                    DefaultLogger.debug("CustExposureUIHelper","IGroupMember.length = " + (list3 == null ? 0 : list3.length));
                } else {
                    DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName is null");
                }
            } else {
                DefaultLogger.debug("CustExposureUIHelper","trxValue is  null ");
            }
        } catch (Exception e) {

        }

        DefaultLogger.debug("CustExposureUIHelper","----------  OK ---------------------");
    }

    /**
     *
     * @param trxValue
     */
    public static void printChildMembersStg(ICustGrpIdentifierTrxValue trxValue) {
        DefaultLogger.debug("CustExposureUIHelper","----------printChildMembersStg ---------------------");
        try {
            if (trxValue != null) {
                ICustGrpIdentifier stagingObj = trxValue.getStagingCustGrpIdentifier();
                if (stagingObj != null) {
                    DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName = " + stagingObj.getGroupName());
                    IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
                    IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
                    IGroupMember[] list3 = stagingObj.getGroupMember();
                    DefaultLogger.debug("CustExposureUIHelper","IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
                    DefaultLogger.debug("CustExposureUIHelper","IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
                    DefaultLogger.debug("CustExposureUIHelper","IGroupMember.length = " + (list3 == null ? 0 : list3.length));
                } else {
                    DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName is null");
                }
            } else {
                DefaultLogger.debug("CustExposureUIHelper","trxValue is  null ");
            }
        } catch (Exception e) {

        }
        DefaultLogger.debug("CustExposureUIHelper","----------  OK ---------------------");
    }

    /**
     *
     * @param stagingObj
     */
    public static void printChildMembersStg(ICustGrpIdentifier stagingObj) {
        DefaultLogger.debug("CustExposureUIHelper","----------printChildMembersStg - Staging ---------------------");
        if (stagingObj != null) {
            DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName = " + stagingObj.getGroupName());
            IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
            IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
            IGroupMember[] list3 = stagingObj.getGroupMember();
            DefaultLogger.debug("CustExposureUIHelper","IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
            DefaultLogger.debug("CustExposureUIHelper","IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
            DefaultLogger.debug("CustExposureUIHelper","IGroupMember.length = " + (list3 == null ? 0 : list3.length));
        } else {
            DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName is null");
        }

        DefaultLogger.debug("CustExposureUIHelper","----------  OK ---------------------");
    }

    /**
     *
     * @param stagingObj
     */

    public static void printChildMembersAct(ICustGrpIdentifier stagingObj) {
            DefaultLogger.debug("CustExposureUIHelper","----------printChildMembersAct  Actual---------------------");
            if (stagingObj != null) {
                DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName = " + stagingObj.getGroupName());
                IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
                IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
                IGroupMember[] list3 = stagingObj.getGroupMember();
                DefaultLogger.debug("CustExposureUIHelper","IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
                DefaultLogger.debug("CustExposureUIHelper","IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
                DefaultLogger.debug("CustExposureUIHelper","IGroupMember.length = " + (list3 == null ? 0 : list3.length));
            } else {
                DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName is null");
            }

            DefaultLogger.debug("CustExposureUIHelper","----------  OK ---------------------");
        }

    /**
     *
     * @param msg
     * @param stagingObj
     */
    public static void printChildMembers(String msg , ICustGrpIdentifier stagingObj) {
            DefaultLogger.debug("CustExposureUIHelper","----------printChildMembers " +msg + " --------------------");
            if (stagingObj != null) {
                DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName = " + stagingObj.getGroupName());
                IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
                IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
                IGroupMember[] list3 = stagingObj.getGroupMember();
                DefaultLogger.debug("CustExposureUIHelper","IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
                DefaultLogger.debug("CustExposureUIHelper","IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
                DefaultLogger.debug("CustExposureUIHelper","IGroupMember.length = " + (list3 == null ? 0 : list3.length));
            } else {
                DefaultLogger.debug("CustExposureUIHelper","trxValue.getGroupName is null");
            }

            DefaultLogger.debug("CustExposureUIHelper","----------  OK ---------------------");
        }

    /**
     *
     * @param subGrpID
     * @return ICustGrpIdentifierTrxValue
     */

    public static ICustGrpIdentifierTrxValue getTransValueByGroupId(String subGrpID) {

        ICustGrpIdentifierTrxValue trxValue = null;
        try {
            ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
            OBTrxContext theOBTrxContext = new OBTrxContext();
            trxValue = proxy.getCustGrpIdentifierByGrpID(theOBTrxContext, subGrpID);
            DefaultLogger.debug("CustExposureUIHelper","----------printChildMembers " + trxValue.getTransactionID() + " --------------------");
            DefaultLogger.debug("CustExposureUIHelper","----------  OK ---------------------");
        } catch (Exception e) {
          e.printStackTrace();
        }
        return trxValue;
    }

    /**
     *
     * @param userId
     * @return  ICommonUser
     */

    public static ICommonUser getUserByPK(String userId) {
         ICommonUser user = null;
         if (userId ==null){
             return user;
         }
       try {
           CMSUserProxy proxy = new CMSUserProxy();
           ICommonUserTrxValue trxValue = proxy.getUserByPK(userId.trim());
           if (trxValue != null){
            user= trxValue.getUser() ;
           }
       } catch (Exception e) {
         e.printStackTrace();
       }
           return user;
       }

    /**
     *
     * @param parentSubProfileIDStr
     * @return  HashMap
     */

    public static  HashMap getShareholder(String parentSubProfileIDStr) {
        HashMap result = new HashMap();
        ICustShareholderTrxValue trxValue =  new OBCustShareholderTrxValue();
        ICustRelationshipTrxValue trxValue1 = new OBCustRelationshipTrxValue();
        OBTrxContext theOBTrxContext = new OBTrxContext(); //   (OBTrxContext) map.get("theOBTrxContext");
        ICustRelationshipProxy custRelaionshipProxy = CustRelationshipProxyFactory.getProxy();
        long parentSubProfileID = (parentSubProfileIDStr == null) ? 0 : Long.parseLong(parentSubProfileIDStr);
        try {
            try {
                trxValue = custRelaionshipProxy.getCustShareholderTrxValue(theOBTrxContext, parentSubProfileID);
                trxValue1 = custRelaionshipProxy.getCustRelationshipTrxValue(theOBTrxContext, parentSubProfileID);
            } catch (Exception e) {
            }
            //DefaultLogger.debug(this, "trxValue : " + trxValue);

            if (trxValue == null){
                trxValue = new OBCustShareholderTrxValue();
                trxValue.setParentSubProfileID(parentSubProfileID);
            }
            if (trxValue.getCustShareholder() == null){
                trxValue.setCustShareholder(new ICustShareholder[0]);
            }

            if (trxValue1 == null){
                trxValue1 = new OBCustRelationshipTrxValue();
                trxValue1.setParentSubProfileID(parentSubProfileID);
            }
            if (trxValue1.getCustRelationship() == null){
                trxValue1.setCustRelationship(new ICustRelationship[0]);
            }

            // if sub profile id is not in request scope...
            // get it from trxValue if available
            if (parentSubProfileID == 0 && trxValue.getParentSubProfileID() != ICMSConstant.LONG_INVALID_VALUE){
                parentSubProfileID = trxValue.getParentSubProfileID();
                parentSubProfileIDStr = String.valueOf(parentSubProfileID);
            }
        } catch (Exception e) {
        }

        result.put("CustShareHolderTrxValue", trxValue);
        result.put("CustRelationshipTrxValue", trxValue1);
        result.put("offset", new Integer(0));
        result.put("length", new Integer(10));
        return result;
    }

    /**
     * @deprecated
     * @param ob
     * @return  ArrayList
     */
     public static  ArrayList getGroupMemeber(ICustGrpIdentifier ob) {
                ArrayList list = new ArrayList();
                IGroupMember[] grpMember = ob.getGroupMember() ;
                if (grpMember != null && grpMember.length > 0 ){
                    for (int i=0;i<grpMember.length ; i++) {
                      IGroupMember obj= grpMember [i]  ;
                       if (ICMSConstant.ENTITY_TYPE_CUSTOMER.equals(obj.getEntityType())){
                            list.add(obj) ;
                        }
                    }
                }
         return list ;
       }

     /**
     *
     * @param ob
     * @return  ArrayList
     */
     public static String[] getSubProfileIDS(ICustGrpIdentifier ob) {

         if (ob ==null){
             return null;
         }

         String[] arrayObj = null;
         ArrayList list = new ArrayList();
         ArrayList grpMember = getGroupMemeber(ob);
         if (grpMember != null){
             for (int i = 0; i < grpMember.size(); i++) {
                 IGroupMember obj = (IGroupMember) grpMember.get(i);
                 list.add(obj.getEntityID() + "");
                 Debug("CustExposureUIHelper, getSubProfileIDS --> obj.getEntityID() = " + obj.getEntityID());
             }

         }
         if (list.size() > 0){
             arrayObj = (String[]) list.toArray(new String[0]);
         }
         return arrayObj;
     }


     /**
     * @param aICustExposure
     * @param sub_profile_id
     * @return ICMSCustomer
     */
    public static ICMSCustomer getCustomer(long sub_profile_id, ICustExposure aICustExposure) {
        ICMSCustomer custOB = null;
        ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
        try {
            custOB = custproxy.getCustomer(sub_profile_id);
            if (custOB != null){
                aICustExposure.setCMSCustomer(custOB);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return custOB;

    }

    /**
     *
     * @param grpID
     * @return  IGroupRelationship
     */

    public static  ICustExposureGroupRelationship[] getGroupRelationship(String grpID) {

           ICustExposureGroupRelationship[] groupRelationship = null;
            return groupRelationship;
       }


    /**
        * Used in Report Scheduler
        *
        * @param date
        * @return String
        */
       public static String getDateAsString(Date date) {
           try {
               if (date != null){
                   SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                   return sdf.format(date);
               }
           } catch (Exception ex) {
           }
           return "";
       }


    /**
     * @param MGELAmount
     * @param TotalExposure
     * @param LimitBooked
     * @return Amount
     */
    public static Amount getGroupTotalAvailable(Amount MGELAmount, Amount TotalExposure, Amount LimitBooked) {
        Amount amount = null;
        double checkAmount = 0;
        String currency = null;
        if (MGELAmount != null && MGELAmount.getCurrencyCode() != null){
            checkAmount = MGELAmount.getAmountAsDouble();
            currency = MGELAmount.getCurrencyCode();
        }
        if (TotalExposure != null && TotalExposure.getCurrencyCode() != null){
            checkAmount += TotalExposure.getAmountAsDouble();
            currency = TotalExposure.getCurrencyCode();
        }
        if (LimitBooked != null && LimitBooked.getCurrencyCode() != null){
            checkAmount = checkAmount - LimitBooked.getAmountAsDouble();
            currency = LimitBooked.getCurrencyCode();
        }

        if (currency != null && !"".equals(currency)){
            amount = new Amount(checkAmount, currency);
        }
        return amount;
    }


    /**
     * @param MGELAmount
     * @param TotalExposure
     * @param LimitBooked
     * @return Amount
     */
    public static double getPercentLimitUtilized(Amount MGELAmount, Amount TotalExposure, Amount LimitBooked) {

        double returnVal = 0;
        double totalAmount = 0;

        if (TotalExposure != null && TotalExposure.getCurrencyCode() != null){
            totalAmount  += TotalExposure.getAmountAsDouble();
        }
        if (LimitBooked != null && LimitBooked.getCurrencyCode() != null){
            totalAmount  += LimitBooked.getAmountAsDouble();
        }
       if (MGELAmount != null && MGELAmount.getCurrencyCode() != null){
        returnVal = (totalAmount/ MGELAmount.getAmountAsDouble()) * 100;
       }


        return returnVal;
    }



    /**
        * @param msg
        */
       private static void Debug(String msg) {
    	   DefaultLogger.debug("CustExposureUIHelper",CustExposureUIHelper.class.getName() + " = " + msg);
       }



}
