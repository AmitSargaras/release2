/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/EBChargeCommonBean.java,v 1.25 2005/08/12 04:39:17 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for asset of type charge.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.25 $
 * @since $Date: 2005/08/12 04:39:17 $ Tag: $Name: $
 */
public abstract class EBChargeCommonBean extends EBCollateralDetailBean implements IChargeCommon {

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
	 * Get registration fee.
	 * 
	 * @return Amount
	 */
	public Amount getRegistrationFee() {
		if (getEBRegistrationFee() != null) {
			return new Amount(getEBRegistrationFee(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	/**
	 * Set registration fee.
	 * 
	 * @param registrationFee is of type Amount
	 */
	public void setRegistrationFee(Amount registrationFee) {
		setEBRegistrationFee((registrationFee == null) ? null : registrationFee.getAmountAsBigDecimal());
	}

	/**
	 * Get purchase price.
	 * 
	 * @return Amount
	 */
	public Amount getPurchasePrice() {
		if (getEBPurchasePrice() != null) {
			return new Amount(getEBPurchasePrice(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	/**
	 * Set purchase price.
	 * 
	 * @param purchasePrice is of type Amount
	 */
	public void setPurchasePrice(Amount purchasePrice) {
		setEBPurchasePrice((purchasePrice == null) ? null : purchasePrice.getAmountAsBigDecimal());
	}

	/**
	 * Get nominal Value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue() {
		if (getEBNominalValue() != null) {
			return new Amount(getEBNominalValue(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue) {
		setEBNominalValue((nominalValue == null) ? null : nominalValue.getAmountAsBigDecimal());
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
	 * Get Insurance Info currency Code - not used in charge common
	 * 
	 * @return null
	 */
	public String getCurrentyCode() {
		return null;
	}

	/*
	 * Set insurance Info Currency Code - not used in charge common
	 * 
	 * @param currencyCode of type String
	 */
	public void setCurrencyCode(String currencyCode) {
	}

	/**
	 * Get Insurance Info ID - not used in charge common
	 * 
	 * @return null
	 */
	public long getInsuranceInfoID() {
		return ICMSConstant.LONG_MIN_VALUE;
	}

	/*
	 * Set insurance Info ID - not used in charge common
	 * 
	 * @param insuranceInfoID of type long
	 */
	public void setInsuranceInfoID(long insuranceInfoID) {
	}

	/**
	 * Get Insurance Info ref ID - not used in charge common
	 * 
	 * @return null
	 */
	public String getRefID() {
		return null;
	}

	/*
	 * Set insurance Info ref ID - not used in charge common
	 * 
	 * @param refID of type String
	 */
	public void setRefID(String refID) {
	}

	/**
	 * Get Insurance Info status - not used in charge common
	 * 
	 * @return null
	 */
	public String getStatus() {
		return null;
	}

	/*
	 * Set insurance Info status - not used in charge common
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
	}

	/**
	 * Get Insurance Info category - not used in charge common
	 * 
	 * @return null
	 */
	public String getCategory() {
		return null;
	}

	public Amount getSalesProceed() {
		if (getEBSalesProceed() != null) {
			return new Amount(getEBSalesProceed(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setSalesProceed(Amount salesProceed) {
		setEBSalesProceed((salesProceed == null) ? null : salesProceed.getAmountAsBigDecimal());
	}

	public Amount getDepreciateRate() {
		if (getEBDepreciateRate() != null) {
			return new Amount(getEBDepreciateRate(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setDepreciateRate(Amount depreciateRate) {
		setEBDepreciateRate((depreciateRate == null) ? null : depreciateRate.getAmountAsBigDecimal());
	}

	/*
	 * Set insurance Info category - not used in charge common
	 * 
	 * @param category of type String
	 */
	public void setCategory(String category) {
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract BigDecimal getEBRegistrationFee();

	public abstract void setEBRegistrationFee(BigDecimal eBRegistrationFee);

	public abstract BigDecimal getEBPurchasePrice();

	public abstract void setEBPurchasePrice(BigDecimal eBPurchasePrice);

	public abstract BigDecimal getEBNominalValue();

	public abstract void setEBNominalValue(BigDecimal nominalValue);

	public abstract String getEBIsPhysicalInspection();

	public abstract void setEBIsPhysicalInspection(String eBIsPhysicalInspection);

	public abstract BigDecimal getEBSalesProceed();

	public abstract void setEBSalesProceed(BigDecimal eBSalesProceed);

	public abstract BigDecimal getEBDepreciateRate();

	public abstract void setEBDepreciateRate(BigDecimal eBDepreciateRate);

	// for amount
	public abstract BigDecimal getEBAssetValue();

	public abstract void setEBAssetValue(BigDecimal eBAssetValue);

	public Amount getAssetValue() {
		if (getEBAssetValue() != null) {
			return new Amount(getEBAssetValue(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setAssetValue(Amount assetValue) {
		setEBAssetValue((assetValue == null) ? null : assetValue.getAmountAsBigDecimal());
	}

	public abstract BigDecimal getEBScrapValue();

	public abstract void setEBScrapValue(BigDecimal eBScrapValue);

	public Amount getScrapValue() {
		if (getEBScrapValue() != null) {
			return new Amount(getEBScrapValue(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setScrapValue(Amount scrapValue) {
		setEBScrapValue((scrapValue == null) ? null : scrapValue.getAmountAsBigDecimal());
	}

	public abstract int getPhysicalInspectionFreq();

	public abstract void setPhysicalInspectionFreq(int physicalInspectionFreq);

	public abstract String getPhysicalInspectionFreqUnit();

	public abstract void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);

	public abstract String getRemarks();

	public abstract void setRemarks(String remarks);

	public abstract Date getLastPhysicalInspectDate();

	public abstract void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);

	public abstract Date getNextPhysicalInspectDate();

	public abstract void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);

	public abstract String getEnvRiskyStatus();

	public abstract void setEnvRiskyStatus(String envRiskyStatus);

	public abstract Date getEnvRiskyDate();

	public abstract void setEnvRiskyDate(Date envRiskyDate);

	public abstract String getEnvRiskyRemarks();

	public abstract void setEnvRiskyRemarks(String envRiskyRemarks);

	public abstract String getAssetType();

	public abstract void setAssetType(String assetType);

	public abstract String getAssetTypeHostRef();

	public abstract void setAssetTypeHostRef(String assetTypeHostRef);

	public abstract Date getPurchaseDate();

	public abstract void setPurchaseDate(Date purchaseDate);

	public abstract Date getRegistrationDate();

	public abstract void setRegistrationDate(Date registrationDate);

	public abstract int getYearOfManufacture();

	public abstract void setYearOfManufacture(int yearOfManufacture);

	public abstract String getModelNo();

	public abstract void setModelNo(String modelNo);

	public abstract String getInsurers();

	public abstract void setInsurers(String insurers);

	public abstract int getResidualAssetLife();

	public abstract void setResidualAssetLife(int residualAssetLife);

	public abstract String getResidualAssetLifeUOM();

	public abstract void setResidualAssetLifeUOM(String residualAssetLifeUOM);

	public abstract int getDocPerfectAge();

	public abstract void setDocPerfectAge(int docPerfectAge);

	public abstract String getRegistrationNo();

	public abstract void setRegistrationNo(String registrationNo);

	public abstract String getPrevOwnerName();

	public abstract void setPrevOwnerName(String prevOwnerName);

	public abstract String getPrevFinancierName();

	public abstract void setPrevFinancierName(String prevFinancierName);

	public abstract Date getRepossessionDate();

	public abstract void setRepossessionDate(Date repossessionDate);

	public abstract int getRepossessionAge();

	public abstract void setRepossessionAge(int repossessionAge);

	public abstract String getYard();

	public abstract void setYard(String yard);

	public abstract String getRegistrationCardNo();

	public abstract void setRegistrationCardNo(String registrationCardNo);

	public abstract String getBrand();

	public abstract void setBrand(String brand);

	public abstract String getGoodStatus();

	public abstract void setGoodStatus(String goodStatus);

	public abstract Date getChattelSoldDate();

	public abstract String getPubTransport();

	public abstract String getRlSerialNumber();

	public abstract void setChattelSoldDate(Date chattelSoldDate);

	public abstract void setPubTransport(String pubTransport);

	public abstract void setRlSerialNumber(String rlSerialNumber);

	public abstract String getInspectionStatusCategoryCode();

	public abstract void setInspectionStatusCategoryCode(String inspectionStatusCategoryCode);

	public abstract String getInspectionStatusEntryCode();

	public abstract void setInspectionStatusEntryCode(String inspectionStatusEntryCode);

}