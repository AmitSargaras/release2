/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This object represents interest rate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBInterestRate implements IInterestRate {
	private Long intRateID;

	private String intRateType;

	private Date intRateDate;

	private Double intRatePercent;

	private long groupID;

	private long versionTime;

	/**
	 * Default Constructor.
	 */
	public OBInterestRate() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IInterestRate
	 */
	public OBInterestRate(IInterestRate obj) {
		this();
		AccessorUtil.copyValue(obj, this);

	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#getIntRateID
	 */
	public Long getIntRateID() {
		return intRateID;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#setIntRateID
	 */
	public void setIntRateID(Long intRateID) {
		this.intRateID = intRateID;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#getIntRateType
	 */
	public String getIntRateType() {
		return intRateType;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#setIntRateType
	 */
	public void setIntRateType(String intRateType) {
		this.intRateType = intRateType;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#getIntRateDate
	 */
	public Date getIntRateDate() {
		return intRateDate;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#setIntRateDate
	 */
	public void setIntRateDate(Date intRateDate) {
		this.intRateDate = intRateDate;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#getIntRatePercent
	 */
	public Double getIntRatePercent() {
		return intRatePercent;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#setIntRatePercent
	 */
	public void setIntRatePercent(Double intRatePercent) {
		this.intRatePercent = intRatePercent;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#getGroupID
	 */
	public long getGroupID() {
		return groupID;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#setGroupID
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#getVersionTime
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#setVersionTime
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBInterestRate)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}