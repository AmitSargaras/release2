/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface represents a common values for Marketability Factor template.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ICommonMFTemplate extends Serializable {
	/**
	 * Get MF Template ID.
	 * 
	 * @return long
	 */
	public long getMFTemplateID();

	/**
	 * Set MF Template ID.
	 * 
	 * @param mFTemplateID of type long
	 */
	public void setMFTemplateID(long mFTemplateID);

	/**
	 * Get MF template name.
	 * 
	 * @return String
	 */
	public String getMFTemplateName();

	/**
	 * Set MF template name.
	 * 
	 * @param mFTemplateName of type String
	 */
	public void setMFTemplateName(String mFTemplateName);

	/**
	 * Get last update date.
	 * 
	 * @return Date
	 */
	public Date getLastUpdateDate();

	/**
	 * Set last update date.
	 * 
	 * @param lastUpdateDate of type Date
	 */
	public void setLastUpdateDate(Date lastUpdateDate);

	/**
	 * Get status
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set status
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Get the version time.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Set the version time.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);

}
