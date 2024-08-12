/*
 * Copyright Integro Technologies Pte Ltd
 *
 */
package com.integrosys.cms.host.eai.security.bus.guarantee;

import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents approved security of type Guarantee.
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @since 2003/12/08
 */
public class GuaranteeSecurity extends ApprovedSecurity {

	private static final long serialVersionUID = -7893797264805601087L;

	private String guaranteesDescription;

	private String refNo;

	private Double guaranteeAmt;

	private String currency;

	private Date guaranteeDate;

	private String bankCountryRiskApproval;

	private String issuingBank;

	private String issuingBankCtry;

	private String beneficiaryName;

	private Double mininalFsv;

	private String projectName;

	private Date awardedDate;

	private String letterOfInstructionFlag;

	private String letterOfUndertakingFlag;

	private String blanketAssignment;

	private String comments;

	private String correspondenceBank;

	private String withinCorrespondanceBankLimit;

	private Double holdingPeriod;

	private String holdingPeriodTimeunit;

	private Long claimPeriod;

	private String claimPeriodUOM;

	private StandardCode reimbursementBank;

	private StandardCode scheme;

	private String issueDate;

	private Double securedAmount;

	private Double unsecuredAmount;

	private Integer securedPercentage;

	private Integer unsecuredPercentage;

	private Date lgCancellationDate;

	/**
	 * Default constructor.
	 */
	public GuaranteeSecurity() {
		super();
	}

	public String getAwardedDate() {
		return MessageDate.getInstance().getString(awardedDate);
	}

