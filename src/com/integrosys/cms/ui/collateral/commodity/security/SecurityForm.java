/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/security/SecurityForm.java,v 1.11 2006/10/10 07:59:47 jzhan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.security;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/10/10 07:59:47 $ Tag: $Name: $
 */

public class SecurityForm extends CommonForm implements Serializable {

	private String securityID = "";

	private String securitySubType = "";

	private String sciSecurityCurrency = "";

	private String cmsSecurityCurrency = "";

	private String leCharge = "";

	private String leJurisdiction = "";

	private String leGoverningLaw = "";

	private String leDateCharge = "";

	private String leDateJurisdiction = "";

	private String leDateGovernginLaw = "";

	private String legalChargeDate = "";

	private String le = "";

	private String leDate = "";

	private String exchangeControlObtained = "";

	private String chargeType = "";

	private String chargeAmount = "";

	private String securityEnvRisk = "";

	private String dateSecurityEnvRisk = "";

	private String remarkSecurityEnvRisk = "";

	private String securityLocation = "";

	private String plusmnSign = "";

	private String customerDiff = "";

	private String isCMTmaker = "";

	private String valuationCurrency = "";

	private String valDate = "";

	private String valCMV = "";

	private String valFSV = "";

	private String securityOrganization = "";

	private String[] deletedApportionments;

	public String getSecurityID() {
		return this.securityID;
	}

	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	public String getSecuritySubType() {
		return this.securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public String getSciSecurityCurrency() {
		return sciSecurityCurrency;
	}

	public void setSciSecurityCurrency(String sciSecurityCurrency) {
		this.sciSecurityCurrency = sciSecurityCurrency;
	}

	public String getCmsSecurityCurrency() {
		return cmsSecurityCurrency;
	}

	public void setCmsSecurityCurrency(String cmsSecurityCurrency) {
		this.cmsSecurityCurrency = cmsSecurityCurrency;
	}

	public String getLe() {
		return this.le;
	}

	public void setLe(String le) {
		this.le = le;
	}

	/*
	 * public String getLeCharge() { return this.leCharge; }
	 * 
	 * public void setLeCharge(String leCharge) { this.leCharge = leCharge; }
	 * 
	 * public String getLeJurisdiction() { return this.leJurisdiction; }
	 * 
	 * public void setLeJurisdiction(String leJurisdiction) {
	 * this.leJurisdiction = leJurisdiction; }
	 * 
	 * public String getLeGoverningLaw() { return this.leGoverningLaw; }
	 * 
	 * public void setLeGoverningLaw(String leGoverningLaw) {
	 * this.leGoverningLaw = leGoverningLaw; }
	 */

	public String getLeDate() {
		return this.leDate;
	}

	public void setLeDate(String leDate) {
		this.leDate = leDate;
	}

	/*
	 * public String getLeDateCharge() { return this.leDateCharge; }
	 * 
	 * public void setLeDateCharge(String leDateCharge) { this.leDateCharge =
	 * leDateCharge; }
	 * 
	 * public String getLeDateJurisdiction() { return this.leDateJurisdiction; }
	 * 
	 * public void setLeDateJurisdiction(String leDateJurisdiction) {
	 * this.leDateJurisdiction = leDateJurisdiction; }
	 * 
	 * public String getLeDateGovernginLaw() { return this.leDateGovernginLaw; }
	 * 
	 * public void setLeDateGovernginLaw(String leDateGovernginLaw) {
	 * this.leDateGovernginLaw = leDateGovernginLaw; }
	 */

	public String getChargeType() {
		return this.chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getLegalChargeDate() {
		return legalChargeDate;
	}

	public void setLegalChargeDate(String legalChargeDate) {
		this.legalChargeDate = legalChargeDate;
	}

	public String getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getExchangeControlObtained() {
		return exchangeControlObtained;
	}

	public void setExchangeControlObtained(String exchangeControlObtained) {
		this.exchangeControlObtained = exchangeControlObtained;
	}

	public String getSecurityEnvRisk() {
		return this.securityEnvRisk;
	}

	public void setSecurityEnvRisk(String securityEnvRisk) {
		this.securityEnvRisk = securityEnvRisk;
	}

	public String getDateSecurityEnvRisk() {
		return this.dateSecurityEnvRisk;
	}

	public void setDateSecurityEnvRisk(String dateSecurityEnvRisk) {
		this.dateSecurityEnvRisk = dateSecurityEnvRisk;
	}

	public String getRemarkSecurityEnvRisk() {
		return this.remarkSecurityEnvRisk;
	}

	public void setRemarkSecurityEnvRisk(String remarkSecurityEnvRisk) {
		this.remarkSecurityEnvRisk = remarkSecurityEnvRisk;
	}

	public String getSecurityLocation() {
		return this.securityLocation;
	}

	public void setSecurityLocation(String securityLocation) {
		this.securityLocation = securityLocation;
	}

	public String getPlusmnSign() {
		return this.plusmnSign;
	}

	public void setPlusmnSign(String plusmnSign) {
		this.plusmnSign = plusmnSign;
	}

	public String getCustomerDiff() {
		return this.customerDiff;
	}

	public void setCustomerDiff(String customerDiff) {
		this.customerDiff = customerDiff;
	}

	public String getIsCMTmaker() {
		return isCMTmaker;
	}

	public void setIsCMTmaker(String isCMTmaker) {
		this.isCMTmaker = isCMTmaker;
	}

	public String getValuationCurrency() {
		return this.valuationCurrency;
	}

	public void setValuationCurrency(String valuationCurrency) {
		this.valuationCurrency = valuationCurrency;
	}

	public String getValDate() {
		return this.valDate;
	}

	public void setValDate(String valDate) {
		this.valDate = valDate;
	}

	public String getValCMV() {
		return this.valCMV;
	}

	public void setValCMV(String valCMV) {
		this.valCMV = valCMV;
	}

	public String getValFSV() {
		return this.valFSV;
	}

	public void setValFSV(String valFSV) {
		this.valFSV = valFSV;
	}

	public String getSecurityOrganization() {
		return securityOrganization;
	}

	public void setSecurityOrganization(String securityOrganization) {
		this.securityOrganization = securityOrganization;
	}

	/**
	 * @return Returns the deletedItems.
	 */
	public String[] getDeletedApportionments() {
		return deletedApportionments;
	}

	/**
	 * @param deletedItems The deletedItems to set.
	 */
	public void setDeletedApportionments(String[] deletedApportionments) {
		this.deletedApportionments = deletedApportionments;
	}

	public String[][] getMapper() {
		String[][] input = { { "commSecObj", "com.integrosys.cms.ui.collateral.commodity.security.SecurityMapper" }, };
		return input;
	}
}
