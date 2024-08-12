package com.integrosys.cms.ui.collateral.guarantees.feedetails;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 8, 2007 Time: 6:34:37 PM
 * To change this template use File | Settings | File Templates.
 */

public class FeeDetailsValidator {

	private static final String ATLEAST_MANDATORY = "error.atleast.mandatory";

	public static ActionErrors validateInput(FeeDetailsForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		DefaultLogger.error(
				"com.integrosys.cms.ui.collateral.guarantees.feedetails.FeeDetailsValidator Inside valiation", "");

		if (//AbstractCommonMapper.isEmptyOrNull(aForm.getAmountCGC())&& 
				AbstractCommonMapper.isEmptyOrNull(aForm.getAmountFee())
				&& AbstractCommonMapper.isEmptyOrNull(aForm.getEffectiveDate())
				&& AbstractCommonMapper.isEmptyOrNull(aForm.getExpirationDate())
				&& AbstractCommonMapper.isEmptyOrNull(aForm.getTenor())) {
			errors.add("atleastOne", new ActionMessage(ATLEAST_MANDATORY));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.guarantees.feedetails.FeeDetailsValidator atleastOne required",
					"");
		}
		else {
			/*
			if (!(errorCode = Validator.checkAmount(aForm.getAmountCGC(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("amountCGC", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						aForm.getAmountCGC()));
				DefaultLogger.error("com.integrosys.cms.ui.collateral.guarantees.feedetails.FeeDetailsValidator",
						"getAmountCGC(): " + aForm.getAmountCGC());
			}
			*/
			if (!(errorCode = Validator.checkAmount(aForm.getAmountFee(), true, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("amountFee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						aForm.getAmountFee()));
				DefaultLogger.error("com.integrosys.cms.ui.collateral.guarantees.feedetails.FeeDetailsValidator",
						"getAmountFee(): " + aForm.getAmountFee());
			}
			
			if (!(errorCode = Validator.checkDate(aForm.getFeePaymentDateCGC(), true, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("feePaymentDateCGC", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}

			/*
			 * if (aForm.getEffectiveDate() != null && aForm.getExpirationDate()
			 * != null) { Date effectiveDate = DateUtil.convertDate(locale,
			 * aForm.getEffectiveDate()); Date expirationDate =
			 * DateUtil.convertDate(locale, aForm.getExpirationDate()); if
			 * (effectiveDate.after(expirationDate)) {
			 * errors.add("effectiveDate", new
			 * ActionMessage("error.date.compareDate.more", "Effective Date ",
			 * " Expiration Date"));DefaultLogger.error(
			 * "com.integrosys.cms.ui.collateral.guarantees.feedetails.FeeDetailsValidator"
			 * , " getEffectiveDate(): " + aForm.getEffectiveDate() +
			 * " getExpirationDate(): " + aForm.getExpirationDate()); } }
			 */

			if (!(errorCode = Validator.checkInteger(aForm.getTenor(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator.ERROR_NONE)) {
				errors.add("tenor", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER + ""));
				DefaultLogger.error("com.integrosys.cms.ui.collateral.guarantees.feedetails.FeeDetailsValidator",
						"getTenor(): " + aForm.getTenor());
			}
		}

		DefaultLogger.error("com.integrosys.cms.ui.collateral.guarantees.feedetails.FeeDetailsValidator existing ",
				"  errors " + errors.size());

		return errors;
	}

}
