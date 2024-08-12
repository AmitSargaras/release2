/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/dealinfo/DealInfoMapper.java,v 1.29 2006/07/28 13:28:43 hmbao Exp $
 */
package com.integrosys.cms.ui.commoditydeal.dealinfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.commodity.common.DifferentialSign;
import com.integrosys.cms.app.commodity.common.PriceDifferential;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityConversionRate;
import com.integrosys.cms.app.commodity.common.QuantityDifferential;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.PriceType;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.OBPurchaseAndSalesDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.OBPurchaseDetails;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.29 $
 * @since $Date: 2006/07/28 13:28:43 $ Tag: $Name: $
 */

public class DealInfoMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DealInfoForm aForm = (DealInfoForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		HashMap uomMap = (HashMap) inputs.get("uomMap");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		OBCommodityDeal dealObj = (OBCommodityDeal) trxValue.getStagingCommodityDeal();

		if (isEmptyOrNull(aForm.getConCommProductSubType())) {
			dealObj.setContractProfileID(ICMSConstant.LONG_INVALID_VALUE);
		}
		else {
			dealObj.setContractProfileID(Long.parseLong(aForm.getConCommProductSubType()));
		}

		boolean isPriceTypeChanged = false;
		if ((aForm.getConCommPriceTypeChange() != null)
				&& aForm.getConCommPriceTypeChange().equals(ICMSConstant.TRUE_VALUE)) {
			isPriceTypeChanged = true;
		}

		String conCmdtP = aForm.getConCommPriceType();
		DefaultLogger.debug(this, " - ConCommPriceType : " + conCmdtP);
		if (isEmptyOrNull(conCmdtP)) {
			dealObj.setContractPriceType(null);
		}
		else {
			dealObj.setContractPriceType(PriceType.valueOf(conCmdtP));
		}

		dealObj.setContractRIC(aForm.getConRIC());
		dealObj.setContractQuantity(setQuantity(aForm.getConQtyVolume(), aForm.getConQtyUOM(), uomMap, locale));
		if (dealObj.getContractQuantity() != null) {
			if (dealObj.getContractQuantity().getUnitofMeasure() != null) {
				dealObj.setContractMarketUOMConversionRate(dealObj.getContractQuantity().getUnitofMeasure()
						.getMarketUOMConversionRate());
				dealObj.setContractMetricUOMConversionRate(dealObj.getContractQuantity().getUnitofMeasure()
						.getMetricUOMConversionRate());
			}
			IPurchaseAndSalesDetails purchaseSale = new OBPurchaseAndSalesDetails();
			IPurchaseDetails purchase = new OBPurchaseDetails();
			if (dealObj.getPurchaseAndSalesDetails() != null) {
				purchaseSale = dealObj.getPurchaseAndSalesDetails();
				if (purchaseSale.getPurchaseDetails() != null) {
					purchase = purchaseSale.getPurchaseDetails();
				}
			}
			if (purchase.getQuantity() == null) {
				purchase.setQuantity(dealObj.getContractQuantity());
			}
			purchaseSale.setPurchaseDetails(purchase);
			dealObj.setPurchaseAndSalesDetails(purchaseSale);
		}

		Quantity qty = setQuantity(aForm.getConQtyDiffValue(), aForm.getConQtyDiffUOM(), uomMap, locale);
		DifferentialSign sign = null;
		if (!isEmptyOrNull(aForm.getConQtyDiffPlusmn()) && (qty != null)) {
			sign = DifferentialSign.valueOf(aForm.getConQtyDiffPlusmn());
			dealObj.setContractQuantityDifferential(new QuantityDifferential(qty, sign));
		}

		Amount amt = null;
		if (isEmptyOrNull(aForm.getConBuyerSellerAgreeDiff())) {
			amt = null;
		}
		else {
			try {
				amt = UIUtil.convertToAmount(locale, aForm.getConContractedPriceCcy(), aForm
						.getConBuyerSellerAgreeDiff());
				/*
				 * double amtVal =
				 * MapperUtil.mapStringToDouble(aForm.getConBuyerSellerAgreeDiff
				 * (), locale); amt = new Amount(amtVal,
				 * aForm.getConContractedPriceCcy());
				 */
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		if (!isEmptyOrNull(aForm.getConPriceDiffPlusmn()) || !isEmptyOrNull(aForm.getConBuyerSellerAgreeDiff())) {
			dealObj.setContractPriceDifferential(new PriceDifferential(amt, aForm.getConPriceDiffPlusmn()));
		}

		dealObj.setContractPrice(UIUtil.convertToAmount(locale, aForm.getConContractedPriceCcy(), aForm
				.getConContractedPriceAmt()));
		if (dealObj.getContractPrice() != null) {
			IPurchaseAndSalesDetails purchaseSale = new OBPurchaseAndSalesDetails();
			IPurchaseDetails purchase = new OBPurchaseDetails();
			if (dealObj.getPurchaseAndSalesDetails() != null) {
				purchaseSale = dealObj.getPurchaseAndSalesDetails();
				if (purchaseSale.getPurchaseDetails() != null) {
					purchase = purchaseSale.getPurchaseDetails();
				}
			}
			if (purchase.getUnitPrice() == null) {
				purchase.setUnitPrice(dealObj.getContractPrice());
			}
			purchaseSale.setPurchaseDetails(purchase);
			dealObj.setPurchaseAndSalesDetails(purchaseSale);
		}
		if (!isEmptyOrNull(aForm.getActQtyVolume())) {
			dealObj.setActualQuantity(setQuantity(aForm.getActQtyVolume(), aForm.getActQtyUOM(), uomMap, locale));
		}
		else {
			dealObj.setActualQuantity(null);
		}

		if (isEmptyOrNull(aForm.getConCommPriceType())) {
			dealObj.setActualCommonDifferential(null);
			dealObj.setActualEODCustomerDifferential(null);
			dealObj.setActualMarketPriceDate(null);
			dealObj.setActualPrice(null);
		}
		else {
			DefaultLogger.debug(this, " - is price type changed: " + isPriceTypeChanged);
			if (isPriceTypeChanged) {
				dealObj.setActualCommonDifferential(null);
				dealObj.setActualEODCustomerDifferential(null);
				dealObj.setActualMarketPriceDate(null);
				dealObj.setActualPrice(null);
			}
			else {
				if (conCmdtP.equals(PriceType.EOD_PRICE.getName())) {
					DefaultLogger.debug(this, " - EOD");
					String currencyCode = null;
					if (dealObj.getActualPrice() != null) {
						currencyCode = dealObj.getActualPrice().getCurrencyCode();
					}
					PriceDifferential priceDiff = setPriceDifferential(aForm.getActEODCustDiffSign(), aForm
							.getActEODCustDiff(), currencyCode, locale);
					dealObj.setActualEODCustomerDifferential(priceDiff);
					priceDiff = setPriceDifferential(aForm.getActEODCommDiffSign(), aForm.getActEODCommDiff(),
							currencyCode, locale);
					dealObj.setActualCommonDifferential(priceDiff);
				}
				else if (conCmdtP.equals(PriceType.MANUAL_EOD_PRICE.getName())) {
					DefaultLogger.debug(this, " - manual EOD");
					dealObj.setActualMarketPriceDate(compareDate(locale, dealObj.getActualMarketPriceDate(), aForm
							.getActEODDate()));
					String currencyCode = aForm.getActEODMarketPriceCcy();
					dealObj.setActualPrice(UIUtil.convertToAmount(locale, aForm.getActEODMarketPriceCcy(), aForm
							.getActEODMarketPriceAmt()));
					PriceDifferential priceDiff = setPriceDifferential(aForm.getActEODCustDiffSign(), aForm
							.getActEODCustDiff(), currencyCode, locale);
					dealObj.setActualEODCustomerDifferential(priceDiff);

					priceDiff = setPriceDifferential(aForm.getActEODCommDiffSign(), aForm.getActEODCommDiff(),
							currencyCode, locale);
					dealObj.setActualCommonDifferential(priceDiff);
				}
				else if (conCmdtP.equals(PriceType.FLOATING_FUTURES_PRICE.getName())) {
					dealObj.setActualCommonDifferential(dealObj.getContractPriceDifferential());
					dealObj.setActualEODCustomerDifferential(null);
					DefaultLogger.debug(this, " - floating futures");
				}
				else if (conCmdtP.equals(PriceType.MANUAL_FLOATING_FUTURES_PRICE.getName())) {
					DefaultLogger.debug(this, " - manual floating futures");
					dealObj.setActualMarketPriceDate(compareDate(locale, dealObj.getActualMarketPriceDate(), aForm
							.getActFloatDate()));
					dealObj.setActualPrice(UIUtil.convertToAmount(locale, aForm.getActFloatMarketPriceCcy(), aForm
							.getActFloatMarketPriceAmt()));
					dealObj.setActualCommonDifferential(dealObj.getContractPriceDifferential());
					dealObj.setActualEODCustomerDifferential(null);
				}
				else if (conCmdtP.equals(PriceType.FIXED_FUTURES_PRICE.getName())) {
					DefaultLogger.debug(this, " - fixed futures");
					dealObj.setActualCommonDifferential(null);
					dealObj.setActualEODCustomerDifferential(null);
					dealObj.setActualPrice(UIUtil.convertToAmount(locale, aForm.getActFixBuySellFixPriceCcy(), aForm
							.getActFixBuySellFixPriceAmt()));
				}
				else if (conCmdtP.equals(PriceType.NON_RIC_PRICE.getName())) {
					DefaultLogger.debug(this, " - Non-RIC ");
					String currencyCode = null;
					if (dealObj.getActualPrice() != null) {
						currencyCode = dealObj.getActualPrice().getCurrencyCode();
					}
					PriceDifferential priceDiff = setPriceDifferential(aForm.getActNonRICCmdtDiffSign(), aForm
							.getActNonRICCmdtDiff(), currencyCode, locale);
					dealObj.setActualCommonDifferential(priceDiff);
				}
				else if (conCmdtP.equals(PriceType.MANUAL_NON_RIC_PRICE.getName())) {
					DefaultLogger.debug(this, " - Manual Non-RIC");
					dealObj.setActualMarketPriceDate(compareDate(locale, dealObj.getActualMarketPriceDate(), aForm
							.getActNonRICDate()));
					String currencyCode = aForm.getActNonRICMarketPriceCcy();
					dealObj.setActualPrice(UIUtil.convertToAmount(locale, aForm.getActNonRICMarketPriceCcy(), aForm
							.getActNonRICMarketPriceAmt()));
					PriceDifferential priceDiff = setPriceDifferential(aForm.getActNonRICCmdtDiffSign(), aForm
							.getActNonRICCmdtDiff(), currencyCode, locale);
					dealObj.setActualCommonDifferential(priceDiff);
				}
			}
		}
		return dealObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DealInfoForm aForm = (DealInfoForm) cForm;
		ICommodityCollateral dealCollateral = (ICommodityCollateral) inputs.get("dealCollateral");
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		HashMap dealMap = (HashMap) obj;
		ICommodityDeal dealObj = (ICommodityDeal) dealMap.get("obj");
		IProfile profile = (IProfile) dealMap.get("profile");
		String from_event = (String) inputs.get("from_event");

		CommodityCategoryList categoryList = CommodityCategoryList.getInstance();
		if (dealCollateral != null) {
			aForm.setConCommCategory(dealCollateral.getCollateralSubType().getSubTypeName());
		}
		if (profile != null) {
			if (from_event.equals(DealInfoAction.EVENT_PROCESS_UPDATE)
					|| from_event.equals(DealInfoAction.EVENT_PREPARE_UPDATE)
					|| from_event.equals(DealInfoAction.EVENT_PREPARE_ADD_DEAL)) {
				aForm.setConCommProductType(profile.getProductType());
				aForm.setConCommProductSubType(String.valueOf(profile.getProfileID()));
			}
			else {
				aForm.setConCommProductType(categoryList.getCommProductItem(profile.getCategory(), profile
						.getProductType()));
				aForm.setConCommProductSubType(profile.getProductSubType());
			}
		}
		if (dealObj.getContractPriceType() != null) {
			aForm.setConCommPriceType(dealObj.getContractPriceType().getName());
		}
		aForm.setConRIC(dealObj.getContractRIC());

		// - Refactor by HongMan - Begin
		try {
			PriceDifferential conPriceDiff = dealObj.getContractPriceDifferential();
			fillUom2Form(aForm, locale, dealObj, from_event);
			fillContractInfo2Form(aForm, locale, dealObj, from_event);
			fillActualInfo2Form(aForm, locale, dealObj);

			DefaultLogger.debug(this, " ContractPriceType : " + dealObj.getContractPriceType());
			if (dealObj.getContractPriceType() != null) {
				String priceType = dealObj.getContractPriceType().getName();
				if (priceType.equals(PriceType.EOD_PRICE.getName())) {
					fillEOD2Form(aForm, locale, dealObj);
				}
				else if (priceType.equals(PriceType.MANUAL_EOD_PRICE.getName())) {
					fillManualEOD2Form(aForm, locale, dealObj);
				}
				else if (priceType.equals(PriceType.FLOATING_FUTURES_PRICE.getName())) {
					fillFloatingFutures2Form(aForm, locale, dealObj, conPriceDiff);
				}
				else if (priceType.equals(PriceType.MANUAL_FLOATING_FUTURES_PRICE.getName())) {
					fillManualFloatingFutures2Form(aForm, locale, dealObj, conPriceDiff);
				}
				else if (priceType.equals(PriceType.FIXED_FUTURES_PRICE.getName())) {
					fillFixedFutures2Form(aForm, locale, dealObj, conPriceDiff);
				}
				else if (priceType.equals(PriceType.NON_RIC_PRICE.getName())) {
					fillNonRIC2Form(aForm, locale, dealObj);
				}
				else if (priceType.equals(PriceType.MANUAL_NON_RIC_PRICE.getName())) {
					fillManualNonRIC2Form(aForm, locale, dealObj);
				}
			}
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}
		// - Refactor by HongMan - End
		return aForm;
	}

	private void fillUom2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj, String from_event)
			throws Exception {
		Quantity conQty = dealObj.getContractQuantity();
		aForm.setConQtyVolume(getQuantityValue(conQty, locale));
		aForm.setConQtyUOM(getUOM(conQty, from_event));
		aForm.setConQtyDiffUOM(getUOM(conQty, from_event));
		aForm.setActQtyUOM(getUOM(conQty, from_event));
	}

	private void fillContractInfo2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj, String from_event)
			throws Exception {
		Quantity conQty = dealObj.getContractQuantity();
		if ((conQty != null) && (conQty.getQuantity() != null)) {
			QuantityConversionRate marketRate = dealObj.getContractMarketUOMConversionRate();
			if ((marketRate == null) && (conQty.getUnitofMeasure() != null)) {
				marketRate = conQty.getUnitofMeasure().getMarketUOMConversionRate();
			}
			if (marketRate != null) {
				Quantity marketUnit = (Quantity) marketRate.convert(conQty);
				if (marketUnit != null) {
					aForm.setConQtyMarketUnit(getQuantityValue(marketUnit, locale) + " "
							+ marketUnit.getUnitofMeasure().getLabel());
				}
			}

			QuantityConversionRate metricRate = dealObj.getContractMetricUOMConversionRate();
			if ((metricRate == null) && (conQty.getUnitofMeasure() != null)) {
				metricRate = conQty.getUnitofMeasure().getMetricUOMConversionRate();
			}
			if (metricRate != null) {
				Quantity metricUnit = (Quantity) metricRate.convert(conQty);
				if (metricUnit != null) {
					aForm.setConQtyMetricUnit(getQuantityValue(metricUnit, locale) + " "
							+ metricUnit.getUnitofMeasure().getLabel());
				}
			}
		}
		fillInContractDiffer2Form(aForm, locale, dealObj);
	}

	private void fillInContractDiffer2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj) throws Exception {
		QuantityDifferential conQtyDiff = dealObj.getContractQuantityDifferential();
		if (conQtyDiff != null) {
			if (conQtyDiff.getSign() != null) {
				aForm.setConQtyDiffPlusmn(conQtyDiff.getSign().getName());
			}
			else {
				aForm.setConQtyDiffPlusmn(ICMSConstant.SIMPLE_SIGN_PLUS);
			}
			aForm.setConQtyDiffValue(getQuantityDiffValue(conQtyDiff.getQuantity(), locale));
		}
		PriceDifferential conPriceDiff = dealObj.getContractPriceDifferential();
		if (conPriceDiff != null) {
			if (conPriceDiff.getSign() != null) {
				aForm.setConPriceDiffPlusmn(conPriceDiff.getSign().getName());
			}
			else {
				aForm.setConPriceDiffPlusmn(ICMSConstant.SIMPLE_SIGN_PLUS);
			}
			if (conPriceDiff.getPrice() != null) {
				// aForm.setConPriceDiffCcy(conPriceDiff.getPrice().
				// getCurrencyCode());
				if (conPriceDiff.getPrice().getAmount() >= 0) {
					aForm.setConBuyerSellerAgreeDiff(UIUtil.formatNumber(conPriceDiff.getPrice()
							.getAmountAsBigDecimal(), 6, locale));
				}
			}
		}
		aForm.setConContractedPriceCcy("");
		aForm.setConContractedPriceAmt("");
		if (dealObj.getContractPrice() != null) {
			aForm.setConContractedPriceCcy(dealObj.getContractPrice().getCurrencyCode());
			if (dealObj.getContractPrice().getAmount() >= 0) {
				aForm.setConContractedPriceAmt(UIUtil.formatNumber(dealObj.getContractPrice().getAmountAsBigDecimal(),
						6, locale));
			}
		}

	}

	private void fillActualInfo2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj) throws Exception {
		Quantity actualQty = dealObj.getActualQuantity();
		if (actualQty == null) {
			return;
		}
		aForm.setActQtyVolume(getQuantityValue(actualQty, locale));
		QuantityConversionRate marketRate = dealObj.getContractMarketUOMConversionRate();
		if ((marketRate == null) && (actualQty.getUnitofMeasure() != null)) {
			marketRate = actualQty.getUnitofMeasure().getMarketUOMConversionRate();
		}
		if (marketRate != null) {
			Quantity marketUnit = (Quantity) marketRate.convert(actualQty);
			if (marketUnit != null) {
				aForm.setActQtyMarketUnit(getQuantityValue(marketUnit, locale) + " "
						+ marketUnit.getUnitofMeasure().getLabel());
			}
		}

		QuantityConversionRate metricRate = dealObj.getContractMetricUOMConversionRate();
		if ((metricRate == null) && (actualQty.getUnitofMeasure() != null)) {
			metricRate = actualQty.getUnitofMeasure().getMetricUOMConversionRate();
		}
		if (metricRate != null) {
			Quantity metricUnit = (Quantity) metricRate.convert(actualQty);
			if (metricUnit != null) {
				aForm.setActQtyMetricUnit(getQuantityValue(metricUnit, locale) + " "
						+ metricUnit.getUnitofMeasure().getLabel());
			}
		}
	}

	private void fillManualNonRIC2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj) throws Exception {
		aForm.setActNonRICMarketPrice("");
		aForm.setActNonRICDate(DateUtil.formatDate(locale, dealObj.getActualMarketPriceDate()));
		if (dealObj.getActualPrice() != null) {
			aForm.setActNonRICMarketPriceCcy(dealObj.getActualPrice().getCurrencyCode());
			if (dealObj.getActualPrice().getAmount() >= 0) {
				aForm.setActNonRICMarketPriceAmt(UIUtil.formatNumber(dealObj.getActualPrice().getAmountAsBigDecimal(),
						6, locale));
			}
			else {
				aForm.setActNonRICMarketPriceAmt("");
			}
		}
		else {
			aForm.setActNonRICMarketPriceAmt("");
			aForm.setActNonRICMarketPriceCcy("");
		}
		String[] priceDiffArr = getPriceDifferential(dealObj.getActualCommonDifferential(), locale);
		DefaultLogger.debug(this, " - Sign : " + priceDiffArr[0]);
		DefaultLogger.debug(this, " - Diff : " + priceDiffArr[1]);
		aForm.setActNonRICCmdtDiffSign(priceDiffArr[0]);
		aForm.setActNonRICCmdtDiff(priceDiffArr[1]);

		if (dealObj.getActualPrice() != null) {
			Amount adjustPrice = dealObj.getActualPrice();
			if (dealObj.getActualCommonDifferential() != null) {
				PriceDifferential tmpPriceDiff = dealObj.getActualCommonDifferential();
				adjustPrice = tmpPriceDiff.calculate(adjustPrice);
			}
			if (adjustPrice != null) {
				aForm.setActNonRICAdjustPrice(UIUtil.formatAmount(adjustPrice, 6, locale));
			}
		}
		else {
			aForm.setActNonRICAdjustPrice("");
		}
	}

	private void fillNonRIC2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj) throws Exception {
		aForm.setActNonRICMarketPriceCcy("");
		aForm.setActNonRICMarketPriceAmt("");
		aForm.setActNonRICDate(DateUtil.formatDate(locale, dealObj.getActualMarketPriceDate()));

		if (dealObj.getActualPrice() == null) {
			aForm.setActNonRICMarketPrice("");
		}
		else {
			aForm.setActNonRICMarketPrice(UIUtil.formatAmount(dealObj.getActualPrice(), 6, locale));
		}

		String[] priceDiffArr = getPriceDifferential(dealObj.getActualCommonDifferential(), locale);
		DefaultLogger.debug(this, " - Sign : " + priceDiffArr[0]);
		DefaultLogger.debug(this, " - Diff : " + priceDiffArr[1]);
		aForm.setActNonRICCmdtDiffSign(priceDiffArr[0]);
		aForm.setActNonRICCmdtDiff(priceDiffArr[1]);

		if (dealObj.getActualPrice() != null) {
			Amount adjustPrice = dealObj.getActualPrice();
			if (dealObj.getActualCommonDifferential() != null) {
				PriceDifferential tmpPriceDiff = dealObj.getActualCommonDifferential();
				adjustPrice = tmpPriceDiff.calculate(adjustPrice);
			}
			if (adjustPrice != null) {
				aForm.setActNonRICAdjustPrice(UIUtil.formatAmount(adjustPrice, 6, locale));
			}
		}
		else {
			aForm.setActNonRICAdjustPrice("");
		}
	}

	private void fillFixedFutures2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj,
			PriceDifferential conPriceDiff) throws Exception {
		if (dealObj.getActualPrice() != null) {
			aForm.setActFixBuySellFixPriceCcy(dealObj.getActualPrice().getCurrencyCode());
			if (dealObj.getActualPrice().getAmount() >= 0) {
				try {
					aForm.setActFixBuySellFixPriceAmt(UIUtil.formatNumber(dealObj.getActualPrice()
							.getAmountAsBigDecimal(), 6, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			else {
				aForm.setActFixBuySellFixPriceAmt("");
			}
		}
		else {
			aForm.setActFixBuySellFixPriceCcy("");
			aForm.setActFixBuySellFixPriceAmt("");
		}

		if (conPriceDiff != null) {
			if (conPriceDiff.getPrice() != null) {
				if (conPriceDiff.getPrice().getAmount() >= 0) {
					aForm.setActFloatBuySellAgreeDiff(UIUtil.formatNumber(conPriceDiff.getPrice()
							.getAmountAsBigDecimal(), 6, locale));
				}
			}
		}
	}

	private void fillManualFloatingFutures2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj,
			PriceDifferential conPriceDiff) throws Exception {
		aForm.setActFloatMarketPrice("");
		aForm.setActFloatDate(DateUtil.formatDate(locale, dealObj.getActualMarketPriceDate()));
		if (dealObj.getActualPrice() != null) {
			aForm.setActFloatMarketPriceCcy(dealObj.getActualPrice().getCurrencyCode());
			if (dealObj.getActualPrice().getAmount() >= 0) {
				aForm.setActFloatMarketPriceAmt(UIUtil.formatNumber(dealObj.getActualPrice().getAmountAsBigDecimal(),
						6, locale));
			}
			else {
				aForm.setActFloatMarketPriceAmt("");
			}
		}
		else {
			aForm.setActFloatMarketPriceCcy("");
			aForm.setActFloatMarketPriceAmt("");
		}

		if (conPriceDiff != null) {
			if (conPriceDiff.getPrice() != null) {
				if (conPriceDiff.getPrice().getAmount() >= 0) {
					aForm.setActFloatBuySellAgreeDiff(UIUtil.formatNumber(conPriceDiff.getPrice()
							.getAmountAsBigDecimal(), 6, locale));
				}
			}
		}
		if (dealObj.getActualPrice() != null) {
			Amount adjustPrice = dealObj.getActualPrice();
			if (dealObj.getActualCommonDifferential() != null) {
				PriceDifferential tmpPriceDiff = dealObj.getActualCommonDifferential();
				adjustPrice = tmpPriceDiff.calculate(adjustPrice);
			}
			if (adjustPrice != null) {
				aForm.setActFloatAdjustPrice(UIUtil.formatAmount(adjustPrice, 6, locale));
			}
		}
		else {
			aForm.setActFloatAdjustPrice("");
		}
	}

	private void fillFloatingFutures2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj,
			PriceDifferential conPriceDiff) throws Exception {
		aForm.setActFloatMarketPriceAmt("");
		aForm.setActFloatMarketPriceCcy("");
		aForm.setActFloatDate(DateUtil.formatDate(locale, dealObj.getActualMarketPriceDate()));
		aForm.setActFloatMarketPrice(UIUtil.formatAmount(dealObj.getActualPrice(), 6, locale));

		if (conPriceDiff != null) {
			if (conPriceDiff.getPrice() != null) {
				if (conPriceDiff.getPrice().getAmount() >= 0) {
					aForm.setActFloatBuySellAgreeDiff(UIUtil.formatNumber(conPriceDiff.getPrice()
							.getAmountAsBigDecimal(), 6, locale));
				}
			}
		}

		if (dealObj.getActualPrice() != null) {
			Amount adjustPrice = dealObj.getActualPrice();
			if (dealObj.getActualCommonDifferential() != null) {
				PriceDifferential tmpPriceDiff = dealObj.getActualCommonDifferential();
				adjustPrice = tmpPriceDiff.calculate(adjustPrice);
			}
			if (adjustPrice != null) {
				aForm.setActFloatAdjustPrice(UIUtil.formatAmount(adjustPrice, 6, locale));
			}
		}
		else {
			aForm.setActFloatAdjustPrice("");
		}
	}

	private void fillManualEOD2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj) throws Exception {
		aForm.setActEODMarketPrice("");
		aForm.setActEODDate(DateUtil.formatDate(locale, dealObj.getActualMarketPriceDate()));
		if (dealObj.getActualPrice() != null) {
			aForm.setActEODMarketPriceCcy(dealObj.getActualPrice().getCurrencyCode());
			if (dealObj.getActualPrice().getAmount() >= 0) {
				aForm.setActEODMarketPriceAmt(UIUtil.formatNumber(dealObj.getActualPrice().getAmountAsBigDecimal(), 6,
						locale));
			}
			else {
				aForm.setActEODMarketPriceAmt("");
			}
		}
		else {
			aForm.setActEODMarketPriceAmt("");
			aForm.setActEODMarketPriceCcy("");
		}
		String[] priceDiffArr = getPriceDifferential(dealObj.getActualEODCustomerDifferential(), locale);
		aForm.setActEODCustDiffSign(priceDiffArr[0]);
		aForm.setActEODCustDiff(priceDiffArr[1]);
		priceDiffArr = getPriceDifferential(dealObj.getActualCommonDifferential(), locale);
		aForm.setActEODCommDiffSign(priceDiffArr[0]);
		aForm.setActEODCommDiff(priceDiffArr[1]);

		if (dealObj.getActualPrice() != null) {
			Amount adjustPrice = dealObj.getActualPrice();
			if (dealObj.getActualEODCustomerDifferential() != null) {
				PriceDifferential tmpPriceDiff = dealObj.getActualEODCustomerDifferential();
				adjustPrice = tmpPriceDiff.calculate(adjustPrice);
			}
			if (dealObj.getActualCommonDifferential() != null) {
				PriceDifferential tmpPriceDiff = dealObj.getActualCommonDifferential();
				adjustPrice = tmpPriceDiff.calculate(adjustPrice);
			}
			if (adjustPrice != null) {
				aForm.setActEODAdjustPrice(UIUtil.formatAmount(adjustPrice, 6, locale));
			}
		}
		else {
			aForm.setActEODAdjustPrice("");
		}
	}

	private void fillEOD2Form(DealInfoForm aForm, Locale locale, ICommodityDeal dealObj) throws Exception {
		aForm.setActEODMarketPriceAmt("");
		aForm.setActEODMarketPriceCcy("");
		aForm.setActEODDate(DateUtil.formatDate(locale, dealObj.getActualMarketPriceDate()));
		aForm.setActEODMarketPrice(UIUtil.formatAmount(dealObj.getActualPrice(), 6, locale));
		String[] priceDiffArr = getPriceDifferential(dealObj.getActualEODCustomerDifferential(), locale);
		aForm.setActEODCustDiffSign(priceDiffArr[0]);
		aForm.setActEODCustDiff(priceDiffArr[1]);
		priceDiffArr = getPriceDifferential(dealObj.getActualCommonDifferential(), locale);
		aForm.setActEODCommDiffSign(priceDiffArr[0]);
		aForm.setActEODCommDiff(priceDiffArr[1]);

		if (dealObj.getActualPrice() != null) {
			Amount adjustPrice = dealObj.getActualPrice();
			if (dealObj.getActualEODCustomerDifferential() != null) {
				PriceDifferential tmpPriceDiff = dealObj.getActualEODCustomerDifferential();
				adjustPrice = tmpPriceDiff.calculate(adjustPrice);
			}
			if (dealObj.getActualCommonDifferential() != null) {
				PriceDifferential tmpPriceDiff = dealObj.getActualCommonDifferential();
				adjustPrice = tmpPriceDiff.calculate(adjustPrice);
			}
			if (adjustPrice != null) {
				aForm.setActEODAdjustPrice(UIUtil.formatAmount(adjustPrice, 6, locale));
			}
		}
		else {
			aForm.setActEODAdjustPrice("");
		}
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "uomMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE }, });
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

	private Quantity setQuantity(String volume, String uom, HashMap uomMap, Locale locale) throws MapperException {
		if (isEmptyOrNull(volume) && isEmptyOrNull(uom)) {
			return null;
		}
		else if (isEmptyOrNull(volume) && !isEmptyOrNull(uom)) {
			return new Quantity(null, (UOMWrapper) uomMap.get(uom));
		}
		else if (!isEmptyOrNull(volume) && isEmptyOrNull(uom)) {
			try {
				BigDecimal value = UIUtil.mapStringToBigDecimal(volume);
				return new Quantity(value, null);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		else {
			try {
				BigDecimal value = UIUtil.mapStringToBigDecimal(volume);
				return new Quantity(value, (UOMWrapper) uomMap.get(uom));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
	}

	private String getUOM(Quantity qty, String from_event) {
		String returnValue = "";
		if ((qty != null) && (qty.getUnitofMeasure() != null)) {
			if (from_event.equals(DealInfoAction.EVENT_PREPARE_ADD_DEAL)
					|| from_event.equals(DealInfoAction.EVENT_PREPARE_UPDATE)
					|| from_event.equals(DealInfoAction.EVENT_PROCESS_UPDATE)) {
				returnValue = qty.getUnitofMeasure().getID();
			}
			else {
				returnValue = qty.getUnitofMeasure().getLabel();
			}
		}
		return returnValue;
	}

	private String getQuantityValue(Quantity qty, Locale locale) throws MapperException {
		String returnValue = "";
		if ((qty != null) && (qty.getQuantity() != null)) {
			try {
				returnValue = UIUtil.formatNumber(qty.getQuantity(), 4, locale);
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		return returnValue;
	}

	private String getQuantityDiffValue(Quantity qty, Locale locale) throws MapperException {
		String returnValue = "";
		if ((qty != null) && (qty.getQuantity() != null)) {
			try {
				returnValue = UIUtil.formatNumber(qty.getQuantity(), 6, locale);
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		return returnValue;
	}

	private PriceDifferential setPriceDifferential(String diffSign, String diffValue, String ccyCode, Locale locale)
			throws MapperException {
		BigDecimal differential = null;
		Amount amount = null;
		PriceDifferential priceDiff = null;
		if (!isEmptyOrNull(diffValue)) {
			try {
				differential = UIUtil.mapStringToBigDecimal(diffValue);
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
			amount = new Amount(differential, null);
		}

		if (!isEmptyOrNull(diffSign)) {
			priceDiff = new PriceDifferential(amount, diffSign);
		}

		return priceDiff;
	}

	private String[] getPriceDifferential(PriceDifferential priceDiff, Locale locale) throws Exception {
		String[] returnStr = new String[2];
		if (priceDiff == null) {
			return returnStr;
		}
		if (priceDiff.getSign() != null) {
			returnStr[0] = priceDiff.getSign().getName();
		}
		else {
			returnStr[0] = ICMSConstant.SIMPLE_SIGN_PLUS;
		}
		if ((priceDiff.getPrice() != null) && (priceDiff.getPrice().getAmount() >= 0)) {
			try {
				returnStr[1] = UIUtil.formatNumber(priceDiff.getPrice().getAmountAsBigDecimal(), 6, locale);
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		return returnStr;
	}
}
