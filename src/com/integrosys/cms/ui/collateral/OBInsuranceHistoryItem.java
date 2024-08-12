package com.integrosys.cms.ui.collateral;

import java.math.BigDecimal;
import java.util.Date;

public class OBInsuranceHistoryItem implements IInsuranceHistoryItem {

	private Long index;
	private String status;
	private Date dueDate;
	private String insurancePolicyNo;
	private String insuranceCompanyName;
	private BigDecimal insuredAmount;
	private Date expiryDate;
	private Date receivedDate;
	
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getInsurancePolicyNo() {
		return insurancePolicyNo;
	}
	public void setInsurancePolicyNo(String insurancePolicyNo) {
		this.insurancePolicyNo = insurancePolicyNo;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public BigDecimal getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(BigDecimal insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	
}