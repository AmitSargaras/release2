/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/documentlocation/DocumentLocationValidator.java,v 1.8 2006/09/14 07:50:38 jitendra Exp $
 */
package com.integrosys.cms.ui.documentlocation;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Description
 * 
 * @author $Author: jitendra $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/09/14 07:50:38 $ Tag: $Name: $
 */

public class DocumentLocationValidator {
	public static ActionErrors validateInput(DocumentLocationForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (!(errorCode = Validator.checkString(aForm.getDocOriginateLoc(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("docOriginateLoc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					20 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getOrgCode(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors
					.add("orgCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							20 + ""));
		}
		if (!aForm.getEvent().equals(DocumentLocationAction.EVENT_APPROVE)
				&& !aForm.getEvent().equals(DocumentLocationAction.EVENT_REJECT)) {
			if (!(errorCode = Validator.checkString(aForm.getDocRemarks(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("docRemarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						250 + ""));
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getRemarks(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("remarks",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
		}
		return errors;
	}
}
