package com.integrosys.cms.ui.collateral.nocollateral;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
/**
 * Created by IntelliJ IDEA. User: jitendra Date: Feb 26, 2007 Time: 4:20:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoCollateralValidator {

	private static String LOGOBJ = NoCollateralValidator.class.getName();

	public static ActionErrors validateInput(NoCollateralForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		errors = CollateralValidator.validateCersaiFields(aForm, locale,errors);

		/*if (!(errorCode = Validator.checkString(aForm.getSourceSecuritySubType(), true, 1, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("sourceSecuritySubType", new ActionMessage("error.string.mandatory", "1", "20"));
		}*/
		
		/*if (!(errorCode = Validator.checkString(aForm.getCollateralStatus(), true, 1, 3))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralStatus", new ActionMessage("error.string.mandatory", "1", "3"));
			DefaultLogger.debug(LOGOBJ, " aForm.getCollateralStatus() =" + aForm.getCollateralStatus());
		}*/

		if (!(errorCode = Validator.checkString(aForm.getCollateralLoc(), true, 1, 3))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralLoc", new ActionMessage("error.string.mandatory", "1", "3"));
			DefaultLogger.debug(LOGOBJ, " aForm.getCollateralLoc() =" + aForm.getCollateralLoc());
		}

		if (!(errorCode = Validator.checkString(aForm.getHaircut(), true, 1, 3))
				.equals(Validator.ERROR_NONE)) {
			errors.add("haircut", new ActionMessage("error.string.mandatory", "1", "3"));
			DefaultLogger.debug(LOGOBJ, " aForm.getHaircut() =" + aForm.getHaircut());
		}
		
		if (!(errorCode = Validator.checkString(aForm.getSecurityOrganization(), true, 1, 10))
				.equals(Validator.ERROR_NONE)) {
			errors.add("securityOrganization", new ActionMessage("error.string.mandatory", "1", "10"));
			DefaultLogger.debug(LOGOBJ, " aForm.getSecurityOrganization() =" + aForm.getSecurityOrganization());
		}
		if (!(errorCode = Validator.checkString(aForm.getLe(), true, 1, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("le", new ActionMessage("error.string.mandatory", "1", "40"));
		}
		
		String le = aForm.getLe()==null?"":aForm.getLe();
		if(le.equals("Y")&&!(errorCode = Validator.checkString(aForm.getLeDate(), true, 0, 15))
				.equals(Validator.ERROR_NONE)){
			errors.add("leDate", new ActionMessage("error.date.mandatory"));
		}
		
		/*if (!(errorCode = Validator.checkString(aForm.getCollateralName(), true, 1, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralName", new ActionMessage("error.string.mandatory", "1", "40"));
		}*/

		if(aForm.getEvent() != null && !(aForm.getEvent().equals("")) && aForm.getEvent().equals("submit")){
			if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("amountCMV", new ActionMessage("error.string.mandatory", "0", "50"));
		
	}
			}
		
		if (!(errorCode = Validator.checkNumber(aForm.getAmountCMV(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_4,5, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_4));
			DefaultLogger.debug(LOGOBJ, "aForm.getAmountCMV() = " + aForm.getAmountCMV());
		}
		
		//New General Field
		if(aForm.getSecurityMargin() != null && !"".equals(aForm.getSecurityMargin()))
		{
			
			if (!(errorCode = Validator.checkNumber(aForm.getSecurityMargin(), false, 0, 100.00))
					.equals(Validator.ERROR_NONE)) {
				errors.add("securityMarginError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", 100.00 + ""));
				DefaultLogger.debug(LOGOBJ, "aForm.getSecurityMargin() = " + aForm.getSecurityMargin());
			}
		}
		//End
		
		return errors;
	}
}
