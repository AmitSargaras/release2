package com.integrosys.cms.ui.collateral.assetbased.assetvessel;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.assetbased.AssetBasedForm;

/**
 * Created by IntelliJ IDEA. * User: Naveen * Date: Jun 20, 2003 * Time: 2:56:38
 * PM * To change this template use Options | File Templates.
 */
public class AssetVesselForm extends AssetBasedForm implements Serializable {

	public static final String ASSETVESSELMAPPER = "com.integrosys.cms.ui.collateral.assetbased.assetvessel.AssetVesselMapper";

	private String coverageType = "";

	private String coverageOthers = "";

	private String regNo = "";

	private String regFee = "";

	private String regDate = "";

	private String yearMfg = "";

	private String brand = "";

	private String modelNo = "";

	private String purchasePrice = "";

	private String datePurchase = "";

	private String vesselName = "";

	private String regCountry = "";

	private String vesselState = "";

	private String vesselExptOccupDate = "";

	private String vesselExptOccup = "";

	private String vesselOccupType = "";

	private String vesselBuildYear = "";

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

	private String vesselCharterContract = "";

	private String vesselChartererName = "";

	private String vesselCharterPeriod = "";

	private String vesselCharterPeriodUnit = "";

	private String vesselCharterAmt = "";

	private String vesselCharterCurrency = "";

	private String vesselCharterRateUnit = "";

	private String vesselCharterRateUnitOTH = "";

	private String vesselCharterRemarks = "";

	private String residualAssetLife = "";

	private String residualAssetLifeUOM = "";

	private String vesselAssetValue = "";

	private String vesselScrapValue = "";

	private String vesselDocPerfectAge = "";

//	private String approvalObtained = "O";

//	private String approvalDate = "";

	private String tradeInDeposit = "";

	private String tradeInValue = "";

	// private String depreciateRate = "" ;

	/*
	 * public String getDepreciateRate() { return depreciateRate; }
	 * 
	 * public void setDepreciateRate(String depreciateRate) {
	 * this.depreciateRate = depreciateRate; }
	 */

	private String insurers;

	public String getInsurers() {
		return insurers;
	}

	public void setInsurers(String insurers) {
		this.insurers = insurers;
	}

