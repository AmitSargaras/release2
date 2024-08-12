/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recreceipt;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.bus.ConvenantComparator;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.OBConvenantSubItem;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/09/21 12:27:54 $ Tag: $Name: $
 */
public class UpdateCovenantCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public UpdateCovenantCommand() {
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
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				{ "covenantItem", "com.integrosys.cms.app.recurrent.bus.IConvenant", SERVICE_SCOPE },
				{ "covenantSubItem", "com.integrosys.cms.app.recurrent.bus.IConvenantSubItem", FORM_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE }, { "subItemIndex", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList",
				SERVICE_SCOPE },
		// {"conList", "java.util.List", SERVICE_SCOPE},
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
		HashMap exceptionMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		IRecurrentCheckList recChkLst = (IRecurrentCheckList) map.get("recChkLst");
		IConvenant covenant = (IConvenant) map.get("covenantItem");
		IConvenantSubItem covenantSubItem = (IConvenantSubItem) map.get("covenantSubItem");

		String index = (String) map.get("index");
		String subItemIndex = (String) map.get("subItemIndex");

		if (covenant != null) {

			if (exceptionMap.isEmpty()) {
				IConvenantSubItem[] subItemList = covenant
						.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING);

				if ((subItemList.length > 0) && (covenantSubItem.getSubItemID() == subItemList[0].getSubItemID())
						&& ((covenantSubItem.getCheckedDate() != null) || (covenantSubItem.getWaivedDate() != null))) {
					DefaultLogger.debug(this, ">>>>>> First Item Checked/Waived <<<<< Update the Main Covenant Item");
					IConvenantSubItem nextSubItem = new OBConvenantSubItem();

					IConvenantSubItem[] aPendingSubItemList = covenant
							.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING);
					IConvenantSubItem[] aNonPendingSubItemList = covenant
							.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_NON_PENDING);

					DefaultLogger.debug(this, ">>>>>> First Item Received/Waived <<<<< "
							+ "Length of aPendingSubItemList: " + aPendingSubItemList.length
							+ " Length of aNonPendingSubItemList: " + aNonPendingSubItemList.length);

					if (aPendingSubItemList.length == 1) {
						if (aNonPendingSubItemList.length > 0) {
							if (covenantSubItem.getSubItemID() > aNonPendingSubItemList[0].getSubItemID()) {
								nextSubItem = aNonPendingSubItemList[0];
							}
							else {
								nextSubItem = covenantSubItem;
							}
						}
						else {
							nextSubItem = covenantSubItem;
						}
					}
					else {
						nextSubItem = aPendingSubItemList[1];
					}

					covenant.setInitialDocEndDate(nextSubItem.getDocEndDate());
					covenant.setInitialDueDate(nextSubItem.getDueDate());

					DefaultLogger.debug(this, ">>>>>> First Item Received/Waived <<<<< InitialDocEndDate is: "
							+ covenant.getInitialDocEndDate());
				}

				covenant.updateSubItem(Integer.parseInt(subItemIndex), covenantSubItem);
			}
			DefaultLogger.debug(this, "updated covenantSubItem into covenantItem");
		}
		if (exceptionMap.isEmpty()) {
			recChkLst.updateConvenant(Integer.parseInt(index), covenant);
			IConvenant conList[] = recChkLst.getConvenantList();
			if (conList != null) {
				// Added by Pratheepa for CR234
				Arrays.sort(conList, new ConvenantComparator());
				recChkLst.setConvenantList(conList);
			}

			resultMap.put("recChkLst", recChkLst);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		returnMap.put(COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}