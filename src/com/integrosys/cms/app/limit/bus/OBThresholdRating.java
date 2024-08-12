/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Model to hold threshold rating.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBThresholdRating extends OBExternalRating implements IThresholdRating, Serializable {

	private long thresholdRatingID = ICMSConstant.LONG_INVALID_VALUE;

	private long agreementID = ICMSConstant.LONG_INVALID_VALUE;

	private Amount thresholdAmt;

	private String status = null;

	private long versionTime = ICMSConstant.LONG_INVALID_VALUE;

	public OBThresholdRating() {
		super();
	}

	public OBThresholdRating(String ratingType) {
		super(ratingType);
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#getThresholdRatingID
	 */
	public long getThresholdRatingID() {
		return thresholdRatingID;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#setThresholdRatingID
	 */
	public void setThresholdRatingID(long value) {
		thresholdRatingID = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#getAgreementID
	 */
	public long getAgreementID() {
		return agreementID;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#setAgreementID
	 */
	public void setAgreementID(long value) {
		agreementID = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#getThresholdAmount
	 */
	public Amount getThresholdAmount() {
		return thresholdAmt;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#setThresholdAmount
	 */
	public void setThresholdAmount(Amount value) {
		thresholdAmt = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#getStatus
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#setStatus
	 */
	public void setStatus(String value) {
		status = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#getVersionTime
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#setVersionTime
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
