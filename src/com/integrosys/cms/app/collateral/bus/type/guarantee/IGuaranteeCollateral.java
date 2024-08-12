/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/IGuaranteeCollateral.java,v 1.8 2006/04/10 07:06:57 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IFeeDetails;

/**
 * This interface represents a Collateral of type Guarantee
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/04/10 07:06:57 $ Tag: $Name: $
 */
public interface IGuaranteeCollateral extends ICollateral {
	public String getBeneficiaryName();

	/**
	 * Get date of guarantee.
	 * 
	 * @return Date
	 */
	public Date getClaimDate();

	public String getClaimPeriod();

	public String getClaimPeriodUnit();

	public String getComments();

	public String getCurrentScheme();

	/**
	 * Get description of guarantee.
	 * 
	 * @return String
	 */
	public String getDescription();

	/**
	 * Get Fee Details information.
	 * 
	 * @return a list of feeDetails
	 */
	public IFeeDetails[] getFeeDetails();

	/**
	 * Get amount of guarantee.
	 * 
	 * @return Amount
	 */
	public Amount getGuaranteeAmount();
	
	public Amount getGuaranteeAmtCalc();
	
	/**
	 * Get currency code for guarantee amount.
	 * 
	 * @return String
	 */
	public String getGuaranteeCcyCode();
	
	/**
	 * Get date of guarantee.
	 * 
	 * @return Date
	 */
	public Date getGuaranteeDate();
	
	/**
	 * Get holding period.
	 * 
	 * @return Long
	 */
	public String getHoldingPeriod();
	
	/**
	 * Get holding period time unit.
	 * 
	 * @return String
	 */
	public String getHoldingPeriodTimeUnit();
	
	public char getICCRulesComplied();
	
	/**
	 * Get if it is bank country risk approval.
	 * 
	 * @return String
	 */
	public String getIsBankCountryRiskApproval();
	
	/**
	 * Get security issuing bank.
	 * 
	 * @return String
	 */
	public String getIssuingBank();
	
	/**
	 * Get country of security issuing bank.
	 * 
	 * @return String
	 */
	public String getIssuingBankCountry();
	
	public Date getIssuingDate();
	
	/**
	 * Get minimal FSV.
	 * 
	 * @return Amount
	 */
	public Amount getMinimalFSV();
	
	/**
	 * Get minimal FSV currency code.
	 * 
	 * @return String
	 */
	public String getMinimalFSVCcyCode();
	
	/**
	 * Get reference number of guarantee.
	 * 
	 * @return String
	 */
	public String getReferenceNo();
	
	public Amount getSecuredAmountCalc();
	
	public Amount getSecuredAmountOrigin();
	
	/**
	 * Get beneficiary name.
	 * 
	 * @return String
	 */
	
	public int getSecuredPortion();

	public char getUCP600Complied();

	public Amount getUnsecuredAmountCalc();

	public Amount getUnsecuredAmountOrigin();

	public int getUnsecuredPortion();

	public char getURDComplied();

    public String getReimbursementBankCategoryCode();
    public String getReimbursementBankEntryCode();
	/**
	 * Set beneficiary name.
	 * 
	 * @param beneficiaryName of type String
	 */
	public void setBeneficiaryName(String beneficiaryName);

	/**
	 * Set date of guarantee.
	 * 
	 * @param guaranteeDate of type Date
	 */
	public void setClaimDate(Date guaranteeDate);

	public void setClaimPeriod(String claimPeriod);

	public void setClaimPeriodUnit(String claimPeriodUnit);

	public void setComments(String comments);

	public void setCurrentScheme(String currentScheme);

	/**
	 * Set description of guarantee.
	 * 
	 * @param description of type String
	 */
	public void setDescription(String description);

	/**
	 * set Fee Details information.
	 * 
	 * @param feeDetails a list of cash deposit
	 */
	public void setFeeDetails(IFeeDetails[] feeDetails);

	/**
	 * Set amount of guarantee.
	 * 
	 * @param guaranteeAmount of type Amount
	 */
	public void setGuaranteeAmount(Amount guaranteeAmount);

	public void setGuaranteeAmtCalc(Amount guaranteeAmtCalc);

	/**
	 * Set currency code for guarantee amount.
	 * 
	 * @param guaranteeCcyCode of type String
	 */
	public void setGuaranteeCcyCode(String guaranteeCcyCode);

	/**
	 * Set date of guarantee.
	 * 
	 * @param guaranteeDate of type Date
	 */
	public void setGuaranteeDate(Date guaranteeDate);

	/**
	 * Set holding period.
	 * 
	 * @param holdingPeriod of type Long
	 */
	public void setHoldingPeriod(String holdingPeriod);

	/**
	 * Set holding period time unit.
	 * 
	 * @param holdingPeriodTimeUnit of type String
	 */
	public void setHoldingPeriodTimeUnit(String holdingPeriodTimeUnit);

	public void setICCRulesComplied(char iccRulesComplied);

	/**
	 * Set if it is bank country approval.
	 * 
	 * @param isBankCountryRiskApproval of type String
	 */
	public void setIsBankCountryRiskApproval(String isBankCountryRiskApproval);

