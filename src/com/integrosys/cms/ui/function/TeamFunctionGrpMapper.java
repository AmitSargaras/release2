package com.integrosys.cms.ui.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.function.bus.ITeamFunctionGrp;
import com.integrosys.cms.app.function.bus.OBTeamFunctionGrp;

public class TeamFunctionGrpMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {
		TeamFunctionGrpForm form = (TeamFunctionGrpForm) aForm;
		List listOfObject = new ArrayList();
		if (form.getIsPreDisb()) {
			ITeamFunctionGrp teamFunctionGrp = new OBTeamFunctionGrp();
			teamFunctionGrp.setTeamId(Long.parseLong(form.getTeamId()));
			teamFunctionGrp.setTeamTypeId(Long.parseLong(form.getTeamTypeId()));
			teamFunctionGrp.setFunctionGrpId(1);
			listOfObject.add(teamFunctionGrp);
		}
		if (form.getIsDisb()) {
			ITeamFunctionGrp teamFunctionGrp = new OBTeamFunctionGrp();
			teamFunctionGrp.setTeamId(Long.parseLong(form.getTeamId()));
			teamFunctionGrp.setTeamTypeId(Long.parseLong(form.getTeamTypeId()));
			teamFunctionGrp.setFunctionGrpId(2);
			listOfObject.add(teamFunctionGrp);
		}
		if (form.getIsPostDisb()) {
			ITeamFunctionGrp teamFunctionGrp = new OBTeamFunctionGrp();
			teamFunctionGrp.setTeamId(Long.parseLong(form.getTeamId()));
			teamFunctionGrp.setTeamTypeId(Long.parseLong(form.getTeamTypeId()));
			teamFunctionGrp.setFunctionGrpId(3);
			listOfObject.add(teamFunctionGrp);
		}
		return listOfObject;
	}

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapOBToForm(...).");


		TeamFunctionGrpForm form = (TeamFunctionGrpForm) aForm;

		List listOfTeamFunctionGrp = (List) object;

		if (listOfTeamFunctionGrp != null && !listOfTeamFunctionGrp.isEmpty()) {
			for (int i = 0; i < listOfTeamFunctionGrp.size(); i++) {
				ITeamFunctionGrp teamFunctionGrp = (ITeamFunctionGrp) listOfTeamFunctionGrp.get(i);
			}
		}
		String event = form.getEvent();


		return form;
	}

	private void extractForDisplay(int offset, int length, TeamFunctionGrpForm form, List result, Locale locale) {

		if (result == null) {
			// Do nothing when there is no group.
			return;
		}

		DefaultLogger.debug(this, "number of feed entries = " + result.size());

		int limit = offset + length;
		if (limit > result.size()) {
			DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > result.size() " + result.size());

			limit = result.size();
		}
	}

}
