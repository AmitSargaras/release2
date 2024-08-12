/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBKYC.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a "Know Your Customer" or "KYC" record.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBKYC implements IKYC {
	private long _id = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _origLocation = null;

	private String _riskRating = null;

	private Date _lastReviewDate = null;

	private Date _nextReviewDate = null;

	private boolean _isCDDSubmit = false;

	private boolean _isKYCSubmit = false;

	private boolean _isGICSubmit = false;

	private boolean _isExceptionApprove = false;

	/**
	 * Default Constructor
	 */
	public OBKYC() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IKYC
	 */
	public OBKYC(IKYC value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get the KYC ID
	 * 
	 * @return long
	 */
	public long getKYCID() {
		return _id;
	}

	/**
	 * Get the document originating location
	 * 
	 * @return String
	 */
	public String getOriginatingLocation() {
		return _origLocation;
	}

	/**
	 * Get Risk Rating
	 * 
	 * @return String
	 */
	public String getRiskRating() {
		return _riskRating;
	}

	/**
	 * Get Last Review Date
	 * 
	 * @return Date
	 */
	public Date getLastReviewDate() {
		return _lastReviewDate;
	}

	/**
	 * Get Next Review Date
	 * 
	 * @return Date
	 */
	public Date getNextReviewDate() {
		return _nextReviewDate;
	}

	/**
	 * Get CDD Doc Submit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getCDDSubmitInd() {
		return _isCDDSubmit;
	}

	/**
	 * Get KYC Doc Submit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getKYCSubmitInd() {
		return _isKYCSubmit;
	}

	/**
	 * Get GIC Doc Submit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getGICSubmitInd() {
		return _isGICSubmit;
	}

	/**
	 * Get Exceptional Approval Indicator
	 * 
	 * @return boolean
	 */
	public boolean getExceptionalApprovalInd() {
		return _isExceptionApprove;
	}

	// Setters

	/**
	 * Set the KYC ID
	 * 
	 * @param value is of type long
	 */
	public void setKYCID(long value) {
		_id = value;
	}

	/**
	 * Set the document originating location
	 * 
	 * @param value is of type String
	 */
	public void setOriginatingLocation(String value) {
		_origLocation = value;
	}

	/**
	 * Set Risk Rating
	 * 
	 * @param value is of type String
	 */
	public void setRiskRating(String value) {
		_riskRating = value;
	}

	/**
	 * Set Last Review Date
	 * 
	 * @param value is of type Date
	 */
	public void setLastReviewDate(Date value) {
		_lastReviewDate = value;
	}

	/**
	 * Set Next Review Date
	 * 
	 * @param value is of type Date
	 */
	public void setNextReviewDate(Date value) {
		_nextReviewDate = null;
	}

	/**
	 * Set CDD Doc Submit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCDDSubmitInd(boolean value) {
		_isCDDSubmit = false;
	}

	/**
	 * Set KYC Doc Submit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setKYCSubmitInd(boolean value) {
		_isKYCSubmit = false;
	}

	/**
	 * Set GIC Doc Submit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setGICSubmitInd(boolean value) {
		_isGICSubmit = false;
	}

	/**
	 * Set Exceptional Approval Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setExceptionalApprovalInd(boolean value) {
		_isExceptionApprove = false;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}