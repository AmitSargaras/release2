package com.integrosys.cms.batch.mimb.collateral;

import java.util.Date;

public class OBCash implements ICash {

	private long tempID;

	private String recordType;

	private String sourceSecurityID;

	private String securityType;

	private String securitySubType;

	private String originatingSecurityCurrency;

	private String cmsSecurityCurrency;

	private String securityLocation;

	private String branchName;

	private String branchDescription;

	private String securityCustodianType;

	private String securityCustodianValue;

	private Date securityPerfectionDate;

	private String legalEnforceability;

	private Date legalEnforceabilityDate;

	private String exchangeControlApprovalObtained;

	private String depositOrSourceReferenceNumber;

	private String depositCurrency;

	private Double depositAmount;

	private String securityPledgorID;

	private String securityPledgorName;

	private String securityPledgorRelationship;

	private String aaNo;

	private String sourceLimitID;

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getSourceSecurityID() {
		return sourceSecurityID;
	}

	public void setSourceSecurityID(String sourceSecurityID) {
		this.sourceSecurityID = sourceSecurityID;
	}

	public long getTempID() {
		return tempID;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getSecuritySubType() {
		return securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public String getOriginatingSecurityCurrency() {
		return originatingSecurityCurrency;
	}

	public void setOriginatingSecurityCurrency(String originatingSecurityCurrency) {
		this.originatingSecurityCurrency = originatingSecurityCurrency;
	}

	public String getCmsSecurityCurrency() {
		return cmsSecurityCurrency;
	}

	public void setCmsSecurityCurrency(String cmsSecurityCurrency) {
		this.cmsSecurityCurrency = cmsSecurityCurrency;
	}

	public String getSecurityLocation() {
		return securityLocation;
	}

	public void setSecurityLocation(String securityLocation) {
		this.securityLocation = securityLocation;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchDescription() {
		return branchDescription;
	}

	public void setBranchDescription(String branchDescription) {
		this.branchDescription = branchDescription;
	}

	public String getSecurityCustodianType() {
		return securityCustodianType;
	}

	public void setSecurityCustodianType(String securityCustodianType) {
		this.securityCustodianType = securityCustodianType;
	}

	public String getSecurityCustodianValue() {
		return securityCustodianValue;
	}

	public void setSecurityCustodianValue(String securityCustodianValue) {
		this.securityCustodianValue = securityCustodianValue;
	}

	public Date getSecurityPerfectionDate() {
		return securityPerfectionDate;
	}

	public void setSecurityPerfectionDate(Date securityPerfectionDate) {
		this.securityPerfectionDate = securityPerfectionDate;
	}

	public String getLegalEnforceability() {
		return legalEnforceability;
	}

	public void setLegalEnforceability(String legalEnforceability) {
		this.legalEnforceability = legalEnforceability;
	}

	public Date getLegalEnforceabilityDate() {
		return legalEnforceabilityDate;
	}

	public void setLegalEnforceabilityDate(Date legalEnforceabilityDate) {
		this.legalEnforceabilityDate = legalEnforceabilityDate;
	}

	public String getExchangeControlApprovalObtained() {
		return exchangeControlApprovalObtained;
	}

	public void setExchangeControlApprovalObtained(String exchangeControlApprovalObtained) {
		this.exchangeControlApprovalObtained = exchangeControlApprovalObtained;
	}

	public String getDepositOrSourceReferenceNumber() {
		return depositOrSourceReferenceNumber;
	}

	public void setDepositOrSourceReferenceNumber(String depositOrSourceReferenceNumber) {
		this.depositOrSourceReferenceNumber = depositOrSourceReferenceNumber;
	}

	public String getDepositCurrency() {
		return depositCurrency;
	}

	public void setDepositCurrency(String depositCurrency) {
		this.depositCurrency = depositCurrency;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getSecurityPledgorID() {
		return securityPledgorID;
	}

	public void setSecurityPledgorID(String securityPledgorID) {
		this.securityPledgorID = securityPledgorID;
	}

	public String getSecurityPledgorName() {
		return securityPledgorName;
	}

	public void setSecurityPledgorName(String securityPledgorName) {
		this.securityPledgorName = securityPledgorName;
	}

	public String getSecurityPledgorRelationship() {
		return securityPledgorRelationship;
	}

	public void setSecurityPledgorRelationship(String securityPledgorRelationship) {
		this.securityPledgorRelationship = securityPledgorRelationship;
	}

	public String getAaNo() {
		return aaNo;
	}

	public void setAaNo(String aaNo) {
		this.aaNo = aaNo;
	}

	public String getSourceLimitID() {
		return sourceLimitID;
	}

	public void setSourceLimitID(String sourceLimitID) {
		this.sourceLimitID = sourceLimitID;
	}

	public void setTempID(long tempID) {
		this.tempID = tempID;
	}

}
