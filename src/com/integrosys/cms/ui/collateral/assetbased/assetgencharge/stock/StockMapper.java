/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/stock/StockMapper.java,v 1.13 2005/08/12 08:20:11 lyng Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2005/08/12 08:20:11 $ Tag: $Name: $
 */

public class StockMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		StockForm aForm = (StockForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ICollateralTrxValue trxValue = (ICollateralTrxValue) inputs.get("serviceColObj");
		IGeneralCharge iCol = (IGeneralCharge) trxValue.getStagingCollateral();

		if (isEmptyOrNull(aForm.getInsCoverNum())) {
			iCol.setStockInsrGracePeriod(ICMSConstant.INT_INVALID_VALUE);
		}
		else {
			iCol.setStockInsrGracePeriod(Integer.parseInt(aForm.getInsCoverNum()));
		}
		iCol.setStockInsrGracePeriodFreq(aForm.getInsCoverUnit());

		if (aForm.getEvent().endsWith(StockAction.EVENT_DELETE_ITEM)) {
			HashMap returnMap = new HashMap();
			returnMap.put("col", iCol);
			returnMap.put("deleteStocks", aForm.getStockItemDelete());
			returnMap.put("deleteInsurances", aForm.getInsuranceItemDelete());
			return returnMap;
		}

		return iCol;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		StockForm aForm = (StockForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String hasException = "false";
		HashMap objMap = (HashMap) obj;
		IGeneralCharge iCol = (IGeneralCharge) objMap.get("col");
		Collection stockSummaryList = (Collection) objMap.get("stockSummaryList");

		if (aForm.getEvent().endsWith(StockAction.EVENT_DELETE_ITEM)) {
			hasException = (String) objMap.get("hasException");
		}

		if ((hasException == null) || hasException.equals("false")) {
			DefaultLogger.debug(this, "<<<<<<< Has Exception: false");
			aForm.setStockItemDelete(new String[0]);
			aForm.setInsuranceItemDelete(new String[0]);
			aForm.setCmsSecCurreny(iCol.getCurrencyCode());

			if ((stockSummaryList != null) && !stockSummaryList.isEmpty()) {
				HashMap stockTotal = AssetGenChargeUtil.getStockSummaryTotal(stockSummaryList);
				try {
					BigDecimal bd = (BigDecimal) stockTotal.get("totalGross");
					if (bd != null) {
						aForm.setTotalGrossValCMS(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalGrossValCMS(AssetGenChargeUtil.FOREX_ERROR);
					}
					bd = (BigDecimal) stockTotal.get("totalGrossCreditor");
					if (bd != null) {
						aForm.setTotalGrossCreditor(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalGrossCreditor(AssetGenChargeUtil.FOREX_ERROR);
					}
					bd = (BigDecimal) stockTotal.get("totalNetValue");
					if (bd != null) {
						aForm.setTotalNetValue(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalNetValue(AssetGenChargeUtil.FOREX_ERROR);
					}
					bd = (BigDecimal) stockTotal.get("totalInsuredAmt");
					if (bd != null) {
						aForm.setTotalStockInsuredAmt(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalStockInsuredAmt(AssetGenChargeUtil.FOREX_ERROR);
					}
					bd = (BigDecimal) stockTotal.get("totalEffectiveAmt");
					if (bd != null) {
						aForm.setTotalStockEffectiveAmt(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalStockEffectiveAmt(AssetGenChargeUtil.FOREX_ERROR);
					}
					bd = (BigDecimal) stockTotal.get("totalRecoverableAmt");
					if (bd != null) {
						aForm.setTotalInsuranceCover(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalInsuranceCover(AssetGenChargeUtil.FOREX_ERROR);
					}
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			else {
				aForm.setTotalGrossValCMS("");
				aForm.setTotalGrossCreditor("");
				aForm.setTotalNetValue("");
				aForm.setTotalStockInsuredAmt("");
				aForm.setTotalStockEffectiveAmt("");
				aForm.setTotalInsuranceCover("");
			}

			HashMap insuranceTotal = AssetGenChargeUtil.getInsuranceSummaryTotal(iCol, IInsurancePolicy.STOCK, locale);
			BigDecimal totalInsuredAmt = (BigDecimal) insuranceTotal.get("totalInsuredAmt");
			BigDecimal totalInsuredCoverAmt = (BigDecimal) insuranceTotal.get("totalInsuredCoverAmt");

			try {
				if (totalInsuredAmt != null) {
					aForm.setTotalInsuredAmt(UIUtil.formatNumber(totalInsuredAmt, 0, locale));
				}
				else {
					aForm.setTotalInsuredAmt(AssetGenChargeUtil.FOREX_ERROR);
				}
				if (totalInsuredCoverAmt != null) {
					aForm.setTotalValidCoverInsAmt(UIUtil.formatNumber(totalInsuredCoverAmt, 0, locale));
				}
				else {
					aForm.setTotalValidCoverInsAmt(AssetGenChargeUtil.FOREX_ERROR);
				}
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}

			if (iCol.getStockInsrGracePeriod() != ICMSConstant.INT_INVALID_VALUE) {
				aForm.setInsCoverNum(String.valueOf(iCol.getStockInsrGracePeriod()));
			}
			else {
				aForm.setInsCoverNum("");
			}

			aForm.setInsCoverUnit(iCol.getStockInsrGracePeriodFreq());

			Amount cmvAmt = ((OBGeneralCharge) iCol).getCalculatedStockCMV();
			Amount fsvAmt = ((OBGeneralCharge) iCol).getCalculatedStockFSV();
			if ((fsvAmt != null) && (fsvAmt.getAmountAsBigDecimal() != null)
					&& (fsvAmt.getAmountAsBigDecimal().signum() < 0)) {
				aForm.setMargin("-");
			}
			else {
				aForm.setMargin(AssetGenChargeUtil.getSummaryAverageMargin(cmvAmt, fsvAmt, locale));
			}

			try {
				aForm.setValuationCMVStock(UIUtil.formatAmount(cmvAmt, 0, locale, false));
				aForm.setValuationFSVStock(UIUtil.formatAmountPositiveVal(fsvAmt, 0, locale, false));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
	}

}
