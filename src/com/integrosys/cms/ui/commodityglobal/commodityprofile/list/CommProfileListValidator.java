/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/list/CommProfileListValidator.java,v 1.2 2004/06/04 05:11:33 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.list;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:33 $ Tag: $Name: $
 */

public class CommProfileListValidator {
	public static ActionErrors validateInput(CommProfileListForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (!(errorCode = Validator.checkString(aForm.getRemarks(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("remarks",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
		}

		return errors;
	}
}
