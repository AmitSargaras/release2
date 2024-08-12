package com.integrosys.cms.ui.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

public class RefreshOtherDocumentCmd extends AbstractCommand implements ICommonEventConstant {

	@Override
	public String[][] getParameterDescriptor() {
		return new String[][]{
			{ "type", "java.lang.String", REQUEST_SCOPE },
			{ "otherDocList", "java.util.List", SERVICE_SCOPE },
			{ "otherSecDocList", "java.util.List", SERVICE_SCOPE },
			{ "tempFacilityDocNameList", "java.util.List", SERVICE_SCOPE },
			{ "tempSecurityDocNameList", "java.util.List", SERVICE_SCOPE },
		};
	}
	
	@Override
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		
		List<String> filteredOtherDocList = new ArrayList();
		List<String> filteredOtherSecDocList = new ArrayList();
		
		List<String> otherDocList = (List) map.get("otherDocList");
		List<String> otherSecDocList = (List) map.get("otherSecDocList");
		List tempFacilityDocNameList = (List) map.get("tempFacilityDocNameList");
		List tempSecurityDocNameList = (List) map.get("tempSecurityDocNameList");
		String type = (String) map.get("type");
		
		if("Facility".equals(type)) {
			filteredOtherDocList = new ArrayList(otherDocList);
			for(int i = 0; i< tempFacilityDocNameList.size(); i++) {
				filteredOtherDocList.remove(tempFacilityDocNameList.get(i));
			}
		}else if("Security".equals(type)) {
			filteredOtherSecDocList = new ArrayList(otherSecDocList);
			for(int i = 0; i< tempSecurityDocNameList.size(); i++) {
				filteredOtherSecDocList.remove(tempSecurityDocNameList.get(i));
			}
		}
	
		result.put("filteredOtherDocList", filteredOtherDocList);
		result.put("filteredOtherSecDocList", filteredOtherSecDocList);
		result.put("type", type);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		
		return returnMap;
	}
	
	@Override
	public String[][] getResultDescriptor() {
		return new String[][]{
			{ "type", "java.lang.String", REQUEST_SCOPE },
			{ "filteredOtherDocList", "java.util.List", REQUEST_SCOPE },
			{ "filteredOtherSecDocList", "java.util.List", REQUEST_SCOPE },
		};
	}
}
