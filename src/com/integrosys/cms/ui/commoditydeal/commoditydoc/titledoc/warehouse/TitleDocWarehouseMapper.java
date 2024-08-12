/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/warehouse/TitleDocWarehouseMapper.java,v 1.15 2004/08/07 05:26:26 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc.warehouse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityConversionRate;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2004/08/07 05:26:26 $ Tag: $Name: $
 */

public class TitleDocWarehouseMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		TitleDocWarehouseForm aForm = (TitleDocWarehouseForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		int index = Integer.parseInt((String) inputs.get("warehouseIndexID"));
		ICommodityTitleDocument titleDocObj = (ICommodityTitleDocument) inputs.get("serviceTitleDocObj");
		IWarehouseReceipt[] warehouseRecArr = titleDocObj.getWarehouseReceipts();
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();

		OBWarehouseReceipt obToChange = null;
		if (index == -1) {
			obToChange = new OBWarehouseReceipt();
		}
		else {
			try {
				obToChange = (OBWarehouseReceipt) AccessorUtil.deepClone(warehouseRecArr[index]);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		// Electronic negotiable warehouse (ERW) Receipt NUmber
		if (isEmptyOrNull(aForm.getWarehouseName())) {
			obToChange.setWarehouse(null);
		}
		else {
			ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
			try {
				IWarehouse warehouse = proxy.getWarehouseByWarehouseID(Long.parseLong(aForm.getWarehouseName()));
				obToChange.setWarehouse(warehouse);
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		// Issued On
		obToChange.setIssueDate(compareDate(locale, obToChange.getIssueDate(), aForm.getIssuedOn()));
		obToChange.setLastUpdateDate(compareDate(locale, obToChange.getLastUpdateDate(), aForm.getLastModified()));
		obToChange.setReceiptStatus(aForm.getStatus());
		obToChange.setExchangeNo(aForm.getExchangeID());
		obToChange.setOrigReceiptNo(aForm.getOrigPaperRecNo());
		obToChange.setEWRDate(compareDate(locale, obToChange.getEWRDate(), aForm.getConvertEWRDate()));
		if (isEmptyOrNull(aForm.getPaperRecNo())) {
			obToChange.setPaperReceiptNo(null);
		}
		else {
			obToChange.setPaperReceiptNo(aForm.getPaperRecNo());
		}
		obToChange.setPWRDate(compareDate(locale, obToChange.getPWRDate(), aForm.getConvertPWRDate()));
		if (isEmptyOrNull(aForm.getTitleHolder())) {
			obToChange.setTitleHolderName(null);
		}
		else {
			obToChange.setTitleHolderName(aForm.getTitleHolder());
		}
		if (isEmptyOrNull(aForm.getTitleHolderNo())) {
			obToChange.setTitleHolderNo(null);
		}
		else {
			obToChange.setTitleHolderNo(aForm.getTitleHolderNo());
		}

		// Beneficiary
		obToChange.setBeneficiaryName(aForm.getBeneficiary());
		obToChange.setBeneficiaryNo(aForm.getBeneficiaryNo());
		obToChange.setTitleHolderChangedDate(compareDate(locale, obToChange.getTitleHolderChangedDate(), aForm
				.getTitleHolderChangedDate()));
		obToChange.setBeneficiaryChangedDate(compareDate(locale, obToChange.getBeneficiaryChangedDate(), aForm
				.getBeneficiaryChangedDate()));
		obToChange.setWarehouseStoreNo(aForm.getWarehouseLocStoreNo());
		obToChange.setCargoNo(aForm.getCargoNo());

		// Product
		obToChange.setProductName(aForm.getProduct());

		// Origin
		obToChange.setProductOrigin(aForm.getOrigin());
		obToChange.setProductCropYear(aForm.getCropYear());
		/*
		 * if (isEmptyOrNull(aForm.getCropYear())) {
		 * obToChange.setProductCropYear(ICMSConstant.LONG_INVALID_VALUE); }
		 * else { long cropYear = ICMSConstant.LONG_INVALID_VALUE; try {
		 * cropYear = (long)MapperUtil.mapStringToDouble(aForm.getCropYear(),
		 * locale); } catch (Exception e) { throw new MapperException
		 * (e.getMessage()); } obToChange.setProductCropYear(cropYear); }
		 */
		obToChange.setProductDesc(aForm.getDescription());

		// Container Number
		obToChange.setContainerNo(aForm.getContainerNo());
		obToChange.setSealNo(aForm.getSealNo());

		// Quantity & Quantity Units
		if (isEmptyOrNull(aForm.getQuantity())) {
			obToChange.setQuantity(null);
		}
		else {
			try {
				BigDecimal qty = UIUtil.mapStringToBigDecimal(aForm.getQuantity());
				if (dealObj.getContractQuantity() != null) {
					obToChange.setQuantity(new Quantity(qty, dealObj.getContractQuantity().getUnitofMeasure()));
				}
				else {
					obToChange.setQuantity(new Quantity(qty, null));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		// ICO Mark
		obToChange.setICOMark(aForm.getIcoMark());
		obToChange.setAdditionalMark(aForm.getAdditionalMark());

		// Vessel Name
		obToChange.setVesselName(aForm.getVesselName());
		obToChange.setCarrierName(aForm.getCarrier());
		obToChange.setVoyageNo(aForm.getVoyageNo());
		obToChange.setBillLadingNo(aForm.getBillLadingNo());
		obToChange.setBillLadingDate(compareDate(locale, obToChange.getBillLadingDate(), aForm.getBillLadingDate()));
		obToChange.setAssignmentDate(compareDate(locale, obToChange.getAssignmentDate(), aForm.getDateAssignment()));
		obToChange.setBankReleaseDate(compareDate(locale, obToChange.getBankReleaseDate(), aForm.getDateBankRelease()));
		obToChange.setDeliveryOrder(aForm.getDeliverOrder());
		obToChange.setFreeTimeExpDate(compareDate(locale, obToChange.getFreeTimeExpDate(), aForm.getFreeTimeExpiry()));
		obToChange.setGradeCert(aForm.getGradeCert());
		obToChange.setWeightNote(aForm.getWeightNote());
		obToChange.setCustomsEntryNo(aForm.getFdaCustomerEntryNo());

		// Remarks
		obToChange.setReceiptRemarks(aForm.getWarehouseRemarks());

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		TitleDocWarehouseForm aForm = (TitleDocWarehouseForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IWarehouseReceipt warehouseRec = (IWarehouseReceipt) obj;
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		String from_event = (String) inputs.get("from_event");

		ICommodityDeal dealObj;
		if (from_event.equals(EVENT_READ)) {
			dealObj = trxValue.getCommodityDeal();
		}
		else {
			dealObj = trxValue.getStagingCommodityDeal();
		}

		// Electronic Negotiable Warehouse (ERW) receipt Number
		if (warehouseRec.getWarehouse() != null) {
			IWarehouse warehouse = warehouseRec.getWarehouse();
			if (aForm.getEvent().equals(EVENT_READ)) {
				aForm.setWarehouseName(warehouse.getName());
			}
			else {
				aForm.setWarehouseName(String.valueOf(warehouse.getWarehouseID()));
			}
		}

		// Issued On
		aForm.setIssuedOn(DateUtil.formatDate(locale, warehouseRec.getIssueDate()));
		aForm.setLastModified(DateUtil.formatDate(locale, warehouseRec.getLastUpdateDate()));
		aForm.setStatus(warehouseRec.getReceiptStatus());
		aForm.setExchangeID(warehouseRec.getExchangeNo());
		aForm.setOrigPaperRecNo(warehouseRec.getOrigReceiptNo());
		aForm.setConvertEWRDate(DateUtil.formatDate(locale, warehouseRec.getEWRDate()));
		aForm.setPaperRecNo(warehouseRec.getPaperReceiptNo());
		aForm.setConvertPWRDate(DateUtil.formatDate(locale, warehouseRec.getPWRDate()));
		aForm.setTitleHolder(warehouseRec.getTitleHolderName());
		aForm.setTitleHolderNo(warehouseRec.getTitleHolderNo());

		// Beneficiary
		aForm.setBeneficiary(warehouseRec.getBeneficiaryName());
		aForm.setBeneficiaryNo(warehouseRec.getBeneficiaryNo());
		aForm.setTitleHolderChangedDate(DateUtil.formatDate(locale, warehouseRec.getTitleHolderChangedDate()));
		aForm.setBeneficiaryChangedDate(DateUtil.formatDate(locale, warehouseRec.getBeneficiaryChangedDate()));
		aForm.setWarehouseLocStoreNo(warehouseRec.getWarehouseStoreNo());
		aForm.setCargoNo(warehouseRec.getCargoNo());

		// Product
		aForm.setProduct(warehouseRec.getProductName());

		// Origin
		aForm.setOrigin(warehouseRec.getProductOrigin());
		aForm.setCropYear(warehouseRec.getProductCropYear());
		/*
		 * if (warehouseRec.getProductCropYear() >= 0) {
		 * aForm.setCropYear(String.valueOf(warehouseRec.getProductCropYear()));
		 * }
		 */
		aForm.setDescription(warehouseRec.getProductDesc());

		// Container Number
		aForm.setContainerNo(warehouseRec.getContainerNo());
		aForm.setSealNo(warehouseRec.getSealNo());

		// Quantity and Quantity Unit
		Quantity qty = warehouseRec.getQuantity();
		Quantity contractQty = dealObj.getContractQuantity();
		if (qty != null) {
			try {
				aForm.setQuantity(UIUtil.formatNumber(qty.getQuantity(), 4, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		if (qty != null) {
			// qty = new Quantity(qty.getQuantity(),
			// contractQty.getUnitofMeasure());
			QuantityConversionRate marketRate = dealObj.getContractMarketUOMConversionRate();
			if (marketRate != null) {
				try {
					Quantity marketUnit = (Quantity) marketRate.convert(qty);
					if (marketUnit != null) {
						aForm.setQuantityUnit(UIUtil.formatNumber(marketUnit.getQuantity(), 4, locale) + " "
								+ marketUnit.getUnitofMeasure().getLabel());
					}
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}

		if (contractQty != null) {
			UOMWrapper contractUOM = contractQty.getUnitofMeasure();
			if (contractUOM != null) {
				aForm.setPackagingType(contractUOM.getLabel());
			}
		}

		// ICO Mark
		aForm.setIcoMark(warehouseRec.getICOMark());
		aForm.setAdditionalMark(warehouseRec.getAdditionalMark());

		// Vessel Name
		aForm.setVesselName(warehouseRec.getVesselName());
		aForm.setCarrier(warehouseRec.getCarrierName());
		aForm.setVoyageNo(warehouseRec.getVoyageNo());
		aForm.setBillLadingNo(warehouseRec.getBillLadingNo());
		aForm.setBillLadingDate(DateUtil.formatDate(locale, warehouseRec.getBillLadingDate()));
		aForm.setDateAssignment(DateUtil.formatDate(locale, warehouseRec.getAssignmentDate()));
		aForm.setDateBankRelease(DateUtil.formatDate(locale, warehouseRec.getBankReleaseDate()));
		aForm.setDeliverOrder(warehouseRec.getDeliveryOrder());
		aForm.setFreeTimeExpiry(DateUtil.formatDate(locale, warehouseRec.getFreeTimeExpDate()));
		aForm.setGradeCert(warehouseRec.getGradeCert());
		aForm.setWeightNote(warehouseRec.getWeightNote());
		aForm.setFdaCustomerEntryNo(warehouseRec.getCustomsEntryNo());

		// Remarks
		aForm.setWarehouseRemarks(warehouseRec.getReceiptRemarks());

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "warehouseIndexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "serviceTitleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "from_event", "java.lang.String", SERVICE_SCOPE }, });
	}

	private static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}

		return returnDate;
	}
}
