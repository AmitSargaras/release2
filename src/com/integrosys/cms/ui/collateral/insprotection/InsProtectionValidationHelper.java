/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/InsProtectionValidationHelper.java,v 1.4 2003/09/17 10:38:31 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.insprotection;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/17 10:38:31 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class InsProtectionValidationHelper extends CollateralValidator {

	public static ActionErrors validateInput(InsProtectionForm aForm, Locale locale, ActionErrors errors) {
		String errorCode = null;
		if (aForm.getEvent().equals("submit") || aForm.getEvent().equals("update")) {

			if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("collateralMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
						errorCode), "0", 256 + ""));
				// DefaultLogger.debug(LOGOBJ,
				// "aForm.getCollateralMaturityDate(): " +
				// aForm.getCollateralMaturityDate());
			}
/*	Already checked in the Collateral Validator		
 * 			if (!(errorCode = Validator.checkString(aForm.getLe(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("le", new ActionMessage("error.string.mandatory", "1", "10"));
				// DefaultLogger.debug(LOGOBJ, "getLe(): " + aForm.getLe());
			}*/
  
			
	/*		if (!(errorCode = Validator.checkNumber(aForm.getAmtCharge(), false, 1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_11_2, 3, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("amtCharge", new ActionMessage("error.number.moredecimalexceeded", "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_11_2_STR,
						"2"));
			}   */
			
			
			
			
			
			
		}
		
		/*if (!(errorCode = Validator.checkAmount(aForm.getPremiumAmount(), false, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode);
			errors.add("insuredAmt", new ActionMessage(errorMessage, "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR));
		}*/
		
		if (!(errorCode = Validator.checkNumber(aForm.getPremiumAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals("error.number.decimalexceeded")) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("premiumAmount", new ActionMessage(errorMessage, "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR, "2"));
		}

		return errors;
	}
}
