/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.collateralNewMaster;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.commoncode.bus.CommonCodeDaoImpl;
import com.integrosys.cms.asst.validator.ASSTValidator;

/**
 *@author $Author: Abhijit R$
 *Validator for System Bank Branch
 */
public class CollateralNewMasterValidator {
	
	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();		
		MaintainCollateralNewMasterForm form = (MaintainCollateralNewMasterForm) commonform;
		
		String event= form.getEvent();		
		String errorCode = null;
		
		if(event!=null && !event.isEmpty() && "EODSyncMasters".equalsIgnoreCase(event)){
			CommonCodeDaoImpl commonCodeDaoImpl = (CommonCodeDaoImpl)BeanHouse.get("commonCodeDao");
	
			if(!commonCodeDaoImpl.checkEntryCodeAvailable("entryCode", "54",form.getNewCollateralSubType())){
				errors.add("newCollateralSubTypeError",new ActionMessage("Invalid Security Sub Type : "+form.getNewCollateralSubType()));
			}
		}
		
		if(form.getGo().equalsIgnoreCase("y")){
				boolean nameFlag = ASSTValidator.isValidANDName(form.getNewCollateralDescriptionSearch());
				if( nameFlag == true)
					errors.add("newCollateralDescriptionError", new ActionMessage("error.string.invalidCharacter"));
			
				boolean nameFlag1 = ASSTValidator.isValidANDName(form.getNewCollateralCodeSearch());
				if( nameFlag1 == true)
					errors.add("newCollateralCodeError", new ActionMessage("error.string.invalidCharacter"));
		}

		if(event.equalsIgnoreCase("maker_create_collateralNewMaster")||event.equalsIgnoreCase("maker_edit_collateralNewMaster")||event.equalsIgnoreCase("maker_save_update")||event.equalsIgnoreCase("maker_confirm_resubmit_delete")){
			
		if(form.getNewCollateralDescription()==null || form.getNewCollateralDescription().trim().equals("") )
		{
			errors.add("newCollateralDescriptionError", new ActionMessage("error.string.mandatory"));
			
		}
		else if(!(errorCode = Validator.checkString(form.getNewCollateralDescription(), true, 0, 75)).equals(Validator.ERROR_NONE)) {
			errors.add("newCollateralDescriptionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",75 + ""));
		}
		
		else{
			boolean nameFlag = ASSTValidator.isValidANDName(form.getNewCollateralDescription());
			if( nameFlag == true)
				errors.add("newCollateralDescriptionError", new ActionMessage("error.string.invalidCharacter"));
		}
		
		if(form.getNewCollateralMainType()==null || form.getNewCollateralMainType().trim().equals("") )
		{
			errors.add("newCollateralMainTypeError", new ActionMessage("error.string.mandatory"));
			
		}
		if(form.getNewCollateralSubType()==null || form.getNewCollateralSubType().trim().equals("") )
		{
			errors.add("newCollateralSubTypeError", new ActionMessage("error.string.mandatory"));
			
		}
		if(StringUtils.isBlank(form.getNewCollateralCategory())) {
			errors.add("newCollateralCategoryError", new ActionMessage("error.string.mandatory"));
		}
		if(StringUtils.isBlank(form.getIsApplicableForCersaiInd())) {
			errors.add("isApplicableForCersaiIndError", new ActionMessage("error.string.mandatory"));
		}
		if(!(form.getRevaluationFrequencyCount()==null || form.getRevaluationFrequencyCount().trim().equals("0")|| form.getRevaluationFrequencyCount().trim().equals("")))
		{
			if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getRevaluationFrequencyCount().toString().trim(),false,1,999)))
			{
					errors.add("revaluationFrequencyCountError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),"1","3"));
			}else{	
					if(form.getRevaluationFrequencyDays()==null || form.getRevaluationFrequencyDays().trim().equals("") )
					{
						errors.add("revaluationFrequencyCountError", new ActionMessage("error.string.mandatory"));						
					}
			}
		}
		
		}else if(event.equalsIgnoreCase("maker_draft_collateralNewMaster")||event.equalsIgnoreCase("maker_update_draft_collateralNewMaster")){
						
			if(form.getNewCollateralDescription()==null || form.getNewCollateralDescription().trim().equals("") )
			{
				errors.add("newCollateralDescriptionError", new ActionMessage("error.string.mandatory"));
				
			}
			else if(!(errorCode = Validator.checkString(form.getNewCollateralDescription(), true, 0, 75)).equals(Validator.ERROR_NONE)) {
				errors.add("newCollateralDescriptionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",75 + ""));
			}
			
			else{
				boolean nameFlag = ASSTValidator.isValidANDName(form.getNewCollateralDescription());
				if( nameFlag == true)
					errors.add("newCollateralDescriptionError", new ActionMessage("error.string.invalidCharacter"));
			}
			
			 /*if(!(errorCode = Validator.checkString(form.getNewCollateralCode(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("newCollateralCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						30 + ""));
			}*/
			
			if(!(form.getRevaluationFrequencyCount()==null || form.getRevaluationFrequencyCount().trim().equals("0")|| form.getRevaluationFrequencyCount().trim().equals("")))
			{
				if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getRevaluationFrequencyCount().toString().trim(),false,1,999)))
				{
					errors.add("revaluationFrequencyCountError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),"1","3"));
				}else{	
						if(form.getRevaluationFrequencyDays()==null || form.getRevaluationFrequencyDays().trim().equals("") )
						{
							errors.add("revaluationFrequencyCountError", new ActionMessage("error.string.mandatory"));							
						}
				}
			}			
		}
		return errors;
    }

}
