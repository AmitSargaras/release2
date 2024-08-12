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
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;

public class GetDocLinkageDetailsCmd extends AbstractCommand implements ICommonEventConstant{
	
	private IImageUploadProxyManager imageUploadProxyManager;
	
	public IImageUploadProxyManager getImageUploadProxyManager() {
		return imageUploadProxyManager;
	}

	public void setImageUploadProxyManager(
			IImageUploadProxyManager imageUploadProxyManager) {
		this.imageUploadProxyManager = imageUploadProxyManager;
	}
	
	@Override
	public String[][] getParameterDescriptor() {
		return new String[][]{
			{ "obImageUploadAddList", "java.util.List", SERVICE_SCOPE },
			{ "imgUploadList", "java.util.List", SERVICE_SCOPE },
			{ "custImageListWithTag", "java.util.List", SERVICE_SCOPE },
			{ "legalName", "java.lang.String", REQUEST_SCOPE },
			{ "ImageUploadAddObjSession", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE},
		};
	}
	
	@Override
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		
		String custId= (String) map.get("legalName");
		
		OBImageUploadAdd imageUploadAdd = (OBImageUploadAdd) map.get("ImageUploadAddObjSession");
		
		if(imageUploadAdd == null) {
			imageUploadAdd = new OBImageUploadAdd();
			imageUploadAdd.setCustId(custId);
			result.put("ImageUploadAddObjSession", imageUploadAdd);
		}
		
		List obImageUploadAddList = (ArrayList) getImageUploadProxyManager().getCustImageList(imageUploadAdd);
		List custImageListWithTag = getImageUploadProxyManager().getImageIdWithTagList((ArrayList)obImageUploadAddList);
		List imgUploadList = getImageUploadProxyManager().getStoredCustImageList(imageUploadAdd.getCustId());
		
		result.put("imgUploadList", imgUploadList);
		result.put("obImageUploadAddList", obImageUploadAddList);
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
			{ "ImageUploadAddObjSession", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE},
		};
	}
}