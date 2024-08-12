package com.integrosys.cms.ui.function;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.function.proxy.ITeamFunctionGrpProxy;
import com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public class CloseTeamFunctionGrpCommand extends TeamFunctionGrpCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "teamFunctionGrpTrxValue", "com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "trxId", "java.lang.String", REQUEST_SCOPE },};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			ITeamFunctionGrpTrxValue value = (ITeamFunctionGrpTrxValue) map.get("teamFunctionGrpTrxValue");
			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			ITeamFunctionGrpProxy proxy = getTeamFunctionGrpProxy();

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if (value == null ) {
				value = proxy.getTeamFunctionGrpByTrxId(trxContext, (String)map.get("trxId"));
			}
			
			// String remarks = (String)map.get(GoldListForm.MAPPER);
			// value.setRemarks(remarks);

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			/*
			 * if (value.getStatus().equals(ICMSConstant.STATE_DRAFT)) { value =
			 * proxy.makerCloseDraftTeamFunctionGrp(trxContext, value); } else {
			 */
			value = proxy.makerCloseRejectedTeamFunctionGrp(trxContext, value);
			// }

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
