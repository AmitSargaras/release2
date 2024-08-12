/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/OBCCCertificateItem.java,v 1.9 2005/07/26 03:34:19 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class provides the implementation for the ICCCertificateItem
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/07/26 03:34:19 $ Tag: $Name: $
 */
public class OBCCCertificateItem implements ICCCertificateItem {
	private long ccCertItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long ccCertItemRef = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String checkListStatus = null;

	private boolean isDeletedInd = false;

	private long limitID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String limitType = null;

	private String limitRef = null;

	private String outerLimitRef = null;

	private String coBorrowerLegalID = null;

	private String coBorrowerName = null;

	private IBookingLocation limitBookingLocation = null;

	private String productDesc = null;

	private Amount approvedLimitAmount = null;

	private Date approvalDate = null;

	private Date maturityDate; // cert maturity date

	private Date limitExpiryDate; // actual limit expiry date

	private Amount activatedAmount = null;

	private boolean isCleanTypeInd = false;

	private long outerLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private long outerLimitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isInnerOuterSameBCA = true;

	public OBCCCertificateItem() {
	}

	public OBCCCertificateItem(String aLimitType, long aLimitID) {
		this.limitType = aLimitType;
		this.limitID = aLimitID;
	}

	/**
	 * Get the CCC item ID
	 * @return long - the CCC item ID
	 */
	public long getCCCertItemID() {
		return this.ccCertItemID;
	}

	/**
	 * Get the ccc item reference, the biz key
	 * @return long - the ccc item reference
	 */
	public long getCCCertItemRef() {
		return this.ccCertItemRef;
	}

	/**
	 * Get the checkList status
	 * @return String - the checklist status
	 */
	public String getCheckListStatus() {
		return this.checkListStatus;
	}

	/**
	 * Get the delete indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
	}

	/**
	 * Get the limit ID
	 * @return long - the limit ID
	 */
	public long getLimitID() {
		/*
		 * if (this.limitID !=
		 * com.integrosys.cms.app.common.constant.ICMSConstant
		 * .LONG_INVALID_VALUE) { return this.limitID; } if (getLimit() != null)
		 * { return getLimit().getLimitID(); } return
		 * com.integrosys.cms.app.common
		 * .constant.ICMSConstant.LONG_INVALID_VALUE;
		 */
		return this.limitID;
	}

	/**
	 * Get the limit type, this will indicate if a limit is outer, inner or
	 * coborrower
	 * @return String - the limit type
	 */
	public String getLimitType() {
		return this.limitType;
	}

	/**
	 * Get the limit reference
	 * @return String - the limit reference
	 */
	public String getLimitRef() {
		return this.limitRef;
	}

	/**
	 * Get the outer limit reference
	 * @return String - the outer limit reference
	 */
	public String getOuterLimitRef() {
		return this.outerLimitRef;
	}

	/**
	 * This is used for Co-Borrower CCC only
	 * @return String - the co borrower legal ID
	 */
	public String getCoBorrowerLegalID() {
		return this.coBorrowerLegalID;
	}

	/**
	 * This is used for Co-Borrower CCC only
	 * @return String - the co borrower name
	 */
	public String getCoBorrowerName() {
		return this.coBorrowerName;
	}

	/**
	 * Get the limit booking location
	 * @return IBookingLocation - the limit booking location
	 */
	public IBookingLocation getLimitBookingLocation() {
		return this.limitBookingLocation;
	}

	/**
	 * Get the product description
	 * @return String - the product description
	 */
	public String getProductDesc() {
		return this.productDesc;
	}

	/**
	 * Get the approved limit amount
	 * @return Amount - the approved limit amount
	 */
	public Amount getApprovedLimitAmount() {
		return this.approvedLimitAmount;
	}

	/**
	 * Get the approval Date
	 * @return Date - the approval date
	 */
	public Date getApprovalDate() {
		return this.approvalDate;
	}

	/**
	 * Get limit maturity date.
	 * 
	 * @return Date
	 */
	public Date getMaturityDate() {
		return maturityDate;
	}

	/**
	 * Get actual limit expiry date.
	 * 
	 * @return Date
	 */
	public Date getLimitExpiryDate() {
		return limitExpiryDate;
	}

	/**
	 * Get the activated amount
	 * @return Amount - the activated amount
	 */
	public Amount getActivatedAmount() {
		return this.activatedAmount;
	}

	/**
	 * Helper method to check if the limit is a clean type or not
	 * @return boolean - true if it is a clean type (no securities)
	 */
	public boolean isCleanType() {
		return this.isCleanTypeInd;
	}

	/**
	 * Helper method to check if the limit is an inner limit
	 * @return boolean - true if it is the inner limit and false otherwise
	 */
	public boolean isInnerLimit() {
		/*
		 * if (getLimit() != null) { long outerLimitID =
		 * getLimit().getOuterLimitID(); if ((0 == outerLimitID) ||
		 * (com.integrosys
		 * .cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE ==
		 * outerLimitID)) { return false; } return true; } return false;
		 */
		if ((getLimitType() == null) || ICMSConstant.CCC_OUTER_LIMIT.equals(getLimitType())) {
			return false;
		}
		return true;
	}

