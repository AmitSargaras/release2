
package com.integrosys.cms.batch.sibs.collateral;

/**
 * @author gp loh
 * date 02 oct 08
 *
 */

import java.util.Date;

public interface ICollateralSMF extends java.io.Serializable {


	public void setCollateralFeedEntryID(long feedID);
	public long getCollateralFeedEntryID();

	public void setRecordType(String recType);
	public String getRecordType();

	public void setSourceSecurityID(String secID);
	public String getSourceSecurityID();

	public void setOriginatingSecurityCurrency(String orgCurrency);
	public String getOriginatingSecurityCurrency();

	public void setCmsSecurityCurrency(String cmsCurrency);
	public String getCmsSecurityCurrency();

	public void setSecurityLocation(String secLoc);
	public String getSecurityLocation();

	public void setBranchName(String bName);
	public String getBranchName();

	public void setSecurityCustodianType(String secCustType);
	public String getSecurityCustodianType();

	public void setSecurityCustodianValue(String secUstValue);
	public String getSecurityCustodianValue();

	public void setLegalEnforceability(String legEnf);
	public String getLegalEnforceability();

	public void setLegalEnforceabilityDate(Date	legEnfDate);
	public Date getLegalEnforceabilityDate();

	public void setEquityType(String eqType);
	public String getEquityType();

	public void setCdsNumber(long cdsNo);
	public long getCdsNumber();

	public void setRegisteredOwner(String regOwner);
	public String getRegisteredOwner();

	public void setNumberOfUnits(double	numUnits);
	public double getNumberOfUnits();

	// Numeric	(22,5)
	public void setUnitPrice(double unitPrice);
	public double getUnitPrice();

	public void setSecurityMaturityDate(Date secMatDate);
	public Date getSecurityMaturityDate();

	public void setStockExchange(String stkEx);
	public String getStockExchange();

	public void setCountryOfStockExchange(String orgCountry);
	public String getCountryOfStockExchange();

	public void setStockCode(String stkCode);
	public String getStockCode();

	public void setApplicationNumber(String aaNumber);
	public String getApplicationNumber();

	public void setFacilityName(String facName);
	public String getFacilityName();

	public void setSequenceNumber(String seqNo);
	public String getSequenceNumber();

}