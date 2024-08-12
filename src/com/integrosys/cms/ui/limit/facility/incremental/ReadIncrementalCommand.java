package com.integrosys.cms.ui.limit.facility.incremental;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityIncremental;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class ReadIncrementalCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "key", "java.lang.String", REQUEST_SCOPE },				
				{ "event", "java.lang.String", REQUEST_SCOPE},
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "sessionFacIncrementalObj", "com.integrosys.cms.app.limit.bus.OBFacilityIncremental", SERVICE_SCOPE },
		});
	}	

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ FacilityIncrementalForm.MAPPER, "java.lang.Object", FORM_SCOPE },
				{ "sessionFacIncrementalObj", "com.integrosys.cms.app.limit.bus.OBFacilityIncremental", SERVICE_SCOPE },
				{ "facilityIncrementalActual", "com.integrosys.cms.app.limit.bus.OBFacilityIncremental", SERVICE_SCOPE }, 
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
			OBFacilityIncremental obj = null;
			List incrementalList = new ArrayList();
			incrementalList.addAll(facilityMaster.getFacilityIncrementals());

			if (FacilityIncrementalAction.EVENT_ERROR.equals(event) ||
					FacilityIncrementalAction.EVENT_ERROR_WO_FRAME.equals(event)) {
//				System.out.println("<<<<<<< at error event");
				// when there is valiation error
				obj = (OBFacilityIncremental)map.get("sessionFacIncrementalObj");

				result.put("sessionFacIncrementalObj", obj);
				HashMap objMap = new HashMap();
				objMap.put("obj", obj);
				result.put(FacilityIncrementalForm.MAPPER, objMap);				
			} else {
				// checker process		
				for (int i = 0; i < incrementalList.size(); i++) {
					OBFacilityIncremental tempOb = (OBFacilityIncremental)incrementalList.get(i);
					if (FacilityIncrementalAction.EVENT_VIEW_DETAIL_CHECKER_PROCESS.equals(event)) {					
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
				
				if (FacilityIncrementalAction.EVENT_VIEW_DETAIL_CHECKER_PROCESS.equals(event)) {		
					IFacilityTrxValue trxValue = (IFacilityTrxValue)map.get("facilityTrxValue");
					if (trxValue.getFacilityMaster() != null && 
							trxValue.getFacilityMaster().getFacilityIncrementals() != null) {
						incrementalList = new ArrayList();
						incrementalList.addAll(trxValue.getFacilityMaster().getFacilityIncrementals());
						
						for (int i = 0; i < incrementalList.size(); i++) {
							OBFacilityIncremental tempOb = (OBFacilityIncremental)incrementalList.get(i);
							if (tempOb.getCmsReferenceId().toString().equals(key)) {
								result.put("facilityIncrementalActual", tempOb);
								break;
							}
						}
					}
				}
				result.put("sessionFacIncrementalObj", (OBFacilityIncremental)AccessorUtil.deepClone(obj));
				result.put(FacilityIncrementalForm.MAPPER, obj);
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
