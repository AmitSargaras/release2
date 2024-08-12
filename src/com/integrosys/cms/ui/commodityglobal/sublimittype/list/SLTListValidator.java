/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/SLTListValidator.java,v 1.1 2005/10/06 06:04:01 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-23
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.list.SLTListValidator.
 *      java
 */
public class SLTListValidator {

	public static ActionErrors validateInput(SubLimitTypeListForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		if (!(errorCode = Validator.checkString(aForm.getRemarks(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("remarks",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
		}
		return errors;
	}
}
