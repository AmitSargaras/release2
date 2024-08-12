/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/IGeneralCharge.java,v 1.14 2005/08/12 08:13:38 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;

/**
 * This interface represents Asset of type General Charge.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2005/08/12 08:13:38 $ Tag: $Name: $
 */
public interface IGeneralCharge extends IAssetBasedCollateral {
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
	 * Get other remarks of the asset charge.
	 * 
	 * @return String
	 */
	public String getOtherRemarks();

	/**
	 * Set other remarks of the asset charge.
	 * 
	 * @param otherRemarks is of type String
	 */
	public void setOtherRemarks(String otherRemarks);

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
	 * Get valuation CMV - stocks.
	 * 
	 * @return Amount
	 */
	public Amount getStockCMV();

	/**
	 * set valuation CMV - stocks.
	 * 
	 * @param stockCMV of type Amount
	 */
	public void setStockCMV(Amount stockCMV);

	/**
	 * Get valuation FSV - stocks.
	 * 
	 * @return Amount
	 */
	public Amount getStockFSV();

	/**
	 * Set valuation FSV - stocks.
	 * 
	 * @param stockFSV of type Amount
	 */
	public void setStockFSV(Amount stockFSV);

	/**
	 * Get margin - stocks.
	 * 
	 * @return double
	 */
	public double getStockMargin();

	/**
	 * Set margin - stocks.
	 * 
	 * @param stockMargin of type double
	 */
	public void setStockMargin(double stockMargin);

	/**
	 * Get Stock Insurance Grace Period
	 * 
	 * @return String
	 */
	public int getStockInsrGracePeriod();

	/**
	 * Set Stock Insurance Grace Period
	 * 
	 * @param stockInsrGracePeriod of type double
	 */
	public void setStockInsrGracePeriod(int stockInsrGracePeriod);

	/**
	 * Get Stock Insurance Grace Period Frequency
	 * 
	 * @return String
	 */
	public String getStockInsrGracePeriodFreq();

	/**
	 * Set Stock Insurance Grace Period Frequency
	 * 
	 * @param stockInsrGracePeriodFreq of type String
	 */
	public void setStockInsrGracePeriodFreq(String stockInsrGracePeriodFreq);

	/**
	 * Get Stock Insurance Shortfall Amount
	 * 
	 * @return Amount
	 */
	public Amount getStockInsrShortfallAmount();

	/**
	 * Set Stock Insurance Shortfall Amount
	 * 
	 * @param shortfallAmt of type Amount
	 */
	public void setStockInsrShortfallAmount(Amount shortfallAmt);

	/**
	 * Get valuation CMV - debtors.
	 * 
	 * @return Amount
	 */
	public Amount getDebtorCMV();

	/**
	 * Set valuation CMV - debtors.
	 * 
	 * @param debtorCMV of type Amount
	 */
	public void setDebtorCMV(Amount debtorCMV);

	/**
	 * Get valuation FSV - debtors.
	 * 
	 * @return Amount
	 */
	public Amount getDebtorFSV();

	/**
	 * Set valuation FSV - debtors.
	 * 
	 * @param debtorFSV of type Amount
	 */
	public void setDebtorFSV(Amount debtorFSV);

	/**
	 * Get margin - debtors.
	 * 
	 * @return double
	 */
	public double getDebtorMargin();

	/**
	 * Set margin - debtors.
	 * 
	 * @param debtorMargin of type double
	 */
	public void setDebtorMargin(double debtorMargin);

	/**
	 * Get valuation CMV - fixed assets/others.
	 * 
	 * @return Amount
	 */
	public Amount getFixedAssetOthersCMV();

	/**
	 * Set valuation CMV - fixed assets/others.
	 * 
	 * @param faoCMV of type Amount
	 */
	public void setFixedAssetOthersCMV(Amount faoCMV);

	/**
	 * Get valuation FSV - fixed assets/others.
	 * 
	 * @return Amount
	 */
	public Amount getFixedAssetOthersFSV();

	/**
	 * Set valuation FSV - fixed assets/others.
	 * 
	 * @param faoFSV of type Amount
	 */
	public void setFixedAssetOthersFSV(Amount faoFSV);

	/**
	 * Get margin - fixed assets/others.
	 * 
	 * @return double
	 */
	public double getFixedAssetOthersMargin();

	/**
	 * Set margin - fixed assets/others.
	 * 
	 * @param faoMargin of type double
	 */
	public void setFixedAssetOthersMargin(double faoMargin);

	/**
	 * Get FAO Insurance Grace Period
	 * 
	 * @return String
	 */
	public int getFaoInsrGracePeriod();

	/**
	 * Set FAO Insurance Grace Period
	 * 
	 * @param faoInsrGracePeriod of type double
	 */
	public void setFaoInsrGracePeriod(int faoInsrGracePeriod);

