package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;

public class FacilityBBAVariPackage implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double custProfRate;

	private String rebateMethod;

	private String GPPPaymentMode;

	private String GPPCalcMethod;

	private Integer GPPTerm;

	private String GPPTermCode;

	private String fullReleaseProfit;

	private String refundFullReleaseProfit;

	private String fullReleaseProfitCalcMethod;

	private Double installment;

	private Double finalPaymentAmt;

	private Double sellingPrice;

	private Double totalProfit;

	private Double totalGPPAmt;

	private String updateStatusIndicator;

	private String changeIndicator;

	public Double getCustProfRate() {
		return custProfRate;
	}

	public void setCustProfRate(Double custProfRate) {
		this.custProfRate = custProfRate;
	}

	public String getRebateMethod() {
		return rebateMethod;
	}

	public void setRebateMethod(String rebateMethod) {
		this.rebateMethod = rebateMethod;
	}

	public String getGPPPaymentMode() {
		return GPPPaymentMode;
	}

	public void setGPPPaymentMode(String paymentMode) {
		GPPPaymentMode = paymentMode;
	}

	public String getGPPCalcMethod() {
		return GPPCalcMethod;
	}

	public void setGPPCalcMethod(String calcMethod) {
		GPPCalcMethod = calcMethod;
	}

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

	public String getFullReleaseProfit() {
		return fullReleaseProfit;
	}

	public void setFullReleaseProfit(String fullReleaseProfit) {
		this.fullReleaseProfit = fullReleaseProfit;
	}

	public String getRefundFullReleaseProfit() {
		return refundFullReleaseProfit;
	}

	public void setRefundFullReleaseProfit(String refundFullReleaseProfit) {
		this.refundFullReleaseProfit = refundFullReleaseProfit;
	}

	public String getFullReleaseProfitCalcMethod() {
		return fullReleaseProfitCalcMethod;
	}

	public void setFullReleaseProfitCalcMethod(String fullReleaseProfitCalcMethod) {
		this.fullReleaseProfitCalcMethod = fullReleaseProfitCalcMethod;
	}

	public Double getInstallment() {
		return installment;
	}

	public void setInstallment(Double installment) {
		this.installment = installment;
	}

	public Double getFinalPaymentAmt() {
		return finalPaymentAmt;
	}

	public void setFinalPaymentAmt(Double finalPaymentAmt) {
		this.finalPaymentAmt = finalPaymentAmt;
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

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public Double getTotalGPPAmt() {
		return totalGPPAmt;
	}

	public void setTotalGPPAmt(Double totalGPPAmt) {
		this.totalGPPAmt = totalGPPAmt;
	}

}
