package com.integrosys.cms.ui.limit.facility.reduction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityReduction;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.incremental.FacilityIncrementalAction;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class ReadReductionCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "key", "java.lang.String", REQUEST_SCOPE },				
				{ "event", "java.lang.String", REQUEST_SCOPE},
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "sessionFacReductionObj", "com.integrosys.cms.app.limit.bus.OBFacilityReduction", SERVICE_SCOPE },
		});
	}	

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ FacilityReductionForm.MAPPER, "java.lang.Object", FORM_SCOPE },
				{ "sessionFacReductionObj", "com.integrosys.cms.app.limit.bus.OBFacilityReduction", SERVICE_SCOPE },
				{ "facilityReductionActual", "com.integrosys.cms.app.limit.bus.OBFacilityReduction", SERVICE_SCOPE }, 
		});
	}	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {

			IFacilityMaster facilityMaster = (IFacilityMaster) map.get("facilityMasterObj");
			String key = (String) map.get("key");
			String event = (String)map.get("event");
			OBFacilityReduction obj = null;
			List reductionList = new ArrayList();
			reductionList.addAll(facilityMaster.getFacilityReductions());
			
			// checker process
			if (FacilityIncrementalAction.EVENT_ERROR.equals(event) ||
					FacilityIncrementalAction.EVENT_ERROR_WO_FRAME.equals(event)) {
				// when there is valiation error
				obj = (OBFacilityReduction)map.get("sessionFacReductionObj");
				result.put("sessionFacReductionObj", obj);
				HashMap objMap = new HashMap();
				objMap.put("obj", obj);
				result.put(FacilityReductionForm.MAPPER, objMap);				
			} else {		
				for (int i = 0; i < reductionList.size(); i++) {
					OBFacilityReduction tempOb = (OBFacilityReduction)reductionList.get(i);
					if (FacilityReductionAction.EVENT_VIEW_DETAIL_CHECKER_PROCESS.equals(event)) {					
						if (tempOb.getCmsReferenceId().toString().equals(key)) {
							obj = tempOb;
							break;
						}
					} else {
						if (i == Integer.parseInt(key)) {
							obj = tempOb;
							break;
						}
					}
				}
				
				if (FacilityReductionAction.EVENT_VIEW_DETAIL_CHECKER_PROCESS.equals(event)) {		
					IFacilityTrxValue trxValue = (IFacilityTrxValue)map.get("facilityTrxValue");
					if (trxValue.getFacilityMaster() != null && 
							trxValue.getFacilityMaster().getFacilityReductions() != null) {
						reductionList = new ArrayList();
						reductionList.addAll(trxValue.getFacilityMaster().getFacilityReductions());
						
						for (int i = 0; i < reductionList.size(); i++) {
							OBFacilityReduction tempOb = (OBFacilityReduction)reductionList.get(i);
							if (tempOb.getCmsReferenceId().toString().equals(key)) {
								result.put("facilityReductionActual", tempOb);
								break;
							}
						}
					}
				}
				result.put("sessionFacReductionObj", (OBFacilityReduction)AccessorUtil.deepClone(obj));
				result.put(FacilityReductionForm.MAPPER, obj);
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
