package com.integrosys.cms.ui.manualinput.security;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateral;

public class SecListMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "secTrxObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		// TODO Auto-generated method stub
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			MISecurityUIHelper helper = new MISecurityUIHelper();
			ICollateral colObj = (ICollateral) obj;
			SecDetailForm secDetailForm = (SecDetailForm) commonForm;
			SecDetailMapper.renderPledgorSummary(colObj, secDetailForm, helper);
			return secDetailForm;
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