/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/OBCCDocumentLocation.java,v 1.1 2004/02/17 02:12:02 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//app
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * Class that provides the implementation for ICCDocumentLocation
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:02 $ Tag: $Name: $
 */
public class OBCCDocumentLocation implements ICCDocumentLocation {
	private long docLocationID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String docLocationCategory = null;

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private IBookingLocation originatingLocation = null;

	private String remarks = null;

	private String legalRef = null;

	private String legalName = null;

	private String customerType = null;

	private boolean isDeletedInd = false;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Get the documentation location ID
	 * @return long - the documentation location ID
	 */
	public long getDocLocationID() {
		return this.docLocationID;
	}

	/**
	 * Get the documentation location category
	 * @return String - the documentation location category
	 */
	public String getDocLocationCategory() {
		return this.docLocationCategory;
	}

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	/**
	 * Get the customer ID
	 * @return long - the customer ID
	 */
	public long getCustomerID() {
		return this.customerID;
	}

	/**
	 * Get the documentation originating location
	 * @return IBookingLocation - the documentation originating location
	 */
	public IBookingLocation getOriginatingLocation() {
		return this.originatingLocation;
	}

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * Get the deleted indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
	}

	/*
	 * Get the legal Ref
	 * 
	 * @return String - the legal Ref
	 */
	public String getLegalRef() {
		return this.legalRef;
	}

	/**
	 * Get the legal Name
	 * @return String - the legal name
	 */
	public String getLegalName() {
		return this.legalName;
	}

	/**
	 * Get the customer type
	 * @return String - the customer type
	 */
	public String getCustomerType() {
		return this.customerType;
	}

	/**
	 * Get the version time
	 * @return long - the version time
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * Set the documentation location
	 * @param aDocLocationID of long type
	 */
	public void setDocLocationID(long aDocLocationID) {
		this.docLocationID = aDocLocationID;
	}

	/**
	 * Set the documentation location category
	 * @param aDocLocationCategory of String type
	 */
	public void setDocLocationCategory(String aDocLocationCategory) {
		this.docLocationCategory = aDocLocationCategory;
	}

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	/**
	 * Set the customer ID param aCustomerID of long type
	 */
	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
	}

	/**
	 * Set the documentation originating location
	 * @param anOriginatingLocation of IBookingLocation type
	 */
	public void setOriginatingLocation(IBookingLocation anOriginatingLocation) {
		this.originatingLocation = anOriginatingLocation;
	}

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks) {
		this.remarks = aRemarks;
	}

	/**
	 * Set the deleted indicator
	 * @param anIsDeletedInd - true if it is deleted and false otherwisef
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	/**
	 * Set the legal Ref
	 * @param aLegalRef of String type
	 */
	public void setLegalRef(String aLegalRef) {
		this.legalRef = aLegalRef;
	}

	/**
	 * Set the legal name
	 * @param aLegalName of String type
	 */
	public void setLegalName(String aLegalName) {
		this.legalName = aLegalName;
	}

	/**
	 * Set the customer type
	 * @param aCustomerType of String type
	 */
	public void setCustomerType(String aCustomerType) {
		this.customerType = aCustomerType;
	}

	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}
}
