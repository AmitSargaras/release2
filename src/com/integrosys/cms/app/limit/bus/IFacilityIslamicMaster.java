package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author siew kheat
 *
 */
public interface IFacilityIslamicMaster extends Serializable {

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode();

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode);
	
	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId();

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId);

	/**
	 * @return the gppTerm
	 */
	public Short getGppTerm();

	/**
	 * @param gppTerm the gppTerm to set
	 */
	public void setGppTerm(Short gppTerm);

	/**
	 * @return the gppTermCode
	 */
	public Character getGppTermCode();

	/**
	 * @param gppTermCode the gppTermCode to set
	 */
	public void setGppTermCode(Character gppTermCode);

	/**
	 * @return the gppPaymentModeNum
	 */
	public String getGppPaymentModeNum();

	/**
	 * @param gppPaymentModeNum the gppPaymentModeNum to set
	 */
	public void setGppPaymentModeNum(String gppPaymentModeNum);

	/**
	 * @return the gppPaymentModeValue
	 */
	public String getGppPaymentModeValue();

	/**
	 * @param gppPaymentModeValue the gppPaymentModeValue to set
	 */
	public void setGppPaymentModeValue(String gppPaymentModeValue);

	/**
	 * @return the sellingPriceAmount
	 */
	public Amount getSellingPriceAmount();

	/**
	 * @param sellingPriceAmount the sellingPriceAmount to set
	 */
	public void setSellingPriceAmount(Amount sellingPriceAmount);

	/**
	 * @return the gppTotalAmount
	 */
	public Amount getGppTotalAmount();

	/**
	 * @param gppTotalAmount the gppTotalAmount to set
	 */
	public void setGppTotalAmount(Amount gppTotalAmount);

	/**
	 * @return the securityDepOfMonth
	 */
	public Short getSecurityDepOfMonth();

	/**
	 * @param securityDepOfMonth the securityDepOfMonth to set
	 */
	public void setSecurityDepOfMonth(Short securityDepOfMonth);

	/**
	 * @return the securityDepPercentagea
	 */
	public Double getSecurityDepPercentage();

	/**
	 * @param securityDepPercentagea the securityDepPercentagea to set
	 */
	public void setSecurityDepPercentage(Double securityDepPercentage);

	/**
	 * @return the securityDepAmount
	 */
	public Amount getSecurityDepAmount();

	/**
	 * @param securityDepAmount the securityDepAmount to set
	 */
	public void setSecurityDepAmount(Amount securityDepAmount);

	/**
	 * @return the customerInterestRate
	 */
	public Double getCustomerInterestRate();

	/**
	 * @param customerInterestRate the customerInterestRate to set
	 */
	public void setCustomerInterestRate(Double customerInterestRate);
	/**
	 * @return the gppCalculationMethodNum
	 */
	public String getGppCalculationMethodNum();

	/**
	 * @param gppCalculationMethodNum the gppCalculationMethodNum to set
	 */
	public void setGppCalculationMethodNum(String gppCalculationMethodNum);

	/**
	 * @return the gppCalculationMethodValue
	 */
	public String getGppCalculationMethodValue();

	/**
	 * @param gppCalculationMethodValue the gppCalculationMethodValue to set
	 */
	public void setGppCalculationMethodValue(String gppCalculationMethodValue);

	/**
	 * @return the fulrelProfitCalcMethod
	 */
	public String getFulrelProfitCalcMethod();

	/**
	 * @param fulrelProfitCalcMethod the fulrelProfitCalcMethod to set
	 */
	public void setFulrelProfitCalcMethod(String fulrelProfitCalcMethod);

	/**
	 * @return the compoundingMethod
	 */
	public Boolean getCompoundingMethod();

	/**
	 * @param compoundingMethod the compoundingMethod to set
	 */
	public void setCompoundingMethod(Boolean compoundingMethod);

	/**
	 * @return the dateStopCompounding
	 */
	public Date getDateStopCompounding();

	/**
	 * @param dateStopCompounding the dateStopCompounding to set
	 */
	public void setDateStopCompounding(Date dateStopCompounding);

	/**
	 * @return the refundGppProfitNum
	 */
	public String getRefundGppProfitNum();

	/**
	 * @param refundGppProfitNum the refundGppProfitNum to set
	 */
	public void setRefundGppProfitNum(String refundGppProfitNum);

	/**
	 * @return the refundGppProfitValue
	 */
	public String getRefundGppProfitValue();

	/**
	 * @param refundGppProfitValue the refundGppProfitValue to set
	 */
	public void setRefundGppProfitValue(String refundGppProfitValue);

	/**
	 * @return the refundFulrelProfitNum
	 */
	public String getRefundFulrelProfitNum();

	/**
	 * @param refundFulrelProfitNum the refundFulrelProfitNum to set
	 */
	public void setRefundFulrelProfitNum(String refundFulrelProfitNum);

	/**
	 * @return the refundFulrelProfitValue
	 */
	public String getRefundFulrelProfitValue();

	/**
	 * @param refundFulrelProfitValue the refundFulrelProfitValue to set
	 */
	public void setRefundFulrelProfitValue(String refundFulrelProfitValue);

	/**
	 * @return the commissionRate
	 */
	public Double getCommissionRate();

	/**
	 * @param commissionRate the commissionRate to set
	 */
	public void setCommissionRate(Double commissionRate);

	/**
	 * @return the fiexedCommissionAmount
	 */
	public Amount getFixedCommissionAmount() ;

	/**
	 * @param fiexedCommissionAmount the fiexedCommissionAmount to set
	 */
	public void setFixedCommissionAmount(Amount fiexedCommissionAmount);

	/**
	 * @return the commissionTerm
	 */
	public Short getCommissionTerm();

	/**
	 * @param commissionTerm the commissionTerm to set
	 */
	public void setCommissionTerm(Short commissionTerm);

	/**
	 * @return the commissionExpiryDate
	 */
	public Date getCommissionExpiryDate();

	/**
	 * @param commissionExpiryDate the commissionExpiryDate to set
	 */
	public void setCommissionExpiryDate(Date commissionExpiryDate);

	/**
	 * @return the excCmpInPmtAmt
	 */
	public Boolean getExcCmpInPmtAmt();

	/**
	 * @param excCmpInPmtAmt the excCmpInPmtAmt to set
	 */
	public void setExcCmpInPmtAmt(Boolean excCmpInPmtAmt);

	/**
	 * @return the sptfDualPaymentModeNum
	 */
	public String getSptfDualPaymentModeNum();

	/**
	 * @param sptfDualPaymentModeNum the sptfDualPaymentModeNum to set
	 */
	public void setSptfDualPaymentModeNum(String sptfDualPaymentModeNum);

	/**
	 * @return the sptfDualPaymentModeValue
	 */
	public String getSptfDualPaymentModeValue();

	/**
	 * @param sptfDualPaymentModeValue the sptfDualPaymentModeValue to set
	 */
	public void setSptfDualPaymentModeValue(String sptfDualPaymentModeValue);

	/**
	 * @return the snpAgreementDate
	 */
	public Date getSnpAgreementDate();

	/**
	 * @param snpAgreementDate the snpAgreementDate to set
	 */
	public void setSnpAgreementDate(Date snpAgreementDate);
	/**
	 * @return the gppDurationForSnp
	 */
	public Character getGppDurationForSnp();

	/**
	 * @param gppDurationForSnp the gppDurationForSnp to set
	 */
	public void setGppDurationForSnp(Character gppDurationForSnp);

	/**
	 * @return the snpRate
	 */
	public Short getSnpTerm();

	/**
	 * @param snpRate the snpRate to set
	 */
	public void setSnpTerm(Short snpTerm);

	/**
	 * @return the snpTermCodeNum
	 */
	public String getSnpTermCodeNum();

	/**
	 * @param snpTermCodeNum the snpTermCodeNum to set
	 */
	public void setSnpTermCodeNum(String snpTermCodeNum);

	/**
	 * @return the snpTermCodeValue
	 */
	public String getSnpTermCodeValue();

	/**
	 * @param snpTermCodeValue the snpTermCodeValue to set
	 */
	public void setSnpTermCodeValue(String snpTermCodeValue);

	/**
	 * @return the fullRelPft12Method
	 */
	public Boolean getFullRelPft12Method();

	/**
	 * @param fullRelPft12Method the fullRelPft12Method to set
	 */
	public void setFullRelPft12Method(Boolean fullRelPft12Method);
	
	/**
	 * @return the graceExpSPTFDate
	 */
	public Date getGraceExpSPTFDate();

	/**
	 * @param graceExpSPTFDate the graceExpSPTFDate to set
	 */
	public void setGraceExpSPTFDate(Date graceExpSPTFDate);
	
	/**
	 * @return the fixedRefundAmount
	 */
	public Amount getFixedRefundAmount();

	/**
	 * @param fixedRefundAmount the fixedRefundAmount to set
	 */
	public void setFixedRefundAmount(Amount fixedRefundAmount);
	
}
