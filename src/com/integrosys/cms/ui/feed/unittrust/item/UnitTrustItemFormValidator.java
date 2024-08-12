package com.integrosys.cms.ui.feed.unittrust.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.feed.FeedUIValidator;

/**
 * This class implements validation
 */
public class UnitTrustItemFormValidator implements java.io.Serializable {

	private static String LOGOBJ = UnitTrustItemFormValidator.class.getName();

	public static ActionErrors validateInput(UnitTrustItemForm form, Locale locale) {

		// Only for save event.

		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		// Assume currency code valid.

		String name = form.getName();
		String isinCode = form.getIsinCode();
		String fundCode = form.getFundCode();
		String currencyCode = form.getCurrencyCode();
		String ric = form.getRic();
		String unitPrice = form.getUnitPrice();
		String productCode = form.getProductCode();
		String fundManagerCode = form.getFundManagerCode();
		String fundManagerName = form.getFundManagerName();
		String fundSize = form.getFundSize();
		String fundSizeUpdateDate = form.getFundSizeUpdateDate();
		String dateLaunched = form.getDateLaunched();

		if (!(errorCode = Validator.checkString(currencyCode, true, 1, 3)).equals(Validator.ERROR_NONE)) {
			errors.add("currencyCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
					new Integer(3)));
		}
		
		if (!(errorCode = Validator.checkString(ric, true, 1, 21)).equals(Validator.ERROR_NONE)) {
			errors.add("ric", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
					new Integer(21)));
		}

		if (!(errorCode = Validator.checkString(name, true, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("name", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
					new Integer(50)));
		}

		if (!(errorCode = Validator.checkString(isinCode, false, 1, 12)).equals(Validator.ERROR_NONE)) {
			errors.add("isinCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(
					1), new Integer(12)));
		}

		/*
		 * if (!(errorCode = Validator.checkString(fundCode, true, 1,
		 * 12)).equals(Validator.ERROR_NONE)) { errors.add("fundCode", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * new Integer(1), new Integer(12))); }
		 */

		FeedUIValidator.validateUnitPrices(unitPrice, errors);
		FeedUIValidator.validateFundSize(fundSize, errors, locale);

		/*
		 * if (!(errorCode = Validator.checkNumber(unitPrice, false, 0,
		 * 10000.00, 3, locale)).equals(Validator.ERROR_NONE)) { if
		 * (!errorCode.equals("decimalexceeded")) { errors.add("unitPrice", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", "10000.00")); } else if (errorCode.equals("decimalexceeded")) {
		 * errors.add("unitPrice", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
		 * "moredecimalexceeded"), "0", "10000.00", "2")); } }
		 */

		if (!(errorCode = Validator.checkString(productCode, false, 1, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("productCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					new Integer(1), new Integer(20)));
		}

		if (!(errorCode = Validator.checkString(fundManagerCode, false, 1, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("fundManagerCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					new Integer(1), new Integer(20)));
		}

		if (!(errorCode = Validator.checkString(fundManagerName, false, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("fundManagerName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					new Integer(1), new Integer(50)));
		}

		DefaultLogger.debug(LOGOBJ, "errors" + errors.size());

		return errors;
	}
	// end validateInput

}
