package com.integrosys.cms.app.limit.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author siew kheat
 *
 */
public class OBFacilityIslamicMaster implements IFacilityIslamicMaster {

	private static final long serialVersionUID = -7569796750303793670L;

	private long facilityMasterId;
	
	private Short gppTerm;
	
	private Character gppTermCode;
	
	private String gppPaymentModeNum;
	
	private String gppPaymentModeValue;
	
	private Amount sellingPriceAmount;
	
	private Amount gppTotalAmount;
	
	private Short securityDepOfMonth;
	
	private Double securityDepPercentage;
	
	private Amount securityDepAmount;
	
	private Double customerInterestRate;
	
	private String gppCalculationMethodNum;
	
	private String gppCalculationMethodValue;
	
	private String fulrelProfitCalcMethod;
	
	private Boolean compoundingMethod;
	
	private Date dateStopCompounding;
	
	private String refundGppProfitNum;
	
	private String refundGppProfitValue;
	
	private String refundFulrelProfitNum;
	
	private String refundFulrelProfitValue;
	
	private Double commissionRate;
	
	private Amount fixedCommissionAmount;
	
	private Short commissionTerm;
	
	private Date commissionExpiryDate;
	
	private Boolean excCmpInPmtAmt;
	
	private String sptfDualPaymentModeNum;
	
	private String sptfDualPaymentModeValue;

	private Date snpAgreementDate;
	
	private Character gppDurationForSnp;

	private Short snpTerm;
	
	private String snpTermCodeNum;
	
	private String snpTermCodeValue;
	
	private Boolean fullRelPft12Method;
	
	private Date  graceExpSPTFDate;
	
	private Amount fixedRefundAmount;

	private String currencyCode;
	
	
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the fixedRefundAmount
	 */
	public Amount getFixedRefundAmount() {
		return fixedRefundAmount;
	}

	/**
	 * @param fixedRefundAmount the fixedRefundAmount to set
	 */
	public void setFixedRefundAmount(Amount fixedRefundAmount) {
		this.fixedRefundAmount = fixedRefundAmount;
	}

	/**
	 * @return the graceExpSPTFDate
	 */
	public Date getGraceExpSPTFDate() {
		return graceExpSPTFDate;
	}

	/**
	 * @param graceExpSPTFDate the graceExpSPTFDate to set
	 */
	public void setGraceExpSPTFDate(Date graceExpSPTFDate) {
		this.graceExpSPTFDate = graceExpSPTFDate;
	}

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	/**
	 * @return the gppTerm
	 */
	public Short getGppTerm() {
		return gppTerm;
	}

	/**
	 * @param gppTerm the gppTerm to set
	 */
	public void setGppTerm(Short gppTerm) {
		this.gppTerm = gppTerm;
	}

	/**
	 * @return the gppTermCode
	 */
	public Character getGppTermCode() {
		return gppTermCode;
	}

	/**
	 * @param gppTermCode the gppTermCode to set
	 */
	public void setGppTermCode(Character gppTermCode) {
		this.gppTermCode = gppTermCode;
	}

	/**
	 * @return the gppPaymentModeNum
	 */
	public String getGppPaymentModeNum() {
		return gppPaymentModeNum;
	}

