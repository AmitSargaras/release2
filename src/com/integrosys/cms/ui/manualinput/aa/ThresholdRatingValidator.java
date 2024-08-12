/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Validation for Threshold Rating Description:
 * Validate the value that user key in
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class ThresholdRatingValidator {

	/**
	 * Description : Check the input value
	 * 
	 * @param form is of type CommonForm
	 * @param locale of type Locale
	 * @return errors with error messages
	 */

	public static ActionErrors validateInput(ThresholdRatingForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String event = aForm.getEvent();

		try {

			if ("prepare_add_threshold_new_confirm".equals(event)
					|| "prepare_update_threshold_new_confirm".equals(event)
					|| "prepare_add_threshold_update_confirm".equals(event)
					|| "prepare_update_threshold_update_confirm".equals(event)) {

				String creditRatingType = aForm.getCreditRatingType();
				String creditRating = aForm.getCreditRating();
				String thresholdCurCode = aForm.getThresholdCurCode();
				String thresholdAmt = aForm.getThresholdAmt();
				String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4).toString();

				if ((creditRatingType == null) || creditRatingType.equals("")) {
					errors.add("creditRatingType", new ActionMessage("error.string.mandatory"));
				}

				if ((creditRating == null) || creditRating.equals("")) {
					errors.add("creditRating", new ActionMessage("error.string.mandatory"));
				}

				if ((thresholdCurCode == null) || thresholdCurCode.equals("")) {
					errors.add("thresholdAmt", new ActionMessage("error.string.mandatory"));
				}
				else {
					if ((thresholdAmt == null) || thresholdAmt.equals("")) {
						errors.add("thresholdAmt", new ActionMessage("error.string.mandatory"));
					}
					else {
						if (!(errorCode = Validator.checkNumber(thresholdAmt, true, 0,
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
							if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
								errors.add("thresholdAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", maximumAmt));

							}
							else if (errorCode.equals("decimalexceeded")) {
								errors.add("thresholdAmt", new ActionMessage("error.number.moredecimalexceeded", "",
										"", "4"));

							}
							else {
								errors.add("thresholdAmt", new ActionMessage("error.number." + errorCode));
							}
							DefaultLogger.debug("com.integrosys.cms.ui.manualinput.agreement.ThresholdRatingValidator",
									"============= thresholdAmt ==========");
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
