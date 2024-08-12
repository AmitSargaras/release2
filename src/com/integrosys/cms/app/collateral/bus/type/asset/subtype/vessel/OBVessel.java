/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.vessel;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBChargeCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Asset of type Vessel.
 * 
 * @author $Author: Naveen $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/01/18 05:34:29 $ Tag: $Name: $
 */

public class OBVessel extends OBChargeCommon implements IVessel {

	private String vesselName = "";

	private Amount tradeInDeposit;

	private Amount tradeInValue;

	private String regCountry = "";

	private String vesselState = "";

	private Date vesselExptOccupDate;

	private String vesselExptOccup = "";

	private String vesselOccupType = "";

	private int vesselBuildYear = 0;

	private String vesselPurchaseCurrency = "";

	private String vesselBuilder = "";

	private String vesselMainReg = "";

	private String vesselLength = "";

	private String vesselWidth = "";

	private String vesselDepth = "";

	private String vesselDeckLoading = "";

	private String vesselDeckWeight = "";

	private String vesselSideBoard = "";

	private String vesselBOW = "";

	private String vesselDECK = "";

	private String vesselDeckThickness = "";

	private String vesselBottom = "";

	private String vesselWinchDrive = "";

	private String vesselBHP = "";

	private String vesselSpeed = "";

	private String vesselAnchor = "";

	private String vesselAnchorDrive = "";

	private String vesselClassSociety = "";

	private String vesselConstructCountry = "";

	private String vesselConstructPlace = "";

	private String vesselUse = "";

	private boolean vesselCharterContract;

	private String vesselChartererName = "";

	private int vesselCharterPeriod;

	private String vesselCharterPeriodUnit = "";

	private Double vesselCharterAmt = new Double(0.0);

	private String vesselCharterCurrency = "";

	private String vesselCharterRateUnit = "";

	private String vesselCharterRateUnitOTH = "";

	private String vesselCharterRemarks = "";

