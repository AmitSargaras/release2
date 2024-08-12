package com.integrosys.cms.ui.user;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.component.login.app.LoginConstant;
import com.integrosys.component.login.app.PersistentEntityType;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.login.LoginValidator;
import com.integrosys.cms.ui.systemBankBranch.SystemBranchValidator;

/**
 * Command class to add the new user by admin maker on the corresponding
 * event...
 * @author $Author: ravi $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2003/10/01 07:55:14 $ Tag: $Name: $
 */
public class MaintainUserValidator {

	public static ActionErrors validateInput(MaintainUserForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		try {
			if ("maker_search_user".equals(form.getEvent())) {
				return validateSearchCondition(form, errors);
			}
			if (!(errorCode = Validator.checkString(form.getName(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("nameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"50"));
			}else{
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getLoginId());
				if( codeFlag == true)
					errors.add("nameError", new ActionMessage("error.string.invalidCharacter"));
				}
			
			if(form.getDepartment()==null || form.getDepartment().trim().equals("") )
			{
				errors.add("departmentError", new ActionMessage("error.string.mandatory"));
				
			}
			if(!(form.getAddress()==null||form.getAddress().trim().equals("")))
			{
			if (!(errorCode = Validator.checkString(form.getAddress(), false, 1, 200))
					.equals(Validator.ERROR_NONE)) {
				errors.add("addressError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"200"));
			}
			}
			if(form.getBranchCode()==null || form.getBranchCode().trim().equals("") )
			{
				errors.add("branchError", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getTeamName()==null || form.getTeamName().trim().equals("") )
			{
				errors.add("teamTypeMembershipIdError", new ActionMessage("error.string.mandatory"));
				
			}

			//boolean isLoginSingleSignOn = PropertyManager.getBoolean("integrosys.login.single.signon.enabled", false);
		//	if (!isLoginSingleSignOn) {
				//boolean passNotNull = false;
				//boolean confPassNotNull = false;
			//	if ((form.getPassword() == null) || form.getPassword().trim().equals("")) {
			//		passNotNull = true;
			//	}
			//	if ((form.getConfirmPassword() == null) || form.getConfirmPassword().trim().equals("")) {
			//		confPassNotNull = true;
			//	}

				//if (form.getCreation() || !passNotNull || !confPassNotNull) {
				//	LoginValidator validator = new LoginValidator(LoginConstant.SME_REALM,
			//				PersistentEntityType.LOS_USER);
			//		String[] formFields = new String[] { "loginIdError", "newPasswordError", "confirmNewPasswordError" };
			//		validator.validateOthersPwdChange(form.getLoginId(), form.getPassword(), form.getConfirmPassword(),
			///				formFields, errors);
			//	}
			//} else {
			//	LoginValidator validator = new LoginValidator(LoginConstant.SME_REALM,
		//				PersistentEntityType.LOS_USER);
			//	validator.validateLoginId(form.getLoginId(), "loginIdError", errors);
		//	}

			if (!(errorCode = Validator.checkString(form.getEmpId(), true, 1, 12)).equals(Validator.ERROR_NONE)) {
				errors.add("empIdError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"12"));
			}
			if (!(errorCode = Validator.checkString(form.getLoginId(), true, 1, 12)).equals(Validator.ERROR_NONE)) {
				errors.add("loginIdError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"12"));
			}
				else{
					boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getLoginId());
					if( codeFlag == true)
						errors.add("loginIdError", new ActionMessage("error.string.invalidCharacter"));
					}
			


//			if (!(errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(form.getEmpId(), false, 1, 10))
//					.equals(Validator.ERROR_NONE)) {
//				errors.add("empIdError", new ActionMessage("error.string.invalidCharacter"));
//			}

			if (!(errorCode = Validator.checkString(form.getEmail(), false, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("emailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"80"));
			}

			if (!(errorCode = Validator.checkEmail(form.getEmail(), false)).equals(Validator.ERROR_NONE)) {
				errors.add("emailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.EMAIL, errorCode)));
			}

			/*if (!(errorCode = Validator.checkPhoneNumber(form.getContactNo(), true, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("contactNoError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.PHONE, errorCode),
						"1", "20"));
			}*/

			if (!(errorCode = Validator.checkString(form.getCountryCode(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("countryCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", "50"));
			}
			if(!(form.getContactNo()==null||form.getContactNo().trim().equals("")))
			{
			if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getContactNo().toString().trim(),false,15,999999999999999.D)))
			{
					errors.add("contactNoError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						   "1",
						   "10"));
			}
			}
			
			/*if(form.getCityTown()==null || form.getCityTown().trim().equals("") )
			{
				errors.add("cityOBIdError", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getCountry()==null || form.getCountry().trim().equals("") )
			{
				errors.add("countryOBIdError", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getState()==null || form.getState().trim().equals("") )
			{
				errors.add("stateOBIdError", new ActionMessage("error.string.mandatory"));
				
			}
			if(form.getRegion()==null || form.getRegion().trim().equals("") )
			{
				errors.add("regionOBIdError", new ActionMessage("error.string.mandatory"));
				
			}*/
			
//			if(form.getSegment()==null || form.getSegment().trim().equals("") )
//			{
//				errors.add("segmentError", new ActionMessage("error.string.mandatory"));
//				
//			}
			
			

			/*if (!(errorCode = Validator.checkDate(form.getValidFromDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("validFromDateError", new ActionMessage("error.date.format"));
			}
			if (!(errorCode = Validator.checkDate(form.getValidToDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("validToDateError", new ActionMessage("error.date.format"));
			}*/

			/*if (form.getValidToDate().length() > 0) {
				if (DateUtil.convertDate(locale, form.getValidToDate()).before(DateUtil.clearTime(DateUtil.getDate()))) {
					errors.add("validToDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier",
							"To Date", "Current Date"));
				}
			}

			if (!(errorCode = Validator.checkDate(form.getValidFromDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				if (form.getValidToDate().length() > 0) {
					if (DateUtil.convertDate(locale, form.getValidToDate()).before(
							DateUtil.convertDate(locale, form.getValidFromDate()))) {
						errors.add("validToDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier",
								"Valid To Date", "Valid From Date"));
					}
				}
			}*/

			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
			/*if(errors.size()>0){
				form.setAppendSegmentList("");
				form.setAppendUserRegionList("");
			}*/
		}
		catch (Exception e) {
		}
		return errors;

	}

	private static ActionErrors validateSearchCondition(MaintainUserForm form, ActionErrors errors) {
		String errorCode = "";

		DefaultLogger.debug("SearchUser", " - SearchBy: " + form.getSearchBy());
		if ("loginId".equals(form.getSearchBy())) {
			if (!(errorCode = Validator.checkString(form.getLoginId(), true, 3, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("loginId", new ActionMessage("error.user.search.login.id"));
			}
		}
		else if ("name".equals(form.getSearchBy())) {
			if (!(errorCode = Validator.checkString(form.getName(), true, 3, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("name", new ActionMessage("error.user.search.name"));
			}
		}else if ("branch".equals(form.getSearchBy())) { 
			if(form.getBranchCode()==null || form.getBranchCode().trim().equals("") )
		
		{
			errors.add("branchCode", new ActionMessage("error.string.mandatory"));
			
		}
		}	
		else if ("status".equals(form.getSearchBy())) {
			if(form.getStatus()==null || form.getStatus().trim().equals("") )
		
		{
			errors.add("status", new ActionMessage("error.string.mandatory"));
			
		}
		}
		return errors;
	}
}