package com.integrosys.cms.ui.limit.facility.securitydeposit;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityIslamicSecurityDeposit;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;


public class ReadFacilityIslamicSecDepositCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "errorMap", "java.util.Map", SERVICE_SCOPE }, 
				{ "nextTab", "java.lang.String", REQUEST_SCOPE }, });
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ FacilityIslamicSecDepositForm.MAPPER, "com.integrosys.cms.app.limit.bus.OBFacilityIslamicRentalRenewal", FORM_SCOPE },
				{ "currentTab", "java.lang.String", SERVICE_SCOPE }, });
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		String currentTab = (String) map.get("nextTab");
        if(StringUtils.isNotBlank(currentTab)) {
		    result.put("currentTab", currentTab);
        }

		Map errorMap = (Map) map.get("errorMap");
		if (errorMap != null) {
			ActionErrors errorMessages = (ActionErrors) errorMap.get("islamicSecrityDeposit");
			if (errorMessages != null) {
				DefaultLogger.warn(this, "there is error message for islamic rental renewal, "
						+ "please retrieve from the display page.");
				returnMap.put(MESSAGE_LIST, errorMessages);
			}
		}

		IFacilityMaster facilityMaster = (IFacilityMaster) map.get("facilityMasterObj");	
		OBFacilityIslamicSecurityDeposit ob = facilityMaster.getFacilityIslamicSecurityDeposit();
		
		result.put(FacilityIslamicSecDepositForm.MAPPER, ob);
		
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;		
	}	
}
