/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrent;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/08/19 11:22:31 $ Tag: $Name: $
 */
public class RecurrentAccessControlCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public RecurrentAccessControlCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			String event = (String) map.get("event");
			filterByFunctionAccess(user, team, event);
		//	filterByDDAP(map);
			DefaultLogger.debug(this, "Going out of doExecute()");
			returnMap.put(COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (AccessDeniedException e) {
			DefaultLogger.error(this, "Caught AccessDeniedException.", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in GlobalAccessControlCommand!", e);
			throw (new CommandProcessingException(e.toString()));
		}
	}

	/**
	 * Determines function access.
	 * @param team The team.
	 * @param action The struts action path value.
	 * @param event The event value.
	 * @throws AccessDeniedException when cannot grant access.
	 */
	private void filterByFunctionAccess(ICommonUser user, ITeam team, String event) throws AccessDeniedException {
		if (!EVENT_READ.equals(event)) {
			if (!CheckListUtil.allowByFunctionAccess(user, team)) {
				AccessDeniedException e = new AccessDeniedException("No function access");
				e.setErrorCode(ICMSErrorCodes.FAP_NO_ACCESS);
				throw e;
			}
		}
	}

	/**
	 * Helper method to filter by dynamic DAP (DDAP)
	 */
	private void filterByDDAP(HashMap map) throws AccessDeniedException {
		String event = (String) map.get("event");
		if (!EVENT_READ.equals(event)) {

			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ILimitProfile profile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			if (!CheckListUtil.allowByDDAP(customer, profile, team)) {
				AccessDeniedException e = new AccessDeniedException(
						"ICMSCustomer in Global Scope is null! Unable to proceed.");
				e.setErrorCode(ICMSErrorCodes.DDAP_SETUP_ERROR);
				throw e;
			}
		}
	}
}