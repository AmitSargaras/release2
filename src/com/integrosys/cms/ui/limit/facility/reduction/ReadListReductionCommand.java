package com.integrosys.cms.ui.limit.facility.reduction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityReduction;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class ReadListReductionCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },				
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "nextTab", "java.lang.String", REQUEST_SCOPE },   
				{ "startIdx", "java.lang.String", REQUEST_SCOPE },
                { "errorMap", "java.util.Map", SERVICE_SCOPE },
                { "event", "java.lang.String", REQUEST_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "facilityReductions", "java.util.List", REQUEST_SCOPE },
				{ "currentTab", "java.lang.String", SERVICE_SCOPE },
				{ "recordSize", "java.lang.String", REQUEST_SCOPE }, { "startIdx", "java.lang.String", REQUEST_SCOPE }, 
		});
	}	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			// for ui tab
			String currentTab = (String) map.get("nextTab");
            if(StringUtils.isNotBlank(currentTab)) {
                result.put("currentTab", currentTab);
            }

			// pagination
			String startIdxStr = "0";
			if (map.get("startIdx") != null) {
				startIdxStr = (String) map.get("startIdx");
			}
			
			int startIdx = Integer.parseInt(startIdxStr);
			int recordSize = 0;
			
            //set officer action errors into message list
            Map errorMap = (Map) map.get("errorMap");
            if (errorMap != null) {
                ActionErrors errorMessages = (ActionErrors) errorMap.get("reduction");
                if (errorMessages != null) {
                    DefaultLogger.warn(this, "there is error message for facility officer, "
                            + "please retrieve from the display page.");
                    returnMap.put(MESSAGE_LIST, errorMessages);
                }
            }
            
            String event = (String)map.get("event");
            
            IFacilityMaster facilityMaster = (IFacilityMaster) map.get("facilityMasterObj");
			if (facilityMaster != null) {
				List facReductionList = new ArrayList();
				
				if (FacilityReductionAction.TAB_FACILITY_REDUCTION_CHECKER_PROCESS.equals(event)) {
					IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
					if (facilityTrxValue != null) {
						OBFacilityReduction[] actualReduction = null;
						OBFacilityReduction[] stageReduction = null;
						if (facilityTrxValue.getFacilityMaster() != null &&
								facilityTrxValue.getFacilityMaster().getFacilityReductions() != null) {
							actualReduction = (OBFacilityReduction[])facilityTrxValue.getFacilityMaster().
								getFacilityReductions().toArray(new OBFacilityReduction[0]);
						}
						if (facilityTrxValue.getStagingFacilityMaster() != null &&
								facilityTrxValue.getStagingFacilityMaster().getFacilityReductions() != null) {
							stageReduction = (OBFacilityReduction[])facilityTrxValue.getStagingFacilityMaster().
							getFacilityReductions().toArray(new OBFacilityReduction[0]);
						}
						facReductionList = CompareOBUtil.compOBArray(stageReduction,
								actualReduction);						
					}
				} else {
					facReductionList.addAll(facilityMaster.getFacilityReductions());
				}
				
				Collections.sort(facReductionList, new FacilityReductionComparator());				
				recordSize = facReductionList.size();
				
				if (recordSize > 0) {
					if ((startIdx + FacilityMainAction.RECORD_PER_PAGE) <= facReductionList.size()) {
						facReductionList = facReductionList.subList(startIdx, (startIdx + FacilityMainAction.RECORD_PER_PAGE));
					} else {
						facReductionList = facReductionList.subList(startIdx, startIdx
								+ ((facReductionList.size() - startIdx) % FacilityMainAction.RECORD_PER_PAGE));						
					}
					
					result.put("facilityReductions", facReductionList);
				}
			}
			result.put("recordSize", String.valueOf(recordSize));
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
