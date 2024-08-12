/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.annexure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.limit.bus.LimitDAO;
/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class ReadAnnexureCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ReadAnnexureCommand() {
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
				{ "subItemIndex", "java.lang.String", REQUEST_SCOPE },
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },
				{ "actionType", "java.lang.String", REQUEST_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue", SERVICE_SCOPE },
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE }
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
				{ "recurrentItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", FORM_SCOPE },
				{ "recurrentSubItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem", FORM_SCOPE },
				{ "recurrentItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", SERVICE_SCOPE },
				{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "recurrentSubItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem", SERVICE_SCOPE },
				{ "actionType", "java.lang.String", REQUEST_SCOPE },
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE }
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
		int index = Integer.parseInt((String) map.get("index"));
		int subItemIndex = Integer.parseInt((String) map.get("subItemIndex"));
		String actionType = (String) map.get("actionType");
		String custTypeTrxID = (String) map.get("custTypeTrxID");
		 List transactionHistoryList= new ArrayList();
		 IRecurrentCheckListTrxValue recurrentCheckListTrxValue = (IRecurrentCheckListTrxValue)  map.get("checkListTrxVal");
		IRecurrentCheckList recChkLst = (IRecurrentCheckList) map.get("recChkLst");
		IRecurrentCheckListItem[] list = recChkLst.getCheckListItemList();
		IRecurrentCheckListItem item = null;
		IRecurrentCheckListSubItem subItem = null;
		if ((list != null) && (index >= 0)) {
			item = list[index];
			if (item != null) {
				IRecurrentCheckListSubItem[] subItemsList = item.getRecurrentCheckListSubItemList();
				if ((subItemsList != null) && (subItemIndex >= 0)) {
					subItem = subItemsList[subItemIndex];
				}
			}
		}
		ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
		 transactionHistoryList = customerDAO.getTransactionHistoryList(recurrentCheckListTrxValue.getTransactionID());
		  resultMap.put("transactionHistoryList", transactionHistoryList);
		  LimitDAO limitDao = new LimitDAO();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("CMS_RECURRENT_DOC_SUB_ITEM",subItem.getSubItemID(),"SUB_ITEM_ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		resultMap.put("custTypeTrxID", custTypeTrxID);
		resultMap.put("actionType", actionType);
		resultMap.put("recurrentItem", item);
		resultMap.put("recurrentSubItem", subItem);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
