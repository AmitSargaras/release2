/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.bizstructure;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: dli $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/08/16 12:33:34 $ Tag: $Name: $
 */
public class UserSearchCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public UserSearchCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "Team", "com.integrosys.component.bizstructure.app.bus.OBTeam", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "memshipTypeId", "java.lang.String", REQUEST_SCOPE },
				{ "teamTypeId", "java.lang.String", REQUEST_SCOPE }, { "teamId", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "fromFlag", "java.lang.String", SERVICE_SCOPE },
				{ "memshipTypeId_Front", "java.lang.String", GLOBAL_SCOPE },
				{ "memshipTypeId", "java.lang.String", SERVICE_SCOPE },
				{ "mapTeam", "com.integrosys.component.bizstructure.app.bus.OBTeam", SERVICE_SCOPE },
				{ "teamTypeId", "java.lang.String", GLOBAL_SCOPE }, { "teamId", "java.lang.String", GLOBAL_SCOPE }, });
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
		String fromFlag = event;
		ITeam team = (ITeam) map.get("Team");
		resultMap.put("mapTeam", team);
		resultMap.put("fromFlag", fromFlag);

		DefaultLogger.debug(this, "==== Team Role Type Id = " + (String) map.get("memshipTypeId"));

		resultMap.put("memshipTypeId_Front", map.get("memshipTypeId"));
		resultMap.put("memshipTypeId", map.get("memshipTypeId"));
		resultMap.put("teamTypeId", map.get("teamTypeId"));
		resultMap.put("teamId", map.get("teamId"));

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		// DefaultLogger.debug(this, "Going out of doExecute()"+team);
		return returnMap;
	}
}
