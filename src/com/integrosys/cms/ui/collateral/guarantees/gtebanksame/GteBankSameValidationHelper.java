/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/guarantees/gtebanksame/GteBankSameValidationHelper.java,v 1.2 2003/07/23 10:20:45 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.guarantees.gtebanksame;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/23 10:20:45 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class GteBankSameValidationHelper {
	public static ActionErrors validateInput(GteBankSameForm aForm, Locale Locale, ActionErrors errors) {
		String errorCode = null;
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		boolean isMandatory = false;
		if ("submit".equals(aForm.getEvent())) {
			isMandatory = true;
		}

		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			
			// Start of Commented by Dattatray Thorat for Gurantee Security 
			/*if (!(errorCode = Validator.checkString(aForm.getSecIssueBank(), isMandatory, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("secIssueBank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						250 + ""));
			}

			// Check Secured Portion

			if (!(errorCode = Validator.checkNumber(aForm.getSecuredPortion(), isMandatory, 0,
					IGlobalConstant.MAXIMUM_PERCENTAGE_VALUE)).equals(Validator.ERROR_NONE)) {
				errors.add("securedPortion", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE_STR));
			}
			else {
				if (aForm.getSecuredPortion() != null) {
					String securedPortion = aForm.getSecuredPortion();
					for (int i = 0; i < securedPortion.length(); i++) {
						char aa = ',';
						if (aa == securedPortion.charAt(i)) {
							errors.add("securedPortion", new ActionMessage("error.integer.format"));
							break;
						}
					}
				}
			}

			// Check Unsecured Portion
			if (!(errorCode = Validator.checkNumber(aForm.getUnsecuredPortion(), isMandatory, 0,
					IGlobalConstant.MAXIMUM_PERCENTAGE_VALUE)).equals(Validator.ERROR_NONE)) {
				errors.add("unsecuredPortion", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE_STR));
			}
			else {
				if (aForm.getUnsecuredPortion() != null) {
					String unSecuredPortion = aForm.getUnsecuredPortion();
					for (int i = 0; i < unSecuredPortion.length(); i++) {
						char aa = ',';
						if (aa == unSecuredPortion.charAt(i)) {
							errors.add("unsecuredPortion", new ActionMessage("error.integer.format"));
							break;
						}
					}
				}
			}*/
			
			// End of Commented by Dattatray Thorat for Gurantee Security
			
			// Check Secured Amount Original
			if (StringUtils.isNotBlank(aForm.getSecuredPortion()) && !"0".equals(aForm.getSecuredPortion()))
				if (!(errorCode = Validator.checkAmount(aForm.getSecuredAmountOrigin(), isMandatory, 0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, Locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("securedAmountOrigin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							errorCode), "1", maximumAmt));
				}
			// Check Unsecured Amount Original
			if (StringUtils.isNotBlank(aForm.getUnsecuredPortion()) && !"0".equals(aForm.getUnsecuredPortion()))
				if (!(errorCode = Validator.checkAmount(aForm.getUnsecuredAmountOrigin(), isMandatory, 0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, Locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("unsecuredAmountOrigin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							errorCode), "1", maximumAmt));
				}
		}

		return errors;

	}
}
