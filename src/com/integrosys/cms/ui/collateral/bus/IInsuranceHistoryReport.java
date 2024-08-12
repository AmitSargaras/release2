package com.integrosys.cms.ui.collateral.bus;

import java.math.BigDecimal;
import java.util.Date;

public interface IInsuranceHistoryReport {

	public Long getIndex();
	public void setIndex(Long index);
	
	public String getPartyName();
	public void setPartyName(String partyName);
	
	public String getPartyId();
	public void setPartyId(String partyId);
	
	public Long getSecurityId();
	public void setSecurityId(Long securityId);
	
	public String getSecuritySubType();
	public void setSecuritySubType(String securitySubType);
	
	public String getStatus();
	public void setStatus(String status);
	
	public Date getDueDate();
	public void setDueDate(Date dueDate);
	
	public String getInsurancePolicyNo();
	public void setInsurancePolicyNo(String insurancePolicyNo);
	
	public String getInsuranceCompanyName();
	public void setInsuranceCompanyName(String insuranceCompanyName);
	
	public BigDecimal getInsuredAmount();
	public void setInsuredAmount(BigDecimal insuredAmount);
	
	public Date getExpiryDate();
	public void setExpiryDate(Date expiryDate);
	
	public Date getReceivedDate();
	public void setReceivedDate(Date receivedDate);
	
	public String getOldInsurancePolicyNo();
	public void setOldInsurancePolicyNo(String oldInsurancePolicyNo);
	
	public String getOldInsuranceCompanyName();
	public void setOldInsuranceCompanyName(String oldInsuranceCompanyName);
	
	public BigDecimal getOldInsuredAmount();
	public void setOldInsuredAmount(BigDecimal oldInsuredAmount);
	
	public Date getOldExpiryDate();
	public void setOldExpiryDate(Date oldExpiryDate);
	
	public Date getOldReceivedDate();
	public void setOldReceivedDate(Date oldReceivedDate);
}
