package com.integrosys.cms.ui.collateral.cash;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.NumberUtils;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 11:27:46 AM
 * To change this template use Options | File Templates.
 */
public class DepositValidator {
	public static ActionErrors validateInput(DepositForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (aForm.getDepMatDate().length() > 0) {
			DefaultLogger.debug(DepositValidator.class, "depMatDate is not null");
			if (!(errorCode = Validator.checkDate(aForm.getDepMatDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("depMatDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1",
						"10"));
			}
		}

		Number depositAmountValue = NumberUtils.parseNumber(aForm.getDepAmt(), locale);

		if (!(errorCode = Validator.checkNumber(aForm.getDepAmt(), true, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
				3, locale)).equals(Validator.ERROR_NONE)) {
			if (Validator.ERROR_DECIMAL_EXCEEDED.equals(errorCode)) {
				errorCode = "moredecimalexceeded";
			}
			errors.add("depAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), null, null,
					"2"));
		}
		else if ((depositAmountValue != null) && (depositAmountValue.intValue() == 0)) {
			errors.add("depAmt", new ActionMessage("error.number.must.morethan", "0"));
		}

		if (!(errorCode = Validator.checkString(aForm.getDepCurr(), true, 1, 3)).equals(Validator.ERROR_NONE)) {
			errors.add("depCurr", new ActionMessage("error.string.mandatory", "1", "3"));
		}

		return errors;
	}

}
