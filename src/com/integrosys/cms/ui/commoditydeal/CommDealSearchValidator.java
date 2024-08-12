/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommDealSearchValidator.java,v 1.4 2004/06/30 03:51:40 pooja Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * Description
 * 
 * @author $Author: pooja $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/06/30 03:51:40 $ Tag: $Name: $
 */
public class CommDealSearchValidator {
	public static ActionErrors validateInput(CommDealSearchForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		try {
			if (!(errorCode = Validator.checkString(aForm.getDealNo(), true, 1, 18)).equals(Validator.ERROR_NONE)) {
				errors.add("dealNo", new ActionMessage("error.string.commodity.dealNo"));
				DefaultLogger.debug(" ERROR occured in dealNo", "--------->" + errors.size());
			}
		}
		catch (Exception e) {
			e.getMessage();
		}
		return errors;

	}
}
