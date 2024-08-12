/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/OBCoBorrowerLimit.java,v 1.22 2006/09/11 01:38:47 nkumar Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

/**
 * This class represents a Limit record.
 * 
 * @author $Author: nkumar $
 * @version $Revision: 1.22 $
 * @since $Date: 2006/09/11 01:38:47 $ Tag: $Name: $
 */
public class OBCoBorrowerLimit implements ICoBorrowerLimit, Comparable {

	private static final long serialVersionUID = 7630063931251851878L;

	private long limitID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String limitRef = null;

	private IBookingLocation bookingLocation = null;

	private IBookingLocation outerLimitBookingLoc = null;

	private Amount approvedLimitAmount = null;

	private Amount activatedLimitAmount = null;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String status = null;

	private long outerLimitID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String outerLimitRef = null;

	private String hostStatus = null;

	private boolean limitActivatedInd = false;

	private boolean existingInd = false;

	private String LEReference;

	private long versionTime = 0;

	private float requiredSecurityCoverages = com.integrosys.cms.app.common.constant.ICMSConstant.FLOAT_INVALID_VALUE;

	private float coActualSecurityCoverage = com.integrosys.cms.app.common.constant.ICMSConstant.FLOAT_INVALID_VALUE;

	private boolean limitZerorised = false;

	private Date zerorisedDate = null;

	private String zerorisedReasons = null;

	private String productDesc = null;

	private boolean isChanged = false;

	private boolean isDAPError = false;

	private boolean isZerorisedChanged;

	private boolean isZerorisedDateChanged;

	private boolean isZerorisedReasonChanged;

	private ICMSCustomer customer;

	private ICMSCustomer coBorrowerCust;

	private ICMSCustomer mainBorrowerCust;

	private ICollateralAllocation[] collateralAllocations = null; // R1.5 CR35

	private Set collateralAllocationsSet;

	private String coLimitSecuredType = null;

	private String sourceId;

