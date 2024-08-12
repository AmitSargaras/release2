/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.cam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: jtan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/04/08 06:58:10 $ Tag: $Name: $
 */
public class AddCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public AddCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "mandatoryRows", "java.lang.String", REQUEST_SCOPE },
				{ "mandatoryDisplayRows", "java.lang.String", REQUEST_SCOPE },
				{ "checkedInVault", "java.lang.String", REQUEST_SCOPE },
				{ "checkedExtCustodian", "java.lang.String", REQUEST_SCOPE },
				{ "checkedAudit", "java.lang.String", REQUEST_SCOPE },
                { "collateralID", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "ccAddCheckList", "java.util.ArrayList", SERVICE_SCOPE } });
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

		ICheckList checkList = (ICheckList) map.get("checkList");
		String mandatoryDisplayRows = (String) map.get("mandatoryDisplayRows");
		HashMap hmMandatoryDisplayRows = getMapFromString(mandatoryDisplayRows);
		
		String mandatoryRows = (String) map.get("mandatoryRows");
		String checkedInVault = (String) map.get("checkedInVault");
		String checkedExtCustodian = (String) map.get("checkedExtCustodian");
		String checkedAudit = (String) map.get("checkedAudit");

		HashMap hmMandatoryRows = getMapFromString(mandatoryRows);
		HashMap hmCheckedInVault = getMapFromString(checkedInVault);
		HashMap hmCheckedExtCustodian = getMapFromString(checkedExtCustodian);
		HashMap hmCheckedAudit = getMapFromString(checkedAudit);
        String collateralID = (String) map.get("collateralID");

		ICheckListItem temp[] = checkList.getCheckListItemList();
		if (temp != null) {
			for (int i = 0; i < temp.length; i++) {

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

		IItem item[];
		try {
            item = this.checklistProxyManager.getItemList(checkList);
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("failed to find template for template id ["
					+ checkList.getTemplateID() + "]", ex);
		}
		catch (CheckListException ex) {
			throw new CommandProcessingException("failed to get item list for checklist [" + checkList + "]", ex);
		}

		List list = new ArrayList();
		if ((item != null) && (item.length > 0)) {
			for (int i = 0; i < item.length; i++) {
				if(item[i].getStatus()!=null){
				if(item[i].getStatus().equals("ENABLE"))
				list.add(item[i]);
				}
			}
		}

		Collections.sort(list);
		resultMap.put("ccAddCheckList", list);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
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
