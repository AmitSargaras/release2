package com.integrosys.cms.ui.limit.facility.multitierfin;

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
import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;
import com.integrosys.cms.app.limit.bus.OBFacilityMultiTierFinancing;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class CompareMultiTierFinListCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "compareResultsMultiTierFinList", "java.util.List", SERVICE_SCOPE },
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
					Set multiTierFinSetActual = null;
					Set multiTierFinSetStaging = null;
					IFacilityMultiTierFinancing[] multiTierFinActual = null;
					IFacilityMultiTierFinancing[] multiTierFinStaging = null;

					if (facilityTrxValue.getFacilityMaster() != null) {
						multiTierFinSetActual = facilityTrxValue.getFacilityMaster().getFacilityMultiTierFinancingSet();
						multiTierFinSetActual = FacilityUtil.filterDeletedMultiTierFin(multiTierFinSetActual);
						multiTierFinActual = (IFacilityMultiTierFinancing[]) multiTierFinSetActual
								.toArray(new OBFacilityMultiTierFinancing[multiTierFinSetActual.size()]);
					}

					multiTierFinSetStaging = facilityMasterObj.getFacilityMultiTierFinancingSet();
					if (multiTierFinSetStaging != null) {
						Set tempStaging = FacilityUtil.filterDeletedMultiTierFin(multiTierFinSetStaging);
						multiTierFinStaging = (IFacilityMultiTierFinancing[]) tempStaging
								.toArray(new IFacilityMultiTierFinancing[0]);

						List compareResultsList = CompareOBUtil.compOBArray(multiTierFinStaging, multiTierFinActual);

						resultMap.put("recordSize", String.valueOf(compareResultsList.size()));
						resultMap.put("compareResultsMultiTierFinList", compareResultsList);
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
