package com.integrosys.cms.ui.genccc;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class GenerateCCCFormValidator {
	public static ActionErrors validateInput(GenerateCCCForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String actLimit[] = aForm.getActLimit();
		String appLimit[] = aForm.getAppLimit();
		String errorCode = "";

		if ((actLimit != null) && (actLimit.length > 0)) {
			for (int i = 0; i < actLimit.length; i++) {
				String temp = actLimit[i];
				String temp1 = appLimit[i];
				try {
					if (CurrencyManager.convertToAmount(locale, aForm.getActAmtCurrCode()[i], temp).getAmountAsDouble() > CurrencyManager
							.convertToAmount(locale, aForm.getActAmtCurrCode()[i], temp1).getAmountAsDouble()) {
						errors.add("actLimit" + i, new ActionMessage("error.amount.not.greaterthan", "Activated limit",
								"Approved limit"));
					}
				}
				catch (Exception e) {
					DefaultLogger.debug("GenerateCCCFormValidator", "Exception ");
				}

				if (!(errorCode = Validator.checkAmount(temp, true, 0.0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
						IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("actLimit" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"0.0", appLimit[i]));
				}
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getCreditOfficerName(), true, 1, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("creditOfficerName", new ActionMessage("error.string.mandatory", "1", "50"));
		}
		if (!(errorCode = Validator.checkString(aForm.getCreditOfficerSgnNo(), true, 1, 20))
				.equals(Validator.ERROR_NONE)) {
			errors.add("creditOfficerSgnNo", new ActionMessage("error.string.mandatory", "1", "20"));
		}

		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
