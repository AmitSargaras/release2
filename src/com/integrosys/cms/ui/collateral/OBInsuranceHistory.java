package com.integrosys.cms.ui.collateral;

import java.util.Date;
import java.util.List;

public class OBInsuranceHistory implements IInsuranceHistory {
	
	private String insuranceCompanyName;
	private Date receivedDateFrom;
	private Date receivedDateTo;
	private List<IInsuranceHistoryItem> insuranceHistoryItems;
	
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public Date getReceivedDateFrom() {
		return receivedDateFrom;
	}
	public void setReceivedDateFrom(Date receivedDateFrom) {
		this.receivedDateFrom = receivedDateFrom;
	}
	public Date getReceivedDateTo() {
		return receivedDateTo;
	}
	public void setReceivedDateTo(Date receivedDateTo) {
		this.receivedDateTo = receivedDateTo;
	}
	public List<IInsuranceHistoryItem> getInsuranceHistoryItems() {
		return insuranceHistoryItems;
	}
	public void setInsuranceHistoryItems(List<IInsuranceHistoryItem> insuranceHistoryItems) {
		this.insuranceHistoryItems = insuranceHistoryItems;
	}
	
}
