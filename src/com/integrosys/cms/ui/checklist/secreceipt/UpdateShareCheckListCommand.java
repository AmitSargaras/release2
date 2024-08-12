package com.integrosys.cms.ui.checklist.secreceipt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.IShareDoc;
import com.integrosys.cms.app.checklist.bus.OBShareDoc;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Feb 2, 2006 Time: 11:01:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateShareCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private static final String CHECKLIST_CATEGORY = "S";

	/**
	 * Default Constructor
	 */
	public UpdateShareCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "index", "java.lang.String", REQUEST_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
				{ "custCategory", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, // used
																														// bt
																														// R1
																														// .5
																														// ,
																														// CR17
																														// :
																														// Share
																														// Checklist
				{ "docNo", "java.lang.String", REQUEST_SCOPE }
		// {"deleteCheckListId", "java.lang.String[]", REQUEST_SCOPE}
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
		return (new String[][] { { "status", "java.lang.String", REQUEST_SCOPE },
				{ "monitorType", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here dynamic checkList added to document
	 * done.
	 * 
	 * @param inputMap is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap resultMap = new HashMap();
		ICheckList checkList = (ICheckList) inputMap.get("checkList");
		long checkListId = checkList.getCheckListID();
		ICheckListItem checkListItem = (ICheckListItem) inputMap.get("checkListItem");
		String status = checkListItem.getItemStatus();
		String currEvent = (String) inputMap.get("event");
		String docNo = (String) inputMap.get("docNo");
		DefaultLogger.debug(this, "DocNo:" + docNo);

		/*
		 * String deleteId[]= (String[])inputMap.get("deleteCheckListId");
		 * DefaultLogger.debug(this, "deleteId:" +deleteId); if(deleteId!=null)
		 * DefaultLogger.debug(this, "deleteId:" +deleteId.length);
		 */

		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();

		try {
			IShareDoc[] jspIShareDoc = processCheckList(checkListItem);
			IShareDoc[] dbIShareDoc = getCheckListDetails(jspIShareDoc);
			Long[] collateralIds = null;
			int count = 0;

			if ((jspIShareDoc != null) && (jspIShareDoc.length > 0)) {
				collateralIds = new Long[jspIShareDoc.length];
				for (int i = 0; i < jspIShareDoc.length; i++) {
					boolean recExist = false;
					if (checkListId == jspIShareDoc[i].getCheckListId()) {
						recExist = true;
						exceptionMap.put("errCheckListId" + i, new ActionMessage("error.checklistId.ownChecklist"));
						DefaultLogger.debug(this, ">>>>>>>>>> ERROR: ChecklistId SHARED TO ITSELF!!!");
						jspIShareDoc[i].setProfileId(0);
						jspIShareDoc[i].setSubProfileId(0);
						jspIShareDoc[i].setPledgorDtlId(0);
						jspIShareDoc[i].setCollateralId(0);
						collateralIds[count++] = new Long(jspIShareDoc[i].getCollateralId());
						DefaultLogger.debug(this, " Same  checkListItemId on row  " + i);
					}
					if (!recExist) {
						if ((dbIShareDoc != null) && (dbIShareDoc.length > 0)) {
							for (int j = 0; j < dbIShareDoc.length; j++) {
								if (jspIShareDoc[i].getCheckListId() == dbIShareDoc[j].getCheckListId()) {
									recExist = true;
									jspIShareDoc[i].setProfileId(dbIShareDoc[j].getProfileId());
									jspIShareDoc[i].setSubProfileId(dbIShareDoc[j].getSubProfileId());
									jspIShareDoc[i].setPledgorDtlId(dbIShareDoc[j].getPledgorDtlId());
									jspIShareDoc[i].setCollateralId(dbIShareDoc[j].getCollateralId());
									// DefaultLogger.debug(this,
									// ">>>>>>> setting the additional details"
									// );
									jspIShareDoc[i].setLeID(dbIShareDoc[j].getLeID());
									jspIShareDoc[i].setLeName(dbIShareDoc[j].getLeName());
									jspIShareDoc[i].setSecurityDtlId(dbIShareDoc[j].getSecurityDtlId());
									jspIShareDoc[i].setSecurityType(dbIShareDoc[j].getSecurityType());
									jspIShareDoc[i].setSecuritySubType(dbIShareDoc[j].getSecuritySubType());
									// DefaultLogger.debug(this,
									// ">>>>>>> jsp security details: " +
									// jspIShareDoc[i].getSecurityDtlId() +
									// " | " + jspIShareDoc[i].getSecurityType()
									// + " | " +
									// jspIShareDoc[i].getSecuritySubType());
									// DefaultLogger.debug(this,
									// ">>>>>>> dbi security details: " +
									// dbIShareDoc[i].getSecurityDtlId() + " | "
									// + dbIShareDoc[i].getSecurityType() +
									// " | " +
									// jspIShareDoc[i].getSecuritySubType());
									collateralIds[count++] = new Long(jspIShareDoc[i].getCollateralId());
									break;
								}
							}
						}
						if (!recExist) {
							exceptionMap.put("errCheckListId" + i, new ActionMessage("error.srChecklistId.notFound"));
							if (!(currEvent.equals(SecurityReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST) || currEvent
									.equals(SecurityReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST + "_prepare"))) {
								exceptionMap.put(ICommonEventConstant.STOP_COMMAND_CHAIN, null);
							}
							DefaultLogger.debug(this, ">>>>>>>>>> ERROR: ChecklistId NOT FOUND!!!");
							jspIShareDoc[i].setProfileId(0);
							jspIShareDoc[i].setSubProfileId(0);
							jspIShareDoc[i].setPledgorDtlId(0);
							jspIShareDoc[i].setCollateralId(0);
							collateralIds[count++] = new Long(jspIShareDoc[i].getCollateralId());
						}
					}
				}

				/*
				 * for (int k = 0;k<collateralIds.length;k++){
				 * DefaultLogger.debug(this, "deleteId" + collateralIds[k]); }
				 */
				HashMap collateralId_LmtProfileIdMap = null;
				long outputCollateralId = 0;
				Long collId = null;
				Long lmtProfileId = null;
				long outputLmtProfileId = 0;
				try {
					collateralId_LmtProfileIdMap = proxy.getCollateralIdForSharedDocs(((new Long(docNo)).longValue()),
							collateralIds);
				}
				catch (Exception e) {
					DefaultLogger.error(this, e.getMessage(), e);
					e.printStackTrace();
					throw (new CommandProcessingException(e.getMessage()));
				}

				collId = (Long) collateralId_LmtProfileIdMap.get("collateralId");
				if (collId != null) {
					outputCollateralId = collId.longValue();
				}

				lmtProfileId = (Long) collateralId_LmtProfileIdMap.get("lmtProfileId");
				if (lmtProfileId != null) {
					outputLmtProfileId = lmtProfileId.longValue();
				}

				DefaultLogger.debug(this, "outputcolateralId" + outputCollateralId);
				DefaultLogger.debug(this, "outputLmtProfileId" + outputLmtProfileId);

				String le_id_bca_ref_num = null;
				if (outputLmtProfileId != 0) {
					try {
						le_id_bca_ref_num = limitProxy.getLEIdAndBCARef(outputLmtProfileId);
					}
					catch (Exception e) {
						DefaultLogger.error(this, e.getMessage(), e);
						e.printStackTrace();
						throw (new CommandProcessingException(e.getMessage()));
					}
				}

				DefaultLogger.debug(this, "le_id_bca_ref_num" + le_id_bca_ref_num);
				jspIShareDoc = deleteCheckList(jspIShareDoc, outputCollateralId, exceptionMap, le_id_bca_ref_num);
				if ((jspIShareDoc != null) && (jspIShareDoc.length > 0)) {
					checkListItem.setShareCheckList(jspIShareDoc);
				}
				else {
					DefaultLogger.debug(this, ">>>>>>>>>> : Setting empty share doc into item!!!");
					List list = Collections.EMPTY_LIST;
					checkListItem.setShareCheckList((IShareDoc[]) list.toArray(new IShareDoc[0]));
				}
			}

			resultMap.put("status", status);
			resultMap.put("checkListItem", checkListItem);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		return returnMap;
	}

	private IShareDoc[] processCheckList(ICheckListItem checkListItem) {
		boolean shared = checkListItem.getShared();
		IShareDoc[] aIShareDoc = checkListItem.getShareCheckList();
		if ((aIShareDoc != null) && (aIShareDoc.length > 0)) {
		}
		else {
			return null;
		}

		/*
		 * ArrayList inputList = new ArrayList(aIShareDoc.length); for (int i =
		 * 0; i < aIShareDoc.length; i++) { inputList.add(aIShareDoc[i]); }
		 * //int index = 0; if (!shared) { Iterator itr = inputList.iterator();
		 * while (itr.hasNext()) { OBShareDoc shareDoc = (OBShareDoc)
		 * itr.next(); if (shareDoc.getDocShareId() == 0) { itr.remove(); }
		 * //else { // shareDoc.setIsDeletedInd(true); //} //index++; }
		 * 
		 * } else if (shared) { Iterator itr = inputList.iterator(); while
		 * (itr.hasNext()) { OBShareDoc objShareDoc = (OBShareDoc) itr.next();
		 * // if (deleteCheckListId[index] != null &&
		 * deleteCheckListId[index].equals(existingChkListId[index])) { if
		 * (objShareDoc.getDeleteCheckListId() != null &&
		 * objShareDoc.getDeleteCheckListId
		 * ().equals(objShareDoc.getExistingChkListId())) { if
		 * (objShareDoc.getDocShareId() == 0) { itr.remove(); } else { //
		 * objShareDoc.setIsDeletedInd(true); } } else if
		 * (objShareDoc.getCheckListId() == 0) { itr.remove(); } //else if
		 * (objShareDoc.getExistingChkListId() != null &&
		 * objShareDoc.getExistingChkListId().equals("Add")) { //} else { //}
		 * //index++; }
		 * 
		 * } else { DefaultLogger.debug(this, "     shared is UNKNOWN = " +
		 * shared); }
		 */
		return aIShareDoc;
	}

	private IShareDoc[] getCheckListDetails(IShareDoc[] aIShareDoc) {

		IShareDoc[] rShareDoc = null;
		try {
			if ((aIShareDoc != null) && (aIShareDoc.length > 0)) {
				String inputData[] = new String[aIShareDoc.length];
				for (int j = 0; j < aIShareDoc.length; j++) {
					OBShareDoc shareDoc = (OBShareDoc) aIShareDoc[j];
					inputData[j] = shareDoc.getCheckListId() + "";
				}
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				List returnList = proxy.getCheckListDetailsByCheckListId(inputData, CHECKLIST_CATEGORY);
				if ((returnList != null) && !returnList.isEmpty()) {
					rShareDoc = (IShareDoc[]) returnList.toArray(new IShareDoc[0]);
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
		}
		return rShareDoc;
	}

	// Method added by Pratheepa on 06-Oct-2006 for CR 100a
	private IShareDoc[] deleteCheckList(IShareDoc[] jspIShareDoc, long outputcolateralId, HashMap exceptionMap,
			String le_id_bca_ref_num) {
		IShareDoc[] rShareDoc = null;

		IShareDoc[] aIShareDoc = jspIShareDoc;

		ArrayList inputList = new ArrayList(aIShareDoc.length);
		for (int i = 0; i < aIShareDoc.length; i++) {
			inputList.add(aIShareDoc[i]);
		}

		Iterator itr = inputList.iterator();
		while (itr.hasNext()) {
			OBShareDoc objShareDoc = (OBShareDoc) itr.next();
			String leId = null;
			String bCARef = null;
			String secId = null;
			DefaultLogger.debug(this, "currr CollateralId" + objShareDoc.getCollateralId());
			DefaultLogger.debug(this, "todelete CollateralId" + outputcolateralId);
			DefaultLogger.debug(this, "objShareDoc.getDeleteCheckListId():" + objShareDoc.getDeleteCheckListId());
			DefaultLogger.debug(this, "objShareDoc.getExistingChkListId():" + objShareDoc.getExistingChkListId());
			// if (objShareDoc.getDeleteCheckListId() != null &&
			// objShareDoc.getDeleteCheckListId
			// ().equals(objShareDoc.getExistingChkListId())) {
			if ((objShareDoc.getDeleteCheckListId() != null) && (objShareDoc.getExistingChkListId() != null)) {
				if (objShareDoc.getCollateralId() == outputcolateralId) {
					if ((le_id_bca_ref_num != null) && (le_id_bca_ref_num.trim().length() > 0)) {
						int place = le_id_bca_ref_num.indexOf(",");
						StringBuffer sb_le_id_bca_ref_num = new StringBuffer(le_id_bca_ref_num);
						DefaultLogger.debug(this, "place" + place);
						leId = sb_le_id_bca_ref_num.substring(0, place);
						bCARef = sb_le_id_bca_ref_num.substring(place + 1, le_id_bca_ref_num.length());
						secId = objShareDoc.getSecurityDtlId();
						// DefaultLogger.debug(this, "leId" + leId);
						// DefaultLogger.debug(this, "bCARef" + bCARef);
						// DefaultLogger.debug(this, "secId" + secId);

					}
					ActionMessage ae = new ActionMessage("error.srChecklistId.tiedToSecurity", secId, leId, bCARef);
					exceptionMap.put("errLinkedToSecurity" + objShareDoc.getDeleteCheckListId(), ae);
					DefaultLogger.debug(this, "error" + ae);

				}
				else {
					DefaultLogger.debug(this, "Removing Record " + objShareDoc.getDeleteCheckListId());
					itr.remove();
				}
			}
		}
		if ((inputList != null) && !inputList.isEmpty()) {
			jspIShareDoc = (IShareDoc[]) inputList.toArray(new IShareDoc[0]);
		}
		else {
			DefaultLogger.debug(this, " deleteCheckList: Setting empty share doc ");
			List list = Collections.EMPTY_LIST;
			jspIShareDoc = (IShareDoc[]) list.toArray(new IShareDoc[0]);
		}

		return jspIShareDoc;

	}

}
