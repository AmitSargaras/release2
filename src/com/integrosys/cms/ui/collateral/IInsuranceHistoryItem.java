package com.integrosys.cms.ui.collateral;

import java.math.BigDecimal;
import java.util.Date;

public interface IInsuranceHistoryItem {

	public Long getIndex();
	public void setIndex(Long index);
	
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
	
}
