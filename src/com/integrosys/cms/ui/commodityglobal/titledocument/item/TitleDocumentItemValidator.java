/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/titledocument/item/TitleDocumentItemValidator.java,v 1.2 2004/06/04 05:11:52 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.titledocument.item;

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
 * @since $Date: 2004/06/04 05:11:52 $ Tag: $Name: $
 */

public class TitleDocumentItemValidator {
	public static ActionErrors validateInput(TitleDocumentItemForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		if (!(errorCode = Validator.checkString(aForm.getDocumentDesc(), true, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("documentDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					100 + ""));
		}
		return errors;
	}
}
