/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/OBDDNItem.java,v 1.16 2005/08/29 08:12:56 whuang Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class provides the implementation for IDDNItem
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2005/08/29 08:12:56 $ Tag: $Name: $
 */
public class OBDDNItem implements IDDNItem, Cloneable {
	private long ddnItemID = ICMSConstant.LONG_INVALID_VALUE;

	private long ddnItemRef = ICMSConstant.LONG_INVALID_VALUE;

	private long limitID = ICMSConstant.LONG_INVALID_VALUE;

	private String limitType = null;

	private String limitRef = null;

	private String outerLimitRef = null;

	private boolean limitExistingInd = false;

	// private ICollateralAllocation[] collateralAllocationList = null;
	private DDNCollateralInfo[] collateralInfoList = null;

	private String coBorrowerLegalID = null;

	private String coBorrowerName = null;

	private IBookingLocation limitBookingLocation = null;

	private String productDesc = null;

	private Amount approvedLimitAmount = null;

	private Amount activatedAmount = null;

	private Amount ddnAmount = null;

	private boolean isDDNIssuedInd = false;

	private boolean isDeletedInd = false;

	private Date issuedDate = null;

	private Date maturityDate = null; // certificate limit expiry date

	private Date limitExpiryDate = null; // actuall limit expiry date

	private HashMap checkListMap = null;

	private boolean isCleanTypeInd = false;

	private long outerLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private long outerLimitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isInnerOuterSameBCA = true;

	private long coBorrowerCustID = ICMSConstant.LONG_INVALID_VALUE;

	private String limitStatus = ICMSConstant.STATE_ACTIVE;

	// private ILimit limit = null;
	private String approvedLimitAmtCcy = null;

	private BigDecimal approvedLimitAmt = null;

	private String bkgLoctnCtry = null;

	private String bkgLoctnOrg = null;

	private String securityIDs = null;

	private String securityTypes = null;

	private String outLimitRef = null;

	private String coBorrowLegalID = null;

	private String coBorrowName = null;

	private Date approvalLimitDate = null;

	private String productType = null;

    private long docNumber = ICMSConstant.LONG_INVALID_VALUE;

    private String docCode = null;

    private String docDesc = null;

    private Date dateDefer = null;

    private Date dateOfReturn = null;

    private String docStatus = null;

    private String actionParty = null;

    private Date theApprovalDate = null;

    private String approvedBy = null;

	public OBDDNItem() {
	}

	public OBDDNItem(String aLimitType, long aLimitID) {
		this.limitID = aLimitID;
		this.limitType = aLimitType;
	}

	/**
	 * Get the DDN item ID
	 * @return long - the DDN item ID
	 */
	public long getDDNItemID() {
		return this.ddnItemID;
	}

	/**
	 * Get the DDN item reference, the biz key
	 * @return long - the DDN item reference
	 */
	public long getDDNItemRef() {
		return this.ddnItemRef;
	}

