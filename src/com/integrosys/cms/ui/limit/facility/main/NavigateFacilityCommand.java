package com.integrosys.cms.ui.limit.facility.main;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class NavigateFacilityCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "nextTab", "java.lang.String", REQUEST_SCOPE },
				{ "currentTab", "java.lang.String", SERVICE_SCOPE },
				{ "errorMap", "java.util.Map", SERVICE_SCOPE },
			});
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "errorMap", "java.util.Map", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		String nextTab = (String) map.get("nextTab");
		result.put("nextTab", nextTab);

		Map errorMap = (Map) map.get("errorMap");
		if (errorMap == null) {
			errorMap = new HashMap();
		}

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
