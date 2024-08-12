package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class LmtListMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		// TODO Auto-generated method stub
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			String event = (String) (inputs.get("event"));
			String limitProfileID = (String) (inputs.get("limitProfileID"));
			MILimitUIHelper helper = new MILimitUIHelper();
			ILimit lmtObj = (ILimit) obj;
			LmtDetailForm lmtDetailForm = (LmtDetailForm) commonForm;

			if (helper.checkShowSublist(inputs)) {
				lmtDetailForm.setShowSublist("Y");
			}
			else {
				lmtDetailForm.setShowSublist(null);
			}

			LmtDetailMapper.renderAcntRefSummary(lmtObj, lmtDetailForm, helper);
			LmtDetailMapper.renderLmtSecSummary(lmtObj, lmtDetailForm, helper);
			return lmtDetailForm;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
		return null;
	}
}