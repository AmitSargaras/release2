/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/inskeyman/InsKeymanMapperHelper.java,v 1.11 2003/10/14 11:17:38 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.insprotection.inskeyman;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2003/10/14 11:17:38 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class InsKeymanMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		InsKeymanForm aForm = (InsKeymanForm) cForm;
		IKeymanInsurance iObj = (IKeymanInsurance) obj;
		ILimitCharge[] limit = iObj.getLimitCharges();
		ILimitCharge objLimit = null;
		if (limit!=null && limit.length>0) objLimit=limit[0];
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("InsKeymanMapperHelper - mapFormToOB", "Locale is: " + locale);
		Date stageDate;
		try {

			iObj.setInsurerName(aForm.getInsureName());
			iObj.setInsuranceType(aForm.getInsurType());
			if(aForm.getMonitorProcess()!=null && !aForm.getMonitorProcess().equals("")){
				iObj.setMonitorProcess(aForm.getMonitorProcess());
			}
			if(aForm.getMonitorFrequency()!=null && !aForm.getMonitorFrequency().equals("")){
				iObj.setMonitorFrequency(aForm.getMonitorFrequency());
			}
			iObj.setInsuredCcyCode(iObj.getCurrencyCode());
			if (aForm.getInsuredAmt().equals("") && (iObj.getInsuredAmount() != null)
					&& (iObj.getInsuredAmount().getAmount() > 0)) {
				iObj.setInsuredAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), "0"));
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInsuredAmt())) {
				Amount amt = new Amount();
				amt.setAmountAsBigDecimal(new BigDecimal(UIUtil.removeComma(aForm.getInsuredAmt())));
				amt.setCurrencyCode(iObj.getCurrencyCode());
				iObj.setInsuredAmount(amt);
				//iObj.setInsuredAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm.getInsuredAmt()));
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getEffDateInsurance())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getInsEffectiveDate(), aForm
						.getEffDateInsurance());
				iObj.setInsEffectiveDate(stageDate);
			}
			else {
				iObj.setInsEffectiveDate(null);
			}

			iObj.setPolicyNo(aForm.getPolicyNo());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getExpiryDateInsure())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getInsExpiryDate(), aForm.getExpiryDateInsure());
				iObj.setInsExpiryDate(stageDate);
			}
			else {
				iObj.setInsExpiryDate(null);
			}
			//--Govind S: This field is use for HDFC (Security Priority), Change it from (Bank's Interest Duly Noted) 07/07/2011--//
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getBankInterestNoted())) {
				iObj.setIsBankInterestDulyNoted(Boolean.valueOf(aForm.getBankInterestNoted()).booleanValue());
			}
			if (aForm.getBankRiskConfirmation()!=null && aForm.getBankRiskConfirmation().equals(ICMSConstant.TRUE_VALUE)) iObj.setBankRiskConfirmation(true);
			else iObj.setBankRiskConfirmation(false);
			iObj.setArrInsurer(aForm.getArrInsurer());
			objLimit.setChargeType(aForm.getChargeType());
			limit[0] = objLimit;
			iObj.setLimitCharges(limit);
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp())) {
				iObj.setIsPhysicalInspection(Boolean.valueOf(aForm.getIsPhysInsp()).booleanValue());
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPhysInspFreq())&& "true".equalsIgnoreCase(aForm.getIsPhysInsp())) {
				iObj.setPhysicalInspectionFreq(Integer.parseInt(aForm.getPhysInspFreq().trim()));
			}
			else {
				iObj.setPhysicalInspectionFreq(ICMSConstant.INT_INVALID_VALUE);
			}
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
		}
		catch (Exception e) {
			DefaultLogger.debug("InsKeymanMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		InsKeymanForm aForm = (InsKeymanForm) cForm;
		IKeymanInsurance iObj = (IKeymanInsurance) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        ILimitCharge[] limit = iObj.getLimitCharges();
        ILimitCharge objLimit = null;        
        if (limit != null && limit.length > 0) {
            objLimit = limit[0];
        }
		DefaultLogger.debug("InsKeymanMapperHelper - mapOBToForm", "Locale is: " + locale);
		try {
			aForm.setInsureName(iObj.getInsurerName());
			aForm.setInsurType(iObj.getInsuranceType());
			if ((iObj.getInsuredAmount() != null) && (iObj.getInsuredAmount().getCurrencyCode() != null)) {
				if (iObj.getInsuredAmount().getAmount() > 0) {
					
					//Phase 3 CR:comma separated
					if("IN".equals(iObj.getCollateralType().getTypeCode()) && "IN501".equals(iObj.getCollateralSubType().getSubTypeCode())){
						aForm.setInsuredAmt(UIUtil.formatWithComma(iObj.getInsuredAmount().getAmountAsBigDecimal().toString()));
					}else{
					aForm.setInsuredAmt(CurrencyManager.convertToString(locale, iObj.getInsuredAmount()));
					}
				}
			}
			if(iObj.getMonitorProcess()!=null && !iObj.getMonitorProcess().equals("")){
				aForm.setMonitorProcess(iObj.getMonitorProcess());
			}
			if(iObj.getMonitorFrequency()!=null && !iObj.getMonitorFrequency().equals("")){
				aForm.setMonitorFrequency(iObj.getMonitorFrequency());
			}
			aForm.setEffDateInsurance(DateUtil.formatDate(locale, iObj.getInsEffectiveDate()));
			aForm.setPolicyNo(iObj.getPolicyNo());
			aForm.setExpiryDateInsure(DateUtil.formatDate(locale, iObj.getInsExpiryDate()));
			aForm.setBankInterestNoted(String.valueOf(iObj.getIsBankInterestDulyNoted()));
			if (iObj.getBankRiskConfirmation()) aForm.setBankRiskConfirmation(ICMSConstant.TRUE_VALUE);
			else aForm.setBankRiskConfirmation(ICMSConstant.FALSE_VALUE);
			aForm.setArrInsurer(iObj.getArrInsurer());
			if (objLimit!=null) aForm.setChargeType(objLimit.getChargeType());
				aForm.setIsPhysInsp(String.valueOf(iObj.getIsPhysicalInspection()));
				if("true".equalsIgnoreCase(String.valueOf(iObj.getIsPhysicalInspection()))){
				aForm.setPhysInspFreq(String.valueOf(iObj.getPhysicalInspectionFreq()));
				aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit());
				}
				else{
					aForm.setPhysInspFreq("");
					aForm.setPhysInspFreqUOM("");
				}
				aForm.setDatePhyInspec(DateUtil.formatDate(locale,iObj.getLastPhysicalInspectDate()));
				aForm.setNextPhysInspDate(DateUtil.formatDate(locale,iObj.getNextPhysicalInspectDate()));
		}
		catch (Exception e) {
			DefaultLogger.debug("InsKeymanMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IKeymanInsurance) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
