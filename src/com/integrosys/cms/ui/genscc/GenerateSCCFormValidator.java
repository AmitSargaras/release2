package com.integrosys.cms.ui.genscc;

import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class GenerateSCCFormValidator {
	public static ActionErrors validateInput(GenerateSCCForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
        /*
		String cleanActLimit[] = aForm.getCleanActLimit();
		String notCleanActLimit[] = aForm.getNotCleanActLimit();
		String cleanAppLimit[] = aForm.getCleanAppLimit();
		String notCleanAppLimit[] = aForm.getNotCleanAppLimit();
		*/

		String event = aForm.getEvent();

		DefaultLogger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", "Inside Validation");

		  //-------------- Commented off by R1.5-CR146 ---------------
        //String actLimit[] = aForm.getActLimit();
        String distAmt[] = aForm.getDistbursementAmt();
        String enforceAmt[] = aForm.getAmtEnforceTodate();
        String appLimit[] = aForm.getAppLimit();            
        
        if ((distAmt != null && distAmt.length > 0) &&
            (enforceAmt != null && enforceAmt.length > 0)) {
            try {
                for (int i = 0; i < distAmt.length; i++) {
                    //String temp = distAmt[i];
                    String curDistAmt = distAmt[i].trim();
                    //String temp1 = appLimit[i];
                    String curAppLimit = appLimit[i].trim();
                    String curEnforceAmt = enforceAmt[i].trim();

                    if (curDistAmt.equals("")) continue;

                    if (!(errorCode = Validator.checkAmount(curDistAmt, true, 0.0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                        errors.add("distAmt" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0.0", appLimit[i]));
                    } else {
                        // checks that the activated amount does not exceeds the approved amount, only if the user input is a valid amount
                        if (CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curDistAmt).getAmountAsDouble() >
                                CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curAppLimit).getAmountAsDouble()) {
                            errors.add("distAmt" + i, new ActionMessage("error.amount.not.greaterthan", "Disbursement Amount", "Approved Limit"));
                        }
                    }

                    if (!(errorCode = Validator.checkAmount(curEnforceAmt, true, 0.0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                        errors.add("enforceAmt" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0.0", appLimit[i]));
                    } else {
                        // checks that the activated amount does not exceeds the approved amount, only if the user input is a valid amount
                        if (CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curEnforceAmt).getAmountAsDouble() >
                                CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curAppLimit).getAmountAsDouble()) {
                            errors.add("enforceAmt" + i, new ActionMessage("error.amount.not.greaterthan", "Amount Enforce Todate", "Approved Limit"));
                        }
                    }
                }
            }
            catch (Exception e) {
                DefaultLogger.debug("GeneratePSCCFormValidator", "Exception ");
                e.printStackTrace();
            }
        }

        /*
        if (actLimit != null && actLimit.length > 0) {
            for (int i = 0; i < actLimit.length; i++) {
                String temp = actLimit[i];
                String temp1 = appLimit[i];
                try {
                    if (CurrencyManager.convertToAmount(locale, aForm.getActAmtCurrCode()[i], temp).getAmountAsDouble() >
                            CurrencyManager.convertToAmount(locale, aForm.getActAmtCurrCode()[i], temp1).getAmountAsDouble()) {
                        errors.add("actLimit" + i, new ActionMessage("error.amount.not.greaterthan", "Activated limit", "Approved limit"));
                    }
                } catch (Exception e) {
                    DefaultLogger.debug("GenerateSCCFormValidator", "Exception ");
                }

                if (!(errorCode = Validator.checkAmount(temp, true, 0.0,
                        IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
                        IGlobalConstant.DEFAULT_CURRENCY,
                        locale)).equals(Validator.ERROR_NONE)) {
                    errors.add("actLimit" + i, new
                            ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
                            "0.0", appLimit[i]));
                }

            }
        }
        */
        //-------------- End of comment off by R1.5-CR146 ---------------


        /*
		// R1.5-CR146: Validation of clean limits
		if ((cleanActLimit != null) && (cleanActLimit.length > 0)) {
			for (int i = 0; i < cleanActLimit.length; i++) {
				String temp = cleanActLimit[i];
				String temp1 = cleanAppLimit[i];
				try {
					if (CurrencyManager.convertToAmount(locale, aForm.getCleanActAmtCurrCode()[i], temp)
							.getAmountAsDouble() > CurrencyManager.convertToAmount(locale,
							aForm.getCleanActAmtCurrCode()[i], temp1).getAmountAsDouble()) {
						errors.add("cleanActLimit" + i, new ActionMessage("error.amount.not.greaterthan",
								"Activated limit", "Approved limit"));
					}
				}
				catch (Exception e) {
					DefaultLogger.debug("GenerateSCCFormValidator", "Exception ");
				}

				boolean mandatoryChk = (event.equals("submit") || event.equals("total")) ? true : false;
				if (!(errorCode = Validator.checkAmount(temp, mandatoryChk, 0.0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("cleanActLimit" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							errorCode), "0", UIUtil.trimDecimal(cleanAppLimit[i])));
				}

			}
		}
		// R1.5-CR146: Validation of not clean limits
		if ((notCleanActLimit != null) && (notCleanActLimit.length > 0)) {
			for (int i = 0; i < notCleanActLimit.length; i++) {
				String temp = notCleanActLimit[i];
				String temp1 = notCleanAppLimit[i];
				try {
					if (CurrencyManager.convertToAmount(locale, aForm.getNotCleanActAmtCurrCode()[i], temp)
							.getAmountAsDouble() > CurrencyManager.convertToAmount(locale,
							aForm.getNotCleanActAmtCurrCode()[i], temp1).getAmountAsDouble()) {
						errors.add("notCleanActLimit" + i, new ActionMessage("error.amount.not.greaterthan",
								"Activated limit", "Approved limit"));
					}
				}
				catch (Exception e) {
					DefaultLogger.debug("GenerateSCCFormValidator", "Exception ");
				}

				boolean mandatoryChk = (event.equals("submit") || event.equals("total")) ? true : false;
				if (!(errorCode = Validator.checkAmount(temp, mandatoryChk, 0.0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("notCleanActLimit" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							errorCode), "0", UIUtil.trimDecimal(notCleanAppLimit[i])));
				}

			}
		}
        */
		if (event.equals("submit")) { // only check this when event is submit
            /*
			if (!(errorCode = Validator.checkString(aForm.getCreditOfficerName(), true, 1, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("creditOfficerName", new ActionMessage("error.string.mandatory", "1", "50"));
			}
			if (!(errorCode = Validator.checkString(aForm.getCreditOfficerSgnNo(), true, 1, 20))
					.equals(Validator.ERROR_NONE)) {
				errors.add("creditOfficerSgnNo", new ActionMessage("error.string.mandatory", "1", "20"));
			}
			*/
		}

		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
