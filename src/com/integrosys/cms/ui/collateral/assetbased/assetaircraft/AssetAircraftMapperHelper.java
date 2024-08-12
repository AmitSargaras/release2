//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetaircraft;

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
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.ISpecificChargeAircraft;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.collateral.assetbased.AssetBasedForm;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class AssetAircraftMapperHelper {
	final static String COLLETERAL_CODE="COL0000139";
	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		ISpecificChargeAircraft iObj = (ISpecificChargeAircraft) obj;
		AssetAircraftForm aForm = (AssetAircraftForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("AssetAircraftMapperHelper - mapFormToOB", "Locale is: " + locale);
		Date stageDate;
		try {
						
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInsUndertaking())) {
				iObj.setIsInsBrokerUndertake(Boolean.valueOf(aForm.getInsUndertaking()).booleanValue());
			}
			iObj.setCollateralCode(aForm.getColCodeHiddenValue());// Added for Bill As colleteral validation on 05-May-2015
			iObj.setProcessAgent(aForm.getProcessAgentOp());
			iObj.setProcessAgentCountry(aForm.getCountryProcesAgent());
			iObj.setLegalAdvice(aForm.getSplLegalAdvise());
			iObj.setExportCrAgency(aForm.getXportCrdtAgency());
			iObj.setGuarantors(aForm.getGuarantors());
			iObj.setInsurers(aForm.getInsuers());
			iObj.setGoodStatus(aForm.getGoodStatus());
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getRegDate())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getRegistrationDate(), aForm.getRegDate());
				iObj.setRegistrationDate(stageDate);
			}
			else {
				iObj.setRegistrationDate(null);
			}
			if (aForm.getYearMfg().equals("")) {
				iObj.setYearOfManufacture(0);
			}
			else {
				iObj.setYearOfManufacture(Integer.parseInt(aForm.getYearMfg().trim()));
			}
			if(aForm.getMonitorProcess()!=null && !aForm.getMonitorProcess().equals("")){
				iObj.setMonitorProcess(aForm.getMonitorProcess());
			}
			if(aForm.getMonitorFrequency()!=null && !aForm.getMonitorFrequency().equals("")){
				iObj.setMonitorFrequency(aForm.getMonitorFrequency());
			}
			iObj.setAssetType(aForm.getAssetType());
			iObj.setModelNo(aForm.getModelNo());
			iObj.setPurchasePrice(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getPurchasePrice(),
					null));

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDatePurchase())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getPurchaseDate(), aForm.getDatePurchase());
				iObj.setPurchaseDate(stageDate);
			}
			else {
				iObj.setPurchaseDate(null);
			}

			iObj.setAircraftSerialNo(aForm.getAircraftSN());
			iObj.setManufacturer(aForm.getManuName());
			iObj.setManufacturerWarranties(aForm.getManuWarr());
			iObj.setAssignors(aForm.getAssignors());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAssgOfInsurance())) {
				iObj.setIsInsAssign(Boolean.valueOf(aForm.getAssgOfInsurance()).booleanValue());
			}

			iObj.setInsAssignAmount(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getAmtAssignment(),
					null));

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getEffDateAssgIns())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getInsAssignEffectiveDate(), aForm
						.getEffDateAssgIns());
				iObj.setInsAssignEffectiveDate(stageDate);
			}
			else {
				iObj.setInsAssignEffectiveDate(null);
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getExpiryDateAssignInsure())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getInsAssignExpiryDate(), aForm
						.getExpiryDateAssignInsure());
				iObj.setInsAssignExpiryDate(stageDate);
			}
			else {
				iObj.setInsAssignExpiryDate(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAssgOfReInsurance())) {
				iObj.setIsReinsAssign(Boolean.valueOf(aForm.getAssgOfReInsurance()).booleanValue());
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getEffDateAssgReIns())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getReinsAssignEffectiveDate(), aForm
						.getEffDateAssgReIns());
				iObj.setReinsAssignEffectiveDate(stageDate);
			}
			else {
				iObj.setReinsAssignEffectiveDate(null);
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getExpDateAssignReInsure())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getReinsAssignExpiryDate(), aForm
						.getExpDateAssignReInsure());
				iObj.setReinsAssignExpiryDate(stageDate);
			}
			else {
				iObj.setReinsAssignExpiryDate(null);
			}

			iObj.setEnvRiskyStatus(aForm.getSecEnvRisky());
			iObj.setEnvRiskyRemarks(aForm.getRemarkEnvRisk());
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateSecurityEnv())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getEnvRiskyDate(), aForm.getDateSecurityEnv());
				iObj.setEnvRiskyDate(stageDate);
			}
			else {
				iObj.setEnvRiskyDate(null);
			}

			//Added by Pramod Katkar for New Filed CR on 5-08-2013
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

			iObj.setNominalValue(UIUtil
					.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getNominalValue(), null));

			iObj.setRemarks(aForm.getDescription());

			// added for gcms

			iObj.setResidualAssetLife(UIUtil.mapStringToInteger(aForm.getResidualAssetLife()));
			iObj.setResidualAssetLifeUOM(aForm.getResidualAssetLifeUOM());

			/*
			 * if (AbstractCommonMapper.isEmptyOrNull(aForm.getDocPerfectAge()))
			 * { iObj.setDocPerfectAge(0); } else {
			 * iObj.setDocPerfectAge(Integer
			 * .parseInt(aForm.getDocPerfectAge().trim())); }
			 */

			/*
			 * if (aForm.getPerfectionDate() != null && aForm.getYearMfg()!=
			 * null ){
			 * 
			 * GregorianCalendar year = new GregorianCalendar();
			 * 
			 * if(iObj.getPerfectionDate() != null){
			 * 
			 * year.setTime(iObj.getPerfectionDate()); int perfectionYear =
			 * year.get( Calendar.YEAR );
			 * 
			 * int age = perfectionYear - Integer.parseInt(aForm.getYearMfg());
			 * iObj.setDocPerfectAge(age); } }else{ iObj.setDocPerfectAge(0); }
			 */

			iObj.setScrapValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getScrapValue(), null));

			/*
			 * if (aForm.getAssetValue().equals("") && (iObj.getAssetValue() !=
			 * null) && (iObj.getAssetValue().getAmount() > 0)) {
			 * iObj.setAssetValue(CurrencyManager.convertToAmount(locale,
			 * iObj.getCurrencyCode(), "0")); } else if
			 * (!AbstractCommonMapper.isEmptyOrNull(aForm.getAssetValue())) {
			 * iObj.setAssetValue(CurrencyManager.convertToAmount(locale,
			 * iObj.getCurrencyCode(), aForm .getAssetValue())); }
			 */

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getChattelSoldDate())) {
				iObj.setChattelSoldDate(DateUtil.convertDate(locale, aForm.getChattelSoldDate()));
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getRepossessionDate())) {
				iObj.setRepossessionDate(DateUtil.convertDate(locale, aForm.getRepossessionDate()));
			}

			if (aForm.getCgcPledged().equals(ICMSConstant.TRUE_VALUE)) {
				iObj.setIsCGCPledged(true);
			}
			else {
				iObj.setIsCGCPledged(false);
			}

			iObj.setPubTransport(aForm.getPubTransport());
			iObj.setRlSerialNumber(aForm.getRlSerialNumber());

			iObj.setTradeinValue(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getTradeinValue(), iObj
					.getTradeinValue()));

			iObj.setTradeinDeposit(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getTradeinDeposit(),
					iObj.getTradeinDeposit()));

			// iObj.setDepreciateRate(UIUtil.mapStringToAmount(locale,
			// iObj.getCurrencyCode(), aForm.getDepreciateRate(),
			// iObj.getDepreciateRate()));
			
			iObj.setRamId(aForm.getRamId());
			
			if (aForm.getStartDate() != null && !aForm.getStartDate().equals("")) {
				iObj.setStartDate(DateUtil.convertDate(aForm.getStartDate()));
			}
			else {
				iObj.setStartDate(null);
			}
			
			if (aForm.getMaturityDate() != null && !aForm.getMaturityDate().equals("")) {
				iObj.setMaturityDate(DateUtil.convertDate(aForm.getMaturityDate()));
			}
			else {
				iObj.setMaturityDate(null);
			}
			

		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		ISpecificChargeAircraft iObj = (ISpecificChargeAircraft) obj;
		AssetAircraftForm aForm = (AssetAircraftForm) cForm;
		String collateralHiddenValue = null;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("AssetAircraftMapperHelper - mapOBToForm", "Locale is: " + locale);
		Amount amt;
		
		// Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015 | Starts
		aForm.setColCodeHiddenValue(iObj.getCollateralCode());
		try {
			
			if (aForm instanceof AssetBasedForm)
			{
				    AssetAircraftForm assetBasedForm = (AssetAircraftForm)aForm;
				    if(assetBasedForm != null){
				    	collateralHiddenValue = (assetBasedForm.getColCodeHiddenValue() != null && COLLETERAL_CODE.equalsIgnoreCase(assetBasedForm.getColCodeHiddenValue())) ? assetBasedForm.getColCodeHiddenValue() : null;
				    }
				    if(collateralHiddenValue!=null)
				    {				    	
				    	if(aForm.getMonitorFrequency()==null || "".equals(aForm.getMonitorFrequency())){
				    		aForm.setMonitorProcess("N");
				    		iObj.setMonitorProcess("N");
				    	}
				    	else{
				    		aForm.setMonitorProcess("Y");
				    		iObj.setMonitorProcess("Y");
				    	}
				    					    		
				    	if(aForm.getMargin()==null || "".equals(aForm.getMargin()))
				    	{
				    		if(iObj.getMargin()!=0)
				    		{
				    			aForm.setMargin(""+iObj.getMargin());
				    		}
				    		else if(iObj.getMargin() < 0)
				    		{
				    			aForm.setMargin("0");
				    			iObj.setMargin(0);
				    		}
				    		else
				    		{
				    			aForm.setMargin("0");
				    			iObj.setMargin(0);
				    		}
				    	}
				    					    	
				    	if(aForm.getAmountCMV()==null || "".equals(aForm.getAmountCMV())){
				    		aForm.setAmountCMV("0");
				    		
				    	}
				    	
				    	
				    	if("true".equalsIgnoreCase(aForm.getIsPhysInsp()))
				    		((AssetBasedForm) aForm).setIsPhysInsp("true");
				    	else
				    		((AssetBasedForm) aForm).setIsPhysInsp("false");
					    
				    }
			}
			if(iObj.getMargin() < 0)
    		{
    			aForm.setMargin("0");
    			iObj.setMargin(0);
    		}
			// Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015 | Ends
			aForm.setInsUndertaking(String.valueOf(iObj.getIsInsBrokerUndertake()));
			aForm.setProcessAgentOp(iObj.getProcessAgent());
			aForm.setCountryProcesAgent(iObj.getProcessAgentCountry());
			aForm.setSplLegalAdvise(iObj.getLegalAdvice());
			aForm.setXportCrdtAgency(iObj.getExportCrAgency());
			aForm.setGuarantors(iObj.getGuarantors());
			aForm.setInsuers(iObj.getInsurers());
			aForm.setGoodStatus(iObj.getGoodStatus());
			if(iObj.getMonitorProcess()!=null && !iObj.getMonitorProcess().equals("")){
				aForm.setMonitorProcess(iObj.getMonitorProcess());
			}
			if(iObj.getMonitorFrequency()!=null && !iObj.getMonitorFrequency().equals("")){
				if("N".equalsIgnoreCase(aForm.getMonitorProcess()))
				{
					aForm.setMonitorFrequency("");
					iObj.setMonitorFrequency("");
				}
				else
					aForm.setMonitorFrequency(iObj.getMonitorFrequency());
			}
			aForm.setRegDate(DateUtil.formatDate(locale, iObj.getRegistrationDate()));
			if (iObj.getYearOfManufacture() > 0) {
				aForm.setYearMfg(String.valueOf(iObj.getYearOfManufacture()));
			}
			aForm.setAssetType(iObj.getAssetType());
			aForm.setModelNo(iObj.getModelNo());
			amt = iObj.getPurchasePrice();
			if ((amt != null) && (amt.getAmount() > 0) && (amt.getCurrencyCode() != null)) {
				aForm.setPurchasePrice(CurrencyManager.convertToString(locale, amt));
			}
			aForm.setDatePurchase(DateUtil.formatDate(locale, iObj.getPurchaseDate()));
			aForm.setAircraftSN(iObj.getAircraftSerialNo());
			aForm.setManuName(iObj.getManufacturer());
			aForm.setManuWarr(iObj.getManufacturerWarranties());
			aForm.setAssignors(iObj.getAssignors());
			aForm.setAssgOfInsurance(String.valueOf(iObj.getIsInsAssign()));
			amt = iObj.getInsAssignAmount();
			if ((amt != null) && (amt.getAmount() > 0) && (amt.getCurrencyCode() != null)) {
				aForm.setAmtAssignment(CurrencyManager.convertToString(locale, amt));
			}
			aForm.setEffDateAssgIns(DateUtil.formatDate(locale, iObj.getInsAssignEffectiveDate()));
			aForm.setExpiryDateAssignInsure(DateUtil.formatDate(locale, iObj.getInsAssignExpiryDate()));
			aForm.setAssgOfReInsurance(String.valueOf(iObj.getIsReinsAssign()));
			aForm.setEffDateAssgReIns(DateUtil.formatDate(locale, iObj.getReinsAssignEffectiveDate()));
			aForm.setExpDateAssignReInsure(DateUtil.formatDate(locale, iObj.getReinsAssignExpiryDate()));
			if (iObj.getEnvRiskyStatus() != null) {
				aForm.setSecEnvRisky(iObj.getEnvRiskyStatus().trim());
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
			if ((amt != null) && (amt.getAmount() > 0) && (amt.getCurrencyCode() != null)) {
				aForm.setNominalValue(CurrencyManager.convertToString(locale, amt));
			}
			aForm.setDescription(iObj.getRemarks());

			// added for gmcs
			// if (iObj.getDocPerfectAge() > 0){
			// aForm.setDocPerfectAge(String.valueOf(iObj.getDocPerfectAge()));
			// }

			/*
			 * if ((iObj.getPerfectionDate() != null) &&
			 * (String.valueOf(iObj.getYearOfManufacture()) != null)) {
			 * 
			 * GregorianCalendar year = new GregorianCalendar();
			 * 
			 * if (aForm.getPerfectionDate() != null) {
			 * 
			 * year.setTime(iObj.getPerfectionDate()); int perfectionYear =
			 * year.get(Calendar.YEAR);
			 * 
			 * int age = perfectionYear - iObj.getYearOfManufacture();
			 * aForm.setDocPerfectAge(String.valueOf(age)); } } else {
			 * aForm.setDocPerfectAge(String.valueOf(0)); }
			 */

			aForm.setResidualAssetLife(UIUtil.mapIntegerToString(iObj.getResidualAssetLife()));
			aForm.setResidualAssetLifeUOM(iObj.getResidualAssetLifeUOM());

			amt = iObj.getScrapValue();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				aForm.setScrapValue(CurrencyManager.convertToString(locale, amt));
			}

			/*
			 * amt = iObj.getAssetValue(); if ((amt != null) && (amt.getAmount()
			 * > 0) && (amt.getCurrencyCode() != null)) {
			 * aForm.setAssetValue(CurrencyManager.convertToString(locale,
			 * amt)); }
			 */

			if (iObj.getIsCGCPledged()) {
				aForm.setCgcPledged(ICMSConstant.TRUE_VALUE);
			}
			else {
				aForm.setCgcPledged(ICMSConstant.FALSE_VALUE);
			}

			aForm.setPubTransport(iObj.getPubTransport());
			aForm.setChattelSoldDate(DateUtil.formatDate(locale, iObj.getChattelSoldDate()));
			aForm.setRepossessionDate((DateUtil.formatDate(locale, iObj.getRepossessionDate())));
			aForm.setRlSerialNumber(iObj.getRlSerialNumber());

			aForm.setTradeinValue(UIUtil.mapAmountToString(locale, iObj.getTradeinValue()));
			if (aForm.getTradeinValue() != null) {
				if ("0.00".equals(aForm.getTradeinValue())) {
					aForm.setTradeinValue("");
				}
			}
			else {
				aForm.setTradeinValue("");
			}
			aForm.setTradeinDeposit(UIUtil.mapAmountToString(locale, iObj.getTradeinDeposit()));
			if (aForm.getTradeinDeposit() != null) {
				if ("0.00".equals(aForm.getTradeinDeposit())) {
					aForm.setTradeinDeposit("");
				}
			}
			else {
				aForm.setTradeinDeposit("");
			}
			
			aForm.setRamId(iObj.getRamId());
			if(iObj.getStartDate()!= null && !iObj.getStartDate().equals("")){
				aForm.setStartDate(DateUtil.formatDate(locale, iObj.getStartDate()));
			}
			if(iObj.getMaturityDate() != null && !iObj.getMaturityDate().equals("")){
				aForm.setMaturityDate(DateUtil.formatDate(locale, iObj.getMaturityDate()));
			}
			
			// aForm.setDepreciateRate(UIUtil.mapAmountToString(locale,
			// iObj.getDepreciateRate()));

		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((ISpecificChargeAircraft) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}
}
