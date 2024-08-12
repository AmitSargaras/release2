package com.integrosys.cms.ui.limit.facility.islamic;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainForm;
import org.apache.commons.lang.StringUtils;

public class FacilityIslamicMasterForm extends FacilityMainForm {/*
															 * private String
															 * limitID;// AA No.
															 * 
															 * private String
															 * facilityCode;//
															 * Facility Code
															 * 
															 * private String
															 * facilitySequence;//
															 * Facility Sequence
															 * No.
															 */

	private static final long serialVersionUID = 1L;

	private String organisationCode;// Branch Number

	private String gppTerm;
	
	private String gppTermCode;
	
	private String gppPaymentModeValue;
	
	private String sellingPriceAmount;
	
	private String gppTotalAmount;
	
	private String securityDepOfMonth;
	
	private String securityDepPercentage;
	
	private String securityDepAmount;
	
	private String customerInterestRate;
	
	private String gppCalculationMethodNum;
	
	private String gppCalculationMethodValue;
	
	private String fulrelProfitCalcMethod;
	
	private String compoundingMethod;
	
	private String dateStopCompounding;
	
	private String refundGppProfitValue;
	
	private String refundFulrelProfitValue;
	
	private String commissionRate;
	
	private String fixedCommissionAmount;
	
	private String commissionTerm;
	
	private String commissionExpiryDate;
	
	private String excCmpInPmtAmt;
	
	private String sptfDualPaymentModeValue;

	private String snpAgreementDate;
	
	private String gppDurationForSnp;

	private String snpTerm;
	
	private String snpTermCodeValue;
	
	private String fullRelPft12Method;
	
	private String fixedRefundAmount;

	/**
	 * @return the fixedRefundAmount
	 */
	public String getFixedRefundAmount() {
		return fixedRefundAmount;
	}

	/**
	 * @param fixedRefundAmount the fixedRefundAmount to set
	 */
	public void setFixedRefundAmount(String fixedRefundAmount) {
		this.fixedRefundAmount = fixedRefundAmount;
	}

	/**
	 * @return the gppTerm
	 */
	public String getGppTerm() {
		return gppTerm;
	}

	/**
	 * @param gppTerm the gppTerm to set
	 */
	public void setGppTerm(String gppTerm) {
		this.gppTerm = gppTerm;
	}

	/**
	 * @return the gppTermCode
	 */
	public String getGppTermCode() {
		return gppTermCode;
	}

	/**
	 * @param gppTermCode the gppTermCode to set
	 */
	public void setGppTermCode(String gppTermCode) {
		this.gppTermCode = gppTermCode;
	}

	/**
	 * @return the gppPaymentModeValue
	 */
	public String getGppPaymentModeValue() {
		return gppPaymentModeValue;
	}

	/**
	 * @param gppPaymentModeValue the gppPaymentModeValue to set
	 */
	public void setGppPaymentModeValue(String gppPaymentModeValue) {
		this.gppPaymentModeValue = gppPaymentModeValue;
	}

	/**
	 * @return the sellingPriceAmount
	 */
	public String getSellingPriceAmount() {
		return sellingPriceAmount;
	}

	/**
	 * @param sellingPriceAmount the sellingPriceAmount to set
	 */
	public void setSellingPriceAmount(String sellingPriceAmount) {
		this.sellingPriceAmount = sellingPriceAmount;
	}

	/**
	 * @return the gppTotalAmount
	 */
	public String getGppTotalAmount() {
		return gppTotalAmount;
	}

	/**
	 * @param gppTotalAmount the gppTotalAmount to set
	 */
	public void setGppTotalAmount(String gppTotalAmount) {
		this.gppTotalAmount = gppTotalAmount;
	}

	/**
	 * @return the securityDepOfMonth
	 */
	public String getSecurityDepOfMonth() {
		return securityDepOfMonth;
	}

	/**
	 * @param securityDepOfMonth the securityDepOfMonth to set
	 */
	public void setSecurityDepOfMonth(String securityDepOfMonth) {
		this.securityDepOfMonth = securityDepOfMonth;
	}

