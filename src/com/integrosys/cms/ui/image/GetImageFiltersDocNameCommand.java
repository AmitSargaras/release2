/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.image;

/**
 * 
 * 
 * @author Anil Pandey
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class GetImageFiltersDocNameCommand extends AbstractCommand implements IImageTagConstants{

	

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				
				{ "subfolderName", "java.lang.String", REQUEST_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "custId","java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "facDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "secDocCode", "java.lang.String", REQUEST_SCOPE },});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "docNameList", "java.util.List", REQUEST_SCOPE },
				{ "facilityDocNameList", "java.util.List", REQUEST_SCOPE },
				{ "securityDocNameList", "java.util.List", REQUEST_SCOPE },
				{ "facilityDocumentNameListTemp", "java.util.List", REQUEST_SCOPE },
				{ "securityDocumentNameListTemp", "java.util.List", REQUEST_SCOPE },
				{ "tempFacilityDocNameList", "java.util.List", SERVICE_SCOPE },
				{ "tempSecurityDocNameList", "java.util.List", SERVICE_SCOPE },
				});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException, AccessDeniedException {
		HashMap returnMap = new HashMap();

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		
		String category = (String) map.get("category");
		String subfolderName = (String) map.get("subfolderName");
		String custId = (String) map.get("custId");
		String event = (String) map.get("event");
		String facDocCode = (String) map.get("facDocCode");
		String secDocCode = (String) map.get("secDocCode");
		
		if(secDocCode != null && !"".equals(secDocCode)) {
		String securityNameId = secDocCode;
		int first = securityNameId.indexOf("-");
		String secId = securityNameId.substring(0,first);
		String subtype = securityNameId.substring(first+1);
		secDocCode = secId;
		}
		
		if(facDocCode == null) {
			facDocCode = "";
		}
		if(secDocCode == null) {
			secDocCode = "";
		}
		IImageUploadProxyManager imageUploadProxyManager=(IImageUploadProxyManager) BeanHouse.get("imageUploadProxy");
		
		List docNameList= new ArrayList();
		List documentNameListTemp = imageUploadProxyManager.getDocumentNameList(custId, category, subfolderName);
		String documentNameLable;
		for (Iterator iterator = documentNameListTemp.iterator(); iterator.hasNext();) {
			documentNameLable= (String) iterator.next();
			if(documentNameLable!=null){
				LabelValueBean lvBean= new LabelValueBean(documentNameLable, documentNameLable);												
				docNameList.add(lvBean);
			}
		}
		
		List facilityDocNameList= new ArrayList();
		List<String> tempFacilityDocNameList = new ArrayList<String>();
		List securityDocNameList= new ArrayList();
		List<String> tempSecurityDocNameList = new ArrayList<String>();
		if(event != null) {
			if(event.equalsIgnoreCase("refresh_facility_document_name")) {
				List facilityDocumentNameListTemp = imageUploadProxyManager.getFacilityDocumentNameList(facDocCode);
				String facilityDocumentNameLable;
				for (Iterator iterator = facilityDocumentNameListTemp.iterator(); iterator.hasNext();) {
					facilityDocumentNameLable= (String) iterator.next();
					if(facilityDocumentNameLable!=null){
						LabelValueBean lvBean= new LabelValueBean(facilityDocumentNameLable, facilityDocumentNameLable);												
						facilityDocNameList.add(lvBean);
						tempFacilityDocNameList.add(facilityDocumentNameLable);
					}
				}
			}else if(event.equalsIgnoreCase("refresh_security_document_name")) {
				List securityDocumentNameListTemp = imageUploadProxyManager.getSecurityDocumentNameList(secDocCode);
				String securityDocumentNameLable;
				for (Iterator iterator = securityDocumentNameListTemp.iterator(); iterator.hasNext();) {
					securityDocumentNameLable= (String) iterator.next();
					if(securityDocumentNameLable!=null){
						LabelValueBean lvBean= new LabelValueBean(securityDocumentNameLable, securityDocumentNameLable);												
						securityDocNameList.add(lvBean);
						tempSecurityDocNameList.add(securityDocumentNameLable);
					}
				}
			}else if(event.equalsIgnoreCase("view_uploaded_image_listing_search") || "view_uploaded_image_listing_search_page".equals(event)) {
				if(!"".equals(facDocCode)) {
					List facilityDocumentNameListTemp = imageUploadProxyManager.getFacilityDocumentNameList(facDocCode);
					result.put("facilityDocumentNameListTemp", facilityDocumentNameListTemp);
				}
				if(!"".equals(secDocCode)) {
					List securityDocumentNameListTemp = imageUploadProxyManager.getSecurityDocumentNameList(secDocCode);
					result.put("securityDocumentNameListTemp", securityDocumentNameListTemp);
				}
			}
		}
		result.put("docNameList", docNameList);
		result.put("facilityDocNameList", facilityDocNameList);
		result.put("securityDocNameList", securityDocNameList);
		result.put("tempFacilityDocNameList", tempFacilityDocNameList);
		result.put("tempSecurityDocNameList", tempSecurityDocNameList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

}
