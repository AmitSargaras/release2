package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.*;

import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityRelationship;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;
import com.integrosys.cms.ui.limit.facility.main.RelationshipObjectValidator;
import org.apache.struts.action.ActionErrors;

public class ReadRelationshipCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "key", "java.lang.String", REQUEST_SCOPE },
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "compareResultsRelationshipList", "java.util.List", SERVICE_SCOPE },});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ RelationshipForm.MAPPER, "com.integrosys.cms.app.limit.bus.IFacilityRelationship", FORM_SCOPE },
				{ "facilityRelationshipObj", "com.integrosys.cms.app.limit.bus.IFacilityRelationship", SERVICE_SCOPE },
				{ "facilityRelationshipActual", "com.integrosys.cms.app.limit.bus.IFacilityRelationship", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			IFacilityMaster facilityMaster = (IFacilityMaster) map.get("facilityMasterObj");
			String key = (String) map.get("key");
			IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
			if (facilityMaster != null && key != null) {
				IFacilityRelationship facilityRelationshipStaging = null;
				IFacilityRelationship facilityRelationshipActual = null;
				if (facilityTrxValue == null
						|| !(ICMSConstant.STATE_PENDING_UPDATE.equals(facilityTrxValue.getStatus()) || ICMSConstant.STATE_PENDING_CREATE
								.equals(facilityTrxValue.getStatus()))) {
					Set setRelationships = facilityMaster.getFacilityRelationshipSet();
					if (setRelationships != null) {
						setRelationships = FacilityUtil.filterDeletedRelationship(setRelationships);
						IFacilityRelationship[] facilityRelationships = (IFacilityRelationship[]) setRelationships
								.toArray(new IFacilityRelationship[0]);
						Arrays.sort(facilityRelationships, new FacilityRelationshipComparator());
						result.put(RelationshipForm.MAPPER, facilityRelationships[(Integer.valueOf(key)).intValue()]);
						result.put("facilityRelationshipObj", facilityRelationships[(Integer.valueOf(key)).intValue()]);
					}
				}
				else {
					List compareResultsList = (List) map.get("compareResultsRelationshipList");
					CompareResult compareResult = (CompareResult) compareResultsList.get(Integer.valueOf(key)
							.intValue());
					facilityRelationshipStaging = (IFacilityRelationship) compareResult.getObj();
					facilityRelationshipActual = (IFacilityRelationship) compareResult.getOriginal();

					// if deleted then staging = actual, because the data in
					// staging is not exist anymore, jsp always reads from the
					// staging one
					/*if (compareResult.isDeleted()) {
						facilityRelationshipStaging = facilityRelationshipActual;
					}*/

					result.put(RelationshipForm.MAPPER, facilityRelationshipStaging);
					result.put("facilityRelationshipObj", facilityRelationshipStaging);
					result.put("facilityRelationshipActual", facilityRelationshipActual);
				}

                //Andy Wong: validate relationship single OB
                ActionErrors errorMessages = RelationshipObjectValidator.validateSingleObject((IFacilityRelationship) result.get("facilityRelationshipObj"));
                if (errorMessages != null) {
                    returnMap.put(MESSAGE_LIST, errorMessages);
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