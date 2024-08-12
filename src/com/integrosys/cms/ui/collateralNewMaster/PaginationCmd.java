

package com.integrosys.cms.ui.collateralNewMaster;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.collateralNewMaster.proxy.ICollateralNewMasterProxyManager;

/**
*@author $Author: Abhijit R$
*Command for pagination of CollateralNewMaster
 */
public class PaginationCmd extends AbstractCommand implements ICommonEventConstant {
	private ICollateralNewMasterProxyManager collateralNewMasterProxy;

	public ICollateralNewMasterProxyManager getCollateralNewMasterProxy() {
		return collateralNewMasterProxy;
	}

	public void setCollateralNewMasterProxy(ICollateralNewMasterProxyManager collateralNewMasterProxy) {
		this.collateralNewMasterProxy = collateralNewMasterProxy;
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
				{ "newCollateralCodeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralDescriptionSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralMainTypeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralSubTypeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "newCollateralDescriptionSession", "java.lang.String", SERVICE_SCOPE },
				{ "newCollateralMainTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "newCollateralSubTypeSession", "java.lang.String", SERVICE_SCOPE },
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
				{ "newCollateralCodeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralDescriptionSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralMainTypeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralSubTypeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "newCollateralDescriptionSession", "java.lang.String", SERVICE_SCOPE },
				{ "newCollateralMainTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "newCollateralSubTypeSession", "java.lang.String", SERVICE_SCOPE },
				 {"collateralNewMasterList", "java.util.ArrayList", REQUEST_SCOPE},
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
		try {
			
			String name = (String) map.get("name");
			String login = (String)map.get("loginId");
			String startIdx = (String) map.get("startIndex");
			SearchResult collateralNewMasterList = new SearchResult();
		
			/*
			 * maintain newCollateralCode in session when clicking on next
			 */
			String newCollateralCodeSearch = "";
			String newCollateralCodeSession=(String) map.get("newCollateralCodeSession");
			if("".equals(newCollateralCodeSession))
				newCollateralCodeSession=null;
			if(newCollateralCodeSession!=null){
				newCollateralCodeSearch=newCollateralCodeSession;
			}else{
				newCollateralCodeSearch = (String) map.get("newCollateralCodeSearch");
				newCollateralCodeSession=newCollateralCodeSearch;
			}
			
			/*
			 * maintain newCollateralDescription in session when clicking on next
			 */
			String newCollateralDescriptionSearch = "";
			String newCollateralDescriptionSession=(String) map.get("newCollateralDescriptionSession");
			if("".equals(newCollateralDescriptionSession))
				newCollateralDescriptionSession=null;
			if(newCollateralDescriptionSession!=null){
				newCollateralDescriptionSearch=newCollateralDescriptionSession;
			}else{
				newCollateralDescriptionSearch = (String) map.get("newCollateralDescriptionSearch");
				newCollateralDescriptionSession=newCollateralDescriptionSearch;
			}
			
			/*
			 * maintain newCollateralMainType in session when clicking on next
			 */
			String newCollateralMainTypeSearch = "";
			String newCollateralMainTypeSession=(String) map.get("newCollateralMainTypeSession");
			if("".equals(newCollateralMainTypeSession))
				newCollateralMainTypeSession=null;
			if(newCollateralMainTypeSession!=null){
				newCollateralMainTypeSearch=newCollateralMainTypeSession;
			}else{
				newCollateralMainTypeSearch = (String) map.get("newCollateralMainTypeSearch");
				newCollateralMainTypeSession=newCollateralMainTypeSearch;
			}
			
			/*
			 * maintain newCollateralSubType in session when clicking on next
			 */
			String newCollateralSubTypeSearch = "";
			String newCollateralSubTypeSession=(String) map.get("newCollateralSubTypeSession");
			if("".equals(newCollateralSubTypeSession))
				newCollateralSubTypeSession=null;
			if(newCollateralSubTypeSession!=null){
				newCollateralSubTypeSearch=newCollateralSubTypeSession;
			}else{
				newCollateralSubTypeSearch = (String) map.get("newCollateralSubTypeSearch");
				newCollateralSubTypeSession=newCollateralSubTypeSearch;
			}

			DefaultLogger.debug(this, "Name: " + name);
			DefaultLogger.debug(this, "Login: " + login);
			DefaultLogger.debug(this, "StartIdx: " + startIdx);
		

			
				collateralNewMasterList= (SearchResult)  getCollateralNewMasterProxy().getFilteredCollateral(newCollateralCodeSearch, newCollateralDescriptionSearch,newCollateralMainTypeSearch,newCollateralSubTypeSearch);

			resultMap.put("collateralNewMasterList", collateralNewMasterList);
			resultMap.put("newCollateralCodeSearch", newCollateralCodeSearch);
			resultMap.put("newCollateralCodeSession", newCollateralCodeSession);
			resultMap.put("newCollateralDescriptionSearch", newCollateralDescriptionSearch);
			resultMap.put("newCollateralDescriptionSession", newCollateralDescriptionSession);
			resultMap.put("newCollateralMainTypeSearch", newCollateralMainTypeSearch);
			resultMap.put("newCollateralMainTypeSession", newCollateralMainTypeSession);
			resultMap.put("newCollateralSubTypeSearch", newCollateralSubTypeSearch);
			resultMap.put("newCollateralSubTypeSession", newCollateralSubTypeSession);
			resultMap.put("startIndex", startIdx);
			
			
		}catch (SystemBankException ex) {
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
