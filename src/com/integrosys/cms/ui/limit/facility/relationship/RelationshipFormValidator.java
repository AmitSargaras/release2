package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class RelationshipFormValidator {
	public static ActionErrors validateInput(RelationshipForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = null;
		DefaultLogger.debug(" form event is ", "-------------------------->" + form.getEvent());
		
		/*if(FacilityMainAction.EVENT_SEARCH_CUSTOMER_WO_FRAME.equals(form.getEvent())){
			errors.add("refuseSearch", new ActionMessage("error.no.records.found","to-do list"));
			return errors;
		}*/
		
		if (!(FacilityMainAction.EVENT_SEARCH_CUSTOMER.equals(form.getEvent())
				|| FacilityMainAction.EVENT_SEARCH_CUSTOMER_WO_FRAME.equals(form.getEvent())
				|| FacilityMainAction.EVENT_ADD.equals(form.getEvent()) || FacilityMainAction.EVENT_ADD_WO_FRAME
				.equals(form.getEvent()))) {
			if (StringUtils.isBlank(form.getAccountRelationshipEntryCode())) {
				errors.add("accountRelationshipEntryCode", new ActionMessage("error.mandatory"));
			}
			if (!(errorCode = Validator.checkNumber(form.getShareHolderPercentage(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9)).equals(Validator.ERROR_NONE)) {
				errors.add("shareHolderPercentage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
						errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR + ""));
			}
			if (!(errorCode = Validator.checkNumber(form.getGuaranteePercentage(), false, 1,
					IGlobalConstant.MAXIMUM_PERCENTAGE_VALUE)).equals(Validator.ERROR_NONE)) {
				errors.add("guaranteePercentage",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "1",
								IGlobalConstant.MAXIMUM_PERCENTAGE_VALUE + ""));
			}
			if (!(errorCode = Validator.checkAmount(form.getGuaranteeAmount(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("guaranteeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_STR + ""));
			}
			// because the field in database is SMALLINT type so it wont accept
			// if the number exclude -32768 to 32767
			if (!(errorCode = Validator.checkNumber(form.getNameAssociateWithFacilityOrder(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator.ERROR_NONE)) {
				errors.add("nameAssociateWithFacilityOrder", new ActionMessage(ErrorKeyMapper.map(
						ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER + ""));
			}
			if (!(errorCode = Validator.checkNumber(form.getProfitRatio(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9)).equals(Validator.ERROR_NONE)) {
				errors.add("profitRatio", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR + ""));
			}
			if (StringUtils.isNotBlank(form.getDividendRatio())) {
				if (!(errorCode = Validator.checkNumber(form.getDividendRatio(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9)).equals(Validator.ERROR_NONE)) {
					errors.add("dividendRatio", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_9_STR + ""));
				}
			}
		}
		else if (FacilityMainAction.EVENT_SEARCH_CUSTOMER.equals(form.getEvent())
				|| FacilityMainAction.EVENT_SEARCH_CUSTOMER_WO_FRAME.equals(form.getEvent())) {
			if (FacilityMainAction.SEARCH_BY_NAME.equals(form.getSearchButton())
					|| FacilityMainAction.SEARCH_BY_NAME_WO_FRAME.equals(form.getSearchButton())) {
				if (!(Validator.checkString(form.getLegalName(), true, 3, 40)).equals(Validator.ERROR_NONE)) {
					errors.add("legalName", new ActionMessage("error.string.customername"));
				}
			}
			else if (FacilityMainAction.SEARCH_BY_LEGAL_ID.equals(form.getSearchButton())
					|| FacilityMainAction.SEARCH_BY_LEGAL_ID_WO_FRAME.equals(form.getSearchButton())) {
                //Andy Wong, 21 Nov 2008: validate CIF Id for numeric type
                //trim CIF field
                form.setLEReference(StringUtils.trim(form.getLEReference()));
                String errMsg = Validator.checkNumber(form.getLEReference(), true, 1, 20);
                if (!(errMsg.equals(Validator.ERROR_NONE)
                        || errMsg.equals(Validator.ERROR_GREATER_THAN)
                        || errMsg.equals(Validator.ERROR_LESS_THAN))) {
                    errors.add("LEReference", new ActionMessage("error.number." + errMsg));
                }
			}
			else if (FacilityMainAction.SEARCH_BY_ID_NUMBER.equals(form.getSearchButton())
					|| FacilityMainAction.SEARCH_BY_ID_NUMBER_WO_FRAME.equals(form.getSearchButton())) {
				if (!(Validator.checkString(form.getIdNo(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
					errors.add("idNo", new ActionMessage("error.mandatory"));
				}
			}
		}
		else if (FacilityMainAction.EVENT_ADD.equals(form.getEvent()) || FacilityMainAction.EVENT_ADD_WO_FRAME.equals(form.getEvent())) {
			if (StringUtils.isBlank(form.getSelectedId())) {
				errors.add("selectedId", new ActionMessage("error.mandatory"));
			}
		}
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
