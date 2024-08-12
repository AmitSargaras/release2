/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.bizstructure;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.TeamSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: dli $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/16 12:33:34 $ Tag: $Name: $
 */
public class MakerListTeamCmd extends AbstractCommand implements ICommonEventConstant {

	private ICMSTeamProxy cmsTeamProxy;

	public void setCmsTeamProxy(ICMSTeamProxy cmsTeamProxy) {
		this.cmsTeamProxy = cmsTeamProxy;
	}

	public ICMSTeamProxy getCmsTeamProxy() {
		return cmsTeamProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerListTeamCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TeamSearchCriteria",
				"com.integrosys.component.bizstructure.app.bus.TeamSearchCriteria", FORM_SCOPE },
			{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
			{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE }		
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
		return (new String[][] { { "TeamList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE } });
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
             TeamSearchCriteria sc = (TeamSearchCriteria) map.get("TeamSearchCriteria");
            //Andy Wong, 2 Jan 2009: sort team by team name
            sc.setFirstSort(MaintainTeamAction.FIRST_SORT);
            ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
            
    		sc.getCriteria().setHasSuperUserConcept(MaintainTeamUtil.hasSuperUserConcept());
    		sc.getCriteria().setSuperUser(MaintainTeamUtil.isSuperUser(user.getLoginID()));
    		sc.getCriteria().setTeam((ITeam) map.get(IGlobalConstant.USER_TEAM));
            
			SearchResult sr = getCmsTeamProxy().searchTeams(sc);
			resultMap.put("TeamList", sr);
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
