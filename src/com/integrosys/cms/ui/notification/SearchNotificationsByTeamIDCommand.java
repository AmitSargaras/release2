/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/notification/SearchNotificationsByTeamIDCommand.java,v 1.16 2006/11/10 02:30:29 jzhan Exp $
 */
package com.integrosys.cms.ui.notification;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.bus.PaginationBean;
import com.integrosys.cms.app.common.bus.PaginationResult;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.notification.proxy.CMSNotificationFactory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2006/11/10 02:30:29 $ Tag: $Name: $
 */

public class SearchNotificationsByTeamIDCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public SearchNotificationsByTeamIDCommand() {

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
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ ICMSConstant.PARAM_NOTIFICATION_START_INDEX, "java.lang.String", REQUEST_SCOPE },
				{ "totalNotificationCount", "java.lang.String", REQUEST_SCOPE },
				{ "notificationList", "java.util.ArrayList", SERVICE_SCOPE },
				// CR-120
				{ "searchLegalName", "java.lang.String", REQUEST_SCOPE },
				{ "searchLeID", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "notificationList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "deletedNotificationList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "session.deletedNotificationList", "java.util.ArrayList", SERVICE_SCOPE },
				{ ICMSConstant.PARAM_NOTIFICATION_START_INDEX, "java.lang.String", REQUEST_SCOPE },
				{ "totalNotificationCount", "java.lang.String", REQUEST_SCOPE },
				// CR-120
				{ "session.startIndex", "java.lang.String", SERVICE_SCOPE },
				{ "session.searchLegalName", "java.lang.String", SERVICE_SCOPE },
				{ "session.searchLeID", "java.lang.String", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Entered doExecute");
		HashMap result = new HashMap();
		HashMap temp = new HashMap();
		try {
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			String status = (String) map.get("status");
			// CR-120 Search
			String searchLegalName = (String) map.get("searchLegalName");
			String searchLeID = (String) map.get("searchLeID");
			long lsearchLeID;
			try {
				lsearchLeID = Long.parseLong(searchLeID);
			}
			catch (Exception e) {
				lsearchLeID = ICMSConstant.LONG_INVALID_VALUE;
			}

			// ArrayList notificationList =
			// (ArrayList)map.get("notificationList");
			ArrayList notificationList = (ArrayList) map.get("notificationList");
			map.remove("notificationList");
			if (notificationList != null) {
				notificationList.clear();
				notificationList = null;
			}

			PaginationBean pgBean;
			String totalCount = (String) (map.get("totalNotificationCount"));
			long totalCounts = (totalCount != null) ? Long.parseLong(totalCount) : -1;

			String startInd = (String) (map.get(ICMSConstant.PARAM_NOTIFICATION_START_INDEX));

			if (startInd != null) {
				pgBean = new PaginationBean(Long.parseLong(startInd) + 1, Long.parseLong(startInd) + 10, totalCounts);
			}
			else {
				pgBean = new PaginationBean(1, 10, totalCounts);
			}

			ArrayList deletedNotificationList = null;
			PaginationResult pgRes = null;
			if ((notificationList == null) || ((searchLegalName != null) && !searchLegalName.equals(""))
					|| ((searchLeID != null) && !searchLeID.equals(""))) {
				pgRes = CMSNotificationFactory.getCMSNotificationProxy().getNotificationsByTeamID(user.getUserID(),
						team.getTeamID(), status, searchLegalName, lsearchLeID, pgBean);
			}
			else {
				pgRes = CMSNotificationFactory.getCMSNotificationProxy().getNotificationsByTeamID(user.getUserID(),
						team.getTeamID(), status, null, ICMSConstant.LONG_INVALID_VALUE, pgBean);
			}
			if (status != null) {
				deletedNotificationList = CMSNotificationFactory.getCMSNotificationProxy().getNotificationsByTeamID(
						user.getUserID(), team.getTeamID(), status, searchLegalName, lsearchLeID);
			}

			if (pgRes != null) {
				notificationList = (ArrayList) (pgRes.getResultList());
			}
			result.put("notificationList", notificationList);
			result.put("deletedNotificationList", deletedNotificationList);
			result.put("totalNotificationCount", "" + pgRes.getCount());
			result.put("session.deletedNotificationList", deletedNotificationList);
			result.put("status", status);
			result.put("session.searchLegalName", searchLegalName);
			result.put("session.searchLeID", searchLeID);
			result.put(ICMSConstant.PARAM_NOTIFICATION_START_INDEX, map
					.get(ICMSConstant.PARAM_NOTIFICATION_START_INDEX));
			result.put("session.startIndex", map.get(ICMSConstant.PARAM_NOTIFICATION_START_INDEX));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}
}
