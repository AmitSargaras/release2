/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;

/**
 * This interface represents collateral Asset Life.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ICollateralAssetLife extends Serializable {
	/**
	 * Get collateral subtype code.
	 * 
	 * @return String
	 */
	public String getSubTypeCode();

	/**
	 * Set collateral subtype code.
	 * 
	 * @param subTypeCode is of type String
	 */
	public void setSubTypeCode(String subTypeCode);

	/**
	 * Get collateral subtype name.
	 * 
	 * @return String
	 */
	public String getSubTypeName();

	/**
	 * Set collateral subtype name.
	 * 
	 * @param subTypeName is of type String
	 */
	public void setSubTypeName(String subTypeName);

	/**
	 * Get collateral subtype description.
	 * 
	 * @return String
	 */
	public String getSubTypeDesc();

	/**
	 * Set collateral subtype description.
	 * 
	 * @param subTypeDesc is of type String
	 */
	public void setSubTypeDesc(String subTypeDesc);

	/**
	 * Get life span value.
	 * 
	 * @return int
	 */
	public int getLifeSpan();

	/**
	 * Set life span value.
	 * 
	 * @param lifeSpanValue of type int
	 */
	public void setLifeSpan(int lifeSpanValue);

	/**
	 * Get security asset life group id.
	 * 
	 * @return long
	 */
	public long getGroupID();

	/**
	 * Set security asset life group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID);

	/**
	 * Get the status of the collateral asset life.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set the status of the collateral asset life.
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status);

	/**
	 * Get the version of the collateral asset life.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Set the version of the collateral asset life.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);
}
