package com.integrosys.cms.ui.workspace;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;

public class WorkspaceFormValidator {

	public static ActionErrors validateInput(WorkspaceForm form, Locale locale) {


		ActionErrors errors = new ActionErrors();
		String errorCode="";
		
		// cam validation
		if(null!=form.getSearchAANumber()){
			if(!(errorCode = Validator.checkString(form.getSearchAANumber().trim(), false, 3, 35))
					.equals(Validator.ERROR_NONE))
				errors.add("aaNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "3",
						"35"));
		if (ASSTValidator.isValidAlphaNumStringWithSpacewWithSlash(form.getSearchAANumber().trim()))
			errors.add("aaNumberError", new ActionMessage("error.string.specialcharacter"));
		}
		//cif no.
		if(null!=form.getSearchLeID()){
		if (!(errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(form.getSearchLeID().trim(), false, 3, 40))
				.equals(Validator.ERROR_NONE)) {
			if(errorCode.equals("format"))
				errors.add("leIDError", new ActionMessage("error.string.specialcharacter"));
			else
			errors.add("leIDError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "3", "40"));
		}
		}
		// customer name
		if(null!=form.getSearchCustomerName()){
			if (!(errorCode = Validator.checkString(form.getSearchCustomerName().trim(), false, 3, 40))
					.equals(Validator.ERROR_NONE)) 
			errors.add("customerNameError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"3", "40"));
				if (ASSTValidator.isValidAndRoundBrackets(form.getSearchCustomerName().trim())){
				errors.add("customerNameError", new ActionMessage("error.string.specialcharacter"));	
			}
			}
		// last updated by
		if(null!=form.getLastUpdatedBy()){
		if (!(errorCode = Validator.checkString(form.getLastUpdatedBy().trim(), false, 3, 40))
				.equals(Validator.ERROR_NONE)) 
			errors.add("lastupdatedbyerror", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"3", "40"));
		if (ASSTValidator.isValidAndRoundBrackets(form.getLastUpdatedBy().trim())){
			errors.add("lastupdatedbyerror", new ActionMessage("error.string.specialcharacter"));	
		}
		}
		// skip validation if not entered
		if (isNull(form.getSearchLegalName()) && isNull(form.getLastUpdatedBy()) && isNull(form.getSearchCustomerName()) && isNull(form.getSearchLeID())
				&& isNull(form.getSearchAANumber())) {
			return errors;
		}
		
		/*if (!(errorCode = Validator.checkString(form.getSearchLeID(), false, 1, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("leIDError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40"));
		}*/
/*
		if ((form.getSearchLeID() != null) && !form.getSearchLeID().trim().equals("")) {
			if (!(errorCode = Validator.checkString(form.getSearchLeIDType(), true, 1, 20))
					.equals(Validator.ERROR_NONE)) {
				errors.add("searchLeIDType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", "40"));
			}
		}
*/
		/*if (!(errorCode = Validator.checkString(form.getSearchCustomerName(), false, 3, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("customerNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"3", "40"));
		}*/
		
		/*if (!(errorCode = Validator.checkString(form.getLastUpdatedBy(), false, 3, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("lastupdatedbyerror", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"3", "40"));
		}*/
		
		/*if (!(errorCode = Validator.checkString(form.getSearchAANumber(), false, 3, 35)).equals(Validator.ERROR_NONE)) {
			errors.add("aaNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "3",
					"35"));
		}*/
		if (!(errorCode = Validator.checkString(form.getSearchLegalName(), false, 1, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("legalNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					"40"));
		}

		return errors;
	}

	public static ActionErrors validateReassignInput(WorkspaceForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode;

		if (!(errorCode = Validator.checkString(form.getReassignTo(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
			errors
					.add("reassignTo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"40"));
		}

		return errors;
	}

	public static ActionErrors validatePendingCases(WorkspaceForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode;

		// do nothing

		return errors;
	}

	public static boolean isNull(String s) {
		return ((s == null) || (s.trim().length() == 0));
	}
}