	/**
	 * Get the limits object
	 * @return ILimit - the limit object
	 */
	/*
	 * public ILimit getLimit() { return this.limit; }
	 */

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
	 * Get the list of Collateral Reference List linked to the limit
	 * @return DDNCollateralInfo[] - the list of collateral references
	 */
	public DDNCollateralInfo[] getDDNCollateralInfoList() {
		return this.collateralInfoList;
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
	 * Get the activated amount
	 * @return Amount - the activated limit amount
	 */
	public Amount getActivatedAmount() {
		return this.activatedAmount;
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
	 * Helper method to check if the limit is a clean type or not
	 * @return boolean - true if it is a clean type (no securities)
	 */
	public boolean isCleanType() {
		return this.isCleanTypeInd;
	}

	/**
	 * Get the DDN Amount
	 * @return Amount - the DDN Amount
	 */
	public Amount getDDNAmount() {
		return this.ddnAmount;
	}

	/**
	 * Get DDN issued indicator
	 * @return boolean - true if DDN is issued and false otherwise
	 */
	public boolean getIsDDNIssuedInd() {
		return this.isDDNIssuedInd;
	}

	/**
	 * Get the delete indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
	}

	/**
	 * Get the issued date
	 * @return Date - the DDN issued date
	 */
	public Date getIssuedDate() {
		return this.issuedDate;
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
	 * Get the checklist map
	 * @return HashMap - the checklist map
	 */
	public HashMap getCheckListMap() {
		return this.checkListMap;
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
	 * Check if the inner and outer limit are of the same BCA.
	 * 
	 * @return
	 */
	public boolean getIsInnerOuterSameBCA() {
		return isInnerOuterSameBCA;
	}

	/**
	 * Get the checklist status for a collateral
	 * @param aCollateralID of long type
	 * @return String - the checklist status
	 */
	public String retrieveCheckListStatus(long aCollateralID) {
		return (String) checkListMap.get(new Long(aCollateralID));
	}

	/**
	 * Set the DDN item ID
	 * @param aDDNItemID of long type
	 */
	public void setDDNItemID(long aDDNItemID) {
		this.ddnItemID = aDDNItemID;
	}

	/**
	 * Set the sc certificate item reference
	 * @param aDDNItemRef of long type
	 */
	public void setDDNItemRef(long aDDNItemRef) {
		this.ddnItemRef = aDDNItemRef;
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
	 * Set the list of collateral references linked to the limit
	 * @param aCollateralInfoList of String[] type
	 */
	public void setDDNCollateralInfoList(DDNCollateralInfo[] aCollateralInfoList) {
		this.collateralInfoList = aCollateralInfoList;
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
	 * Set the activated amount
	 * @param anActivatedAmount of Amount type
	 */
	public void setActivatedAmount(Amount anActivatedAmount) {
		this.activatedAmount = anActivatedAmount;
	}

	/**
	 * Set the DDN Amount
	 * @param aDDNAmount of Amount type
	 */
	public void setDDNAmount(Amount aDDNAmount) {
		this.ddnAmount = aDDNAmount;
	}

	/**
	 * Set the clean type indicator
	 * @param aCleanTypeInd of boolean type
	 */
	public void setIsCleanTypeInd(boolean aCleanTypeInd) {
		this.isCleanTypeInd = aCleanTypeInd;
	}

	/**
	 * Set the DDN issued indicator
	 * @param anIsDDNIssuedInd of boolean type
	 */
	public void setIsDDNIssuedInd(boolean anIsDDNIssuedInd) {
		this.isDDNIssuedInd = anIsDDNIssuedInd;
	}

	/**
	 * Set the delete indicator
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	/**
	 * Set the DDN issued date
	 * @param anIssuedDate of Date type
	 */
	public void setIssuedDate(Date anIssuedDate) {
		this.issuedDate = anIssuedDate;
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
	 * Set limit expiry date.
	 * 
	 * @param limitExpiryDate of type Date
	 */
	public void setLimitExpiryDate(Date limitExpiryDate) {
		this.limitExpiryDate = limitExpiryDate;
	}

	/**
	 * Set the checklist map
	 * @param aCheckListMap of HashMap type
	 */
	public void setCheckListMap(HashMap aCheckListMap) {
		this.checkListMap = aCheckListMap;
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
	 * Set indicator if inner and outer limit are of the same BCA.
	 * 
	 * @param isInnerOuterSameBCA of type boolean
	 */
	public void setIsInnerOuterSameBCA(boolean isInnerOuterSameBCA) {
		this.isInnerOuterSameBCA = isInnerOuterSameBCA;
	}

	/**
	 * Get cms coborrower customer id.
	 * 
	 * @return long
	 */
	public long getCoBorrowerCustID() {
		return coBorrowerCustID;
	}

	/**
	 * Set cms coborrower customer id.
	 * 
	 * @param coBorrowerCustID of type long
	 */
	public void setCoBorrowerCustID(long coBorrowerCustID) {
		this.coBorrowerCustID = coBorrowerCustID;
	}

	/**
	 * Get the limit status.
	 * 
	 * @return String
	 */
	public String getLimitStatus() {
		return limitStatus;
	}

	/**
	 * Set limit status.
	 * 
	 * @param limitStatus of type String
	 */
	public void setLimitStatus(String limitStatus) {
		this.limitStatus = limitStatus;
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

	/**
	 * @return Returns the approvedLimitAmt.
	 */
	public BigDecimal getApprovedLimitAmt() {
		return approvedLimitAmt;
	}

	/**
	 * @param approvedLimitAmt The approvedLimitAmt to set.
	 */
	public void setApprovedLimitAmt(BigDecimal approvedLimitAmt) {
		this.approvedLimitAmt = approvedLimitAmt;
	}

	/**
	 * @return Returns the approvedLimitAmtCcy.
	 */
	public String getApprovedLimitAmtCcy() {
		return approvedLimitAmtCcy;
	}

	/**
	 * @param approvedLimitAmtCcy The approvedLimitAmtCcy to set.
	 */
	public void setApprovedLimitAmtCcy(String approvedLimitAmtCcy) {
		this.approvedLimitAmtCcy = approvedLimitAmtCcy;
	}

	/**
	 * @return Returns the bkgLoctnCtry.
	 */
	public String getBkgLoctnCtry() {
		return bkgLoctnCtry;
	}

	/**
	 * @param bkgLoctnCtry The bkgLoctnCtry to set.
	 */
	public void setBkgLoctnCtry(String bkgLoctnCtry) {
		this.bkgLoctnCtry = bkgLoctnCtry;
	}

	/**
	 * @return Returns the bkgLoctnOrg.
	 */
	public String getBkgLoctnOrg() {
		return bkgLoctnOrg;
	}

	/**
	 * @param bkgLoctnOrg The bkgLoctnOrg to set.
	 */
	public void setBkgLoctnOrg(String bkgLoctnOrg) {
		this.bkgLoctnOrg = bkgLoctnOrg;
	}

	/**
	 * @return Returns the securityIDs.
	 */
	public String getSecurityIDs() {
		return securityIDs;
	}

	/**
	 * @param securityIDs The securityIDs to set.
	 */
	public void setSecurityIDs(String securityIDs) {
		this.securityIDs = securityIDs;
	}

	/**
	 * @return Returns the securityTypes.
	 */
	public String getSecurityTypes() {
		return securityTypes;
	}

	/**
	 * @param securityTypes The securityTypes to set.
	 */
	public void setSecurityTypes(String securityTypes) {
		this.securityTypes = securityTypes;
	}

	/**
	 * @return Returns the approvalLimitDate.
	 */
	public Date getApprovalLimitDate() {
		return approvalLimitDate;
	}

	/**
	 * @param approvalLimitDate The approvalLimitDate to set.
	 */
	public void setApprovalLimitDate(Date approvalLimitDate) {
		this.approvalLimitDate = approvalLimitDate;
	}

	/**
	 * @return Returns the productType.
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType The productType to set.
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return Returns the coBorrowName.
	 */
	public String getCoBorrowName() {
		return coBorrowName;
	}

	/**
	 * @param coBorrowName The coBorrowName to set.
	 */
	public void setCoBorrowName(String coBorrowName) {
		this.coBorrowName = coBorrowName;
	}

	/**
	 * @return Returns the coBorrowID.
	 */
	public String getCoBorrowLegalID() {
		return coBorrowLegalID;
	}

	/**
	 * @param coBorrowID The coBorrowID to set.
	 */
	public void setCoBorrowLegalID(String coBorrowID) {
		this.coBorrowLegalID = coBorrowID;
	}

	/**
	 * @return Returns the outLimitRef.
	 */
	public String getOutLimitRef() {
		return outLimitRef;
	}

	/**
	 * @param outLimitRef The outLimitRef to set.
	 */
	public void setOutLimitRef(String outLimitRef) {
		this.outLimitRef = outLimitRef;
	}

    public long getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(long docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc;
    }

    public Date getDateDefer() {
        return dateDefer;
    }

    public void setDateDefer(Date dateDefer) {
        this.dateDefer = dateDefer;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getActionParty() {
        return actionParty;
    }

    public void setActionParty(String actionParty) {
        this.actionParty = actionParty;
    }

    public Date getTheApprovalDate() {
        return theApprovalDate;
    }

    public void setTheApprovalDate(Date theApprovalDate) {
        this.theApprovalDate = theApprovalDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
}
