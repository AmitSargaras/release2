/*
 * Created on Apr 4, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.customer.bus;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MILeSearchCriteria implements Serializable {
	private String sourceId;

	private String leId;

	private String customerName;

	private String iDType;

	private String iDNo;

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
	 * @return Returns the iDType.
	 */
	public String getIDType() {
		return iDType;
	}

	/**
	 * @param iDType The iDType to set.
	 */
	public void setIDType(String iDType) {
		this.iDType = iDType;
	}

	/**
	 * @return Returns the iDNo.
	 */
	public String getIDNo() {
		return iDNo;
	}

	/**
	 * @param iDNo The iDNo to set.
	 */
	public void setIDNo(String iDNo) {
		this.iDNo = iDNo;
	}
}
