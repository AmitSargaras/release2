package com.integrosys.cms.ui.limit.facility.bbavaripkg;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainForm;

public class FacilityIslamicBbaVariPackageForm extends FacilityMainForm {/*
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

	private String custProfRate;
	private String rebateMethod;
	private String gppPaymentMode;
	private String gppCalculationMethod;
	private String gppTerm;
	private String gppTermCode;
	private String fullReleaseProfit;
	private String refundFullReleaseProfit;
	private String fulrelProfitCalMethod;
	private String installment;
	private String finalPaymentAmount;
	private String totalGppAmount;
	private String sellingPrice;
	private String totalProfit;	
	
	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.bbavaripkg.FacilityIslamicBbaVariPackageMapper";
	
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}
	
	/**
	 * @return the custProfRate
	 */
	public String getCustProfRate() {
		return custProfRate;
	}



	/**
	 * @param custProfRate the custProfRate to set
	 */
	public void setCustProfRate(String custProfRate) {
		this.custProfRate = custProfRate;
	}



	/**
	 * @return the rebateMethod
	 */
	public String getRebateMethod() {
		return rebateMethod;
	}



	/**
	 * @param rebateMethod the rebateMethod to set
	 */
	public void setRebateMethod(String rebateMethod) {
		this.rebateMethod = rebateMethod;
	}



	/**
	 * @return the gppPaymentMode
	 */
	public String getGppPaymentMode() {
		return gppPaymentMode;
	}



	/**
	 * @param gppPaymentMode the gppPaymentMode to set
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
	 * @param gppCalculationMethod the gppCalculationMethod to set
	 */
	public void setGppCalculationMethod(String gppCalculationMethod) {
		this.gppCalculationMethod = gppCalculationMethod;
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
	 * @return the fullReleaseProfit
	 */
	public String getFullReleaseProfit() {
		return fullReleaseProfit;
	}



	/**
	 * @param fullReleaseProfit the fullReleaseProfit to set
	 */
	public void setFullReleaseProfit(String fullReleaseProfit) {
		this.fullReleaseProfit = fullReleaseProfit;
	}



	/**
	 * @return the refundFullReleaseProfit
	 */
	public String getRefundFullReleaseProfit() {
		return refundFullReleaseProfit;
	}



	/**
	 * @param refundFullReleaseProfit the refundFullReleaseProfit to set
	 */
	public void setRefundFullReleaseProfit(String refundFullReleaseProfit) {
		this.refundFullReleaseProfit = refundFullReleaseProfit;
	}



	/**
	 * @return the fulrelProfitCalMethod
	 */
	public String getFulrelProfitCalMethod() {
		return fulrelProfitCalMethod;
	}



	/**
	 * @param fulrelProfitCalMethod the fulrelProfitCalMethod to set
	 */
	public void setFulrelProfitCalMethod(String fulrelProfitCalMethod) {
		this.fulrelProfitCalMethod = fulrelProfitCalMethod;
	}



	/**
	 * @return the installment
	 */
	public String getInstallment() {
		return installment;
	}



	/**
	 * @param instalment the installment to set
	 */
	public void setInstallment(String installment) {
		this.installment = installment;
	}



	/**
	 * @return the finalPaymentAmount
	 */
	public String getFinalPaymentAmount() {
		return finalPaymentAmount;
	}



	/**
	 * @param finalPaymentAmount the finalPaymentAmount to set
	 */
	public void setFinalPaymentAmount(String finalPaymentAmount) {
		this.finalPaymentAmount = finalPaymentAmount;
	}



	/**
	 * @return the totalGppAmount
	 */
	public String getTotalGppAmount() {
		return totalGppAmount;
	}



	/**
	 * @param totalGppAmount the totalGppAmount to set
	 */
	public void setTotalGppAmount(String totalGppAmount) {
		this.totalGppAmount = totalGppAmount;
	}



	/**
	 * @return the sellingPrice
	 */
	public String getSellingPrice() {
		return sellingPrice;
	}



	/**
	 * @param sellingPrice the sellingPrice to set
	 */
	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}



	/**
	 * @return the totalProfit
	 */
	public String getTotalProfit() {
		return totalProfit;
	}



	/**
	 * @param totalProfit the totalProfit to set
	 */
	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}



	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