	/**
	 * Get FAO Insurance Grace Period Frequency
	 * 
	 * @return String
	 */
	public String getFaoInsrGracePeriodFreq();

	/**
	 * Set FAO Insurance Grace Period Frequency
	 * 
	 * @param faoInsrGracePeriodFreq of type String
	 */
	public void setFaoInsrGracePeriodFreq(String faoInsrGracePeriodFreq);

	/**
	 * Get Bank's participating share
	 * 
	 * @return double
	 */
	public double getBankShare();

	/**
	 * Set Bank's participating share
	 * 
	 * @param bankShare of type double
	 */
	public void setBankShare(double bankShare);

	/**
	 * Get drawing power gross amount without taking into consideration
	 * insurance coverage.
	 * 
	 * @ return Amount
	 */
	public Amount getDrawingPowerGrossAmount();

	/**
	 * Set drawing power gross amount.
	 * 
	 * @param dpGrossAmt - Amount
	 */
	public void setDrawingPowerGrossAmount(Amount dpGrossAmt);

	/**
	 * Get drawing power gross amount taking into consideration insurance
	 * coverage.
	 * 
	 * @ return Amount
	 */
	public Amount getDrawingPowerLessInsrGrossAmount();

	/**
	 * Set drawing power gross amount taking into consideration insurance
	 * coverage
	 * 
	 * @param dpLessInsrGrossAmt - Amount
	 */
	public void setDrawingPowerLessInsrGrossAmount(Amount dpLessInsrGrossAmt);

	/**
	 * Get last index used to generated id - stock.
	 * 
	 * @return double
	 */
	public int getLastUsedStockIndex();

	/**
	 * Set last index used to generated id - stock.
	 * 
	 * @param lastUsedStockIndex of type int
	 */
	public void setLastUsedStockIndex(int lastUsedStockIndex);

	/**
	 * Get last index used to generated id - fixed assets/others.
	 * 
	 * @return double
	 */
	public int getLastUsedFixedAssetOthersIndex();

	/**
	 * Set last index used to generated id - fixed assets/others.
	 * 
	 * @param lastUsedStockIndex of type int
	 */
	public void setLastUsedFixedAssetOthersIndex(int lastUsedStockIndex);

	/**
	 * Get last index used to generated id - Insurance.
	 * 
	 * @return int
	 */
	public int getLastUsedInsuranceIndex();

	/**
	 * Set last index used to generated id - Insurance.
	 * 
	 * @param lastUsedInsuranceIndex of type int
	 */
	public void setLastUsedInsuranceIndex(int lastUsedInsuranceIndex);

	/**
	 * Get debtor details for the security.
	 * 
	 * @return IDebtor
	 */
	public IDebtor getDebtor();

	/**
	 * Set debtor details for the security.
	 * 
	 * @param debtor - IDebtor
	 */
	public void setDebtor(IDebtor debtor);

	/**
	 * Add insurance policy for the security if the insuranceID does not exist.
	 * Otherwise, the existing insurance policy will be updated.
	 * 
	 * @param insurance - IInsurance
	 */
	// public void updateInsurance(IInsurance insurance);
	/**
	 * Get stocks tied to the security.
	 * 
	 * @return Map with key as String stockID and value as IStock
	 */
	public Map getStocks();

	/**
	 * Set stocks tied to the security.
	 * 
	 * @param stocksMap - Map with key as String stockID and value as IStock
	 */
	public void setStocks(Map stocksMap);

	/**
	 * Add stock for the security if the stockID does not exist. Otherwise, the
	 * existing stock will be updated.
	 * 
	 * @param stock - IStock
	 */
	// public void updateStock(IStock stock);
	/**
	 * Get fixed assets/others tied to the security.
	 * 
	 * @return Map with key as String faoID and value as IInsurance
	 */
	public Map getFixedAssetOthers();

	/**
	 * Set fixed assets/others tied to the security.
	 * 
	 * @param faoMap - Map with key as String faoID and value as IInsurance
	 */
	public void setFixedAssetOthers(Map faoMap);

	/**
	 * Add fixed asset/others for the security if the faoID does not exist.
	 * Otherwise, the existing stock will be updated.
	 * 
	 * @param fao - IFixedAssetOthers
	 */
	// public void updateFixedAssetOthers(IFixedAssetOthers fao);
	/**
	 * Get insurance - to - stock mapping.
	 * 
	 * @return Map with key as String insuranceID and value as List of String
	 *         stockID
	 */
	public Map get_Insurance_Stock_Map();

	/**
	 * Set insurance - to - stock mapping.
	 * 
	 * @param insuranceStockMap - Map with key as String insuranceID and value
	 *        as List of String stockID
	 */
	public void set_Insurance_Stock_Map(Map insuranceStockMap);

