/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.bizstructure;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.user.bus.StdUserSearchCriteria;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.user.MaintainUserAction;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/10/25 06:35:13 $ Tag: $Name: $
 */
public class ListUsersCmd extends AbstractCommand implements ICommonEventConstant {

	private ICommonUserProxy userProxy;

	public void setUserProxy(ICommonUserProxy userProxy) {
		this.userProxy = userProxy;
	}

	public ICommonUserProxy getUserProxy() {
		return userProxy;
	}

	/**
	 * Default Constructor
	 */
	public ListUsersCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form omr service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "CommonUserSearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",
						FORM_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "memshipTypeId_Front", "java.lang.String", GLOBAL_SCOPE },
				{ "teamTypeId", "java.lang.String", GLOBAL_SCOPE }, { "teamId", "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "mapTeam", "com.integrosys.component.bizstructure.app.bus.OBTeam", SERVICE_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "UserList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "addUserList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "userMap", "java.util.ArrayList", SERVICE_SCOPE }, });
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
		String event = (String) map.get("event");
		String memshipTypeId = (String) map.get("memshipTypeId_Front");

		DefaultLogger.debug(this, "Inside doExecute()>>  = " + memshipTypeId);

		CommonUserSearchCriteria criteria = (CommonUserSearchCriteria) map.get("CommonUserSearchCriteria");
		StdUserSearchCriteria obCriteria = new StdUserSearchCriteria(criteria.getCriteria());
		obCriteria.setMemshipTypeId(memshipTypeId);

        //Andy Wong, 2 Jan 2009: sort user listing by login Id
        criteria.setFirstSort(MaintainUserAction.FIRST_SORT);
		criteria.setSecondSort(MaintainUserAction.SECOND_SORT);

		obCriteria.setMaintainTeam((ITeam) map.get("mapTeam"));

		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		if (!MaintainTeamUtil.isSuperUser(user.getLoginID())) {
			ITeam userTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			DefaultLogger.debug(this, "-- TeadID:" + userTeam.getTeamID());
			if (userTeam != null) {
				obCriteria.setTeamID(userTeam.getTeamID());
			}
		}

		criteria.setCriteria(obCriteria);
		SearchResult sr = null;
		try {
			sr = getUserProxy().searchUsers(criteria);
		}
		catch (EntityNotFoundException e) {
			CommandProcessingException cpe = new CommandProcessingException("failed to search users using criteria");
			cpe.initCause(e);
			throw cpe;
		}
		catch (RemoteException e) {
			CommandProcessingException cpe = new CommandProcessingException("failed to remote call on user proxy ["
					+ this.userProxy.getClass() + "], throwing root cause");
			cpe.initCause(e.getCause());
			throw cpe;
		}

		resultMap.put("UserList", sr);

		if ((event != null) && (event.equals("start") || event.equals("start_rejected"))) {
			resultMap.put("userMap", new ArrayList());
			resultMap.put("addUserList", new ArrayList());
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
