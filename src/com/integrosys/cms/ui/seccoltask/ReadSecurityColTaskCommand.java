/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccoltask;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.collaborationtask.bus.OBCollateralTask;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
import com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/08/07 11:24:31 $ Tag: $Name: $
 */
public class ReadSecurityColTaskCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadSecurityColTaskCommand() {
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
						GLOBAL_SCOPE }, { "colTaskList", "java.util.List", SERVICE_SCOPE },
				{ "colTaskList1", "java.util.List", SERVICE_SCOPE }, { "index", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "colTaskList", "java.util.List", SERVICE_SCOPE },
				{ "colTaskList1", "java.util.List", SERVICE_SCOPE }, { "status", "java.util.List", REQUEST_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "colTrxVal", "com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue", SERVICE_SCOPE },
				{ "colTask", "com.integrosys.cms.app.collaborationtask.bus.ICollateralTask", FORM_SCOPE },
				{ "colTask", "com.integrosys.cms.app.collaborationtask.bus.ICollateralTask", SERVICE_SCOPE },
				{ "originEvent", "java.lang.String", SERVICE_SCOPE }, });
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ICollaborationTaskProxyManager proxy = CollaborationTaskProxyManagerFactory.getProxyManager();
			boolean wip = false;
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			int ind = Integer.parseInt((String) map.get("index"));
			String event = (String) map.get("event");
			ArrayList list = (ArrayList) map.get("colTaskList");
			ArrayList deletedList = (ArrayList) map.get("colTaskList1");
			CollateralCheckListSummary summary = null;
			int normalListSize = 0;
			if ((list != null) && (list.size() > 0)) {
				normalListSize = list.size();
			}
			if ((normalListSize > 0) && (ind < list.size())) {
				summary = (CollateralCheckListSummary) list.get(ind);
			}
			else {
				summary = (CollateralCheckListSummary) deletedList.get(ind - normalListSize);
			}
			if ((!"view_task".equals(event))
					&& proxy.hasPendingCollateralTaskTrx(limitProfileID, summary.getCollateralID(), summary
							.getCollateralLocation())) {
				resultMap.put("wip", "wip");
			}

			ICollateralTask colTask = null;
			if ("view_task".equals(event) || "read_task_update".equals(event) || "maker_pre_reject".equals(event)) {
				ICollateralTaskTrxValue trx = proxy.getCollateralTaskTrxValue(limitProfileID,
						summary.getCollateralID(), summary.getCollateralLocation());
				if (trx != null) {
					resultMap.put("colTrxVal", trx);
					if ("view_task".equals(event)) {
						colTask = trx.getCollateralTask();
					}
					else if (ICMSConstant.STATE_REJECTED.equals(trx.getStatus())
							|| ICMSConstant.STATE_REJECT_REJECTED.equals(trx.getStatus())) {
						colTask = trx.getStagingCollateralTask();
					}
					else {
						colTask = trx.getCollateralTask();
					}
				}
			}
			if (colTask == null) {
				colTask = new OBCollateralTask();
				colTask.setCollateralID(summary.getCollateralID());
				colTask.setCollateralRef(summary.getCollateralRef());
				colTask.setCollateralLocation(summary.getCollateralLocation());
				colTask.setSecurityOrganisation(summary.getSecurityOrganization());
				colTask.setLeSubProfileID(summary.getLeSubProfileID());
				colTask.setCustomerCategory(summary.getCustCategory());
				colTask.setCoBorrowerLimitList(summary.getCoborrowerLimitList());
				colTask.setCollateralSubType(summary.getCollateralSubType());
				colTask.setCollateralType(summary.getCollateralType());
				colTask.setLimitProfileID(limitProfileID);
				colTask.setLimitList(summary.getLimitList());
				resultMap.put("colTrxVal", null);
			}

			resultMap.put("originEvent", map.get("event"));
			resultMap.put("colTask", colTask);
			resultMap.put("limitProfile", String.valueOf(limitProfileID));
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
