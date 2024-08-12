/**
 * 
 */
package com.integrosys.cms.batch.sibs.collateral;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * @author : gploh
 * @date   : 02 oct 08 1747hr
 *
 */
public class OBCollateralSMF implements ICollateralSMF {

	private String recordType;
	private String sourceSecurityID;
	private String originatingSecurityCurrency;
	private String cmsSecurityCurrency;
	private String securityLocation;
	private String branchName;
	private String securityCustodianType;
	private String securityCustodianValue;
	private String legalEnforceability;
	private Date   legalEnforceabilityDate;
	private String equityType;
	private long   cdsNumber;
	private String registeredOwner;
	private double numberOfUnits;
	private double unitPrice;
	private Date   securityMaturityDate;
	private String stockExchange;
	private String countryOfStockExchange;
	private String stockCode;
	private String applicationNumber;
	private String facilityName;
	private String sequenceNumber;
	private String endLineInd;
	private long feedID;


	/**
	 * Default Constructor
	 */
	public OBCollateralSMF() {
	}

	/**
	 * Construct OB from interface
	 *
	 * @param value is of type ICollateralFD
	 */
	public OBCollateralSMF(ICollateralSMF value) {
		this();
		AccessorUtil.copyValue(value, this);
	}


	public void setCollateralFeedEntryID(long feedID) {
		this.feedID = feedID;
	}
	public long getCollateralFeedEntryID() {
		return feedID;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getApplicationNumber()
	 */
	public String getApplicationNumber() {
		// TODO Auto-generated method stub
		return applicationNumber;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getBranchName()
	 */
	public String getBranchName() {
		// TODO Auto-generated method stub
		return branchName;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getCDSNumber()
	 */
	public long getCdsNumber() {
		// TODO Auto-generated method stub
		return cdsNumber;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getCMSSecurityCurrency()
	 */
	public String getCmsSecurityCurrency() {
		// TODO Auto-generated method stub
		return cmsSecurityCurrency;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getCountryOfStockExchange()
	 */
	public String getCountryOfStockExchange() {
		// TODO Auto-generated method stub
		return countryOfStockExchange;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getEquityType()
	 */
	public String getEquityType() {
		// TODO Auto-generated method stub
		return equityType;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getFacilityName()
	 */
	public String getFacilityName() {
		// TODO Auto-generated method stub
		return facilityName;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getLegalEnforceability()
	 */
	public String getLegalEnforceability() {
		// TODO Auto-generated method stub
		return legalEnforceability;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getLegalEnforceabilityDate()
	 */
	public Date getLegalEnforceabilityDate() {
		// TODO Auto-generated method stub
		return legalEnforceabilityDate;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getNumberOfUnits()
	 */
	public double getNumberOfUnits() {
		// TODO Auto-generated method stub
		//return new Double( removeLeadingZeros(numberOfUnits, 2, 3) );
		return (numberOfUnits/100);
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getOriginatingSecurityCurrency()
	 */
	public String getOriginatingSecurityCurrency() {
		// TODO Auto-generated method stub
		return originatingSecurityCurrency;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getRecordType()
	 */
	public String getRecordType() {
		// TODO Auto-generated method stub
		return recordType;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getRegisteredOwner()
	 */
	public String getRegisteredOwner() {
		// TODO Auto-generated method stub
		return registeredOwner;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getSecurityCustodianType()
	 */
	public String getSecurityCustodianType() {
		// TODO Auto-generated method stub
		return securityCustodianType;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getSecurityCustodianValue()
	 */
	public String getSecurityCustodianValue() {
		// TODO Auto-generated method stub
		return securityCustodianValue;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getSecurityLocation()
	 */
	public String getSecurityLocation() {
		// TODO Auto-generated method stub
		return securityLocation;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getSecurityMaturityDate()
	 */
	public Date getSecurityMaturityDate() {
		// TODO Auto-generated method stub
		return securityMaturityDate;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getSequenceNumber()
	 */
	public String getSequenceNumber() {
		// TODO Auto-generated method stub
		return sequenceNumber;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getSourceSecurityID()
	 */
	public String getSourceSecurityID() {
		// TODO Auto-generated method stub
		return sourceSecurityID;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getStockCode()
	 */
	public String getStockCode() {
		// TODO Auto-generated method stub
		return stockCode;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getStockExchange()
	 */
	public String getStockExchange() {
		// TODO Auto-generated method stub
		return stockExchange;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#getUnitPrice()
	 */
	public double getUnitPrice() {
		// TODO Auto-generated method stub
		//return new Double( removeLeadingZeros(unitPrice, 5, 6) );
		return (unitPrice/100000);
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setApplicationNumber(java.lang.String)
	 */
	public void setApplicationNumber(String aaNumber) {
		// TODO Auto-generated method stub
		this.applicationNumber = aaNumber;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setBranchName(java.lang.String)
	 */
	public void setBranchName(String bName) {
		// TODO Auto-generated method stub
		this.branchName = bName;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setCDSNumber(long)
	 */
	public void setCdsNumber(long cdsNo) {
		// TODO Auto-generated method stub
		this.cdsNumber = cdsNo;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setCMSSecurityCurrency(java.lang.String)
	 */
	public void setCmsSecurityCurrency(String cmsCurrency) {
		// TODO Auto-generated method stub
		this.cmsSecurityCurrency = cmsCurrency;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setCountryOfStockExchange(java.lang.String)
	 */
	public void setCountryOfStockExchange(String orgCountry) {
		// TODO Auto-generated method stub
		this.countryOfStockExchange = orgCountry;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setEquityType(java.lang.String)
	 */
	public void setEquityType(String eqType) {
		// TODO Auto-generated method stub
		this.equityType = eqType;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setFacilityName(java.lang.String)
	 */
	public void setFacilityName(String facName) {
		// TODO Auto-generated method stub
		this.facilityName = facName;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setLegalEnforceability(java.lang.String)
	 */
	public void setLegalEnforceability(String legEnf) {
		// TODO Auto-generated method stub
		this.legalEnforceability = legEnf;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setLegalEnforceabilityDate(java.util.Date)
	 */
	public void setLegalEnforceabilityDate(Date legEnfDate) {
		// TODO Auto-generated method stub
		this.legalEnforceabilityDate = legEnfDate;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setNumberOfUnits(java.lang.String)
	 */
	public void setNumberOfUnits(double numUnits) {
		// TODO Auto-generated method stub
		this.numberOfUnits = numUnits;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setOriginatingSecurityCurrency(java.lang.String)
	 */
	public void setOriginatingSecurityCurrency(String orgCurrency) {
		// TODO Auto-generated method stub
		this.originatingSecurityCurrency = orgCurrency;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setRecordType(java.lang.String)
	 */
	public void setRecordType(String recType) {
		// TODO Auto-generated method stub
		this.recordType = recType;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setRegisteredOwner(java.lang.String)
	 */
	public void setRegisteredOwner(String regOwner) {
		// TODO Auto-generated method stub
		this.registeredOwner = regOwner;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setSecurityCustodianType(java.lang.String)
	 */
	public void setSecurityCustodianType(String secCustType) {
		// TODO Auto-generated method stub
		this.securityCustodianType = secCustType;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setSecurityCustodianValue(java.lang.String)
	 */
	public void setSecurityCustodianValue(String secCustValue) {
		// TODO Auto-generated method stub
		this.securityCustodianValue = secCustValue;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setSecurityLocation(java.lang.String)
	 */
	public void setSecurityLocation(String loc) {
		// TODO Auto-generated method stub
		this.securityLocation = loc;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setSecurityMaturityDate(java.util.Date)
	 */
	public void setSecurityMaturityDate(Date secMatDate) {
		// TODO Auto-generated method stub
		this.securityMaturityDate = secMatDate;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setSequenceNumber(java.lang.String)
	 */
	public void setSequenceNumber(String seqNo) {
		// TODO Auto-generated method stub
		this.sequenceNumber = seqNo;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setSourceSecurityID(java.lang.String)
	 */
	public void setSourceSecurityID(String secID) {
		// TODO Auto-generated method stub
		this.sourceSecurityID = secID;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setStockCode(java.lang.String)
	 */
	public void setStockCode(String stkCode) {
		// TODO Auto-generated method stub
		this.stockCode = stkCode;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setStockExchange(java.lang.String)
	 */
	public void setStockExchange(String stkEx) {
		// TODO Auto-generated method stub
		this.stockExchange = stkEx;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.sibs.collateral.ICollateralSMF#setUnitPrice(java.lang.String)
	 */
	public void setUnitPrice(double sUnitPrice) {
		// TODO Auto-generated method stub
		this.unitPrice = sUnitPrice;
	}

	public void setEndLineIndicator(String endT) {
		this.endLineInd = endT;
	}
	public String getEndLineIndicator() {
		return endLineInd;
	}

} //end of class
