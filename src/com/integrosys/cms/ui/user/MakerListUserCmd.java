/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.cms.app.user.proxy.CMSStdUserProxyFactory;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/30 07:49:56 $ Tag: $Name: $
 */
public class MakerListUserCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public MakerListUserCmd() {
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
				{ "fromMenu", "java.lang.String", REQUEST_SCOPE },
				{ "User_SearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",
						SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				//{ "Curr_SearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",
				//		SERVICE_SCOPE }
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
				{ "UserList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "User_SearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",
						SERVICE_SCOPE },
				//{ "Curr_SearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",
				//		SERVICE_SCOPE } 
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
			ITeam userTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			CommonUserSearchCriteria sc = MaintainUserUitl.createEmptySearchCriteria(user.getLoginID(), userTeam);
			CommonUserSearchCriteria criteria = (CommonUserSearchCriteria) map.get("User_SearchCriteria");
			//CommonUserSearchCriteria reloadCriteria = (CommonUserSearchCriteria) map.get("Curr_SearchCriteria");
			if ("Y".equals((String) map.get("fromMenu"))) {
				// from menu,clear seach criteria .
				criteria = null;
				//reloadCriteria = null;
			}
			else {
				// view/edit/delete return
//				if (reloadCriteria != null) {
//					sc = reloadCriteria;
//				}
//				else 
				if (criteria != null) {
					sc = criteria;
				}
			}
			// Start:  Added by Uma Khot for IE11 QC issue fixing
			SearchResult sr= new SearchResult();
			if(null!=sc &&  (null !=sc.getCriteria()) && null !=sc.getCriteria().getLoginId()){
				
				CMSUserProxy userProxy = new CMSUserProxy();
				 sr  = userProxy.searchUsers(sc);
			}
			// end:  Added by Uma Khot for IE11 QC issue fixing
			else{
			 sr = CMSStdUserProxyFactory.getUserProxy().searchUsers(sc);
			}
			resultMap.put("UserList", sr);
			resultMap.put("User_SearchCriteria", criteria);
			//resultMap.put("Curr_SearchCriteria", reloadCriteria);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			//e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage(), e));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
