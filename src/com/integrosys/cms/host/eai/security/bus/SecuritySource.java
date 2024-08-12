package com.integrosys.cms.host.eai.security.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: 31-Mar-2007 Time: 23:22:32 To
 * change this template use File | Settings | File Templates.
 */
public class SecuritySource implements java.io.Serializable {
	private long cMSSecuritySourceId = ICMSConstant.LONG_INVALID_VALUE;

	private long cMSSecurityId = ICMSConstant.LONG_INVALID_VALUE;

	private String sourceSecurityId;

	private String sourceId;

	private String status;

	private Date lastUpdateDate;

	private String securitySubTypeId;

	public SecuritySource() {
		this.status = ICMSConstant.STATE_ACTIVE;
		this.lastUpdateDate = new Date();
	}

	public long getCMSSecuritySourceId() {
		return cMSSecuritySourceId;
	}

	public void setCMSSecuritySourceId(long cMSSecuritySourceId) {
		this.cMSSecuritySourceId = cMSSecuritySourceId;
	}

	public long getCMSSecurityId() {
		return cMSSecurityId;
	}

	public void setCMSSecurityId(long cMSSecurityId) {
		this.cMSSecurityId = cMSSecurityId;
	}

	public String getSourceSecurityId() {
		return sourceSecurityId;
	}

	public void setSourceSecurityId(String sourceSecurityId) {
		this.sourceSecurityId = sourceSecurityId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getSecuritySubTypeId() {
		return securitySubTypeId;
	}

	public void setSecuritySubTypeId(String securitySubTypeId) {
		this.securitySubTypeId = securitySubTypeId;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
