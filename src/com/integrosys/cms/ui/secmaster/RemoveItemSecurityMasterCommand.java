/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.secmaster;

import java.util.HashMap;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class RemoveItemSecurityMasterCommand extends AbstractCommand implements ICommonEventConstant {

	public RemoveItemSecurityMasterCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "removeIndex", "java.lang.String", REQUEST_SCOPE },
				{ "itemTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "itemTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "isEdit", "java.lang.String", REQUEST_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			String removeIndex = (String) map.get("removeIndex");
			DefaultLogger.debug(this, "Selected Items to remove" + removeIndex);
			StringTokenizer st = new StringTokenizer(removeIndex, ",");
			int removeAry[] = new int[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens()) {
				removeAry[i] = Integer.parseInt(st.nextToken());
				i++;
			}
			ITemplateTrxValue itemTrxVal = (ITemplateTrxValue) map.get("itemTrxVal");
			DefaultLogger.debug(this, "removed  items" + removeAry.length);
			if (itemTrxVal != null) {
				if (ICMSConstant.STATE_REJECTED.equals(itemTrxVal.getStatus())) {
					itemTrxVal.getStagingTemplate().removeItems(removeAry);
					resultMap.put("template", itemTrxVal.getStagingTemplate());
				}
				else {
					itemTrxVal.getTemplate().removeItems(removeAry);
					resultMap.put("template", itemTrxVal.getTemplate());
				}
				resultMap.put("isEdit", "true");
			}
			else {
				ITemplate temp = (ITemplate) map.get("template");
				temp.removeItems(removeAry);
				resultMap.put("template", temp);
				resultMap.put("isEdit", "false");
			}
			resultMap.put("itemTrxVal", itemTrxVal);
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
