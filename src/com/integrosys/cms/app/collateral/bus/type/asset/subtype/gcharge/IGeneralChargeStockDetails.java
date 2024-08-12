package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.io.Serializable;
import java.util.Date;

public interface IGeneralChargeStockDetails extends Serializable {
	
	public long getGeneralChargeStockDetailsID() ;
	public void setGeneralChargeStockDetailsID(long generalChargeStockDetailsID) ;
	
	public long getLocationId() ;
	public void setLocationId(long locationId) ;
	
	public String getLocationDetail() ;
	public void setLocationDetail(String locationDetail);
	
	public String getStockType() ;
	public void setStockType(String stockType) ;
	
	public String getMarginType() ;
	public void setMarginType(String marginType) ;
	
	public String getComponent() ;
	public void setComponent(String component) ;
	
	public String getComponentAmount() ;
	public void setComponentAmount(String componentAmount);
	
	public String getMargin() ;
	public void setMargin(String margin) ;
	
	public String getLonable();
	public void setLonable(String lonable) ;
	
	public String getHasInsurance();
	public void setHasInsurance(String hasInsurance);
	
	public String getInsuranceCompanyName() ;
	public void setInsuranceCompanyName(String insuranceCompanyName);
	
	public String getInsuranceCompanyCategory() ;
	public void setInsuranceCompanyCategory(String insuranceCompanyCategory) ;
	
	public String getInsuredAmount() ;
	public void setInsuredAmount(String insuredAmount) ;
	
	public Date getEffectiveDateOfInsurance() ;
	public void setEffectiveDateOfInsurance(Date effectiveDateOfInsurance) ;
	
	public Date getExpiryDate() ;
	public void setExpiryDate(Date expiryDate) ;
	
	public String getInsuranceDescription() ;
	public void setInsuranceDescription(String insuranceDescription) ;
	
	public String getInsurancePolicyNo();
	public void setInsurancePolicyNo(String insurancePolicyNo) ;
	
	public String getInsuranceCoverNote();
	public void setInsuranceCoverNote(String insuranceCoverNote);
	
	public String getInsuranceCurrency() ;
	public void setInsuranceCurrency(String insuranceCurrency);
	
	public String getTotalPolicyAmount();
	public void setTotalPolicyAmount(String totalPolicyAmount) ;
	
	public Date getInsuranceRecivedDate() ;
	public void setInsuranceRecivedDate(Date insuranceRecivedDate) ;
	
	public String getInsuranceDefaulted();
	public void setInsuranceDefaulted(String insuranceDefaulted);
	
	public String getInsurancePremium();
	public void setInsurancePremium(String insurancePremium);
	
	//Foreign Key
	public long getGeneralChargeDetailsID();
	public void setGeneralChargeDetailsID(long generalChargeDetailsID) ;
	
	//Start Santosh
	public String getApplicableForDp();
	public void setApplicableForDp(String applicableForDp);
	//End Santosh
	
	public String getStockComponentCat();
	public void setStockComponentCat(String stockComponentCat);
	
	public long getCmsRefId();
	public void setCmsRefId(long cmsRefId);
	
	
}

