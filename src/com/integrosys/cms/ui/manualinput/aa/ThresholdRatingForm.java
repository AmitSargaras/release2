/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by Threshold Rating Description: Have set and get method to store the screen
 * value and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class ThresholdRatingForm extends TrxContextForm implements java.io.Serializable {

	private String creditRatingType;

	private String creditRating;

	private String thresholdCurCode;

	private String thresholdAmt;

	private String index;

	private String[] deletedThreshold;

	private String[] ratingType;

	private String[] rating;

	private String[] thresholdAmount;

	/**
	 * Description : get method for form to get the credit rating type
	 * 
	 * @return creditRatingType
	 */

	public String getCreditRatingType() {
		return creditRatingType;
	}

	/**
	 * Description : set the credit rating type
	 * 
	 * @param creditRatingType is the credit rating type
	 */

	public void setCreditRatingType(String creditRatingType) {
		this.creditRatingType = creditRatingType;
	}

	/**
	 * Description : get method for form to get the credit rating
	 * 
	 * @return creditRating
	 */

	public String getCreditRating() {
		return creditRating;
	}

	/**
	 * Description : set the credit rating
	 * 
	 * @param creditRating is the credit rating
	 */

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	/**
	 * Description : get method for form to get the threshold currency code
	 * 
	 * @return thresholdCurCode
	 */

	public String getThresholdCurCode() {
		return thresholdCurCode;
	}

	/**
	 * Description : set the threshold currency code
	 * 
	 * @param thresholdCurCode is the threshold currency code
	 */

	public void setThresholdCurCode(String thresholdCurCode) {
		this.thresholdCurCode = thresholdCurCode;
	}

	/**
	 * Description : get method for form to get the threshold amount
	 * 
	 * @return thresholdAmt
	 */

	public String getThresholdAmt() {
		return thresholdAmt;
	}

	/**
	 * Description : set the threshold amount
	 * 
	 * @param thresholdAmt is the threshold amount
	 */

	public void setThresholdAmt(String thresholdAmt) {
		this.thresholdAmt = thresholdAmt;
	}

	/**
	 * Description : get method for form to get the deleted credit rating
	 * 
	 * @return deletedThreshold
	 */

	public String[] getDeletedThreshold() {
		return deletedThreshold;
	}

	/**
	 * Description : set the deleted credit rating
	 * 
	 * @param deletedThreshold is the deleted credit rating
	 */

	public void setDeletedThreshold(String[] deletedThreshold) {
		this.deletedThreshold = deletedThreshold;
	}

	/**
	 * Description : get method for form to get the array of rating type
	 * 
	 * @return ratingType
	 */

	public String[] getRatingType() {
		return ratingType;
	}

	/**
	 * Description : set the array of rating type
	 * 
	 * @param ratingType is the array of rating type
	 */

	public void setRatingType(String[] ratingType) {
		this.ratingType = ratingType;
	}

	/**
	 * Description : get method for form to get the array of rating
	 * 
	 * @return rating
	 */

	public String[] getRating() {
		return rating;
	}

	/**
	 * Description : set the array of rating
	 * 
	 * @param rating is the array of rating
	 */

	public void setRating(String[] rating) {
		this.rating = rating;
	}

	/**
	 * Description : get method for form to get the array of Threshold Amount
	 * 
	 * @return thresholdAmount
	 */

	public String[] getThresholdAmount() {
		return thresholdAmount;
	}

	/**
	 * Description : set the array of Threshold Amount
	 * 
	 * @param rating is the array of Threshold Amount
	 */

	public void setThresholdAmount(String[] thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */

	public String[][] getMapper() {

		String[][] input = {

		{ "InitialThresholdRating", "com.integrosys.cms.ui.manualinput.aa.ThresholdRatingMapper" },

		{ "ThresholdRating", "com.integrosys.cms.ui.manualinput.aa.ThresholdRatingMapper" },

		{ "ThresholdRatingTrxValue", "com.integrosys.cms.ui.manualinput.aa.ThresholdRatingMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "mapper", "com.integrosys.cms.ui.manualinput.aa.ThresholdRatingMapper" }

		};

		return input;

	}

}
