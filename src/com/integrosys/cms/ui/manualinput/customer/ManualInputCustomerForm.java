/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.manualinput.customer;

import java.io.Serializable;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Manual Input Customer Form.
 * 
 * @author $Author: Jerlin, Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ManualInputCustomerForm extends TrxContextForm implements Serializable {

	private String legalId = "";
	private String leIDType = "";
	private String customerName;
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Description : get method for form to get the Legal ID No
	 * 
	 * @return legalId
	 */
	public String getLegalId() {
		return legalId;
	}

	/**
	 * Description : set the Legal ID No
	 * 
	 * @param legalId is the Legal ID
	 */
	public void setLegalId(String legalId) {
		this.legalId = legalId;
	}

	/**
	 * Description : get method for form to get the Legal ID Type
	 * 
	 * @return leIDType
	 */
	public String getLeIDType() {
		return leIDType;
	}

	/**
	 * Description : set the Legal ID Type
	 * 
	 * @param leIDType is the Legal ID leIDType
	 */
	public void setLeIDType(String leIDType) {
		this.leIDType = leIDType;
	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */
	
		// Sandeep Shinde Commented on 28-Feb-2011
	/*	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };

		return input;

	}*/

	// Sandeep Shinde Added on 28-Feb-2011
	
	public String[][] getMapper() {
		DefaultLogger.debug(this,"Getting Mapper");
		String[][] input = {
					{ "OBTrxContext", TRX_MAPPER },
					{ "customerInfoObj", CUSTOMER_INFO_MAPPER }		
				};
		return input;
	}

	public static final String CUSTOMER_INFO_MAPPER = "com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	/** 
	 * @author sandiip.shinde
	 * Added Following Properties and its Getter/Setter Methods on 3-March-2011  
	 */
	
static final long serialVersionUID = 0L;
	
//	private String cifId;
//	private String customerName;
//	private String groupIdNo;
//	private String groupName;	
//	private String shortName;
//	private String countryOfOperation;	
//	private String countryOfInCorporation;	
//	private String idType;
//	private String idNo;	
//	private String dateOfInCorporation;
//	private String industryCode;
//	private String relationshipManager;	
//	private String creditFirstGrantedDate;
//	private String lineOfActivity;
//	private String branchName;
//	private String customerType;
//	private String accountClarification;
//	private String customerConstitution;	
//	
//	private String registerdAddressLine1;	
//	private String registerdAddressLine2;
//	private String registerdPostalCode;
//	private String country;
//	private String state;
//	private String city;	
//	private String telephoneNo;	
//	private String faxNo;
//	
//	private String contactPerson;
//	private String emailId;		
//	private String communicationAddressLine1;	
//	private String communicationAddressLine2;
//	private String communicationPostalCode;
//	
//	private String yearOfBusinessCommencemant;
//	private String noOfYearInOperation;
//	private String noOFFulltimeEmployees;
//	private String averageAnnualTurnover;
//	private String currentPaidupCapital;	
//	private String otherAccountsWithBank;
//	private String otherGroupCompanies;
//	private String grossInvestment;
//	
//	private String limitId;
//	private String groupExposure;
//	private String requiredFacilitySecurityCoverage;
//	private String actualFacilitySecurityCoverage;
//	
//	private String customerCategoryCode;	
//	private String natureOfAdvance;
//	private String sectorCode;	
//	private String subSectorCode;
//	
//	private String segmentCode;	
//	private String exposureCategory;
//	private String siteVisitDate;
//	private String rbiDefaultersList;
//	
//	private String currentInternalRating;
//	private String originalInternalRating;
//	private String currentInternalRatingEffectiveDate;
//	private String originalInternalRatingEffectiveDate;
//	
//	private String crisilExternalCreditGrade;
//	private String reasonForChange;
//	private String externalCreditGradeEffectiveDate;
//  
//	private String versionTime;
//    private String creationDate;
//    private String createBy;
//    private String updateDate;
//    private String updateBy;
//    private String deprecated;
//    private String masterId;
//    
//	public String getCifId() {
//		return cifId;
//	}
//
//	public void setCifId(String cifId) {
//		this.cifId = cifId;
//	}
//
//	public String getGroupIdNo() {
//		return groupIdNo;
//	}
//
//	public void setGroupIdNo(String groupIdNo) {
//		this.groupIdNo = groupIdNo;
//	}
//
//	public String getGroupName() {
//		return groupName;
//	}
//
//	public void setGroupName(String groupName) {
//		this.groupName = groupName;
//	}
//
//	public String getShortName() {
//		return shortName;
//	}
//
//	public void setShortName(String shortName) {
//		this.shortName = shortName;
//	}
//
//	public String getCountryOfOperation() {
//		return countryOfOperation;
//	}
//
//	public void setCountryOfOperation(String countryOfOperation) {
//		this.countryOfOperation = countryOfOperation;
//	}
//
//	public String getCountryOfInCorporation() {
//		return countryOfInCorporation;
//	}
//
//	public void setCountryOfInCorporation(String countryOfInCorporation) {
//		this.countryOfInCorporation = countryOfInCorporation;
//	}
//
//	public String getIdType() {
//		return idType;
//	}
//
//	public void setIdType(String idType) {
//		this.idType = idType;
//	}
//
//	public String getIdNo() {
//		return idNo;
//	}
//
//	public void setIdNo(String idNo) {
//		this.idNo = idNo;
//	}
//
//	public String getDateOfInCorporation() {
//		return dateOfInCorporation;
//	}
//
//	public void setDateOfInCorporation(String dateOfInCorporation) {
//		this.dateOfInCorporation = dateOfInCorporation;
//	}
//
//	public String getIndustryCode() {
//		return industryCode;
//	}
//
//	public void setIndustryCode(String industryCode) {
//		this.industryCode = industryCode;
//	}
//
//	public String getRelationshipManager() {
//		return relationshipManager;
//	}
//
//	public void setRelationshipManager(String relationshipManager) {
//		this.relationshipManager = relationshipManager;
//	}
//
//	public String getCreditFirstGrantedDate() {
//		return creditFirstGrantedDate;
//	}
//
//	public void setCreditFirstGrantedDate(String creditFirstGrantedDate) {
//		this.creditFirstGrantedDate = creditFirstGrantedDate;
//	}
//
//	public String getLineOfActivity() {
//		return lineOfActivity;
//	}
//
//	public void setLineOfActivity(String lineOfActivity) {
//		this.lineOfActivity = lineOfActivity;
//	}
//
//	public String getBranchName() {
//		return branchName;
//	}
//
//	public void setBranchName(String branchName) {
//		this.branchName = branchName;
//	}
//
//	public String getCustomerType() {
//		return customerType;
//	}
//
//	public void setCustomerType(String customerType) {
//		this.customerType = customerType;
//	}
//
//	public String getAccountClarification() {
//		return accountClarification;
//	}
//
//	public void setAccountClarification(String accountClarification) {
//		this.accountClarification = accountClarification;
//	}
//
//	public String getCustomerConstitution() {
//		return customerConstitution;
//	}
//
//	public void setCustomerConstitution(String customerConstitution) {
//		this.customerConstitution = customerConstitution;
//	}
//
//	public String getRegisterdAddressLine1() {
//		return registerdAddressLine1;
//	}
//
//	public void setRegisterdAddressLine1(String registerdAddressLine1) {
//		this.registerdAddressLine1 = registerdAddressLine1;
//	}
//
//	public String getRegisterdAddressLine2() {
//		return registerdAddressLine2;
//	}
//
//	public void setRegisterdAddressLine2(String registerdAddressLine2) {
//		this.registerdAddressLine2 = registerdAddressLine2;
//	}
//
//	public String getRegisterdPostalCode() {
//		return registerdPostalCode;
//	}
//
//	public void setRegisterdPostalCode(String registerdPostalCode) {
//		this.registerdPostalCode = registerdPostalCode;
//	}
//
//	public String getCountry() {
//		return country;
//	}
//
//	public void setCountry(String country) {
//		this.country = country;
//	}
//
//	public String getState() {
//		return state;
//	}
//
//	public void setState(String state) {
//		this.state = state;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getTelephoneNo() {
//		return telephoneNo;
//	}
//
//	public void setTelephoneNo(String telephoneNo) {
//		this.telephoneNo = telephoneNo;
//	}
//
//	public String getFaxNo() {
//		return faxNo;
//	}
//
//	public void setFaxNo(String faxNo) {
//		this.faxNo = faxNo;
//	}
//
//	public String getContactPerson() {
//		return contactPerson;
//	}
//
//	public void setContactPerson(String contactPerson) {
//		this.contactPerson = contactPerson;
//	}
//
//	public String getEmailId() {
//		return emailId;
//	}
//
//	public void setEmailId(String emailId) {
//		this.emailId = emailId;
//	}
//
//	public String getCommunicationAddressLine1() {
//		return communicationAddressLine1;
//	}
//
//	public void setCommunicationAddressLine1(String communicationAddressLine1) {
//		this.communicationAddressLine1 = communicationAddressLine1;
//	}
//
//	public String getCommunicationAddressLine2() {
//		return communicationAddressLine2;
//	}
//
//	public void setCommunicationAddressLine2(String communicationAddressLine2) {
//		this.communicationAddressLine2 = communicationAddressLine2;
//	}
//
//	public String getCommunicationPostalCode() {
//		return communicationPostalCode;
//	}
//
//	public void setCommunicationPostalCode(String communicationPostalCode) {
//		this.communicationPostalCode = communicationPostalCode;
//	}
//
//	public String getYearOfBusinessCommencemant() {
//		return yearOfBusinessCommencemant;
//	}
//
//	public void setYearOfBusinessCommencemant(String yearOfBusinessCommencemant) {
//		this.yearOfBusinessCommencemant = yearOfBusinessCommencemant;
//	}
//
//	public String getNoOfYearInOperation() {
//		return noOfYearInOperation;
//	}
//
//	public void setNoOfYearInOperation(String noOfYearInOperation) {
//		this.noOfYearInOperation = noOfYearInOperation;
//	}
//
//	public String getNoOFFulltimeEmployees() {
//		return noOFFulltimeEmployees;
//	}
//
//	public void setNoOFFulltimeEmployees(String noOFFulltimeEmployees) {
//		this.noOFFulltimeEmployees = noOFFulltimeEmployees;
//	}
//
//	public String getAverageAnnualTurnover() {
//		return averageAnnualTurnover;
//	}
//
//	public void setAverageAnnualTurnover(String averageAnnualTurnover) {
//		this.averageAnnualTurnover = averageAnnualTurnover;
//	}
//
//	public String getCurrentPaidupCapital() {
//		return currentPaidupCapital;
//	}
//
//	public void setCurrentPaidupCapital(String currentPaidupCapital) {
//		this.currentPaidupCapital = currentPaidupCapital;
//	}
//
//	public String getOtherAccountsWithBank() {
//		return otherAccountsWithBank;
//	}
//
//	public void setOtherAccountsWithBank(String otherAccountsWithBank) {
//		this.otherAccountsWithBank = otherAccountsWithBank;
//	}
//
//	public String getOtherGroupCompanies() {
//		return otherGroupCompanies;
//	}
//
//	public void setOtherGroupCompanies(String otherGroupCompanies) {
//		this.otherGroupCompanies = otherGroupCompanies;
//	}
//
//	public String getGrossInvestment() {
//		return grossInvestment;
//	}
//
//	public void setGrossInvestment(String grossInvestment) {
//		this.grossInvestment = grossInvestment;
//	}
//
//	public String getLimitId() {
//		return limitId;
//	}
//
//	public void setLimitId(String limitId) {
//		this.limitId = limitId;
//	}
//
//	public String getGroupExposure() {
//		return groupExposure;
//	}
//
//	public void setGroupExposure(String groupExposure) {
//		this.groupExposure = groupExposure;
//	}
//
//	public String getRequiredFacilitySecurityCoverage() {
//		return requiredFacilitySecurityCoverage;
//	}
//
//	public void setRequiredFacilitySecurityCoverage(
//			String requiredFacilitySecurityCoverage) {
//		this.requiredFacilitySecurityCoverage = requiredFacilitySecurityCoverage;
//	}
//
//	public String getActualFacilitySecurityCoverage() {
//		return actualFacilitySecurityCoverage;
//	}
//
//	public void setActualFacilitySecurityCoverage(
//			String actualFacilitySecurityCoverage) {
//		this.actualFacilitySecurityCoverage = actualFacilitySecurityCoverage;
//	}
//
//	public String getCustomerCategoryCode() {
//		return customerCategoryCode;
//	}
//
//	public void setCustomerCategoryCode(String customerCategoryCode) {
//		this.customerCategoryCode = customerCategoryCode;
//	}
//
//	public String getNatureOfAdvance() {
//		return natureOfAdvance;
//	}
//
//	public void setNatureOfAdvance(String natureOfAdvance) {
//		this.natureOfAdvance = natureOfAdvance;
//	}
//
//	public String getSectorCode() {
//		return sectorCode;
//	}
//
//	public void setSectorCode(String sectorCode) {
//		this.sectorCode = sectorCode;
//	}
//
//	public String getSubSectorCode() {
//		return subSectorCode;
//	}
//
//	public void setSubSectorCode(String subSectorCode) {
//		this.subSectorCode = subSectorCode;
//	}
//
//	public String getSegmentCode() {
//		return segmentCode;
//	}
//
//	public void setSegmentCode(String segmentCode) {
//		this.segmentCode = segmentCode;
//	}
//
//	public String getExposureCategory() {
//		return exposureCategory;
//	}
//
//	public void setExposureCategory(String exposureCategory) {
//		this.exposureCategory = exposureCategory;
//	}
//
//	public String getSiteVisitDate() {
//		return siteVisitDate;
//	}
//
//	public void setSiteVisitDate(String siteVisitDate) {
//		this.siteVisitDate = siteVisitDate;
//	}
//
//	public String getRbiDefaultersList() {
//		return rbiDefaultersList;
//	}
//
//	public void setRbiDefaultersList(String rbiDefaultersList) {
//		this.rbiDefaultersList = rbiDefaultersList;
//	}
//
//	public String getCurrentInternalRating() {
//		return currentInternalRating;
//	}
//
//	public void setCurrentInternalRating(String currentInternalRating) {
//		this.currentInternalRating = currentInternalRating;
//	}
//
//	public String getOriginalInternalRating() {
//		return originalInternalRating;
//	}
//
//	public void setOriginalInternalRating(String originalInternalRating) {
//		this.originalInternalRating = originalInternalRating;
//	}
//
//	public String getCurrentInternalRatingEffectiveDate() {
//		return currentInternalRatingEffectiveDate;
//	}
//
//	public void setCurrentInternalRatingEffectiveDate(
//			String currentInternalRatingEffectiveDate) {
//		this.currentInternalRatingEffectiveDate = currentInternalRatingEffectiveDate;
//	}
//
//	public String getOriginalInternalRatingEffectiveDate() {
//		return originalInternalRatingEffectiveDate;
//	}
//
//	public void setOriginalInternalRatingEffectiveDate(
//			String originalInternalRatingEffectiveDate) {
//		this.originalInternalRatingEffectiveDate = originalInternalRatingEffectiveDate;
//	}
//
//	public String getCrisilExternalCreditGrade() {
//		return crisilExternalCreditGrade;
//	}
//
//	public void setCrisilExternalCreditGrade(String crisilExternalCreditGrade) {
//		this.crisilExternalCreditGrade = crisilExternalCreditGrade;
//	}
//
//	public String getReasonForChange() {
//		return reasonForChange;
//	}
//
//	public void setReasonForChange(String reasonForChange) {
//		this.reasonForChange = reasonForChange;
//	}
//
//	public String getExternalCreditGradeEffectiveDate() {
//		return externalCreditGradeEffectiveDate;
//	}
//
//	public void setExternalCreditGradeEffectiveDate(
//			String externalCreditGradeEffectiveDate) {
//		this.externalCreditGradeEffectiveDate = externalCreditGradeEffectiveDate;
//	}
//
//	public String getVersionTime() {
//		return versionTime;
//	}
//
//	public void setVersionTime(String versionTime) {
//		this.versionTime = versionTime;
//	}
//
//	public String getCreationDate() {
//		return creationDate;
//	}
//
//	public void setCreationDate(String creationDate) {
//		this.creationDate = creationDate;
//	}
//
//	public String getCreateBy() {
//		return createBy;
//	}
//
//	public void setCreateBy(String createBy) {
//		this.createBy = createBy;
//	}
//
//	public String getUpdateDate() {
//		return updateDate;
//	}
//
//	public void setUpdateDate(String updateDate) {
//		this.updateDate = updateDate;
//	}
//
//	public String getUpdateBy() {
//		return updateBy;
//	}
//
//	public void setUpdateBy(String updateBy) {
//		this.updateBy = updateBy;
//	}
//
//	public String getDeprecated() {
//		return deprecated;
//	}
//
//	public void setDeprecated(String deprecated) {
//		this.deprecated = deprecated;
//	}
//
//	public String getMasterId() {
//		return masterId;
//	}
//
//	public void setMasterId(String masterId) {
//		this.masterId = masterId;
//	}
}
