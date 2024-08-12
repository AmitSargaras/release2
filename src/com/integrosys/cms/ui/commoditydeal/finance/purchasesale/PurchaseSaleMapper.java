/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/purchasesale/PurchaseSaleMapper.java,v 1.24 2005/09/23 02:38:22 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.purchasesale;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityConversionRate;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.ISalesDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.OBPurchaseAndSalesDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.OBPurchaseDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.OBSalesDetails;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2005/09/23 02:38:22 $ Tag: $Name: $
 */

public class PurchaseSaleMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		PurchaseSaleForm aForm = (PurchaseSaleForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		ICommodityDeal deal = trxValue.getStagingCommodityDeal();
		IProfile profile = (IProfile) inputs.get("profileService");

		OBPurchaseAndSalesDetails obToChange = null;
		try {
			obToChange = (OBPurchaseAndSalesDetails) AccessorUtil.deepClone(deal.getPurchaseAndSalesDetails());
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}
		if (obToChange == null) {
			obToChange = new OBPurchaseAndSalesDetails();
		}
		OBPurchaseDetails obChangePurchase = (OBPurchaseDetails) obToChange.getPurchaseDetails();
		OBSalesDetails obChangeSales = (OBSalesDetails) obToChange.getSalesDetails();
		if (obChangePurchase == null) {
			obChangePurchase = new OBPurchaseDetails();
		}
		if (obChangeSales == null) {
			obChangeSales = new OBSalesDetails();
		}
		// mapping purchase information
		if (isEmptyOrNull(aForm.getPurSupplier())) {
			// obChangePurchase.setSupplierID(ICMSConstant.LONG_INVALID_VALUE);
			obChangePurchase.setSupplier(null);
		}
		else {
			if (!aForm.getPurSupplier().equals(ICMSConstant.NOT_AVAILABLE_VALUE)) {
				// obChangePurchase.setSupplierID(Long.parseLong(aForm.
				// getPurSupplier()));
				if (((obChangePurchase.getSupplier() != null) && !String.valueOf(
						obChangePurchase.getSupplier().getSupplierID()).equals(aForm.getPurSupplier()))
						|| (obChangePurchase.getSupplier() == null)) {
					boolean found = false;
					if ((profile != null) && (profile.getSuppliers() != null)) {
						ISupplier[] supplierList = profile.getSuppliers();
						for (int i = 0; !found && (i < supplierList.length); i++) {
							if (String.valueOf(supplierList[i].getSupplierID()).equals(aForm.getPurSupplier())) {
								obChangePurchase.setSupplier(supplierList[i]);
								found = true;
							}
						}
					}
				}
			}
			else {
				obChangePurchase.setSupplier(null);
			}
		}
		obChangePurchase.setOtherSupplierName(aForm.getPurSupplierOth());
		if (isEmptyOrNull(aForm.getPurQty())) {
			obChangePurchase.setQuantity(null);
			;
		}
		else {
			try {
				BigDecimal qtyVal = UIUtil.mapStringToBigDecimal(aForm.getPurQty());
				if (deal.getContractQuantity() != null) {
					Quantity qty = new Quantity(qtyVal, deal.getContractQuantity().getUnitofMeasure());
					obChangePurchase.setQuantity(qty);
				}
				else {
					Quantity qty = new Quantity(qtyVal, null);
					obChangePurchase.setQuantity(qty);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		if (isEmptyOrNull(aForm.getPurUnitPriceAmt())) {
			obChangePurchase.setUnitPrice(null);
		}
		else {
			try {
				obChangePurchase.setUnitPrice(UIUtil.convertToAmount(locale, aForm.getPurUnitPriceCcy(), aForm
						.getPurUnitPriceAmt()));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		obChangePurchase.setShipDate(compareDate(locale, obChangePurchase.getShipDate(), aForm.getPurShipmentDate()));
		obChangePurchase.setExpiryDate(compareDate(locale, obChangePurchase.getExpiryDate(), aForm.getPurExpiryDate()));
		obChangePurchase.setShipmentSource(aForm.getPurShipmentFrom());
		obChangePurchase.setShipmentDestination(aForm.getPurShipmentTo());
		obChangePurchase.setTransportationDocumentNo(aForm.getPurTransportDoc());
		obChangePurchase.setPaymentMode(aForm.getPurPayment());
		obChangePurchase.setBankName(aForm.getPurCorrBank());
		obChangePurchase.setIsClaimAllowed(Boolean.valueOf(aForm.getPurIsTTClaim()).booleanValue());
		if (isEmptyOrNull(aForm.getPurTTClaimDay())) {
			obChangePurchase.setNoDaysClaimed(0);
		}
		else {
			obChangePurchase.setNoDaysClaimed(Integer.parseInt(aForm.getPurTTClaimDay()));
		}
		obChangePurchase.setReferenceNo(aForm.getPurRefNo());
		obChangePurchase.setRemarks(aForm.getPurRemarks());

		obToChange.setPurchaseDetails(obChangePurchase);

		// mapping sales information
		if (isEmptyOrNull(aForm.getSalesBuyer())) {
			obChangeSales.setBuyer(null);
		}
		else if (!aForm.getSalesBuyer().equals(ICMSConstant.NOT_AVAILABLE_VALUE)) {
			if ((obChangeSales.getBuyer() == null)
					|| ((obChangeSales.getBuyer() != null) && !String.valueOf(obChangeSales.getBuyer().getBuyerID())
							.equals(aForm.getSalesBuyer()))) {
				boolean found = false;
				if ((profile != null) && (profile.getBuyers() != null)) {
					IBuyer[] buyerList = profile.getBuyers();
					for (int i = 0; !found && (i < buyerList.length); i++) {
						if (String.valueOf(buyerList[i].getBuyerID()).equals(aForm.getSalesBuyer())) {
							obChangeSales.setBuyer(buyerList[i]);
							found = true;
						}
					}
				}
			}
		}
		else {
			obChangeSales.setBuyer(null);
		}
		obChangeSales.setOtherBuyerName(aForm.getSalesBuyerOth());
		if (isEmptyOrNull(aForm.getSalesQty())) {
			obChangeSales.setQuantity(null);
			;
		}
		else {
			try {
				BigDecimal qtyVal = UIUtil.mapStringToBigDecimal(aForm.getSalesQty());
				if (deal.getContractQuantity() != null) {
					Quantity qty = new Quantity(qtyVal, deal.getContractQuantity().getUnitofMeasure());
					obChangeSales.setQuantity(qty);
				}
				else {
					Quantity qty = new Quantity(qtyVal, null);
					obChangeSales.setQuantity(qty);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		if (isEmptyOrNull(aForm.getSalesUnitPriceAmt())) {
			obChangeSales.setUnitPrice(null);
		}
		else {
			try {
				obChangeSales.setUnitPrice(UIUtil.convertToAmount(locale, aForm.getSalesUnitPriceCcy(), aForm
						.getSalesUnitPriceAmt()));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		obChangeSales.setShipDate(compareDate(locale, obChangeSales.getShipDate(), aForm.getSalesShipmentDate()));
		obChangeSales.setExpiryDate(compareDate(locale, obChangeSales.getExpiryDate(), aForm.getSalesExpiryDate()));
		obChangeSales.setShipmentSource(aForm.getSalesShipmentFrom());
		obChangeSales.setShipmentDestination(aForm.getSalesShipmentTo());
		obChangeSales.setTransportationDocumentNo(aForm.getSalesTransportDoc());
		obChangeSales.setPaymentMode(aForm.getSalesPayment());
		obChangeSales.setBankName(aForm.getSalesCorrBank());
		obChangeSales.setIsClaimAllowed(Boolean.valueOf(aForm.getSalesIsTTClaim()).booleanValue());
		if (isEmptyOrNull(aForm.getSalesTTClaimDay())) {
			obChangeSales.setNoDaysClaimed(0);
		}
		else {
			obChangeSales.setNoDaysClaimed(Integer.parseInt(aForm.getSalesTTClaimDay()));
		}
		obChangeSales.setReferenceNo(aForm.getSalesRefNo());
		obChangeSales.setRemarks(aForm.getSalesRemarks());

		obToChange.setSalesDetails(obChangeSales);

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		PurchaseSaleForm aForm = (PurchaseSaleForm) cForm;
		DefaultLogger.debug(this, "=========== purchase sale mapper == mapOBToForm");
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String from_event = (String) inputs.get("from_event");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		IProfile profile = (IProfile) inputs.get("profileService");
		// DefaultLogger.debug(this, "================= profile: "+profile);

		IPurchaseAndSalesDetails purchaseSales = (IPurchaseAndSalesDetails) obj;
		IPurchaseDetails purchase = purchaseSales.getPurchaseDetails();
		ISalesDetails sales = purchaseSales.getSalesDetails();
		ICommodityDeal deal;
		if (from_event.equals(PurchaseSaleAction.EVENT_READ)) {
			deal = trxValue.getCommodityDeal();
		}
		else {
			deal = trxValue.getStagingCommodityDeal();
		}
		String goodsDesc = "";
		if (profile != null) {
			CommodityCategoryList categoryList = CommodityCategoryList.getInstance();
			goodsDesc = categoryList.getCommProductItem(profile.getCategory(), profile.getProductType());
			goodsDesc = goodsDesc + "/" + profile.getProductSubType();
			aForm.setPurDescGood(goodsDesc);
			aForm.setSalesDescGood(goodsDesc);
		}

		if (aForm.getEvent().equals(PurchaseSaleAction.EVENT_READ)) {
			if ((purchase != null) && (purchase.getSupplier() != null)) {
				aForm.setPurSupplier(purchase.getSupplier().getName());
			}
			if ((sales != null) && (sales.getBuyer() != null)) {
				aForm.setSalesBuyer(sales.getBuyer().getName());
			}
		}
		else {
			if ((purchase != null) && (purchase.getSupplier() != null)) {
				aForm.setPurSupplier(String.valueOf(purchase.getSupplier().getSupplierID()));
			}
			if ((sales != null) && (sales.getBuyer() != null)) {
				aForm.setSalesBuyer(String.valueOf(sales.getBuyer().getBuyerID()));
			}
		}

		Quantity qty = deal.getContractQuantity();
		QuantityConversionRate marketRate = deal.getContractMarketUOMConversionRate();
		BigDecimal totalValue = null;
		Amount unitPrice = null;
		if (qty != null) {
			if (qty.getUnitofMeasure() != null) {
				aForm.setSalesQtyUOM(qty.getUnitofMeasure().getLabel());
			}
		}

		// mapping purchase information
		if (purchase != null) {
			if (!isEmptyOrNull(purchase.getOtherSupplierName())) {
				aForm.setPurSupplier(ICMSConstant.NOT_AVAILABLE_VALUE);
			}
			aForm.setPurSupplierOth(purchase.getOtherSupplierName());

			qty = purchase.getQuantity();
			totalValue = null;
			if (qty != null) {
				try {
					aForm.setPurQty(UIUtil.formatNumber(qty.getQuantity(), 4, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
				if (marketRate != null) {
					try {
						Quantity purQty = (Quantity) marketRate.convert(qty);
						if (purQty != null) {
							totalValue = purQty.getQuantity();
						}
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
			}
			unitPrice = purchase.getUnitPrice();
			if (unitPrice != null) {
				if (unitPrice.getAmount() >= 0) {
					aForm.setPurUnitPriceCcy(unitPrice.getCurrencyCode());
					try {
						aForm.setPurUnitPriceAmt(UIUtil.formatNumber(unitPrice.getAmountAsBigDecimal(), 6, locale));
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}

					if ((totalValue != null) && (unitPrice.getAmountAsBigDecimal() != null)) {
						totalValue = totalValue.multiply(unitPrice.getAmountAsBigDecimal());
					}
					if (totalValue != null) {
						try {
							aForm.setPurTotalVal(unitPrice.getCurrencyCode() + " "
									+ UIUtil.formatNumber(totalValue, 6, locale));
						}
						catch (Exception e) {
							throw new MapperException(e.getMessage());
						}
					}
					else {
						aForm.setPurTotalVal("-");
					}
				}
				else {
					aForm.setPurTotalVal("");
				}
			}
			else {
				aForm.setPurTotalVal("");
			}

			aForm.setPurShipmentDate(DateUtil.formatDate(locale, purchase.getShipDate()));
			aForm.setPurExpiryDate(DateUtil.formatDate(locale, purchase.getExpiryDate()));
			aForm.setPurShipmentFrom(purchase.getShipmentSource());
			aForm.setPurShipmentTo(purchase.getShipmentDestination());
			aForm.setPurTransportDoc(purchase.getTransportationDocumentNo());
			aForm.setPurPayment(purchase.getPaymentMode());
			aForm.setPurCorrBank(purchase.getBankName());
			aForm.setPurIsTTClaim(String.valueOf(purchase.getIsClaimAllowed()));
			if (purchase.getIsClaimAllowed()) {
				aForm.setPurTTClaimDay(String.valueOf(purchase.getNoDaysClaimed()));
			}
			aForm.setPurRefNo(purchase.getReferenceNo());
			aForm.setPurRemarks(purchase.getRemarks());
		}

		// mapping sales information
		if (sales != null) {
			// DefaultLogger.debug(this,
			// "<<<<<<<< other buyer name: "+sales.getOtherBuyerName() );
			if (!isEmptyOrNull(sales.getOtherBuyerName())) {
				aForm.setSalesBuyer(ICMSConstant.NOT_AVAILABLE_VALUE);
			}
			aForm.setSalesBuyerOth(sales.getOtherBuyerName());
			qty = sales.getQuantity();
			totalValue = null;
			if (qty != null) {
				try {
					aForm.setSalesQty(UIUtil.formatNumber(qty.getQuantity(), 4, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
				if (marketRate != null) {
					try {
						Quantity salesQty = (Quantity) marketRate.convert(qty);
						if (salesQty != null) {
							totalValue = salesQty.getQuantity();
						}
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
			}
			unitPrice = sales.getUnitPrice();
			if (unitPrice != null) {
				if (unitPrice.getAmount() >= 0) {
					aForm.setSalesUnitPriceCcy(unitPrice.getCurrencyCode());
					try {
						aForm.setSalesUnitPriceAmt(UIUtil.formatNumber(unitPrice.getAmountAsBigDecimal(), 6, locale));
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}

					if ((totalValue != null) && (unitPrice.getAmountAsBigDecimal() != null)) {
						totalValue = totalValue.multiply(unitPrice.getAmountAsBigDecimal());
					}
					if (totalValue != null) {
						try {
							aForm.setSalesTotalVal(unitPrice.getCurrencyCode() + " "
									+ UIUtil.formatNumber(totalValue, 6, locale));
						}
						catch (Exception e) {
							throw new MapperException(e.getMessage());
						}
					}
					else {
						aForm.setSalesTotalVal("-");
					}
				}
				else {
					aForm.setSalesTotalVal("");
				}
			}
			else {
				aForm.setSalesTotalVal("");
			}
			aForm.setSalesShipmentDate(DateUtil.formatDate(locale, sales.getShipDate()));
			aForm.setSalesExpiryDate(DateUtil.formatDate(locale, sales.getExpiryDate()));
			aForm.setSalesShipmentFrom(sales.getShipmentSource());
			aForm.setSalesShipmentTo(sales.getShipmentDestination());
			aForm.setSalesTransportDoc(sales.getTransportationDocumentNo());
			aForm.setSalesPayment(sales.getPaymentMode());
			aForm.setSalesCorrBank(sales.getBankName());
			aForm.setSalesIsTTClaim(String.valueOf(sales.getIsClaimAllowed()));
			if (sales.getIsClaimAllowed()) {
				aForm.setSalesTTClaimDay(String.valueOf(sales.getNoDaysClaimed()));
			}
			aForm.setSalesRefNo(sales.getReferenceNo());
			aForm.setSalesRemarks(sales.getRemarks());
		}
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE }, });
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
