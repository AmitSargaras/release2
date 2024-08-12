/*
 * Created on Jun 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class OBApportionSummaryLstItem implements Serializable {
	private String secApportionmentId;

	private String priorityRank;

	private String leIdSubProfileIdConcat;

	private String leName;

	private String limitId;

	private String apportionedAmount;

	private String apportionedAmtPrev;

	private String refId;

	private String headerClass;

	private String rowClass;

	private String hyperLinkLabel;

	/**
	 * @return Returns the apportionedAmount.
	 */
	public String getApportionedAmount() {
		return apportionedAmount;
	}

	/**
	 * @param apportionedAmount The apportionedAmount to set.
	 */
	public void setApportionedAmount(String apportionedAmount) {
		this.apportionedAmount = apportionedAmount;
	}

	/**
	 * @return Returns the apportionedAmtPrev.
	 */
	public String getApportionedAmtPrev() {
		return apportionedAmtPrev;
	}

	/**
	 * @param apportionedAmtPrev The apportionedAmtPrev to set.
	 */
	public void setApportionedAmtPrev(String apportionedAmtPrev) {
		this.apportionedAmtPrev = apportionedAmtPrev;
	}

	/**
	 * @return Returns the hyperLinkTitle.
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * @param hyperLinkTitle The hyperLinkTitle to set.
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}

	/**
	 * @return Returns the leIdSubProfileIdConcat.
	 */
	public String getLeIdSubProfileIdConcat() {
		return leIdSubProfileIdConcat;
	}

	/**
	 * @param leIdSubProfileIdConcat The leIdSubProfileIdConcat to set.
	 */
	public void setLeIdSubProfileIdConcat(String leIdSubProfileIdConcat) {
		this.leIdSubProfileIdConcat = leIdSubProfileIdConcat;
	}

	/**
	 * @return Returns the leName.
	 */
	public String getLeName() {
		return leName;
	}

	/**
	 * @param leName The leName to set.
	 */
	public void setLeName(String leName) {
		this.leName = leName;
	}

	/**
	 * @return Returns the limitId.
	 */
	public String getLimitId() {
		return limitId;
	}

	/**
	 * @param limitId The limitId to set.
	 */
	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	/**
	 * @return Returns the priorityRank.
	 */
	public String getPriorityRank() {
		return priorityRank;
	}

	/**
	 * @param priorityRank The priorityRank to set.
	 */
	public void setPriorityRank(String priorityRank) {
		this.priorityRank = priorityRank;
	}

	/**
	 * @return Returns the secApportionmentId.
	 */
	public String getSecApportionmentId() {
		return secApportionmentId;
	}

	/**
	 * @param secApportionmentId The secApportionmentId to set.
	 */
	public void setSecApportionmentId(String secApportionmentId) {
		this.secApportionmentId = secApportionmentId;
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
	 * @return Returns the hyperLinkLabel.
	 */
	public String getHyperLinkLabel() {
		return hyperLinkLabel;
	}

	/**
	 * @param hyperLinkLabel The hyperLinkLabel to set.
	 */
	public void setHyperLinkLabel(String hyperLinkLabel) {
		this.hyperLinkLabel = hyperLinkLabel;
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
