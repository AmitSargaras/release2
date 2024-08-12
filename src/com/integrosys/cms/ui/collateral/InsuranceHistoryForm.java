package com.integrosys.cms.ui.collateral;

import java.util.List;

import com.integrosys.base.uiinfra.common.CommonSearchForm;

public class InsuranceHistoryForm extends CommonSearchForm {

	private static final long serialVersionUID = 1L;
	
	public String[][] getMapper(){	
        String[][] input =
            {
            	{CollateralConstant.INSURANCE_HISTORY_KEY, InsuranceHistoryMapper.class.getName()},
            };
        return input;
    }
	private String insuranceCompanyName;
	private String receivedDateFrom;
	private String receivedDateTo;
	private List<InsuranceHistoryItemForm> insuranceHistoryItem;
	private int recordsPerPage;
	private String report;
	
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
	public List<InsuranceHistoryItemForm> getInsuranceHistoryItem() {
		return insuranceHistoryItem;
	}
	public void setInsuranceHistoryItem(List<InsuranceHistoryItemForm> insuranceHistoryItem) {
		this.insuranceHistoryItem = insuranceHistoryItem;
	}
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	
}
