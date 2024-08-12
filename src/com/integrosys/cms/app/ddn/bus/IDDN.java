/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/IDDN.java,v 1.9 2005/08/29 08:12:56 whuang Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface defines the list of attributes that is required for DDN
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/08/29 08:12:56 $ Tag: $Name: $
 */
public interface IDDN extends IValueObject, Serializable {
	/**
	 * Get the DDN ID
	 * @return long - the DDN ID
	 */
	public long getDDNID();

	/**
	 * Get the DDN ID
	 * @return String - the DDN reference
	 */
	public String getDDNRef();

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID();

	/**
	 * Get the date of last DDN generation
	 * @return date - the date of last DDN generation
	 */
	public Date getDateGenerated();

	/**
	 * Get the deferred to date
	 * @return Date - the deferred to date
	 */
	public Date getDeferredToDate();

	/**
	 * Get the number of days that the DDN is valid for
	 * @return int - the number of days that the DDN is valid for
	 */
	public int getDaysValid();

	/**
	 * Get the extended to date
	 * @return Date - the extended to date
	 */
	public Date getExtendedToDate();

	/**
	 * Get the approval date
	 * @return Date - the approval date
	 */
	public Date getApprovalDate();

	/**
	 * Get the approval by
	 * @return String - the approval by
	 */
	public String getApprovalBy();

	/**
	 * Get the Document Type Used to differentiate the DDN and LLI
	 * @return String - the docs type
	 */
	public String getDocumentType();

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
	 * Get the clean DDN amount for clean limits
	 * @return Amount - the activated amount for clean limits
	 */
	public Amount getCleanDDNAmount();

	/**
	 * Get the DDN amount (limit with security)
	 * @return Amount - the activated amount
	 */
	public Amount getDDNAmount();

	/**
	 * Get the total ddn amount
	 * @return Amount - the total ddn amount calculated
	 */
	public Amount getTotalDDNAmount();

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
	 * Get the is SCC issued indicator
	 * @return boolean - true if SCC is issued and false otherwise
	 */
	public boolean getIsSCCIssuedInd();

	/**
	 * Get the list of CCCertificate items for clean limit
	 * @return ICCCertificateItem[] - the list of CCCertificate items for clean
	 *         limits
	 */
	public IDDNItem[] getCleanDDNItemList();

	/**
	 * Get the list of DDN items (limit with securities)
	 * @return IDDNItem[] - the list of DDN item
	 */
	public IDDNItem[] getNotCleanDDNItemList();

	/**
	 * Get the list of DDN items
	 * @return IDDNItem[] - the list of scc items
	 */
	public IDDNItem[] getDDNItemList();

	/**
	 * Set the DDN ID
	 * @param aDDNID of long type
	 */
	public void setDDNID(long aDDNID);

	/**
	 * Set the DDN Reference
	 * @param aDDNRef of String type
	 */
	public void setDDNRef(String aDDNRef);

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
	 * Get the deferred to date
	 * @param aDeferredDate of Date type
	 */
	public void setDeferredToDate(Date aDeferredToDate);

	/**
	 * Get the number of days that the DDN is valid for
	 * @param aDaysValid of int type
	 */
	public void setDaysValid(int aDaysValid);

	/**
	 * Set the extended date
	 * @param anExtendedDate of Date type
	 */
	public void setExtendedToDate(Date anExtendedToDate);

	/**
	 * Set the approval date
	 * @param anApprovalDate of Date type
	 */
	public void setApprovalDate(Date anApprovalDate);

	/**
	 * Set the approval by
	 * @param anApprovalBy of String type
	 */
	public void setApprovalBy(String anApprovalBy);

	/**
	 * Get the approval amount for clean limits
	 * @return Amount - the clean approval amount
	 */
	public void setCleanApprovalAmount(Amount aCleanApprovalAmount);

	/**
	 * Get the approval amount (limit with security)
	 * @return Amount - the approval amount
	 */
	public void setApprovalAmount(Amount anApprovalAmount);

	/**
	 * Set the total approval amount
	 * @param aTotalApprovalAmount of Amount type
	 */
	public void setTotalApprovalAmount(Amount aTotalApprovalAmount);

	/**
	 * Get the clean activated amount for clean limits
	 * @return Amount - the activated amount for clean limits
	 */
	public void setCleanActivatedAmount(Amount aCleanActivatedAmount);

	/**
	 * Get the activated amount (limit with security)
	 * @return Amount - the activated amount
	 */
	public void setActivatedAmount(Amount anActivatedAmount);

	/**
	 * Set the total activated amount
	 * @param aTotalActivatedAmount of Amount type
	 */
	public void setTotalActivatedAmount(Amount aTotalActivatedAmount);

	/**
	 * Get the clean ddn amount for clean limits
	 * @return Amount - the ddn amount for clean limits
	 */
	public void setCleanDDNAmount(Amount aCleanDDNAmount);

	/**
	 * Get the ddn amount (limit with security)
	 * @return Amount - the ddn amount
	 */
	public void setDDNAmount(Amount anDDNAmount);

	/**
	 * Set the total ddn amount
	 * @parma aTotalDDNAmount of Amount type
	 */
	public void setTotalDDNAmount(Amount aTotalDDNAmount);

	/**
	 * Set the Document Type Used to differentiate the DDN and LLI
	 * @parma aType - the docs type
	 */
	public void setDocumentType(String aDocumentType);

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
	 * Set the is SCC issued indicator
	 * @param anIsSCCIssuedInd of boolean type
	 */
	public void setIsSCCIssuedInd(boolean anIsSCCIssuedInd);

	/**
	 * Set the list of DDN items
	 * @param anIDDNItemList of IDDNItem[] type
	 */
	public void setDDNItemList(IDDNItem[] anIDDNItemList);

	/****************************** CMS-1366********Starts ******************/

	public void setCreditOfficerDt(Date aCreditOfficerDt);

	public void setSeniorOfficerDt(Date aSeniorOfficerDt);

	public Date getCreditOfficerDt();

	public Date getSeniorOfficerDt();

	/****************************** CMS-1366********Ends ********************/

	public void setBcaApprovalDate(Date approvedDate);

	public Date getBcaApprovalDate();

	public void setBcaApprovalAuthority(String approvalAuthority);

	public String getBcaApprovalAuthority();

	public void setBcaNextReviewDate(Date nextReviewDate);

	public Date getBcaNextReviewDate();

	public void setBcaExtReviewDate(Date extReviewDate);

	public Date getBcaExtReviewDate();

	public void setFamCode(String famCode);

	public String getFamCode();

	public void setFamName(String famName);

	public String getFamName();

	public void setBcaOrigCtry(String bcaOrigCtry);

	public String getBcaOrigCtry();

	public void setBcaOrigOrg(String bcaOrigOrg);

	public String getBcaOrigOrg();

	public void setLegalName(String legalName);

	public String getLegalName();

	public void setCustomerName(String customerName);

	public String getCustomerName();

	public void setCustSegmentCode(String custSegmentCode);

	public String getCustSegmentCode();

	public void setBFLIssuedDate(Date issuedDate);

	public Date getBFLIssuedDate();

	public void setCreditGradeCode(String creditCode);

	public String getCreditGradeCode();

	public void setSubProfileID(String subProfile);

	public String getSubProfileID();

	public void setLegalID(String legalID);

	public String getLegalID();

    public void setReleaseTo(String releaseTo);

    public String getReleaseTo();
}
