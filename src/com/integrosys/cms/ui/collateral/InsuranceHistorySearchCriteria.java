package com.integrosys.cms.ui.collateral;

import com.integrosys.base.businfra.search.SearchCriteria;

public class InsuranceHistorySearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = 1L;

	private Long collateralId;
	private String insuranceCompanyName;
	private String receivedDateFrom;
	private String receivedDateTo;
	
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
	
}