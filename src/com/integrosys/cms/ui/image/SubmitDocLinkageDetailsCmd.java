package com.integrosys.cms.ui.image;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;

public class SubmitDocLinkageDetailsCmd extends AbstractCommand implements ICommonEventConstant{

	@Override
	public String[][] getParameterDescriptor() {
		return new String[][]{
			{ "obImageUploadAddList", "java.util.List", SERVICE_SCOPE },
			{ "legalName", "java.lang.String", REQUEST_SCOPE },
		};
	}
	
	@Override
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		
		String custId = (String) map.get("legalName");
		List obImageUploadAddList = (List) map.get("obImageUploadAddList");
		IImageUploadProxyManager imageUploadProxyManager = (IImageUploadProxyManager) BeanHouse.get("imageUploadProxy");
		for(Object obj : obImageUploadAddList) {
			OBImageUploadAdd imageUploadAdd = (OBImageUploadAdd) obj;
			if(imageUploadAdd.getImgId() == 0)
				imageUploadProxyManager.createImageUploadAdd(imageUploadAdd, true);
		}
		
		result.put("legalName", custId);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		
		return returnMap;
	}
	
	@Override
	public String[][] getResultDescriptor() {
		return new String[][]{
			{ "legalName", "java.lang.String", REQUEST_SCOPE },
		};
	}
	
}