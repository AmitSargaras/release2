/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by GMRA Deal Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class GMRADealForm extends TrxContextForm implements java.io.Serializable {

	private String cmsDealID = "";

	private String dealID = "";

	private String limitProfileID = "";

	private String agreementID = "";

	private String productType = "";

	private String secDesc = "";

	private String isinCode = "";

	private String cusipCode = "";

	private String dealCurCode = "";

	private String dealPrice = "";

	private String notionalCurCode = "";

	private String notional = "";

	private String haircut = "";

	private String dealRate = "";

	private String tradeDate = "";

	private String maturityDate = "";

	private String dealCountry = "";

	private String dealBranch = "";

	private String targetOffset = "";

	private String repoStartAmt = "";

	private String repoEndAmt = "";

	/**
	 * Description : get method for form to get the target off set
	 * 
	 * @return targetOffset
	 */

	public String getTargetOffset() {
		return targetOffset;
	}

	/**
	 * Description : set the target off set
	 * 
	 * @param targetOffset is the target off set
	 */

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}

	/**
	 * Description : get method for form to get the csm deal id
	 * 
	 * @return cmsDealID
	 */

	public String getCmsDealID() {
		return cmsDealID;
	}

	/**
	 * Description : set the cms deal ID No
	 * 
	 * @param dealID is the cms deal ID
	 */

	public void setCmsDealID(String cmsDealID) {
		this.cmsDealID = cmsDealID;
	}

	/**
	 * Description : get method for form to get the csm deal id
	 * 
	 * @return dealID
	 */

	public String getDealID() {
		return dealID;
	}

	/**
	 * Description : set the cms deal ID No
	 * 
	 * @param dealID is the cms deal ID
	 */

	public void setDealID(String dealID) {
		this.dealID = dealID;
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
	 * Description : get method for form to get the product type
	 * 
	 * @return productType
	 */

	public String getProductType() {
		return productType;
	}

	/**
	 * Description : set the product type
	 * 
	 * @param productType is the product type
	 */

	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * Description : get method for form to get the security desc
	 * 
	 * @return secDesc
	 */

	public String getSecDesc() {
		return secDesc;
	}

	/**
	 * Description : set the security desc
	 * 
	 * @param secDesc is the security desc
	 */

	public void setSecDesc(String secDesc) {
		this.secDesc = secDesc;
	}

	/**
	 * Description : get method for form to get the ISIN code
	 * 
	 * @return isinCode
	 */

	public String getIsinCode() {
		return isinCode;
	}

	/**
	 * Description : set the ISIN code
	 * 
	 * @param isinCode is the ISIN code
	 */

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	/**
	 * Description : get method for form to get the CUSIP code
	 * 
	 * @return cusipCode
	 */

	public String getCusipCode() {
		return cusipCode;
	}

	/**
	 * Description : set the CUSIP code
	 * 
	 * @param cusipCode is the CUSIP code
	 */

	public void setCusipCode(String cusipCode) {
		this.cusipCode = cusipCode;
	}

	/**
	 * Description : get method for form to get the deal amount cur code
	 * 
	 * @return dealCurCode
	 */

	public String getDealCurCode() {
		return dealCurCode;
	}

	/**
	 * Description : set the deal amount cur code
	 * 
	 * @param dealCurCode is the deal amount cur code
	 */

	public void setDealCurCode(String dealCurCode) {
		this.dealCurCode = dealCurCode;
	}

	/**
	 * Description : get method for form to get the deal amount
	 * 
	 * @return dealPrice
	 */

	public String getDealPrice() {
		return dealPrice;
	}

	/**
	 * Description : set the deal amount
	 * 
	 * @param dealPrice is the deal amount
	 */

	public void setDealPrice(String dealPrice) {
		this.dealPrice = dealPrice;
	}

	/**
	 * Description : get method for form to get the national amount cur code
	 * 
	 * @return nationalCurCode
	 */

	public String getNotionalCurCode() {
		return notionalCurCode;
	}

	/**
	 * Description : set the national amount cur code
	 * 
	 * @param nationalCurCode is the national amount cur code
	 */

	public void setNotionalCurCode(String notionalCurCode) {
		this.notionalCurCode = notionalCurCode;
	}

	/**
	 * Description : get method for form to get the national amount
	 * 
	 * @return national
	 */

	public String getNotional() {
		return notional;
	}

	/**
	 * Description : set the national amount
	 * 
	 * @param national is the national amount
	 */

	public void setNotional(String notional) {
		this.notional = notional;
	}

	/**
	 * Description : get method for form to get the haircut
	 * 
	 * @return haircut
	 */

	public String getHaircut() {
		return haircut;
	}

	/**
	 * Description : set the haircut
	 * 
	 * @param haircut is the haircut
	 */

	public void setHaircut(String haircut) {
		this.haircut = haircut;
	}

	/**
	 * Description : get method for form to get the deal rate
	 * 
	 * @return dealRate
	 */

	public String getDealRate() {
		return dealRate;
	}

	/**
	 * Description : set the deal rate
	 * 
	 * @param dealRate is the deal rate
	 */

	public void setDealRate(String dealRate) {
		this.dealRate = dealRate;
	}

	/**
	 * Description : get method for form to get the trade date
	 * 
	 * @return tradeDate
	 */

	public String getTradeDate() {
		return tradeDate;
	}

	/**
	 * Description : set the trade date
	 * 
	 * @param tradeDate is the trade date
	 */

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	/**
	 * Description : get method for form to get the maturity date
	 * 
	 * @return maturityDate
	 */

	public String getMaturityDate() {
		return maturityDate;
	}

	/**
	 * Description : set the maturity date
	 * 
	 * @param maturityDate is the maturity date
	 */

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * Description : get method for form to get the deal country
	 * 
	 * @return dealCountry
	 */

	public String getDealCountry() {
		return dealCountry;
	}

	/**
	 * Description : set the deal country
	 * 
	 * @param dealCountry is the deal country
	 */

	public void setDealCountry(String dealCountry) {
		this.dealCountry = dealCountry;
	}

	/**
	 * Description : get method for form to get the deal branch
	 * 
	 * @return dealBranch
	 */

	public String getDealBranch() {
		return dealBranch;
	}

	/**
	 * Description : set the deal branch
	 * 
	 * @param dealBranch is the deal branch
	 */

	public void setDealBranch(String dealBranch) {
		this.dealBranch = dealBranch;
	}

	/**
	 * Description : get method for form to get the repo amount start cash
	 * 
	 * @return repoStartAmt
	 */
	public String getRepoStartAmt() {
		return repoStartAmt;
	}

	/**
	 * Description : set the repo amount start cash
	 * 
	 * @param repoStartAmt is the repo amount start cash
	 */
	public void setRepoStartAmt(String repoStartAmt) {
		this.repoStartAmt = repoStartAmt;
	}

	/**
	 * Description : get method for form to get the repo amount end cash
	 * 
	 * @return repoEndAmt
	 */
	public String getRepoEndAmt() {
		return repoEndAmt;
	}

	/**
	 * Description : set the repo amount end cash
	 * 
	 * @param repoEndAmt is the repo amount end cash
	 */
	public void setRepoEndAmt(String repoEndAmt) {
		this.repoEndAmt = repoEndAmt;
	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */

	public String[][] getMapper() {

		String[][] input = {

		{ "InitialGMRADealSummary", "com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealMapper" },

		{ "GMRADealSummaryTrxValue", "com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealMapper" },

		{ "InitialGMRADeal", "com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealMapper" },

		{ "GMRADeal", "com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealMapper" },

		{ "GMRADealTrxValue", "com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "mapper", "com.integrosys.cms.ui.tradingbook.deal.gmra.GMRADealMapper" }

		};

		return input;

	}

}
