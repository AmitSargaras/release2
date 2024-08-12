/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/ICCCertificateItem.java,v 1.9 2005/07/26 03:34:19 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface defines the list of attributes that is required for CCC
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/07/26 03:34:19 $ Tag: $Name: $
 */
public interface ICCCertificateItem extends Serializable {
	/**
	 * Get the CCC item ID
	 * @return long - the CCC item ID
	 */
	public long getCCCertItemID();

	/**
	 * Get the CCC item reference, the biz key
	 * @return long - the CCC item reference
	 */
	public long getCCCertItemRef();

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
	 * Get the Outer Limit Ref
	 * 
	 * @return String
	 */
	public String getOuterLimitRef();

	/**
	 * Get the limit reference
	 * @return String - the limit reference
	 */
	public String getLimitRef();

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
	 * Helper method to check if the limit is a clean type or not
	 * @return boolean - true if it is a clean type (no securities)
	 */
	public boolean isCleanType();

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
	 * Set the CCC item ID
	 * @param aCCCertItemID of long type
	 */
	public void setCCCertItemID(long aCCCertItemID);

	/**
	 * Set the cc certificate item reference
	 * @param aCCCertItemRef of long type
	 */
	public void setCCCertItemRef(long aCCCertItemRef);

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
	 * Set the clean type indicator
	 * @param aCleanTypeInd of boolean type
	 */
	public void setIsCleanTypeInd(boolean aCleanTypeInd);

	/**
	 * Get outer limit id.
	 * 
	 * @return long
	 */
	public long getOuterLimitID();

	/**
	 * Set outer limit id.
	 * 
	 * @param outerLimitID of type long
	 */
	public void setOuterLimitID(long outerLimitID);

	/**
	 * Get outer limit profile id.
	 * 
	 * @return long
	 */
	public long getOuterLimitProfileID();

	/**
	 * Set outer limit profile id.
	 * 
	 * @param outerLimitProfileID of type long
	 */
	public void setOuterLimitProfileID(long outerLimitProfileID);

	/**
	 * Check if inner and outer limit is of same BCA.
	 * 
	 * @return boolean
	 */
	public boolean getIsInnerOuterSameBCA();

	/**
	 * Set the indicator to check if inner and outer is of same BCA.
	 * 
	 * @param isInnerOuterSameBCA of type boolean
	 */
	public void setIsInnerOuterSameBCA(boolean isInnerOuterSameBCA);

	/**
	 * Set the limits object
	 * @param anILimit of ILimit type
	 */
	// public void setLimit(ILimit anILimit);
}
