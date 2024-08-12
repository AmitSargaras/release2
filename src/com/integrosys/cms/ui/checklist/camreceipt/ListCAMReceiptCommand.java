/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.camreceipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAO;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICustomerSearchResult;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: cwtan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/02/04 03:19:21 $ Tag: $Name: $
 */
public class ListCAMReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListCAMReceiptCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
                { "isViewFlag", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
						{"event","java.lang.String",REQUEST_SCOPE},
						{"camNo","java.lang.String",REQUEST_SCOPE},
						{"session.camNo","java.lang.String",SERVICE_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
//				{ "colChkLst", "java.util.List", REQUEST_SCOPE },
				{ "camCheckListMap", "java.util.HashMap", REQUEST_SCOPE },
				{ "camCheckList", "com.integrosys.cms.app.checklist.bus.CheckListSearchResult", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", FORM_SCOPE },
				{ "migCheckList", "java.util.ArrayList", SERVICE_SCOPE },
				{"session.camNo","java.lang.String",SERVICE_SCOPE},
				{"event","java.lang.String",REQUEST_SCOPE},
				{ "session.list", "java.lang.String", SERVICE_SCOPE },
				{ "session.searchType", "java.lang.String", SERVICE_SCOPE },
				{ "session.search", "java.lang.String", SERVICE_SCOPE },
//				{ "innerOuterBcaObList", "java.util.HashMap", REQUEST_SCOPE },
                { "isViewFlag", "java.lang.Boolean", REQUEST_SCOPE }});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		ArrayList migCheckList = new ArrayList();
		ICheckListProxyManager checklistProxyManager=(ICheckListProxyManager) BeanHouse.get("checklistProxy");
		DefaultLogger.debug(this, "Inside doExecute()");
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		long limitProfileID = limit.getLimitProfileID();
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
        String isViewFlag = (String) map.get("isViewFlag");
        String event=(String)map.get("event");
        String camNo=(String)map.get("camNo");
        String list = "";
        String searchType ="";
        String search ="";
        if(camNo==null && "search_cam_no".equals(event))
        	camNo=(String)map.get("session.camNo");

          try {
        	  HashMap camCheckListMap=new HashMap();
        	  //Date : 22-Apr-2013 : Single CAM retrival method changed to multiple CAM retrival method for HDFC Bank 
        	  // CAM Document CR 
        	  
        	  //CheckListSearchResult camCheckList= checklistProxyManager.getCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
        	  /*This code will search cam on the basis of cam number in update camreceipt list*/
        	  if(camNo!=null && !"".equals(camNo)){

        	        String searchCam =camNo.trim();
        		  camCheckListMap=CheckListDAOFactory.getCheckListDAO().getSearchByCAM(limitProfileID,searchCam);
        	  }else{        	  
        	  camCheckListMap= CheckListDAOFactory.getCheckListDAO().getBulkCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
        	  }
        	  
        	  resultMap.put("session.list", list);
  			resultMap.put("session.searchType", searchType);
  			resultMap.put("session.search", search);
        	  resultMap.put("camCheckListMap", camCheckListMap);
        	  resultMap.put("session.camNo", camNo);
        	  resultMap.put("event", event);
        	  resultMap.put("camCheckListKeySet", camCheckListMap.keySet());
        	  CheckListDAO checkListDAO = new CheckListDAO();
        	  migCheckList= checkListDAO.getMigratedCheckListItem(String.valueOf(limitProfileID), "CAM");
			  resultMap.put("migCheckList", migCheckList);
		} catch (CheckListException e) {
			
			e.printStackTrace();
			throw new CommandProcessingException("failed to retrieve  checklist ", e);
		}catch (Exception e1) {
			
			e1.printStackTrace();
			throw new CommandProcessingException("failed to retrieve  migrated checklist ", e1);
		}
      
		

		

		resultMap.put("frame", "true");
		resultMap.put("limitProfileID", String.valueOf(limitProfileID));
        resultMap.put("isViewFlag", new Boolean(isViewFlag));

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
