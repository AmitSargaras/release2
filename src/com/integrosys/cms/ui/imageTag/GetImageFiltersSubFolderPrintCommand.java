/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.imageTag;

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

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class GetImageFiltersSubFolderPrintCommand extends AbstractCommand implements IImageTagConstants{

	private IImageTagProxyManager imageTagProxyManager;
	private IImageUploadProxyManager imageUploadProxyManager;
	
	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	
	public IImageUploadProxyManager getImageUploadProxyManager() {
		return imageUploadProxyManager;
	}

	public void setImageUploadProxyManager(
			IImageUploadProxyManager imageUploadProxyManager) {
		this.imageUploadProxyManager = imageUploadProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				
				{ "subfolderName", "java.lang.String", REQUEST_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "custId","java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "subfolderNameList", "java.util.List", REQUEST_SCOPE },
				});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException, AccessDeniedException {
		DefaultLogger.debug(this, "getUploadImageList() Inside command-1.1-------->" +DateUtil.getDate().getTime());
		HashMap returnMap = new HashMap();

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		
		String category = (String) map.get("category");
		String subfolderName = (String) map.get("subfolderName");
		String custId = (String) map.get("custId");
		String event = (String) map.get("event");
		
		List subfolderNameList= new ArrayList();
		List subFolderNameListTemp = getImageUploadProxyManager().getSubFolderNameList(custId, category);
		String subFolderNameLable;
		for (Iterator iterator = subFolderNameListTemp.iterator(); iterator.hasNext();) {
			subFolderNameLable= (String) iterator.next();
			if(subFolderNameLable!=null){
				LabelValueBean lvBean= new LabelValueBean(subFolderNameLable, subFolderNameLable);												
				subfolderNameList.add(lvBean);
			}
		}
			
		result.put("subfolderNameList", subfolderNameList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "getUploadImageList() Inside command-2.2-------->" +DateUtil.getDate().getTime());
		return returnMap;
	}

}
