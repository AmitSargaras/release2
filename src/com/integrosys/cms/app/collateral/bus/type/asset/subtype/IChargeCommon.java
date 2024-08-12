/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/IChargeCommon.java,v 1.11 2005/08/12 04:39:17 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;

/**
 * This interface represents a common charge for asset type.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/08/12 04:39:17 $ Tag: $Name: $
 */
public interface IChargeCommon extends IAssetBasedCollateral {
	/**
	 * Get physical inspection frequency.
	 * 
	 * @return int
	 */
	public int getPhysicalInspectionFreq();

	/**
	 * Set physical inspection frequency.
	 * 
	 * @param physicalInspectionFreq is of type int
	 */
	public void setPhysicalInspectionFreq(int physicalInspectionFreq);

	/**
	 * Get physical inspection frequency unit.
	 * 
	 * @return String
	 */
	public String getPhysicalInspectionFreqUnit();

	/**
	 * Set physical inspection frequency unit.
	 * 
	 * @param physicalInspectionFreqUnit of type String
	 */
	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);

	/**
	 * Get description of the asset charge.
	 * 
	 * @return String
	 */
	public String getRemarks();

	/**
	 * Set description of the asset charge.
	 * 
	 * @param remarks is of type String
	 */
	public void setRemarks(String remarks);

	/**
	 * Get last physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getLastPhysicalInspectDate();

	/**
	 * Set last physical inspection date.
	 * 
	 * @param lastPhysicalInspectDate is of type Date
	 */
	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);

	/**
	 * Get next physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getNextPhysicalInspectDate();

	/**
	 * Set next physical inspection date.
	 * 
	 * @param nextPhysicalInspectDate is of type Date
	 */
	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);

	/**
	 * Get security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @return String
	 */
	public String getEnvRiskyStatus();

	/**
	 * Set security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @param envRiskyStatus is of type String
	 */
	public void setEnvRiskyStatus(String envRiskyStatus);

	/**
	 * get date the collateral confirmed as environmentally risky.
	 * 
	 * @return Date
	 */
	public Date getEnvRiskyDate();

	/**
	 * Set date the collateral confirmed as environmentally risky.
	 * 
	 * @param envRiskyDate is of type Date
	 */
	public void setEnvRiskyDate(Date envRiskyDate);

	/**
	 * Get remarks for Security Environmentally Risky.
	 * 
	 * @return String
	 */
	public String getEnvRiskyRemarks();

	/**
	 * Set remarks for Security Environmentally Risky.
	 * 
	 * @param envRiskyRemarks is of type String
	 */
	public void setEnvRiskyRemarks(String envRiskyRemarks);

	/**
	 * Get plant/equipment type.
	 * 
	 * @return String
	 */
	public String getAssetType();

	/**
	 * Set plant/equipment type.
	 * 
	 * @param assetType is of type String
	 */
	public void setAssetType(String assetType);

	/**
	 * Get nominal Value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue();

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue);

	/**
	 * Get if it is physical inspection.
	 * 
	 * @return boolean
	 */
	public boolean getIsPhysicalInspection();

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection(boolean isPhysicalInspection);

	public String getAssetTypeHostRef();

	public void setAssetTypeHostRef(String assetTypeHostRef);

	/**
	 * Get purchase price.
	 * 
	 * @return Amount
	 */
	public Amount getPurchasePrice();

	/**
	 * Set purchase price.
	 * 
	 * @param purchasePrice is of type Amount
	 */
	public void setPurchasePrice(Amount purchasePrice);

	/**
	 * Get purchase date.
	 * 
	 * @return Date
	 */
	public Date getPurchaseDate();

	/**
	 * Set purchase date.
	 * 
	 * @param purchaseDate is of type Date
	 */
	public void setPurchaseDate(Date purchaseDate);

	/**
	 * Get registration date.
	 * 
	 * @return Date
	 */
	public Date getRegistrationDate();

	/**
	 * Set registration date.
	 * 
	 * @param registrationDate of type Date
	 */
	public void setRegistrationDate(Date registrationDate);

	/**
	 * Get year of manufacture.
	 * 
	 * @return int
	 */
	public int getYearOfManufacture();

	/**
	 * Set year of manufacture.
	 * 
	 * @param yearOfManufacture of type int
	 */
	public void setYearOfManufacture(int yearOfManufacture);

	/**
	 * Get model number.
	 * 
	 * @return String
	 */
	public String getModelNo();

	/**
	 * Set model number.
	 * 
	 * @param modelNo is of type String
	 */
	public void setModelNo(String modelNo);

	/**
	 * Get insurers.
	 * 
	 * @return String
	 */
	public String getInsurers();

	/**
	 * Set insurers.
	 * 
	 * @param insurers of type String
	 */
	public void setInsurers(String insurers);

	/**
	 * Get residual asset life
	 * 
	 * @return int
	 */
	public int getResidualAssetLife();

	/**
	 * set residual asset life
	 * 
	 * @param residualAssetLife of type int
	 */
	public void setResidualAssetLife(int residualAssetLife);

	/**
	 * Get residual asset life Unit of Measure (UOM)
	 * 
	 * @return String
	 */
	public String getResidualAssetLifeUOM();

	/**
	 * Set residual asset life unit of measure (UOM)
	 * 
	 * @param residualAssetLifeUOM of type String
	 */
	public void setResidualAssetLifeUOM(String residualAssetLifeUOM);

	/**
	 * Get scrap value
	 * 
	 * @return Amount
	 */
	public Amount getScrapValue();

	/**
	 * Set scrap value
	 * 
	 * @param scrapValue of type Amount
	 */
	public void setScrapValue(Amount scrapValue);

	public Amount getAssetValue();

	public void setAssetValue(Amount assetValue);

	public int getDocPerfectAge();

	public void setDocPerfectAge(int docPerfectAge);

	public Amount getSalesProceed();

	public void setSalesProceed(Amount salesProceed);

	public String getRegistrationNo();

	public void setRegistrationNo(String registrationNo);

	public String getPrevOwnerName();

	public void setPrevOwnerName(String prevOwnerName);

	public String getPrevFinancierName();

	public void setPrevFinancierName(String prevFinancierName);

	public Date getRepossessionDate();

	public void setRepossessionDate(Date repossessionDate);

	public int getRepossessionAge();

	public void setRepossessionAge(int repossessionAge);

	public String getYard();

	public void setYard(String yard);

	public String getRegistrationCardNo();

	public void setRegistrationCardNo(String registrationCardNo);

	public Amount getRegistrationFee();

	public void setRegistrationFee(Amount registrationFee);

	public String getBrand();

	public void setBrand(String brand);

	public String getGoodStatus();

	public void setGoodStatus(String goodStatus);
	
    public Date getChattelSoldDate();
    
    public void setChattelSoldDate(Date chattelSoldDate);
    
/*    public String getRlSerialNumber();
    
    public void setRlSerialNumber(String rlSerialNumber);*/
    
    public String getPubTransport();
    
    public void setPubTransport(String pubTransport);
    
	public String getRlSerialNumber();
	
	public void setRlSerialNumber(String rlSerialNumber);
	
	public String getInspectionStatusCategoryCode();

	public void setInspectionStatusCategoryCode(String inspectionStatusCategoryCode);

	public String getInspectionStatusEntryCode();

	public void setInspectionStatusEntryCode(String inspectionStatusEntryCode);
	
}
