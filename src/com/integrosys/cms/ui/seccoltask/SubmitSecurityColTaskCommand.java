/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccoltask;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
import com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/04/13 07:55:06 $ Tag: $Name: $
 */
public class SubmitSecurityColTaskCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SubmitSecurityColTaskCommand() {
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
				{ "colTask", "com.integrosys.cms.app.collaborationtask.bus.ICollateralTask", SERVICE_SCOPE },
				{ "colRemarks", "java.lang.String", REQUEST_SCOPE },
				{ "colTrxVal", "com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
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

			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICollateralTaskTrxValue colTrxVal = (ICollateralTaskTrxValue) map.get("colTrxVal");
			String colRemarks = (String) map.get("colRemarks");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ctx.setRemarks(colRemarks);
			ICollaborationTaskProxyManager proxy = CollaborationTaskProxyManagerFactory.getProxyManager();

			if (colTrxVal != null) {
				ICollateralTask sTask = colTrxVal.getStagingCollateralTask();
				ctx.setTrxCountryOrigin(SecColTaskUtil.getSecColTaskTrxCountry(ctx, sTask, limit));

				sTask.setRemarks(colRemarks);
				if (ICMSConstant.STATE_REJECTED.equals(colTrxVal.getStatus())
						|| ICMSConstant.STATE_REJECT_REJECTED.equals(colTrxVal.getStatus())) {
					colTrxVal = proxy.makerEditRejectedCollaborationTask(ctx, colTrxVal, sTask);
				}
				else {
					colTrxVal = proxy.makerUpdateCollaborationTask(ctx, colTrxVal, sTask);
				}
				DefaultLogger.debug(this, "Remarks in Coll task" + sTask.getRemarks());
			}
			else {
				ICollateralTask task = (ICollateralTask) map.get("colTask");
				task.setRemarks(colRemarks);
				ctx.setTrxCountryOrigin(SecColTaskUtil.getSecColTaskTrxCountry(ctx, task, limit));
				colTrxVal = proxy.makerCreateCollaborationTask(ctx, task);
			}
			resultMap.put("request.ITrxValue", colTrxVal);
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
