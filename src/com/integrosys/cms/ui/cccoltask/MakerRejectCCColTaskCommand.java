package com.integrosys.cms.ui.cccoltask;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
import com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Mar 20, 2006 Time: 12:51:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class MakerRejectCCColTaskCommand extends AbstractCommand implements ICommonEventConstant {

	public MakerRejectCCColTaskCommand() {

	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "colTask", "com.integrosys.cms.app.collaborationtask.bus.ICCTask", SERVICE_SCOPE },
				{ "colRemarks", "java.lang.String", REQUEST_SCOPE },
				{ "colTrxVal", "com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {

			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICCTask task = (ICCTask) map.get("colTask");
			ICollaborationTaskProxyManager proxy = CollaborationTaskProxyManagerFactory.getProxyManager();
			if (customer.getNonBorrowerInd()) {
				ctx.setTrxCountryOrigin(CCColTaskUtil.getCCColTaskTrxCountry(ctx, task, null, customer));
			}
			else {
				ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
				ctx.setTrxCountryOrigin(CCColTaskUtil.getCCColTaskTrxCountry(ctx, task, limit, customer));
			}
			ICCTaskTrxValue colTrxVal = (ICCTaskTrxValue) map.get("colTrxVal");
			String colRemarks = (String) map.get("colRemarks");

			ctx.setRemarks(colRemarks);
			if (colTrxVal != null) {
				ICCTask sTask = colTrxVal.getStagingCCTask();
				sTask.setRemarks(colRemarks);
				sTask.setOrgCode(task.getOrgCode());
				sTask.setDomicileCountry(task.getDomicileCountry());
				colTrxVal = proxy.makerRejectCollaborationTask(ctx, colTrxVal, task);
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
