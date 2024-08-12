//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION

package com.integrosys.cms.ui.collateral.guarantees;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.CollateralForm;

/**
 * 
 * Created by IntelliJ IDEA.
 * 
 * User: ssathish
 * 
 * Date: Jun 20, 2003
 * 
 * Time: 2:56:38 PM
 * 
 * To change this template use Options | File Templates.
 */

public abstract class GuaranteesForm extends CollateralForm implements Serializable {

	private String[] deleteItem;

	private String[] deleteInsItem;

	public String[] getDeleteItem() {
		return deleteItem;
	}

	public void setDeleteItem(String[] deleteItem) {
		this.deleteItem = deleteItem;
	}

	public String[] getDeleteInsItem() {
		return deleteInsItem;
	}

	public void setDeleteInsItem(String[] deleteInsItem) {
		this.deleteInsItem = deleteInsItem;
	}

	private String descGuarantee = "";

	public String getDescGuarantee() {

		return this.descGuarantee;

	}

	public void setDescGuarantee(String descGuarantee) {

		this.descGuarantee = descGuarantee;

	}

	private String guaRefNo = "";

	public String getGuaRefNo() {

		return this.guaRefNo;

	}

	public void setGuaRefNo(String guaRefNo) {

		this.guaRefNo = guaRefNo;

	}

	private String amtGuarantee = "";

	public String getAmtGuarantee() {

		return this.amtGuarantee;

	}

	public void setAmtGuarantee(String amtGuarantee) {

		this.amtGuarantee = amtGuarantee;

	}

	private String currencyGuarantee = "";

	public String getCurrencyGuarantee() {

		return this.currencyGuarantee;

	}

	public void setCurrencyGuarantee(String currencyGuarantee) {

		this.currencyGuarantee = currencyGuarantee;

	}

	private String dateGuarantee = "";

	public String getDateGuarantee() {

		return this.dateGuarantee;

	}

	public void setDateGuarantee(String dateGuarantee) {

		this.dateGuarantee = dateGuarantee;

	}

	private String secIssueBank = "";

	public String getSecIssueBank() {

		return this.secIssueBank;

	}

	public void setSecIssueBank(String secIssueBank) {

		this.secIssueBank = secIssueBank;

	}

	private String countrySecurityIssBank = "";

	public String getCountrySecurityIssBank() {

		return this.countrySecurityIssBank;

	}

	public void setCountrySecurityIssBank(String countrySecurityIssBank) {

		this.countrySecurityIssBank = countrySecurityIssBank;

	}

	private String bankCountryRiskAppr = "";

	public String getBankCountryRiskAppr() {

		return this.bankCountryRiskAppr;

	}

	public void setBankCountryRiskAppr(String bankCountryRiskAppr) {

		this.bankCountryRiskAppr = bankCountryRiskAppr;

	}

	private String beneName = "";

	public String getBeneName() {

		return this.beneName;

	}

	public void setBeneName(String beneName) {

		this.beneName = beneName;

	}

	private String holdingPeriod = "";

	public String getHoldingPeriod() {

		return this.holdingPeriod;

	}

	public void setHoldingPeriod(String holdingPeriod) {

		this.holdingPeriod = holdingPeriod;

	}

	private String holdingPeriodTimeUnit = "";

	public String getHoldingPeriodTimeUnit() {

		return this.holdingPeriodTimeUnit;

	}

	public void setHoldingPeriodTimeUnit(String holdingPeriodTimeUnit) {

		this.holdingPeriodTimeUnit = holdingPeriodTimeUnit;

	}

	private String claimPeriod = "";

	public String getClaimPeriod() {

		return claimPeriod;

	}

	public void setClaimPeriod(String claimPeriod) {

		this.claimPeriod = claimPeriod;

	}

	private String claimPeriodUnit = "";

	public String getClaimPeriodUnit() {

		return claimPeriodUnit;

	}

	public void setClaimPeriodUnit(String claimPeriodUnit) {

		this.claimPeriodUnit = claimPeriodUnit;

	}

	private String claimDate = "";
	
	public String getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(String claimDate) {
		this.claimDate = claimDate;
	}

	public void reset() {

	}

	private String securedPortion = "";
	
