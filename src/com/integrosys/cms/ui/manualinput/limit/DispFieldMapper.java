package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class DispFieldMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "agreementType", "java.lang.String", REQUEST_SCOPE }, });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		try {
			String limitProfileID = (String) (inputs.get("limitProfileID"));
			ICommonUser user = (ICommonUser) (inputs.get(IGlobalConstant.USER));
			LmtDetailForm lmtDetailForm = (LmtDetailForm) commonForm;
			MILimitUIHelper helper = new MILimitUIHelper();
			String agreementType = (String) (inputs.get("agreementType"));
			if (agreementType.equals("") || agreementType.equals(null)) {
				agreementType = helper.getSBMILmtProxy().getAgreementByAA(limitProfileID);
			}

			lmtDetailForm.setAgreementType(agreementType);
			String facGroup = helper.getSBMILmtProxy().getFacilityGroupByAA(limitProfileID);
			lmtDetailForm.setFacilityGroup(facGroup);
			lmtDetailForm.setFacilityGroupDesc(facGroup);
			lmtDetailForm.setOrigBookingCtry(user.getCountry());
			String defaultCurrency = CurrencyList.getInstance().getCurrencyCodeByCountry(user.getCountry());
			lmtDetailForm.setApprovedCurrency(defaultCurrency);

			if (helper.checkShowSublist(inputs)) {
				lmtDetailForm.setShowSublist("Y");
			}
			else {
				lmtDetailForm.setShowSublist(null);
			}
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