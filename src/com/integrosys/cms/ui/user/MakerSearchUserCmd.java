/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.OBSearchCommonUser;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/30 07:49:56 $ Tag: $Name: $
 */
public class MakerSearchUserCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "CommonUserSearchCriteria",
				"com.integrosys.component.user.app.bus.CommonUserSearchCriteria", FORM_SCOPE } });
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
				{ "UserList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "User_SearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",
						SERVICE_SCOPE } });
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
			CMSUserProxy userProxy = new CMSUserProxy();
			CommonUserSearchCriteria criteria = (CommonUserSearchCriteria) map.get("CommonUserSearchCriteria");
			SearchResult sr = userProxy.searchUsers(criteria);
			
			
			ArrayList resultList1 = null;
			if(sr != null){
				if(sr.getResultList() != null) {
					resultList1 = new ArrayList(sr.getResultList());
					System.out.println("**************inside MakersearchUsercmd******79******resultList1.size() : "+resultList1.size());
			for(int i=0;i<resultList1.size()-1;i++) {
				for(int j=i+1;j<resultList1.size();j++) {
					OBSearchCommonUser usr = (OBSearchCommonUser) resultList1.get(i);
					OBSearchCommonUser usr1 = (OBSearchCommonUser) resultList1.get(j);
					System.out.println("usr.getLoginID()"+usr.getLoginID()+"usr1.getLoginID()"+usr1.getLoginID());
					if(usr.getLoginID().equalsIgnoreCase(usr1.getLoginID())) {
						resultList1.remove(i);
					}
				}
			}
			sr.getResultList().clear();
			sr.getResultList().addAll(resultList1);
				}
			}
			
			resultMap.put("UserList", sr);
			resultMap.put("User_SearchCriteria", criteria);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "MakerSearchUserCmd.java=>got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()=>MakerSearchUserCmd.java");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
