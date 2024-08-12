package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityRelationship;
import com.integrosys.cms.app.limit.bus.OBFacilityRelationship;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;
import com.integrosys.base.techinfra.diff.CompareResult;

public class CompareRelationshipListCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "compareResultsRelationshipList", "java.util.List", SERVICE_SCOPE },
				{ "recordSize", "java.lang.String", REQUEST_SCOPE }, };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
			IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
			if (facilityTrxValue != null) {
				if (facilityTrxValue.getStagingFacilityMaster() != null) {
					Set setFacilityRelationshipActual = null;
					Set setFacilityRelationshipStaging = null;
					IFacilityRelationship[] facilityRelationshipActual = null;
					IFacilityRelationship[] facilityRelationshipStaging = null;

					if (facilityTrxValue.getFacilityMaster() != null) {
						setFacilityRelationshipActual = facilityTrxValue.getFacilityMaster()
								.getFacilityRelationshipSet();
						Set tempActual = FacilityUtil.filterDeletedRelationship(setFacilityRelationshipActual);
						if(tempActual!=null){
							facilityRelationshipActual = (IFacilityRelationship[]) tempActual
								.toArray(new OBFacilityRelationship[tempActual.size()]);
						}
						Arrays.sort(facilityRelationshipActual, new FacilityRelationshipComparator());
					}

					setFacilityRelationshipStaging = facilityMasterObj.getFacilityRelationshipSet();
					if (setFacilityRelationshipStaging != null) {
						Set tempStaging = FacilityUtil.filterDeletedRelationship(setFacilityRelationshipStaging);
						facilityRelationshipStaging = (IFacilityRelationship[]) tempStaging
								.toArray(new IFacilityRelationship[0]);
						Arrays.sort(facilityRelationshipStaging, new FacilityRelationshipComparator());

						List compareResultsList = CompareOBUtil.compOBArray(facilityRelationshipStaging,
								facilityRelationshipActual);

						resultMap.put("recordSize", String.valueOf(compareResultsList.size()));
						resultMap.put("compareResultsRelationshipList", compareResultsList);
					}
					resultMap.put("facilityTrxValue", facilityTrxValue);

				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
