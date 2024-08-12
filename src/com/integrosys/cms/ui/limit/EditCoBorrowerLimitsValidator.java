package com.integrosys.cms.ui.limit;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

public class EditCoBorrowerLimitsValidator {

	/**
	 * validate input
	 * @param aForm
	 * @param locale
	 * @return
	 */
	public static ActionErrors validateInput(ViewLimitsForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String[] customerIDs = aForm.getCustomerIDs();
		String[] limitIDs = aForm.getLimitIDs();
		String[] actLimitAmts = aForm.getActivatedLimits();
		String[] reasons = aForm.getReasons();
		String[] limitsZerorised = aForm.getLimitZerorised();
		String[] zerorisedDates = aForm.getDateOfZerorisations();
		for (int x = 0; x < customerIDs.length; x++) {
			if (limitsZerorised != null) {
				for (int i = 0; i < limitsZerorised.length; i++) {
					if (customerIDs[x].equals(limitsZerorised[i])) {
						String zerorisedDate = zerorisedDates[x];
						String reason = reasons[x];
						// check zerorised date
						if (!(errorCode = Validator.checkDate(zerorisedDate, true, locale))
								.equals(Validator.ERROR_NONE)) {
							errors.add("zerorisedDateError" + x, new ActionMessage(ErrorKeyMapper.map(
									ErrorKeyMapper.DATE, errorCode)));
						}
						// check zerorised reasons
						if (!(errorCode = Validator.checkString(reason, true, 0, 250)).equals(Validator.ERROR_NONE)) {
							errors.add("reasonError" + x, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "0", "250"));
						}
					}
				}
			}
			else {
				String reason = reasons[x];
				// check zerorised reasons
				if (!reason.equals("")
						&& !(errorCode = Validator.checkString(reason, true, 0, 250)).equals(Validator.ERROR_NONE)) {
					errors.add("reasonError" + x, new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.STRING, errorCode), "0", "250"));
				}
			}
		}
		// check activated amount
		for (int y = 0; y < limitIDs.length; y++) {
			String actLimitAmt = actLimitAmts[y];
			if (!actLimitAmt.equals("")
					&& !(errorCode = Validator.checkNumber(actLimitAmt, true, 0.0, 999999999))
							.equals(Validator.ERROR_NONE)) {
				errors.add("amountFormatError" + y, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
						errorCode), "0", String.valueOf(999999999)));
			}
		}
		return errors;
	}
}
