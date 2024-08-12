package com.integrosys.cms.ui.limit.facility.officer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityOfficer;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class SaveOfficerDetailToSessionCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ OfficerForm.MAPPER, "com.integrosys.cms.app.limit.bus.IFacilityOfficer", FORM_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "facilityOfficerObj", "com.integrosys.cms.app.limit.bus.IFacilityOfficer", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster",
				SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			IFacilityOfficer facilityOfficer = (IFacilityOfficer) map.get(OfficerForm.MAPPER);
			IFacilityOfficer facilityOfficerObj = (IFacilityOfficer) map.get("facilityOfficerObj");
			IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
			Set setFacilityOfficer = facilityMasterObj.getFacilityOfficerSet();

			if (facilityOfficerObj == null) { // if Create

				if (setFacilityOfficer == null) {
					setFacilityOfficer = new HashSet();
				}
				if (setFacilityOfficer.add(facilityOfficer)) {
					facilityMasterObj.setFacilityOfficerSet(setFacilityOfficer);
				}
				else {
					DefaultLogger.warn(this, "FacilityOfficer already exist");
					exceptionMap.put("uniqueCombination", new ActionMessage("error.officer.already.exists"));
				}
			}
			else { // if Update
				if (setFacilityOfficer.contains(facilityOfficerObj)) {
					Iterator iter = setFacilityOfficer.iterator();
					int i = 0;
					while (iter.hasNext()) {
						IFacilityOfficer facilityOfficerTemp = (IFacilityOfficer) iter.next();
						if (facilityOfficerObj.equals(facilityOfficerTemp)) {
							facilityOfficer.setCmsRefId(facilityOfficerTemp.getCmsRefId());
							break;
						}
						i++;
					}
					setFacilityOfficer.remove(facilityOfficerObj);

					if(!setFacilityOfficer.add(facilityOfficer)){
						setFacilityOfficer.add(facilityOfficerObj);
						exceptionMap.put("uniqueCombination", new ActionMessage("error.officer.already.exists"));
					}
					
					facilityMasterObj.setFacilityOfficerSet(setFacilityOfficer);
				}
			}
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
