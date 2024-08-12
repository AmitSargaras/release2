/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/OBCCCertificate.java,v 1.5 2005/08/26 04:18:11 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.util.ArrayList;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class provide the implementation for ICCCertificate
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/08/26 04:18:11 $ Tag: $Name: $
 */
public class OBCCCertificate implements ICCCertificate {
	private long ccCertID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String ccCertRef = null;

	private String ccCertCategory = null;

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long subProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long pledgorID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private Date dateGenerated = null;

	private Amount cleanApprovalAmount = null;

	private Amount approvalAmount = null;

	private Amount totalApprovalAmount = null;

	private Amount cleanActivatedAmount = null;

	private Amount activatedAmount = null;

	private Amount totalActivatedAmount = null;

	private String creditOfficerName = null;

	private String creditOfficerSignNo = null;

	private IBookingLocation creditOfficerLocation = null;

	private String seniorOfficerName = null;

	private String seniorOfficerSignNo = null;

	private IBookingLocation seniorOfficerLocation = null;

	private String remarks = null;

	private ICCCertificateItem[] itemList = null;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private ICCCertificateCustomerDetail custDetails;

	/***
	 * Get the CC Certificate ID
	 * @return long - the CC certification ID
	 */
	public long getCCCertID() {
		return this.ccCertID;
	}

	/**
	 * Get the CC Certificate Reference
	 * @return String - the CC certificate reference
	 */
	public String getCCCertRef() {
		return this.ccCertRef;
	}

	/**
	 * Get the CC certificate category
	 * @return String - the CC certification category
	 */
	public String getCCCertCategory() {
		return this.ccCertCategory;
	}

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	/**
	 * Get the subprofile ID
	 * @return long - the sub profile ID
	 */
	public long getSubProfileID() {
		return this.subProfileID;
	}

	/**
	 * Get the pledgor ID
	 * @return long - the pledgor ID
	 */
	public long getPledgorID() {
		return this.pledgorID;
	}

	/**
	 * Helper method to get the owner ID based on the category
	 * @return long - the ownerID
	 */
	public long getOwnerID() {
		if (ICMSConstant.CHECKLIST_PLEDGER.equals(getCCCertCategory())) {
			return getPledgorID();
		}
		return getSubProfileID();
	}

	/**
	 * Get the checklist ID
	 * @return long - the checklistID
	 */
	public long getCheckListID() {
		return this.checkListID;
	}

	/**
	 * Get the date generated
	 * @return Date - the date generated
	 */
	public Date getDateGenerated() {
		return this.dateGenerated;
	}

	/**
	 * Get the approval amount for clean limits
	 * @return Amount - the clean approval amount
	 */
	public Amount getCleanApprovalAmount() {
		return this.cleanApprovalAmount;
	}

	/**
	 * Get the approval amount (limit with security)
	 * @return Amount - the approval amount
	 */
	public Amount getApprovalAmount() {
		return this.approvalAmount;
	}

	/**
	 * Get the total approval amount. This amount is calculated based on the
	 * base currency of the bank
	 * @return Amount - the total approval amount calculated
	 */
	public Amount getTotalApprovalAmount() {
		return this.totalApprovalAmount;
	}

	/**
	 * Get the clean activated amount for clean limits
	 * @return Amount - the activated amount for clean limits
	 */
	public Amount getCleanActivatedAmount() {
		return this.cleanActivatedAmount;
	}

	/**
	 * Get the activated amount (limit with security)
	 * @return Amount - the activated amount
	 */
	public Amount getActivatedAmount() {
		return this.activatedAmount;
	}

	/**
	 * Get the total activated amount. This amount is calculated based on the
	 * base currency of the bank
	 * @return Amount - the total activated amount calculated
	 */
	public Amount getTotalActivatedAmount() {
		return this.totalActivatedAmount;
	}

	/**
	 * Get the credit officer name
	 * @return String - the creditOfficer name
	 */
	public String getCreditOfficerName() {
		return this.creditOfficerName;
	}

	/**
	 * Get the credit officet signing number
	 * @return String - the credit officer signing number
	 */
	public String getCreditOfficerSignNo() {
		return this.creditOfficerSignNo;
	}

