/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/OBDDN.java,v 1.10 2005/08/29 08:12:56 whuang Exp $
 */
package com.integrosys.cms.app.ddn.bus;

import java.util.ArrayList;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This class provide the implementation for IDDN
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/08/29 08:12:56 $ Tag: $Name: $
 */
public class OBDDN implements IDDN {
	private long ddnID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String ddnRef = null;

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private Date dateGenerated = null;

	private Date deferredToDate = null;

	private int daysValid = Integer.MIN_VALUE;

	private Date extendedToDate = null;

	private Date approvalDate = null;

	private String approvalBy = null;

	private Amount cleanApprovalAmount = null;

	private Amount approvalAmount = null;

	private Amount totalApprovalAmount = null;

	private Amount cleanActivatedAmount = null;

	private Amount activatedAmount = null;

	private Amount totalActivatedAmount = null;

	private Amount cleanDDNAmount = null;

	private Amount ddnAmount = null;

	private Amount totalDDNAmount = null;

	private String creditOfficerName = null;

	private String creditOfficerSignNo = null;

	private IBookingLocation creditOfficerLocation = null;

	private String seniorOfficerName = null;

	private String seniorOfficerSignNo = null;

	private IBookingLocation seniorOfficerLocation = null;

	private String remarks = null;

	private String documentType = null;

	private boolean isSCCIssuedInd = false;

	private IDDNItem[] ddnItemList = null;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private Date bcaApprovalDate = null;

	private String bcaApprovalAuthority = null;

	private Date bcaNextReviewDate = null;

	private Date bcaExtReviewDate = null;

	private String famCode = null;

	private String famName = null;

	private String bcaOrigCtry = null;

	private String bcaOrigOrg = null;

	private String legalName = null;

	private String customerName = null;

	private String custSegmentCode = null;

	private Date bFLIssuedDate = null;

	private String creditGradeCode = null;

	private String subProfileID = null;

	private String legalID = null;

    private String releaseTo = null;

	/**
	 * Get the DDN ID
	 * @return long - the DDN ID
	 */
	public long getDDNID() {
		return this.ddnID;
	}

	/**
	 * Get the DDN Reference
	 * @return String - the DDN reference
	 */
	public String getDDNRef() {
		return this.ddnRef;
	}

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	/**
	 * Get the date of last DDN generation
	 * @return date - the date of last DDN generation
	 */
	public Date getDateGenerated() {
		return this.dateGenerated;
	}

	/**
	 * Get the deferred to date
	 * @return Date - the deferred to date
	 */
	public Date getDeferredToDate() {
		return this.deferredToDate;
	}

	/**
	 * Get the number of days that the DDN is valid for
	 * @return int - the number of days that the DDN is valid for
	 */
	public int getDaysValid() {
		return this.daysValid;
	}

	/**
	 * Get the extended to date
	 * @return Date - the extended to date
	 */
	public Date getExtendedToDate() {
		return this.extendedToDate;
	}

	/**
	 * Get the approval date
	 * @return Date - the approval date
	 */
	public Date getApprovalDate() {
		return this.approvalDate;
	}

