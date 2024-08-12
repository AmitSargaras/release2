//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION

package com.integrosys.cms.ui.collateral.assetbased.assetspecplant;

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

public class AssetSpecPlantForm extends AssetBasedForm implements Serializable {

	public static final String ASSETSPECPLANTMAPPER = "com.integrosys.cms.ui.collateral.assetbased.assetspecplant.AssetSpecPlantMapper";

	private String purchasePrice = "";

	private String purchaseDate = "";

	private String datePurchase = "";

	// Used in common extra read
	private String registrationNo;

	private String registrationCardNo;

	private String registrationFee;

	private String registrationDate;

	private String yearOfManufacture;

	private String brand;

	private String modelNo;

	private String assetType;

	private String chassisNumber;

	// private String depreciateRate ;

	private String prevOwnerName;

	private String prevFinancierName;

	private String repossessionDate;

	private String repossessionAge;

	private String yard;

	private String salesProceed;

	// for extra fields
	private String coverageType;

	private String residualAssetLife;

	private String residualAssetLifeUOM;

	private String scrapValue;

	private String assetValue;

	private String docPerfectAge;

	private String goodStatus;

	private String inspectionStatus;

	// Added by Saritha for Asset based Plant & Equipment --- Begin
	private String purpose;

	private String equipmf;

	private String equipriskgrading;

	private String equipcode;

	private String quantity;

	private String olv;

	private String remainusefullife;

	private String valuationbasis;

	private String serialNumber;

	private String invoiceNumber;

	private String dpcash;

	private String dptradein;

	private String tradeinValue;

    private String invoiceDate;

	private String plantEquipValue;

	private String tradeInMake;

	private String tradeInModel;

	private String tradeInYearOfManufacture;

	private String tradeInRegistrationNo;

	private String tradeInValue;

	private String tradeInDeposit;
	
	private String plist;
	
	private String ramId;

	private String actionType = "";
	
	private String insuranceStatus;
	private String originalTargetDate;
	private String nextDueDate;
	private String dateDeferred;
	private String creditApprover;
	private String waivedDate;
	private String insuranceStatusRadio;
	
	public String getRamId() {
		return ramId;
	}

	public void setRamId(String ramId) {
		this.ramId = ramId;
	}
	
	public String getPlist() {
		return plist;
	}

	public void setPlist(String plist) {
		this.plist = plist;
	}

	public String getInspectionStatus() {
		return inspectionStatus;
	}

	public void setInspectionStatus(String inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}

	public String getPlantEquipValue() {
		return plantEquipValue;
	}

	public void setPlantEquipValue(String plantEquipValue) {
		this.plantEquipValue = plantEquipValue;
	}

	public String getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getDatePurchase() {
		return this.datePurchase;
	}

