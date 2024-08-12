/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing.advspayment;

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
public class AdvanceValidator {

	public static ActionErrors validateInput(AdvsPaymentForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode;

		try {

			boolean isSubmit = false;
			if (AdvsPaymentAction.EVENT_CREATE.equals(form.getEvent())
					|| AdvsPaymentAction.EVENT_UPDATE.equals(form.getEvent())) {
				isSubmit = true;
			}

			if (!(errorCode = Validator.checkString(form.getReferenceNo(), isSubmit, 1, 30))
					.equals(Validator.ERROR_NONE)) {
				errors.add("referenceNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"30"));
			}
			if (!(errorCode = Validator.checkDate(form.getDrawdownDate(), isSubmit, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("drawdownDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkInteger(form.getTenorPeriod(), isSubmit, 1, 999))
					.equals(Validator.ERROR_NONE)) {
				errors.add("tenorPeriod", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "1",
						"999"));
			}
			if (!(errorCode = Validator.checkString(form.getTenorUom(), isSubmit, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("tenorUom",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "3"));
			}
			if (!(errorCode = Validator.checkString(form.getClaimCurrency(), isSubmit, 1, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("claimCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", "3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getClaimAmount(), isSubmit, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("claimAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errorCode = Validator.checkDate(form.getExpiryDate(), isSubmit, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("expiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}

			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}