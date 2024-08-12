/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/LoanAgencyValidator.java,v 1.23 2005/11/12 02:53:33 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainConstant;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.23 $
 * @since $Date: 2005/11/12 02:53:33 $ Tag: $Name: $
 */
public class LoanAgencyValidator {
	public static ActionErrors validateInput(LoanAgencyForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (aForm.getEvent().equals(LoanAgencyAction.EVENT_CREATE)
				|| aForm.getEvent().equals(LoanAgencyAction.EVENT_UPDATE)) {
			if (!(errorCode = Validator.checkString(aForm.getSecurityID(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("securityID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						20 + ""));
			}
			if ((aForm.getSelectedLimitID() == null) || (aForm.getSelectedLimitID().length == 0)) {
				errors.add("selectedLimitID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						Validator.ERROR_MANDATORY), "0", 150 + ""));
			}
			if (!(errorCode = UIValidator.checkNumber(aForm.getDebtRate(), true, 0, 999.99, 3, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("debtRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"999.99", "2"));
			}
			if ((aForm.getAllowMultipleCurr() != null) && aForm.getAllowMultipleCurr().equals(ICMSConstant.TRUE_VALUE)) {
				if ((aForm.getSelectedCurrency() == null) || (aForm.getSelectedCurrency().length == 0)) {
					errors.add("selectedCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
							Validator.ERROR_MANDATORY), "0", 150 + ""));
				}
			}
			else if ((aForm.getAllowMultipleCurr() != null)
					&& aForm.getAllowMultipleCurr().equals(ICMSConstant.FALSE_VALUE)) {
				if ((aForm.getSelectedCurrency() != null) && (aForm.getSelectedCurrency().length > 1)) {
					errors.add("selectedCurrency", new ActionMessage("error.collateral.commodity.no.multiplecurrency"));
				}
			}
		}
		else {
			if (aForm.getEvent().equals(LoanAgencyAction.EVENT_REFRESH_INTEREST_AMT)) {
				if (!(errorCode = UIValidator.checkNumber(aForm.getDebtRate(), true, 0, 999.99, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("debtRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
							"999.99", "2"));
				}
				if (!(errorCode = UIValidator.checkNumber(aForm.getCalculateBase(), true, 0, 999, 0, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("calculateBase", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", "999"));
				}
			}
			else {
				if (!(errorCode = UIValidator.checkNumber(aForm.getDebtRate(), false, 0, 999.99, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("debtRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
							"999.99", "2"));
				}
				if (!(errorCode = UIValidator.checkNumber(aForm.getCalculateBase(), false, 0, 999, 0, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("calculateBase", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", "999"));
				}
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getAdminAgentName(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("adminAgentName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getColAgentName(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("colAgentName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getDocumentAgent(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("documentAgent", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getLcIssuingBank(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("lcIssuingBank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (aForm.getBorrowerList() != null) {
			String[] tmpList = aForm.getBorrowerList();
			boolean hasError = false;
			for (int i = 0; (i < tmpList.length) && !hasError; i++) {
				if (!(errorCode = Validator.checkString(tmpList[i], false, 0, 150)).equals(Validator.ERROR_NONE)) {
					hasError = true;
				}
			}
			if (hasError) {
				errors.add("borrowerList", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						150 + ""));
			}
		}
		if (aForm.getGuarantorList() != null) {
			String[] tmpList = aForm.getGuarantorList();
			boolean hasError = false;
			for (int i = 0; (i < tmpList.length) && !hasError; i++) {
				if (!(errorCode = Validator.checkString(tmpList[i], false, 0, 150)).equals(Validator.ERROR_NONE)) {
					hasError = true;
				}
			}
			if (hasError) {
				errors.add("guarantorList", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 150 + ""));
			}
		}
		errors = UIValidator.checkAmount(errors, "globalAmountCcy", "globalAmountAmt", aForm.getGlobalAmountCcy(),
				aForm.getGlobalAmountAmt(), false, 0, CommodityMainConstant.MAX_AMOUNT, 0, locale,
				CommodityMainConstant.MAX_AMOUNT_STR);
		/*
		 * if (!(errorCode = UIValidator.checkNumber(aForm.getGlobalAmountAmt(),
		 * false, 0, CommodityMainConstant.MAX_AMOUNT, 0,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("globalAmountAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_AMOUNT_STR)); } else { if
		 * (aForm.getGlobalAmountAmt() != null &&
		 * aForm.getGlobalAmountAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getGlobalAmountCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("globalAmountCcy", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } } }
		 */

		if (!(errorCode = Validator.checkDate(aForm.getFacCommitmentDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("facCommitmentDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validatePreviousDate(errors, aForm.getFacCommitmentDate(), "facCommitmentDate",
					"Facility Commitment Date", locale);
		}
		if (!(errorCode = Validator.checkDate(aForm.getFacEffectiveDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("facEffectiveDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validatePreviousDate(errors, aForm.getFacEffectiveDate(), "facEffectiveDate",
					"Facility Effective Date", locale);
		}
		if (!(errorCode = Validator.checkDate(aForm.getFacMaturityDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("facMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validateFutureDate(errors, aForm.getFacMaturityDate(), "facMaturityDate",
					"Facility Maturity Date", locale);
		}
		if (!(errorCode = Validator.checkDate(aForm.getFacTerminationDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("facTerminationDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getLastDateIssueLC(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("lastDateIssueLC", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getFinalLCMaturityDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("finalLCMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getCounselNameAgentBank(), false, 0, 150))
				.equals(Validator.ERROR_NONE)) {
			errors.add("counselNameAgentBank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 150 + ""));
		}

		// sub-limit validation
		if (aForm.getSubLimitID() != null) {
			String[] ccyList = aForm.getSubLimitCcy();
			String[] amtList = aForm.getSubLimitAmt();
			for (int i = 0; i < aForm.getSubLimitID().length; i++) {
				if ((amtList[i] != null) && (amtList[i].length() > 0)) {
					errors = UIValidator
							.checkAmount(errors, "subLimitCcy" + String.valueOf(i), "subLimitAmt" + String.valueOf(i),
									ccyList[i], amtList[i], false, 0, CommodityMainConstant.MAX_AMOUNT_15_2, 3, locale,
									CommodityMainConstant.MAX_AMOUNT_15_2_STR);
					/*
					 * if (!(errorCode = UIValidator.checkNumber(amtList[i],
					 * false, 0, CommodityMainConstant.MAX_AMOUNT_15_2, 3,
					 * locale)).equals(Validator.ERROR_NONE)) {
					 * errors.add("subLimitAmt"+String.valueOf(i), new
					 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					 * errorCode), "0",
					 * CommodityMainConstant.MAX_AMOUNT_15_2_STR)); } else if
					 * (amtList[i] != null && amtList[i].length() > 0) { if
					 * (!(errorCode = Validator.checkString(ccyList[i], true, 0,
					 * 3)).equals(Validator.ERROR_NONE)) {
					 * errors.add("subLimitCcy"+String.valueOf(i), new
					 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
					 * errorCode), "0", 3 + "")); } }
					 */
				}
			}
		}

		// participant validation
		if (aForm.getParticipantID() != null) {
			String[] amtList = aForm.getAllocatedAmt();
			String[] pricingList = aForm.getPricing();
			for (int i = 0; i < aForm.getParticipantID().length; i++) {
				if (!(errorCode = UIValidator.checkNumber(amtList[i], false, 0, CommodityMainConstant.MAX_AMOUNT_15_2,
						3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("allocatedAmt" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
							ErrorKeyMapper.NUMBER, errorCode), "0", CommodityMainConstant.MAX_AMOUNT_15_2_STR, "2"));
				}
				if (!(errorCode = Validator.checkString(pricingList[i], false, 0, 500)).equals(Validator.ERROR_NONE)) {
					errors.add("pricing" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
							ErrorKeyMapper.STRING, errorCode), "0", 500 + ""));
				}
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getInstalmentEqualType(), true, 0, 3))
				.equals(Validator.ERROR_NONE)) {
			errors.add("instalmentEqualType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 3 + ""));
		}
		else if (aForm.getInstalmentEqualType().equals(ICMSConstant.TRUE_VALUE)) {
			// Term Loan Amortization Schedules - Equal Instalments
			if (aForm.getEvent().equals(LoanAgencyAction.EVENT_CREATE)
					|| aForm.getEvent().equals(LoanAgencyAction.EVENT_UPDATE)
					|| aForm.getEvent().equals(LoanAgencyAction.EVENT_REFRESH_PAYMENT_SCHEDULE)
					|| aForm.getEvent().equals(LoanAgencyAction.EVENT_VIEW_EQUAL_PAYMENT)
					|| (aForm.getInstalmentEqualType().equals(ICMSConstant.TRUE_VALUE) && aForm.getEvent().equals(
							LoanAgencyAction.EVENT_REFRESH_INTEREST_AMT))) {
				if (!(errorCode = Validator.checkString(aForm.getEqualPrinAmtCcy(), true, 0, 3))
						.equals(Validator.ERROR_NONE)) {
					errors.add("equalPrinAmtCcy", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.STRING, errorCode), "0", 3 + ""));
				}
				if (!(errorCode = UIValidator.checkNumber(aForm.getEqualPrinAmtVal(), true, 0,
						CommodityMainConstant.MAX_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("equalPrinAmtVal",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
									CommodityMainConstant.MAX_AMOUNT_15_2_STR, "2"));
				}
				if (!(errorCode = Validator.checkInteger(aForm.getEqualNoInstalments(), true, 1, 120))
						.equals(Validator.ERROR_NONE)) {
					errors.add("equalNoInstalments", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
							errorCode), "1", "120"));
				}
				if (!(errorCode = Validator.checkString(aForm.getEqualPaymentFreq(), true, 0, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("equalPaymentFreq", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
							errorCode), "0", 20 + ""));
				}
				if (!(errorCode = Validator.checkDate(aForm.getEqualFirstPaymentDate(), true, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("equalFirstPaymentDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
							errorCode), "0", 256 + ""));
				}
			}
		}
		else {
			// Term Loan Amortization Schedules - Non Equal Instalments
			if (aForm.getEvent().equals(LoanAgencyAction.EVENT_CREATE)
					|| aForm.getEvent().equals(LoanAgencyAction.EVENT_UPDATE)
					|| aForm.getEvent().equals(LoanAgencyAction.EVENT_EDIT_NON_EQUAL_PAYMENT)
					|| (aForm.getInstalmentEqualType().equals(ICMSConstant.FALSE_VALUE) && aForm.getEvent().equals(
							LoanAgencyAction.EVENT_REFRESH_INTEREST_AMT))) {
				if (!(errorCode = Validator.checkString(aForm.getNonEqPrinAmtCcy(), true, 0, 3))
						.equals(Validator.ERROR_NONE)) {
					errors.add("nonEqPrinAmtCcy", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.STRING, errorCode), "0", 3 + ""));
				}
				if (!(errorCode = UIValidator.checkNumber(aForm.getNonEqPrinAmtVal(), true, 0,
						CommodityMainConstant.MAX_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("nonEqPrinAmtVal",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
									CommodityMainConstant.MAX_AMOUNT_15_2_STR, "2"));
				}
				if (!(errorCode = Validator.checkInteger(aForm.getNonEqNoInstalments(), true, 1, 120))
						.equals(Validator.ERROR_NONE)) {
					errors.add("nonEqNoInstalments", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
							errorCode), "1", "120"));
				}
				if (!(errorCode = Validator.checkString(aForm.getNonEqPaymentFreq(), true, 0, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("nonEqPaymentFreq", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
							errorCode), "0", 20 + ""));
				}
				if (!(errorCode = Validator.checkDate(aForm.getNonEqFirstPaymentDate(), true, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("nonEqFirstPaymentDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
							errorCode), "0", 256 + ""));
				}
			}
		}
		errors = UIValidator.checkAmount(errors, "prePaymentMinCcy", "prePaymentMinAmt", aForm.getPrePaymentMinCcy(),
				aForm.getPrePaymentMinAmt(), false, 0, CommodityMainConstant.MAX_AMOUNT_15_2, 3, locale,
				CommodityMainConstant.MAX_AMOUNT_15_2_STR);
		/*
		 * if (!(errorCode =
		 * UIValidator.checkNumber(aForm.getPrePaymentMinAmt(), false, 0,
		 * CommodityMainConstant.MAX_AMOUNT_15_2, 3,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("prePaymentMinAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_AMOUNT_15_2_STR)); }
		 */
		if (!(errorCode = UIValidator.checkNumber(aForm.getNumDayNotice(), false, 0, 999, 0, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("numDayNotice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"999"));
		}
		if (!(errorCode = Validator.checkString(aForm.getGoverningLaw(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("governingLaw", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					100 + ""));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getInterestDuration(), false, 0, 999, 0, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("interestDuration", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"999"));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getMaxLoanOutstanding(), false, 0, 999, 0, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("maxLoanOutstanding", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", "999"));
		}
		errors = UIValidator.checkAmount(errors, "minDrawdownAllowCcy", "minDrawdownAllowAmt", aForm
				.getMinDrawdownAllowCcy(), aForm.getMinDrawdownAllowAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3,
				locale, CommodityMainConstant.MAX_FEE_STR);

		/*
		 * if (!(errorCode =
		 * UIValidator.checkNumber(aForm.getMinDrawdownAllowAmt(), false, 0,
		 * CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("minDrawdownAllowAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getMinDrawdownAllowAmt() != null &&
		 * aForm.getMinDrawdownAllowAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getMinDrawdownAllowCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("minDrawdownAllowCcy",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
		 * errorCode), "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "maxDrawdownAllowCcy", "maxDrawdownAllowAmt", aForm
				.getMaxDrawdownAllowCcy(), aForm.getMaxDrawdownAllowAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3,
				locale, CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode =
		 * UIValidator.checkNumber(aForm.getMaxDrawdownAllowAmt(), false, 0,
		 * CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("maxDrawdownAllowAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getMaxDrawdownAllowAmt() != null &&
		 * aForm.getMaxDrawdownAllowAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getMaxDrawdownAllowCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("maxDrawdownAllowCcy",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
		 * errorCode), "0", 3 + "")); } }
		 */
		if (!(errorCode = UIValidator.checkNumber(aForm.getMinNumDrawdownAllow(), false, 0, 999, 0, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("minNumDrawdownAllow", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", "999"));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getMaxNumDrawdownAllow(), false, 0, 999, 0, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("maxNumDrawdownAllow", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", "999"));
		}
		errors = UIValidator.checkAmount(errors, "minAssignmentFeeCcy", "minAssignmentFeeAmt", aForm
				.getMinAssignmentFeeCcy(), aForm.getMinAssignmentFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3,
				locale, CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode =
		 * UIValidator.checkNumber(aForm.getMinAssignmentFeeAmt(), false, 0,
		 * CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("minAssignmentFeeAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getMinAssignmentFeeAmt() != null &&
		 * aForm.getMinAssignmentFeeAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getMinAssignmentFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("minAssignmentFeeCcy",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
		 * errorCode), "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "maxAssignmentFeeCcy", "maxAssignmentFeeAmt", aForm
				.getMaxAssignmentFeeCcy(), aForm.getMaxAssignmentFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3,
				locale, CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode =
		 * UIValidator.checkNumber(aForm.getMaxAssignmentFeeAmt(), false, 0,
		 * CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("maxAssignmentFeeAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getMaxAssignmentFeeAmt() != null &&
		 * aForm.getMaxAssignmentFeeAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getMaxAssignmentFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("maxAssignmentFeeCcy",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
		 * errorCode), "0", 3 + "")); } }
		 */
		if (!(errorCode = UIValidator.checkNumber(aForm.getMajorityLendConsent(), false, 0, 999.99, 3, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("majorityLendConsent", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", "999.99", "2"));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getDefaultRate(), false, 0, 999.99, 3, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("defaultRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"999.99", "2"));
		}
		errors = UIValidator.checkAmount(errors, "agencyFeeCcy", "agencyFeeAmt", aForm.getAgencyFeeCcy(), aForm
				.getAgencyFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3, locale,
				CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode = UIValidator.checkNumber(aForm.getAgencyFeeAmt(),
		 * false, 0, CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("agencyFeeAmt",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
		 * errorCode), "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getAgencyFeeAmt() != null && aForm.getAgencyFeeAmt().length()
		 * > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getAgencyFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("agencyFeeCcy", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "closingFeeCcy", "closingFeeAmt", aForm.getClosingFeeCcy(), aForm
				.getClosingFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3, locale,
				CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode = UIValidator.checkNumber(aForm.getClosingFeeAmt(),
		 * false, 0, CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("closingFeeAmt",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
		 * errorCode), "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getClosingFeeAmt() != null &&
		 * aForm.getClosingFeeAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getClosingFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("closingFeeCcy", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "commitmentFeeCcy", "commitmentFeeAmt", aForm.getCommitmentFeeCcy(),
				aForm.getCommitmentFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3, locale,
				CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode =
		 * UIValidator.checkNumber(aForm.getCommitmentFeeAmt(), false, 0,
		 * CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("commitmentFeeAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getCommitmentFeeAmt() != null &&
		 * aForm.getCommitmentFeeAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getCommitmentFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("commitmentFeeCcy",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
		 * errorCode), "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "facilityFeeCcy", "facilityFeeAmt", aForm.getFacilityFeeCcy(), aForm
				.getFacilityFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3, locale,
				CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode = UIValidator.checkNumber(aForm.getFacilityFeeAmt(),
		 * false, 0, CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("facilityFeeAmt",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
		 * errorCode), "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getFacilityFeeAmt() != null &&
		 * aForm.getFacilityFeeAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getFacilityFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("facilityFeeCcy", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "upfrontFeeCcy", "upfrontFeeAmt", aForm.getUpfrontFeeCcy(), aForm
				.getUpfrontFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3, locale,
				CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode = UIValidator.checkNumber(aForm.getUpfrontFeeAmt(),
		 * false, 0, CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("upfrontFeeAmt",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
		 * errorCode), "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getUpfrontFeeAmt() != null &&
		 * aForm.getUpfrontFeeAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getUpfrontFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("upfrontFeeCcy", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "lcFeeCcy", "lcFeeAmt", aForm.getLcFeeCcy(), aForm.getLcFeeAmt(),
				false, 0, CommodityMainConstant.MAX_FEE, 3, locale, CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode = UIValidator.checkNumber(aForm.getLcFeeAmt(), false,
		 * 0, CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("lcFeeAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getLcFeeAmt() != null && aForm.getLcFeeAmt().length() > 0) {
		 * if (!(errorCode = Validator.checkString(aForm.getLcFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("lcFeeCcy", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "amendmentFeeCcy", "amendmentFeeAmt", aForm.getAmendmentFeeCcy(),
				aForm.getAmendmentFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3, locale,
				CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode = UIValidator.checkNumber(aForm.getAmendmentFeeAmt(),
		 * false, 0, CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("amendmentFeeAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getAmendmentFeeAmt() != null &&
		 * aForm.getAmendmentFeeAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getAmendmentFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("amendmentFeeAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "extensionFeeCcy", "extensionFeeAmt", aForm.getExtensionFeeCcy(),
				aForm.getExtensionFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3, locale,
				CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode = UIValidator.checkNumber(aForm.getExtensionFeeAmt(),
		 * false, 0, CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("extensionFeeAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getExtensionFeeAmt() != null &&
		 * aForm.getExtensionFeeAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getExtensionFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("extensionFeeAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "arrangementFeeCcy", "arrangementFeeAmt",
				aForm.getArrangementFeeCcy(), aForm.getArrangementFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3,
				locale, CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode =
		 * UIValidator.checkNumber(aForm.getArrangementFeeAmt(), false, 0,
		 * CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("arrangementFeeAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getArrangementFeeAmt() != null &&
		 * aForm.getArrangementFeeAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getArrangementFeeCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("arrangementFeeAmt",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
		 * errorCode), "0", 3 + "")); } }
		 */
		errors = UIValidator.checkAmount(errors, "otherFeeCcy", "otherFeeAmt", aForm.getOtherFeeCcy(), aForm
				.getOtherFeeAmt(), false, 0, CommodityMainConstant.MAX_FEE, 3, locale,
				CommodityMainConstant.MAX_FEE_STR);
		/*
		 * if (!(errorCode = UIValidator.checkNumber(aForm.getOtherFeeAmt(),
		 * false, 0, CommodityMainConstant.MAX_FEE, 3,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("otherFeeAmt",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
		 * errorCode), "0", CommodityMainConstant.MAX_FEE_STR)); } else if
		 * (aForm.getOtherFeeAmt() != null && aForm.getOtherFeeAmt().length() >
		 * 0) { if (!(errorCode = Validator.checkString(aForm.getOtherFeeCcy(),
		 * true, 0, 3)).equals(Validator.ERROR_NONE)) {
		 * errors.add("otherFeeAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } }
		 */

		return errors;
	}

	private static ActionErrors validateFutureDate(ActionErrors errors, String dateStr, String fieldName, String desc,
			Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.before(DateUtil.getDate())) {
				errors.add(fieldName, new ActionMessage("error.collateral.commodity.futuredate", desc));
			}
		}
		return errors;
	}

	private static ActionErrors validatePreviousDate(ActionErrors errors, String dateStr, String fieldName,
			String desc, Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.after(DateUtil.getDate())) {
				errors.add(fieldName, new ActionMessage("error.collateral.commodity.not.futuredate", desc));
			}
		}
		return errors;
	}
}
