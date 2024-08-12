package com.integrosys.cms.ui.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.function.bus.ITeamFunctionGrp;
import com.integrosys.cms.app.function.proxy.ITeamFunctionGrpProxy;
import com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.base.businfra.search.SearchResult;

public class ReadTeamFunctionGrpCommand extends TeamFunctionGrpCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "formTeamFunctionGrps", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "isTrack", "java.lang.String", REQUEST_SCOPE},
				{ "teamId", "java.lang.String", REQUEST_SCOPE }, 
				{ "trxId", "java.lang.String", REQUEST_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce the offset.
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				// Produce the length.
				{ "length", "java.util.Integer", SERVICE_SCOPE },
				// To populate the form.
				/*
				 * { TeamFunctionGrpForm.MAPPER,
				 * "com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue",
				 * FORM_SCOPE },
				 */
				{ "formTeamFunctionGrp", "com.integrosys.cms.ui.function.TeamFunctionGrpForm", SERVICE_SCOPE },
				{ "teamFunctionGrpTrxValue", "com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue",
						SERVICE_SCOPE },
				{ "teamFunctionGrpTrxValueForView", "com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue",
						SERVICE_SCOPE }, };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		ITeamFunctionGrpTrxValue trxValue = null;
		ITeamFunctionGrpTrxValue trxValueForView = null;
		TeamFunctionGrpForm formTeamFunctionGrp = null;
		String event = (String) map.get("event");
		try {

			SearchResult sr = (SearchResult) map.get("formTeamFunctionGrps");
			String trxId = (String) map.get("trxId");
			String isTrack = (String)map.get("isTrack");
			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
			if ((TeamFunctionGrpAction.EVENT_READ_MAKER_EDIT.equals(event) ||
					(TeamFunctionGrpAction.EVENT_VIEW.equals(event) 
							&& (isTrack == null || isTrack.length() == 0)))
					&& sr != null) {// called
				// from
				// list
				// page
				String teamId = (String) map.get("teamId");
				List listFormTeamFunctionGrp = (List)sr.getResultList();
				for (int i = 0; i < listFormTeamFunctionGrp.size(); i++) {
					formTeamFunctionGrp = (TeamFunctionGrpForm) listFormTeamFunctionGrp.get(i);
					if (formTeamFunctionGrp.getTeamId().equals(teamId)) {
						break;
					}
				}

				ITeamFunctionGrpProxy proxy = getTeamFunctionGrpProxy();
				trxValue = proxy
						.getTeamFunctionGrpByTeamId(trxContext, Long.parseLong(formTeamFunctionGrp.getTeamId()));
				resultMap.put("formTeamFunctionGrp", formTeamFunctionGrp);
			}
			else if (TeamFunctionGrpAction.EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)
					|| TeamFunctionGrpAction.EVENT_READ_MAKER_PROCESS.equals(event)
					|| TeamFunctionGrpAction.EVENT_VIEW.equals(event)
					|| TeamFunctionGrpAction.EVENT_READ_CHECKER_CLOSE.equals(event)) {

				ITeamFunctionGrpProxy proxy = getTeamFunctionGrpProxy();
				trxValue = proxy.getTeamFunctionGrpByTrxId(trxContext, trxId);
				trxValueForView = proxy.getTeamFunctionGrpByTrxId(trxContext, trxId);

				List listTeamFunctionGrpStaging = proxy.getStageTeamFunctionGrpByGroupId(Long.parseLong(trxValue
						.getStagingReferenceID()));
				List listTeamFunctionGrpActual = null;
				if (trxValue.getReferenceID() != null) {
					listTeamFunctionGrpActual = proxy.getActualTeamFunctionGrpByGroupId(Long.parseLong(trxValue
							.getReferenceID()));
				}

				long teamId = ((ITeamFunctionGrp) listTeamFunctionGrpStaging.get(0)).getTeamId();
				CMSTeamProxy teamProxy = new CMSTeamProxy();
				ITeam team = teamProxy.getTeam(teamId);
				TeamFunctionGrpForm form = new TeamFunctionGrpForm();
				form.setTeamId(String.valueOf(team.getTeamID()));
				form.setTeamTypeId("1");
				form.setTeamName(team.getAbbreviation());
				form.setTeamDesc(team.getDescription());

				List listFunctionId = null;
				if (listTeamFunctionGrpStaging != null && !listTeamFunctionGrpStaging.isEmpty()) {
					listFunctionId = new ArrayList();
					for (int i = 0; i < listTeamFunctionGrpStaging.size(); i++) {
						ITeamFunctionGrp teamFunctionGrp = (ITeamFunctionGrp) listTeamFunctionGrpStaging.get(i);
						listFunctionId.add(String.valueOf(teamFunctionGrp.getFunctionGrpId()));
					}
					form.setGroupFunction(listFunctionId);
					form.setIsPreDisb(listFunctionId.contains("1"));
					form.setIsDisb(listFunctionId.contains("2"));
					form.setIsPostDisb(listFunctionId.contains("3"));
				}
				List stagingTeamFunctionGrps = new ArrayList();
				stagingTeamFunctionGrps.add(form);
				trxValueForView.setStagingTeamFunctionGrps(stagingTeamFunctionGrps);
				resultMap.put("teamFunctionGrpTrxValueForView", trxValueForView);
				resultMap.put("formTeamFunctionGrp", form);
				trxValue.setStagingTeamFunctionGrps(listTeamFunctionGrpStaging);
				trxValue.setTeamFunctionGrps(listTeamFunctionGrpActual);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		resultMap.put("teamFunctionGrpTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
