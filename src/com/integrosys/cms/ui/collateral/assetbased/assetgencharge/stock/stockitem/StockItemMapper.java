/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/stock/stockitem/StockItemMapper.java,v 1.12 2005/06/10 06:27:56 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock.stockitem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBStock;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.GeneralChargeSubTypeMapper;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Mapper for Stock Item
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2005/06/10 06:27:56 $ Tag: $Name: $
 */

public class StockItemMapper extends GeneralChargeSubTypeMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		StockItemForm aForm = (StockItemForm) cForm;
		String strIndex = (String) inputs.get("indexID");

		IGeneralCharge iCol = (IGeneralCharge) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());
		IStock stock = null;
		HashMap stockMap = (HashMap) iCol.getStocks();
		if (strIndex.equals("-1")) {
			stock = new OBStock();
		}
		else {
			try {
				stock = (IStock) AccessorUtil.deepClone(stockMap.get(strIndex));
			}
			catch (Exception e) {
				DefaultLogger.error(this, "AccessorUtil.deepClone", e);
				throw new MapperException(e.getMessage());
			}
		}

		stock = (IStock) super.mapFormToOB(cForm, inputs, stock);

		locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		currCode = aForm.getValCurrency();

		try {
			stock.setCreditorAmt(convertToAmount(aForm.getCreditor()));

			stock.setFinishGoodsAmt(convertToAmount(aForm.getFinishedGoodsAmt()));
			stock.setFinishGoodsMargin(AssetGenChargeUtil.setMarginStrToDouble(aForm.getFinishedGoodsMargin()));

			stock.setGoodsTransitAmt(convertToAmount(aForm.getGoodsInTransitAmt()));
			stock.setGoodsTransitMargin(AssetGenChargeUtil.setMarginStrToDouble(aForm.getGoodsInTransitMargin()));

			stock.setOtherMerchandiseAmt(convertToAmount(aForm.getOthMerchandiseAmt()));
			stock.setOtherMerchandiseMargin(AssetGenChargeUtil.setMarginStrToDouble(aForm.getOthMerchandiseMargin()));

			stock.setRawMaterialAmt(convertToAmount(aForm.getRawMaterialsAmt()));
			stock.setRawMaterialMargin(AssetGenChargeUtil.setMarginStrToDouble(aForm.getRawMaterialsMargin()));

			stock.setStoresSparesAmt(convertToAmount(aForm.getStoresSparesAmt()));
			stock.setStoresSparesMargin(AssetGenChargeUtil.setMarginStrToDouble(aForm.getStoresSparesMargin()));

			stock.setWorkProgressAmt(convertToAmount(aForm.getWipAmt()));
			stock.setWorkProgressMargin(AssetGenChargeUtil.setMarginStrToDouble(aForm.getWipMargin()));

			DefaultLogger.debug(this, "<<<<<< physicalInspection: " + aForm.getPhysicalInspection());
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPhysicalInspection())) {
				if (aForm.getPhysicalInspection().equals(ICMSConstant.TRUE_VALUE)) {
					stock.setPhysicalInspectionDone(true);
					int freq = convertToInt(aForm.getPhyInsFreqNum());
					int freqCode = UIUtil.getFreqCode(aForm.getPhyInsFreqUnit());
					stock.setPhysicalInspectionFreq(freq);
					stock.setPhysicalInspectionFreqUnit(aForm.getPhyInsFreqUnit());

					Date stageDate = null;
					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getLastPhyInsDate())) {
						stageDate = UIUtil.compareDate(locale, stock.getLastPhysicalInspectDate(), aForm
								.getLastPhyInsDate());
						stock.setLastPhysicalInspectDate(stageDate);
					}
					else {
						stock.setLastPhysicalInspectDate(null);
					}

					if ((stageDate != null) && (freq > 0) && (freqCode > 0)) {
						Date nextDate = UIUtil.calculateDate(freq, freqCode, stageDate);
						stock.setNextPhysicalInspectDate(nextDate);
					}
					else if (stageDate != null) {
						stock.setNextPhysicalInspectDate(stageDate);
					}
					else {
						stock.setNextPhysicalInspectDate(null);
					}

				}
				else {
					stock.setPhysicalInspectionDone(false);
					stock.setPhysicalInspectionFreq(ICMSConstant.INT_INVALID_VALUE);
					stock.setPhysicalInspectionFreqUnit(null);
					stock.setLastPhysicalInspectDate(null);
					stock.setNextPhysicalInspectDate(null);
				}
			}
			else {
				stock.setPhysicalInspectionDone(false);
				stock.setPhysicalInspectionFreq(ICMSConstant.INT_INVALID_VALUE);
				stock.setPhysicalInspectionFreqUnit(null);
				stock.setLastPhysicalInspectDate(null);
				stock.setNextPhysicalInspectDate(null);
			}

			stock.setStockID(aForm.getStockID());
			stock.setGrossValue(((OBStock) stock).getCalculatedGrossValue());
			stock.setNetValue(((OBStock) stock).getCalculatedNetValue());
			stock.setMargin(((OBStock) stock).getCalculatedMargin());
		}
		catch (Exception e) {
			DefaultLogger.debug(this + " StockItemMapper", "error is :" + e.toString());
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		return stock;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		StockItemForm aForm = (StockItemForm) cForm;

		locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ForexHelper fr = new ForexHelper();

		HashMap stockMap = (HashMap) obj;
		OBStock stock = (OBStock) stockMap.get("obj");
		String cmsSecurityCcy = (String) stockMap.get("ccy");
		CurrencyCode cmsCcy = new CurrencyCode(cmsSecurityCcy);

		try {
			aForm = (StockItemForm) super.mapOBToForm(cForm, obj, inputs);

			currCode = stock.getValuationCurrency();
			Amount amt = stock.getCreditorAmt();
			aForm.setCreditor(convertAmtToString(amt));
			double value = 0;
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				try {
					value = fr.convertAmount(amt, cmsCcy);
					aForm.setCreditorCMS(CurrencyManager.convertToString(locale, new Amount(value, cmsCcy)));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "Caught Forex Exception!", e);
					aForm.setCreditorCMS("Forex Error");
				}
			}
			else {
				aForm.setCreditorCMS("");
			}

			aForm.setFinishedGoodsAmt(convertAmtToString(stock.getFinishGoodsAmt()));
			aForm.setFinishedGoodsMargin(AssetGenChargeUtil.setMarginDoubleToStr(stock.getFinishGoodsMargin(), locale));

			aForm.setGoodsInTransitAmt(convertAmtToString(stock.getGoodsTransitAmt()));
			aForm.setGoodsInTransitMargin(AssetGenChargeUtil
					.setMarginDoubleToStr(stock.getGoodsTransitMargin(), locale));

			amt = stock.getOtherMerchandiseAmt();
			aForm.setOthMerchandiseAmt(convertAmtToString(amt));
			aForm.setOthMerchandiseMargin(AssetGenChargeUtil.setMarginDoubleToStr(stock.getOtherMerchandiseMargin(),
					locale));

			aForm.setRawMaterialsAmt(convertAmtToString(stock.getRawMaterialAmt()));
			aForm.setRawMaterialsMargin(AssetGenChargeUtil.setMarginDoubleToStr(stock.getRawMaterialMargin(), locale));

			aForm.setStoresSparesAmt(convertAmtToString(stock.getStoresSparesAmt()));
			aForm.setStoresSparesMargin(AssetGenChargeUtil.setMarginDoubleToStr(stock.getStoresSparesMargin(), locale));

			aForm.setWipAmt(convertAmtToString(stock.getWorkProgressAmt()));
			aForm.setWipMargin(AssetGenChargeUtil.setMarginDoubleToStr(stock.getWorkProgressMargin(), locale));

			aForm.setLastPhyInsDate(DateUtil.formatDate(locale, stock.getLastPhysicalInspectDate()));
			aForm.setNextPhyInsDate(DateUtil.formatDate(locale, stock.getNextPhysicalInspectDate()));

			if (stock.getPhysicalInspectionFreq() != ICMSConstant.INT_INVALID_VALUE) {
				aForm.setPhyInsFreqNum(String.valueOf(stock.getPhysicalInspectionFreq()));
			}
			else {
				aForm.setPhyInsFreqNum("");
			}

			aForm.setPhyInsFreqUnit(stock.getPhysicalInspectionFreqUnit());
			if (stock.getPhysicalInspectionDone()) {
				aForm.setPhysicalInspection(ICMSConstant.TRUE_VALUE);
			}
			else {
				aForm.setPhysicalInspection(ICMSConstant.FALSE_VALUE);
			}

			aForm.setStockID(stock.getStockID());
			double stockMargin = stock.getCalculatedMargin();
			if (stockMargin < 0) {
				aForm.setMargin("-");
			}
			else {
				aForm.setMargin(AssetGenChargeUtil.setMarginDoubleToStr(stock.getCalculatedMargin(), locale));
			}

			amt = ((OBStock) stock).getCalculatedGrossValue();
			DefaultLogger.debug(this, "<<<<<<<<<<< getCalculatedGrossValue(): " + amt);
			aForm.setTotalStockTypeAmt(convertAmtToString(amt));

			aForm.setWeightedAverageMargin(getWeightedAverageMargin((OBStock) stock, amt, locale));

			amt = ((OBStock) stock).getCalculatedGrossValueLessCreditors(amt);
			aForm.setGrossValueValCurr(convertAmtToString(amt));
			value = 0;
			if (amt != null) {
				try {
					value = fr.convertAmount(amt, cmsCcy);
					aForm.setGrossValueCMSCurr(CurrencyManager.convertToString(locale, new Amount(value, cmsCcy)));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "Caught Forex Exception!", e);
					aForm.setGrossValueCMSCurr("Forex Error");
				}
			}
			else {
				aForm.setGrossValueCMSCurr("");
			}

			amt = ((OBStock) stock).getCalculatedNetValue();
			DefaultLogger.debug(this, "<<<<<<<<<<< getCalculatedNetValue(): " + amt);
			value = 0;
			if (amt != null) {
				try {
					value = fr.convertAmount(amt, cmsCcy);
					aForm.setNetValue(CurrencyManager.convertToString(locale, new Amount(value, cmsCcy)));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "Caught Forex Exception!", e);
					aForm.setNetValue("Forex Error");
				}
			}
			else {
				aForm.setNetValue("");
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this + " StockItemMapper", "error is :" + e.toString());
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

	private String getWeightedAverageMargin(OBStock stock, Amount totalStock, Locale locale) throws Exception {
		if (totalStock == null) {
			return "";
		}
		Amount totalStockNet = getTotalStockNet(stock);
		if (totalStockNet == null) {
			return "";
		}

		BigDecimal bd1 = totalStock.getAmountAsBigDecimal();
		BigDecimal bd2 = totalStockNet.getAmountAsBigDecimal();
		double averageMargin = bd2.divide(bd1, 2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		DefaultLogger.debug(this, "totalStock: " + bd1 + "\ttotalStockNet: " + bd2 + "\taverageMargin: "
				+ averageMargin);
		return AssetGenChargeUtil.setMarginDoubleToStr(averageMargin, locale);
	}

	private Amount getTotalStockNet(OBStock stock) throws Exception {
		Amount total = null;
		Amount amt = getStockTypeNet(stock.getFinishGoodsAmt(), stock.getFinishGoodsMargin());
		total = addStockTotal(total, amt);
		amt = getStockTypeNet(stock.getGoodsTransitAmt(), stock.getGoodsTransitMargin());
		total = addStockTotal(total, amt);
		amt = getStockTypeNet(stock.getOtherMerchandiseAmt(), stock.getOtherMerchandiseMargin());
		total = addStockTotal(total, amt);
		amt = getStockTypeNet(stock.getRawMaterialAmt(), stock.getRawMaterialMargin());
		total = addStockTotal(total, amt);
		amt = getStockTypeNet(stock.getStoresSparesAmt(), stock.getStoresSparesMargin());
		total = addStockTotal(total, amt);
		amt = getStockTypeNet(stock.getWorkProgressAmt(), stock.getWorkProgressMargin());
		total = addStockTotal(total, amt);
		return total;
	}

	private Amount getStockTypeNet(Amount stockTypeAmt, double stockTypeMargin) {
		if ((stockTypeAmt == null) || (stockTypeMargin < 0)) {
			return null;
		}
		if (stockTypeMargin == 0) {
			return new Amount(new BigDecimal(0), stockTypeAmt.getCurrencyCodeAsObject());
		}
		return stockTypeAmt.multiply(new BigDecimal(stockTypeMargin));
	}

	private Amount addStockTotal(Amount total, Amount amt) throws Exception {
		if (total == null) {
			total = amt;
		}
		else if (amt != null) {
			total = total.add(amt);
		}
		return total;
	}
}
