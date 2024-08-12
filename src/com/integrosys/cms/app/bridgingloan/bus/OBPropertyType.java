package com.integrosys.cms.app.bridgingloan.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBPropertyType implements IPropertyType {
	private long projectID = ICMSConstant.LONG_INVALID_VALUE;

	private long propertyTypeID = ICMSConstant.LONG_INVALID_VALUE;

	private String propertyType;

	private String propertyTypeOthers;

	private int noOfUnits = ICMSConstant.INT_INVALID_VALUE;

	private String remarks;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd;

	/**
	 * Default Constructor
	 */
	public OBPropertyType() {
	}

	public long getProjectID() {
		return projectID;
	}

	public void setProjectID(long projectID) {
		this.projectID = projectID;
	}

	public long getPropertyTypeID() {
		return propertyTypeID;
	}

	public void setPropertyTypeID(long propertyTypeID) {
		this.propertyTypeID = propertyTypeID;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyTypeOthers() {
		return propertyTypeOthers;
	}

	public void setPropertyTypeOthers(String propertyTypeOthers) {
		this.propertyTypeOthers = propertyTypeOthers;
	}

	public int getNoOfUnits() {
		return noOfUnits;
	}

	public void setNoOfUnits(int noOfUnits) {
		this.noOfUnits = noOfUnits;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public boolean getIsDeletedInd() {
		return isDeletedInd;
	}

	public void setIsDeletedInd(boolean isDeletedInd) {
		this.isDeletedInd = isDeletedInd;
	}
}