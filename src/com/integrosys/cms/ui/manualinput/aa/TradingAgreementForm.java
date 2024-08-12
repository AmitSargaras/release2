/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by Trading Agreement Description: Have set and get method to store the screen
 * value and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class TradingAgreementForm extends TrxContextForm implements java.io.Serializable {

	private String agreementID;

	private String limitProfileID;

	private String agreementType;

	private String counterPartyBranch;

	private String minTransCurCode;

	private String minTransfer;

	private String countRatingType;

	private String counterpartyRating;

	private String maybankRatingType;

	private String maybankRating;

	private String agreeIntRateType;

	private String baseCurrency;

	private String counterPartyThresholdAmt;

	private String mbbThresholdAmt;

	private String agentBankName;

	private String agentBankAddress;

	private String bankClearanceId;

	private String bankAccountId;

	private String clearingDesc;

	private String notificationTime;

	private String valuationTime;

	/**
	 * Description : get method for form to get the agreement id
	 * 
	 * @return agreementID
	 */

	public String getAgreementID() {
		return agreementID;
	}

	/**
	 * Description : set the agreement id
	 * 
	 * @param agreementID is the agreement id
	 */

	public void setAgreementID(String agreementID) {
		this.agreementID = agreementID;
	}

	/**
	 * Description : get method for form to get the limit profile id
	 * 
	 * @return limitProfileID
	 */

	public String getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * Description : set the limit profile id
	 * 
	 * @param limitProfileID is the limit profile id
	 */

	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	/**
	 * Description : get method for form to get the agreement type
	 * 
	 * @return agreementType
	 */

	public String getAgreementType() {
		return agreementType;
	}

	/**
	 * Description : set the agreement type
	 * 
	 * @param agreementType is the agreement type
	 */

	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	/**
	 * Description : get method for form to get the counter party branch
	 * 
	 * @return counterPartyBranch
	 */

	public String getCounterPartyBranch() {
		return counterPartyBranch;
	}

	/**
	 * Description : set the counter party branch
	 * 
	 * @param counterPartyBranch is the counter party branch
	 */

	public void setCounterPartyBranch(String counterPartyBranch) {
		this.counterPartyBranch = counterPartyBranch;
	}

	/**
	 * Description : get method for form to get the minimum transfer currency
	 * code
	 * 
	 * @return minTransCurCode
	 */

	public String getMinTransCurCode() {
		return minTransCurCode;
	}

	/**
	 * Description : set the minimum transfer currency code
	 * 
	 * @param minTransCurCode is the minimum transfer currency code
	 */

	public void setMinTransCurCode(String minTransCurCode) {
		this.minTransCurCode = minTransCurCode;
	}

	/**
	 * Description : get method for form to get the minimum transfer amount
	 * 
	 * @return minTransfer
	 */

	public String getMinTransfer() {
		return minTransfer;
	}

	/**
	 * Description : set the minimum transfer amount
	 * 
	 * @param minTransfer is the minimum transfer amount
	 */

	public void setMinTransfer(String minTransfer) {
		this.minTransfer = minTransfer;
	}

	/**
	 * Description : get method for form to get the counterparty rating type
	 * 
	 * @return countRatingType
	 */

	public String getCountRatingType() {
		return countRatingType;
	}

	/**
	 * Description : set the counterparty rating type
	 * 
	 * @param countRatingType is the counterparty rating type
	 */

	public void setCountRatingType(String countRatingType) {
		this.countRatingType = countRatingType;
	}

	/**
	 * Description : get method for form to get the counterparty rating
	 * 
	 * @return counterpartyRating
	 */

	public String getCounterpartyRating() {
		return counterpartyRating;
	}

	/**
	 * Description : set the counterparty rating
	 * 
	 * @param counterpartyRating is the counterparty rating
	 */

	public void setCounterpartyRating(String counterpartyRating) {
		this.counterpartyRating = counterpartyRating;
	}

	/**
	 * Description : get method for form to get the maybank rating type
	 * 
	 * @return maybankRatingType
	 */

	public String getMaybankRatingType() {
		return maybankRatingType;
	}

	/**
	 * Description : set the maybank rating type
	 * 
	 * @param maybankRatingType is the maybank rating type
	 */

	public void setMaybankRatingType(String maybankRatingType) {
		this.maybankRatingType = maybankRatingType;
	}

	/**
	 * Description : get method for form to get the maybank rating
	 * 
	 * @return maybankRating
	 */

	public String getMaybankRating() {
		return maybankRating;
	}

	/**
	 * Description : set the maybank rating
	 * 
	 * @param maybankRating is the maybank rating
	 */

	public void setMaybankRating(String maybankRating) {
		this.maybankRating = maybankRating;
	}

	/**
	 * Description : get method for form to get the agreement interest rate type
	 * 
	 * @return agreeIntRateType
	 */

	public String getAgreeIntRateType() {
		return agreeIntRateType;
	}

	/**
	 * Description : set the agreement interest rate type
	 * 
	 * @param agreeIntRateType is the agreement interest rate type
	 */

	public void setAgreeIntRateType(String agreeIntRateType) {
		this.agreeIntRateType = agreeIntRateType;
	}

	/**
	 * Description : get method for form to get the base currency
	 * 
	 * @return baseCurrency
	 */

	public String getBaseCurrency() {
		return baseCurrency;
	}

	/**
	 * Description : set the base currency
	 * 
	 * @param baseCurrency is the base currency
	 */

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	/**
	 * Description : get method for form to get the counterparty threshold
	 * amount
	 * 
	 * @return counterPartyThresholdAmt
	 */

	public String getCounterPartyThresholdAmt() {
		return counterPartyThresholdAmt;
	}

	/**
	 * Description : set the counterparty threshold amount
	 * 
	 * @param counterPartyThresholdAmt is the counterparty threshold amount
	 */

	public void setCounterPartyThresholdAmt(String counterPartyThresholdAmt) {
		this.counterPartyThresholdAmt = counterPartyThresholdAmt;
	}

	/**
	 * Description : set the maybank threshold amount
	 * 
	 * @param mbbThresholdAmt is the maybank threshold amount
	 */

	public void setMbbThresholdAmt(String mbbThresholdAmt) {
		this.mbbThresholdAmt = mbbThresholdAmt;
	}

	/**
	 * Description : get method for form to get the maybank threshold amount
	 * 
	 * @return mbbThresholdAmt
	 */

	public String getMbbThresholdAmt() {
		return mbbThresholdAmt;
	}

	/**
	 * Description : get method for form to get the name of agent bank
	 * 
	 * @return agentBankName
	 */

	public String getAgentBankName() {
		return agentBankName;
	}

	/**
	 * Description : set the name of agent bank
	 * 
	 * @param agentBankName is the name of agent bank
	 */

	public void setAgentBankName(String agentBankName) {
		this.agentBankName = agentBankName;
	}

	/**
	 * Description : get method for form to get the address of agent bank
	 * 
	 * @return agentBankAddress
	 */

	public String getAgentBankAddress() {
		return agentBankAddress;
	}

	/**
	 * Description : set the address of agent bank
	 * 
	 * @param agentBankAddress is the address of agent bank
	 */

	public void setAgentBankAddress(String agentBankAddress) {
		this.agentBankAddress = agentBankAddress;
	}

	/**
	 * Description : get method for form to get the bank clearance id
	 * 
	 * @return bankClearanceId
	 */

	public String getBankClearanceId() {
		return bankClearanceId;
	}

	/**
	 * Description : set the bank clearance id
	 * 
	 * @param agreeIntRateType is the bank clearance id
	 */

	public void setBankClearanceId(String bankClearanceId) {
		this.bankClearanceId = bankClearanceId;
	}

	/**
	 * Description : get method for form to get the bank account id
	 * 
	 * @return bankAccountId
	 */

	public String getBankAccountId() {
		return bankAccountId;
	}

	/**
	 * Description : set the bank account id
	 * 
	 * @param bankAccountId is the bank account id
	 */

	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	/**
	 * Description : get method for form to get the clearing description
	 * 
	 * @return clearingDesc
	 */

	public String getClearingDesc() {
		return clearingDesc;
	}

	/**
	 * Description : set the clearing description
	 * 
	 * @param clearingDesc is the clearing description
	 */

	public void setClearingDesc(String clearingDesc) {
		this.clearingDesc = clearingDesc;
	}

	/**
	 * Description : get method for form to get the notification time
	 * 
	 * @return notificationTime
	 */

	public String getNotificationTime() {
		return notificationTime;
	}

	/**
	 * Description : set the notification time
	 * 
	 * @param notificationTime is the notification time
	 */

	public void setNotificationTime(String notificationTime) {
		this.notificationTime = notificationTime;
	}

	/**
	 * Description : get method for form to get the valuation time
	 * 
	 * @return valuationTime
	 */

	public String getValuationTime() {
		return valuationTime;
	}

	/**
	 * Description : set the valuation time
	 * 
	 * @param valuationTime is the valuation time
	 */

	public void setValuationTime(String valuationTime) {
		this.valuationTime = valuationTime;
	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */

	public String[][] getMapper() {

		String[][] input = {

		{ "InitialTradingAgreement", "com.integrosys.cms.ui.manualinput.aa.TradingAgreementMapper" },

		{ "TradingAgreement", "com.integrosys.cms.ui.manualinput.aa.TradingAgreementMapper" },

		{ "TradingAgreementTrxValue", "com.integrosys.cms.ui.manualinput.aa.TradingAgreementMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "mapper", "com.integrosys.cms.ui.manualinput.aa.TradingAgreementMapper" }

		};

		return input;

	}

}
