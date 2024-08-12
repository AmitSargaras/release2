package com.integrosys.cms.ui.collateral;

import java.util.Date;
import java.util.List;

public interface IInsuranceHistory {

	public String getInsuranceCompanyName();
	public void setInsuranceCompanyName(String insuranceCompanyName);
	
	public Date getReceivedDateFrom();
	public void setReceivedDateFrom(Date receivedDateFrom);
	
	public Date getReceivedDateTo();
	public void setReceivedDateTo(Date receivedDateTo);
	
	public List<IInsuranceHistoryItem> getInsuranceHistoryItems();
	public void setInsuranceHistoryItems(List<IInsuranceHistoryItem> insuranceHistoryItems);
}
