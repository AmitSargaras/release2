package com.integrosys.cms.ui.limit.facility.relationship;

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
import com.integrosys.cms.app.limit.bus.IFacilityRelationship;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class SaveRelationshipDetailToSessionCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ RelationshipForm.MAPPER, "com.integrosys.cms.app.limit.bus.IFacilityRelationship", FORM_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "facilityRelationshipObj", "com.integrosys.cms.app.limit.bus.IFacilityRelationship", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster",
				SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IFacilityRelationship facilityRelationship = (IFacilityRelationship) map.get(RelationshipForm.MAPPER);
		IFacilityRelationship facilityRelationshipObj = (IFacilityRelationship) map.get("facilityRelationshipObj");
		IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
		Set setFacilityRelationship = facilityMasterObj.getFacilityRelationshipSet();

		if (facilityRelationshipObj == null) { // if Create
			if (setFacilityRelationship == null) {
				setFacilityRelationship = new HashSet();
			}
			if (setFacilityRelationship.add(facilityRelationship)) {
				// setFacilityRelationship.add(facilityRelationship);
				facilityMasterObj.setFacilityRelationshipSet(setFacilityRelationship);
			}
			else {
				DefaultLogger.warn(this, "FacilityRelationship already exist");
				exceptionMap.put("uniqueCombination", new ActionMessage("error.relationship.already.exists",
						facilityRelationship.getCifNumber()));
			}
		}
		else { // if Update
			if (setFacilityRelationship.contains(facilityRelationshipObj)) {
				Iterator iter = setFacilityRelationship.iterator();
				int i = 0;
				while (iter.hasNext()) {
					IFacilityRelationship facilityRelationshipTemp = (IFacilityRelationship) iter.next();
					if (facilityRelationshipObj.equals(facilityRelationshipTemp)) {
						facilityRelationship.setCmsRefId(facilityRelationshipTemp.getCmsRefId());
						break;
					}
					i++;
				}
				setFacilityRelationship.remove(facilityRelationshipObj);
				if (!setFacilityRelationship.add(facilityRelationship)) {
					setFacilityRelationship.add(facilityRelationshipObj);
					exceptionMap.put("uniqueCombination", new ActionMessage("error.relationship.already.exists"));
				}
				facilityMasterObj.setFacilityRelationshipSet(setFacilityRelationship);
			}
		}
		result.put("facilityMasterObj", facilityMasterObj);

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
