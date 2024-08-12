/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/OBWarehouseReceipt.java,v 1.6 2004/07/20 10:20:52 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a Warehouse Receipt entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/07/20 10:20:52 $ Tag: $Name: $
 */
public class OBWarehouseReceipt implements IWarehouseReceipt {
	private long warehouseReceiptID = ICMSConstant.LONG_INVALID_VALUE;

	private Date issueDate;

	private Date lastUpdateDate;

	private String receiptStatus;

	private String exchangeNo;

	private String origReceiptNo;

	private Date eWRDate;

	private String paperReceiptNo;

	private Date pWRDate;

	private String titleHolderNo;

	private String titleHolderName;

	private String beneficiaryNo;

	private String beneficiaryName;

	private IWarehouse warehouse;

	private String warehouseStoreNo;

	private String warehouseAddress;

	private String cargoNo;

	private String productName;

	private String productOrigin;

	private String productCropYear;

	private String productDesc;

	private String containerNo;

	private String sealNo;

	private Quantity quantity;

	private String ICOMark;

	private String additionalMark;

	private String vesselName;

	private String carrierName;

	private String voyageNo;

	private String billLadingNo;

	private Date billLadingDate;

	private Date assignmentDate;

	private Date bankReleaseDate;

	private String deliveryOrder;

	private Date freeTimeExpDate;

	private String gradeCert;

	private String weightNote;

	private String customsEntryNo;

	private String receiptRemarks;

	private long refID = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	private Date titleHolderChangedDate;

	private Date beneficiaryChangedDate;

	/**
	 * Default Constructor.
	 */
	public OBWarehouseReceipt() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IWarehouseReceipt
	 */
	public OBWarehouseReceipt(IWarehouseReceipt obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get warehouse receipt id.
	 * 
	 * @return long
	 */
	public long getWarehouseReceiptID() {
		return warehouseReceiptID;
	}

	/**
	 * Set warehouse receipt id.
	 * 
	 * @param warehouseReceiptID of type long
	 */
	public void setWarehouseReceiptID(long warehouseReceiptID) {
		this.warehouseReceiptID = warehouseReceiptID;
	}

	/**
	 * Get receipt issue date.
	 * 
	 * @return Date
	 */
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * Set receipt issue date.
	 * 
	 * @param issueDate of type Date
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * Get last modify date.
	 * 
	 * @return Date
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * Set last modify date.
	 * 
	 * @param lastUpdateDate of type Date
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * Get warehouse receipt status.
	 * 
	 * @return String
	 */
	public String getReceiptStatus() {
		return receiptStatus;
	}

	/**
	 * Set warehouse receipt status.
	 * 
	 * @param receiptStatus of type String
	 */
	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	/**
	 * Get exchange number.
	 * 
	 * @return String
	 */
	public String getExchangeNo() {
		return exchangeNo;
	}

	/**
	 * Set exchange number.
	 * 
	 * @param exchangeNo of type String
	 */
	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	/**
	 * Get original receipt no.
	 * 
	 * @return String
	 */
	public String getOrigReceiptNo() {
		return origReceiptNo;
	}

	/**
	 * Set original receipt no.
	 * 
	 * @param origReceiptNo of type String
	 */
	public void setOrigReceiptNo(String origReceiptNo) {
		this.origReceiptNo = origReceiptNo;
	}

	/**
	 * Get EWR conversion date.
	 * 
	 * @return Date
	 */
	public Date getEWRDate() {
		return eWRDate;
	}

	/**
	 * Set EWR conversion date.
	 * 
	 * @param eWRDate of type Date
	 */
	public void setEWRDate(Date eWRDate) {
		this.eWRDate = eWRDate;
	}

	/**
	 * Get paper receipt number.
	 * 
	 * @return String
	 */
	public String getPaperReceiptNo() {
		return paperReceiptNo;
	}

	/**
	 * Set paper receipt number.
	 * 
	 * @param paperReceiptNo of type String
	 */
	public void setPaperReceiptNo(String paperReceiptNo) {
		this.paperReceiptNo = paperReceiptNo;
	}

	/**
	 * Get conversion date of PWR.
	 * 
	 * @return Date
	 */
	public Date getPWRDate() {
		return pWRDate;
	}

	/**
	 * Set conversion date of PWR.
	 * 
	 * @param pWRDate of type Date
	 */
	public void setPWRDate(Date pWRDate) {
		this.pWRDate = pWRDate;
	}

	/**
	 * Get title holder number.
	 * 
	 * @return String
	 */
	public String getTitleHolderNo() {
		return titleHolderNo;
	}

	/**
	 * Set title holder number.
	 * 
	 * @param titleHolderNo of type String
	 */
	public void setTitleHolderNo(String titleHolderNo) {
		this.titleHolderNo = titleHolderNo;
	}

	/**
	 * Get title holder name.
	 * 
	 * @return String
	 */
	public String getTitleHolderName() {
		return titleHolderName;
	}

	/**
	 * Set title holder name.
	 * 
	 * @param titleHolderName of type String
	 */
	public void setTitleHolderName(String titleHolderName) {
		this.titleHolderName = titleHolderName;
	}

	/**
	 * Get beneficiary number.
	 * 
	 * @return String
	 */
	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	/**
	 * Set beneficiary number.
	 * 
	 * @param beneficiaryNo of type String
	 */
	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
	}

