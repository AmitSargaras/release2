/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/IKYC.java,v 1.2 2003/06/17 05:08:10 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

/**
 * This interface represents a "Know Your Customer" or "KYC" record.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/17 05:08:10 $ Tag: $Name: $
 */
public interface IKYC extends java.io.Serializable {
	// Getters

	/**
	 * Get the KYC ID
	 * 
	 * @return long
	 */
	public long getKYCID();

	/**
	 * Get the document originating location
	 * 
	 * @return String
	 */
	public String getOriginatingLocation();

	/**
	 * Get Risk Rating
	 * 
	 * @return String
	 */
	public String getRiskRating();

	/**
	 * Get Last Review Date
	 * 
	 * @return Date
	 */
	public Date getLastReviewDate();

	/**
	 * Get Next Review Date
	 * 
	 * @return Date
	 */
	public Date getNextReviewDate();

	/**
	 * Get CDD Doc Submit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getCDDSubmitInd();

	/**
	 * Get KYC Doc Submit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getKYCSubmitInd();

	/**
	 * Get GIC Doc Submit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getGICSubmitInd();

	/**
	 * Get Exceptional Approval Indicator
	 * 
	 * @return boolean
	 */
	public boolean getExceptionalApprovalInd();

	// Setters

	/**
	 * Set the KYC ID
	 * 
	 * @param value is of type long
	 */
	public void setKYCID(long value);

	/**
	 * Set the document originating location
	 * 
	 * @param value is of type String
	 */
	public void setOriginatingLocation(String value);

	/**
	 * Set Risk Rating
	 * 
	 * @param value is of type String
	 */
	public void setRiskRating(String value);

	/**
	 * Set Last Review Date
	 * 
	 * @param value is of type Date
	 */
	public void setLastReviewDate(Date value);

	/**
	 * Set Next Review Date
	 * 
	 * @param value is of type Date
	 */
	public void setNextReviewDate(Date value);

	/**
	 * Set CDD Doc Submit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCDDSubmitInd(boolean value);

	/**
	 * Set KYC Doc Submit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setKYCSubmitInd(boolean value);

	/**
	 * Set GIC Doc Submit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setGICSubmitInd(boolean value);

	/**
	 * Set Exceptional Approval Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setExceptionalApprovalInd(boolean value);
}