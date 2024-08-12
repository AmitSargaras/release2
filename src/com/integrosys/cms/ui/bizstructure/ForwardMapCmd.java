/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.bizstructure;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/07 05:04:05 $ Tag: $Name: $
 */
public class ForwardMapCmd extends AbstractCommand implements ICommonEventConstant {

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
	public ForwardMapCmd() {

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
				{ "CommonUserSearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",
						FORM_SCOPE }, { "userMap", "java.util.ArrayList", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "addUserList", "java.util.ArrayList", SERVICE_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		List userIdList = (List) map.get("userMap");
		String event = (String) map.get("event");
		ArrayList userList = new ArrayList();
		DefaultLogger.debug(this, "Inside doExecute() ");
		if ((event != null)
				&& !(event.equals("cancle_add_users_for_add") || event.equals("cancle_add_users_for_add_reject")
						|| event.equals("cancle_add_users_for_edit") || event
						.equals("cancle_add_users_for_edit_reject"))) {
			if ((userIdList != null) && (userIdList.size() > 0)) {
				for (int i = 0; i < userIdList.size(); i++) {
					try {

						userList.add(getUserProxy().getUserByPK((String) userIdList.get(i)).getUser());
					}
					catch (EntityNotFoundException e) {
						CommandProcessingException cpe = new CommandProcessingException(
								"failed to retrieve user, user id [" + userIdList.get(i) + "]");
						cpe.initCause(e);
						throw cpe;
					}
					catch (RemoteException e) {
						CommandProcessingException cpe = new CommandProcessingException(
								"failed to remote call on user proxy [" + this.userProxy.getClass()
										+ "], throwing root cause.");
						cpe.initCause(e.getCause());
						throw cpe;
					}
				}

			}
		}
		else if ((event.equals("cancle_add_users_for_add") || event.equals("cancle_add_users_for_add_reject")
				|| event.equals("cancle_add_users_for_edit") || event.equals("cancle_add_users_for_edit_reject"))) {
			// unset all the selected user when its a cancel event..to avoid the
			// session manipulation probs..
			userList = new ArrayList();
		}

		DefaultLogger.debug(this, "return from doExecute() ");
		resultMap.put("addUserList", userList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}