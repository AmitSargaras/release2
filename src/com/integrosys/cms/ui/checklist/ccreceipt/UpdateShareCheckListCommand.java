package com.integrosys.cms.ui.checklist.ccreceipt;

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

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Feb 2, 2006 Time: 11:01:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateShareCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private static final String CHECKLIST_CATEGORY = "CC";

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
		return (new String[][] { { "custCategory", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, // used bt R1.5,
																// CR17: Share
																// Checklist
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
		return (new String[][] {
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
		// {"invalidCheckListMap", "java.util.HashMap", REQUEST_SCOPE},
		// {"inValidCheckListId", "java.lang.Boolean", REQUEST_SCOPE},
		});
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
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		ICheckListItem checkListItem = (ICheckListItem) inputMap.get("checkListItem");
		ICheckList checkList = (ICheckList) inputMap.get("checkList");
		long checkListId = checkList.getCheckListID();
		String currEvent = (String) inputMap.get("event");

		String status = checkListItem.getItemStatus();
		// HashMap invalidCheckListMap = new HashMap(); //no longer in use
		// boolean inValidCheckListId = false;
		try {
			IShareDoc[] jspIShareDoc = processCheckList(checkListItem);
			IShareDoc[] dbIShareDoc = getCheckListDetails(jspIShareDoc);
			if ((jspIShareDoc != null) && (jspIShareDoc.length > 0)) {
				for (int i = 0; i < jspIShareDoc.length; i++) {
					boolean recExist = false;
					if (checkListId == jspIShareDoc[i].getCheckListId()) {
						// inValidCheckListId = true;
						recExist = true;
						// invalidCheckListMap.put(String.valueOf(i),
						// ERR_SHARE_TO_ITSELF); //to be removed
						exceptionMap.put("errCheckListId" + i, new ActionMessage("error.checklistId.ownChecklist"));
						DefaultLogger.debug(this, ">>>>>>>>>> ERROR: ChecklistId SHARED TO ITSELF!!!");
						jspIShareDoc[i].setProfileId(0);
						jspIShareDoc[i].setSubProfileId(0);
						jspIShareDoc[i].setPledgorDtlId(0);
						jspIShareDoc[i].setCollateralId(0);
						// DefaultLogger.debug(this,
						// " Same  checkListItemId on row  " + i);
					}
					// DefaultLogger.debug(this, ">>>>>>>>>>>. recExists: " +
					// recExist);
					if (!recExist) {
						if ((dbIShareDoc != null) && (dbIShareDoc.length > 0)) {
							for (int j = 0; j < dbIShareDoc.length; j++) {
								if (jspIShareDoc[i].getCheckListId() == dbIShareDoc[j].getCheckListId()) {
									recExist = true;
									jspIShareDoc[i].setProfileId(dbIShareDoc[j].getProfileId());
									jspIShareDoc[i].setSubProfileId(dbIShareDoc[j].getSubProfileId());
									jspIShareDoc[i].setPledgorDtlId(dbIShareDoc[j].getPledgorDtlId());
									jspIShareDoc[i].setCollateralId(dbIShareDoc[j].getCollateralId());
									// newly added
									// DefaultLogger.debug(this,
									// ">>>>>>> setting the additional details"
									// );
									jspIShareDoc[i].setLeID(dbIShareDoc[j].getLeID());
									jspIShareDoc[i].setLeName(dbIShareDoc[j].getLeName());
									jspIShareDoc[i].setSecurityDtlId(dbIShareDoc[j].getSecurityDtlId());
									jspIShareDoc[i].setSecurityType(dbIShareDoc[j].getSecurityType());
									jspIShareDoc[i].setSecuritySubType(dbIShareDoc[j].getSecuritySubType());
									// DefaultLogger.debug(this,
									// ">>>>>>> le id/name: " +
									// jspIShareDoc[i].getLeID() + " | " +
									// jspIShareDoc[i].getLeName());
									// DefaultLogger.debug(this,
									// ">>>>>>> le id/name: " +
									// dbIShareDoc[i].getLeID() + " | " +
									// dbIShareDoc[i].getLeName());
									break;
								}
							}
						}
						if (!recExist) {
							// inValidCheckListId = true;
							// invalidCheckListMap.put(String.valueOf(i),
							// ERR_NOT_FOUND); //to be removed
							exceptionMap.put("errCheckListId" + i, new ActionMessage("error.ccChecklistId.notFound"));
							if (!(currEvent.equals(CCReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST) || currEvent
									.equals(CCReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST + "_prepare"))) {
								exceptionMap.put(ICommonEventConstant.STOP_COMMAND_CHAIN, null);
							}

							DefaultLogger.debug(this, ">>>>>>>>>> ERROR: ChecklistId NOT FOUND!!!");
							jspIShareDoc[i].setProfileId(0);
							jspIShareDoc[i].setSubProfileId(0);
							jspIShareDoc[i].setPledgorDtlId(0);
							jspIShareDoc[i].setCollateralId(0);
						}
					}
				}
			}

			if ((jspIShareDoc != null) && (jspIShareDoc.length > 0)) {
				checkListItem.setShareCheckList(jspIShareDoc);
			}
			else {
				DefaultLogger.debug(this, ">>>>>>>>>> ERROR: Setting empty share doc into item!!!");
				List list = Collections.EMPTY_LIST;
				checkListItem.setShareCheckList((IShareDoc[]) list.toArray(new IShareDoc[0]));
			}

			/*
			 * //DefaultLogger.debug(this,
			 * " >>>>>>>>> CHECK SETTING the share checklist"); if (jspIShareDoc
			 * != null && jspIShareDoc.length > 0) { //DefaultLogger.debug(this,
			 * " >>>>>>>>> (Update share checklist) SET the share checklist!!");
			 * checkListItem.setShareCheckList(jspIShareDoc); }
			 * 
			 * IShareDoc[] shareDoc = checkListItem.getShareCheckList();
			 * if(shareDoc != null) { for(int i=0; i<shareDoc.length; i++) {
			 * DefaultLogger.debug(this, ">>>>>>> (UPDATE Command) le id/name: "
			 * + shareDoc[i].getLeID() + " | " + shareDoc[i].getLeName()); } }
			 */

			resultMap.put("status", status);
			resultMap.put("checkListItem", checkListItem);
			// resultMap.put("invalidCheckListMap", invalidCheckListMap);
			// resultMap.put("inValidCheckListId", inValidCheckListId ?
			// Boolean.TRUE : Boolean.FALSE );

			//if(currEvent.equals(CCReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST)
			// ) {
			// resultMap.put("prev_event", prevEvent); //use for redirection --
			// use by CCReceiptAction only, no need to set in ResultDescriptor
			// }

		}
		catch (Exception e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap); // use
																					// this
																					// to
																					// replace
																					// the
																					// one
																					// above
		return returnMap;
	}

	/**
	 * used for process the Document share checkListId removed if it is deleted
	 * by user
	 * @param checkListItem
	 * @return array of IShareDoc
	 */
	private IShareDoc[] processCheckList(ICheckListItem checkListItem) {
		boolean shared = checkListItem.getShared();
		IShareDoc[] aIShareDoc = checkListItem.getShareCheckList();
		DefaultLogger.debug(this, "aIShareDoc length: " + ((aIShareDoc == null) ? "null" : aIShareDoc.length + ""));
		if ((aIShareDoc != null) && (aIShareDoc.length > 0)) {
			ArrayList inputList = new ArrayList(aIShareDoc.length);
			for (int i = 0; i < aIShareDoc.length; i++) {
				inputList.add(aIShareDoc[i]);
			}
			int index = 0;
			if (!shared) {
				Iterator itr = inputList.iterator();
				while (itr.hasNext()) {
					OBShareDoc shareDoc = (OBShareDoc) itr.next();
					if (shareDoc.getDocShareId() == 0) {
						itr.remove();
					} // else {
					// shareDoc.setIsDeletedInd(true);
					// }
					index++;
				}

			}
			else if (shared) {
				Iterator itr = inputList.iterator();
				while (itr.hasNext()) {
					OBShareDoc objShareDoc = (OBShareDoc) itr.next();
					if ((objShareDoc.getDeleteCheckListId() != null)
							&& objShareDoc.getDeleteCheckListId().equals(objShareDoc.getExistingChkListId())) {
						if (objShareDoc.getDocShareId() == 0) {
							itr.remove();
						}
						else {
							// objShareDoc.setIsDeletedInd(true);
						}
					}
					else if (objShareDoc.getCheckListId() == 0) {
						itr.remove();
					}
					else if ((objShareDoc.getExistingChkListId() != null)
							&& objShareDoc.getExistingChkListId().equals("Add")) {
					}
					else {
					}
					index++;
				}
			}
			else {
				DefaultLogger.debug(this, "  shared is UNKNOWN = " + shared);
			}
			// DefaultLogger.debug(this," Ending processCheckList");
		}
		return aIShareDoc;

	}

	/**
	 * Private Method valids the checklistID by calling DAO through 1) Proxy
	 * (SBCheckListProxyManagerBean) 2) SBBean (SBCheckListBusManagerBean ) 3)
	 * EBBean (EBCheckListHomeBean ) 4) DAO (CheckListDAO )
	 * 
	 * @param aIShareDoc contains list of CheckListId
	 * @return List contains valid of CheckListId
	 */
	private IShareDoc[] getCheckListDetails(IShareDoc[] aIShareDoc) {
		DefaultLogger.debug(this, " calling getCheckListDetails");
		IShareDoc[] rShareDoc = null;
		try {
			if ((aIShareDoc != null) && (aIShareDoc.length > 0)) {
				String inputData[] = new String[aIShareDoc.length];
				for (int j = 0; j < aIShareDoc.length; j++) {
					OBShareDoc shareDoc = (OBShareDoc) aIShareDoc[j];
					inputData[j] = shareDoc.getCheckListId() + "";
				}
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				// DefaultLogger.debug(this,
				// "  Calling CheckListDAO,  CustCategory =" +
				// CHECKLIST_SUB_CATEGORY_CODE);
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
		DefaultLogger.debug(this, " Ending getCheckListDetails");
		return rShareDoc;
	}
}