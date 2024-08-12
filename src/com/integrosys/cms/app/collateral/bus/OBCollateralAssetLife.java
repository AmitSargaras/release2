/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This object represents collateral asset life.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBCollateralAssetLife implements ICollateralAssetLife {
	private String subTypeCode;

	private String subTypeDesc;

	private String subTypeName;

	private int lifeSpan;

	private long groupID;

	private String status;

	private long versionTime;

	/**
	 * Default Constructor.
	 */
	public OBCollateralAssetLife() {
		super();
	}

	/**
	 * Construct the collateral asset life given the sub type code.
	 * 
	 * @param subTypeCode of type String
	 */
	public OBCollateralAssetLife(String subTypeCode) {
		this();
		this.subTypeCode = subTypeCode;

	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICollateralAssetLife
	 */
	public OBCollateralAssetLife(ICollateralAssetLife obj) {
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
	 * Set life span value.
	 * 
	 * @return int
	 */
	public int getLifeSpan() {
		return lifeSpan;
	}

	/**
	 * Set life span value.
	 * 
	 * @param lifeSpan of type int
	 */
	public void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	/**
	 * Get security asset life group id.
	 * 
	 * @return long
	 */
	public long getGroupID() {
		return groupID;
	}

	/**
	 * Set security asset life group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * Get the status of the collateral asset life.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the status of the collateral asset life.
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get the version of the collateral asset life.
	 * 
	 * @return long
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * Set the version of the collateral asset life.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBCollateralAssetLife)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}