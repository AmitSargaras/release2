/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/OBGeneralCharge.java,v 1.53 2006/07/14 03:31:09 jychong Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.OBAssetBasedCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.DueDateAndStockHelper;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.SaveDueDateAndStockCommand;

/**
 * This class represents Asset of type General Charge.
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.53 $
 * @since $Date: 2006/07/14 03:31:09 $ Tag: $Name: $
 */
public class OBGeneralCharge extends OBAssetBasedCollateral implements IGeneralCharge {
	// static
	public static final int TYPE_STOCK = 1;

	public static final int TYPE_FIXEDASSETOTHERS = 2;

	public static final int TYPE_INSURANCE = 3;

	// common asset attributes
	private String remarks; // this is actually the asset description

	private String envRiskyStatus;

	private Date envRiskyDate;

	private String envRiskyRemarks;

	// attributes specific to general charge
	private String otherRemarks;

	private Amount dpGrossAmt;

	private Amount dpLessInsrGrossAmt;

	// debtor - TODO : consider moving cmv, fsv, margin into debtor details
	private Amount debtorCMV;

	private Amount debtorFSV;

	private double debtorMargin = ICMSConstant.DOUBLE_INVALID_VALUE;

	private IDebtor debtor;

	// stock
	private Amount stockCMV;

	private Amount stockFSV;

	private double stockMargin = ICMSConstant.DOUBLE_INVALID_VALUE;

	private Map stockMap;

	private int stockInsrGracePeriod = ICMSConstant.INT_INVALID_VALUE;

	private String stockInsrGracePeriodFreq;

	private Amount stockInsrShortfallAmt;

	// fixed asset/others
	private Amount faoCMV;

	private Amount faoFSV;

	private double faoMargin = ICMSConstant.DOUBLE_INVALID_VALUE;

	private Map faoMap;

	private int faoInsrGracePeriod = ICMSConstant.INT_INVALID_VALUE;

	private String faoInsrGracePeriodFreq;

	// insurance and mappings
	private Map insuranceID_StockID_Map;

	private Map insuranceID_FixedAssetOthersID_Map;

	private Map stockID_InsuranceID_Map;

	private Map fixedAssetOthersID_InsuranceID_Map;

	// drawing power
	private double bankShare = ICMSConstant.DOUBLE_INVALID_VALUE;

	// last index used to generate ID for stock, FAO and Insurance
	private int lastUsedStockIndex = 0;

	private int lastUsedFaoIndex = 0;

	private int lastUsedInsuranceIndex = 0;

	// added by Jitendra - MBB-474

	// private Amount depreciateRate ;
	private boolean isPhysicalInspection;

	private int physicalInspectionFreq;

	private String physicalInspectionFreqUnit;

	private Date lastPhysicalInspectDate;

	private Date nextPhysicalInspectDate;

	private Date chattelSoldDate;

	private String rlSerialNumber;
	//Added by Anil for generalChargeDetails 
	private IGeneralChargeDetails [] generalChargeDetails;
	
	private List<IGeneralChargeDetails> generalChargeDetailList;
	
	private Map<String, String> dueDateAndStockStatements;
	/*
	 * public Amount getDepreciateRate() { return depreciateRate; }
	 * 
	 * public void setDepreciateRate(Amount depreciateRate) {
	 * this.depreciateRate = depreciateRate; }
	 */

	public String getRlSerialNumber() {
		return rlSerialNumber;
	}

	public void setRlSerialNumber(String rlSerialNumber) {
		this.rlSerialNumber = rlSerialNumber;
	}

	public Date getChattelSoldDate() {
		return chattelSoldDate;
	}

	public void setChattelSoldDate(Date chattelSoldDate) {
		this.chattelSoldDate = chattelSoldDate;
	}

	public boolean getIsPhysicalInspection() {
		return isPhysicalInspection;
	}

	public void setIsPhysicalInspection(boolean isPhysicalInspection) {
		this.isPhysicalInspection = isPhysicalInspection;
	}

	public int getPhysicalInspectionFreq() {
		return physicalInspectionFreq;
	}

	public void setPhysicalInspectionFreq(int physicalInspectionFreq) {
		this.physicalInspectionFreq = physicalInspectionFreq;
	}

