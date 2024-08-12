/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/OBGuaranteeCollateral.java,v 1.8 2006/04/10 07:06:57 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IFeeDetails;

/**
 * This class represents a Collateral of type Guarantee entity.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/04/10 07:06:57 $ Tag: $Name: $
 */
public class OBGuaranteeCollateral extends OBCollateral implements IGuaranteeCollateral {
	private String guaranteeCcyCode;

	private Amount guaranteeAmount;

	private Date guaranteeDate;

	private Date claimDate;

	private int securedPortion;

	private Amount securedAmountOrigin;

	private int unsecuredPortion;

	private Amount unsecuredAmountOrigin;

	private Amount securedAmountCalc;

	private Amount unsecuredAmountCalc;

	private Amount guaranteeAmtCalc;

	private String beneficiaryName;

	private String issuingBank;

	private String issuingBankCountry;

	private Date issuingDate;

	private String description;

	private String referenceNo;

	private String isBankCountryRiskApproval;

	private Amount minimalFSV;

	private String minimalFSVCcyCode;

	private String currentScheme;

	private String holdingPeriod;

	private String holdingPeriodTimeUnit;

	private String claimPeriod;

	private String claimPeriodUnit;

	private String comments;

	private char iCCRulesComplied;

	private char uRDComplied;

	private char uCP600Complied;

	private IFeeDetails[] FeeDetails;

    private String reimbursementBankCategoryCode;

    private String reimbursementBankEntryCode;
    
    
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
    
    private String  telephoneAreaCode;
    
    private String rating;
    
    private String recourse;
    
    private String discriptionOfAssets;
    
    private String ramId;
    
    private String assetStatement;
    
    private String guarantorType;
    
    private String district;
    
    private String pinCode;
    
    private String guarantorNature;
    
    private Date followUpDate;
    
    private ILineDetail[] lineDetails;
    
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

	public  String getRamId() {
		return ramId;
	}

	public void setRamId(String  ramId) {
		this.ramId = ramId;
	}

	/**
	 * Default Constructor.
	 */
	public OBGuaranteeCollateral() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IGuaranteeCollateral
	 */
	public OBGuaranteeCollateral(IGuaranteeCollateral obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}
	
