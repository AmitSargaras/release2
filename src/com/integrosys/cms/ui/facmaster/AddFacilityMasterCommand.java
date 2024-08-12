/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.facmaster;

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
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/18 07:26:22 $ Tag: $Name: $
 */
public class AddFacilityMasterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public AddFacilityMasterCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "templateItem", "com.integrosys.cms.app.chktemplate.bus.OBItem", FORM_SCOPE },
				{ "hiddenItemID", "java.lang.String", REQUEST_SCOPE },
				{ "subType", "java.lang.String", REQUEST_SCOPE },
				{ "sAddTemplateList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE },
				{ "secTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "facCategory", "java.lang.String", SERVICE_SCOPE },
				{ "facType", "java.lang.String", SERVICE_SCOPE },
				{ "facSystem", "java.lang.String", SERVICE_SCOPE },});
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
				{ "secTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "isEdit", "java.lang.String", REQUEST_SCOPE },
				{ "dynamicPropertySetupList", "com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup",
						SERVICE_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE },
				{ "facCategory", "java.lang.String", REQUEST_SCOPE },
				{ "facType", "java.lang.String", REQUEST_SCOPE },
				{ "facSystem", "java.lang.String", REQUEST_SCOPE },});

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
		String facCategory =(String)map.get("facCategory");
		String facType = (String)map.get("facType");
		String facSystem = (String)map.get("facSystem");
		DefaultLogger.debug(this, "Selected Items=" + hiddenItemID);
		StringTokenizer st = new StringTokenizer(hiddenItemID, ",");
		HashMap hm = new HashMap();
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			hm.put(key, key);
		}
		ArrayList list = (ArrayList) map.get("sAddTemplateList");
		IItem tempAry[] = new OBItem[hm.size()];

		if (list != null) {
			Iterator itr = list.iterator();
			int i = 0;
			while (itr.hasNext()) {
				DocumentSearchResultItem temp = (DocumentSearchResultItem) itr.next();
				if (hm.containsKey(String.valueOf(temp.getItem().getItemID()))) {
					tempAry[i] = temp.getItem();
					i++;
				}
			}
		}
		ITemplateTrxValue itemTrxVal = (ITemplateTrxValue) map.get("secTrxVal");
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
		resultMap.put("secTrxVal", itemTrxVal);

		String subType = (String) map.get("subType");
		ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
		//if (subType.equalsIgnoreCase("AB102")) {
        if (subType.equalsIgnoreCase(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
			try {
				IDynamicPropertySetup[] dynamicPropertySetupList = proxy.getDynamicPropertySetup(subType);
				resultMap.put("dynamicPropertySetupList", dynamicPropertySetupList);
			}
			catch (CheckListTemplateException ex) {
				throw new CommandProcessingException("failed to retrieve dynamic property for security subtype 'AB102'");
			}
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		resultMap.put("facCategory",facCategory );
		resultMap.put("facType",facType );
		resultMap.put("facSystem",facSystem );
		return returnMap;
	}
}
