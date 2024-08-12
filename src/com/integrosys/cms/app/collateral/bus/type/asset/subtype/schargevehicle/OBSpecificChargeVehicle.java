/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/schargevehicle/OBSpecificChargeVehicle.java,v 1.6 2006/01/18 05:34:29 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.ITradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBChargeCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 24, 2007 Time: 10:59:41 AM
 * To change this template use File | Settings | File Templates.
 */

public class OBSpecificChargeVehicle extends OBChargeCommon implements ISpecificChargeVehicle {
	private String coverageType;

	private String chassisNumber;

	private String iindicator;

	private String collateralfee;

	private Amount dptradein;

	private Amount dpcash;

	private Amount fcharges;

	private Amount plist;

	private String heavyvehicle;

	private String engineNo;

	private Amount coe;

	private String transType;

	private String energySource;

	private String horsePower;

	private String vehColor;

	private String pbtIndicator;

	private Long pbtPbrPeriodDays;

	private Amount amtCollectedFromSales;

	private Amount roadTaxAmt;

	private String roadTaxAmtType;

	private Date roadTaxExpiryDate;

	private boolean isAllowPassive;

	private String logBookNumber;

	private String engineCapacity;

	private String ownershipClaimNumber;

	private String eHakMilikNumber;

	private String yardOptions;

	private String dealerName;

	private String rlSerialNumber;

	private Amount tradeinValue;

	private ITradeInInfo[] tradeInInfo;

    private Date invoiceDate;
    
    private String invoiceNo;
    
    private String ramId;
    
    private Date startDate;
    
    
    private BigDecimal assetCollateralBookingVal;
    
    private String descriptionAssets;
   

    
    
    
	public BigDecimal getAssetCollateralBookingVal() {
		return assetCollateralBookingVal;
	}

	public void setAssetCollateralBookingVal(BigDecimal assetCollateralBookingVal) {
		this.assetCollateralBookingVal = assetCollateralBookingVal;
	}

	public String getDescriptionAssets() {
		return descriptionAssets;
	}

	public void setDescriptionAssets(String descriptionAssets) {
		this.descriptionAssets = descriptionAssets;
	}

	public String getRamId() {
		return ramId;
	}

	public void setRamId(String ramId) {
		this.ramId = ramId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getRlSerialNumber() {
		return rlSerialNumber;
	}

	public void setRlSerialNumber(String rlSerialNumber) {
		this.rlSerialNumber = rlSerialNumber;
	}

	public String getLogBookNumber() {
		return logBookNumber;
	}

	public void setLogBookNumber(String logBookNumber) {
		this.logBookNumber = logBookNumber;
	}

	public String getEngineCapacity() {
		return engineCapacity;
	}

	public void setEngineCapacity(String engineCapacity) {
		this.engineCapacity = engineCapacity;
	}

	public String getOwnershipClaimNumber() {
		return ownershipClaimNumber;
	}

	public void setOwnershipClaimNumber(String ownershipClaimNumber) {
		this.ownershipClaimNumber = ownershipClaimNumber;
	}

	public String getEHakMilikNumber() {
		return eHakMilikNumber;
	}

	public void setEHakMilikNumber(String hakMilikNumber) {
		eHakMilikNumber = hakMilikNumber;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	/**
	 * Default Constructor.
	 */
	public OBSpecificChargeVehicle() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISpecificChargeVehicle
	 */
	public OBSpecificChargeVehicle(ISpecificChargeVehicle obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get coverage type.
	 * 
	 * @return String
	 */
	public String getCoverageType() {
		return coverageType;
	}

	/**
	 * Set coverage type.
	 * 
	 * @param coverageType of type String
	 */
	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public String getIindicator() {
		return iindicator;
	}

	public void setIindicator(String iindicator) {
		this.iindicator = iindicator;
	}

	public String getCollateralfee() {
		return collateralfee;
	}

	public void setCollateralfee(String collateralfee) {
		this.collateralfee = collateralfee;
	}

	public Amount getDptradein() {
		return dptradein;
	}

	public void setDptradein(Amount dptradein) {
		this.dptradein = dptradein;
	}

	public Amount getDpcash() {
		return dpcash;
	}

	public void setDpcash(Amount dpcash) {
		this.dpcash = dpcash;
	}

	public Amount getFcharges() {
		return fcharges;
	}

	public void setFcharges(Amount fcharges) {
		this.fcharges = fcharges;
	}

	public Amount getPlist() {
		return plist;
	}

	public void setPlist(Amount plist) {
		this.plist = plist;
	}

	public String getHeavyvehicle() {
		return heavyvehicle;
	}

	public void setHeavyvehicle(String heavyvehicle) {
		this.heavyvehicle = heavyvehicle;
	}

	/* Added by Saritha for Asset based Vehicles */

	public Amount getCoe() {
		return coe;
	}

	public void setCoe(Amount coe) {
		this.coe = coe;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getEnergySource() {
		return energySource;
	}

	public void setEnergySource(String energySource) {
		this.energySource = energySource;
	}

	public String getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(String horsePower) {
		this.horsePower = horsePower;
	}

	public String getVehColor() {
		return vehColor;
	}

	public void setVehColor(String vehColor) {
		this.vehColor = vehColor;
	}

	public String getPBTIndicator() {
		return pbtIndicator;
	}

	public void setPBTIndicator(String pbtIndicator) {
		this.pbtIndicator = pbtIndicator;
	}

	public Amount getAmtCollectedFromSales() {
		return amtCollectedFromSales;
	}

	public void setAmtCollectedFromSales(Amount amtCollectedFromSales) {
		this.amtCollectedFromSales = amtCollectedFromSales;
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
		else if (!(obj instanceof OBSpecificChargeVehicle)) {
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

	public Amount getRoadTaxAmt() {
		return roadTaxAmt;
	}

	public void setRoadTaxAmt(Amount roadTaxAmt) {
		this.roadTaxAmt = roadTaxAmt;
	}

	public Date getRoadTaxExpiryDate() {
		return roadTaxExpiryDate;
	}

	public void setRoadTaxExpiryDate(Date roadTaxExpiryDate) {
		this.roadTaxExpiryDate = roadTaxExpiryDate;
	}

	public boolean getIsAllowPassive() {
		return isAllowPassive;
	}

	public void setIsAllowPassive(boolean isAllowPassive) {
		this.isAllowPassive = isAllowPassive;
	}

	public String getYardOptions() {
		return yardOptions;
	}

	public void setYardOptions(String yardOptions) {
		this.yardOptions = yardOptions;
	}

	public String getRoadTaxAmtType() {
		return roadTaxAmtType;
	}

	public void setRoadTaxAmtType(String roadTaxAmtType) {
		this.roadTaxAmtType = roadTaxAmtType;
	}

	public Amount getTradeinValue() {
		return tradeinValue;
	}

	public void setTradeinValue(Amount tradeinValue) {
		this.tradeinValue = tradeinValue;
	}

	public ITradeInInfo[] getTradeInInfo() {
		return tradeInInfo;
	}

	public void setTradeInInfo(ITradeInInfo[] tradeInInfo) {
		this.tradeInInfo = tradeInInfo;
	}

	public Long getPbtPbrPeriodDays() {
		return this.pbtPbrPeriodDays;
	}

	public void setPbtPbrPeriodDays(Long pbtPbrPeriodDays) {
		this.pbtPbrPeriodDays = pbtPbrPeriodDays;
	}

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

}