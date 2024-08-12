/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/OBChargeCommon.java,v 1.8 2005/08/12 04:39:17 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.asset.OBAssetBasedCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a common charge for asset.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/08/12 04:39:17 $ Tag: $Name: $
 */
public class OBChargeCommon extends OBAssetBasedCollateral implements IChargeCommon {
	private boolean isPhysicalInspection;

	private int physicalInspectionFreq = ICMSConstant.INT_INVALID_VALUE;

	private String physicalInspectionFreqUnit;

	private String remarks;

	private Date lastPhysicalInspectDate;

	private Date nextPhysicalInspectDate;

	private String envRiskyStatus;

	private Date envRiskyDate;

	private String envRiskyRemarks;

	private String assetType;

	private Amount nominalValue;

	private String assetTypeHostRef;

	private Amount purchasePrice;

	private Date purchaseDate;

	private Date registrationDate;

	private int yearOfManufacture;

	private String modelNo;

	private String insurers;

	private int residualAssetLife;

	private String residualAssetLifeUOM;

	private Amount scrapValue;

	private Amount assetValue;

	private int docPerfectAge;

	public Amount salesProceed;

	private String registrationNo;

	private String prevOwnerName;

	private String prevFinancierName;

	private Date repossessionDate;

	private int repossessionAge;

	private String yard;

	private String registrationCardNo;

	private Amount registrationFee;

	private String brand;

	private String goodStatus;
	
	private Date chattelSoldDate;
	
	private String pubTransport;
	
	private String rlSerialNumber;
	
	private String inspectionStatusCategoryCode;

	private String inspectionStatusEntryCode;

	public String getRlSerialNumber() {
		return rlSerialNumber;
	}

	public void setRlSerialNumber(String rlSerialNumber) {
		this.rlSerialNumber = rlSerialNumber;
	}

	public String getPubTransport() {
		return pubTransport;
	}

	public void setPubTransport(String pubTransport) {
		this.pubTransport = pubTransport;
	}

	public Date getChattelSoldDate() {
		return chattelSoldDate;
	}

	public void setChattelSoldDate(Date chattelSoldDate) {
		this.chattelSoldDate = chattelSoldDate;
	}

	/**
	 * Default Constructor.
	 */
	public OBChargeCommon() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IChargeCommon
	 */
	public OBChargeCommon(IChargeCommon obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get if it is physical inspection.
	 * 
	 * @return boolean
	 */
	public boolean getIsPhysicalInspection() {
		return isPhysicalInspection;
	}

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection(boolean isPhysicalInspection) {
		this.isPhysicalInspection = isPhysicalInspection;
	}

	/**
	 * Get physical inspection frequency.
	 * 
	 * @return int
	 */
	public int getPhysicalInspectionFreq() {
		return physicalInspectionFreq;
	}

	/**
	 * Set physical inspection frequency.
	 * 
	 * @param physicalInspectionFreq is of type int
	 */
	public void setPhysicalInspectionFreq(int physicalInspectionFreq) {
		this.physicalInspectionFreq = physicalInspectionFreq;
	}

	/**
	 * Get physical inspection frequency unit.
	 * 
	 * @return String
	 */
	public String getPhysicalInspectionFreqUnit() {
		return physicalInspectionFreqUnit;
	}

	/**
	 * Set physical inspection frequency unit.
	 * 
	 * @param physicalInspectionFreqUnit of type String
	 */
	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit) {
		this.physicalInspectionFreqUnit = physicalInspectionFreqUnit;
	}

