package com.integrosys.cms.ui.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.function.bus.ITeamFunctionGrp;
import com.integrosys.cms.app.function.bus.TeamFunctionGrpException;
import com.integrosys.cms.ui.bizstructure.MaintainTeamAction;
import com.integrosys.cms.ui.bizstructure.MaintainTeamUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.OBTeamSearchCriteria;
import com.integrosys.component.bizstructure.app.bus.TeamSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;

public class ListTeamFunctionGrpCommand extends TeamFunctionGrpCommand {
	public String[][] getResultDescriptor() {
		return new String[][] { { "formTeamFunctionGrps", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE } };
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "startIndex", "java.lang.String", REQUEST_SCOPE},
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE } });
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		// TeamFunctionGrpForm form = new TeamFunctionGrpForm();
		// List listTeamFunctionGrp = null;
		List result = new ArrayList();
		TeamFunctionGrpForm form = null;
		List listFunctionId = null;
		
		try {
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			
			OBTeamSearchCriteria obSearchCriteria = new OBTeamSearchCriteria();
			obSearchCriteria.setHasSuperUserConcept(MaintainTeamUtil.hasSuperUserConcept());
			obSearchCriteria.setSuperUser(MaintainTeamUtil.isSuperUser(user.getLoginID()));
			obSearchCriteria.setTeam((ITeam) map.get(IGlobalConstant.USER_TEAM));
			obSearchCriteria.setTeamTypeID(ICMSConstant.TEAM_TYPE_SSC);
			
			TeamSearchCriteria sc = new TeamSearchCriteria();
			sc.setCriteria(obSearchCriteria);
			
			String startIndexStr = (String)map.get("startIndex");
			if (StringUtils.isEmpty(startIndexStr))
				sc.setStartIndex(0);
			else
				sc.setStartIndex(Integer.parseInt(startIndexStr));
			
			sc.setNItems(PropertyManager.getInt("pagination.records.per.page", 10));
			sc.setFirstSort(MaintainTeamAction.FIRST_SORT);
			
			CMSTeamProxy teamProxy = new CMSTeamProxy();
			SearchResult sr = teamProxy.searchTeams(sc);
			List teamList = (List)sr.getResultList();

			for (int i = 0; i < teamList.size(); i++) {
				ITeam team = (ITeam)teamList.get(i);
				List listTeamFunctionGrp = getTeamFunctionGrpProxy().getTeamFunctionGrpByTeamId(team.getTeamID());

				form = new TeamFunctionGrpForm();
				form.setTeamId(String.valueOf(team.getTeamID()));
				form.setTeamTypeId(String.valueOf(ICMSConstant.TEAM_TYPE_SSC));
				form.setTeamName(team.getAbbreviation());
				form.setTeamDesc(team.getDescription());

				if (listTeamFunctionGrp != null && !listTeamFunctionGrp.isEmpty()) {
					listFunctionId = new ArrayList();
					for (int j = 0; j < listTeamFunctionGrp.size(); j++) {
						ITeamFunctionGrp teamFunctionGrp = (ITeamFunctionGrp) listTeamFunctionGrp.get(j);
						listFunctionId.add(String.valueOf(teamFunctionGrp.getFunctionGrpId()));
					}
					form.setGroupFunction(listFunctionId);
					form.setIsPreDisb(listFunctionId.contains("1"));
					form.setIsDisb(listFunctionId.contains("2"));
					form.setIsPostDisb(listFunctionId.contains("3"));
				}
				result.add(form);
			}
			
			sr = new SearchResult(sr.getStartIndex(), result.size(), sr.getNItems(), result);
			resultMap.put("formTeamFunctionGrps", sr);

		}
		catch (TeamFunctionGrpException e) {
			throw new CommandProcessingException("Exception at ListTeamFunctionGrpCommand - TeamFunctionGrpException", e);
		}
		catch (Exception e) {
			throw new CommandProcessingException("Caught exception at ListTeamFunctionGrpCommand ", e);
		}		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
