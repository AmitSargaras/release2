/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/FinanceMapper.java,v 1.28 2004/12/06 05:58:24 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IHedgingContractInfo;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.ISalesDetails;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.ui.commoditydeal.CommodityDealUtil;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.28 $
 * @since $Date: 2004/12/06 05:58:24 $ Tag: $Name: $
 */

public class FinanceMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		FinanceForm aForm = (FinanceForm) cForm;
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();

		return dealObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		FinanceForm aForm = (FinanceForm) cForm;
		ICommodityDeal dealObj = (ICommodityDeal) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityCollateral dealCollateral = (ICommodityCollateral) inputs.get("dealCollateral");

		IPurchaseAndSalesDetails purchaseSale = dealObj.getPurchaseAndSalesDetails();
		IPurchaseDetails purchase = null;
		ISalesDetails sales = null;
		if (purchaseSale != null) {
			purchase = purchaseSale.getPurchaseDetails();
			sales = purchaseSale.getSalesDetails();
		}

		IProfile profile = (IProfile) inputs.get("profileService");
		if (profile != null) {
			String goodsDesc = null;
			CommodityCategoryList categoryList = CommodityCategoryList.getInstance();
			goodsDesc = categoryList.getCommProductItem(profile.getCategory(), profile.getProductType());
			goodsDesc = goodsDesc + "/" + profile.getProductSubType();
			aForm.setGoodsDesc(goodsDesc);
		}
		else {
			/*
			 * aForm.setSupplierName("-"); aForm.setBuyerName("-");
			 */
			aForm.setGoodsDesc("-");
		}

		if ((purchase != null) && (purchase.getSupplier() != null)) {
			aForm.setSupplierName(purchase.getSupplier().getName());
		}
		else if (purchase != null) {
			aForm.setSupplierName(purchase.getOtherSupplierName());
		}
		else {
			aForm.setSupplierName("-");
		}
		if ((sales != null) && (sales.getBuyer() != null)) {
			aForm.setBuyerName(sales.getBuyer().getName());
		}
		else if (sales != null) {
			aForm.setBuyerName(sales.getOtherBuyerName());
		}
		else {
			aForm.setBuyerName("-");
		}

		if ((purchase != null) && (purchase.getQuantity() != null) && (purchase.getQuantity().getQuantity() != null)
				&& (purchase.getQuantity().getQuantity().doubleValue() > 0) && (purchase.getUnitPrice() != null)
				&& (purchase.getUnitPrice().getAmount() > 0)) {
			try {
				Quantity marketUnit = null;
				if (dealObj.getContractMarketUOMConversionRate() != null) {
					marketUnit = (Quantity) dealObj.getContractMarketUOMConversionRate()
							.convert(purchase.getQuantity());
				}
				if (marketUnit != null) {
					BigDecimal purchaseValue = marketUnit.getQuantity().multiply(
							purchase.getUnitPrice().getAmountAsBigDecimal());
					aForm.setPurchaseValue(purchase.getUnitPrice().getCurrencyCode() + " "
							+ UIUtil.formatNumber(purchaseValue, 6, locale));
				}
				else {
					aForm.setPurchaseValue("-");
				}
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setPurchaseValue("-");
		}

		if ((sales != null) && (sales.getQuantity() != null) && (sales.getQuantity().getQuantity() != null)
				&& (sales.getQuantity().getQuantity().doubleValue() > 0) && (sales.getUnitPrice() != null)
				&& (sales.getUnitPrice().getAmount() > 0)) {
			try {
				Quantity marketUnit = null;
				if (dealObj.getContractMarketUOMConversionRate() != null) {
					marketUnit = (Quantity) dealObj.getContractMarketUOMConversionRate().convert(sales.getQuantity());
				}
				if (marketUnit != null) {
					BigDecimal salesValue = marketUnit.getQuantity().multiply(
							sales.getUnitPrice().getAmountAsBigDecimal());
					aForm.setSalesValue(sales.getUnitPrice().getCurrencyCode() + " "
							+ UIUtil.formatNumber(salesValue, 6, locale));
				}
				else {
					aForm.setSalesValue("-");
				}
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setSalesValue("-");
		}

		if ((dealObj.getHedgePrice() != null) && (dealObj.getHedgePrice().getAmount() >= 0)) {
			try {
				aForm.setHedgingPrice(UIUtil.formatAmount(dealObj.getHedgePrice(), 6, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setHedgingPrice("-");
		}

		if ((dealObj.getHedgeQuantity() != null) && (dealObj.getHedgeQuantity().getUnitofMeasure() != null)) {
			try {
				aForm.setTotalQtyGoodsHedge(UIUtil.formatNumber(dealObj.getHedgeQuantity().getQuantity(), 4, locale)
						+ " " + dealObj.getHedgeQuantity().getUnitofMeasure().getLabel());
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setTotalQtyGoodsHedge("-");
		}

		if ((dealCollateral != null) && (dealObj.getHedgeContractID() > 0)) {
			IHedgingContractInfo[] hedgingContractList = dealCollateral.getHedgingContractInfos();
			if (hedgingContractList != null) {
				boolean found = false;
				for (int i = 0; !found && (i < hedgingContractList.length); i++) {
					if (hedgingContractList[i].getHedgingContractInfoID() == dealObj.getHedgeContractID()) {
						found = true;
						aForm.setMargin(String.valueOf(hedgingContractList[i].getMargin()));
					}
				}
				if (!found) {
					aForm.setMargin("-");
				}
			}
			else {
				aForm.setMargin("-");
			}
		}
		else {
			aForm.setMargin("-");
		}

		if ((dealObj.getActualPrice() != null) && (dealObj.getActualPrice().getAmount() >= 0)
				&& (dealObj.getActualPrice().getCurrencyCode() != null)) {
			try {
				aForm.setHedgeMarketPrice(UIUtil.formatAmount(dealObj.getActualPrice(), 6, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setHedgeMarketPrice("-");
		}
		Amount amt = null;
		aForm.setHedgeProfitLoss("-");
		try {
			amt = dealObj.getHedgeProfitLossAmt();
		}
		catch (Exception e) {
			aForm.setHedgeProfitLoss("No forex defined for this currency");
		}
		if ((amt != null) && (amt.getCurrencyCode() != null)) {
			try {
				aForm.setHedgeProfitLoss(UIUtil.formatAmount(amt, 0, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		amt = dealObj.getTotalSettlementAmt();
		if ((amt != null) && (amt.getCurrencyCode() != null)) {
			try {
				aForm.setTotalSettlePayment(UIUtil.formatAmount(amt, 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setTotalSettlePayment("-");
		}

		amt = dealObj.getBalanceDealAmt();
		/*
		 * if (dealObj.getOrigFaceValue() != null &&
		 * dealObj.getOrigFaceValue().getCurrencyCode() != null && amt != null
		 * && amt.getCurrencyCode() != null) { BigDecimal balance =
		 * dealObj.getOrigFaceValue
		 * ().getAmountAsBigDecimal().subtract(amt.getAmountAsBigDecimal()); try
		 * {aForm.setSettleBalanceOutstanding(UIUtil.formatAmount(dealObj.
		 * getOrigFaceValue().getCurrencyCode(), balance, 2, locale)); } catch
		 * (Exception e) { throw new MapperException(e.getMessage()); } } else {
		 * aForm.setSettleBalanceOutstanding("-"); }
		 */
		if ((amt != null) && (amt.getCurrencyCode() != null)) {
			try {
				aForm.setSettleBalanceOutstanding(UIUtil.formatAmount(amt, 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setSettleBalanceOutstanding("-");
		}
		Quantity qty = dealObj.getTotalQuantityReleased();
		if (qty != null) {
			try {
				String qtyStr = UIUtil.formatNumber(qty.getQuantity(), 4, locale);
				qtyStr += " " + qty.getUnitofMeasure().getLabel();
				aForm.setTotalSettleQtyRel(qtyStr);
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setTotalSettleQtyRel("-");
		}

		qty = dealObj.getActualQuantity();
		if ((qty != null) && (qty.getQuantity() != null) && (qty.getUnitofMeasure() != null)) {
			try {
				String qtyStr = UIUtil.formatNumber(qty.getQuantity(), 4, locale);
				qtyStr += " " + qty.getUnitofMeasure().getLabel();
				aForm.setActualQty(qtyStr);
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setActualQty("-");
		}

		qty = dealObj.getBalanceDealQty();
		if ((qty != null) && (qty.getQuantity() != null) && (qty.getUnitofMeasure() != null)) {
			try {
				String qtyStr = UIUtil.formatNumber(qty.getQuantity(), 4, locale);
				qtyStr += " " + qty.getUnitofMeasure().getLabel();
				aForm.setSettleQtyBalanceOutstanding(qtyStr);
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setSettleQtyBalanceOutstanding("-");
		}

		if ((dealObj.getOrigFaceValue() != null) && (dealObj.getOrigFaceValue() != null)) {
			try {
				aForm.setOriginalFaceValue(UIUtil.formatAmount(dealObj.getOrigFaceValue(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setOriginalFaceValue("-");
		}

		if (dealObj.getFinancingPct() > 0) {
			aForm.setPercentageFinancing(String.valueOf((int) dealObj.getFinancingPct()));
		}
		else {
			aForm.setPercentageFinancing("-");
		}

		if ((dealObj.getDealAmt() != null) && (dealObj.getDealAmt().getCurrencyCode() != null)) {
			try {
				aForm.setDealFinanceAmt(UIUtil.formatAmount(dealObj.getDealAmt(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setDealFinanceAmt("-");
		}

		if ((dealObj.getActualCMVAmt() != null) && (dealObj.getActualCMVAmt().getCurrencyCode() != null)) {
			try {
				aForm.setActualDealCMV(UIUtil.formatAmount(dealObj.getActualCMVAmt(), 0, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setActualDealCMV("-");
		}

		ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		if (dealCollateral != null) {
			ICollateralParameter colParam = null;

			try {
				colParam = proxy.getCollateralParameter(dealCollateral.getCollateralLocation(), dealCollateral
						.getCollateralSubType().getSubTypeCode());
			}
			catch (Exception e) {
				// ignore the exception when trying to get CRP.
			}

			double hedgingMargin = 0;
			if (dealObj.getHedgeContractID() > 0) {
				IHedgingContractInfo hedgingContract = CommodityDealUtil.getHedgingContractByID(dealCollateral, dealObj
						.getHedgeContractID());
				if ((hedgingContract != null) && (hedgingContract.getMargin() > 0)) {
					hedgingMargin = hedgingContract.getMargin();
				}
			}
			double crp = 0;
			if (colParam != null) {
				crp = colParam.getThresholdPercent();
			}
			Amount actualFsvAmt = dealObj.getActualFSVAmt(crp, hedgingMargin);
			if (actualFsvAmt != null) {
				try {
					aForm.setActualDealFSV(UIUtil.formatAmount(actualFsvAmt, 0, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			else {
				aForm.setActualDealFSV("-");
			}
		}
		else {
			aForm.setActualDealFSV("-");
		}

		if ((dealObj.getCMV() != null) && (dealObj.getCMV().getCurrencyCode() != null)) {
			try {
				aForm.setDealCMV(UIUtil.formatAmount(dealObj.getCMV(), 0, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setDealCMV("-");
		}

		if ((dealObj.getFSV() != null) && (dealObj.getFSV().getCurrencyCode() != null)) {
			try {
				aForm.setDealFSV(UIUtil.formatAmount(dealObj.getFSV(), 0, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setDealFSV("-");
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE }, });
	}
}
