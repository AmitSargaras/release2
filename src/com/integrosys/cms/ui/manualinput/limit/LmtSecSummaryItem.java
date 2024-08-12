/*
 * Created on 2007-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LmtSecSummaryItem implements Serializable {
	private String collateralId;

	private String sciSecurityId;

	private String securityType;

	private String securitySubType;
	
	private String lmtSecurityCoverage;
	
	private String cpsSecurityId;

	private String headerClass;

	private String rowClass;

	/**
	 * @return Returns the collateralId.
	 */
	public String getCollateralId() {
		return collateralId;
	}

	/**
	 * @param collateralId The collateralId to set.
	 */
	public void setCollateralId(String collateralId) {
		this.collateralId = collateralId;
	}

	/**
	 * @return Returns the sciSecurityId.
	 */
	public String getSciSecurityId() {
		return sciSecurityId;
	}

	/**
	 * @param sciSecurityId The sciSecurityId to set.
	 */
	public void setSciSecurityId(String sciSecurityId) {
		this.sciSecurityId = sciSecurityId;
	}

	/**
	 * @return Returns the securitySubType.
	 */
	public String getSecuritySubType() {
		return securitySubType;
	}

	/**
	 * @param securitySubType The securitySubType to set.
	 */
	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	/**
	 * @return Returns the securityType.
	 */
	public String getSecurityType() {
		return securityType;
	}

	/**
	 * @param securityType The securityType to set.
	 */
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
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

	/**
	 * @return Returns the headerClass.
	 */
	public String getHeaderClass() {
		return headerClass;
	}

	/**
	 * @param headerClass The headerClass to set.
	 */
	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}

	/**
	 * @return Returns the rowClass.
	 */
	public String getRowClass() {
		return rowClass;
	}

	/**
	 * @param rowClass The rowClass to set.
	 */
	public void setRowClass(String rowClass) {
		this.rowClass = rowClass;
	}
}
