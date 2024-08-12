/*
 * Created on Mar 13, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LimitListSecItemBase implements Serializable {
	private String securityId;

	private String secTypeName;

	private String secSubtypeName;

	private String secLocDesc;

	private String secOrgDesc;
	
	private String lmtSecurityCoverage;
	
	private String cpsSecurityId;

	/**
	 * @return Returns the secLocDesc.
	 */
	public String getSecLocDesc() {
		return secLocDesc;
	}

	/**
	 * @param secLocDesc The secLocDesc to set.
	 */
	public void setSecLocDesc(String secLocDesc) {
		this.secLocDesc = secLocDesc;
	}

	/**
	 * @return Returns the secOrgDesc.
	 */
	public String getSecOrgDesc() {
		return secOrgDesc;
	}

	/**
	 * @param secOrgDesc The secOrgDesc to set.
	 */
	public void setSecOrgDesc(String secOrgDesc) {
		this.secOrgDesc = secOrgDesc;
	}

	/**
	 * @return Returns the secSubtypeName.
	 */
	public String getSecSubtypeName() {
		return secSubtypeName;
	}

	/**
	 * @param secSubtypeName The secSubtypeName to set.
	 */
	public void setSecSubtypeName(String secSubtypeName) {
		this.secSubtypeName = secSubtypeName;
	}

	/**
	 * @return Returns the secTypeName.
	 */
	public String getSecTypeName() {
		return secTypeName;
	}

	/**
	 * @param secTypeName The secTypeName to set.
	 */
	public void setSecTypeName(String secTypeName) {
		this.secTypeName = secTypeName;
	}

	/**
	 * @return Returns the securityId.
	 */
	public String getSecurityId() {
		return securityId;
	}

	/**
	 * @param securityId The securityId to set.
	 */
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getLmtSecurityCoverage() {
		return lmtSecurityCoverage;
	}

	public void setLmtSecurityCoverage(String lmtSecurityCoverage) {
		this.lmtSecurityCoverage = lmtSecurityCoverage;
	}

	public String getCpsSecurityId() {
		return cpsSecurityId;
	}

	public void setCpsSecurityId(String cpsSecurityId) {
		this.cpsSecurityId = cpsSecurityId;
	}
	
	
}
