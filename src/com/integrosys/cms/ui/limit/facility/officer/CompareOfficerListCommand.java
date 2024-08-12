package com.integrosys.cms.ui.limit.facility.officer;

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
import com.integrosys.cms.app.limit.bus.IFacilityOfficer;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class CompareOfficerListCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				//{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } 
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "compareResultsOfficerList", "java.util.List", SERVICE_SCOPE },
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
					Set setFacilityOfficerActual = null;
					Set setFacilityOfficerStaging = null;
					IFacilityOfficer[] facilityOfficerActual = null;
					IFacilityOfficer[] facilityOfficerStaging = null;

					if (facilityTrxValue.getFacilityMaster() != null) {
						setFacilityOfficerActual = facilityTrxValue.getFacilityMaster().getFacilityOfficerSet();
						setFacilityOfficerActual = FacilityUtil.filterDeletedOfficer(setFacilityOfficerActual);
						facilityOfficerActual = (IFacilityOfficer[]) setFacilityOfficerActual
								.toArray(new IFacilityOfficer[0]);
						Arrays.sort(facilityOfficerActual, new FacilityOfficerComparator());
					}

					setFacilityOfficerStaging = facilityMasterObj.getFacilityOfficerSet();
					if (setFacilityOfficerStaging != null) {
						Set tempStaging = FacilityUtil.filterDeletedOfficer(setFacilityOfficerStaging);
						facilityOfficerStaging = (IFacilityOfficer[]) tempStaging
								.toArray(new IFacilityOfficer[0]);
						Arrays.sort(facilityOfficerStaging, new FacilityOfficerComparator());

						List compareResultsList = CompareOBUtil.compOBArray(facilityOfficerStaging,
								facilityOfficerActual);

						resultMap.put("recordSize", String.valueOf(compareResultsList.size()));
						resultMap.put("compareResultsOfficerList", compareResultsList);
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
