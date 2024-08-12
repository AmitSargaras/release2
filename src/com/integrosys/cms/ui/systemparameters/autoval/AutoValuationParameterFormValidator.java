package com.integrosys.cms.ui.systemparameters.autoval;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Validation for Auto Valuation Parameters
 * Description: Validate the value that user key in
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class AutoValuationParameterFormValidator {
	public static ActionErrors validateInput(AutoValuationParameterForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(AutoValuationParameterFormValidator.class,
				"lolcale from common action ..,.,.,..>>>>>>>>>>>>>>>" + locale);
		String errorCode = "";

		if ((aForm.getSelectedSecuritySubType() == null) || (aForm.getSelectedSecuritySubType().length <= 0)) {
			errors.add("securitySubTypeError", new ActionMessage("error.auto.param.securitySubTypeList"));
		}

		if (!(errorCode = Validator.checkString(aForm.getCountry(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("countryError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					"10"));
		}

		if ((aForm.getSelectedState() == null) || (aForm.getSelectedState().length <= 0)) {
			errors.add("stateError", new ActionMessage("error.auto.param.stateList"));
		}

		if ((aForm.getSelectedDistrict() == null) || (aForm.getSelectedDistrict().length <= 0)) {
			errors.add("districtError", new ActionMessage("error.auto.param.districtList"));
		}

		if ((aForm.getSelectedMukim() == null) || (aForm.getSelectedMukim().length <= 0)) {
			errors.add("mukimError", new ActionMessage("error.auto.param.mukimList"));
		}

		if (!(errorCode = Validator.checkInteger(aForm.getPostCode(), false, 0, 999999999))
				.equals(Validator.ERROR_NONE)) {
			errors.add("postCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "0",
					"999999999"));
		}

		if ((aForm.getFromLandArea() != null) && !("").equals(aForm.getFromLandArea())) {
			if (!(errorCode = Validator.checkInteger(aForm.getFromLandArea(), false, 0, 999999999))
					.equals(Validator.ERROR_NONE)) {
				errors.add("landAreaError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
						"0", "999999999"));
			}
			else {
				if (!(errorCode = Validator.checkInteger(aForm.getToLandArea(), true, 0, 999999999))
						.equals(Validator.ERROR_NONE)) {
					errors.add("landAreaError", new ActionMessage(
							ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "0", "999999999"));
				}

				if ((aForm.getFromLandAreaMeasure() == null) || ("").equals(aForm.getFromLandAreaMeasure())
						|| (aForm.getToLandAreaMeasure() == null) || ("").equals(aForm.getToLandAreaMeasure())) {
					errors.add("landAreaMeasureError", new ActionMessage("error.auto.param.landarea.measure"));
				}
			}
		}

		if ((aForm.getFromBuiltUpArea() != null) && !("").equals(aForm.getFromBuiltUpArea())) {
			if (!(errorCode = Validator.checkInteger(aForm.getFromBuiltUpArea(), false, 0, 999999999))
					.equals(Validator.ERROR_NONE)) {
				errors.add("builtUpAreaError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
						"0", "999999999"));
			}
			else {
				if (!(errorCode = Validator.checkInteger(aForm.getToBuiltUpArea(), true, 0, 999999999))
						.equals(Validator.ERROR_NONE)) {
					errors.add("builtUpAreaError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER,
							errorCode), "0", "999999999"));
				}

				if ((aForm.getFromBuiltUpAreaMeasure() == null) || ("").equals(aForm.getFromBuiltUpAreaMeasure())
						|| (aForm.getToBuiltUpAreaMeasure() == null) || ("").equals(aForm.getToBuiltUpAreaMeasure())) {
					errors.add("builtUpAreaMeasureError", new ActionMessage("error.auto.param.builtup.area.measure"));
				}
			}
		}

		if (!(errorCode = Validator.checkNumber(aForm.getMinCurrentOMV(), true, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("minCurrentOMVError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
						"moredecimalexceeded"), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR, "2"));
			}
			else {
				errors.add("minCurrentOMVError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
			}
		}

		if ((aForm.getOmvValue() != null) && !("").equals(aForm.getOmvValue())) {
			if (!(errorCode = Validator.checkInteger(aForm.getOmvValue(), false, 0, 999999999))
					.equals(Validator.ERROR_NONE)) {
				errors.add("omvValueError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
						"0", "999999999"));
			}
			else {
				if (!(errorCode = Validator.checkString(aForm.getOmvIndicator(), true, 1, 8))
						.equals(Validator.ERROR_NONE)) {
					errors.add("omvIndicatorError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
							errorCode), "1", "8"));
				}
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getValuationDescription(), false, 1, 200))
				.equals(Validator.ERROR_NONE)) {
			errors.add("valuationDescriptionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
					errorCode), "1", "200"));
		}

		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
