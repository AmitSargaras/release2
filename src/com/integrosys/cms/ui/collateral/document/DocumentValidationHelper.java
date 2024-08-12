/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/DocumentValidationHelper.java,v 1.9 2005/10/04 08:28:31 vishal Exp $
 */

package com.integrosys.cms.ui.collateral.document;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: vishal $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/10/04 08:28:31 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class DocumentValidationHelper extends CollateralValidator {

	public static ActionErrors validateInput(DocumentForm aForm, Locale locale, ActionErrors errors) {
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		String errorCode = null;
		if (aForm.getEvent().equals("update") || aForm.getEvent().equals("submit")) {
			boolean isMandatory = false;
			if ("submit".equals(aForm.getEvent())) {
				isMandatory = true;
			}
			if (!(errorCode = Validator.checkDate(aForm.getDocumentDate(), isMandatory, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("documentDate", new ActionMessage("error.date.mandatory", "1", "256"));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.document.DocumentValidator",
						"============= aForm.getDocumentDate() ============> " + aForm.getDocumentDate());
			}

			if (!(errorCode = Validator.checkString(aForm.getChargeType(), isMandatory, 1, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("chargeType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						10 + ""));
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getNumberLetter(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("numberLetter", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					100 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getDescription(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("description", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					250 + ""));
		}

		if (aForm.getMinAmount() != null) {
			if (!(errorCode = Validator.checkAmount(aForm.getMinAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("minAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
						maximumAmt));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.CollateralValidator",
						"============= aForm.getMinAmount() ==========");
			}
		}
		if (aForm.getMaxAmount() != null) {
			if (!(errorCode = Validator.checkAmount(aForm.getMaxAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("maxAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
						maximumAmt));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.DocumentValidationHelper",
						"============= aForm.getMaxAmount() ==========");
			}
		}
		if (aForm.getAmountLetter() != null) {
			if (!(errorCode = Validator.checkAmount(aForm.getAmountLetter(), true, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("amountLetter", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
						maximumAmt));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.DocumentValidationHelper",
						"============= aForm.getAmountLetter() ==========");
			}
		}
		boolean isMandatory = ((aForm.getNonStdFreqUnit() != null) && (aForm.getNonStdFreqUnit().length() > 0));
		if (!(errorCode = Validator.checkNumber(aForm.getNonStdFreq(), isMandatory, 0, 999))
				.equals(Validator.ERROR_NONE)) {
			errors.add("nonStdFreq",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "999"));
		}
		isMandatory = ((aForm.getNonStdFreq() != null) && (aForm.getNonStdFreq().length() > 0));
		if (!(errorCode = Validator.checkString(aForm.getNonStdFreqUnit(), isMandatory, 0, 3))
				.equals(Validator.ERROR_NONE)) {
			errors.add("nonStdFreqUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"3"));
		}

/*		if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralMaturityDate", new ActionMessage("error.date.mandatory", "1", "256"));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.DocumentValidationHelper",
					" aForm.getCollateralMaturityDate()= " + aForm.getCollateralMaturityDate());
		}*/

		return errors;

	}
}
