package com.integrosys.cms.ui.collateral;

import java.util.List;

public class InsuranceHistorySearchResult {
	
	private Long collateralId;
	private String insuranceCompanyName;
	private String receivedDateFrom;
	private String receivedDateTo;	
	private List<IInsuranceHistoryItem> insuranceHistory;
	private String report;
	
	public Long getCollateralId() {
		return collateralId;
	}
	public void setCollateralId(Long collateralId) {
		this.collateralId = collateralId;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getReceivedDateFrom() {
		return receivedDateFrom;
	}
	public void setReceivedDateFrom(String receivedDateFrom) {
		this.receivedDateFrom = receivedDateFrom;
	}
	public String getReceivedDateTo() {
		return receivedDateTo;
	}
	public void setReceivedDateTo(String receivedDateTo) {
		this.receivedDateTo = receivedDateTo;
	}
	public List<IInsuranceHistoryItem> getInsuranceHistory() {
		return insuranceHistory;
	}
	public void setInsuranceHistory(List<IInsuranceHistoryItem> insuranceHistory) {
		this.insuranceHistory = insuranceHistory;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	
}
