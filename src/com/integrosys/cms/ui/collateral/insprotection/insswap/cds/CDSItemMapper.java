/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/insswap/cds/CDSItemMapper.java,v 1.2 2005/10/24 05:37:57 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.insprotection.insswap.cds;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.bus.type.insurance.ICDSItem;
import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.OBCDSItem;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.collateral.TimeFreqList;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/10/24 05:37:57 $ Tag: $Name: $
 */

public class CDSItemMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CDSItemForm aForm = (CDSItemForm) cForm;

		IInsuranceCollateral iCol = (IInsuranceCollateral) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICDSItem[] cdsArr = iCol.getCdsItems();
		int index = Integer.parseInt((String) inputs.get("indexID"));

		ICDSItem obToChange = null;
		if (index == -1) {
			obToChange = new OBCDSItem();
		}
		else {
			try {
				obToChange = (ICDSItem) AccessorUtil.deepClone(cdsArr[index]);
			}
			catch (Exception e) {
				DefaultLogger.debug(this, e.getMessage());
			}
		}

		Date stageDate = null;
		try {
			obToChange.setBankEntity(aForm.getBankEntity());
			obToChange.setHedgeType(aForm.getHedgeType());
			obToChange.setHedgeRef(aForm.getHedgeRef());
			obToChange.setCdsRef(aForm.getCdsRef());
			obToChange.setTradeID(aForm.getTradeID());

			if (!isEmptyOrNull(aForm.getTradeDate())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getTradeDate(), aForm.getTradeDate());
				obToChange.setTradeDate(stageDate);
			}
			else {
				obToChange.setTradeDate(null);
			}
			if (!isEmptyOrNull(aForm.getDealDate())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getDealDate(), aForm.getDealDate());
				obToChange.setDealDate(stageDate);
			}
			else {
				obToChange.setDealDate(null);
			}
			if (!isEmptyOrNull(aForm.getStartDate())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getStartDate(), aForm.getStartDate());
				obToChange.setStartDate(stageDate);
			}
			else {
				obToChange.setStartDate(null);
			}
			if (!isEmptyOrNull(aForm.getCdsMaturityDate())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getCdsMaturityDate(), aForm.getCdsMaturityDate());
				obToChange.setCdsMaturityDate(stageDate);
			}
			else {
				obToChange.setCdsMaturityDate(null);
			}
			if (isEmptyOrNull(aForm.getTenor())) {
				obToChange.setTenor(ICMSConstant.INT_INVALID_VALUE);
			}
			else {
				obToChange.setTenor(Integer.parseInt(aForm.getTenor()));
			}
			obToChange.setTenorUnit(aForm.getTenorUnit());
			obToChange.setTradeCurrency(aForm.getTradeCurrency());

			IValuation val = obToChange.getValuation();
			if (val == null) {
				val = new OBValuation();
			}

			val.setCurrencyCode(aForm.getValuationCurrency());

			if (isEmptyOrNull(aForm.getNotionalHedgeAmt())) {
				obToChange.setNotionalHedgeAmt(null);
			}
			else {
				obToChange.setNotionalHedgeAmt(UIUtil.convertToAmount(locale, val.getCurrencyCode(), aForm
						.getNotionalHedgeAmt()));
			}

			obToChange.setReferenceEntity(aForm.getReferenceEntity());
			obToChange.setCdsBookingLoc(aForm.getCdsBookingLoc());
			obToChange.setLoanBondBkLoc(aForm.getLoanBondBkLoc());
			obToChange.setReferenceAsset(aForm.getReferenceAsset());
			obToChange.setIssuer(aForm.getIssuer());
			obToChange.setIssuerID(aForm.getIssuerID());
			obToChange.setDetailsIssuer(aForm.getDetailsIssuer());

			if (isEmptyOrNull(aForm.getDealtPrice())) {
				obToChange.setDealtPrice(null);
			}
			else {
				obToChange.setDealtPrice(UIUtil.convertToAmount(locale, val.getCurrencyCode(), aForm.getDealtPrice()));
			}
			if (isEmptyOrNull(aForm.getResidualMaturityField())) {
				obToChange.setResidualMaturityField(null);
			}
			else {
				obToChange.setResidualMaturityField(UIUtil.convertToAmount(locale, val.getCurrencyCode(), aForm
						.getResidualMaturityField()));
			}

			obToChange.setSettlement(aForm.getSettlement());

			if (isEmptyOrNull(aForm.getParValue())) {
				obToChange.setParValue(null);
			}
			else {
				obToChange.setParValue(UIUtil.convertToAmount(locale, val.getCurrencyCode(), aForm.getParValue()));
			}
			if (isEmptyOrNull(aForm.getDeclineMarketValue())) {
				obToChange.setDeclineMarketValue(null);
			}
			else {
				obToChange.setDeclineMarketValue(UIUtil.convertToAmount(locale, val.getCurrencyCode(), aForm
						.getDeclineMarketValue()));
			}

			if (!isEmptyOrNull(aForm.getEventDeterminationDate())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getEventDeterminationDate(), aForm
						.getEventDeterminationDate());
				obToChange.setEventDeterminationDate(stageDate);
			}
			else {
				obToChange.setEventDeterminationDate(null);
			}

			obToChange.setComplianceCert(Boolean.valueOf(aForm.getComplianceCert()).booleanValue());

			// valuation details
			if (!isEmptyOrNull(aForm.getValuationDate())) {
				stageDate = UIUtil.compareDate(locale, val.getValuationDate(), aForm.getValuationDate());
				val.setValuationDate(stageDate);
			}
			else {
				val.setValuationDate(null);
			}
			if (isEmptyOrNull(aForm.getNominalValue())) {
				obToChange.setNominalValue(null);
			}
			else {
				obToChange.setNominalValue(UIUtil.convertToAmount(locale, val.getCurrencyCode(), aForm
						.getNominalValue()));
			}
			if (isEmptyOrNull(aForm.getCdsMargin())) {
				obToChange.setMargin(ICMSConstant.DOUBLE_INVALID_VALUE);
			}
			else {
				obToChange.setMargin(SecuritySubTypeUtil.setMarginStrToDouble(aForm.getCdsMargin()));
			}
			if (isEmptyOrNull(aForm.getNonStdFreq())) {
				val.setNonRevaluationFreq(ICMSConstant.INT_INVALID_VALUE);
			}
			else {
				val.setNonRevaluationFreq(Integer.parseInt(aForm.getNonStdFreq()));
			}
			val.setNonRevaluationFreqUnit(aForm.getNonStdFreqUnit());
			if (isEmptyOrNull(aForm.getValuationCMV())) {
				val.setCMV(null);
			}
			else {
				val.setCMV(UIUtil.convertToAmount(locale, val.getCurrencyCode(), aForm.getValuationCMV()));
				if (obToChange.getMargin() != ICMSConstant.DOUBLE_INVALID_VALUE) {
					val.setFSV(new Amount(val.getCMV().getAmountAsDouble() * obToChange.getMargin(), val
							.getCurrencyCode()));
				}
			}

			obToChange.setValuation(val);

		}
		catch (Exception e) {
			DefaultLogger.debug(this + " CDSItemMapper", "error is :" + e.toString());
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CDSItemForm aForm = (CDSItemForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICDSItem item = (ICDSItem) obj;

		aForm.setBankEntity(item.getBankEntity());
		aForm.setHedgeType(item.getHedgeType());
		aForm.setHedgeRef(item.getHedgeRef());
		aForm.setCdsRef(item.getCdsRef());
		aForm.setTradeID(item.getTradeID());
		aForm.setTradeDate(DateUtil.formatDate(locale, item.getTradeDate()));
		aForm.setDealDate(DateUtil.formatDate(locale, item.getDealDate()));
		aForm.setStartDate(DateUtil.formatDate(locale, item.getStartDate()));
		aForm.setCdsMaturityDate(DateUtil.formatDate(locale, item.getCdsMaturityDate()));
		if (item.getTenor() != ICMSConstant.INT_INVALID_VALUE) {
			aForm.setTenor(String.valueOf(item.getTenor()));
		}
		else {
			aForm.setTenor("");
		}
		aForm.setTenorUnit(item.getTenorUnit());
		aForm.setTradeCurrency(item.getTradeCurrency());
		if (item.getNotionalHedgeAmt() != null) {
			try {
				aForm.setNotionalHedgeAmt(UIUtil.formatAmount(item.getNotionalHedgeAmt(), 2, locale, false));
			}
			catch (Exception e) {
				DefaultLogger.error(this, "exception thrown... " + e);
				throw new MapperException(e.getMessage());
			}
		}

		aForm.setReferenceEntity(item.getReferenceEntity());
		aForm.setCdsBookingLoc(item.getCdsBookingLoc());
		aForm.setLoanBondBkLoc(item.getLoanBondBkLoc());
		aForm.setReferenceAsset(item.getReferenceAsset());
		aForm.setIssuer(item.getIssuer());
		aForm.setIssuerID(item.getIssuerID());
		aForm.setDetailsIssuer(item.getDetailsIssuer());
		if (item.getDealtPrice() != null) {
			try {
				aForm.setDealtPrice(UIUtil.formatAmount(item.getDealtPrice(), 3, locale, false));
			}
			catch (Exception e) {
				DefaultLogger.error(this, "exception thrown... " + e);
				throw new MapperException(e.getMessage());
			}
		}

		if (item.getResidualMaturityField() != null) {
			try {
				aForm.setResidualMaturityField(UIUtil.formatAmount(item.getResidualMaturityField(), 4, locale, false));
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "exception thrown... " + e);
				throw new MapperException(e.getMessage());
			}
		}

		aForm.setSettlement(item.getSettlement());
		if (item.getParValue() != null) {
			try {
				aForm.setParValue(UIUtil.formatAmount(item.getParValue(), 3, locale, false));
			}
			catch (Exception e) {
				DefaultLogger.error(this, "exception thrown... " + e);
				throw new MapperException(e.getMessage());
			}
		}
		if (item.getDeclineMarketValue() != null) {
			try {
				aForm.setDeclineMarketValue(UIUtil.formatAmount(item.getDeclineMarketValue(), 3, locale, false));
			}
			catch (Exception e) {
				DefaultLogger.error(this, "exception thrown... " + e);
				throw new MapperException(e.getMessage());
			}
		}

		aForm.setEventDeterminationDate(DateUtil.formatDate(locale, item.getEventDeterminationDate()));
		aForm.setComplianceCert(String.valueOf(item.getComplianceCert()));

		if (item.getNominalValue() != null) {
			try {
				aForm.setNominalValue(CurrencyManager.convertToString(locale, item.getNominalValue()));
			}
			catch (Exception e) {
				DefaultLogger.error(this, "exception thrown... " + e);
				throw new MapperException(e.getMessage());
			}
		}

		aForm.setCdsMargin(SecuritySubTypeUtil.setMarginDoubleToStr(item.getMargin(), locale));

		int revalFreq = ICMSConstant.INT_INVALID_VALUE;
		String revalFreqUnit = null;
		IValuation val = item.getValuation();
		if (val != null) {
			aForm.setValuationDate(DateUtil.formatDate(locale, val.getValuationDate()));
			aForm.setValuationCurrency(val.getCurrencyCode());
			if (val.getNonRevaluationFreq() != ICMSConstant.INT_INVALID_VALUE) {
				revalFreq = val.getNonRevaluationFreq();
				revalFreqUnit = val.getNonRevaluationFreqUnit();
				aForm.setNonStdFreq(String.valueOf(val.getNonRevaluationFreq()));
			}
			aForm.setNonStdFreqUnit(val.getNonRevaluationFreqUnit());
			if (val.getCMV() != null) {
				try {
					aForm.setValuationCMV(CurrencyManager.convertToString(locale, val.getCMV()));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown... " + e);
					throw new MapperException(e.getMessage());
				}
			}
			if (val.getFSV() != null) {
				try {
					aForm.setValuationFSV(CurrencyManager.convertToString(locale, val.getFSV()));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown... " + e);
					throw new MapperException(e.getMessage());
				}
			}
			IInsuranceCollateral iCol = (IInsuranceCollateral) (((ICollateralTrxValue) inputs.get("serviceColObj"))
					.getStagingCollateral());
			if (!isEmptyOrNull(iCol.getCollateralLocation())) {
				ICollateralParameter parameter = null;
				try {
					parameter = CollateralProxyFactory.getProxy().getCollateralParameter(iCol.getCollateralLocation(),
							iCol.getCollateralSubType().getSubTypeCode());
				}
				catch (Exception e) {
					DefaultLogger.debug(this + " Collateral Mapper", "There is no parameter found in the database");
				}
				if (parameter != null) {
					String unit = TimeFreqList.getInstance().getTimeFreqItem(parameter.getValuationFrequencyUnit());
					if (unit == null) {
						unit = "-";
					}
					aForm.setRevalFreq(String.valueOf(parameter.getValuationFrequency()) + " " + unit);
					if (revalFreq == ICMSConstant.INT_INVALID_VALUE) {
						revalFreq = parameter.getValuationFrequency();
						revalFreqUnit = parameter.getValuationFrequencyUnit();
					}
				}
			}
			Date revaluationDate = UIUtil.calculateDate(revalFreq, revalFreqUnit, val.getValuationDate());
			aForm.setRevalDate(DateUtil.formatDate(locale, revaluationDate));
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
	}
}
