/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.facilityNewMaster;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;


/**
 *@author $Author: Abhijit R$
 *Validator for System Bank Branch
 */
public class FacilityNewMasterValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		
		MaintainFacilityNewMasterForm form = (MaintainFacilityNewMasterForm) commonform;
		String event= form.getEvent();
		
		String errorCode = null;
		
		if(form.getGo().equalsIgnoreCase("y")){
			boolean nameFlag1 = ASSTValidator.isValidFacilityName(form.getNewFacilityCodeSearch());
			if( nameFlag1 == true)
				errors.add("newFacilityCodeError", new ActionMessage("error.string.invalidCharacter"));
			
			boolean nameFlag2 = ASSTValidator.isValidFacilityName(form.getNewFacilityNameSearch());
			if( nameFlag2 == true)
				errors.add("newFacilityNameError", new ActionMessage("error.string.invalidCharacter"));
			
			boolean nameFlag3 = ASSTValidator.isValidFacilityName(form.getLineNumberSearch());
			if( nameFlag3 == true)
				errors.add("newFacilityLineError", new ActionMessage("error.string.invalidCharacter"));
		}

		if(event.equalsIgnoreCase("maker_create_facilityNewMaster")||event.equalsIgnoreCase("maker_edit_facilityNewMaster")||event.equalsIgnoreCase("maker_save_update")||event.equalsIgnoreCase("maker_confirm_resubmit_delete")
		){
				
		if(!(form.getWeightage()==null||form.getWeightage().trim().equals("")))
		{
		 if (!(errorCode = Validator.checkNumber(form.getWeightage(), true, 0, 999.99)).equals(Validator.ERROR_NONE)) {
			errors.add("WeightageError" , new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "999.99"));
		 	}
		}
		if(form.getNewFacilityName()==null || form.getNewFacilityName().trim().equals("") )
		{
			errors.add("newFacilityNameError", new ActionMessage("error.string.mandatory"));
			errors.add("facilityNameError", new ActionMessage("Facility Name is mandatory"));
			
		}
		else if(!(errorCode = Validator.checkString(form.getNewFacilityName(), true, 0, 75)).equals(Validator.ERROR_NONE)) {
			errors.add("newFacilityNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					75 + ""));
		}
		else{
			boolean nameFlag = ASSTValidator.isValidFacilityName(form.getNewFacilityName());
			if( nameFlag == true)
				errors.add("newFacilityNameError", new ActionMessage("error.string.invalidCharacter"));
		}
		
	
		if(form.getNewFacilityCategory()==null || form.getNewFacilityCategory().trim().equals("") )
		{
			errors.add("newFacilityCategoryError", new ActionMessage("error.string.mandatory"));
			
		}
		if(form.getIntradayLimit()==null || form.getIntradayLimit().trim().equals("") )
		{
			errors.add("intradayLimitError", new ActionMessage("error.string.mandatory"));
			
		}
		if(form.getStlFlag()==null || form.getStlFlag().trim().equals("") )
		{
			errors.add("stlFlagError", new ActionMessage("error.string.mandatory"));
			
		}
		if(form.getLineDescription()==null || form.getLineDescription().trim().equals("") )
		{
			errors.add("lineDescriptionError", new ActionMessage("error.string.mandatory"));
			
		}
		if(form.getLineDescription().length() > 100 )
		{
			errors.add("lineDescriptionlengthError", new ActionMessage("Length Should be Less than 100 characters"));
			
		}
		if (!(errorCode = Validator.checkString(form.getLineDescription(), false, 1, 1000))
				 .equals(Validator.ERROR_NONE)) {
				 errors.add("lineDescriptionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
				 "100"));
		}
		if(ASSTValidator.isValidLineDescription(form.getLineDescription())) {
			errors.add("lineDescriptionError", new ActionMessage("error.string.invalidCharacter"));
		}
