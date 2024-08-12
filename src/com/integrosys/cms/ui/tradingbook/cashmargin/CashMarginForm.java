/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.cashmargin;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by cash margin Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class CashMarginForm extends TrxContextForm implements java.io.Serializable {

	private String lEID = "";

	private String agreementID = "";

	private String previousEvent = "";

	private String index = "";

	private String cashMarginID;

	private String trxDate;

	private String napSignAddValue;

	private String napValue;

	private String[] deletedCreditRating;

	private String[] displayCashMarginID;

	private String[] displayTrxDate;

	private String[] displayNapSignAddValue;

	private String[] displayNapValue;

	private String[] displayCashInterest;

	private String displayTotalCashInterest;

	/**
	 * Description : get method for form to get the LE ID
	 * 
	 * @return lEID
	 */

	public String getLEID() {
		return lEID;
	}

	/**
	 * Description : set the LE ID No
	 * 
	 * @param lEID is the LE ID
	 */

	public void setLEID(String lEID) {
		this.lEID = lEID;
	}

	/**
	 * Description : get method for form to get the Agreement ID
	 * 
	 * @return agreementID
	 */

	public String getAgreementID() {
		return agreementID;
	}

	/**
	 * Description : set the Agreement ID
	 * 
	 * @param agreementID is the Agreement ID
	 */

	public void setAgreementID(String agreementID) {
		this.agreementID = agreementID;
	}

	/**
	 * Description : get method for form to get the previous event
	 * 
	 * @return previousEvent
	 */

	public String getPreviousEvent() {
		return previousEvent;
	}

	/**
	 * Description : set the previous event
	 * 
	 * @param previousEvent is the previous event
	 */

	public void setPreviousEvent(String previousEvent) {
		this.previousEvent = previousEvent;
	}

	/**
	 * Description : get method for form to get the csm deal id
	 * 
	 * @return singleCashMarginID
	 */

	public String getIndex() {
		return index;
	}

	/**
	 * Description : set the cms deal ID No
	 * 
	 * @param singleCashMarginID is the cms deal ID
	 */

	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * Description : get method for form to get the NPV Base Amount
	 * 
	 * @return trxDate
	 */

	public String getTrxDate() {
		return trxDate;
	}

	/**
	 * Description : set the NPV Base Amount
	 * 
	 * @param trxDate is the NPV Base Amount
	 */

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	/**
	 * Description : get method for form to get the NPV Base Amount
	 * 
	 * @return napSignAddValue
	 */

	public String getNapSignAddValue() {
		return napSignAddValue;
	}

	/**
	 * Description : set the NPV Base Amount
	 * 
	 * @param nAPSignAddValue is the NPV Base Amount
	 */

	public void setNapSignAddValue(String napSignAddValue) {
		this.napSignAddValue = napSignAddValue;
	}

	/**
	 * Description : get method for form to get the NPV Base Amount
	 * 
	 * @return napValue
	 */

	public String getNapValue() {
		return napValue;
	}

	/**
	 * Description : set the NPV Base Amount
	 * 
	 * @param napValue is the NPV Base Amount
	 */

	public void setNapValue(String napValue) {
		this.napValue = napValue;
	}

	/**
	 * Description : get method for form to get the deleted credit rating
	 * 
	 * @return deletedCreditRating
	 */

	public String[] getDeletedCreditRating() {
		return deletedCreditRating;
	}

	/**
	 * Description : set the deleted credit rating
	 * 
	 * @param deletedCreditRating is the deleted credit rating
	 */

	public void setDeletedCreditRating(String[] deletedCreditRating) {
		this.deletedCreditRating = deletedCreditRating;
	}

	/**
	 * Description : get method for form to get the array of cash margin ID for
	 * display
	 * 
	 * @return displayCashMarginID
	 */

	public String[] getDisplayCashMarginID() {
		return displayCashMarginID;
	}

	/**
	 * Description : set the array of cash margin ID for display
	 * 
	 * @param displayCashMarginID is the array of cash margin ID for display
	 */

	public void setDisplayCashMarginID(String[] displayCashMarginID) {
		this.displayCashMarginID = displayCashMarginID;
	}

	/**
	 * Description : get method for form to get the transaction date for display
	 * 
	 * @return displayTrxDate
	 */

	public String[] getDisplayTrxDate() {
		return displayTrxDate;
	}

	/**
	 * Description : set the transaction date for display
	 * 
	 * @param displayTrxDate is the transaction date for display
	 */

	public void setDisplayTrxDate(String[] displayTrxDate) {
		this.displayTrxDate = displayTrxDate;
	}

	/**
	 * Description : get method for form to get the NPV Base Amount for display
	 * 
	 * @return displayNapSignAddValue
	 */

	public String[] getDisplayNapSignAddValue() {
		return displayNapSignAddValue;
	}

	/**
	 * Description : set the NPV Base Amount for display
	 * 
	 * @param displayNapSignAddValue is the NPV Base Amount for display
	 */

	public void setDisplayNapSignAddValue(String[] displayNapSignAddValue) {
		this.displayNapSignAddValue = displayNapSignAddValue;
	}

	/**
	 * Description : get method for form to get the Nap value for display
	 * 
	 * @return displayNapValue
	 */

	public String[] getDisplayNapValue() {
		return displayNapValue;
	}

	/**
	 * Description : set the Nap value for display
	 * 
	 * @param displayNapValue is the Nap value for display
	 */

	public void setDisplayNapValue(String[] displayNapValue) {
		this.displayNapValue = displayNapValue;
	}

	/**
	 * Description : get method for form to get the cash interest for display
	 * 
	 * @return displayCashInterest
	 */

	public String[] getDisplayCashInterest() {
		return displayCashInterest;
	}

	/**
	 * Description : set the cash interest for display
	 * 
	 * @param displayCashInterest is the cash interest for display
	 */

	public void setDisplayCashInterest(String[] displayCashInterest) {
		this.displayCashInterest = displayCashInterest;
	}

	/**
	 * Description : get method for form to get the total cash interest for
	 * display
	 * 
	 * @return displayTotalCashInterest
	 */

	public String getDisplayTotalCashInterest() {
		return displayTotalCashInterest;
	}

	/**
	 * Description : set the total cash interest for display
	 * 
	 * @param displayTotalCashInterest is the total cash interest for display
	 */

	public void setDisplayTotalCashInterest(String displayTotalCashInterest) {
		this.displayTotalCashInterest = displayTotalCashInterest;
	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */

	public String[][] getMapper() {

		String[][] input = {

		{ "InitialCashMargin", "com.integrosys.cms.ui.tradingbook.cashmargin.CashMarginMapper" },

		{ "CashMargin", "com.integrosys.cms.ui.tradingbook.cashmargin.CashMarginMapper" },

		{ "CashMarginTrxValue", "com.integrosys.cms.ui.tradingbook.cashmargin.CashMarginMapper" },

		{ "InitialSingleCashMargin", "com.integrosys.cms.ui.tradingbook.cashmargin.CashMarginMapper" },

		{ "SingleCashMarginTrxValue", "com.integrosys.cms.ui.tradingbook.cashmargin.CashMarginMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "mapper", "com.integrosys.cms.ui.tradingbook.cashmargin.CashMarginMapper" }

		};

		return input;

	}

}
