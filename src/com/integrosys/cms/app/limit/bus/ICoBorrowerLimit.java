/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/ICoBorrowerLimit.java,v 1.22 2006/09/11 01:38:47 nkumar Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Date;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

/**
 * This interface represents a Co-Borrower Limit record.
 * 
 * @author $Author: nkumar $
 * @version $Revision: 1.22 $
 * @since $Date: 2006/09/11 01:38:47 $ Tag: $Name: $
 */
public interface ICoBorrowerLimit extends IValueObject, java.io.Serializable {
	// Getters

	/**
	 * Get Activated Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getActivatedLimitAmount();

	/**
	 * Get Approved Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getApprovedLimitAmount();

	/**
	 * Get Booking Location
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getBookingLocation();

	public float getCoActualSecurityCoverage();

	public ICMSCustomer getCoBorrowerCust();

	public String getCoLimitSecuredType();

	/**
	 * Get All Collateral Allocations
	 * 
	 * @return ICollateralAllocation[]
	 */
	public ICollateralAllocation[] getCollateralAllocations();

	public Set getCollateralAllocationsSet();

	public ICMSCustomer getCustomer();

	/**
	 * Get Co-borrower customer ID
	 * 
	 * @return long
	 */
	public long getCustomerID();

	/**
	 * Get the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @return boolean
	 */
	public boolean getExistingInd();

	/**
	 * Get the host record change status
	 * 
	 * @return String
	 */
	public String getHostStatus();

	public boolean getIsChanged();

	public boolean getIsDAPError();

	// Setters

	public boolean getIsZerorisedChanged();

	public boolean getIsZerorisedDateChanged();

	public boolean getIsZerorisedReasonChanged();

	/**
	 * Get coborrower limit's LE reference (Coborrower SCI LEID).
	 * 
	 * @return String
	 */
	public String getLEReference();

	/**
	 * Get the limit activated indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitActivatedInd();

	/**
	 * Get Limit ID
	 * 
	 * @return long
	 */
	public long getLimitID();

	/*
	 * Get Limit Reference
	 * 
	 * @return String
	 */
	public String getLimitRef();

	public boolean getLimitZerorised();

	public ICMSCustomer getMainBorrowerCust();

	public ICollateralAllocation[] getNonDeletedCollateralAllocations();

	/**
	 * Get outer limit booking location.
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getOuterLimitBookingLoc();

	/**
	 * Get Outer Limit ID
	 * 
	 * @return long
	 */
	public long getOuterLimitID();

	/**
	 * Get the outer limit ref
	 * 
	 * @return String
	 */
	public String getOuterLimitRef();

	public String getProductDesc();

	public float getRequiredSecurityCoverages();

	public String getSourceId();

	/**
	 * Get status
	 * 
	 * @return String
	 */
	public String getStatus();

	public Date getZerorisedDate();

	public String getZerorisedReasons();

	/**
	 * Set Activated Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setActivatedLimitAmount(Amount value);

	/**
	 * Set Approved Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setApprovedLimitAmount(Amount value);

	/**
	 * Set Booking Location
	 * 
	 * @param value is of type IBookingLocation
	 */
	public void setBookingLocation(IBookingLocation value);

	public void setCoActualSecurityCoverage(float coActualSecurityCoverage);

	public void setCoBorrowerCust(ICMSCustomer cust);

	public void setCoLimitSecuredType(String coLimitSecuredType);

	// R1.5 CR35 ----------------
	/**
	 * Set All Collateral Allocations
	 * 
	 * @param value is of type ICollateralAllocation[]
	 */
	public void setCollateralAllocations(ICollateralAllocation[] value);

	public void setCollateralAllocationsSet(Set collateralAllocationsSet);

	public void setCustomer(ICMSCustomer customer);

	/**
	 * Set Co-borrower customer ID
	 * 
	 * @param value is of type long
	 */
	public void setCustomerID(long value);

	/**
	 * Set the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @param value is of type boolean
	 */
	public void setExistingInd(boolean value);

	/**
	 * Set the host record change status
	 * 
	 * @param value is of type String
	 */
	public void setHostStatus(String value);

	public void setIsChanged(boolean isChanged);

	public void setIsDAPError(boolean isDAPError);

	public void setIsZerorisedChanged(boolean isChanged);

	public void setIsZerorisedDateChanged(boolean isChanged);

	public void setIsZerorisedReasonChanged(boolean isChanged);

	/**
	 * Set coborrower limit's LE reference (Coborrower SCI LEID).
	 * 
	 * @param lEReference coborrower LE reference
	 */
	public void setLEReference(String lEReference);

	/**
	 * Set the limit activated indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitActivatedInd(boolean value);

	/**
	 * Set Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitID(long value);

	/*
	 * Set Limit Reference
	 * 
	 * @param value is of type String
	 */
	public void setLimitRef(String value);

	public void setLimitZerorised(boolean zerorised);

	public void setMainBorrowerCust(ICMSCustomer cust);

	/**
	 * Set outer limit booking location.
	 * 
	 * @param outerLimitBookingLoc of type IBookingLocation
	 */
	public void setOuterLimitBookingLoc(IBookingLocation outerLimitBookingLoc);

	/**
	 * Set Outer Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setOuterLimitID(long value);

	/**
	 * Set the outer limit ref
	 * 
	 * @param value is of type String
	 */
	public void setOuterLimitRef(String value);

	public void setProductDesc(String productDesc);

	public void setRequiredSecurityCoverages(float securityCoverages);

	// --------------------------

	public void setSourceId(String sourceId);

	/**
	 * Set status
	 * 
	 * @param value is of type String
	 */
	public void setStatus(String value);
	
	public void setZerorisedDate(Date zerorisedDate);
	
	public void setZerorisedReasons(String reasons);

}