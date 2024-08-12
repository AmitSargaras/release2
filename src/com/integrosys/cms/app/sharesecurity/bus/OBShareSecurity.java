/*
 * Created on Mar 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.sharesecurity.bus;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class OBShareSecurity implements IShareSecurity {
	private long shareSecurityId = ICMSConstant.LONG_INVALID_VALUE;

	private long cmsCollateralId = ICMSConstant.LONG_INVALID_VALUE;

	private String sourceSecurityId;

	private String securitySubTypeId;

	private String sourceId;

	private String status;

	private Date lastUpdatedTime;

	/**
	 * @return Returns the cmsCollateralId.
	 */
	public long getCmsCollateralId() {
		return cmsCollateralId;
	}

	/**
	 * @param cmsCollateralId The cmsCollateralId to set.
	 */
	public void setCmsCollateralId(long cmsCollateralId) {
		this.cmsCollateralId = cmsCollateralId;
	}

	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return Returns the shareSecurityId.
	 */
	public long getShareSecurityId() {
		return shareSecurityId;
	}

	/**
	 * @param shareSecurityId The shareSecurityId to set.
	 */
	public void setShareSecurityId(long shareSecurityId) {
		this.shareSecurityId = shareSecurityId;
	}

	/**
	 * @return Returns the sourceId.
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId The sourceId to set.
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return Returns the sourceSecurityId.
	 */
	public String getSourceSecurityId() {
		return sourceSecurityId;
	}

	/**
	 * @param sourceSecurityId The sourceSecurityId to set.
	 */
	public void setSourceSecurityId(String sourceSecurityId) {
		this.sourceSecurityId = sourceSecurityId;
	}

	public String getSecuritySubTypeId() {
		return this.securitySubTypeId;
	}

	public void setSecuritySubTypeId(String securitySubTypeId) {
		this.securitySubTypeId = securitySubTypeId;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
