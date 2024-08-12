/*
 * Created on Feb 2016
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.imageTag;

/**
 * 
 * 
 * @author Uma Khot
 *  Phase 3 CR:tag scanned images of Annexure II
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
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListDAO;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.bus.RecurrentDAOFactory;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshRecurrentDocStatusDateCmd extends AbstractCommand implements IImageTagConstants{

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
		//if(docDesc.length()<=17 && docDesc.length()>14){
		long recurrentCheckListItemId=Long.valueOf(docDesc).longValue();
		OBRecurrentCheckListSubItem ob=new OBRecurrentCheckListSubItem();
		List docList=new ArrayList();
		
		try {
			IRecurrentCheckListDAO recurrentCheckListDAO = RecurrentDAOFactory.getRecurrentCheckListDAO();
			docList.add(recurrentCheckListDAO.getRecurrentDocStatusDate(recurrentCheckListItemId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator it=docList.iterator();
		while(it.hasNext()){
			ob=(OBRecurrentCheckListSubItem)it.next();
		}
		
		result.put("obchk",ob);
		result.put("docList",docList);
		//}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	}
