package com.integrosys.cms.ui.limit.facility.islamic;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityIslamicMaster;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class ReadFacilityIslamicMasterCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "limit", "com.integrosys.cms.app.limit.bus.ILimit", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "errorMap", "java.util.Map", SERVICE_SCOPE }, { "nextTab", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ FacilityIslamicMasterForm.MAPPER, "com.integrosys.cms.app.limit.bus.IFacilityIslamicMaster", FORM_SCOPE },
				{ "currentTab", "java.lang.String", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		String currentTab = (String) map.get("nextTab");
        //Andy Wong: standandize tab routing
        if(StringUtils.isNotBlank(currentTab)) {
		    result.put("currentTab", currentTab);
        }

		Map errorMap = (Map) map.get("errorMap");
		if (errorMap != null) {
			ActionErrors errorMessages = (ActionErrors) errorMap.get("islamicMaster");
			if (errorMessages != null) {
				DefaultLogger.warn(this, "there is error message for facility islamic master, "
						+ "please retrieve from the display page.");
				returnMap.put(MESSAGE_LIST, errorMessages);
			}
		}

		IFacilityMaster facilityMaster = (IFacilityMaster) map.get("facilityMasterObj");
		IFacilityIslamicMaster islamicMaster = null;
		if (facilityMaster != null) {
			islamicMaster = facilityMaster.getFacilityIslamicMaster();
		}
		result.put(FacilityIslamicMasterForm.MAPPER, islamicMaster);

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
