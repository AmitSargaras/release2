/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/ISCCertificate.java,v 1.7 2006/05/30 10:19:38 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface defines the list of attributes that is required for SCC
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/05/30 10:19:38 $ Tag: $Name: $
 */
public interface ISCCertificate extends IValueObject, Serializable {
	/**
	 * Get the SCC ID
	 * @return long - the SCC ID
	 */
	public long getSCCertID();

	/**
	 * Get the SCC Reference
	 * @return String - the SCC reference
	 */
	public String getSCCertRef();

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID();

	/**
	 * Get the date generated
	 * @return Date - the date of SCC generation
	 */
	public Date getDateGenerated();

	/**
	 * Get the approval amount for clean limits
	 * @return Amount - the clean approval amount
	 */
	public Amount getCleanApprovalAmount();

	/**
	 * Get the approval amount (limit with security)
	 * @return Amount - the approval amount
	 */
	public Amount getApprovalAmount();

	/**
	 * Get the total approval amount. This amount is calculated based on the
	 * base currency of the bank
	 * @return Amount - the total approval amount calculated
	 */
	public Amount getTotalApprovalAmount();

	/**
	 * Get the clean activated amount for clean limits
	 * @return Amount - the activated amount for clean limits
	 */
	public Amount getCleanActivatedAmount();

	/**
	 * Get the activated amount (limit with security)
	 * @return Amount - the activated amount
	 */
	public Amount getActivatedAmount();

	/**
	 * Get the total activated amount. This amount is calculated based on the
	 * base currency of the bank
	 * @return Amount - the total activated amount calculated
	 */
	public Amount getTotalActivatedAmount();

	/**
	 * Get the credit officer name
	 * @return String - the credit officer name
	 */
	public String getCreditOfficerName();

	/**
	 * Get the credit officer signing number
	 * @return String - the credit officer signing number
	 */
	public String getCreditOfficerSignNo();

	/**
	 * Get the credit officer booking location
	 * @return IBookingLocation - the credit officer location
	 */
	public IBookingLocation getCreditOfficerLocation();

	/**
	 * Get the senior officer name
	 * @return String - the senior officer name
	 */
	public String getSeniorOfficerName();

	/**
	 * Get the senior officer signing number
	 * @return String - the senior officer signing number
	 */
	public String getSeniorOfficerSignNo();

	/**
	 * Get the senior officer location
	 * @return IBookingLocation - the senior officer location
	 */
	public IBookingLocation getSeniorOfficerLocation();

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks();

	/**
	 * Get the list of SCC items
	 * @return ISCCertificateItem[] - the list of scc items
	 */
	public ISCCertificateItem[] getSCCItemList();

    public char getHasCheck1();

    public char getHasCheck2();

    public char getHasCheck3();

    public char getHasCheck4();

    public String getInsuredWith();

    public BigDecimal getInsuredWithAmt();

    public String getAmbm();

    public Date getExpiry();

    public BigDecimal getSinkFundAmt();

    public String getPmForPeriodOf();

    public String getCommencingFrom();

    public BigDecimal getFundReach();

    public BigDecimal getFeesAmt();

    public String getOthers();

	/**
	 * Set the SCC ID
	 * @param aSCCertID of long type
	 */
	public void setSCCertID(long aSCCertID);

