/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/IWarehouseReceipt.java,v 1.5 2004/07/20 10:20:51 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;

/**
 * This interface represents Warehouse Receipt.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/07/20 10:20:51 $ Tag: $Name: $
 */
public interface IWarehouseReceipt extends Serializable {
	/**
	 * Get warehouse receipt id.
	 * 
	 * @return long
	 */
	public long getWarehouseReceiptID();

	/**
	 * Set warehouse receipt id.
	 * 
	 * @param warehouseReceiptID of type long
	 */
	public void setWarehouseReceiptID(long warehouseReceiptID);

	/**
	 * Get receipt issue date.
	 * 
	 * @return Date
	 */
	public Date getIssueDate();

	/**
	 * Set receipt issue date.
	 * 
	 * @param issueDate of type Date
	 */
	public void setIssueDate(Date issueDate);

	/**
	 * Get last modify date.
	 * 
	 * @return Date
	 */
	public Date getLastUpdateDate();

	/**
	 * Set last modify date.
	 * 
	 * @param lastUpdateDate of type Date
	 */
	public void setLastUpdateDate(Date lastUpdateDate);

	/**
	 * Get warehouse receipt status.
	 * 
	 * @return String
	 */
	public String getReceiptStatus();

	/**
	 * Set warehouse receipt status.
	 * 
	 * @param receiptStatus of type String
	 */
	public void setReceiptStatus(String receiptStatus);

	/**
	 * Get exchange number.
	 * 
	 * @return String
	 */
	public String getExchangeNo();

	/**
	 * Set exchange number.
	 * 
	 * @param exchangeNo of type String
	 */
	public void setExchangeNo(String exchangeNo);

	/**
	 * Get original receipt no.
	 * 
	 * @return String
	 */
	public String getOrigReceiptNo();

	/**
	 * Set original receipt no.
	 * 
	 * @param origReceiptNo of type String
	 */
	public void setOrigReceiptNo(String origReceiptNo);

	/**
	 * Get EWR conversion date.
	 * 
	 * @return Date
	 */
	public Date getEWRDate();

	/**
	 * Set EWR conversion date.
	 * 
	 * @param eWRDate of type Date
	 */
	public void setEWRDate(Date eWRDate);

	/**
	 * Get paper receipt number.
	 * 
	 * @return String
	 */
	public String getPaperReceiptNo();

	/**
	 * Set paper receipt number.
	 * 
	 * @param paperReceiptNo of type String
	 */
	public void setPaperReceiptNo(String paperReceiptNo);

	/**
	 * Get conversion date of PWR.
	 * 
	 * @return Date
	 */
	public Date getPWRDate();

	/**
	 * Set conversion date of PWR.
	 * 
	 * @param pWRDate of type Date
	 */
	public void setPWRDate(Date pWRDate);

	/**
	 * Get title holder number.
	 * 
	 * @return String
	 */
	public String getTitleHolderNo();

	/**
	 * Set title holder number.
	 * 
	 * @param titleHolderNo of type String
	 */
	public void setTitleHolderNo(String titleHolderNo);

	/**
	 * Get title holder name.
	 * 
	 * @return String
	 */
	public String getTitleHolderName();

	/**
	 * Set title holder name.
	 * 
	 * @param titleHolderName of type String
	 */
	public void setTitleHolderName(String titleHolderName);

	/**
	 * Get beneficiary number.
	 * 
	 * @return String
	 */
	public String getBeneficiaryNo();

	/**
	 * Set beneficiary number.
	 * 
	 * @param beneficiaryNo of type String
	 */
	public void setBeneficiaryNo(String beneficiaryNo);

	/**
	 * Get beneficiary name.
	 * 
	 * @return String
	 */
	public String getBeneficiaryName();

	/**
	 * Set beneficiary name.
	 * 
	 * @param beneficiaryName of type String
	 */
	public void setBeneficiaryName(String beneficiaryName);

	/**
	 * Get warehouse.
	 * 
	 * @return IWarehouse
	 */
	public IWarehouse getWarehouse();

	/**
	 * Set warehouse.
	 * 
	 * @param warehouse of type IWarehouse
	 */
	public void setWarehouse(IWarehouse warehouse);

	/**
	 * Get warehouse store number.
	 * 
	 * @return String
	 */
	public String getWarehouseStoreNo();

	/**
	 * Set warehouse store number.
	 * 
	 * @param warehouseStoreNo of type String
	 */
	public void setWarehouseStoreNo(String warehouseStoreNo);

	/**
	 * Get warehouse address.
	 * 
	 * @return String
	 */
	public String getWarehouseAddress();

	/**
	 * Set warehouse address.
	 * 
	 * @param warehouseAddress of type String
	 */
	public void setWarehouseAddress(String warehouseAddress);

	/**
	 * Get cargo number.
	 * 
	 * @return String
	 */
	public String getCargoNo();

	/**
	 * Set cargo number.
	 * 
	 * @param cargoNo of type String
	 */
	public void setCargoNo(String cargoNo);

	/**
	 * Get product name.
	 * 
	 * @return String
	 */
	public String getProductName();

	/**
	 * Set product name.
	 * 
	 * @param productName of type String
	 */
	public void setProductName(String productName);

	/**
	 * Get product origin.
	 * 
	 * @return String
	 */
	public String getProductOrigin();

	/**
	 * Set product origin.
	 * 
	 * @param productOrigin of type String
	 */
	public void setProductOrigin(String productOrigin);

	/**
	 * Get product crop year.
	 * 
	 * @return String
	 */
	public String getProductCropYear();

	/**
	 * Set product crop year.
	 * 
	 * @param productCropYear of type String
	 */
	public void setProductCropYear(String productCropYear);

