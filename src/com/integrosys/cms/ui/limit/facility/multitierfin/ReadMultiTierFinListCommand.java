package com.integrosys.cms.ui.limit.facility.multitierfin;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;
import com.integrosys.cms.app.limit.bus.OBFacilityMultiTierFinancing;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class ReadMultiTierFinListCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "limit", "com.integrosys.cms.app.limit.bus.ILimit", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "nextTab", "java.lang.String", REQUEST_SCOPE }, { "startIdx", "java.lang.String", REQUEST_SCOPE },
				{ "errorMap", "java.util.Map", SERVICE_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "multiTierFins", "[Lcom.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing", REQUEST_SCOPE },
				{ "currentTab", "java.lang.String", SERVICE_SCOPE },
				{ "recordSize", "java.lang.String", REQUEST_SCOPE }, { "startIdx", "java.lang.String", REQUEST_SCOPE },
				{ "multiTierFinObj", "com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing", SERVICE_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			// for ui tab
			String currentTab = (String) map.get("nextTab");
			// Andy Wong: standandize tab routing
			if (StringUtils.isNotBlank(currentTab)) {
				result.put("currentTab", currentTab);
			}
			// if (StringUtils.isBlank(currentTab)) {
			// currentTab = "multiTierFin";
			// }
			// result.put("currentTab", currentTab);

			// pagination
			String temp = "";
			if (map.get("startIdx") != null) {
				temp = (String) map.get("startIdx");
			}
			else
				temp = "0";
			int startIdx = Integer.parseInt(temp);
			int recordSize = 0;

			// set multitier financing action errors into message list
			Map errorMap = (Map) map.get("errorMap");
			if (errorMap != null) {
				ActionErrors errorMessages = (ActionErrors) errorMap.get("multiTierFinancing");
				if (errorMessages != null) {
					DefaultLogger.warn(this, "there is error message for facility multitier financing, "
							+ "please retrieve from the display page.");
					returnMap.put(MESSAGE_LIST, errorMessages);
				}
			}

			IFacilityMaster facilityMaster = (IFacilityMaster) map.get("facilityMasterObj");
			if (facilityMaster != null) {
				Set multiTierFinSet = facilityMaster.getFacilityMultiTierFinancingSet();
				multiTierFinSet = FacilityUtil.filterDeletedMultiTierFin(multiTierFinSet);
				List multiTierFinList = new ArrayList();
				if (multiTierFinSet != null) {
					multiTierFinList.addAll(multiTierFinSet);
				}
				recordSize = multiTierFinList.size();

				if (multiTierFinList != null && multiTierFinList.size() != 0) {

					if ((startIdx + FacilityMainAction.RECORD_PER_PAGE) <= multiTierFinList.size()) {
						multiTierFinList = multiTierFinList.subList(startIdx,
								(startIdx + FacilityMainAction.RECORD_PER_PAGE));
					}
					else {
						multiTierFinList = multiTierFinList.subList(startIdx, startIdx
								+ ((multiTierFinList.size() - startIdx) % FacilityMainAction.RECORD_PER_PAGE));
					}
					IFacilityMultiTierFinancing[] multiTierFins = (IFacilityMultiTierFinancing[]) multiTierFinList
							.toArray(new OBFacilityMultiTierFinancing[multiTierFinList.size()]);

					result.put("multiTierFins", multiTierFins);
					result.put("startIdx", String.valueOf(startIdx));
				}
			}
			result.put("recordSize", String.valueOf(recordSize));
			result.put("multiTierFinObj", null);
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
