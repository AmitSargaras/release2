/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.facilityreceipt;

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
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
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
public class ListFacilityReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	
	/**
	 * Default Constructor
	 */
	public ListFacilityReceiptCommand() {
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
				{ "checkListIDMap", "java.util.HashMap", REQUEST_SCOPE },
				{ "session.list", "java.lang.String", SERVICE_SCOPE },
				{ "session.searchType", "java.lang.String", SERVICE_SCOPE },
				{ "session.search", "java.lang.String", SERVICE_SCOPE },
				/*{ "colChkLst", "java.util.List", REQUEST_SCOPE },
				{ "delColChkLst", "java.util.List", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", FORM_SCOPE },
				{ "innerOuterBcaObList", "java.util.HashMap", REQUEST_SCOPE },
                { "isViewFlag", "java.lang.String", REQUEST_SCOPE }*/
				});
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
		HashMap checkListIDMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		
		ICheckListProxyManager checklistProxyManager=(ICheckListProxyManager) BeanHouse.get("checklistProxy");
		
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		long limitProfileID = limit.getLimitProfileID();
		
		 String list = "";
	        String searchType ="";
	        String search ="";
          	ILimit[] OB=limit.getLimits();
          	for(int i=0;i<OB.length;i++){
              ILimit obLimit= OB[i];
              long limitId= obLimit.getLimitID();
           
              try {
            	  CheckListSearchResult checkList=checklistProxyManager.getCheckListByCollateralID(limitId);
				checkListIDMap.put(new Long(limitId), checkList);
				  resultMap.put("session.list", list);
		  			resultMap.put("session.searchType", searchType);
		  			resultMap.put("session.search", search);
				resultMap.put("checkListIDMap",checkListIDMap);
			} catch (CheckListException e) {
				
				e.printStackTrace();
				throw new CommandProcessingException("failed to retrieve  checklist ", e);
			}
          	}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
