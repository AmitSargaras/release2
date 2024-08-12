/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/drawingpower/DrawingPowerMapper.java,v 1.29 2006/09/01 11:37:04 nkumar Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.drawingpower;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.mapper.MapperUtil;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IDebtor;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.29 $
 * @since $Date: 2006/09/01 11:37:04 $ Tag: $Name: $
 */

public class DrawingPowerMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DrawingPowerForm aForm = (DrawingPowerForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		HashMap limitMap = (HashMap) inputs.get("limitMap");

		ICollateralTrxValue trxValue = (ICollateralTrxValue) inputs.get("serviceColObj");
		IGeneralCharge iCol = null;
		try {
			iCol = (IGeneralCharge) AccessorUtil.deepClone(trxValue.getStagingCollateral());
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}

		if (isEmptyOrNull(aForm.getBankParticipatingShare())) {
			iCol.setBankShare(ICMSConstant.DOUBLE_INVALID_VALUE);
		}
		else {
			iCol.setBankShare(Double.parseDouble(aForm.getBankParticipatingShare()));
		}
		iCol.setMargin(AssetGenChargeUtil.setMarginStrToDouble(aForm.getCrpMargin()));
		iCol.setOtherRemarks(aForm.getOtherRemarks());

		IValuation val = iCol.getValuation();
		if (val == null) {
			val = new OBValuation();
		}

		val.setCurrencyCode(aForm.getValuationCurrency());
		iCol.setValuation(val);

		// Setting security to limit details
		String[] limitID = aForm.getLimitID();
		List limitIDList = new ArrayList();
		if (limitID != null) {
			limitIDList = Arrays.asList(limitID);
		}
		String[] applicableLimitAmt = aForm.getAppliedActivatedLimitBase();
		String[] releasedLimitAmt = aForm.getReleasedLimitBase();

		ICollateralLimitMap[] colLimitMap = iCol.getCollateralLimits();
		if (colLimitMap != null) {
			for (int i = 0; i < colLimitMap.length; i++) {
				ICollateralLimitMap obj = colLimitMap[i];
				String id = String.valueOf(obj.getLimitID());

				ILimit limit = (ILimit) limitMap.get(id);
				if (limit != null) {
					String limitCcy = (limit.getApprovedLimitAmount() != null) ? limit.getApprovedLimitAmount()
							.getCurrencyCode() : null;
					int index = limitIDList.indexOf(id);
					if (index >= 0) {
						if (isEmptyOrNull(applicableLimitAmt[index])) {
							obj.setAppliedLimitAmount(null);
							obj.setIsAppliedLimitAmountIncluded(false);
						}
						else {
							try {
								obj.setAppliedLimitAmount(CurrencyManager.convertToAmount(locale, limitCcy,
										AssetGenChargeUtil.removeBracket(applicableLimitAmt[index])));
								obj.setIsAppliedLimitAmountIncluded(!hasBracket(applicableLimitAmt[index]));
							}
							catch (Exception e) {
								throw new MapperException(e.getMessage());
							}
						}
						if (isEmptyOrNull(releasedLimitAmt[index])) {
							obj.setReleasedLimitAmount(null);
							obj.setIsReleasedLimitAmountIncluded(false);
						}
						else {
							try {
								obj.setReleasedLimitAmount(CurrencyManager.convertToAmount(locale, limitCcy,
										AssetGenChargeUtil.removeBracket(releasedLimitAmt[index])));
								obj.setIsReleasedLimitAmountIncluded(!hasBracket(releasedLimitAmt[index]));
							}
							catch (Exception e) {
								throw new MapperException(e.getMessage());
							}
						}
					}
				}
				colLimitMap[i] = obj;
			}
		}
		iCol.setCollateralLimits(colLimitMap);

		return iCol;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DrawingPowerForm aForm = (DrawingPowerForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		HashMap objMap = (HashMap) obj;

		OBGeneralCharge obCol = (OBGeneralCharge) objMap.get("col");
		Collection colLimitMap = (Collection) objMap.get("colLimitMap");
		HashMap limitObjMap = (HashMap) objMap.get("limitMap");
		BigDecimal totalApprovedLimit = (BigDecimal) objMap.get("totalApprovedLimit");

		CurrencyCode cmsCurrCode = new CurrencyCode(obCol.getCurrencyCode());
		try {
			mapOBToFormSecurityLimitDetails(aForm, cmsCurrCode, colLimitMap, limitObjMap, locale);

			// stock summary
			Amount stockGrossAmt = obCol.getStockGrossValue();
			aForm.setGrossStockVal(AssetGenChargeUtil.convertAmtToStringWForex(stockGrossAmt, locale));
			DefaultLogger.debug(this, "getGrossStockVal : " + stockGrossAmt);

			Amount stockTotalCreditorsAmt = obCol.getTotalStockCreditors();
			aForm.setTotalCreditors(AssetGenChargeUtil.convertAmtToStringWForex(stockTotalCreditorsAmt, locale));
			DefaultLogger.debug(this, "getTotalCreditors : " + stockTotalCreditorsAmt);

			Amount stockTotalValidInsrAmt = obCol.getTotalValidStockInsrAmount();
			aForm.setTotalValidInsrStocks(AssetGenChargeUtil.convertAmtToStringWForex(stockTotalValidInsrAmt, locale));
			DefaultLogger.debug(this, "getTotalValidInsrStocks : " + stockTotalValidInsrAmt);

			Amount stockTotalRecoverableAmt = obCol.getTotalRecoverableStockAmount();
			if (AssetGenChargeUtil.isAmountNegativeValue(stockTotalRecoverableAmt)) {
				aForm.setTotalRecoverInsrAmt("-");
			}
			else {
				aForm.setTotalRecoverInsrAmt(AssetGenChargeUtil.convertAmtToStringWForex(stockTotalRecoverableAmt,
						locale));
			}
			DefaultLogger.debug(this, "getTotalRecoverInsrAmt : " + stockTotalRecoverableAmt);

			Amount stockGrossAmtLessCreditors = null;
			Amount stockInsrShortFallAmt = null;
			if (AssetGenChargeUtil.isForexErrorAmount(stockGrossAmt)) {
				stockGrossAmtLessCreditors = AssetGenChargeUtil.getForexErrorAmount();
				stockInsrShortFallAmt = AssetGenChargeUtil.getForexErrorAmount();
			}
			else if (stockGrossAmt != null) {
				if (AssetGenChargeUtil.isForexErrorAmount(stockTotalCreditorsAmt)) {
					stockGrossAmtLessCreditors = AssetGenChargeUtil.getForexErrorAmount();
				}
				else {
					stockGrossAmtLessCreditors = (stockTotalCreditorsAmt == null) ? stockGrossAmt : stockGrossAmt
							.subtract(stockTotalCreditorsAmt);
				}
				if (AssetGenChargeUtil.isForexErrorAmount(stockTotalValidInsrAmt)) {
					stockInsrShortFallAmt = AssetGenChargeUtil.getForexErrorAmount();
				}
				else {
					stockInsrShortFallAmt = (stockTotalValidInsrAmt == null) ? stockGrossAmt : stockGrossAmt
							.subtract(stockTotalValidInsrAmt);
				}
			}
			aForm.setGrossStockValLessCreditors(AssetGenChargeUtil.convertAmtToStringWForex(stockGrossAmtLessCreditors,
					locale));
			DefaultLogger.debug(this, "getGrossStockValLessCreditors : " + stockGrossAmtLessCreditors);

			if (AssetGenChargeUtil.isAmountNegativeValue(stockInsrShortFallAmt)) {
				aForm.setInsrShortfallVsStocks("-");
			}
			else {
				aForm.setInsrShortfallVsStocks(AssetGenChargeUtil.convertAmtToStringWForex(stockInsrShortFallAmt,
						locale));
			}
			DefaultLogger.debug(this, "getInsrShortfallVsStocks : " + stockInsrShortFallAmt);

			Amount stockNetValue = obCol.getStockNetValue();
			if (AssetGenChargeUtil.isAmountNegativeValue(stockNetValue)) {
				aForm.setNetValStocksMargin("-");
			}
			else {
				aForm.setNetValStocksMargin(AssetGenChargeUtil.convertAmtToStringWForex(stockNetValue, locale));
			}
			DefaultLogger.debug(this, "getNetValStocksMargin : " + stockNetValue);
			// DefaultLogger.debug(this, "getNetValStocksMargin - abs value: " +
			// stockNetValue.getAmountAsBigDecimal().abs());

			// debtor summary
			Amount debtorGrossValue = obCol.getDebtorGrossValue();
			aForm.setGrossDebtors(AssetGenChargeUtil.convertAmtToStringWForex(debtorGrossValue, locale));
			DefaultLogger.debug(this, "getGrossDebtors : " + debtorGrossValue);

			Amount applicableDebtAmt = obCol.getApplicableDebtAmount();
			aForm.setGrossDebtorsLessOverdue(AssetGenChargeUtil.convertAmtToStringWForex(applicableDebtAmt, locale));
			DefaultLogger.debug(this, "setGrossDebtorsLessOverdue : " + applicableDebtAmt);

			Amount debtorOverdueDebtAmt = null;
			if (debtorGrossValue != null) {
				debtorOverdueDebtAmt = (applicableDebtAmt == null) ? debtorGrossValue : debtorGrossValue
						.subtract(applicableDebtAmt);
			}
			aForm.setOverdue(AssetGenChargeUtil.convertAmtToStringWForex(debtorOverdueDebtAmt, locale));
			DefaultLogger.debug(this, "getOverdue : " + debtorOverdueDebtAmt);

			if (AssetGenChargeUtil.isForexErrorAmount(stockNetValue)) {
				aForm.setNegativeStockValue(AssetGenChargeUtil.FOREX_ERROR);
			}
			else if (AssetGenChargeUtil.isAmountNegativeValue(stockNetValue)) {
				aForm.setNegativeStockValue(UIUtil.formatNumberPositiveVal(stockNetValue.getAmountAsBigDecimal().abs(),
						0, locale));
			}
			else {
				aForm.setNegativeStockValue("-");
			}

			Amount amt = obCol.getApplicableDebtAmountLessNetStock(applicableDebtAmt, stockNetValue);
			aForm.setApplicableDebtors(AssetGenChargeUtil.convertAmtToStringWForex(amt, locale));

			Amount debtorNetValue = obCol.getDebtorNetValue(stockNetValue);
			if (AssetGenChargeUtil.isAmountNegativeValue(debtorNetValue)) {
				aForm.setNetDebtors("-");
			}
			else {
				aForm.setNetDebtors(AssetGenChargeUtil.convertAmtToStringWForex(debtorNetValue, locale));
			}
			DefaultLogger.debug(this, "setNetDebtors : " + debtorNetValue);

			// fao summary
			amt = obCol.getFAOGrossValue();
			aForm.setGrossFAO(AssetGenChargeUtil.convertAmtToStringWForex(amt, locale));
			DefaultLogger.debug(this, "getGrossFAO : " + amt);
			;

			Amount faoNetValue = obCol.getFAONetValue();
			aForm.setNetFAOMargin(AssetGenChargeUtil.convertAmtToStringWForex(faoNetValue, locale));
			DefaultLogger.debug(this, "getNetFAOMargin : " + faoNetValue);

			// Calculation of FSV, DP and DP (without netting insurance)
			// Common for all the 3
			DefaultLogger.debug(this, "<<<<<<<<<<< bank share: " + obCol.getBankShare());
			if (obCol.getBankShare() != ICMSConstant.DOUBLE_INVALID_VALUE) {
				aForm.setBankParticipatingShare(MapperUtil.mapDoubleToString(obCol.getBankShare(), 2, locale));
			}
			else {
				aForm.setBankParticipatingShare("");
			}
			double crpMargin = ICMSConstant.DOUBLE_INVALID_VALUE;
			if (obCol.getMargin() == ICMSConstant.DOUBLE_INVALID_VALUE) {
				if (!AbstractCommonMapper.isEmptyOrNull(obCol.getCollateralLocation())) {
					ICollateralParameter parameter = null;
					try {
						parameter = CollateralProxyFactory.getProxy().getCollateralParameter(
								obCol.getCollateralLocation(), ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE);
					}
					catch (Exception e) {
						DefaultLogger.debug(this, "There is no parameter found in the database");
					}
					if (parameter != null) {
						crpMargin = parameter.getThresholdPercent();
						// if obCol.getMargin is invalid value... use the
						// collateral parameter setting
						obCol.setMargin(crpMargin);
						DefaultLogger.debug(this, "security parameter getThresholdPercent: " + crpMargin);
						aForm.setCrpMargin(MapperUtil.mapDoubleToString(crpMargin, 0, locale));
					}
					else {
						aForm.setCrpMargin("");
					}
				}
				else {
					aForm.setCrpMargin("");
				}
			}
			else {
				crpMargin = obCol.getMargin();
				aForm.setCrpMargin(AssetGenChargeUtil.setMarginDoubleToStr(obCol.getMargin(), locale));
			}

			if (obCol.getValuation() != null) {
				// CMS-2705: take the lastest valuation date from stocks and
				// debtor

				Map stocks = obCol.getStocks();
				IDebtor debtor = obCol.getDebtor();

				ArrayList aListOfDates = new ArrayList();
				Date aDate = new Date();

				if (null != stocks) {
					Iterator i = stocks.values().iterator();
					while (i.hasNext()) {
						IStock stock = (IStock) i.next();
						aDate = stock.getValuationDate();
						if (null != aDate) {
							aListOfDates.add(aDate);
						}
					}
				}
				if (null != debtor) {
					aDate = debtor.getValuationDate();
					if (null != aDate) {
						aListOfDates.add(aDate);
					}
				}

				Date[] arrayDates = (Date[]) aListOfDates.toArray(new Date[0]);
				Arrays.sort(arrayDates);

				if (arrayDates.length > 0) {
					aForm.setEvalDateFSV(DateUtil.formatDate(locale, arrayDates[arrayDates.length - 1]));
					// CMS-2705 End
					// aForm.setEvalDateFSV(DateUtil.formatDate(locale,
					// obCol.getValuation().getFSVEvaluationDate()));
				}

				aForm.setValuationCurrency(obCol.getValuation().getCurrencyCode());
			}

			aForm.setOtherRemarks(obCol.getOtherRemarks());

			// FSV Calculation
			Amount totalNetValue = obCol.getTotalNetValue(stockGrossAmt, stockTotalCreditorsAmt, stockNetValue,
					faoNetValue, debtorNetValue);
			aForm.setTotalStockDebtorsFAO(AssetGenChargeUtil.convertAmtToStringWForex(totalNetValue, locale));
			DefaultLogger.debug(this, "getTotalStockDebtorsFAO : " + totalNetValue);

			Amount bankShareAmt = obCol.getBankShareAmount(totalNetValue);
			aForm.setBankTotalStockDebtorsFAO(AssetGenChargeUtil.convertAmtToStringWForex(bankShareAmt, locale));
			DefaultLogger.debug(this, "getBankTotalStockDebtorsFAO : " + bankShareAmt);

			Amount totalNetFSV = obCol.getDrawingPowerNetAmount(bankShareAmt);
			aForm.setTotalNetFSV(AssetGenChargeUtil.convertAmtToStringWForex(totalNetFSV, locale));
			DefaultLogger.debug(this, "setTotalNetFSV : " + totalNetFSV);

			// Common fields for drawing power With or Without neting insurance
			Amount totalAppliedLimitAmt = obCol.getTotalAppliedLimitAmount();
			aForm.setTotalMaxApplApprLimit(AssetGenChargeUtil.convertAmtToStringWForex(totalAppliedLimitAmt, locale));
			DefaultLogger.debug(this, "getTotalMaxApplApprLimits : " + totalAppliedLimitAmt);

			Amount releasedLimit = obCol.getTotalReleasedLimitAmount();
			aForm.setTotalExistReleasedLimit(AssetGenChargeUtil.convertAmtToStringWForex(releasedLimit, locale));
			DefaultLogger.debug(this, "getTotalExistReleasedLimit : " + releasedLimit);

			// drawing power calculation
			Amount drawingPowerGross = obCol.getCalculatedDrawingPowerLessInsrGrossAmount(stockGrossAmt,
					stockTotalCreditorsAmt, stockNetValue, debtorNetValue, stockTotalRecoverableAmt);
			aForm.setGrossAmtDrawingPower(AssetGenChargeUtil.convertAmtToStringWForex(drawingPowerGross, locale));

			Amount drawingPowerBankShareAmt = obCol.getBankShareAmount(drawingPowerGross);
			aForm.setBankGrossAmtDrawingPower(AssetGenChargeUtil.convertAmtToStringWForex(drawingPowerBankShareAmt,
					locale));

			Amount drawingPowerNetAmt = obCol.getDrawingPowerNetAmount(drawingPowerBankShareAmt);
			aForm.setNetAmtDrawingPower(AssetGenChargeUtil.convertAmtToStringWForex(drawingPowerNetAmt, locale));

			Amount drawingPower = obCol.getDrawingPower(drawingPowerNetAmt, totalAppliedLimitAmt);
			aForm.setAmtToReleased(AssetGenChargeUtil.convertAmtToStringWForex(drawingPower, locale));
			DefaultLogger.debug(this, "setAmtToReleased : " + drawingPower);

			aForm.setDeficit(getDeficit(drawingPower, releasedLimit, locale));

			// Drawing Power Calculation (Without netting insurance)
			Amount drawingPowerGrossWOIns = obCol.getCalculatedDrawingPowerGrossAmount(stockGrossAmt,
					stockTotalCreditorsAmt, stockNetValue, debtorNetValue);
			aForm.setGrossAmtDrawingPowerWOIns(AssetGenChargeUtil.convertAmtToStringWForex(drawingPowerGrossWOIns,
					locale));

			Amount drawingPowerBankGrossWOIns = obCol.getBankShareAmount(drawingPowerGrossWOIns);
			aForm.setBankGrossAmtDrawingPowerWOIns(AssetGenChargeUtil.convertAmtToStringWForex(
					drawingPowerBankGrossWOIns, locale));

			Amount drawingPowerNetAmtWOIns = obCol.getDrawingPowerNetAmount(drawingPowerBankGrossWOIns);
			aForm.setNetAmtDrawingPowerWOIns(AssetGenChargeUtil.convertAmtToStringWForex(drawingPowerNetAmtWOIns,
					locale));

			Amount drawingPowerWOIns = obCol.getDrawingPower(drawingPowerNetAmtWOIns, totalAppliedLimitAmt);
			aForm.setAmtToReleasedWOIns(AssetGenChargeUtil.convertAmtToStringWForex(drawingPowerWOIns, locale));
			DefaultLogger.debug(this, "setAmtToReleasedWOIns : " + drawingPowerWOIns);

			aForm.setDeficitWOIns(getDeficit(drawingPowerWOIns, releasedLimit, locale));

			if (totalApprovedLimit == null) {
				aForm.setTotalApprovedLimit(AssetGenChargeUtil.FOREX_ERROR);
			}
			else {
				aForm.setTotalApprovedLimit(UIUtil.formatNumber(totalApprovedLimit, 0, locale));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "limitMap", "java.util.HashMap", SERVICE_SCOPE }, });
	}

	private String convertAmtToString(Locale locale, Amount amt) throws Exception {
		if ((amt != null) && (amt.getCurrencyCode() != null)) {
			return CurrencyManager.convertToString(locale, amt);
		}

		return "";
	}

	private void mapOBToFormSecurityLimitDetails(DrawingPowerForm aForm, CurrencyCode cmsCurrCode,
			Collection colLimitMap, HashMap limitObjMap, Locale locale) throws Exception {
		String[] appliedActivatedLimitBase = new String[0];
		String[] appliedActivatedLimitCMS = new String[0];
		String[] releasedLimitBase = new String[0];
		String[] releasedLimitCMS = new String[0];
		ILimitProxy proxy = LimitProxyFactory.getProxy();
		BigDecimal totalAppliedActivatedLimit = new BigDecimal(0);
		BigDecimal totalReleasedLimit = new BigDecimal(0);

		DefaultLogger.debug(this, "colLimitMap.size: "
				+ (colLimitMap == null ? "null" : String.valueOf(colLimitMap.size())));

		if (colLimitMap != null) {
			Iterator itr = colLimitMap.iterator();
			appliedActivatedLimitBase = new String[colLimitMap.size()];
			appliedActivatedLimitCMS = new String[colLimitMap.size()];
			releasedLimitBase = new String[colLimitMap.size()];
			releasedLimitCMS = new String[colLimitMap.size()];
			int count = 0;
			Amount amt = null;
			BigDecimal bd = null;
			ILimit limit = null;
			ICoBorrowerLimit coLimit = null;
			while (itr.hasNext()) {
				ICollateralLimitMap limitMap = null;
				Object tempObj = itr.next();
				if (tempObj instanceof ICollateralLimitMap) {
					limitMap = (ICollateralLimitMap) tempObj;
				}
				else {
					limitMap = (ICollateralLimitMap) ((CompareResult) tempObj).getObj();
				}

				if (limitMap.getCustomerCategory().equals("MB")) {

					limit = (ILimit) limitObjMap.get(String.valueOf(limitMap.getLimitID()));
					DefaultLogger.debug(this, " limit id " + limit.getLimitID());
				}
				else {

					coLimit = proxy.getCoBorrowerLimit(limitMap.getCoBorrowerLimitID());
					limit = (ILimit) limitObjMap.get(String.valueOf(coLimit.getOuterLimitID()));
					DefaultLogger.debug(this, " Colimit id " + limit.getLimitID());
				}

				boolean isInnerLimit = ((limit.getOuterLimitID() == ICMSConstant.LONG_INVALID_VALUE) || (limit
						.getOuterLimitID() == 0)) ? false : true;
				Amount approvedLimit = limit.getApprovedLimitAmount();
				BigDecimal cmsApprovedLimit = null;
				if (approvedLimit != null) {
					cmsApprovedLimit = AssetGenChargeUtil.getCMSAmountAsBigDecimal(approvedLimit, cmsCurrCode, locale);
				}

				amt = limitMap.getAppliedLimitAmount();
				if (amt != null) {
					appliedActivatedLimitBase[count] = setBracketforAmt(convertAmtToString(locale, amt), !limitMap
							.getIsAppliedLimitAmountIncluded());
					bd = AssetGenChargeUtil.getCMSAmountAsBigDecimal(amt, cmsCurrCode, locale);
					if (bd != null) {
						appliedActivatedLimitCMS[count] = setBracketforAmt(UIUtil.formatNumber(bd, 0, locale),
								!limitMap.getIsAppliedLimitAmountIncluded());
						if ((totalAppliedActivatedLimit != null) && limitMap.getIsAppliedLimitAmountIncluded()
								&& !ICMSConstant.HOST_STATUS_DELETE.equals(limitMap.getSCIStatus())) {
							totalAppliedActivatedLimit = totalAppliedActivatedLimit.add(bd);
						}
					}
					else {
						appliedActivatedLimitCMS[count] = AssetGenChargeUtil.FOREX_ERROR;
						totalAppliedActivatedLimit = null;
					}
				}
				else {
					if (approvedLimit != null) {
						appliedActivatedLimitBase[count] = setBracketforAmt(convertAmtToString(locale, approvedLimit),
								isInnerLimit);
						if (cmsApprovedLimit != null) {
							appliedActivatedLimitCMS[count] = setBracketforAmt(UIUtil.formatNumber(cmsApprovedLimit, 0,
									locale), isInnerLimit);
							if ((totalAppliedActivatedLimit != null) && !isInnerLimit
									&& !ICMSConstant.HOST_STATUS_DELETE.equals(limitMap.getSCIStatus())) {
								totalAppliedActivatedLimit = totalAppliedActivatedLimit.add(cmsApprovedLimit);
							}
						}
						else {
							appliedActivatedLimitCMS[count] = AssetGenChargeUtil.FOREX_ERROR;
							totalAppliedActivatedLimit = null;
						}
					}
					else {
						appliedActivatedLimitBase[count] = "";
						appliedActivatedLimitCMS[count] = "";
					}
				}

				amt = limitMap.getReleasedLimitAmount();
				if (amt != null) {
					releasedLimitBase[count] = setBracketforAmt(convertAmtToString(locale, amt), !limitMap
							.getIsReleasedLimitAmountIncluded());
					bd = AssetGenChargeUtil.getCMSAmountAsBigDecimal(amt, cmsCurrCode, locale);
					if (bd != null) {
						releasedLimitCMS[count] = setBracketforAmt(UIUtil.formatNumber(bd, 0, locale), !limitMap
								.getIsReleasedLimitAmountIncluded());
						if ((totalReleasedLimit != null) && limitMap.getIsReleasedLimitAmountIncluded()
								&& !ICMSConstant.HOST_STATUS_DELETE.equals(limitMap.getSCIStatus())) {
							totalReleasedLimit = totalReleasedLimit.add(bd);
						}
					}
					else {
						releasedLimitCMS[count] = AssetGenChargeUtil.FOREX_ERROR;
						totalReleasedLimit = null;
					}
				}
				else {
					if (approvedLimit != null) {
						releasedLimitBase[count] = setBracketforAmt(convertAmtToString(locale, approvedLimit),
								isInnerLimit);
						if (cmsApprovedLimit != null) {
							releasedLimitCMS[count] = setBracketforAmt(
									UIUtil.formatNumber(cmsApprovedLimit, 0, locale), isInnerLimit);
							if ((totalReleasedLimit != null) && !isInnerLimit
									&& !ICMSConstant.HOST_STATUS_DELETE.equals(limitMap.getSCIStatus())) {
								totalReleasedLimit = totalReleasedLimit.add(cmsApprovedLimit);
							}
						}
						else {
							releasedLimitCMS[count] = AssetGenChargeUtil.FOREX_ERROR;
							totalReleasedLimit = null;
						}
					}
					else {
						releasedLimitBase[count] = "";
						releasedLimitCMS[count] = "";
					}
				}
				count++;
			}
		}
		aForm.setAppliedActivatedLimitBase(appliedActivatedLimitBase);
		aForm.setAppliedActivatedLimitCMS(appliedActivatedLimitCMS);
		aForm.setReleasedLimitBase(releasedLimitBase);
		aForm.setReleasedLimitCMS(releasedLimitCMS);

		aForm.setTotalAppliedActLimit(UIUtil.formatNumber(totalAppliedActivatedLimit, 0, locale));
		aForm.setTotalReleasedLimit(UIUtil.formatNumber(totalReleasedLimit, 0, locale));
	}

	private String getDeficit(Amount drawingPower, Amount releasedLimit, Locale locale) throws Exception {
		if (AssetGenChargeUtil.isForexErrorAmount(drawingPower) || AssetGenChargeUtil.isForexErrorAmount(releasedLimit)) {
			return AssetGenChargeUtil.FOREX_ERROR;
		}
		else if ((drawingPower != null) && (releasedLimit != null)
				&& (releasedLimit.getAmount() > drawingPower.getAmount())) {
			return UIUtil.formatNumber(releasedLimit.getAmountAsBigDecimal().subtract(
					drawingPower.getAmountAsBigDecimal()), 0, locale);
		}
		return "-";
	}

	private String setBracketforAmt(String strAmt, boolean isBracket) {
		if (isBracket) {
			return "(" + strAmt + ")";
		}
		return strAmt;
	}

	private boolean hasBracket(String strAmt) {
		if ((strAmt != null) && (strAmt.indexOf("(") >= 0)) {
			return true;
		}
		return false;
	}
}
