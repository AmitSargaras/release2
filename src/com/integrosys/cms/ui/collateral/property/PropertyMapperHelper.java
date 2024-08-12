/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/property/PropertyMapperHelper.java,v 1.30 2006/06/29 13:26:34 jzhan Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.property;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.OBLimitCharge;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.common.UIUtil;
/**
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.30 $
 * @since $Date: 2006/06/29 13:26:34 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PropertyMapperHelper {

	// todo add mapping the new fields once it is ready

	private static Object mapFormToOBPhysical(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		IPropertyCollateral iObj = (IPropertyCollateral) obj;
		PropertyForm aForm = (PropertyForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		Date stageDate_v1;
		
//	

		//Added by Pramod Katkar for New Filed CR on 8-08-2013
		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp_v1())) {
			iObj.setIsPhysicalInspection_v1(Boolean.valueOf(aForm.getIsPhysInsp_v1()).booleanValue());
		}

		/*if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPhysInspFreq())&& "true".equalsIgnoreCase(aForm.getIsPhysInsp())) {
			iObj.setPhysicalInspectionFreq(Integer.parseInt(aForm.getPhysInspFreq().trim()));
		}
		else {
			iObj.setPhysicalInspectionFreq(ICMSConstant.INT_INVALID_VALUE);
		}*/
		if( "true".equalsIgnoreCase(aForm.getIsPhysInsp_v1())){
			iObj.setPhysicalInspectionFreqUnit_v1(aForm.getPhysInspFreqUOM_v1());
		}else{
			iObj.setPhysicalInspectionFreqUnit_v1("");
		}
		if( "true".equalsIgnoreCase(aForm.getIsPhysInsp_v1())){
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDatePhyInspec_v1())) {
				stageDate_v1 = CollateralMapper.compareDate(locale, iObj.getLastPhysicalInspectDate_v1(), aForm
						.getDatePhyInspec_v1());
				iObj.setLastPhysicalInspectDate_v1(stageDate_v1);
				
			}
		}
		else {
			iObj.setLastPhysicalInspectDate_v1(null);
			
		}
		if( "true".equalsIgnoreCase(aForm.getIsPhysInsp_v1())){
			if ( !AbstractCommonMapper.isEmptyOrNull(aForm.getNextPhysInspDate_v1())) {
				stageDate_v1 = CollateralMapper.compareDate(locale, iObj.getNextPhysicalInspectDate_v1(), aForm
						.getNextPhysInspDate_v1());
				iObj.setNextPhysicalInspectDate_v1(stageDate_v1);
			}	
		}
		else {
			iObj.setNextPhysicalInspectDate_v1(null);
		}
		//End by Pramod Katkar
		
		//Valuation 3 start
		
		Date stageDate_v3;
		
	//	

			//Added by Pramod Katkar for New Filed CR on 8-08-2013
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp_v3())) {
				iObj.setIsPhysicalInspection_v3(Boolean.valueOf(aForm.getIsPhysInsp_v3()).booleanValue());
			}

			/*if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPhysInspFreq())&& "true".equalsIgnoreCase(aForm.getIsPhysInsp())) {
				iObj.setPhysicalInspectionFreq(Integer.parseInt(aForm.getPhysInspFreq().trim()));
			}
			else {
				iObj.setPhysicalInspectionFreq(ICMSConstant.INT_INVALID_VALUE);
			}*/
			if( "true".equalsIgnoreCase(aForm.getIsPhysInsp_v3())){
				iObj.setPhysicalInspectionFreqUnit_v3(aForm.getPhysInspFreqUOM_v3());
			}else{
				iObj.setPhysicalInspectionFreqUnit_v3("");
			}
			if( "true".equalsIgnoreCase(aForm.getIsPhysInsp_v3())){
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDatePhyInspec_v3())) {
					stageDate_v3 = CollateralMapper.compareDate(locale, iObj.getLastPhysicalInspectDate_v3(), aForm
							.getDatePhyInspec_v3());
					iObj.setLastPhysicalInspectDate_v3(stageDate_v3);
					
				}
			}
			else {
				iObj.setLastPhysicalInspectDate_v3(null);
				
			}
			if( "true".equalsIgnoreCase(aForm.getIsPhysInsp_v3())){
				if ( !AbstractCommonMapper.isEmptyOrNull(aForm.getNextPhysInspDate_v3())) {
					stageDate_v3 = CollateralMapper.compareDate(locale, iObj.getNextPhysicalInspectDate_v3(), aForm
							.getNextPhysInspDate_v3());
					iObj.setNextPhysicalInspectDate_v3(stageDate_v3);
				}	
			}
			else {
				iObj.setNextPhysicalInspectDate_v3(null);
			}
		
		//Valuation 3 Ends
			
			Date stageDate_v2;
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp_v2())) {
						iObj.setIsPhysicalInspection_v2(Boolean.valueOf(aForm.getIsPhysInsp_v2()).booleanValue());
					}
					
					if( "true".equalsIgnoreCase(aForm.getIsPhysInsp_v2())){
						iObj.setPhysicalInspectionFreqUnit_v2(aForm.getPhysInspFreqUOM_v2());
					}else{
						iObj.setPhysicalInspectionFreqUnit_v2("");
					}
					if( "true".equalsIgnoreCase(aForm.getIsPhysInsp_v2())){
						if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDatePhyInspec_v2())) {
							stageDate_v2 = CollateralMapper.compareDate(locale, iObj.getLastPhysicalInspectDate_v2(), aForm
									.getDatePhyInspec_v2());
							iObj.setLastPhysicalInspectDate_v2(stageDate_v2);
							
						}
					}
					else {
						iObj.setLastPhysicalInspectDate_v2(null);
						
					}
					if( "true".equalsIgnoreCase(aForm.getIsPhysInsp_v2())){
						if ( !AbstractCommonMapper.isEmptyOrNull(aForm.getNextPhysInspDate_v2())) {
							stageDate_v2 = CollateralMapper.compareDate(locale, iObj.getNextPhysicalInspectDate_v2(), aForm
									.getNextPhysInspDate_v2());
							iObj.setNextPhysicalInspectDate_v2(stageDate_v2);
						}	
					}
					else {
						iObj.setNextPhysicalInspectDate_v2(null);
					}
		
		return iObj;
	}
	
	private static Object mapFormToOBRental(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		IPropertyCollateral iObj = (IPropertyCollateral) obj;
		PropertyForm aForm = (PropertyForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (aForm.getQuitRentAmount().equals("") && (iObj.getQuitRentPaid() != null)
				&& (iObj.getQuitRentPaid().getAmount() > 0)) {
			iObj.setQuitRentPaid(getAmount(locale, iObj, "0"));
		}
		else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getQuitRentAmount())) {
			iObj.setQuitRentPaid(getAmount(locale, iObj, aForm.getQuitRentAmount()));
		}
		iObj.setStdQuitRent(aForm.getStdQuitRent());
		// iObj.setNonStdQuitRent(aForm.getNonStdQuitRent());
		iObj.setQuitRentReceipt(aForm.getQuitRentReceipt());
		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getNextQuitRentDate())) {
			iObj.setNextQuitRentDate(DateUtil.convertDate(locale, aForm.getNextQuitRentDate()));
		}
		else {
			iObj.setNextQuitRentDate(null);
		}
		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getQuitRentPaymentDate())) {
			iObj.setQuitRentPaymentDate(DateUtil.convertDate(locale,aForm.getQuitRentPaymentDate()));
		}
		else {
			iObj.setQuitRentPaymentDate(null);
		}
		
		iObj.setRealEstateRentalFlag(aForm.getRealEstateRentalFlag());
		
		return iObj;
	}
	public static Object mapFormToOBTsr(CommonForm cForm, HashMap inputs, Object obj) throws MapperException{
		
		IPropertyCollateral iObj = (IPropertyCollateral) obj;
		PropertyForm aForm = (PropertyForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		if(aForm.getTsrDate()!=null){
			iObj.setTsrDate(DateUtil.convertDate(aForm.getTsrDate()));
		}
		else{
			iObj.setTsrDate(null);
		}
		if(aForm.getCersiaRegistrationDate()!=null){
			iObj.setCersiaRegistrationDate(DateUtil.convertDate(aForm.getCersiaRegistrationDate()));
		}
		else{
			iObj.setCersiaRegistrationDate(null);
		}
		if(aForm.getNextTsrDate()!=null ){
			iObj.setNextTsrDate(DateUtil.convertDate(aForm.getNextTsrDate()));
		}
		else{
			iObj.setNextTsrDate(null);;
		}
		if(aForm.getTsrFrequency()!=null){
			iObj.setTsrFrequency(aForm.getTsrFrequency());
		}
		
		return iObj;
	}
	
	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {

		// System.out.println("$&$&$&$&Enterging mapFormToOB
		// PropertyMapperHelper#########1");
		IPropertyCollateral iObj = (IPropertyCollateral) obj;
		PropertyForm aForm = (PropertyForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		Date stageDate;

		iObj = (IPropertyCollateral) mapFormToOBRental(aForm, inputs, iObj);
		iObj = (IPropertyCollateral) mapFormToOBPhysical(aForm, inputs, iObj);
		iObj = (IPropertyCollateral) mapFormToOBTsr(aForm, inputs, iObj);
		
		DefaultLogger.debug("PropertyMapperHelper - mapFormToOB", "Locale is: " + locale);
		if (aForm.getEvent().equals(PropertyAction.EVENT_DELETE_ITEM)) {
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
			else if (CollateralConstant.ADD_DOC_FAC_DET_INFO.equals(aForm.getItemType())) {
				if (aForm.getDeleteInsItem() != null) {
					String[] id = aForm.getDeleteInsItem();
					IAddtionalDocumentFacilityDetails[] oldList = iObj.getAdditonalDocFacDetails();
					int numDelete = SecuritySubTypeUtil.getNumberOfDelete(id, oldList.length);
					if (numDelete != 0) {
						IAddtionalDocumentFacilityDetails[] newList = new IAddtionalDocumentFacilityDetails[oldList.length - numDelete];
						newList = (IAddtionalDocumentFacilityDetails[]) SecuritySubTypeUtil.deleteObjByList(oldList, newList, id);
						iObj.setAdditonalDocFacDetails(newList);
					}
				}
			}
		}
		try {
			if(aForm.getMonitorProcess()!=null && !aForm.getMonitorProcess().equals("")){
				iObj.setMonitorProcess(aForm.getMonitorProcess());
			}
			if(aForm.getMonitorFrequency()!=null && !aForm.getMonitorFrequency().equals("")){
				iObj.setMonitorFrequency(aForm.getMonitorFrequency());
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

			iObj.setTitleType(aForm.getTitleType());
			iObj.setTitleNumber(aForm.getTitleNo());
			iObj.setTitleNumberPrefix(aForm.getTitleNumberPrefix());

			if (aForm.getSalePurchareAmount().equals("") && (iObj.getSalePurchaseValue() != null)
					&& (iObj.getSalePurchaseValue().getAmount() > 0)) {
				iObj.setSalePurchaseValue(getAmount(locale, iObj, "0"));
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getSalePurchareAmount())) {
				iObj.setSalePurchaseValue(getAmount(locale, iObj, aForm.getSalePurchareAmount()));
			}
			
			iObj.setSalePurchaseDate(DateUtil.convertDate(locale, aForm.getSalePurchaseDate()));

			iObj.setLotNo(aForm.getLotNo());
			iObj.setLotNumberPrefix(aForm.getLotNumberPrefix());
			
			iObj.setSectionNo(aForm.getSectionNo());
			iObj.setStoreyNumber(aForm.getStoreyNumber());
			
			iObj.setTown(aForm.getState());

			iObj.setTaman(aForm.getTaman()); // Added by Grace;13/8/2007;ABank
												// CLIMS Project;
			//iObj.setLotLocation(aForm.getLocationLot());
			iObj.setPostcode(aForm.getPostCode());

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getLandArea_v1())) {
				iObj.setLandArea_v1(0);
			}
			else {
				iObj.setLandArea_v1(MapperUtil.mapStringToDouble(aForm.getLandArea_v1(), locale));
			}

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getInSqftBuiltupArea_v1())) {
				iObj.setInSqftBuiltupArea_v1(0);
			}
			else {
				iObj.setInSqftBuiltupArea_v1(MapperUtil.mapStringToDouble(aForm.getInSqftBuiltupArea_v1(), locale));
			}
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getInSqftLandArea_v1())) {
				iObj.setInSqftLandArea_v1(0);;
			}
			else {
				iObj.setInSqftLandArea_v1(MapperUtil.mapStringToDouble(aForm.getInSqftLandArea_v1(), locale));
			}
			
			iObj.setLandAreaUOM_v1(aForm.getLandUOM_v1());

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getTenureYear())) {
				iObj.setTenure(0);
			}
			else {
				iObj.setTenure(Integer.parseInt(aForm.getTenureYear()));
			}

			iObj.setTenureUnit(aForm.getTenure());
			// String tenure = aForm.getTenure();

			/*
			 * if (tenure.equals("L1")) { iObj.setTenure(33); } else if
			 * (tenure.equals("L2")) { iObj.setTenure(65); } else if
			 * (tenure.equals("L3")) { iObj.setTenure(99); } else if
			 * (tenure.equals("L4")) { iObj.setTenure(999); }
			 */

			if (aForm.getNominalValue().equals("")) {
				iObj.setNominalValue(null);
			}
			else {
				iObj.setNominalValue(getAmount(locale, iObj, aForm.getNominalValue()));
			}

			iObj.setRestrictionCondition(aForm.getRestrictCondition());

			
			
			iObj.setDescription(aForm.getDescription());
			// Start CR CMS-574 add new field remarks
			iObj.setRemarksProperty_v1(aForm.getRemarksProperty_v1());
			// End CR CMS-574 add new field remarks

			iObj.setMasterTitleNumber(aForm.getMasterTitleNo());
			iObj.setAssessment(aForm.getAssessment()); // Added by
														// Grace;13/8/2007;ABank
														// CLIMS Project;
			iObj.setAssessmentOption(aForm.getAssessmentOption());
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getRegistedHolder())) {
				iObj.setRegistedHolder(null);
			}
			else {
				iObj.setRegistedHolder(aForm.getRegistedHolder());
			}
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getPropertyAddress())) {
				iObj.setPropertyAddress(null);
			}
			else {
				iObj.setPropertyAddress(aForm.getPropertyAddress());
			}

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getPropertyAddress2())) {
				iObj.setPropertyAddress2(null);
			}
			else {
				iObj.setPropertyAddress2(aForm.getPropertyAddress2());
			}
			//Start Santosh
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getLandValue_v1())) {
				iObj.setLandValue_v1(0);
			}
			else {
				iObj.setLandValue_v1(MapperUtil.mapStringToDouble(aForm.getLandValue_v1(), locale));
			}
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getBuildingValue_v1())) {
				iObj.setBuildingValue_v1(0);
			}
			else {
				iObj.setBuildingValue_v1(MapperUtil.mapStringToDouble(aForm.getBuildingValue_v1(), locale));
			}
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getReconstructionValueOfTheBuilding_v1())) {
				iObj.setReconstructionValueOfTheBuilding_v1(0);
			}
			else {
				iObj.setReconstructionValueOfTheBuilding_v1(MapperUtil.mapStringToDouble(aForm.getReconstructionValueOfTheBuilding_v1(), locale));
			}
			//End Santosh
			if(aForm.getLegalAuditDate()!=null ){
				iObj.setLegalAuditDate(DateUtil.convertDate(aForm.getLegalAuditDate()));
			}
			else{
				iObj.setLegalAuditDate(null);;
			}
			if(aForm.getInterveingPeriSearchDate()!=null ){
				iObj.setInterveingPeriSearchDate(DateUtil.convertDate(aForm.getInterveingPeriSearchDate()));
			}
			else{
				iObj.setInterveingPeriSearchDate(null);;
			}
			
			if(aForm.getDateOfReceiptTitleDeed()!=null ){
				iObj.setDateOfReceiptTitleDeed(DateUtil.convertDate(aForm.getDateOfReceiptTitleDeed()));
			}
			else{
				iObj.setDateOfReceiptTitleDeed(null);;
			}
			iObj.setWaiver(aForm.getWaiver() != null ? aForm.getWaiver().trim() : "off");
			iObj.setDeferral(aForm.getDeferral() != null ? aForm.getDeferral().trim() : "off");
			iObj.setDeferralId(aForm.getDeferralId());
			iObj.setVal1_id(aForm.getVal1_id());
			
			iObj.setVersion1(aForm.getVersion1());
			//iObj.setValEdited_v1(aForm.getValEdited_v1());
			if(aForm.getValcreationdate_v1()!=null ){
				iObj.setValcreationdate_v1(DateUtil.convertDate(aForm.getValcreationdate_v1()));
			}
			else{
				iObj.setValcreationdate_v1(null);
			}
			
			iObj.setMortgageCreationAdd(aForm.getMortgageCreationAdd());
			//private String previousMortCreationDate;
			
			if(aForm.getPreviousMortCreationDate()!=null ){
				iObj.setPreviousMortCreationDate(DateUtil.convertDate(aForm.getPreviousMortCreationDate()));
			}
			else{
				iObj.setPreviousMortCreationDate(null);;
			}
			
			if(null != aForm.getEvent() && "viewPreviousValuation1".equals(aForm.getEvent()))
			iObj.setPreValDate_v1(aForm.getPreValDate_v1());
			
			iObj.setRevalOverride(aForm.getRevalOverride());

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getLandArea_v2())) {
				iObj.setLandArea_v2(0);
			}
			else {
				iObj.setLandArea_v2(MapperUtil.mapStringToDouble(aForm.getLandArea_v2(), locale));
			}

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getInSqftBuiltupArea_v2())) {
				iObj.setInSqftBuiltupArea_v2(0);
			}
			else {
				iObj.setInSqftBuiltupArea_v2(MapperUtil.mapStringToDouble(aForm.getInSqftBuiltupArea_v2(), locale));
			}
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getInSqftLandArea_v2())) {
				iObj.setInSqftLandArea_v2(0);
			}
			else {
				iObj.setInSqftLandArea_v2(MapperUtil.mapStringToDouble(aForm.getInSqftLandArea_v2(), locale));
			}
			
			iObj.setLandAreaUOM_v2(aForm.getLandUOM_v2());

			
			iObj.setRemarksProperty_v2(aForm.getRemarksProperty_v2());
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getLandValue_v2())) {
				iObj.setLandValue_v2(0);
			}
			else {
				iObj.setLandValue_v2(MapperUtil.mapStringToDouble(aForm.getLandValue_v2(), locale));
			}
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getBuildingValue_v2())) {
				iObj.setBuildingValue_v2(0);
			}
			else {
				iObj.setBuildingValue_v2(MapperUtil.mapStringToDouble(aForm.getBuildingValue_v2(), locale));
			}
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getReconstructionValueOfTheBuilding_v2())) {
				iObj.setReconstructionValueOfTheBuilding_v2(0);
			}
			else {
				iObj.setReconstructionValueOfTheBuilding_v2(MapperUtil.mapStringToDouble(aForm.getReconstructionValueOfTheBuilding_v2(), locale));
			}
			
				iObj.setVal2_id(aForm.getVal2_id());
				
				iObj.setVersion2(aForm.getVersion2());
			//iObj.setValEdited_v2(aForm.getValEdited_v2());
			if(aForm.getValcreationdate_v2()!=null ){
				iObj.setValcreationdate_v2(DateUtil.convertDate(aForm.getValcreationdate_v2()));
			}
			else{
				iObj.setValcreationdate_v2(null);
			}
			
			if(null != aForm.getEvent() && "viewPreviousValuation2".equals(aForm.getEvent()))
			iObj.setPreValDate_v2(aForm.getPreValDate_v2());
			
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.property.PropertyMapperHelper", "error is :"
					+ e.toString(), e);
			throw new MapperException(e.getMessage());
		}
		
		try {
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getPropertyAddress3())) {
				iObj.setPropertyAddress3(null);
			}
			else {
				iObj.setPropertyAddress3(aForm.getPropertyAddress3());
			}

			iObj.setCombinedValueIndicator(aForm.getCombinedValueIndicator());

			/*
			 * if ("false".equalsIgnoreCase(aForm.getCombinedValueIndicator())){
			 * iObj.setCombinedValueIndicator("0") ; }else{
			 * iObj.setCombinedValueIndicator("1") ; }
			 */
			// iObj.setCombinedValueIndicator("true".equalsIgnoreCase(aForm.
			// getCombinedValueIndicator()));
			try {
				iObj.setCombinedValueAmount(Double.parseDouble(aForm.getCombinedValueAmount()));
				iObj.setValueNumber(Long.parseLong(aForm.getValueNumber()));
			}
			catch (Exception e) {

			}

			iObj.setAbandonedProject(aForm.getAbandonedProject());
			// iObj.setBreachInd(aForm.getBreachInd());
			iObj.setIndependentValuerFlag(aForm.getIndependentValuerFlag());
			iObj.setMethodologyForValuationFlag(aForm.getMethodologyForValuationFlag());
			iObj.setPropertyCompletedFlag(aForm.getPropertyCompletedFlag());

			// iObj.setExtSeniorLien( aForm.getExtSeniorLien () );
			iObj.setCategoryOfLandUse_v1(aForm.getCategoryOfLandUse_v1());
			// iObj.setCaveatWaivedInd(aForm.getCaveatWaivedInd());
			// iObj.setDateLodged(aForm.getDateLodged());
			iObj.setDeveloperName_v1(aForm.getDeveloperName_v1());
			
			/*
			 * if (AbstractCommonMapper.isEmptyOrNull(aForm.getExpiryDate())) {
			 * iObj.setExpiryDate(null); } else {
			 * iObj.setExpiryDate(DateUtil.convertDate(locale, aForm
			 * .getExpiryDate())); }
			 * 
			 * if
			 * (AbstractCommonMapper.isEmptyOrNull(aForm.getPresentationDate()))
			 * { iObj.setPresentationDate(null); } else {
			 * iObj.setPresentationDate(DateUtil.convertDate(locale, aForm
			 * .getPresentationDate())); }
			 */
			iObj.setExpressedCondition(aForm.getExpressedCondition());
			iObj.setLocationDistrict(aForm.getLocationDistrict());
			iObj.setLocationMukim(aForm.getLocationMukim());
			iObj.setLocationState(aForm.getLocationState());
			iObj.setLongEstablishedMarketFlag(aForm.getLongEstablishedMarketFlag());
			iObj.setPhaseNo(aForm.getPhaseNo());
			// iObj.setPresentationNo(aForm.getPresentationNo());
			iObj.setProjectName(aForm.getProjectName());
			iObj.setPropertyCompletionStatus_v1(aForm.getPropertyCompletionStatus_v1());
			iObj.setPropertyCompletionStage(aForm.getPropertyCompletionStage());
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAssumption())) {
				if (aForm.getAssumption().equals(ICMSConstant.TRUE_VALUE)) iObj.setAssumption(true);
				else iObj.setAssumption(false);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAmountRedeem())) {
				iObj.setAmountRedeem(getAmount(locale, iObj, aForm.getAmountRedeem()));
			}
			else {
				iObj.setAmountRedeem(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getUnitPrice())) {
				iObj.setUnitPrice(getAmount(locale, iObj, aForm.getUnitPrice()));
			}
			else {
				iObj.setUnitPrice(null);
			}
			iObj.setPropertyType(aForm.getPropertyType());
			iObj.setPropertyUsage(aForm.getPropertyUsage());
			iObj.setPropertyWellDevelopedFlag(aForm.getPropertyWellDevelopedFlag());
			iObj.setRealEstateUsage(aForm.getRealEstateUsage());
			iObj.setReducedRiskWeightFlag(aForm.getReducedRiskWeightFlag());
			// iObj.setRefNo(aForm.getRefNo());
			// iObj.setSolicitorName(aForm.getSolicitorName());
			iObj.setUnitParcelNo(aForm.getUnitParcelNo());
			
			//********** Lines Added by Dattatray Thorat for Property - Commercial Security
			iObj.setMortageRegisteredRef(aForm.getMortageRegisteredRef());
			iObj.setValuatorCompany_v1(aForm.getValuatorCompany_v1());
			iObj.setValuatorName(aForm.getValuatorName());
			iObj.setAdvocateLawyerName(aForm.getAdvocateLawyerName());
			iObj.setCategoryOfLandUse_v1(aForm.getCategoryOfLandUse_v1());
			iObj.setPropertyLotLocation(aForm.getPropertyLotLocation());
			iObj.setCountry_v1(aForm.getCountry_v1());
			iObj.setRegion_v1(aForm.getRegion_v1());
			iObj.setLocationState_v1(aForm.getLocationState_v1());
			iObj.setNearestCity_v1(aForm.getNearestCity_v1());
			iObj.setOtherCity(aForm.getOtherCity());
			
			
			iObj.setValuationDate_v1(DateUtil.convertDate(locale,aForm.getValuationDate_v1()));
			if(aForm.getTypeOfMargage()!=null)
				iObj.setTypeOfMargage(aForm.getTypeOfMargage());
			else
				iObj.setTypeOfMargage("0");
			
			iObj.setMorgageCreatedBy(aForm.getMorgageCreatedBy());
			iObj.setDocumentReceived(aForm.getDocumentReceived());
			iObj.setDocumentBlock(aForm.getDocumentBlock());
			iObj.setBinNumber(aForm.getBinNumber());
			
			if (aForm.getTotalPropertyAmount_v1()==null || aForm.getTotalPropertyAmount_v1().equals("")) {
				iObj.setTotalPropertyAmount_v1(null);
			}
			else {
				Amount amt = new Amount(UIUtil.mapStringToBigDecimal( aForm.getTotalPropertyAmount_v1()),
						new CurrencyCode(iObj.getCurrencyCode()));
				iObj.setTotalPropertyAmount_v1(amt);
			}
			
			iObj.setPropertyAddress4(aForm.getPropertyAddress4());
			iObj.setPropertyAddress5(aForm.getPropertyAddress5());
			iObj.setPropertyAddress6(aForm.getPropertyAddress6());
			iObj.setPropertyId(aForm.getPropertyId());
			iObj.setPinCode_v1(aForm.getPinCode_v1());
			iObj.setClaim(aForm.getClaim());
			if(aForm.getClaim().equalsIgnoreCase("Y")&& !aForm.getClaim().trim().equals("")){
				iObj.setClaimType(aForm.getClaimType());
			}
			else{
				iObj.setClaimType("");
			}
			
			iObj.setCategoryOfLandUse_v2(aForm.getCategoryOfLandUse_v2());
			iObj.setDeveloperName_v2(aForm.getDeveloperName_v2());
			iObj.setPropertyCompletionStatus_v2(aForm.getPropertyCompletionStatus_v2());
			iObj.setValuatorCompany_v2(aForm.getValuatorCompany_v2());
			iObj.setCategoryOfLandUse_v2(aForm.getCategoryOfLandUse_v2());
			iObj.setCountry_v2(aForm.getCountry_v2());
						iObj.setRegion_v2(aForm.getRegion_v2());
						iObj.setLocationState_v2(aForm.getLocationState_v2());
						iObj.setNearestCity_v2(aForm.getNearestCity_v2());
						iObj.setValuationDate_v2(DateUtil.convertDate(locale,aForm.getValuationDate_v2()));
						
							if (aForm.getTotalPropertyAmount_v2()==null || aForm.getTotalPropertyAmount_v2().equals("")) {
							iObj.setTotalPropertyAmount_v2(null);
						}
						else {
							Amount amt = new Amount(UIUtil.mapStringToBigDecimal( aForm.getTotalPropertyAmount_v2()),
									new CurrencyCode(iObj.getCurrencyCode()));
							iObj.setTotalPropertyAmount_v2(amt);
						}
						iObj.setPinCode_v2(aForm.getPinCode_v2());
			
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.proptery.ProptertyMapperHelper", "error is :"
					+ e.toString(), e);
			throw new MapperException(e.getMessage());
		}
		
		try {
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getBuiltupArea_v1())) {
				iObj.setBuiltupArea_v1(0);
			}
			else {
				iObj.setBuiltupArea_v1(MapperUtil.mapStringToDouble(aForm.getBuiltupArea_v1(), locale));
			}
			iObj.setBuiltupAreaUOM_v1(aForm.getBuiltUpAreaUnit_v1());
			iObj.setScheduledLocation(aForm.getScheduledLocation());
			// WLS Aug 9, 2007: set developer group company form field to bean
			iObj.setDevGrpCo(aForm.getDevGrpCo());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getComputedMFScore())) {
				iObj.setComputedMFScore(Double.valueOf(aForm.getComputedMFScore()));
			}
			else {
				iObj.setComputedMFScore(null);
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getUserInput())) {
				iObj.setUserInput(Double.valueOf(aForm.getUserInput()));
			}
			else {
				iObj.setUserInput(null);
			}
			
			/* Commented as giving String Tokenizer Error -Null PointerException
				iObj.setChattelSoldDate(DateUtil.convertDate(locale, aForm.getChattelSoldDate()));*/
			iObj.setMasterTitle(aForm.getMasterTitle());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAssessmentRate())) {
				iObj.setAssessmentRate(getAmount(locale, iObj, aForm.getAssessmentRate()));
			}
			else {
				iObj.setAssessmentRate(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAssessmentPeriod())) {
				iObj.setAssessmentPeriod(Integer.parseInt(aForm.getAssessmentPeriod()));
			}
			else {
				iObj.setAssessmentPeriod(0);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAssessmentPaymentDate())) {
				iObj.setAssessmentPaymentDate(DateUtil.convertDate(locale,aForm.getAssessmentPaymentDate()));
			}
			else {
				iObj.setAssessmentPaymentDate(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAuctionPrice())) {
				iObj.setAuctionPrice(getAmount(locale, iObj, aForm.getAuctionPrice()));
			}
			else {
				iObj.setAuctionPrice(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAuctionDate())) {
				iObj.setAuctionDate(DateUtil.convertDate(locale,aForm.getAuctionDate()));
			}
			else {
				iObj.setAuctionDate(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPriCaveatGuaranteeDate())) {
				iObj.setPriCaveatGuaranteeDate(DateUtil.convertDate(locale,aForm.getPriCaveatGuaranteeDate()));
			}
			else {
				iObj.setPriCaveatGuaranteeDate(null);
			}
            iObj.setAuctioneer(aForm.getAuctioneer());
			iObj.setNonPreferredLocation(aForm.getNonPreferredLocation());
			iObj.setCommissionType(aForm.getCommissionType());
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getConstitution())) {
				iObj.setConstitution(aForm.getConstitution());
			}
			else {
				iObj.setConstitution(null);
			}
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getBuiltupArea_v2())) {
				iObj.setBuiltupArea_v2(0);
			}
			else {
				iObj.setBuiltupArea_v2(MapperUtil.mapStringToDouble(aForm.getBuiltupArea_v2(), locale));
			}
			iObj.setBuiltupAreaUOM_v2(aForm.getBuiltUpAreaUnit_v2());
			
			//Valuation 3 start
			
			iObj.setValuationDate_v3(DateUtil.convertDate(locale,aForm.getValuationDate_v3()));
			
			iObj.setPropertyCompletionStatus_v3(aForm.getPropertyCompletionStatus_v3());
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getLandArea_v3())) {
				iObj.setLandArea_v3(0);
			}
			else {
				iObj.setLandArea_v3(MapperUtil.mapStringToDouble(aForm.getLandArea_v3(), locale));
			}

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getInSqftBuiltupArea_v3())) {
				iObj.setInSqftBuiltupArea_v3(0);
			}
			else {
				iObj.setInSqftBuiltupArea_v3(MapperUtil.mapStringToDouble(aForm.getInSqftBuiltupArea_v3(), locale));
			}
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getInSqftLandArea_v3())) {
				iObj.setInSqftLandArea_v3(0);;
			}
			else {
				iObj.setInSqftLandArea_v3(MapperUtil.mapStringToDouble(aForm.getInSqftLandArea_v3(), locale));
			}
			
			iObj.setLandAreaUOM_v3(aForm.getLandUOM_v3());
			iObj.setRemarksProperty_v3(aForm.getRemarksProperty_v3());
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getLandValue_v3())) {
				iObj.setLandValue_v3(0);
			}
			else {
				iObj.setLandValue_v3(MapperUtil.mapStringToDouble(aForm.getLandValue_v3(), locale));
			}
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getBuildingValue_v3())) {
				iObj.setBuildingValue_v3(0);
			}
			else {
				iObj.setBuildingValue_v3(MapperUtil.mapStringToDouble(aForm.getBuildingValue_v3(), locale));
			}
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getReconstructionValueOfTheBuilding_v3())) {
				iObj.setReconstructionValueOfTheBuilding_v3(0);
			}
			else {
				iObj.setReconstructionValueOfTheBuilding_v3(MapperUtil.mapStringToDouble(aForm.getReconstructionValueOfTheBuilding_v3(), locale));
			}
			iObj.setVal3_id(aForm.getVal3_id());
			
			iObj.setVersion3(aForm.getVersion3());
			
			if(aForm.getValcreationdate_v3()!=null ){
				iObj.setValcreationdate_v3(DateUtil.convertDate(aForm.getValcreationdate_v3()));
			}
			else{
				iObj.setValcreationdate_v3(null);
			}
			iObj.setCategoryOfLandUse_v3(aForm.getCategoryOfLandUse_v3());
			
			iObj.setDeveloperName_v3(aForm.getDeveloperName_v3());
			
			iObj.setValuatorCompany_v3(aForm.getValuatorCompany_v3());
			iObj.setCategoryOfLandUse_v3(aForm.getCategoryOfLandUse_v3());
			iObj.setCountry_v3(aForm.getCountry_v3());
			iObj.setRegion_v3(aForm.getRegion_v3());
			iObj.setLocationState_v3(aForm.getLocationState_v3());
			iObj.setNearestCity_v3(aForm.getNearestCity_v3());
			if (aForm.getTotalPropertyAmount_v3()==null || aForm.getTotalPropertyAmount_v3().equals("")) {
				iObj.setTotalPropertyAmount_v3(null);
			}
			else {
				Amount amt = new Amount(UIUtil.mapStringToBigDecimal( aForm.getTotalPropertyAmount_v3()),
						new CurrencyCode(iObj.getCurrencyCode()));
				iObj.setTotalPropertyAmount_v3(amt);
			}
			iObj.setPinCode_v3(aForm.getPinCode_v3());
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getBuiltupArea_v3())) {
				iObj.setBuiltupArea_v3(0);
			}
			else {
				iObj.setBuiltupArea_v3(MapperUtil.mapStringToDouble(aForm.getBuiltupArea_v3(), locale));
			}
			iObj.setBuiltupAreaUOM_v3(aForm.getBuiltUpAreaUnit_v3());
			
			if(null != aForm.getEvent() && "viewPreviousValuation3".equals(aForm.getEvent()))
				iObj.setPreValDate_v3(aForm.getPreValDate_v3());
			
			
			//Valuation 3 end
			
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.proptery.ProptertyMapperHelper", "error is :"
					+ e.toString(), e);
			throw new MapperException(e.getMessage());
		}

		return iObj;
	}

	private static CommonForm mapOBToFormRental(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		IPropertyCollateral iObj = (IPropertyCollateral) obj;
		PropertyForm aForm = (PropertyForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		try {
			aForm.setNextQuitRentDate(DateUtil.formatDate(locale, iObj.getNextQuitRentDate()));
			aForm.setQuitRentPaymentDate(DateUtil.formatDate(locale, iObj.getQuitRentPaymentDate()));
			aForm.setStdQuitRent(iObj.getStdQuitRent());
			if ((iObj.getQuitRentPaid() != null) && (iObj.getQuitRentPaid().getCurrencyCode() != null)) {
				if (iObj.getQuitRentPaid().getAmount() > 0) {
					aForm.setQuitRentAmount(CurrencyManager.convertToString(locale, iObj.getQuitRentPaid()));
				}
			}
			aForm.setRealEstateRentalFlag(iObj.getRealEstateRentalFlag());
			aForm.setQuitRentReceipt(iObj.getQuitRentReceipt());
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.proptery.ProptertyMapperHelper", "error is :"
					+ e.toString());
			throw new MapperException(e.getMessage());
		}

		return aForm;
	}

	private static CommonForm mapOBToFormPhysical(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		IPropertyCollateral iObj = (IPropertyCollateral) obj;
		PropertyForm aForm = (PropertyForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		aForm.setIsPhysInsp_v1(String.valueOf(iObj.getIsPhysicalInspection_v1()));
		
		if (iObj.getPhysicalInspectionFreq() > 0) {
			aForm.setPhysInspFreq(String.valueOf(iObj.getPhysicalInspectionFreq()));
		}else{
			aForm.setPhysInspFreq("");
		}
		if (iObj.getPhysicalInspectionFreqUnit_v1() != null) {
			DefaultLogger.debug("PropertyMapperHelper", "physical inspection freq unit length is: "
					+ iObj.getPhysicalInspectionFreqUnit_v1().length() + "-----");
			aForm.setPhysInspFreqUOM_v1(iObj.getPhysicalInspectionFreqUnit_v1().trim());
		}
		aForm.setDatePhyInspec_v1(DateUtil.formatDate(locale, iObj.getLastPhysicalInspectDate_v1()));
		aForm.setNextPhysInspDate_v1(DateUtil.formatDate(locale, iObj.getNextPhysicalInspectDate_v1()));
		
		//Valuation 3 start
		
		aForm.setIsPhysInsp_v3(String.valueOf(iObj.getIsPhysicalInspection_v3()));
		
//		if (iObj.getPhysicalInspectionFreq() > 0) {
//			aForm.setPhysInspFreq(String.valueOf(iObj.getPhysicalInspectionFreq()));
//		}else{
//			aForm.setPhysInspFreq("");
//		}
		if (iObj.getPhysicalInspectionFreqUnit_v3() != null) {
			DefaultLogger.debug("PropertyMapperHelper", "physical inspection freq unit length is: "
					+ iObj.getPhysicalInspectionFreqUnit_v3().length() + "-----");
			aForm.setPhysInspFreqUOM_v3(iObj.getPhysicalInspectionFreqUnit_v3().trim());
		}
		aForm.setDatePhyInspec_v3(DateUtil.formatDate(locale, iObj.getLastPhysicalInspectDate_v3()));
		aForm.setNextPhysInspDate_v3(DateUtil.formatDate(locale, iObj.getNextPhysicalInspectDate_v3()));
		
		//Valuation 3 Ends

		
		aForm.setIsPhysInsp_v2(String.valueOf(iObj.getIsPhysicalInspection_v2()));
		if (iObj.getPhysicalInspectionFreqUnit_v2() != null) {
			DefaultLogger.debug("PropertyMapperHelper", "physical inspection freq unit 2 length is: "
					+ iObj.getPhysicalInspectionFreqUnit_v2().length() + "-----");
			aForm.setPhysInspFreqUOM_v2(iObj.getPhysicalInspectionFreqUnit_v2().trim());
		}
		aForm.setDatePhyInspec_v2(DateUtil.formatDate(locale, iObj.getLastPhysicalInspectDate_v2()));
		aForm.setNextPhysInspDate_v2(DateUtil.formatDate(locale, iObj.getNextPhysicalInspectDate_v2()));

		return aForm;
	}
	private static CommonForm mapOBToFormTsr(CommonForm cForm, Object obj, HashMap inputs) throws MapperException{
		PropertyForm aForm = (PropertyForm) cForm;
		IPropertyCollateral iObj = (IPropertyCollateral) obj;
		
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		aForm.setTsrFrequency(iObj.getTsrFrequency());
		
		if(iObj.getTsrDate()!=null){
			aForm.setTsrDate(df.format(iObj.getTsrDate()));
		}
		else{
				aForm.setTsrDate("");
		}
		if(iObj.getCersiaRegistrationDate()!=null)
		{
			aForm.setCersiaRegistrationDate(df.format(iObj.getCersiaRegistrationDate()));
		}
		else
		{
				aForm.setCersiaRegistrationDate("");
		}
		if(iObj.getNextTsrDate()!=null)
		{
			aForm.setNextTsrDate(df.format(iObj.getNextTsrDate()));
		}
		else
		{
			aForm.setNextTsrDate("");
		}
		
		return aForm;
	}
	
	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		// System.out.println("$&$&$&$&Enterging mapOBToForm
		// PropertyMapperHelper#########2");
		IPropertyCollateral iObj = (IPropertyCollateral) obj;
		PropertyForm aForm = (PropertyForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("PropertyMapperHelper - mapOBToForm", "Locale is: " + locale);

		aForm = (PropertyForm) mapOBToFormRental(aForm, iObj, inputs);
		aForm = (PropertyForm) mapOBToFormPhysical(aForm, iObj, inputs);
		aForm = (PropertyForm) mapOBToFormTsr(aForm, iObj, inputs);
		
		try {
			if(iObj.getMonitorProcess()!=null && !iObj.getMonitorProcess().equals("")){
				aForm.setMonitorProcess(iObj.getMonitorProcess());
			}
			if(iObj.getMonitorFrequency()!=null && !iObj.getMonitorFrequency().equals("")){
				aForm.setMonitorFrequency(iObj.getMonitorFrequency());
			}
			
			if (iObj.getEnvRiskyStatus() != null) {
				aForm.setSecEnvRisky(iObj.getEnvRiskyStatus().trim());
			}
			if ((iObj.getNominalValue() != null) && (iObj.getNominalValue().getCurrencyCode() != null)) {
				aForm.setNominalValue(CurrencyManager.convertToString(locale, iObj.getNominalValue()));
			}
			aForm.setRemarkEnvRisk(iObj.getEnvRiskyRemarks());
			aForm.setDateSecurityEnv(DateUtil.formatDate(locale, iObj.getEnvRiskyDate()));
			aForm.setTitleType(iObj.getTitleType());
			aForm.setTitleNo(iObj.getTitleNumber());
			aForm.setTitleNumberPrefix(iObj.getTitleNumberPrefix());
			
			aForm.setMasterTitle(iObj.getMasterTitle());
			
			if ((iObj.getSalePurchaseValue() != null) && (iObj.getSalePurchaseValue().getCurrencyCode() != null)) {
				if (iObj.getSalePurchaseValue().getAmount() > 0) {
					aForm.setSalePurchareAmount(CurrencyManager.convertToString(locale, iObj.getSalePurchaseValue()));
				}
			}
			
			aForm.setSalePurchaseDate(DateUtil.formatDate(locale, iObj.getSalePurchaseDate()));
			aForm.setSalePurchaseDate_(DateUtil.formatDate(locale, iObj.getSalePurchaseDate()));
			aForm.setLotNo(iObj.getLotNo());
			aForm.setLotNumberPrefix(iObj.getLotNumberPrefix());
			
			aForm.setSectionNo(iObj.getSectionNo());
			aForm.setStoreyNumber(iObj.getStoreyNumber());
			
			// aForm.setState(iObj.getTown());
			aForm.setTaman(iObj.getTaman()); // Added by Grace;13/8/2007;ABank
												// CLIMS Project;
			//aForm.setLocationLot(iObj.getLotLocation());
			aForm.setPostCode(iObj.getPostcode());
				if (iObj.getLandArea_v1() > 0) {
					aForm.setLandArea_v1(MapperUtil.mapDoubleToString(iObj.getLandArea_v1(), 8, locale));
				}
				aForm.setLandUOM_v1(iObj.getLandAreaUOM_v1());

			if (iObj.getTenureUnit() != null) {
				aForm.setTenure(iObj.getTenureUnit().trim());
			}
			else {
				aForm.setTenure("");
			}

			if (iObj.getTenure() == 0) {
				aForm.setTenureYear("");

			}
			else {
				aForm.setTenureYear(String.valueOf(iObj.getTenure()));
			}

			aForm.setRestrictCondition(iObj.getRestrictionCondition());
			if ("FR".equalsIgnoreCase((aForm.getTenure()))) {
				aForm.setRemainTenureYear("");
			}
			else {
				Date maturityDate = iObj.getCollateralMaturityDate();
				if (maturityDate != null) {
					GregorianCalendar mcal = new GregorianCalendar();
					mcal.setTime(maturityDate);
					int year = mcal.get(Calendar.YEAR);
					mcal.setTime(new Date());
					int currY = mcal.get(Calendar.YEAR);
					aForm.setRemainTenureYear(String.valueOf(Math.abs(year - currY)));
				}
				else {
					aForm.setRemainTenureYear("");
				}
			}

			aForm.setDescription(iObj.getDescription());
			// Start CR CMS-574 add new field remarks
			aForm.setRemarksProperty_v1(iObj.getRemarksProperty_v1());
			// End CR CMS-574 add new field remarks

			// aForm.setNonStdQuitRent(iObj.getNonStdQuitRent());
			aForm.setMasterTitleNo(iObj.getMasterTitleNumber());
			aForm.setAssessment(iObj.getAssessment()); // Added by
														// Grace;13/8/2007;ABank
														// CLIMS Project;
			aForm.setAssessmentOption(iObj.getAssessmentOption());
			
			aForm.setRegistedHolder(iObj.getRegistedHolder());
			aForm.setPropertyAddress(iObj.getPropertyAddress());
			aForm.setPropertyAddress2(iObj.getPropertyAddress2());
			aForm.setPropertyAddress3(iObj.getPropertyAddress3());

			aForm.setDeleteItem(new String[0]);
			aForm.setDeleteInsItem(new String[0]);

			aForm.setCombinedValueAmount(Double.toString(iObj.getCombinedValueAmount()));

			if ((iObj.getCombinedValueIndicator() != null) && "1".equals(iObj.getCombinedValueIndicator().trim())) {
				aForm.setCombinedValueIndicator("1");
			}
			else {
				aForm.setCombinedValueIndicator("0");
			}
			// aForm.setCombinedValueIndicator(String.valueOf(iObj
			// .getCombinedValueIndicator()));

			aForm.setValueNumber(Long.toString(iObj.getValueNumber()));

			aForm.setAbandonedProject(iObj.getAbandonedProject());
			// aForm.setBreachInd(iObj.getBreachInd());
			aForm.setCategoryOfLandUse_v1(iObj.getCategoryOfLandUse_v1());
			// aForm.setCaveatWaivedInd(iObj.getCaveatWaivedInd());
			// aForm.setDateLodged(iObj.getDateLodged());
			aForm.setDeveloperName_v1(iObj.getDeveloperName_v1());
			/*
			 * aForm.setExpiryDate(DateUtil.formatDate(locale, iObj
			 * .getExpiryDate()));
			 */
			
			aForm.setDeveloperNameIDX((iObj.getDeveloperName_v1()!= null && iObj.getDeveloperName_v1().length() > 0) ? iObj.getDeveloperName_v1().substring(0,1) : "");
			
			//Start Santosh
			if (iObj.getLandValue_v1() > 0) {
				aForm.setLandValue_v1(MapperUtil.mapDoubleToString(iObj.getLandValue_v1(), 2, locale));
			}
			else {
				aForm.setLandValue_v1(MapperUtil.mapDoubleToString(0, 2, locale));
			}
			
			if (iObj.getBuildingValue_v1() > 0) {
				aForm.setBuildingValue_v1(MapperUtil.mapDoubleToString(iObj.getBuildingValue_v1(), 2, locale));
			}
			else {
				aForm.setBuildingValue_v1(MapperUtil.mapDoubleToString(0, 2, locale));
			}

			if (iObj.getReconstructionValueOfTheBuilding_v1() > 0) {
				aForm.setReconstructionValueOfTheBuilding_v1(MapperUtil.mapDoubleToString(iObj.getReconstructionValueOfTheBuilding_v1(), 2, locale));
			}
			else {
				aForm.setReconstructionValueOfTheBuilding_v1(MapperUtil.mapDoubleToString(0, 2, locale));
			}
			//End Santosh
			
			
	        if (iObj.getLandArea_v2() > 0) {
				aForm.setLandArea_v2(MapperUtil.mapDoubleToString(iObj.getLandArea_v2(), 8, locale));
			}
			aForm.setLandUOM_v2(iObj.getLandAreaUOM_v2());
	        aForm.setRemarksProperty_v2(iObj.getRemarksProperty_v2());
		    aForm.setCategoryOfLandUse_v2(iObj.getCategoryOfLandUse_v2());
			
			aForm.setDeveloperName_v2(iObj.getDeveloperName_v2());
          //  aForm.setDeveloperNameIDX((iObj.getDeveloperName_v2()!= null && iObj.getDeveloperName_v2().length() > 0) ? iObj.getDeveloperName_v2().substring(0,2) : "");
			
			
			if (iObj.getLandValue_v2() > 0) {
				aForm.setLandValue_v2(MapperUtil.mapDoubleToString(iObj.getLandValue_v2(), 2, locale));
			}
			else {
				aForm.setLandValue_v2(MapperUtil.mapDoubleToString(0, 2, locale));
			}
			
			if (iObj.getBuildingValue_v2() > 0) {
				aForm.setBuildingValue_v2(MapperUtil.mapDoubleToString(iObj.getBuildingValue_v2(), 2, locale));
			}
			else {
				aForm.setBuildingValue_v2(MapperUtil.mapDoubleToString(0, 2, locale));
			}

			if (iObj.getReconstructionValueOfTheBuilding_v2() > 0) {
				aForm.setReconstructionValueOfTheBuilding_v2(MapperUtil.mapDoubleToString(iObj.getReconstructionValueOfTheBuilding_v2(), 2, locale));
			}
			else {
				aForm.setReconstructionValueOfTheBuilding_v2(MapperUtil.mapDoubleToString(0, 2, locale));
			}
			
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.proptery.ProptertyMapperHelper", "error is :"
					+ e.toString());
			throw new MapperException(e.getMessage());
		}	
		try {
			aForm.setExpressedCondition(iObj.getExpressedCondition());
			aForm.setIndependentValuerFlag(iObj.getIndependentValuerFlag());
			aForm.setLocationDistrict(iObj.getLocationDistrict());
			aForm.setLocationMukim(iObj.getLocationMukim());
			aForm.setLocationState(iObj.getLocationState());
			aForm.setLongEstablishedMarketFlag(iObj.getLongEstablishedMarketFlag());
			aForm.setMethodologyForValuationFlag(iObj.getMethodologyForValuationFlag());
			aForm.setPhaseNo(iObj.getPhaseNo());
			/*
			 * aForm.setPresentationDate(DateUtil.formatDate(locale, iObj
			 * .getPresentationDate()));
			 */
			// aForm.setPresentationNo(iObj.getPresentationNo());
			aForm.setProjectName(iObj.getProjectName());
			aForm.setPropertyCompletedFlag(iObj.getPropertyCompletedFlag());
			aForm.setPropertyCompletionStatus_v1(iObj.getPropertyCompletionStatus_v1());
			aForm.setPropertyCompletionStage(iObj.getPropertyCompletionStage());
			if (iObj.getAssumption()) aForm.setAssumption(ICMSConstant.TRUE_VALUE);
			else aForm.setAssumption(ICMSConstant.FALSE_VALUE);
			if (iObj.getAmountRedeem()!=null && iObj.getAmountRedeem().getCurrencyCode()!=null)
				aForm.setAmountRedeem(CurrencyManager.convertToString(locale, iObj.getAmountRedeem()));
			if (iObj.getUnitPrice()!=null && iObj.getUnitPrice().getCurrencyCode()!=null)
				aForm.setUnitPrice(CurrencyManager.convertToString(locale, iObj.getUnitPrice()));
			aForm.setPropertyType(iObj.getPropertyType());
			aForm.setPropertyUsage(iObj.getPropertyUsage());
			aForm.setPropertyWellDevelopedFlag(iObj.getPropertyWellDevelopedFlag());

			aForm.setRealEstateUsage(iObj.getRealEstateUsage());
			aForm.setReducedRiskWeightFlag(iObj.getReducedRiskWeightFlag());
			// aForm.setRefNo(iObj.getRefNo());
			// aForm.setSolicitorName(iObj.getSolicitorName());
			aForm.setUnitParcelNo(iObj.getUnitParcelNo());
			aForm.setScheduledLocation(iObj.getScheduledLocation());
			if (iObj.getBuiltupArea_v1() > 0) {
				aForm.setBuiltupArea_v1(MapperUtil.mapDoubleToString(iObj.getBuiltupArea_v1(), 6, locale));
			}
			if (iObj.getInSqftBuiltupArea_v1() > 0) {
				aForm.setInSqftBuiltupArea_v1(MapperUtil.mapDoubleToString(iObj.getInSqftBuiltupArea_v1(), 2, locale));
			}
			if (iObj.getInSqftLandArea_v1() > 0) {
				aForm.setInSqftLandArea_v1(MapperUtil.mapDoubleToString(iObj.getInSqftLandArea_v1(), 2, locale));
			}
			aForm.setBuiltUpAreaUnit_v1(iObj.getBuiltupAreaUOM_v1());

			// WLS Aug 9, 2007: set developer group company bean field to form
			aForm.setDevGrpCo(iObj.getDevGrpCo());

			if (iObj.getComputedMFScore() != null) {
				aForm.setComputedMFScore(String.valueOf(iObj.getComputedMFScore()));
			}
			else {
				aForm.setComputedMFScore("");
			}

			if (iObj.getUserInput() != null) {
				aForm.setUserInput(String.valueOf(iObj.getUserInput()));
			}
			else {
				aForm.setUserInput("");
			}
			aForm.setChattelSoldDate(DateUtil.formatDate(locale, iObj.getChattelSoldDate()));
			if (iObj.getAssessmentRate()!=null && iObj.getAssessmentRate().getCurrencyCode()!=null)
				aForm.setAssessmentRate(CurrencyManager.convertToString(locale, iObj.getAssessmentRate()));
			aForm.setAssessmentPeriod(Integer.toString(iObj.getAssessmentPeriod()));
			aForm.setAssessmentPaymentDate(DateUtil.formatDate(locale, iObj.getAssessmentPaymentDate()));			
			if (iObj.getAuctionPrice()!=null && iObj.getAuctionPrice().getCurrencyCode()!=null) 
				aForm.setAuctionPrice(CurrencyManager.convertToString(locale, iObj.getAuctionPrice()));
			aForm.setAuctionDate(DateUtil.formatDate(locale, iObj.getAuctionDate()));
			aForm.setAuctioneer(iObj.getAuctioneer());
            aForm.setPriCaveatGuaranteeDate(DateUtil.formatDate(locale,iObj.getPriCaveatGuaranteeDate()));
			aForm.setNonPreferredLocation(iObj.getNonPreferredLocation());
			aForm.setCommissionType(iObj.getCommissionType());
			
			//********** Lines Added by Dattatray Thorat for Property - Commercial Security
			aForm.setMortageRegisteredRef(iObj.getMortageRegisteredRef());
			aForm.setValuatorCompany_v1(iObj.getValuatorCompany_v1());
			aForm.setValuatorName(iObj.getValuatorName());
			aForm.setAdvocateLawyerName(iObj.getAdvocateLawyerName());
			aForm.setCategoryOfLandUse_v1(iObj.getCategoryOfLandUse_v1());
			aForm.setPropertyLotLocation(iObj.getPropertyLotLocation());
			aForm.setCountry_v1(iObj.getCountry_v1());
			aForm.setRegion_v1(iObj.getRegion_v1());
			aForm.setLocationState_v1(iObj.getLocationState_v1());
			aForm.setNearestCity_v1(iObj.getNearestCity_v1());
			aForm.setOtherCity(iObj.getOtherCity());
			
			Amount amt_v1 = null;
			//santosh
			/*Amount value =null;
			double d = iObj.getLandValue()+iObj.getBuildingValue()+iObj.getReconstructionValueOfTheBuilding();
			value.setAmount(iObj.getLandValue()+iObj.getBuildingValue()+iObj.getReconstructionValueOfTheBuilding());
			amt=iObj.getTotalPropertyAmount().add(value);*/
			amt_v1=iObj.getTotalPropertyAmount_v1();
			aForm.setValuationDate_v1(DateUtil.formatDate(locale,iObj.getValuationDate_v1()));
			aForm.setTypeOfMargage(iObj.getTypeOfMargage());
			aForm.setMorgageCreatedBy(iObj.getMorgageCreatedBy());
			aForm.setDocumentReceived(iObj.getDocumentReceived());
			aForm.setDocumentBlock(iObj.getDocumentBlock());
			aForm.setBinNumber(iObj.getBinNumber());
			if ((amt_v1 != null) && (amt_v1.getCurrencyCode() != null)) {
				try {
					//Phase 3 CR:comma separated
					aForm.setTotalPropertyAmount_v1(UIUtil.formatWithCommaAndDecimal(amt_v1.getAmountAsBigDecimal().toString()));
					}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}	
			else if((amt_v1 != null) &&(amt_v1.getCurrencyCode() == null))
			{
				//Phase 3 CR:comma separated
				aForm.setTotalPropertyAmount_v1(UIUtil.formatWithCommaAndDecimal(amt_v1.getAmountAsBigDecimal().toString()));
			}
			aForm.setPropertyAddress4(iObj.getPropertyAddress4());
			aForm.setPropertyAddress5(iObj.getPropertyAddress5());
			aForm.setPropertyAddress6(iObj.getPropertyAddress6());
			aForm.setPropertyId(iObj.getPropertyId());
			aForm.setPinCode_v1(iObj.getPinCode_v1());
			if(iObj.getClaim()!=null){
			aForm.setClaim(iObj.getClaim());
			}
			else{
				aForm.setClaim("No");
			}
		if(iObj.getClaimType()!=null){
			if(iObj.getClaim()!=null) {
			if(iObj.getClaim().equalsIgnoreCase("Y")&& !iObj.getClaim().trim().equalsIgnoreCase("")
					&& iObj.getClaim()!=null){
				aForm.setClaimType(iObj.getClaimType());
			}
			else {
				aForm.setClaimType("");
			}
			}else {
				aForm.setClaimType("");
			}
		}
		else {
			aForm.setClaimType("");
		}
		if(iObj.getIsPhysicalInspection_v1()){
			aForm.setIsPhysInsp_v1("true");
		}else if(!iObj.getIsPhysicalInspection_v1()){
			aForm.setIsPhysInsp_v1("false");
		}else{
			aForm.setIsPhysInsp_v1("");
		}
		
		aForm.setConstitution(iObj.getConstitution());	
		


		if(iObj.getLegalAuditDate()!=null ){
					aForm.setLegalAuditDate(DateUtil.formatDate(locale,iObj.getLegalAuditDate()));
				}
				else{
					aForm.setLegalAuditDate(null);;
				}
				if(iObj.getInterveingPeriSearchDate()!=null ){
					aForm.setInterveingPeriSearchDate(DateUtil.formatDate(locale,iObj.getInterveingPeriSearchDate()));
				}
				else{
					aForm.setInterveingPeriSearchDate(null);;
				}
				
				if(iObj.getDateOfReceiptTitleDeed()!=null ){
					aForm.setDateOfReceiptTitleDeed(DateUtil.formatDate(locale,iObj.getDateOfReceiptTitleDeed()));
				}
				else{
					aForm.setDateOfReceiptTitleDeed(null);;
				}
				aForm.setWaiver(iObj.getWaiver() != null ? iObj.getWaiver().trim() : "off");
				aForm.setDeferral(iObj.getDeferral() != null ? iObj.getDeferral().trim() : "off");
				aForm.setDeferralId(iObj.getDeferralId());
				String deferralIds = (String) inputs.get("deferralIds");
				aForm.setDeferralIds(deferralIds);
				
				String valuation2Mandatory = (String) inputs.get("valuation2Mandatory");
				aForm.setValuation2Mandatory(valuation2Mandatory);
				
				aForm.setVal1_id(iObj.getVal1_id());
				
				aForm.setVersion1(iObj.getVersion1());
				//aForm.setValEdited_v1(iObj.getValEdited_v1());
				if(iObj.getValcreationdate_v1()!=null ){
					aForm.setValcreationdate_v1(DateUtil.formatDate(locale,iObj.getValcreationdate_v1()));
				}
				else{
					aForm.setValcreationdate_v1(null);
				}
				
					aForm.setMortgageCreationAdd(iObj.getMortgageCreationAdd());
					aForm.setPreValDate_v1(iObj.getPreValDate_v1());
	
					if(null!=aForm.getEvent() && "returnPreviousValuation1".equals(aForm.getEvent()))
					aForm.setPreValDate_v1("");
					
					aForm.setRevalOverride(iObj.getRevalOverride());

					/*//private String previousMortCreationDate;
					if(iObj.getPreviousMortCreationDate()!=null ){
						aForm.setPreviousMortCreationDate(DateUtil.formatDate(locale,iObj.getPreviousMortCreationDate()));
					}
					else{
						aForm.setPreviousMortCreationDate(null);
					}*/
					
					//Valuation 3 start
					
					if (iObj.getLandArea_v3() > 0) {
						aForm.setLandArea_v3(MapperUtil.mapDoubleToString(iObj.getLandArea_v3(), 8, locale));
					}
					aForm.setLandUOM_v3(iObj.getLandAreaUOM_v3());
					aForm.setRemarksProperty_v3(iObj.getRemarksProperty_v3());
					aForm.setCategoryOfLandUse_v3(iObj.getCategoryOfLandUse_v3());
					aForm.setDeveloperName_v3(iObj.getDeveloperName_v3());
					//aForm.setDeveloperNameIDX((iObj.getDeveloperName_v3()!= null && iObj.getDeveloperName_v3().length() > 0) ? iObj.getDeveloperName_v3().substring(0,1) : "");
					
					
					if (iObj.getLandValue_v3() > 0) {
						aForm.setLandValue_v3(MapperUtil.mapDoubleToString(iObj.getLandValue_v3(), 2, locale));
					}
					else {
						aForm.setLandValue_v3(MapperUtil.mapDoubleToString(0, 2, locale));
					}
					
					if (iObj.getBuildingValue_v3() > 0) {
						aForm.setBuildingValue_v3(MapperUtil.mapDoubleToString(iObj.getBuildingValue_v3(), 2, locale));
					}
					else {
						aForm.setBuildingValue_v3(MapperUtil.mapDoubleToString(0, 2, locale));
					}

					if (iObj.getReconstructionValueOfTheBuilding_v3() > 0) {
						aForm.setReconstructionValueOfTheBuilding_v3(MapperUtil.mapDoubleToString(iObj.getReconstructionValueOfTheBuilding_v3(), 2, locale));
					}
					else {
						aForm.setReconstructionValueOfTheBuilding_v3(MapperUtil.mapDoubleToString(0, 2, locale));
					}
					
					aForm.setPropertyCompletionStatus_v3(iObj.getPropertyCompletionStatus_v3());
					if (iObj.getBuiltupArea_v3() > 0) {
						aForm.setBuiltupArea_v3(MapperUtil.mapDoubleToString(iObj.getBuiltupArea_v3(), 6, locale));
					}
					if (iObj.getInSqftBuiltupArea_v3() > 0) {
						aForm.setInSqftBuiltupArea_v3(MapperUtil.mapDoubleToString(iObj.getInSqftBuiltupArea_v3(), 2, locale));
					}
					if (iObj.getInSqftLandArea_v3() > 0) {
						aForm.setInSqftLandArea_v3(MapperUtil.mapDoubleToString(iObj.getInSqftLandArea_v3(), 2, locale));
					}
					aForm.setBuiltUpAreaUnit_v3(iObj.getBuiltupAreaUOM_v3());
					aForm.setValuatorCompany_v3(iObj.getValuatorCompany_v3());
					aForm.setCategoryOfLandUse_v3(iObj.getCategoryOfLandUse_v3());
					aForm.setCountry_v3(iObj.getCountry_v3());
					aForm.setRegion_v3(iObj.getRegion_v3());
					aForm.setLocationState_v3(iObj.getLocationState_v3());
					aForm.setNearestCity_v3(iObj.getNearestCity_v3());
					
					Amount amt_v3 = null;
					
					amt_v3=iObj.getTotalPropertyAmount_v3();
					aForm.setValuationDate_v3(DateUtil.formatDate(locale,iObj.getValuationDate_v3()));
					if ((amt_v3 != null) && (amt_v3.getCurrencyCode() != null)) {
						try {
							//Phase 3 CR:comma separated
							aForm.setTotalPropertyAmount_v3(UIUtil.formatWithCommaAndDecimal(amt_v3.getAmountAsBigDecimal().toString()));
							}
						catch (Exception e) {
							throw new MapperException(e.getMessage());
						}
					}	
					else if((amt_v3 != null) &&(amt_v3.getCurrencyCode() == null))
					{
						//Phase 3 CR:comma separated
						aForm.setTotalPropertyAmount_v3(UIUtil.formatWithCommaAndDecimal(amt_v3.getAmountAsBigDecimal().toString()));
					}
					aForm.setPinCode_v3(iObj.getPinCode_v3());
					if(iObj.getIsPhysicalInspection_v3()){
					aForm.setIsPhysInsp_v3("true");
				}else if(!iObj.getIsPhysicalInspection_v3()){
					aForm.setIsPhysInsp_v3("false");
				}else{
					aForm.setIsPhysInsp_v3("");
				}
				aForm.setVal3_id(iObj.getVal3_id());
				aForm.setVersion3(iObj.getVersion3());
						if(iObj.getValcreationdate_v3()!=null ){
							aForm.setValcreationdate_v3(DateUtil.formatDate(locale,iObj.getValcreationdate_v3()));
						}
						else{
							aForm.setValcreationdate_v3(null);
						}
						aForm.setPreValDate_v3(iObj.getPreValDate_v3());
						
						if(null!=aForm.getEvent() && "returnPreviousValuation3".equals(aForm.getEvent()))
							aForm.setPreValDate_v1("");
					
					
					//Valuation 3 end
						
						aForm.setPropertyCompletionStatus_v2(iObj.getPropertyCompletionStatus_v2());
						if (iObj.getBuiltupArea_v2() > 0) {
										aForm.setBuiltupArea_v2(MapperUtil.mapDoubleToString(iObj.getBuiltupArea_v2(), 6, locale));
									}
									if (iObj.getInSqftBuiltupArea_v2() > 0) {
										aForm.setInSqftBuiltupArea_v2(MapperUtil.mapDoubleToString(iObj.getInSqftBuiltupArea_v2(), 2, locale));
									}
									if (iObj.getInSqftLandArea_v2() > 0) {
										aForm.setInSqftLandArea_v2(MapperUtil.mapDoubleToString(iObj.getInSqftLandArea_v2(), 2, locale));
									}
									aForm.setBuiltUpAreaUnit_v2(iObj.getBuiltupAreaUOM_v2());
									aForm.setValuatorCompany_v2(iObj.getValuatorCompany_v2());
									aForm.setCategoryOfLandUse_v2(iObj.getCategoryOfLandUse_v2());
									aForm.setCountry_v2(iObj.getCountry_v2());
									aForm.setRegion_v2(iObj.getRegion_v2());
									aForm.setLocationState_v2(iObj.getLocationState_v2());
									aForm.setNearestCity_v2(iObj.getNearestCity_v2());
									Amount amt_v2 = null;
									
									amt_v2=iObj.getTotalPropertyAmount_v2();
									aForm.setValuationDate_v2(DateUtil.formatDate(locale,iObj.getValuationDate_v2()));
											if ((amt_v2 != null) && (amt_v2.getCurrencyCode() != null)) {
										try {
											aForm.setTotalPropertyAmount_v2(UIUtil.formatWithCommaAndDecimal(amt_v2.getAmountAsBigDecimal().toString()));
											}
										catch (Exception e) {
											throw new MapperException(e.getMessage());
										}
									}	
									else if((amt_v2 != null) &&(amt_v2.getCurrencyCode() == null))
									{
										aForm.setTotalPropertyAmount_v2(UIUtil.formatWithCommaAndDecimal(amt_v2.getAmountAsBigDecimal().toString()));
									}
									
									aForm.setPinCode_v2(iObj.getPinCode_v2());
									if(iObj.getIsPhysicalInspection_v2()){
									aForm.setIsPhysInsp_v2("true");
								}else if(!iObj.getIsPhysicalInspection_v2()){
									aForm.setIsPhysInsp_v2("false");
								}else{
									aForm.setIsPhysInsp_v2("");
								}
								
								aForm.setVal2_id(iObj.getVal2_id());
								
								aForm.setVersion2(iObj.getVersion2());
								
										if(iObj.getValcreationdate_v2()!=null ){
											aForm.setValcreationdate_v2(DateUtil.formatDate(locale,iObj.getValcreationdate_v2()));
										}
										else{
											aForm.setValcreationdate_v2(null);
										}
										
										if(null!=aForm.getEvent() && "returnPreviousValuation2".equals(aForm.getEvent()))
											aForm.setPreValDate_v2("");
			
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.proptery.ProptertyMapperHelper", "error is :"
					+ e.toString());
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

	private static Amount getAmount(Locale locale, IPropertyCollateral iObj, String defVal) {
		try {
			return CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), defVal);
		}
		catch (Exception e) {
			return null;
		}
	}

}
