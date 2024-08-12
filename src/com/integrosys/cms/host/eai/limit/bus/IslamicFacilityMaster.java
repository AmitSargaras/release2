package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * To map the IslamicFacilityMaster portion of the CA001 message.
 * @author Thurein
 * 
 */
public class IslamicFacilityMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer GPPTerm;

	private String GPPTermCode;

	private String GPPPaymentMode;

	private Double customerInterestRate;

	private String GPPCalcMethod;

	private String compoundingMethod;

	private String dateStopCompounding;

	private String fulrelProfitCalMehod;

	private String refundGppProfit;

	private String refundFulRelProfit;

	private String excCMPInPMTAMT;

	private Double sellingPrice;

	private Double totalGPPAmt;

	private String updateStatusIndicator;

	private String changeIndicator;

	private String currencyCode;

	public Integer getGPPTerm() {
		return GPPTerm;
	}

	public void setGPPTerm(Integer term) {
		GPPTerm = term;
	}

	public String getGPPTermCode() {
		return GPPTermCode;
	}

	public void setGPPTermCode(String termCode) {
		GPPTermCode = termCode;
	}

	public String getGPPPaymentMode() {
		return GPPPaymentMode;
	}

	public void setGPPPaymentMode(String paymentMode) {
		GPPPaymentMode = paymentMode;
	}

	public Double getCustomerInterestRate() {
		return customerInterestRate;
	}

	public void setCustomerInterestRate(Double customerInterestRate) {
		this.customerInterestRate = customerInterestRate;
	}

	public String getGPPCalcMethod() {
		return GPPCalcMethod;
	}

	public void setGPPCalcMethod(String calcMethod) {
		GPPCalcMethod = calcMethod;
	}

	public String getCompoundingMethod() {
		return compoundingMethod;
	}

	public void setCompoundingMethod(String compoundingMethod) {
		this.compoundingMethod = compoundingMethod;
	}

	public String getDateStopCompounding() {
		return dateStopCompounding;
	}

	public void setDateStopCompounding(String dateStopCompounding) {
		this.dateStopCompounding = dateStopCompounding;
	}

	public Date getJDODateStopCompounding() {
		return MessageDate.getInstance().getDate(dateStopCompounding);
	}

	public void setJDODateStopCompounding(Date dateStopCompounding) {
		this.dateStopCompounding = MessageDate.getInstance().getString(dateStopCompounding);
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public String getFulrelProfitCalMehod() {
		return fulrelProfitCalMehod;
	}

	public void setFulrelProfitCalMehod(String fulrelProfitCalMehod) {
		this.fulrelProfitCalMehod = fulrelProfitCalMehod;
	}

	public String getRefundGppProfit() {
		return refundGppProfit;
	}

	public void setRefundGppProfit(String refundGppProfit) {
		this.refundGppProfit = refundGppProfit;
	}

	public String getRefundFulRelProfit() {
		return refundFulRelProfit;
	}

	public void setRefundFulRelProfit(String refundFulRelProfit) {
		this.refundFulRelProfit = refundFulRelProfit;
	}

	public String getExcCMPInPMTAMT() {
		return excCMPInPMTAMT;
	}

	public void setExcCMPInPMTAMT(String excCMPInPMTAMT) {
		this.excCMPInPMTAMT = excCMPInPMTAMT;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Double getTotalGPPAmt() {
		return totalGPPAmt;
	}

	public void setTotalGPPAmt(Double totalGPPAmt) {
		this.totalGPPAmt = totalGPPAmt;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}
