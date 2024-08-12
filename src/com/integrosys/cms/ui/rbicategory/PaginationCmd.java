package com.integrosys.cms.ui.rbicategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;

/**
*@author $Govind.Sahu$
*Command for pagination of Rbi Category
 */
public class PaginationCmd extends AbstractCommand implements ICommonEventConstant {
	private IRbiCategoryProxyManager rbiCategoryProxy;

	/**
	 * @return the rbiCategoryProxy
	 */
	public IRbiCategoryProxyManager getRbiCategoryProxy() {
		return rbiCategoryProxy;
	}

	/**
	 * @param rbiCategoryProxy the rbiCategoryProxy to set
	 */
	public void setRbiCategoryProxy(IRbiCategoryProxyManager rbiCategoryProxy) {
		this.rbiCategoryProxy = rbiCategoryProxy;
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
                 {"industryNameSaveValueList", "java.util.ArrayList", REQUEST_SCOPE},
				 {"rbiCategoryList", "java.util.ArrayList", REQUEST_SCOPE},
				 {"searchResultRbiCategory", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
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
		try {
			
			String name = (String) map.get("name");
			String login = (String)map.get("loginId");
			String startIndex = (String) map.get("startIndex");
			ArrayList rbiCategoryList = new ArrayList();
			SearchResult searchResultRbiCategory = null;



			DefaultLogger.debug(this, "Name: " + name);
			DefaultLogger.debug(this, "Login: " + login);
			DefaultLogger.debug(this, "startIndex: " + startIndex);
		

			rbiCategoryList= (ArrayList)  getRbiCategoryProxy().searchRbiCategory(login);
			searchResultRbiCategory = new SearchResult(Integer.parseInt(startIndex), 10, rbiCategoryList.size(), rbiCategoryList);

			resultMap.put("rbiCategoryList", rbiCategoryList);
			resultMap.put("searchResultRbiCategory", searchResultRbiCategory);
			
			resultMap.put("startIndex", startIndex);
			
		}catch (RbiCategoryException ex) {
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