	/**
	 * Get description of the asset charge.
	 * 
	 * @return String
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Set description of the asset charge.
	 * 
	 * @param remarks is of type String
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Get last physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getLastPhysicalInspectDate() {
		return lastPhysicalInspectDate;
	}

	/**
	 * Set last physical inspection date.
	 * 
	 * @param lastPhysicalInspectDate is of type Date
	 */
	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate) {
		this.lastPhysicalInspectDate = lastPhysicalInspectDate;
	}

	/**
	 * Get next physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getNextPhysicalInspectDate() {
		return nextPhysicalInspectDate;
	}

	/**
	 * Set next physical inspection date.
	 * 
	 * @param nextPhysicalInspectDate is of type Date
	 */
	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate) {
		this.nextPhysicalInspectDate = nextPhysicalInspectDate;
	}

	/**
	 * Get security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @return String
	 */
	public String getEnvRiskyStatus() {
		if (envRiskyStatus != null) {
			envRiskyStatus = envRiskyStatus.trim();
		}

		return envRiskyStatus;
	}

	/**
	 * Set security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @param envRiskyStatus is of type String
	 */
	public void setEnvRiskyStatus(String envRiskyStatus) {
		this.envRiskyStatus = envRiskyStatus;
	}

	/**
	 * get date the collateral confirmed as environmentally risky.
	 * 
	 * @return Date
	 */
	public Date getEnvRiskyDate() {
		return envRiskyDate;
	}

	/**
	 * Set date the collateral confirmed as environmentally risky.
	 * 
	 * @param envRiskyDate is of type Date
	 */
	public void setEnvRiskyDate(Date envRiskyDate) {
		this.envRiskyDate = envRiskyDate;
	}

	/**
	 * Get remarks for Security Environmentally Risky.
	 * 
	 * @return String
	 */
	public String getEnvRiskyRemarks() {
		return envRiskyRemarks;
	}

	/**
	 * Set remarks for Security Environmentally Risky.
	 * 
	 * @param envRiskyRemarks is of type String
	 */
	public void setEnvRiskyRemarks(String envRiskyRemarks) {
		this.envRiskyRemarks = envRiskyRemarks;
	}

	/**
	 * Get plant/equipment type.
	 * 
	 * @return String
	 */
	public String getAssetType() {
		return assetType;
	}

	/**
	 * Set plant/equipment type.
	 * 
	 * @param assetType is of type String
	 */
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	/**
	 * Get nominal Value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue() {
		return nominalValue;
	}

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue) {
		this.nominalValue = nominalValue;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public String getAssetTypeHostRef() {
		return assetTypeHostRef;
	}

	public void setAssetTypeHostRef(String assetTypeHostRef) {
		this.assetTypeHostRef = assetTypeHostRef;
	}

	/**
	 * Get purchase price.
	 * 
	 * @return Amount
	 */
	public Amount getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * Set purchase price.
	 * 
	 * @param purchasePrice is of type Amount
	 */
	public void setPurchasePrice(Amount purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * Get purchase date.
	 * 
	 * @return Date
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * Set purchase date.
	 * 
	 * @param purchaseDate is of type Date
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * Get registration date.
	 * 
	 * @return Date
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Set registration date.
	 * 
	 * @param registrationDate of type Date
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * Get year of manufacture.
	 * 
	 * @return int
	 */
	public int getYearOfManufacture() {
		return yearOfManufacture;
	}

	/**
	 * Set year of manufacture.
	 * 
	 * @param yearOfManufacture of type int
	 */
	public void setYearOfManufacture(int yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	/**
	 * Get model number.
	 * 
	 * @return String
	 */
	public String getModelNo() {
		return modelNo;
	}

	/**
	 * Set model number.
	 * 
	 * @param modelNo is of type String
	 */
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * Get insurers.
	 * 
	 * @return String
	 */
	public String getInsurers() {
		return insurers;
	}

	/**
	 * Set insurers.
	 * 
	 * @param insurers of type String
	 */
	public void setInsurers(String insurers) {
		this.insurers = insurers;
	}

	public int getResidualAssetLife() {
		return residualAssetLife;
	}

	public void setResidualAssetLife(int residualAssetLife) {
		this.residualAssetLife = residualAssetLife;
	}

	public String getResidualAssetLifeUOM() {
		return residualAssetLifeUOM;
	}

	public void setResidualAssetLifeUOM(String residualAssetLifeUOM) {
		this.residualAssetLifeUOM = residualAssetLifeUOM;
	}

	public Amount getScrapValue() {
		return scrapValue;
	}

	public void setScrapValue(Amount scrapValue) {
		this.scrapValue = scrapValue;
	}

	public Amount getAssetValue() {
		return assetValue;
	}

	public void setAssetValue(Amount assetValue) {
		this.assetValue = assetValue;
	}

	public int getDocPerfectAge() {
		return docPerfectAge;
	}

	public void setDocPerfectAge(int docPerfectAge) {
		this.docPerfectAge = docPerfectAge;
	}

	public Amount getSalesProceed() {
		return salesProceed;
	}

	public void setSalesProceed(Amount salesProceed) {
		this.salesProceed = salesProceed;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getPrevOwnerName() {
		return prevOwnerName;
	}

	public void setPrevOwnerName(String prevOwnerName) {
		this.prevOwnerName = prevOwnerName;
	}

	public String getPrevFinancierName() {
		return prevFinancierName;
	}

	public void setPrevFinancierName(String prevFinancierName) {
		this.prevFinancierName = prevFinancierName;
	}

	public Date getRepossessionDate() {
		return repossessionDate;
	}

	public void setRepossessionDate(Date repossessionDate) {
		this.repossessionDate = repossessionDate;
	}

	public int getRepossessionAge() {
		return repossessionAge;
	}

	public void setRepossessionAge(int repossessionAge) {
		this.repossessionAge = repossessionAge;
	}

	public String getYard() {
		return yard;
	}

	public void setYard(String yard) {
		this.yard = yard;
	}

	public String getRegistrationCardNo() {
		return registrationCardNo;
	}

	public void setRegistrationCardNo(String registrationCardNo) {
		this.registrationCardNo = registrationCardNo;
	}

	public Amount getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(Amount registrationFee) {
		this.registrationFee = registrationFee;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}
	
	
	

	public String getInspectionStatusCategoryCode() {
		return inspectionStatusCategoryCode;
	}

	public void setInspectionStatusCategoryCode(String inspectionStatusCategoryCode) {
		this.inspectionStatusCategoryCode = inspectionStatusCategoryCode;
	}

	public String getInspectionStatusEntryCode() {
		return inspectionStatusEntryCode;
	}

	public void setInspectionStatusEntryCode(String inspectionStatusEntryCode) {
		this.inspectionStatusEntryCode = inspectionStatusEntryCode;
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
		else if (!(obj instanceof OBChargeCommon)) {
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
}