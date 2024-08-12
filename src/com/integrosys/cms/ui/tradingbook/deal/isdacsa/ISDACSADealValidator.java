/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.isdacsa;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Validation for ISDA CSA Deal Description:
 * Validate the value that user key in
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class ISDACSADealValidator {

	/**
	 * Description : Check the input value
	 * 
	 * @param form is of type CommonForm
	 * @param locale of type Locale
	 * @return errors with error messages
	 */

	public static ActionErrors validateInput(ISDACSADealForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String event = aForm.getEvent();

		try {

			String[] marketValues = aForm.getMarketValue();
			String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4).toString();
			String minimumAmt = new BigDecimal(IGlobalConstant.MINIMUM_ALLOWED_AMOUNT_15_4).toString();
			for (int i = 0; i < marketValues.length; i++) {

				String marketValue = marketValues[i];
				String propertyName = "marketValue" + i;

				if (!(errorCode = Validator.checkNumber(marketValue, true, IGlobalConstant.MINIMUM_ALLOWED_AMOUNT_15_4,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
					if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
						errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
								"heightlessthan"), minimumAmt, maximumAmt));
					}
					else if (errorCode.equals("decimalexceeded")) {
						errors.add(propertyName, new ActionMessage("error.number.moredecimalexceeded", "", "", "4"));
					}
					else {
						if ("paginate".equals(event) && !errorCode.equals("mandatory")) {
							errors.add(propertyName, new ActionMessage("error.number." + errorCode));
						}
						else if (!"paginate".equals(event)) {
							errors.add(propertyName, new ActionMessage("error.number." + errorCode));
						}
					}
					DefaultLogger.debug("com.integrosys.cms.ui.tradingbook.deal.ISDACSADealValidator",
							"============= marketValue ==========");
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
