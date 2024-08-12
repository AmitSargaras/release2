package com.integrosys.cms.ui.collateral.assetbased.assetvessel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.vessel.IVessel;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Created by IntelliJ IDEA. User: Jitendra Date: Mar 02, 2007 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */

public class AssetVesselMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		IVessel iObj = (IVessel) obj;
		AssetVesselForm aForm = (AssetVesselForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {

			iObj.setRegistrationNo(aForm.getRegNo());

			iObj.setBrand(aForm.getBrand());
			iObj.setModelNo(aForm.getModelNo());
			iObj.setAssetType(aForm.getAssetType());

			iObj.setEnvRiskyStatus(aForm.getSecEnvRisky());
			iObj.setEnvRiskyRemarks(aForm.getRemarkEnvRisk());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp())) {
				iObj.setIsPhysicalInspection(Boolean.valueOf(aForm.getIsPhysInsp()).booleanValue());
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPhysInspFreq())) {
				iObj.setPhysicalInspectionFreq(Integer.parseInt(aForm.getPhysInspFreq().trim()));
			}
			else {
				iObj.setPhysicalInspectionFreq(ICMSConstant.INT_INVALID_VALUE);
			}

			iObj.setPhysicalInspectionFreqUnit(aForm.getPhysInspFreqUOM());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDatePhyInspec())) {
				if (aForm.getIsPhysInsp().equals("true")) {
					iObj.setNextPhysicalInspectDate(UIUtil.mapFormString_OBDate(locale, iObj
							.getNextPhysicalInspectDate(), aForm.getNextPhysInspDate()));
				}
			}
			else {
				iObj.setNextPhysicalInspectDate(null);
			}

			iObj.setRemarks(aForm.getDescription());
			iObj.setInsurers(aForm.getInsurers());

			iObj.setVesselName(aForm.getVesselName());

			iObj.setTradeInDeposit(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getTradeInDeposit(),
					iObj.getTradeInDeposit()));

			iObj.setTradeInValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getTradeInValue(), iObj
					.getTradeInValue()));

			iObj.setRegCountry(aForm.getRegCountry());
			iObj.setVesselState(aForm.getVesselState());
			iObj.setGoodStatus(aForm.getVesselState()); // valuation require
			// this

			iObj.setVesselExptOccup(aForm.getVesselExptOccup());
			iObj.setVesselOccupType(aForm.getVesselOccupType());

			iObj.setVesselPurchaseCurrency(aForm.getVesselPurchaseCurrency());

			iObj.setVesselBuilder(aForm.getVesselBuilder());
			iObj.setVesselMainReg(aForm.getVesselMainReg());

			iObj.setVesselLength(aForm.getVesselLength());
			iObj.setVesselWidth(aForm.getVesselWidth());

			iObj.setVesselDepth(aForm.getVesselDepth());
			iObj.setVesselDeckLoading(aForm.getVesselDeckLoading());
			iObj.setVesselDeckWeight(aForm.getVesselDeckWeight());
			iObj.setVesselSideBoard(aForm.getVesselSideBoard());
			iObj.setVesselBOW(aForm.getVesselBOW());
			iObj.setVesselDECK(aForm.getVesselDECK());

			iObj.setVesselDeckThickness(aForm.getVesselDeckThickness());
			iObj.setVesselBottom(aForm.getVesselBottom());
			iObj.setVesselWinchDrive(aForm.getVesselWinchDrive());
			iObj.setVesselBHP(aForm.getVesselBHP());
			iObj.setVesselSpeed(aForm.getVesselSpeed());
			iObj.setVesselAnchor(aForm.getVesselAnchor());
			iObj.setVesselAnchorDrive(aForm.getVesselAnchorDrive());
			iObj.setVesselClassSociety(aForm.getVesselClassSociety());
			iObj.setVesselConstructCountry(aForm.getVesselConstructCountry());
			iObj.setVesselConstructPlace(aForm.getVesselConstructPlace());
			iObj.setVesselUse(aForm.getVesselUse());

			iObj.setVesselChartererName(aForm.getVesselChartererName());
			iObj.setVesselCharterPeriodUnit(aForm.getVesselCharterPeriodUnit());

			iObj.setVesselCharterCurrency(aForm.getVesselCharterCurrency());
			iObj.setVesselCharterRateUnit(aForm.getVesselCharterRateUnit());
			iObj.setVesselCharterRateUnitOTH(aForm.getVesselCharterRateUnitOTH());
			iObj.setVesselCharterRemarks(aForm.getVesselCharterRemarks());
			iObj.setResidualAssetLifeUOM(aForm.getResidualAssetLifeUOM());

			iObj.setVesselCharterContract(UIUtil.mapFormString_OBBoolean(aForm.getVesselCharterContract()));
			iObj.setPurchaseDate(UIUtil.mapFormString_OBDate(locale, iObj.getPurchaseDate(), aForm.getDatePurchase()));
			iObj.setLastPhysicalInspectDate(UIUtil.mapFormString_OBDate(locale, iObj.getLastPhysicalInspectDate(),
					aForm.getDatePhyInspec()));
			iObj.setLastPhysicalInspectDate(UIUtil.mapFormString_OBDate(locale, iObj.getLastPhysicalInspectDate(),
					aForm.getDatePhyInspec()));
			iObj.setEnvRiskyDate(UIUtil
					.mapFormString_OBDate(locale, iObj.getEnvRiskyDate(), aForm.getDateSecurityEnv()));
			iObj.setRegistrationDate(UIUtil
					.mapFormString_OBDate(locale, iObj.getRegistrationDate(), aForm.getRegDate()));
			iObj.setVesselExptOccupDate(UIUtil.mapFormString_OBDate(locale, iObj.getVesselExptOccupDate(), aForm
					.getVesselExptOccupDate()));

			iObj.setYearOfManufacture(UIUtil.mapStringToInteger(aForm.getVesselBuildYear()));
			iObj.setVesselBuildYear(UIUtil.mapStringToInteger(aForm.getVesselBuildYear()));
			iObj.setVesselCharterPeriod(UIUtil.mapStringToInteger(aForm.getVesselCharterPeriod()));
			// iObj.setDocPerfectAge(UIUtil.mapStringToInteger(aForm.
			// getVesselDocPerfectAge()));

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getVesselDocPerfectAge())) {
				iObj.setDocPerfectAge(0);
			}
			else {
				iObj.setDocPerfectAge(UIUtil.mapStringToInteger(aForm.getVesselDocPerfectAge()));
			}

			iObj.setResidualAssetLife(UIUtil.mapStringToInteger(aForm.getResidualAssetLife()));

			iObj.setVesselCharterAmt(UIUtil.mapFormString_OBDouble(aForm.getVesselCharterAmt()));

			iObj.setRegistrationFee(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getRegFee(), iObj
					.getRegistrationFee()));
			iObj.setPurchasePrice(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getPurchasePrice(),
					iObj.getPurchasePrice()));
			iObj.setNominalValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getNominalValue(), iObj
					.getNominalValue()));
			// iObj.setDepreciateRate(UIUtil.mapStringToAmount(locale,
			// iObj.getCurrencyCode(), aForm.getDepreciateRate(),
			// iObj.getDepreciateRate()));
			iObj.setScrapValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getVesselScrapValue(),
					iObj.getScrapValue()));
			iObj.setAssetValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getVesselAssetValue(),
					iObj.getAssetValue()));
			iObj.setPubTransport(aForm.getPubTransport());
			iObj.setChattelSoldDate(DateUtil.convertDate(locale, aForm.getChattelSoldDate()));
			iObj.setRlSerialNumber(aForm.getRlSerialNumber());
			iObj.setRepossessionDate(DateUtil.convertDate(locale, aForm.getRepossessionDate()));
			iObj.setVesselCharterRemarks(aForm.getVesselCharterRemarks());

		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.assetbased.assetvessel.AssetVesselMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iObj;

	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		IVessel iObj = (IVessel) obj;
		AssetVesselForm aForm = (AssetVesselForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {

			aForm.setRegNo(iObj.getRegistrationNo());
			aForm.setBrand(iObj.getBrand());
			aForm.setModelNo(iObj.getModelNo());
			aForm.setAssetType(iObj.getAssetType());

			aForm.setSecEnvRisky(iObj.getEnvRiskyStatus());
			aForm.setRemarkEnvRisk(iObj.getEnvRiskyRemarks());

			aForm.setIsPhysInsp(String.valueOf(iObj.getIsPhysicalInspection()));

			if (iObj.getPhysicalInspectionFreq() >= 0) {
				aForm.setPhysInspFreq(String.valueOf(iObj.getPhysicalInspectionFreq()));
			}
			if (iObj.getPhysicalInspectionFreqUnit() != null) {
				aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit().trim());
			}

			// aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit());
			aForm.setDescription(iObj.getRemarks());
			aForm.setInsurers(iObj.getInsurers());

			aForm.setVesselName(iObj.getVesselName());
			// aForm.setApprovalObtained(iObj.getIsExchangeCtrlObtained());
			// aForm.setApprovalDate(DateUtil.formatDate(locale,
			// iObj.getExchangeCtrlDate()));
			aForm.setTradeInDeposit(UIUtil.mapAmountToString(iObj.getTradeInDeposit(), locale, false));
			aForm.setTradeInValue(UIUtil.mapAmountToString(iObj.getTradeInValue(), locale, false));
			aForm.setRegCountry(iObj.getRegCountry());
			aForm.setVesselState(iObj.getVesselState());
			aForm.setVesselExptOccup(iObj.getVesselExptOccup());
			aForm.setVesselOccupType(iObj.getVesselOccupType());

			aForm.setVesselPurchaseCurrency(iObj.getVesselPurchaseCurrency());
			aForm.setVesselBuilder(iObj.getVesselBuilder());
			aForm.setVesselMainReg(iObj.getVesselMainReg());
			aForm.setVesselLength(iObj.getVesselLength());
			aForm.setVesselWidth(iObj.getVesselWidth());
			aForm.setVesselDepth(iObj.getVesselDepth());
			aForm.setVesselDeckLoading(iObj.getVesselDeckLoading());
			aForm.setVesselDeckWeight(iObj.getVesselDeckWeight());
			aForm.setVesselSideBoard(iObj.getVesselSideBoard());
			aForm.setVesselBOW(iObj.getVesselBOW());
			aForm.setVesselDECK(iObj.getVesselDECK());
			aForm.setVesselDeckThickness(iObj.getVesselDeckThickness());
			aForm.setVesselBottom(iObj.getVesselBottom());
			aForm.setVesselWinchDrive(iObj.getVesselWinchDrive());
			aForm.setVesselBHP(iObj.getVesselBHP());
			aForm.setVesselSpeed(iObj.getVesselSpeed());
			aForm.setVesselAnchor(iObj.getVesselAnchor());
			aForm.setVesselAnchorDrive(iObj.getVesselAnchorDrive());
			aForm.setVesselClassSociety(iObj.getVesselClassSociety());
			aForm.setVesselConstructCountry(iObj.getVesselConstructCountry());
			aForm.setVesselConstructPlace(iObj.getVesselConstructPlace());
			aForm.setVesselUse(iObj.getVesselUse());

			aForm.setVesselChartererName(iObj.getVesselChartererName());
			aForm.setVesselCharterPeriodUnit(iObj.getVesselCharterPeriodUnit());
			aForm.setVesselCharterCurrency(iObj.getVesselCharterCurrency());

			aForm.setVesselCharterRateUnit(iObj.getVesselCharterRateUnit());
			aForm.setVesselCharterRateUnitOTH(iObj.getVesselCharterRateUnitOTH());
			aForm.setVesselCharterRemarks(iObj.getVesselCharterRemarks());
			aForm.setResidualAssetLifeUOM(iObj.getResidualAssetLifeUOM());

			aForm.setVesselCharterContract(String.valueOf(iObj.getVesselCharterContract()));

			aForm.setDateSecurityEnv(DateUtil.formatDate(locale, iObj.getEnvRiskyDate()));
			aForm.setDatePurchase(DateUtil.formatDate(locale, iObj.getPurchaseDate()));
			aForm.setRegDate(UIUtil.mapOBDate_FormString(locale, iObj.getRegistrationDate()));
			aForm.setVesselExptOccupDate(UIUtil.mapOBDate_FormString(locale, iObj.getVesselExptOccupDate()));
			aForm.setDatePhyInspec(DateUtil.formatDate(locale, iObj.getLastPhysicalInspectDate()));
			aForm.setNextPhysInspDate(DateUtil.formatDate(locale, iObj.getNextPhysicalInspectDate()));

			aForm.setYearMfg(UIUtil.mapIntegerToString(iObj.getYearOfManufacture()));
			// aForm.setPhysInspFreq(UIUtil.mapIntegerToString(iObj.
			// getPhysicalInspectionFreq()));
			aForm.setVesselBuildYear(UIUtil.mapIntegerToString(iObj.getVesselBuildYear()));
			aForm.setVesselCharterPeriod(UIUtil.mapIntegerToString(iObj.getVesselCharterPeriod()));
			// aForm.setVesselDocPerfectAge(UIUtil.mapIntegerToString(iObj.
			// getDocPerfectAge()));

			if ((iObj.getPerfectionDate() != null) && (String.valueOf(iObj.getVesselBuildYear()) != null)) {
				GregorianCalendar year = new GregorianCalendar();
				if (aForm.getPerfectionDate() != null) {
					year.setTime(iObj.getPerfectionDate());
					int perfectionYear = year.get(Calendar.YEAR);

					int age = perfectionYear - iObj.getVesselBuildYear();
					aForm.setVesselDocPerfectAge(String.valueOf(age));
				}
			}
			else {
				aForm.setVesselDocPerfectAge(String.valueOf(0));
			}

			aForm.setResidualAssetLife(UIUtil.mapIntegerToString(iObj.getResidualAssetLife()));

			aForm.setVesselCharterAmt(UIUtil.mapOBDouble_FormString(iObj.getVesselCharterAmt()));

			aForm.setRegFee(UIUtil.mapAmountToString(locale, iObj.getRegistrationFee()));
			aForm.setPurchasePrice(UIUtil.mapAmountToString(locale, iObj.getPurchasePrice()));
			aForm.setNominalValue(UIUtil.mapAmountToString(locale, iObj.getNominalValue()));
			aForm.setVesselScrapValue(UIUtil.mapAmountToString(locale, iObj.getScrapValue()));
			aForm.setVesselAssetValue(UIUtil.mapAmountToString(locale, iObj.getAssetValue()));
			aForm.setPubTransport(iObj.getPubTransport());
			aForm.setChattelSoldDate(DateUtil.formatDate(locale, iObj.getChattelSoldDate()));
			aForm.setRlSerialNumber(iObj.getRlSerialNumber());
			aForm.setRepossessionDate(DateUtil.formatDate(locale, iObj.getRepossessionDate()));
			// aForm.setVesselCharterRateUnit(iObj.getVesselCharterRemarks());
			// aForm.setDepreciateRate(UIUtil.mapAmountToString(locale,
			// iObj.getDepreciateRate()));

		}
		catch (Exception e) {
			throw new MapperException("failed to map value object to form object", e);
		}

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IVessel) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
