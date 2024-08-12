/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.paripassuupdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.18 $
 * @since $Date: 2006/10/17 09:22:35 $ Tag: $Name: $
 */
public class SaveCheckListItemCommand extends AbstractCommand implements ICommonEventConstant {



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
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE }, // R1
																											// .5
																											// CR17
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "actionTypeName", "java.lang.String", REQUEST_SCOPE },
				{ "isEffDateChanged", "java.lang.String", REQUEST_SCOPE } });
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
				{ "leyes", "java.lang.String", REQUEST_SCOPE },
				{ "actionPartyLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "actionPartyValues", "java.util.Collection", REQUEST_SCOPE }, });
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
			if(checkListItem.getItemStatus().equalsIgnoreCase("PENDING_RECEIVED")){
				checkListItem.setItemStatus("RECEIVED");
			}
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

			ICheckListItem temp[] = checkList.getCheckListItemList();
			Date docEffDate = null;
			if (checkListItem != null) {
				docEffDate = checkListItem.getEffectiveDate();
			}
			int deferCount=0;
			if (checkListItem != null) {
				if(checkListItem.getDeferCount()==null || checkListItem.getDeferCount().trim().equals("")){
				deferCount=1;
				checkListItem.setDeferCount(String.valueOf(deferCount));
					
				}else{
					deferCount=Integer.parseInt(checkListItem.getDeferCount());
					deferCount=deferCount+1;
					checkListItem.setDeferCount(String.valueOf(deferCount));
				}
			}
			if(checkListItem.getDeferExtendedDate()!=null){
				checkListItem.setExpectedReturnDate(checkListItem.getDeferExtendedDate());
				checkListItem.setDeferExtendedDate(null);
			}
			if(checkListItem.getDeferExpiryDate()!=null && checkListItem.getExpectedReturnDate()!=null){
			Calendar calendar1 = Calendar.getInstance();
			  Calendar calendar2 = Calendar.getInstance();
			  calendar1.set(checkListItem.getDeferExpiryDate().getYear(),checkListItem.getDeferExpiryDate().getMonth(),checkListItem.getDeferExpiryDate().getDate());
			  calendar2.set(checkListItem.getExpectedReturnDate().getYear(), checkListItem.getExpectedReturnDate().getMonth(), checkListItem.getExpectedReturnDate().getDate());
			  long milliseconds1 = calendar1.getTimeInMillis();
			  long milliseconds2 = calendar2.getTimeInMillis();
			  long diff =  milliseconds2-milliseconds1;
			  long diffSeconds = diff / 1000;
			  long diffMinutes = diff / (60 * 1000);
			  long diffHours = diff / (60 * 60 * 1000);
			  long diffDays = diff / (24 * 60 * 60 * 1000);
			/*if(checkListItem.getDeferedDays()!=null && !checkListItem.getDeferedDays().trim().equals("")){
				long deferedDays=Long.parseLong(checkListItem.getDeferedDays());
				diffDays=diffDays+deferedDays;
			}*/
			  checkListItem.setDeferedDays(String.valueOf(diffDays));
		}
			// Priya - CMSSP-751 - Encountered TD while updating the Insurance
			// document with the blank effective date - Start
			String dateChange = (String) map.get("isEffDateChanged");

			if ((temp != null) && (dateChange != null) && dateChange.equals("true")) {
				for (int i = 0; i < temp.length; i++) {
					Date changedDate = null;
					if (checkListItem != null) {
						if ((checkListItem.getCheckListItemRef() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
								&& (checkListItem.getCheckListItemRef() == temp[i].getParentCheckListItemRef())) {
							// If the Effective date is Not Null, Set Child docs
							// ExpiryDate as EffectiveDate + 60days
							if (docEffDate != null) {
								changedDate = CommonUtil.rollUpDateByDays(docEffDate, 60);// calendar
																							// .
																							// getTime
																							// (
																							// )
																							// ;
							}
							temp[i].setExpiryDate(changedDate);
							// checkList.setCheckListItemList (temp); shifted
							// out to below (outside the for loop)
						}
					}
				}

				checkList.setCheckListItemList(temp);
			}
			// CMSSP-751 - end

			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListItem[] newItems = proxy.getNextCheckListReceiptsOld(checkListItem, actionTypeName);
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



}
