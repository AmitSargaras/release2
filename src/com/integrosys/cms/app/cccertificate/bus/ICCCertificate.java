/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/ICCCertificate.java,v 1.4 2005/08/26 04:18:11 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface defines the list of attributes that is required for CCC
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/26 04:18:11 $ Tag: $Name: $
 */
public interface ICCCertificate extends IValueObject, Serializable {
	/***
	 * Get the CC Certificate ID
	 * @return long - the CC certification ID
	 */
	public long getCCCertID();

	/**
	 * Get the CC Certificate Reference
	 * @return String - the CC certificate reference
	 */
	public String getCCCertRef();

	/**
	 * Get the CC certificate category
	 * @return String - the CC certification category
	 */
	public String getCCCertCategory();

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID();

	/**
	 * Get the subprofile ID
	 * @return long - the sub profile ID
	 */
	public long getSubProfileID();

	/**
	 * Get the pledgor ID
	 * @return long - the pledgor ID
	 */
	public long getPledgorID();

	/**
	 * Helper method to get the owner ID based on the category
	 * @return long - the ownerID
	 */
	public long getOwnerID();

	/**
	 * Get the checklist ID
	 * @return long - the checkList ID
	 */
	public long getCheckListID();

	/**
	 * Get the date generated
	 * @return Date - the date generated
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
	 * @return String - the creditOfficer name
	 */
	public String getCreditOfficerName();

	/**
	 * Get the credit officet signing number
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
	 * Get the list of CCCertificate items for clean limit
	 * @return ICCCertificateItem[] - the list of CCCertificate items for clean
	 *         limits
	 */
	public ICCCertificateItem[] getCleanCCCertificateItemList();

	/**
	 * Get the list of CCCertificate items (limit with securities)
	 * @return ICCCertificateItem[] - the list of CCCertificate item
	 */
	public ICCCertificateItem[] getNotCleanCCCertificateItemList();

	/**
	 * Get the list of CCCertificate items
	 * @return ICCCertificateItem[] - the list of CCCertificate item
	 */
	public ICCCertificateItem[] getCCCertificateItemList();

	/**
	 * Get ccc customer details.
	 * 
	 * @return ICCCertificateCustomerDetail
	 */
	public ICCCertificateCustomerDetail getCustDetails();

	/**
	 * Set the CCCertID
	 * @param aCCCertID of long type
	 */
	public void setCCCertID(long aCCCertID);

	/**
	 * Set the CCCert Reference
	 * @param aCCCertRef of String type
	 */
	public void setCCCertRef(String aCCCertRef);

	/**
	 * Set the cc certificate category
	 * @param aCCCertCategory of String type
	 */
	public void setCCCertCategory(String aCCCertCategory);

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID);

	/**
	 * Set the sub profile ID
	 * @param aSubProfileID of long type
	 */
	public void setSubProfileID(long aSubProfileID);

	/**
	 * Set the pledgor ID
	 * @param aPledgorID of long type
	 */
	public void setPledgorID(long aPledgorID);

	/**
	 * Set the checklist ID
	 * @param aCheckListID of long type
	 */
	public void setCheckListID(long aCheckListID);

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
	 * Set the list of CC certificate items (limit with securities)
	 * @param anICCCertificateItemList - the list of cc certificate items
	 */
	public void setCCCertificateItemList(ICCCertificateItem[] anICCCertificateItemList);

	/****************************** CMS-1366********Starts *******************/

	public void setCreditOfficerDt(Date aCreditOfficerDt);

	public void setSeniorOfficerDt(Date aSeniorOfficerDt);

	public Date getCreditOfficerDt();

	public Date getSeniorOfficerDt();

	/****************************** CMS-1366********Ends ********************/

	/**
	 * Set ccc customer details.
	 * 
	 * @param custDetails of type ICCCertificateCustomerDetail
	 */
	public void setCustDetails(ICCCertificateCustomerDetail custDetails);
}
