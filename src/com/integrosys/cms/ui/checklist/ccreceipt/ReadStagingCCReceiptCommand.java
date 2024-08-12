/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.ccreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.checklist.SharedDocumentsHelper;
import com.integrosys.cms.ui.common.LegalFirmList;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/07/20 06:19:09 $ Tag: $Name: $
 */
public class ReadStagingCCReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadStagingCCReceiptCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "forwardUser", "java.lang.String", REQUEST_SCOPE } // +OFFICE
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
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "legalFirmLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "legalFirmValues", "java.util.Collection", REQUEST_SCOPE },
				{ "custTrxDtList", "java.util.HashMap", SERVICE_SCOPE },
				{ "forwardCollection", "java.util.Collection", REQUEST_SCOPE }, // +
																				// OFFICE
				{ "checkListObj", "com.integrosys.cms.app.checklist.bus.ICheckList", FORM_SCOPE } });
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
			String custTypeTrxID = (String) map.get("custTypeTrxID");
			String event = (String) map.get(ICommonEventConstant.EVENT);
			DefaultLogger.debug(this, "TrxiD before backend call" + custTypeTrxID);
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListTrxValue checkListTrxVal = proxy.getCheckListByTrxID(custTypeTrxID);

			// Sorts checklist before putting into resultMap
			ICheckList checkList = checkListTrxVal.getStagingCheckList();
			SharedDocumentsHelper.mergeViewableCheckListItemIntoStaging(checkListTrxVal.getCheckList(), checkList); // R1
																													// .5
																													// CR17
			ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
			checkList.setCheckListItemList(sortedItems);

			resultMap.put("checkList", checkList);
			resultMap.put("checkListObj", checkList);

			resultMap.put("checkListTrxVal", checkListTrxVal);
			resultMap.put("flag", "Edit");
			if ("close_checklist_item".equals(event)) {
				resultMap.put("flag", "Close");
			}
			if ("cancel_checklist_item".equals(event)) {
				resultMap.put("flag", "Cancel");
			}
			if ("to_track".equals(event)) {
				resultMap.put("flag", "To Track");
			}
			resultMap.put("frame", "false");// used to hide frames when user
											// comes from to do list

			// CR-380 starts
			String countryCode = "none";
			// ICheckList checkList =(ICheckList)
			// checkListTrxVal.getStagingCheckList();
			if ((checkList != null) && (checkList.getCheckListLocation() != null)
					&& (checkList.getCheckListLocation().getCountryCode() != null)) {
				countryCode = checkList.getCheckListLocation().getCountryCode();
				// System.out.println("Country Code Cmd Class????????:"+
				// countryCode);
			}
			LegalFirmList legalFirmList = LegalFirmList.getInstance(countryCode);
			resultMap.put("legalFirmLabels", legalFirmList.getLegalFirmLabel());
			resultMap.put("legalFirmValues", legalFirmList.getLegalFirmProperty());
			// CR-380 ends

			resultMap.put("forwardCollection", checkListTrxVal.getNextRouteCollection()); // +
																							// OFFICE
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