	/**
	 * Get beneficiary name.
	 * 
	 * @return String
	 */
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	/**
	 * Set beneficiary name.
	 * 
	 * @param beneficiaryName of type String
	 */
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	/**
	 * Get warehouse.
	 * 
	 * @return IWarehouse
	 */
	public IWarehouse getWarehouse() {
		return warehouse;
	}

	/**
	 * Set warehouse.
	 * 
	 * @param warehouse of type IWarehouse
	 */
	public void setWarehouse(IWarehouse warehouse) {
		this.warehouse = warehouse;
	}

	/**
	 * Get warehouse store number.
	 * 
	 * @return String
	 */
	public String getWarehouseStoreNo() {
		return warehouseStoreNo;
	}

	/**
	 * Set warehouse store number.
	 * 
	 * @param warehouseStoreNo of type String
	 */
	public void setWarehouseStoreNo(String warehouseStoreNo) {
		this.warehouseStoreNo = warehouseStoreNo;
	}

	/**
	 * Get warehouse address.
	 * 
	 * @return String
	 */
	public String getWarehouseAddress() {
		return warehouseAddress;
	}

	/**
	 * Set warehouse address.
	 * 
	 * @param warehouseAddress of type String
	 */
	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

	/**
	 * Get cargo number.
	 * 
	 * @return String
	 */
	public String getCargoNo() {
		return cargoNo;
	}

	/**
	 * Set cargo number.
	 * 
	 * @param cargoNo of type String
	 */
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	/**
	 * Get product name.
	 * 
	 * @return String
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Set product name.
	 * 
	 * @param productName of type String
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Get product origin.
	 * 
	 * @return String
	 */
	public String getProductOrigin() {
		return productOrigin;
	}

	/**
	 * Set product origin.
	 * 
	 * @param productOrigin of type String
	 */
	public void setProductOrigin(String productOrigin) {
		this.productOrigin = productOrigin;
	}

	/**
	 * Get product crop year.
	 * 
	 * @return String
	 */
	public String getProductCropYear() {
		return productCropYear;
	}

	/**
	 * Set product crop year.
	 * 
	 * @param productCropYear of type String
	 */
	public void setProductCropYear(String productCropYear) {
		this.productCropYear = productCropYear;
	}

	/**
	 * Get product description.
	 * 
	 * @return String
	 */
	public String getProductDesc() {
		return productDesc;
	}

	/**
	 * Set product description.
	 * 
	 * @param productDesc of type String
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	/**
	 * Get container number.
	 * 
	 * @return String
	 */
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * Set container number.
	 * 
	 * @param containerNo of type String
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * Set seal number.
	 * 
	 * @return String
	 */
	public String getSealNo() {
		return sealNo;
	}

