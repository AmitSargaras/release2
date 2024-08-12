package com.integrosys.cms.ui.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;

public class RemoveDocLinkageDetailsToSessionCmd extends AbstractCommand implements ICommonEventConstant{

	@Override
	public String[][] getParameterDescriptor() {
		return new String[][]{
			{ "obImageUploadAddList", "java.util.List", SERVICE_SCOPE },
			{ "imgUploadList", "java.util.List", SERVICE_SCOPE },
			{ "custImageListWithTag", "java.util.List", SERVICE_SCOPE },
			{ "legalName", "java.lang.String", REQUEST_SCOPE },
			{ "removeIdx", "java.lang.String", REQUEST_SCOPE },
			{ "ImageUploadAddObjSession", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE},
		};
	}
	
	@Override
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		
		OBImageUploadAdd imageUploadAddObjSession = (OBImageUploadAdd) map.get("ImageUploadAddObjSession");
		String custId = imageUploadAddObjSession.getCustId();
		String removeIdx = (String) map.get("removeIdx");
		List imgUploadList = (List) map.get("imgUploadList");;
		List obImageUploadAddList = (List) map.get("obImageUploadAddList");
		List custImageListWithTag = (List) map.get("custImageListWithTag");

		String[] idxs = removeIdx.split(",");
		
		List toNewList = new ArrayList();
		
		for(int i = 0; i < obImageUploadAddList.size(); i++) {
			boolean found = false;
			for(String id : idxs) {
				if(i == Integer.parseInt(id)) {
					found = true;
					break;
				}
			}
			
			if(!found) {
				toNewList.add(obImageUploadAddList.get(i));
			}
		}
		
		result.put("imgUploadList", imgUploadList);
		result.put("obImageUploadAddList", toNewList);
		result.put("custImageListWithTag", custImageListWithTag);
		result.put("legalName", custId);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		
		return returnMap;
	}
	
	@Override
	public String[][] getResultDescriptor() {
		return new String[][]{
			{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE },
			{ "imgUploadList", "java.util.List", SERVICE_SCOPE },
			{ "custImageListWithTag", "java.util.List", SERVICE_SCOPE },
			{ "legalName", "java.lang.String", REQUEST_SCOPE },
		};
	}
	
}