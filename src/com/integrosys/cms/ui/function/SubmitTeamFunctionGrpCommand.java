package com.integrosys.cms.ui.function;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.function.bus.ITeamFunctionGrp;
import com.integrosys.cms.app.function.proxy.ITeamFunctionGrpProxy;
import com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue;
import com.integrosys.cms.app.function.trx.OBTeamFunctionGrpTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class SubmitTeamFunctionGrpCommand extends TeamFunctionGrpCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ TeamFunctionGrpForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "teamFunctionGrpTrxValue", "com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
		};
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue", REQUEST_SCOPE },
				{ "teamFunctionGrpTrxValue", "com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue",
						SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(TeamFunctionGrpForm.MAPPER);
			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
			ITeamFunctionGrpTrxValue value = (ITeamFunctionGrpTrxValue) map.get("teamFunctionGrpTrxValue");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);
			ITeamFunctionGrpProxy proxy = getTeamFunctionGrpProxy();
			ITeamFunctionGrpTrxValue resultValue = null;
			ITeam team = null;
			if (inputList != null) {
				ITeamFunctionGrp teamFunctionGrp = (ITeamFunctionGrp) inputList.get(0);
				CMSTeamProxy teamProxy = new CMSTeamProxy();
				team = teamProxy.getTeam(teamFunctionGrp.getTeamId());
			}
			if (value == null) {
				value = new OBTeamFunctionGrpTrxValue();
				value.setStagingTeamFunctionGrps(inputList);
				resultValue = proxy.makerSubmitTeamFunctionGrpCreateTrans(trxContext, value, value
						.getStagingTeamFunctionGrps(), team);
			}
			else {
				if (value.getReferenceID() != null) {
					value.setTeamFunctionGrps(getTeamFunctionGrpProxy().getActualTeamFunctionGrpByGroupId(
							Long.parseLong(value.getReferenceID())));
				}
				value.setStagingTeamFunctionGrps(inputList);
				if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
					resultValue = proxy.makerSubmitRejectedTeamFunctionGrp(trxContext, value, team);
				}
				else {
					resultValue = proxy.makerSubmitTeamFunctionGrpUpdateTrans(trxContext, value, value
							.getStagingTeamFunctionGrps(), team);
				}
			}
			resultMap.put("request.ITrxValue", resultValue);
			resultMap.put("teamFunctionGrpTrxValue", resultValue);
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
