/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.bizstructure;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamType;
import com.integrosys.component.bizstructure.app.bus.OBTeamSearchCriteria;
import com.integrosys.component.bizstructure.app.bus.TeamSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: ravi $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/25 03:58:08 $ Tag: $Name: $
 */
public class DefaultTeamCmd extends AbstractTeamCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public DefaultTeamCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TeamTypeList", "java.util.Arrays$ArrayList", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "TeamList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "teamTypeId", "java.lang.String", REQUEST_SCOPE },
				{ "teamTypeName", "java.lang.String", REQUEST_SCOPE } });
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
		
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		
		List rootNodeList = (List) map.get("TeamTypeList");
		DefaultLogger.debug(this, "root List size > " + rootNodeList.size());
		ITeamType teamType = null;
		for (int i = 0; i < rootNodeList.size(); i++) {
			ITeamType temp = (ITeamType) (rootNodeList.get(i));
			if (MaintainTeamUtil.hasTeamTypeAccess(user.getLoginID(), String.valueOf(temp.getTeamTypeID()))) {
				teamType = temp;
				break;
			}
		}
		OBTeamSearchCriteria obSearchCriteria = new OBTeamSearchCriteria();
		
		DefaultLogger.debug(this, "--Login User ID:" + user.getLoginID());

		obSearchCriteria.setHasSuperUserConcept(MaintainTeamUtil.hasSuperUserConcept());
		obSearchCriteria.setSuperUser(MaintainTeamUtil.isSuperUser(user.getLoginID()));
		obSearchCriteria.setTeam((ITeam) map.get(IGlobalConstant.USER_TEAM));
		
		obSearchCriteria.setTeamTypeID(teamType.getTeamTypeID());
		
		TeamSearchCriteria sc = new TeamSearchCriteria();
		sc.setCriteria(obSearchCriteria);
		sc.setStartIndex(0);
		sc.setNItems(10);

        //Andy Wong, 2 Jan 2009: sort team by team name
        sc.setFirstSort(MaintainTeamAction.FIRST_SORT);

		try {
			SearchResult sr = getCmsTeamProxy().searchTeams(sc);

			resultMap.put("TeamList", sr);
			resultMap.put("teamTypeId", "" + teamType.getTeamTypeID());
			resultMap.put("teamTypeName", "" + teamType.getDescription());
		}
		catch (BizStructureException e) {
			CommandProcessingException cpe = new CommandProcessingException("failed to search team using criteria");
			cpe.initCause(e);
			throw cpe;
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
