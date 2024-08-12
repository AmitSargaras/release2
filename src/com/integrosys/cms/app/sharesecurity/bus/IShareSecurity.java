/*
 * Created on Mar 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.sharesecurity.bus;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface IShareSecurity extends Serializable {
	/**
	 * @return Returns the cmsCollateralId.
	 */
	public long getCmsCollateralId();

	/**
	 * @param cmsCollateralId The cmsCollateralId to set.
	 */
	public void setCmsCollateralId(long cmsCollateralId);

	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public Date getLastUpdatedTime();

	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Date lastUpdatedTime);

	/**
	 * @return Returns the shareSecurityId.
	 */
	public long getShareSecurityId();

	/**
	 * @param shareSecurityId The shareSecurityId to set.
	 */
	public void setShareSecurityId(long shareSecurityId);

	/**
	 * @return Returns the sourceId.
	 */
	public String getSourceId();

	/**
	 * @param sourceId The sourceId to set.
	 */
	public void setSourceId(String sourceId);

	/**
	 * @return Returns the sourceSecurityId.
	 */
	public String getSourceSecurityId();

	/**
	 * @param sourceSecurityId The sourceSecurityId to set.
	 */
	public void setSourceSecurityId(String sourceSecurityId);

	/**
	 * @return Returns the status.
	 */
	public String getStatus();

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status);

	public String getSecuritySubTypeId();

	public void setSecuritySubTypeId(String securitySubTypeId);
}
