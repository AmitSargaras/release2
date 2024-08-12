package com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.ITradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBTradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle.ISpecificChargeVehicle;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 24, 2007 Time: 10:59:41 AM
 * To change this template use File | Settings | File Templates.
 */

public class AssetSpecVehiclesMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		ISpecificChargeVehicle iObj = (ISpecificChargeVehicle) obj;
		AssetSpecVehiclesForm aForm = (AssetSpecVehiclesForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		Date stageDate;
		try {

			iObj.setRegistrationNo(aForm.getRegNo());
			if(aForm.getMonitorProcess()!=null && !aForm.getMonitorProcess().equals("")){
				iObj.setMonitorProcess(aForm.getMonitorProcess());
			}
			if(aForm.getMonitorFrequency()!=null && !aForm.getMonitorFrequency().equals("")){
				iObj.setMonitorFrequency(aForm.getMonitorFrequency());
			}
			iObj.setBrand(aForm.getBrand());
			iObj.setModelNo(aForm.getModelNo());
			iObj.setAssetType(aForm.getAssetType());
			iObj.setEnvRiskyStatus(aForm.getSecEnvRisky());
			iObj.setEnvRiskyRemarks(aForm.getRemarkEnvRisk());
			if(aForm.getScrapValue()!=null && !"".equals(aForm.getScrapValue())){
			iObj.setScrapValue(new Amount(new BigDecimal(UIUtil.removeComma(aForm.getScrapValue())), BaseCurrency.getCurrencyCode())); //Phase 3 CR:comma separated
			}
			//Added by Pramod Katkar for New Filed CR on 7-08-2013
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp())) {
				iObj.setIsPhysicalInspection(Boolean.valueOf(aForm.getIsPhysInsp()).booleanValue());
			}

			/*if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPhysInspFreq())&& "true".equalsIgnoreCase(aForm.getIsPhysInsp())) {
				iObj.setPhysicalInspectionFreq(Integer.parseInt(aForm.getPhysInspFreq().trim()));
*/
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

			iObj.setRemarks(aForm.getDescription());
			iObj.setCoverageType(aForm.getCoverageType());

			iObj.setRegistrationCardNo(aForm.getRegistrationCardNo());

			iObj.setChassisNumber(aForm.getChassisNumber());

			iObj.setPrevOwnerName(aForm.getPrevOwnerName());
			iObj.setPrevFinancierName(aForm.getPrevFinancierName());
            
			iObj.setYard(aForm.getYard());
			iObj.setYardOptions(aForm.getYardOptions());

			/* Added by Saritha for Asset based Vehicles */
			iObj.setIindicator(aForm.getIindicator());
			iObj.setCollateralfee(aForm.getCollateralfee());

			iObj.setDptradein(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getDptradein(), null));

			iObj.setDpcash(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getDpcash(), null));

			iObj.setTradeinValue(UIUtil
					.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getTradeinValue(), null));

			iObj.setFcharges(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getFcharges(), null));

			//iObj.setPlist(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getPlist(), null));
			if(aForm.getPlist()!=null && !aForm.getPlist().trim().equals(""))
			{Amount amt = new Amount();
			amt.setAmount(Double.parseDouble(UIUtil.removeComma(aForm.getPlist()))); //Phase 3 CR:comma separated
			amt.setCurrencyCode(BaseCurrency.getCurrencyCode().toString());
			iObj.setPlist(amt);
				//iObj.setPlist(new Amount(new BigDecimal(aForm.getPlist()), BaseCurrency.getCurrencyCode()));
			}
			iObj.setHeavyvehicle(aForm.getHeavyvehicle());
			iObj.setPubTransport(aForm.getPubTransport());
			/* Added by Saritha for Asset based Vehicles */

			iObj.setEngineNo(aForm.getEngineNo());
			iObj.setGoodStatus(aForm.getGoodStatus());
			iObj.setTransType(aForm.getTransType());
			iObj.setEnergySource(aForm.getEnergySource());
			iObj.setVehColor(aForm.getVehColor());
			iObj.setStartDate(UIUtil.mapStringToDate(locale, iObj.getStartDate(), aForm.getStartDate()));
			iObj.setRamId(aForm.getRamId());
			iObj.setDescriptionAssets(aForm.getDescriptionAssets());
			
			if(aForm.getAssetCollateralBookingVal()!=null){
				if("".equals(aForm.getAssetCollateralBookingVal())){
					iObj.setAssetCollateralBookingVal(null);
				}else{
					iObj.setAssetCollateralBookingVal(new BigDecimal(UIUtil.removeComma(aForm.getAssetCollateralBookingVal()).trim()));
				}
			}
			iObj.setRegistrationDate(UIUtil.mapStringToDate(locale, iObj.getRegistrationDate(), aForm.getRegDate()));
			iObj.setPurchaseDate(UIUtil.mapStringToDate(locale, iObj.getPurchaseDate(), aForm.getDatePurchase()));
			iObj.setEnvRiskyDate(UIUtil.mapStringToDate(locale, iObj.getEnvRiskyDate(), aForm.getDateSecurityEnv()));
			iObj.setLastPhysicalInspectDate(UIUtil.mapStringToDate(locale, iObj.getLastPhysicalInspectDate(), aForm
					.getDatePhyInspec()));
			iObj.setRepossessionDate(UIUtil.mapStringToDate(locale, iObj.getRepossessionDate(), aForm
					.getRepossessionDate()));

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

			iObj.setRepossessionAge(UIUtil.mapStringToInteger(aForm.getRepossessionAge()));
			iObj.setYearOfManufacture(UIUtil.mapStringToInteger(aForm.getYearMfg()));
			iObj.setHorsePower(aForm.getHorsePower());
			iObj.setYearOfManufacture(UIUtil.mapStringToInteger(aForm.getYearMfg()));

			iObj.setRegistrationFee(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getRegFee(), null));

			//iObj.setPurchasePrice(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getPurchasePrice(),null));
			if(aForm.getPurchasePrice()!= null && !aForm.getPurchasePrice().trim().equals(""))
				iObj.setPurchasePrice(new Amount(new BigDecimal(UIUtil.removeComma(aForm.getPurchasePrice())), BaseCurrency.getCurrencyCode())); //Phase 3 CR:comma separated
			
			iObj.setNominalValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getNominalValue(), null));

		//	iObj.setScrapValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getScrapValue(), null));

			iObj.setAssetValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getAssetValue(), null));

			iObj.setSalesProceed(UIUtil
					.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getSalesProceed(), null));

			iObj.setCoe(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getCoe(), null));

			iObj.setAmtCollectedFromSales(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm
					.getAmtCollectedFromSales(), null));

			iObj.setRoadTaxAmtType(aForm.getRoadTaxAmtType());
			if (aForm.getRoadTaxAmtType() != null) {
				if (aForm.getRoadTaxAmtType().equals("Y")) {
					iObj.setRoadTaxAmt(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm
							.getYearlyRoadTaxAmt(), iObj.getRoadTaxAmt()));
				}
				else if (aForm.getRoadTaxAmtType().equals("H")) {
					iObj.setRoadTaxAmt(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm
							.getHalfYearlyRoadTaxAmt(), iObj.getRoadTaxAmt()));
				}
			}
			iObj.setRoadTaxExpiryDate(UIUtil.mapStringToDate(locale, iObj.getRoadTaxExpiryDate(), aForm
					.getRoadTaxExpiryDate()));
			if (ICMSConstant.TRUE_VALUE.equals(aForm.getAllowPassive())) {
				iObj.setIsAllowPassive(true);
			}
			else {
				iObj.setIsAllowPassive(false);
			}

			iObj.setPBTIndicator(aForm.getPbtIndicator());
			if (StringUtils.isNotBlank(aForm.getPbrPbtPeriod())) {
				iObj.setPbtPbrPeriodDays(Long.valueOf(aForm.getPbrPbtPeriod()));
			}
			else {
				iObj.setPbtPbrPeriodDays(null);
			}
			iObj.setLogBookNumber(aForm.getLogBookNumber());
			iObj.setEngineCapacity(aForm.getEngineCapacity());
			iObj.setOwnershipClaimNumber(aForm.getOwnershipClaimNumber());
			iObj.setDealerName(aForm.getDealerName());
			iObj.setEHakMilikNumber(aForm.getEHakMilikNumber());
			iObj.setRlSerialNumber(aForm.getRlSerialNumber());
		//	iObj.setChattelSoldDate(DateUtil.convertDate(locale, aForm.getChattelSoldDate()));

			ITradeInInfo[] tradeInInfo = iObj.getTradeInInfo();
			if ((tradeInInfo == null) || (tradeInInfo.length == 0)) {
				tradeInInfo = new OBTradeInInfo[1];
				tradeInInfo[0] = new OBTradeInInfo();
				mapVehicleFormToTradeInfoOB(aForm, tradeInInfo[0], iObj.getCurrencyCode(), locale);
			}
			else {
				mapVehicleFormToTradeInfoOB(aForm, tradeInInfo[0], iObj.getCurrencyCode(), locale);
			}
			iObj.setTradeInInfo(tradeInInfo);
			iObj.setInvoiceDate(UIUtil.mapStringToDate(locale, iObj.getInvoiceDate(), aForm.getInvoiceDate()));
			iObj.setInvoiceNo(aForm.getInvoiceNo());
		}
		catch (Exception e) {
			throw new MapperException("failed to map Form to OB", e);
		}

		return iObj;

	}

	private static void mapVehicleFormToTradeInfoOB(AssetSpecVehiclesForm aForm, ITradeInInfo tradeInInfo, String ccy,
			Locale locale) {
	//	tradeInInfo.setMake(aForm.getTradeInMake().trim());
		tradeInInfo.setMake(aForm.getBrand().trim());
	//	tradeInInfo.setModel(aForm.getTradeInModel().trim());
		tradeInInfo.setModel(aForm.getModelNo().trim());
	//	tradeInInfo.setRegistrationNo(aForm.getTradeInRegistrationNo().trim());
		tradeInInfo.setRegistrationNo(aForm.getRegNo().trim());
	//	if (AbstractCommonMapper.isEmptyOrNull(aForm.getTradeInYearOfManufacture())) {
		if (AbstractCommonMapper.isEmptyOrNull(aForm.getYearMfg())) {
			tradeInInfo.setYearOfManufacture(null);
		}
		else {
		//	tradeInInfo.setYearOfManufacture(Integer.valueOf(aForm.getTradeInYearOfManufacture()));
			tradeInInfo.setYearOfManufacture(Integer.valueOf(aForm.getYearMfg()));
		}

	//	tradeInInfo.setTradeInValue(UIUtil.mapStringToAmount(locale, ccy, aForm.getTradeInValue().trim(), null));

	//	tradeInInfo.setTradeInDeposit(UIUtil.mapStringToAmount(locale, ccy, aForm.getTradeInDeposit().trim(), null));
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		ISpecificChargeVehicle iObj = (ISpecificChargeVehicle) obj;
		AssetSpecVehiclesForm aForm = (AssetSpecVehiclesForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			aForm.setRegNo(iObj.getRegistrationNo());
			aForm.setYearMfg(String.valueOf(iObj.getYearOfManufacture()));
			aForm.setBrand(iObj.getBrand());
			aForm.setModelNo(iObj.getModelNo());
			aForm.setAssetType(iObj.getAssetType());
			if(iObj.getMonitorProcess()!=null && !iObj.getMonitorProcess().equals("")){
				aForm.setMonitorProcess(iObj.getMonitorProcess());
			}
			if(iObj.getMonitorFrequency()!=null && !iObj.getMonitorFrequency().equals("")){
				aForm.setMonitorFrequency(iObj.getMonitorFrequency());
			}
			aForm.setSecEnvRisky((iObj.getEnvRiskyStatus() != null) ? iObj.getEnvRiskyStatus().trim() : "");
			aForm.setRemarkEnvRisk(iObj.getEnvRiskyRemarks());

			if (iObj.getPhysicalInspectionFreq() >= 0) {
				aForm.setPhysInspFreq(String.valueOf(iObj.getPhysicalInspectionFreq()));
			}
			if (iObj.getPhysicalInspectionFreqUnit() != null) {
				aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit().trim());
			}
			aForm.setIsPhysInsp(String.valueOf(iObj.getIsPhysicalInspection()));

			aForm.setDescription(iObj.getRemarks());
			aForm.setCoverageType(iObj.getCoverageType());
			aForm.setRegistrationCardNo(iObj.getRegistrationCardNo());
			aForm.setChassisNumber(iObj.getChassisNumber());

			aForm.setPrevOwnerName(iObj.getPrevOwnerName());
			aForm.setPrevFinancierName(iObj.getPrevFinancierName());

			aForm.setYard(iObj.getYard());
			aForm.setYardOptions(iObj.getYardOptions());
			/* Added by Saritha for Asset based Vehicles */
			aForm.setIindicator(iObj.getIindicator());
			aForm.setCollateralfee(iObj.getCollateralfee());

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

			aForm.setFcharges(UIUtil.mapAmountToString(locale, iObj.getFcharges()));

			if (iObj.getPlist() != null) {
			//	aForm.setPlist(CurrencyManager.convertToString(locale, iObj.getPlist()));
				

				aForm.setPlist(UIUtil.formatWithCommaAndDecimal(String.valueOf(iObj.getPlist().getAmountAsBigDecimal()))); //Phase 3 CR:comma separated
		
			}
			if ("-1.00".equals(aForm.getPlist())) {
				aForm.setPlist("");
			}
			aForm.setHeavyvehicle(iObj.getHeavyvehicle());
			aForm.setPubTransport(iObj.getPubTransport());
			/* Added by Saritha for Asset based Vehicles */

			aForm.setEngineNo(iObj.getEngineNo());
			aForm.setGoodStatus(iObj.getGoodStatus());
			aForm.setTransType(iObj.getTransType());
			aForm.setEnergySource(iObj.getEnergySource());
			aForm.setVehColor(iObj.getVehColor());
			aForm.setStartDate(DateUtil.formatDate(locale, iObj.getStartDate()));
			aForm.setRamId(iObj.getRamId());
			aForm.setDescriptionAssets(iObj.getDescriptionAssets());
			if(iObj.getAssetCollateralBookingVal()!=null){
			aForm.setAssetCollateralBookingVal(UIUtil.formatWithComma(String.valueOf(iObj.getAssetCollateralBookingVal()))); //Phase 3 CR:comma separated
			}else{
				aForm.setAssetCollateralBookingVal("");	
			}
			
			aForm.setRegDate(DateUtil.formatDate(locale, iObj.getRegistrationDate()));
			aForm.setDatePurchase(DateUtil.formatDate(locale, iObj.getPurchaseDate()));
			aForm.setDateSecurityEnv(DateUtil.formatDate(locale, iObj.getEnvRiskyDate()));
			if(iObj.getLastPhysicalInspectDate()!=null){
			aForm.setDatePhyInspec(DateUtil.formatDate(locale, iObj.getLastPhysicalInspectDate()));
				}
			if(iObj.getNextPhysicalInspectDate()!=null){
			aForm.setNextPhysInspDate(DateUtil.formatDate(locale, iObj.getNextPhysicalInspectDate()));
				}
			aForm.setRepossessionDate(DateUtil.formatDate(locale, iObj.getRepossessionDate()));

			// aForm.setPhysInspFreq(UIUtil.mapIntegerToString(iObj.
			// getPhysicalInspectionFreq()));
			aForm.setResidualAssetLife(UIUtil.mapIntegerToString(iObj.getResidualAssetLife()));
			aForm.setResidualAssetLifeUOM(iObj.getResidualAssetLifeUOM());

			// aForm.setDocPerfectAge(UIUtil.mapIntegerToString(iObj.
			// getDocPerfectAge()));

			if ((iObj.getPerfectionDate() != null) && (String.valueOf(iObj.getYearOfManufacture()) != null)) {

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
			}

			if ((iObj.getRepossessionDate() != null) && (String.valueOf(iObj.getYearOfManufacture()) != null)) {

				GregorianCalendar year = new GregorianCalendar();

				if (aForm.getRepossessionDate() != null) {

					year.setTime(iObj.getRepossessionDate());
					int possessionYear = year.get(Calendar.YEAR);

					int age = possessionYear - iObj.getYearOfManufacture();
					aForm.setRepossessionAge(String.valueOf(age));
				}
			}
			else {
				aForm.setRepossessionAge(String.valueOf(0));
			}

			aForm.setYearMfg(UIUtil.mapIntegerToString(iObj.getYearOfManufacture()));
			aForm.setHorsePower(iObj.getHorsePower());

			if (iObj.getRegistrationFee() != null) {
				if (iObj.getRegistrationFee().getAmountAsDouble() == ICMSConstant.DOUBLE_INVALID_VALUE) {
					aForm.setRegFee("");
				}
				else {
					aForm.setRegFee(UIUtil.mapAmountToString(locale, iObj.getRegistrationFee()));
				}
			}

			if (iObj.getPurchasePrice() != null) {
				if (iObj.getPurchasePrice().getAmountAsDouble() == ICMSConstant.DOUBLE_INVALID_VALUE) {
					aForm.setPurchasePrice("");
				}
				else {
				//	aForm.setPurchasePrice(UIUtil.mapAmountToString(locale, iObj.getPurchasePrice()));
					aForm.setPurchasePrice(UIUtil.formatWithCommaAndDecimal(String.valueOf(iObj.getPurchasePrice().getAmountAsBigDecimal()))); //Phase 3 CR:comma separated
					
				//	form.setGroupExpLimit(String.valueOf(item.getGroupExpLimit().getAmountAsBigDecimal()));			
				}
			}
			
			if (iObj.getScrapValue() != null) {
				//Phase 3 CR:comma separated
					aForm.setScrapValue(UIUtil.formatWithCommaAndDecimal(String.valueOf(iObj.getScrapValue().getAmountAsBigDecimal()))); 
			
				}

		//	aForm.setScrapValue(UIUtil.mapAmountToString(locale, iObj.getScrapValue()));
			if (iObj.getAssetValue() != null) {
				aForm.setAssetValue(UIUtil.mapAmountToString(locale, iObj.getAssetValue()));
			}
			// aForm.setDepreciateRate(UIUtil.mapAmountToString(locale,
			// iObj.getDepreciateRate()));
			aForm.setSalesProceed(UIUtil.mapAmountToString(locale, iObj.getSalesProceed()));
			aForm.setCoe(UIUtil.mapAmountToString(locale, iObj.getCoe()));
			aForm.setNominalValue(UIUtil.mapAmountToString(locale, iObj.getNominalValue()));

			// end of gcms

			aForm.setAmtCollectedFromSales(UIUtil.mapAmountToString(locale, iObj.getAmtCollectedFromSales()));

			aForm.setRoadTaxAmtType(iObj.getRoadTaxAmtType());
			if (iObj.getRoadTaxAmtType() != null) {
				if (iObj.getRoadTaxAmtType().equals("Y")) {
					aForm.setYearlyRoadTaxAmt(UIUtil.mapAmountToString(locale, iObj.getRoadTaxAmt()));
					aForm.setHalfYearlyRoadTaxAmt("");
				}
				else if (iObj.getRoadTaxAmtType().equals("H")) {
					aForm.setYearlyRoadTaxAmt("");
					aForm.setHalfYearlyRoadTaxAmt(UIUtil.mapAmountToString(locale, iObj.getRoadTaxAmt()));
				}
			}
			aForm.setRoadTaxExpiryDate(DateUtil.formatDate(locale, iObj.getRoadTaxExpiryDate()));
			if (iObj.getIsAllowPassive()) {
				aForm.setAllowPassive(ICMSConstant.TRUE_VALUE);
			}
			else {
				aForm.setAllowPassive(ICMSConstant.FALSE_VALUE);
			}

			aForm.setPbtIndicator(iObj.getPBTIndicator());
			if (iObj.getPbtPbrPeriodDays() != null) {
				aForm.setPbrPbtPeriod(iObj.getPbtPbrPeriodDays().toString());
			}
			aForm.setLogBookNumber(iObj.getLogBookNumber());
			aForm.setEngineCapacity(iObj.getEngineCapacity());
			aForm.setOwnershipClaimNumber(iObj.getOwnershipClaimNumber());
			aForm.setDealerName(iObj.getDealerName());
			aForm.setEHakMilikNumber(iObj.getEHakMilikNumber());
			aForm.setRlSerialNumber(iObj.getRlSerialNumber());
			aForm.setChattelSoldDate(DateUtil.formatDate(locale, iObj.getChattelSoldDate()));

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
			aForm.setInvoiceNo(iObj.getInvoiceNo());
		}
		catch (Exception e) {
			throw new MapperException("failed to map OB to Form", e);
		}

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((ISpecificChargeVehicle) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
