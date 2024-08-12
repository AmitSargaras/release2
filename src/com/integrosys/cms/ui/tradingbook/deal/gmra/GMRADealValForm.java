/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by GMRA Deal Valuation Description: Have set and get method to store the
 * screen value and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class GMRADealValForm extends TrxContextForm implements java.io.Serializable {

	private String limitProfileID = "";

	private String agreementID = "";

	private String[] dealID;

	private String[] marketPriceCurCode;

	private String[] marketPrice;

	/**
	 * Description : get method for form to get the limit profile ID
	 * 
	 * @return limitProfileID
	 */

	public String getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * Description : set the limit profile ID No
	 * 
	 * @param limitProfileID is the limit profile ID
	 */

	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
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
	 * Description : get method for form to get the csm deal id
	 * 
	 * @return dealID
	 */

	public String[] getDealID() {
		return dealID;
	}

	/**
	 * Description : get method for form to get the csm deal id
	 * 
	 * @return dealID
	 */

	public String getDealID(int i) {
		return dealID[i];
	}

	/**
	 * Description : set the cms deal ID No
	 * 
	 * @param dealID is the cms deal ID
	 */

	public void setDealID(String[] dealID) {
		this.dealID = dealID;
	}

	/**
	 * Description : get method for form to get the NPV Base Amount
	 * 
	 * @return nPVBaseAmount
	 */

	public String[] getMarketPriceCurCode() {
		return marketPriceCurCode;
	}

	/**
	 * Description : get method for form to get the NPV Base Amount
	 * 
	 * @return nPVBaseAmount
	 */

	public String getMarketPriceCurCode(int i) {
		return marketPriceCurCode[i];
	}

	/**
	 * Description : set the NPV Base Amount
	 * 
	 * @param nPVBaseAmount is the NPV Base Amount
	 */

	public void setMarketPriceCurCode(String[] marketPriceCurCode) {
		this.marketPriceCurCode = marketPriceCurCode;
	}

	/**
	 * Description : get method for form to get the NPV Base Amount
	 * 
	 * @return nPVBaseAmount
	 */

	public String[] getMarketPrice() {
		return marketPrice;
	}

	/**
	 * Description : get method for form to get the NPV Base Amount
	 * 
	 * @return nPVBaseAmount
	 */

	public String getMarketPrice(int i) {
		return marketPrice[i];
	}

	/**
	 * Description : set the NPV Base Amount
	 * 
	 * @param nPVBaseAmount is the NPV Base Amount
	 */

	public void setMarketPrice(String[] marketPrice) {
		this.marketPrice = marketPrice;
	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */

	public String[][] getMapper() {

		String[][] input = {

		{ "InitialGMRADealVal", "com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealValMapper" },

		{ "GMRADealVal", "com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealValMapper" },

		{ "GMRADealValTrxValue", "com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealValMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "mapper", "com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealValMapper" }

		};

		return input;

	}

}
