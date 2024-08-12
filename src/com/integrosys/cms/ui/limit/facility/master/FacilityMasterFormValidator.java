package com.integrosys.cms.ui.limit.facility.master;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class FacilityMasterFormValidator {
	public static final String THIS_CLASS = "com.integrosys.cms.ui.limit.facility.master.FacilityMasterFormValidator";

	public static ActionErrors validateInput(FacilityMasterForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();

		String event = form.getEvent();
		String errorCode = null;
		boolean isMandatoryValidate = false;
		if (FacilityMainAction.EVENT_SUBMIT.equals(event) || FacilityMainAction.EVENT_SUBMIT_WO_FRAME.equals(event)) {
			isMandatoryValidate = true;
		}

		// Installment Amount
		if (isMandatoryValidate && StringUtils.isBlank(form.getInstallmentAmount())) {
			errors.add("installmentAmount", new ActionMessage("error.mandatory"));
		}
		// Drawing Limit
		// if (StringUtils.isBlank(form.getDrawingLimitAmount())) {
		// errors.add("drawingLimitAmount", new
		// ActionMessage("error.mandatory"));
		// }
		// Final Payment Amount
		if (isMandatoryValidate && StringUtils.isBlank(form.getFinalPaymentAmount())) {
			errors.add("finalPaymentAmount", new ActionMessage("error.mandatory"));
		}
		// ACF No.
		// if (!ICMSConstant.APPLICATION_TYPE_HP.equals(form.getAaType())
		// && !ICMSConstant.APPLICATION_TYPE_IH.equals(form.getAaType())
		// && !ICMSConstant.PRODUCT_TYPE_SV.equals(form.getProductDesc())
		// && StringUtils.isBlank(form.getAcfNo())) {
		// errors.add("acfNo", new ActionMessage("error.mandatory"));
		// }
		// Loan Purpose Code
		if (isMandatoryValidate && StringUtils.isBlank(form.getLoanPurposeEntryCode())) {
			errors.add("loanPurposeEntryCode", new ActionMessage("error.mandatory"));
		}
		// Date Offer Accepted
		if (isMandatoryValidate && StringUtils.isBlank(form.getOfferAcceptedDate())) {
			errors.add("offerAcceptedDate", new ActionMessage("error.mandatory"));
		}
		// Date Of Offer
		if (isMandatoryValidate && StringUtils.isBlank(form.getOfferDate())) {
			errors.add("offerDate", new ActionMessage("error.mandatory"));
		}
		// Approved By
		if (isMandatoryValidate && StringUtils.isBlank(form.getPersonApprovedEntryCode())) {
			errors.add("personApprovedEntryCode", new ActionMessage("error.mandatory"));
		}
		// Date Approved
		if (isMandatoryValidate && StringUtils.isBlank(form.getApprovedDate())) {
			errors.add("approvedDate", new ActionMessage("error.mandatory"));
		}
		// Car Code
		if (isMandatoryValidate && StringUtils.isBlank(form.getCarEntryCode())) {
			errors.add("carEntryCode", new ActionMessage("error.mandatory"));
		}
		// Limit Status
		if (isMandatoryValidate && StringUtils.isBlank(form.getLimitStatusEntryCode())) {
			errors.add("limitStatusEntryCode", new ActionMessage("error.mandatory"));
		}
		// Facility Status
		if (isMandatoryValidate && StringUtils.isBlank(form.getFacilityStatusEntryCode())) {
			errors.add("facilityStatusEntryCode", new ActionMessage("error.mandatory"));
		}
		// CAR Code Flag
		if (isMandatoryValidate && StringUtils.isBlank(form.getCarCodeFlag())) {
			errors.add("carCodeFlag", new ActionMessage("error.mandatory"));
		}
		// Rate Type
		/*
		 * if (StringUtils.isBlank(form.getInterestRateTypeEntryCode())) {
		 * errors.add("interestRateTypeEntryCode", new
		 * ActionMessage("error.mandatory")); }
		 */
		// Spread Sign
		/*
		 * if (StringUtils.isBlank(form.getSpreadSign())) {
		 * errors.add("spreadSign", new ActionMessage("error.mandatory")); }
		 */
		// Application Source
		if (isMandatoryValidate && StringUtils.isBlank(form.getApplicationSourceEntryCode())) {
			errors.add("applicationSourceEntryCode", new ActionMessage("error.mandatory"));
		}
		// Revolving Non-Revolving
		if (isMandatoryValidate && StringUtils.isBlank(form.getRevolvingIndicator())) {
			errors.add("revolvingIndicator", new ActionMessage("error.mandatory"));
		}
		// Rev On O/S Bal Or ORGAMT
		/*
		 * if (StringUtils.isBlank(form.getRevolvingOnCriteriaIndicator())) {
		 * errors.add("revolvingOnCriteriaIndicator", new
		 * ActionMessage("error.mandatory")); }
		 */
		// Installment Amount
		if (!(errorCode = Validator.checkAmount(form.getInstallmentAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("installmentAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("installmentAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Drawing Limit
		if (!(errorCode = Validator.checkAmount(form.getDrawingLimitAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {

			if (errorCode.equals("decimalexceeded")) {
				errors.add("drawingLimitAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("drawingLimitAmount", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}

		String minimumFinalPaymentAmountValue = "-" + IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR;
		double minimumFinalPaymentAmount = Double.parseDouble(minimumFinalPaymentAmountValue);

		// Final Payment Amount
		if (!(errorCode = Validator.checkAmount(form.getFinalPaymentAmount(), false, minimumFinalPaymentAmount,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("finalPaymentAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("finalPaymentAmount", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), minimumFinalPaymentAmountValue,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
			}
		}

		// Required Security Coverage (%)
		if (!(errorCode = Validator.checkNumber(form.getRequiredSecurityCoverage(), false, 0,
				IGlobalConstant.MAXIMUM_PERCENTAGE_VALUE)).equals(Validator.ERROR_NONE)) {
			errors.add("requiredSecurityCoverage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE_STR + ""));
		}
		// Prime Rate Floor
		if (!(errorCode = Validator.checkNumber(form.getPrimeRateFloor(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9)).equals(Validator.ERROR_NONE)) {
			errors.add("primeRateFloor", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR + ""));
		}
		// Prime Rate Ceiling
		if (!(errorCode = Validator.checkNumber(form.getPrimeRateCeiling(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9)).equals(Validator.ERROR_NONE)) {
			errors.add("primeRateCeiling", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR + ""));
		}
		// Prime Review Term
		if (!(errorCode = Validator.checkNumber(form.getPrimeReviewTerm(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator.ERROR_NONE)) {
			errors.add("primeReviewTerm", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER + ""));
		}
		// OD Excess Rate Var
		if (!(errorCode = Validator.checkNumber(form.getOdExcessRateVar(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9)).equals(Validator.ERROR_NONE)) {
			errors.add("odExcessRateVar", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR + ""));
		}
		// Commission Fees
		if (!(errorCode = Validator.checkAmount(form.getCommissionFeesAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("commissionFeesAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("commissionFeesAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
						errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Handling Fees
		if (!(errorCode = Validator.checkAmount(form.getHandlingFeesAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("handlingFeesAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("handlingFeesAmount", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Subsidy Amount
		if (!(errorCode = Validator.checkAmount(form.getSubsidyAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("subsidyAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("subsidyAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Others Fee
		if (!(errorCode = Validator.checkAmount(form.getOthersFeeAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("othersFeeAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("othersFeeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Commission Rate
		// new Double(form.getCommissionRate()).compareTo(new Double(0)) != 0
		if (!(errorCode = Validator.checkNumber(form.getCommissionRate(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9)).equals(Validator.ERROR_NONE)) {
			errors.add("commissionRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR + ""));
		}
		else if (StringUtils.isNotBlank(form.getCommissionRate())
				&& new Double(form.getCommissionRate()).compareTo(new Double(0)) != 0) {
			if (StringUtils.isBlank(form.getCommissionBasisEntryCode())) {
				errors.add("commissionBasisEntryCode", new ActionMessage("error.mandatory"));
			}
		}
		else if (StringUtils.isBlank(form.getCommissionRate())
				|| new Double(form.getCommissionRate()).compareTo(new Double(0)) == 0) {
			if (StringUtils.isNotBlank(form.getCommissionBasisEntryCode())) {
				errors.add("commissionBasisEntryCode", new ActionMessage("error.string.empty"));
			}
		}
		// Maximum Commission
		if (!(errorCode = Validator.checkAmount(form.getMaximumCommissionAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("maximumCommissionAmount",
						new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("maximumCommissionAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
						errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Minimum Commission
		if (!(errorCode = Validator.checkAmount(form.getMinimumCommissionAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("minimumCommissionAmount",
						new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("minimumCommissionAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
						errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Minimum Commission cannot greater than Maximum Commission
		if (StringUtils.isNotBlank(form.getMinimumCommissionAmount())
				&& StringUtils.isNotBlank(form.getMaximumCommissionAmount())) {
			if (Double.parseDouble(form.getMinimumCommissionAmount()) > Double.parseDouble(form
					.getMaximumCommissionAmount())) {
				errors.add("minimumCommissionAmount", new ActionMessage("error.amount.not.greaterthan",
						"Minimum Commission", "Maximum Commission"));
			}
		}
		// Fac Commitment Rate
		if (!(errorCode = Validator.checkNumber(form.getFacilityCommitmentRate(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9)).equals(Validator.ERROR_NONE)) {
			errors.add("facilityCommitmentRate", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR + ""));
		}
		// Main Facility AA No.
		/*
		 * if (!(errorCode =
		 * Validator.checkAmount(form.getMainFacilityAaNumber(), false, 0,
		 * IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18,
		 * IGlobalConstant.DEFAULT_CURRENCY, locale))
		 * .equals(Validator.ERROR_NONE)) { errors.add("mainFacilityAaNumber",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
		 * errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_STR +
		 * "")); }
		 */
		// Main Facility Seq No.
		if (StringUtils.isNotBlank(form.getMainFacilitySequenceNumber())) {

			if (form.getMainFacilitySequenceNumber().length() > 19) {
				errors.add("mainFacilitySequenceNumber", new ActionMessage("error.integer.greaterthan", "0",
						StringUtils.repeat("9", 19)));
			}
			else {
				try {
					Long.parseLong(form.getMainFacilitySequenceNumber());
				}
				catch (NumberFormatException ex) {
					errors.add("mainFacilitySequenceNumber", new ActionMessage("error.integer.format"));
				}
			}
		}
		// Floor Pledged Limit
		if (!(errorCode = Validator.checkAmount(form.getFloorPledgedLimitAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("floorPledgedLimitAmount",
						new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("floorPledgedLimitAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
						errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Retention Sum
		if (!(errorCode = Validator.checkAmount(form.getRetentionSumAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("retentionSumAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("retentionSumAmount", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Retention Period
		if (!(errorCode = Validator.checkAmount(form.getRetentionPeriod(), false, 0, Short.MAX_VALUE,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("retentionPeriod", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("retentionPeriod", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", String.valueOf(Short.MAX_VALUE)));
			}
		}
		// ECOF Administration Cost
		if (!(errorCode = Validator.checkAmount(form.getEcofAdministrationCostAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("ecofAdministrationCostAmount", new ActionMessage("error.number.moredecimalexceeded", "",
						"", "2"));
			}
			else {
				errors.add("ecofAdministrationCostAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
						errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// ECOF Rate
		if (!(errorCode = Validator
				.checkNumber(form.getEcofRate(), false, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9))
				.equals(Validator.ERROR_NONE)) {
			errors.add("ecofRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR + ""));
		}
		// ECOF Variance
		if (!(errorCode = Validator.checkNumber(form.getEcofVariance(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9)).equals(Validator.ERROR_NONE)) {
			errors.add("ecofVariance", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR + ""));
		}

		// financed amount / applied amount
		if (!(errorCode = Validator.checkAmount(form.getFinancedAmount(), isMandatoryValidate, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("amountFinanced", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("amountFinanced", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}

		// approved amount
		if (!(errorCode = Validator.checkAmount(form.getApprovedLimitAmount(), isMandatoryValidate, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("approvedLimit", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("approvedLimit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}

		// limit tenor
		if (!(errorCode = Validator.checkInteger(form.getTerm(), false, 0, 99999)).equals(Validator.ERROR_NONE)) {
			errors.add("term", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "0", "99999"));
		}

		// limit tenor code (D, M, Y)

		// interest rate
		if (!(errorCode = Validator.checkNumber(form.getInterestRate(), isMandatoryValidate, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9)).equals(Validator.ERROR_NONE)) {
			errors.add("interestRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR));
		}

		// interest rate type
		// if (isMandatoryValidate &&
		// StringUtils.isBlank(form.getInterestRateTypeEntryCode())) {
		// errors.add("interestRateTypeEntryCode", new
		// ActionMessage("error.mandatory"));
		// }

		// officer
		if (isMandatoryValidate && StringUtils.isBlank(form.getOfficerEntryCode())) {
			errors.add("officer", new ActionMessage("error.mandatory"));
		}

		// spread sign
		// if (isMandatoryValidate && StringUtils.isBlank(form.getSpreadSign()))
		// {
		// errors.add("spreadSign", new ActionMessage("error.mandatory"));
		// }

		// spread
		if (!(errorCode = Validator.checkNumber(form.getSpread(), false, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9))
				.equals(Validator.ERROR_NONE)) {
			errors.add("spread", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR));
		}

		// grace period
		if (!(errorCode = Validator.checkInteger(form.getGracePeriodEntryCode(), false, 0, 999))
				.equals(Validator.ERROR_NONE)) {
			errors.add("gracePeriodEntryCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
					"0", "999"));
		}

		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}

	public static String mapModifiedStringToDefaultString(String amount) {
		String result = null;
		BigDecimal temp = UIUtil.mapStringToBigDecimal(amount);
		if (temp != null)
			result = temp.toString();
		return result;
	}
}