	public void setDatePurchase(String datePurchase) {
		this.datePurchase = datePurchase;
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

	public String getRepossessionDate() {
		return repossessionDate;
	}

	public void setRepossessionDate(String repossessionDate) {
		this.repossessionDate = repossessionDate;
	}

	public String getRepossessionAge() {
		return repossessionAge;
	}

	public void setRepossessionAge(String repossessionAge) {
		this.repossessionAge = repossessionAge;
	}

	public String getYard() {
		return yard;
	}

	public void setYard(String yard) {
		this.yard = yard;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getRegistrationCardNo() {
		return registrationCardNo;
	}

	public void setRegistrationCardNo(String registrationCardNo) {
		this.registrationCardNo = registrationCardNo;
	}

	public String getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(String registrationFee) {
		this.registrationFee = registrationFee;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getYearOfManufacture() {
		return yearOfManufacture;
	}

	public void setYearOfManufacture(String yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		if (null != assetType) {
			assetType = assetType.trim();
		}
		this.assetType = assetType;
	}

	public String getSalesProceed() {
		return salesProceed;
	}

	public void setSalesProceed(String salesProceed) {
		this.salesProceed = salesProceed;
	}

	public String getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}

	/*
	 * public String getDepreciateRate() { return depreciateRate; }
	 * 
	 * public void setDepreciateRate(String depreciateRate) {
	 * this.depreciateRate = depreciateRate; }
	 */

	// Added by Saritha for Asset based Plant & Equipment --- Begin
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getEquipmf() {
		return equipmf;
	}

	public void setEquipmf(String equipmf) {
		this.equipmf = equipmf;
	}

	public String getEquipriskgrading() {
		return equipriskgrading;
	}

	public void setEquipriskgrading(String equipriskgrading) {
		this.equipriskgrading = equipriskgrading;
	}

	public String getEquipcode() {
		return equipcode;
	}

	public void setEquipcode(String equipcode) {
		this.equipcode = equipcode;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getOlv() {
		return olv;
	}

	public void setOlv(String olv) {
		this.olv = olv;
	}

	public String getRemainusefullife() {
		return remainusefullife;
	}

	public void setRemainusefullife(String remainusefullife) {
		this.remainusefullife = remainusefullife;
	}

	public String getValuationbasis() {
		return valuationbasis;
	}

	public void setValuationbasis(String valuationbasis) {
		this.valuationbasis = valuationbasis;
	}

	// Added by Saritha for Asset based Plant & Equipment --- End

	public String getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		if (null != goodStatus) {
			goodStatus = goodStatus.trim();
		}
		this.goodStatus = goodStatus;
	}

	public void reset() {
		super.reset();
	}

	public String[][] getMapper() {
		String[][] input = { { "form.collateralObject", ASSETSPECPLANTMAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" }, };
		return input;

	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getDpcash() {
		return dpcash;
	}

	public void setDpcash(String dpcash) {
		this.dpcash = dpcash;
	}

	public String getDptradein() {
		return dptradein;
	}

	public void setDptradein(String dptradein) {
		this.dptradein = dptradein;
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

	public String getTradeInModel() {
		return tradeInModel;
	}

	public String getTradeInYearOfManufacture() {
		return tradeInYearOfManufacture;
	}

	public String getTradeInRegistrationNo() {
		return tradeInRegistrationNo;
	}

	public String getTradeInValue() {
		return tradeInValue;
	}

	public String getTradeInDeposit() {
		return tradeInDeposit;
	}

	public void setTradeInMake(String tradeInMake) {
		this.tradeInMake = tradeInMake;
	}

	public void setTradeInModel(String tradeInModel) {
		this.tradeInModel = tradeInModel;
	}

	public void setTradeInYearOfManufacture(String tradeInYearOfManufacture) {
		this.tradeInYearOfManufacture = tradeInYearOfManufacture;
	}

	public void setTradeInRegistrationNo(String tradeInRegistrationNo) {
		this.tradeInRegistrationNo = tradeInRegistrationNo;
	}

	public void setTradeInValue(String tradeInValue) {
		this.tradeInValue = tradeInValue;
	}

	public void setTradeInDeposit(String tradeInDeposit) {
		this.tradeInDeposit = tradeInDeposit;
	}

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public String getInsuranceStatus() {
		return insuranceStatus;
	}

	public void setInsuranceStatus(String insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}

	public String getOriginalTargetDate() {
		return originalTargetDate;
	}

	public void setOriginalTargetDate(String originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}

	public String getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public String getDateDeferred() {
		return dateDeferred;
	}

	public void setDateDeferred(String dateDeferred) {
		this.dateDeferred = dateDeferred;
	}

	public String getCreditApprover() {
		return creditApprover;
	}

	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}

	public String getWaivedDate() {
		return waivedDate;
	}

	public void setWaivedDate(String waivedDate) {
		this.waivedDate = waivedDate;
	}

	public String getInsuranceStatusRadio() {
		return insuranceStatusRadio;
	}

	public void setInsuranceStatusRadio(String insuranceStatusRadio) {
		this.insuranceStatusRadio = insuranceStatusRadio;
	}
}
