/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/ISCCertificateItem.java,v 1.11 2006/05/30 10:19:38 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;

/**
 * This interface defines the list of attributes that is required for SCC item
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/05/30 10:19:38 $ Tag: $Name: $
 */
public interface ISCCertificateItem extends Serializable {
	/**
	 * Get the SCC item ID
	 * @return long - the SCC item ID
	 */
	public long getSCCertItemID();

	/**
	 * Get the scc item reference, the biz key
	 * @return long - the scc item reference
	 */
	public long getSCCertItemRef();

	/**
	 * Get the checklist status
	 * @return String - the checklist status
	 */
	public String getCheckListStatus();

	/**
	 * Get the delete indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd();

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
	 * Get the limit reference
	 * @return String - the limit reference
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
	public ICollateralAllocation[] getCollateralAllocations();

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
	 * Get the product description
	 * @return String - the product description
	 */
	public String getProductDesc();

	/**
	 * Get the approved limit amount
	 * @return Amount - the approved limit amount
	 */
	public Amount getApprovedLimitAmount();

	/**
	 * Get the approval Date
	 * @return Date - the approval date
	 */
	public Date getApprovalDate();

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
	 * Get the activated amount
	 * @return Amount - the activated amount
	 */
	public Amount getActivatedAmount();

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
	 * Check if inner and outer limit are of the same BCA.
	 * 
	 * @return long
	 */
	public boolean getIsInnerOuterSameBCA();

    public Date getExpiryAvailabilityDate();

    public Amount getDisbursementAmount();

    public Amount getEnforceAmount();

    public String getPaymentInstruction();

	/**
	 * Helper method to check if the limit is an inner limit
	 * @return boolean - true if it is the inner limit and false otherwise
	 */
	public boolean isInnerLimit();

	/**
	 * Get the limits object
	 * @return ILimit - the limit object
	 */
	// public ILimit getLimit();
	/**
	 * Set the SCC item ID
	 * @param aSCCCertItemID of long type
	 */
	public void setSCCertItemID(long aSCCCertItemID);

	/**
	 * Set the sc certificate item reference
	 * @param aSCCertItemRef of long type
	 */
	public void setSCCertItemRef(long aSCCertItemRef);

	/**
	 * Set the checklist status
	 * @param aCheckListStatus of String type
	 */
	public void setCheckListStatus(String aCheckListStatus);

	/**
	 * Set the delete indicator
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	/**
	 * Set the limit reference
	 * @param aLimitRef of String type
	 */
	public void setLimitRef(String aLimitRef);

	/**
	 * Set the Outer Limit Ref
	 * 
	 * @param outerLimitRef is of type String
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
	public void setCollateralAllocations(ICollateralAllocation[] anICollateralAllocationList);

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
	 * Set the approval Date
	 * @param anApprovalDate of Date type
	 */
	public void setApprovalDate(Date anApprovalDate);

	/**
	 * Set limit maturity date.
	 * 
	 * @param maturityDate of type Date
	 */
	public void setMaturityDate(Date maturityDate);

	/**
	 * Set actual limit expiry date.
	 * 
	 * @param limitExpiryDate of type Date
	 */
	public void setLimitExpiryDate(Date limitExpiryDate);

	/**
	 * Set the activated amount
	 * @param anActivateAmount of Amount type
	 */
	public void setActivatedAmount(Amount anActivateAmount);

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
	 * Set an indicator to check if inner and outer limit are of the same BCA.
	 * 
	 * @param isInnerOuterSameBCA of type boolean
	 */
	public void setIsInnerOuterSameBCA(boolean isInnerOuterSameBCA);

    public void setExpiryAvailabilityDate(Date expiryAvailabilatyDate);

    public void setDisbursementAmount(Amount disbursementAmount);

    public void setEnforceAmount(Amount enforceAmount);

    public void setPaymentInstruction(String paymentInstruction);

	/**
	 * Helper method to check if the limit is a clean type or not
	 * @return boolean - true if it is a clean type (no securities)
	 */
	public boolean isCleanType();

	/**
	 * Set the clean type indicator
	 * @param aCleanTypeInd of boolean type
	 */
	public void setIsCleanTypeInd(boolean aCleanTypeInd);

	// public void setLimit(ILimit anILimit);
	public Object clone();
}