	/**
	 * Get the credit officer booking location
	 * @return IBookingLocation - the credit officer location
	 */
	public IBookingLocation getCreditOfficerLocation() {
		return this.creditOfficerLocation;
	}

	/**
	 * Get the senior officer name
	 * @return String - the senior officer name
	 */
	public String getSeniorOfficerName() {
		return this.seniorOfficerName;
	}

	/**
	 * Get the senior officer signing number
	 * @return String - the senior officer signing number
	 */
	public String getSeniorOfficerSignNo() {
		return this.seniorOfficerSignNo;
	}

	/**
	 * Get the senior officer location
	 * @return IBookingLocation - the senior officer location
	 */
	public IBookingLocation getSeniorOfficerLocation() {
		return this.seniorOfficerLocation;
	}

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * Get the list of CCCertificate items for clean limit
	 * @return ICCCertificateItem[] - the list of CCCertificate items for clean
	 *         limits
	 */
	public ICCCertificateItem[] getCleanCCCertificateItemList() {
		ICCCertificateItem[] fullList = getCCCertificateItemList();
		if (fullList != null) {
			ArrayList cleanList = new ArrayList();
			for (int ii = 0; ii < fullList.length; ii++) {
				if (fullList[ii].isCleanType()) {
					cleanList.add(fullList[ii]);
				}
			}
			if (cleanList.size() > 0) {
				return (ICCCertificateItem[]) cleanList.toArray(new ICCCertificateItem[0]);
			}
		}
		return null;
	}

	/**
	 * Get the list of CCCertificate items (limit with securities)
	 * @return ICCCertificateItem[] - the list of CCCertificate item
	 */
	public ICCCertificateItem[] getNotCleanCCCertificateItemList() {
		ICCCertificateItem[] fullList = getCCCertificateItemList();
		if (fullList != null) {
			ArrayList notCleanList = new ArrayList();
			for (int ii = 0; ii < fullList.length; ii++) {
				if (!fullList[ii].isCleanType()) {
					notCleanList.add(fullList[ii]);
				}
			}
			if (notCleanList.size() > 0) {
				return (ICCCertificateItem[]) notCleanList.toArray(new ICCCertificateItem[0]);
			}
		}
		return null;
	}

	/**
	 * Get the list of CCCertificate items
	 * @return ICCCertificateItem[] - the list of CCCertificate item
	 */
	public ICCCertificateItem[] getCCCertificateItemList() {
		return this.itemList;
	}

	/**
	 * Get the version time
	 * @return long - the version time
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * Get ccc customer details.
	 * 
	 * @return ICCCertificateCustomerDetail
	 */
	public ICCCertificateCustomerDetail getCustDetails() {
		return custDetails;
	}

	/**
	 * Set the CCCertID
	 * @param aCCCertID of long type
	 */
	public void setCCCertID(long aCCCertID) {
		this.ccCertID = aCCCertID;
	}

	/**
	 * Set the CCCert Reference
	 * @param aCCCertRef of String type
	 */
	public void setCCCertRef(String aCCCertRef) {
		this.ccCertRef = aCCCertRef;
	}

	/**
	 * Set the cc certificate category
	 * @param aCCCertCategory of String type
	 */
	public void setCCCertCategory(String aCCCertCategory) {
		this.ccCertCategory = aCCCertCategory;
	}

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	/**
	 * Set the sub profile ID
	 * @param aSubProfileID of long type
	 */
	public void setSubProfileID(long aSubProfileID) {
		this.subProfileID = aSubProfileID;
	}

	/**
	 * Set the pledgor ID
	 * @param aPledgorID of long type
	 */
	public void setPledgorID(long aPledgorID) {
		this.pledgorID = aPledgorID;
	}

	/**
	 * Set the checklist ID
	 * @param aCheckListID - long
	 */
	public void setCheckListID(long aCheckListID) {
		this.checkListID = aCheckListID;
	}

	/**
	 * Set the date generated
	 * @param aDateGenerated of Date type
	 */
	public void setDateGenerated(Date aDateGenerated) {
		this.dateGenerated = aDateGenerated;
	}

