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

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.ui.whatifana.WhatIfCondReportForm;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshImageStatusDateCmd extends AbstractCommand implements IImageTagConstants{

	private IImageTagProxyManager imageTagProxyManager;

	private ICheckListProxyManager checklistProxyManager;



	public ICheckListProxyManager getChecklistProxyManager() {
		return checklistProxyManager;
	}

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "docDesc", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "docList", "java.util.List", REQUEST_SCOPE },
				{ "obchk", "com.integrosys.cms.app.checklist.bus.OBCheckListItem", REQUEST_SCOPE },
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException, AccessDeniedException {
		HashMap returnMap = new HashMap();

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String docDesc = (String) map.get("docDesc");
		if(docDesc.length()<=17 && docDesc.length()>14){
		long checkListItemId=Long.valueOf(docDesc).longValue();
		OBCheckListItem ob=new OBCheckListItem();
		List docList=new ArrayList();
		
		try {
			docList.add(getChecklistProxyManager().getCheckListItemById(checkListItemId));
		} catch (CheckListException e) {
			e.printStackTrace();
		}
		Iterator it=docList.iterator();
		while(it.hasNext()){
			ob=(OBCheckListItem)it.next();
		}
		
		result.put("obchk",ob);
		result.put("docList",docList);
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	}
