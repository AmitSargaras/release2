/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/PropertySecurity.java,v 1.3 2003/12/08 03:07:39 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus.property;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents approved security of type Property.
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @since 2003/12/08
 */
public class PropertySecurity extends ApprovedSecurity {

	private static final long serialVersionUID = 8255834596084939621L;

	private String abandonedProject;

	private Double amountRedeem;

	private String assessment;

	private String AssessmentPaymentDate;

	private StandardCode assessmentPeriod;

	private Double assessmentRate;

	private Long assessmentYear;

	private String assetDescription;

	private String auctionDate;

	private Double auctionPrice;

	private String breachInd;

	private Double builtUpArea;

	private StandardCode builtUpAreaUOM;

	private StandardCode categoryOfLandUse;

	private String combinedValueInd;

	private StandardCode completionStage;

	private String dateChattelSold;

	private StandardCode developerName;

	private StandardCode district;

	private String expressedCondition;

	private String independentValuerFlag;

	private Double landArea;

	private StandardCode landAreaUOM;

	private Date lastPhysicalInspectionDate;

	private String longEstablishedMarketFlag;

	private String lotLocation;

	private String lotNo;

	private StandardCode lotNoType;

	private StandardCode mukim;

	private Date nextPhysicalInspectionDate;

	private String nextQuitRentDate;

	private StandardCode nonPreferredLocation;

	private String phaseNo;

	private String physicalInspectionFlag;

	private Short physicalInspectionFrequencyUnit;

	private String physicalInspectionFrequencyUOM;

	private String postCode;

	private Double pricePerUnit;

	private String privateCaveatGteeExpDate;

	private StandardCode projectName;

	private String propertyAddress;

	private String propertyAddress1;

	private String propertyAddress2;

	private String propertyAddress3;

	private String propertyCompletedFlag;

	private StandardCode propertyCompletionStatus;

	private String propertyHasMethodologyForValuationFlag;

	private StandardCode propertyType;

	private StandardCode propertyUsage;

	private String propertyWellDevelopedFlag;

	private Double quitRentAmountPaid;

	private String quitRentInd;

	private String quitRentPaymentDate;

	private StandardCode realEstateUsage;

	private String realEstateWithFewRentalUnitsFlag;

	private String reducedRiskWeightFlag;

	private String registeredHolderOwner;

	private Long remainingTenurePeriod;

	private String remarks;

	private String restrictionCondition;

	private String securityEnvironmentallyRisky;

	private Date securityEnvironmentallyRiskyConfirmedDate;

	private String securityEnvironmentallyRiskyRemarks;

	private String snpAgreementDate;

	private Double snpAgreementValue;

	private StandardCode state;

	private String taman;

	private Long tenure;

	private String tenureType;

	private String tenureUnit;

	private String titleNumber;

	private StandardCode titleNumberPrefix;

	private StandardCode titleType;

	private String unitParcelNo;

	private StandardCode storeyNo;

	private String sectionNo;

	private StandardCode developerGroupName;
	
	/*private long inSqftBuiltUpArea;
	
	private long inSqftLandArea;*/
	
	/*private Date tsrDate;
	
	private Date nextTsrDate;
	
	private String tsrFrequency;*/
	   
	private String constitution;
	
	public PropertySecurity() {
		super();
		combinedValueInd = "0";
	}

	public String getAbandonedProject() {
		return abandonedProject;
	}

	public Double getAmountRedeem() {
		return amountRedeem;
	}

	public String getAssessment() {
		return assessment;
	}

	public String getAssessmentPaymentDate() {
		return AssessmentPaymentDate;
	}

	public StandardCode getAssessmentPeriod() {
		return assessmentPeriod;
	}

	public Double getAssessmentRate() {
		return assessmentRate;
	}

