

package com.integrosys.cms.ui.caseBranch;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.caseBranch.proxy.ICaseBranchProxyManager;

/**
*@author $Author: Abhijit R$
*Command for pagination of CaseBranch
 */
public class PaginationCmd extends AbstractCommand implements ICommonEventConstant {
	private ICaseBranchProxyManager caseBranchProxy;

	public ICaseBranchProxyManager getCaseBranchProxy() {
		return caseBranchProxy;
	}

	public void setCaseBranchProxy(ICaseBranchProxyManager caseBranchProxy) {
		this.caseBranchProxy = caseBranchProxy;
	}
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "name", "java.lang.String", REQUEST_SCOPE },
				{ "loginId", "java.lang.String", REQUEST_SCOPE},
				{ "branchCodeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "branchNameSearch", "java.lang.String", REQUEST_SCOPE },
				{ "branchCodeSearchSession", "java.lang.String", SERVICE_SCOPE },
				{ "branchNameSearchSession", "java.lang.String", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }
				
				 });
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
				{ "branchCodeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "branchNameSearch", "java.lang.String", REQUEST_SCOPE },
				{ "branchCodeSearchSession", "java.lang.String", SERVICE_SCOPE },
				{ "branchNameSearchSession", "java.lang.String", SERVICE_SCOPE },
				 {"caseBranchList", "java.util.ArrayList", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE }
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
		/*
		 * maintain branchCodeSearch in session when clicking on next
		 */
		String branchCodeSearch = "";
		String branchCodeSearchSession=(String) map.get("branchCodeSearchSession");
		if("".equals(branchCodeSearchSession))
			branchCodeSearchSession=null;
		if(branchCodeSearchSession!=null){
			branchCodeSearch=branchCodeSearchSession;
		}else{
			branchCodeSearch = (String) map.get("branchCodeSearch");
			branchCodeSearchSession=branchCodeSearch;
		}
		
		/*
		 * maintain branchNameSearch in session when clicking on next
		 */
		String branchNameSearch = "";
		String branchNameSearchSession=(String) map.get("branchNameSearchSession");
		if("".equals(branchNameSearchSession))
			branchNameSearchSession=null;
		if(branchNameSearchSession!=null){
			branchNameSearch=branchNameSearchSession;
		}else{
			branchNameSearch = (String) map.get("branchNameSearch");
			branchNameSearchSession=branchNameSearch;
		}
		
		if(null==branchNameSearch)
			branchNameSearch="";
		if(null==branchCodeSearch)
			branchCodeSearch="";
		
		try {
			
			String name = (String) map.get("name");
			String login = (String)map.get("loginId");
			String startIdx = (String) map.get("startIndex");
			SearchResult caseBranchList = new SearchResult();
		

			DefaultLogger.debug(this, "Name: " + name);
			DefaultLogger.debug(this, "Login: " + login);
			DefaultLogger.debug(this, "StartIdx: " + startIdx);
			
				caseBranchList= getCaseBranchProxy().getAllFilteredActualCaseBranch(branchCodeSearch, branchNameSearch);
			

			resultMap.put("caseBranchList", caseBranchList);
			
			
			 resultMap.put("startIndex", startIdx);
			 resultMap.put("branchCodeSearch", branchCodeSearch);
             resultMap.put("branchCodeSearchSession", branchCodeSearchSession);
             resultMap.put("branchNameSearch", branchNameSearch);
             resultMap.put("branchNameSearchSession", branchNameSearchSession);
			
			
		}catch (CaseBranchException ex) {
       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
         ex.printStackTrace();
         throw (new CommandProcessingException(ex.getMessage()));
	}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage(), e));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
