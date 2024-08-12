/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/debtors/DebtorsMapper.java,v 1.15 2005/08/12 11:16:03 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.debtors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.mapper.MapperUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IDebtor;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBDebtor;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.TimeFreqList;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Mapper Class for Debtors
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2005/08/12 11:16:03 $ Tag: $Name: $
 */

public class DebtorsMapper extends AbstractCommonMapper {

	protected Locale locale;

	protected CurrencyCode ccy;

	protected int iDecimalPlaces = 0;

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DebtorsForm aForm = (DebtorsForm) cForm;
		locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ICollateralTrxValue trxValue = (ICollateralTrxValue) inputs.get("serviceColObj");
		IGeneralCharge iCol = (IGeneralCharge) trxValue.getStagingCollateral();

		IDebtor debtor = iCol.getDebtor();
		if (debtor == null) {
			debtor = new OBDebtor();
		}
		debtor.setDebtAmountCurrency(aForm.getValCurrency());
		String currCode = debtor.getDebtAmountCurrency();
		try {
			try {
				int applPeriod = 0;
				applPeriod = Integer.parseInt(aForm.getPeriodApplicable());
				debtor.setApplicablePeriod(applPeriod);
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}

			// set debtors ageing
			if (aForm.getDebtorsAgeing() != null) {
				for (int i = 0; i < aForm.getDebtorsAgeing().length; i++) {
					String debtorAmt = aForm.getDebtorsAgeing()[i];
					switch (i) {
					case 0:
						debtor.setMonth1DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 1:
						debtor.setMonth2DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 2:
						debtor.setMonth3DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 3:
						debtor.setMonth4DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 4:
						debtor.setMonth5DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 5:
						debtor.setMonth6DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 6:
						debtor.setMonth7DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 7:
						debtor.setMonth8DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 8:
						debtor.setMonth9DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 9:
						debtor.setMonth10DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 10:
						debtor.setMonth11DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 11:
						debtor.setMonth12DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					case 12:
						debtor.setMonthMoreThan12DebtAmount(UIUtil.convertToAmount(locale, currCode, debtorAmt, false));
						break;
					}
				}
			}
			iCol.setDebtorMargin(AssetGenChargeUtil.setMarginStrToDouble(aForm.getMargin()));
			debtor.setGrossValue(((OBDebtor) debtor).getCalculatedGrossValue());
			Amount stockFSV = ((OBGeneralCharge) iCol).getStockNetValue();
			debtor.setNetValue(((OBGeneralCharge) iCol).getDebtorNetValue(stockFSV));

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getNonStdRevalFreqNum())
					&& AbstractCommonMapper.isEmptyOrNull(aForm.getNonStdRevalFreqUnit())) {
				debtor.setRevalFreq(ICMSConstant.INT_INVALID_VALUE);
				debtor.setRevalFreqUnit(null);
			}
			else {
				debtor.setRevalFreq(convertToInt(aForm.getNonStdRevalFreqNum()));
				debtor.setRevalFreqUnit(aForm.getNonStdRevalFreqUnit());
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getValuationDate())) {
				Date stageDate = UIUtil.compareDate(locale, debtor.getValuationDate(), aForm.getValuationDate());
				debtor.setValuationDate(stageDate);
			}
			else {
				debtor.setValuationDate(null);
			}

			iCol.setDebtor(debtor);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}

		return iCol;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DebtorsForm aForm = (DebtorsForm) cForm;

		locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IGeneralCharge iCol = (IGeneralCharge) obj;
		IDebtor debtor = iCol.getDebtor();
		String currCode = iCol.getCurrencyCode();
		String[] debtorAmtArr = new String[13];
		String[] debtorAmtArrCMS = new String[13];
		BigDecimal totalDebtorAmt = new BigDecimal(0);
		BigDecimal totalApplicableAmt = new BigDecimal(0);
		BigDecimal totalDebtorCMS = new BigDecimal(0);
		BigDecimal totalApplicableCMS = new BigDecimal(0);

		aForm.setCmsCurrency(currCode);
		ccy = new CurrencyCode(currCode);
		int freq = 0;
		int freqCode = 0;
		boolean nonFreqExist = false;

		if (debtor != null) {
			if (isEmptyOrNull(debtor.getDebtAmountCurrency())) {
				aForm.setValCurrency(currCode);
			}
			else {
				aForm.setValCurrency(debtor.getDebtAmountCurrency());
			}
			aForm.setPeriodApplicable(String.valueOf(debtor.getApplicablePeriod()));
			int appPeriod = debtor.getApplicablePeriod();
			BigDecimal[] bdResult = null;
			for (int i = 0; i < debtorAmtArr.length; i++) {
				switch (i) {
				case 0:
					bdResult = setDebtorAgeingDetails(debtor.getMonth1DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 1:
					bdResult = setDebtorAgeingDetails(debtor.getMonth2DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 2:
					bdResult = setDebtorAgeingDetails(debtor.getMonth3DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 3:
					bdResult = setDebtorAgeingDetails(debtor.getMonth4DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 4:
					bdResult = setDebtorAgeingDetails(debtor.getMonth5DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 5:
					bdResult = setDebtorAgeingDetails(debtor.getMonth6DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 6:
					bdResult = setDebtorAgeingDetails(debtor.getMonth7DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 7:
					bdResult = setDebtorAgeingDetails(debtor.getMonth8DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 8:
					bdResult = setDebtorAgeingDetails(debtor.getMonth9DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 9:
					bdResult = setDebtorAgeingDetails(debtor.getMonth10DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 10:
					bdResult = setDebtorAgeingDetails(debtor.getMonth11DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 11:
					bdResult = setDebtorAgeingDetails(debtor.getMonth12DebtAmount(), debtorAmtArr, debtorAmtArrCMS,
							totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i, appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				case 12:
					bdResult = setDebtorAgeingDetails(debtor.getMonthMoreThan12DebtAmount(), debtorAmtArr,
							debtorAmtArrCMS, totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS, i,
							appPeriod);
					totalDebtorAmt = bdResult[0];
					totalDebtorCMS = bdResult[1];
					totalApplicableAmt = bdResult[2];
					totalApplicableCMS = bdResult[3];
					break;
				}
			}
			aForm.setValuationDate(DateUtil.formatDate(locale, debtor.getValuationDate()));

			if (debtor.getRevalFreq() != ICMSConstant.INT_INVALID_VALUE) {
				aForm.setNonStdRevalFreqNum(String.valueOf(debtor.getRevalFreq()));
				freq = debtor.getRevalFreq();
				nonFreqExist = true;
			}
			else {
				aForm.setNonStdRevalFreqNum("");
			}

			aForm.setNonStdRevalFreqUnit(debtor.getRevalFreqUnit());
			if (!AbstractCommonMapper.isEmptyOrNull(debtor.getRevalFreqUnit())) {
				freqCode = UIUtil.getFreqCode(debtor.getRevalFreqUnit());
			}

		}
		else {
			aForm.setValCurrency(currCode);
		}
		try {
			DefaultLogger.debug(this, "<<<<<<< <<<<<<<< totalDebtorAmt: " + totalDebtorAmt);
			DefaultLogger.debug(this, "<<<<<<< <<<<<<<< totalDebtorCMS: " + totalDebtorCMS);
			DefaultLogger.debug(this, "<<<<<<< <<<<<<<< totalApplicableAmt: " + totalApplicableAmt);
			DefaultLogger.debug(this, "<<<<<<< <<<<<<<< totalApplicableCMS: " + totalApplicableCMS);

			aForm.setTotal(UIUtil.formatNumber(totalDebtorAmt, iDecimalPlaces, locale));
			aForm.setTotalCMS((totalDebtorCMS == null) ? AssetGenChargeUtil.FOREX_ERROR : UIUtil.formatNumber(
					totalDebtorCMS, iDecimalPlaces, locale));
			aForm.setTotalDebtors(UIUtil.formatNumber(totalApplicableAmt, iDecimalPlaces, locale));
			aForm.setTotalDebtorsCMS((totalApplicableCMS == null) ? AssetGenChargeUtil.FOREX_ERROR : UIUtil
					.formatNumber(totalApplicableCMS, iDecimalPlaces, locale));
			aForm.setDebtorsAgeing(debtorAmtArr);
			aForm.setDebtorsAgeingCMS(debtorAmtArrCMS);

			aForm.setMargin(AssetGenChargeUtil.setMarginDoubleToStr(iCol.getDebtorMargin(), locale));

			Amount stockFSV = ((OBGeneralCharge) iCol).getCalculatedStockFSV();
			if (AssetGenChargeUtil.isForexErrorAmount(stockFSV)) {
				aForm.setNegativeStockValue(AssetGenChargeUtil.FOREX_ERROR);
			}
			else if ((stockFSV != null) && (stockFSV.getAmountAsBigDecimal() != null)
					&& (stockFSV.getAmountAsBigDecimal().signum() < 0)) {
				aForm.setNegativeStockValue(UIUtil.formatNumber(stockFSV.getAmountAsBigDecimal().abs(), 0, locale));
			}
			else {
				aForm.setNegativeStockValue("-");
			}

			Amount debtorCMV = (totalApplicableCMS == null) ? AssetGenChargeUtil.getForexErrorAmount() : new Amount(
					totalApplicableCMS, ccy);
			Amount applicableDebtor = ((OBGeneralCharge) iCol).getApplicableDebtAmountLessNetStock(debtorCMV, stockFSV);
			aForm.setApplicableDebtors(AssetGenChargeUtil.convertAmtToStringWForex(applicableDebtor, locale));
			if ((applicableDebtor != null) && (applicableDebtor.getAmountAsBigDecimal() != null)
					&& (applicableDebtor.getAmountAsBigDecimal().signum() < 0)) {
				aForm.setValuationFSV("-");
			}
			else {
				aForm.setValuationFSV(AssetGenChargeUtil.convertAmtToStringWForex(((OBGeneralCharge) iCol)
						.getCalculatedDebtorFSV(stockFSV), locale));
			}

			if (!AbstractCommonMapper.isEmptyOrNull(iCol.getCollateralLocation())) {
				ICollateralParameter parameter = null;
				try {
					parameter = CollateralProxyFactory.getProxy().getCollateralParameter(iCol.getCollateralLocation(),
							ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE);
				}
				catch (Exception e) {
					DefaultLogger.debug(this + " GeneralChargeSubTypeMapper",
							"There is no parameter found in the database");
				}
				String stdRevalFreq = "";
				if (parameter != null) {
					if (parameter.getValuationFrequencyUnit() != null) {
						if (!nonFreqExist) {
							freq = parameter.getValuationFrequency();
							freqCode = UIUtil.getFreqCode(parameter.getValuationFrequencyUnit());
						}
						stdRevalFreq = String.valueOf(parameter.getValuationFrequency());
						stdRevalFreq += " "
								+ TimeFreqList.getInstance().getTimeFreqItem(parameter.getValuationFrequencyUnit());
					}
				}
				aForm.setStdRevalFreq(stdRevalFreq);
			}
			if (debtor != null) {
				Date revaluationDate = UIUtil.calculateDate(freq, freqCode, debtor.getValuationDate());
				aForm.setRevaluationDate(DateUtil.formatDate(locale, revaluationDate));
			}
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
	}

	private BigDecimal[] setDebtorAgeingDetails(Amount debtorAmt, String[] debtorAmtArr, String[] debtorAmtArrCMS,
			BigDecimal totalDebtorAmt, BigDecimal totalDebtorCMS, BigDecimal totalApplicableAmt,
			BigDecimal totalApplicableCMS, int index, int appPeriod) {
		if (debtorAmt != null) {
			debtorAmtArr[index] = MapperUtil.mapDoubleToString(debtorAmt.getAmountAsDouble(), iDecimalPlaces, locale);
			totalDebtorAmt = totalDebtorAmt.add(debtorAmt.getAmountAsBigDecimal());
			BigDecimal value = AssetGenChargeUtil.getCMSAmountAsBigDecimal(debtorAmt, ccy, locale);
			if (value != null) {
				if (totalDebtorCMS != null) {
					totalDebtorCMS = totalDebtorCMS.add(value);
				}
				debtorAmtArrCMS[index] = MapperUtil.mapDoubleToString(value.doubleValue(), iDecimalPlaces, locale);
			}
			else {
				totalDebtorCMS = null;
				debtorAmtArrCMS[index] = AssetGenChargeUtil.FOREX_ERROR;
			}
			if (index < appPeriod) {
				totalApplicableAmt = totalApplicableAmt.add(debtorAmt.getAmountAsBigDecimal());
				if ((totalApplicableCMS != null) && (value != null)) {
					totalApplicableCMS = totalApplicableCMS.add(value);
				}
				else {
					totalApplicableCMS = null;
				}
			}
		}

		DefaultLogger.debug(this, index + " debtorAmt: " + debtorAmt + "\t<<<<<<< totalDebtorAmt: " + totalDebtorAmt);
		DefaultLogger.debug(this, "<<<<<<< totalDebtorCMS: " + totalDebtorCMS);
		DefaultLogger.debug(this, "<<<<<<< totalApplicableAmt: " + totalApplicableAmt);
		DefaultLogger.debug(this, "<<<<<<< totalApplicableCMS: " + totalApplicableCMS);

		return new BigDecimal[] { totalDebtorAmt, totalDebtorCMS, totalApplicableAmt, totalApplicableCMS };
	}

	protected int convertToInt(String strAmount) throws Exception {
		int iAmount = ICMSConstant.INT_INVALID_VALUE;
		if (!AbstractCommonMapper.isEmptyOrNull(strAmount)) {
			iAmount = Integer.parseInt(strAmount);
		}
		return iAmount;
	}

}
