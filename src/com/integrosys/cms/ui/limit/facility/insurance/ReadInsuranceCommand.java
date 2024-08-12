package com.integrosys.cms.ui.limit.facility.insurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityInsurance;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;
import org.apache.commons.lang.StringUtils;

public class ReadInsuranceCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "limit", "com.integrosys.cms.app.limit.bus.ILimit", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "nextTab", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { InsuranceForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "currentTab", "java.lang.String", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			// untuk ui tab
			String currentTab = (String) map.get("nextTab");
            if(StringUtils.isNotBlank(currentTab)) {
                result.put("currentTab", currentTab);
            }

			IFacilityMaster facilityMaster = (IFacilityMaster) map.get("facilityMasterObj");
			if (facilityMaster != null) {
				Set setInsurances = facilityMaster.getFacilityInsuranceSet();
				if (setInsurances != null) {
					List listInsurances = new ArrayList();
					Iterator iterInsurances = setInsurances.iterator();
					while (iterInsurances.hasNext()) {
						IFacilityInsurance facilityInsurance = (IFacilityInsurance) iterInsurances.next();
						if (facilityMaster.getId() == facilityInsurance.getFacilityMasterId()) {
							listInsurances.add(facilityInsurance);
						}
					}
					result.put(InsuranceForm.MAPPER, listInsurances);
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
