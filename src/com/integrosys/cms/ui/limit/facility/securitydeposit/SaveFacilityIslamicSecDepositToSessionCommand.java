package com.integrosys.cms.ui.limit.facility.securitydeposit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class SaveFacilityIslamicSecDepositToSessionCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ FacilityIslamicSecDepositForm.MAPPER, "com.integrosys.cms.app.limit.bus.IFacilityMaster", FORM_SCOPE }, 
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
			IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get(FacilityIslamicSecDepositForm.MAPPER);
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