	public String getPhysicalInspectionFreqUnit() {
		return physicalInspectionFreqUnit;
	}

	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit) {
		this.physicalInspectionFreqUnit = physicalInspectionFreqUnit;
	}

	public Date getLastPhysicalInspectDate() {
		return lastPhysicalInspectDate;
	}

	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate) {
		this.lastPhysicalInspectDate = lastPhysicalInspectDate;
	}

	public Date getNextPhysicalInspectDate() {
		return nextPhysicalInspectDate;
	}

	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate) {
		this.nextPhysicalInspectDate = nextPhysicalInspectDate;
	}

	/**
	 * Default Constructor.
	 */
	public OBGeneralCharge() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IGeneralCharge
	 */
	public OBGeneralCharge(IGeneralCharge obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get insurance map.
	 * 
	 * @return a map with key as String reference id and value as
	 *         IInsurancePolicy
	 */
	public Map getInsurance() {
		Map insr = super.getInsurance();
		if ((insr == null) || insr.isEmpty()) {
			insr = new HashMap();
			IInsurancePolicy[] policies = super.getInsurancePolicies();
			int len = policies == null ? 0 : policies.length;
			for (int i = 0; i < len; i++) {
				insr.put(policies[i].getRefID(), policies[i]);
			}
			super.setInsurance(insr);
		}
		return insr;
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
	 * Get other remarks of the asset charge.
	 * 
	 * @return String
	 */
	public String getOtherRemarks() {
		return otherRemarks;
	}

	/**
	 * Set other remarks of the asset charge.
	 * 
	 * @param otherRemarks is of type String
	 */
	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}

	/**
	 * Get security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @return String
	 */
	public String getEnvRiskyStatus() {
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
	 * Get valuation CMV - debtors.
	 * 
	 * @return Amount
	 */
	public Amount getDebtorCMV() {
		return debtorCMV;
	}

	/**
	 * Set valuation CMV - debtors.
	 * 
	 * @param debtorCMV of type Amount
	 */
	public void setDebtorCMV(Amount debtorCMV) {
		this.debtorCMV = debtorCMV;
	}

	/**
	 * Get valuation FSV - debtors.
	 * 
	 * @return Amount
	 */
	public Amount getDebtorFSV() {
		return debtorFSV;
	}

	/**
	 * Set valuation FSV - debtors.
	 * 
	 * @param debtorFSV of type Amount
	 */
	public void setDebtorFSV(Amount debtorFSV) {
		this.debtorFSV = debtorFSV;
	}

	/**
	 * Get margin - debtors.
	 * 
	 * @return double
	 */
	public double getDebtorMargin() {
		return debtorMargin;
	}

	/**
	 * Set margin - debtors.
	 * 
	 * @param debtorMargin of type double
	 */
	public void setDebtorMargin(double debtorMargin) {
		this.debtorMargin = debtorMargin;
	}

	/**
	 * Get valuation CMV - stocks.
	 * 
	 * @return Amount
	 */
	public Amount getStockCMV() {
		return stockCMV;
	}

	/**
	 * set valuation CMV - stocks.
	 * 
	 * @param stockCMV of type Amount
	 */
	public void setStockCMV(Amount stockCMV) {
		this.stockCMV = stockCMV;
	}

	/**
	 * Get valuation FSV - stocks.
	 * 
	 * @return Amount
	 */
	public Amount getStockFSV() {
		return stockFSV;
	}

	/**
	 * Set valuation FSV - stocks.
	 * 
	 * @param stockFSV of type Amount
	 */
	public void setStockFSV(Amount stockFSV) {
		this.stockFSV = stockFSV;
	}

	/**
	 * Get margin - stocks.
	 * 
	 * @return double
	 */
	public double getStockMargin() {
		return stockMargin;
	}

	/**
	 * Set margin - stocks.
	 * 
	 * @param stockMargin of type double
	 */
	public void setStockMargin(double stockMargin) {
		this.stockMargin = stockMargin;
	}

	/**
	 * Get Stock Insurance Grace Period
	 * 
	 * @return int
	 */
	public int getStockInsrGracePeriod() {
		return stockInsrGracePeriod;
	}

	/**
	 * Set Stock Insurance Grace Period
	 * 
	 * @param stockInsrGracePeriod of type int
	 */
	public void setStockInsrGracePeriod(int stockInsrGracePeriod) {
		this.stockInsrGracePeriod = stockInsrGracePeriod;
	}

	/**
	 * Get Stock Insurance Grace Period Frequency
	 * 
	 * @return String
	 */
	public String getStockInsrGracePeriodFreq() {
		return stockInsrGracePeriodFreq;
	}

	/**
	 * Set Stock Insurance Grace Period Frequency
	 * 
	 * @param stockInsrGracePeriodFreq of type String
	 */
	public void setStockInsrGracePeriodFreq(String stockInsrGracePeriodFreq) {
		this.stockInsrGracePeriodFreq = stockInsrGracePeriodFreq;
	}

	/**
	 * Get Stock Insurance Shortfall Amount
	 * 
	 * @return Amount
	 */
	public Amount getStockInsrShortfallAmount() {
		return stockInsrShortfallAmt;
	}

	/**
	 * Set Stock Insurance Shortfall Amount
	 * 
	 * @param shortfallAmt of type Amount
	 */
	public void setStockInsrShortfallAmount(Amount shortfallAmt) {
		this.stockInsrShortfallAmt = shortfallAmt;
	}

	/**
	 * Get drawing power gross amount without taking into consideration
	 * insurance coverage.
	 * 
	 * @ return Amount
	 */
	public Amount getDrawingPowerGrossAmount() {
		return dpGrossAmt;
	}

	/**
	 * Set drawing power gross amount.
	 * 
	 * @param dpGrossAmt - Amount
	 */
	public void setDrawingPowerGrossAmount(Amount dpGrossAmt) {
		this.dpGrossAmt = dpGrossAmt;
	}

	/**
	 * Get drawing power gross amount taking into consideration insurance
	 * coverage.
	 * 
	 * @ return Amount
	 */
	public Amount getDrawingPowerLessInsrGrossAmount() {
		return dpLessInsrGrossAmt;
	}

	/**
	 * Set drawing power gross amount taking into consideration insurance
	 * coverage
	 * 
	 * @param dpLessInsrGrossAmt - Amount
	 */
	public void setDrawingPowerLessInsrGrossAmount(Amount dpLessInsrGrossAmt) {
		this.dpLessInsrGrossAmt = dpLessInsrGrossAmt;
	}

	/**
	 * Get valuation CMV - fixed assets/others.
	 * 
	 * @return Amount
	 */
	public Amount getFixedAssetOthersCMV() {
		return faoCMV;
	}

	/**
	 * Set valuation CMV - fixed assets/others.
	 * 
	 * @param faoCMV of type Amount
	 */
	public void setFixedAssetOthersCMV(Amount faoCMV) {
		this.faoCMV = faoCMV;
	}

	/**
	 * Get valuation FSV - fixed assets/others.
	 * 
	 * @return Amount
	 */
	public Amount getFixedAssetOthersFSV() {
		return faoFSV;
	}

	/**
	 * Set valuation FSV - fixed assets/others.
	 * 
	 * @param faoFSV of type Amount
	 */
	public void setFixedAssetOthersFSV(Amount faoFSV) {
		this.faoFSV = faoFSV;
	}

	/**
	 * Get margin - fixed assets/others.
	 * 
	 * @return double
	 */
	public double getFixedAssetOthersMargin() {
		return faoMargin;
	}

	/**
	 * Set margin - fixed assets/others.
	 * 
	 * @param faoMargin of type double
	 */
	public void setFixedAssetOthersMargin(double faoMargin) {
		this.faoMargin = faoMargin;
	}

	/**
	 * Get FAO Insurance Grace Period
	 * 
	 * @return int
	 */
	public int getFaoInsrGracePeriod() {
		return faoInsrGracePeriod;
	}

	/**
	 * Set FAO Insurance Grace Period
	 * 
	 * @param faoInsrGracePeriod of type int
	 */
	public void setFaoInsrGracePeriod(int faoInsrGracePeriod) {
		this.faoInsrGracePeriod = faoInsrGracePeriod;
	}

	/**
	 * Get FAO Insurance Grace Period Frequency
	 * 
	 * @return String
	 */
	public String getFaoInsrGracePeriodFreq() {
		return faoInsrGracePeriodFreq;
	}

	/**
	 * Set FAO Insurance Grace Period Frequency
	 * 
	 * @param faoInsrGracePeriodFreq of type String
	 */
	public void setFaoInsrGracePeriodFreq(String faoInsrGracePeriodFreq) {
		this.faoInsrGracePeriodFreq = faoInsrGracePeriodFreq;
	}

	/**
	 * Get Bank's participating share
	 * 
	 * @return double
	 */
	public double getBankShare() {
		return bankShare;
	}

	/**
	 * Set Bank's participating share
	 * 
	 * @param bankShare of type double
	 */
	public void setBankShare(double bankShare) {
		this.bankShare = bankShare;
	}

	/**
	 * Get last index used to generated id - stock.
	 * 
	 * @return int
	 */
	public int getLastUsedStockIndex() {
		return lastUsedStockIndex;
	}

	/**
	 * Set last index used to generated id - stock.
	 * 
	 * @param lastUsedStockIndex of type int
	 */
	public void setLastUsedStockIndex(int lastUsedStockIndex) {
		this.lastUsedStockIndex = lastUsedStockIndex;
	}

	/**
	 * Get last index used to generated id - fixed assets/others.
	 * 
	 * @return int
	 */
	public int getLastUsedFixedAssetOthersIndex() {
		return lastUsedFaoIndex;
	}

	/**
	 * Set last index used to generated id - fixed assets/others.
	 * 
	 * @param lastUsedFaoIndex of type int
	 */
	public void setLastUsedFixedAssetOthersIndex(int lastUsedFaoIndex) {
		this.lastUsedFaoIndex = lastUsedFaoIndex;
	}

	/**
	 * Get last index used to generated id - Insurance.
	 * 
	 * @return int
	 */
	public int getLastUsedInsuranceIndex() {
		return lastUsedInsuranceIndex;
	}

	/**
	 * Set last index used to generated id - Insurance.
	 * 
	 * @param lastUsedInsuranceIndex of type int
	 */
	public void setLastUsedInsuranceIndex(int lastUsedInsuranceIndex) {
		this.lastUsedInsuranceIndex = lastUsedInsuranceIndex;
	}

	/**
	 * Get debtor details for the security.
	 * 
	 * @return IDebtor
	 */
	public IDebtor getDebtor() {
		return debtor;
	}

	/**
	 * Set debtor details for the security.
	 * 
	 * @param debtor - IDebtor
	 */
	public void setDebtor(IDebtor debtor) {
		this.debtor = debtor;
	}

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
	public Map getStocks() {
		return stockMap;
	}

	/**
	 * Set stocks tied to the security.
	 * 
	 * @param stockMap - Map with key as String stockID and value as IStock
	 */
	public void setStocks(Map stockMap) {
		this.stockMap = stockMap;
	}

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
	public Map getFixedAssetOthers() {
		return faoMap;
	}

	/**
	 * Set fixed assets/others tied to the security.
	 * 
	 * @param faoMap - Map with key as String faoID and value as IInsurance
	 */
	public void setFixedAssetOthers(Map faoMap) {
		this.faoMap = faoMap;
	}

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
	public Map get_Insurance_Stock_Map() {
		return insuranceID_StockID_Map;
	}

	/**
	 * Set insurance - to - stock mapping.
	 * 
	 * @param insuranceStockMap - Map with key as String insuranceID and value
	 *        as List of String stockID
	 */
	public void set_Insurance_Stock_Map(Map insuranceStockMap) {
		this.insuranceID_StockID_Map = insuranceStockMap;
	}

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
	public Map get_Stock_Insurance_Map() {
		return stockID_InsuranceID_Map;
	}

	/**
	 * Set stock - to - insurance mapping.
	 * 
	 * @param stockInsuranceMap - Map with key as String stockID and value as
	 *        List of String insuranceID
	 */
	public void set_Stock_Insurance_Map(Map stockInsuranceMap) {
		this.stockID_InsuranceID_Map = stockInsuranceMap;
	}

	/**
	 * Get insurance policies - to - fixed assets/others mapping.
	 * 
	 * @return Map with key as String insuranceID and value as List of String
	 *         faoID
	 */
	public Map get_Insurance_FixedAssetOthers_Map() {
		return insuranceID_FixedAssetOthersID_Map;
	}

	/**
	 * Set insurance policies - to - fixed assets/others mapping.
	 * 
	 * @param insuranceFaoMap - Map with key as String insuranceID and value as
	 *        List of String faoID
	 */
	public void set_Insurance_FixedAssetOthers_Map(Map insuranceFaoMap) {
		this.insuranceID_FixedAssetOthersID_Map = insuranceFaoMap;
	}

	/**
	 * Get fixed assets/others - to - insurance policy mapping.
	 * 
	 * @return Map with key as String faoID and value as List of String
	 *         insuranceID
	 */
	public Map get_FixedAssetOthers_Insurance_Map() {
		return fixedAssetOthersID_InsuranceID_Map;
	}

	/**
	 * set fixed assets/others - to - insurance policy mapping.
	 * 
	 * @param faoInsuranceMap with key as String faoID and value as List of
	 *        String insuranceID
	 */
	public void set_FixedAssetOthers_Insurance_Map(Map faoInsuranceMap) {
		this.fixedAssetOthersID_InsuranceID_Map = faoInsuranceMap;
	}

	/**
	 * Get list of insurance policy ID given a stockID or faoID.
	 * 
	 * @param id - String representing stockID or faoID
	 * @return List of String insuranceID
	 */
	public List getInsuranceIDList(String id, int type) {
		List theList = null;
		if ((id != null) && (type == TYPE_STOCK) && (stockID_InsuranceID_Map != null)) {
			theList = (List) stockID_InsuranceID_Map.get(id);
		}
		if ((id != null) && (type == TYPE_FIXEDASSETOTHERS) && (fixedAssetOthersID_InsuranceID_Map != null)) {
			theList = (List) fixedAssetOthersID_InsuranceID_Map.get(id);
		}
		return (theList != null) ? theList : new ArrayList(0);
	}

	/**
	 * Get list of stock ID given an insuranceID.
	 * 
	 * @param insuranceID - String representing insuranceID
	 * @return List of String stockID/faoID
	 */
	public List getStockIDList(String insuranceID) {
		List theList = null;
		if ((insuranceID != null) && (insuranceID_StockID_Map != null)) {
			theList = (List) insuranceID_StockID_Map.get(insuranceID);
		}
		return (theList != null) ? theList : new ArrayList(0);
	}

	/**
	 * Get list of faoID given a insuranceID.
	 * 
	 * @param insuranceID - String representing insuranceID
	 * @return List of String faoID
	 */
	public List getFixedAssetOthersIDList(String insuranceID) {
		List theList = null;
		if ((insuranceID != null) && (insuranceID_FixedAssetOthersID_Map != null)) {
			theList = (List) insuranceID_FixedAssetOthersID_Map.get(insuranceID);
		}
		return (theList != null) ? theList : new ArrayList(0);
	}

	/**
	 * Generate new ID for the specific type and increment the appropriate
	 * index. This is used by the UI.
	 * 
	 * @return String representing the new ID
	 */
	public String generateNewID(int type) {
		int newIdx = 0;
		String newID = null;
		if (type == TYPE_STOCK) {
			newID = OBStock.generateNewID(getLastUsedStockIndex() + 1);
		}
		else if (type == TYPE_FIXEDASSETOTHERS) {
			newID = OBFixedAssetOthers.generateNewID(getLastUsedFixedAssetOthersIndex() + 1);
		}
		else if (type == TYPE_INSURANCE) {
			newID = OBInsurancePolicy.generateNewID(getLastUsedInsuranceIndex() + 1);
		}
		return newID;
	}

	////////////////////////////////////////////////////////////////////////////
	// //////
	// /////// Methods to calculate derived values
	// ////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	// //////

	// todo : consider doing all the calculation for drawing power in one step
	// maybe even create a summary object

	/**
	 * Get end date of grace period for the specific insurance.
	 * 
	 * @param insr - IInsuranceInfo for which to calculate the grace period
	 * @return Date - end date of grace period
	 */
	public Date getInsuranceGracePeriodEndDate(IInsurancePolicy insr) {
		if (insr.getExpiryDate() != null) {
			int gracePeriod = insr.getCategory().equals(IInsurancePolicy.STOCK) ? getStockInsrGracePeriod()
					: getFaoInsrGracePeriod();
			String gracePeriodFreq = insr.getCategory().equals(IInsurancePolicy.STOCK) ? getStockInsrGracePeriodFreq()
					: getFaoInsrGracePeriodFreq();
			DefaultLogger.debug(this, "insurance category: " + insr.getCategory() + "\tgracePeriod: " + gracePeriod
					+ "\tunit: " + gracePeriodFreq);
			// DefaultLogger.debug(this, "GracePeriodEndDate: " +
			// CommonUtil.rollUpDate(insr.getExpiryDate(), gracePeriod,
			// gracePeriodFreq));
			return ((gracePeriod > 0) && (gracePeriodFreq != null)) ? CommonUtil.rollUpDate(insr.getExpiryDate(),
					gracePeriod, gracePeriodFreq) : insr.getExpiryDate();
		}
		return null;
	}

	/**
	 * Get calculated CMV for stock in CMS Security Currency.
	 * 
	 * @return Amount - Stock CMV
	 */
	public Amount getCalculatedStockCMV() {
		return getStockGrossValue();
	}

	/**
	 * Get calculated FSV for stock in CMS Security Currency.
	 * 
	 * @return Amount - Stock FSV
	 */
	public Amount getCalculatedStockFSV() {
		return getStockNetValue();
	}

	/**
	 * Get calculated CMV for fao in CMS Security Currency.
	 * 
	 * @return Amount - FAO CMV
	 */
	public Amount getCalculatedFAOCMV() {
		return getFAOGrossValue();
	}

	/**
	 * Get calculated FSV for fao in CMS Security Currency.
	 * 
	 * @return Amount - FAO FSV
	 */
	public Amount getCalculatedFAOFSV() {
		return getFAONetValue();
	}

	/**
	 * Get calculated CMV for debtor in CMS Security Currency.
	 * 
	 * @return Amount - Debtor CMV
	 */
	public Amount getCalculatedDebtorCMV() {
		return getDebtorGrossValue();
	}

	/**
	 * Get calculated FSV for debtor in CMS Security Currency.
	 * 
	 * @param totalNetStockAmt - Amount
	 * @return Amount - Debtor FSV
	 */
	public Amount getCalculatedDebtorFSV(Amount totalNetStockAmt) {
		return getDebtorNetValue(totalNetStockAmt);
	}

	/**
	 * Get calculated gross value for debtor in CMS Security Currency. Also the
	 * applicable debt amount.
	 * 
	 * @return Amount - Debtor Gross Value
	 */
	public Amount getDebtorGrossValue() {
		OBDebtor debtor = (OBDebtor) getDebtor();
		if (debtor == null) {
			return null;
		}
		Amount cmv = null;
		try {
			CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
			cmv = debtor.getGrossValue();
			cmv = GeneralChargeUtil.convertAmount(cmv, cmsSecurityCCYCode);
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating debtor gross value : " + e.toString());
		}
		return cmv;
	}

	/**
	 * Get calculated net value for debtor in CMS Security Currency. Substract
	 * net stock only if total net stock is negative, before margin is applied.
	 * 0 amount is returned if result is negative.
	 * 
	 * @param totalNetStockAmt - total net stock amount in CMS Security Currency
	 * @return Amount - Debtor Net Value
	 */
	public Amount getDebtorNetValue(Amount totalNetStockAmt) {
		double margin = getDebtorMargin();
		Amount applicableDebtAmt = getApplicableDebtAmount();
		Amount applicableDebtAmtLessNetStock = getApplicableDebtAmountLessNetStock(applicableDebtAmt, totalNetStockAmt);
		if ((applicableDebtAmtLessNetStock == null) || (margin < 0)) {
			return null;
		}
		if (GeneralChargeUtil.isForexErrorAmount(applicableDebtAmtLessNetStock)) {
			return GeneralChargeUtil.getForexErrorAmount();
		}
		if (margin == 0) {
			return new Amount(0, getCurrencyCode());
		}

		return ((applicableDebtAmtLessNetStock.getAmount() < 0) || (margin == 100)) ? applicableDebtAmtLessNetStock
				: applicableDebtAmtLessNetStock.multiply(new BigDecimal(margin));
	}

	/**
	 * Get total gross value for undeleted stocks in CMS Security Currency.
	 * 
	 * @return Amount - Gross value for stocks.
	 */
	public Amount getStockGrossValue() {
		Map map = getStocks();
		if (map == null) {
			return null;
		}
		return getGrossValue(map, false);
	}

	/**
	 * Get total amount owed to creditors for undeleted stocks in CMS Security
	 * Currency.
	 * 
	 * @return Amount
	 */
	public Amount getTotalStockCreditors() {
		if (stockMap == null) {
			return null;
		}
		String stockID = null;
		Amount totalCreditorsAmt = null;
		try {
			CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
			for (Iterator iterator = stockMap.values().iterator(); iterator.hasNext();) {
				OBStock stock = (OBStock) iterator.next();
				if (!ICMSConstant.STATE_DELETED.equals(stock.getStatus()) && (stock.getCreditorAmt() != null)) {
					Amount creditorAmt = GeneralChargeUtil.convertAmount(stock.getCreditorAmt(), cmsSecurityCCYCode);

					// return forex error if any of the creditor amt to be added
					// is forex error
					if (GeneralChargeUtil.isForexErrorAmount(creditorAmt)) {
						return GeneralChargeUtil.getForexErrorAmount();
					}

					totalCreditorsAmt = GeneralChargeUtil.add(totalCreditorsAmt, creditorAmt);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating total gross value for [" + stockID + "] : "
					+ e.toString());
		}
		return totalCreditorsAmt;
	}

	/**
	 * Get total gross value for undeleted fao in CMS Security Currency.
	 * 
	 * @return Amount - Gross value for fao.
	 */
	public Amount getFAOGrossValue() {
		Map map = getFixedAssetOthers();
		if (map == null) {
			return null;
		}
		return getGrossValue(map, false);
	}

	/**
	 * Get total net value for undeleted stocks in CMS Security Currency.
	 * 
	 * @return Amount - Net value for stocks.
	 */
	public Amount getStockNetValue() {
		Map map = getStocks();
		if (map == null) {
			return null;
		}
		return getNetValue(map, false);
	}

	/**
	 * Get total net value for undeleted fao in CMS Security Currency.
	 * 
	 * @return Amount - Net value for fao.
	 */
	public Amount getFAONetValue() {
		Map map = getFixedAssetOthers();
		if (map == null) {
			return null;
		}
		return getNetValue(map, false);
	}

	/**
	 * Get total valid(not expired and not deleted) insurance tied to stocks in
	 * CMS Security Currency.
	 * 
	 * @return Amount
	 */
	public Amount getTotalValidStockInsrAmount() {
		Map insuranceMap = super.getInsurance();
		if ((stockID_InsuranceID_Map == null) || (insuranceMap == null) || (stockID_InsuranceID_Map.values() == null)) {
			return new Amount(0, getCurrencyCode());
		}

		Amount totalInsrAmt = null;
		try {
			CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
			Date currentDate = DateUtil.initializeStartDate(DateUtil.getDate());
			HashMap insrExpiryStatusMap = new HashMap();
			for (Iterator stockItr = stockID_InsuranceID_Map.values().iterator(); stockItr.hasNext();) {
				List insrIDs = (List) stockItr.next();
				if (insrIDs != null) {
					for (Iterator insrItr = insrIDs.iterator(); insrItr.hasNext();) {
						IGenChargeMapEntry entry = (IGenChargeMapEntry) insrItr.next();
						String insrID = (String) entry.getInsuranceID();
						if (insrExpiryStatusMap.containsKey(insrID)
								|| ICMSConstant.STATE_DELETED.equals(entry.getStatus())) {
							continue;
						}

						boolean isInsrExpired = true;
						IInsurancePolicy insr = (IInsurancePolicy) insuranceMap.get(insrID);
						Date gracePeriodEndDate = getInsuranceGracePeriodEndDate(insr);
						if (gracePeriodEndDate != null) {
							gracePeriodEndDate = DateUtil.initializeStartDate(gracePeriodEndDate);
							isInsrExpired = ((gracePeriodEndDate != null) && (currentDate.compareTo(gracePeriodEndDate) > 0));
						}
						else {
							// if no expiry date found, default insr as expired
							isInsrExpired = true;
						}
						insrExpiryStatusMap.put(insrID, new Boolean(isInsrExpired));

						if (!isInsrExpired) {
							Amount convertedInsrAmt = GeneralChargeUtil.convertAmount(insr.getInsuredAmount(),
									cmsSecurityCCYCode);

							// return forex error if any of the insr amt to be
							// added is forex error
							if (GeneralChargeUtil.isForexErrorAmount(convertedInsrAmt)) {
								return GeneralChargeUtil.getForexErrorAmount();
							}

							totalInsrAmt = GeneralChargeUtil.add(totalInsrAmt, convertedInsrAmt);
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception in calculating total valid insured amt : " + e.toString());
		}
		return totalInsrAmt;
	}

	/**
	 * Get applicable debt amount in CMS Security Currency.
	 * 
	 * @return Amount
	 */
	public Amount getApplicableDebtAmount() {
		if (debtor == null) {
			return null;
		}
		Amount applicableDebtAmt = null;
		try {
			CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
			applicableDebtAmt = ((OBDebtor) debtor).getCalculatedApplicableDebtAmount();
			applicableDebtAmt = GeneralChargeUtil.convertAmount(applicableDebtAmt, cmsSecurityCCYCode);
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating applicable debt amt : " + e.toString());
		}
		return applicableDebtAmt;
	}

	/**
	 * Get applicable debt amt less total net stock amt only if total net stock
	 * amt is negative. Otherwise, this returns the applicable debt amt.
	 * 
	 * @param applicableDebtAmt - Amount in cms security ccy
	 * @param totalNetStockAmt - Amount in cms security ccy
	 * @return Amount
	 */
	public Amount getApplicableDebtAmountLessNetStock(Amount applicableDebtAmt, Amount totalNetStockAmt) {
		if (applicableDebtAmt == null) {
			return null;
		}
		if (GeneralChargeUtil.isForexErrorAmount(applicableDebtAmt)
				|| GeneralChargeUtil.isForexErrorAmount(totalNetStockAmt)) {
			return GeneralChargeUtil.getForexErrorAmount();
		}
		if ((totalNetStockAmt == null) || (totalNetStockAmt.getAmount() >= 0)) {
			return applicableDebtAmt;
		}

		Amount applicableDebtAmtLessNetStock = null;
		try {
			// since net stock amt is negative, this translates to (applicable
			// amt - total net stock amt)
			applicableDebtAmtLessNetStock = applicableDebtAmt.add(totalNetStockAmt);
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating applicable debt amt less net stock : " + e.toString());
		}

		return applicableDebtAmtLessNetStock;
	}

	/**
	 * Get total recoverable amount (coverage from insr) for stocks in CMS
	 * Security Currency.
	 * 
	 * @return Amount
	 * @return Amount representing forex error amt if recoverable amt to be
	 *         added is a forex error
	 */
	public Amount getTotalRecoverableStockAmount() {
		if ((stockMap == null) || (stockMap.values() == null) || (stockMap.values().size() <= 0)) {
			return null;
		}

		Amount totalRecoverableAmt = null;
		try {
			CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
			int stockInsrMapSize = 0;
			if ((stockID_InsuranceID_Map != null) && (stockID_InsuranceID_Map.values() != null)) {
				stockInsrMapSize = stockID_InsuranceID_Map.values().size();
			}

			// to loop thru the list of stocks
			for (Iterator stockItr = stockMap.keySet().iterator(); stockItr.hasNext();) {
				String stockID = (String) stockItr.next();
				IStock stock = (IStock) stockMap.get(stockID);

				DefaultLogger
						.debug("OBGeneralCharge.getTotalRecoverableStockAmount", ">>>>>>> stock : " + stockID
								+ "   status : " + stock.getStatus() + "     recoverable amt : "
								+ stock.getRecoverableAmount());

				// return forex error amt if recoverable amt to be added is a
				// forex error
				if (GeneralChargeUtil.isForexErrorAmount(stock.getRecoverableAmount())) {
					return GeneralChargeUtil.getForexErrorAmount();
				}

				// skip if stock has been deleted
				if (ICMSConstant.STATE_DELETED.equals(stock.getStatus())) {
					continue;
				}

				totalRecoverableAmt = GeneralChargeUtil.add(totalRecoverableAmt, stock.getRecoverableAmount());
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating total stock recoverable amt : " + e.toString());
		}
		return totalRecoverableAmt;
	}

	/**
	 * Get total gross value for stock, debtor and FAO in CMS Security Currency.
	 * 
	 * @return Amount
	 */
	public Amount getTotalGrossValue() {
		return getTotalGrossValue(getStockGrossValue(), getFAOGrossValue(), getDebtorGrossValue());
	}

	/**
	 * Get total gross value for stock, debtor and FAO in CMS Security Currency.
	 * 
	 * @param stockGrossValue - Amount
	 * @param faoGrossValue - Amount
	 * @param debtorGrossValue - Amount
	 * @return Amount
	 * @return Amount representing forex error if any of the gross value param
	 *         is a forex error
	 */
	public static Amount getTotalGrossValue(Amount stockGrossValue, Amount faoGrossValue, Amount debtorGrossValue) {
		Amount totalGrossValue = null;
		try {
			if ((stockGrossValue == null) && (faoGrossValue == null) && (debtorGrossValue == null)) {
				return null;
			}

			// return forex error amt if any of the params for the formula is a
			// forex error
			if (GeneralChargeUtil.isForexErrorAmount(stockGrossValue)
					|| GeneralChargeUtil.isForexErrorAmount(faoGrossValue)
					|| GeneralChargeUtil.isForexErrorAmount(debtorGrossValue)) {
				return GeneralChargeUtil.getForexErrorAmount();
			}

			totalGrossValue = GeneralChargeUtil.add(totalGrossValue, stockGrossValue);
			totalGrossValue = GeneralChargeUtil.add(totalGrossValue, faoGrossValue);
			totalGrossValue = GeneralChargeUtil.add(totalGrossValue, debtorGrossValue);
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating total gross value : " + e.toString());
		}
		return totalGrossValue;
	}

	/**
	 * Get total net value for stock, debtor and FAO in CMS Security Currency.
	 * 
	 * @return Amount
	 */
	public Amount getTotalNetValue() {
		Amount stockNetAmt = getStockNetValue();
		return getTotalNetValue(getStockGrossValue(), getTotalStockCreditors(), stockNetAmt, getFAONetValue(),
				getDebtorNetValue(stockNetAmt));
	}

	/**
	 * Get total net value for stock, debtor and FAO in CMS Security Currency.
	 * 
	 * @param stockGrossValue - Amount
	 * @param stockCreditorsAmt - Amount
	 * @param stockNetValue - Amount
	 * @param faoNetValue - Amount
	 * @param debtorNetValue - Amount
	 * @return Amount
	 * @return Amount representing forex error if any of the net value param is
	 *         a forex error
	 */
	public Amount getTotalNetValue(Amount stockGrossValue, Amount stockCreditorsAmt, Amount stockNetValue,
			Amount faoNetValue, Amount debtorNetValue) {
		try {
			if ((stockNetValue == null) && (faoNetValue == null) && (debtorNetValue == null)) {
				return null;
			}
			// return forex error amt if any of the params for the formula is a
			// forex error
			if (GeneralChargeUtil.isForexErrorAmount(stockNetValue)
					|| GeneralChargeUtil.isForexErrorAmount(faoNetValue)
					|| GeneralChargeUtil.isForexErrorAmount(debtorNetValue)) {
				return GeneralChargeUtil.getForexErrorAmount();
			}

			Amount stockGrossLessCreditorsAmt = getStockGrossLessCreditorsAmt(stockGrossValue, stockCreditorsAmt);
			Amount totalNetValue = null;
			// if gross stock >= stock creditor
			// total net value = net stock + net fao + net debtor
			if ((stockGrossLessCreditorsAmt == null) || (stockGrossLessCreditorsAmt.getAmount() >= 0)) {
				totalNetValue = GeneralChargeUtil.add(totalNetValue, stockNetValue);
				totalNetValue = GeneralChargeUtil.add(totalNetValue, faoNetValue);
				totalNetValue = GeneralChargeUtil.add(totalNetValue, debtorNetValue);
				return totalNetValue;
			}

			// else if gross stock < stock creditor
			// if net currency asset > 0
			// total net value = net debtor + net fao
			// else
			// total net value = net fao

			if (isNegativeNetCurrentAsset(stockNetValue, debtorNetValue)) {
				return faoNetValue;
			}
			return (debtorNetValue == null) ? faoNetValue : GeneralChargeUtil.add(debtorNetValue, faoNetValue);

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception in calculating total net value : " + e.toString());
		}
	}

	/**
	 * Helper method to calculate the stock gross less creditor amt
	 * 
	 * @param stockGrossValue - Amount
	 * @param stockCreditorsAmt - Amount
	 * @return Amount
	 */
	private Amount getStockGrossLessCreditorsAmt(Amount stockGrossValue, Amount stockCreditorsAmt) {
		Amount stockGrossLessCreditorsAmt = null;
		try {
			if (stockGrossValue != null) {
				stockGrossLessCreditorsAmt = (stockCreditorsAmt == null) ? stockGrossValue : stockGrossValue
						.subtract(stockCreditorsAmt);
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating stock gross less credit amt : " + e.toString());
		}
		return stockGrossLessCreditorsAmt;
	}

	/**
	 * Helper to check if the net value of current asset is negative i.e. when
	 * the debtor net value is not enough to cover the negative stock net value
	 * 
	 * @param stockNetValue - Amount
	 * @param debtorNetValue - Amount
	 * @return boolean
	 */
	private static boolean isNegativeNetCurrentAsset(Amount stockNetValue, Amount debtorNetValue) {

		return ((stockNetValue == null) && (debtorNetValue == null))
				|| ((debtorNetValue != null) && (debtorNetValue.getAmount() < 0) && (stockNetValue != null) && (stockNetValue
						.getAmount() < 0));
	}

	/**
	 * Get gross amount for drawing power, taking in consideration the insurance
	 * coverage.
	 * 
	 * @return Amount
	 */
	public Amount getCalculatedDrawingPowerLessInsrGrossAmount() {
		Amount stockNetAmt = getStockNetValue();
		return getCalculatedDrawingPowerLessInsrGrossAmount(getStockGrossValue(), getTotalStockCreditors(),
				stockNetAmt, getDebtorNetValue(stockNetAmt), getTotalRecoverableStockAmount());
	}

	/**
	 * Get gross amount for drawing power, taking in consideration the insurance
	 * coverage.
	 * 
	 * @param stockGrossValue - Amount
	 * @param stockCreditorsAmt - Amount
	 * @param stockNetValue - Amount
	 * @param debtorNetValue - Amount
	 * @param stockRecoverableAmt - Amount
	 * @return Amount
	 */
	public Amount getCalculatedDrawingPowerLessInsrGrossAmount(Amount stockGrossValue, Amount stockCreditorsAmt,
			Amount stockNetValue, Amount debtorNetValue, Amount stockRecoverableAmt) {
		try {
			// return forex error amt if any of the params for the formula is a
			// forex error
			if (GeneralChargeUtil.isForexErrorAmount(stockGrossValue)
					|| GeneralChargeUtil.isForexErrorAmount(stockCreditorsAmt)
					|| GeneralChargeUtil.isForexErrorAmount(debtorNetValue)) {
				return GeneralChargeUtil.getForexErrorAmount();
			}

			Amount stockGrossLessCreditorsAmt = getStockGrossLessCreditorsAmt(stockGrossValue, stockCreditorsAmt);
			Amount dpLessInsrAmount = null;
			// if gross stock >= stock creditor
			// dp less insr coverage = total stock recoverable amt + net debtor
			if ((stockGrossLessCreditorsAmt == null) || (stockGrossLessCreditorsAmt.getAmount() >= 0)) {

				// return forex error amt if any of the params for the formula
				// is a forex error
				if (GeneralChargeUtil.isForexErrorAmount(stockRecoverableAmt)) {
					return GeneralChargeUtil.getForexErrorAmount();
				}

				dpLessInsrAmount = GeneralChargeUtil.add(dpLessInsrAmount, stockRecoverableAmt);
				dpLessInsrAmount = GeneralChargeUtil.add(dpLessInsrAmount, debtorNetValue);
				return dpLessInsrAmount;
			}

			// else if gross stock < stock creditor
			// if net currency asset > 0
			// total net value = net debtor
			// else
			// total net value = 0
			if (isNegativeNetCurrentAsset(stockNetValue, debtorNetValue)) {
				return new Amount(0, getCurrencyCode());
			}
			return debtorNetValue;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating drawing power less insr coverage gross amt : "
					+ e.toString());
		}
	}

	/**
	 * Get gross amount for drawing power without taking into consideration the
	 * insurance coverage
	 * 
	 * @return Amount
	 */
	public Amount getCalculatedDrawingPowerGrossAmount() {
		Amount stockNetAmt = getStockNetValue();
		return getCalculatedDrawingPowerGrossAmount(getStockGrossValue(), getTotalStockCreditors(), stockNetAmt,
				getDebtorNetValue(stockNetAmt));
	}

	/**
	 * Get gross amount for drawing power without taking into consideration the
	 * insurance coverage
	 * 
	 * @param stockGrossValue - Amount
	 * @param stockCreditorsAmt - Amount
	 * @param stockNetValue - Amount
	 * @param debtorNetValue - Amount
	 * @return Amount
	 */
	public Amount getCalculatedDrawingPowerGrossAmount(Amount stockGrossValue, Amount stockCreditorsAmt,
			Amount stockNetValue, Amount debtorNetValue) {
		try {
			// return forex error amt if any of the params for the formula is a
			// forex error
			if (GeneralChargeUtil.isForexErrorAmount(stockGrossValue)
					|| GeneralChargeUtil.isForexErrorAmount(stockCreditorsAmt)
					|| GeneralChargeUtil.isForexErrorAmount(debtorNetValue)) {
				return GeneralChargeUtil.getForexErrorAmount();
			}

			Amount stockGrossLessCreditorsAmt = getStockGrossLessCreditorsAmt(stockGrossValue, stockCreditorsAmt);
			Amount dpAmount = null;
			// if gross stock >= stock creditor
			// dp less insr coverage = total stock recoverable amt + net debtor
			if ((stockGrossLessCreditorsAmt == null) || (stockGrossLessCreditorsAmt.getAmount() >= 0)) {

				// return forex error amt if any of the params for the formula
				// is a forex error
				if (GeneralChargeUtil.isForexErrorAmount(stockNetValue)) {
					return GeneralChargeUtil.getForexErrorAmount();
				}

				dpAmount = GeneralChargeUtil.add(dpAmount, stockNetValue);
				dpAmount = GeneralChargeUtil.add(dpAmount, debtorNetValue);
				return dpAmount;
			}

			// else if gross stock < stock creditor
			// if net currency asset > 0
			// total net value = net debtor
			// else
			// total net value = 0
			if (isNegativeNetCurrentAsset(stockNetValue, debtorNetValue)) {
				return new Amount(0, getCurrencyCode());
			}
			return debtorNetValue;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating drawing power gross amt : " + e.toString());
		}
	}

	/**
	 * Get bank's participating share amount in CMS Security Currency. Formula =
	 * TotalNetvalue * BankShare * 0.01
	 * 
	 * @param netvalue - Amount
	 * @return Amount
	 * @return Amount representing forex error if netvalue is a forex error
	 */
	public Amount getBankShareAmount(Amount netvalue) {
		double bankShare = getBankShare();
		if ((netvalue == null) || (bankShare < 0)) {
			return null;
		}
		CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
		if (bankShare == 0) {
			return new Amount(0, cmsSecurityCCYCode);
		}
		if (GeneralChargeUtil.isForexErrorAmount(netvalue)) {
			return GeneralChargeUtil.getForexErrorAmount();
		}
		Amount bankShareAmt = null;
		try {
			bankShareAmt = (bankShare == 100) ? netvalue : netvalue.multiply(new BigDecimal(bankShare
					* ICMSConstant.DOUBLE_PERCENT));
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating participating bank share : " + e.toString());
		}
		return bankShareAmt;
	}

	/**
	 * Get total net FSV (after margin) for this asset in CMS Security Currency.
	 * Formula = BankShareAmount * Margin
	 * 
	 * @param bankShareAmt - Amount
	 * @return Amount
	 * @return Amount representing forex error if bankShareAmt is a forex error
	 */
	public Amount getDrawingPowerNetAmount(Amount bankShareAmt) {
		double securityMargin = getMargin();
		if ((bankShareAmt == null) || (securityMargin < 0)) {
			return null;
		}
		CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
		if (securityMargin == 0) {
			return new Amount(0, cmsSecurityCCYCode);
		}
		if (GeneralChargeUtil.isForexErrorAmount(bankShareAmt)) {
			return GeneralChargeUtil.getForexErrorAmount();
		}
		Amount totalNetFSV = null;
		try {
			totalNetFSV = (securityMargin == 1) ? bankShareAmt : bankShareAmt.multiply(new BigDecimal(securityMargin));
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating total net FSV : " + e.toString());
		}
		return totalNetFSV;
	}

	/**
	 * Get Total Maximum Applied Approved Limit in CMS Security Currency.
	 * 
	 * @return Amount
	 * @return Amount representing forex error if any of the max approved limit
	 *         being added is a forex error amt
	 */
	public Amount getTotalAppliedLimitAmount() {
		ICollateralLimitMap[] colLimitMapList = getCurrentCollateralLimits();
		if ((colLimitMapList == null) || (colLimitMapList.length == 0)) {
			return null;
		}
		Amount totalAppliedLimitAmt = null;
		try {
			CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
			for (int i = 0; i < colLimitMapList.length; i++) {
				if (ICMSConstant.HOST_STATUS_DELETE.equals(colLimitMapList[i].getSCIStatus())) {
					continue;
				}

				if (colLimitMapList[i].getIsAppliedLimitAmountIncluded()) {
					Amount appliedLimitAmt = GeneralChargeUtil.convertAmount(
							colLimitMapList[i].getAppliedLimitAmount(), cmsSecurityCCYCode);

					// return forex error amt if max approved limit amt to be
					// added is a forex error
					if (GeneralChargeUtil.isForexErrorAmount(appliedLimitAmt)) {
						return GeneralChargeUtil.getForexErrorAmount();
					}

					totalAppliedLimitAmt = GeneralChargeUtil.add(totalAppliedLimitAmt, appliedLimitAmt);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating total applied limit amount : " + e.toString());
		}
		return totalAppliedLimitAmt;
	}

	/**
	 * Get Total Existing Released Limit in CMS Security Currency.
	 * 
	 * @return Amount
	 * @return Amount representing forex error if any of the gross amt being
	 *         added is a forex error amt
	 */
	public Amount getTotalReleasedLimitAmount() {
		ICollateralLimitMap[] colLimitMapList = getCurrentCollateralLimits();
		if ((colLimitMapList == null) || (colLimitMapList.length == 0)) {
			return null;
		}
		Amount totalReleasedLimitAmt = null;
		try {
			CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
			for (int i = 0; i < colLimitMapList.length; i++) {
				if (ICMSConstant.HOST_STATUS_DELETE.equals(colLimitMapList[i].getSCIStatus())) {
					continue;
				}

				if (colLimitMapList[i].getIsReleasedLimitAmountIncluded()) {
					Amount releasedLimitAmt = GeneralChargeUtil.convertAmount(colLimitMapList[i]
							.getReleasedLimitAmount(), cmsSecurityCCYCode);

					// return forex error amt if release limit amt to be added
					// is a forex error
					if (GeneralChargeUtil.isForexErrorAmount(releasedLimitAmt)) {
						return GeneralChargeUtil.getForexErrorAmount();
					}

					totalReleasedLimitAmt = GeneralChargeUtil.add(totalReleasedLimitAmt, releasedLimitAmt);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating total released limit amount : " + e.toString());
		}
		return totalReleasedLimitAmt;
	}

	/**
	 * Get Amount to be Released (Drawing Power) in CMS Security Currency.
	 * 
	 * @param dpNetAmount in CMS Security Currency
	 * @param totalAppliedLimitAmt in CMS Security Currency
	 * @return Amount
	 */
	public Amount getDrawingPower(Amount dpNetAmount, Amount totalAppliedLimitAmt) {
		if (dpNetAmount == null) {
			return null;
		}
		if (totalAppliedLimitAmt == null) {
			return dpNetAmount;
		}
		BigDecimal min = CommonUtil.min(dpNetAmount.getAmountAsBigDecimal(), totalAppliedLimitAmt
				.getAmountAsBigDecimal());
		return (min == null) ? null : new Amount(min, dpNetAmount.getCurrencyCodeAsObject());
	}

	/**
	 * Get total gross value for subtype in CMS Security Currency.
	 * 
	 * @param subTypeMap - Map (subTypeID, subType)
	 * @param isDeletedIncluded - boolean denoting if deleted subtypes are to be
	 *        included
	 * @return Amount
	 * @return Amount representing forex error if any of the gross amt being
	 *         added is a forex error amt
	 */
	private Amount getGrossValue(Map subTypeMap, boolean isDeletedIncluded) {
		if (subTypeMap == null) {
			return null;
		}

		String subTypeID = null;
		Amount totalGrossValue = null;
		try {
			CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
			for (Iterator iterator = subTypeMap.values().iterator(); iterator.hasNext();) {
				OBGeneralChargeSubType subType = (OBGeneralChargeSubType) iterator.next();
				subTypeID = subType.getID();
				// filter off deleted subtypes if isDeletedIncluded is true
				if (isIncluded(subType, isDeletedIncluded)) {
					Amount convertedSubTypeGrossValue = GeneralChargeUtil.convertAmount(subType.getGrossValue(),
							cmsSecurityCCYCode);

					// return forex error amt if gross amt to be added is a
					// forex error
					if (GeneralChargeUtil.isForexErrorAmount(convertedSubTypeGrossValue)) {
						return GeneralChargeUtil.getForexErrorAmount();
					}

					totalGrossValue = GeneralChargeUtil.add(totalGrossValue, convertedSubTypeGrossValue);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating total gross value for [" + subTypeID + "] : "
					+ e.toString());
		}
		return totalGrossValue;
	}

	/**
	 * Get total net value for subtype in CMS Security Currency.
	 * 
	 * @param subTypeMap - Map (subTypeID, subType)
	 * @param isDeletedIncluded - boolean denoting if deleted subtypes are to be
	 *        included
	 * @return Amount
	 * @return Amount representing forex error if any of the net amt being added
	 *         is a forex error amt
	 */
	private Amount getNetValue(Map subTypeMap, boolean isDeletedIncluded) {
		if (subTypeMap == null) {
			return null;
		}

		String subTypeID = null;
		Amount totalNetValue = null;
		try {
			CurrencyCode cmsSecurityCCYCode = new CurrencyCode(getCurrencyCode());
			for (Iterator iterator = subTypeMap.values().iterator(); iterator.hasNext();) {
				OBGeneralChargeSubType subType = (OBGeneralChargeSubType) iterator.next();
				subTypeID = subType.getID();
				// filter off deleted subtypes if isDeletedIncluded is true
				if (isIncluded(subType, isDeletedIncluded)) {
					Amount convertedSubTypeNetValue = GeneralChargeUtil.convertAmount(subType.getNetValue(),
							cmsSecurityCCYCode);

					// return forex error amt if net amt to be added is a forex
					// error
					if (GeneralChargeUtil.isForexErrorAmount(convertedSubTypeNetValue)) {
						return GeneralChargeUtil.getForexErrorAmount();
					}

					totalNetValue = GeneralChargeUtil.add(totalNetValue, convertedSubTypeNetValue);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating total net value for [" + subTypeID + "] : "
					+ e.toString());
		}
		return totalNetValue;
	}

	/**
	 * Helper method to check if a subtype is to be included.
	 * 
	 * @param subType - OBGeneralChargeSubType
	 * @param isDeletedIncluded - boolean
	 */
	private boolean isIncluded(OBGeneralChargeSubType subType, boolean isDeletedIncluded) {
		return (isDeletedIncluded || !ICMSConstant.STATE_DELETED.equals(subType.getStatus()));
	}
	
	

	public IGeneralChargeDetails[] getGeneralChargeDetails() {
		return generalChargeDetails;
	}

	public void setGeneralChargeDetails(IGeneralChargeDetails[] generalChargeDetails) {
		this.generalChargeDetails = generalChargeDetails;
	}

	public boolean addGeneralChargeDetails(IGeneralChargeDetails chargeDetails) {
		if (chargeDetails == null)
			return false;
		
		IGeneralChargeDetails[] chargeDetailsArr = this.getGeneralChargeDetails();
		if (chargeDetailsArr == null)
			chargeDetailsArr = new IGeneralChargeDetails[0];
		
		DueDateAndStockHelper.calculateStockDetailsLoanable(chargeDetailsArr, chargeDetails);
		
		List<IGeneralChargeDetails> chargeDetailsList = new ArrayList<IGeneralChargeDetails>(
				Arrays.asList(chargeDetailsArr));
		chargeDetailsList.add(chargeDetails);
		chargeDetailsArr = chargeDetailsList.toArray(new IGeneralChargeDetails[chargeDetailsList.size()]);
		this.setGeneralChargeDetails(chargeDetailsArr);
		return true;
	}
	
	public boolean replaceGeneralChargeDetails(int index, IGeneralChargeDetails chargeDetails) throws Exception {
		if (chargeDetails == null || index > -1)
			return false;
		
		IGeneralChargeDetails[] chargeDetailsArr = this.getGeneralChargeDetails();
		if (chargeDetailsArr == null || chargeDetailsArr.length >= index)
			throw new Exception("General Charge Detail not found Exception for id: "
					+ chargeDetails.getGeneralChargeDetailsID() + ", index: " + index);
		
		if(chargeDetails.getGeneralChargeDetailsID() == chargeDetailsArr[index].getGeneralChargeDetailsID()) {
			chargeDetailsArr[index] = chargeDetails;
			this.setGeneralChargeDetails(chargeDetailsArr);
		}
		return true;
	}
	
	public Map<String, String> getDueDateAndStockStatements() {
		return dueDateAndStockStatements;
	}

	public void setDueDateAndStockStatements(Map<String, String> dueDateAndStockStatements) {
		this.dueDateAndStockStatements = dueDateAndStockStatements;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
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
		else if (!(obj instanceof OBGeneralCharge)) {
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