	/**
	 * Default Constructor.
	 */
	public OBVessel() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_VESSEL));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IVessel
	 */
	public OBVessel(IVessel obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public Amount getTradeInDeposit() {
		return tradeInDeposit;
	}

	public void setTradeInDeposit(Amount tradeInDeposit) {
		this.tradeInDeposit = tradeInDeposit;
	}

	public Amount getTradeInValue() {
		return tradeInValue;
	}

	public void setTradeInValue(Amount tradeInValue) {
		this.tradeInValue = tradeInValue;
	}

	public String getRegCountry() {
		return regCountry;
	}

	public void setRegCountry(String regCountry) {
		this.regCountry = regCountry;
	}

	public String getVesselState() {
		return vesselState;
	}

	public void setVesselState(String vesselState) {
		this.vesselState = vesselState;
	}

	public Date getVesselExptOccupDate() {
		return vesselExptOccupDate;
	}

	public void setVesselExptOccupDate(Date vesselExptOccupDate) {
		this.vesselExptOccupDate = vesselExptOccupDate;
	}

	public String getVesselExptOccup() {
		return vesselExptOccup;
	}

	public void setVesselExptOccup(String vesselExptOccup) {
		this.vesselExptOccup = vesselExptOccup;
	}

	public String getVesselOccupType() {
		return vesselOccupType;
	}

	public void setVesselOccupType(String vesselOccupType) {
		this.vesselOccupType = vesselOccupType;
	}

	public int getVesselBuildYear() {
		return vesselBuildYear;
	}

	public void setVesselBuildYear(int vesselBuildYear) {
		this.vesselBuildYear = vesselBuildYear;
	}

	public String getVesselPurchaseCurrency() {
		return vesselPurchaseCurrency;
	}

	public void setVesselPurchaseCurrency(String vesselPurchaseCurrency) {
		this.vesselPurchaseCurrency = vesselPurchaseCurrency;
	}

	public String getVesselBuilder() {
		return vesselBuilder;
	}

	public void setVesselBuilder(String vesselBuilder) {
		this.vesselBuilder = vesselBuilder;
	}

	public String getVesselMainReg() {
		return vesselMainReg;
	}

	public void setVesselMainReg(String vesselMainReg) {
		this.vesselMainReg = vesselMainReg;
	}

	public String getVesselLength() {
		return vesselLength;
	}

	public void setVesselLength(String vesselLength) {
		this.vesselLength = vesselLength;
	}

	public String getVesselWidth() {
		return vesselWidth;
	}

	public void setVesselWidth(String vesselWidth) {
		this.vesselWidth = vesselWidth;
	}

	public String getVesselDepth() {
		return vesselDepth;
	}

	public void setVesselDepth(String vesselDepth) {
		this.vesselDepth = vesselDepth;
	}

	public String getVesselDeckLoading() {
		return vesselDeckLoading;
	}

	public void setVesselDeckLoading(String vesselDeckLoading) {
		this.vesselDeckLoading = vesselDeckLoading;
	}

	public String getVesselDeckWeight() {
		return vesselDeckWeight;
	}

	public void setVesselDeckWeight(String vesselDeckWeight) {
		this.vesselDeckWeight = vesselDeckWeight;
	}

	public String getVesselSideBoard() {
		return vesselSideBoard;
	}

	public void setVesselSideBoard(String vesselSideBoard) {
		this.vesselSideBoard = vesselSideBoard;
	}

	public String getVesselBOW() {
		return vesselBOW;
	}

	public void setVesselBOW(String vesselBOW) {
		this.vesselBOW = vesselBOW;
	}

	public String getVesselDECK() {
		return vesselDECK;
	}

	public void setVesselDECK(String vesselDECK) {
		this.vesselDECK = vesselDECK;
	}

	public String getVesselDeckThickness() {
		return vesselDeckThickness;
	}

	public void setVesselDeckThickness(String vesselDeckThickness) {
		this.vesselDeckThickness = vesselDeckThickness;
	}

	public String getVesselBottom() {
		return vesselBottom;
	}

	public void setVesselBottom(String vesselBottom) {
		this.vesselBottom = vesselBottom;
	}

	public String getVesselWinchDrive() {
		return vesselWinchDrive;
	}

	public void setVesselWinchDrive(String vesselWinchDrive) {
		this.vesselWinchDrive = vesselWinchDrive;
	}

	public String getVesselBHP() {
		return vesselBHP;
	}

	public void setVesselBHP(String vesselBHP) {
		this.vesselBHP = vesselBHP;
	}

	public String getVesselSpeed() {
		return vesselSpeed;
	}

	public void setVesselSpeed(String vesselSpeed) {
		this.vesselSpeed = vesselSpeed;
	}

	public String getVesselAnchor() {
		return vesselAnchor;
	}

	public void setVesselAnchor(String vesselAnchor) {
		this.vesselAnchor = vesselAnchor;
	}

	public String getVesselAnchorDrive() {
		return vesselAnchorDrive;
	}

	public void setVesselAnchorDrive(String vesselAnchorDrive) {
		this.vesselAnchorDrive = vesselAnchorDrive;
	}

	public String getVesselClassSociety() {
		return vesselClassSociety;
	}

	public void setVesselClassSociety(String vesselClassSociety) {
		this.vesselClassSociety = vesselClassSociety;
	}

	public String getVesselConstructCountry() {
		return vesselConstructCountry;
	}

	public void setVesselConstructCountry(String vesselConstructCountry) {
		this.vesselConstructCountry = vesselConstructCountry;
	}

	public String getVesselConstructPlace() {
		return vesselConstructPlace;
	}

	public void setVesselConstructPlace(String vesselConstructPlace) {
		this.vesselConstructPlace = vesselConstructPlace;
	}

	public String getVesselUse() {
		return vesselUse;
	}

	public void setVesselUse(String vesselUse) {
		this.vesselUse = vesselUse;
	}

	public boolean getVesselCharterContract() {
		return vesselCharterContract;
	}

	public void setVesselCharterContract(boolean vesselCharterContract) {
		this.vesselCharterContract = vesselCharterContract;
	}

	public String getVesselChartererName() {
		return vesselChartererName;
	}

	public void setVesselChartererName(String vesselChartererName) {
		this.vesselChartererName = vesselChartererName;
	}

	public int getVesselCharterPeriod() {
		return vesselCharterPeriod;
	}

	public void setVesselCharterPeriod(int vesselCharterPeriod) {
		this.vesselCharterPeriod = vesselCharterPeriod;
	}

	public String getVesselCharterPeriodUnit() {
		return vesselCharterPeriodUnit;
	}

	public void setVesselCharterPeriodUnit(String vesselCharterPeriodUnit) {
		this.vesselCharterPeriodUnit = vesselCharterPeriodUnit;
	}

	public Double getVesselCharterAmt() {
		return vesselCharterAmt;
	}

	public void setVesselCharterAmt(Double vesselCharterAmt) {
		this.vesselCharterAmt = vesselCharterAmt;
	}

	public String getVesselCharterCurrency() {
		return vesselCharterCurrency;
	}

	public void setVesselCharterCurrency(String vesselCharterCurrency) {
		this.vesselCharterCurrency = vesselCharterCurrency;
	}

	public String getVesselCharterRateUnit() {
		return vesselCharterRateUnit;
	}

	public void setVesselCharterRateUnit(String vesselCharterRateUnit) {
		this.vesselCharterRateUnit = vesselCharterRateUnit;
	}

	public String getVesselCharterRateUnitOTH() {
		return vesselCharterRateUnitOTH;
	}

	public void setVesselCharterRateUnitOTH(String vesselCharterRateUnitOTH) {
		this.vesselCharterRateUnitOTH = vesselCharterRateUnitOTH;
	}

	public String getVesselCharterRemarks() {
		return vesselCharterRemarks;
	}

	public void setVesselCharterRemarks(String vesselCharterRemarks) {
		this.vesselCharterRemarks = vesselCharterRemarks;
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
		else if (!(obj instanceof OBVessel)) {
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