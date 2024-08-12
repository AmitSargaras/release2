package com.integrosys.cms.ui.limit.facility.multitierfin;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;
import com.integrosys.cms.ui.limit.facility.main.MultiTierFinanceObjectValidator;
import org.apache.struts.action.ActionErrors;

public class ReadMultiTierFinCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "key", "java.lang.String", REQUEST_SCOPE },
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "compareResultsMultiTierFinList", "java.util.List", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ MultiTierFinForm.MAPPER, "com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing", FORM_SCOPE },
				{ "multiTierFinObj", "com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing", SERVICE_SCOPE },
				{ "multiTierFinActual", "com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IFacilityMaster facilityMaster = (IFacilityMaster) map.get("facilityMasterObj");
		String key = (String) map.get("key");
		IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
		if (facilityMaster != null && key != null) {
			IFacilityMultiTierFinancing multiTierFinStaging = null;
			IFacilityMultiTierFinancing multiTierFinActual = null;
			if (facilityTrxValue == null
					|| !(ICMSConstant.STATE_PENDING_UPDATE.equals(facilityTrxValue.getStatus()) || ICMSConstant.STATE_PENDING_CREATE
							.equals(facilityTrxValue.getStatus()))) {
				Set multiTierFinSet = facilityMaster.getFacilityMultiTierFinancingSet();
				if (multiTierFinSet != null) {
					multiTierFinSet = FacilityUtil.filterDeletedMultiTierFin(multiTierFinSet);
					IFacilityMultiTierFinancing[] multiTierFins = (IFacilityMultiTierFinancing[]) multiTierFinSet
							.toArray(new IFacilityMultiTierFinancing[0]);
					result.put(MultiTierFinForm.MAPPER, multiTierFins[(Integer.valueOf(key)).intValue()]);
					result.put("multiTierFinObj", multiTierFins[(Integer.valueOf(key)).intValue()]);
				}
			}
			else {
				List compareResultsList = (List) map.get("compareResultsMultiTierFinList");
				CompareResult compareResult = (CompareResult) compareResultsList.get(Integer.valueOf(key).intValue());
				multiTierFinStaging = (IFacilityMultiTierFinancing) compareResult.getObj();
				multiTierFinActual = (IFacilityMultiTierFinancing) compareResult.getOriginal();

				// if deleted then staging = actual, because the data in
				// staging is not exist anymore, jsp always reads from the
				// staging one
				/*
				 * if (compareResult.isDeleted()) { multiTierFinStaging =
				 * multiTierFinActual; }
				 */

				result.put(MultiTierFinForm.MAPPER, multiTierFinStaging);
				result.put("multiTierFinObj", multiTierFinStaging);
				result.put("multiTierFinActual", multiTierFinActual);
			}

            //validate multitier financing single OB
            String gppPaymentModeValue = "";
            if (facilityMaster.getFacilityIslamicMaster() != null
                    && facilityMaster.getFacilityIslamicMaster().getGppPaymentModeValue() != null) {
                gppPaymentModeValue = facilityMaster.getFacilityIslamicMaster().getGppPaymentModeValue();
            }
            ActionErrors errorMessages = MultiTierFinanceObjectValidator.validateSingleObject(
                    (IFacilityMultiTierFinancing) result.get("multiTierFinObj"), gppPaymentModeValue);
            if (errorMessages != null) {
                returnMap.put(MESSAGE_LIST, errorMessages);
            }
        }
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}