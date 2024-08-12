package com.integrosys.cms.ui.limit.facility.islamic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityIslamicMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityIslamicMaster;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;

public class FacilityIslamicMasterMapper extends AbstractCommonMapper {
	private int defaultValue = -1;
	private Amount defaultValueAmount = new Amount(-1,"MYR");

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				});
	}

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		FacilityIslamicMasterForm form = (FacilityIslamicMasterForm) cForm;
		IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");

		ILimit limit = (ILimit) map.get("limit");
		if (limit == null) {
			limit = facilityMasterObj.getLimit();
		}
		
		if (facilityMasterObj.getFacilityIslamicMaster() == null) {
			facilityMasterObj.setFacilityIslamicMaster(new OBFacilityIslamicMaster());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");

		String currencyCode = limit.getApprovedLimitAmount().getCurrencyCode();
		
		if (facilityMasterObj == null) {
			facilityMasterObj = new OBFacilityMaster();
		}
		try {
			facilityMasterObj.getFacilityIslamicMaster().setCurrencyCode(currencyCode);
			
			facilityMasterObj.getFacilityIslamicMaster().setGppPaymentModeNum(CategoryCodeConstant.GPP_PAYMENT_MODE);
			facilityMasterObj.getFacilityIslamicMaster().setGppPaymentModeValue(form.getGppPaymentModeValue());
			
			// GPP Calculation Method
			facilityMasterObj.getFacilityIslamicMaster().setGppCalculationMethodNum(CategoryCodeConstant.GPP_CALCULATION_METHOD);
			facilityMasterObj.getFacilityIslamicMaster().setGppCalculationMethodValue(form.getGppCalculationMethodValue());
			
			// GPP Term
			if (StringUtils.isNotBlank(form.getGppTerm())) {
				facilityMasterObj.getFacilityIslamicMaster().setGppTerm(new Short(form.getGppTerm()));
			} 
			else {
				facilityMasterObj.getFacilityIslamicMaster().setGppTerm(null);
			}
			
			// GPP Term Code
			if (StringUtils.isNotBlank(form.getGppTermCode())  && form.getGppTermCode().length() != 0) {
				facilityMasterObj.getFacilityIslamicMaster().setGppTermCode(new Character(form.getGppTermCode().charAt(0)));
			} 
			else {
				facilityMasterObj.getFacilityIslamicMaster().setGppTermCode(new Character(ICMSConstant.GPP_TERM_CODE_M.charAt(0)));
			}
			
			// Security Dep # of Month
			if (StringUtils.isNotBlank(form.getSecurityDepOfMonth())) {
				facilityMasterObj.getFacilityIslamicMaster().setSecurityDepOfMonth(new Short(form.getSecurityDepOfMonth()));
			} 
			else {
				facilityMasterObj.getFacilityIslamicMaster().setSecurityDepOfMonth(null);
			}
			
			// Security Dep %
			if (StringUtils.isNotBlank(form.getSecurityDepPercentage())) {
				facilityMasterObj.getFacilityIslamicMaster().setSecurityDepPercentage(new Double(form.getSecurityDepPercentage()));
			} 
			else {
				facilityMasterObj.getFacilityIslamicMaster().setSecurityDepPercentage(null);
			}
			
			// Security Dep Amount
			if (StringUtils.isNotBlank(form.getSecurityDepAmount())) {
				facilityMasterObj.getFacilityIslamicMaster().setSecurityDepAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getSecurityDepAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityIslamicMaster().setSecurityDepAmount(null);
			}
			
			// Pricing - Tier 1 Rate *
			if (StringUtils.isNotBlank(form.getCustomerInterestRate())) {
				facilityMasterObj.getFacilityIslamicMaster().
					setCustomerInterestRate(Double.valueOf(form.getCustomerInterestRate()));
			} else {
				facilityMasterObj.getFacilityIslamicMaster().setCustomerInterestRate(null);
			}
			
			// Fulrel profit calculation method
			facilityMasterObj.getFacilityIslamicMaster().setFulrelProfitCalcMethod(form.getFulrelProfitCalcMethod());

			// Fixed Amount Refund

			// Compounding Method *
			if (StringUtils.isNotBlank(form.getCompoundingMethod())) {
				facilityMasterObj.getFacilityIslamicMaster().setCompoundingMethod(Boolean.valueOf(form.getCompoundingMethod().equals("Y")));
			}

			// Date to Stop Compounding
			if (StringUtils.isNotBlank(form.getDateStopCompounding())) {
				facilityMasterObj.getFacilityIslamicMaster().setDateStopCompounding(sdf.parse(form.getDateStopCompounding()));
			}
			else {
				facilityMasterObj.getFacilityIslamicMaster().setDateStopCompounding(null);
			}			

			// Refund - GPP Profit
			facilityMasterObj.getFacilityIslamicMaster().setRefundGppProfitNum(CategoryCodeConstant.REFUND_GPP_PROFIT);
			facilityMasterObj.getFacilityIslamicMaster().setRefundGppProfitValue(form.getRefundGppProfitValue());

			// Refund - Fulrel Profit
			facilityMasterObj.getFacilityIslamicMaster().setRefundFulrelProfitNum(CategoryCodeConstant.REFUND_FULREL_PROFIT);
			facilityMasterObj.getFacilityIslamicMaster().setRefundFulrelProfitValue(form.getRefundFulrelProfitValue());
			
			// Commission Rate
			try {
				facilityMasterObj.getFacilityIslamicMaster().setCommissionRate(Double.valueOf(form.getCommissionRate()));
			} catch (Exception e) {
				facilityMasterObj.getFacilityIslamicMaster().setCommissionRate(null);
			}
			
			// Fixed Commission Amount
			if (StringUtils.isNotBlank(form.getFixedCommissionAmount())) {
				facilityMasterObj.getFacilityIslamicMaster().setFixedCommissionAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getFixedCommissionAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityIslamicMaster().setFixedCommissionAmount(null);
			}
			
			// Commission Term
			if (StringUtils.isNotBlank(form.getCommissionTerm())) {
				facilityMasterObj.getFacilityIslamicMaster().setCommissionTerm(new Short(form.getCommissionTerm()));
			} else {
				facilityMasterObj.getFacilityIslamicMaster().setCommissionTerm(null);
			}
			
			// Commission Expiry Date
			if (StringUtils.isNotBlank(form.getCommissionExpiryDate())) {
				facilityMasterObj.getFacilityIslamicMaster().setCommissionExpiryDate(sdf.parse(form.getCommissionExpiryDate()));
			}
			else {
				facilityMasterObj.getFacilityIslamicMaster().setCommissionExpiryDate(null);
			}
			
			// Exc CMP in PMT/AMT *
			if (StringUtils.isNotBlank(form.getExcCmpInPmtAmt())) {
				facilityMasterObj.getFacilityIslamicMaster().setExcCmpInPmtAmt(Boolean.valueOf(form.getExcCmpInPmtAmt().equals("Y")));
			}
			
			// SPTF Dual Repayment Mode
			facilityMasterObj.getFacilityIslamicMaster().setSptfDualPaymentModeNum(CategoryCodeConstant.SPTF_DUAL_PAYMENT_MODE);
			facilityMasterObj.getFacilityIslamicMaster().setSptfDualPaymentModeValue(form.getSptfDualPaymentModeValue());			
			
			// S&P Agreement Date
			if (StringUtils.isNotBlank(form.getSnpAgreementDate())) {
				facilityMasterObj.getFacilityIslamicMaster().setSnpAgreementDate(sdf.parse(form.getSnpAgreementDate()));
			}
			else {
				facilityMasterObj.getFacilityIslamicMaster().setSnpAgreementDate(null);
			}

			// Full Rel Pft on 1/12 Mtd *
			if (StringUtils.isNotBlank(form.getFullRelPft12Method())) {
				facilityMasterObj.getFacilityIslamicMaster().setFullRelPft12Method(Boolean.valueOf(form.getFullRelPft12Method().equals("Y")));
			}
			
			// Fixed Refund Amount
			if (StringUtils.isNotBlank(form.getFixedRefundAmount())) {
				facilityMasterObj.getFacilityIslamicMaster().setFixedRefundAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getFixedRefundAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityIslamicMaster().setFixedRefundAmount(null);
			}

            // S&P Term
			if (StringUtils.isNotBlank(form.getSnpTerm())) {
				facilityMasterObj.getFacilityIslamicMaster().setSnpTerm(new Short(form.getSnpTerm()));
            } else {
                facilityMasterObj.getFacilityIslamicMaster().setSnpTerm(null);
            }

			// S&P Term Code
			facilityMasterObj.getFacilityIslamicMaster().setSnpTermCodeValue(form.getSnpTermCodeValue());

            // GPP duration for S&P date
            facilityMasterObj.getFacilityIslamicMaster().setGppDurationForSnp(
                    StringUtils.isNotBlank(form.getGppDurationForSnp())?new Character(form.getGppDurationForSnp().charAt(0)):null);
		}
		catch (ParseException e) {
			MapperException me = new MapperException("failed to parse string to object when map form to ob");
			me.initCause(e);
			throw me;
		}
		catch (RuntimeException e) {
			MapperException me = new MapperException("failed to map form values to ob");
			me.initCause(e);
			throw me;
		}

		return facilityMasterObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		FacilityIslamicMasterForm form = (FacilityIslamicMasterForm) cForm;
		IFacilityIslamicMaster facilityIslamic = (IFacilityIslamicMaster) obj;
		IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
		int decPlaces = 2;
		boolean withCCY = false;
		try {
			
			form.setGppPaymentModeValue(facilityIslamic.getGppPaymentModeValue());
			form.setGppCalculationMethodValue(facilityIslamic.getGppCalculationMethodValue());
			
			if ( facilityIslamic.getGppTerm() != null) {
				form.setGppTerm(String.valueOf(facilityIslamic.getGppTerm()));
			}
			
			if (facilityIslamic.getGppTermCode() == null) {
				form.setGppTermCode(ICMSConstant.GPP_TERM_CODE_M);
			} else {
                form.setGppTermCode(String.valueOf(facilityIslamic.getGppTermCode()));
            }
			
			// Selling Price 
			if (facilityIslamic.getSellingPriceAmount() != null) {
				form.setSellingPriceAmount(UIUtil.formatAmount(facilityIslamic
						.getSellingPriceAmount(), decPlaces, locale, withCCY));
			}
			
			// Total GPP Amount
			if (facilityIslamic.getGppTotalAmount() != null) {
				form.setGppTotalAmount(UIUtil.formatAmount(facilityIslamic
						.getGppTotalAmount(), decPlaces, locale, withCCY));
			}
			
			// Security Dep # of Month
			if (facilityIslamic.getSecurityDepOfMonth() != null) {
				form.setSecurityDepOfMonth(String.valueOf(facilityIslamic.getSecurityDepOfMonth()));
			}
			
			// Security Dep % -- TODO Double
			if (facilityIslamic.getSecurityDepPercentage() != null) {
				form.setSecurityDepPercentage(String.valueOf(facilityIslamic.getSecurityDepPercentage()));
			}			
			
			// Security Dep Amount
			if (facilityIslamic.getSecurityDepAmount() != null) {
				form.setSecurityDepAmount(UIUtil.formatAmount(facilityIslamic
						.getSecurityDepAmount(), decPlaces, locale, withCCY));
			}
			
			// Pricing - Tier 1 Rate *
			if (facilityIslamic.getCustomerInterestRate() != null) {
				form.setCustomerInterestRate(String.valueOf(facilityIslamic.getCustomerInterestRate()));
			}
			
			// Fulrel profit calculation method
			form.setFulrelProfitCalcMethod(facilityIslamic.getFulrelProfitCalcMethod());
			
			// Fixed Amount Refund
			
			// Compounding Method *
			if (facilityIslamic.getCompoundingMethod()!= null) {
				form.setCompoundingMethod(facilityIslamic.getCompoundingMethod().booleanValue() ? "Y" : "N");
			}
			else {
				form.setCompoundingMethod("N");
			}
			
			// Date to Stop Compounding
			form.setDateStopCompounding(facilityIslamic.getDateStopCompounding() != null ? sdf
					.format(facilityIslamic.getDateStopCompounding()) : null);
			
			if (facilityIslamic.getFixedCommissionAmount() != null) {
				form.setFixedCommissionAmount(UIUtil.formatAmount(facilityIslamic
						.getFixedCommissionAmount(), decPlaces, locale, withCCY));
			}
			
			// Refund - GPP Profit
			form.setRefundGppProfitValue(facilityIslamic.getRefundGppProfitValue());
			
			// Refund - Fulrel Profit
			form.setRefundFulrelProfitValue(facilityIslamic.getRefundFulrelProfitValue());
			
			// Commission Rate
			if (facilityIslamic.getCommissionRate() != null) {
				form.setCommissionRate(UIUtil.mapOBDouble_FormString(facilityIslamic.getCommissionRate()));
			}
			
			// Fixed Commission Amount
			if (facilityIslamic.getFixedCommissionAmount() != null) {
				form.setFixedCommissionAmount(UIUtil.formatAmount(facilityIslamic
						.getFixedCommissionAmount(), decPlaces, locale, withCCY));
			}
			
			// Commission Term
			if (facilityIslamic.getCommissionTerm() != null) {
				form.setCommissionTerm(String.valueOf(facilityIslamic.getCommissionTerm()));
			}
			
			// Commission Expiry Date			
			form.setCommissionExpiryDate(facilityIslamic.getCommissionExpiryDate() != null ? sdf
					.format(facilityIslamic.getCommissionExpiryDate()) : null);
			
			// Exc CMP in PMT/AMT *
			if (facilityIslamic.getExcCmpInPmtAmt()!= null) {
				form.setExcCmpInPmtAmt(facilityIslamic.getExcCmpInPmtAmt().booleanValue() ? "Y" : "N");
			}
			else {
				form.setExcCmpInPmtAmt("N");
			}
			
			// SPTF Dual Repayment Mode
			form.setSptfDualPaymentModeValue(facilityIslamic.getSptfDualPaymentModeValue());
			
			// S&P Agreement Date
			form.setSnpAgreementDate(facilityIslamic.getSnpAgreementDate() != null ? sdf
					.format(facilityIslamic.getSnpAgreementDate()) : null);

			// GPP Duration from S&P Date
			form.setGppDurationForSnp(facilityIslamic.getGppDurationForSnp() != null ?facilityIslamic.getGppDurationForSnp().toString():"");
			
			// S&P Term
			if (facilityIslamic.getSnpTerm() != null) {
				form.setSnpTerm(String.valueOf(facilityIslamic.getSnpTerm()));
			}
			
			// S&P Term Code
			form.setSnpTermCodeValue(String.valueOf(facilityIslamic.getSnpTermCodeValue()));
			
			// Full Rel Pft on 1/12 Mtd *
			if (facilityIslamic.getFullRelPft12Method() != null) {
				form.setFullRelPft12Method(facilityIslamic.getFullRelPft12Method().booleanValue() ? "Y" : "N");
			}
			else {
				form.setFullRelPft12Method("N");
			}
			
			// Fixed Refund Amount
			if (facilityIslamic.getFixedRefundAmount() != null) {
				form.setFixedRefundAmount(UIUtil.formatAmount(facilityIslamic
						.getFixedRefundAmount(), decPlaces, locale, withCCY));
			}
			
		}
		catch (RuntimeException e) {
			MapperException me = new MapperException("failed to map ob values into form");
			me.initCause(e);
			throw me;
		}
		catch (Exception e) {
			MapperException me = new MapperException("failed to format amount from object to display values");
			me.initCause(e);
			throw me;
		}
		return form;
	}
}