	/**
	 * Get the approval amount for clean limits
	 */
	public void setCleanApprovalAmount(Amount aCleanApprovalAmount) {
		this.cleanApprovalAmount = aCleanApprovalAmount;
	}

	/**
	 * Get the approval amount (limit with security)
	 */
	public void setApprovalAmount(Amount anApprovalAmount) {
		this.approvalAmount = anApprovalAmount;
	}

	/**
	 * Set the total approval amount
	 * @param aTotalApprovalAmount of Amount type
	 */
	public void setTotalApprovalAmount(Amount aTotalApprovalAmount) {
		this.totalApprovalAmount = aTotalApprovalAmount;
	}

	/**
	 * Get the clean activated amount for clean limits
	 */
	public void setCleanActivatedAmount(Amount aCleanActivatedAmount) {
		this.cleanActivatedAmount = aCleanActivatedAmount;
	}

	/**
	 * Get the activated amount (limit with security)
	 */
	public void setActivatedAmount(Amount anActivatedAmount) {
		this.activatedAmount = anActivatedAmount;
	}

	/**
	 * Set the total activated amount
	 * @param aTotalActivatedAmount of Amount type
	 */
	public void setTotalActivatedAmount(Amount aTotalActivatedAmount) {
		this.totalActivatedAmount = aTotalActivatedAmount;
	}

	/**
	 * Set the credit officer name
	 * @param aCreditOfficerName of String type
	 */
	public void setCreditOfficerName(String aCreditOfficerName) {
		this.creditOfficerName = aCreditOfficerName;
	}

	/**
	 * Set the credit officer signing number
	 * @param aCreditOfficerSignNo of String type
	 */
	public void setCreditOfficerSignNo(String aCreditOfficerSignNo) {
		this.creditOfficerSignNo = aCreditOfficerSignNo;
	}

	/**
	 * Set the credit officer location
	 * @param anIBookingLocation of IBookingLocation type
	 */
	public void setCreditOfficerLocation(IBookingLocation anIBookingLocation) {
		this.creditOfficerLocation = anIBookingLocation;
	}

	/**
	 * Set the senior officer name
	 * @param aSeniorOfficerName of String type
	 */
	public void setSeniorOfficerName(String aSeniorOfficerName) {
		this.seniorOfficerName = aSeniorOfficerName;
	}

	/**
	 * Set the senior officer signing number
	 * @param aSeniorOfficerSignNo of String type
	 */
	public void setSeniorOfficerSignNo(String aSeniorOfficerSignNo) {
		this.seniorOfficerSignNo = aSeniorOfficerSignNo;
	}

	/**
	 * Set the senior officer location
	 * @param anIBookingLocation of IBookingLocation type
	 */
	public void setSeniorOfficerLocation(IBookingLocation anIBookingLocation) {
		this.seniorOfficerLocation = anIBookingLocation;
	}

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks) {
		this.remarks = aRemarks;
	}

	/**
	 * Set the list of CC certificate items
	 * @param anICCCertificateItemList - the list of cc certificate items
	 */
	public void setCCCertificateItemList(ICCCertificateItem[] anICCCertificateItemList) {
		this.itemList = anICCCertificateItemList;
	}

	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}

	/**
	 * Set ccc customer details.
	 * 
	 * @param custDetails of type ICCCertificateCustomerDetail
	 */
	public void setCustDetails(ICCCertificateCustomerDetail custDetails) {
		this.custDetails = custDetails;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/****************************** CMS-1366********Starts ******************/

	private Date creditOfficerDt = null;

	public Date getCreditOfficerDt() {

		return this.creditOfficerDt;
	}

	public void setCreditOfficerDt(Date aCreditOfficerDt) {

		this.creditOfficerDt = aCreditOfficerDt;
	}

	private Date seniorOfficerDt = null;

	public Date getSeniorOfficerDt() {

		return this.seniorOfficerDt;
	}

	public void setSeniorOfficerDt(Date aSeniorOfficerDt) {

		this.seniorOfficerDt = aSeniorOfficerDt;
	}

	/****************************** CMS-1366********Ends ********************/
}
