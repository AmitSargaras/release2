/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/insurance/InsuranceValidator.java,v 1.7 2005/04/29 02:47:25 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.insurance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/04/29 02:47:25 $ Tag: $Name: $
 */

public class InsuranceValidator {
	public static ActionErrors validateInput(InsuranceForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		boolean isMandatory = (aForm.getEvent().equals(InsuranceAction.EVENT_CREATE) || aForm.getEvent().equals(
				InsuranceAction.EVENT_UPDATE));

		if (!(errorCode = Validator.checkString(aForm.getInsPolicyNum(), (isMandatory && true), 1, 30))
				.equals(Validator.ERROR_NONE)) {
			errors.add("insPolicyNum", new ActionMessage("error.string.mandatory", "1", "25"));
		}
		if (!(errorCode = Validator.checkString(aForm.getInsurerName(), (isMandatory && true), 1, 25))
				.equals(Validator.ERROR_NONE)) {
			errors.add("insurerName", new ActionMessage("error.string.mandatory", "1", "25"));
		}
		if (!(errorCode = Validator.checkString(aForm.getInsuranceType(), (isMandatory && true), 1, 25))
				.equals(Validator.ERROR_NONE)) {
			errors.add("insuranceType", new ActionMessage("error.string.mandatory", "1", "25"));
		}
		Date expiryDate = null;
		Date effectiveDate = null;
		if (!(errorCode = Validator.checkDate(aForm.getEffectiveDateIns(), (isMandatory && true), locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("effectiveDateIns", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			effectiveDate = DateUtil.convertDate(locale, aForm.getEffectiveDateIns());
		}
		if (!(errorCode = Validator.checkDate(aForm.getExpiryDateIns(), (isMandatory && true), locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("expiryDateIns", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			expiryDate = DateUtil.convertDate(locale, aForm.getExpiryDateIns());
		}
		if (isMandatory) {
			if ((effectiveDate != null) && (expiryDate != null) && effectiveDate.after(expiryDate)) {
				errors.add("effectiveDateIns", new ActionMessage("error.date.compareDate.greater", "Effective Date",
						"Expiry Date"));
			}
		}
		// double insurableAmt = Double.MAX_VALUE;
		// double insuredAmt = Double.MIN_VALUE;
		try {
			if (!(errorCode = Validator.checkAmount(aForm.getInsurableAmt(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insurableAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						maximumAmt));
				/*
				 * } else { if (aForm.getInsurableAmt() != null &&
				 * aForm.getInsurableAmt().trim().length() > 0) { insurableAmt =
				 * MapperUtil.mapStringToDouble(aForm.getInsurableAmt(),
				 * locale); }
				 */
			}
			if (!(errorCode = Validator.checkAmount(aForm.getInsuredAmt(), (isMandatory && true), 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insuredAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						maximumAmt));
				/*
				 * } else { if (isMandatory) { insuredAmt =
				 * MapperUtil.mapStringToDouble(aForm.getInsuredAmt(), locale);
				 * }
				 */
			}
			/*
			 * if (insuredAmt > insurableAmt) { errors.add("insuredAmt", new
			 * ActionMessage("error.amount.not.greaterthan", "Insured Amount",
			 * "Insurable Amount")); }
			 */
		}
		catch (Exception e) {
			DefaultLogger.error("InsuranceValidator", "Exception throw at mapperUtil convert String to double");
		}
		
		System.out.println("remark1Error validate::aForm.getEvent();==========="+aForm.getEvent());
		if(AbstractCommonMapper.isEmptyOrNull(aForm.getRemark1())) {
			errors.add("remark1Error", new ActionMessage("error.string.mandatory"));
		}

		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getInsuredAgainst(), false))
				.equals(Validator.ERROR_NONE)) {
			errors.add("insuredAgainst", RemarksValidatorUtil.getErrorMessage(errorCode));
		}

		return errors;
	}
}
