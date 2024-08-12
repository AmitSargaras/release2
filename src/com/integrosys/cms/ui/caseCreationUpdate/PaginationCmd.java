

package com.integrosys.cms.ui.caseCreationUpdate;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;

/**
*@author $Author: Abhijit R$
*Command for pagination of CaseCreation
 */
public class PaginationCmd extends AbstractCommand implements ICommonEventConstant {
	private ICaseCreationProxyManager caseCreationProxy;

	
	public ICaseCreationProxyManager getCaseCreationProxy() {
		return caseCreationProxy;
	}

	public void setCaseCreationProxy(ICaseCreationProxyManager caseCreationProxy) {
		this.caseCreationProxy = caseCreationProxy;
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
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex2", "java.lang.String", REQUEST_SCOPE },
				{ "itemID", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				
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
				 {"startIndex", "java.util.ArrayList", REQUEST_SCOPE},
				 { "startIndex2", "java.lang.String", REQUEST_SCOPE },
				 { "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
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
			String startIdx2 = (String) map.get("startIndex2");
			DefaultLogger.debug(this, "Name: " + name);
			DefaultLogger.debug(this, "Login: " + login);
			DefaultLogger.debug(this, "StartIdx: " + startIdx);
			resultMap.put("startIndex2", startIdx2);
			resultMap.put("startIndex", startIdx);
			
			String itemID =(String) map.get("itemID");
			String unCheckId =(String) map.get("unCheckId");
			String event = (String) map.get("event");
			HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
			if(selectedArrayMap==null){ 
			selectedArrayMap = new HashMap();
			}
			
			DefaultLogger.debug(this, "PaginationCmd====================97========================>"+itemID);
			if(itemID!=null && !itemID.equals("")){
				String[] selected=itemID.split("-");
				if(selected!=null){
				for(int k=0;k<selected.length;k++){
				selectedArrayMap.put(selected[k], selected[k]);
				}
				}
			}
			
			DefaultLogger.debug(this, "PaginationCmd====================107========================>"+unCheckId);
			if(unCheckId!=null && !unCheckId.equals("")){
			String[] unchecked=unCheckId.split("-");
			if(unchecked!=null){
				for(int ak=0;ak<unchecked.length;ak++){
				selectedArrayMap.remove(unchecked[ak]);
				}
				}
			}
			
			resultMap.put("selectedArrayMap", selectedArrayMap);
			
		}catch (CaseCreationException ex) {
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
