/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.security;

import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/10/20 06:28:34 $ Tag: $Name: $
 */
public class RemoveSecurityCheckListCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public RemoveSecurityCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "removeIndex", "java.lang.String", REQUEST_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "mandatoryRows", "java.lang.String", REQUEST_SCOPE },{ "mandatoryDisplayRows", "java.lang.String", REQUEST_SCOPE },
				{ "checkedInVault", "java.lang.String", REQUEST_SCOPE },
				{ "checkedExtCustodian", "java.lang.String", REQUEST_SCOPE },
				{ "checkedAudit", "java.lang.String", REQUEST_SCOPE }, });
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
		HashMap exceptionMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		String removeIndex = (String) map.get("removeIndex");

		mapCheckListItem(map); // to retain the values of in-vault, external
		// custodian etc

		StringTokenizer st = new StringTokenizer(removeIndex, ",");
		int removeAry[] = new int[st.countTokens()];
		int i = 0;
		HashMap temp1 = new HashMap();
		while (st.hasMoreTokens()) {
			removeAry[i] = Integer.parseInt(st.nextToken());
			temp1.put(String.valueOf(removeAry[i]), "dd");
			i++;
		}
		ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
		boolean error = false;
		if (checkListTrxVal != null) {
			if (ICMSConstant.STATE_REJECTED.equals(checkListTrxVal.getStatus())) {
				ICheckListItem chkLst[] = checkListTrxVal.getStagingCheckList().getCheckListItemList();
				resultMap.put("checkList", checkListTrxVal.getStagingCheckList());
				if (!checkBeforeRemove(chkLst, temp1)) {
					error = true;
				}
			}
			else {
				ICheckListItem chkLst[] = checkListTrxVal.getCheckList().getCheckListItemList();
				resultMap.put("checkList", checkListTrxVal.getCheckList());
				if (!checkBeforeRemove(chkLst, temp1)) {
					error = true;
				}
			}
		}
		else {
			ICheckList temp = (ICheckList) map.get("checkList");
			ICheckListItem chkLst[] = temp.getCheckListItemList();
			if (!checkBeforeRemove(chkLst, temp1)) {
				error = true;
			}
			resultMap.put("checkList", temp);
		}

		if (error) {
			exceptionMap.put("remove.error", new ActionMessage("error.remove.checklist.items"));
		}
		else {
			DefaultLogger.debug(this, "removed  items" + removeAry.length);
			if (checkListTrxVal != null) {
				if (ICMSConstant.STATE_REJECTED.equals(checkListTrxVal.getStatus())) {
					checkListTrxVal.getStagingCheckList().removeItems(removeAry);
					resultMap.put("checkList", checkListTrxVal.getStagingCheckList());
				}
				else {
					checkListTrxVal.getCheckList().removeItems(removeAry);
					resultMap.put("checkList", checkListTrxVal.getCheckList());
				}
			}
			else {
				ICheckList temp = (ICheckList) map.get("checkList");
				temp.removeItems(removeAry);
				resultMap.put("checkList", temp);
			}
		}
		resultMap.put("checkListTrxVal", checkListTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	private boolean checkBeforeRemove(ICheckListItem chkLst[], HashMap temp) {
		boolean allowRemove = false;
		if ((chkLst == null) || (chkLst.length < 0)) {
			return allowRemove;
		}
		for (int i = 0; i < chkLst.length; i++) {
			ICheckListItem iCheckListItem = chkLst[i];
			if (!temp.containsKey(String.valueOf(i))) {
				if (!ICMSConstant.STATE_ITEM_NOT_USED.equals(iCheckListItem.getItemStatus())) {
					return true;
				}
			}
		}
		return allowRemove;
	}

	private void mapCheckListItem(HashMap map) {
		String mandatoryDisplayRows = (String) map.get("mandatoryDisplayRows");
		HashMap hmMandatoryDisplayRows = getMapFromString(mandatoryDisplayRows);
		String mandatoryRows = (String) map.get("mandatoryRows");
		String checkedInVault = (String) map.get("checkedInVault");
		String checkedExtCustodian = (String) map.get("checkedExtCustodian");
		String checkedAudit = (String) map.get("checkedAudit");
		ICheckList checkList = (ICheckList) map.get("checkList");

		HashMap hmMandatoryRows = getMapFromString(mandatoryRows);
		HashMap hmCheckedInVault = getMapFromString(checkedInVault);
		HashMap hmCheckedExtCustodian = getMapFromString(checkedExtCustodian);
		HashMap hmCheckedAudit = getMapFromString(checkedAudit);

		ICheckListItem temp[] = checkList.getCheckListItemList();
		if (temp != null) {
			for (int i = 0; i < temp.length; i++) {

				/**
				 * skip update of deleted items
				 */
				if (isItemDeleted(checkList, i)) {
					continue;
				}

				if (!checkList.getCheckListItemList()[i].getIsInherited()) {
					if (hmMandatoryRows.containsKey(String.valueOf(i))) {
						checkList.getCheckListItemList()[i].setIsMandatoryInd(true);
					}
					else {
						checkList.getCheckListItemList()[i].setIsMandatoryInd(false);
					}
				}
				if (!checkList.getCheckListItemList()[i].getIsInherited()) {
					if (hmMandatoryDisplayRows.containsKey(String.valueOf(i))) {
						checkList.getCheckListItemList()[i].setIsMandatoryDisplayInd(true);
					}
					else {
						checkList.getCheckListItemList()[i].setIsMandatoryDisplayInd(false);
					}
				}
				if (hmCheckedInVault.containsKey(String.valueOf(i))) {
					checkList.getCheckListItemList()[i].setIsInVaultInd(true);
				}
				else {
					checkList.getCheckListItemList()[i].setIsInVaultInd(false);
				}
				if (hmCheckedExtCustodian.containsKey(String.valueOf(i))) {
					checkList.getCheckListItemList()[i].setIsExtCustInd(true);
				}
				else {
					checkList.getCheckListItemList()[i].setIsExtCustInd(false);
				}
				if (hmCheckedAudit.containsKey(String.valueOf(i))) {
					checkList.getCheckListItemList()[i].setIsAuditInd(true);
				}
				else {
					checkList.getCheckListItemList()[i].setIsAuditInd(false);
				}
			}
		}
	}

	private HashMap getMapFromString(String commaSepInput) {
		HashMap hm = new HashMap();
		StringTokenizer st = new StringTokenizer(commaSepInput, ",");
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			hm.put(key, key);
		}
		return hm;
	}

	private boolean isItemDeleted(ICheckList checkList, int i) {
		return ICMSConstant.STATE_DELETED.equals(checkList.getCheckListItemList()[i].getItemStatus());
	}

}