	/**
	 * Set security issuing bank.
	 * 
	 * @param issuingBank of type String
	 */
	public void setIssuingBank(String issuingBank);

	/**
	 * Set country of security issuing bank.
	 * 
	 * @param issuingBankCountry of type String
	 */
	public void setIssuingBankCountry(String issuingBankCountry);

	public void setIssuingDate(Date issuingDate);

	/**
	 * Set minimal FSV.
	 * 
	 * @param minimalFSV of type Amount
	 */
	public void setMinimalFSV(Amount minimalFSV);
	
	/**
	 * Set minimal FSV currency code.
	 * 
	 * @param minimalFSVCcyCode of type String
	 */
	public void setMinimalFSVCcyCode(String minimalFSVCcyCode);
	
	/**
	 * Set reference no of guarantee.
	 * 
	 * @param referenceNo of type String
	 */
	public void setReferenceNo(String referenceNo);
	
	public void setSecuredAmountCalc(Amount securedAmountCalc);
	
	public void setSecuredAmountOrigin(Amount securedAmountOrigin);
	
	public void setSecuredPortion(int securedPortion);
	
	public void setUCP600Complied(char ucp600Complied);

	public void setUnsecuredAmountCalc(Amount unsecuredAmountCalc);
	
	public void setUnsecuredAmountOrigin(Amount unsecuredAmountOrigin);
	public void setUnsecuredPortion(int unsecuredPortion);
	public void setURDComplied(char urdComplied);

    public void setReimbursementBankCategoryCode(String reimbursementBankCategoryCode);
    public void setReimbursementBankEntryCode(String reimbursementBankEntryCode);
    
    
    //******* Methods added by Dattatray Thorat for Guarantee Security on 14/07/2011 
    
    /**
	 * @return the telephoneNumber
	 */
	public String getTelephoneNumber() ;

	/**
	 * @param telephoneNumber the telephoneNumber to set
	 */
	public void setTelephoneNumber(String telephoneNumber) ;

	/**
	 * @return the guarantersDunsNumber
	 */
	public String getGuarantersDunsNumber() ;

	/**
	 * @param guarantersDunsNumber the guarantersDunsNumber to set
	 */
	public void setGuarantersDunsNumber(String guarantersDunsNumber) ;

	/**
	 * @return the guarantersPam
	 */
	public String getGuarantersPam() ;

	/**
	 * @param guarantersPam the guarantersPam to set
	 */
	public void setGuarantersPam(String guarantersPam) ;

	/**
	 * @return the guarantersName
	 */
	public String getGuarantersName() ;

	/**
	 * @param guarantersName the guarantersName to set
	 */
	public void setGuarantersName(String guarantersName) ;

	/**
	 * @return the guarantersNamePrefix
	 */
	public String getGuarantersNamePrefix() ;

	/**
	 * @param guarantersNamePrefix the guarantersNamePrefix to set
	 */
	public void setGuarantersNamePrefix(String guarantersNamePrefix) ;

	/**
	 * @return the guarantersFullName
	 */
	public String getGuarantersFullName() ;

	/**
	 * @param guarantersFullName the guarantersFullName to set
	 */
	public void setGuarantersFullName(String guarantersFullName) ;

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() ;

	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) ;

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() ;

	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2);

	/**
	 * @return the addressLine3
	 */
	public String getAddressLine3() ;

	/**
	 * @param addressLine3 the addressLine3 to set
	 */
	public void setAddressLine3(String addressLine3) ;

	/**
	 * @return the city
	 */
	public String getCity() ;

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) ;

	/**
	 * @return the state
	 */
	public String getState();

	/**
	 * @param state the state to set
	 */
	public void setState(String state) ;

	/**
	 * @return the region
	 */
	public String getRegion();

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) ;

	/**
	 * @return the country
	 */
	public String getCountry();

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) ;

	/**
	 * @return the telephoneAreaCode
	 */
	public String getTelephoneAreaCode() ;

	/**
	 * @param telephoneAreaCode the telephoneAreaCode to set
	 */
	public void setTelephoneAreaCode(String telephoneAreaCode);

	/**
	 * @return the rating
	 */
	public String getRating() ;

	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) ;

	/**
	 * @return the recourse
	 */
	public String getRecourse() ;

	/**
	 * @param recourse the recourse to set
	 */
	public void setRecourse(String recourse) ;

	/**
	 * @return the discriptionOfAssets
	 */
	public String getDiscriptionOfAssets();

	/**
	 * @param discriptionOfAssets the discriptionOfAssets to set
	 */
	public void setDiscriptionOfAssets(String discriptionOfAssets) ;
	
	public String getRamId();

	public void setRamId(String ramId);
	
	public String getAssetStatement();

	public void setAssetStatement(String assetStatement);
	
	public String getGuarantorType();

	public void setGuarantorType(String guarantorType);
	
	public String getDistrict();

	public void setDistrict(String district);
	
	public String getPinCode();

	public void setPinCode(String pinCode);
	
	public String getGuarantorNature();

	public void setGuarantorNature(String guarantorNature);
	
	public Date getFollowUpDate();
	
	public void setFollowUpDate(Date followUpDate); 
	
	public ILineDetail[] getLineDetails();
	
	public void setLineDetails(ILineDetail[] lineDetails);
}
