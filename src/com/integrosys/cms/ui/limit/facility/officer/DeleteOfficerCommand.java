package com.integrosys.cms.ui.limit.facility.officer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityOfficer;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class DeleteOfficerCommand extends FacilityMainCommand {
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
		try {
			IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
			Set setFacilityOfficerActual = null;
			if(facilityTrxValue!=null&&facilityTrxValue.getFacilityMaster()!=null){
				setFacilityOfficerActual = facilityTrxValue.getFacilityMaster().getFacilityOfficerSet();
			}
			
			IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
			Set setFacilityOfficerAll = facilityMasterObj.getFacilityOfficerSet();
			Set setFacilityOfficer = FacilityUtil.filterDeletedOfficer(setFacilityOfficerAll);
			String key = (String) map.get("key");
			if (setFacilityOfficer != null) {
				IFacilityOfficer[] facilityOfficers = (IFacilityOfficer[]) setFacilityOfficer
						.toArray(new IFacilityOfficer[0]);
				Arrays.sort(facilityOfficers, new FacilityOfficerComparator());
				IFacilityOfficer delFacilityOfficerObj = facilityOfficers[Integer.parseInt(key)];
				
				boolean isCreate = isCreateCheck(setFacilityOfficerActual,delFacilityOfficerObj);
				setFacilityOfficerAll.remove(delFacilityOfficerObj);
				
				if(!isCreate){
					delFacilityOfficerObj.setStatus("D");
					setFacilityOfficerAll.add(delFacilityOfficerObj);
				}
			}
			result.put("facilityMasterObj", facilityMasterObj);
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
	
	public boolean isCreateCheck(Set actualSet,IFacilityOfficer delObj){
		boolean isCreate = true;
		if(actualSet==null||actualSet.size()==0){
			return true;
		}
		
		IFacilityOfficer[] facilityOfficerActual = (IFacilityOfficer[]) actualSet
			.toArray(new IFacilityOfficer[0]);
		if(delObj.getId()==0){
			return true;
		}
		else{
			for(int i=0;i<facilityOfficerActual.length;i++){
				if(delObj.getCmsRefId().equals(facilityOfficerActual[i].getCmsRefId())){
					isCreate = false;
					break;
				}
			}
		}
		return isCreate;
	}
}