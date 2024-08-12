/*
 * Created on Apr 4, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PledgorSummaryItem implements Serializable {
	private String pledgorSecLinkId;

	private String customerName;

	private String leId;

	private String leIdType;

	private String headerClass;

	private String rowClass;

	private String idType;

	private String idNo;

	private String secPledgorRelnship;

	/**
	 * @return Returns the customerName.
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName The customerName to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	 * @return Returns the leId.
	 */
	public String getLeId() {
		return leId;
	}

	/**
	 * @param leId The leId to set.
	 */
	public void setLeId(String leId) {
		this.leId = leId;
	}

	/**
	 * @return Returns the leIdType.
	 */
	public String getLeIdType() {
		return leIdType;
	}

	/**
	 * @param leIdType The leIdType to set.
	 */
	public void setLeIdType(String leIdType) {
		this.leIdType = leIdType;
	}

	/**
	 * @return Returns the pledgorSecLinkId.
	 */
	public String getPledgorSecLinkId() {
		return pledgorSecLinkId;
	}

	/**
	 * @param pledgorSecLinkId The pledgorSecLinkId to set.
	 */
	public void setPledgorSecLinkId(String pledgorSecLinkId) {
		this.pledgorSecLinkId = pledgorSecLinkId;
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

	/**
	 * @return Returns the idType.
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param value The idType to set.
	 */
	public void setIdType(String value) {
		this.idType = value;
	}

	/**
	 * @return Returns the idNo.
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param value The idNo to set.
	 */
	public void setIdNo(String value) {
		this.idNo = value;
	}

	/**
	 * @return Returns the secPledgorRelnship.
	 */
	public String getSecPledgorRelnship() {
		return secPledgorRelnship;
	}

	/**
	 * @param value The secPledgorRelnship to set.
	 */
	public void setSecPledgorRelnship(String value) {
		this.secPledgorRelnship = value;
	}
}
