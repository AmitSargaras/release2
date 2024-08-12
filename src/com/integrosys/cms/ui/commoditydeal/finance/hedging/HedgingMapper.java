/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/hedging/HedgingMapper.java,v 1.13 2004/08/07 05:27:21 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.hedging;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IHedgingContractInfo;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.finance.IHedgePriceExtension;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBHedgePriceExtension;
import com.integrosys.cms.app.commodity.deal.bus.finance.PeriodUnit;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2004/08/07 05:27:21 $ Tag: $Name: $
 */

public class HedgingMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		HedgingForm aForm = (HedgingForm) cForm;
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityDeal dealObj = null;
		try {
			dealObj = (ICommodityDeal) AccessorUtil.deepClone(trxValue.getStagingCommodityDeal());
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}

		if (!isEmptyOrNull(aForm.getGlobalTreasuryRefNo())) {
			dealObj.setHedgeContractID(Long.parseLong(aForm.getGlobalTreasuryRefNo()));
		}
		else {
			dealObj.setHedgeContractID(ICMSConstant.LONG_INVALID_VALUE);
		}

		if (isEmptyOrNull(aForm.getHedgingPriceAmt())) {
			dealObj.setHedgePrice(null);
		}
		else {
			try {
				dealObj.setHedgePrice(UIUtil.convertToAmount(locale, aForm.getHedgingPriceCcy(), aForm
						.getHedgingPriceAmt()));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		if (isEmptyOrNull(aForm.getTotalQtyGoodsHedge())) {
			dealObj.setHedgeQuantity(null);
		}
		else {
			UOMWrapper uom = null;
			if (dealObj.getContractQuantity() != null) {
				uom = dealObj.getContractQuantity().getUnitofMeasure();
			}
			try {
				BigDecimal qtyValue = UIUtil.mapStringToBigDecimal(aForm.getTotalQtyGoodsHedge());
				dealObj.setHedgeQuantity(new Quantity(qtyValue, uom));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		String[] periodID = aForm.getPeriodID();
		String[] period = aForm.getPeriod();
		String[] periodUnit = aForm.getPeriodUnit();
		String[] startDate = aForm.getPeriodStartDate();
		String[] endDate = aForm.getPeriodEndDate();
		if (periodID != null) {
			OBHedgePriceExtension[] priceExtension = (OBHedgePriceExtension[]) dealObj.getHedgePriceExtension();
			HashMap priceExtensionMap = new HashMap();
			if (priceExtension != null) {
				for (int i = 0; i < priceExtension.length; i++) {
					DefaultLogger.debug(this, "<<<<<<<<<<<<<<< price extension id: "
							+ priceExtension[i].getExtensionID());
					priceExtensionMap.put(String.valueOf(priceExtension[i].getExtensionID()), priceExtension[i]);
				}
			}

			OBHedgePriceExtension[] newPriceExtension = new OBHedgePriceExtension[periodID.length];
			for (int i = 0; i < periodID.length; i++) {
				DefaultLogger.debug(this, "========= periodID: " + periodID[i] + "\tperiod: " + period[i]);
				OBHedgePriceExtension priceObj;
				if (isEmptyOrNull(periodID[i])) {
					priceObj = new OBHedgePriceExtension();
					priceObj.setExtensionID(ICMSConstant.LONG_INVALID_VALUE);
				}
				else {
					priceObj = (OBHedgePriceExtension) priceExtensionMap.get(periodID[i]);
				}
				if (isEmptyOrNull(period[i])) {
					priceObj.setPeriodUnitCount(ICMSConstant.LONG_INVALID_VALUE);
				}
				else {
					priceObj.setPeriodUnitCount(Long.parseLong(period[i]));
				}
				if (isEmptyOrNull(periodUnit[i])) {
					priceObj.setPeriodUnit(null);
				}
				else {
					priceObj.setPeriodUnit(PeriodUnit.valueOf(periodUnit[i]));
				}
				priceObj.setStartDate(compareDate(locale, priceObj.getStartDate(), startDate[i]));
				priceObj.setEndDate(compareDate(locale, priceObj.getEndDate(), endDate[i]));
				newPriceExtension[i] = priceObj;
			}
			dealObj.setHedgePriceExtension(newPriceExtension);
			DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<<<<<<< new hedge price: " + dealObj.getHedgePrice());
		}
		else {
			dealObj.setHedgePriceExtension(null);
		}
		if (aForm.getEvent().equals(HedgingAction.EVENT_REMOVE_EXTENSION)) {
			HashMap dealObjMap = new HashMap();
			dealObjMap.put("obj", dealObj);
			dealObjMap.put("deleteArr", aForm.getDeletePeriod());
			return dealObjMap;
		}
		return dealObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		HedgingForm aForm = (HedgingForm) cForm;
		ICommodityDeal dealObj = (ICommodityDeal) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityCollateral dealCollateral = (ICommodityCollateral) inputs.get("dealCollateral");

		aForm.setTpDealRefNo(dealObj.getDealReferenceNo());
		if (dealObj.getHedgeContractID() != ICMSConstant.LONG_INVALID_VALUE) {
			aForm.setGlobalTreasuryRefNo(String.valueOf(dealObj.getHedgeContractID()));
			IHedgingContractInfo[] hedgingContract = dealCollateral.getHedgingContractInfos();
			IHedgingContractInfo hedgeContractObj = null;
			boolean found = false;
			if (hedgingContract != null) {
				for (int i = 0; !found && (i < hedgingContract.length); i++) {
					if (hedgingContract[i].getHedgingContractInfoID() == dealObj.getHedgeContractID()) {
						found = true;
						hedgeContractObj = hedgingContract[i];
					}
				}
			}
			if (!found) {
				hedgingContract = dealCollateral.getDeletedHedgeContractInfos();
				if (hedgingContract != null) {
					for (int i = 0; !found && (i < hedgingContract.length); i++) {
						if (hedgingContract[i].getHedgingContractInfoID() == dealObj.getHedgeContractID()) {
							found = true;
							hedgeContractObj = hedgingContract[i];
						}
					}
				}
			}
			if (hedgeContractObj != null) {
				if (aForm.getEvent().equals(EVENT_READ)) {
					aForm.setGlobalTreasuryRefNo(hedgeContractObj.getGlobalTreasuryReference());
				}

				DefaultLogger.debug(this, "==================== hedgingContractObj: " + hedgeContractObj);
				aForm.setDealdate(DateUtil.formatDate(locale, hedgeContractObj.getDateOfDeal()));
				if (hedgeContractObj.getDealAmount() != null) {
					try {
						aForm.setDealAmt(UIUtil.formatAmount(hedgeContractObj.getDealAmount(), 6, locale));
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
				aForm.setHedgeAgreeRef(hedgeContractObj.getHedgingAgreement());
				aForm.setHedgeAgreeDate(DateUtil.formatDate(locale, hedgeContractObj.getHedgingAgreementDate()));
				aForm.setCounterPartyName(hedgeContractObj.getNameOfTheCounterParty());
				if (hedgeContractObj.getMargin() >= 0) {
					aForm.setMargin(String.valueOf(hedgeContractObj.getMargin()));
				}
			}
		}

		if (dealObj.getHedgePrice() != null) {
			aForm.setHedgingPriceCcy(dealObj.getHedgePrice().getCurrencyCode());
			if (dealObj.getHedgePrice().getAmount() >= 0) {
				try {
					aForm.setHedgingPriceAmt(UIUtil.formatNumber(dealObj.getHedgePrice().getAmountAsBigDecimal(), 6,
							locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}

		BigDecimal totalGoodHedge = null;
		BigDecimal totalGoodQty = null;
		if (dealObj.getContractQuantity() != null) {
			Quantity qty = dealObj.getContractQuantity();
			if (qty.getQuantity() != null) {
				totalGoodQty = qty.getQuantity();
				try {
					aForm.setTotalQtyGoods(UIUtil.formatNumber(qty.getQuantity(), 4, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			if (qty.getUnitofMeasure() != null) {
				aForm.setHedgeQtyUOM(qty.getUnitofMeasure().getLabel());
			}
		}
		if (dealObj.getHedgeQuantity() != null) {
			Quantity qty = dealObj.getHedgeQuantity();
			if (qty.getQuantity() != null) {
				totalGoodHedge = qty.getQuantity();
				try {
					aForm.setTotalQtyGoodsHedge(UIUtil.formatNumber(qty.getQuantity(), 4, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}
		if ((totalGoodHedge != null) && (totalGoodQty != null) && (totalGoodQty.doubleValue() > 0)) {
			BigDecimal percentage = CommonUtil.calcPercentage(totalGoodQty, totalGoodHedge);
			// totalGoodHedge.divide(totalGoodQty,
			// ICMSConstant.PERCENTAGE_SCALE,
			// BigDecimal.ROUND_UNNECESSARY).multiply(new BigDecimal(100));
			try {
				aForm.setPercentageGoodsHedge(UIUtil.formatNumber(percentage, 0, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		IHedgePriceExtension[] extension = dealObj.getHedgePriceExtension();
		if (extension != null) {
			DefaultLogger.debug(this, "================= extension length: " + extension.length);
			String[] periodID = new String[extension.length];
			String[] period = new String[extension.length];
			String[] periodUnit = new String[extension.length];
			String[] startDate = new String[extension.length];
			String[] endDate = new String[extension.length];
			for (int i = 0; i < extension.length; i++) {
				if (extension[i].getExtensionID() != ICMSConstant.LONG_INVALID_VALUE) {
					periodID[i] = String.valueOf(extension[i].getExtensionID());
				}
				else {
					periodID[i] = "";
				}
				if (extension[i].getPeriodUnitCount() >= 0) {
					period[i] = String.valueOf(extension[i].getPeriodUnitCount());
				}
				else {
					period[i] = "";
				}
				if (extension[i].getPeriodUnit() != null) {
					periodUnit[i] = extension[i].getPeriodUnit().getName();
				}
				else {
					periodUnit[i] = "";
				}
				startDate[i] = DateUtil.formatDate(locale, extension[i].getStartDate());
				endDate[i] = DateUtil.formatDate(locale, extension[i].getEndDate());
			}
			aForm.setPeriodID(periodID);
			aForm.setPeriod(period);
			aForm.setPeriodUnit(periodUnit);
			aForm.setPeriodStartDate(startDate);
			aForm.setPeriodEndDate(endDate);
		}
		aForm.setDeletePeriod(new String[0]);

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
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
}
