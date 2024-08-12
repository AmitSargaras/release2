package com.integrosys.cms.ui.limit.facility.multitierfin;

import java.util.HashMap;
import java.util.Set;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class DeleteMultiTierFinCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "key", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster",
				SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
		Set facilityMultiTierFinancingActual = null;
		if (facilityTrxValue != null && facilityTrxValue.getFacilityMaster() != null) {
			facilityMultiTierFinancingActual = facilityTrxValue.getFacilityMaster().getFacilityMultiTierFinancingSet();
		}

		IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
		Set multiTierFinSetAll = facilityMasterObj.getFacilityMultiTierFinancingSet();
		Set multiTierFinSet = FacilityUtil.filterDeletedMultiTierFin(multiTierFinSetAll);
		String key = (String) map.get("key");
		if (multiTierFinSet != null) {
			IFacilityMultiTierFinancing[] multiTierFins = (IFacilityMultiTierFinancing[]) multiTierFinSet
					.toArray(new IFacilityMultiTierFinancing[0]);
			IFacilityMultiTierFinancing delMultiTierFinObj = multiTierFins[Integer.parseInt(key)];

			boolean isCreate = isCreateCheck(facilityMultiTierFinancingActual, delMultiTierFinObj);
			multiTierFinSetAll.remove(delMultiTierFinObj);

			if (!isCreate) {
				delMultiTierFinObj.setStatus("D");
				multiTierFinSetAll.add(delMultiTierFinObj);
			}
		}
		result.put("facilityMasterObj", facilityMasterObj);

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	public boolean isCreateCheck(Set actualSet, IFacilityMultiTierFinancing delObj) {
		boolean isCreate = true;
		if (actualSet == null || actualSet.size() == 0) {
			return true;
		}

		IFacilityMultiTierFinancing[] facilityMultiTierFinancingActual = (IFacilityMultiTierFinancing[]) actualSet
				.toArray(new IFacilityMultiTierFinancing[0]);
		if (delObj.getId() == 0) {
			return true;
		}
		else {
			for (int i = 0; i < facilityMultiTierFinancingActual.length; i++) {
				if (delObj.getCmsRefId().equals(facilityMultiTierFinancingActual[i].getCmsRefId())) {
					isCreate = false;
					break;
				}
			}
		}
		return isCreate;
	}
}