	/**
	 * Get product description.
	 * 
	 * @return String
	 */
	public String getProductDesc();

	/**
	 * Set product description.
	 * 
	 * @param productDesc of type String
	 */
	public void setProductDesc(String productDesc);

	/**
	 * Get container number.
	 * 
	 * @return String
	 */
	public String getContainerNo();

	/**
	 * Set container number.
	 * 
	 * @param containerNo of type String
	 */
	public void setContainerNo(String containerNo);

	/**
	 * Set seal number.
	 * 
	 * @return String
	 */
	public String getSealNo();

	/**
	 * Set seal number.
	 * 
	 * @param sealNo of type String
	 */
	public void setSealNo(String sealNo);

	/**
	 * Get quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getQuantity();

	/**
	 * Set quantity.
	 * 
	 * @param quantity of type Quantity
	 */
	public void setQuantity(Quantity quantity);

	/**
	 * Get ICO mark.
	 * 
	 * @return String
	 */
	public String getICOMark();

	/**
	 * Set ICO mark.
	 * 
	 * @param ICOMark of type String
	 */
	public void setICOMark(String ICOMark);

	/**
	 * Get additional marks.
	 * 
	 * @return String
	 */
	public String getAdditionalMark();

	/**
	 * Set additional marks.
	 * 
	 * @param additionalMark of type String
	 */
	public void setAdditionalMark(String additionalMark);

	/**
	 * Get vessel name.
	 * 
	 * @return String
	 */
	public String getVesselName();

	/**
	 * Set vessel name.
	 * 
	 * @param vesselName of type String
	 */
	public void setVesselName(String vesselName);

	/**
	 * Get carrier name.
	 * 
	 * @return String
	 */
	public String getCarrierName();

	/**
	 * Set carrier name.
	 * 
	 * @param carrierName of type String
	 */
	public void setCarrierName(String carrierName);

	/**
	 * Get voyage number.
	 * 
	 * @return String
	 */
	public String getVoyageNo();

	/**
	 * Set voyage number.
	 * 
	 * @param voyageNo of type String
	 */
	public void setVoyageNo(String voyageNo);

	/**
	 * Get bill of lading number.
	 * 
	 * @return String
	 */
	public String getBillLadingNo();

	/**
	 * Set bill of lading number.
	 * 
	 * @param billLadingNo of type String
	 */
	public void setBillLadingNo(String billLadingNo);

	/**
	 * Get bill of lading date.
	 * 
	 * @return Date
	 */
	public Date getBillLadingDate();

	/**
	 * Set bill of lading date.
	 * 
	 * @param billLadingDate of type Date
	 */
	public void setBillLadingDate(Date billLadingDate);

	/**
	 * Get date of assignment.
	 * 
	 * @return Date
	 */
	public Date getAssignmentDate();

	/**
	 * Set date of assignment.
	 * 
	 * @param assignmentDate of type Date
	 */
	public void setAssignmentDate(Date assignmentDate);

	/**
	 * Get date of bank release.
	 * 
	 * @return Date
	 */
	public Date getBankReleaseDate();

	/**
	 * Set date of bank release.
	 * 
	 * @param bankReleaseDate of type Date
	 */
	public void setBankReleaseDate(Date bankReleaseDate);

	/**
	 * Get delivery order.
	 * 
	 * @return String
	 */
	public String getDeliveryOrder();

	/**
	 * Set delivery order.
	 * 
	 * @param deliveryOrder of type String
	 */
	public void setDeliveryOrder(String deliveryOrder);

	/**
	 * Get free type exipry date.
	 * 
	 * @return Date
	 */
	public Date getFreeTimeExpDate();

	/**
	 * Set free time expiry date.
	 * 
	 * @param freeTimeExpDate of type Date
	 */
	public void setFreeTimeExpDate(Date freeTimeExpDate);

	/**
	 * Get grade certificate.
	 * 
	 * @return String
	 */
	public String getGradeCert();

	/**
	 * Set grade certificate.
	 * 
	 * @param gradeCert of type String
	 */
	public void setGradeCert(String gradeCert);

	/**
	 * Get weight note.
	 * 
	 * @return String
	 */
	public String getWeightNote();

	/**
	 * Set weight note.
	 * 
	 * @param weightNote of type String
	 */
	public void setWeightNote(String weightNote);

	/**
	 * Get FDA/customs entry number.
	 * 
	 * @return String
	 */
	public String getCustomsEntryNo();

	/**
	 * Set FDA/customs entry number.
	 * 
	 * @param customsEntryNo of type String
	 */
	public void setCustomsEntryNo(String customsEntryNo);

	/**
	 * Get warehouse receipt remarks.
	 * 
	 * @return String
	 */
	public String getReceiptRemarks();

	/**
	 * Set warehouse receipt remarks.
	 * 
	 * @param receiptRemarks of type String
	 */
	public void setReceiptRemarks(String receiptRemarks);

	/**
	 * Get common reference id for actual and staging data.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Set common reference id for actual and staging data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Get CMS business status.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set CMS business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Get title holder changed date.
	 * 
	 * @return Date
	 */
	public Date getTitleHolderChangedDate();

	/**
	 * Set title holder changed date.
	 * 
	 * @param titleHolderChangedDate of type Date
	 */
	public void setTitleHolderChangedDate(Date titleHolderChangedDate);

	/**
	 * Get beneficiary changed date.
	 * 
	 * @return Date
	 */
	public Date getBeneficiaryChangedDate();

	/**
	 * Set beneficiary changed date.
	 * 
	 * @param beneficiaryChangedDate of type Date
	 */
	public void setBeneficiaryChangedDate(Date beneficiaryChangedDate);
}