/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.ccmaster;

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
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/09 09:21:19 $ Tag: $Name: $
 */
public class AddCCMasterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public AddCCMasterCommand() {
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
				{ "itemTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "ccAddTemplateList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE }, });
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
				{ "itemTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "isEdit", "java.lang.String", REQUEST_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE }, });
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
		DefaultLogger.debug(this, "Selected Items=" + hiddenItemID);
		StringTokenizer st = new StringTokenizer(hiddenItemID, ",");
		HashMap hm = new HashMap();
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			hm.put(key, key);
		}

		ArrayList list = (ArrayList) map.get("ccAddTemplateList");
		IItem tempAry[] = new OBItem[hm.size()];
		int i = 0;
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			DocumentSearchResultItem temp = (DocumentSearchResultItem) itr.next();
			if (hm.containsKey(String.valueOf(temp.getItem().getItemID()))) {
				tempAry[i] = temp.getItem();
				i++;
			}
		}

		ITemplateTrxValue itemTrxVal = (ITemplateTrxValue) map.get("itemTrxVal");
		DefaultLogger.debug(this, "added items=" + tempAry.length);

		if (itemTrxVal != null) {
			if (ICMSConstant.STATE_REJECTED.equals(itemTrxVal.getStatus())) {
				itemTrxVal.getStagingTemplate().addItems(tempAry);
				Arrays.sort(itemTrxVal.getStagingTemplate().getTemplateItemList());
				resultMap.put("template", itemTrxVal.getStagingTemplate());
			}
			else {
				itemTrxVal.getTemplate().addItems(tempAry);
				Arrays.sort(itemTrxVal.getTemplate().getTemplateItemList());
				resultMap.put("template", itemTrxVal.getTemplate());
			}

			resultMap.put("isEdit", "true");
		}
		else {
			ITemplate temp = (ITemplate) map.get("template");
			temp.addItems(tempAry);
			Arrays.sort(temp.getTemplateItemList());
			resultMap.put("template", temp);
			resultMap.put("isEdit", "false");
		}

		resultMap.put("itemTrxVal", itemTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
