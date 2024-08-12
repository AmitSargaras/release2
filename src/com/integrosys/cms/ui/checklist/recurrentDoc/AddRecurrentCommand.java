/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDoc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import com.ibm.db2.jcc.am.ne;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListItem;
import com.integrosys.cms.ui.checklist.CheckListHelper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class AddRecurrentCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public AddRecurrentCommand() {
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
			//	{ "selectedValues", "java.lang.String", REQUEST_SCOPE } ,
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
//				{ "limitProfileId", "java.lang.String", REQUEST_SCOPE },
				{ "globalDocChkList", "java.util.ArrayList", SERVICE_SCOPE },
//				{ "subProfileId", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				
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
		return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "stockDocChkList", "java.util.HashMap", SERVICE_SCOPE },
				
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
		HashMap stockDocChkList = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
		
			DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
			ICheckListItem recurrentItem = (ICheckListItem) map.get("checkListItem");
			
			String selectedValues = recurrentItem.getActionParty();
			String[] selectedValuesItem= selectedValues.split("-");
			
			ICheckList recChkLst = (ICheckList) map.get("checkList");
			ArrayList list = (ArrayList) map.get("globalDocChkList");
			
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			if (recChkLst == null) {
				recChkLst = new OBCheckList();
			}
			DocumentSearchResultItem[] tempAry = (DocumentSearchResultItem[]) list.toArray(new DocumentSearchResultItem[list.size()]);
			ArrayList totalNewItems = new ArrayList();
			recChkLst.setCheckListType("REC");
			ICheckListItem[] existingItems = recChkLst.getCheckListItemList();
			if (existingItems != null) {
				totalNewItems = new ArrayList(Arrays.asList(existingItems));
			}
			if(selectedValuesItem!=null){
			for (int j = 0; j < selectedValuesItem.length; j++) {
				
			for (int i = 0; i < tempAry.length; i++) {
				DocumentSearchResultItem newItem = tempAry[i];
				if(selectedValuesItem[j].equals(newItem.getItemCode())){
				ICheckListItem parentItem = new OBCheckListItem();
				
				parentItem.setItem(newItem.getItem());
				parentItem.setStatementType(newItem.getStatementType());
				parentItem.setExpectedReturnDate(newItem.getExpiryDate());
				parentItem.setExpiryDate(newItem.getExpiryDate());
				
				totalNewItems.add(parentItem);
				}

			}
			}
			}
			recChkLst.setCheckListItemList((ICheckListItem[]) totalNewItems.toArray(new OBCheckListItem[0]));
			for(int i=0;i<recChkLst.getCheckListItemList().length;i++){
				ICheckListItem resultItem=(ICheckListItem)recChkLst.getCheckListItemList()[i];
			
					if("STOCK_STATEMENT".equals(resultItem.getStatementType())){
				stockDocChkList.put(resultItem.getItemCode(), resultItem.getItemCode());		
			}
			}
			
			resultMap.put("stockDocChkList", stockDocChkList);
			resultMap.put("checkList", recChkLst);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
