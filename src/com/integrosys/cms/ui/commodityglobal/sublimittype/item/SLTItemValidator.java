/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/item/SLTItemValidator.java,v 1.1 2005/10/06 06:03:37 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-26
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.item.SLTItemValidator.
 *      java
 */
public class SLTItemValidator {
	public static ActionErrors validateInput(SubLimitTypeItemForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		if (!(errorCode = Validator.checkString(aForm.getLimitType(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add(SLTUIConstants.FN_LIMIT_TYPE, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
					errorCode), "0", "40"));
		}
		if (!(errorCode = Validator.checkString(aForm.getSubLimitType(), true, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add(SLTUIConstants.FN_SUB_LIMIT_TYPE, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
					errorCode), "0", "100"));
		}
		return errors;
	}

}
