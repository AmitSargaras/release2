/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.cc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/03/17 02:27:41 $ Tag: $Name: $
 */
public class AddCCCheckListCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public AddCCCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "hiddenItemID", "java.lang.String", REQUEST_SCOPE },
				{ "ccAddCheckList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }, });
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
		DefaultLogger.debug(this, "Inside doExecute()");
		String hiddenItemID = (String) map.get("hiddenItemID");
		StringTokenizer st = new StringTokenizer(hiddenItemID, ",");

		HashMap hm = new HashMap();
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			hm.put(key, key);
		}
		ArrayList list = (ArrayList) map.get("ccAddCheckList");
		Iterator itr = list.iterator();

		ArrayList itemList = new ArrayList();
		while (itr.hasNext()) {
			IItem item = (OBItem) itr.next();
			if (hm.containsKey(String.valueOf(item.getItemCode()))) {
				itemList.add(item);
			}
		}
		IItem[] tempAry = (IItem[]) itemList.toArray(new IItem[itemList.size()]);

		ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
		if (checkListTrxVal != null) {
			if (ICMSConstant.STATE_REJECTED.equals(checkListTrxVal.getStatus())) {
				checkListTrxVal.getStagingCheckList().addItems(tempAry);
				resultMap.put("checkList", checkListTrxVal.getStagingCheckList());
			}
			else {
				checkListTrxVal.getCheckList().addItems(tempAry);
				resultMap.put("checkList", checkListTrxVal.getCheckList());
			}
		}
		else {
			ICheckList temp = (ICheckList) map.get("checkList");
			temp.addItems(tempAry);
			resultMap.put("checkList", temp);
		}

		ICheckList checklist = (ICheckList) resultMap.get("checkList");
		if (checklist.getCheckListItemList() != null) {
			Arrays.sort(checklist.getCheckListItemList());
		}
		resultMap.put("checkListTrxVal", checkListTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
