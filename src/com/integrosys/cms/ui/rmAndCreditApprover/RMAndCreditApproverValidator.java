/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.rmAndCreditApprover;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.relationshipmgr.RelationshipMgrValidator;


/**
 *@author $Author: Abhijit R$
 *Validator for System Bank Branch
 */
public class RMAndCreditApproverValidator {


	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
	{
		ActionErrors errors = new ActionErrors();

		RMAndCreditApproverForm form = (RMAndCreditApproverForm) commonform;
		String event=form.getEvent();
		String errorCode = null;

		/*RM_MGR_NAME --done
		REGION
		DEPRECATED
		RM_MGR_MAIL
		EMPLOYEE_ID
		REPORTING_HEAD*/

		if(form.getUserName()==null || "".equals(form.getUserName().trim()))
		{	
			errors.add("userNameError", new ActionMessage("USER_NAME is mandatory"));
			DefaultLogger.debug(RelationshipMgrValidator.class, "userNameError");
		}
		else if(form.getUserName().length()>40)
		{
			errors.add("userNameError", new ActionMessage("For USER_NAME valid range is between 0 and 40 character(s)"));
			DefaultLogger.debug(RMAndCreditApproverValidator.class, "relationshipMgrNameError");
		}
		else{
			boolean nameFlag = ASSTValidator.isValidRelationshipManagerName(form.getUserName());
			if( nameFlag == true)
				errors.add("userNameError", new ActionMessage("USER_NAME has Invalid characters"));
		}

		if(form.getRegion()==null || "".equals(form.getRegion().trim()) )
		{
			errors.add("regionError", new ActionMessage("USER_LOCCODE is mandatory"));
			DefaultLogger.debug(RMAndCreditApproverValidator.class, "regionError");

		}

		if(form.getDeprecated()!=null && !"".equals(form.getDeprecated().trim()))
		{
			if( !"Y".equalsIgnoreCase(form.getDeprecated()) && !"N".equalsIgnoreCase(form.getDeprecated()))
				errors.add("deprecatedError", new ActionMessage("USER_DISABLED has invalid Character"));

		}
		else{
			errors.add("deprecatedError", new ActionMessage("USER_DISABLED is mandatory"));
		}
		if(form.getDpValue()==null || "".equals(form.getDpValue()))
		{

			if(form.getUserEmail()==null || "".equals(form.getUserEmail().trim()))
			{
				errors.add("userMailIdError", new ActionMessage("USER_EMAIL is mandatory"));
				DefaultLogger.debug(RMAndCreditApproverValidator.class, "userMailIdError");

			}
			else if( form.getUserEmail().length()>50 ) 	{
				errors.add("userMailIdError", new ActionMessage("For USER_EMAIL valid range is between 0 and 50 character(s)"));
				DefaultLogger.debug(RelationshipMgrValidator.class, "userMailIdError");
			}
			else if( ASSTValidator.isValidEmail(form.getUserEmail()) )
			{
				errors.add("userMailIdError", new ActionMessage("USER_EMAIL format is invalid"));
				DefaultLogger.debug(RelationshipMgrValidator.class, "userMailIdError");
			}

		}
		else{
			if(form.getUserEmail()!=null && !(form.getUserEmail().equals("")))
			{
				if( form.getUserEmail().length()>50 ) 	{
					errors.add("userMailIdError", new ActionMessage("For USER_EMAIL valid range is between 0 and 50 character(s)"));
					DefaultLogger.debug(RelationshipMgrValidator.class, "userMailIdError");
				}
				else if( ASSTValidator.isValidEmail(form.getUserEmail()) )
				{
					errors.add("userMailIdError", new ActionMessage("USER_EMAIL format is invalid"));
					DefaultLogger.debug(RelationshipMgrValidator.class, "userMailIdError");
				}
			}
		}
		if(form.getCpsId()==null || "".equals(form.getCpsId().trim()))
		{
			errors.add("loginIdError", new ActionMessage("LOGIN_ID is mandatory"));
			DefaultLogger.debug(RMAndCreditApproverValidator.class, "loginIdError");
		}
		
		if(form.getDeprecated()==null || "".equals(form.getDeprecated().trim()))
		{
			errors.add("deprecatedError", new ActionMessage("USER_DISABLED is mandatory"));

		}else{
			if( !"Y".equalsIgnoreCase(form.getDeprecated()) && !"N".equalsIgnoreCase(form.getDeprecated()))
				errors.add("deprecatedError", new ActionMessage("USER_DISABLE has invalid Character"));
		}

		if(form.getSeniorApproval()!=null && !"".equals(form.getSeniorApproval().trim()))
		{
			if( !"Y".equalsIgnoreCase(form.getSeniorApproval()) && !"N".equalsIgnoreCase(form.getSeniorApproval()))
				errors.add("seniorApprovalError", new ActionMessage("SENIOR_APPROVAL has invalid Character"));

		}

		if(form.getDpValue()!=null && !"".equals(form.getDpValue().trim())){
			if (!(errorCode = Validator.checkNumber(form.getDpValue(),
					false, 0, 99999999999999.99, 3, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dpValueLengthError", new ActionMessage("DP_VALUE Length Should be Number(14,2)"));
			}
			
			boolean nameFlag = ASSTValidator.isValidCreditApproverName(form.getUserName());
			if (nameFlag == true)
				errors.add("userNameError", new ActionMessage("USER_NAME has Invalid characters"));
		}
		
		if(form.getUserUnitType()==null || "".equals(form.getUserUnitType().trim()))
		{
			errors.add("userUnitTypeError", new ActionMessage("USER_UNIT_TYPE is mandatory"));
			DefaultLogger.debug(RelationshipMgrValidator.class, "userUnitTypeError");
		}
		
		if(form.getUserRole()==null || "".equals(form.getUserRole().trim()))
		{
			errors.add("userRoleError", new ActionMessage("USER_ROLE is mandatory"));
			DefaultLogger.debug(RelationshipMgrValidator.class, "userRoleError");
		}
		
		return errors;
	}

}
