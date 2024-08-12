/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/LimitsAction.java,v 1.9 2006/09/27 06:09:07 hshii Exp $
 */

package com.integrosys.cms.ui.limit.limitaccount;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class RefreshLimitAccountCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "hostSystemCountry", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "hostSystemNameList", "java.util.List", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String hostSystemCountry = (String) map.get("hostSystemCountry");

		result.put("hostSystemNameList", CommonUtil.sortDropdown(UIUtil
				.getSourceSystemAccountNameList(hostSystemCountry)));

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}