/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/OBSCCertificateItem.java,v 1.12 2006/05/30 10:19:38 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;

/**
 * This class that provides the implementation for ISCCertificateItem
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/05/30 10:19:38 $ Tag: $Name: $
 */
public class OBSCCertificateItem implements ISCCertificateItem, Cloneable {
	private long scCertItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long scCertItemRef = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String checkListStatus = null;

	private boolean isDeletedInd = false;

	private long limitID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String limitType = null;

	private String limitRef = null;

	private String outerLimitRef = null;

	private boolean limitExistingInd = false;

	private ICollateralAllocation[] collateralAllocationList = null;

	private String coBorrowerLegalID = null;

	private String coBorrowerName = null;

	private IBookingLocation limitBookingLocation = null;

	private String productDesc = null;

	private Amount approvedLimitAmount = null;

	private Date approvalDate = null;

	private Date maturityDate; // cert maturity date.

	private Date limitExpiryDate; // actual limit expiry date.

	private Amount activatedAmount = null;

	private boolean isCleanTypeInd = false; // R1.5 CR146

	private long outerLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private long outerLimitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isInnerOuterSameBCA = true;

    private Date expiryAvailabilityDate = null;

    private Amount disbursementAmount = null;

    private Amount enforceAmount = null;

    private String paymentInstruction = null;

	public OBSCCertificateItem() {
	}

	public OBSCCertificateItem(String aLimitType, long aLimitID) {
		this.limitType = aLimitType;
		this.limitID = aLimitID;
	}

	/**
	 * Get the SCC item ID
	 * @return long - the SCC item ID
	 */
	public long getSCCertItemID() {
		return this.scCertItemID;
	}

	/**
	 * Get the scc item reference, the biz key
	 * @return long - the scc item reference
	 */
	public long getSCCertItemRef() {
		return this.scCertItemRef;
	}

	/**
	 * Get the checklist status
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
	 * Get the limit existing indicator
	 * @return boolean - true if limit exist in previous BCA
	 */
	public boolean getIsLimitExistingInd() {
		return this.limitExistingInd;
	}

	/**
	 * Get the list of collateral tied to the limit
	 * @return ICollateralAllocation[] - the list of collateral tied to the
	 *         limit
	 */
	public ICollateralAllocation[] getCollateralAllocations() {
		return this.collateralAllocationList;
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
	 * Get cms outer limit id.
	 * 
	 * @return long
	 */
	public long getOuterLimitID() {
		return outerLimitID;
	}

	/**
	 * Get cms outer limit profile id.
	 * 
	 * @return long
	 */
	public long getOuterLimitProfileID() {
		return outerLimitProfileID;
	}

	/**
	 * Check if inner and outer limit are of the same BCA.
	 * 
	 * @return long
	 */
	public boolean getIsInnerOuterSameBCA() {
		return isInnerOuterSameBCA;
	}

    public Date getExpiryAvailabilityDate() {
        return expiryAvailabilityDate;
    }

    public Amount getDisbursementAmount() {
        return disbursementAmount;
    }

    public Amount getEnforceAmount() {
        return enforceAmount;
    }

    public String getPaymentInstruction() {
        return paymentInstruction;
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
	 * Set the SCC item ID
	 * @param aSCCCertItemID of long type
	 */
	public void setSCCertItemID(long aSCCCertItemID) {
		this.scCertItemID = aSCCCertItemID;
	}

	/**
	 * Set the sc certificate item reference
	 * @param aSCCertItemRef of long type
	 */
	public void setSCCertItemRef(long aSCCertItemRef) {
		this.scCertItemRef = aSCCertItemRef;
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
	 * Set the limit existing indicator
	 * @param anIsLimitExistingInd of boolean type
	 */
	public void setIsLimitExistingInd(boolean anIsLimitExistingInd) {
		this.limitExistingInd = anIsLimitExistingInd;
	}

	/**
	 * Set the list of collaterals tied to the limit
	 * @param anICollateralAllocationList of ICollateralAllocation type
	 */
	public void setCollateralAllocations(ICollateralAllocation[] anICollateralAllocationList) {
		this.collateralAllocationList = anICollateralAllocationList;
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
	 * Set the approval Date
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
	 * Set cms outer limit id.
	 * 
	 * @param outerLimitID of type long
	 */
	public void setOuterLimitID(long outerLimitID) {
		this.outerLimitID = outerLimitID;
	}

	/**
	 * Set cms outer limit profile id.
	 * 
	 * @param outerLimitProfileID of type long
	 */
	public void setOuterLimitProfileID(long outerLimitProfileID) {
		this.outerLimitProfileID = outerLimitProfileID;
	}

	/**
	 * Set an indicator to check if inner and outer limit are of the same BCA.
	 * 
	 * @param isInnerOuterSameBCA of type boolean
	 */
	public void setIsInnerOuterSameBCA(boolean isInnerOuterSameBCA) {
		this.isInnerOuterSameBCA = isInnerOuterSameBCA;
	}

    public void setExpiryAvailabilityDate(Date expiryAvailabilityDate) {
        this.expiryAvailabilityDate = expiryAvailabilityDate;
    }

    public void setDisbursementAmount(Amount disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

    public void setEnforceAmount(Amount enforceAmount) {
        this.enforceAmount = enforceAmount;
    }

    public void setPaymentInstruction(String paymentInstruction) {
        this.paymentInstruction = paymentInstruction;
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

	public Object clone() {
		try {
			return super.clone();
		}
		catch (CloneNotSupportedException ex) {
			throw new RuntimeException();
		}
	}
}
