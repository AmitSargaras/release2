package com.integrosys.cms.ui.limit.facility.incremental;

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityIncremental;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;


public class SaveIncrementalDetailToSessionCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ FacilityIncrementalForm.MAPPER, "com.integrosys.cms.app.limit.bus.OBFacilityIncremental", FORM_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "key", "java.lang.String", REQUEST_SCOPE}
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster",SERVICE_SCOPE }, 
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			OBFacilityIncremental incremental = (OBFacilityIncremental)map.get(FacilityIncrementalForm.MAPPER);
			int index = Integer.parseInt((String)map.get("key"));
			IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
			Set facilityIncrementalSet = facilityMasterObj.getFacilityIncrementals();
			Iterator itr = facilityIncrementalSet.iterator();
			
			int count = 0;
			while (itr.hasNext()) {
				if (count++ == index) {					
					OBFacilityIncremental tempOb = (OBFacilityIncremental)itr.next();
					facilityIncrementalSet.remove(tempOb);
					facilityIncrementalSet.add(incremental);					
					break;
				}
			}
			facilityMasterObj.setFacilityIncrementals(facilityIncrementalSet);
			result.put("facilityMasterObj", facilityMasterObj);
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
