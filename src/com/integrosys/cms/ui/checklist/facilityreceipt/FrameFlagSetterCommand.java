/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.facilityreceipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.ui.common.LegalFirmList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/06/27 09:14:02 $ Tag: $Name: $
 */
public class FrameFlagSetterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public FrameFlagSetterCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "legalFirm", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", SERVICE_SCOPE }, 
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "prev_event", "java.lang.String", REQUEST_SCOPE }, // used bt
																		// R1.5,
																		// CR17:
																		// Share
																		// Checklist
				{ "event", "java.lang.String", REQUEST_SCOPE }, // used bt R1.5,
																// CR17: Share
																// Checklist
                { "isErrorEvent", "java.lang.String", REQUEST_SCOPE }, // used to determine error from lawyer mandatory check
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
		return (new String[][] { { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "legalFirmLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "session.checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "legalFirmValues", "java.util.Collection", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE},
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }, });
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
			String event = (String) map.get("event");
			
			resultMap.put("frame", map.get("frame"));
			// CR-380 starts
			String countryCode = "none";
			ICheckList checkList = (ICheckList) map.get("checkList");
			if ((checkList != null) && (checkList.getCheckListLocation() != null)
					&& (checkList.getCheckListLocation().getCountryCode() != null)) {
				countryCode = checkList.getCheckListLocation().getCountryCode();
			}
			HashMap selectedArrayMap =  new HashMap();
			resultMap.put("selectedArrayMap", selectedArrayMap);

            LegalFirmList legalFirmList = LegalFirmList.getInstance(countryCode);
			resultMap.put("legalFirmLabels", legalFirmList.getLegalFirmLabel());
			resultMap.put("legalFirmValues", legalFirmList.getLegalFirmProperty());
			ICheckListItem checkListItem = (ICheckListItem) map.get("checkListItem");
			// Start modification on 13May04
			String hLegalFirm = (String) map.get("leaglFirm");
			int h=0;
			if("conform_inactive".equals(event)){
				
				ICheckListItem temp[] = checkList.getCheckListItemList();
				ArrayList list = new ArrayList(Arrays.asList(temp));
				
				ICheckListItem newItem = null;
				for (int j = 0; j < temp.length; j++) {
					if(temp[j].getCheckListItemID()==checkListItem.getCheckListItemID()){
						
						String status=temp[j].getDocumentStatus();
						if(status.equals("ACTIVE")){
							//temp[j].setDocumentStatus("PENDING_INACTIVE");
							 newItem=(ICheckListItem)list.get(j);
							newItem.setDocumentStatus("PENDING_INACTIVE");
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
							newItem.setUpdatedBy(user.getLoginID());
							newItem.setUpdatedDate(applicationDate);
							
							
							/*
							 * 
							 */
							list.remove(j);
							//list.add(0, newItem);				
							
						}
						break;
					}
				}
				LinkedList list2 = new LinkedList();
				list2.addAll(list);
				list2.addFirst(newItem);
				temp = (ICheckListItem[]) list2.toArray(new OBCheckListItem[0]);
							
				//temp= (ICheckListItem[]) list.toArray(new OBCheckListItem[0]);
				checkList.setCheckListItemList(temp);
			}
			
			if("approve_checklist_item".equals(event)){
				ICheckListItem temp[] = checkList.getCheckListItemList();
				for (int j = 0; j < temp.length; j++) {					
						String status=temp[j].getDocumentStatus();
						if(status.equals("PENDING_INACTIVE")){
							temp[j].setDocumentStatus("INACTIVE");				
											
					}
				}
			}
			if (checkList != null) {
				if (hLegalFirm != null) {
					checkList.setLegalFirm(hLegalFirm);
				}
				resultMap.put("session.checkList", checkList);
			}
			// End modification on 13May04
			// CR-380 ends

            
            // R1.5-CR17 starts
			String prevEvent = (String) map.get("prev_event");
			String currEvent = (String) map.get("event");
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>> PREV_EVENT: " + prevEvent);
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>> CURR_EVENT: " + currEvent);

			// use for redirection -- use by SecurityReceiptAction only, no need
			// to set in ResultDescriptor
			if (currEvent.equals(FacilityReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST)
					|| currEvent.equals(FacilityReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST + "_prepare")) {
				resultMap.put("prev_event", prevEvent);
			}
			// R1.5-CR17 Ends

            //to check if this is an error event with the lawyer mandatory check
            //if its an error event, then set checklist to null so that mapper will not remap the values for display.
            Boolean isErrorEvent = (Boolean) map.get("isErrorEvent");
            if(isErrorEvent!=null && isErrorEvent.booleanValue()) {
                DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>**************** isErrorEvent");
                resultMap.put("checkListForm", null);
            } else {
                resultMap.put("checkListForm", checkList);
                DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>**************** NOT ERROR EVENT");                
            }
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
