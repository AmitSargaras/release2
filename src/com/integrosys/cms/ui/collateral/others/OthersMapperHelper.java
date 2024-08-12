/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/others/othersMapperHelper.java
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.others;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.OBLimitCharge;
import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/07/29 10:20:04 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class OthersMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {

		// System.out.println(
		// "$&$&$&$&Enterging mapFormToOB OthersMapperHelper#########1");
		IOthersCollateral iObj = (IOthersCollateral) obj;
		OthersForm aForm = (OthersForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		DefaultLogger.debug("OthersMapperHelper - mapFormToOB", "Locale is: " + locale);
		Date stageDate;

		if (aForm.getEvent().equals(OthersAction.EVENT_DELETE_ITEM)) {
			if (CollateralConstant.LIMIT_CHARGE.equals(aForm.getItemType())) {
				if (aForm.getDeleteItem() != null) {
					String[] id = aForm.getDeleteItem();
					ILimitCharge[] oldList = iObj.getLimitCharges();
					int numDelete = SecuritySubTypeUtil.getNumberOfDelete(id, oldList.length);
					if (numDelete != 0) {
						ILimitCharge[] newList = new OBLimitCharge[oldList.length - numDelete];
						newList = (ILimitCharge[]) SecuritySubTypeUtil.deleteObjByList(oldList, newList, id);
						iObj.setLimitCharges(newList);
					}
				}
			}
			else if (CollateralConstant.INS_INFO.equals(aForm.getItemType())) {
				if (aForm.getDeleteInsItem() != null) {
					String[] id = aForm.getDeleteInsItem();
					IInsurancePolicy[] oldList = iObj.getInsurancePolicies();
					int numDelete = SecuritySubTypeUtil.getNumberOfDelete(id, oldList.length);
					if (numDelete != 0) {
						IInsurancePolicy[] newList = new IInsurancePolicy[oldList.length - numDelete];
						newList = (IInsurancePolicy[]) SecuritySubTypeUtil.deleteObjByList(oldList, newList, id);
						iObj.setInsurancePolicies(newList);
					}
				}
			}
		}
		try {

			iObj.setEnvRiskyStatus(aForm.getSecEnvRisky());
			iObj.setEnvRiskyRemarks(aForm.getRemarkEnvRisk());
			iObj.setGoodStatus(aForm.getGoodStatus());
			if(aForm.getMonitorProcess()!=null && !aForm.getMonitorProcess().equals("")){
				iObj.setMonitorProcess(aForm.getMonitorProcess());
			}
			if(aForm.getMonitorFrequency()!=null && !aForm.getMonitorFrequency().equals("")){
				iObj.setMonitorFrequency(aForm.getMonitorFrequency());
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateSecurityEnv())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getEnvRiskyDate(), aForm.getDateSecurityEnv());
				iObj.setEnvRiskyDate(stageDate);
			}
			else {
				iObj.setEnvRiskyDate(null);
			}


			//Added by Pramod Katkar for New Filed CR on 8-08-2013
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

			if (aForm.getMinimalValue().equals("")) {
				iObj.setMinimalValue(null);
			}
			else {
				iObj.setMinimalValue(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
						.getMinimalValue()));
			}

			iObj.setDescription(aForm.getDescription());
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getUnitsNumber())) iObj.setUnitsNumber(Double.parseDouble(aForm.getUnitsNumber()));
			else iObj.setUnitsNumber(ICMSConstant.DOUBLE_INVALID_VALUE);

		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.others.OthersMapperHelper", "error is :"
					+ e.toString());
			throw new MapperException(e.getMessage());
		}
		/*FSVBalance field is used for asset Value at the time of
		 collateral booking & reservePrice field is used for 
		 Residual scrap value  By Sachin patil*/ 
		iObj.setFSVBalance(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getFSVBalance(), iObj
				.getFSVBalance()));
		iObj.setReservePrice(UIUtil.mapStringToAmount(locale, iObj.getCurrencyCode(), aForm.getReservePrice(), iObj
				.getReservePrice()));
		
		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		// System.out.println(
		// "$&$&$&$&Enterging mapOBToForm OthersMapperHelper#########2");
		IOthersCollateral iObj = (IOthersCollateral) obj;
		OthersForm aForm = (OthersForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("OthersMapperHelper - mapOBToForm", "Locale is: " + locale);

		try {
			if (iObj.getEnvRiskyStatus() != null) {
				aForm.setSecEnvRisky(iObj.getEnvRiskyStatus().trim());
			}
			if ((iObj.getMinimalValue() != null) && (iObj.getMinimalValue().getCurrencyCode() != null)) {
				aForm.setMinimalValue(CurrencyManager.convertToString(locale, iObj.getMinimalValue()));
			}
			aForm.setRemarkEnvRisk(iObj.getEnvRiskyRemarks());
			aForm.setDateSecurityEnv(DateUtil.formatDate(locale, iObj.getEnvRiskyDate()));
			aForm.setIsPhysInsp(String.valueOf(iObj.getIsPhysicalInspection()));
			if(iObj.getMonitorProcess()!=null && !iObj.getMonitorProcess().equals("")){
				aForm.setMonitorProcess(iObj.getMonitorProcess());
			}
			if(iObj.getMonitorFrequency()!=null && !iObj.getMonitorFrequency().equals("")){
				aForm.setMonitorFrequency(iObj.getMonitorFrequency());
			}
			if (iObj.getPhysicalInspectionFreq() >= 0 && iObj.getIsPhysicalInspection()) {
				aForm.setPhysInspFreq(String.valueOf(iObj.getPhysicalInspectionFreq()));
			}
			else{
				aForm.setPhysInspFreq("");
			}
			if (iObj.getPhysicalInspectionFreqUnit() != null) {
				DefaultLogger.debug("OthersMapperHelper", "physical inspection freq unit length is: "
						+ iObj.getPhysicalInspectionFreqUnit().length() + "-----");
				aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit().trim());
			}
			else{
				aForm.setPhysInspFreqUOM("");
			}
			if(iObj.getLastPhysicalInspectDate()!=null){
			aForm.setDatePhyInspec(DateUtil.formatDate(locale, iObj.getLastPhysicalInspectDate()));
			}
			else{
				aForm.setDatePhyInspec("");
			}
			if(iObj.getNextPhysicalInspectDate()!=null){
			aForm.setNextPhysInspDate(DateUtil.formatDate(locale, iObj.getNextPhysicalInspectDate()));
			}
			else{
				aForm.setNextPhysInspDate("");
			}
			aForm.setGoodStatus(iObj.getGoodStatus());
			/*aForm.setAssetValue(iObj.getAssetValue());
			aForm.setScrapValue(iObj.getScrapValue());
*/

			/*
			 * Date maturityDate = iObj.getCollateralMaturityDate(); if
			 * (maturityDate != null) { Date currentDate = new Date(); int day =
			 * 0; int month = 0; int year = 0; if (maturityDate.getTime() >
			 * currentDate.getTime()) { GregorianCalendar mcal = new
			 * GregorianCalendar(); mcal.setTime(maturityDate);
			 * GregorianCalendar ccal = new GregorianCalendar();
			 * ccal.setTime(currentDate);
			 * 
			 * day = mcal.get(Calendar.DAY_OF_MONTH) -
			 * ccal.get(Calendar.DAY_OF_MONTH); if (day < 0) {
			 * mcal.add(Calendar.MONTH, -1);
			 * 
			 * day = day + mcal.getActualMaximum(Calendar.DAY_OF_MONTH); } month
			 * = mcal.get(Calendar.MONTH) - ccal.get(Calendar.MONTH); if (month
			 * < 0) { mcal.add(Calendar.YEAR, -1); month = month + 12; } year =
			 * mcal.get(Calendar.YEAR) - ccal.get(Calendar.YEAR); }
			 * aForm.setRemainTenureDay(String.valueOf(day));
			 * aForm.setRemainTenureMonth(String.valueOf(month));
			 * aForm.setRemainTenureYear(String.valueOf(year)); }
			 */

			aForm.setDescription(iObj.getDescription());
			if (iObj.getUnitsNumber()!=ICMSConstant.DOUBLE_INVALID_VALUE) aForm.setUnitsNumber(""+iObj.getUnitsNumber());
			else aForm.setUnitsNumber("");
			aForm.setDeleteItem(new String[0]);
			aForm.setDeleteInsItem(new String[0]);
			
			/*FSVBalance field is used for asset Value at the time of
			 collateral booking & reservePrice field is used for 
			 Residual scrap value  By Sachin patil*/ 
			aForm.setFSVBalance(UIUtil.mapAmountToString(locale, iObj.getFSVBalance()));
			aForm.setReservePrice(UIUtil.mapAmountToString(locale, iObj.getReservePrice()));
	
			
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.others.OthersMapperHelper", "error is :"
					+ e.toString());
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

}
