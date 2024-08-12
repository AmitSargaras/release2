/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/AssetGenChargeMapperHelper.java,v 1.21 2005/08/12 08:16:45 lyng Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2005/08/12 08:16:45 $ Tag: $Name: $
 */

public class AssetGenChargeMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		AssetGenChargeForm aForm = (AssetGenChargeForm) cForm;
		IGeneralCharge iObj = (IGeneralCharge) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("AssetGenChargeMapperHelper - mapFormToOB", "Locale is: " + locale);
		Date stageDate;
		try {
			iObj.setEnvRiskyStatus(aForm.getSecEnvRisky());
			iObj.setEnvRiskyRemarks(aForm.getRemarkEnvRisk());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateSecurityEnv())) {
				stageDate = DateUtil.convertDate(locale, aForm.getDateSecurityEnv());
				stageDate = CollateralMapper.compareDate(locale, iObj.getEnvRiskyDate(), aForm.getDateSecurityEnv());
				iObj.setEnvRiskyDate(stageDate);
			}
			else {
				iObj.setEnvRiskyDate(null);
			}
			iObj.setRemarks(aForm.getDescription());
			
			if(aForm.getMonitorProcess()!=null && !aForm.getMonitorProcess().equals("")){
				iObj.setMonitorProcess(aForm.getMonitorProcess());
			}
			if(aForm.getMonitorFrequency()!=null && !aForm.getMonitorFrequency().equals("")){
				iObj.setMonitorFrequency(aForm.getMonitorFrequency());
			}

			// addded for MBB-474
			/*
			 * if (aForm.getDepreciateRate().equals("") &&
			 * iObj.getDepreciateRate() != null &&
			 * iObj.getDepreciateRate().getAmount() > 0) {
			 * iObj.setDepreciateRate(CurrencyManager.convertToAmount(locale,
			 * iObj.getCurrencyCode(), "0")); } else if
			 * (!AbstractCommonMapper.isEmptyOrNull(aForm.getDepreciateRate()))
			 * { iObj.setDepreciateRate(CurrencyManager.convertToAmount(locale,
			 * iObj.getCurrencyCode(), aForm.getDepreciateRate())); }
			 */

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateSecurityEnv())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getEnvRiskyDate(), aForm.getDateSecurityEnv());
				iObj.setEnvRiskyDate(stageDate);
			}
			else {
				iObj.setEnvRiskyDate(null);
			}

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
				stageDate = CollateralMapper.compareDate(locale, iObj.getLastPhysicalInspectDate(), aForm
						.getDatePhyInspec());
				iObj.setLastPhysicalInspectDate(stageDate);
				if (aForm.getIsPhysInsp().equals("true")
						&& !AbstractCommonMapper.isEmptyOrNull(aForm.getNextPhysInspDate())) {
					stageDate = CollateralMapper.compareDate(locale, iObj.getNextPhysicalInspectDate(), aForm
							.getNextPhysInspDate());
					iObj.setNextPhysicalInspectDate(stageDate);
				}
				else {
					iObj.setNextPhysicalInspectDate(null);
				}
			}
			else {
				iObj.setLastPhysicalInspectDate(null);
				iObj.setNextPhysicalInspectDate(null);
			}
			iObj.setChattelSoldDate(UIUtil.mapStringToDate(locale, iObj.getChattelSoldDate(), aForm.getChattelSoldDate()));
			iObj.setRlSerialNumber(aForm.getRlSerialNumber());
			
			iObj.setBankingArrangement(aForm.getBankingArrangement());
			
		}
		catch (Exception e) {
			DefaultLogger.debug(
					"com.integrosys.cms.ui.collateral.assetbased.assetGenCharge.AssetGenChargeMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		AssetGenChargeForm aForm = (AssetGenChargeForm) cForm;
		IGeneralCharge iObj = (IGeneralCharge) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("AssetGenChargeMapperHelper - mapOBToForm", "Locale is: " + locale);
		try {
			if (iObj.getEnvRiskyStatus() != null) {
				aForm.setSecEnvRisky(iObj.getEnvRiskyStatus().trim());
			}
			aForm.setRemarkEnvRisk(iObj.getEnvRiskyRemarks());
			if (iObj.getEnvRiskyDate() != null) {
				aForm.setDateSecurityEnv(DateUtil.formatDate(locale, iObj.getEnvRiskyDate()));
			}
			aForm.setDescription(iObj.getRemarks());
			
			if(aForm.getMonitorProcess()!=null && !aForm.getMonitorProcess().equals("")){
				iObj.setMonitorProcess(aForm.getMonitorProcess());
			}
			if(aForm.getMonitorFrequency()!=null && !aForm.getMonitorFrequency().equals("")){
				iObj.setMonitorFrequency(aForm.getMonitorFrequency());
			}

			// addded for MBB-474

			/*
			 * Amount amt = iObj.getDepreciateRate(); if (amt != null &&
			 * amt.getCurrencyCode() != null) {
			 * aForm.setDepreciateRate(CurrencyManager.convertToString(locale,
			 * amt)); }
			 */

			aForm.setIsPhysInsp(String.valueOf(iObj.getIsPhysicalInspection()));
			if (iObj.getPhysicalInspectionFreq() >= 0) {
				aForm.setPhysInspFreq(String.valueOf(iObj.getPhysicalInspectionFreq()));
			}
			if (iObj.getPhysicalInspectionFreqUnit() != null) {
				aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit().trim());
			}

			aForm.setDatePhyInspec(DateUtil.formatDate(locale, iObj.getLastPhysicalInspectDate()));
			aForm.setNextPhysInspDate(DateUtil.formatDate(locale, iObj.getNextPhysicalInspectDate()));
			aForm.setChattelSoldDate(DateUtil.formatDate(locale, iObj.getChattelSoldDate()));
			aForm.setRlSerialNumber(iObj.getRlSerialNumber());
			
			String bankingArrangement = MapperUtil.emptyIfNull(iObj.getBankingArrangement());
			aForm.setBankingArrangement(bankingArrangement);
		}
		catch (Exception e) {
			DefaultLogger
					.debug(
							"com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeMapperHelper - MapOBToForm",
							"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IGeneralCharge) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());

	}

}
