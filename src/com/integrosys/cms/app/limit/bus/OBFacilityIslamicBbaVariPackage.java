/**
 * 
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author user
 * 
 */
public class OBFacilityIslamicBbaVariPackage implements
		IFacilityIslamicBbaVariPackage {

	private static final long serialVersionUID = 1674153030698668882L;

	private long facilityMasterId;
	private Double custProfRate;
	private Character rebateMethod;
	private String gppPaymentMode;
	private String gppCalculationMethod;
	private Short gppTerm;
	private Character gppTermCode;
	private Boolean fullReleaseProfit;
	private Character refundFullReleaseProfit;
	private Character fulrelProfitCalMethod;
	private Amount installment;
	private Amount finalPaymentAmount;
	private Amount totalGppAmount;
	private Amount sellingPrice;
	private Amount totalProfit;
	private Date lastMaintainDate;
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
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	/**
	 * @param facilityMasterId
	 *            the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	/**
	 * @return the custProfRate
	 */
	public Double getCustProfRate() {
		return custProfRate;
	}

	/**
	 * @param custProfRate
	 *            the custProfRate to set
	 */
	public void setCustProfRate(Double custProfRate) {
		this.custProfRate = custProfRate;
	}

	/**
	 * @return the rebateMethod
	 */
	public Character getRebateMethod() {
		return rebateMethod;
	}

	/**
	 * @param rebateMethod
	 *            the rebateMethod to set
	 */
	public void setRebateMethod(Character rebateMethod) {
		this.rebateMethod = rebateMethod;
	}

	/**
	 * @return the gppPaymentMode
	 */
	public String getGppPaymentMode() {
		return gppPaymentMode;
	}

	/**
	 * @param gppPaymentMode
	 *            the gppPaymentMode to set
	 */
	public void setGppPaymentMode(String gppPaymentMode) {
		this.gppPaymentMode = gppPaymentMode;
	}

	/**
	 * @return the gppCalculationMethod
	 */
	public String getGppCalculationMethod() {
		return gppCalculationMethod;
	}

	/**
	 * @param gppCalculationMethod
	 *            the gppCalculationMethod to set
	 */
	public void setGppCalculationMethod(String gppCalculationMethod) {
		this.gppCalculationMethod = gppCalculationMethod;
	}

	/**
	 * @return the gppTerm
	 */
	public Short getGppTerm() {
		return gppTerm;
	}

	/**
	 * @param gppTerm
	 *            the gppTerm to set
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
	 * @param gppTermCode
	 *            the gppTermCode to set
	 */
	public void setGppTermCode(Character gppTermCode) {
		this.gppTermCode = gppTermCode;
	}

	/**
	 * @return the fullReleaseProfit
	 */
	public Boolean getFullReleaseProfit() {
		return fullReleaseProfit;
	}

	/**
	 * @param fullReleaseProfit
	 *            the fullReleaseProfit to set
	 */
	public void setFullReleaseProfit(Boolean fullReleaseProfit) {
		this.fullReleaseProfit = fullReleaseProfit;
	}

	/**
	 * @return the refundFullReleaseProfit
	 */
	public Character getRefundFullReleaseProfit() {
		return refundFullReleaseProfit;
	}

	/**
	 * @param refundFullReleaseProfit
	 *            the refundFullReleaseProfit to set
	 */
	public void setRefundFullReleaseProfit(Character refundFullReleaseProfit) {
		this.refundFullReleaseProfit = refundFullReleaseProfit;
	}

	/**
	 * @return the fulrelProfitCalMethod
	 */
	public Character getFulrelProfitCalMethod() {
		return fulrelProfitCalMethod;
	}

	/**
	 * @param fulrelProfitCalMethod
	 *            the fulrelProfitCalMethod to set
	 */
	public void setFulrelProfitCalMethod(Character fulrelProfitCalMethod) {
		this.fulrelProfitCalMethod = fulrelProfitCalMethod;
	}

	/**
	 * @return the installment
	 */
	public Amount getInstallment() {
		return installment;
	}

	/**
	 * @param installment
	 *            the installment to set
	 */
	public void setInstallment(Amount installment) {
		this.installment = installment;
	}

	/**
	 * @return the finalPaymentAmount
	 */
	public Amount getFinalPaymentAmount() {
		return finalPaymentAmount;
	}

	/**
	 * @param finalPaymentAmount
	 *            the finalPaymentAmount to set
	 */
	public void setFinalPaymentAmount(Amount finalPaymentAmount) {
		this.finalPaymentAmount = finalPaymentAmount;
	}

	/**
	 * @return the totalGppAmount
	 */
	public Amount getTotalGppAmount() {
		return totalGppAmount;
	}

	/**
	 * @param totalGppAmount
	 *            the totalGppAmount to set
	 */
	public void setTotalGppAmount(Amount totalGppAmount) {
		this.totalGppAmount = totalGppAmount;
	}

	/**
	 * @return the sellingPrice
	 */
	public Amount getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice
	 *            the sellingPrice to set
	 */
	public void setSellingPrice(Amount sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the totalProfit
	 */
	public Amount getTotalProfit() {
		return totalProfit;
	}

	/**
	 * @param totalProfit
	 *            the totalProfit to set
	 */
	public void setTotalProfit(Amount totalProfit) {
		this.totalProfit = totalProfit;
	}

	/**
	 * @return the lastMaintainDate
	 */
	public Date getLastMaintainDate() {
		return lastMaintainDate;
	}

	/**
	 * @param lastMaintainDate
	 *            the lastMaintainDate to set
	 */
	public void setLastMaintainDate(Date lastMaintainDate) {
		this.lastMaintainDate = lastMaintainDate;
	}

}
