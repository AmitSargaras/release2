/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/warehouse/TitleDocWarehouseForm.java,v 1.3 2004/06/11 10:43:03 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc.warehouse;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/11 10:43:03 $ Tag: $Name: $
 */

public class TitleDocWarehouseForm extends CommonForm implements Serializable {

	// Eletronic negotiable warehouse (ERW) receipt number
	private String warehouseName = "";

	// Issued on
	private String issuedOn = "";

	private String lastModified = "";

	private String status = "";

	private String exchangeID = "";

	private String origPaperRecNo = "";

	private String convertEWRDate = "";

	private String paperRecNo = "";

	private String convertPWRDate = "";

	private String titleHolder = "";

	private String titleHolderNo = "";

	// Beneficiary
	private String beneficiary = "";

	private String beneficiaryNo = "";

	private String titleHolderChangedDate = "";

	private String beneficiaryChangedDate = "";

	private String warehouseLocCountry = "";

	private String warehouseLocStoreNo = "";

	private String cargoNo = "";

	// Product
	private String product = "";

	// Origin
	private String origin = "";

	private String cropYear = "";

	private String description = "";

	// Container Number
	private String containerNo = "";

	private String sealNo = "";

	// Quantity
	private String quantity = "";

	// Quantity Units
	private String quantityUnit = "";

	private String packagingType = "";

	// ICO Mark
	private String icoMark = "";

	private String additionalMark = "";

	// Vessel Name
	private String vesselName = "";

	private String carrier = "";

	private String voyageNo = "";

	private String billLadingNo = "";

	private String billLadingDate = "";

	private String dateAssignment = "";

	private String dateBankRelease = "";

	private String deliverOrder = "";

	private String freeTimeExpiry = "";

	private String gradeCert = "";

	private String weightNote = "";

	private String fdaCustomerEntryNo = "";

	// Remarks
	private String warehouseRemarks = "";

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getIssuedOn() {
		return issuedOn;
	}

	public void setIssuedOn(String issuedOn) {
		this.issuedOn = issuedOn;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExchangeID() {
		return exchangeID;
	}

	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}

	public String getOrigPaperRecNo() {
		return origPaperRecNo;
	}

	public void setOrigPaperRecNo(String origPaperRecNo) {
		this.origPaperRecNo = origPaperRecNo;
	}

	public String getConvertEWRDate() {
		return convertEWRDate;
	}

	public void setConvertEWRDate(String convertEWRDate) {
		this.convertEWRDate = convertEWRDate;
	}

	public String getPaperRecNo() {
		return paperRecNo;
	}

	public void setPaperRecNo(String paperRecNo) {
		this.paperRecNo = paperRecNo;
	}

	public String getConvertPWRDate() {
		return convertPWRDate;
	}

	public void setConvertPWRDate(String convertPWRDate) {
		this.convertPWRDate = convertPWRDate;
	}

	public String getTitleHolder() {
		return titleHolder;
	}

	public void setTitleHolder(String titleHolder) {
		this.titleHolder = titleHolder;
	}

	public String getTitleHolderNo() {
		return titleHolderNo;
	}

	public void setTitleHolderNo(String titleHolderNo) {
		this.titleHolderNo = titleHolderNo;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
	}

	public String getTitleHolderChangedDate() {
		return titleHolderChangedDate;
	}

	public void setTitleHolderChangedDate(String titleHolderChangedDate) {
		this.titleHolderChangedDate = titleHolderChangedDate;
	}

	public String getBeneficiaryChangedDate() {
		return beneficiaryChangedDate;
	}

	public void setBeneficiaryChangedDate(String beneficiaryChangedDate) {
		this.beneficiaryChangedDate = beneficiaryChangedDate;
	}

	public String getWarehouseLocCountry() {
		return warehouseLocCountry;
	}

	public void setWarehouseLocCountry(String warehouseLocCountry) {
		this.warehouseLocCountry = warehouseLocCountry;
	}

	public String getWarehouseLocStoreNo() {
		return warehouseLocStoreNo;
	}

	public void setWarehouseLocStoreNo(String warehouseLocStoreNo) {
		this.warehouseLocStoreNo = warehouseLocStoreNo;
	}

	public String getCargoNo() {
		return cargoNo;
	}

	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getCropYear() {
		return cropYear;
	}

	public void setCropYear(String cropYear) {
		this.cropYear = cropYear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public String getPackagingType() {
		return packagingType;
	}

	public void setPackagingType(String packagingType) {
		this.packagingType = packagingType;
	}

	public String getIcoMark() {
		return icoMark;
	}

	public void setIcoMark(String icoMark) {
		this.icoMark = icoMark;
	}

	public String getAdditionalMark() {
		return additionalMark;
	}

	public void setAdditionalMark(String additionalMark) {
		this.additionalMark = additionalMark;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getVoyageNo() {
		return voyageNo;
	}

	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}

	public String getBillLadingNo() {
		return billLadingNo;
	}

	public void setBillLadingNo(String billLadingNo) {
		this.billLadingNo = billLadingNo;
	}

	public String getBillLadingDate() {
		return billLadingDate;
	}

	public void setBillLadingDate(String billLadingDate) {
		this.billLadingDate = billLadingDate;
	}

	public String getDateAssignment() {
		return dateAssignment;
	}

	public void setDateAssignment(String dateAssignment) {
		this.dateAssignment = dateAssignment;
	}

	public String getDateBankRelease() {
		return dateBankRelease;
	}

	public void setDateBankRelease(String dateBankRelease) {
		this.dateBankRelease = dateBankRelease;
	}

	public String getDeliverOrder() {
		return deliverOrder;
	}

	public void setDeliverOrder(String deliverOrder) {
		this.deliverOrder = deliverOrder;
	}

	public String getFreeTimeExpiry() {
		return freeTimeExpiry;
	}

	public void setFreeTimeExpiry(String freeTimeExpiry) {
		this.freeTimeExpiry = freeTimeExpiry;
	}

	public String getGradeCert() {
		return gradeCert;
	}

	public void setGradeCert(String gradeCert) {
		this.gradeCert = gradeCert;
	}

	public String getWeightNote() {
		return weightNote;
	}

	public void setWeightNote(String weightNote) {
		this.weightNote = weightNote;
	}

	public String getFdaCustomerEntryNo() {
		return fdaCustomerEntryNo;
	}

	public void setFdaCustomerEntryNo(String fdaCustomerEntryNo) {
		this.fdaCustomerEntryNo = fdaCustomerEntryNo;
	}

	public String getWarehouseRemarks() {
		return warehouseRemarks;
	}

	public void setWarehouseRemarks(String warehouseRemarks) {
		this.warehouseRemarks = warehouseRemarks;
	}

	public String[][] getMapper() {
		String[][] input = { { "titleDocWarehouseObj",
				"com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc.warehouse.TitleDocWarehouseMapper" }, };
		return input;
	}
}