	/**
	 * @return the securityDepPercentage
	 */
	public String getSecurityDepPercentage() {
		return securityDepPercentage;
	}

	/**
	 * @param securityDepPercentage the securityDepPercentage to set
	 */
	public void setSecurityDepPercentage(String securityDepPercentage) {
		this.securityDepPercentage = securityDepPercentage;
	}

	/**
	 * @return the securityDepAmount
	 */
	public String getSecurityDepAmount() {
		return securityDepAmount;
	}

	/**
	 * @param securityDepAmount the securityDepAmount to set
	 */
	public void setSecurityDepAmount(String securityDepAmount) {
		this.securityDepAmount = securityDepAmount;
	}

	/**
	 * @return the customerInterestRate
	 */
	public String getCustomerInterestRate() {
		return customerInterestRate;
	}

	/**
	 * @param customerInterestRate the customerInterestRate to set
	 */
	public void setCustomerInterestRate(String customerInterestRate) {
		this.customerInterestRate = customerInterestRate;
	}

	/**
	 * @return the gppCalculationMethodNum
	 */
	public String getGppCalculationMethodNum() {
		return gppCalculationMethodNum;
	}

	/**
	 * @param gppCalculationMethodNum the gppCalculationMethodNum to set
	 */
	public void setGppCalculationMethodNum(String gppCalculationMethodNum) {
		this.gppCalculationMethodNum = gppCalculationMethodNum;
	}

	/**
	 * @return the gppCalculationMethodValue
	 */
	public String getGppCalculationMethodValue() {
		return gppCalculationMethodValue;
	}

	/**
	 * @param gppCalculationMethodValue the gppCalculationMethodValue to set
	 */
	public void setGppCalculationMethodValue(String gppCalculationMethodValue) {
		this.gppCalculationMethodValue = gppCalculationMethodValue;
	}

	/**
	 * @return the fulrelProfitCalcMethod
	 */
	public String getFulrelProfitCalcMethod() {
		return fulrelProfitCalcMethod;
	}

	/**
	 * @param fulrelProfitCalcMethod the fulrelProfitCalcMethod to set
	 */
	public void setFulrelProfitCalcMethod(String fulrelProfitCalcMethod) {
		this.fulrelProfitCalcMethod = fulrelProfitCalcMethod;
	}

	/**
	 * @return the compoundingMethod
	 */
	public String getCompoundingMethod() {
		return compoundingMethod;
	}

	/**
	 * @param compoundingMethod the compoundingMethod to set
	 */
	public void setCompoundingMethod(String compoundingMethod) {
		this.compoundingMethod = compoundingMethod;
	}

	/**
	 * @return the dateStopCompounding
	 */
	public String getDateStopCompounding() {
		return dateStopCompounding;
	}

	/**
	 * @param dateStopCompounding the dateStopCompounding to set
	 */
	public void setDateStopCompounding(String dateStopCompounding) {
		this.dateStopCompounding = dateStopCompounding;
	}

	/**
	 * @return the refundGppProfitValue
	 */
	public String getRefundGppProfitValue() {
		return refundGppProfitValue;
	}

	/**
	 * @param refundGppProfitValue the refundGppProfitValue to set
	 */
	public void setRefundGppProfitValue(String refundGppProfitValue) {
		this.refundGppProfitValue = refundGppProfitValue;
	}

	/**
	 * @return the refundFulrelProfitValue
	 */
	public String getRefundFulrelProfitValue() {
		return refundFulrelProfitValue;
	}

	/**
	 * @param refundFulrelProfitValue the refundFulrelProfitValue to set
	 */
	public void setRefundFulrelProfitValue(String refundFulrelProfitValue) {
		this.refundFulrelProfitValue = refundFulrelProfitValue;
	}

	/**
	 * @return the commissionRate
	 */
	public String getCommissionRate() {
		return commissionRate;
	}

