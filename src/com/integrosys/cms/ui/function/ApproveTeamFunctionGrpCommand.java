package com.integrosys.cms.ui.function;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.function.proxy.ITeamFunctionGrpProxy;
import com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public class ApproveTeamFunctionGrpCommand extends TeamFunctionGrpCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "teamFunctionGrpTrxValue", "com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			ITeamFunctionGrpTrxValue value = (ITeamFunctionGrpTrxValue) map.get("teamFunctionGrpTrxValue");
			
			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);
			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}
			
			ITeamFunctionGrpProxy proxy = getTeamFunctionGrpProxy();
			value = proxy.checkerApproveTeamFunctionGrp(trxContext, value);
			resultMap.put("request.ITrxValue", value);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
