package com.integrosys.cms.ui.workspace;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.workflow.OBTaskListSearchCriteria;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.workspace.SBWorkspaceManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

public class PrepareWorkspaceCommand extends AbstractCommand implements ICommonEventConstant {

	public static final String WORKSPACEMGR_SB_HOME = "SBWorkspaceManagerHome";

	public static final String WORKSPACEMGR_SB_HOME_PATH = "com.integrosys.cms.app.workspace.SBWorkspaceManagerHome";

	/**
	 * Default Constructor
	 */
	public PrepareWorkspaceCommand() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "searchResult", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
		// {"colTrackList", "java.util.Collection", REQUEST_SCOPE},
				// {"notList", "java.util.Collection", REQUEST_SCOPE},
				{ "event", "java.lang.String", REQUEST_SCOPE },
		// {"crDoList", "java.util.Collection", REQUEST_SCOPE}
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here preparation for company borrower is
	 * done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();

		Locale locales = (Locale) map.get("locale");
		try {
			// todo-type can be set based on the role type
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			String todoType = "";
			OBTaskListSearchCriteria criteria = new OBTaskListSearchCriteria();
			criteria.setStartIndex(1);
			criteria.setNItems(20);
			criteria.setProcessID("102"); // hardcode for testing
			criteria.setUserID(new Long(user.getUserID()).toString());
			criteria.setGroupID(new Long(team.getTeamID()).toString());
			SearchResult searchResult = getSBWorkspaceManager().getToDoList(criteria, todoType);
			result.put("searchResult", searchResult);
			// Collection col = result.getResultList();
			// result.put("colDoList", col);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			return returnMap;
		}
		catch (Throwable e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
	}

	private SBWorkspaceManager getSBWorkspaceManager() throws Exception {
		SBWorkspaceManager workspaceMgr = (SBWorkspaceManager) BeanController.getEJB(WORKSPACEMGR_SB_HOME,
				WORKSPACEMGR_SB_HOME_PATH);
		if (workspaceMgr == null) {
			throw new Exception();
		}
		return workspaceMgr;
	}
}