	/**
	 * @param commissionRate the commissionRate to set
	 */
	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}

	/**
	 * @return the fiexedCommissionAmount
	 */
	public String getFixedCommissionAmount() {
		return fixedCommissionAmount;
	}

	/**
	 * @param fiexedCommissionAmount the fiexedCommissionAmount to set
	 */
	public void setFixedCommissionAmount(String fixedCommissionAmount) {
		this.fixedCommissionAmount = fixedCommissionAmount;
	}

	/**
	 * @return the commissionTerm
	 */
	public String getCommissionTerm() {
		return commissionTerm;
	}

	/**
	 * @param commissionTerm the commissionTerm to set
	 */
	public void setCommissionTerm(String commissionTerm) {
		this.commissionTerm = commissionTerm;
	}

	/**
	 * @return the commissionExpiryDate
	 */
	public String getCommissionExpiryDate() {
		return commissionExpiryDate;
	}

	/**
	 * @param commissionExpiryDate the commissionExpiryDate to set
	 */
	public void setCommissionExpiryDate(String commissionExpiryDate) {
		this.commissionExpiryDate = commissionExpiryDate;
	}

	/**
	 * @return the excCmpInPmtAmt
	 */
	public String getExcCmpInPmtAmt() {
		return excCmpInPmtAmt;
	}

	/**
	 * @param excCmpInPmtAmt the excCmpInPmtAmt to set
	 */
	public void setExcCmpInPmtAmt(String excCmpInPmtAmt) {
		this.excCmpInPmtAmt = excCmpInPmtAmt;
	}

	/**
	 * @return the sptfDualPaymentModeValue
	 */
	public String getSptfDualPaymentModeValue() {
		return sptfDualPaymentModeValue;
	}

	/**
	 * @param sptfDualPaymentModeValue the sptfDualPaymentModeValue to set
	 */
	public void setSptfDualPaymentModeValue(String sptfDualPaymentModeValue) {
		this.sptfDualPaymentModeValue = sptfDualPaymentModeValue;
	}

	/**
	 * @return the snpAgreementDate
	 */
	public String getSnpAgreementDate() {
		return snpAgreementDate;
	}

	/**
	 * @param snpAgreementDate the snpAgreementDate to set
	 */
	public void setSnpAgreementDate(String snpAgreementDate) {
		this.snpAgreementDate = snpAgreementDate;
	}

	/**
	 * @return the gppDurationForSnp
	 */
	public String getGppDurationForSnp() {
		return gppDurationForSnp;
	}

	/**
	 * @param gppDurationForSnp the gppDurationForSnp to set
	 */
	public void setGppDurationForSnp(String gppDurationForSnp) {
		this.gppDurationForSnp = gppDurationForSnp;
	}

	/**
	 * @return the snpTerm
	 */
	public String getSnpTerm() {
		return snpTerm;
	}

	/**
	 * @param snpTerm the snpTerm to set
	 */
	public void setSnpTerm(String snpTerm) {
		this.snpTerm = snpTerm;
	}

	/**
	 * @return the snpTermCodeValue
	 */
	public String getSnpTermCodeValue() {
		return snpTermCodeValue;
	}

	/**
	 * @param snpTermCodeValue the snpTermCodeValue to set
	 */
	public void setSnpTermCodeValue(String snpTermCodeValue) {
		this.snpTermCodeValue = snpTermCodeValue;
	}

	/**
	 * @return the fullRelPft12Method
	 */
	public String getFullRelPft12Method() {
		return fullRelPft12Method;
	}

	/**
	 * @param fullRelPft12Method the fullRelPft12Method to set
	 */
	public void setFullRelPft12Method(String fullRelPft12Method) {
		this.fullRelPft12Method = fullRelPft12Method;
	}

	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.islamic.FacilityIslamicMasterMapper";

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	/**
	 * @return the organisationCode
	 */
	public String getOrganisationCode() {
		return organisationCode;
	}

	/**
	 * @param organisationCode the organisationCode to set
	 */
	public void setOrganisationCode(String organisationCode) {
		this.organisationCode = organisationCode;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
