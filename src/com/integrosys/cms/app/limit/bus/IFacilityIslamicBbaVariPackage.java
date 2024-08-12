package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author siew kheat
 *
 */
/**
 * @author user
 *
 */
public interface IFacilityIslamicBbaVariPackage extends Serializable {

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
	 * @param facilityMasterId
	 *            the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId);

	/**
	 * @return the custProfRate
	 */
	public Double getCustProfRate();

	/**
	 * @param custProfRate
	 *            the custProfRate to set
	 */
	public void setCustProfRate(Double custProfRate);

	/**
	 * @return the rebateMethod
	 */
	public Character getRebateMethod();

	/**
	 * @param rebateMethod
	 *            the rebateMethod to set
	 */
	public void setRebateMethod(Character rebateMethod);

	/**
	 * @return the gppPaymentMode
	 */
	public String getGppPaymentMode();

	/**
	 * @param gppPaymentMode
	 *            the gppPaymentMode to set
	 */
	public void setGppPaymentMode(String gppPaymentMode);

	/**
	 * @return the gppCalculationMethod
	 */
	public String getGppCalculationMethod();

	/**
	 * @param gppCalculationMethod
	 *            the gppCalculationMethod to set
	 */
	public void setGppCalculationMethod(String gppCalculationMethod);

	/**
	 * @return the gppTerm
	 */
	public Short getGppTerm();
	/**
	 * @param gppTerm
	 *            the gppTerm to set
	 */
	public void setGppTerm(Short gppTerm);

	/**
	 * @return the gppTermCode
	 */
	public Character getGppTermCode();

	/**
	 * @param gppTermCode
	 *            the gppTermCode to set
	 */
	public void setGppTermCode(Character gppTermCode);

	/**
	 * @return the fullReleaseProfit
	 */
	public Boolean getFullReleaseProfit();

	/**
	 * @param fullReleaseProfit
	 *            the fullReleaseProfit to set
	 */
	public void setFullReleaseProfit(Boolean fullReleaseProfit);

	/**
	 * @return the refundFullReleaseProfit
	 */
	public Character getRefundFullReleaseProfit();

	/**
	 * @param refundFullReleaseProfit
	 *            the refundFullReleaseProfit to set
	 */
	public void setRefundFullReleaseProfit(Character refundFullReleaseProfit);

	/**
	 * @return the fulrelProfitCalMethod
	 */
	public Character getFulrelProfitCalMethod();

	/**
	 * @param fulrelProfitCalMethod
	 *            the fulrelProfitCalMethod to set
	 */
	public void setFulrelProfitCalMethod(Character fulrelProfitCalMethod);

	/**
	 * @return the installment
	 */
	public Amount getInstallment();
	/**
	 * @param instalment
	 *            the installment to set
	 */
	public void setInstallment(Amount installment);

	/**
	 * @return the finalPaymentAmount
	 */
	public Amount getFinalPaymentAmount();

	/**
	 * @param finalPaymentAmount
	 *            the finalPaymentAmount to set
	 */
	public void setFinalPaymentAmount(Amount finalPaymentAmount);

	/**
	 * @return the totalGppAmount
	 */
	public Amount getTotalGppAmount();

	/**
	 * @param totalGppAmount
	 *            the totalGppAmount to set
	 */
	public void setTotalGppAmount(Amount totalGppAmount);

	/**
	 * @return the sellingPrice
	 */
	public Amount getSellingPrice();

	/**
	 * @param sellingPrice
	 *            the sellingPrice to set
	 */
	public void setSellingPrice(Amount sellingPrice);

	/**
	 * @return the totalProfit
	 */
	public Amount getTotalProfit();

	/**
	 * @param totalProfit
	 *            the totalProfit to set
	 */
	public void setTotalProfit(Amount totalProfit);
	/**
	 * @return the lastMaintainDate
	 */
	public Date getLastMaintainDate();

	/**
	 * @param lastMaintainDate
	 *            the lastMaintainDate to set
	 */
	public void setLastMaintainDate(Date lastMaintainDate);

}
