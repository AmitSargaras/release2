package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IInsuranceGC extends Serializable, IValueObject{
	
	public long getId();
	public void setId(long id);
	
	public long getVersionTime();
	public void setVersionTime(long versionTime);
	
	public long getParentId();
	public void setParentId(long parentId);
	
	public Date getCreationDate();
	public void setCreationDate(Date creationDate);
	
	public String getIsProcessed();
	public void setIsProcessed(String isProcessed);
	
	public String getDeprecated();
	public void setDeprecated(String deprecated);
	
	public String getInsuranceRequired();
	public void setInsuranceRequired(String insuranceRequired);
	
	public String getInsurancePolicyNo();
	public void setInsurancePolicyNo(String insurancePolicyNo);
	
	public String getCoverNoteNo();
	public void setCoverNoteNo(String coverNoteNo);
	
	public String getInsuranceCompany();
	public void setInsuranceCompany(String insuranceCompany);
	
	public String getInsuranceCurrency();
	public void setInsuranceCurrency(String insuranceCurrency);
	
	public String getInsuranceCoverge();
	public void setInsuranceCoverge(String insuranceCoverge);
	
	public String getInsurancePolicyAmt();
	public void setInsurancePolicyAmt(String insurancePolicyAmt);
	
	public String getInsuredAmount();
	public void setInsuredAmount(String insuredAmount);
	
	public Date getReceivedDate();
	public void setReceivedDate(Date receivedDate);
	
	public Date getEffectiveDate();
	public void setEffectiveDate(Date effectiveDate);
	
	public Date getExpiryDate();
	public void setExpiryDate(Date expiryDate);
	
	public String getInsuranceDefaulted();
	public void setInsuranceDefaulted(String insuranceDefaulted);
	
	public String getInsurancePremium();
	public void setInsurancePremium(String insurancePremium);
	
	public String getRemark();
	public void setRemark(String remark);
	
	/*public String getAllComponent();
	public void setAllComponent(String allComponent);*/
	
	public String getSelectComponent();
	public void setSelectComponent(String selectComponent);
	
	public String getInsuranceCode();
	public void setInsuranceCode(String insuranceCode);
	
	public String getAppendedComponent();
	public void setAppendedComponent(String appendedComponent);
	
	public String getLastUpdatedBy();
	public void setLastUpdatedBy(String lastUpdatedBy);
	
	public String getLastApproveBy();
	public void setLastApproveBy(String lastApproveBy);
	
	public Date getLastUpdatedOn();
	public void setLastUpdatedOn(Date lastUpdatedOn);
	
	public Date getLastApproveOn();
	public void setLastApproveOn(Date lastApproveOn);
	
//	public String getFundedShare() ;
//	public void setFundedShare(String fundedShare) ;
	
	public String getCalculatedDP() ;
	public void setCalculatedDP(String calculatedDP) ;
	
	public String getDueDate() ;
	public void setDueDate(String dueDate) ;
	
	public String getStockLocation() ;
	public void setStockLocation(String stockLocation);
	
	//Uma Khot:Cam upload and Dp field calculation CR
	public String getDpShare() ;
	public void setDpShare(String dpShare) ;

	//Uma Khot::Insurance Deferral maintainance
	public String getInsuranceStatus() ;
	public void setInsuranceStatus(String insuranceStatus);
	
	public String getInsuredAddress();
	public void setInsuredAddress(String insuredAddress);
	
	public String getRemark2();
	public void setRemark2(String remark2);
	
	public String getInsuredAgainst();
	public void setInsuredAgainst(String insuredAgainst);
	
	public Date getOriginalTargetDate();
	public void setOriginalTargetDate(Date originalTargetDate);
	
	public Date getNextDueDate();
	public void setNextDueDate(Date nextDueDate);
	
	public Date getDateDeferred();
	public void setDateDeferred(Date dateDeferred);
	
	public String getCreditApprover();
	public void setCreditApprover(String creditApprover);
	
	public Date getWaivedDate();
	public void setWaivedDate(Date waivedDate);
	
	public String getOldPolicyNo();
	public void setOldPolicyNo(String oldPolicyNo);
}