	/**
	 * Get the limits object
	 * @return ILimit - the limit object
	 */
	/*
	 * public ILimit getLimit() { return this.limit; }
	 */

	/**
	 * Set the CCC item ID
	 * @param aCCCertItemID of long type
	 */
	public void setCCCertItemID(long aCCCertItemID) {
		this.ccCertItemID = aCCCertItemID;
	}

	/**
	 * Set the cc certificate item reference
	 * @param aCCCertItemRef of long type
	 */
	public void setCCCertItemRef(long aCCCertItemRef) {
		this.ccCertItemRef = aCCCertItemRef;
	}

	/**
	 * Set the checklist status
	 * @param aCheckListStatus of String type
	 */
	public void setCheckListStatus(String aCheckListStatus) {
		this.checkListStatus = aCheckListStatus;
	}

	/**
	 * Set the delete indicator
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	/**
	 * Set the limit reference
	 * @param aLimitRef of String type
	 */
	public void setLimitRef(String aLimitRef) {
		this.limitRef = aLimitRef;
	}

	/**
	 * Set the outer limit reference
	 * @param outerLimitRef of String type
	 */
	public void setOuterLimitRef(String outerLimitRef) {
		this.outerLimitRef = outerLimitRef;
	}

	/**
	 * Set the co-borrower legal ID
	 * @param aCoBorrowerLegalID of String type
	 */
	public void setCoBorrowerLegalID(String aCoBorrowerLegalID) {
		this.coBorrowerLegalID = aCoBorrowerLegalID;
	}

	/**
	 * Set the co-borrower name
	 * @param aCoBorrowerName of String type
	 */
	public void setCoBorrowerName(String aCoBorrowerName) {
		this.coBorrowerName = aCoBorrowerName;
	}

	/**
	 * Set the limit booking location
	 * @param aLimitBookingLocation of IBookingLocation type
	 */
	public void setLimitBookingLocation(IBookingLocation aLimitBookingLocation) {
		this.limitBookingLocation = aLimitBookingLocation;
	}

	/**
	 * Set the product description
	 * @param aProductDesc of String type
	 */
	public void setProductDesc(String aProductDesc) {
		this.productDesc = aProductDesc;
	}

	/**
	 * Set the approved limit amount
	 * @param anApprovedLimitAmount of Amount type
	 */
	public void setApprovedLimitAmount(Amount anApprovedLimitAmount) {
		this.approvedLimitAmount = anApprovedLimitAmount;
	}

	/**
	 * Set the approved Date
	 * @param anApprovalDate of Date type
	 */
	public void setApprovalDate(Date anApprovalDate) {
		this.approvalDate = anApprovalDate;
	}

	/**
	 * Set limit maturity date.
	 * 
	 * @param maturityDate of type Date
	 */
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * Set actual limit expiry date.
	 * 
	 * @param limitExpiryDate of type Date
	 */
	public void setLimitExpiryDate(Date limitExpiryDate) {
		this.limitExpiryDate = limitExpiryDate;
	}

	/**
	 * Set the activated amount
	 * @param anActivatedAmount of Amount type
	 */
	public void setActivatedAmount(Amount anActivatedAmount) {
		this.activatedAmount = anActivatedAmount;
	}

	/**
	 * Set the clean type indicator
	 * @param aCleanTypeInd of boolean type
	 */
	public void setIsCleanTypeInd(boolean aCleanTypeInd) {
		this.isCleanTypeInd = aCleanTypeInd;
	}

	/**
	 * Get outer limit id.
	 * 
	 * @return long
	 */
	public long getOuterLimitID() {
		return outerLimitID;
	}

	/**
	 * Set outer limit id.
	 * 
	 * @param outerLimitID of type long
	 */
	public void setOuterLimitID(long outerLimitID) {
		this.outerLimitID = outerLimitID;
	}

	/**
	 * Get outer limit profile id.
	 * 
	 * @return long
	 */
	public long getOuterLimitProfileID() {
		return outerLimitProfileID;
	}

	/**
	 * Set outer limit profile id.
	 * 
	 * @param outerLimitProfileID of type long
	 */
	public void setOuterLimitProfileID(long outerLimitProfileID) {
		this.outerLimitProfileID = outerLimitProfileID;
	}

	/**
	 * Check if inner and outer limit is of same BCA.
	 * 
	 * @return boolean
	 */
	public boolean getIsInnerOuterSameBCA() {
		return isInnerOuterSameBCA;
	}

	/**
	 * Set the indicator to check if inner and outer is of same BCA.
	 * 
	 * @param isInnerOuterSameBCA of type boolean
	 */
	public void setIsInnerOuterSameBCA(boolean isInnerOuterSameBCA) {
		this.isInnerOuterSameBCA = isInnerOuterSameBCA;
	}

	/**
	 * Set the limits object
	 * @param anILimit of ILimit type
	 */
	/*
	 * public void setLimit(ILimit anILimit) { this.limit = anILimit; }
	 */

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
