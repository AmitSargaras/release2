/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/guarantees/gteslcdiff/GteSLCDiffValidationHelper.java,v 1.6 2006/04/10 07:10:31 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.guarantees.gteslcdiff;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/04/10 07:10:31 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class GteSLCDiffValidationHelper {
	public static ActionErrors validateInput(GteSLCDiffForm aForm, Locale locale, ActionErrors errors) {
		String errorCode;
		if (aForm.getEvent().equals("update") || aForm.getEvent().equals("submit")) {
			boolean isMandatory = false;
			if ("submit".equals(aForm.getEvent())) {
				isMandatory = true;
			}
			if (!(errorCode = Validator.checkString(aForm.getValCurrency(), isMandatory, 1, 3)).equals(Validator.ERROR_NONE)) {
				// errors.add("valCurrency", new
				// ActionMessage("error.string.mandatory", "1", "3"));
				DefaultLogger.debug(
						"com.integrosys.cms.ui.collateral.guarantees.gteslcdiff.gteslcdiffValidationHelper",
						"============= aForm.valBefMargin - aForm.valCurrency() ==========>");
			}
			
			if (!(errorCode = Validator.checkInteger(aForm.getSecuredPortion(), isMandatory, 0, 100))
					.equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode);
				errors.add("securedPortion", new ActionMessage(errorMessage,"0", "100"));
			}else{
				if(aForm.getSecuredPortion()!=null){
					String securedPortion = aForm.getSecuredPortion();
					for(int i=0;i<securedPortion.length();i++){
						char aa = ',';
						if(aa==securedPortion.charAt(i)){
							errors.add("securedPortion", new ActionMessage("error.integer.format"));
							break;
						}
					}
				}
			}
			
			if (!(errorCode = Validator.checkInteger(aForm.getUnsecuredPortion(), isMandatory, 0, 100))
					.equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode);
				errors.add("unsecuredPortion", new ActionMessage(errorMessage,"0", "100"));
			}else{
				if(aForm.getUnsecuredPortion()!=null){
					String unSecuredPortion = aForm.getUnsecuredPortion();
					for(int i=0;i<unSecuredPortion.length();i++){
						char aa = ',';
						if(aa==unSecuredPortion.charAt(i)){
							errors.add("unsecuredPortion", new ActionMessage("error.integer.format"));
							break;
						}
					}
				}
			}
		}

		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		if (!(errorCode = Validator.checkAmount(aForm.getMinimalFSV(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("minimalFSV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
		}

		return errors;

	}
}