	public String getSecuredPortion() {
		return securedPortion;
	}

	public void setSecuredPortion(String securedPortion) {
		this.securedPortion = securedPortion;
	}

	private String securedAmountOrigin = "";
	
	public String getSecuredAmountOrigin() {
		return securedAmountOrigin;
	}

	public void setSecuredAmountOrigin(String securedAmountOrigin) {
		this.securedAmountOrigin = securedAmountOrigin;
	}

	private String unsecuredPortion = "";
	
	public String getUnsecuredPortion() {
		return unsecuredPortion;
	}

	public void setUnsecuredPortion(String unsecuredPortion) {
		this.unsecuredPortion = unsecuredPortion;
	}

	private String unsecuredAmountOrigin = "";
	
	public String getUnsecuredAmountOrigin() {
		return unsecuredAmountOrigin;
	}

	public void setUnsecuredAmountOrigin(String unsecuredAmountOrigin) {
		this.unsecuredAmountOrigin = unsecuredAmountOrigin;
	}

	private String securedAmountCalc = "";
	
	public String getSecuredAmountCalc() {
		return securedAmountCalc;
	}

	public void setSecuredAmountCalc(String securedAmountCalc) {
		this.securedAmountCalc = securedAmountCalc;
	}

	private String unsecuredAmountCalc = "";
	
	public String getUnsecuredAmountCalc() {
		return unsecuredAmountCalc;
	}

	public void setUnsecuredAmountCalc(String unsecuredAmountCalc) {
		this.unsecuredAmountCalc = unsecuredAmountCalc;
	}

	private String guaranteeAmtCalc = "";

	public String getGuaranteeAmtCalc() {
		return guaranteeAmtCalc;
	}

	public void setGuaranteeAmtCalc(String guaranteeAmtCalc) {
		this.guaranteeAmtCalc = guaranteeAmtCalc;
	}
		
	private String exchangeControl = "";
	
	public String getExchangeControl() {
		return exchangeControl;
	}

	public void setExchangeControl(String exchangeControl) {
		this.exchangeControl = exchangeControl;
	}

	private String exchangeControlDate = "";

	public String getExchangeControlDate() {
		return exchangeControlDate;
	}

	public void setExchangeControlDate(String exchangeControlDate) {
		this.exchangeControlDate = exchangeControlDate;
	}

	private String comments = "";

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	private char iccRulesComplied = ' ';

	public char getIccRulesComplied() {
		return iccRulesComplied;
	}

	public void setIccRulesComplied(char iccRulesComplied) {
		this.iccRulesComplied = iccRulesComplied;
	}

	private char urdComplied = ' ';
	
	public char getUrdComplied() {
		return urdComplied;
	}

	public void setUrdComplied(char urdComplied) {
		this.urdComplied = urdComplied;
	}

	private char ucp600Complied = ' ';

	public char getUcp600Complied() {
		return ucp600Complied;
	}

	public void setUcp600Complied(char ucp600Complied) {
		this.ucp600Complied = ucp600Complied;
	}
	
	private String issuingDate = "";