	/**
	 * Set the SCC reference
	 * @param aSCCertRef of String type
	 */
	public void setSCCertRef(String aSCCertRef);

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID);

	/**
	 * Set the date generated
	 * @param aDateGenerated of Date type
	 */
	public void setDateGenerated(Date aDateGenerated);

	/**
	 * Get the approval amount for clean limits
	 */
	public void setCleanApprovalAmount(Amount aCleanApprovalAmount);

	/**
	 * Get the approval amount (limit with security)
	 */
	public void setApprovalAmount(Amount anApprovalAmount);

	/**
	 * Set the total approval amount
	 * @param aTotalApprovalAmount of Amount type
	 */
	public void setTotalApprovalAmount(Amount aTotalApprovalAmount);

	/**
	 * Get the clean activated amount for clean limits
	 */
	public void setCleanActivatedAmount(Amount aCleanActivatedAmount);

	/**
	 * Get the activated amount (limit with security)
	 */
	public void setActivatedAmount(Amount anActivatedAmount);

	/**
	 * Set the total activated amount
	 * @param aTotalActivatedAmount of Amount type
	 */
	public void setTotalActivatedAmount(Amount aTotalActivatedAmount);

	/**
	 * Set the credit officer name
	 * @param aCreditOfficerName of String type
	 */
	public void setCreditOfficerName(String aCreditOfficerName);

	/**
	 * Set the credit officer signing number
	 * @param aCreditOfficerSignNo of String type
	 */
	public void setCreditOfficerSignNo(String aCreditOfficerSignNo);

	/**
	 * Set the credit officer location
	 * @param anIBookingLocation of IBookingLocation type
	 */
	public void setCreditOfficerLocation(IBookingLocation anIBookingLocation);

	/**
	 * Set the senior officer name
	 * @param aSeniorOfficerName of String type
	 */
	public void setSeniorOfficerName(String aSeniorOfficerName);

	/**
	 * Set the senior officer signing number
	 * @param aSeniorOfficerSignNo of String type
	 */
	public void setSeniorOfficerSignNo(String aSeniorOfficerSignNo);

	/**
	 * Set the senior officer location
	 * @param anIBookingLocation of IBookingLocation type
	 */
	public void setSeniorOfficerLocation(IBookingLocation anIBookingLocation);

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks);

	/**
	 * Set the list of SCC items
	 * @param anISCCertificateItemList of ISCCertificateItem[] type
	 */
	public void setSCCItemList(ISCCertificateItem[] anISCCertificateItemList);

	/****************************** CMS-1366********Starts ******************/

	public void setCreditOfficerDt(Date aCreditOfficerDt);

	public void setSeniorOfficerDt(Date aSeniorOfficerDt);

	public Date getCreditOfficerDt();

	public Date getSeniorOfficerDt();

	/****************************** CMS-1366********Ends ********************/

	/**
	 * Get certificate customer info.
	 * 
	 * @return ISCCertificateCustomerDetail
	 */
	public ISCCertificateCustomerDetail getCustDetails();

	/**
	 * Set certificate customer info.
	 * 
	 * @param custDetails of type ISCCertificateCustomerDetail
	 */
	public void setCustDetails(ISCCertificateCustomerDetail custDetails);

	/****************************** R1.5-CR146 ********Starts ******************/

	/**
	 * Get the list of SCCertificate items for clean limit
	 * @return ISCCertificateItem[] - the list of SCCertificate items for clean
	 *         limits
	 */
	public ISCCertificateItem[] getCleanSCCertificateItemList();

	/**
	 * Get the list of SCCertificate items (limit with securities)
	 * @return ISCCertificateItem[] - the list of SCCertificate item
	 */
	public ISCCertificateItem[] getNotCleanSCCertificateItemList();

    public void setHasCheck1(char hasCheck1);

    public void setHasCheck2(char hasCheck2);

    public void setHasCheck3(char hasCheck3);

    public void setHasCheck4(char hasCheck4);

    public void setInsuredWith(String insuredWith);

    public void setInsuredWithAmt(BigDecimal insuredWithAmt);

    public void setAmbm(String ambm);

    public void setExpiry(Date expiry);

    public void setSinkFundAmt(BigDecimal sinkFundAmt);

    public void setPmForPeriodOf(String pmForPeriodOf);

    public void setCommencingFrom(String commencingFrom);

    public void setFundReach(BigDecimal fundReach);

    public void setFeesAmt(BigDecimal feesAmt);

    public void setOthers(String others);

	/****************************** R1.5-CR146 ********Ends ******************/

}
