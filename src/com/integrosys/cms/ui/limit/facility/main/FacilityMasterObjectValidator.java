package com.integrosys.cms.ui.limit.facility.main;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityJdbc;

/**
 * <p>
 * Validate object inside the Facility Master Tab only
 * <p>
 * This validation is only to filter all the mandatory fields, for another
 * validation should already done from each form validator
 */
public abstract class FacilityMasterObjectValidator {

	private static final String ACCOUNTY_TYPE_LOAN = "L";

	private static final String THIS_CLASS = "com.integrosys.cms.ui.limit.facility.main.FacilityMasterObjectValidator";

	public static ActionErrors validateObject(IFacilityMaster facilityMasterObj, String aaType,
			String limitProductType, String limitDealerProductFlag, String aaLawType, String accountType) {
		// FacilityMasterObject
		ActionErrors facilityMasterErrors = new ActionErrors();
		// AA No.
		if (facilityMasterObj.getLimit().getLimitID() == 0) {
			facilityMasterErrors.add("aaNo", new ActionMessage("error.mandatory"));
		}
		// Facility Code
		if (StringUtils.isBlank(facilityMasterObj.getLimit().getFacilityCode())) {
			facilityMasterErrors.add("facilityCode", new ActionMessage("error.mandatory"));
		}

		// Product Type
		if (facilityMasterObj.getLimit().getProductDesc() == null) {
			facilityMasterErrors.add("productDesc", new ActionMessage("error.mandatory"));
		}
		// Amount Financed
		if (facilityMasterObj.getFacilityGeneral().getFinancedAmount() == null) {
			facilityMasterErrors.add("amountFinanced", new ActionMessage("error.mandatory"));
		}
		// Application Date
		if (facilityMasterObj.getFacilityGeneral().getApplicationDate() == null) {
			facilityMasterErrors.add("applicationDate", new ActionMessage("error.mandatory"));
		}
		// Approved Limit
		if (facilityMasterObj.getLimit().getApprovedLimitAmount() == null) {
			facilityMasterErrors.add("approvedLimit", new ActionMessage("error.mandatory"));
		}
		// Installment Amount
		if (facilityMasterObj.getFacilityGeneral().getInstallmentAmount() == null) {
			facilityMasterErrors.add("installmentAmount", new ActionMessage("error.mandatory"));
		}

		// Final Payment Amount
		if (facilityMasterObj.getFacilityGeneral().getFinalPaymentAmount() == null) {
			facilityMasterErrors.add("finalPaymentAmount", new ActionMessage("error.mandatory"));
		}
		// Term Code
		if (StringUtils.isBlank(facilityMasterObj.getLimit().getLimitTenorUnit())
				&& ACCOUNTY_TYPE_LOAN.equals(facilityMasterObj.getLimit().getAccountType())) {
			facilityMasterErrors.add("termCode", new ActionMessage("error.mandatory"));
		}
		// Term
		if (facilityMasterObj.getLimit().getLimitTenor() == null
				&& ACCOUNTY_TYPE_LOAN.equals(facilityMasterObj.getLimit().getAccountType())) {
			facilityMasterErrors.add("term", new ActionMessage("error.mandatory"));
		}

		// ACF No.
		if (StringUtils.isNotEmpty(limitProductType)) {
			if (!ICMSConstant.PRODUCT_GROUP_HP.equals(limitProductType)
					&& !ICMSConstant.PRODUCT_GROUP_IH.equals(limitProductType)
					&& !ICMSConstant.PRODUCT_TYPE_SV.equals(facilityMasterObj.getLimit().getProductDesc())
					&& StringUtils.isBlank(facilityMasterObj.getAcfNo())) {
				facilityMasterErrors.add("acfNo", new ActionMessage("error.mandatory"));
			}
		}

        // Andy Wong, 11 May 2009: validate if ACF No have been used under the same AA, subject to first facility been stp successfully
        // Sibs rule: MLS3041
        if(StringUtils.isNotBlank(facilityMasterObj.getAcfNo())){
            IFacilityJdbc facilityJdbc = (IFacilityJdbc) BeanHouse.get("facilityJdbc");

            if(facilityJdbc.isAcfNoExists(facilityMasterObj.getLimit().getLimitID(), facilityMasterObj.getLimit().getLimitProfileReferenceNumber(), facilityMasterObj.getAcfNo())){
                facilityMasterErrors.add("acfNo", new ActionMessage("error.acf.no.already.exists"));
            }
        }

		// Loan Purpose Code
		if (StringUtils.isBlank(facilityMasterObj.getFacilityGeneral().getLoanPurposeEntryCode())) {
			facilityMasterErrors.add("loanPurposeEntryCode", new ActionMessage("error.mandatory"));
		}
		// Date Offer Accepted
		if (facilityMasterObj.getFacilityGeneral().getOfferAcceptedDate() == null) {
			facilityMasterErrors.add("offerAcceptedDate", new ActionMessage("error.mandatory"));
		}
		// Date Of Offer
		if (facilityMasterObj.getFacilityGeneral().getOfferDate() == null) {
			facilityMasterErrors.add("offerDate", new ActionMessage("error.mandatory"));
		}
		// Approved By
		if (StringUtils.isBlank(facilityMasterObj.getFacilityGeneral().getPersonApprovedEntryCode())) {
			facilityMasterErrors.add("personApprovedEntryCode", new ActionMessage("error.mandatory"));
		}
		// Date Approved
		if (facilityMasterObj.getFacilityGeneral().getApprovedDate() == null) {
			facilityMasterErrors.add("approvedDate", new ActionMessage("error.mandatory"));
		}
		// Car Code
		if (StringUtils.isBlank(facilityMasterObj.getFacilityGeneral().getCarEntryCode())) {
			facilityMasterErrors.add("carEntryCode", new ActionMessage("error.mandatory"));
		}
		// Officer
		if (StringUtils.isBlank(facilityMasterObj.getFacilityGeneral().getOfficerEntryCode())) {
			facilityMasterErrors.add("officer", new ActionMessage("error.mandatory"));
		}
		// Documentation Status
		if (StringUtils.isBlank(facilityMasterObj.getFacilityGeneral().getLimitStatusEntryCode())) {
			facilityMasterErrors.add("limitStatusEntryCode", new ActionMessage("error.mandatory"));
		}
		// Facility Status
		if (StringUtils.isBlank(facilityMasterObj.getFacilityGeneral().getFacilityStatusEntryCode())) {
			facilityMasterErrors.add("facilityStatusEntryCode", new ActionMessage("error.mandatory"));
		}
		// Interest Rate
		if (facilityMasterObj.getFacilityInterest().getInterestRate() == null) {
			facilityMasterErrors.add("interestRate", new ActionMessage("error.mandatory"));
		}
		// CAR Code Flag
		if (facilityMasterObj.getFacilityGeneral().getCarCodeFlag() == null) {
			facilityMasterErrors.add("carCodeFlag", new ActionMessage("error.mandatory"));
		}

		/* 15 Jan 2009: include additional validation based on SIBS mapping */

		// Cancellation date require when facility status is either C/R
		if ((ICMSConstant.FACILITY_STATUS_CANCELLED.equals(facilityMasterObj.getFacilityGeneral()
				.getFacilityStatusEntryCode()) || ICMSConstant.FACILITY_STATUS_REJECTED.equals(facilityMasterObj
				.getFacilityGeneral().getFacilityStatusEntryCode()))
				&& facilityMasterObj.getFacilityGeneral().getCancelOrRejectDate() == null) {
			facilityMasterErrors.add("cancelOrRejectDate", new ActionMessage("error.mandatory"));
		}

		// Cancellation date not require when facility status is neither C/R
		if (!(ICMSConstant.FACILITY_STATUS_CANCELLED.equals(facilityMasterObj.getFacilityGeneral()
				.getFacilityStatusEntryCode()) || ICMSConstant.FACILITY_STATUS_REJECTED.equals(facilityMasterObj
				.getFacilityGeneral().getFacilityStatusEntryCode()))
				&& facilityMasterObj.getFacilityGeneral().getCancelOrRejectDate() != null) {
			facilityMasterErrors.add("cancelOrRejectDate", new ActionMessage("error.cancellation.date.not.required"));
		}

		// Facility status must be C when cancel code provide
		if (StringUtils.isNotBlank(facilityMasterObj.getFacilityGeneral().getCancelOrRejectEntryCode())
				&& !(ICMSConstant.FACILITY_STATUS_CANCELLED.equals(facilityMasterObj.getFacilityGeneral()
						.getFacilityStatusEntryCode()))) {
			facilityMasterErrors.add("facilityStatusEntryCode",
					new ActionMessage("error.facility.status.not.cancelled"));
		}
		// Facility status cannot be C when cancel code not provide
		if (StringUtils.isBlank(facilityMasterObj.getFacilityGeneral().getCancelOrRejectEntryCode())
				&& ICMSConstant.FACILITY_STATUS_CANCELLED.equals(facilityMasterObj.getFacilityGeneral()
						.getFacilityStatusEntryCode())) {
			facilityMasterErrors.add("facilityStatusEntryCode", new ActionMessage(
					"error.facility.status.cannot.be.cancelled"));
		}

		// Both prime review term and code must having the value or vice-versa
		if (facilityMasterObj.getFacilityInterest() != null) {
			if (facilityMasterObj.getFacilityInterest().getPrimeReviewTerm() != null
					&& facilityMasterObj.getFacilityInterest().getPrimeReviewTerm().intValue() > 0
					&& StringUtils.isBlank(facilityMasterObj.getFacilityInterest().getPrimeReviewTermCodeEntryCode())) {
				facilityMasterErrors.add("primeReviewTerm", new ActionMessage("error.conditional.mandatory.pair",
						"Prime Review Term", "Prime Review Term Code"));
			}
			if ((facilityMasterObj.getFacilityInterest().getPrimeReviewTerm() == null || (facilityMasterObj
					.getFacilityInterest().getPrimeReviewTerm() != null && facilityMasterObj.getFacilityInterest()
					.getPrimeReviewTerm().intValue() <= 0))
					&& StringUtils
							.isNotBlank(facilityMasterObj.getFacilityInterest().getPrimeReviewTermCodeEntryCode())) {
				facilityMasterErrors.add("primeReviewTerm", new ActionMessage("error.conditional.mandatory.pair",
						"Prime Review Term", "Prime Review Term Code"));
			}
		}
		if (facilityMasterObj.getFacilityFeeCharge() != null) {
			// Andy Wong, 3 Feb 2009: both commision rate and commision fee
			// cannot be provide together
			if (facilityMasterObj.getFacilityFeeCharge().getCommissionRate() != null
					&& facilityMasterObj.getFacilityFeeCharge().getCommissionRate().doubleValue() > 0
					&& facilityMasterObj.getFacilityFeeCharge().getCommissionFeesAmount() != null
					&& facilityMasterObj.getFacilityFeeCharge().getCommissionFeesAmount().getAmount() > 0) {
				facilityMasterErrors.add("commissionFeesAmount", new ActionMessage(
						"error.either.commission.rate.fees.required"));
			}
		}

		// Either commision fees or hanlding fees is require when product group
		// is either IH/HP
		if (ICMSConstant.PRODUCT_GROUP_HP.equals(limitProductType)
				|| ICMSConstant.PRODUCT_GROUP_IH.equals(limitProductType)
				&& facilityMasterObj.getFacilityFeeCharge() != null) {
			// If Both filled then error
			if (facilityMasterObj.getFacilityFeeCharge().getCommissionFeesAmount() != null
					&& facilityMasterObj.getFacilityFeeCharge().getCommissionFeesAmount().getAmount() > 0
					&& facilityMasterObj.getFacilityFeeCharge().getHandlingFeesAmount() != null
					&& facilityMasterObj.getFacilityFeeCharge().getHandlingFeesAmount().getAmount() > 0) {
				facilityMasterErrors.add("handlingFeesAmount", new ActionMessage(
						"error.either.comm.rate.handling.fees.when.product.is", ICMSConstant.PRODUCT_GROUP_HP + "/"
								+ ICMSConstant.PRODUCT_GROUP_IH));
			}
		}

		// End of SIBS validation added

		// Dealer Number / LPP Code
		if (ICMSConstant.APPLICATION_TYPE_HP.equals(aaType)
				&& StringUtils.isBlank(facilityMasterObj.getDealerNumberOrLppCodeEntryCode())) {
			facilityMasterErrors.add("dealerNumberOrLppCodeEntryCode", new ActionMessage("error.mandatory"));
		}

		if (StringUtils.isNotEmpty(limitDealerProductFlag)
				&& ICMSConstant.TRUE_VALUE.equalsIgnoreCase(limitDealerProductFlag)
				&& StringUtils.isBlank(facilityMasterObj.getDealerNumberOrLppCodeEntryCode())) {
			facilityMasterErrors.add("dealerNumberOrLppCodeEntryCode", new ActionMessage("error.mandatory"));
		}

		// Department code
		if (StringUtils.isBlank(facilityMasterObj.getDepartmentCodeEntryCode())) {
			facilityMasterErrors.add("departmentCodeEntryCode", new ActionMessage("error.mandatory"));
		}

		// Rate Type
		if (facilityMasterObj.getFacilityInterest().getInterestRate() == null
				&& StringUtils.isBlank(facilityMasterObj.getFacilityInterest().getInterestRateTypeEntryCode())) {
			facilityMasterErrors.add("interestRateTypeEntryCode", new ActionMessage("error.mandatory"));
		}

		// Revolving Non-Revolving
		if (facilityMasterObj.getRevolvingIndicator() == null) {
			facilityMasterErrors.add("revolvingIndicator", new ActionMessage("error.mandatory"));
		}
		// Rev On O/S Bal Or ORGAMT
		if (facilityMasterObj.getRevolvingIndicator() != null) {
			if (StringUtils.equals(facilityMasterObj.getRevolvingIndicator(), "R")
					// &&
					// StringUtils.isNotBlank(facilityMasterObj.getRevolvingOnCriteriaIndicator())
					&& !StringUtils.equals(facilityMasterObj.getRevolvingOnCriteriaIndicator(), "O")
					&& !StringUtils.equals(facilityMasterObj.getRevolvingOnCriteriaIndicator(), "P")
					&& !StringUtils.equals(facilityMasterObj.getRevolvingOnCriteriaIndicator(), "S")
					&& !StringUtils.equals(facilityMasterObj.getRevolvingOnCriteriaIndicator(), "B")) {
				facilityMasterErrors.add("revolvingOnCriteriaIndicator", new ActionMessage(
						"error.revolving.os.bal.code"));
			}
			else if (StringUtils.equals(facilityMasterObj.getRevolvingIndicator(), "N")
					&& StringUtils.isNotBlank(facilityMasterObj.getRevolvingOnCriteriaIndicator())) {
				facilityMasterErrors.add("revolvingOnCriteriaIndicator", new ActionMessage("error.string.empty"));
			}
		}

		// Added By KLYong
		// Validation MLS3101 - ECOF variance Code must be blanks when effective
		// cost of fund is set to N
		// Andy Wong, 19 Jan 2009: retrieve error message from message resources
		// properties
		if ((new Boolean(false).equals(facilityMasterObj.getEffectiveCostOfFund()))
				&& StringUtils.isNotBlank(facilityMasterObj.getEcofVarianceCode())) {
			facilityMasterErrors.add("ecofVarianceCode", new ActionMessage("error.ecof.variance.code.blank"));
		}

		// Validation MLS3102 - ECOF variance cannot be 0 when EOCF variance
		// code is either +/-
		if ((StringUtils.equals(facilityMasterObj.getEcofVarianceCode(), "+") || StringUtils.equals(facilityMasterObj
				.getEcofVarianceCode(), "-"))
				&& (facilityMasterObj.getEcofVariance() != null && facilityMasterObj.getEcofVariance().doubleValue() == 0)) {
			facilityMasterErrors.add("ecofVariance", new ActionMessage("error.ecof.variance.zero"));
		}
		// Validation MLS3102 - ECOF variance must be 0 when effective cost of
		// fund is set to N
		else if ((new Boolean(false).equals(facilityMasterObj.getEffectiveCostOfFund()))
				&& (facilityMasterObj.getEcofVariance() != null && facilityMasterObj.getEcofVariance().compareTo(
						new Double(0)) != 0)) {
			facilityMasterErrors.add("ecofVariance", new ActionMessage("error.ecof.variance.not.zero"));
		}

		// Validation MLS3103 - ECOF rate must not be 0 when effective cost of
		// fund is set to Y
		if ((new Boolean(true).equals(facilityMasterObj.getEffectiveCostOfFund()))
				&& (facilityMasterObj.getEcofRate() != null && facilityMasterObj.getEcofRate().doubleValue() == 0)) {
			facilityMasterErrors.add("ecofRate", new ActionMessage("error.ecof.rate.zero"));
		}

		// Validation MLS3140 - Both OD excess rate and code must having the
		// value or vice-versa
		if (StringUtils.isBlank(StringUtils.trimToEmpty(facilityMasterObj.getOdExcessRateVarCode()))
				&& facilityMasterObj.getOdExcessRateVar() != null
				&& facilityMasterObj.getOdExcessRateVar().compareTo(new Double(0)) != 0) {
			facilityMasterErrors.add("odExcessRateVarCode", new ActionMessage("error.conditional.mandatory.pair",
					"OD Excess Rate Var Code", "OD Excess Rate Var"));
		}
		else if (StringUtils.isNotBlank(StringUtils.trimToEmpty(facilityMasterObj.getOdExcessRateVarCode()))
				&& (facilityMasterObj.getOdExcessRateVar() == null || facilityMasterObj.getOdExcessRateVar().compareTo(
						new Double(0)) == 0)) {
			facilityMasterErrors.add("odExcessRateVar", new ActionMessage("error.conditional.mandatory.pair",
					"OD Excess Rate Var", "OD Excess Rate Var Code"));
		}

		// Date Compare Validation
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
		Date formApplicationDate = facilityMasterObj.getFacilityGeneral().getApplicationDate();
		Date formCancelOrRejectDate = facilityMasterObj.getFacilityGeneral().getCancelOrRejectDate();
		Date formApprovedDate = facilityMasterObj.getFacilityGeneral().getApprovedDate();
		Date formOfferDate = facilityMasterObj.getFacilityGeneral().getOfferDate();
		Date formOfferAcceptedDate = facilityMasterObj.getFacilityGeneral().getOfferAcceptedDate();
		Date formCgcBnmApprovedDate = facilityMasterObj.getCgcBnmApprovedDate();
		Date formDateInstructed = facilityMasterObj.getDateInstructed();
		Date formFacilityAvailDate = facilityMasterObj.getFacilityAvailDate();

		if (formApplicationDate != null) {
			if (DateUtil.clearTime(formApplicationDate).after(DateUtil.clearTime(DateUtil.getDate()))) {
				facilityMasterErrors.add("applicationDate", new ActionMessage("error.date.compareDate.cannotBelater",
						"Application Date", "Current Date"));
			}
			if (formCancelOrRejectDate != null
					&& DateUtil.clearTime(formApplicationDate).after(DateUtil.clearTime(formCancelOrRejectDate))) {
				facilityMasterErrors.add("applicationDate", new ActionMessage("error.date.compareDate.cannotBelater",
						"Application Date", "Canc/Rej Date"));
			}
			if (formFacilityAvailDate != null
					&& DateUtil.clearTime(formFacilityAvailDate).before(DateUtil.clearTime(formApplicationDate))) {
				facilityMasterErrors.add("facilityAvailDate", new ActionMessage(
						"error.date.compareDate.cannotBeEarlier", "Facility Aval. Date", "Application Date"));
			}
		}
		if (formCancelOrRejectDate != null) {
			if (DateUtil.clearTime(formCancelOrRejectDate).after(DateUtil.clearTime(DateUtil.getDate()))) {
				facilityMasterErrors.add("cancelOrRejectDate", new ActionMessage(
						"error.date.compareDate.cannotBelater", "Canc/Rej Date", "Current Date"));
			}
		}
		if (formApprovedDate != null) {
			if (DateUtil.clearTime(formApprovedDate).after(DateUtil.clearTime(DateUtil.getDate()))) {
				facilityMasterErrors.add("approvedDate", new ActionMessage("error.date.compareDate.cannotBelater",
						"Date Approved", "Current Date"));
			}
			if (formApplicationDate != null
					&& DateUtil.clearTime(formApprovedDate).before(DateUtil.clearTime(formApplicationDate))) {
				facilityMasterErrors.add("approvedDate", new ActionMessage("error.date.compareDate.cannotBeEarlier",
						"Date Approved", "Application Date"));
			}
			if (formCgcBnmApprovedDate != null
					&& DateUtil.clearTime(formApprovedDate).after(DateUtil.clearTime(formCgcBnmApprovedDate))) {
				facilityMasterErrors.add("approvedDate", new ActionMessage("error.date.compareDate.cannotBelater",
						"Date Approved", "Date approved by CGC/BNM"));
			}
			if (formFacilityAvailDate != null
					&& DateUtil.clearTime(formApprovedDate).after(DateUtil.clearTime(formFacilityAvailDate))) {
				facilityMasterErrors.add("approvedDate", new ActionMessage("error.date.compareDate.cannotBelater",
						"Date Approved", "Facility Aval. Date"));
			}
		}
		if (formOfferDate != null) {
			if (DateUtil.clearTime(formOfferDate).after(DateUtil.clearTime(DateUtil.getDate()))) {
				facilityMasterErrors.add("offerDate", new ActionMessage("error.date.compareDate.cannotBelater",
						"Date Of Offer", "Current Date"));
			}
			if (formApprovedDate != null
					&& DateUtil.clearTime(formOfferDate).before(DateUtil.clearTime(formApprovedDate))) {
				facilityMasterErrors.add("offerDate", new ActionMessage("error.date.compareDate.cannotBeEarlier",
						"Date Of Offer", "Date Approved"));
			}
		}
		if (formOfferAcceptedDate != null) {
			if (DateUtil.clearTime(formOfferAcceptedDate).after(DateUtil.clearTime(DateUtil.getDate()))) {
				facilityMasterErrors.add("offerAcceptedDate", new ActionMessage("error.date.compareDate.cannotBelater",
						"Date Offer Accepted", "Current Date"));
			}
			if (formOfferDate != null
					&& DateUtil.clearTime(formOfferAcceptedDate).before(DateUtil.clearTime(formOfferDate))) {
				facilityMasterErrors.add("offerAcceptedDate", new ActionMessage(
						"error.date.compareDate.cannotBeEarlier", "Date Offer Accepted", "Date Of Offer"));
			}
			if (formDateInstructed != null
					&& DateUtil.clearTime(formOfferAcceptedDate).after(DateUtil.clearTime(formDateInstructed))) {
				facilityMasterErrors.add("offerAcceptedDate", new ActionMessage("error.date.compareDate.cannotBelater",
						"Date Offer Accepted", "Date Instructed"));
			}
		}

		// Andy Wong, 4 Feb 2009: validate fac available period and date if both
		// provided
		if (facilityMasterObj.getFacilityAvailDate() != null
				&& StringUtils.isNotBlank(facilityMasterObj.getFacilityAvailPeriod())) {
			facilityMasterErrors.add("facilityAvailDate", new ActionMessage("error.facility.avail.period.date"));
		}

		if (limitProductType != null && limitProductType.equals("FS")) {
			if (!(facilityMasterObj.getRevolvingIndicator().equals("R"))) {
				facilityMasterErrors.add("revolvingIndicator", new ActionMessage("error.facility.fs.revol.ind"));
			}
			else {
				if (!(facilityMasterObj.getRevolvingOnCriteriaIndicator().equals("B"))) {
					facilityMasterErrors.add("revolvingOnCriteriaIndicator", new ActionMessage(
							"error.facility.fs.revol.criteria.ind"));
				}
			}
		}

        // Validate MLS3077 - 1. Facility term must be greater or equal to payment frequency
        //                       when both payment frequency code and loan term code is same
        if (facilityMasterObj.getLimit().getLimitTenorUnit() != null
                && facilityMasterObj.getFacilityPayment() != null
                && facilityMasterObj.getFacilityPayment().getPaymentFrequencyCodeEntryCode() != null
                && StringUtils.equals(facilityMasterObj.getLimit().getLimitTenorUnit(),
                                        facilityMasterObj.getFacilityPayment().getPaymentFrequencyCodeEntryCode())
                && facilityMasterObj.getFacilityPayment().getPaymentFrequencyEntryCode() != null
                && StringUtils.isNotBlank(facilityMasterObj.getFacilityPayment().getPaymentFrequencyEntryCode())
                && facilityMasterObj.getLimit().getLimitTenor() != null
                && facilityMasterObj.getLimit().getLimitTenor().longValue() <
                Long.valueOf(facilityMasterObj.getFacilityPayment().getPaymentFrequencyEntryCode()).longValue()) {
            facilityMasterErrors.add("term", new ActionMessage("error.term.must.greater.equal.payment.frequency"));
        }

        // Validate MLS3077 - 2. Facility term should less or equal to 480 or 999 when loan term code is set to M
        if (facilityMasterObj.getLimit().getLimitTenorUnit() != null
                && StringUtils.equals(facilityMasterObj.getLimit().getLimitTenorUnit(), ICMSConstant.FACILITY_TERM_CODE_MONTH)
                && facilityMasterObj.getLimit().getLimitTenor() != null
                && facilityMasterObj.getLimit().getLimitTenor().longValue() > 480
                && facilityMasterObj.getLimit().getLimitTenor().longValue() != 999) {
            facilityMasterErrors.add("term", new ActionMessage("error.term.if.term.code", "Month(s)"));
        }

        // Validate MLS3077 - 3. Facility term should not 0 when account type is set to O and it is the islamic product
        //                    4. Facility term should not 0 when account type is set to D and it is the islamic product
        if (StringUtils.equals(aaLawType, ICMSConstant.AA_LAW_TYPE_ISLAM)
                && facilityMasterObj.getLimit().getLimitTenor() != null
                && facilityMasterObj.getLimit().getLimitTenor().longValue() == 0) {
            if (StringUtils.equals(accountType, ICMSConstant.ACCOUNT_TYPE_O)) {
                facilityMasterErrors.add("term", new ActionMessage("error.term.if.account.type", ICMSConstant.ACCOUNT_TYPE_O));
            }
            else if (StringUtils.equals(accountType, ICMSConstant.ACCOUNT_TYPE_D)) {
                facilityMasterErrors.add("term", new ActionMessage("error.term.if.account.type", ICMSConstant.ACCOUNT_TYPE_D));
            }
        }

        // Validate MLS3077 - 5. Facility term should not 0 when account type is neither O/F/D and facility type provided
        String[] accountTypeList1 = { ICMSConstant.ACCOUNT_TYPE_O, ICMSConstant.ACCOUNT_TYPE_D, ICMSConstant.ACCOUNT_TYPE_F };
        if (StringUtils.isNotBlank(facilityMasterObj.getLimit().getProductDesc())
                && !ArrayUtils.contains(accountTypeList1, accountType)
                && facilityMasterObj.getLimit().getLimitTenor() != null
                && facilityMasterObj.getLimit().getLimitTenor().longValue() == 0) {
            facilityMasterErrors.add("term", new ActionMessage("error.term.if.account.type.is.neither"));
        }

        // Validate MLS3085 - Facility term code either D/M when facility term provide
        String[] termCodeList1 = { ICMSConstant.FACILITY_TERM_CODE_DAY, ICMSConstant.FACILITY_TERM_CODE_MONTH };
        if (facilityMasterObj.getLimit().getLimitTenor() != null
                && !ArrayUtils.contains(termCodeList1, facilityMasterObj.getLimit().getLimitTenorUnit())) {
            facilityMasterErrors.add("termCode", new ActionMessage("error.term.code.if.term.provided", "Day(s) / Month(s)"));
        }

        // Validate MLS3089 - Facility limit cannot greater than facility amount apply.
        if (facilityMasterObj.getFacilityGeneral().getFinancedAmount() != null
                && facilityMasterObj.getLimit().getApprovedLimitAmount() != null
                && facilityMasterObj.getLimit().getApprovedLimitAmount().getAmount()
                        > facilityMasterObj.getFacilityGeneral().getFinancedAmount().getAmount()) {
            facilityMasterErrors.add("approvedLimit", new ActionMessage("error.amount.not.greaterthan",
                    "Approved Limit", "Amount Applied"));
        }

        // Validate MLS3095 - 1. Facility grace period must be 0 when it is the islamic facility
        //                    2. Facility grace period code must be blank when it is the islamic facility
        if (StringUtils.equals(aaLawType, ICMSConstant.AA_LAW_TYPE_ISLAM)) {
            if (facilityMasterObj.getFacilityPayment() != null
                    && facilityMasterObj.getFacilityPayment().getGracePeriodEntryCode() != null
                    && !StringUtils.equals(facilityMasterObj.getFacilityPayment().getGracePeriodEntryCode(), "0" )) {
                facilityMasterErrors.add("gracePeriodEntryCode",
                        new ActionMessage("error.grace.period.not.allowed.if.islamic.facility"));
            }
            if (facilityMasterObj.getFacilityPayment() != null
                    && facilityMasterObj.getFacilityPayment().getGracePeriodCodeEntryCode() != null
                    && StringUtils.isNotBlank(facilityMasterObj.getFacilityPayment().getGracePeriodCodeEntryCode())) {
                facilityMasterErrors.add("gracePeriodCodeEntryCode",
                        new ActionMessage("error.grace.period.code.not.allowed.if.islamic.facility"));
            }
        }

        // Validate MLS3097 - Grace term code is either D/M if provide
        String[] gradeCodeList1 = { ICMSConstant.FACILITY_TERM_CODE_DAY, ICMSConstant.FACILITY_TERM_CODE_MONTH };
        if (facilityMasterObj.getFacilityPayment() != null
                && facilityMasterObj.getFacilityPayment().getGracePeriodCodeEntryCode() != null
                && StringUtils.isNotBlank(facilityMasterObj.getFacilityPayment().getGracePeriodCodeEntryCode())
                && !ArrayUtils.contains(termCodeList1, facilityMasterObj.getFacilityPayment().getGracePeriodCodeEntryCode())) {
            facilityMasterErrors.add("gracePeriodCodeEntryCode",
                    new ActionMessage("error.grace.period.code.must.be.values.if.provided", "Day(s) / Month(s)"));
        }

        // Validate MLS3098 - 1. Grace term require when grace term code is either D/M
        //                    2. Grace term is not require when grace term code is blanks
        if (facilityMasterObj.getFacilityPayment() != null) {
            if (facilityMasterObj.getFacilityPayment().getGracePeriodCodeEntryCode() == null
                    || StringUtils.isBlank(facilityMasterObj.getFacilityPayment().getGracePeriodCodeEntryCode())) {
                if (facilityMasterObj.getFacilityPayment().getGracePeriodEntryCode() != null
                        && !StringUtils.equals(facilityMasterObj.getFacilityPayment().getGracePeriodEntryCode(), "0" )) {
                    facilityMasterErrors.add("gracePeriodEntryCode",
                            new ActionMessage("error.grace.period.if.grace.period.code", "is not", "Blank"));
                }
            }
            else if (ArrayUtils.contains(termCodeList1, facilityMasterObj.getFacilityPayment().getGracePeriodCodeEntryCode())) {
                if (facilityMasterObj.getFacilityPayment().getGracePeriodEntryCode() == null
                        || StringUtils.equals(facilityMasterObj.getFacilityPayment().getGracePeriodEntryCode(), "0" )) {
                    facilityMasterErrors.add("gracePeriodEntryCode",
                            new ActionMessage("error.grace.period.if.grace.period.code", "", "Day(s) / Month(s)"));
                }
            }
        }

        // Validate MLS3099 - 1. Rate require when product group is HP and facility type is neither HP/HN
        String[] productTypeList1 = { ICMSConstant.PRODUCT_TYPE_HP, ICMSConstant.PRODUCT_TYPE_HN };
        if (StringUtils.equals(limitProductType, ICMSConstant.PRODUCT_GROUP_HP)
                && !ArrayUtils.contains(productTypeList1, facilityMasterObj.getLimit().getProductDesc())
                && facilityMasterObj.getFacilityInterest().getInterestRate() != null
                && facilityMasterObj.getFacilityInterest().getInterestRate().doubleValue() <= new Double(0).doubleValue()) {
			facilityMasterErrors.add("interestRate", new ActionMessage("error.interest.rate.if.product.group.facility.type",
                    ICMSConstant.PRODUCT_GROUP_HP, ICMSConstant.PRODUCT_TYPE_HP + " / " + ICMSConstant.PRODUCT_TYPE_HN));
		}        
        
        DefaultLogger.debug(" FacilityMaster Total Errors", "--------->" + facilityMasterErrors.size());

		// Officer Object , there is no need to validate this object,
		// because there is no object can be added if not going through the form
		// validator first

		// Relationship Object, there is no need to validate this object,
		// because there is no object can be added if not going through the form
		// validator first

		// Insurance Object, no mandatory fields

		return facilityMasterErrors.size() == 0 ? null : facilityMasterErrors;
	}
}
