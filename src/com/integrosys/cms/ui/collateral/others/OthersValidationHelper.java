/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/others/OthersValidationHelper.java
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.others;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.collateral.ManualValuationValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: visveswari $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/02 06:24:49 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class OthersValidationHelper extends CollateralValidator {

	public static ActionErrors validateInput(OthersForm aForm, Locale locale, ActionErrors errors) {

		String errorCode = null;
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		if (aForm.getEvent().equals("submit") || aForm.getEvent().equals("update")) {
			boolean isMandatory = false;
			if ("submit".equals(aForm.getEvent())) {
				isMandatory = true;
			}

			errors = ManualValuationValidator.validateInput(aForm, locale, errors);

/*			if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), isMandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("collateralMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
						errorCode), "1", 250 + ""));
			}*/

			/*if (!(errorCode = Validator.checkString(aForm.getDescription(), isMandatory, 1, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("description", new ActionMessage("error.string.mandatory", "1", "3"));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.others.OthersValidationHelper",
						"======== aForm.getDescription() - aForm.getDescription() =======>");
			}*/

			/*if (!(errorCode = Validator.checkString(aForm.getSecEnvRisky(), isMandatory, 0, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("secEnvRisky", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						50 + ""));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.others.OthersValidationHelper",
						"======== aForm.getSecEnvRisky() - aForm.getSecEnvRisky() =======>");
			}*/

			if(aForm.getEvent() != null && !(aForm.getEvent().equals("")) && aForm.getEvent().equals("submit")){
				if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50))
						.equals(Validator.ERROR_NONE)) {
			errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
				}
			if (isMandatory
					&& !(errorCode = Validator.checkNumber(aForm.getUnitsNumber(), false, 0,
							IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("unitsNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						maximumAmt));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.others.OthersValidationHelper",
						"======== aForm.getUnitsNumber() - aForm.getUnitsNumber() =======>");
			}
		}

		return errors;

	}
}
