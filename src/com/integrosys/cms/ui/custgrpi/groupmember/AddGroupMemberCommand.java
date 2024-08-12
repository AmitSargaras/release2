package com.integrosys.cms.ui.custgrpi.groupmember;

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
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 8, 2007 Time: 7:23:48 PM
 * To change this template use File | Settings | File Templates.
 */

public class AddGroupMemberCommand extends AbstractCommand {

    public static String GLOCAL_ADD_GROUP = "false";

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_EntitySelectedIDMapper, "java.util.List", FORM_SCOPE},
                {CustGroupUIHelper.service_groupMemberSearchList, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"entityID", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {GLOCAL_ADD_GROUP, "java.lang.String", GLOBAL_SCOPE},

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
                {CustGroupUIHelper.form_groupMemberSearchList, "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE},
                {CustGroupUIHelper.service_groupMemberSearchList, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"errNoSelection", "java.lang.String", REQUEST_SCOPE},
                {"errEntityID", "java.lang.String", REQUEST_SCOPE},
                {GLOCAL_ADD_GROUP, "java.lang.String", GLOBAL_SCOPE},
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

        String event = (String) map.get("event");
        String from_event = (String) map.get("from_event");
        String GLOCAL_ADD_GROUP = (String) map.get(AddGroupMemberCommand.GLOCAL_ADD_GROUP);

        SearchResult customerlist = (SearchResult) map.get(CustGroupUIHelper.service_groupMemberSearchList);
        List selectedEntityIDList = (List) map.get(CustGroupUIHelper.form_EntitySelectedIDMapper);

        ICustGrpIdentifierTrxValue groupTrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);
        if (groupTrxValue == null) {
            groupTrxValue = new OBCustGrpIdentifierTrxValue();
        }


        int noOfSelected = 0;
        int dbIDoc_length = 0;
        int jspIDoc_length = 0;

        // if (GroupMemberAction.EVENT_CREATE.equals(event)){
        if (selectedEntityIDList != null && !selectedEntityIDList.isEmpty()) {
            noOfSelected = selectedEntityIDList.size();
        } else {
            resultMap.put("errNoSelection", "Please select customer ");
            exceptionMap.put("errNoSelection", new ActionMessage("error.no.selection", " customer"));
        }
        // }


        ICustGrpIdentifier stagingGroupObj = groupTrxValue.getStagingCustGrpIdentifier();
        ICustGrpIdentifier actualGroupObj = groupTrxValue.getCustGrpIdentifier();
        IGroupMember[] jspIDoc;
        IGroupMember[] dbIDoc = stagingGroupObj.getGroupMember();
        IGroupMember[] updatedMember = null;
        List list = new ArrayList();
        boolean exist = true;
        boolean recursiveGrp = false;

        if (dbIDoc != null) {
            dbIDoc_length = dbIDoc.length;

        }

        Debug("event is  " + event);
        Debug(" selectedEntityIDList is  " + noOfSelected);
        Debug(" dbIDoc_length  = " + dbIDoc_length);

        try {
            if (customerlist != null && customerlist.getResultList() != null
                    && selectedEntityIDList != null && !selectedEntityIDList.isEmpty()) {
                jspIDoc = getGroupMemebers(customerlist, selectedEntityIDList);

                if (jspIDoc != null) {
                    jspIDoc_length = jspIDoc.length;
                }

                Debug(" jspIDoc_length  = " + jspIDoc_length);

                recursiveGrp = validateRecursiveGrp(jspIDoc, actualGroupObj, exceptionMap);
//                Debug(" recursiveGrp  = " + recursiveGrp);
                if (recursiveGrp) {
                    resultMap.put("errEntityID", "Recursive Sub-group");
                    list = getNewList(dbIDoc, null);
                } else if (dbIDoc_length == 0) {
                    list = getNewList(null, jspIDoc);
                } else {
//                    if (GLOCAL_ADD_GROUP != null && !GLOCAL_ADD_GROUP.equals("false")){
                    exist = ValidateDuplicate(jspIDoc, dbIDoc, exceptionMap);
//                    }
                    if (exist) {
                        resultMap.put("errEntityID", "Duplicate record found ");
                        // exceptionMap.put("errGroupMember", new ActionMessage("error.group.ownEntityID"));
                        list = getNewList(dbIDoc, null);
                    } else {
                        list = getNewList(dbIDoc, jspIDoc);
                    }

                }
            } else {
                list = getNewList(dbIDoc, null);
            }

            Debug("list.size() = " + list.size());

            ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
            //calling proxy to grab the member credit rating and entity limit info
            list = proxy.setEntityDetails(list);

            updatedMember = (IGroupMember[]) list.toArray(new IGroupMember[0]);
            stagingGroupObj.setGroupMember(updatedMember);
            groupTrxValue.setStagingCustGrpIdentifier(stagingGroupObj);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        resultMap.put("event", event);
        resultMap.put("itemType", "GROUPMEMBER_update");
        resultMap.put(AddGroupMemberCommand.GLOCAL_ADD_GROUP, "false");
        resultMap.put(CustGroupUIHelper.form_groupMemberSearchList, customerlist);
        resultMap.put(CustGroupUIHelper.service_groupMemberSearchList, customerlist);
        resultMap.put(CustGroupUIHelper.service_groupTrxValue, groupTrxValue);

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        DefaultLogger.debug(this, "Existing doExecute()");
        return returnMap;

    }

    private boolean validateRecursiveGrp(IGroupMember[] jspIDoc, ICustGrpIdentifier actualGroupObj, HashMap exceptionMap) {
        boolean recursive = false;

        if (jspIDoc == null || jspIDoc.length == 0) {
            return false;
        }

        if (actualGroupObj == null || actualGroupObj.getGrpID() == ICMSConstant.LONG_INVALID_VALUE) {
            Debug(">>>>>>>>>> actualGroupObj == null");
            return false;
        }

        String errorKey = "";
        for (int i = 0; i < jspIDoc.length; i++) {

            if (CustGroupUIHelper.ENTITY_TYPE_GROUP.equals(jspIDoc[i].getEntityType())) {
//                Debug(">>>>>>>>>> jspIDoc[i].getEntityID():"+jspIDoc[i].getEntityID());
//                Debug(">>>>>>>>>> jspIDoc[i].getEntityName():"+jspIDoc[i].getEntityName());
//                Debug(">>>>>>>>>> jspIDoc[i].getLmpLeID():"+jspIDoc[i].getLmpLeID());
//                Debug(">>>>>>>>>> jspIDoc[i].getGrpNo():"+jspIDoc[i].getGrpNo());
//                Debug(">>>>>>>>>> jspIDoc[i].getIdNO():"+jspIDoc[i].getIdNO());
//                Debug(">>>>>>>>>> actualGroupObj.getGrpID(): "+actualGroupObj.getGrpID());
//                Debug(">>>>>>>>>> actualGroupObj.getMasterGroupEntityID(): "+actualGroupObj.getMasterGroupEntityID());
                if (jspIDoc[i].getEntityID() == actualGroupObj.getMasterGroupEntityID()) {
                    exceptionMap.put("GROUP_ERROR", new ActionMessage("error.group.recursive"));
                    errorKey = "errEntityID" + jspIDoc[i].getEntityID();
                    Debug(">>>>>>>>>> " + jspIDoc[i].getEntityName() + "could not be added to itself as sub-group");
                    recursive = true;
                    exceptionMap.put(errorKey, new ActionMessage("error.group.recursive"));
                }
            }

        }
        return recursive;
    }

    /**
     * @param jspIDoc
     * @param dbIDoc
     * @param exceptionMap
     * @return boolean
     */
    private boolean ValidateDuplicate(IGroupMember[] jspIDoc, IGroupMember[] dbIDoc, HashMap exceptionMap) {
        long grpID = 0;
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

                if (CustGroupUIHelper.ENTITY_TYPE_GROUP.equals(jspIDoc[i].getEntityType())) {
                    if (jspIDoc[i].getEntityID() == dbIDoc[jj].getEntityID()) {
                        exceptionMap.put("GROUP_ERROR", new ActionMessage("error.group.duplicate"));
                        errorKey = "errEntityID" + jspIDoc[i].getEntityID();
                        Debug(">>>>>>>>>> Group Exist, Before , =  errEntityID" + i + " | " + jspIDoc[i].getEntityID());
                        valid = true;
                        exceptionMap.put(errorKey, new ActionMessage("error.group.duplicate"));
                    }
                } else if (CustGroupUIHelper.ENTITY_TYPE_CUSTOMER.equals(jspIDoc[i].getEntityType())) {
                    String jspKey = jspIDoc[i].getSourceID() + jspIDoc[i].getLmpLeID();
                    String dbKey = dbIDoc[jj].getSourceID() + dbIDoc[jj].getLmpLeID();
                    Debug(">>>>>>>>>> ENTITY_TYPE_CUSTOMER Compare jspKey =" + jspKey + "= dbKey " + dbKey);
                    if (jspKey != null && dbKey != null && jspKey.equals(dbKey)) {
                        exceptionMap.put("CUSTOMER_ERROR", new ActionMessage("error.member.duplicate"));
                        errorKey = "errEntityID" + jspIDoc[i].getSourceID() + jspIDoc[i].getLmpLeID();
                        Debug(">>>>>>>>>Customer Exist, Before , =  errEntityID" + i + " | " + jspIDoc[i].getSourceID() + jspIDoc[i].getLmpLeID());
                        valid = true;
                        exceptionMap.put(errorKey, new ActionMessage("error.member.duplicate"));
                    }
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
    private List getNewList(IGroupMember[] oldList, IGroupMember[] newList) {
        List list = new ArrayList();
        if (oldList != null) {
            for (int i = 0; i < oldList.length; i++) {
                list.add(oldList[i]);
            }
        }
        if (newList != null) {
            for (int i = 0; i < newList.length; i++) {
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
    private IGroupMember[] getGroupMemebers(SearchResult customerlist,
                                            List selectedEntityIDList) {
        IGroupMember obj = null;
        IGroupMember[] newList = null;
        if (selectedEntityIDList == null) {
            return newList;
        }

        List v = (List) customerlist.getResultList();
        List objlist = new ArrayList();
        if (v != null) {
            for (int j = 0; j < selectedEntityIDList.size(); j++) {
                String customerID = (String) selectedEntityIDList.get(j);
                boolean added = false;
                for (int i = 0; i < v.size() && !added; i++) {
                    OBCustomerSearchResult col = (OBCustomerSearchResult) v.get(i);
                    // This is same as in jsp checkbox
                    String tempCustkey = col.getSourceID() + col.getLegalReference();
                    if (StringUtils.isNotEmpty(customerID)) {

                        // Use LEID to compare
                        if (customerID.equals(tempCustkey + "") || customerID.equals(col.getGrpID() + "")) {

                            DefaultLogger.debug(this, "***********************************");
                            DefaultLogger.debug(this, "Customer ID = " + customerID);

                            obj = createObjectFromResult(col);

                            /*DefaultLogger.debug(this, AccessorUtil
                                           .printMethodValue(obj));*/
                            if (obj != null) {
                                objlist.add(obj);
                                added = true;
                            }
                        }
                    }
                }
            }

            DefaultLogger.debug(this, "objlist.size() = " + objlist.size());

        }

        return (IGroupMember[]) objlist.toArray(new IGroupMember[0]);

    }

    /**
     * @param col
     * @return IGroupMember
     */
    private IGroupMember createObjectFromResult(OBCustomerSearchResult col) {
        IGroupMember obj = null;
        if (col == null) {
            return obj;
        } else {
            obj = new OBGroupMember();

            // Group Records
            if (StringUtils.isNotEmpty(col.getGrpID()) && !col.getGrpID().equals(String.valueOf(ICMSConstant.LONG_INVALID_VALUE))) {
                obj.setEntityID(Long.parseLong(col.getGrpID() != null ? col.getGrpID() : ICMSConstant.LONG_INVALID_VALUE + ""));
                obj.setEntityType(ICMSConstant.ENTITY_TYPE_GROUP);
                obj.setEntityName(col.getGroupName());
                obj.setLmpLeID(col.getGrpID());
                obj.setGrpNo(Long.parseLong(col.getGrpNo() != null ? col.getGrpNo() : ICMSConstant.LONG_INVALID_VALUE + ""));
                obj.setIdNO(String.valueOf(ICMSConstant.LONG_INVALID_VALUE));
                if (col.getGrpNo() == null) {
                    Debug("getGrpNo  = null, Please check................why it is null ");
                }
            } else {
                // Member records
                obj.setEntityID(col.getSubProfileID());
                obj.setEntityType(ICMSConstant.ENTITY_TYPE_CUSTOMER);
                obj.setEntityName(col.getLegalName());
                obj.setLmpLeID(col.getLegalReference());
                obj.setIdNO(col.getIdNo());

                obj.setlEIDSource(col.getIdType());
                obj.setSourceID(col.getSourceID());

                // Trigger External CU001 Search
//				try {
////					MainProfile mp = EAICustomerHelper.getInstance()
////							.getMainProfileOrCreateDummy(new CastorDb(true),
////									col.getLmpLeID(), obj.getSourceID(),
////									col.getLegalName());
//
//	            	MainProfile mirrorMainProfile=new MainProfile();
//	            	mirrorMainProfile.setCustomerNameLong(col.getCustomerName());
//	            	mirrorMainProfile.setCustomerNameShort(col.getCustomerName());
//	            	mirrorMainProfile.setJDOIncorporationDate(col.getIncorporationDate());
//	            	mirrorMainProfile.setIdNo(StringUtils.defaultString(col.getIdNO()));
//
//	            	mirrorMainProfile.setAddress1(col.getAddress().getAddressLine1());
//	            	mirrorMainProfile.setAddress2(col.getAddress().getAddressLine2());
//
//
//	            	MainProfile mp = CustomerProxyFactory.getProxy().getSBCustomerManager().getMainProfileOrCreateDummy(col.getLmpLeID(),
//					 obj.getSourceID(), mirrorMainProfile);
//
//					// Set Sub Profile ID
//					obj.setEntityID(mp.getSubProfilePrimaryKey());
//                } catch (Exception e) {
//					e.printStackTrace();
//					return null;
//				}
            }

        }
        return obj;

    }

    /**
     * helper class for adding new child record to mainObj
     *
     * @param stagingGroupObj
     * @param childdOBj
     */
    private void addChildRecord(ICustGrpIdentifier stagingGroupObj,
                                IGroupMember childdOBj) {
        if (stagingGroupObj == null) {
            stagingGroupObj = new OBCustGrpIdentifier();
            Debug("stagingGroupObj = null");
        } else {
            Debug("stagingGroupObj.getGroupName = "
                    + stagingGroupObj.getGroupName());
            IGroupSubLimit[] list = stagingGroupObj.getGroupSubLimit();
            Debug("stagingGroupObj IGroupCreditGrade length = "
                    + (list == null ? 0 : list.length));
        }

        Debug("mainObj = " + stagingGroupObj);

        IGroupSubLimit[] existingArray = stagingGroupObj.getGroupSubLimit();
        int arrayLength = 0;
        if (existingArray != null) {
            arrayLength = existingArray.length;
        }

        IGroupMember[] newArray = new IGroupMember[arrayLength + 1];
        if (existingArray != null) {
            System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
        }
        if (childdOBj != null) {
            Debug("childdOBj = " + ((OBGroupMember) childdOBj).toString());
            newArray[arrayLength] = childdOBj;
        } else {
            Debug("childdOBj is null ");
        }

        stagingGroupObj.setGroupMember(newArray);
    }

    /**
     * for checking dupplicates
     *
     * @param list
     * @param obj
     * @return boolean
     */
    private boolean validate(IGroupMember[] list, IGroupMember obj) {
        if (list != null && list.length > 0 && obj != null) {
            for (int i = 0; i < list.length; i++) {
                if ((!list[i].getStatus().equals("DELETED"))
                        && list[i].getSourceID() != null
                        && obj.getSourceID() != null) {
                    if (obj.getSourceID().equals(list[i].getSourceID())) {
                        return true;
                    }
                }

            }
        }
        return false;

    }

    /**
     * @param msg
     */
    private void Debug(String msg) {
    	DefaultLogger.debug(this,"AddGroupMemberCommand = " + msg);
	}

}
