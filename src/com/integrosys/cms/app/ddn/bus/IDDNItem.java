/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/IDDNItem.java,v 1.15 2005/08/29 08:13:29 whuang Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface defines the list of attributes that is required for DDN item
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2005/08/29 08:13:29 $ Tag: $Name: $
 */
public interface IDDNItem extends Serializable {
	/**
	 * Get the DDN item ID
	 * @return long - the DDN item ID
	 */
	public long getDDNItemID();

	/**
	 * Get the DDN item reference, the biz key
	 * @return long - the DDN item reference
	 */
	public long getDDNItemRef();

	/**
	 * Get the limit ID
	 * @return long - the limit ID
	 */
	public long getLimitID();

	/**
	 * Get the limit type, this will indicate if a limit is outer, inner or
	 * coborrower
	 * @return String - the limit type
	 */
	public String getLimitType();

	/**
	 * Helper method to ger the limit reference @ String - the limit reference
	 */
	public String getLimitRef();

	/**
	 * Get the Outer Limit Ref
	 * 
	 * @return String
	 */
	public String getOuterLimitRef();

	/**
	 * Get the limit existing indicator
	 * @return boolean - true if limit exist in previous BCA
	 */
	public boolean getIsLimitExistingInd();

	/**
	 * Get the list of collateral tied to the limit
	 * @return ICollateralAllocation[] - the list of collateral tied to the
	 *         limit
	 */
	// public ICollateralAllocation[] getCollateralAllocations();
	/**
	 * Get the list of Collateral Reference List linked to the limit
	 * @return DDNCollateralInfo[] - the list of collateral references
	 */
	public DDNCollateralInfo[] getDDNCollateralInfoList();

	/**
	 * This is used for Co-Borrower CCC only
	 * @return String - the co borrower legal ID
	 */
	public String getCoBorrowerLegalID();

	/**
	 * This is used for Co-Borrower CCC only
	 * @return String - the co borrower name
	 */
	public String getCoBorrowerName();

	/**
	 * Get the limit booking location
	 * @return IBookingLocation - the limit booking location
	 */
	public IBookingLocation getLimitBookingLocation();

	/**
	 * Get the limit product description
	 * @return String - the limit product description
	 */
	public String getProductDesc();

	/**
	 * Get the approved limit amount
	 * @return Amount - the approved limit amount
	 */
	public Amount getApprovedLimitAmount();

	/**
	 * Get the activated amount
	 * @return Amount - the activated limit amount
	 */
	public Amount getActivatedAmount();

	/**
	 * Get the DDN Amount
	 * @return Amount - the DDN Amount
	 */
	public Amount getDDNAmount();

	/**
	 * Get DDN issued indicator
	 * @return boolean - true if DDN is issued and false otherwise
	 */
	public boolean getIsDDNIssuedInd();

	/**
	 * Get the delete indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd();

	/**
	 * Get the issued date
	 * @return Date - the DDN issued date
	 */
	public Date getIssuedDate();

	/**
	 * Get limit maturity date.
	 * 
	 * @return Date
	 */
	public Date getMaturityDate();

	/**
	 * Get actual limit expiry date.
	 * 
	 * @return Date
	 */
	public Date getLimitExpiryDate();

	/**
	 * Helper method to check if the limit is an inner limit
	 * @return boolean - true if it is the inner limit and false otherwise
	 */
	public boolean isInnerLimit();

	/**
	 * Helper method to check if the limit is a clean type or not
	 * @return boolean - true if it is a clean type (no securities)
	 */
	public boolean isCleanType();

	/**
	 * Get the checklist map
	 * @return HashMap - the checklist map
	 */
	public HashMap getCheckListMap();

	/**
	 * Get cms outer limit id.
	 * 
	 * @return long
	 */
	public long getOuterLimitID();

	/**
	 * Get cms outer limit profile id.
	 * 
	 * @return long
	 */
	public long getOuterLimitProfileID();

	/**
	 * Check if the inner and outer limit are of the same BCA.
	 * 
	 * @return
	 */
	public boolean getIsInnerOuterSameBCA();

	/**
	 * Get the checklist status for a collateral
	 * @param aCollateralID of long type
	 * @return String - the checklist status
	 */
	public String retrieveCheckListStatus(long aCollateralID);

	/**
	 * Get the limits object
	 * @return ILimit - the limit object
	 */
	// public ILimit getLimit();
	/**
	 * Set the DDN item ID
	 * @param aDDNItemID of long type
	 */
	public void setDDNItemID(long aDDNItemID);

	/**
	 * Set the sc certificate item reference
	 * @param aDDNItemRef of long type
	 */
	public void setDDNItemRef(long aDDNItemRef);

	/**
	 * Set the limit reference
	 * @param aLimitRef of String type
	 */
	public void setLimitRef(String aLimitRef);

	/**
	 * Set the Outer Limit Ref
	 */
	public void setOuterLimitRef(String outerLimitRef);

	/**
	 * Set the limit existing indicator
	 * @param anIsLimitExistingInd of boolean type
	 */
	public void setIsLimitExistingInd(boolean anIsLimitExistingInd);

	/**
	 * Set the list of collaterals tied to the limit
	 * @param anICollateralAllocationList of ICollateralAllocation type
	 */
	// public void setCollateralAllocations(ICollateralAllocation[]
	// anICollateralAllocationList);
	/**
	 * Set the list of collateral references linked to the limit
	 * @param aDDNCollateralInfoList of String[] type
	 */
	public void setDDNCollateralInfoList(DDNCollateralInfo[] aDDNCollateralInfoList);