	public String getBankCountryRiskApproval() {
		return bankCountryRiskApproval;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public String getBlanketAssignment() {
		return blanketAssignment;
	}

	public Long getClaimPeriod() {
		return claimPeriod;
	}

	public String getClaimPeriodUOM() {
		return this.claimPeriodUOM;
	}

	public String getComments() {
		return comments;
	}

	public String getCorrespondenceBank() {
		return correspondenceBank;
	}

	public String getCurrency() {
		return currency;
	}

	public Double getGuaranteeAmt() {
		return guaranteeAmt;
	}

	public String getGuaranteeDate() {
		return MessageDate.getInstance().getString(guaranteeDate);
	}

	public String getGuaranteesDescription() {
		return guaranteesDescription;
	}

	public Double getHoldingPeriod() {
		return holdingPeriod;
	}

	public String getHoldingPeriodTimeunit() {
		return holdingPeriodTimeunit;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public String getIssuingBank() {
		return issuingBank;
	}

	public String getIssuingBankCtry() {
		return issuingBankCtry;
	}

	public Date getJDOAwardedDate() {
		return awardedDate;
	}

	public Date getJDOGuaranteeDate() {
		return guaranteeDate;
	}

	public Date getJDOIssueDate() {
		return MessageDate.getInstance().getDate(issueDate);
	}

	public String getLetterOfInstructionFlag() {
		return letterOfInstructionFlag;
	}

	public String getLetterOfUndertakingFlag() {
		return letterOfUndertakingFlag;
	}

	public Date getLgCancellationDate() {
		return lgCancellationDate;
	}

	public Double getMininalFsv() {
		return mininalFsv;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getRefNo() {
		return refNo;
	}

	public StandardCode getReimbursementBank() {
		return reimbursementBank;
	}

	public StandardCode getScheme() {
		return scheme;
	}

	public Double getSecuredAmount() {
		return securedAmount;
	}

	public Integer getSecuredPercentage() {
		return securedPercentage;
	}

	public Double getUnsecuredAmount() {
		return unsecuredAmount;
	}

	public Integer getUnsecuredPercentage() {
		return unsecuredPercentage;
	}

	public String getWithinCorrespondanceBankLimit() {
		return withinCorrespondanceBankLimit;
	}

	public void setAwardedDate(Date awardedDate) {
		this.awardedDate = awardedDate;
	}

	public void setAwardedDate(String awardedDate) {
		this.awardedDate = MessageDate.getInstance().getDate(awardedDate);
	}

	public void setBankCountryRiskApproval(String bankCountryRiskApproval) {
		this.bankCountryRiskApproval = bankCountryRiskApproval;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public void setBlanketAssignment(String blanketAssignment) {
		this.blanketAssignment = blanketAssignment;
	}

	public void setClaimPeriod(Long claimPeriod) {
		this.claimPeriod = claimPeriod;
	}

	public void setClaimPeriodUOM(String claimPeriodUOM) {
		this.claimPeriodUOM = claimPeriodUOM;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCorrespondenceBank(String correspondenceBank) {
		this.correspondenceBank = correspondenceBank;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setGuaranteeAmt(Double guaranteeAmt) {
		this.guaranteeAmt = guaranteeAmt;
	}

	public void setGuaranteeDate(Date guaranteeDate) {
		this.guaranteeDate = guaranteeDate;
	}

	public void setGuaranteeDate(String guaranteeDate) {
		this.guaranteeDate = MessageDate.getInstance().getDate(guaranteeDate);
	}

	public void setGuaranteesDescription(String guaranteesDescription) {
		this.guaranteesDescription = guaranteesDescription;
	}

	public void setHoldingPeriod(Double holdingPeriod) {
		this.holdingPeriod = holdingPeriod;
	}

	public void setHoldingPeriodTimeunit(String holdingPeriodTimeunit) {
		this.holdingPeriodTimeunit = holdingPeriodTimeunit;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}

	public void setIssuingBankCtry(String issuingBankCtry) {
		this.issuingBankCtry = issuingBankCtry;
	}

	public void setJDOAwardedDate(Date awardedDate) {
		this.awardedDate = awardedDate;
	}

	public void setJDOGuaranteeDate(Date guaranteeDate) {
		this.guaranteeDate = guaranteeDate;
	}

	public void setJDOIssueDate(Date issueDate) {
		this.issueDate = MessageDate.getInstance().getString(issueDate);
	}

	public void setLetterOfInstructionFlag(String letterOfInstructionFlag) {
		this.letterOfInstructionFlag = letterOfInstructionFlag;
	}

	public void setLetterOfUndertakingFlag(String letterOfUndertakingFlag) {
		this.letterOfUndertakingFlag = letterOfUndertakingFlag;
	}

	public void setLgCancellationDate(Date lgCancellationDate) {
		this.lgCancellationDate = lgCancellationDate;
	}

	public void setMininalFsv(Double mininalFsv) {
		this.mininalFsv = mininalFsv;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public void setReimbursementBank(StandardCode reimbursementBank) {
		this.reimbursementBank = reimbursementBank;
	}

	public void setScheme(StandardCode scheme) {
		this.scheme = scheme;
	}

	public void setSecuredAmount(Double securedAmount) {
		this.securedAmount = securedAmount;
	}

	public void setSecuredPercentage(Integer securedPercentage) {
		this.securedPercentage = securedPercentage;
	}

	public void setUnsecuredAmount(Double unsecuredAmount) {
		this.unsecuredAmount = unsecuredAmount;
	}

	public void setUnsecuredPercentage(Integer unsecuredPercentage) {
		this.unsecuredPercentage = unsecuredPercentage;
	}

	public void setWithinCorrespondanceBankLimit(String withinCorrespondanceBankLimit) {
		this.withinCorrespondanceBankLimit = withinCorrespondanceBankLimit;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("GuaranteeSecurity [");
		buf.append("losSecurityId=");
		buf.append(getLOSSecurityId());
		buf.append(", guaranteesDescription=");
		buf.append(guaranteesDescription);
		buf.append(", claimPeriod=");
		buf.append(claimPeriod);
		buf.append(", claimPeriodUOM=");
		buf.append(claimPeriodUOM);
		buf.append(", refNo=");
		buf.append(refNo);
		buf.append(", guaranteeAmt=");
		buf.append(guaranteeAmt);
		buf.append(", guaranteeDate=");
		buf.append(guaranteeDate);
		buf.append(", issuingBank=");
		buf.append(issuingBank);
		buf.append(", issuingBankCtry=");
		buf.append(issuingBankCtry);
		buf.append(", beneficiaryName=");
		buf.append(beneficiaryName);
		buf.append(", comments=");
		buf.append(comments);
		buf.append("]");
		return buf.toString();
	}

}
