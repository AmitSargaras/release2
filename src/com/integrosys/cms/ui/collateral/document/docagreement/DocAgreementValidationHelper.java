/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/doclou/DocLoUValidationHelper.java,v 1.2 2003/07/18 11:12:39 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.document.docagreement;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * 
 * @author Thurein
 * @since  2/Sep/2008	
 *
 */
public class DocAgreementValidationHelper {
	public static ActionErrors validateInput(DocAgreementForm aForm, Locale locale, ActionErrors errors) {
		String errorCode = null;
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
        if (!(errorCode = Validator.checkAmount(aForm.getGuranteeAmount(), true, 0,
                IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
                .equals(Validator.ERROR_NONE)) {
            errors.add("guranteeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
                    maximumAmt));
        }

        if (!(errorCode = Validator.checkAmount(aForm.getBuybackValue(), true, 0,
                IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
                .equals(Validator.ERROR_NONE)) {
            errors.add("buybackValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
                    maximumAmt));
        }

		return errors;
	}
}
