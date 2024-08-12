package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityRelationship;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class DeleteRelationshipCommand extends FacilityMainCommand {
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
			Set setFacilityRelationshipActual = null;
			if(facilityTrxValue!=null&&facilityTrxValue.getFacilityMaster()!=null){
				setFacilityRelationshipActual = facilityTrxValue.getFacilityMaster().getFacilityRelationshipSet();
			}
			
			IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
			Set setFacilityRelationshipAll = facilityMasterObj.getFacilityRelationshipSet();
			Set setFacilityRelationship = FacilityUtil.filterDeletedRelationship(setFacilityRelationshipAll);
			String key = (String) map.get("key");
			
			if (setFacilityRelationship != null) {
				IFacilityRelationship[] facilityRelationships = (IFacilityRelationship[]) setFacilityRelationship
						.toArray(new IFacilityRelationship[0]);
				Arrays.sort(facilityRelationships, new FacilityRelationshipComparator());

				IFacilityRelationship delFacilityRelationshipObj = facilityRelationships[Integer.parseInt(key)];
				
				boolean isCreate = isCreateCheck(setFacilityRelationshipActual,delFacilityRelationshipObj);
				setFacilityRelationshipAll.remove(delFacilityRelationshipObj);
				
				if(!isCreate){
					delFacilityRelationshipObj.setStatus("D");
					setFacilityRelationshipAll.add(delFacilityRelationshipObj);
				}
				
			//	facilityMasterObj.setFacilityRelationshipSet(setFacilityRelationshipAll);
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
	
	
	public boolean isCreateCheck(Set actualSet,IFacilityRelationship delObj){
		boolean isCreate = true;
		if(actualSet==null||actualSet.size()==0){
			return true;
		}
		
		IFacilityRelationship[] facilityRelationshipActual = (IFacilityRelationship[]) actualSet
			.toArray(new IFacilityRelationship[0]);
		if(delObj.getId()==0){
			return true;
		}
		else{
			for(int i=0;i<facilityRelationshipActual.length;i++){
				if(delObj.getCmsRefId().equals(facilityRelationshipActual[i].getCmsRefId())){
					isCreate = false;
					break;
				}
			}
		}
		return isCreate;
	}
	
}
