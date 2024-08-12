/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Validation for GMRA Deal Valuation Description:
 * Validate the value that user key in
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class GMRADealValValidator {

	/**
	 * Description : Check the input value
	 * 
	 * @param form is of type CommonForm
	 * @param locale of type Locale
	 * @return errors with error messages
	 */

	public static ActionErrors validateInput(GMRADealValForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String event = aForm.getEvent();

		try {

			if ("maker_update_valuation_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)
					|| "paginate".equals(event)) {

				String[] marketPrices = aForm.getMarketPrice();
				String[] marketPricesCurCode = aForm.getMarketPriceCurCode();
				String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4).toString();

				for (int i = 0; i < marketPrices.length; i++) {
					String marketPrice = marketPrices[i];
					String propertyName = "marketPrice" + i;

					if (!(errorCode = Validator.checkNumber(marketPrice, true, 0,
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
						if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
							errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
									"heightlessthan"), "0", maximumAmt));
						}
						else if (errorCode.equals("decimalexceeded")) {
							errors
									.add(propertyName, new ActionMessage("error.number.moredecimalexceeded", "", "",
											"4"));
						}
						else {
							if ("paginate".equals(event) && !errorCode.equals("mandatory")) {
								errors.add(propertyName, new ActionMessage("error.number." + errorCode));
							}
							else if (!"paginate".equals(event)) {
								errors.add(propertyName, new ActionMessage("error.number." + errorCode));
							}
						}
						DefaultLogger.debug("com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealValValidator",
								"============= marketPrice ==========");
					}
				}

				for (int i = 0; i < marketPricesCurCode.length; i++) {
					String marketPriceCurCode = marketPricesCurCode[i];
					String propertyName = "marketPrice" + i;
					if (!(errorCode = Validator.checkString(marketPriceCurCode, false, 0, 250))
							.equals(Validator.ERROR_NONE)) {
						errors.add(propertyName, new ActionMessage(
								ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
						DefaultLogger.debug("com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealValValidator",
								"============= marketPriceCurCode ==========");
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