//		if(form.getRevolvingLine()==null || form.getRevolvingLine().trim().equals("") )
//		{
//			errors.add("revolvingLine", new ActionMessage("error.string.mandatory"));
//			
//		}
		if(form.getNewFacilitySystem().equalsIgnoreCase("UBS-LIMITS") || form.getNewFacilitySystem().equalsIgnoreCase("FCUBS-LIMITS") ){
			if(form.getCurrencyRestriction()==null || form.getCurrencyRestriction().trim().equals("") || form.getCurrencyRestriction().trim().equals("-") )
			{
				errors.add("currencyRestrictionError", new ActionMessage("error.string.mandatory"));
				
			}
		}	
		
		
		if(form.getNewFacilitySystem()==null || form.getNewFacilitySystem().trim().equals("")||"null".equals(form.getNewFacilitySystem()) )
		{
			errors.add("newFacilitySystemError", new ActionMessage("error.string.mandatory"));
			errors.add("facilitySystemError", new ActionMessage("SYSTEM_ID is mandatory"));
			
		}
		else
		{
			if(form.getNewFacilitySystem().equalsIgnoreCase("UBSL")){
				if(form.getLineNumber()==null || form.getLineNumber().trim().equals("") )
				{
					errors.add("lineNumberError", new ActionMessage("error.string.mandatory"));
					
				}else if( form.getLineNumber().length() > 20 )
				{
					errors.add("lineNumberError", new ActionMessage("Length Should be Less than 20 characters"));
				}
				else
				{
					boolean nameFlag = ASSTValidator.isValidFacilityLineNumber(form.getLineNumber());
					if( nameFlag == true)
						errors.add("lineNumberError", new ActionMessage("error.string.invalidCharacter"));
				}
				
			}
			else{
				if( form.getLineNumber() != null && !form.getLineNumber().trim().equals("") ){
					if( form.getLineNumber().length() > 20 )
					{
						errors.add("lineNumberError", new ActionMessage("Length Should be Less than 20 characters"));
					}
					else
					{
						boolean nameFlag = ASSTValidator.isValidFacilityLineNumber(form.getLineNumber());
						if( nameFlag == true)
							errors.add("lineNumberError", new ActionMessage("error.string.invalidCharacter"));
					}
				}				
			}
		}
		if(form.getNewFacilityType()==null || form.getNewFacilityType().trim().equals("")  || form.getNewFacilityType().trim().equals("null"))
		{
			errors.add("newFacilityTypeError", new ActionMessage("error.string.mandatory"));
			errors.add("facilityTypeError", new ActionMessage("FACILITY_TYPE is mandatory"));
			
		}
		/* Sandeep shinde Commented*/
		
		/*if(!(errorCode = Validator.checkString(form.getLineNumber(), false, 0, 9)).equals(Validator.ERROR_NONE)) {
			errors.add("lineNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					9 + ""));
		}
		else{
			boolean nameFlag = ASSTValidator.isValidFacilityLineNumber(form.getLineNumber());
			if( nameFlag == true)
				errors.add("lineNumberError", new ActionMessage("error.string.invalidCharacter"));
		}
		
		 if( !(form.getLineNumber()==null || form.getLineNumber().trim().equals("") ) )
		{
			 if( form.getLineNumber().length() > 20 )
				 errors.add("lineNumberError", new ActionMessage("Length Should be Less than 20 characters"));
			 else{
				boolean nameFlag = ASSTValidator.isValidFacilityLineNumber(form.getLineNumber());
				if( nameFlag == true)
					errors.add("lineNumberError", new ActionMessage("error.string.invalidCharacter"));
			 }
		}*/
		
		if( form.getNewFacilityType().equalsIgnoreCase("FUNDED") || form.getNewFacilityType().equalsIgnoreCase("NON_FUNDED")){
			if( form.getPurpose() == null || form.getPurpose().equals("") )
				errors.add("purposeListError", new ActionMessage("error.string.mandatory"));
		}
		
		if(null == form.getSelectedRiskTypes() || ("").equals(form.getSelectedRiskTypes().trim()) )
		{
			errors.add("riskTypeNames", new ActionMessage("error.string.mandatory"));
			
		}
		
		
		
		if(form.getScmFlag()==null || form.getScmFlag().trim().equals("") )
		{
			errors.add("scmFlagError", new ActionMessage("error.string.mandatory"));
			
		}
		}else if(event.equalsIgnoreCase("maker_draft_facilityNewMaster")||event.equalsIgnoreCase("maker_update_draft_facilityNewMaster")){
			
			if(form.getNewFacilityName()==null || form.getNewFacilityName().trim().equals("") )
			{
				errors.add("newFacilityNameError", new ActionMessage("error.string.mandatory"));
				
			}else{
				boolean nameFlag = ASSTValidator.isValidFacilityName(form.getNewFacilityName());
				if( nameFlag == true)
					errors.add("newFacilityNameError", new ActionMessage("error.string.invalidCharacter"));
			}
			if(form.getNewFacilityCategory()==null || form.getNewFacilityCategory().trim().equals("") )
			{
				errors.add("newFacilityCategoryError", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getNewFacilityType()==null || form.getNewFacilityType().trim().equals("") )
			{
				errors.add("newFacilityTypeError", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getIntradayLimit()==null || form.getIntradayLimit().trim().equals("") )
			{
				errors.add("intradayLimitError", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getStlFlag()==null || form.getStlFlag().trim().equals("") )
			{
				errors.add("stlFlagError", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getLineDescription()==null || form.getLineDescription().trim().equals("") )
			{
				errors.add("lineDescriptionError", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getLineDescription().length() > 100 )
			{
				errors.add("lineDescriptionlengthError", new ActionMessage("Length Should be Less than 100 characters"));
				
			}
			if(form.getScmFlag()==null || form.getScmFlag().trim().equals("") )
			{
				errors.add("scmFlagError", new ActionMessage("error.string.mandatory"));
				
			}
			
			if(null == form.getSelectedRiskTypes() || ("").equals(form.getSelectedRiskTypes().trim()) )
			{
				errors.add("riskTypeNames", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getRevolvingLine()==null || form.getRevolvingLine().trim().equals("") )
			{
				errors.add("revolvingLine", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getNewFacilitySystem().equalsIgnoreCase("UBS-LIMITS") || form.getNewFacilitySystem().equalsIgnoreCase("FCUBS-LIMITS") ){
				if(form.getCurrencyRestriction()==null || form.getCurrencyRestriction().trim().equals("") || form.getCurrencyRestriction().trim().equals("-") )
				{
					errors.add("currencyRestrictionError", new ActionMessage("error.string.mandatory"));
					
				}
			}	
			/* if(!(errorCode = Validator.checkString(form.getNewFacilityCode(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("newFacilityCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						30 + ""));
			}*/
			
			/* Sandeep shinde Commented*/
			
			/*if(!(errorCode = Validator.checkString(form.getLineNumber(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("lineNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					20 + ""));
			}
			else{
				boolean nameFlag = ASSTValidator.isValidFacilityLineNumber(form.getLineNumber());
				if( nameFlag == true)
					errors.add("lineNumberError", new ActionMessage("error.string.invalidCharacter"));
			}*/
			
			if(form.getNewFacilitySystem()==null || form.getNewFacilitySystem().trim().equals("")|| form.getNewFacilitySystem().trim().equals("null") )
			{
				errors.add("newFacilitySystemError", new ActionMessage("error.string.mandatory"));
				
				
			}
			else
			{
				if(form.getNewFacilitySystem().equalsIgnoreCase("UBSL")){
					if(form.getLineNumber()==null || form.getLineNumber().trim().equals("") )
					{
						errors.add("lineNumberError", new ActionMessage("error.string.mandatory"));
						
					}else if( form.getLineNumber().length() > 20 )
					{
						errors.add("lineNumberError", new ActionMessage("Length Should be Less than 20 characters"));
					}
					else
					{
						boolean nameFlag = ASSTValidator.isValidFacilityLineNumber(form.getLineNumber());
						if( nameFlag == true)
							errors.add("lineNumberError", new ActionMessage("error.string.invalidCharacter"));
					}
					
				}
				else{
					if( form.getLineNumber() != null && !form.getLineNumber().trim().equals("") ){
						if( form.getLineNumber().length() > 20 )
						{
							errors.add("lineNumberError", new ActionMessage("Length Should be Less than 20 characters"));
						}
						else
						{
							boolean nameFlag = ASSTValidator.isValidFacilityLineNumber(form.getLineNumber());
							if( nameFlag == true)
								errors.add("lineNumberError", new ActionMessage("error.string.invalidCharacter"));
						}
					}				
				}
			}
		}
		if(event!=null && event.equalsIgnoreCase("EODSyncMasters")){
			
			
			if(form.getNewFacilityName()==null || form.getNewFacilityName().trim().equals("") )
			{
				errors.add("facilityNameError", new ActionMessage("FACILITY_NAME is mandatory"));
				
			}
			else if(!(errorCode = Validator.checkString(form.getNewFacilityName(), true, 0, 75)).equals(Validator.ERROR_NONE)) {
				errors.add("newFacilityNameError", new ActionMessage("For FACILITY_NAME valid range is between 0 and 75 character(s)"));
			}
			else{
				boolean nameFlag = ASSTValidator.isValidFacilityName(form.getNewFacilityName());
				if( nameFlag == true)
					errors.add("newFacilityNameError", new ActionMessage("FACILITY_NAME has Invalid characters"));
			}	
			if(form.getNewFacilitySystem().equalsIgnoreCase("UBS-LIMITS") || form.getNewFacilitySystem().equalsIgnoreCase("FCUBS-LIMITS") ){
				if(form.getCurrencyRestriction()==null || form.getCurrencyRestriction().trim().equals("") || form.getCurrencyRestriction().trim().equals("-"))
				{
					errors.add("currencyRestrictionError", new ActionMessage("error.string.mandatory"));
					
				}
			}	
			
			if(form.getNewFacilitySystem()==null || form.getNewFacilitySystem().trim().equals("")||"null".equals(form.getNewFacilitySystem()) )
			{
				errors.add("facilitySystemError", new ActionMessage("SYSTEM_ID is mandatory"));
				
			}
			else
			{
				if(form.getNewFacilitySystem().equalsIgnoreCase("UBSL")){
					if(form.getLineNumber()==null || form.getLineNumber().trim().equals("") )
					{
						errors.add("lineNumberError", new ActionMessage("FACILITY_BANKCODE is mandatory"));
						
					}else if( form.getLineNumber().length() > 20 )
					{
						errors.add("lineNumberError", new ActionMessage("FACILITY_BANKCODE Length Should be Less than 20 characters"));
					}
					else
					{
						boolean nameFlag = ASSTValidator.isValidFacilityLineNumber(form.getLineNumber());
						if( nameFlag == true)
							errors.add("lineNumberError", new ActionMessage("FACILITY_BANKCODE has invalid Characters"));
					}
					
				}
				else{
					if( form.getLineNumber() != null && !form.getLineNumber().trim().equals("") ){
						if( form.getLineNumber().length() > 20 )
						{
							errors.add("lineNumberError", new ActionMessage("FACILITY_BANKCODE Length Should be Less than 20 characters"));
						}
						else
						{
							boolean nameFlag = ASSTValidator.isValidFacilityLineNumber(form.getLineNumber());
							if( nameFlag == true)
								errors.add("lineNumberError", new ActionMessage("FACILITY_BANKCODE has invalid Character"));
						}
					}				
				}
			}
			
			/*if(form.getNewFacilityType()==null || form.getNewFacilityType().trim().equals(""))
			{
				errors.add("facilityTypeError", new ActionMessage("FACILITY_TYPE is mandatory"));
				
			}*/
			if(form.getNewFacilityType()!=null && !form.getNewFacilityType().trim().isEmpty()){
				MasterAccessUtility masterAccessUtilityObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
				try {
					Object obj = masterAccessUtilityObj.getObjByEntityNameAndCPSId("entryCode", form.getNewFacilityType().trim(), "FACILITY_TYPE");
					if(obj!=null){
						form.setNewFacilityType(((ICommonCodeEntry)obj).getEntryCode());
					}else{
						errors.add("newFacilityTypeError",new ActionMessage("FACILITY_TYPE is invalid"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				errors.add("newFacilityTypeError",new ActionMessage("FACILITY_TYPE is mandatory"));
			}
			if(form.getDeprecated()==null || "".equals(form.getDeprecated().trim()))
			{
				errors.add("deprecatedError", new ActionMessage("FACILITY_DELETED is mandatory"));

			}else{
				if( !"Y".equalsIgnoreCase(form.getDeprecated()) && !"N".equalsIgnoreCase(form.getDeprecated()))
					errors.add("deprecatedError", new ActionMessage("FACILITY_DELETED has invalid Character"));
			}
			
		}
		
		return errors;
    }

}
