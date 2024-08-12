package com.integrosys.cms.ui.custgrpi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.bus.IGroupMember;
import com.integrosys.cms.app.custgrpi.bus.IGroupOtrLimit;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.bus.OBGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.bus.OBGroupOtrLimit;
import com.integrosys.cms.app.custgrpi.bus.OBGroupSubLimit;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Feb 29, 2008
 * Time: 2:07:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustGrpIdentifierMapperHelper {


    public static Map getGrpSubLimit(ICustGrpIdentifierTrxValue itrxValue, CustGrpIdentifierForm aForm) {
        Map returnMap = new HashMap();
        IGroupSubLimit[] oldList = null;
        boolean hasLimitBooking = false;

        String itemType = aForm.getItemType();
        if (itrxValue != null) {
            ICustGrpIdentifier stagingObj = itrxValue.getStagingCustGrpIdentifier();
            if (stagingObj != null) {
                oldList = stagingObj.getGroupSubLimit();
            }

            hasLimitBooking = itrxValue.isHasLimitBooking();
        }

        returnMap.put("limitList", oldList);
        returnMap.put("errDelete", new Boolean(false));


        if (CustGroupUIHelper.GROUPSUBLIMIT.equals(itemType)) {
        } else {
            return returnMap;
        }

/*
        for (int i=0; i<oldList.length; i++){
            debug("-----------------IGroupSubLimit-------------------");
            debug(""+oldList[i].getGroupSubLimitID());
            debug(""+oldList[i].getGroupSubLimitIDRef());
            debug(oldList[i].getSubLimitTypeCD());
            debug(""+oldList[i].getLimitAmt());
            debug(oldList[i].getCurrencyCD());
            debug(""+oldList[i].getLastReviewedDt());
            debug(oldList[i].getDescription());
            debug(oldList[i].getRemarks());
            debug(oldList[i].getStatus());
            debug(""+oldList[i].getGrpID());
        }
*/

        String[] deleteIds = aForm.getDeleteItem();

        List validDelIdList = new ArrayList();
        if (hasLimitBooking && deleteIds != null) {
            for (int i = 0; i < deleteIds.length; i++) {
                if (oldList[Integer.parseInt(deleteIds[i])].getGroupSubLimitID() == ICMSConstant.LONG_INVALID_VALUE) {
                    validDelIdList.add(deleteIds[i]);
//                    debug("validDelIdList:"+deleteIds[i]);
                }
            }
            deleteIds = (String[]) validDelIdList.toArray(new String[validDelIdList.size()]);
//            debug("******getGrpSubLimit - deleteIds: "+deleteIds.length+" aForm.getDeleteItem(): "+aForm.getDeleteItem().length);
        }

        IGroupSubLimit[] newList = oldList;
        if (CustGroupUIHelper.GROUPSUBLIMIT.equals(itemType)) {
            if (deleteIds != null && deleteIds.length > 0) {
                for (int i = 0; i < deleteIds.length; i++) {
                    debug("[getGrpSubLimit] To be Deleted BY User [" + i + "] = " + deleteIds[i]);
                }
                if (oldList != null && oldList.length > 0) {
                    int numDelete = CustGroupUIHelper.getNumberOfDelete(deleteIds, oldList.length);
                    if (numDelete != 0) {
                        newList = new OBGroupSubLimit[oldList.length - numDelete];
                        newList = (IGroupSubLimit[]) CustGroupUIHelper.deleteObjByList(oldList, newList, deleteIds);
                        debug("[getGrpSubLimit] newSubLimitList Length = " + newList.length);
                    }
                } else {
                    debug("[getGrpSubLimit] objSubLimitList is  null");
                }
            } else {
                debug("[getGrpSubLimit] Nothing Deleted so return as Old");
            }
        }
        returnMap.put("limitList", newList);

        if (deleteIds != null && aForm.getDeleteItem().length != deleteIds.length) {
            returnMap.put("errDelete", new Boolean(true));
        }

        return returnMap;
    }

    public static IGroupCreditGrade[] getGroupCreditGrade(ICustGrpIdentifierTrxValue itrxValue, CustGrpIdentifierForm aForm) {
        IGroupCreditGrade[] oldList = null;

        String itemType = aForm.getItemType();

        if (itrxValue != null) {
            ICustGrpIdentifier stagingObj = itrxValue.getStagingCustGrpIdentifier();
            if (stagingObj != null) {
                oldList = stagingObj.getGroupCreditGrade();
            }
        }

        if (CustGroupUIHelper.GROUPCREDITGRADE.equals(itemType)) {
        } else {
            return oldList;
        }

        String[] deleteIds = aForm.getDeleteItem();
        IGroupCreditGrade[] newList = null;

        if (CustGroupUIHelper.GROUPCREDITGRADE.equals(itemType)) {
            if (deleteIds != null && deleteIds.length > 0) {
                for (int i = 0; i < deleteIds.length; i++) {
                    debug("[getGroupCreditGrade] To be Deleted BY User [" + i + "] = " + deleteIds[i]);
                }
                if (oldList != null && oldList.length > 0) {
                    int numDelete = CustGroupUIHelper.getNumberOfDelete(deleteIds, oldList.length);
                    if (numDelete != 0) {
                        newList = new OBGroupCreditGrade[oldList.length - numDelete];
                        newList = (IGroupCreditGrade[]) CustGroupUIHelper.deleteObjByList(oldList, newList, deleteIds);
                        debug("[getGroupCreditGrade] newGradeList Length = " + newList.length);
                    }
                } else {
                    debug("[getGroupCreditGrade] objGradeList is  null");
                }
            } else {
                debug("[getGroupCreditGrade] Nothing Deleted so return as Old");
            }
        }
        return newList;
    }

    public static IGroupMember[] getGrpMember(ICustGrpIdentifierTrxValue itrxValue, CustGrpIdentifierForm aForm) {
        IGroupMember[] oldList = null;

        String itemType = aForm.getItemType();

        if (itrxValue != null) {
            ICustGrpIdentifier stagingObj = itrxValue.getStagingCustGrpIdentifier();
            if (stagingObj != null) {
                oldList = stagingObj.getGroupMember();
            }
        }


        if (CustGroupUIHelper.GROUPMEMBER.equals(itemType)) {
        } else {
            setMemberListFormToOB(aForm, oldList, null, null);
            return oldList;
        }

        String[] deleteIds = aForm.getDeleteItem();
        String[] deleteOtrLmts = aForm.getDeleteOtrLmt();
        List deleteGrpSubLimitList = new ArrayList();
        List deleteGrpOtrLimitList = new ArrayList();
        IGroupMember[] newList = null;
        newList = oldList;

        if (deleteIds != null && deleteIds.length > 0 && aForm.getEvent().equals(CustGrpIdentifierAction.EVENT_DELETE_ITEM)) {
            deleteGrpSubLimitList = Arrays.asList(deleteIds);
        }

        if (deleteOtrLmts != null && deleteOtrLmts.length > 0) {
            deleteGrpOtrLimitList = Arrays.asList(deleteOtrLmts);
        }
        ArrayList requiredList = new ArrayList();


        if (CustGroupUIHelper.GROUPMEMBER.equals(itemType)) {
            if (newList != null && newList.length > 0) {
                debug(" Input Records = " + newList.length);
                for (int index = 0; index < newList.length; index++) {
                    IGroupMember obj = newList[index];
                    if (deleteGrpSubLimitList != null && deleteGrpSubLimitList.contains(index + "")) {
                        debug(" Not adding Record Index [" + index + "] getGroupMembID()" + obj.getGroupMemberID());
                    } else {
                        if (obj.getGroupMemberID() == 0 || obj.getGroupMemberID() == ICMSConstant.LONG_INVALID_VALUE) {
                            debug(" Adding new Record Index [" + index + "] getGroupMembID()" + obj.getGroupMemberID());
                            requiredList.add(obj);
                        } else {
                            debug(" Existing Record to the updated Index [" + index + "] getGroupMembID()" + obj.getGroupMemberID());
                            requiredList.add(obj);
                        }
                    }
                }
            }

        }
        IGroupMember[] newList1 = (IGroupMember[]) requiredList.toArray(new IGroupMember[0]);
        // Using old list with deleted indexes list as the relation and other arrays jumble up otherwise
        setMemberListFormToOB(aForm, oldList, deleteGrpSubLimitList, deleteGrpOtrLimitList);
        return newList1;
    }

    /**
     * @param itrxValue
     * @param aForm
     * @return
     * @author Andy Wong
     * @date 27 June 2008
     * @desc Retrieve group outer limit
     */
    public static Map getGrpOtrLimit(ICustGrpIdentifierTrxValue itrxValue, CustGrpIdentifierForm aForm) {
        Map returnMap = new HashMap();
        IGroupOtrLimit[] oldList = null;
        boolean hasLimitBooking = false;

        String itemType = aForm.getItemType();
        if (itrxValue != null) {
            ICustGrpIdentifier stagingObj = itrxValue.getStagingCustGrpIdentifier();
            if (stagingObj != null) {
                oldList = stagingObj.getGroupOtrLimit();
            }

            hasLimitBooking = itrxValue.isHasLimitBooking();
        }

        returnMap.put("limitList", oldList);
        returnMap.put("errDelete", new Boolean(false));

        if (CustGroupUIHelper.GROUPOTRLIMIT.equals(itemType)) {
        } else {
            return returnMap;
        }

/*
        for (int i=0; i<oldList.length; i++){
            debug("-----------------IGroupOtrLimit-------------------");
            debug(""+oldList[i].getGroupOtrLimitID());
            debug(""+oldList[i].getGroupOtrLimitIDRef());
            debug(oldList[i].getOtrLimitTypeCD());
            debug(""+oldList[i].getLimitAmt());
            debug(oldList[i].getCurrencyCD());
            debug(""+oldList[i].getLastReviewedDt());
            debug(oldList[i].getDescription());
            debug(oldList[i].getRemarks());
            debug(oldList[i].getStatus());
            debug(""+oldList[i].getGrpID());
        }
*/

        String[] deleteIds = aForm.getDeleteItem();

        List validDelIdList = new ArrayList();
        if (hasLimitBooking && deleteIds != null) {
            for (int i = 0; i < deleteIds.length; i++) {
                if (oldList[Integer.parseInt(deleteIds[i])].getGroupOtrLimitID() == ICMSConstant.LONG_INVALID_VALUE) {
                    validDelIdList.add(deleteIds[i]);
                }
            }
            deleteIds = (String[]) validDelIdList.toArray(new String[validDelIdList.size()]);
//            debug("******getGrpOtrLimit - deleteIds: "+deleteIds.length+" aForm.getDeleteItem(): "+aForm.getDeleteItem().length);
        }

        IGroupOtrLimit[] newList = oldList;
        if (CustGroupUIHelper.GROUPOTRLIMIT.equals(itemType)) {
            if (deleteIds != null && deleteIds.length > 0) {
                for (int i = 0; i < deleteIds.length; i++) {
                    debug("[getGrpOtrLimit] To be Deleted BY User [" + i + "] = " + deleteIds[i]);
                }
                if (oldList != null && oldList.length > 0) {
                    int numDelete = CustGroupUIHelper.getNumberOfDelete(deleteIds, oldList.length);
                    if (numDelete != 0) {
                        newList = new OBGroupOtrLimit[oldList.length - numDelete];
                        newList = (IGroupOtrLimit[]) CustGroupUIHelper.deleteObjByList(oldList, newList, deleteIds);
                        debug("[getGrpOtrLimit] newOtrLimitList Length = " + newList.length);
                    }
                } else {
                    debug("[getGrpOtrLimit] objOtrLimitList is  null");
                }
            } else {
                debug("[getGrpOtrLimit] Nothing Deleted so return as Old");
            }
        }
        returnMap.put("limitList", newList);

        if (deleteIds != null && aForm.getDeleteItem().length != deleteIds.length) {
            returnMap.put("errDelete", new Boolean(true));
        }

        return returnMap;
    }

    public static void setMemberListFormToOB(CustGrpIdentifierForm aForm, IGroupMember[] list, List deleteGrpSubLimitList, List deleteGrpOtrLimitList) {
        // if ("submit".equals(aForm.getEvent())) {

        String[] relationName = aForm.getRelationName();
        String[] percentOwned = aForm.getPercentOwned();
        String[] relBorMemberName = aForm.getRelBorMemberName();
        String[] entityID = aForm.getEntityID();
        String[] entityType = aForm.getEntityType();

        if (relationName != null && relationName.length > 0) {
            debug("setMemberListFormToOB  relationName is " + relationName.length);
            if (list != null && list.length > 0) {
                debug("setMemberListFormToOB  IGroupMember is " + list.length);
                for (int index = 0; index < list.length; index++) {
                    IGroupMember obj = list[index];
                    if (deleteGrpSubLimitList != null && deleteGrpSubLimitList.contains(index + "")
                            || deleteGrpOtrLimitList != null && deleteGrpOtrLimitList.contains(index + "")) {
                        debug(" Not adding Record Index [" + index + "] getGroupMembID()" + obj.getGroupMemberID());
                    } else {

                        debug("setMemberListFormToOB  GroupMember relationName[" + index + " ]=" + relationName[index]);

                        if (!AbstractCommonMapper.isEmptyOrNull(entityID[index])) {
                            obj.setEntityID(Long.valueOf(entityID[index]).longValue());
                        }

                        obj.setEntityType(isNotEmpty(entityType[index]) ? entityType[index] : "");
                        obj.setRelationName(isNotEmpty(relationName[index]) ? relationName[index] : "");
                        obj.setPercentOwned(isNotEmpty(percentOwned[index]) ? new Double(percentOwned[index]) : new Double(ICMSConstant.DOUBLE_INVALID_VALUE));
                        obj.setRelBorMemberName(isNotEmpty(relBorMemberName[index]) ? relBorMemberName[index] : "");
                    }   // Else of Not Adding.. i.e Adding record
                }
            }
        } else {
            debug("setMemberListFormToOB  groupMemberList is 0");
        }
        // }

    }

    public static void setMemberListOBtoForm(CustGrpIdentifierForm aForm, IGroupMember[] list) {
        String[] relationName = null;
        String[] percentOwned = null;
        String[] entityID = null;
        String[] entityType = null;
        String[] relBorMemberName = null;

        if (list != null && list.length > 0) {
            debug("setMemberListOBtoForm  groupMemberList is  " + list.length);
            relationName = new String[list.length];
            percentOwned = new String[list.length];
            entityID = new String[list.length];
            entityType = new String[list.length];
            relBorMemberName = new String[list.length];

            for (int index = 0; index < list.length; index++) {
                debug("setMemberListOBtoForm  GroupMember relationName[" + index + " ]=" + relationName[index]);
                relationName[index] = (isNotEmpty(list[index].getRelationName()) ? list[index].getRelationName() : "");
                if (list[index].getPercentOwned() != null && list[index].getPercentOwned().doubleValue() != ICMSConstant.DOUBLE_INVALID_VALUE) {
                    percentOwned[index] = list[index].getPercentOwned() + "";
                }

                if (list[index].getEntityID() != ICMSConstant.LONG_INVALID_VALUE) {
                    entityID[index] = list[index].getEntityID() + "";
                }

                // percentOwned[index] = (isNotEmpty(list[index].getPercentOwned()) ? list[index].getPercentOwned() + "" : ICMSConstant.DOUBLE_INVALID_VALUE+"");
                entityType[index] = (isNotEmpty(list[index].getEntityType()) ? list[index].getEntityType() : "");
                relBorMemberName[index] = (isNotEmpty(list[index].getRelBorMemberName()) ? list[index].getRelBorMemberName() : "");
            }
        } else {
            // debug("setMemberListOBtoForm  groupMemberList is 0");
        }
        aForm.setEntityID(entityID);
        aForm.setEntityType(entityType);
        aForm.setRelationName(relationName);
        aForm.setPercentOwned(percentOwned);
        aForm.setRelBorMemberName(relBorMemberName);

    }

    private static boolean isNotEmpty(String str) {
        return CustGrpIdentifierUIHelper.isNotEmpty(str);
    }

    private static void debug(String msg) {
    	DefaultLogger.debug("","CustGrpIdentifierMapperHelper,  " + msg);
    }

}
