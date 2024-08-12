/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/property/EBPropertyCollateralBean.java,v 1.15 2006/06/29 13:26:16 jzhan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.property;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.FinderException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.EBMFChecklistLocal;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.EBMFChecklistLocalHome;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for property collateral type.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/06/29 13:26:16 $ Tag: $Name: $
 */
public abstract class EBPropertyCollateralBean extends EBCollateralDetailBean implements IPropertyCollateral {
	/**
	 * Get collateral ID.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return getEBCollateralID().longValue();
	}

	/**
	 * set collateral ID.
	 * 
	 * @param collateralID is of type long
	 */
	public void setCollateralID(long collateralID) {
		setEBCollateralID(new Long(collateralID));
	}

	/**
	 * Get if it is using Master Title .
	 * 
	 * @return String
	 */
	public String getMasterTitle() {
		return getMasterTitleStr();
	}

	/**
	 * Set if it is using Master Title.
	 * 
	 * @param masterTitle of type String
	 */
	public void setMasterTitle(String masterTitle) {
		setMasterTitleStr(masterTitle);
	}

	/**
	 * Get if it is physical inspection.
	 * 
	 * @return boolean
	 */
	public boolean getIsPhysicalInspection() {
		String isInspect = getEBIsPhysicalInspection();
		if ((isInspect != null) && isInspect.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection(boolean isPhysicalInspection) {
		if (isPhysicalInspection) {
			setEBIsPhysicalInspection(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsPhysicalInspection(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get sale and purchase agreement value.
	 * 
	 * @return Amount
	 */
	public Amount getSalePurchaseValue() {
		if (getEBSalePurchaseValue() != ICMSConstant.DOUBLE_INVALID_VALUE)
			return new Amount(getEBSalePurchaseValue(), currencyCode);
		return null;
	}

	/**
	 * Set sale and purchase agreement value.
	 * 
	 * @param salePurchaseValue of type Amount
	 */
	public void setSalePurchaseValue(Amount salePurchaseValue) {
		if (salePurchaseValue != null) {
			setEBSalePurchaseValue(salePurchaseValue.getAmountAsDouble());
		}
		else
			setEBSalePurchaseValue(ICMSConstant.DOUBLE_INVALID_VALUE);
	}

	/**
	 * Get quit rent amount paid.
	 * 
	 * @return Amount
	 */
	public Amount getQuitRentPaid() {
		if (getEBQuitRentPaid() != ICMSConstant.DOUBLE_INVALID_VALUE)
			return new Amount(getEBQuitRentPaid(), currencyCode);
		return null;
	}

	/**
	 * Set quit rent amount paid.
	 * 
	 * @param quitRentPaid of type Amount
	 */
	public void setQuitRentPaid(Amount quitRentPaid) {
		if (quitRentPaid != null) {
			setEBQuitRentPaid(quitRentPaid.getAmountAsDouble());
		}
		else
			setEBQuitRentPaid(ICMSConstant.DOUBLE_INVALID_VALUE);
	}

	/**
	 * Get nominal value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue() {
		if (getEBNominalValue() != null) {
			return new Amount(getEBNominalValue().doubleValue(), currencyCode);
		}
		else {
			return null;
		}
	}

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue) {
		if (nominalValue != null) {
			setEBNominalValue(new Double(nominalValue.getAmountAsDouble()));
		}
		else {
			setEBNominalValue(null);
		}
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract double getEBSalePurchaseValue();

	public abstract void setEBSalePurchaseValue(double eBSalePurchaseValue);

	public abstract String getEBIsPhysicalInspection();

	public abstract void setEBIsPhysicalInspection(String eBIsPhysicalInspection);

	public abstract double getEBQuitRentPaid();

	public abstract void setEBQuitRentPaid(double quitRentPaid);

	public abstract double getBuiltupArea();

	public abstract void setBuiltupArea(double builtupArea);

	public abstract String getBuiltupAreaUOM();

	public abstract void setBuiltupAreaUOM(String builtupAreaUOM);

	public abstract Double getEBNominalValue();

	public abstract void setEBNominalValue(Double nominalValue);

	public String getNonStdQuitRent() {
		return "N";
	}

	public void setNonStdQuitRent(String nonStdQuitRent) {
	}

	public abstract Character getReducedRiskWeightFlagOB();

	public abstract void setReducedRiskWeightFlagOB(Character reducedRiskWeightFlag);

	public abstract Character getPropertyWellDevelopedFlagOB();

	public abstract void setPropertyWellDevelopedFlagOB(Character propertyWellDevelopedFlag);

	public abstract Character getRealEstateRentalFlagOB();

	public abstract void setRealEstateRentalFlagOB(Character realEstateRentalFlag);

	public abstract Character getPropertyCompletedFlagOB();

	public abstract void setPropertyCompletedFlagOB(Character propertyCompletedFlag);

	public abstract Character getPropertyCompletionStatusOB();

	public abstract void setPropertyCompletionStatusOB(Character propertyCompletionStatus);

	public abstract Character getPropertyCompletionStageOB();

	public abstract void setPropertyCompletionStageOB(Character propertyCompletionStageOB);

	public abstract Character getMethodologyForValuationFlagOB();

	public abstract void setMethodologyForValuationFlagOB(Character methodologyForValuationFlag);

	public abstract Character getIndependentValuerFlagOB();

	public abstract void setIndependentValuerFlagOB(Character independentValuerFlag);

	public abstract Character getAbandonedProjectOB();

	public abstract void setAbandonedProjectOB(Character abandonedProject);

	public abstract Character getBreachIndOB();

	public abstract void setBreachIndOB(Character breachInd);

	public char getReducedRiskWeightFlag() {
		Character flag = getReducedRiskWeightFlagOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setReducedRiskWeightFlag(char reducedRiskWeightFlag) {
		setReducedRiskWeightFlagOB(new Character(reducedRiskWeightFlag));
	}

	public char getPropertyWellDevelopedFlag() {
		Character flag = getPropertyWellDevelopedFlagOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setPropertyWellDevelopedFlag(char propertyWellDevelopedFlag) {
		setPropertyWellDevelopedFlagOB(new Character(propertyWellDevelopedFlag));
	}

	public char getRealEstateRentalFlag() {
		Character flag = getRealEstateRentalFlagOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setRealEstateRentalFlag(char realEstateRentalFlag) {
		setRealEstateRentalFlagOB(new Character(realEstateRentalFlag));
	}

	public char getPropertyCompletedFlag() {
		Character flag = getPropertyCompletedFlagOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setPropertyCompletedFlag(char propertyCompletedFlag) {
		setPropertyCompletedFlagOB(new Character(propertyCompletedFlag));
	}

	public char getPropertyCompletionStatus() {
		Character flag = getPropertyCompletionStatusOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setPropertyCompletionStatus(char propertyCompletionStatus) {
		setPropertyCompletionStatusOB(new Character(propertyCompletionStatus));
	}

	public char getPropertyCompletionStage() {
		Character flag = getPropertyCompletionStageOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setPropertyCompletionStage(char propertyCompletionStage) {
		setPropertyCompletionStageOB(new Character(propertyCompletionStage));
	}

	public Amount getAssessmentRate() {
		if (getEBAssessmentRate() != null)
			return new Amount(getEBAssessmentRate().doubleValue(), currencyCode);
		return null;
	}

	public void setAssessmentRate(Amount assessmentRate) {
		if (assessmentRate != null) {
			setEBAssessmentRate(new Double(assessmentRate.getAmountAsDouble()));
		}
		else
			setEBAssessmentRate(null);
	}

	public Amount getAuctionPrice() {
		if (getEBAuctionPrice() != null)
			return new Amount(getEBAuctionPrice().doubleValue(), currencyCode);
		return null;
	}

	public void setAuctionPrice(Amount auctionPrice) {
		if (auctionPrice != null) {
			setEBAuctionPrice(new Double(auctionPrice.getAmountAsDouble()));
		}
		else
			setEBAuctionPrice(null);
	}

	public boolean getAssumption() {
		if (getAssumptionStr() != null && getAssumptionStr().equals(ICMSConstant.TRUE_VALUE))
			return true;
		return false;
	}

	public void setAssumption(boolean assumption) {
		if (assumption)
			setAssumptionStr(ICMSConstant.TRUE_VALUE);
		else
			setAssumptionStr(ICMSConstant.FALSE_VALUE);
	}

	public Amount getAmountRedeem() {
		if (getEBAmountRedeem() != null)
			return new Amount(getEBAmountRedeem().doubleValue(), currencyCode);
		return null;
	}

	public void setAmountRedeem(Amount amountRedeem) {
		if (amountRedeem != null) {
			setEBAmountRedeem(new Double(amountRedeem.getAmountAsDouble()));
		}
		else
			setEBAmountRedeem(null);
	}

	public Amount getUnitPrice() {
		if (getEBUnitPrice() != null)
			return new Amount(getEBUnitPrice().doubleValue(), currencyCode);
		return null;
	}

	public void setUnitPrice(Amount unitPrice) {
		if (unitPrice != null) {
			setEBUnitPrice(new Double(unitPrice.getAmountAsDouble()));
		}
		else
			setEBUnitPrice(null);
	}

	public char getMethodologyForValuationFlag() {
		Character flag = getMethodologyForValuationFlagOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setMethodologyForValuationFlag(char methodologyForValuationFlag) {
		setMethodologyForValuationFlagOB(new Character(methodologyForValuationFlag));
	}

	public char getIndependentValuerFlag() {
		Character flag = getIndependentValuerFlagOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setIndependentValuerFlag(char independentValuerFlag) {
		setIndependentValuerFlagOB(new Character(independentValuerFlag));
	}

	public char getAbandonedProject() {
		Character flag = getAbandonedProjectOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setAbandonedProject(char abandonedProject) {
		setAbandonedProjectOB(new Character(abandonedProject));
	}

	public char getBreachInd() {
		Character flag = getBreachIndOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setBreachInd(char breachInd) {
		setBreachIndOB(new Character(breachInd));
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral#setComputedMFScore
	 */
	public void setComputedMFScore(Double value) {
		// do nothing
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral#getComputedMFScore
	 */
	public Double getComputedMFScore() {

		try {
			EBMFChecklistLocalHome ejbHome = getEBLocalHomeMFChecklist();
			DefaultLogger.debug(this, "Get ComputedMFScore for collateralID:" + getCollateralID());

			EBMFChecklistLocal checklist = ejbHome.findByCollateralID(getCollateralID());

			if (checklist == null) {
				return null;
			}

			BigDecimal total = new BigDecimal("0");
			IMFChecklist obj = checklist.getValue();
			IMFChecklistItem[] itemList = obj.getMFChecklistItemList();
			if ((itemList != null) && (itemList.length > 0)) {
				DefaultLogger.debug(this, " itemList length: " + itemList.length);

				for (int i = 0; i < itemList.length; i++) {
					IMFChecklistItem item = itemList[i];
					DefaultLogger.debug(this, "Get WeightScore: " + item.getWeightScore());
					BigDecimal score = new BigDecimal(item.getWeightScore());
					score = score.setScale(2, BigDecimal.ROUND_HALF_UP);
					total = total.add(score);
					DefaultLogger.debug(this, "Get WeightScore, total: " + total.doubleValue());
				}
			}

			return new Double(total.doubleValue());

		}
		catch (FinderException e) {
			DefaultLogger.debug(this, e);
			return null;
		}
		catch (CollateralException e) {
			DefaultLogger.error(this, e);
			return null;
		}
	}

	/**
	 * Method to get EB Local Home for EBMFChecklist
	 * 
	 * @return EBMFChecklistLocalHome
	 * @throws CollateralException on errors
	 */
	protected EBMFChecklistLocalHome getEBLocalHomeMFChecklist() throws CollateralException {
		EBMFChecklistLocalHome home = (EBMFChecklistLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_MF_CHECKLIST_LOCAL_JNDI, EBMFChecklistLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CollateralException("EBMFChecklistLocalHome is null!");
		}
	}

	public abstract String getEnvRiskyStatus();

	public abstract void setEnvRiskyStatus(String envRiskyStatus);

	public abstract Date getEnvRiskyDate();

	public abstract void setEnvRiskyDate(Date envRiskyDate);

	public abstract String getEnvRiskyRemarks();

	public abstract void setEnvRiskyRemarks(String envRiskyRemarks);

	public abstract int getPhysicalInspectionFreq();

	public abstract void setPhysicalInspectionFreq(int physicalInspectionFreq);

	public abstract String getPhysicalInspectionFreqUnit();

	public abstract void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);

	public abstract String getTitleType();

	public abstract void setTitleType(String titleType);

	public abstract String getTitleNumber();

	public abstract void setTitleNumber(String titleNumber);
	
	public abstract String getTitleNumberPrefix();

	public abstract void setTitleNumberPrefix(String titleNumberPrefix);

	public abstract String getMasterTitleStr();

	public abstract void setMasterTitleStr(String masterTitleStr);

	public abstract String getMasterTitleNumber();

	public abstract void setMasterTitleNumber(String masterTitleNumber);

	public abstract Date getLastPhysicalInspectDate();

	public abstract void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);

	public abstract Date getNextPhysicalInspectDate();

	public abstract void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);

	public abstract String getLotNo();

	public abstract void setLotNo(String lotNo);
	
	public abstract String getLotNumberPrefix();

	public  abstract void setLotNumberPrefix(String lotNumberPrefix);

	public abstract String getTown();

	public abstract void setTown(String town);

	public abstract String getLotLocation();

	public abstract void setLotLocation(String lotLocation);

	public abstract String getPostcode();

	public abstract void setPostcode(String postcode);

	public abstract double getLandArea();

	public abstract void setLandArea(double landArea);

	public abstract String getLandAreaUOM();

	public abstract void setLandAreaUOM(String landAreaUOM);

	public abstract int getTenure();

	public abstract void setTenure(int tenure);

	public abstract String getTenureUnit();

	public abstract void setTenureUnit(String tenureUnit);

	public abstract int getRemainTenurePeriod();

	public abstract void setRemainTenurePeriod(int remainTenurePeriod);

	public abstract String getRemainTenureUnit();

	public abstract void setRemainTenureUnit(String remainTenureUnit);

	public abstract String getRestrictionCondition();

	public abstract void setRestrictionCondition(String restrictionCondition);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract String getRemarksProperty();

	public abstract void setRemarksProperty(String remarksProperty);

	public abstract String getStdQuitRent();

	public abstract void setStdQuitRent(String stdQuitRent);

	public abstract String getQuitRentReceipt();

	public abstract void setQuitRentReceipt(String quitRentReceipt);

	public abstract String getRegistedHolder();

	public abstract void setRegistedHolder(String registedHolder);

	public abstract String getPropertyAddress();

	public abstract void setPropertyAddress(String propertyAddress);

	public abstract String getPropertyAddress2();

	public abstract void setPropertyAddress2(String propertyAddress2);

	public abstract String getPropertyAddress3();

	public abstract void setPropertyAddress3(String propertyAddress3);

	public abstract String getCombinedValueIndicator();

	public abstract void setCombinedValueIndicator(String indicator);

	public abstract long getValueNumber();

	public abstract void setValueNumber(long valueNo);

	public abstract double getCombinedValueAmount();

	public abstract void setCombinedValueAmount(double combinedValue);

	public abstract String getUnitParcelNo();

	public abstract void setUnitParcelNo(String unitParcelNo);

	public abstract String getCategoryOfLandUse();

	public abstract void setCategoryOfLandUse(String categoryOfLandUse);

	public abstract String getDeveloperName();

	public abstract void setDeveloperName(String developerName);

	public abstract String getExpressedCondition();

	public abstract void setExpressedCondition(String expressedCondition);

	public abstract String getLocationDistrict();

	public abstract void setLocationDistrict(String locationDistrict);

	public abstract String getLocationMukim();

	public abstract void setLocationMukim(String locationMukim);

	public abstract String getLocationState();

	public abstract void setLocationState(String locationState);

	public abstract String getLongEstablishedMarketFlag();

	public abstract void setLongEstablishedMarketFlag(String longEstablishedMarketFlag);

	public abstract String getPhaseNo();

	public abstract void setPhaseNo(String phaseNo);

	public abstract String getProjectName();

	public abstract void setProjectName(String projectName);

	public abstract String getPropertyType();

	public abstract void setPropertyType(String propertyType);

	public abstract String getPropertyUsage();

	public abstract void setPropertyUsage(String propertyUsage);

	public abstract String getRealEstateUsage();

	public abstract void setRealEstateUsage(String realEstateUsage);

	public abstract String getScheduledLocation();

	public abstract void setScheduledLocation(String scheduledLocation);

	public abstract String getDevGrpCo();

	public abstract void setDevGrpCo(String devGrpCo);

	public abstract String getAssessment();

	public abstract void setAssessment(String assessment);

	public abstract String getTaman();

	public abstract void setTaman(String taman);

	public abstract String getTooltipsLand();

	public abstract void setTooltipsLand(String tooltipsLand);

	public abstract String getTooltipsBuiltArea();

	public abstract void setTooltipsBuiltArea(String tooltipsBuiltArea);

	public abstract Double getUserInput();

	public abstract void setUserInput(Double userInput);

	public abstract Double getEBAssessmentRate();

	public abstract void setEBAssessmentRate(Double assessmentRate);

	public abstract Double getEBAuctionPrice();

	public abstract void setEBAuctionPrice(Double auctionPrice);

	public abstract String getAssumptionStr();

	public abstract void setAssumptionStr(String assumptionStr);

	public abstract Double getEBAmountRedeem();

	public abstract void setEBAmountRedeem(Double amountRedeem);

	public abstract Double getEBUnitPrice();

	public abstract void setEBUnitPrice(Double unitPrice);

	public abstract Date getPriCaveatGuaranteeDate();

	public abstract void setPriCaveatGuaranteeDate(Date priCaveatGuaranteeDate);

	public abstract Date getAssessmentPaymentDate();

	public abstract void setAssessmentPaymentDate(Date assessmentPaymentDate);

	public abstract Date getAuctionDate();

	public abstract void setAuctionDate(Date auctionDate);

	public abstract String getNonPreferredLocation();

	public abstract void setNonPreferredLocation(String nonPreferredLocation);

	public abstract int getAssessmentPeriod();

	public abstract void setAssessmentPeriod(int assessmentPeriod);

	public abstract Date getNextQuitRentDate();

	public abstract void setNextQuitRentDate(Date nextQuitRentDate);

	public abstract Date getQuitRentPaymentDate();

	public abstract void setQuitRentPaymentDate(Date quitRentPaymentDate);

	public abstract String getCommissionType();

	public abstract void setCommissionType(String commissionType);

	public abstract String getAssessmentOption();

	public abstract Date getChattelSoldDate();

	public abstract Date getSalePurchaseDate();

	public abstract void setAssessmentOption(String assessmentOption);

	public abstract void setChattelSoldDate(Date chattelSoldDate);

	public abstract void setSalePurchaseDate(Date salePurchaseDate);

    public abstract String getAuctioneer();

	public abstract void setAuctioneer(String auctioneer);
	
	public abstract String getSectionNo();

	public abstract void setSectionNo(String sectionNo);
	
	public abstract String getStoreyNumber();

	public abstract void setStoreyNumber(String storeyNumber);
	
	//********** Added by Dattatray Thorat for Property - Commercial Security
	
	public abstract String getValuatorCompany();

	public abstract void setValuatorCompany(String valuatorCompany);
	
	public abstract String getMortageRegisteredRef();

	public abstract void setMortageRegisteredRef(String mortageRegisteredRef);
	
	public abstract String getValuatorName();

	public abstract void setValuatorName(String valuatorName);
	
	public abstract String getAdvocateLawyerName();

	public abstract void setAdvocateLawyerName(String advocateLawyerName);
	
	public abstract String getPropertyLotLocation();

	public abstract void setPropertyLotLocation(String propertyLotLocation);
	
	public abstract String getCountry();

	public abstract void setCountry(String country);
	
	public abstract String getRegion();

	public abstract void setRegion(String region);
	
	public abstract String getNearestState();

	public abstract void setNearestState(String State);
	
	public abstract String getNearestCity();

	public abstract void setNearestCity(String nearestCity);
	
	public abstract String getOtherCity();

	public abstract void setOtherCity(String otherCity);
	
	public abstract String getPinCode();
	
	public abstract void setPinCode(String pinCode);
	
	public abstract Date getValuationDate();
	
	public abstract void setValuationDate(Date valuationDate);
	
	public abstract String getTypeOfMargage();
	
	public abstract void setTypeOfMargage(String typeOfMargage);
	
	public abstract String getMorgageCreatedBy();
	
	public abstract void setMorgageCreatedBy(String morgageCreatedBy);
	
	public abstract String getDocumentReceived();
	
	public abstract void setDocumentReceived(String documentReceived);
	
	public abstract String getDocumentBlock();
	
	public abstract void setDocumentBlock(String documentBlock);
	
	public abstract String getBinNumber();
	
	public abstract void setBinNumber(String binNumber);
	
	public abstract BigDecimal getEBTotalPropertyAmount();
	
	public abstract void setEBTotalPropertyAmount(BigDecimal totalPropertyAmount);
	
	public abstract String getPropertyAddress4();

	public abstract void setPropertyAddress4(String propertyAddress4);
	
	public abstract String getPropertyAddress5();

	public abstract void setPropertyAddress5(String propertyAddress5);
	
	public abstract String getPropertyAddress6();

	public abstract void setPropertyAddress6(String propertyAddress6);
	
	public abstract String getPropertyId() ;

	public abstract void setPropertyId(String propertyId) ;
	
	//Added by Pramod Katkar for New Filed CR on 12-08-2013
	 public abstract String getClaim();
	 
	public abstract void setClaim(String claim);
	
	public abstract String getClaimType() ;
		
	public abstract void setClaimType(String claimType) ;
	//End by Pramod Katkar
	
	public Amount getTotalPropertyAmount() {
		return getEBTotalPropertyAmount() == null ? null : new Amount(getEBTotalPropertyAmount(), new CurrencyCode(
				getCurrencyCode()));
	}

	public void setTotalPropertyAmount(Amount totalPropertyAmount) {
		setEBTotalPropertyAmount(totalPropertyAmount == null ? null : totalPropertyAmount.getAmountAsBigDecimal());
	}

	//CR : Tightening of property fields -start
	public abstract double getInSqftBuiltUpArea();

	public abstract void setInSqftBuiltUpArea(double inSqftBuiltUpArea);

	public abstract double getInSqftLandArea();

	public abstract void setInSqftLandArea(double inSqftLandArea);

	public abstract Date getTsrDate();

	public abstract void setTsrDate(Date tsrDate);

	public abstract Date getNextTsrDate() ;

	public abstract void setNextTsrDate(Date nextTsrDate);

	public abstract String getTsrFrequency();

	public abstract void setTsrFrequency(String tsrFrequency);
	
	public abstract Date getCersiaRegistrationDate();
	
	public abstract void setCersiaRegistrationDate(Date cersiaRegistrationDate);
	//	CR : Tightening of property fields - end
	
	//Start Santosh
	public abstract double getLandValue();
	
	public abstract void setLandValue(double landValue);
	
	public abstract double getBuildingValue();
	
	public abstract void setBuildingValue(double buildingValue);
	
	public abstract double getReconstructionValueOfTheBuilding() ;
	
	public abstract void setReconstructionValueOfTheBuilding(double reconstructionValueOfTheBuilding);
	//End Santosh
	
	public abstract String getConstitution();

	public abstract void setConstitution(String constitution);
	
	public abstract Date getLegalAuditDate();
	public abstract void setLegalAuditDate(Date legalAuditDate);
	public abstract Date getInterveingPeriSearchDate();
	public abstract void setInterveingPeriSearchDate(Date interveingPeriSearchDate);
	public abstract Date getDateOfReceiptTitleDeed();
	public abstract void setDateOfReceiptTitleDeed(Date dateOfReceiptTitleDeed);
	public abstract String getWaiver();
	public abstract void setWaiver(String waiver);
	public abstract String getDeferral();
	public abstract void setDeferral(String deferral);
	public abstract String getDeferralId();
	public abstract void setDeferralId(String deferralId);
	public abstract Date getValuationDate_v1();
	public abstract void setValuationDate_v1(Date valuationDate_v1);
	
	public abstract String getValuatorCompany_v1();
	public abstract void setValuatorCompany_v1(String valuatorCompany_v1);
	public abstract String getCategoryOfLandUse_v1();
	public abstract void setCategoryOfLandUse_v1(String categoryOfLandUse_v1);
	public abstract String getDeveloperName_v1();
	public abstract void setDeveloperName_v1(String developerName_v1);
	public abstract String getCountry_v1();
	public abstract void setCountry_v1(String country_v1);
	public abstract String getRegion_v1();
	public abstract void setRegion_v1(String region_v1);
	public abstract String getLocationState_v1();
	public abstract void setLocationState_v1(String locationState_v1);
	public abstract String getNearestCity_v1();
	public abstract void setNearestCity_v1(String nearestCity_v1);
	public abstract String getPinCode_v1();
	public abstract void setPinCode_v1(String pinCode_v1);
	public abstract double getLandArea_v1();
	public abstract void setLandArea_v1(double landArea_v1);
	public abstract String getLandAreaUOM_v1();
	public abstract void setLandAreaUOM_v1(String landAreaUOM_v1);
	
	public abstract double getInSqftLandArea_v1();
	public abstract void setInSqftLandArea_v1(double inSqftLandArea_v1);
	public abstract double getBuiltupArea_v1();
	public abstract void setBuiltupArea_v1(double builtupArea_v1);
	
	public abstract String getBuiltupAreaUOM_v1();
	public abstract void setBuiltupAreaUOM_v1(String builtupAreaUOM_v1);
	public abstract double getInSqftBuiltupArea_v1();
	public abstract void setInSqftBuiltupArea_v1(double inSqftBuiltupArea_v1);
	
	public abstract Character getPropertyCompletionStatusOB_v1();
	public abstract void setPropertyCompletionStatusOB_v1(Character propertyCompletionStatusOB_v1);
/*	public abstract Character getPropertyCompletionStageOB_v1();
	public abstract void setPropertyCompletionStageOB_v1(Character propertyCompletionStageOB_v1);
*/
	
	public abstract double getLandValue_v1();
	public abstract void setLandValue_v1(double landValue_v1);
	public abstract double getBuildingValue_v1();
	public abstract void setBuildingValue_v1(double buildingValue_v1);
	public abstract double getReconstructionValueOfTheBuilding_v1();
	public abstract void setReconstructionValueOfTheBuilding_v1(double reconstructionValueOfTheBuilding_v1);
	public abstract String getEBIsPhysicalInspection_v1();

	public abstract void setEBIsPhysicalInspection_v1(String eBIsPhysicalInspection_v1);
	/*public abstract int getPhysicalInspectionFreq_v1();

	public abstract void setPhysicalInspectionFreq_v1(int physicalInspectionFreq_v1);
	*/
	
	
	public abstract String getPhysicalInspectionFreqUnit_v1();

	public abstract void setPhysicalInspectionFreqUnit_v1(String physicalInspectionFreqUnit_v1);
	public abstract Date getLastPhysicalInspectDate_v1();
	public abstract void setLastPhysicalInspectDate_v1(Date lastPhysicalInspectDate_v1);
	public abstract Date getNextPhysicalInspectDate_v1();
	public abstract void setNextPhysicalInspectDate_v1(Date nextPhysicalInspectDate_v1);
	
	public abstract String getRemarksProperty_v1();
	public abstract void setRemarksProperty_v1(String remarksProperty_v1);
	public abstract String getVal1_id();
	public abstract void setVal1_id(String val1_id);
	/*public abstract String getValEdited_v1();
	public abstract void setValEdited_v1(String valEdited_v1);*/
	public abstract Date getValcreationdate_v1();
	public abstract void setValcreationdate_v1(Date valcreationdate_v1);
	
	public abstract BigDecimal getEBTotalPropertyAmount_v1();
	public abstract void setEBTotalPropertyAmount_v1(BigDecimal totalPropertyAmount_v1);
	public Amount getTotalPropertyAmount_v1() {
		return getEBTotalPropertyAmount_v1() == null ? null : new Amount(getEBTotalPropertyAmount_v1(), new CurrencyCode(
				getCurrencyCode()));
	}

	public void setTotalPropertyAmount_v1(Amount totalPropertyAmount_v1) {
		setEBTotalPropertyAmount_v1(totalPropertyAmount_v1 == null ? null : totalPropertyAmount_v1.getAmountAsBigDecimal());
	}
	
	public char getPropertyCompletionStatus_v1() {
		Character flag = getPropertyCompletionStatusOB_v1();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setPropertyCompletionStatus_v1(char propertyCompletionStatus_v1) {
		setPropertyCompletionStatusOB_v1(new Character(propertyCompletionStatus_v1));
	}

	/*public char getPropertyCompletionStage_v1() {
		Character flag = getPropertyCompletionStageOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setPropertyCompletionStage_v1(char propertyCompletionStage_v1) {
		setPropertyCompletionStageOB_v1(new Character(propertyCompletionStage_v1));
	}*/
	public boolean getIsPhysicalInspection_v1() {
		String isInspect = getEBIsPhysicalInspection_v1();
		if ((isInspect != null) && isInspect.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection_v1(boolean isPhysicalInspection_v1) {
		if (isPhysicalInspection_v1) {
			setEBIsPhysicalInspection_v1(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsPhysicalInspection_v1(ICMSConstant.FALSE_VALUE);
		}
	}
	
	public abstract String getMortgageCreationAdd();
	public abstract void setMortgageCreationAdd(String mortgageCreationAdd);
	public abstract Date getPreviousMortCreationDate();
	public abstract void setPreviousMortCreationDate(Date previousMortCreationDate);
	public abstract String getPreValDate_v1();
	public abstract void setPreValDate_v1(String preValDate_v1);
	
	//Valuation 3 start
	
	public abstract Date getValuationDate_v3();
	public abstract void setValuationDate_v3(Date valuationDate_v3);
	
	public abstract String getValuatorCompany_v3();
	public abstract void setValuatorCompany_v3(String valuatorCompany_v3);
	public abstract String getCategoryOfLandUse_v3();
	public abstract void setCategoryOfLandUse_v3(String categoryOfLandUse_v3);
	public abstract String getDeveloperName_v3();
	public abstract void setDeveloperName_v3(String developerName_v3);
	public abstract String getCountry_v3();
	public abstract void setCountry_v3(String country_v3);
	public abstract String getRegion_v3();
	public abstract void setRegion_v3(String region_v3);
	public abstract String getLocationState_v3();
	public abstract void setLocationState_v3(String locationState_v3);
	public abstract String getNearestCity_v3();
	public abstract void setNearestCity_v3(String nearestCity_v3);
	public abstract String getPinCode_v3();
	public abstract void setPinCode_v3(String pinCode_v3);
	public abstract double getLandArea_v3();
	public abstract void setLandArea_v3(double landArea_v3);
	public abstract String getLandAreaUOM_v3();
	public abstract void setLandAreaUOM_v3(String landAreaUOM_v3);
	
	public abstract double getInSqftLandArea_v3();
	public abstract void setInSqftLandArea_v3(double inSqftLandArea_v3);
	public abstract double getBuiltupArea_v3();
	public abstract void setBuiltupArea_v3(double builtupArea_v3);
	
	public abstract String getBuiltupAreaUOM_v3();
	public abstract void setBuiltupAreaUOM_v3(String builtupAreaUOM_v3);
	public abstract double getInSqftBuiltupArea_v3();
	public abstract void setInSqftBuiltupArea_v3(double inSqftBuiltupArea_v3);
	
	public abstract Character getPropertyCompletionStatusOB_v3();
	public abstract void setPropertyCompletionStatusOB_v3(Character propertyCompletionStatusOB_v3);
/*	public abstract Character getPropertyCompletionStageOB_v3();
	public abstract void setPropertyCompletionStageOB_v3(Character propertyCompletionStageOB_v3);
*/
	
	public abstract double getLandValue_v3();
	public abstract void setLandValue_v3(double landValue_v3);
	public abstract double getBuildingValue_v3();
	public abstract void setBuildingValue_v3(double buildingValue_v3);
	public abstract double getReconstructionValueOfTheBuilding_v3();
	public abstract void setReconstructionValueOfTheBuilding_v3(double reconstructionValueOfTheBuilding_v3);
	public abstract String getEBIsPhysicalInspection_v3();

	public abstract void setEBIsPhysicalInspection_v3(String eBIsPhysicalInspection_v3);
	/*public abstract int getPhysicalInspectionFreq_v3();

	public abstract void setPhysicalInspectionFreq_v3(int physicalInspectionFreq_v3);
	*/
	
	
	public abstract String getPhysicalInspectionFreqUnit_v3();

	public abstract void setPhysicalInspectionFreqUnit_v3(String physicalInspectionFreqUnit_v3);
	public abstract Date getLastPhysicalInspectDate_v3();
	public abstract void setLastPhysicalInspectDate_v3(Date lastPhysicalInspectDate_v3);
	public abstract Date getNextPhysicalInspectDate_v3();
	public abstract void setNextPhysicalInspectDate_v3(Date nextPhysicalInspectDate_v3);
	
	public abstract String getRemarksProperty_v3();
	public abstract void setRemarksProperty_v3(String remarksProperty_v3);
	public abstract String getVal3_id();
	public abstract void setVal3_id(String val3_id);
	/*public abstract String getValEdited_v3();
	public abstract void setValEdited_v3(String valEdited_v3);*/
	public abstract Date getValcreationdate_v3();
	public abstract void setValcreationdate_v3(Date valcreationdate_v3);
	
	public abstract BigDecimal getEBTotalPropertyAmount_v3();
	public abstract void setEBTotalPropertyAmount_v3(BigDecimal totalPropertyAmount_v3);
	public Amount getTotalPropertyAmount_v3() {
		return getEBTotalPropertyAmount_v3() == null ? null : new Amount(getEBTotalPropertyAmount_v3(), new CurrencyCode(
				getCurrencyCode()));
	}

	public void setTotalPropertyAmount_v3(Amount totalPropertyAmount_v3) {
		setEBTotalPropertyAmount_v3(totalPropertyAmount_v3 == null ? null : totalPropertyAmount_v3.getAmountAsBigDecimal());
	}
	
	public char getPropertyCompletionStatus_v3() {
		Character flag = getPropertyCompletionStatusOB_v3();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setPropertyCompletionStatus_v3(char propertyCompletionStatus_v3) {
		setPropertyCompletionStatusOB_v3(new Character(propertyCompletionStatus_v3));
	}

	/*public char getPropertyCompletionStage_v3() {
		Character flag = getPropertyCompletionStageOB();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setPropertyCompletionStage_v3(char propertyCompletionStage_v3) {
		setPropertyCompletionStageOB_v3(new Character(propertyCompletionStage_v3));
	}*/
	public boolean getIsPhysicalInspection_v3() {
		String isInspect = getEBIsPhysicalInspection_v3();
		if ((isInspect != null) && isInspect.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection_v3(boolean isPhysicalInspection_v3) {
		if (isPhysicalInspection_v3) {
			setEBIsPhysicalInspection_v3(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsPhysicalInspection_v3(ICMSConstant.FALSE_VALUE);
		}
	}
	
	/*public abstract String getMortgageCreationAdd();
	public abstract void setMortgageCreationAdd(String mortgageCreationAdd);
	public abstract Date getPreviousMortCreationDate();
	public abstract void setPreviousMortCreationDate(Date previousMortCreationDate);*/
	public abstract String getPreValDate_v3();
	public abstract void setPreValDate_v3(String preValDate_v3);
	
	//Valuation 3 ends
	
	public abstract Date getValuationDate_v2();
	public abstract void setValuationDate_v2(Date valuationDate_v2);
	
	public abstract String getValuatorCompany_v2();
	public abstract void setValuatorCompany_v2(String valuatorCompany_v2);
	public abstract String getCategoryOfLandUse_v2();
	public abstract void setCategoryOfLandUse_v2(String categoryOfLandUse_v2);
	public abstract String getDeveloperName_v2();
	public abstract void setDeveloperName_v2(String developerName_v2);
	public abstract String getCountry_v2();
	public abstract void setCountry_v2(String country_v2);
	public abstract String getRegion_v2();
	public abstract void setRegion_v2(String region_v2);
	public abstract String getLocationState_v2();
	public abstract void setLocationState_v2(String locationState_v2);
	public abstract String getNearestCity_v2();
	public abstract void setNearestCity_v2(String nearestCity_v2);
	public abstract String getPinCode_v2();
	public abstract void setPinCode_v2(String pinCode_v2);
	public abstract double getLandArea_v2();
	public abstract void setLandArea_v2(double landArea_v2);
	public abstract String getLandAreaUOM_v2();
	public abstract void setLandAreaUOM_v2(String landAreaUOM_v2);
	
	public abstract double getInSqftLandArea_v2();
	public abstract void setInSqftLandArea_v2(double inSqftLandArea_v2);
	public abstract double getBuiltupArea_v2();
	public abstract void setBuiltupArea_v2(double builtupArea_v2);
	
	public abstract String getBuiltupAreaUOM_v2();
	public abstract void setBuiltupAreaUOM_v2(String builtupAreaUOM_v2);
	public abstract double getInSqftBuiltupArea_v2();
	public abstract void setInSqftBuiltupArea_v2(double inSqftBuiltupArea_v2);
	
	public abstract Character getPropertyCompletionStatusOB_v2();
	public abstract void setPropertyCompletionStatusOB_v2(Character propertyCompletionStatusOB_v2);

	
	public abstract double getLandValue_v2();
	public abstract void setLandValue_v2(double landValue_v2);
	public abstract double getBuildingValue_v2();
	public abstract void setBuildingValue_v2(double buildingValue_v2);
	public abstract double getReconstructionValueOfTheBuilding_v2();
	public abstract void setReconstructionValueOfTheBuilding_v2(double reconstructionValueOfTheBuilding_v2);
	public abstract String getEBIsPhysicalInspection_v2();

	public abstract void setEBIsPhysicalInspection_v2(String eBIsPhysicalInspection_v2);
	public abstract String getPhysicalInspectionFreqUnit_v2();

	public abstract void setPhysicalInspectionFreqUnit_v2(String physicalInspectionFreqUnit_v2);
	public abstract Date getLastPhysicalInspectDate_v2();
	public abstract void setLastPhysicalInspectDate_v2(Date lastPhysicalInspectDate_v2);
	public abstract Date getNextPhysicalInspectDate_v2();
	public abstract void setNextPhysicalInspectDate_v2(Date nextPhysicalInspectDate_v2);
	
	public abstract String getRemarksProperty_v2();
	public abstract void setRemarksProperty_v2(String remarksProperty_v2);
	public abstract String getVal2_id();
	public abstract void setVal2_id(String val2_id);
	public abstract Date getValcreationdate_v2();
	public abstract void setValcreationdate_v2(Date valcreationdate_v2);
	
	public abstract BigDecimal getEBTotalPropertyAmount_v2();
	public abstract void setEBTotalPropertyAmount_v2(BigDecimal totalPropertyAmount_v2);
	public Amount getTotalPropertyAmount_v2() {
		return getEBTotalPropertyAmount_v2() == null ? null : new Amount(getEBTotalPropertyAmount_v2(), new CurrencyCode(
				getCurrencyCode()));
	}

	public void setTotalPropertyAmount_v2(Amount totalPropertyAmount_v2) {
		setEBTotalPropertyAmount_v2(totalPropertyAmount_v2 == null ? null : totalPropertyAmount_v2.getAmountAsBigDecimal());
	}
	
	public char getPropertyCompletionStatus_v2() {
		Character flag = getPropertyCompletionStatusOB_v2();
		if (flag != null) {
			return flag.charValue();
		}
		else {
			return ' ';
		}
	}

	public void setPropertyCompletionStatus_v2(char propertyCompletionStatus_v2) {
		setPropertyCompletionStatusOB_v2(new Character(propertyCompletionStatus_v2));
	}


	public boolean getIsPhysicalInspection_v2() {
		String isInspect = getEBIsPhysicalInspection_v2();
		if ((isInspect != null) && isInspect.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection_v2(boolean isPhysicalInspection_v2) {
		if (isPhysicalInspection_v2) {
			setEBIsPhysicalInspection_v2(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsPhysicalInspection_v2(ICMSConstant.FALSE_VALUE);
		}
	}
	

	public abstract String getPreValDate_v2();
	public abstract void setPreValDate_v2(String preValDate_v2);
	
	public abstract String getVersion1();
	public abstract void setVersion1(String version1);
	public abstract String getVersion2();
	public abstract void setVersion2(String version2);
	public abstract String getVersion3() ;
	public abstract void setVersion3(String version3);
	
	public abstract String getRevalOverride();
	public abstract void setRevalOverride(String revalOverride);

}
