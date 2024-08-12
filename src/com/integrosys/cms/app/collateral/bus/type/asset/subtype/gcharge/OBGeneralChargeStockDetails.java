package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBGeneralChargeStockDetails implements IGeneralChargeStockDetails {

	private long generalChargeStockDetailsID = ICMSConstant.LONG_MIN_VALUE;
	private long generalChargeDetailsID;
	private long locationId;
	private String locationDetail;
	private String stockType;
	private String marginType;
	private String component;
	private String componentAmount;
	private String margin;
	private String lonable;
	private String hasInsurance;
	private String insuranceCompanyName;
	private String insuranceCompanyCategory;
	private String insuredAmount;
	private Date effectiveDateOfInsurance;
	private Date expiryDate;
	private String insuranceDescription;
	
	private String insurancePolicyNo;
	private String insuranceCoverNote;
	private String insuranceCurrency;
	private String totalPolicyAmount;
	private Date insuranceRecivedDate;
	private String insuranceDefaulted;
	private String insurancePremium;
	
	//Start Santosh
	private String applicableForDp;
			
	public String getApplicableForDp() {
		return applicableForDp;
	}
	public void setApplicableForDp(String applicableForDp) {
		this.applicableForDp = applicableForDp;
	}
	//End Santosh
	
	public long getGeneralChargeDetailsID() {
		return generalChargeDetailsID;
	}
	public void setGeneralChargeDetailsID(long generalChargeDetailsID) {
		this.generalChargeDetailsID = generalChargeDetailsID;
	}
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	public String getLocationDetail() {
		return locationDetail;
	}
	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}
	public long getGeneralChargeStockDetailsID() {
		return generalChargeStockDetailsID;
	}
	public void setGeneralChargeStockDetailsID(long generalChargeStockDetailsID) {
		this.generalChargeStockDetailsID = generalChargeStockDetailsID;
	}
	public String getStockType() {
		return stockType;
	}
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	public String getMarginType() {
		return marginType;
	}
	public void setMarginType(String marginType) {
		this.marginType = marginType;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getComponentAmount() {
		return componentAmount;
	}
	public void setComponentAmount(String componentAmount) {
		this.componentAmount = componentAmount;
	}
	public String getMargin() {
		return margin;
	}
	public void setMargin(String margin) {
		this.margin = margin;
	}
	public String getLonable() {
		return lonable;
	}
	public void setLonable(String lonable) {
		this.lonable = lonable;
	}
	public String getHasInsurance() {
		return hasInsurance;
	}
	public void setHasInsurance(String hasInsurance) {
		this.hasInsurance = hasInsurance;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getInsuranceCompanyCategory() {
		return insuranceCompanyCategory;
	}
	public void setInsuranceCompanyCategory(String insuranceCompanyCategory) {
		this.insuranceCompanyCategory = insuranceCompanyCategory;
	}
	public String getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	public Date getEffectiveDateOfInsurance() {
		return effectiveDateOfInsurance;
	}
	public void setEffectiveDateOfInsurance(Date effectiveDateOfInsurance) {
		this.effectiveDateOfInsurance = effectiveDateOfInsurance;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getInsuranceDescription() {
		return insuranceDescription;
	}
	public void setInsuranceDescription(String insuranceDescription) {
		this.insuranceDescription = insuranceDescription;
	}
	public String getInsurancePolicyNo() {
		return insurancePolicyNo;
	}
	public void setInsurancePolicyNo(String insurancePolicyNo) {
		this.insurancePolicyNo = insurancePolicyNo;
	}
	public String getInsuranceCoverNote() {
		return insuranceCoverNote;
	}
	public void setInsuranceCoverNote(String insuranceCoverNote) {
		this.insuranceCoverNote = insuranceCoverNote;
	}
	public String getInsuranceCurrency() {
		return insuranceCurrency;
	}
	public void setInsuranceCurrency(String insuranceCurrency) {
		this.insuranceCurrency = insuranceCurrency;
	}
	public String getTotalPolicyAmount() {
		return totalPolicyAmount;
	}
	public void setTotalPolicyAmount(String totalPolicyAmount) {
		this.totalPolicyAmount = totalPolicyAmount;
	}
	public Date getInsuranceRecivedDate() {
		return insuranceRecivedDate;
	}
	public void setInsuranceRecivedDate(Date insuranceRecivedDate) {
		this.insuranceRecivedDate = insuranceRecivedDate;
	}
	public String getInsuranceDefaulted() {
		return insuranceDefaulted;
	}
	public void setInsuranceDefaulted(String insuranceDefaulted) {
		this.insuranceDefaulted = insuranceDefaulted;
	}
	public String getInsurancePremium() {
		return insurancePremium;
	}
	public void setInsurancePremium(String insurancePremium) {
		this.insurancePremium = insurancePremium;
	}
	
	private String stockComponentCat;

	public String getStockComponentCat() {
		return stockComponentCat;
	}
	public void setStockComponentCat(String stockComponentCat) {
		this.stockComponentCat = stockComponentCat;
	}

	private long cmsRefId;

	public long getCmsRefId() {
		return cmsRefId;
	}
	public void setCmsRefId(long cmsRefId) {
		this.cmsRefId = cmsRefId;
	}
	
}
