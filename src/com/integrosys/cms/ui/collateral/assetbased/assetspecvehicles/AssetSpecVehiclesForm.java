//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION

package com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.assetbased.AssetBasedForm;

/**
 * 
 * Created by IntelliJ IDEA.
 * 
 * User: ssathish
 * 
 * Date: Jun 20, 2003
 * 
 * Time: 2:56:38 PM
 * 
 * To change this template use Options | File Templates.
 */

/**
 * @author rajib.aich
 *
 */
public class AssetSpecVehiclesForm extends AssetBasedForm implements Serializable {

	public static final String ASSETSPECVEHICLESMAPPER = "com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles.AssetSpecVehiclesMapper";

	private String regNo = "";

	private String regFee = "";

	private String regDate = "";

	private String coverageOthers = "";

	private String yearMfg = "";

	private String purchasePrice = "";

	private String datePurchase = "";

	private String coverageType = "";

	private String registrationCardNo = "";

	private String assetType = "";

	private String residualAssetLife = "";

	private String residualAssetLifeUOM = "";

	private String scrapValue = "";

	private String assetValue = "";

	private String docPerfectAge = "";

	private String chassisNumber;

	private String prevOwnerName;

	private String prevFinancierName;

	private String yardOptions;

	private String yard;

	private String iindicator;

	private String collateralfee;

	private String collateralLocation;

	private String dptradein;

	private String dpcash;

	private String fcharges;

	private String plist;

	private String heavyvehicle;

	private String plantEquipValue;

	private String salesProceed;

	private String engineNo;

	private String goodStatus;

	private String coe = "";

	private String transType;

	private String energySource;

	private String horsePower;

	private String vehColor;

	private String amtCollectedFromSales;

	private String pbtIndicator;

	private String pbrPbtPeriod;

	private String chattelSoldDate;

	private String roadTaxAmt;

	private String roadTaxAmtType;

	private String roadTaxExpiryDate;

	private String allowPassive;

	private String logBookNumber;

	private String engineCapacity;

	private String ownershipClaimNumber;

	private String dealerName;

	private String eHakMilikNumber;

	private String halfYearlyRoadTaxAmt;

	private String yearlyRoadTaxAmt;

	private String tradeinValue;

	private String tradeInMake;

	private String tradeInModel;

	private String tradeInYearOfManufacture;

	private String tradeInRegistrationNo;

	private String tradeInValue;

	private String tradeInDeposit;

    private String invoiceDate;
    
    private String invoiceNo;
    
    private String branchName;
    
    private String ramId;
    
    private String startDate;
    
    private String assetCollateralBookingVal;
    
    private String descriptionAssets;
    
    
    
    


	public String getAssetCollateralBookingVal() {
		return assetCollateralBookingVal;
	}

	public void setAssetCollateralBookingVal(String assetCollateralBookingVal) {
		this.assetCollateralBookingVal = assetCollateralBookingVal;
	}

	public String getDescriptionAssets() {
		return descriptionAssets;
	}