	public String getIssuingDate() {
		return issuingDate;
	}

	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}
	
	public String subtypeOfCorp = "";

	public String getSubtypeOfCorp() {
		return subtypeOfCorp;
	}

	public void setSubtypeOfCorp(String subtypeOfCorp) {
		this.subtypeOfCorp = subtypeOfCorp;
	}
	
	public String reimbursementBankEntryCode="";

	public String getReimbursementBankEntryCode() {
		return reimbursementBankEntryCode;
	}

	public void setReimbursementBankEntryCode(String reimbursementBankEntryCode) {
		this.reimbursementBankEntryCode = reimbursementBankEntryCode;
	}
	
	 // New Fields added by Dattatray Thorat for Guarantee security on 14/07/2011
	
	private String telephoneNumber;
    
    private String guarantersDunsNumber;
    
    private String guarantersPam;
    
    private String guarantersName;
    
    private String guarantersNamePrefix;
    
    private String guarantersFullName;
    
    private String addressLine1;
    
    private String addressLine2;
    
    private String addressLine3;
    
    private String city;
    
    private String state;
    
    private String region; 
    
    private String country;
    
    private String telephoneAreaCode;
    
    private String rating;
    
    private String recourse;
    
    private String discriptionOfAssets;
    
    private String followUpDate;
    
    private String totalLineLevelSecurityOMV;

	/**
	 * @return the telephoneNumber
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * @param telephoneNumber the telephoneNumber to set
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	/**
	 * @return the guarantersDunsNumber
	 */
	public String getGuarantersDunsNumber() {
		return guarantersDunsNumber;
	}

	/**
	 * @param guarantersDunsNumber the guarantersDunsNumber to set
	 */
	public void setGuarantersDunsNumber(String guarantersDunsNumber) {
		this.guarantersDunsNumber = guarantersDunsNumber;
	}

	/**
	 * @return the guarantersPam
	 */
	public String getGuarantersPam() {
		return guarantersPam;
	}

	/**
	 * @param guarantersPam the guarantersPam to set
	 */
	public void setGuarantersPam(String guarantersPam) {
		this.guarantersPam = guarantersPam;
	}

	/**
	 * @return the guarantersName
	 */
	public String getGuarantersName() {
		return guarantersName;
	}

	/**
	 * @param guarantersName the guarantersName to set
	 */
	public void setGuarantersName(String guarantersName) {
		this.guarantersName = guarantersName;
	}

	/**
	 * @return the guarantersNamePrefix
	 */
	public String getGuarantersNamePrefix() {
		return guarantersNamePrefix;
	}

	/**
	 * @param guarantersNamePrefix the guarantersNamePrefix to set
	 */
	public void setGuarantersNamePrefix(String guarantersNamePrefix) {
		this.guarantersNamePrefix = guarantersNamePrefix;
	}

	/**
	 * @return the guarantersFullName
	 */
	public String getGuarantersFullName() {
		return guarantersFullName;
	}

	/**
	 * @param guarantersFullName the guarantersFullName to set
	 */
	public void setGuarantersFullName(String guarantersFullName) {
		this.guarantersFullName = guarantersFullName;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the addressLine3
	 */
	public String getAddressLine3() {
		return addressLine3;
	}

	/**
	 * @param addressLine3 the addressLine3 to set
	 */
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the telephoneAreaCode
	 */
	public String getTelephoneAreaCode() {
		return telephoneAreaCode;
	}

	/**
	 * @param telephoneAreaCode the telephoneAreaCode to set
	 */
	public void setTelephoneAreaCode(String telephoneAreaCode) {
		this.telephoneAreaCode = telephoneAreaCode;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the recourse
	 */
	public String getRecourse() {
		return recourse;
	}

	/**
	 * @param recourse the recourse to set
	 */
	public void setRecourse(String recourse) {
		this.recourse = recourse;
	}

	/**
	 * @return the discriptionOfAssets
	 */
	public String getDiscriptionOfAssets() {
		return discriptionOfAssets;
	}

	/**
	 * @param discriptionOfAssets the discriptionOfAssets to set
	 */
	public void setDiscriptionOfAssets(String discriptionOfAssets) {
		this.discriptionOfAssets = discriptionOfAssets;
	}
    
	 private String ramId;

	public String getRamId() {
		return ramId;
	}

	public void setRamId(String ramId) {
		this.ramId = ramId;
	}
	   
	private String assetStatement;
    
    private String guarantorType;
    
    private String district;
    
    private String pinCode;
    
    private String guarantorNature;

	public String getAssetStatement() {
		return assetStatement;
	}

	public void setAssetStatement(String assetStatement) {
		this.assetStatement = assetStatement;
	}

	public String getGuarantorType() {
		return guarantorType;
	}

	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getGuarantorNature() {
		return guarantorNature;
	}

	public void setGuarantorNature(String guarantorNature) {
		this.guarantorNature = guarantorNature;
	}

	public String getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(String followUpDate) {
		this.followUpDate = followUpDate;
	}

	public String getTotalLineLevelSecurityOMV() {
		return totalLineLevelSecurityOMV;
	}

	public void setTotalLineLevelSecurityOMV(String totalLineLevelSecurityOMV) {
		this.totalLineLevelSecurityOMV = totalLineLevelSecurityOMV;
	}
	    
}
