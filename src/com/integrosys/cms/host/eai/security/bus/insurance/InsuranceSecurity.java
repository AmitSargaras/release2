/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/InsuranceSecurity.java,v 1.3 2003/12/08 03:47:35 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus.insurance;

import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents approved security of type Insurance.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/12/08 03:47:35 $ Tag: $Name: $
 */
public class InsuranceSecurity extends ApprovedSecurity {
	private String insuranceDescription;

	private StandardCode insuranceName;

	private StandardCode insuranceType;

	private Double insuredAmount;

	private String insuredAmountCurrency;

	private Date effectiveDate;

	private String policyNo;

	private Date expiryDate;

	private String externalLegalCounsel;

	private String accelerationClause;

	private String localCurrencyInCM;

	private StandardCode coreMarket;

	private Date iSDADate;

	private Date treasuryDate;

	private String bankInterestNoted;

	private StandardCode bankCustArrangeIns;

	private Double insurancePremium;

	private String insurancePremiumCurrency;

	private Date issuanceDate;

	/**
	 * Default constructor.
	 */
	public InsuranceSecurity() {
		super();
	}

	public String getAccelerationClause() {
		return accelerationClause;
	}

	public StandardCode getBankCustArrangeIns() {
		return bankCustArrangeIns;
	}

	public String getBankInterestNoted() {
		return bankInterestNoted;
	}

	public StandardCode getCoreMarket() {
		return coreMarket;
	}

	public String getEffectiveDate() {
		return MessageDate.getInstance().getString(effectiveDate);
	}

	public String getExpiryDate() {
		return MessageDate.getInstance().getString(expiryDate);
	}

	public String getExternalLegalCounsel() {
		return externalLegalCounsel;
	}

	public String getInsuranceDescription() {
		return insuranceDescription;
	}

	public StandardCode getInsuranceName() {
		return insuranceName;
	}

	public Double getInsurancePremium() {
		return insurancePremium;
	}

	public String getInsurancePremiumCurrency() {
		return insurancePremiumCurrency;
	}

	public StandardCode getInsuranceType() {
		return insuranceType;
	}

	public Double getInsuredAmount() {
		return insuredAmount;
	}

	/**
	 * @return the insuredAmountCurrency
	 */
	public String getInsuredAmountCurrency() {
		return insuredAmountCurrency;
	}

	public String getISDADate() {
		return MessageDate.getInstance().getString(iSDADate);
	}

	public String getIssuanceDate() {
		return MessageDate.getInstance().getString(issuanceDate);
	}

	public Date getIssuanceDateValue() {
		return issuanceDate;
	}

	public Date getJDOEffectiveDate() {
		return effectiveDate;
	}

	public Date getJDOExpiryDate() {
		return expiryDate;
	}

	public Date getJDOISDADate() {
		return iSDADate;
	}

	public Date getJDOTreasuryDate() {
		return treasuryDate;
	}

	public String getLocalCurrencyInCM() {
		return localCurrencyInCM;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getTreasuryDate() {
		return MessageDate.getInstance().getString(treasuryDate);
	}

	public void setAccelerationClause(String accelerationClause) {
		this.accelerationClause = accelerationClause;
	}

	public void setBankCustArrangeIns(StandardCode bankCustArrangeIns) {
		this.bankCustArrangeIns = bankCustArrangeIns;
	}

	public void setBankInterestNoted(String bankInterestNoted) {
		this.bankInterestNoted = bankInterestNoted;
	}

	public void setCoreMarket(StandardCode coreMarket) {
		this.coreMarket = coreMarket;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = MessageDate.getInstance().getDate(effectiveDate);
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = MessageDate.getInstance().getDate(expiryDate);
	}

	public void setExternalLegalCounsel(String externalLegalCounsel) {
		this.externalLegalCounsel = externalLegalCounsel;
	}

	public void setInsuranceDescription(String insuranceDescription) {
		this.insuranceDescription = insuranceDescription;
	}

	public void setInsuranceName(StandardCode insuranceName) {
		this.insuranceName = insuranceName;
	}

	public void setInsurancePremium(Double insurancePremium) {
		this.insurancePremium = insurancePremium;
	}

	public void setInsurancePremiumCurrency(String insurancePremiumCurrency) {
		this.insurancePremiumCurrency = insurancePremiumCurrency;
	}

	public void setInsuranceType(StandardCode insuranceType) {
		this.insuranceType = insuranceType;
	}

	public void setInsuredAmount(Double insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	/**
	 * @param insuredAmountCurrency the insuredAmountCurrency to set
	 */
	public void setInsuredAmountCurrency(String insuredAmountCurrency) {
		this.insuredAmountCurrency = insuredAmountCurrency;
	}

	public void setISDADate(String iSDADate) {
		this.iSDADate = MessageDate.getInstance().getDate(iSDADate);
	}

	public void setIssuanceDate(String issuanceDate) {
		this.issuanceDate = MessageDate.getInstance().getDate(issuanceDate);
	}

	public void setIssuanceDateValue(Date issuanceDateValue) {
		this.issuanceDate = issuanceDateValue;
	}

	public void setJDOEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setJDOExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setJDOISDADate(Date iSDADate) {
		this.iSDADate = iSDADate;
	}

	public void setJDOTreasuryDate(Date treasuryDate) {
		this.treasuryDate = treasuryDate;
	}

	public void setLocalCurrencyInCM(String localCurrencyInCM) {
		this.localCurrencyInCM = localCurrencyInCM;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public void setTreasuryDate(String treasuryDate) {
		this.treasuryDate = MessageDate.getInstance().getDate(treasuryDate);
	}

}
