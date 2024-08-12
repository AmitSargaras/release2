/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/guarantees/gtegovtlink/GteGovtLinkValidationHelper.java,v 1.8 2006/04/10 07:09:58 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.guarantees.gtegovtlink;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class GteGovtLinkValidationHelper {
	public static ActionErrors validateInput(GteGovtLinkForm aForm, Locale locale, ActionErrors errors) {
		String errorCode;
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		boolean isMandatory = false;
		if ("submit".equals(aForm.getEvent())) {
			isMandatory = true;
		}

		if (aForm.getEvent().equals("update") || aForm.getEvent().equals("submit")) {

			if (!(errorCode = Validator.checkString(aForm.getValCurrency(), isMandatory, 1, 3))
					.equals(Validator.ERROR_NONE)) {
			}
			try {
				if (!(errorCode = Validator.checkDate(aForm.getDateGuarantee(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("dateGuarantee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"0", 256 + ""));
				}
				else if ((aForm.getDateGuarantee() != null) && (aForm.getDateGuarantee().length() > 0)
						&& (aForm.getCollateralMaturityDate() != null)
						&& (aForm.getCollateralMaturityDate().length() > 0)) {
					Iterator itr = errors.get("collateralMaturityDate");
					if ((itr == null) || !itr.hasNext()) {
						Date currDate = DateUtil.convertDate(locale, aForm.getCollateralMaturityDate());
						Date date1 = DateUtil.convertDate(locale, aForm.getDateGuarantee());
						if (date1.after(currDate)) {
							errors.add("dateGuarantee", new ActionMessage("error.date.compareDate.more",
									"Date of Guarantee ", "Security Maturity Date"));
						}
					}
				}
				// Check Secured Portion

				if (!(errorCode = Validator.checkInteger(aForm.getSecuredPortion(), isMandatory, 0,
						IGlobalConstant.MAXIMUM_PERCENTAGE_VALUE)).equals(Validator.ERROR_NONE)) {
					errors.add("securedPortion", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.INTEGER, errorCode), "0",
							IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE_STR));
				}

				// Check Unsecured Portion
				if (!(errorCode = Validator.checkInteger(aForm.getUnsecuredPortion(), isMandatory, 0,
						IGlobalConstant.MAXIMUM_PERCENTAGE_VALUE)).equals(Validator.ERROR_NONE)) {
					errors.add("unsecuredPortion", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER,
							errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE_STR));
				}

				// Check Secured Amount Original
				if (!(errorCode = Validator.checkAmount(aForm.getSecuredAmountOrigin(), isMandatory, 0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("securedAmountOrigin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							errorCode), "1", maximumAmt));
				}
				// Check Unsecured Amount Original
				if (!(errorCode = Validator.checkAmount(aForm.getUnsecuredAmountOrigin(), isMandatory, 0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("unsecuredAmountOrigin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							errorCode), "1", maximumAmt));
				}

				if (!Validator.validateMandatoryField(aForm.getCurrentScheme())) {
					errors.add("currentScheme", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.STRING, "mandatory")));
				}
				
				if (!(errorCode = Validator.checkDate(aForm.getCancellationDateLG(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("cancellationDateLG", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!(errorCode = Validator.checkAmount(aForm.getMinimalFSV(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("minimalFSV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
		}

		return errors;
	}
}
