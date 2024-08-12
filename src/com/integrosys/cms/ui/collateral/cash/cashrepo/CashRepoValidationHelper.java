package com.integrosys.cms.ui.collateral.cash.cashrepo;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/*
 * Created by IntelliJ IDEA.
 * User: Naveen
 * Date: Feb 20, 2007
 * Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class CashRepoValidationHelper {
	public static ActionErrors validateInput(CashRepoForm aForm, Locale locale, ActionErrors errors) {
		String errorCode;
		/*
		 * if (aForm.getEvent().equals("submit") ||
		 * aForm.getEvent().equals("update")) { if (!(errorCode =
		 * Validator.checkString(aForm.getValCurrency(), true, 1,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("valCurrency", new
		 * ActionMessage("error.string.mandatory", "1", "3"));
		 * DefaultLogger.debug(
		 * "com.integrosys.cms.ui.collateral.cash,cashrepo.CashRepoValidationHelper"
		 * ,
		 * "============= aForm.valBefMargin - aForm.valCurrency() ==========>"
		 * ); } }
		 */

		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		if (aForm.getEvent().equals("submit") || aForm.getEvent().equals("update")) {
			if (!(errorCode = Validator.checkAmount(aForm.getMinimalFSV(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("minimalFSV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						maximumAmt));
			}
		}

		return errors;

	}
}
