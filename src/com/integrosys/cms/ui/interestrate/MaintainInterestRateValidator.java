/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/interestrate/MaintainInterestRateValidator.java,v 1 2007/02/08 Jerlin Exp $
 */
package com.integrosys.cms.ui.interestrate;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Describe this class. Purpose: Validation for Interest Rate Description:
 * Validate the percentage that user key in
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class MaintainInterestRateValidator {

	/**
	 * Description : Check the input value
	 * 
	 * @param form is of type CommonForm
	 * @param locale of type Locale
	 * @return errors with error messages
	 */

	public static ActionErrors validateInput(MaintainInterestRateForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String event = aForm.getEvent();

		try {

			if (MaintainInterestRateAction.EVENT_LIST.equals(event) || "checker_view".equals(event)) {
				String intRateType = aForm.getTypeInterestRates();
				String intRateMthYr = aForm.getMonthYear();

				if ((intRateType == null) || intRateType.equals("")) {
					errors.add("typeIntRates", new ActionMessage("error.intratestype.mandatory"));
					aForm.setMonthYear(intRateMthYr);
				}

				if ((intRateMthYr == null) || intRateMthYr.equals("")) {
					errors.add("mthYear", new ActionMessage("error.intratesmonthyear.mandatory"));
					aForm.setTypeInterestRates(intRateType);
				}

			}
			else if ("maker_edit_interestrate_confirm".equals(event)) {
				String[] intRatePercents = aForm.getIntRatePercent();
				if ((intRatePercents == null) || (intRatePercents.length == 0)) {

				}
				else {
					for (int i = 0; i < intRatePercents.length; i++) {

						String intRatePercent = intRatePercents[i];
						String propertyName = "intRatePercent" + i;
						String errMsg = Validator.checkNumber(intRatePercent, true, 0, 100, 6, locale);
						DefaultLogger.debug(
								">>>>>>>>>>>>>>>>>>>>>>>>> errMessage in MaintainInterestRateValidator is ", ""
										+ errMsg);

						if (!errMsg.equals(Validator.ERROR_NONE)) {
							DefaultLogger.debug("2 errMessage is ", "" + errMsg);

							if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
								errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", "100"));

							}
							else if (errMsg.equals("decimalexceeded")) {
								errors.add(propertyName, new ActionMessage("error.number.moredecimalexceeded", "", "",
										"4"));

							}
							else if (!errMsg.equals("mandatory")) {
								errors.add(propertyName, new ActionMessage("error.number." + errMsg));
							}
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
