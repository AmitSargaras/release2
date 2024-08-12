/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/Mar/09 $ Tag: $Name: $
 */
public class FacilityTypeValidator {

	public static ActionErrors validateInput(com.integrosys.cms.ui.contractfinancing.FacilityTypeForm form,
			Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode;

		try {
			if (FacilityTypeAction.EVENT_CREATE.equals(form.getEvent())) {
				if (!(errorCode = Validator.checkString(form.getFacilityType(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("facilityType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"1", "10"));
				}

				if (form.getFacilityType().equals("OTH")) {
					if (!(errorCode = Validator.checkString(form.getFacilityOthers(), true, 1, 50))
							.equals(Validator.ERROR_NONE)) {
						errors.add("facilityOthers", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
								errorCode), "1", "50"));
					}
				}
			}
			if (!(errorCode = Validator.checkDate(form.getFacilityDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("facilityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkInteger(form.getMoa(), true, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("moa", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "0", "100"));
			}
			if (!(errorCode = Validator.checkString(form.getMaxCapCurrency(), false, 0, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("maxCapCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", "3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getMaxCapAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("maxCapAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errorCode = Validator.checkString(form.getRemarks(), false, 0, 500)).equals(Validator.ERROR_NONE)) {
				errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"500"));
			}

			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}