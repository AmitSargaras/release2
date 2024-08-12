//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION

package com.integrosys.cms.ui.collateral.document.docagreement;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.document.DocumentForm;

/**
 * 
 * @author Thurein
 * @since  2/Sep/2008	
 *
 */

public class DocAgreementForm extends DocumentForm implements Serializable {

	
	public static final String DOCAgreementMAPPER = "com.integrosys.cms.ui.collateral.document.docagreement.DocAgreementMapper";
	
	public void reset() {

		super.reset();

	}
	
	

	public String[][] getMapper() {

		String[][] input = {

		{ "form.collateralObject", DOCAgreementMAPPER },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
		{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" },
		};

		return input;

	}
	
	private String collateralName="";
	private String issuer="";
	private String dateOfLeaseAggrement="";
	private String dateExchangeControlApprovalObtained="";
	private String leaseRentalAgreement="";
	private String limitationOfLease="";
	private String propertyType="";
	private String locationOfLots="";
	private String titleNumber;
	private String buybackValue="";
	private String borrowerDependencyOnCollateral;
	private String guranteeAmount="";
	private String leaseType;
	private String collateralDescription="";
	private String securityPerfectionDate="";
	private String titleNumberValue="";

	public String getCollateralName() {
		return collateralName;
	}



	public void setCollateralName(String collateralName) {
		this.collateralName = collateralName;
	}



	public String getIssuer() {
		return issuer;
	}



	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}



	public String getDateOfLeaseAggrement() {
		return dateOfLeaseAggrement;
	}



	public void setDateOfLeaseAggrement(String dateOfLeaseAggrement) {
		this.dateOfLeaseAggrement = dateOfLeaseAggrement;
	}



	public String getDateExchangeControlApprovalObtained() {
		return dateExchangeControlApprovalObtained;
	}



	public void setDateExchangeControlApprovalObtained(
			String dateExchangeControlApprovalObtained) {
		this.dateExchangeControlApprovalObtained = dateExchangeControlApprovalObtained;
	}



	public String getLeaseRentalAgreement() {
		return leaseRentalAgreement;
	}



	public void setLeaseRentalAgreement(String leaseRentalAgreement) {
		this.leaseRentalAgreement = leaseRentalAgreement;
	}



	public String getLimitationOfLease() {
		return limitationOfLease;
	}



	public void setLimitationOfLease(String limitationOfLease) {
		this.limitationOfLease = limitationOfLease;
	}



	public String getPropertyType() {
		return propertyType;
	}



	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}



	public String getLocationOfLots() {
		return locationOfLots;
	}



	public void setLocationOfLots(String locationOfLots) {
		this.locationOfLots = locationOfLots;
	}



	public String getTitleNumber() {
		return titleNumber;
	}



	public void setTitleNumber(String titleNumber) {
		this.titleNumber = titleNumber;
	}



	public String getBuybackValue() {
		return buybackValue;
	}



	public void setBuybackValue(String buybackValue) {
		this.buybackValue = buybackValue;
	}



	public String getBorrowerDependencyOnCollateral() {
		return borrowerDependencyOnCollateral;
	}



	public void setBorrowerDependencyOnCollateral(
			String borrowerDependencyOnCollateral) {
		this.borrowerDependencyOnCollateral = borrowerDependencyOnCollateral;
	}



	public String getGuranteeAmount() {
		return guranteeAmount;
	}



	public void setGuranteeAmount(String guranteeAmount) {
		this.guranteeAmount = guranteeAmount;
	}



	public String getLeaseType() {
		return leaseType;
	}



	public void setLeaseType(String leaseType) {
		this.leaseType = leaseType;
	}



	public String getCollateralDescription() {
		return collateralDescription;
	}



	public void setCollateralDescription(String collateralDescription) {
		this.collateralDescription = collateralDescription;
	}



	public String getSecurityPerfectionDate() {
		return securityPerfectionDate;
	}



	public void setSecurityPerfectionDate(String securityPerfectionDate) {
		this.securityPerfectionDate = securityPerfectionDate;
	}



	public String getTitleNumberValue() {
		return titleNumberValue;
	}



	public void setTitleNumberValue(String titleNumberValue) {
		this.titleNumberValue = titleNumberValue;
	}

}