	/**
	 * Set the co-borrower legal ID
	 * @param aCoBorrowerLegalID of String type
	 */
	public void setCoBorrowerLegalID(String aCoBorrowerLegalID);

	/**
	 * Set the co-borrower name
	 * @param aCoBorrowerName of String type
	 */
	public void setCoBorrowerName(String aCoBorrowerName);

	/**
	 * Set the limit booking location
	 * @param aLimitBookingLocation of IBookingLocation type
	 */
	public void setLimitBookingLocation(IBookingLocation aLimitBookingLocation);

	/**
	 * Set the product description
	 * @param aProductDesc of String type
	 */
	public void setProductDesc(String aProductDesc);

	/**
	 * Set the approved limit amount
	 * @param anApprovedLimitAmount of Amount type
	 */
	public void setApprovedLimitAmount(Amount anApprovedLimitAmount);

	/**
	 * Set the activated amount
	 * @param anActivateAmount of Amount type
	 */
	public void setActivatedAmount(Amount anActivateAmount);

	/**
	 * Set the clean type indicator
	 * @param aCleanTypeInd of boolean type
	 */
	public void setIsCleanTypeInd(boolean aCleanTypeInd);

	/**
	 * Set the DDN Amount
	 * @param aDDNAmount of Amount type
	 */
	public void setDDNAmount(Amount aDDNAmount);

	/**
	 * Set the DDN issued indicator
	 * @param anIsDDNIssuedInd of boolean type
	 */
	public void setIsDDNIssuedInd(boolean anIsDDNIssuedInd);

	/**
	 * Set the delete indicator
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	/**
	 * Set the DDN issued date
	 * @param anIssuedDate of Date type
	 */
	public void setIssuedDate(Date anIssuedDate);

	/**
	 * Set limit maturity date.
	 * 
	 * @param maturityDate of type Date
	 */
	public void setMaturityDate(Date maturityDate);

	/**
	 * Set limit expiry date.
	 * 
	 * @param limitExpiryDate of type Date
	 */
	public void setLimitExpiryDate(Date limitExpiryDate);

	/**
	 * Set the checklist map
	 * @param aCheckListMap of HashMap type
	 */
	public void setCheckListMap(HashMap aCheckListMap);

	/**
	 * Set cms outer limit id.
	 * 
	 * @param outerLimitID of type long
	 */
	public void setOuterLimitID(long outerLimitID);

	/**
	 * Set cms outer limit profile id.
	 * 
	 * @param outerLimitProfileID of type long
	 */
	public void setOuterLimitProfileID(long outerLimitProfileID);

	/**
	 * Set indicator if inner and outer limit are of the same BCA.
	 * 
	 * @param isInnerOuterSameBCA of type boolean
	 */
	public void setIsInnerOuterSameBCA(boolean isInnerOuterSameBCA);

	/**
	 * Get cms coborrower customer id.
	 * 
	 * @return long
	 */
	public long getCoBorrowerCustID();

	/**
	 * Set cms coborrower customer id.
	 * 
	 * @param coBorrowerCustID of type long
	 */
	public void setCoBorrowerCustID(long coBorrowerCustID);

	/**
	 * Get the limit status.
	 * 
	 * @return String
	 */
	public String getLimitStatus();

	/**
	 * Set limit status.
	 * 
	 * @param limitStatus of type String
	 */
	public void setLimitStatus(String limitStatus);

	// public void setLimit(ILimit anILimit);
	public Object clone();

	public String getApprovedLimitAmtCcy();

	public BigDecimal getApprovedLimitAmt();

	public String getBkgLoctnCtry();

	public String getBkgLoctnOrg();

	public String getSecurityIDs();

	public String getSecurityTypes();

	public String getOutLimitRef();

	public String getCoBorrowLegalID();

	public String getCoBorrowName();

	public Date getApprovalLimitDate();

	public String getProductType();

    public long getDocNumber();

    public String getDocCode();

    public String getDocDesc();

    public Date getDateDefer();

    public Date getDateOfReturn();

    public String getDocStatus();

    public String getActionParty();

    public Date getTheApprovalDate();

    public String getApprovedBy();

	public void setApprovedLimitAmtCcy(String approvedLimitAmtCcy);

	public void setApprovedLimitAmt(BigDecimal approvedLimitAmt);

	public void setBkgLoctnCtry(String bkgLoctnCtry);

	public void setBkgLoctnOrg(String bkgLoctnOrg);

	public void setSecurityIDs(String securityIDs);

	public void setSecurityTypes(String securityTypes);

	public void setCoBorrowName(String name);

	public void setCoBorrowLegalID(String legalID);

	public void setOutLimitRef(String ref);

	public void setApprovalLimitDate(Date limitDate);

	public void setProductType(String type);

    public void setDocNumber(long docNumber);

    public void setDocCode(String docCode);

    public void setDocDesc(String docDesc);

    public void setDateDefer(Date dateDefer);

    public void setDateOfReturn(Date dateOfReturn);

    public void setDocStatus(String docStatus);

    public void setActionParty(String actionParty);

    public void setTheApprovalDate(Date theApprovalDate);

    public void setApprovedBy(String approvedBy);
}
