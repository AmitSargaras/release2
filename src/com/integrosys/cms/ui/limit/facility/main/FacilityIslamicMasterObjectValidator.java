package com.integrosys.cms.ui.limit.facility.main;

import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityIslamicMaster;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;

public class FacilityIslamicMasterObjectValidator {
	public static final String THIS_CLASS = "com.integrosys.cms.ui.limit.facility.main.FacilityIslamicMasterObjectValidator";

	/**
	 * The only valid GPP payment code if the fixedGppProfit
	 * (fullRelPft12Method) is 'Y'
	 */
	private static final String VALID_GPP_PAYMENT_CODE_IF_IS_FIXED_GPP_PROFIT = "E";

	/**
	 * Valid GPP calculation method codes if the fixedGppProfit
	 * (fullRelPft12Method) is 'Y'
	 */
	private static final String[] VALID_GPP_CALC_METHOD_CODES_IF_IS_FIXED_GPP_PROFIT = new String[] { "A", "F" };

	/**
	 * The only valid GPP payment code for various reason as following:
	 * <ul>
	 * <li>Compouding Method is 'Yes'
	 * </ul>
	 */
	private static final String VALID_GPP_PAYMENT_CODE_FOR_VARIOUS = "I";

	public static ActionErrors validateObject(IFacilityMaster facilityMaster, String conceptCode, String accountType, String limitProductGrp) {
		IFacilityIslamicMaster facilityIslamicMaster = facilityMaster.getFacilityIslamicMaster();
		ActionErrors facilityIslamicMasterErrors = new ActionErrors();
		String productType = facilityMaster.getLimit().getProductDesc(); // Product
		// Type

		String[] gppPaymentMode1 = { "Y", "E", "N" };
		if (ArrayUtils.contains(gppPaymentMode1, facilityIslamicMaster.getGppPaymentModeValue())
				&& (facilityIslamicMaster.getGppTerm() == null || facilityIslamicMaster.getGppTerm().intValue() <= 0)) {
			facilityIslamicMasterErrors.add("gppTerm", new ActionMessage("error.mandatory"));
		}

		// Conditional Mandatory
		if (!(StringUtils.isBlank(facilityIslamicMaster.getGppPaymentModeValue()))
				&& !"I".equals(facilityIslamicMaster.getGppPaymentModeValue())) {
			if (StringUtils.isBlank(facilityIslamicMaster.getGppCalculationMethodValue())) {
				facilityIslamicMasterErrors.add("gppCalculationMethodValue", new ActionMessage("error.mandatory"));
			}

//			if (StringUtils.isBlank(facilityIslamicMaster.getRefundGppProfitValue())) {
//				facilityIslamicMasterErrors.add("refundGppProfitValue", new ActionMessage("error.mandatory"));
//			}
		}
		else if (!(StringUtils.isBlank(facilityIslamicMaster.getGppPaymentModeValue()))
				&& "I".equals(facilityIslamicMaster.getGppPaymentModeValue())) {
			if (StringUtils.isNotBlank(facilityIslamicMaster.getRefundGppProfitValue())) {
				facilityIslamicMasterErrors.add("refundGppProfitValue", new ActionMessage(
						"error.refund.gpp.profit.if.gpp.payment.mode", "I"));
			}

			if (facilityIslamicMaster.getGppTerm() != null && facilityIslamicMaster.getGppTerm().intValue() != 0) {
				facilityIslamicMasterErrors
						.add("gppTerm", new ActionMessage("error.gpp.term.if.gpp.payment.mode", "I"));
			}

			if (StringUtils.isNotBlank(facilityIslamicMaster.getGppCalculationMethodValue())) {
				facilityIslamicMasterErrors.add("gppCalculationMethodValue", new ActionMessage(
						"error.gpp.calc.method.if.gpp.payment.mode", "I"));
			}
        }

        //Andy Wong, 14 April 2009: remove conditional mandatory check on refundFulrelProfitValue
        // when FulrelProfitCalcMethod is F
//		if (!(StringUtils.isBlank(facilityIslamicMaster.getFulrelProfitCalcMethod()))
//				&& facilityIslamicMaster.getFulrelProfitCalcMethod().equals("F")) {
//			if (StringUtils.isBlank(facilityIslamicMaster.getRefundFulrelProfitValue())) {
//				facilityIslamicMasterErrors.add("refundFulrelProfitValue", new ActionMessage("error.mandatory"));
//			}
//		}

		if (StringUtils.equals(productType, ICMSConstant.PRODUCT_TYPE_AR)
				&& facilityIslamicMaster.getCustomerInterestRate() != null
				&& facilityIslamicMaster.getCustomerInterestRate().doubleValue() != 0) {
			facilityIslamicMasterErrors.add("customerInterestRate", new ActionMessage("error.number.must.be", "0"));
		}

		if (StringUtils.equals(facilityMaster.getDisbursementManner(), ICMSConstant.DISBURSEMENT_MANNER_LS)) {
			if (StringUtils.equals(facilityIslamicMaster.getGppPaymentModeValue(), "Y")) {
				facilityIslamicMasterErrors.add("gppPaymentModeValue", new ActionMessage(
						"error.gpp.payment.mode.not.allowed.when.disbursement.manner.is",
						"'Payment after grace period expiry/full release'", ICMSConstant.DISBURSEMENT_MANNER_LS));
			}
		}

		if (StringUtils.equals(facilityIslamicMaster.getGppPaymentModeValue(), "E")
				&& StringUtils.equals(facilityIslamicMaster.getRefundGppProfitValue(), "F")) {
			facilityIslamicMasterErrors.add("refundGppProfitValue", new ActionMessage(
					"error.gpp.refund.method.not.allowed.when.payment.mode.is", "'Upon full release'",
					"'Payment after grace period expiry'"));
		}

		if (!StringUtils.equals(productType, ICMSConstant.PRODUCT_TYPE_RP)
				&& StringUtils.isNotBlank(facilityIslamicMaster.getSptfDualPaymentModeValue())) {
			facilityIslamicMasterErrors.add("sptfDualPaymentModeValue", new ActionMessage(
					"error.dual.payment.mode.if.product.type.is.not", ICMSConstant.PRODUCT_TYPE_RP));
		}

		Set filteredMultitierFinancingSet = FacilityUtil.filterDeletedMultiTierFin(facilityMaster
				.getFacilityMultiTierFinancingSet());
		if (facilityMaster.getFacilityInterest() != null) {
			if (facilityMaster.getFacilityInterest().getInterestRate() != null
					&& (filteredMultitierFinancingSet == null || filteredMultitierFinancingSet.isEmpty())
					&& facilityIslamicMaster.getCustomerInterestRate() != null) {
				double interestRate = facilityMaster.getFacilityInterest().getInterestRate().doubleValue();
				double customerInterestRate = facilityIslamicMaster.getCustomerInterestRate().doubleValue();

				if (interestRate > 0 && customerInterestRate < interestRate) {
					facilityIslamicMasterErrors.add("customerInterestRate", new ActionMessage(
							"error.cust.interest.rate.must.not.less.than.interest.rate", new Integer(0)));
				}
			}
		}

		if (facilityIslamicMaster.getFullRelPft12Method() != null) {
			if (facilityIslamicMaster.getFullRelPft12Method().booleanValue()) {
				if (!VALID_GPP_PAYMENT_CODE_IF_IS_FIXED_GPP_PROFIT.equals(facilityIslamicMaster
						.getGppPaymentModeValue())) {
					facilityIslamicMasterErrors.add("gppPaymentModeValue", new ActionMessage(
							"error.gpp.payment.mode.if.fixed.gpp.profit.is.yes",
							VALID_GPP_PAYMENT_CODE_IF_IS_FIXED_GPP_PROFIT));
				}

				if (!ArrayUtils.contains(VALID_GPP_CALC_METHOD_CODES_IF_IS_FIXED_GPP_PROFIT, facilityIslamicMaster
						.getGppCalculationMethodValue())) {
					facilityIslamicMasterErrors.add("gppPaymentModeValue", new ActionMessage(
							"error.gpp.calculation.method.if.fixed.gpp.profit.is.yes",
							VALID_GPP_CALC_METHOD_CODES_IF_IS_FIXED_GPP_PROFIT[0],
							VALID_GPP_CALC_METHOD_CODES_IF_IS_FIXED_GPP_PROFIT[1]));
				}
			}
		}

		if (facilityIslamicMaster.getCompoundingMethod() != null) {
			if (facilityIslamicMaster.getCompoundingMethod().booleanValue()) {
				if (!VALID_GPP_PAYMENT_CODE_FOR_VARIOUS.equals(facilityIslamicMaster.getGppPaymentModeValue())) {
					facilityIslamicMasterErrors.add("gppPaymentModeValue", new ActionMessage(
							"error.gpp.payment.mode.if.compouding.method.is.yes", VALID_GPP_PAYMENT_CODE_FOR_VARIOUS));
				}
			}
		}

		if (facilityMaster.getFacilityPayment() != null
				&& "5".equals(facilityMaster.getFacilityPayment().getPaymentCodeEntryCode())
				&& facilityMaster.getFacilityGeneral() != null
				&& "D".equals(facilityMaster.getFacilityGeneral().getTermCodeEntryCode())) {
			if (!VALID_GPP_PAYMENT_CODE_FOR_VARIOUS.equals(facilityIslamicMaster.getGppPaymentModeValue())) {
				facilityIslamicMasterErrors.add("gppPaymentModeValue", new ActionMessage(
						"error.gpp.payment.mode.if.payment.code.term.code", VALID_GPP_PAYMENT_CODE_FOR_VARIOUS, "5",
						"D"));
			}

			if ("F".equals(accountType) && "F".equals(facilityIslamicMaster.getFulrelProfitCalcMethod())) {
				facilityIslamicMasterErrors.add("fulrelProfitCalcMethod", new ActionMessage(
						"error.full.rel.comp.method.if.payment.code.term.code", "F", "5", "D"));
			}

		}

		if ("X".equals(facilityIslamicMaster.getSptfDualPaymentModeValue())
				&& facilityMaster.getFacilityGeneral() != null
				&& "D".equals(facilityMaster.getFacilityGeneral().getTermCodeEntryCode())) {
			if (!VALID_GPP_PAYMENT_CODE_FOR_VARIOUS.equals(facilityIslamicMaster.getGppPaymentModeValue())) {
				facilityIslamicMasterErrors.add("gppPaymentModeValue", new ActionMessage(
						"error.gpp.payment.mode.if.dual.payment.mode.term.code", VALID_GPP_PAYMENT_CODE_FOR_VARIOUS,
						"X", "D"));
			}
		}

		if ("T".equals(accountType) && facilityMaster.getFacilityGeneral() != null
				&& facilityMaster.getFacilityGeneral().getTerm() != null
				&& facilityMaster.getFacilityGeneral().getTerm().intValue() == 0
				&& facilityMaster.getFacilityPayment() != null
				&& "5".equals(facilityMaster.getFacilityPayment().getPaymentCodeEntryCode())) {
			if (!VALID_GPP_PAYMENT_CODE_FOR_VARIOUS.equals(facilityIslamicMaster.getGppPaymentModeValue())) {
				facilityIslamicMasterErrors.add("gppPaymentModeValue", new ActionMessage(
						"error.gpp.payment.mode.if.account.type.term.payment.code", VALID_GPP_PAYMENT_CODE_FOR_VARIOUS,
						"T", "0", "5"));
			}
		}

		if (facilityIslamicMaster.getCompoundingMethod() != null) {
			if (!facilityIslamicMaster.getCompoundingMethod().booleanValue()
					&& facilityIslamicMaster.getDateStopCompounding() != null) {
				facilityIslamicMasterErrors.add("dateStopCompounding", new ActionMessage(
						"error.date.stop.compounding.must.be.empty.if.no.compouding.method"));
			}
		}

		if ("P".equals(facilityIslamicMaster.getRefundFulrelProfitValue())
				&& facilityMaster.getFacilityInterest() != null
				&& facilityMaster.getFacilityInterest().getInterestRate() != null
				&& facilityIslamicMaster.getCustomerInterestRate() != null) {
			double interestRate = facilityMaster.getFacilityInterest().getInterestRate().doubleValue();
			double customerInterestRate = facilityIslamicMaster.getCustomerInterestRate().doubleValue();
			if (interestRate == customerInterestRate && !"F".equals(facilityIslamicMaster.getFulrelProfitCalcMethod())) {
				facilityIslamicMasterErrors.add("fulrelProfitCalcMethod", new ActionMessage(
						"error.full.rel.comp.method.if.full.rel.rebate.method.rate.same", "F", "P"));
			}
		}

		if (StringUtils.isBlank(facilityIslamicMaster.getFulrelProfitCalcMethod())
				&& "A".equals(facilityIslamicMaster.getGppCalculationMethodValue())
				&& facilityMaster.getFacilityInterest() != null
				&& facilityMaster.getFacilityInterest().getInterestRate() != null
				&& facilityIslamicMaster.getCustomerInterestRate() != null) {
			double interestRate = facilityMaster.getFacilityInterest().getInterestRate().doubleValue();
			double customerInterestRate = facilityIslamicMaster.getCustomerInterestRate().doubleValue();
			if (interestRate == customerInterestRate && facilityIslamicMaster.getFixedRefundAmount() != null) {
				facilityIslamicMasterErrors.add("fixedRefundAmount", new ActionMessage(
						"error.fixed.refund.amount.if.full.rel.comp.method.gpp.cacl.method.rate.same", "A"));
			}
		}

        //Andy Wong, 14 March 2009: validate for MG product group
        if(!StringUtils.equals(limitProductGrp, "MG") && facilityIslamicMaster.getSnpAgreementDate()!=null){
            facilityIslamicMasterErrors.add("snpAgreementDate", new ActionMessage(
						"error.product.gpp.not.mg.snpdate.not.blank"));
        }

        if(!StringUtils.equals(limitProductGrp, "MG")
                && facilityIslamicMaster.getGppDurationForSnp()!=null
                && StringUtils.isNotBlank(facilityIslamicMaster.getGppDurationForSnp().toString())){
            facilityIslamicMasterErrors.add("gppDurationForSnp", new ActionMessage(
						"error.product.gpp.not.mg.gpp.duration.not.blank"));
        }

        if(!StringUtils.equals(limitProductGrp, "MG")
                && facilityIslamicMaster.getSnpTerm()!=null
                && facilityIslamicMaster.getSnpTerm().intValue() != 0){
            facilityIslamicMasterErrors.add("snpTerm", new ActionMessage(
						"error.product.gpp.not.mg.snp.term.not.blank"));
        }

		DefaultLogger.debug(" FacilityIslamicMaster Total Errors", "--------->" + facilityIslamicMasterErrors.size());
		return facilityIslamicMasterErrors.size() == 0 ? null : facilityIslamicMasterErrors;
	}
}