	public void setDescriptionAssets(String descriptionAssets) {
		this.descriptionAssets = descriptionAssets;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getRamId() {
		return ramId;
	}

	public void setRamId(String ramId) {
		this.ramId = ramId;
	}

	public String getHalfYearlyRoadTaxAmt() {
		return halfYearlyRoadTaxAmt;
	}

	public void setHalfYearlyRoadTaxAmt(String halfYearlyRoadTaxAmt) {
		this.halfYearlyRoadTaxAmt = halfYearlyRoadTaxAmt;
	}

	public String getYearlyRoadTaxAmt() {
		return yearlyRoadTaxAmt;
	}

	public void setYearlyRoadTaxAmt(String yearlyRoadTaxAmt) {
		this.yearlyRoadTaxAmt = yearlyRoadTaxAmt;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getGoodStatus() {
		if (goodStatus != null) {
			goodStatus = goodStatus.trim();
		}
		return goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
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

	/*
	 * public String getDepreciateRate() { return depreciateRate; }
	 * 
	 * public void setDepreciateRate(String depreciateRate) {
	 * this.depreciateRate = depreciateRate; }
	 */

	public String getSalesProceed() {
		return salesProceed;
	}

	public void setSalesProceed(String salesProceed) {
		this.salesProceed = salesProceed;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRegFee() {
		return regFee;
	}

	public void setRegFee(String regFee) {
		this.regFee = regFee;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getCoverageOthers() {
		return coverageOthers;
	}

	public void setCoverageOthers(String coverageOthers) {
		this.coverageOthers = coverageOthers;
	}

	public String getYearMfg() {
		return yearMfg;
	}

	public void setYearMfg(String yearMfg) {
		this.yearMfg = yearMfg;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(String datePurchase) {
		this.datePurchase = datePurchase;
	}

	public String getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}

	public String getRegistrationCardNo() {
		return registrationCardNo;
	}

	public void setRegistrationCardNo(String registrationCardNo) {
		this.registrationCardNo = registrationCardNo;
	}

	public String getCoe() {
		return coe;
	}

	public void setCoe(String coe) {
		this.coe = coe;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getResidualAssetLife() {
		return residualAssetLife;
	}

	public void setResidualAssetLife(String residualAssetLife) {
		this.residualAssetLife = residualAssetLife;
	}

	public String getResidualAssetLifeUOM() {
		return residualAssetLifeUOM;
	}

	public void setResidualAssetLifeUOM(String residualAssetLifeUOM) {
		this.residualAssetLifeUOM = residualAssetLifeUOM;
	}

	public String getScrapValue() {
		return scrapValue;
	}

	public void setScrapValue(String scrapValue) {
		this.scrapValue = scrapValue;
	}

	public String getAssetValue() {
		return assetValue;
	}

	public void setAssetValue(String assetValue) {
		this.assetValue = assetValue;
	}

	public String getDocPerfectAge() {
		return docPerfectAge;
	}

	public void setDocPerfectAge(String docPerfectAge) {
		this.docPerfectAge = docPerfectAge;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
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

	/* Added by Saritha for Asset based Vehicles */
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

	public String getDptradein() {
		return dptradein;
	}

	public void setDptradein(String dptradein) {
		this.dptradein = dptradein;
	}

	public String getDpcash() {
		return dpcash;
	}

	public void setDpcash(String dpcash) {
		this.dpcash = dpcash;
	}

	public String getFcharges() {
		return fcharges;
	}

	public void setFcharges(String fcharges) {
		this.fcharges = fcharges;
	}

	public String getPlist() {
		return plist;
	}

	public void setPlist(String plist) {
		this.plist = plist;
	}

	public String getHeavyvehicle() {
		return heavyvehicle;
	}

	public void setHeavyvehicle(String heavyvehicle) {
		this.heavyvehicle = heavyvehicle;
	}

	/* Added by Saritha for Asset based Vehicles */
	public String getPlantEquipValue() {
		return plantEquipValue;
	}

	public void setPlantEquipValue(String plantEquipValue) {
		this.plantEquipValue = plantEquipValue;
	}

	/*
	 * public String getYearOfManufacture() { return yearOfManufacture; }
	 * 
	 * public void setYearOfManufacture(String yearOfManufacture) {
	 * this.yearOfManufacture = yearOfManufacture; }
	 */

	public String getAmtCollectedFromSales() {
		return amtCollectedFromSales;
	}

	public void setAmtCollectedFromSales(String amtCollectedFromSales) {
		this.amtCollectedFromSales = amtCollectedFromSales;
	}

	public void reset() {
		super.reset();
	}

	public String[][] getMapper() {
		String[][] input = { { "form.collateralObject", ASSETSPECVEHICLESMAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" }, };
		return input;

	}

	public String getChattelSoldDate() {
		return chattelSoldDate;
	}

	public void setChattelSoldDate(String chattelSoldDate) {
		this.chattelSoldDate = chattelSoldDate;
	}

	public String getRoadTaxAmt() {
		return roadTaxAmt;
	}

	public void setRoadTaxAmt(String roadTaxAmt) {
		this.roadTaxAmt = roadTaxAmt;
	}

	public String getRoadTaxExpiryDate() {
		return roadTaxExpiryDate;
	}

	public void setRoadTaxExpiryDate(String roadTaxExpiryDate) {
		this.roadTaxExpiryDate = roadTaxExpiryDate;
	}

	public String getAllowPassive() {
		return allowPassive;
	}

	public void setAllowPassive(String allowPassive) {
		this.allowPassive = allowPassive;
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

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getEHakMilikNumber() {
		return eHakMilikNumber;
	}

	public void setEHakMilikNumber(String hakMilikNumber) {
		eHakMilikNumber = hakMilikNumber;
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

	public String getCollateralLocation() {
		return collateralLocation;
	}

	public void setCollateralLocation(String collateralLocation) {
		this.collateralLocation = collateralLocation;
	}

	public String getYard() {
		return yard;
	}

	public void setYard(String yard) {
		this.yard = yard;
	}

	public String getPbtIndicator() {
		return pbtIndicator;
	}

	public void setPbtIndicator(String pbtIndicator) {
		this.pbtIndicator = pbtIndicator;
	}

	public String getTradeinValue() {
		return tradeinValue;
	}

	public void setTradeinValue(String tradeinValue) {
		this.tradeinValue = tradeinValue;
	}

	public String getTradeInMake() {
		return tradeInMake;
	}

	public void setTradeInMake(String tradeInMake) {
		this.tradeInMake = tradeInMake;
	}

	public String getTradeInModel() {
		return tradeInModel;
	}

	public void setTradeInModel(String tradeInModel) {
		this.tradeInModel = tradeInModel;
	}

	public String getTradeInYearOfManufacture() {
		return tradeInYearOfManufacture;
	}

	public void setTradeInYearOfManufacture(String tradeInYearOfManufacture) {
		this.tradeInYearOfManufacture = tradeInYearOfManufacture;
	}

	public String getTradeInRegistrationNo() {
		return tradeInRegistrationNo;
	}

	public void setTradeInRegistrationNo(String tradeInRegistrationNo) {
		this.tradeInRegistrationNo = tradeInRegistrationNo;
	}

	public String getTradeInValue() {
		return tradeInValue;
	}

	public void setTradeInValue(String tradeInValue) {
		this.tradeInValue = tradeInValue;
	}

	public String getTradeInDeposit() {
		return tradeInDeposit;
	}

	public void setTradeInDeposit(String tradeInDeposit) {
		this.tradeInDeposit = tradeInDeposit;
	}

	public String getPbrPbtPeriod() {
		return pbrPbtPeriod;
	}

	public void setPbrPbtPeriod(String pbrPbtPeriod) {
		this.pbrPbtPeriod = pbrPbtPeriod;
	}

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

}
