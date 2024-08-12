package com.integrosys.cms.ui.limitbooking;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.bus.OBGroupSearchResult;
import com.integrosys.cms.app.limitbooking.bus.IBankGroupDetail;
import com.integrosys.cms.app.limitbooking.bus.OBBankGroupDetail;
import com.integrosys.cms.app.limitbooking.bus.OBLimitBooking;
import com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy;
import com.integrosys.cms.app.limitbooking.proxy.LimitBookingProxyFactory;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author priya
 */

public class AddGroupCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", SERVICE_SCOPE},
                {CustGroupUIHelper.form_EntitySelectedIDMapper, "java.util.List", FORM_SCOPE},
                {LimitBookingAction.SESSION_SEARCH_RESULT, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"entityID", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, "java.lang.String", REQUEST_SCOPE}
        });
    }

    /**
     * Defines an two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {LimitBookingAction.SESSION_SEARCH_RESULT, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {"sess.limitBooking", "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", SERVICE_SCOPE},
                {LimitBookingAction.LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", FORM_SCOPE},
                {"errNoSelection", "java.lang.String", REQUEST_SCOPE},
                {"errEntityID", "java.lang.String", REQUEST_SCOPE},
                {LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, "java.lang.String", REQUEST_SCOPE}
        });
    }

    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "inside doExecute ");
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();

        SearchResult sessGroupList = (SearchResult) map.get(LimitBookingAction.SESSION_SEARCH_RESULT);
        List selectedEntityIDList = (List) map.get(CustGroupUIHelper.form_EntitySelectedIDMapper);
        OBLimitBooking limitBooking = (OBLimitBooking) map.get(LimitBookingAction.SESSION_LIMIT_BOOKING);
        List bgelGroups = limitBooking.getBankGroupList();
        if (bgelGroups == null)
            bgelGroups = new ArrayList();

        if (selectedEntityIDList == null && selectedEntityIDList.isEmpty()) {
            resultMap.put("errNoSelection", "Please select customer ");
            exceptionMap.put("errNoSelection", new ActionMessage("error.no.selection", " customer"));
        }
        IBankGroupDetail[] addedGroup;
        IBankGroupDetail[] currentBGEL = (IBankGroupDetail[]) bgelGroups.toArray(new IBankGroupDetail[0]);
        ArrayList list = new ArrayList();
        boolean exist = true;

        try {
            if (sessGroupList != null && sessGroupList.getResultList() != null
                    && selectedEntityIDList != null && !selectedEntityIDList.isEmpty()) {
                addedGroup = getGroups(sessGroupList, selectedEntityIDList);

                if (currentBGEL == null) {
                    list = getNewList(addedGroup, null);
                } else {
                    exist = validateDuplicate(addedGroup, currentBGEL, exceptionMap);
                    if (exist) {
                        list = getNewList(currentBGEL, null);
                    } else {
                        list = getNewList(currentBGEL, addedGroup);
                    }
                }
            } else {
                list = getNewList(currentBGEL, null);
            }

            List groupIDList = new ArrayList();

            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    groupIDList.add(((IBankGroupDetail) list.get(i)).getBkgTypeCode());
                }
            }

            ILimitBookingProxy proxy = LimitBookingProxyFactory.getProxy();
            List bankGroupMasterListSearchResult = proxy.retrieveMasterGroupBySubGroupID(groupIDList);

            List bankGroupMasterList = new ArrayList();

            if (bankGroupMasterListSearchResult != null) {
                for (int w = 0; w < bankGroupMasterListSearchResult.size(); w++) {
                    bankGroupMasterList.add(createObjectFromResult((OBGroupSearchResult) bankGroupMasterListSearchResult.get(w)));
                }
            }

            List newList = new ArrayList();

            newList = list;

            if (bankGroupMasterList != null) {
                for (int j = 0; j < bankGroupMasterList.size(); j++) {
                    boolean found = false;
                    for (int k = 0; k < list.size(); k++) {
                        if (((IBankGroupDetail) bankGroupMasterList.get(j)).getBkgTypeCode().equals(((IBankGroupDetail) list.get(k)).getBkgTypeCode())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        newList.add(bankGroupMasterList.get(j));
                    }
                }
            }

            limitBooking.setBankGroupList(newList);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }
        resultMap.put(LimitBookingAction.SEARCH_RESULT, sessGroupList);
        resultMap.put(LimitBookingAction.SESSION_SEARCH_RESULT, sessGroupList);
        resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING, limitBooking);
        resultMap.put(LimitBookingAction.LIMIT_BOOKING, limitBooking);
        resultMap.put(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, (String) map.get(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT));

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        DefaultLogger.debug(this, "Existing doExecute()");
        return returnMap;

    }

    /**
     * @param jspIDoc
     * @param dbIDoc
     * @param exceptionMap
     * @return boolean
     */
    private boolean validateDuplicate(IBankGroupDetail[] jspIDoc, IBankGroupDetail[] dbIDoc, HashMap exceptionMap) {
        boolean valid = false;

        if (jspIDoc == null || jspIDoc.length == 0) {
            return false;
        }
        if (dbIDoc == null || dbIDoc.length == 0) {
            return false;
        }
        String errorKey = "";
        for (int i = 0; i < jspIDoc.length; i++) {

            for (int jj = 0; jj < dbIDoc.length; jj++) {
                if (jspIDoc[i].getBkgTypeCode() != null && jspIDoc[i].getBkgTypeCode().equals(dbIDoc[jj].getBkgTypeCode())) {
                    errorKey = "duplicateGroupEntryError" + jspIDoc[i].getBkgTypeCode();
                    valid = true;
                    exceptionMap.put(errorKey, new ActionMessage("error.group.duplicate"));
                }
            }
        }
        return valid;
    }

    /**
     * @param oldList
     * @param newList
     * @return List
     */
    public ArrayList getNewList(IBankGroupDetail[] oldList, IBankGroupDetail[] newList) {
        ArrayList list = new ArrayList();
        if (oldList != null) {
            for (int i = 0; i < oldList.length; i++) {
                list.add(oldList[i]);
            }
        }
        if (newList != null) {
            for (int i = 0; i < newList.length; i++) {
//                System.out.println("setting to false = " + newList);
                //newList[i].setGrpIsRetrieved(false);
                list.add(newList[i]);
            }
        }
        return list;

    }

    /**
     * @param customerlist
     * @param selectedEntityIDList
     * @return IGroupMember
     */
    private IBankGroupDetail[] getGroups(SearchResult customerlist,
                                         List selectedEntityIDList) {
        IBankGroupDetail obj = null;
        IBankGroupDetail[] newList = null;
        if (selectedEntityIDList == null) {
            return newList;
        }

        List v = (List) customerlist.getResultList();
        List objlist = new ArrayList();
        if (v != null) {
            for (int j = 0; j < selectedEntityIDList.size(); j++) {
                String customerID = (String) selectedEntityIDList.get(j);
                for (int i = 0; i < v.size(); i++) {
                    OBGroupSearchResult col = (OBGroupSearchResult) v.get(i);
                    // This is same as in jsp checkbox
                    String tempCustkey = new Long(col.getGrpID()).toString();
                    if (StringUtils.isNotEmpty(customerID)) {
                        // Use LEID to compare
                        if (customerID.equals(tempCustkey + "") || customerID.equals(col.getGrpID() + "")) {
                            obj = createObjectFromResult(col);
                            if (obj != null) {
                                objlist.add(obj);
                            }
                        }
                    }
                }
            }

        }
        return (IBankGroupDetail[]) objlist.toArray(new IBankGroupDetail[0]);

    }

    /**
     * @param col
     * @return IGroupMember
     */
    private IBankGroupDetail createObjectFromResult(OBGroupSearchResult col) {
        IBankGroupDetail obj = null;
        if (col == null) {
            return obj;
        } else {
            obj = new OBBankGroupDetail();

            // Group Records
            if (StringUtils.isNotEmpty(new Long(col.getGrpID()).toString()) && !new Long(col.getGrpID()).toString().equals(String.valueOf(ICMSConstant.LONG_INVALID_VALUE))) {
                obj.setBkgTypeCode(new Long(col.getGrpID()).toString());
                obj.setBkgTypeDesc(col.getGroupName());
                obj.setLimitConvAmount(col.getConvLmt());
                obj.setLimitInvAmount(col.getInvLmt());
                obj.setLimitIslamAmount(col.getIslamLmt());
                obj.setLimitAmount(col.getGroupLmt());
                obj.setGrpIsRetrieved(false);
                obj.setBkgType(ICMSConstant.BKG_TYPE_BGEL);
                if (col.getGrpNo() == null) {
                    Debug("getGrpNo  = null is null");
                }
            }
        }
        return obj;

    }


    private void Debug(String msg) {
    	DefaultLogger.debug(this,"AddGroupMemberCommand = " + msg);
	}

}