	/**
	 * Add fixed asset/others for the security if the faoID does not exist.
	 * Otherwise, the existing stock will be updated.
	 * 
	 * @param fao - IFixedAssetOthers
	 */
	// public void update_Insurance_Stock_Map(String insuranceID, List
	// stockIDs);
	/**
	 * Get stock - to - insurance mapping.
	 * 
	 * @return Map with key as String stockID and value as List of String
	 *         insuranceID
	 */
	public Map get_Stock_Insurance_Map();

	/**
	 * Set stock - to - insurance mapping.
	 * 
	 * @param stockInsuranceMap - Map with key as String stockID and value as
	 *        List of String insuranceID
	 */
	public void set_Stock_Insurance_Map(Map stockInsuranceMap);

	/**
	 * Get insurance policies - to - fixed assets/others mapping.
	 * 
	 * @return Map with key as String insuranceID and value as List of String
	 *         faoID
	 */
	public Map get_Insurance_FixedAssetOthers_Map();

	/**
	 * Set insurance policies - to - fixed assets/others mapping.
	 * 
	 * @param insuranceFaoMap - Map with key as String insuranceID and value as
	 *        List of String faoID
	 */
	public void set_Insurance_FixedAssetOthers_Map(Map insuranceFaoMap);

	/**
	 * Get fixed assets/others - to - insurance policy mapping.
	 * 
	 * @return Map with key as String faoID and value as List of String
	 *         insuranceID
	 */
	public Map get_FixedAssetOthers_Insurance_Map();

	/**
	 * set fixed assets/others - to - insurance policy mapping.
	 * 
	 * @param faoInsuranceMap with key as String faoID and value as List of
	 *        String insuranceID
	 */
	public void set_FixedAssetOthers_Insurance_Map(Map faoInsuranceMap);

	/**
	 * Get list of insurance policy ID given a stockID or faoID.
	 * 
	 * @param id - String representing stockID or faoID
	 * @return List of String insuranceID
	 */
	public List getInsuranceIDList(String id, int type);

	/**
	 * Get list of stock ID given an insuranceID.
	 * 
	 * @param insuranceID - String representing insuranceID
	 * @return List of String stockID/faoID
	 */
	public List getStockIDList(String insuranceID);

	/**
	 * Get list of faoID given a insuranceID.
	 * 
	 * @param insuranceID - String representing insuranceID
	 * @return List of String faoID
	 */
	public List getFixedAssetOthersIDList(String insuranceID);

	/**
	 * Get end date of grace period for the specific insurance.
	 * 
	 * @param insr - IInsurancePolicy for which to calculate the grace period
	 * @return Date - end date of grace period
	 */
	public Date getInsuranceGracePeriodEndDate(IInsurancePolicy insr);

	/**
	 * Generate new ID for the specific type and increment the appropriate
	 * index. This is used by the UI.
	 * 
	 * @return String representing the new ID
	 */
	public String generateNewID(int type);

	// TODO : add methods to calculate values for drawing power

	// to check with SH if these are still required
	/**
	 * Get total insurance coverage.
	 * 
	 * @return Amount
	 */
	// public Amount getTotalCoverage();
	/**
	 * Set total insurance coverage.
	 * 
	 * @param totalCoverage of type Amount
	 */
	// public void setTotalCoverage(Amount totalCoverage);
	// added by Jitendra - MBB-474
	/*
	 * public Amount getDepreciateRate() ; public void setDepreciateRate(Amount
	 * depreciateRate) ;
	 */

	public boolean getIsPhysicalInspection();

	public void setIsPhysicalInspection(boolean isPhysicalInspection);

	public int getPhysicalInspectionFreq();

	public void setPhysicalInspectionFreq(int physicalInspectionFreq);

	public String getPhysicalInspectionFreqUnit();

	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);

	public Date getLastPhysicalInspectDate();

	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);

	public Date getNextPhysicalInspectDate();

	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);

    public Date getChattelSoldDate();
    
    public void setChattelSoldDate(Date chattelSoldDate);
    
    public String getRlSerialNumber();
    
    public void setRlSerialNumber(String rlSerialNumber);
    
    public IGeneralChargeDetails[] getGeneralChargeDetails() ;
	public void setGeneralChargeDetails(IGeneralChargeDetails[] generalChargeDetails);
	
	public boolean addGeneralChargeDetails(IGeneralChargeDetails chargeDetails);
	
	public boolean replaceGeneralChargeDetails(int index, IGeneralChargeDetails chargeDetails) throws Exception;
	
	public Map<String, String> getDueDateAndStockStatements();
	public void setDueDateAndStockStatements(Map<String, String> dueDateAndStockStatements);
	
}