	public String getCoverageType() {
		return this.coverageType;
	}

	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}

	public String getCoverageOthers() {
		return this.coverageOthers;
	}

	public void setCoverageOthers(String coverageOthers) {
		this.coverageOthers = coverageOthers;
	}

	public String getRegNo() {
		return this.regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRegFee() {
		return this.regFee;
	}

	public void setRegFee(String regFee) {
		this.regFee = regFee;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getYearMfg() {
		return this.yearMfg;
	}

	public void setYearMfg(String yearMfg) {
		this.yearMfg = yearMfg;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModelNo() {
		return this.modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getDatePurchase() {
		return this.datePurchase;
	}

	public void setDatePurchase(String datePurchase) {
		this.datePurchase = datePurchase;
	}

	// Added By Naveen
	public String getVesselName() {
		return vesselName;
	}

	public String getRegCountry() {
		return regCountry;
	}

	public String getVesselState() {
		return vesselState;
	}

	public String getVesselExptOccupDate() {
		return vesselExptOccupDate;
	}

	public String getVesselExptOccup() {
		return vesselExptOccup;
	}

	public String getVesselOccupType() {
		return vesselOccupType;
	}

	public String getVesselBuildYear() {
		return vesselBuildYear;
	}

	public String getVesselPurchaseCurrency() {
		return vesselPurchaseCurrency;
	}

	public String getVesselBuilder() {
		return vesselBuilder;
	}

	public String getVesselMainReg() {
		return vesselMainReg;
	}

	public String getVesselLength() {
		return vesselLength;
	}

	public String getVesselWidth() {
		return vesselWidth;
	}

	public String getVesselDepth() {
		return vesselDepth;
	}

	public String getVesselDeckLoading() {
		return vesselDeckLoading;
	}

	public String getVesselDeckWeight() {
		return vesselDeckWeight;
	}

	public String getVesselSideBoard() {
		return vesselSideBoard;
	}

	public String getVesselBOW() {
		return vesselBOW;
	}

	public String getVesselDECK() {
		return vesselDECK;
	}

	public String getVesselDeckThickness() {
		return vesselDeckThickness;
	}

	public String getVesselBottom() {
		return vesselBottom;
	}

	public String getVesselWinchDrive() {
		return vesselWinchDrive;
	}

	public String getVesselBHP() {
		return vesselBHP;
	}

	public String getVesselSpeed() {
		return vesselSpeed;
	}

	public String getVesselAnchor() {
		return vesselAnchor;
	}

	public String getVesselAnchorDrive() {
		return vesselAnchorDrive;
	}

	public String getVesselClassSociety() {
		return vesselClassSociety;
	}

	public String getVesselConstructCountry() {
		return vesselConstructCountry;
	}

	public String getVesselConstructPlace() {
		return vesselConstructPlace;
	}

	public String getVesselUse() {
		return vesselUse;
	}

	public String getVesselCharterContract() {
		return vesselCharterContract;
	}

	public String getVesselChartererName() {
		return vesselChartererName;
	}

	public String getVesselCharterPeriod() {
		return vesselCharterPeriod;
	}

	public String getVesselCharterPeriodUnit() {
		return vesselCharterPeriodUnit;
	}

	public String getVesselCharterAmt() {
		return vesselCharterAmt;
	}

	public String getVesselCharterCurrency() {
		return vesselCharterCurrency;
	}

	public String getVesselCharterRateUnit() {
		return vesselCharterRateUnit;
	}

	public String getVesselCharterRateUnitOTH() {
		return vesselCharterRateUnitOTH;
	}

	public String getVesselCharterRemarks() {
		return vesselCharterRemarks;
	}

	public String getResidualAssetLife() {
		return residualAssetLife;
	}

	public String getResidualAssetLifeUOM() {
		return residualAssetLifeUOM;
	}

	public String getVesselAssetValue() {
		return vesselAssetValue;
	}

	public String getVesselScrapValue() {
		return vesselScrapValue;
	}

	public String getVesselDocPerfectAge() {
		return vesselDocPerfectAge;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public void setRegCountry(String regCountry) {
		this.regCountry = regCountry;
	}

	public void setVesselState(String vesselState) {
		this.vesselState = vesselState;
	}

	public void setVesselExptOccupDate(String vesselExptOccupDate) {
		this.vesselExptOccupDate = vesselExptOccupDate;
	}

	public void setVesselExptOccup(String vesselExptOccup) {
		this.vesselExptOccup = vesselExptOccup;
	}

	public void setVesselOccupType(String vesselOccupType) {
		this.vesselOccupType = vesselOccupType;
	}

	public void setVesselBuildYear(String vesselBuildYear) {
		this.vesselBuildYear = vesselBuildYear;
	}

	public void setVesselPurchaseCurrency(String vesselPurchaseCurrency) {
		this.vesselPurchaseCurrency = vesselPurchaseCurrency;
	}

	public void setVesselBuilder(String vesselBuilder) {
		this.vesselBuilder = vesselBuilder;
	}

	public void setVesselMainReg(String vesselMainReg) {
		this.vesselMainReg = vesselMainReg;
	}

	public void setVesselLength(String vesselLength) {
		this.vesselLength = vesselLength;
	}

	public void setVesselWidth(String vesselWidth) {
		this.vesselWidth = vesselWidth;
	}

	public void setVesselDepth(String vesselDepth) {
		this.vesselDepth = vesselDepth;
	}

	public void setVesselDeckLoading(String vesselDeckLoading) {
		this.vesselDeckLoading = vesselDeckLoading;
	}

	public void setVesselDeckWeight(String vesselDeckWeight) {
		this.vesselDeckWeight = vesselDeckWeight;
	}

	public void setVesselSideBoard(String vesselSideBoard) {
		this.vesselSideBoard = vesselSideBoard;
	}

	public void setVesselBOW(String vesselBOW) {
		this.vesselBOW = vesselBOW;
	}

	public void setVesselDECK(String vesselDECK) {
		this.vesselDECK = vesselDECK;
	}

	public void setVesselDeckThickness(String vesselDeckThickness) {
		this.vesselDeckThickness = vesselDeckThickness;
	}

	public void setVesselBottom(String vesselBottom) {
		this.vesselBottom = vesselBottom;
	}

	public void setVesselWinchDrive(String vesselWinchDrive) {
		this.vesselWinchDrive = vesselWinchDrive;
	}

	public void setVesselBHP(String vesselBHP) {
		this.vesselBHP = vesselBHP;
	}

	public void setVesselSpeed(String vesselSpeed) {
		this.vesselSpeed = vesselSpeed;
	}

	public void setVesselAnchor(String vesselAnchor) {
		this.vesselAnchor = vesselAnchor;
	}

	public void setVesselAnchorDrive(String vesselAnchorDrive) {
		this.vesselAnchorDrive = vesselAnchorDrive;
	}

	public void setVesselClassSociety(String vesselClassSociety) {
		this.vesselClassSociety = vesselClassSociety;
	}

	public void setVesselConstructCountry(String vesselConstructCountry) {
		this.vesselConstructCountry = vesselConstructCountry;
	}

	public void setVesselConstructPlace(String vesselConstructPlace) {
		this.vesselConstructPlace = vesselConstructPlace;
	}

	public void setVesselUse(String vesselUse) {
		this.vesselUse = vesselUse;
	}

	public void setVesselCharterContract(String vesselCharterContract) {
		this.vesselCharterContract = vesselCharterContract;
	}

	public void setVesselChartererName(String vesselChartererName) {
		this.vesselChartererName = vesselChartererName;
	}

	public void setVesselCharterPeriod(String vesselCharterPeriod) {
		this.vesselCharterPeriod = vesselCharterPeriod;
	}

	public void setVesselCharterPeriodUnit(String vesselCharterPeriodUnit) {
		this.vesselCharterPeriodUnit = vesselCharterPeriodUnit;
	}

	public void setVesselCharterAmt(String vesselCharterAmt) {
		this.vesselCharterAmt = vesselCharterAmt;
	}

	public void setVesselCharterCurrency(String vesselCharterCurrency) {
		this.vesselCharterCurrency = vesselCharterCurrency;
	}

	public void setVesselCharterRateUnit(String vesselCharterRateUnit) {
		this.vesselCharterRateUnit = vesselCharterRateUnit;
	}

	public void setVesselCharterRateUnitOTH(String vesselCharterRateUnitOTH) {
		this.vesselCharterRateUnitOTH = vesselCharterRateUnitOTH;
	}

	public void setVesselCharterRemarks(String vesselCharterRemarks) {
		this.vesselCharterRemarks = vesselCharterRemarks;
	}

	public void setResidualAssetLife(String residualAssetLife) {
		this.residualAssetLife = residualAssetLife;
	}

	public void setResidualAssetLifeUOM(String residualAssetLifeUOM) {
		this.residualAssetLifeUOM = residualAssetLifeUOM;
	}

	public void setVesselAssetValue(String vesselAssetValue) {
		this.vesselAssetValue = vesselAssetValue;
	}

	public void setVesselScrapValue(String vesselScrapValue) {
		this.vesselScrapValue = vesselScrapValue;
	}

	public void setVesselDocPerfectAge(String vesselDocPerfectAge) {
		this.vesselDocPerfectAge = vesselDocPerfectAge;
	}

	/*public String getApprovalObtained() {
		return approvalObtained;
	}

	public void setApprovalObtained(String approvalObtained) {
		this.approvalObtained = approvalObtained;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}*/

	public String getTradeInDeposit() {
		return tradeInDeposit;
	}

	public void setTradeInDeposit(String tradeInDeposit) {
		this.tradeInDeposit = tradeInDeposit;
	}

	public String getTradeInValue() {
		return tradeInValue;
	}

	public void setTradeInValue(String tradeInValue) {
		this.tradeInValue = tradeInValue;
	}

	public void reset() {
		super.reset();
	}

	public String[][] getMapper() {
		String[][] input = { { "form.collateralObject", ASSETVESSELMAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" }, };
		return input;
	}

}