/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MISecValidator {
	public static ActionErrors validateMISecurity(ActionForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		try {
			SecDetailForm secForm = (SecDetailForm) aForm;
			if (!(errorCode = Validator.checkString(secForm.getSecBookingOrg(), false, 1, 40))
					.equals(Validator.ERROR_NONE)) {
				errors.add("secBookingOrg", new ActionMessage("error.string.mandatory", "1", "40"));
			}
			
			if(!"WS_FAC_CREATE".equalsIgnoreCase(secForm.getEvent()) && 
					!"WS_FAC_EDIT".equalsIgnoreCase(secForm.getEvent()) && !"WS_NEW_FAC_EDIT".equalsIgnoreCase(secForm.getEvent()) &&
					!"WS_FAC_CREATE_REST".equalsIgnoreCase(secForm.getEvent())){

				if (!(errorCode = Validator.checkString(secForm.getSecBookingCountry(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("secBookingCountry", new ActionMessage("error.string.mandatory", "1", "40"));
				}
				
				if (!(errorCode = Validator.checkString(secForm.getSecType(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
					errors.add("secType", new ActionMessage("error.string.mandatory", "1", "40"));
				}
				
				if (!(errorCode = Validator.checkString(secForm.getSecCurrency(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("secCurrency", new ActionMessage("error.string.mandatory", "1", "40"));
				}
			}
			if(!"WS_NEW_FAC_EDIT".equalsIgnoreCase(secForm.getEvent())) {
				if (!(errorCode = Validator.checkString(secForm.getSecSubtype(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
					errors.add("secSubtype", new ActionMessage("error.string.mandatory", "1", "40"));
				}

				if (!(errorCode = Validator.checkString(secForm.getCollateralCode(), true, 1, 80))
						.equals(Validator.ERROR_NONE)) {
					errors.add("collateralCode", new ActionMessage("error.string.mandatory", "1", "80"));
				}
				if (!(errorCode = Validator.checkString(secForm.getSecPriority(), true, 1, 80))
						.equals(Validator.ERROR_NONE)) {
					errors.add("secPriority", new ActionMessage("error.string.mandatory", "1", "80"));
				}
				
				if(secForm.getSecCurrency()!=null&& !"".equals(secForm.getSecCurrency())){
					if(secForm.getSecType()!=null&& !"".equals(secForm.getSecType())){
						if(secForm.getSecType().equals("MS") && !secForm.getSecCurrency().equals("INR")){
							errors.add("secCurrency", new ActionMessage("error.string.secType", "1", "40"));	
						}
					}

				}
				if(secForm.getMonitorProcess()==null || secForm.getMonitorProcess().equals("")){
					errors.add("monitorProcess", new ActionMessage("error.string.mandatory", "1", "80"));
				}else if(secForm.getMonitorProcess().equalsIgnoreCase("Y")&& (secForm.getMonitorFrequency()==null || secForm.getMonitorFrequency().equals("")) ){
					errors.add("monitorFrequency", new ActionMessage("error.string.mandatory", "1", "80"));
				}
			}
			if("WS_FAC_CREATE".equalsIgnoreCase(secForm.getEvent()) || "WS_FAC_EDIT".equalsIgnoreCase(secForm.getEvent()) || "WS_NEW_FAC_EDIT".equalsIgnoreCase(secForm.getEvent())){
				if(secForm.getCpsSecurityId()!=null && !secForm.getCpsSecurityId().trim().isEmpty()){
					if(secForm.getCpsSecurityId().trim().length()>19){
						errors.add("cpsSecurityId",new ActionMessage("error.cpsSecurityId.length.exceeds","1","19"));
					}
				}
				
				if(secForm.getPrimarySecurityAddress().length() > 200) {
					errors.add("primarySecurityAddress", new ActionMessage("error.lengthofprimarySecurityAddress.exceed"));
				}

				if(secForm.getSecondarySecurityAddress().length() > 200) {
					errors.add("secondarySecurityAddress", new ActionMessage("error.lengthofsecondarySecurityAddress.exceed"));
				}

				if (!(secForm.getSecurityMargin() == null || secForm.getSecurityMargin().trim().equals(""))) {
					if (!Validator.ERROR_NONE.equals(errorCode = Validator
							.checkNumber(secForm.getSecurityMargin().toString().trim(), false, 0, 100))) {
						errors.add("securityMargin",
								new ActionMessage("error.string.securityMargin",secForm.getSecurityMargin()));
					}
				}
			}
			if(secForm.getSecType()!=null&& !"".equals(secForm.getSecType()) && secForm.getSecSubtype()!=null&& !"".equals(secForm.getSecSubtype()) ){
				if("AB101".equalsIgnoreCase(secForm.getSecSubtype()) && (null == secForm.getSecBookingOrg() || "".equals(secForm.getSecBookingOrg())) ){
					errors.add("secBookingOrg", new ActionMessage("error.string.mandatory"));
				}
			}
			

			if(!(errorCode = Validator.checkString(secForm.getSecReferenceNote(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("secReferenceNote", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						100 + ""));
			}
		}
		catch (Exception ex) {
		}
		return errors;
	}
	
	
	public static ActionErrors validateMISecurityRest(ActionForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		try {
			SecDetailForm secForm = (SecDetailForm) aForm;
			
			
			if(!"WS_FAC_CREATE".equalsIgnoreCase(secForm.getEvent()) && 
					!"WS_FAC_EDIT".equalsIgnoreCase(secForm.getEvent()) && 
					!"WS_FAC_CREATE_REST".equalsIgnoreCase(secForm.getEvent())){

				if (!(errorCode = Validator.checkString(secForm.getSecBookingCountry(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("secBookingCountry", new ActionMessage("error.string.mandatory", "1", "40"));
				}
				
				if (!(errorCode = Validator.checkString(secForm.getSecType(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
					errors.add("secType", new ActionMessage("error.string.mandatory", "1", "40"));
				}
				
				if (!(errorCode = Validator.checkString(secForm.getSecCurrency(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("secCurrency", new ActionMessage("error.string.mandatory", "1", "40"));
				}
			}
			if((secForm.getSecType()!=null && !secForm.getSecType().trim().isEmpty() ))
			{
				if(!("PT".equalsIgnoreCase(secForm.getSecType().trim()))){
					if (!(errorCode = Validator.checkString(secForm.getSecSubtype(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
						errors.add("secSubtype", new ActionMessage("error.string.mandatory", "1", "40"));
					}

					if (!(errorCode = Validator.checkString(secForm.getCollateralCode(), true, 1, 80))
							.equals(Validator.ERROR_NONE)) {
						errors.add("collateralCode", new ActionMessage("error.string.mandatory", "1", "80"));
					}
					if (!(errorCode = Validator.checkString(secForm.getSecPriority(), true, 1, 80))
							.equals(Validator.ERROR_NONE)) {
						errors.add("secPriority", new ActionMessage("error.string.mandatory", "1", "80"));
					}
					if(secForm.getSecCurrency()!=null&& !"".equals(secForm.getSecCurrency())){
						if(secForm.getSecType()!=null&& !"".equals(secForm.getSecType())){
							if(secForm.getSecType().equals("MS") && !secForm.getSecCurrency().equals("INR")){
								errors.add("secCurrency", new ActionMessage("error.string.secType", "1", "40"));	
							}
						}

					}
					if(secForm.getMonitorProcess()==null || secForm.getMonitorProcess().equals("")){
						errors.add("monitorProcess", new ActionMessage("error.string.mandatory", "1", "80"));
					}else if(secForm.getMonitorProcess().equalsIgnoreCase("Y")&& (secForm.getMonitorFrequency()==null || secForm.getMonitorFrequency().equals("")) ){
						errors.add("monitorFrequency", new ActionMessage("error.string.mandatory", "1", "80"));
					}


					if(secForm.getSecType()!=null&& !"".equals(secForm.getSecType()) && secForm.getSecSubtype()!=null&& !"".equals(secForm.getSecSubtype()) ){
						if("AB101".equalsIgnoreCase(secForm.getSecSubtype()) && (null == secForm.getSecBookingOrg() || "".equals(secForm.getSecBookingOrg())) ){
							errors.add("secBookingOrg", new ActionMessage("error.string.mandatory"));
						}
					}


					if(!(errorCode = Validator.checkString(secForm.getSecReferenceNote(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
						errors.add("secReferenceNote", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
								100 + ""));
					}
				}
			}
		}
		catch (Exception ex) {
		}
		return errors;
	}

	public static ActionErrors validatePledgorDtl(ActionForm aForm, Locale locale) {
		return null;
	}

}