	public Long getAssessmentYear() {
		return assessmentYear;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public String getAuctionDate() {
		return auctionDate;
	}

	public Double getAuctionPrice() {
		return this.auctionPrice;
	}

	public String getBreachInd() {
		return breachInd;
	}

	public Double getBuiltUpArea() {
		return builtUpArea;
	}

	public StandardCode getBuiltUpAreaUOM() {
		return builtUpAreaUOM;
	}

	public StandardCode getCategoryOfLandUse() {
		return categoryOfLandUse;
	}

	public String getCombinedValueInd() {
		return combinedValueInd;
	}

	public StandardCode getCompletionStage() {
		return completionStage;
	}

	public String getDateChattelSold() {
		return dateChattelSold;
	}

	public StandardCode getDeveloperGroupName() {
		return developerGroupName;
	}

	public StandardCode getDeveloperName() {
		return developerName;
	}

	public StandardCode getDistrict() {
		return district;
	}

	public String getExpressedCondition() {
		return expressedCondition;
	}

	public String getIndependentValuerFlag() {
		return independentValuerFlag;
	}

	public Date getJDOAssessmentPaymentDate() {
		return MessageDate.getInstance().getDate(AssessmentPaymentDate);
	}

	public Date getJDOAuctionDate() {
		return MessageDate.getInstance().getDate(auctionDate);
	}

	public Date getJDODateChattelSold() {
		return MessageDate.getInstance().getDate(dateChattelSold);
	}

	public Date getJDOLastPhysicalInspectionDate() {
		return lastPhysicalInspectionDate;
	}

	public Date getJDONextPhysicalInspectionDate() {
		return nextPhysicalInspectionDate;
	}

	public Date getJDONextQuitRentDate() {
		return MessageDate.getInstance().getDate(nextQuitRentDate);
	}

	public Date getJDOPrivateCaveatGteeExpDate() {
		return MessageDate.getInstance().getDate(privateCaveatGteeExpDate);
	}

	public Date getJDOQuitRentPaymentDate() {
		return MessageDate.getInstance().getDate(quitRentPaymentDate);
	}

	public Date getJDOSecurityEnvironmentallyRiskyConfirmedDate() {
		return securityEnvironmentallyRiskyConfirmedDate;
	}

	public Date getJDOSnpAgreementDate() {
		return MessageDate.getInstance().getDate(snpAgreementDate);
	}

	public Double getLandArea() {
		return landArea;
	}

	public StandardCode getLandAreaUOM() {
		return landAreaUOM;
	}

	public String getLastPhysicalInspectionDate() {
		return MessageDate.getInstance().getString(lastPhysicalInspectionDate);
	}

	public String getLongEstablishedMarketFlag() {
		return longEstablishedMarketFlag;
	}

	public String getLotLocation() {
		return lotLocation;
	}

	public String getLotNo() {
		return lotNo;
	}

	public StandardCode getLotNoType() {
		return lotNoType;
	}

	public StandardCode getMukim() {
		return mukim;
	}

	public String getNextPhysicalInspectionDate() {
		return MessageDate.getInstance().getString(nextPhysicalInspectionDate);
	}

	public String getNextQuitRentDate() {
		return nextQuitRentDate;
	}

	public StandardCode getNonPreferredLocation() {
		return nonPreferredLocation;
	}

	public String getPhaseNo() {
		return phaseNo;
	}

	public String getPhysicalInspectionFlag() {
		return physicalInspectionFlag;
	}

	public Short getPhysicalInspectionFrequencyUnit() {
		return physicalInspectionFrequencyUnit;
	}

	public String getPhysicalInspectionFrequencyUOM() {
		return physicalInspectionFrequencyUOM;
	}

	public String getPostCode() {
		return postCode;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public String getPrivateCaveatGteeExpDate() {
		return privateCaveatGteeExpDate;
	}

	public StandardCode getProjectName() {
		return projectName;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public String getPropertyAddress1() {
		return propertyAddress1;
	}

	public String getPropertyAddress2() {
		return propertyAddress2;
	}

	public String getPropertyAddress3() {
		return propertyAddress3;
	}

	public String getPropertyCompletedFlag() {
		return propertyCompletedFlag;
	}

	public StandardCode getPropertyCompletionStatus() {
		return propertyCompletionStatus;
	}

	public String getPropertyHasMethodologyForValuationFlag() {
		return propertyHasMethodologyForValuationFlag;
	}

	public StandardCode getPropertyType() {
		return propertyType;
	}

	public StandardCode getPropertyUsage() {
		return propertyUsage;
	}

	public String getPropertyWellDevelopedFlag() {
		return propertyWellDevelopedFlag;
	}

	public Double getQuitRentAmountPaid() {
		return quitRentAmountPaid;
	}

	public String getQuitRentInd() {
		return this.quitRentInd;
	}

	public String getQuitRentPaymentDate() {
		return quitRentPaymentDate;
	}

	public StandardCode getRealEstateUsage() {
		return realEstateUsage;
	}

	public String getRealEstateWithFewRentalUnitsFlag() {
		return realEstateWithFewRentalUnitsFlag;
	}

	public String getReducedRiskWeightFlag() {
		return reducedRiskWeightFlag;
	}

	public String getRegisteredHolderOwner() {
		return registeredHolderOwner;
	}

	public Long getRemainingTenurePeriod() {
		return remainingTenurePeriod;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getRestrictionCondition() {
		return restrictionCondition;
	}

	public String getSectionNo() {
		return sectionNo;
	}

	public String getSecurityEnvironmentallyRisky() {
		return securityEnvironmentallyRisky;
	}

	public String getSecurityEnvironmentallyRiskyConfirmedDate() {
		return MessageDate.getInstance().getString(securityEnvironmentallyRiskyConfirmedDate);
	}

	public String getSecurityEnvironmentallyRiskyRemarks() {
		return securityEnvironmentallyRiskyRemarks;
	}

	public String getSnpAgreementDate() {
		return snpAgreementDate;
	}

	public Double getSnpAgreementValue() {
		return snpAgreementValue;
	}

	public StandardCode getState() {
		return state;
	}

	public StandardCode getStoreyNo() {
		return storeyNo;
	}

	public String getTaman() {
		return taman;
	}

	public Long getTenure() {
		return tenure;
	}

	public String getTenureType() {
		return tenureType;
	}

	public String getTenureUnit() {
		return tenureUnit;
	}

	public String getTitleNumber() {
		return titleNumber;
	}

	public StandardCode getTitleNumberPrefix() {
		return titleNumberPrefix;
	}

	public StandardCode getTitleType() {
		return titleType;
	}

	public String getUnitParcelNo() {
		return unitParcelNo;
	}

	public void setAbandonedProject(String abandonedProject) {
		this.abandonedProject = abandonedProject;
	}

	public void setAmountRedeem(Double amountRedeem) {
		this.amountRedeem = amountRedeem;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

	public void setAssessmentPaymentDate(String assessmentPaymentDate) {
		AssessmentPaymentDate = assessmentPaymentDate;
	}

	public void setAssessmentPeriod(StandardCode assessmentPeriod) {
		this.assessmentPeriod = assessmentPeriod;
	}

	public void setAssessmentRate(Double assessmentRate) {
		this.assessmentRate = assessmentRate;
	}

	public void setAssessmentYear(Long assessmentYear) {
		this.assessmentYear = assessmentYear;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public void setAuctionDate(String auctionDate) {
		this.auctionDate = auctionDate;
	}

	public void setAuctionPrice(Double auctionPrice) {
		this.auctionPrice = auctionPrice;
	}

	public void setBreachInd(String breachInd) {
		this.breachInd = breachInd;
	}

	public void setBuiltUpArea(Double builtUpArea) {
		this.builtUpArea = builtUpArea;
	}

	public void setBuiltUpAreaUOM(StandardCode builtUpAreaUOM) {
		this.builtUpAreaUOM = builtUpAreaUOM;
	}

	public void setCategoryOfLandUse(StandardCode categoryOfLandUse) {
		this.categoryOfLandUse = categoryOfLandUse;
	}

	public void setCombinedValueInd(String combinedValueInd) {
		if (StringUtils.equalsIgnoreCase(combinedValueInd, "Y") || StringUtils.equalsIgnoreCase(combinedValueInd, "1")) {
			this.combinedValueInd = "1";
		}
		else {
			this.combinedValueInd = "0";
		}
	}

	public void setCompletionStage(StandardCode completionStage) {
		this.completionStage = completionStage;
	}

	public void setDateChattelSold(String dateChattelSold) {
		this.dateChattelSold = dateChattelSold;
	}

	public void setDeveloperGroupName(StandardCode developerGroupName) {
		this.developerGroupName = developerGroupName;
	}

	public void setDeveloperName(StandardCode developerName) {
		this.developerName = developerName;
	}

	public void setDistrict(StandardCode district) {
		this.district = district;
	}

	public void setExpressedCondition(String expressedCondition) {
		this.expressedCondition = expressedCondition;
	}

	public void setIndependentValuerFlag(String independentValuerFlag) {
		this.independentValuerFlag = independentValuerFlag;
	}

	public void setJDOAssessmentPaymentDate(Date assessmentPaymentDate) {
		AssessmentPaymentDate = MessageDate.getInstance().getString(assessmentPaymentDate);
	}

	public void setJDOAuctionDate(Date auctionDate) {
		this.auctionDate = MessageDate.getInstance().getString(auctionDate);
	}

	public void setJDODateChattelSold(Date dateChattelSold) {
		this.dateChattelSold = MessageDate.getInstance().getString(dateChattelSold);
	}

	public void setJDOLastPhysicalInspectionDate(Date lastPhysicalInspectionDate) {
		this.lastPhysicalInspectionDate = lastPhysicalInspectionDate;
	}

	public void setJDONextPhysicalInspectionDate(Date nextPhysicalInspectionDate) {
		this.nextPhysicalInspectionDate = nextPhysicalInspectionDate;
	}

	public void setJDONextQuitRentDate(Date nextQuitRentDate) {
		this.nextQuitRentDate = MessageDate.getInstance().getString(nextQuitRentDate);
	}

	public void setJDOPrivateCaveatGteeExpDate(Date privateCaveatGteeExpDate) {
		this.privateCaveatGteeExpDate = MessageDate.getInstance().getString(privateCaveatGteeExpDate);
	}

	public void setJDOQuitRentPaymentDate(Date quitRentPaymentDate) {
		this.quitRentPaymentDate = MessageDate.getInstance().getString(quitRentPaymentDate);
	}

	public void setJDOSecurityEnvironmentallyRiskyConfirmedDate(Date securityEnvironmentallyRiskyConfirmedDate) {
		this.securityEnvironmentallyRiskyConfirmedDate = securityEnvironmentallyRiskyConfirmedDate;
	}

	public void setJDOSnpAgreementDate(Date snpAgreementDate) {
		this.snpAgreementDate = MessageDate.getInstance().getString(snpAgreementDate);
	}

	public void setLandArea(Double landArea) {
		this.landArea = landArea;
	}

	public void setLandAreaUOM(StandardCode landAreaUOM) {
		this.landAreaUOM = landAreaUOM;
	}

	public void setLastPhysicalInspectionDate(String lastPhysicalInspectionDate) {
		this.lastPhysicalInspectionDate = MessageDate.getInstance().getDate(lastPhysicalInspectionDate);
	}

	public void setLongEstablishedMarketFlag(String longEstablishedMarketFlag) {
		this.longEstablishedMarketFlag = longEstablishedMarketFlag;
	}

	public void setLotLocation(String lotLocation) {
		this.lotLocation = lotLocation;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public void setLotNoType(StandardCode lotNoType) {
		this.lotNoType = lotNoType;
	}

	public void setMukim(StandardCode mukim) {
		this.mukim = mukim;
	}

	public void setNextPhysicalInspectionDate(String nextPhysicalInspectionDate) {
		this.nextPhysicalInspectionDate = MessageDate.getInstance().getDate(nextPhysicalInspectionDate);
	}

	public void setNextQuitRentDate(String nextQuitRentDate) {
		this.nextQuitRentDate = nextQuitRentDate;
	}

	public void setNonPreferredLocation(StandardCode nonPreferredLocation) {
		this.nonPreferredLocation = nonPreferredLocation;
	}

	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}

	public void setPhysicalInspectionFlag(String physicalInspectionFlag) {
		this.physicalInspectionFlag = physicalInspectionFlag;
	}

	public void setPhysicalInspectionFrequencyUnit(Short physicalInspectionFrequencyUnit) {
		this.physicalInspectionFrequencyUnit = physicalInspectionFrequencyUnit;
	}

	public void setPhysicalInspectionFrequencyUOM(String physicalInspectionFrequencyUOM) {
		this.physicalInspectionFrequencyUOM = physicalInspectionFrequencyUOM;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public void setPrivateCaveatGteeExpDate(String privateCaveatGteeExpDate) {
		this.privateCaveatGteeExpDate = privateCaveatGteeExpDate;
	}

	public void setProjectName(StandardCode projectName) {
		this.projectName = projectName;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public void setPropertyAddress1(String propertyAddress1) {
		this.propertyAddress1 = propertyAddress1;
	}

	public void setPropertyAddress2(String propertyAddress2) {
		this.propertyAddress2 = propertyAddress2;
	}

	public void setPropertyAddress3(String propertyAddress3) {
		this.propertyAddress3 = propertyAddress3;
	}

	public void setPropertyCompletedFlag(String propertyCompletedFlag) {
		this.propertyCompletedFlag = propertyCompletedFlag;
	}

	public void setPropertyCompletionStatus(StandardCode propertyCompletionStatus) {
		this.propertyCompletionStatus = propertyCompletionStatus;
	}

	public void setPropertyHasMethodologyForValuationFlag(String propertyHasMethodologyForValuationFlag) {
		this.propertyHasMethodologyForValuationFlag = propertyHasMethodologyForValuationFlag;
	}

	public void setPropertyType(StandardCode propertyType) {
		this.propertyType = propertyType;
	}

	public void setPropertyUsage(StandardCode propertyUsage) {
		this.propertyUsage = propertyUsage;
	}

	public void setPropertyWellDevelopedFlag(String propertyWellDevelopedFlag) {
		this.propertyWellDevelopedFlag = propertyWellDevelopedFlag;
	}

	public void setQuitRentAmountPaid(Double quitRentAmountPaid) {
		this.quitRentAmountPaid = quitRentAmountPaid;
	}

	public void setQuitRentInd(String quitRentInd) {
		this.quitRentInd = quitRentInd;
	}

	public void setQuitRentPaymentDate(String quitRentPaymentDate) {
		this.quitRentPaymentDate = quitRentPaymentDate;
	}

	public void setRealEstateUsage(StandardCode realEstateUsage) {
		this.realEstateUsage = realEstateUsage;
	}

	public void setRealEstateWithFewRentalUnitsFlag(String realEstateWithFewRentalUnitsFlag) {
		this.realEstateWithFewRentalUnitsFlag = realEstateWithFewRentalUnitsFlag;
	}

	public void setReducedRiskWeightFlag(String reducedRiskWeightFlag) {
		this.reducedRiskWeightFlag = reducedRiskWeightFlag;
	}

	public void setRegisteredHolderOwner(String registeredHolderOwner) {
		this.registeredHolderOwner = registeredHolderOwner;
	}

	public void setRemainingTenurePeriod(Long remainingTenurePeriod) {
		this.remainingTenurePeriod = remainingTenurePeriod;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setRestrictionCondition(String restrictionCondition) {
		this.restrictionCondition = restrictionCondition;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public void setSecurityEnvironmentallyRisky(String securityEnvironmentallyRisky) {
		this.securityEnvironmentallyRisky = securityEnvironmentallyRisky;
	}

	public void setSecurityEnvironmentallyRiskyConfirmedDate(String securityEnvironmentallyRiskyConfirmedDate) {
		this.securityEnvironmentallyRiskyConfirmedDate = MessageDate.getInstance().getDate(
				securityEnvironmentallyRiskyConfirmedDate);
	}

	public void setSecurityEnvironmentallyRiskyRemarks(String securityEnvironmentallyRiskyRemarks) {
		this.securityEnvironmentallyRiskyRemarks = securityEnvironmentallyRiskyRemarks;
	}

	public void setSnpAgreementDate(String snpAgreementDate) {
		this.snpAgreementDate = snpAgreementDate;
	}

	public void setSnpAgreementValue(Double snpAgreementValue) {
		this.snpAgreementValue = snpAgreementValue;
	}

	public void setState(StandardCode state) {
		this.state = state;
	}

	public void setStoreyNo(StandardCode storeyNo) {
		this.storeyNo = storeyNo;
	}

	public void setTaman(String taman) {
		this.taman = taman;
	}

	public void setTenure(Long tenure) {
		this.tenure = tenure;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}

	public void setTenureUnit(String tenureUnit) {
		this.tenureUnit = tenureUnit;
	}

	public void setTitleNumber(String titleNumber) {
		this.titleNumber = titleNumber;
	}

	public void setTitleNumberPrefix(StandardCode titleNumberPrefix) {
		this.titleNumberPrefix = titleNumberPrefix;
	}

	public void setTitleType(StandardCode titleType) {
		this.titleType = titleType;
	}

	public void setUnitParcelNo(String unitParcelNo) {
		this.unitParcelNo = unitParcelNo;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("PropertySecurity [assessment=");
		buf.append(assessment);
		buf.append(", assessmentPeriod=");
		buf.append(assessmentPeriod);
		buf.append(", assessmentRate=");
		buf.append(assessmentRate);
		buf.append(", assessmentYear=");
		buf.append(assessmentYear);
		buf.append(", AssessmentPaymentDate=");
		buf.append(AssessmentPaymentDate);
		buf.append(", builtUpArea=");
		buf.append(builtUpArea);
		buf.append(", builtUpAreaUOM=");
		buf.append(builtUpAreaUOM);
		buf.append(", state=");
		buf.append(state);
		buf.append(", district=");
		buf.append(district);
		buf.append(", mukim=");
		buf.append(mukim);
		buf.append(", landArea=");
		buf.append(landArea);
		buf.append(", landAreaUOM=");
		buf.append(landAreaUOM);
		buf.append(", physicalInspectionFlag=");
		buf.append(physicalInspectionFlag);
		buf.append(", physicalInspectionFrequencyUnit=");
		buf.append(physicalInspectionFrequencyUnit);
		buf.append(", physicalInspectionFrequencyUOM=");
		buf.append(physicalInspectionFrequencyUOM);
		buf.append(", propertyCompletionStatus=");
		buf.append(propertyCompletionStatus);
		buf.append(", propertyType=");
		buf.append(propertyType);
		buf.append(", propertyUsage=");
		buf.append(propertyUsage);
		buf.append(", quitRentInd=");
		buf.append(quitRentInd);
		buf.append(", quitRentAmountPaid=");
		buf.append(quitRentAmountPaid);
		buf.append(", quitRentPaymentDate=");
		buf.append(quitRentPaymentDate);
		buf.append(", tenureType=");
		buf.append(tenureType);
		buf.append(", tenure=");
		buf.append(tenure);
		buf.append(", titleNumberPrefix=");
		buf.append(titleNumberPrefix);
		buf.append(", titleNumber=");
		buf.append(titleNumber);
		buf.append(", titleType=");
		buf.append(titleType);
		buf.append("]");
		return buf.toString();
	}

	/*public long getInSqftBuiltUpArea() {
		return inSqftBuiltUpArea;
	}

	public void setInSqftBuiltUpArea(long inSqftBuiltUpArea) {
		this.inSqftBuiltUpArea = inSqftBuiltUpArea;
	}

	public long getInSqftLandArea() {
		return inSqftLandArea;
	}

	public void setInSqftLandArea(long inSqftLandArea) {
		this.inSqftLandArea = inSqftLandArea;
	}

	public Date getTsrDate() {
		return tsrDate;
	}

	public void setTsrDate(Date tsrDate) {
		this.tsrDate = tsrDate;
	}

	public Date getNextTsrDate() {
		return nextTsrDate;
	}

	public void setNextTsrDate(Date nextTsrDate) {
		this.nextTsrDate = nextTsrDate;
	}

	public String getTsrFrequency() {
		return tsrFrequency;
	}

	public void setTsrFrequency(String tsrFrequency) {
		this.tsrFrequency = tsrFrequency;
	}
*/
	public String getConstitution() {
		return constitution;
	}

	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}

}