	/**
	 * Default Constructor
	 */
	public OBCoBorrowerLimit() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICoBorrowerLimit
	 */
	public OBCoBorrowerLimit(ICoBorrowerLimit value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * Get Activated Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getActivatedLimitAmount() {
		return activatedLimitAmount;
	}

	/**
	 * Get Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getApprovedLimitAmount() {
		return approvedLimitAmount;
	}

	/**
	 * Get Booking Location
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getBookingLocation() {
		return bookingLocation;
	}

	public float getCoActualSecurityCoverage() {
		return coActualSecurityCoverage;
	}

	/**
	 * @return Returns the coBorrowerCust.
	 */
	public ICMSCustomer getCoBorrowerCust() {
		return coBorrowerCust;
	}

	public String getCoLimitSecuredType() {

		return coLimitSecuredType;

	}

	/**
	 * Get All Collateral Allocations
	 * 
	 * @return ICollateralAllocation[]
	 */
	public ICollateralAllocation[] getCollateralAllocations() {
		return collateralAllocations;
	}

	public Set getCollateralAllocationsSet() {
		return collateralAllocationsSet;
	}

	// Getters

	/**
	 * @return Returns the customer.
	 */
	public ICMSCustomer getCustomer() {
		return customer;
	}

	/**
	 * Get Co-borrower customer ID
	 * 
	 * @return long
	 */
	public long getCustomerID() {
		return customerID;
	}

	/**
	 * Get the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @return boolean
	 */
	public boolean getExistingInd() {
		return existingInd;
	}

	/**
	 * Get the host record change status
	 * 
	 * @return String
	 */
	public String getHostStatus() {
		return hostStatus;
	}

	/**
	 * @return Returns the isChanged.
	 */
	public boolean getIsChanged() {
		return isChanged;
	}

	/**
	 * @return Returns the isDAPError.
	 */
	public boolean getIsDAPError() {
		return isDAPError;
	}

	/**
	 * @return Returns the isZerorisedChanged.
	 */
	public boolean getIsZerorisedChanged() {
		return isZerorisedChanged;
	}

	/**
	 * @return Returns the isZerorisedDateChanged.
	 */
	public boolean getIsZerorisedDateChanged() {
		return isZerorisedDateChanged;
	}

	/**
	 * @return Returns the isZerorisedReasonChanged.
	 */
	public boolean getIsZerorisedReasonChanged() {
		return isZerorisedReasonChanged;
	}

	/**
	 * Get coborrower limit's LE reference (Coborrower SCI LEID).
	 * 
	 * @return String
	 */
	public String getLEReference() {
		return LEReference;
	}

	/**
	 * Get the limit activated indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitActivatedInd() {
		return limitActivatedInd;
	}

	/**
	 * Get Limit ID
	 * 
	 * @return long
	 */
	public long getLimitID() {
		return limitID;
	}

	/*
	 * Get Limit Reference
	 * 
	 * @return String
	 */
	public String getLimitRef() {
		return limitRef;
	}

	/**
	 * @return Returns the limitZerorised.
	 */
	public boolean getLimitZerorised() {
		return limitZerorised;
	}

	/**
	 * @return Returns the mainBorrowerCust.
	 */
	public ICMSCustomer getMainBorrowerCust() {
		return mainBorrowerCust;
	}

	public ICollateralAllocation[] getNonDeletedCollateralAllocations() {
		ArrayList list = new ArrayList();
		if (collateralAllocations == null) {
			return null;
		}
		for (int ii = 0; ii < collateralAllocations.length; ii++) {
			if (!ICMSConstant.HOST_STATUS_DELETE.equals(collateralAllocations[ii].getHostStatus())) {
				list.add(collateralAllocations[ii]);
			}
		}
		return (ICollateralAllocation[]) list.toArray(new ICollateralAllocation[0]);
	}

	/**
	 * Get outer limit booking location.
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getOuterLimitBookingLoc() {
		return outerLimitBookingLoc;
	}

	/**
	 * Get Outer Limit ID
	 * 
	 * @return long
	 */
	public long getOuterLimitID() {
		return outerLimitID;
	}

	/**
	 * Get the outer limit ref
	 * 
	 * @return String
	 */
	public String getOuterLimitRef() {
		return outerLimitRef;
	}

	/**
	 * @return Returns the productDesc.
	 */
	public String getProductDesc() {
		return productDesc;
	}

	/**
	 * @return Returns the requiredSecurityCoverages.
	 */
	public float getRequiredSecurityCoverages() {
		return requiredSecurityCoverages;
	}

	public String getSourceId() {
		return sourceId;
	}

	/**
	 * Get status
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * @return Returns the zerorisedDate.
	 */
	public Date getZerorisedDate() {
		return zerorisedDate;
	}

	/**
	 * @return Returns the zerorisedReasons.
	 */
	public String getZerorisedReasons() {
		return zerorisedReasons;
	}

	/**
	 * Set Activated Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setActivatedLimitAmount(Amount value) {
		activatedLimitAmount = value;
	}

	/**
	 * Set Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setApprovedLimitAmount(Amount value) {
		approvedLimitAmount = value;
	}

	/**
	 * Set Booking Location
	 * 
	 * @param value is of type IBookingLocation
	 */
	public void setBookingLocation(IBookingLocation value) {
		bookingLocation = value;
	}

	public void setCoActualSecurityCoverage(float coActualSecurityCoverage) {
		this.coActualSecurityCoverage = coActualSecurityCoverage;
	}

	/**
	 * @param coBorrowerCust The coBorrowerCust to set.
	 */
	public void setCoBorrowerCust(ICMSCustomer coBorrowerCust) {
		this.coBorrowerCust = coBorrowerCust;
	}

	public void setCoLimitSecuredType(String coLimitSecuredType) {

		this.coLimitSecuredType = coLimitSecuredType;

	}

	// R1.5 CR35 ----------------
	/**
	 * Set All Collateral Allocations
	 * 
	 * @param value is of type ICollateralAllocation[]
	 */
	public void setCollateralAllocations(ICollateralAllocation[] value) {
		collateralAllocations = value;

		this.collateralAllocationsSet = (value == null) ? new HashSet(0) : new HashSet(Arrays.asList(value));
	}

	public void setCollateralAllocationsSet(Set collateralAllocationsSet) {
		this.collateralAllocationsSet = collateralAllocationsSet;

		this.collateralAllocations = (collateralAllocationsSet == null) ? null
				: (ICollateralAllocation[]) collateralAllocationsSet.toArray(new ICollateralAllocation[0]);
	}

	/**
	 * @param customer The customer to set.
	 */
	public void setCustomer(ICMSCustomer customer) {
		this.customer = customer;
	}

	/**
	 * Set Co-borrower customer ID
	 * 
	 * @param value is of type long
	 */
	public void setCustomerID(long value) {
		customerID = value;
	}

	/**
	 * Set the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @param value is of type boolean
	 */
	public void setExistingInd(boolean value) {
		existingInd = value;
	}

	/**
	 * Set the host record change status
	 * 
	 * @param value is of type String
	 */
	public void setHostStatus(String value) {
		hostStatus = value;
	}

	/**
	 * @param isChanged The isChanged to set.
	 */
	public void setIsChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	/**
	 * @param isDAPError The isDAPError to set.
	 */
	public void setIsDAPError(boolean isDAPError) {
		this.isDAPError = isDAPError;
	}

	/**
	 * @param isZerorisedChanged The isZerorisedChanged to set.
	 */
	public void setIsZerorisedChanged(boolean isZerorisedChanged) {
		this.isZerorisedChanged = isZerorisedChanged;
	}

	/**
	 * @param isZerorisedDateChanged The isZerorisedDateChanged to set.
	 */
	public void setIsZerorisedDateChanged(boolean isZerorisedDateChanged) {
		this.isZerorisedDateChanged = isZerorisedDateChanged;
	}

	/**
	 * @param isZerorisedReasonChanged The isZerorisedReasonChanged to set.
	 */
	public void setIsZerorisedReasonChanged(boolean isZerorisedReasonChanged) {
		this.isZerorisedReasonChanged = isZerorisedReasonChanged;
	}

	/**
	 * Set coborrower limit's LE reference (Coborrower SCI LEID).
	 * 
	 * @param lEReference coborrower LE reference
	 */
	public void setLEReference(String lEReference) {
		LEReference = lEReference;
	}

	/**
	 * Set the limit activated indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitActivatedInd(boolean value) {
		limitActivatedInd = value;
	}

	// Setters
	/**
	 * Set Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitID(long value) {
		limitID = value;
	}

	/*
	 * Set Limit Reference
	 * 
	 * @param value is of type String
	 */
	public void setLimitRef(String value) {
		limitRef = value;
	}

	/**
	 * @param limitZerorised The limitZerorised to set.
	 */
	public void setLimitZerorised(boolean limitZerorised) {
		this.limitZerorised = limitZerorised;
	}

	/**
	 * @param mainBorrowerCust The mainBorrowerCust to set.
	 */
	public void setMainBorrowerCust(ICMSCustomer mainBorrowerCust) {
		this.mainBorrowerCust = mainBorrowerCust;
	}

	/**
	 * Set outer limit booking location.
	 * 
	 * @param outerLimitBookingLoc of type IBookingLocation
	 */
	public void setOuterLimitBookingLoc(IBookingLocation outerLimitBookingLoc) {
		this.outerLimitBookingLoc = outerLimitBookingLoc;
	}

	/**
	 * Set Outer Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setOuterLimitID(long value) {
		outerLimitID = value;
	}

	/**
	 * Set the outer limit ref
	 * 
	 * @param value is of type String
	 */
	public void setOuterLimitRef(String value) {
		outerLimitRef = value;
	}

	/**
	 * @param productDesc The productDesc to set.
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	/**
	 * @param requiredSecurityCoverages The requiredSecurityCoverages to set.
	 */
	public void setRequiredSecurityCoverages(float requiredSecurityCoverages) {
		this.requiredSecurityCoverages = requiredSecurityCoverages;
	}

	public void setSourceId(String value) {
		this.sourceId = value;
	}

	/**
	 * Set status
	 * 
	 * @param value is of type String
	 */
	public void setStatus(String value) {
		status = value;
	}

	/**
	 * Set the version time
	 * 
	 * @param value is of type long
	 */
	public void setVersionTime(long value) {
		versionTime = value;
	}

	/**
	 * @param zerorisedDate The zerorisedDate to set.
	 */
	public void setZerorisedDate(Date zerorisedDate) {
		this.zerorisedDate = zerorisedDate;
	}

	/**
	 * @param zerorisedReasons The zerorisedReasons to set.
	 */
	public void setZerorisedReasons(String zerorisedReasons) {
		this.zerorisedReasons = zerorisedReasons;
	}

	// --------------------------

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public int compareTo(Object other) {
		long otherLimitID = (other == null) ? Long.MAX_VALUE : ((ICoBorrowerLimit) other).getLimitID();

		return (this.limitID == otherLimitID) ? 0 : ((this.limitID > otherLimitID) ? 1 : -1);
	}
}