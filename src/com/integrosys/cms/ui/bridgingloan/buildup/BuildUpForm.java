/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.buildup;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * ActionForm for BuildUpAction.
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class BuildUpForm extends TrxContextForm implements Serializable {
	private String buildUpID = "";

	private String propertyType = "";

	private String unitID = "";

	private String blockNo = "";

	private String titleNo = "";

	private String unitNo = "";

	private String isUnitDischarged = "";

	private String approxArea = "";

	private String approxAreaUOM = "";

	private String approxAreaSecondary = "";

	private String approxAreaUOMSecondary = "";

	private String redemptionCurrency = "";

	private String redemptionAmount = "";

	private String salesCurrency = "";

	private String salesAmount = "";

	private String salesDate = ""; // Date of Sale & Purchase

	private String purchaserName = ""; // Name of Purchaser

	private String endFinancier = "";

	private String buRemarks = "";

	private String tenancyDate = ""; // Tenancy Agreement Date

	private String tenantName = "";

	private String tenancyPeriodUnit = "";

	private String tenancyPeriod = ""; // Period of Tenancy

	private String tenancyExpiryDate = "";

	private String rentalCurrency = "";

	private String rentalAmount = "";

	private String paymentFrequency = "";

	public String getBuildUpID() {
		return buildUpID;
	}

	public void setBuildUpID(String buildUpID) {
		this.buildUpID = buildUpID;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getUnitID() {
		return unitID;
	}

	public void setUnitID(String unitID) {
		this.unitID = unitID;
	}

	public String getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	public String getTitleNo() {
		return titleNo;
	}

	public void setTitleNo(String titleNo) {
		this.titleNo = titleNo;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getIsUnitDischarged() {
		return isUnitDischarged;
	}

	public void setIsUnitDischarged(String unitDischarged) {
		isUnitDischarged = unitDischarged;
	}

	public String getApproxArea() {
		return approxArea;
	}

	public void setApproxArea(String approxArea) {
		this.approxArea = approxArea;
	}

	public String getApproxAreaUOM() {
		return approxAreaUOM;
	}

	public void setApproxAreaUOM(String approxAreaUOM) {
		this.approxAreaUOM = approxAreaUOM;
	}

	public String getApproxAreaSecondary() {
		return approxAreaSecondary;
	}

	public void setApproxAreaSecondary(String approxAreaSecondary) {
		this.approxAreaSecondary = approxAreaSecondary;
	}

	public String getApproxAreaUOMSecondary() {
		return approxAreaUOMSecondary;
	}

	public void setApproxAreaUOMSecondary(String approxAreaUOMSecondary) {
		this.approxAreaUOMSecondary = approxAreaUOMSecondary;
	}

	public String getRedemptionCurrency() {
		return redemptionCurrency;
	}

	public void setRedemptionCurrency(String redemptionCurrency) {
		this.redemptionCurrency = redemptionCurrency;
	}

	public String getRedemptionAmount() {
		return redemptionAmount;
	}

	public void setRedemptionAmount(String redemptionAmount) {
		this.redemptionAmount = redemptionAmount;
	}

	public String getSalesCurrency() {
		return salesCurrency;
	}

	public void setSalesCurrency(String salesCurrency) {
		this.salesCurrency = salesCurrency;
	}

	public String getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(String salesAmount) {
		this.salesAmount = salesAmount;
	}

	public String getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getEndFinancier() {
		return endFinancier;
	}

	public void setEndFinancier(String endFinancier) {
		this.endFinancier = endFinancier;
	}

	public String getBuRemarks() {
		return buRemarks;
	}

	public void setBuRemarks(String buRemarks) {
		this.buRemarks = buRemarks;
	}

	public String getTenancyDate() {
		return tenancyDate;
	}

	public void setTenancyDate(String tenancyDate) {
		this.tenancyDate = tenancyDate;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenancyPeriodUnit() {
		return tenancyPeriodUnit;
	}

	public void setTenancyPeriodUnit(String tenancyPeriodUnit) {
		this.tenancyPeriodUnit = tenancyPeriodUnit;
	}

	public String getTenancyPeriod() {
		return tenancyPeriod;
	}

	public void setTenancyPeriod(String tenancyPeriod) {
		this.tenancyPeriod = tenancyPeriod;
	}

	public String getTenancyExpiryDate() {
		return tenancyExpiryDate;
	}

	public void setTenancyExpiryDate(String tenancyExpiryDate) {
		this.tenancyExpiryDate = tenancyExpiryDate;
	}

	public String getRentalCurrency() {
		return rentalCurrency;
	}

	public void setRentalCurrency(String rentalCurrency) {
		this.rentalCurrency = rentalCurrency;
	}

	public String getRentalAmount() {
		return rentalAmount;
	}

	public void setRentalAmount(String rentalAmount) {
		this.rentalAmount = rentalAmount;
	}

	public String getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue" },
				{ "objBridgingLoan", "com.integrosys.cms.ui.bridgingloan.buildup.BuildUpMapper" },
				{ "current_page", "java.lang.String" }, };
		return input;
	}
}