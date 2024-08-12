package com.integrosys.cms.ui.custgrpi;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.bus.*;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
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

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Feb 7, 2008
 * Time: 2:43:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustGroupUIHelper {


    public static String search_ByGroup = "ByGroup";

    public static String form_CustGrpIdentifierSearchObj = "form.CustGrpIdentifierSearchObj";
    public static String service_CustGrpIdentifierSearchObj = "service.CustGrpIdentifierSearchObj";
    public static String form_CustGrpIdentifierSearchListObj = "form.CustGrpIdentifierSearchListObj";
    public static String service_CustGrpIdentifierSearchListObj = "service.CustGrpIdentifierSearchListObj";

    public static String form_groupResult = "form.groupResult";
    public static String service_groupResult = "service.groupResult";

    public static String form_CustGrpIdentifierResultListObj = "form.CustGrpIdentifierResultListObj";

    public static String form_custGrpIdentifierObj = "form.custGrpIdentifierObj";
    public static String service_groupTrxValue = "service.groupTrxValue";

    public static String popup_form_custGrpIdentifierObj = "popup_form.custGrpIdentifierObj";
    public static String popup_service_groupTrxValue = "popup_service_groupTrxValue";

    public static String GROUPCREDITGRADE = "GROUPCREDITGRADE";
    public static String GROUPSUBLIMIT = "GROUPSUBLIMIT";
    public static String GROUPOTRLIMIT = "GROUPOTRLIMIT";
    public static String GROUPMEMBER = "GROUPMEMBER";
    public static String GROUPGROUP = "GROUPGROUP";

    public static String form_groupCreditGradeObj = "form.groupCreditGradeObj";
    public static String act_groupCreditGradeObj = "act.groupCreditGradeObj";
    public static String stg_groupCreditGradeObj = "stg.groupCreditGradeObj";


    public static String form_groupSubLimitObj = "form.groupSubLimitObj";
    public static String act_groupSubLimitObj = "act.groupSubLimitObj";
    public static String stg_groupSubLimitObj = "stg.groupSubLimitObj";

    public static String form_groupOtrLimitObj = "form.groupOtrLimitObj";
    public static String act_groupOtrLimitObj = "act.groupOtrLimitObj";
    public static String stg_groupOtrLimitObj = "stg.groupOtrLimitObj";

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
    public static final String GROUP_NAME_EXIST = "<font color=\"#FF0000\" size=\"1\">System should not allow user to create two groups with same name.</font>";

    //Andy Wong, 3 July 2008: Internal Limit To Be Applied
    public static final String INT_LMT_GP5_REQ = "1";
    public static final String INT_LMT_CAP_FUND_PERCENT = "2";
    public static final String INT_LMT_CREDIT_RATE = "3";
    public static final String INT_LMT_ABSOLUTE = "4";

    //Andy Wong, 3 July 2008: Internal Limit Param
    public static final String INT_LMT_PARAM_BANK_GRP = "ABG";
    public static final String INT_LMT_PARAM_CONVENT = "2";
    public static final String INT_LMT_PARAM_ISLAMIC = "3";
    public static final String INT_LMT_PARAM_INVEST = "4";

    public static final String INT_LMT_SUBTYP_INTER_NONEXEMPT = "1";
    public static final String INT_LMT_SUBTYP_AB_ENTITY = "2";

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

    public static void printDependantsRecords(ICustGrpIdentifier stagingObj) {
        if (stagingObj != null) {
            System.out.println("getGroupName = " + stagingObj.getGroupName());
            IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
            IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
            IGroupMember[] list3 = stagingObj.getGroupMember();
            IGroupOtrLimit[] list4 = stagingObj.getGroupOtrLimit();
            System.out.println("IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
            System.out.println("IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
            System.out.println("IGroupMember.length = " + (list3 == null ? 0 : list3.length));
            System.out.println("IGroupOtrLimit.length = " + (list4 == null ? 0 : list4.length));
        } else {
            System.out.println("ICustGrpIdentifier is null");
        }
    }

    public static void printChildMembers(ICustGrpIdentifierTrxValue trxValue) {

        try {
            if (trxValue != null) {
                ICustGrpIdentifier stagingObj = trxValue.getStagingCustGrpIdentifier();
                if (stagingObj != null) {
                    System.out.println("trxValue.getGroupName = " + stagingObj.getGroupName());
                    IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
                    IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
                    IGroupMember[] list3 = stagingObj.getGroupMember();
                    IGroupOtrLimit[] list4 = stagingObj.getGroupOtrLimit();
                    System.out.println("IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
                    System.out.println("IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
                    System.out.println("IGroupMember.length = " + (list3 == null ? 0 : list3.length));
                    System.out.println("IGroupOtrLimit.length = " + (list4 == null ? 0 : list4.length));
                } else {
                    System.out.println("stagingObj  is null");
                }
            } else {
                System.out.println("trxValue is  null ");
            }
        } catch (Exception e) {

        }
    }

    public static void printChildMembersAct(ICustGrpIdentifierTrxValue trxValue) {
        System.out.println("----------printChildMembersAct ---------------------");
        try {
            if (trxValue != null) {
                ICustGrpIdentifier stagingObj = trxValue.getCustGrpIdentifier();
                if (stagingObj != null) {
                    System.out.println("trxValue.getGroupName = " + stagingObj.getGroupName());
                    IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
                    IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
                    IGroupMember[] list3 = stagingObj.getGroupMember();
                    IGroupOtrLimit[] list4 = stagingObj.getGroupOtrLimit();
                    System.out.println("IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
                    System.out.println("IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
                    System.out.println("IGroupMember.length = " + (list3 == null ? 0 : list3.length));
                    System.out.println("IGroupOtrLimit.length = " + (list4 == null ? 0 : list4.length));
                } else {
                    System.out.println("trxValue.getGroupName is null");
                }
            } else {
                System.out.println("trxValue is  null ");
            }
        } catch (Exception e) {

        }

        System.out.println("----------  OK ---------------------");
    }

    public static void printChildMembersStg(ICustGrpIdentifierTrxValue trxValue) {
        System.out.println("----------printChildMembersStg ---------------------");
        try {
            if (trxValue != null) {
                ICustGrpIdentifier stagingObj = trxValue.getStagingCustGrpIdentifier();
                if (stagingObj != null) {
                    System.out.println("trxValue.getGroupName = " + stagingObj.getGroupName());
                    IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
                    IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
                    IGroupMember[] list3 = stagingObj.getGroupMember();
                    IGroupOtrLimit[] list4 = stagingObj.getGroupOtrLimit();
                    System.out.println("IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
                    System.out.println("IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
                    System.out.println("IGroupMember.length = " + (list3 == null ? 0 : list3.length));
                    System.out.println("IGroupOtrLimit.length = " + (list4 == null ? 0 : list4.length));
                } else {
                    System.out.println("trxValue.getGroupName is null");
                }
            } else {
                System.out.println("trxValue is  null ");
            }
        } catch (Exception e) {

        }
        System.out.println("----------  OK ---------------------");
    }

    public static void printChildMembersStg(ICustGrpIdentifier stagingObj) {
        System.out.println("----------printChildMembersStg - Staging ---------------------");
        if (stagingObj != null) {
            System.out.println("trxValue.getGroupName = " + stagingObj.getGroupName());
            IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
            IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
            IGroupMember[] list3 = stagingObj.getGroupMember();
            IGroupOtrLimit[] list4 = stagingObj.getGroupOtrLimit();
            System.out.println("IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
            System.out.println("IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
            System.out.println("IGroupMember.length = " + (list3 == null ? 0 : list3.length));
            System.out.println("IGroupOtrLimit.length = " + (list4 == null ? 0 : list4.length));
        } else {
            System.out.println("trxValue.getGroupName is null");
        }

        System.out.println("----------  OK ---------------------");
    }


    public static void printChildMembersAct(ICustGrpIdentifier stagingObj) {
        System.out.println("----------printChildMembersAct  Actual---------------------");
        if (stagingObj != null) {
            System.out.println("trxValue.getGroupName = " + stagingObj.getGroupName());
            IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
            IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
            IGroupMember[] list3 = stagingObj.getGroupMember();
            IGroupOtrLimit[] list4 = stagingObj.getGroupOtrLimit();
            System.out.println("IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
            System.out.println("IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
            System.out.println("IGroupMember.length = " + (list3 == null ? 0 : list3.length));
            System.out.println("IGroupOtrLimit.length = " + (list4 == null ? 0 : list4.length));
        } else {
            System.out.println("trxValue.getGroupName is null");
        }

        System.out.println("----------  OK ---------------------");
    }

    public static void printChildMembers(String msg, ICustGrpIdentifier stagingObj) {
        System.out.println("----------printChildMembers " + msg + " --------------------");
        if (stagingObj != null) {
            System.out.println("trxValue.getGroupName = " + stagingObj.getGroupName());
            IGroupCreditGrade[] list1 = stagingObj.getGroupCreditGrade();
            IGroupSubLimit[] list2 = stagingObj.getGroupSubLimit();
            IGroupMember[] list3 = stagingObj.getGroupMember();
            IGroupOtrLimit[] list4 = stagingObj.getGroupOtrLimit();
            System.out.println("IGroupSubLimit.length = " + (list2 == null ? 0 : list2.length));
            System.out.println("IGroupCreditGrade.length = " + (list1 == null ? 0 : list1.length));
            System.out.println("IGroupMember.length = " + (list3 == null ? 0 : list3.length));
            System.out.println("IGroupOtrLimit.length = " + (list4 == null ? 0 : list4.length));
        } else {
            System.out.println("trxValue.getGroupName is null");
        }

        System.out.println("----------  OK ---------------------");
    }

    public static ICustGrpIdentifierTrxValue getTransValueByGroupId(String subGrpID) {

        ICustGrpIdentifierTrxValue trxValue = null;
        try {
            ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
            OBTrxContext theOBTrxContext = new OBTrxContext();
            trxValue = proxy.getCustGrpIdentifierByGrpID(theOBTrxContext, subGrpID);
            System.out.println("----------printChildMembers " + trxValue.getTransactionID() + " --------------------");
            System.out.println("----------  OK ---------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trxValue;
    }


    public static ICommonUser getUserByPK(String userId) {
        ICommonUser user = null;
        if (userId == null) {
            return user;
        }
        try {
            CMSUserProxy proxy = new CMSUserProxy();
            ICommonUserTrxValue trxValue = proxy.getUserByPK(userId.trim());
            if (trxValue != null) {
                user = trxValue.getUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static HashMap getShareholder(String parentSubProfileIDStr) {
        HashMap result = new HashMap();
        ICustShareholderTrxValue trxValue = new OBCustShareholderTrxValue();
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

            if (trxValue == null) {
                trxValue = new OBCustShareholderTrxValue();
                trxValue.setParentSubProfileID(parentSubProfileID);
            }
            if (trxValue.getCustShareholder() == null) {
                trxValue.setCustShareholder(new ICustShareholder[0]);
            }

            if (trxValue1 == null) {
                trxValue1 = new OBCustRelationshipTrxValue();
                trxValue1.setParentSubProfileID(parentSubProfileID);
            }
            if (trxValue1.getCustRelationship() == null) {
                trxValue1.setCustRelationship(new ICustRelationship[0]);
            }

            // if sub profile id is not in request scope...
            // get it from trxValue if available
            if (parentSubProfileID == 0 && trxValue.getParentSubProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
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

    public static boolean groupHasLimitBooking(ICustGrpIdentifierTrxValue trxValue) {
        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
        ICustGrpIdentifier actual = trxValue.getCustGrpIdentifier();
        List groupIDList = new ArrayList();
        groupIDList.add(new Long(actual.getMasterGroupEntityID()).toString());

        try {
            return proxy.groupHasLimitBooking(groupIDList);
        } catch (CustGrpIdentifierException e) {
            e.printStackTrace();
        }
        return false;
    }


}
