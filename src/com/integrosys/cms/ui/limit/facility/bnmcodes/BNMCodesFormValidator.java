package com.integrosys.cms.ui.limit.facility.bnmcodes;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class BNMCodesFormValidator {
	public static ActionErrors validateInput(BNMCodesForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();

		String event = form.getEvent();
		boolean isMandatoryValidate = false;
		if (FacilityMainAction.EVENT_SUBMIT.equals(event) || FacilityMainAction.EVENT_SUBMIT_WO_FRAME.equals(event)) {
			isMandatoryValidate = true;
		}
		if (isMandatoryValidate) {
			if (StringUtils.isBlank(form.getIndustryCodeEntryCode())) {
				errors.add("industryCodeEntryCode", new ActionMessage("error.mandatory"));
			}
			if (StringUtils.isBlank(form.getSectorCodeEntryCode())) {
				errors.add("sectorCodeEntryCode", new ActionMessage("error.mandatory"));
			}
			if (StringUtils.isBlank(form.getStateCodeEntryCode())) {
				errors.add("stateCodeEntryCode", new ActionMessage("error.mandatory"));
			}
			if (StringUtils.isBlank(form.getBumiOrNrccCodeEntryCode())) {
				errors.add("bumiOrNrccCodeEntryCode", new ActionMessage("error.mandatory"));
			}
			if (StringUtils.isBlank(form.getSmallScaleCodeCodeEntryCode())) {
				errors.add("smallScaleCodeCodeEntryCode", new ActionMessage("error.mandatory"));
			}
			/*if (StringUtils.isBlank(form.getPrescribedRateCodeEntryCode())) {
				errors.add("prescribedRateCodeEntryCode", new ActionMessage("error.mandatory"));
			}*/
			
			String exemptedCodeIndicator = form.getExemptedCodeIndicator();
			if(exemptedCodeIndicator!=null){
				if(exemptedCodeIndicator.equals("Y")){
					if (StringUtils.isBlank(form.getExemptedCodeEntryCode())) {
						errors.add("exemptedCodeEntryCode", new ActionMessage("error.mandatory"));
					}
				}
			}
			
			if (StringUtils.isBlank(form.getRelationshipCodeEntryCode())) {
				errors.add("relationshipCodeEntryCode", new ActionMessage("error.mandatory"));
			}
			/*if (StringUtils.isBlank(form.getGoodsFinancedCodeOneEntryCode())) {
				errors.add("goodsFinancedCodeOneEntryCode", new ActionMessage("error.mandatory"));
			}*/
			/*if (StringUtils.isBlank(form.getGoodsFinancedCodeTwoEntryCode())) {
				errors.add("goodsFinancedCodeTwoEntryCode", new ActionMessage("error.mandatory"));
			}*/
			/*if (StringUtils.isBlank(form.getExemptedCodeEntryCode())) {
				errors.add("exemptedCodeEntryCode", new ActionMessage("error.mandatory"));
			}*/
			/*
			 * if (StringUtils.isBlank(form.getHostTierSequenceNumber())) {
			 * errors.add("hostTierSequenceNumber", new
			 * ActionMessage("error.mandatory")); }
			 */
			if (StringUtils.isBlank(form.getPurposeCodeEntryCode())) {
				errors.add("purposeCodeEntryCode", new ActionMessage("error.mandatory"));
			}
			if (StringUtils.isBlank(form.getExemptedCodeIndicator())) {
				errors.add("exemptedCodeIndicator", new ActionMessage("error.mandatory"));
			}
		}

		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
