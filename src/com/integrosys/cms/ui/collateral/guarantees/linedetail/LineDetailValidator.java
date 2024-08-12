package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class LineDetailValidator {

	public static ActionErrors validateInput(LineDetailForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = null;
		
		boolean isFacDetailMandatory = ICMSConstant.YES.equals(form.getFacDetailMandatory());
		
		if(isFacDetailMandatory && AbstractCommonMapper.isEmptyOrNull(form.getFacilityName())) {
			errors.add("facilityName", new ActionMessage("error.string.mandatory"));
		}
		if(isFacDetailMandatory && AbstractCommonMapper.isEmptyOrNull(form.getFacilityID())) {
			errors.add("facilityID", new ActionMessage("error.string.mandatory"));
		}
		if(AbstractCommonMapper.isEmptyOrNull(form.getLineNo())) {
			errors.add("lineNo", new ActionMessage("error.string.mandatory"));
		}
		
		if (!(errorCode = Validator.checkNumber(form.getSerialNo(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10)).equals(Validator.ERROR_NONE)) {
			errors.add("serialNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
		}
		
		if (!(errorCode = Validator.checkNumber(form.getLcnNo(), false, 0, 99, 0, locale)).equals(Validator.ERROR_NONE)) {
			if("decimalexceeded".equals(errorCode)) {
				errors.add("lcnNo", new ActionMessage("error.string.nodecimal"));
			}
			else {
				errors.add("lcnNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", "99"));
			}
		}
		
		if (!(errorCode = Validator.checkNumber(form.getLineLevelSecurityOMV(), false, 0,
				ILineDetailConstants.MAXIMUM_ALLOWED_AMOUNT_22_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("lineLevelSecurityOMV",new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0,
					ILineDetailConstants.MAXIMUM_ALLOWED_AMOUNT_22_2_STR));
		}
		else {
			errorCode = UIUtil.compareExponentialValue(form.getLineLevelSecurityOMV(), ILineDetailConstants.MAXIMUM_ALLOWED_AMOUNT_22_2_STR);
			if(!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("lineLevelSecurityOMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", ILineDetailConstants.MAXIMUM_ALLOWED_AMOUNT_22_2_STR));
			}
		}
		
		DefaultLogger.warn(LineDetailValidator.class.getName() ,"  errors " + errors.size());
		return errors;
	}
	
}
