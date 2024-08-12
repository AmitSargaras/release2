/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.ccreceipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.IShareDoc;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.OBShareDoc;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/09/04 13:39:03 $ Tag: $Name: $
 */
public class SaveCheckListItemCommand extends AbstractCommand implements ICommonEventConstant {

	/** variable used in command when checklist not found */
	private static final String ERR_NOT_FOUND = "C/C ChecklistId does not exist";

	/** variable used in command for category */
	private static final String CHECKLIST_CATEGORY_CODE = "CC";

	/** variable used in command for sub category */
	private final String CHECKLIST_SUB_CATEGORY_CODE = "MAIN_BORROWER";

	/**
	 * Default Constructor
	 */
	public SaveCheckListItemCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				// {"checkListItem",
				// "com.integrosys.cms.app.checklist.bus.ICheckListItem"
				// ,FORM_SCOPE},
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				// {"checkListItemShare",
				// "com.integrosys.cms.app.checklist.bus.ICheckListItem"
				// ,FORM_SCOPE},
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "actionTypeName", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checkListObj", "com.integrosys.cms.app.checklist.bus.ICheckList", FORM_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ICheckList checkList = (ICheckList) map.get("checkList");
			ICheckListItem checkListItem = (ICheckListItem) map.get("checkListItem");
			int index = Integer.parseInt((String) map.get("index"));
			String actionTypeName = (String) map.get("actionTypeName");
			DefaultLogger.debug(this, "actionTypeName" + actionTypeName);

			DefaultLogger.debug(this, ">>>>>>>>>> REMOVING THE SHARE CHECKLIST PORTION FROM SaveCheckListItemCommand ");
			/*
			 * IShareDoc[] jspIShareDoc = checkListItem.getShareCheckList(); if
			 * (jspIShareDoc != null && jspIShareDoc.length > 0) {
			 * checkListItem.setShareCheckList(processIShareDoc(jspIShareDoc));
			 * }
			 */

			IShareDoc[] shareDoc = checkListItem.getShareCheckList();
			if (shareDoc != null) {
				for (int i = 0; i < shareDoc.length; i++) {
					DefaultLogger.debug(this, ">>>>>>> (SAVE Command) le id/name: " + shareDoc[i].getLeID() + " | "
							+ shareDoc[i].getLeName());
				}
			}

			ICheckListItem temp[] = checkList.getCheckListItemList();
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListItem[] newItems = proxy.getNextCheckListReceipts(checkListItem, actionTypeName);
			if (newItems != null) {
				temp[index] = newItems[0];
				if (newItems.length > 1) {
					ArrayList list = new ArrayList(Arrays.asList(temp));
					for (int i = 1; i < newItems.length; i++) {
						list.add(newItems[i]);
					}
					temp = (ICheckListItem[]) list.toArray(new OBCheckListItem[0]);
				}
				checkList.setCheckListItemList(temp);
			}
			resultMap.put("checkList", checkList);
			resultMap.put("checkListObj", checkList);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.info(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private IShareDoc[] processIShareDoc(IShareDoc[] jspIShareDoc) {
		IShareDoc[] dbIShareDoc = getCheckListDetails(jspIShareDoc);

		if ((jspIShareDoc != null) && (jspIShareDoc.length > 0)) {
			for (int i = 0; i < jspIShareDoc.length; i++) {
				boolean exist = false;
				if ((dbIShareDoc != null) && (dbIShareDoc.length > 0)) {
					for (int j = 0; j < dbIShareDoc.length; j++) {
						if (jspIShareDoc[i].getCheckListId() == dbIShareDoc[j].getCheckListId()) {
							exist = true;
							jspIShareDoc[i].setProfileId(dbIShareDoc[j].getProfileId());
							jspIShareDoc[i].setSubProfileId(dbIShareDoc[j].getSubProfileId());
							jspIShareDoc[i].setPledgorDtlId(dbIShareDoc[j].getPledgorDtlId());
							jspIShareDoc[i].setCollateralId(dbIShareDoc[j].getCollateralId());
							break;
						}
					}
				}
				if (!exist) {
					// invalidCheckListMap.put(String.valueOf(i),
					// ERR_NOT_FOUND);
					jspIShareDoc[i].setProfileId(0);
					jspIShareDoc[i].setSubProfileId(0);
					jspIShareDoc[i].setPledgorDtlId(0);
					jspIShareDoc[i].setCollateralId(0);

				}
			}
		}
		return jspIShareDoc;

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
		IShareDoc[] rShareDoc = null;
		try {
			if ((aIShareDoc != null) && (aIShareDoc.length > 0)) {
				String inputData[] = new String[aIShareDoc.length];
				for (int j = 0; j < aIShareDoc.length; j++) {
					OBShareDoc shareDoc = (OBShareDoc) aIShareDoc[j];
					inputData[j] = shareDoc.getCheckListId() + "";
				}
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				// List returnList =
				// proxy.getCheckListDetailsByCheckListId(inputData,
				// CHECKLIST_CATEGORY_CODE, CHECKLIST_SUB_CATEGORY_CODE);
				List returnList = proxy.getCheckListDetailsByCheckListId(inputData);
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

}