	/**
	 * @param gppPaymentModeNum the gppPaymentModeNum to set
	 */
	public void setGppPaymentModeNum(String gppPaymentModeNum) {
		this.gppPaymentModeNum = gppPaymentModeNum;
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
	public Amount getSellingPriceAmount() {
		return sellingPriceAmount;
	}

	/**
	 * @param sellingPriceAmount the sellingPriceAmount to set
	 */
	public void setSellingPriceAmount(Amount sellingPriceAmount) {
		this.sellingPriceAmount = sellingPriceAmount;
	}

	/**
	 * @return the gppTotalAmount
	 */
	public Amount getGppTotalAmount() {
		return gppTotalAmount;
	}

	/**
	 * @param gppTotalAmount the gppTotalAmount to set
	 */
	public void setGppTotalAmount(Amount gppTotalAmount) {
		this.gppTotalAmount = gppTotalAmount;
	}

	/**
	 * @return the securityDepOfMonth
	 */
	public Short getSecurityDepOfMonth() {
		return securityDepOfMonth;
	}

	/**
	 * @param securityDepOfMonth the securityDepOfMonth to set
	 */
	public void setSecurityDepOfMonth(Short securityDepOfMonth) {
		this.securityDepOfMonth = securityDepOfMonth;
	}

	/**
	 * @return the securityDepPercentagea
	 */
	public Double getSecurityDepPercentage() {
		return securityDepPercentage;
	}

	/**
	 * @param securityDepPercentagea the securityDepPercentagea to set
	 */
	public void setSecurityDepPercentage(Double securityDepPercentage) {
		this.securityDepPercentage = securityDepPercentage;
	}

	/**
	 * @return the securityDepAmount
	 */
	public Amount getSecurityDepAmount() {
		return securityDepAmount;
	}

	/**
	 * @param securityDepAmount the securityDepAmount to set
	 */
	public void setSecurityDepAmount(Amount securityDepAmount) {
		this.securityDepAmount = securityDepAmount;
	}

	/**
	 * @return the customerInterestRate
	 */
	public Double getCustomerInterestRate() {
		return customerInterestRate;
	}

	/**
	 * @param customerInterestRate the customerInterestRate to set
	 */
	public void setCustomerInterestRate(Double customerInterestRate) {
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
	public Boolean getCompoundingMethod() {
		return compoundingMethod;
	}

	/**
	 * @param compoundingMethod the compoundingMethod to set
	 */
	public void setCompoundingMethod(Boolean compoundingMethod) {
		this.compoundingMethod = compoundingMethod;
	}

	/**
	 * @return the dateStopCompounding
	 */
	public Date getDateStopCompounding() {
		return dateStopCompounding;
	}

	/**
	 * @param dateStopCompounding the dateStopCompounding to set
	 */
	public void setDateStopCompounding(Date dateStopCompounding) {
		this.dateStopCompounding = dateStopCompounding;
	}

	/**
	 * @return the refundGppProfitNum
	 */
	public String getRefundGppProfitNum() {
		return refundGppProfitNum;
	}

	/**
	 * @param refundGppProfitNum the refundGppProfitNum to set
	 */
	public void setRefundGppProfitNum(String refundGppProfitNum) {
		this.refundGppProfitNum = refundGppProfitNum;
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
	 * @return the refundFulrelProfitNum
	 */
	public String getRefundFulrelProfitNum() {
		return refundFulrelProfitNum;
	}

	/**
	 * @param refundFulrelProfitNum the refundFulrelProfitNum to set
	 */
	public void setRefundFulrelProfitNum(String refundFulrelProfitNum) {
		this.refundFulrelProfitNum = refundFulrelProfitNum;
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
	public Double getCommissionRate() {
		return commissionRate;
	}

	/**
	 * @param commissionRate the commissionRate to set
	 */
	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	/**
	 * @return the fixedCommissionAmount
	 */
	public Amount getFixedCommissionAmount() {
		return fixedCommissionAmount;
	}

	/**
	 * @param fixedCommissionAmount the fixedCommissionAmount to set
	 */
	public void setFixedCommissionAmount(Amount fixedCommissionAmount) {
		this.fixedCommissionAmount = fixedCommissionAmount;
	}

	/**
	 * @return the commissionTerm
	 */
	public Short getCommissionTerm() {
		return commissionTerm;
	}

	/**
	 * @param commissionTerm the commissionTerm to set
	 */
	public void setCommissionTerm(Short commissionTerm) {
		this.commissionTerm = commissionTerm;
	}

	/**
	 * @return the commissionExpiryDate
	 */
	public Date getCommissionExpiryDate() {
		return commissionExpiryDate;
	}

	/**
	 * @param commissionExpiryDate the commissionExpiryDate to set
	 */
	public void setCommissionExpiryDate(Date commissionExpiryDate) {
		this.commissionExpiryDate = commissionExpiryDate;
	}

	/**
	 * @return the excCmpInPmtAmt
	 */
	public Boolean getExcCmpInPmtAmt() {
		return excCmpInPmtAmt;
	}

	/**
	 * @param excCmpInPmtAmt the excCmpInPmtAmt to set
	 */
	public void setExcCmpInPmtAmt(Boolean excCmpInPmtAmt) {
		this.excCmpInPmtAmt = excCmpInPmtAmt;
	}

	/**
	 * @return the sptfDualPaymentModeNum
	 */
	public String getSptfDualPaymentModeNum() {
		return sptfDualPaymentModeNum;
	}

	/**
	 * @param sptfDualPaymentModeNum the sptfDualPaymentModeNum to set
	 */
	public void setSptfDualPaymentModeNum(String sptfDualPaymentModeNum) {
		this.sptfDualPaymentModeNum = sptfDualPaymentModeNum;
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
	public Date getSnpAgreementDate() {
		return snpAgreementDate;
	}

	/**
	 * @param snpAgreementDate the snpAgreementDate to set
	 */
	public void setSnpAgreementDate(Date snpAgreementDate) {
		this.snpAgreementDate = snpAgreementDate;
	}

	/**
	 * @return the gppDurationForSnp
	 */
	public Character getGppDurationForSnp() {
		return gppDurationForSnp;
	}

	/**
	 * @param gppDurationForSnp the gppDurationForSnp to set
	 */
	public void setGppDurationForSnp(Character gppDurationForSnp) {
		this.gppDurationForSnp = gppDurationForSnp;
	}

	/**
	 * @return the snpRate
	 */
	public Short getSnpTerm() {
		return snpTerm;
	}

	/**
	 * @param snpRate the snpRate to set
	 */
	public void setSnpTerm(Short snpTerm) {
		this.snpTerm = snpTerm;
	}

	/**
	 * @return the snpTermCodeNum
	 */
	public String getSnpTermCodeNum() {
		return snpTermCodeNum;
	}

	/**
	 * @param snpTermCodeNum the snpTermCodeNum to set
	 */
	public void setSnpTermCodeNum(String snpTermCodeNum) {
		this.snpTermCodeNum = snpTermCodeNum;
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
	public Boolean getFullRelPft12Method() {
		return fullRelPft12Method;
	}

	/**
	 * @param fullRelPft12Method the fullRelPft12Method to set
	 */
	public void setFullRelPft12Method(Boolean fullRelPft12Method) {
		this.fullRelPft12Method = fullRelPft12Method;
	}
	
	


}
