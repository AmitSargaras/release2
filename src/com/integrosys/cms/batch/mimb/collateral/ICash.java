package com.integrosys.cms.batch.mimb.collateral;

import java.util.Date;

public interface ICash {

	public String getRecordType();

	public void setRecordType(String recordType);

	public String getSourceSecurityID();

	public void setSourceSecurityID(String sourceSecurityID);

	public String getSecurityType();

	public void setSecurityType(String securityType);

	public String getSecuritySubType();

	public void setSecuritySubType(String securitySubType);

	public String getOriginatingSecurityCurrency();

	public void setOriginatingSecurityCurrency(String originatingSecurityCurrency);

	public String getSecurityLocation();

	public void setSecurityLocation(String securityLocation);

	public String getBranchName();

	public void setBranchName(String branchName);

	public String getBranchDescription();

	public void setBranchDescription(String branchDescription);

	public String getSecurityCustodianType();

	public void setSecurityCustodianType(String securityCustodianType);

	public String getSecurityCustodianValue();

	public void setSecurityCustodianValue(String securityCustodianValue);

	public Date getSecurityPerfectionDate();

	public void setSecurityPerfectionDate(Date securityPerfectionDate);

	public String getLegalEnforceability();

	public void setLegalEnforceability(String legalEnforceability);

	public Date getLegalEnforceabilityDate();

	public void setLegalEnforceabilityDate(Date legalEnforceabilityDate);

	public String getExchangeControlApprovalObtained();

	public void setExchangeControlApprovalObtained(String exchangeControlApprovalObtained);

	public String getDepositOrSourceReferenceNumber();

	public void setDepositOrSourceReferenceNumber(String depositOrSourceReferenceNumber);

	public String getDepositCurrency();

	public void setDepositCurrency(String depositCurrency);

	public Double getDepositAmount();

	public void setDepositAmount(Double depositAmount);

	public String getSecurityPledgorID();

	public void setSecurityPledgorID(String securityPledgorID);

	public String getSecurityPledgorName();

	public void setSecurityPledgorName(String securityPledgorName);

	public String getSecurityPledgorRelationship();

	public void setSecurityPledgorRelationship(String securityPledgorRelationship);

	public String getAaNo();

	public void setAaNo(String aaNo);

	public String getSourceLimitID();

	public void setSourceLimitID(String sourceLimitID);

	public long getTempID();

	public void setTempID(long tempID);

	public String getCmsSecurityCurrency();

	public void setCmsSecurityCurrency(String cmsSecurityCurrency);

}
