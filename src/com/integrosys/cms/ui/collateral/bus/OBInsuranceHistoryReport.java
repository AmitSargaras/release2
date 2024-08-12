package com.integrosys.cms.ui.collateral.bus;

import java.math.BigDecimal;
import java.util.Date;

public class OBInsuranceHistoryReport implements IInsuranceHistoryReport {

	private Long index;
	private String partyName;
	private String partyId;
	private Long securityId;
	private String securitySubType;
	private String status;
	private Date dueDate;
	private String insurancePolicyNo;
	private String insuranceCompanyName;
	private BigDecimal insuredAmount;
	private Date expiryDate;
	private Date receivedDate;
	private String oldInsurancePolicyNo;
	private String oldInsuranceCompanyName;
	private BigDecimal oldInsuredAmount;
	private Date oldExpiryDate;
	private Date oldReceivedDate;
	
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public Long getSecurityId() {
		return securityId;
	}
	public void setSecurityId(Long securityId) {
		this.securityId = securityId;
	}
	public String getSecuritySubType() {
		return securitySubType;
	}
	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
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
	public String getOldInsurancePolicyNo() {
		return oldInsurancePolicyNo;
	}
	public void setOldInsurancePolicyNo(String oldInsurancePolicyNo) {
		this.oldInsurancePolicyNo = oldInsurancePolicyNo;
	}
	public String getOldInsuranceCompanyName() {
		return oldInsuranceCompanyName;
	}
	public void setOldInsuranceCompanyName(String oldInsuranceCompanyName) {
		this.oldInsuranceCompanyName = oldInsuranceCompanyName;
	}
	public BigDecimal getOldInsuredAmount() {
		return oldInsuredAmount;
	}
	public void setOldInsuredAmount(BigDecimal oldInsuredAmount) {
		this.oldInsuredAmount = oldInsuredAmount;
	}
	public Date getOldExpiryDate() {
		return oldExpiryDate;
	}
	public void setOldExpiryDate(Date oldExpiryDate) {
		this.oldExpiryDate = oldExpiryDate;
	}
	public Date getOldReceivedDate() {
		return oldReceivedDate;
	}
	public void setOldReceivedDate(Date oldReceivedDate) {
		this.oldReceivedDate = oldReceivedDate;
	}
	
}
