package com.integrosys.cms.ui.collateral.pledge;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class PledgeFormValidator {
	public static ActionErrors validateInput(PledgeForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();

		boolean isPledgeAmountRequired = false;
		boolean isPledgePercentageRequired = false;
		boolean isDrawAmountRequired = false;
		boolean isDrawPercentageRequired = false;
		String errorCode = null;

		if (StringUtils.isBlank(form.getFacilityNo())) {
			errors.add("facilityNo", new ActionMessage("error.mandatory"));
		}
		if (StringUtils.isBlank(form.getPledgeAmount()) && StringUtils.isBlank(form.getPledgeAmountPercentage())) {
			errors.add("pledgeAmount", new ActionMessage("error.charge.info.mandatory"));
		}
		if (StringUtils.isBlank(form.getAmountDraw()) && StringUtils.isBlank(form.getAmountDrawPercentage())) {
			errors.add("amountDraw", new ActionMessage("error.charge.info.mandatory"));
		}

		if (String.valueOf(ICollateralLimitMap.CHARGE_INFO_PERCENTAGE_USAGE).equals(form.getChargeInfoUsageIndicator())) {
			isPledgePercentageRequired = true;
		}

		if (String.valueOf(ICollateralLimitMap.CHARGE_INFO_AMOUNT_USAGE).equals(form.getChargeInfoUsageIndicator())) {
			isPledgeAmountRequired = true;
		}

		if (String.valueOf(ICollateralLimitMap.CHARGE_INFO_PERCENTAGE_USAGE).equals(form.getAmountDrawIndicator())) {
			isDrawPercentageRequired = true;
		}

		if (String.valueOf(ICollateralLimitMap.CHARGE_INFO_AMOUNT_USAGE).equals(form.getAmountDrawIndicator())) {
			isDrawAmountRequired = true;
		}
		// Pledge Amount
		if (!(errorCode = Validator.checkAmount(form.getPledgeAmount(), isPledgeAmountRequired, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("pledgeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_STR + ""));
		}
		// Pledge Amount Percentage
		if (!(errorCode = Validator.checkNumber(form.getPledgeAmountPercentage(), isPledgePercentageRequired, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE)).equals(Validator.ERROR_NONE)) {
			errors.add("pledgeAmountPercentage", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE_STR + ""));
		}
		// Amount Draw
		if (!(errorCode = Validator.checkAmount(form.getAmountDraw(), isDrawAmountRequired, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("amountDraw", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_STR + ""));
		}
		// Amount Draw Percentage
		if (!(errorCode = Validator.checkNumber(form.getAmountDrawPercentage(), isDrawPercentageRequired, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE)).equals(Validator.ERROR_NONE)) {
			errors.add("amountDrawPercentage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE_STR + ""));
		}

		return errors;
	}
}