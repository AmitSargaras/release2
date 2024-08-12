/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.cashmargin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Validation for Cash Margin Description:
 * Validate the value that user key in
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class CashMarginValidator {

	/**
	 * Description : Check the input value
	 * 
	 * @param form is of type CommonForm
	 * @param locale of type Locale
	 * @return errors with error messages
	 */

	public static ActionErrors validateInput(CashMarginForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String event = aForm.getEvent();

		try {

			if ("maker_create_cashmargin_confirm".equals(event) || "maker_update_cashmargin_confirm".equals(event)) {
				String trxDate = aForm.getTrxDate();
				String napSignAddValue = aForm.getNapSignAddValue();
				String napValue = aForm.getNapValue();
				String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4).toString();

				if ("maker_create_cashmargin_confirm".equals(event)) {
					if ((trxDate == null) || trxDate.equals("")) {
						errors.add("trxDate", new ActionMessage("error.string.mandatory"));
					}
					else {
						if (!(errorCode = Validator.checkDate(trxDate, false, locale)).equals(Validator.ERROR_NONE)) {
							errors.add("trxDate", new ActionMessage("error.date.mandatory", "1", "256"));
							DefaultLogger.debug("com.integrosys.cms.ui.tradingbook.cashmargin.CashMarginValidator",
									"============= trxDate ==========");
						}
						else {
							Date currentMth = DateUtil.getDate();
							int mth = currentMth.getMonth();
							int yr = currentMth.getYear();
							Date trxD = DateUtil.convertDate(locale, trxDate);
							int inputMth = trxD.getMonth();
							int inputYr = trxD.getYear();
							if ((inputMth != mth) || (inputYr != yr)) {
								errors.add("trxDate", new ActionMessage("error.notequals.currentmth",
										"Transaction Date"));
							}
						}
					}
				}

				if ((napSignAddValue == null) || napSignAddValue.equals("")) {
					errors.add("napValue", new ActionMessage("error.string.mandatory"));
				}
				else {
					if ((napValue == null) || napValue.equals("")) {
						errors.add("nAPValue", new ActionMessage("error.string.mandatory"));
					}
					else {
						if (!(errorCode = Validator.checkNumber(napValue, true, 0,
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
							if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
								errors.add("napValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", maximumAmt));

							}
							else if (errorCode.equals("decimalexceeded")) {
								errors.add("napValue", new ActionMessage("error.number.moredecimalexceeded", "", "",
										"4"));

							}
							else if (!errorCode.equals("mandatory")) {
								errors.add("napValue", new ActionMessage("error.number." + errorCode));
							}
							DefaultLogger.debug("com.integrosys.cms.ui.tradingbook.cashmargin.CashMarginValidator",
									"============= napValue ==========");
						}
					}

				}

			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return errors;

	}

}
