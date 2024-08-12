/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/EditLimitsValidator.java,v 1.13 2006/10/04 10:26:34 nkumar Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/10/04 10:26:34 $ Tag: $Name: $
 */

public class EditLimitsValidator {

	/**
	 * validate input
	 * @param aForm
	 * @param locale
	 * @return
	 */
	public static ActionErrors validateInput(ViewLimitsForm aForm, Locale locale) {
		String event = aForm.getEvent();
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String[] limitIDs = aForm.getLimitIDs();
		String[] approvedLimitAmt = aForm.getApprovedLimits();
		String[] approvedCurrencies = aForm.getApprovedCurrencies();
		String[] actLimitAmt = aForm.getActivatedLimits();
		String[] requiredSecurityCoverages = aForm.getRequiredSecurityCoverages();
		String[] coReqSecCoverages = aForm.getCoRequiredSecurityCoverages();
		String reason = aForm.getZerorisedReason();
		String isZerorised = aForm.getIsZerorised();
		String zerorisedDate = aForm.getZerorisedDate();
		String[] isDAP = aForm.getIsDAP();
		DefaultLogger.debug("com.integrosys.cms.ui.limit.EditLimitsValidator",
				"isZerorised: -------------------------------------------- " + isZerorised);
		for (int i = 0; i < limitIDs.length; i++) {
			// CMSSP-791 : Validation performed for Non-editable fields and so
			// Limits submission was not successful
			if (!isDAP[i].equals("Y")) {
				if (LimitsAction.EDIT_LIMITS.equals(event)) {
					// check security coverages
					if (!requiredSecurityCoverages[i].equals("")
							&& !(errorCode = Validator.checkNumber(requiredSecurityCoverages[i], true, 0.0, 999999))
									.equals(Validator.ERROR_NONE)) {
						errors.add("coveragesError" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
								errorCode), "0", "999999"));
					}
				}
				if (LimitsAction.EDIT_CO_BORROWER_LIMITS.equals(event)) {
					// check for coborrower security coverages
					if (!coReqSecCoverages[i].equals("")
							&& !(errorCode = Validator.checkNumber(coReqSecCoverages[i], true, 0.0, 999999))
									.equals(Validator.ERROR_NONE)) {
						errors.add("secCoveragesError" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
								errorCode), "0", "999999"));
					}
				}
				// check activated amount
				if (!actLimitAmt[i].equals("")
						&& !actLimitAmt[i].equals("-")
						&& !(errorCode = Validator.checkNumber(actLimitAmt[i], true, 0.0,
								IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15)).equals(Validator.ERROR_NONE)) {
					errors.add("amountFormatError" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
				}
				else if (!actLimitAmt[i].equals("") && !actLimitAmt[i].equals("-")
						&& errorCode.equals(Validator.ERROR_NONE)) {
					try {
						Amount approvedAmount = CurrencyManager.convertToAmount(locale, approvedCurrencies[i],
								approvedLimitAmt[i]);
						Amount activatedAmount = CurrencyManager.convertToAmount(locale, approvedCurrencies[i],
								actLimitAmt[i]);
						if (approvedAmount.compareTo(activatedAmount) < 0) {
							errors.add("amountCompareError" + i, new ActionMessage("error.activated.more.than.approved"));
						}
					}
					catch (Exception e) {
						DefaultLogger.debug("com.integrosys.cms.ui.limit.EditLimitsValidator", e);
					}
				}
			}
		}

		// check zerorised date
		if (isZerorised != null) {
			if (!(errorCode = Validator.checkDate(zerorisedDate, true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("zerorisedDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			// check zerorised reasons
			if (!(errorCode = Validator.checkString(reason, true, 0, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("reasonError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"250"));
			}
		}
		else {
			// check zerorised reasons
			if (!reason.equals("")
					&& !(errorCode = Validator.checkString(reason, true, 0, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("reasonError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"250"));
			}
		}
		return errors;
	}
}
