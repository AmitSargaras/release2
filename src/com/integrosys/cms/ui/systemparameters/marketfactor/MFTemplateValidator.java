/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MFTemplateValidator {

	public static ActionErrors validateMFTemplate(ActionForm aForm, Locale locale) {

		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		Date currDate = DateUtil.getDate();
		try {
			MFTemplateForm lmtForm = (MFTemplateForm) aForm;

			if (!(errorCode = Validator.checkString(lmtForm.getMFTemplateName(), true, 0, 150))
					.equals(Validator.ERROR_NONE)) {
				errors.add("MFTemplateName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", "150"));
			}
			String value = lmtForm.getMFTemplateStatus();
			if ((value == null) || value.equals("")) {
				errors.add("MFTemplateStatus", new ActionMessage("error.string.mandatory"));
			}
			String[] list = lmtForm.getSecSubType();
			if ((list == null) || (list.length == 0)) {
				errors.add("secSubType", new ActionMessage("error.string.mandatory"));
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return errors;
	}

	public static ActionErrors validateMFItem(ActionForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		try {
			MFItemForm itemForm = (MFItemForm) aForm;

			if (!(errorCode = Validator.checkString(itemForm.getFactorDescription(), true, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("factorDescription", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", "250"));
			}

			if (AbstractCommonMapper.isEmptyOrNull(itemForm.getWeightPercentage())) {
				errors.add("weightPercentage", new ActionMessage("error.string.mandatory"));
			}
			else {
				String value = itemForm.getWeightPercentage();
				if (!(errorCode = Validator.checkNumber(value, true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3_2, 3,
						locale)).equals(Validator.ERROR_NONE)) {
					if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
						errors.add("weightPercentage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
								"heightlessthan"), "1", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3_2_STR));

					}
					else if (errorCode.equals("decimalexceeded")) {
						errors.add("weightPercentage", new ActionMessage("error.number.moredecimalexceeded", "", "",
								"2"));

					}
					else if (!errorCode.equals("mandatory")) {
						errors.add("weightPercentage", new ActionMessage("error.number." + errorCode));
					}
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return errors;
	}

}