	/**
	 * @return the telephoneNumber
	 */
	public String  getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * @param telephoneNumber the telephoneNumber to set
	 */
	public void setTelephoneNumber(String  telephoneNumber) {
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
	public  String getTelephoneAreaCode() {
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

	/**
	 * Get beneficiary name.
	 * 
	 * @return String
	 */
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public String getClaimPeriod() {
		return claimPeriod;
	}

	public String getClaimPeriodUnit() {
		return claimPeriodUnit;
	}

	public String getComments() {
		return comments;
	}

	public String getCurrentScheme() {
		return currentScheme;
	}

	/**
	 * Get description of guarantee.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	public IFeeDetails[] getFeeDetails() {
		return FeeDetails;
	}

	/**
	 * Get amount of guarantee.
	 * 
	 * @return Amount
	 */
	public Amount getGuaranteeAmount() {
		return guaranteeAmount;
	}

	public Amount getGuaranteeAmtCalc() {
		return guaranteeAmtCalc;
	}

	/**
	 * Get currency code for guarantee amount.
	 * 
	 * @return String
	 */
	public String getGuaranteeCcyCode() {
		return guaranteeCcyCode;
	}

	/**
	 * Get date of guarantee.
	 * 
	 * @return Date
	 */
	public Date getGuaranteeDate() {
		return guaranteeDate;
	}

	/**
	 * Get holding period.
	 * 
	 * @return Long
	 */
	public String getHoldingPeriod() {
		return holdingPeriod;
	}

	/**
	 * Get holding period time unit
	 * 
	 * @return String
	 */
	public String getHoldingPeriodTimeUnit() {
		return holdingPeriodTimeUnit;
	}

	public char getICCRulesComplied() {
		return iCCRulesComplied;
	}

	/**
	 * Get if it is bank country risk approval.
	 * 
	 * @return String
	 */
	public String getIsBankCountryRiskApproval() {
		return isBankCountryRiskApproval;
	}

	/**
	 * Get security issuing bank.
	 * 
	 * @return String
	 */
	public String getIssuingBank() {
		return issuingBank;
	}

	/**
	 * Get country of security issuing bank.
	 * 
	 * @return String
	 */
	public String getIssuingBankCountry() {
		return issuingBankCountry;
	}

	public Date getIssuingDate() {
		return issuingDate;
	}

	/**
	 * Get minimal FSV.
	 * 
	 * @return Amount
	 */
	public Amount getMinimalFSV() {
		return minimalFSV;
	}

	/**
	 * Get minimal FSV currency code.
	 * 
	 * @return String
	 */
	public String getMinimalFSVCcyCode() {
		return minimalFSVCcyCode;
	}

	/**
	 * Get reference number of guarantee.
	 * 
	 * @return String
	 */
	public String getReferenceNo() {
		return referenceNo;
	}

	public Amount getSecuredAmountCalc() {
		return securedAmountCalc;
	}

	public Amount getSecuredAmountOrigin() {
		return securedAmountOrigin;
	}

	public int getSecuredPortion() {
		return securedPortion;
	}

	public char getUCP600Complied() {
		return uCP600Complied;
	}

	public Amount getUnsecuredAmountCalc() {
		return unsecuredAmountCalc;
	}

	public Amount getUnsecuredAmountOrigin() {
		return unsecuredAmountOrigin;
	}

	public int getUnsecuredPortion() {
		return unsecuredPortion;
	}

	public char getURDComplied() {
		return uRDComplied;
	}

    public String getReimbursementBankCategoryCode() {
        return reimbursementBankCategoryCode;
    }

    public String getReimbursementBankEntryCode() {
        return reimbursementBankEntryCode;
    }
    
    public Date getFollowUpDate() {
    	return followUpDate;
    }

	/**
	 * Set beneficiary name.
	 * 
	 * @param beneficiaryName of type String
	 */
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public void setClaimPeriod(String claimPeriod) {
		this.claimPeriod = claimPeriod;
	}

	public void setClaimPeriodUnit(String claimPeriodUnit) {
		this.claimPeriodUnit = claimPeriodUnit;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCurrentScheme(String currentScheme) {
		this.currentScheme = currentScheme;
	}

	/**
	 * Set description of guarantee.
	 * 
	 * @param description of type String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public void setFeeDetails(IFeeDetails[] IFeeDetails) {
		this.FeeDetails = IFeeDetails;
	}

	/**
	 * Set amount of guarantee.
	 * 
	 * @param guaranteeAmount of type Amount
	 */
	public void setGuaranteeAmount(Amount guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	public void setGuaranteeAmtCalc(Amount guaranteeAmtCalc) {
		this.guaranteeAmtCalc = guaranteeAmtCalc;
	}

	/**
	 * Set currency code for guarantee amount.
	 * 
	 * @param guaranteeCcyCode of type String
	 */
	public void setGuaranteeCcyCode(String guaranteeCcyCode) {
		this.guaranteeCcyCode = guaranteeCcyCode;
	}

	/**
	 * Set date of guarantee.
	 * 
	 * @param guaranteeDate of type Date
	 */
	public void setGuaranteeDate(Date guaranteeDate) {
		this.guaranteeDate = guaranteeDate;
	}

	/**
	 * Set holding period.
	 * 
	 * @param holdingPeriod of type Long
	 */
	public void setHoldingPeriod(String holdingPeriod) {
		this.holdingPeriod = holdingPeriod;
	}

	/**
	 * Set holding period time unit.
	 * 
	 * @param holdingPeriodTimeUnit of type String
	 */
	public void setHoldingPeriodTimeUnit(String holdingPeriodTimeUnit) {
		this.holdingPeriodTimeUnit = holdingPeriodTimeUnit;
	}

	public void setICCRulesComplied(char rulesComplied) {
		iCCRulesComplied = rulesComplied;
	}

	/**
	 * Set if it is bank country approval.
	 * 
	 * @param isBankCountryRiskApproval of type String
	 */
	public void setIsBankCountryRiskApproval(String isBankCountryRiskApproval) {
		this.isBankCountryRiskApproval = isBankCountryRiskApproval;
	}

	/**
	 * Set security issuing bank.
	 * 
	 * @param issuingBank of type String
	 */
	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}

	/**
	 * Set country of security issuing bank.
	 * 
	 * @param issuingBankCountry of type String
	 */
	public void setIssuingBankCountry(String issuingBankCountry) {
		this.issuingBankCountry = issuingBankCountry;
	}

	public void setIssuingDate(Date issuingDate) {
		this.issuingDate = issuingDate;
	}

	/**
	 * Set minimal FSV.
	 * 
	 * @param minimalFSV of type Amount
	 */
	public void setMinimalFSV(Amount minimalFSV) {
		this.minimalFSV = minimalFSV;
	}

	/**
	 * Set minimal FSV currency code.
	 * 
	 * @param minimalFSVCcyCode of type String
	 */
	public void setMinimalFSVCcyCode(String minimalFSVCcyCode) {
		this.minimalFSVCcyCode = minimalFSVCcyCode;
	}

	/**
	 * Set reference no of guarantee.
	 * 
	 * @param referenceNo of type String
	 */
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public void setSecuredAmountCalc(Amount securedAmountCalc) {
		this.securedAmountCalc = securedAmountCalc;
	}

	public void setSecuredAmountOrigin(Amount securedAmountOrigin) {
		this.securedAmountOrigin = securedAmountOrigin;
	}

	public void setSecuredPortion(int securedPortion) {
		this.securedPortion = securedPortion;
	}

	public void setUCP600Complied(char complied) {
		uCP600Complied = complied;
	}

	public void setUnsecuredAmountCalc(Amount unsecuredAmountCalc) {
		this.unsecuredAmountCalc = unsecuredAmountCalc;
	}

	public void setUnsecuredAmountOrigin(Amount unsecuredAmountOrigin) {
		this.unsecuredAmountOrigin = unsecuredAmountOrigin;
	}

	public void setUnsecuredPortion(int unsecuredPortion) {
		this.unsecuredPortion = unsecuredPortion;
	}

	public void setURDComplied(char complied) {
		uRDComplied = complied;
	}

    public void setReimbursementBankCategoryCode(String reimbursementBankCategoryCode) {
        this.reimbursementBankCategoryCode = reimbursementBankCategoryCode;
    }

    public void setReimbursementBankEntryCode(String reimbursementBankEntryCode) {
        this.reimbursementBankEntryCode = reimbursementBankEntryCode;
    }
    
    public void setFollowUpDate(Date followUpDate) {
    	this.followUpDate = followUpDate;
    }

	public ILineDetail[] getLineDetails() {
		return lineDetails;
	}

	public void setLineDetails(ILineDetail[] lineDetails) {
		this.lineDetails = lineDetails;
	}
    
    /**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBGuaranteeCollateral)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}