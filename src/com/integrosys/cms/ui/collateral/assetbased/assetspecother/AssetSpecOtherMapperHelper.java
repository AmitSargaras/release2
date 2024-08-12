//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetspecother;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeothers.ISpecificChargeOthers;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class AssetSpecOtherMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		ISpecificChargeOthers iObj = (ISpecificChargeOthers) obj;
		AssetSpecOtherForm aForm = (AssetSpecOtherForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		Date stageDate;
		try {

			iObj.setEnvRiskyStatus(aForm.getSecEnvRisky());
			iObj.setEnvRiskyRemarks(aForm.getRemarkEnvRisk());
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

			/*if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPhysInspFreq())&& "true".equalsIgnoreCase(aForm.getIsPhysInsp())) {
				iObj.setPhysicalInspectionFreq(Integer.parseInt(aForm.getPhysInspFreq().trim()));
			}
			else {
				iObj.setPhysicalInspectionFreq(ICMSConstant.INT_INVALID_VALUE);
			}*/
			
			//Added by Pramod Katkar for New Filed CR on 5-08-2013
			if( "true".equalsIgnoreCase(aForm.getIsPhysInsp())){
			iObj.setPhysicalInspectionFreqUnit(aForm.getPhysInspFreqUOM());
			}else{
				iObj.setPhysicalInspectionFreqUnit("");
			}
			if( "true".equalsIgnoreCase(aForm.getIsPhysInsp())){
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDatePhyInspec())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getLastPhysicalInspectDate(), aForm
						.getDatePhyInspec());
				iObj.setLastPhysicalInspectDate(stageDate);
				}
			}
			else {
				iObj.setLastPhysicalInspectDate(null);
			}
			if( "true".equalsIgnoreCase(aForm.getIsPhysInsp())){
				if ( !AbstractCommonMapper.isEmptyOrNull(aForm.getNextPhysInspDate())) {
					stageDate = CollateralMapper.compareDate(locale, iObj.getNextPhysicalInspectDate(), aForm
							.getNextPhysInspDate());
					iObj.setNextPhysicalInspectDate(stageDate);
				}
			}
			else {
				iObj.setNextPhysicalInspectDate(null);
			}
			//End by Pramod Katkar
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getNominalValue())) {
				iObj.setNominalValue(null);
			}
			else {
				iObj.setNominalValue(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
						.getNominalValue()));
			}

			iObj.setRemarks(aForm.getDescription());

			iObj.setAssetType(aForm.getAssetType());
			iObj.setGoodStatus(aForm.getGoodStatus());
			iObj.setScrapValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(),
					aForm.getScrapValue(), iObj.getScrapValue()));

			iObj.setAssetValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getAssetValue(), iObj
					.getAssetValue()));
			//iObj.set;
			/*iObj.setPurchasePrice(purchasePrice);*/

		}
		catch (Exception e) {
			DefaultLogger.debug(
					"com.integrosys.cms.ui.collateral.assetbased.assetspecother.AssetSpecOtherMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		ISpecificChargeOthers iObj = (ISpecificChargeOthers) obj;
		AssetSpecOtherForm aForm = (AssetSpecOtherForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		Amount amt;
		try {
			if (iObj.getEnvRiskyStatus() != null) {
				aForm.setSecEnvRisky(iObj.getEnvRiskyStatus().trim());
			}
			if(iObj.getMonitorProcess()!=null && !iObj.getMonitorProcess().equals("")){
				aForm.setMonitorProcess(iObj.getMonitorProcess());
			}
			if(iObj.getMonitorFrequency()!=null && !iObj.getMonitorFrequency().equals("")){
				aForm.setMonitorFrequency(iObj.getMonitorFrequency());
			}
			aForm.setRemarkEnvRisk(iObj.getEnvRiskyRemarks());
			aForm.setDateSecurityEnv(DateUtil.formatDate(locale, iObj.getEnvRiskyDate()));
			aForm.setIsPhysInsp(String.valueOf(iObj.getIsPhysicalInspection()));
			if (iObj.getPhysicalInspectionFreq() >= 0) {
				aForm.setPhysInspFreq(String.valueOf(iObj.getPhysicalInspectionFreq()));
			}
			if (iObj.getPhysicalInspectionFreqUnit() != null) {
				aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit().trim());
			}
			if(iObj.getLastPhysicalInspectDate()!=null){
			aForm.setDatePhyInspec(DateUtil.formatDate(locale, iObj.getLastPhysicalInspectDate()));
			}
			if(iObj.getNextPhysicalInspectDate()!=null){
			aForm.setNextPhysInspDate(DateUtil.formatDate(locale, iObj.getNextPhysicalInspectDate()));
			}
			amt = iObj.getNominalValue();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				aForm.setNominalValue(CurrencyManager.convertToString(locale, amt));
			}
			aForm.setDescription(iObj.getRemarks());

			aForm.setAssetType(iObj.getAssetType());
			aForm.setGoodStatus(iObj.getGoodStatus());
		   // aForm.setReservePrice(iObj.getReservePrice());
			// aForm.setAssetTypeHostRef(iObj.getAssetTypeHostRef());
			aForm.setScrapValue(UIUtil.mapAmountToString(locale, iObj.getScrapValue()));
			aForm.setAssetValue(UIUtil.mapAmountToString(locale, iObj.getAssetValue()));
		}
		catch (Exception e) {
			DefaultLogger.debug(
					"com.integrosys.cms.ui.collateral.assetbased.assetspecother.AssetSpecOtherMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return aForm;
	}

	public static Object getObject(HashMap inputs) {

		return ((ISpecificChargeOthers) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
