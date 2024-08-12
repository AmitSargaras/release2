package com.integrosys.cms.ui.collateral.insprotection.inscrdtderiv;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.insprotection.InsProtectionValidator;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class InsCrdtDerivValidator {
	public static ActionErrors validateInput(InsCrdtDerivForm aForm, Locale locale) {

		ActionErrors errors = InsProtectionValidator.validateInput(aForm, locale);

		String errorCode = null;
		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			boolean mandatory = "submit".equals(aForm.getEvent());

			if (!(errorCode = Validator.checkString(aForm.getDescription(), false, 1, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("description", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						250 + ""));
			}

			if (!(errorCode = Validator.checkDate(aForm.getDateISDAMasterAgmt(), mandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dateISDAMasterAgmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"1", 256 + ""));
			}

			if (!(errorCode = Validator.checkDate(aForm.getDateTreasury(), mandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dateTreasury", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1",
						256 + ""));
			}

			// in CollateralValidator.java will check amount, here only check
			// mandatory
			if (mandatory && !Validator.validateMandatoryField(aForm.getAmtCharge())) {
				errors.add("amtCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, "mandatory")));
			}

			if (!(errorCode = Validator.checkDate(aForm.getLegalChargeDate(), mandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("legalChargeDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"1", 256 + ""));
			}

			if (!(errorCode = Validator.checkString(aForm.getChargeType(), mandatory, 1, 40))
					.equals(Validator.ERROR_NONE)) {
				errors.add("chargeType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						50 + ""));
			}

			InsCrdtDerivValidationHelper.validateInput(aForm, locale, errors);
		}
		return errors;

	}
}
