package com.integrosys.cms.ui.checklist.paripassuupdate;

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.IShareDoc;
import com.integrosys.cms.app.checklist.bus.OBShareDoc;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/07/19 09:08:30 $ Tag: $Name: $
 */
public class CheckerViewPariPassuReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CheckerViewPariPassuReceiptCommand() {
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
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "itemRef", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "stagingShareCheckList", "java.util.List", REQUEST_SCOPE },
				{ "stagingOb", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				{ "actualOb", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE }, });
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
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			long itemRef = Long.parseLong((String) map.get("itemRef"));
			ICheckListItem actual = null;
			ICheckListItem staging = null;
			if (checkListTrxVal.getCheckList() != null) {
				actual = getItem(checkListTrxVal.getCheckList().getCheckListItemList(), itemRef);
			}
			if (checkListTrxVal.getStagingCheckList() != null) {
				staging = getItem(checkListTrxVal.getStagingCheckList().getCheckListItemList(), itemRef);
			}
			if ((actual != null) && (staging == null) && actual.getViewable()) { // R1
																					// .5
																					// CR17
				staging = actual; // item that shared "in" by other checklist
									// are not found in staging, only actual
			}
			resultMap.put("actualOb", actual);
			resultMap.put("stagingOb", staging);
			resultMap.put("checkListItem", staging);
			resultMap.put("stagingShareCheckList", getShareCheckList(staging));

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private ICheckListItem getItem(ICheckListItem temp[], long itemRef) {
		ICheckListItem item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getCheckListItemRef() == itemRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}

	// get staging records to view by security checker
	private List getShareCheckList(ICheckListItem iCheckListItem) {

		List aProfileListId = new ArrayList();
		if (iCheckListItem == null) {
			DefaultLogger.debug(this, "iCheckListItem is null");
			return null;
		}

		IShareDoc[] iShareDocArray = iCheckListItem.getShareCheckList();
		if (iShareDocArray != null) {
			DefaultLogger.debug(this, "STAGING SHARE CHECKLIST " + iShareDocArray.length);
			for (int k = 0; k < iShareDocArray.length; k++) {
				DefaultLogger.debug(this, "ISHAREDOC PK " + iShareDocArray[k].getDocShareId());
				aProfileListId.add(iShareDocArray[k]);
			}
		}
		else {
			DefaultLogger.debug(this, "ISHAREDOC IS NULL");
		}

		List returnList = new ArrayList();
		if ((aProfileListId != null) && !aProfileListId.isEmpty()) {
			Iterator itr = aProfileListId.iterator();
			while (itr.hasNext()) {
				IShareDoc iShareDoc = (IShareDoc) itr.next();
				OBShareDoc oBShareDoc = new OBShareDoc();
				/*
				 * oBShareDoc.setDocShareId(iShareDoc.getDocShareId());
				 * oBShareDoc.setDetails(iShareDoc.getDetails());
				 * oBShareDoc.setProfileId(iShareDoc.getProfileId());
				 * oBShareDoc.setSecuritySubTypeDetailsFromDB();
				 * oBShareDoc.setCheckListId(iShareDoc.getCheckListId());
				 * oBShareDoc.setShareStatus(iShareDoc.getShareStatus());
				 * oBShareDoc.setDocShareIdRef(iShareDoc.getDocShareIdRef())
				 */;

				oBShareDoc.setDocShareId(iShareDoc.getDocShareId());
				oBShareDoc.setCheckListId(iShareDoc.getCheckListId());
				oBShareDoc.setProfileId(iShareDoc.getProfileId());
				oBShareDoc.setSubProfileId(iShareDoc.getSubProfileId());
				oBShareDoc.setPledgorDtlId(iShareDoc.getPledgorDtlId());
				oBShareDoc.setCollateralId(iShareDoc.getCollateralId());
				oBShareDoc.setDocShareIdRef(iShareDoc.getDocShareIdRef());
				oBShareDoc.setDetails(iShareDoc.getDetails());
				// oBShareDoc.setSecuritySubTypeDetailsFromDB() ;
				returnList.add(oBShareDoc);
			}
		}
		DefaultLogger.debug(this, "getShareCheckList " + returnList.size());
		return returnList;
	}

}
