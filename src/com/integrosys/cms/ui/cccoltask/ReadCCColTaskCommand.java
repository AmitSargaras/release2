/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.cccoltask;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.collaborationtask.bus.OBCCTask;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
import com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/04/13 06:57:53 $ Tag: $Name: $
 */
public class ReadCCColTaskCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadCCColTaskCommand() {
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
				{ "colTaskList", "java.util.List", SERVICE_SCOPE },
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
				{ "status", "java.util.List", REQUEST_SCOPE }, { "wip", "java.lang.String", REQUEST_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "colTrxVal", "com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue", SERVICE_SCOPE },
				{ "colTask", "com.integrosys.cms.app.collaborationtask.bus.ICCTask", FORM_SCOPE },
				{ "colTask", "com.integrosys.cms.app.collaborationtask.bus.ICCTask", SERVICE_SCOPE },
				{ "originEvent", "java.lang.String", SERVICE_SCOPE } });
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
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if (limit != null) {
				limitProfileID = limit.getLimitProfileID();
			}

			int ind = Integer.parseInt((String) map.get("index"));
			String event = (String) map.get("event");
			ArrayList list = (ArrayList) map.get("colTaskList");
			// CR CMS-534 Starts
			ArrayList deletedList = (ArrayList) map.get("colTaskList1");
			CCCheckListSummary summary = null;
			int normalListSize = 0;
			if ((list != null) && (list.size() > 0)) {
				normalListSize = list.size();
			}
			if ((normalListSize > 0) && (ind < list.size())) {
				summary = (CCCheckListSummary) list.get(ind);
			}
			else {
				summary = (CCCheckListSummary) deletedList.get(ind - normalListSize);
			}
			// CR CMS-534 Ends

			if ((!"view_task".equals(event))
					&& proxy.hasPendingCCTaskTrx(limitProfileID, summary.getCustCategory(), summary.getSubProfileID(),
							summary.getDomicileCtry())) {
				resultMap.put("wip", "wip");
			}
			ICCTask colTask = null;
			if ("view_task".equals(event) || "read_task_update".equals(event) || "view_out_task".equals(event)
					|| "maker_pre_reject".equals(event)) {
				ICCTaskTrxValue trx = proxy.getCCTaskTrxValue(limitProfileID, summary.getCustCategory(), summary
						.getSubProfileID(), summary.getDomicileCtry());
				if (trx != null) {
					resultMap.put("colTrxVal", trx);
					if ("read_task_update".equals(event) && ICMSConstant.STATE_REJECTED.equals(trx.getStatus())) {
						colTask = trx.getStagingCCTask();
					}
					else {
						colTask = trx.getCCTask();
						colTask.setDomicileCountry(summary.getDomicileCtry());
						colTask.setOrgCode(summary.getOrgCode());
					}
				}
			}
			if (colTask == null) {
				colTask = new OBCCTask();
				colTask.setCustomerCategory(summary.getCustCategory());
				colTask.setCustomerID(summary.getSubProfileID());
				colTask.setLegalRef(summary.getLegalID());
				colTask.setLegalName(summary.getLegalName());
				colTask.setCustomerType(summary.getCustomerSegmentCode());
				colTask.setDomicileCountry(summary.getDomicileCtry());
				colTask.setOrgCode(summary.getOrgCode());
				colTask.setLimitProfileID(limitProfileID);
				resultMap.put("colTrxVal", null);
			}
			resultMap.put("originEvent", event);
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
