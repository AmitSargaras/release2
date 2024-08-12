/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.isdacsa;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by ISDA CSA Deal Description: Have set and get method to store the screen
 * value and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class ISDACSADealForm extends TrxContextForm implements java.io.Serializable {

	private String singleCMSDealID = "";

	private String productType = "";

	private String limitProfileID = "";

	private String agreementID = "";

	private String[] dealID;

	private String[] marketValue;

	/**
	 * Description : get method for form to get the single value for csm deal id
	 * 
	 * @return singleCMSDealID
	 */

	public String getSingleCMSDealID() {
		return singleCMSDealID;
	}

	/**
	 * Description : set the single value for cms deal ID No
	 * 
	 * @param singleCMSDealID is the single value for cms deal ID
	 */

	public void setSingleCMSDealID(String singleCMSDealID) {
		this.singleCMSDealID = singleCMSDealID;
	}

	/**
	 * Description : get method for form to get the type of product
	 * 
	 * @return productType
	 */

	public String getProductType() {
		return productType;
	}

	/**
	 * Description : set the type of product
	 * 
	 * @param productType is the type of product
	 */

	public void setProductType(String productType) {
		this.productType = productType;
	}

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
	 * @return cmsDealID
	 */

	public String getDealID(int i) {
		return dealID[i];
	}

	/**
	 * Description : get method for form to get the csm deal id
	 * 
	 * @return cmsDealID
	 */

	public String[] getDealID() {
		return dealID;
	}

	/**
	 * Description : set the cms deal ID No
	 * 
	 * @param cmsDealID is the cms deal ID
	 */

	public void setDealID(String[] dealID) {
		this.dealID = dealID;
	}

	/**
	 * Description : get method for form to get the NPV Base Amount
	 * 
	 * @return nPVBaseAmount
	 */

	public String getMarketValue(int i) {
		return marketValue[i];
	}

	/**
	 * Description : get method for form to get the NPV Base Amount
	 * 
	 * @return nPVBaseAmount
	 */

	public String[] getMarketValue() {
		return marketValue;
	}

	/**
	 * Description : set the NPV Base Amount
	 * 
	 * @param nPVBaseAmount is the NPV Base Amount
	 */

	public void setMarketValue(String[] marketValue) {
		this.marketValue = marketValue;
	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */

	public String[][] getMapper() {

		String[][] input = {

		{ "InitialISDACSADealSummary", "com.integrosys.cms.ui.tradingbook.deal.isdacsa.ISDACSADealMapper" },

		{ "ISDACSADealSummaryTrxValue", "com.integrosys.cms.ui.tradingbook.deal.isdacsa.ISDACSADealMapper" },

		{ "InitialISDACSADealDetail", "com.integrosys.cms.ui.tradingbook.deal.isdacsa.ISDACSADealMapper" },

		{ "ISDACSADealDetailTrxValue", "com.integrosys.cms.ui.tradingbook.deal.isdacsa.ISDACSADealMapper" },

		{ "InitialISDACSADeal", "com.integrosys.cms.ui.tradingbook.deal.isdacsa.ISDACSADealMapper" },

		{ "ISDACSADeal", "com.integrosys.cms.ui.tradingbook.deal.isdacsa.ISDACSADealMapper" },

		{ "ISDACSADealTrxValue", "com.integrosys.cms.ui.tradingbook.deal.isdacsa.ISDACSADealMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "mapper", "com.integrosys.cms.ui.tradingbook.deal.isdacsa.ISDACSADealMapper" }

		};

		return input;

	}

}
