/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.otherreceipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
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
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.ui.checklist.ITagUntagImageConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.18 $
 * @since $Date: 2006/10/17 09:22:35 $ Tag: $Name: $
 */
public class SaveCheckListItemCommand extends AbstractCommand implements ICommonEventConstant, ITagUntagImageConstant {


	private static String CHECKLIST_CATEGORY_CODE = "CAM";

	private String CHECKLIST_SUB_CATEGORY_CODE = "";

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
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
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
				{ "actionPartyValues", "java.util.Collection", REQUEST_SCOPE },
				{ IS_IMAGE_TAG_PENDING, Boolean.class.getName(), SERVICE_SCOPE }
		});
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

			ICheckListItem temp[] = checkList.getCheckListItemList();
			Date docEffDate = null;
			if (checkListItem != null) {
				docEffDate = checkListItem.getEffectiveDate();
			}
			int deferCount=0;
			/*if("DEFER".equals(actionTypeName)||"UPDATE_DEFERRED".equals(actionTypeName)){
				if (checkListItem != null) {
					if(checkListItem.getDeferCount()==null || checkListItem.getDeferCount().trim().equals("")||checkListItem.getItemStatus().equals("PENDING_DEFER")){
					deferCount=1;
					if(checkListItem.getDeferCount()!=null && !checkListItem.getDeferCount().trim().equals("")){
					deferCount=Integer.parseInt(checkListItem.getDeferCount());
					}
					checkListItem.setDeferCount(String.valueOf(deferCount));
						
					}else{
						deferCount=Integer.parseInt(checkListItem.getDeferCount());
						deferCount=deferCount+1;
						checkListItem.setDeferCount(String.valueOf(deferCount));
					}
				}
				}*/
			/*if(checkListItem.getDeferExtendedDate()!=null){
				checkListItem.setExpectedReturnDate(checkListItem.getDeferExtendedDate());
				checkListItem.setDeferExtendedDate(null);
			}*/
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
			
			/*
			 * Code for Audit trail individual By Abhijit R
			 * 
			 */
			
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			Date d = DateUtil.getDate();
			
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			Date applicationDate=new Date();
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					 applicationDate=new Date(generalParamEntries[i].getParamValue());
				}
			}
			
			//date.setTime(d.getTime());
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
			DefaultLogger.debug(this,"date from general param:"+applicationDate);
			DefaultLogger.debug(this,"Login id from global scope:"+user.getLoginID());
			checkListItem.setUpdatedBy(user.getLoginID());
			checkListItem.setUpdatedDate(applicationDate);
			
			
			/*
			 * 
			 */
			// Priya - CMSSP-751 - Encountered TD while updating the Insurance
			// document with the blank effective date - Start
			String dateChange = (String) map.get("isEffDateChanged");

			// This value should match the value in COMMON CODE PARAMETER (TIME_FREQ)
			String day="1";
			String week="2";
			String month="3";
			String year="4";
			if(checkListItem.getExpiryDate()==null){
			if(checkListItem.getDocDate()!=null){
			if(checkListItem.getItem()!=null){
				if(checkListItem.getItem().getTenureType()!=null){
					Date docDate = null;
					docDate=checkListItem.getDocDate();
					if(checkListItem.getItem().getTenureType().trim().equals(day)){
						Date changedExpDate = null;
						changedExpDate  = CommonUtil.rollUpDateByDays(docDate, checkListItem.getItem().getTenureCount());
						checkListItem.setExpiryDate(changedExpDate);
					}
					if(checkListItem.getItem().getTenureType().trim().equals(week)){
						Date changedExpDate = null;
						changedExpDate  = CommonUtil.rollUpDateByWeeks(docDate, checkListItem.getItem().getTenureCount());
						checkListItem.setExpiryDate(changedExpDate);
					}
					if(checkListItem.getItem().getTenureType().trim().equals(month)){
						Date changedExpDate = null;
						changedExpDate  = CommonUtil.rollUpDateByMonths(docDate, checkListItem.getItem().getTenureCount());
						checkListItem.setExpiryDate(changedExpDate);
					}
					if(checkListItem.getItem().getTenureType().trim().equals(year)){
						Date changedExpDate = null;
						changedExpDate  = CommonUtil.rollUpDateByYears(docDate, checkListItem.getItem().getTenureCount());
						checkListItem.setExpiryDate(changedExpDate);
					}
				}
			}
		}
			}
			
			ICheckListItem[] iCheckListItems=checkList.getCheckListItemList();
			for(int j=0;j<iCheckListItems.length;j++){
				
				
				/*if(iCheckListItems[j].getDeferExpiryDate()!=null && checkListItem.getDeferExpiryDate()!=null){
					Calendar calendar1 = Calendar.getInstance();
					  Calendar calendar2 = Calendar.getInstance();
					  calendar1.set(checkListItem.getDeferExpiryDate().getYear(),checkListItem.getDeferExpiryDate().getMonth(),checkListItem.getDeferExpiryDate().getDate());
					  calendar2.set(iCheckListItems[j].getDeferExpiryDate().getYear(), iCheckListItems[j].getDeferExpiryDate().getMonth(), iCheckListItems[j].getDeferExpiryDate().getDate());
					  long milliseconds1 = calendar1.getTimeInMillis();
					  long milliseconds2 = calendar2.getTimeInMillis();
					  long diff = milliseconds1 - milliseconds2;
					  long diffSeconds = diff / 1000;
					  long diffMinutes = diff / (60 * 1000);
					  long diffHours = diff / (60 * 60 * 1000);
					  long diffDays = diff / (24 * 60 * 60 * 1000);
					if(iCheckListItems[j].getDeferedDays()!=null && !iCheckListItems[j].getDeferedDays().trim().equals("")){
						long deferedDays=Long.parseLong(iCheckListItems[j].getDeferedDays());
						diffDays=diffDays+deferedDays;
					}
					  checkListItem.setDeferedDays(String.valueOf(diffDays));
				}*/
				
				
				if(iCheckListItems[j].getCheckListItemID()==checkListItem.getCheckListItemID()){
					if(checkListItem.getExpiryDate()==null){
					if(checkListItem.getDocDate()!=null){
						if(iCheckListItems[j].getDocDate()!=null){
					if((checkListItem.getDocDate().compareTo(iCheckListItems[j].getDocDate())!=0)){	
//						checkListItem.setDocDate(iCheckListItems[j].getDocDate());
					if(checkListItem.getItem()!=null){
						if(checkListItem.getItem().getTenureType()!=null){
							Date docDate = null;
							docDate=checkListItem.getDocDate();
							if(checkListItem.getItem().getTenureType().trim().equals(day)){
								Date changedExpDate = null;
								changedExpDate  = CommonUtil.rollUpDateByDays(docDate, checkListItem.getItem().getTenureCount());
								checkListItem.setExpiryDate(changedExpDate);
							}
							if(checkListItem.getItem().getTenureType().trim().equals(week)){
								Date changedExpDate = null;
								changedExpDate  = CommonUtil.rollUpDateByWeeks(docDate, checkListItem.getItem().getTenureCount());
								checkListItem.setExpiryDate(changedExpDate);
							}
							if(checkListItem.getItem().getTenureType().trim().equals(month)){
								Date changedExpDate = null;
								changedExpDate  = CommonUtil.rollUpDateByMonths(docDate, checkListItem.getItem().getTenureCount());
								checkListItem.setExpiryDate(changedExpDate);
							}
							if(checkListItem.getItem().getTenureType().trim().equals(year)){
								Date changedExpDate = null;
								changedExpDate  = CommonUtil.rollUpDateByYears(docDate, checkListItem.getItem().getTenureCount());
								checkListItem.setExpiryDate(changedExpDate);
								}
							}
							}
						}
					}else{
						if(checkListItem.getItem()!=null){
							if(checkListItem.getItem().getTenureType()!=null){
								Date docDate = null;
								docDate=checkListItem.getDocDate();
								if(checkListItem.getItem().getTenureType().trim().equals(day)){
									Date changedExpDate = null;
									changedExpDate  = CommonUtil.rollUpDateByDays(docDate, checkListItem.getItem().getTenureCount());
									checkListItem.setExpiryDate(changedExpDate);
								}
								if(checkListItem.getItem().getTenureType().trim().equals(week)){
									Date changedExpDate = null;
									changedExpDate  = CommonUtil.rollUpDateByWeeks(docDate, checkListItem.getItem().getTenureCount());
									checkListItem.setExpiryDate(changedExpDate);
								}
								if(checkListItem.getItem().getTenureType().trim().equals(month)){
									Date changedExpDate = null;
									changedExpDate  = CommonUtil.rollUpDateByMonths(docDate, checkListItem.getItem().getTenureCount());
									checkListItem.setExpiryDate(changedExpDate);
								}
								if(checkListItem.getItem().getTenureType().trim().equals(year)){
									Date changedExpDate = null;
									changedExpDate  = CommonUtil.rollUpDateByYears(docDate, checkListItem.getItem().getTenureCount());
									checkListItem.setExpiryDate(changedExpDate);
									}
								}
								}
					}
					}
					}
					}
				
				}
			
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
			ICheckListItem[] newItems = proxy.getNextCheckListReceipts(checkListItem, actionTypeName);
			if (newItems != null) {
				temp[index] =newItems[0] ;
				//temp[0] = newItems[0];
				ArrayList list = new ArrayList(Arrays.asList(temp));
				list.remove(index);
//				swapItems(temp,newItems,index);
				if (newItems.length > 1) {
					
					for (int i = 1; i < newItems.length; i++) {
						list.add(0,newItems[i]);
					}
					
				}
				
				LinkedList list2 = new LinkedList();
				list2.addAll(list);
				list2.addFirst(newItems[0]);
				temp = (ICheckListItem[]) list2.toArray(new OBCheckListItem[0]);
				checkList.setCheckListItemList(temp);
			}
			resultMap.put("checkList", checkList);
			resultMap.put(IS_IMAGE_TAG_PENDING,null);
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
		List returnList = new ArrayList();
		IShareDoc[] rShareDoc = null;
		try {
			if ((aIShareDoc != null) && (aIShareDoc.length > 0)) {
				String inputData[] = new String[aIShareDoc.length];
				for (int j = 0; j < aIShareDoc.length; j++) {
					OBShareDoc shareDoc = (OBShareDoc) aIShareDoc[j];
					inputData[j] = shareDoc.getCheckListId() + "";
				}
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				// returnList =
				// proxy.getCheckListDetailsByCheckListId(inputData,
				// CHECKLIST_CATEGORY_CODE, CHECKLIST_SUB_CATEGORY_CODE);
				returnList = proxy.getCheckListDetailsByCheckListId(inputData);
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
