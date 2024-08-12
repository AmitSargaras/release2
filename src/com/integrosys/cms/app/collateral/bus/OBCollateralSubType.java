/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateralSubType.java,v 1.6 2003/08/15 06:00:40 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This interface represents collateral subtype, such as general charge, post
 * dated cheque, personal guarantee, agricultural, etc.
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @since 2003/08/15
 */
public class OBCollateralSubType extends OBCollateralType implements ICollateralSubType, Comparable {

	private static final long serialVersionUID = 8593238074275405628L;

	private String subTypeCode;

	private String subTypeDesc;

	private boolean subTypeStandardisedApproach;

	private boolean subTypeFoundationIRB;

	private boolean subTypeAdvancedIRB;

	private String subTypeName;

	private double maxValue;

	private long groupID;

	private String status;

	private long versionTime;

	/**
	 * Default Constructor.
	 */
	public OBCollateralSubType() {
		super();
	}

	/**
	 * Construct the collateral subtype given the code.
	 * 
	 * @param subTypeCode of type String
	 */
	public OBCollateralSubType(String subTypeCode) {
		this();
		this.subTypeCode = subTypeCode;
	}

	/**
	 * Construct the collateral subtype given the type code and subtype code
	 * 
	 * @param typeCode collateral type code
	 * @param subTypeCode collateral subtype code
	 */
	public OBCollateralSubType(String typeCode, String subTypeCode) {
		setTypeCode(typeCode);
		this.subTypeCode = subTypeCode;
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICollateralSubType
	 */
	public OBCollateralSubType(ICollateralSubType obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get collateral subtype code.
	 * 
	 * @return String
	 */
	public String getSubTypeCode() {
		return subTypeCode;
	}

	/**
	 * Set collateral subtype code.
	 * 
	 * @param subTypeCode is of type String
	 */
	public void setSubTypeCode(String subTypeCode) {
		this.subTypeCode = subTypeCode;
	}

	/**
	 * Get collateral subtype description.
	 * 
	 * @return String
	 */
	public String getSubTypeDesc() {
		return subTypeDesc;
	}

	/**
	 * Set collateral subtype description.
	 * 
	 * @param subTypeDesc is of type String
	 */
	public void setSubTypeDesc(String subTypeDesc) {
		this.subTypeDesc = subTypeDesc;
	}

	/**
	 * Helper method to get the invault indicator
	 * @return boolean
	 */
	public boolean getSubTypeStandardisedApproach() {
		return subTypeStandardisedApproach;
	}

	/**
	 * Helper method to set invault indicator
	 * @param subTypeStandardisedApproach - boolean
	 */
	public void setSubTypeStandardisedApproach(boolean subTypeStandardisedApproach) {
		this.subTypeStandardisedApproach = subTypeStandardisedApproach;
	}

	/**
	 * Helper method to get the invault indicator
	 * @return boolean
	 */
	public boolean getSubTypeFoundationIRB() {
		return subTypeFoundationIRB;
	}

	/**
	 * Helper method to set invault indicator
	 * @param subTypeFoundationIRB - boolean
	 */
	public void setSubTypeFoundationIRB(boolean subTypeFoundationIRB) {
		this.subTypeFoundationIRB = subTypeFoundationIRB;
	}

	/**
	 * Helper method to get the invault indicator
	 * @return boolean
	 */
	public boolean getSubTypeAdvancedIRB() {
		return subTypeAdvancedIRB;
	}

	/**
	 * Helper method to set invault indicator
	 * @param subTypeAdvancedIRB - boolean
	 */
	public void setSubTypeAdvancedIRB(boolean subTypeAdvancedIRB) {
		this.subTypeAdvancedIRB = subTypeAdvancedIRB;
	}

	/**
	 * Get collateral subtype name.
	 * 
	 * @return String
	 */
	public String getSubTypeName() {
		return subTypeName;
	}

	/**
	 * Set collateral subtype name.
	 * 
	 * @param subTypeName is of type String
	 */
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	/**
	 * Get max value.
	 * 
	 * @return double
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * Set max value.
	 * 
	 * @param maxValue of type double
	 */
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * Get security subtype group id.
	 * 
	 * @return long
	 */
	public long getGroupID() {
		return groupID;
	}

	/**
	 * Set security subtype group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * Get the status of the collateral subtype.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the status of the collateral subtype.
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get the version of the collateral subtype.
	 * 
	 * @return long
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * Set the version of the collateral subtype.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBCollateralSubType [");
		buf.append("typeCode=");
		buf.append(getTypeCode());
		buf.append(", typeName=");
		buf.append(getTypeName());
		buf.append(", subTypeCode=");
		buf.append(subTypeCode);
		buf.append(", subTypeDesc=");
		buf.append(subTypeDesc);
		buf.append(", subTypeName=");
		buf.append(subTypeName);
		buf.append(", status=");
		buf.append(status);
		buf.append("]");
		return buf.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subTypeCode == null) ? 0 : subTypeCode.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OBCollateralSubType other = (OBCollateralSubType) obj;
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		}
		else if (!status.equals(other.status)) {
			return false;
		}
		if (subTypeCode == null) {
			if (other.subTypeCode != null) {
				return false;
			}
		}
		else if (!subTypeCode.equals(other.subTypeCode)) {
			return false;
		}

		return true;
	}

	public int compareTo(Object other) {
		String otherSubTypeName = (other == null) ? null : ((ICollateralSubType) other).getSubTypeName();

		if (this.subTypeName == null) {
			return (otherSubTypeName == null) ? 0 : -1;
		}

		return (otherSubTypeName == null) ? 1 : this.subTypeName.compareTo(otherSubTypeName);
	}
}