	/**
	 * Set seal number.
	 * 
	 * @param sealNo of type String
	 */
	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}

	/**
	 * Get quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getQuantity() {
		return quantity;
	}

	/**
	 * Set quantity.
	 * 
	 * @param quantity of type Quantity
	 */
	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	/**
	 * Get ICO mark.
	 * 
	 * @return String
	 */
	public String getICOMark() {
		return ICOMark;
	}

	/**
	 * Set ICO mark.
	 * 
	 * @param ICOMark of type String
	 */
	public void setICOMark(String ICOMark) {
		this.ICOMark = ICOMark;
	}

	/**
	 * Get additional marks.
	 * 
	 * @return String
	 */
	public String getAdditionalMark() {
		return additionalMark;
	}

	/**
	 * Set additional marks.
	 * 
	 * @param additionalMark of type String
	 */
	public void setAdditionalMark(String additionalMark) {
		this.additionalMark = additionalMark;
	}

	/**
	 * Get vessel name.
	 * 
	 * @return String
	 */
	public String getVesselName() {
		return vesselName;
	}

	/**
	 * Set vessel name.
	 * 
	 * @param vesselName of type String
	 */
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	/**
	 * Get carrier name.
	 * 
	 * @return String
	 */
	public String getCarrierName() {
		return carrierName;
	}

	/**
	 * Set carrier name.
	 * 
	 * @param carrierName of type String
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	/**
	 * Get voyage number.
	 * 
	 * @return String
	 */
	public String getVoyageNo() {
		return voyageNo;
	}

	/**
	 * Set voyage number.
	 * 
	 * @param voyageNo of type String
	 */
	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}

	/**
	 * Get bill of lading number.
	 * 
	 * @return String
	 */
	public String getBillLadingNo() {
		return billLadingNo;
	}

	/**
	 * Set bill of lading number.
	 * 
	 * @param billLadingNo of type String
	 */
	public void setBillLadingNo(String billLadingNo) {
		this.billLadingNo = billLadingNo;
	}

	/**
	 * Get bill of lading date.
	 * 
	 * @return Date
	 */
	public Date getBillLadingDate() {
		return billLadingDate;
	}

	/**
	 * Set bill of lading date.
	 * 
	 * @param billLadingDate of type Date
	 */
	public void setBillLadingDate(Date billLadingDate) {
		this.billLadingDate = billLadingDate;
	}

	/**
	 * Get date of assignment.
	 * 
	 * @return Date
	 */
	public Date getAssignmentDate() {
		return assignmentDate;
	}

	/**
	 * Set date of assignment.
	 * 
	 * @param assignmentDate of type Date
	 */
	public void setAssignmentDate(Date assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	/**
	 * Get date of bank release.
	 * 
	 * @return Date
	 */
	public Date getBankReleaseDate() {
		return bankReleaseDate;
	}

	/**
	 * Set date of bank release.
	 * 
	 * @param bankReleaseDate of type Date
	 */
	public void setBankReleaseDate(Date bankReleaseDate) {
		this.bankReleaseDate = bankReleaseDate;
	}

	/**
	 * Get delivery order.
	 * 
	 * @return String
	 */
	public String getDeliveryOrder() {
		return deliveryOrder;
	}

	/**
	 * Set delivery order.
	 * 
	 * @param deliveryOrder of type String
	 */
	public void setDeliveryOrder(String deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
	}

	/**
	 * Get free type exipry date.
	 * 
	 * @return Date
	 */
	public Date getFreeTimeExpDate() {
		return freeTimeExpDate;
	}

	/**
	 * Set free time expiry date.
	 * 
	 * @param freeTimeExpDate of type Date
	 */
	public void setFreeTimeExpDate(Date freeTimeExpDate) {
		this.freeTimeExpDate = freeTimeExpDate;
	}

	/**
	 * Get grade certificate.
	 * 
	 * @return String
	 */
	public String getGradeCert() {
		return gradeCert;
	}

	/**
	 * Set grade certificate.
	 * 
	 * @param gradeCert of type String
	 */
	public void setGradeCert(String gradeCert) {
		this.gradeCert = gradeCert;
	}

	/**
	 * Get weight note.
	 * 
	 * @return String
	 */
	public String getWeightNote() {
		return weightNote;
	}

	/**
	 * Set weight note.
	 * 
	 * @param weightNote of type String
	 */
	public void setWeightNote(String weightNote) {
		this.weightNote = weightNote;
	}

	/**
	 * Get FDA/customs entry number.
	 * 
	 * @return String
	 */
	public String getCustomsEntryNo() {
		return customsEntryNo;
	}

	/**
	 * Set FDA/customs entry number.
	 * 
	 * @param customsEntryNo of type String
	 */
	public void setCustomsEntryNo(String customsEntryNo) {
		this.customsEntryNo = customsEntryNo;
	}

	/**
	 * Get warehouse receipt remarks.
	 * 
	 * @return String
	 */
	public String getReceiptRemarks() {
		return receiptRemarks;
	}

	/**
	 * Set warehouse receipt remarks.
	 * 
	 * @param receiptRemarks of type String
	 */
	public void setReceiptRemarks(String receiptRemarks) {
		this.receiptRemarks = receiptRemarks;
	}

	/**
	 * Get common reference id for actual and staging data.
	 * 
	 * @return long
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * Set common reference id for actual and staging data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Get CMS business status.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set CMS business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get title holder changed date.
	 * 
	 * @return Date
	 */
	public Date getTitleHolderChangedDate() {
		return titleHolderChangedDate;
	}

	/**
	 * Set title holder changed date.
	 * 
	 * @param titleHolderChangedDate of type Date
	 */
	public void setTitleHolderChangedDate(Date titleHolderChangedDate) {
		this.titleHolderChangedDate = titleHolderChangedDate;
	}

	/**
	 * Get beneficiary changed date.
	 * 
	 * @return Date
	 */
	public Date getBeneficiaryChangedDate() {
		return beneficiaryChangedDate;
	}

	/**
	 * Set beneficiary changed date.
	 * 
	 * @param beneficiaryChangedDate of type Date
	 */
	public void setBeneficiaryChangedDate(Date beneficiaryChangedDate) {
		this.beneficiaryChangedDate = beneficiaryChangedDate;
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
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(warehouseReceiptID);
		return hash.hashCode();
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
		else if (!(obj instanceof IWarehouseReceipt)) {
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