	/**
	 * Get the approval by
	 * @return String - the approval by
	 */
	public String getApprovalBy() {
		return this.approvalBy;
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
	 * Get the clean ddn amount for clean limits
	 * @return Amount - the activated amount for clean limits
	 */
	public Amount getCleanDDNAmount() {
		return this.cleanDDNAmount;
	}

	/**
	 * Get the ddn amount (limit with security)
	 * @return Amount - the activated amount
	 */
	public Amount getDDNAmount() {
		return this.ddnAmount;
	}

	/**
	 * Get the total ddn amount
	 * @return Amount - the total ddn amount calculated
	 */
	public Amount getTotalDDNAmount() {
		return this.totalDDNAmount;
	}

	/**
	 * Get the document Used to differentiate LLI and DDN
	 * @return String - the total drawn limit amount calculated
	 */
	public String getDocumentType() {
		return this.documentType;
	}

	/**
	 * Get the credit officer name
	 * @return String - the credit officer name
	 */
	public String getCreditOfficerName() {
		return this.creditOfficerName;
	}

	/**
	 * Get the credit officer signing number
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
	 * Get the is valid indicator
	 * @return boolean - true if it is valid and false otherwise
	 */
	public boolean getIsSCCIssuedInd() {
		return this.isSCCIssuedInd;
	}

	/**
	 * Get the list of ddn items for clean limit
	 * @return IDDNItem[] - the list of ddn items for clean limits
	 */
	public IDDNItem[] getCleanDDNItemList() {
		IDDNItem[] fullList = getDDNItemList();
		if (fullList != null) {
			ArrayList cleanList = new ArrayList();
			for (int ii = 0; ii < fullList.length; ii++) {
				if (fullList[ii].isCleanType()) {
					cleanList.add(fullList[ii]);
				}
			}
			if (cleanList.size() > 0) {
				return (IDDNItem[]) cleanList.toArray(new IDDNItem[0]);
			}
		}
		return null;
	}

	/**
	 * Get the list of ddn items (limit with securities)
	 * @return IDDNItem[] - the list of ddn item
	 */
	public IDDNItem[] getNotCleanDDNItemList() {
		IDDNItem[] fullList = getDDNItemList();
		if (fullList != null) {
			ArrayList notCleanList = new ArrayList();
			for (int ii = 0; ii < fullList.length; ii++) {
				if (!fullList[ii].isCleanType()) {
					notCleanList.add(fullList[ii]);
				}
			}
			if (notCleanList.size() > 0) {
				return (IDDNItem[]) notCleanList.toArray(new IDDNItem[0]);
			}
		}
		return null;
	}

	/**
	 * Get the list of DDN items
	 * @return ISCCertificateItem[] - the list of scc items
	 */
	public IDDNItem[] getDDNItemList() {
		return this.ddnItemList;
	}

	/**
	 * Get the version time
	 * @return long - the version time
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * Set the DDN ID
	 * @param aDDNID of long type
	 */
	public void setDDNID(long aDDNID) {
		this.ddnID = aDDNID;
	}

	/**
	 * Set the DDN Reference
	 * @param aDDNRef of String type
	 */
	public void setDDNRef(String aDDNRef) {
		this.ddnRef = aDDNRef;
	}

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	/**
	 * Set the date generated
	 * @param aDateGenerated of Date type
	 */
	public void setDateGenerated(Date aDateGenerated) {
		this.dateGenerated = aDateGenerated;
	}

	/**
	 * Get the deferred to date
	 */
	public void setDeferredToDate(Date aDeferredToDate) {
		this.deferredToDate = aDeferredToDate;
	}

	/**
	 * Get the number of days that the DDN is valid for
	 * @param aDaysValid of int type
	 */
	public void setDaysValid(int aDaysValid) {
		this.daysValid = aDaysValid;
	}

	/**
	 * Set the extended date
	 */
	public void setExtendedToDate(Date anExtendedToDate) {
		this.extendedToDate = anExtendedToDate;
	}

	/**
	 * Set the approval date
	 * @param anApprovalDate of Date type
	 */
	public void setApprovalDate(Date anApprovalDate) {
		this.approvalDate = anApprovalDate;
	}

	/**
	 * Set the approval by
	 * @param anApprovalBy of String type
	 */
	public void setApprovalBy(String anApprovalBy) {
		this.approvalBy = anApprovalBy;
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
	 * Get the clean activated amount for clean limits
	 */
	public void setCleanDDNAmount(Amount aCleanDDNAmount) {
		this.cleanDDNAmount = aCleanDDNAmount;
	}

	/**
	 * Get the activated amount (limit with security)
	 */
	public void setDDNAmount(Amount anDDNAmount) {
		this.ddnAmount = anDDNAmount;
	}

	/**
	 * Set the total ddn amount
	 */
	public void setTotalDDNAmount(Amount aTotalDDNAmount) {
		this.totalDDNAmount = aTotalDDNAmount;
	}

	/**
	 * Set the document Used to differentiate document type
	 */
	public void setDocumentType(String aDocumentType) {
		this.documentType = aDocumentType;
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
	 * Set the is SCC issued indicator
	 * @param anIsSCCIssuedInd of boolean type
	 */
	public void setIsSCCIssuedInd(boolean anIsSCCIssuedInd) {
		this.isSCCIssuedInd = anIsSCCIssuedInd;
	}

	/**
	 * Set the list of DDN items
	 * @param anIDDNItemList of IDDNItem[] type
	 */
	public void setDDNItemList(IDDNItem[] anIDDNItemList) {
		this.ddnItemList = anIDDNItemList;
	}

	/**
	 * Set the version time
	 * @param aVersionTime of long type
	 */
	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
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

	/**
	 * @return Returns the bcaApprovalAuthority.
	 */
	public String getBcaApprovalAuthority() {
		return bcaApprovalAuthority;
	}

	/**
	 * @param bcaApprovalAuthority The bcaApprovalAuthority to set.
	 */
	public void setBcaApprovalAuthority(String bcaApprovalAuthority) {
		this.bcaApprovalAuthority = bcaApprovalAuthority;
	}

	/**
	 * @return Returns the bcaApprovalDate.
	 */
	public Date getBcaApprovalDate() {
		return bcaApprovalDate;
	}

	/**
	 * @param bcaApprovalDate The bcaApprovalDate to set.
	 */
	public void setBcaApprovalDate(Date bcaApprovalDate) {
		this.bcaApprovalDate = bcaApprovalDate;
	}

	/**
	 * @return Returns the bcaExtReviewDate.
	 */
	public Date getBcaExtReviewDate() {
		return bcaExtReviewDate;
	}

	/**
	 * @param bcaExtReviewDate The bcaExtReviewDate to set.
	 */
	public void setBcaExtReviewDate(Date bcaExtReviewDate) {
		this.bcaExtReviewDate = bcaExtReviewDate;
	}

	/**
	 * @return Returns the bcaNextReviewDate.
	 */
	public Date getBcaNextReviewDate() {
		return bcaNextReviewDate;
	}

	/**
	 * @param bcaNextReviewDate The bcaNextReviewDate to set.
	 */
	public void setBcaNextReviewDate(Date bcaNextReviewDate) {
		this.bcaNextReviewDate = bcaNextReviewDate;
	}

	/**
	 * @return Returns the bcaOrigCtry.
	 */
	public String getBcaOrigCtry() {
		return bcaOrigCtry;
	}

	/**
	 * @param bcaOrigCtry The bcaOrigCtry to set.
	 */
	public void setBcaOrigCtry(String bcaOrigCtry) {
		this.bcaOrigCtry = bcaOrigCtry;
	}

	/**
	 * @return Returns the bcaOrigOrg.
	 */
	public String getBcaOrigOrg() {
		return bcaOrigOrg;
	}

	/**
	 * @param bcaOrigOrg The bcaOrigOrg to set.
	 */
	public void setBcaOrigOrg(String bcaOrigOrg) {
		this.bcaOrigOrg = bcaOrigOrg;
	}

	/**
	 * @return Returns the bFLIssuedDate.
	 */
	public Date getBFLIssuedDate() {
		return bFLIssuedDate;
	}

	/**
	 * @param issuedDate The bFLIssuedDate to set.
	 */
	public void setBFLIssuedDate(Date issuedDate) {
		bFLIssuedDate = issuedDate;
	}

	/**
	 * @return Returns the creditGradeCode.
	 */
	public String getCreditGradeCode() {
		return creditGradeCode;
	}

	/**
	 * @param creditGradeCode The creditGradeCode to set.
	 */
	public void setCreditGradeCode(String creditGradeCode) {
		this.creditGradeCode = creditGradeCode;
	}

	/**
	 * @return Returns the customerName.
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName The customerName to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return Returns the custSegmentCode.
	 */
	public String getCustSegmentCode() {
		return custSegmentCode;
	}

	/**
	 * @param custSegmentCode The custSegmentCode to set.
	 */
	public void setCustSegmentCode(String custSegmentCode) {
		this.custSegmentCode = custSegmentCode;
	}

	/**
	 * @return Returns the famCode.
	 */
	public String getFamCode() {
		return famCode;
	}

	/**
	 * @param famCode The famCode to set.
	 */
	public void setFamCode(String famCode) {
		this.famCode = famCode;
	}

	/**
	 * @return Returns the famName.
	 */
	public String getFamName() {
		return famName;
	}

	/**
	 * @param famName The famName to set.
	 */
	public void setFamName(String famName) {
		this.famName = famName;
	}

	/**
	 * @return Returns the legalName.
	 */
	public String getLegalName() {
		return legalName;
	}

	/**
	 * @param legalName The legalName to set.
	 */
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	/**
	 * @return Returns the subProfileID.
	 */
	public String getSubProfileID() {
		return subProfileID;
	}

	/**
	 * @param subProfileID The subProfileID to set.
	 */
	public void setSubProfileID(String subProfileID) {
		this.subProfileID = subProfileID;
	}

	/**
	 * @return Returns the legalID.
	 */
	public String getLegalID() {
		return legalID;
	}

	/**
	 * @param legalID The legalID to set.
	 */
	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

    public String getReleaseTo() {
        return releaseTo;
    }

    public void setReleaseTo(String releaseTo) {
        this.releaseTo = releaseTo;
    }

}
