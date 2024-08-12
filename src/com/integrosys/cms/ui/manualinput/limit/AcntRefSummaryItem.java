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
public class AcntRefSummaryItem implements Serializable {
	private String accountId;

	private String sourceSystemCountry;

	private String sourceSystemName;

	private String accountNo;

	private String acctClassification;

	private String headerClass;

	private String rowClass;

	/**
	 * @return Returns the accountId.
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId The accountId to set.
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return Returns the sourceSystemCountry.
	 */
	public String getSourceSystemCountry() {
		return sourceSystemCountry;
	}

	/**
	 * @param sourceSystemCountry The sourceSystemCountry to set.
	 */
	public void setSourceSystemCountry(String sourceSystemCountry) {
		this.sourceSystemCountry = sourceSystemCountry;
	}

	/**
	 * @return Returns the sourceSystemName.
	 */
	public String getSourceSystemName() {
		return sourceSystemName;
	}

	/**
	 * @param sourceSystemName The sourceSystemName to set.
	 */
	public void setSourceSystemName(String sourceSystemName) {
		this.sourceSystemName = sourceSystemName;
	}

	/**
	 * @return Returns the acctClassification.
	 */
	public String getAcctClassification() {
		return acctClassification;
	}

	/**
	 * @param acctClassification The acctClassification to set.
	 */
	public void setAcctClassification(String acctClassification) {
		this.acctClassification = acctClassification;
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
