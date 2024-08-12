/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ICollateralSubType.java,v 1.6 2003/08/15 06:00:40 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * This interface represents collateral subtype, such as general charge, post
 * dated cheque, personal guarantee, agricultural, etc.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/15 06:00:40 $ Tag: $Name: $
 */
public interface ICollateralSubType extends ICollateralType {
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
	 * Get collateral subtype Standardised Approach.
	 * 
	 * @return boolean
	 */
	public boolean getSubTypeStandardisedApproach();

	/**
	 * Set collateral subtype Standardised Approach.
	 * 
	 * @param subTypeStandardisedApproach is of type boolean
	 */
	public void setSubTypeStandardisedApproach(boolean subTypeStandardisedApproach);

	/**
	 * Get collateral subType Foundation IRB.
	 * 
	 * @return boolean
	 */
	public boolean getSubTypeFoundationIRB();

	/**
	 * Set collateral subType Foundation IRB.
	 * 
	 * @param subTypeStandardisedApproach is of type boolean
	 */
	public void setSubTypeFoundationIRB(boolean subTypeFoundationIRB);

	/**
	 * Get collateral subType Advanced IRB.
	 * 
	 * @return boolean
	 */
	public boolean getSubTypeAdvancedIRB();

	/**
	 * Set collateral subType Advanced IRB.
	 * 
	 * @param subTypeStandardisedApproach is of type boolean
	 */
	public void setSubTypeAdvancedIRB(boolean subTypeAdvancedIRB);

	/**
	 * Get max value.
	 * 
	 * @return double
	 */
	public double getMaxValue();

	/**
	 * Set max value.
	 * 
	 * @param maxValue of type double
	 */
	public void setMaxValue(double maxValue);

	/**
	 * Get security subtype group id.
	 * 
	 * @return long
	 */
	public long getGroupID();

	/**
	 * Set security subtype group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID);

	/**
	 * Get the status of the collateral subtype.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set the status of the collateral subtype.
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status);

	/**
	 * Get the version of the collateral subtype.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Set the version of the collateral subtype.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);
}
