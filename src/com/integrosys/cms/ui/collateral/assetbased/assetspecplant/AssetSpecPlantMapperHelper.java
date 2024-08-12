//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetspecplant;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.ITradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBTradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.ISpecificChargePlant;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author Jitendra
 * @author Chong Jun Yong
 * @since Jun 22, 2006
 */
public class AssetSpecPlantMapperHelper {

	private static String LOGOBG = AssetSpecPlantMapperHelper.class.getName();

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		AssetSpecPlantForm aForm = (AssetSpecPlantForm) cForm;
		ISpecificChargePlant iObj = (ISpecificChargePlant) obj;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		Date stageDate;
		try {

			iObj.setAssetType(aForm.getAssetType());
			iObj.setEnvRiskyStatus(aForm.getSecEnvRisky());
			iObj.setEnvRiskyRemarks(aForm.getRemarkEnvRisk());
			iObj.setRegistrationNo(aForm.getRegistrationNo());
			iObj.setRegistrationCardNo(aForm.getRegistrationCardNo());
			iObj.setModelNo(aForm.getModelNo());
			iObj.setBrand(aForm.getBrand());
			iObj.setGoodStatus(aForm.getGoodStatus());
			if(aForm.getMonitorProcess()!=null && !aForm.getMonitorProcess().equals("")){
				iObj.setMonitorProcess(aForm.getMonitorProcess());
			}
			if(aForm.getMonitorFrequency()!=null && !aForm.getMonitorFrequency().equals("")){
				iObj.setMonitorFrequency(aForm.getMonitorFrequency());
			}
			// iObj.setChassisNumber(aForm.getChassisNumber());
			iObj.setPrevOwnerName(aForm.getPrevOwnerName());
			iObj.setPrevFinancierName(aForm.getPrevFinancierName());

			iObj.setPhysicalInspectionFreqUnit(aForm.getPhysInspFreqUOM());
			iObj.setRemarks(aForm.getDescription());
			iObj.setYard(aForm.getYard());
			


			//Added by Pramod Katkar for New Filed CR on 7-08-2013
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp())) {
				iObj.setIsPhysicalInspection(Boolean.valueOf(aForm.getIsPhysInsp()).booleanValue());
			}

			/*if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPhysInspFreq())&& "true".equalsIgnoreCase(aForm.getIsPhysInsp())) {
				iObj.setPhysicalInspectionFreq(Integer.parseInt(aForm.getPhysInspFreq().trim()));
			}
			else {
				iObj.setPhysicalInspectionFreq(ICMSConstant.INT_INVALID_VALUE);
			}*/
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

			iObj.setResidualAssetLife(UIUtil.mapStringToInteger(aForm.getResidualAssetLife()));
			iObj.setResidualAssetLifeUOM(aForm.getResidualAssetLifeUOM());

			// iObj.setDocPerfectAge(UIUtil.mapStringToInteger(aForm.
			// getDocPerfectAge()));
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getDocPerfectAge())) {
				iObj.setDocPerfectAge(0);
			}
			else {
				iObj.setDocPerfectAge(UIUtil.mapStringToInteger(aForm.getDocPerfectAge()));
			}
   
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getRepossessionAge())) {
				iObj.setRepossessionAge(0);
			}
			else {
				iObj.setRepossessionAge(UIUtil.mapStringToInteger(aForm.getRepossessionAge()));
			}

			iObj.setYearOfManufacture(UIUtil.mapStringToInteger(aForm.getYearOfManufacture()));

			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPhysInspFreq())) {
				iObj.setPhysicalInspectionFreq(Integer.parseInt(aForm.getPhysInspFreq().trim()));
			}
			else {
				iObj.setPhysicalInspectionFreq(ICMSConstant.INT_INVALID_VALUE);
			}

			iObj.setRegistrationFee(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(),
					aForm.getRegistrationFee(), iObj.getRegistrationFee()));

			iObj.setPurchasePrice(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getPurchasePrice(),
					iObj.getPurchasePrice()));

			iObj.setSalesProceed(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getSalesProceed(), iObj
					.getSalesProceed()));
			iObj.setScrapValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getScrapValue(), iObj
					.getScrapValue()));
			iObj.setAssetValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getAssetValue(), iObj
					.getAssetValue()));
			iObj.setRamId(aForm.getRamId());
			iObj.setPurpose(aForm.getPurpose());
			iObj.setEquipmf(aForm.getEquipmf());
			iObj.setEquipriskgrading(aForm.getEquipriskgrading());
			iObj.setEquipcode(aForm.getEquipcode());
			iObj.setQuantity(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getQuantity(), iObj
					.getQuantity()));
			iObj.setSerialNumber(aForm.getSerialNumber());
			iObj.setInvoiceNumber(aForm.getInvoiceNumber());
			iObj.setDpcash(UIUtil
					.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getDpcash(), iObj.getDpcash()));
			iObj.setDptradein(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getDptradein(), iObj
					.getDptradein()));
			iObj.setTradeinValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getTradeinValue(), iObj
					.getTradeinValue()));
			iObj.setRlSerialNumber(aForm.getRlSerialNumber());
			iObj.setChattelSoldDate(DateUtil.convertDate(locale, aForm.getChattelSoldDate()));
			iObj.setPubTransport(aForm.getPubTransport());
			//iObj.setRepossessionDate(DateUtil.convertDate(locale, aForm.getRepossessionDate()));
			iObj.setInspectionStatusCategoryCode(CategoryCodeConstant.PHYSICAL_INSPECTION_STATUS);
			iObj.setInspectionStatusEntryCode(aForm.getInspectionStatus());

			iObj.setPlist(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getPlist(), null));

			IValuation iVal = (IValuation) iObj.getValuationIntoCMS();
			if (iVal != null) {
				iVal.setOlv(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getOlv(), iVal.getOlv()));
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getRemainusefullife())) {
					iVal.setRemainusefullife(Double.valueOf(aForm.getRemainusefullife()));
				}
				else {
					iVal.setRemainusefullife(null);
				}
				iVal.setValuationbasis(aForm.getValuationbasis());
			}

			ITradeInInfo[] tradeInInfo = iObj.getTradeInInfo();
			if ((tradeInInfo == null) || (tradeInInfo.length == 0)) {
				tradeInInfo = new OBTradeInInfo[1];
				tradeInInfo[0] = new OBTradeInInfo();
				//mapPlantFormToTradeInfoOB(aForm, tradeInInfo[0], iObj.getCurrencyCode(), locale);
			}
			else {
			//	mapPlantFormToTradeInfoOB(aForm, tradeInInfo[0], iObj.getCurrencyCode(), locale);
			}
			iObj.setTradeInInfo(tradeInInfo);

			iObj.setInvoiceDate(UIUtil.mapStringToDate(locale, iObj.getInvoiceDate(), aForm.getInvoiceDate()));
			
			if(null!=aForm.getInsuranceStatus()){
				iObj.setInsuranceStatus(aForm.getInsuranceStatus());
			}
			
			if(aForm.getOriginalTargetDate()!=null && (!aForm.getOriginalTargetDate().equals("")))
            {
				iObj.setOriginalTargetDate(DateUtil.convertDate(aForm.getOriginalTargetDate()));
            }
			if(aForm.getNextDueDate()!=null && (!aForm.getNextDueDate().equals("")))
            {
				iObj.setNextDueDate(DateUtil.convertDate(aForm.getNextDueDate()));
            }
			if(aForm.getDateDeferred()!=null && (!aForm.getDateDeferred().equals("")))
            {
				iObj.setDateDeferred(DateUtil.convertDate(aForm.getDateDeferred()));
            }
			if(aForm.getCreditApprover()!=null && (!aForm.getCreditApprover().equals("")))
            {
				iObj.setCreditApprover(aForm.getCreditApprover());
            }
			if(aForm.getWaivedDate()!=null && (!aForm.getWaivedDate().equals("")))
            {
				iObj.setWaivedDate(DateUtil.convertDate(aForm.getWaivedDate()));
            }
		}
		catch (Exception e) {
			throw new MapperException("failed to map from form to ob for asset plant and equipment", e);
		}
		return iObj;
	}

	private static void mapPlantFormToTradeInfoOB(AssetSpecPlantForm aForm, ITradeInInfo tradeInInfo, String ccy,
			Locale locale) {
		tradeInInfo.setMake(aForm.getTradeInMake().trim());
		tradeInInfo.setModel(aForm.getTradeInModel().trim());
		tradeInInfo.setRegistrationNo(aForm.getTradeInRegistrationNo().trim());
		if (AbstractCommonMapper.isEmptyOrNull(aForm.getTradeInYearOfManufacture())) {
			tradeInInfo.setYearOfManufacture(null);
		}
		else {
			tradeInInfo.setYearOfManufacture(Integer.valueOf(aForm.getTradeInYearOfManufacture()));
		}

		tradeInInfo.setTradeInValue(UIUtil.mapStringToAmount(locale, ccy, aForm.getTradeInValue().trim(), null));

		tradeInInfo.setTradeInDeposit(UIUtil.mapStringToAmount(locale, ccy, aForm.getTradeInDeposit().trim(), null));
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		AssetSpecPlantForm aForm = (AssetSpecPlantForm) cForm;
		ISpecificChargePlant iObj = (ISpecificChargePlant) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {

			DefaultLogger.debug(LOGOBG, "in try,  Locale " + locale);

			aForm.setAssetType(iObj.getAssetType());
			aForm.setSecEnvRisky(UIUtil.mapOBString_FormString(iObj.getEnvRiskyStatus()));
			aForm.setRemarkEnvRisk(iObj.getEnvRiskyRemarks());
			if(iObj.getMonitorProcess()!=null && !iObj.getMonitorProcess().equals("")){
				aForm.setMonitorProcess(iObj.getMonitorProcess());
			}
			if(iObj.getMonitorFrequency()!=null && !iObj.getMonitorFrequency().equals("")){
				aForm.setMonitorFrequency(iObj.getMonitorFrequency());
			}
			// aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit());
			if (iObj.getPhysicalInspectionFreq() >= 0) {
				aForm.setPhysInspFreq(String.valueOf(iObj.getPhysicalInspectionFreq()));
			}
			if (iObj.getPhysicalInspectionFreqUnit() != null) {
				aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit().trim());
			}

			aForm.setRegistrationNo(iObj.getRegistrationNo());
			aForm.setRegistrationCardNo(iObj.getRegistrationCardNo());
			aForm.setModelNo(iObj.getModelNo());
			aForm.setBrand(iObj.getBrand());
			aForm.setGoodStatus(iObj.getGoodStatus());
			// aForm.setChassisNumber(iObj.getChassisNumber());

			aForm.setPrevOwnerName(iObj.getPrevOwnerName());
			aForm.setPrevFinancierName(iObj.getPrevFinancierName());

			aForm.setYard(iObj.getYard());
			
			aForm.setDescription(iObj.getRemarks());
			aForm.setIsPhysInsp(String.valueOf(iObj.getIsPhysicalInspection()));
			if (iObj.getPhysicalInspectionFreqUnit() != null) {
				aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit().trim());
			}
			DefaultLogger.debug(LOGOBG, "mapOBDate_FormString ");

			aForm.setRegistrationDate(UIUtil.mapOBDate_FormString(locale, iObj.getRegistrationDate()));
			if(iObj.getLastPhysicalInspectDate()!=null){
				aForm.setDatePhyInspec(DateUtil.formatDate(locale, iObj.getLastPhysicalInspectDate()));
				}
			if(iObj.getNextPhysicalInspectDate()!=null){
				aForm.setNextPhysInspDate(DateUtil.formatDate(locale, iObj.getNextPhysicalInspectDate()));
				}
			aForm.setRepossessionDate(UIUtil.mapOBDate_FormString(locale, iObj.getRepossessionDate()));
			aForm.setDateSecurityEnv(UIUtil.mapOBDate_FormString(locale, iObj.getEnvRiskyDate()));
			aForm.setDatePurchase(UIUtil.mapOBDate_FormString(locale, iObj.getPurchaseDate()));

			DefaultLogger.debug(LOGOBG, "mapOBInteger_FormString ");

			// aForm.setPhysInspFreq(UIUtil.mapIntegerToString(iObj.
			// getPhysicalInspectionFreq()));
			aForm.setResidualAssetLife(UIUtil.mapIntegerToString(iObj.getResidualAssetLife()));
			aForm.setResidualAssetLifeUOM(iObj.getResidualAssetLifeUOM());

			 if (iObj.getDocPerfectAge() > 0){
			 aForm.setDocPerfectAge(UIUtil.mapIntegerToString(iObj.
			 getDocPerfectAge()));
			 }
			 else {
					aForm.setDocPerfectAge(null);
				}
			/*if ((iObj.getPerfectionDate() != null) && (String.valueOf(iObj.getYearOfManufacture()) != null)) {

				GregorianCalendar year = new GregorianCalendar();

				if (aForm.getPerfectionDate() != null) {

					year.setTime(iObj.getPerfectionDate());
					int perfectionYear = year.get(Calendar.YEAR);

					int age = perfectionYear - iObj.getYearOfManufacture();
					aForm.setDocPerfectAge(String.valueOf(age));
				}
			}
			else {
				aForm.setDocPerfectAge(String.valueOf(0));
			}*/

			if ((iObj.getRepossessionDate() != null) && (String.valueOf(iObj.getYearOfManufacture()) != null)) {

				GregorianCalendar year = new GregorianCalendar();

				if (aForm.getRepossessionDate() != null) {

					year.setTime(iObj.getRepossessionDate());
					int repossessionYear = year.get(Calendar.YEAR);

					int age = repossessionYear - iObj.getYearOfManufacture();
					aForm.setDocPerfectAge(String.valueOf(age));
				}
			}
			else {
				aForm.setRepossessionAge(String.valueOf(0));
			}

			aForm.setYearOfManufacture(UIUtil.mapIntegerToString(iObj.getYearOfManufacture()));
			aForm.setRepossessionAge(UIUtil.mapIntegerToString(iObj.getRepossessionAge()));

			DefaultLogger.debug(LOGOBG, "mapOBAmount_FormString ");

			aForm.setRegistrationFee(UIUtil.mapAmountToString(locale, iObj.getRegistrationFee()));
			aForm.setPurchasePrice(UIUtil.mapAmountToString(locale, iObj.getPurchasePrice()));
			// aForm.setDepreciateRate(UIUtil.mapAmountToString(locale,
			// iObj.getDepreciateRate()));
			aForm.setSalesProceed(UIUtil.mapAmountToString(locale, iObj.getSalesProceed()));
			aForm.setScrapValue(UIUtil.mapAmountToString(locale, iObj.getScrapValue()));
			aForm.setAssetValue(UIUtil.mapAmountToString(locale, iObj.getAssetValue()));
			// Added by Saritha for Asset based Plant & Equipment -- Begin
			aForm.setPurpose(iObj.getPurpose());
			aForm.setEquipmf(iObj.getEquipmf());
			aForm.setEquipriskgrading(iObj.getEquipriskgrading());
			aForm.setEquipcode(iObj.getEquipcode());
			aForm.setQuantity(UIUtil.mapAmountToString(locale, iObj.getQuantity()));
			aForm.setSerialNumber(iObj.getSerialNumber());
			aForm.setInvoiceNumber(iObj.getInvoiceNumber());
			aForm.setInspectionStatus(iObj.getInspectionStatusEntryCode());

			aForm.setDptradein(UIUtil.mapAmountToString(locale, iObj.getDptradein()));
			if ("0.00".equals(aForm.getDptradein())) {
				aForm.setDptradein("");
			}

			aForm.setDpcash(UIUtil.mapAmountToString(locale, iObj.getDpcash()));
			if ("0.00".equals(aForm.getDpcash())) {
				aForm.setDpcash("");
			}

			aForm.setTradeinValue(UIUtil.mapAmountToString(locale, iObj.getTradeinValue()));
			if ("0.00".equals(aForm.getTradeinValue())) {
				aForm.setTradeinValue("");
			}

			if (iObj.getPlist() != null) {
				aForm.setPlist(CurrencyManager.convertToString(locale, iObj.getPlist()));
			}
			if ("-1.00".equals(aForm.getPlist())) {
				aForm.setPlist("");
			}
			aForm.setRamId(iObj.getRamId());
			aForm.setRlSerialNumber(iObj.getRlSerialNumber());
			aForm.setChattelSoldDate(DateUtil.formatDate(locale, iObj.getChattelSoldDate()));
			aForm.setPubTransport(iObj.getPubTransport());

			IValuation iVal = (IValuation) iObj.getValuationIntoCMS();

			if ((iVal != null) && (iVal.getOlv() != null)) {
				aForm.setOlv(UIUtil.mapAmountToString(locale, iVal.getOlv()));
				if (iVal.getRemainusefullife() != null) {
					aForm.setRemainusefullife(iVal.getRemainusefullife().toString());
				}
			}

			if (iVal != null) {
				aForm.setValuationbasis(iVal.getValuationbasis());
			}

			ITradeInInfo[] tradeInInfo = iObj.getTradeInInfo();
			if ((tradeInInfo == null) || (tradeInInfo.length == 0)) {
				tradeInInfo = new OBTradeInInfo[1];
				tradeInInfo[0] = new OBTradeInInfo();
			}

			if (tradeInInfo[0].getMake() != null) {
				aForm.setTradeInMake(tradeInInfo[0].getMake());
			}

			if (tradeInInfo[0].getModel() != null) {
				aForm.setTradeInModel(tradeInInfo[0].getModel());
			}

			if (tradeInInfo[0].getRegistrationNo() != null) {
				aForm.setTradeInRegistrationNo(tradeInInfo[0].getRegistrationNo());
			}

			if (tradeInInfo[0].getYearOfManufacture() != null) {
				aForm.setTradeInYearOfManufacture(String.valueOf(tradeInInfo[0].getYearOfManufacture()));
			}

			if ((tradeInInfo[0].getTradeInValue() != null)
					&& (tradeInInfo[0].getTradeInValue().getAmountAsDouble() != ICMSConstant.DOUBLE_INVALID_VALUE)) {
				aForm.setTradeInValue(UIUtil.mapAmountToString(locale, tradeInInfo[0].getTradeInValue()));
			}

			if ((tradeInInfo[0].getTradeInDeposit() != null)
					&& (tradeInInfo[0].getTradeInDeposit().getAmountAsDouble() != ICMSConstant.DOUBLE_INVALID_VALUE)) {
				aForm.setTradeInDeposit(UIUtil.mapAmountToString(locale, tradeInInfo[0].getTradeInDeposit()));
			}

			aForm.setInvoiceDate(DateUtil.formatDate(locale, iObj.getInvoiceDate()));
			
			if(null!=iObj.getInsuranceStatus()){
				aForm.setInsuranceStatus(iObj.getInsuranceStatus());
			}
			if(iObj.getOriginalTargetDate()!=null  && !iObj.getOriginalTargetDate().equals("")){
				String date=DateUtil.formatDate(locale,iObj.getOriginalTargetDate());
				aForm.setOriginalTargetDate(date);
				}
			if(iObj.getNextDueDate()!=null  && !iObj.getNextDueDate().equals("")){
				String date=DateUtil.formatDate(locale,iObj.getNextDueDate());
				aForm.setNextDueDate(date);
				}
			if(iObj.getDateDeferred()!=null  && !iObj.getDateDeferred().equals("")){
				String date=DateUtil.formatDate(locale,iObj.getDateDeferred());
				aForm.setDateDeferred(date);
				}
			if(iObj.getWaivedDate()!=null  && !iObj.getWaivedDate().equals("")){
				String date=DateUtil.formatDate(locale,iObj.getWaivedDate());
				aForm.setWaivedDate(date);
				}
			if(iObj.getCreditApprover()!=null && (!iObj.getCreditApprover().equals("")))
            {
				aForm.setCreditApprover(iObj.getCreditApprover());
            }
		}
		catch (Exception e) {
			throw new MapperException("Failed to map ob to form for asset plant and equipment", e);
		}
		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((ISpecificChargePlant) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
