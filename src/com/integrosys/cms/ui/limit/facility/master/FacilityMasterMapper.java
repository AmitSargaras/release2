package com.integrosys.cms.ui.limit.facility.master;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.common.NumberUtils;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;

public class FacilityMasterMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "limit", "com.integrosys.cms.app.limit.bus.ILimit", SERVICE_SCOPE },
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE }, });
	}

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		FacilityMasterForm form = (FacilityMasterForm) cForm;
		IFacilityMaster facilityMasterObj = null;
		if (facilityMasterObj == null) {
			facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
		}
		ILimit limit = (ILimit) map.get("limit");
		if (limit == null) {
			limit = facilityMasterObj.getLimit();
		}

		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		String currencyCode = limit.getApprovedLimitAmount().getCurrencyCode();
		if (facilityMasterObj == null) {
			facilityMasterObj = new OBFacilityMaster();
		}
		try {
			if (StringUtils.isNotBlank(form.getFacilityCurrencyCode())) {
				facilityMasterObj.setFacilityCurrencyCode(form.getFacilityCurrencyCode());
			}
			else {
				facilityMasterObj.setFacilityCurrencyCode(currencyCode);
			}
			facilityMasterObj.getFacilityFeeCharge().setCurrencyCode(currencyCode);
			facilityMasterObj.getFacilityGeneral().setCurrencyCode(currencyCode);

			// Product Type
			if (StringUtils.isNotBlank(form.getProductDesc())) {
				// facilityMasterObj.setProductDesc(form.getProductDesc());
				facilityMasterObj.getLimit().setProductDesc(form.getProductDesc());
			}

			// Installment Amount
			if (StringUtils.isNotBlank(form.getInstallmentAmount())) {
				facilityMasterObj.getFacilityGeneral().setInstallmentAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getInstallmentAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityGeneral().setInstallmentAmount(null);
			}

			// Drawing Limit
			if (StringUtils.isNotBlank(form.getDrawingLimitAmount())) {
				facilityMasterObj.setDrawingLimitAmount(new Amount(UIUtil.mapStringToBigDecimal(form
						.getDrawingLimitAmount()), new CurrencyCode(currencyCode)));
				facilityMasterObj.getLimit().setDrawingLimitAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getDrawingLimitAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.setDrawingLimitAmount(null);
				facilityMasterObj.getLimit().setDrawingLimitAmount(null);
			}

			// Final Payment Amount
			if (StringUtils.isNotBlank(form.getFinalPaymentAmount())) {
				facilityMasterObj.getFacilityGeneral().setFinalPaymentAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getFinalPaymentAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityGeneral().setFinalPaymentAmount(null);
			}
			// Required Security Coverage (%)
			if (StringUtils.isNotBlank(form.getRequiredSecurityCoverage())) {
				facilityMasterObj.setRequiredSecurityCoverage(form.getRequiredSecurityCoverage());  // Shiv 190911
				facilityMasterObj.getLimit().setRequiredSecurityCoverage(form.getRequiredSecurityCoverage());  // Shiv 190911
			}
			else {
				facilityMasterObj.setRequiredSecurityCoverage(null);
				facilityMasterObj.getLimit().setRequiredSecurityCoverage("0");  // Shiv 190911
			}
			facilityMasterObj.setAcfNo(form.getAcfNo());// ACF No.

			// Loan Purpose Code
			facilityMasterObj.getFacilityGeneral().setLoanPurposeCategoryCode(CategoryCodeConstant.LOAN_PURPOSE);
			facilityMasterObj.getFacilityGeneral().setLoanPurposeEntryCode(form.getLoanPurposeEntryCode());

			// Product Package Code
			facilityMasterObj.setProductPackageCodeCategoryCode(CategoryCodeConstant.PRODUCT_PACKAGE);
			facilityMasterObj.setProductPackageCodeEntryCode(form.getProductPackageCodeEntryCode());

			// Date Offer Accepted
			if (StringUtils.isNotBlank(form.getOfferAcceptedDate())) {
				facilityMasterObj.getFacilityGeneral().setOfferAcceptedDate(
						DateUtil.convertDate(locale, form.getOfferAcceptedDate()));
			}
			else {
				facilityMasterObj.getFacilityGeneral().setOfferAcceptedDate(null);
			}

			// Date Of Offer
			if (StringUtils.isNotBlank(form.getOfferDate())) {
				facilityMasterObj.getFacilityGeneral().setOfferDate(DateUtil.convertDate(locale, form.getOfferDate()));
			}
			else {
				facilityMasterObj.getFacilityGeneral().setOfferDate(null);
			}

			// Approved By
			facilityMasterObj.getFacilityGeneral().setPersonApprovedCategoryCode(CategoryCodeConstant.APPROVED_BY);
			facilityMasterObj.getFacilityGeneral().setPersonApprovedEntryCode(form.getPersonApprovedEntryCode());

			// Date Approved
			if (StringUtils.isNotBlank(form.getApprovedDate())) {
				facilityMasterObj.getFacilityGeneral().setApprovedDate(
						DateUtil.convertDate(locale, form.getApprovedDate()));
			}
			else {
				facilityMasterObj.getFacilityGeneral().setApprovedDate(null);
			}

			// Canc/Rej Code
			facilityMasterObj.getFacilityGeneral().setCancelOrRejectCategoryCode(CategoryCodeConstant.CANC_REJ_CODE);
			facilityMasterObj.getFacilityGeneral().setCancelOrRejectEntryCode(form.getCancelOrRejectEntryCode());

			// Canc/Rej Date
			if (StringUtils.isNotBlank(form.getFacilityStatusEntryCode())
					&& (ICMSConstant.FACILITY_STATUS_CANCELLED.equals(form.getFacilityStatusEntryCode()) || ICMSConstant.FACILITY_STATUS_REJECTED
							.equals(form.getFacilityStatusEntryCode()))) {
				if (StringUtils.isNotBlank(form.getCancelOrRejectDate())) {
					facilityMasterObj.getFacilityGeneral().setCancelOrRejectDate(
							DateUtil.convertDate(locale, form.getCancelOrRejectDate()));
				}
				else {
					facilityMasterObj.getFacilityGeneral().setCancelOrRejectDate(null);
				}
			}
			else {
				facilityMasterObj.getFacilityGeneral().setCancelOrRejectDate(null);
			}
			// Car Code
			facilityMasterObj.getFacilityGeneral().setCarCategoryCode(CategoryCodeConstant.CAR_CODE);
			facilityMasterObj.getFacilityGeneral().setCarEntryCode(form.getCarEntryCode());

			// Limit Status
			facilityMasterObj.getFacilityGeneral().setLimitStatusCategoryCode(CategoryCodeConstant.LMT_STATUS);
			facilityMasterObj.getFacilityGeneral().setLimitStatusEntryCode(form.getLimitStatusEntryCode());

			// Facility Status
			facilityMasterObj.getFacilityGeneral().setFacilityStatusCategoryCode(CategoryCodeConstant.FACILITY_STATUS);
			facilityMasterObj.getFacilityGeneral().setFacilityStatusEntryCode(form.getFacilityStatusEntryCode());

			// CAR Code Flag
			facilityMasterObj.getFacilityGeneral().setCarCodeFlag(
					Boolean.valueOf(ICMSConstant.YES.equals(form.getCarCodeFlag())));

			// Spread Sign
			/*
			 * if (StringUtils.isNotBlank(form.getSpreadSign())) {
			 * facilityMasterObj.getFacilityInterest().setSpreadSign(new
			 * Character(form.getSpreadSign().charAt(0)));
			 * 
			 * }
			 */

			// Andy Wong: set spread sign to NA when blank
			// facilityMasterObj.getFacilityInterest().setSpreadSign(new
			// Character(' '));
			// Rate Type
			// facilityMasterObj.getFacilityInterest().setInterestRateTypeCategoryCode(CategoryCodeConstant.FAC_RATE);
			// facilityMasterObj.getFacilityInterest().setInterestRateTypeEntryCode(form.getInterestRateTypeEntryCode());
			// Prime Rate Floor
			if (StringUtils.isNotBlank(form.getPrimeRateFloor())) {
				facilityMasterObj.getFacilityInterest().setPrimeRateFloor(Double.valueOf(form.getPrimeRateFloor()));
			}
			else {
				facilityMasterObj.getFacilityInterest().setPrimeRateFloor(null);
			}

			// Prime Rate Ceiling
			if (StringUtils.isNotBlank(form.getPrimeRateCeiling())) {
				facilityMasterObj.getFacilityInterest().setPrimeRateCeiling(Double.valueOf(form.getPrimeRateCeiling()));
			}
			else {
				facilityMasterObj.getFacilityInterest().setPrimeRateCeiling(null);
			}

			// Prime Review Date
			if (StringUtils.isNotBlank(form.getPrimeReviewDate())) {
				facilityMasterObj.getFacilityInterest().setPrimeReviewDate(
						DateUtil.convertDate(locale, form.getPrimeReviewDate()));
			}
			else {
				facilityMasterObj.getFacilityInterest().setPrimeReviewDate(null);
			}

			// Prime Review Term
			if (StringUtils.isNotBlank(form.getPrimeReviewTerm())) {
				facilityMasterObj.getFacilityInterest().setPrimeReviewTerm(Integer.valueOf(form.getPrimeReviewTerm()));
			}
			else {
				facilityMasterObj.getFacilityInterest().setPrimeReviewTerm(null);
			}

			// Prime Review Term Code
			facilityMasterObj.getFacilityInterest().setPrimeReviewTermCodeCategoryCode(
					CategoryCodeConstant.PRIME_REVIEW_TERM_CODE);
			facilityMasterObj.getFacilityInterest().setPrimeReviewTermCodeEntryCode(
					form.getPrimeReviewTermCodeEntryCode());

			// Application Source
			facilityMasterObj.setApplicationSourceCategoryCode(CategoryCodeConstant.APPLICATION_SOURCE);
			facilityMasterObj.setApplicationSourceEntryCode(form.getApplicationSourceEntryCode());

			// OD Excess Rate Var Code
			if (StringUtils.isNotBlank(form.getOdExcessRateVarCode())) {
				facilityMasterObj.setOdExcessRateVarCode(form.getOdExcessRateVarCode());
			}
			else {
				facilityMasterObj.setOdExcessRateVarCode(" ");
			}
			// OD Excess Rate Var
			if (StringUtils.isNotBlank(form.getOdExcessRateVar())) {
				facilityMasterObj.setOdExcessRateVar(Double.valueOf(form.getOdExcessRateVar()));
			}
			else {
				facilityMasterObj.setOdExcessRateVar(null);
			}
			facilityMasterObj.setDealerNumberOrLppCodeCategoryCode(CategoryCodeConstant.DEALER);

			// Dealer Number / LPP Code
			facilityMasterObj.setDealerNumberOrLppCodeEntryCode(form.getDealerNumberOrLppCodeEntryCode());

			// Commission Fees
			if (StringUtils.isNotBlank(form.getCommissionFeesAmount())) {
				facilityMasterObj.getFacilityFeeCharge().setCommissionFeesAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getCommissionFeesAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityFeeCharge().setCommissionFeesAmount(null);
			}

			// Handling Fees
			if (StringUtils.isNotBlank(form.getHandlingFeesAmount())) {
				facilityMasterObj.getFacilityFeeCharge().setHandlingFeesAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getHandlingFeesAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityFeeCharge().setHandlingFeesAmount(null);
			}

			// Subsidy Amount
			if (StringUtils.isNotBlank(form.getSubsidyAmount())) {
				facilityMasterObj.getFacilityFeeCharge().setSubsidyAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getSubsidyAmount()),
								new CurrencyCode(currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityFeeCharge().setSubsidyAmount(null);
			}

			// Others Fee
			if (StringUtils.isNotBlank(form.getOthersFeeAmount())) {
				facilityMasterObj.getFacilityFeeCharge().setOthersFeeAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getOthersFeeAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityFeeCharge().setOthersFeeAmount(null);
			}

			// Commission Rate
			if (StringUtils.isNotBlank(form.getCommissionRate())) {
				facilityMasterObj.getFacilityFeeCharge().setCommissionRate(Double.valueOf(form.getCommissionRate()));
			}
			else {
				facilityMasterObj.getFacilityFeeCharge().setCommissionRate(null);
			}

			// Commission Basis
			facilityMasterObj.getFacilityFeeCharge().setCommissionBasisCategoryCode(
					CategoryCodeConstant.COMMISSION_BASIS);
			facilityMasterObj.getFacilityFeeCharge().setCommissionBasisEntryCode(form.getCommissionBasisEntryCode());

			// Maximum Commission
			if (StringUtils.isNotBlank(form.getMaximumCommissionAmount())) {
				facilityMasterObj.getFacilityFeeCharge().setMaximumCommissionAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getMaximumCommissionAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityFeeCharge().setMaximumCommissionAmount(null);
			}

			// Minimum Commission
			if (StringUtils.isNotBlank(form.getMinimumCommissionAmount())) {
				facilityMasterObj.getFacilityFeeCharge().setMinimumCommissionAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getMinimumCommissionAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityFeeCharge().setMinimumCommissionAmount(null);
			}

			// Fac Commitment Rate
			if (StringUtils.isNotBlank(form.getFacilityCommitmentRate())) {
				facilityMasterObj.getFacilityFeeCharge().setFacilityCommitmentRate(
						Double.valueOf(form.getFacilityCommitmentRate()));
			}
			else {
				facilityMasterObj.getFacilityFeeCharge().setFacilityCommitmentRate(null);
			}

			// Fac Commitment Rate No.
			facilityMasterObj.getFacilityFeeCharge().setFacilityCommitmentRateNumberCategoryCode(
					CategoryCodeConstant.FAC_RATE);
			facilityMasterObj.getFacilityFeeCharge().setFacilityCommitmentRateNumberEntryCode(
					form.getFacilityCommitmentRateNumberEntryCode());

			// Main Facility AA No.
			facilityMasterObj.setMainFacilityAaNumber(form.getMainFacilityAaNumber());

			// Main Facility Code
			facilityMasterObj.setMainFacilityCode(form.getMainFacilityCode());

			// Main Facility Seq No.
			if (StringUtils.isNotBlank(form.getMainFacilitySequenceNumber())) {
				facilityMasterObj.setMainFacilitySequenceNumber(new Long(form.getMainFacilitySequenceNumber()));
			}
			else {
				facilityMasterObj.setMainFacilitySequenceNumber(null);
			}

			// Standby Line
			facilityMasterObj.setStandbyLine(form.getStandbyLine());
			// Revolving Non-Revolving
			if (StringUtils.isNotBlank(form.getRevolvingIndicator())) {
				facilityMasterObj.setRevolvingIndicator(form.getRevolvingIndicator());
			}

			// Rev On O/S Bal Or ORGAMT
			if (StringUtils.isNotBlank(form.getRevolvingOnCriteriaIndicator())) {
				facilityMasterObj.setRevolvingOnCriteriaIndicator(form.getRevolvingOnCriteriaIndicator());
			}
			else {
				// Andy Wong: need to set null to reset value store in DB
				facilityMasterObj.setRevolvingOnCriteriaIndicator(null);
			}

			// Floor Pledged Limit
			if (StringUtils.isNotBlank(form.getFloorPledgedLimitAmount())) {
				facilityMasterObj.setFloorPledgedLimitAmount(new Amount(UIUtil.mapStringToBigDecimal(form
						.getFloorPledgedLimitAmount()), new CurrencyCode(currencyCode)));
			}
			else {
				facilityMasterObj.setFloorPledgedLimitAmount(null);
			}

			// Retention Sum
			if (StringUtils.isNotBlank(form.getRetentionSumAmount())) {
				facilityMasterObj.setRetentionSumAmount(new Amount(UIUtil.mapStringToBigDecimal(form
						.getRetentionSumAmount()), new CurrencyCode(currencyCode)));
			}
			else {
				facilityMasterObj.setRetentionSumAmount(null);
			}

			// Retention Period
			if (StringUtils.isNotBlank(form.getRetentionPeriod())) {
				facilityMasterObj.setRetentionPeriod(Short.valueOf(form.getRetentionPeriod()));
			}
			else {
				facilityMasterObj.setRetentionPeriod(null);
			}

			// Allow Incentive
			if (StringUtils.isNotBlank(form.getAllowIncentive())) {
				facilityMasterObj.setAllowIncentive(Boolean.valueOf(form.getAllowIncentive().equals("Y")));
			}

			// Date approved by CGC/BNM
			if (StringUtils.isNotBlank(form.getCgcBnmApprovedDate())) {
				facilityMasterObj.setCgcBnmApprovedDate(DateUtil.convertDate(locale, form.getCgcBnmApprovedDate()));
			}
			else {
				facilityMasterObj.setCgcBnmApprovedDate(null);
			}

			// Product Package Code
			facilityMasterObj.setProductPackageCodeCategoryCode(CategoryCodeConstant.PRODUCT_PACKAGE);
			facilityMasterObj.setProductPackageCodeEntryCode(form.getProductPackageCodeEntryCode());

			// Effective Cost Of Fund
			if (StringUtils.isNotBlank(form.getEffectiveCostOfFund())) {
				facilityMasterObj.setEffectiveCostOfFund(Boolean.valueOf(form.getEffectiveCostOfFund().equals("Y")));
			}

			// ECOF Administration Cost
			if (StringUtils.isNotBlank(form.getEcofAdministrationCostAmount())) {
				facilityMasterObj.setEcofAdministrationCostAmount(new Amount(UIUtil.mapStringToBigDecimal(form
						.getEcofAdministrationCostAmount()), new CurrencyCode(currencyCode)));
			}
			else {
				facilityMasterObj.setEcofAdministrationCostAmount(null);
			}

			// ECOF Rate
			if (StringUtils.isNotBlank(form.getEcofRate())) {
				facilityMasterObj.setEcofRate(new Double(form.getEcofRate()));
			}
			else {
				facilityMasterObj.setEcofRate(null);
			}

			// ECOF Variance
			if (StringUtils.isNotBlank(form.getEcofVariance())) {
				facilityMasterObj.setEcofVariance(Double.valueOf(form.getEcofVariance()));
			}
			else {
				facilityMasterObj.setEcofVariance(null);
			}

			// ECOF Variance Code
			if (StringUtils.isNotBlank(form.getEcofVarianceCode())) {
				facilityMasterObj.setEcofVarianceCode(form.getEcofVarianceCode());
			}
			else {
				facilityMasterObj.setEcofVarianceCode(null);
			}

			// Facility Aval. Date
			if (StringUtils.isNotBlank(form.getFacilityAvailDate())) {
				facilityMasterObj.setFacilityAvailDate(DateUtil.convertDate(locale, form.getFacilityAvailDate()));
			}
			else {
				facilityMasterObj.setFacilityAvailDate(null);
			}

			// Facility Aval. Period
			facilityMasterObj.setFacilityAvailPeriod(form.getFacilityAvailPeriod());

			// Department Code
			facilityMasterObj.setDepartmentCodeCategoryCode(CategoryCodeConstant.DEPARTMENT);
			facilityMasterObj.setDepartmentCodeEntryCode(form.getDepartmentCodeEntryCode());

			// Refinance From Bank
			facilityMasterObj.setRefinanceFromBankCategoryCode(CategoryCodeConstant.REFINANCE_BANK);
			facilityMasterObj.setRefinanceFromBankEntryCode(form.getRefinanceFromBankEntryCode());

			// Solicitor Name
			facilityMasterObj.setSolicitorName(form.getSolicitorName());

			// term code
			if (StringUtils.isNotBlank(form.getTermCodeEntryCode())) {
				facilityMasterObj.getFacilityGeneral().setTermCodeCategoryCode("28");
				facilityMasterObj.getFacilityGeneral().setTermCodeEntryCode(form.getTermCodeEntryCode());
				facilityMasterObj.getLimit().setLimitTenorUnitNum("28");
				facilityMasterObj.getLimit().setLimitTenorUnit(form.getTermCodeEntryCode());
			}
			else {
				facilityMasterObj.getFacilityGeneral().setTermCodeCategoryCode(null);
				facilityMasterObj.getFacilityGeneral().setTermCodeEntryCode(null);
				facilityMasterObj.getLimit().setLimitTenorUnitNum(null);
				facilityMasterObj.getLimit().setLimitTenorUnit(null);
			}

			// term
			if (StringUtils.isNotBlank(form.getTerm())) {
				facilityMasterObj.getFacilityGeneral().setTerm(Integer.valueOf(form.getTerm()));
				facilityMasterObj.getLimit().setLimitTenor(Long.valueOf(form.getTerm()));
			}
			else {
				facilityMasterObj.getFacilityGeneral().setTerm(null);
				facilityMasterObj.getLimit().setLimitTenor(null);
			}

			// interest rate
			if (StringUtils.isNotBlank(form.getInterestRate())) {
				facilityMasterObj.getFacilityInterest().setInterestRate(Double.valueOf(form.getInterestRate()));
				facilityMasterObj.getLimit().setInterestRate(Double.valueOf(form.getInterestRate()));
			}
			else {
				facilityMasterObj.getFacilityInterest().setInterestRate(null);
				facilityMasterObj.getLimit().setInterestRate(null);
			}

			// interest rate type
			if (StringUtils.isNotBlank(form.getInterestRateTypeEntryCode())) {
				facilityMasterObj.getFacilityInterest().setInterestRateTypeCategoryCode(CategoryCodeConstant.FAC_RATE);
				facilityMasterObj.getFacilityInterest().setInterestRateTypeEntryCode(
						form.getInterestRateTypeEntryCode());
			}
			else {
				facilityMasterObj.getFacilityInterest().setInterestRateTypeCategoryCode(null);
				facilityMasterObj.getFacilityInterest().setInterestRateTypeEntryCode(null);
			}

			// spread
			if (StringUtils.isNotBlank(form.getSpread())) {
				facilityMasterObj.getFacilityInterest().setSpread(Double.valueOf(form.getSpread()));
			}
			else {
				facilityMasterObj.getFacilityInterest().setSpread(null);
			}

			// spread sign
			if (form.getSpreadSign() != null) {
				facilityMasterObj.getFacilityInterest()
						.setSpreadSign(CharUtils.toCharacterObject(form.getSpreadSign()));
			}
			else {
				facilityMasterObj.getFacilityInterest().setSpreadSign(null);
			}

			// grace period
			if (StringUtils.isNotBlank(form.getGracePeriodEntryCode())) {
				facilityMasterObj.getFacilityPayment().setGracePeriodEntryCode(form.getGracePeriodEntryCode());
			}
			else {
				facilityMasterObj.getFacilityPayment().setGracePeriodEntryCode(null);
			}

			// grace code
			if (StringUtils.isNotBlank(form.getGracePeriodCodeEntryCode())) {
				facilityMasterObj.getFacilityPayment().setGracePeriodCodeCategoryCode("28");
				facilityMasterObj.getFacilityPayment().setGracePeriodCodeEntryCode(form.getGracePeriodCodeEntryCode());
			}
			else {
				facilityMasterObj.getFacilityPayment().setGracePeriodCodeCategoryCode(null);
				facilityMasterObj.getFacilityPayment().setGracePeriodCodeEntryCode(null);
			}

			// financed amount
			if (StringUtils.isNotBlank(form.getFinancedAmount())) {
				facilityMasterObj.getFacilityGeneral().setFinancedAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getFinancedAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityGeneral().setFinancedAmount(null);
			}

			// approved amount
			if (StringUtils.isNotBlank(form.getApprovedLimitAmount())) {
				Amount approvedAmount = new Amount(UIUtil.mapStringToBigDecimal(form.getApprovedLimitAmount()),
						new CurrencyCode(currencyCode));
				facilityMasterObj.getFacilityGeneral().setApprovedAmount(approvedAmount);
				facilityMasterObj.getLimit().setApprovedLimitAmount(approvedAmount);
			}
			else {
				facilityMasterObj.getFacilityGeneral().setApprovedAmount(null);
				facilityMasterObj.getLimit().setApprovedLimitAmount(null);
			}

			// officer
			if (StringUtils.isNotBlank(form.getOfficerEntryCode())) {
				facilityMasterObj.getFacilityGeneral().setOfficerCategoryCode(CategoryCodeConstant.OFFICER);
				facilityMasterObj.getFacilityGeneral().setOfficerEntryCode(form.getOfficerEntryCode());
			}
			else {
				facilityMasterObj.getFacilityGeneral().setOfficerCategoryCode(null);
				facilityMasterObj.getFacilityGeneral().setOfficerEntryCode(null);
			}
		}
		catch (RuntimeException e) {
			throw new MapperException("failed to map form values to ob", e);
		}

		return facilityMasterObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		FacilityMasterForm form = (FacilityMasterForm) cForm;
		IFacilityMaster facilityMaster = (IFacilityMaster) obj;
		String currencyCode = (facilityMaster.getLimit().getApprovedLimitAmount() != null ? facilityMaster.getLimit()
				.getApprovedLimitAmount().getCurrencyCode() : "");
		IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
		int decPlaces = 2;
		boolean withCCY = false;
		try {
			form.setOrganisationCode(facilityMaster.getLimit().getBookingLocation().getOrganisationCode());
			// form.setProductDescNum(facilityMaster.getLimit().getProductDescNum());
			form.setProductDesc(facilityMaster.getLimit().getProductDesc());
			if (facilityMaster.getFacilityGeneral().getFinancedAmount() != null) {
				form.setFinancedAmount(UIUtil.formatAmount(facilityMaster.getFacilityGeneral().getFinancedAmount(),
						decPlaces, locale, withCCY));
			}

			form.setEnteredDate(facilityMaster.getFacilityGeneral().getEnteredDate() != null ? DateUtil.formatDate(
					locale, facilityMaster.getFacilityGeneral().getEnteredDate()) : null);

			form.setApplicationDate(facilityMaster.getFacilityGeneral().getApplicationDate() != null ? DateUtil
					.formatDate(locale, facilityMaster.getFacilityGeneral().getApplicationDate()) : null);

			if (facilityMaster.getFacilityGeneral().getInstallmentAmount() != null) {
				form.setInstallmentAmount(UIUtil.formatAmount(facilityMaster.getFacilityGeneral()
						.getInstallmentAmount(), decPlaces, locale, withCCY));
			}

			if (facilityMaster.getFacilityGeneral().getFinalPaymentAmount() != null) {
				form.setFinalPaymentAmount(UIUtil.formatAmount(facilityMaster.getFacilityGeneral()
						.getFinalPaymentAmount(), decPlaces, locale, withCCY));
			}

			// for those values interchangeable between facility master and
			// limits.
			if ((facilityTrxValue == null) || ICMSConstant.STATE_ACTIVE.equals(facilityTrxValue.getStatus())) {
				if (facilityMaster.getLimit().getDrawingLimitAmount() != null) {
					form.setDrawingLimitAmount(UIUtil.formatAmount(facilityMaster.getLimit().getDrawingLimitAmount(),
							decPlaces, locale, withCCY));
				}

				if (facilityMaster.getLimit().getRequiredSecurityCoverage() != null) {
					form.setRequiredSecurityCoverage(String.valueOf(facilityMaster.getLimit()    //Shiv 190911
							.getRequiredSecurityCoverage()));
				}

				if (facilityMaster.getLimit().getProductDesc() != null) {
					form.setProductDesc(facilityMaster.getLimit().getProductDesc());
				}

				if (facilityMaster.getLimit().getLimitTenorUnit() != null) {
					form.setTermCodeEntryCode(facilityMaster.getLimit().getLimitTenorUnit());
				}

				if (facilityMaster.getLimit().getLimitTenor() != null) {
					form.setTerm(String.valueOf(facilityMaster.getLimit().getLimitTenor()));
				}

				if (facilityMaster.getLimit().getApprovedLimitAmount() != null) {
					form.setApprovedLimitAmount(UIUtil.formatAmount(facilityMaster.getLimit().getApprovedLimitAmount(),
							decPlaces, locale, withCCY));
				}
			}
			else {
				if (facilityMaster.getDrawingLimitAmount() != null) {
					form.setDrawingLimitAmount(UIUtil.formatAmount(facilityMaster.getDrawingLimitAmount(), decPlaces,
							locale, withCCY));
				}

				if (facilityMaster.getRequiredSecurityCoverage() != null) {
					form.setRequiredSecurityCoverage(String.valueOf(facilityMaster.getRequiredSecurityCoverage()));
				}
				else {
					form.setRequiredSecurityCoverage("0");
				}

				if (facilityMaster.getLimit().getProductDesc() != null) {
					// form.setProductDesc(facilityMaster.getProductDesc());
					form.setProductDesc(facilityMaster.getLimit().getProductDesc());
				}

				if (facilityMaster.getFacilityGeneral().getTermCodeEntryCode() != null) {
					form.setTermCodeEntryCode(facilityMaster.getFacilityGeneral().getTermCodeEntryCode());
				}

				if (facilityMaster.getFacilityGeneral().getTerm() != null) {
					form.setTerm(facilityMaster.getFacilityGeneral().getTerm().toString());
				}

				if (facilityMaster.getFacilityGeneral().getApprovedAmount() != null) {
					form.setApprovedLimitAmount(UIUtil.formatAmount(facilityMaster.getFacilityGeneral()
							.getApprovedAmount(), decPlaces, locale, withCCY));
				}
			}
			form.setAcfNo(facilityMaster.getAcfNo());

			form.setLoanPurposeEntryCode(facilityMaster.getFacilityGeneral().getLoanPurposeEntryCode());

			form.setProductPackageCodeEntryCode(facilityMaster.getProductPackageCodeEntryCode());

			form.setOfferAcceptedDate(facilityMaster.getFacilityGeneral().getOfferAcceptedDate() != null ? DateUtil
					.formatDate(locale, facilityMaster.getFacilityGeneral().getOfferAcceptedDate()) : null);

			form.setOfferDate(facilityMaster.getFacilityGeneral().getOfferDate() != null ? DateUtil.formatDate(locale,
					facilityMaster.getFacilityGeneral().getOfferDate()) : null);

			form.setPersonApprovedEntryCode(facilityMaster.getFacilityGeneral().getPersonApprovedEntryCode());

			form.setApprovedDate(facilityMaster.getFacilityGeneral().getApprovedDate() != null ? DateUtil.formatDate(
					locale, facilityMaster.getFacilityGeneral().getApprovedDate()) : null);

			form.setCancelOrRejectEntryCode(facilityMaster.getFacilityGeneral().getCancelOrRejectEntryCode());

			form.setCancelOrRejectDate(facilityMaster.getFacilityGeneral().getCancelOrRejectDate() != null ? DateUtil
					.formatDate(locale, facilityMaster.getFacilityGeneral().getCancelOrRejectDate()) : null);

			form.setCarEntryCode(facilityMaster.getFacilityGeneral().getCarEntryCode());

			form.setOfficerEntryCode(facilityMaster.getFacilityGeneral().getOfficerEntryCode());

			form.setLimitStatusEntryCode(facilityMaster.getFacilityGeneral().getLimitStatusEntryCode());

			form.setFacilityStatusEntryCode(facilityMaster.getFacilityGeneral().getFacilityStatusEntryCode());

			if (facilityMaster.getFacilityInterest().getInterestRate() != null) {
				form.setInterestRate(NumberUtils.formatBigDecimalValue(new BigDecimal(Double.toString(facilityMaster
						.getFacilityInterest().getInterestRate().doubleValue()))));
			}

			if (facilityMaster.getFacilityGeneral().getCarCodeFlag() != null) {
				form.setCarCodeFlag(facilityMaster.getFacilityGeneral().getCarCodeFlag().booleanValue() ? "Y" : "N");
			}
			else {
				form.setCarCodeFlag("N");
			}

			form.setInterestRateTypeEntryCode(facilityMaster.getFacilityInterest().getInterestRateTypeEntryCode());

			if (facilityMaster.getFacilityInterest().getSpread() != null) {
				form.setSpread(facilityMaster.getFacilityInterest().getSpread().toString());
			}

			if (facilityMaster.getFacilityInterest().getSpreadSign() != null) {
				form.setSpreadSign(facilityMaster.getFacilityInterest().getSpreadSign().toString());
			}

			if (facilityMaster.getFacilityInterest().getPrimeRateFloor() != null) {
				form.setPrimeRateFloor(String.valueOf(facilityMaster.getFacilityInterest().getPrimeRateFloor()));
			}
			else {
				form.setPrimeRateFloor("0");
			}

			if (facilityMaster.getFacilityInterest().getPrimeRateCeiling() != null) {
				form.setPrimeRateCeiling(String.valueOf(facilityMaster.getFacilityInterest().getPrimeRateCeiling()));
			}
			else {
				form.setPrimeRateCeiling("0");
			}

			form.setPrimeReviewDate(facilityMaster.getFacilityInterest().getPrimeReviewDate() != null ? DateUtil
					.formatDate(locale, facilityMaster.getFacilityInterest().getPrimeReviewDate()) : null);

			if (facilityMaster.getFacilityInterest().getPrimeReviewTerm() != null) {
				form.setPrimeReviewTerm(String.valueOf(facilityMaster.getFacilityInterest().getPrimeReviewTerm()));
			}
			else {
				form.setPrimeReviewTerm("0");
			}

			String primeReviewTermCodeEntryCode = facilityMaster.getFacilityInterest()
					.getPrimeReviewTermCodeEntryCode();
			form.setPrimeReviewTermCodeEntryCode(primeReviewTermCodeEntryCode);

			form.setApplicationSourceEntryCode(facilityMaster.getApplicationSourceEntryCode());

			form.setFacilityCurrencyCode(currencyCode);

			form.setOdExcessRateVarCode(String.valueOf(facilityMaster.getOdExcessRateVarCode()));
			if ((facilityMaster.getOdExcessRateVar() != null)
					&& (facilityMaster.getOdExcessRateVar().doubleValue() > 0)) {
				form.setOdExcessRateVar(String.valueOf(facilityMaster.getOdExcessRateVar()));
			}
			else {
				form.setOdExcessRateVar("0");
			}

			form.setDealerNumberOrLppCodeEntryCode(facilityMaster.getDealerNumberOrLppCodeEntryCode());

			form.setLateChargeType(String.valueOf(facilityMaster.getFacilityFeeCharge().getLateChargeType()));

			if (facilityMaster.getFacilityFeeCharge().getCommissionFeesAmount() != null) {
				form.setCommissionFeesAmount(UIUtil.formatAmount(facilityMaster.getFacilityFeeCharge()
						.getCommissionFeesAmount(), decPlaces, locale, withCCY));
			}
			if (facilityMaster.getFacilityFeeCharge().getHandlingFeesAmount() != null) {
				form.setHandlingFeesAmount(UIUtil.formatAmount(facilityMaster.getFacilityFeeCharge()
						.getHandlingFeesAmount(), decPlaces, locale, withCCY));
			}

			if (facilityMaster.getFacilityFeeCharge().getSubsidyAmount() != null) {
				form.setSubsidyAmount(UIUtil.formatAmount(facilityMaster.getFacilityFeeCharge().getSubsidyAmount(),
						decPlaces, locale, withCCY));
			}

			if (facilityMaster.getFacilityFeeCharge().getOthersFeeAmount() != null) {
				form.setOthersFeeAmount(UIUtil.formatAmount(facilityMaster.getFacilityFeeCharge().getOthersFeeAmount(),
						decPlaces, locale, withCCY));
			}

			if (facilityMaster.getFacilityFeeCharge().getCommissionRate() != null) {
				form.setCommissionRate(String.valueOf(facilityMaster.getFacilityFeeCharge().getCommissionRate()));
			}
			else {
				form.setCommissionRate("0");
			}

			form.setCommissionBasisEntryCode(facilityMaster.getFacilityFeeCharge().getCommissionBasisEntryCode());

			if (facilityMaster.getFacilityFeeCharge().getMaximumCommissionAmount() != null) {
				form.setMaximumCommissionAmount(UIUtil.formatAmount(facilityMaster.getFacilityFeeCharge()
						.getMaximumCommissionAmount(), decPlaces, locale, withCCY));
			}

			if (facilityMaster.getFacilityFeeCharge().getMinimumCommissionAmount() != null) {
				form.setMinimumCommissionAmount(UIUtil.formatAmount(facilityMaster.getFacilityFeeCharge()
						.getMinimumCommissionAmount(), decPlaces, locale, withCCY));
			}

			form.setStandByLineFacilityCode(facilityMaster.getStandByLineFacilityCode());

			String standByLineFacilityCodeSequence = String
					.valueOf(facilityMaster.getStandByLineFacilityCodeSequence());
			form.setStandByLineFacilityCodeSequence(standByLineFacilityCodeSequence);

			form.setLimitExpiryDate(facilityMaster.getLimit().getLimitExpiryDate() != null ? DateUtil.formatDate(
					locale, facilityMaster.getLimit().getLimitExpiryDate()) : null);
			if (facilityMaster.getFacilityFeeCharge().getFacilityCommitmentRate() != null) {
				form.setFacilityCommitmentRate(String.valueOf(facilityMaster.getFacilityFeeCharge()
						.getFacilityCommitmentRate()));
			}
			else {
				form.setFacilityCommitmentRate("0");
			}

			form.setFacilityCommitmentRateNumberEntryCode(facilityMaster.getFacilityFeeCharge()
					.getFacilityCommitmentRateNumberEntryCode());

			form.setLastMaintenanceDate(facilityMaster.getLastMaintenanceDate() != null ? DateUtil.formatDate(locale,
					facilityMaster.getLastMaintenanceDate()) : null);

			if (facilityMaster.getFacilityGeneral().getOriginalAmount() != null) {
				form.setOriginalAmount(UIUtil.formatAmount(facilityMaster.getFacilityGeneral().getOriginalAmount(),
						decPlaces, locale, withCCY));
			}

			form.setMainFacility(facilityMaster.getMainFacility());

			form.setMainFacilityAaNumber(facilityMaster.getMainFacilityAaNumber());

			form.setMainFacilityCode(facilityMaster.getMainFacilityCode());

			if (facilityMaster.getMainFacilitySequenceNumber() != null) {
				form.setMainFacilitySequenceNumber(String.valueOf(facilityMaster.getMainFacilitySequenceNumber()));
			}
			else {
				form.setMainFacilitySequenceNumber("0");
			}

			form.setStandbyLine(facilityMaster.getStandbyLine());

			form.setLevel(String.valueOf(facilityMaster.getLevel()));

			if (facilityMaster.getFacilityGeneral().getUtilisedAmount() != null) {
				form.setUtilisedAmount(UIUtil.formatAmount(facilityMaster.getFacilityGeneral().getUtilisedAmount(),
						decPlaces, locale, withCCY));
			}

			form.setSpecProvision(String.valueOf(facilityMaster.getSpecProvision()));

			form.setIntInSuspense(String.valueOf(facilityMaster.getIntInSuspense()));

			if (facilityMaster.getFacilityGeneral().getOustandingBalanceAmount() != null) {
				form.setOustandingBalanceAmount(UIUtil.formatAmount(facilityMaster.getFacilityGeneral()
						.getOustandingBalanceAmount(), decPlaces, locale, withCCY));
			}

			form.setPaymentCodeEntryCode(facilityMaster.getFacilityPayment().getPaymentCodeEntryCode());

			form.setPaymentFrequencyEntryCode(facilityMaster.getFacilityPayment().getPaymentFrequencyEntryCode());

			form.setPaymentFrequencyCodeEntryCode(facilityMaster.getFacilityPayment()
					.getPaymentFrequencyCodeEntryCode());

			form.setInterestPaymentFrequencyEntryCode(facilityMaster.getFacilityPayment()
					.getInterestPaymentFrequencyEntryCode());

			form.setInterestPaymentFrequencyCodeEntryCode(facilityMaster.getFacilityPayment()
					.getInterestPaymentFrequencyCodeEntryCode());

			form.setGracePeriodEntryCode(facilityMaster.getFacilityPayment().getGracePeriodEntryCode());

			form.setGracePeriodCodeEntryCode(facilityMaster.getFacilityPayment().getGracePeriodCodeEntryCode());

			form.setRevolvingIndicator(String.valueOf(facilityMaster.getRevolvingIndicator()));

			form.setRevolvingOnCriteriaIndicator(String.valueOf(facilityMaster.getRevolvingOnCriteriaIndicator()));

			if (facilityMaster.getFloorPledgedLimitAmount() != null) {
				form.setFloorPledgedLimitAmount(UIUtil.formatAmount(facilityMaster.getFloorPledgedLimitAmount(),
						decPlaces, locale, withCCY));
			}

			if (facilityMaster.getActualPledgedLimitAmount() != null) {
				form.setActualPledgedLimitAmount(UIUtil.formatAmount(facilityMaster.getActualPledgedLimitAmount(),
						decPlaces, locale, withCCY));
			}

			if (facilityMaster.isAltSchedule() != null) {
				form.setAltSchedule(facilityMaster.isAltSchedule().booleanValue());
			}

			form.setAvailPeriodInMonths(String.valueOf(facilityMaster.getAvailPeriodInMonths()));

			form.setAvailPeriodInDays(String.valueOf(facilityMaster.getAvailPeriodInDays()));

			if (facilityMaster.getRetentionSumAmount() != null) {
				form.setRetentionSumAmount(UIUtil.formatAmount(facilityMaster.getRetentionSumAmount(), decPlaces,
						locale, withCCY));
			}

			if (facilityMaster.getRetentionPeriod() != null) {
				form.setRetentionPeriod(String.valueOf(facilityMaster.getRetentionPeriod()));
			}
			else {
				form.setRetentionPeriod("0");
			}

			form.setRetentionPeriodCode(facilityMaster.getRetentionPeriodCode());

			form.setDisbursementManner(facilityMaster.getDisbursementManner());

			form.setCalFirstInstlDate(facilityMaster.getCalFirstInstlDate());

			form.setIsoReferralNumber(facilityMaster.getIsoReferralNumber());

			if (facilityMaster.getAllowIncentive() != null) {
				form.setAllowIncentive(facilityMaster.getAllowIncentive().booleanValue() ? ICMSConstant.TRUE_VALUE
						: ICMSConstant.FALSE_VALUE);
			}

			form.setCgcBnmApprovedDate(facilityMaster.getCgcBnmApprovedDate() != null ? DateUtil.formatDate(locale,
					facilityMaster.getCgcBnmApprovedDate()) : null);

			form.setAlternateRate(facilityMaster.getAlternateRate());

			form.setCreditLineIndicator(String.valueOf(facilityMaster.getCreditLineIndicator()));

			if (facilityMaster.getEffectiveCostOfFund() != null) {
				boolean isEffectiveCostOfFund = facilityMaster.getEffectiveCostOfFund().booleanValue();
				form.setEffectiveCostOfFund(isEffectiveCostOfFund ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
			}

			if (facilityMaster.getEcofAdministrationCostAmount() != null) {
				form.setEcofAdministrationCostAmount(UIUtil.formatAmount(facilityMaster
						.getEcofAdministrationCostAmount(), decPlaces, locale, withCCY));
			}

			if (facilityMaster.getEcofRate() != null) {
				form.setEcofRate(String.valueOf(facilityMaster.getEcofRate()));
			}
			else {
				form.setEcofRate("0");
			}

			// Andy Wong, 22 Jan 2009: default ecof variance to 0 when blank
			if (facilityMaster.getEcofVariance() != null) {
				form.setEcofVariance(String.valueOf(facilityMaster.getEcofVariance()));
			}
			else {
				form.setEcofVariance("0");
			}

			// Andy Wong, 22 Jan 2009: default ecof variance code to NA when
			// blank
			if (StringUtils.isNotBlank(facilityMaster.getEcofVarianceCode())) {
				form.setEcofVarianceCode(String.valueOf(facilityMaster.getEcofVarianceCode()));
			}
			else {
				form.setEcofVarianceCode("");
			}

			form.setFacilityAvailDate(facilityMaster.getFacilityAvailDate() != null ? DateUtil.formatDate(locale,
					facilityMaster.getFacilityAvailDate()) : null);

			form.setFacilityAvailPeriod(facilityMaster.getFacilityAvailPeriod());

			form.setDateInstructed(facilityMaster.getDateInstructed() != null ? DateUtil.formatDate(locale,
					facilityMaster.getDateInstructed()) : null);

			form.setSolicitorReference(facilityMaster.getSolicitorReference());

			form.setDepartmentCodeEntryCode(facilityMaster.getDepartmentCodeEntryCode());

			form.setRefinanceFromBankEntryCode(facilityMaster.getRefinanceFromBankEntryCode());

			form.setSolicitorName(facilityMaster.getSolicitorName());

			form.setLawyerCodeEntryCode(facilityMaster.getLawyerCodeEntryCode());

			form.setParValueShares(facilityMaster.getParValueShares());
		}
		catch (RuntimeException e) {
			throw new MapperException("failed to map ob values into form", e);
		}
		catch (Exception e) {
			throw new MapperException("failed to format amount from object to display values", e);
		}
		return form;
	}
}
