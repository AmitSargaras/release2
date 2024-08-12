package com.integrosys.cms.batch.mimb.collateral;

import java.util.Date;

public interface ICollateralMarketable {

	public String getRecordType();

	public void setRecordType(String recordType);

	public String getSourceSecurityID();

	public void setSourceSecurityID(String sourceSecurityID);

	public String getSecurityType();

	public void setSecurityType(String securityType);

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

	public Date getSecurityMaturityDate();

	public void setSecurityMaturityDate(Date securityMaturityDate);

	public Date getSecurityPerfectionDate();

	public void setSecurityPerfectionDate(Date securityPerfectionDate);

	public String getLegalEnforceability();

	public void setLegalEnforceability(String legalEnforceability);

	public Date getLegalEnforceabilityDate();

	public void setLegalEnforceabilityDate(Date legalEnforceabilityDate);

	public String getNatureOfCharge();

	public void setNatureOfCharge(String natureOfCharge);

	public Double getAmountOfCharge();

	public void setAmountOfCharge(Double amountOfCharge);

	public Date getDateLegallyCharged();

	public void setDateLegallyCharged(Date dateLegallyCharged);

	public String getChargeType();

	public void setChargeType(String chargeType);

	public String getEquityType();

	public void setEquityType(String equityType);

	public String getCdsNumber();

	public void setCdsNumber(String cdsNumber);

	public String getNomineeName();

	public void setNomineeName(String nomineeName);

	public String getRecognizeExchange();

	public void setRecognizeExchange(String recognizeExchange);

	public String getRegisteredOwner();

	public void setRegisteredOwner(String registeredOwner);

	public long getNumberOfUnits();

	public void setNumberOfUnits(long numberOfUnits);

	public Double getUnitPrice();

	public void setUnitPrice(Double unitPrice);

	public String getStockExchange();

	public void setStockExchange(String stockExchange);

	public String getCountryOfStockExchange();

	public void setCountryOfStockExchange(String countryOfStockExchange);

	public String getExchangeControlApprovalObtained();

	public void setExchangeControlApprovalObtained(String exchangeControlApprovalObtained);

	public String getStockCode();

	public void setStockCode(String stockCode);

	public String getBaselCompliantUnitTrustCollateral();

	public void setBaselCompliantUnitTrustCollateral(String baselCompliantUnitTrustCollateral);

	public String getSecurityPledgorID();

	public void setSecurityPledgorID(String securityPledgorID);

	public String getSecurityPledgorName();

	public void setSecurityPledgorName(String securityPledgorName);

	public String getSecurityPledgorRelationship();

	public void setSecurityPledgorRelationship(String securityPledgorRelationship);

	public String getAaNo();

	public void setAaNo(String aaNo);

	public long getTempID();

	public void setTempID(long tempID);
	
	public String getCmsSecurityCurrency();

	public void setCmsSecurityCurrency(String cmsSecurityCurrency);

	public String getSourceLimitID();

	public void setSourceLimitID(String sourceLimitID);

}
