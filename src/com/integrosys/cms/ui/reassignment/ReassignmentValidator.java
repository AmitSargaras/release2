/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/reassignment/ReassignmentValidator.java,v 1.2 2004/10/08 10:22:24 hshii Exp $
 */
package com.integrosys.cms.ui.reassignment;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/10/08 10:22:24 $ Tag: $Name: $
 */
public class ReassignmentValidator {
	public static ActionErrors validateInput(ReassignmentForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (ReassignmentConstant.TASK_TYPE_CCC.equals(aForm.getReassignmentType())) {
			if (ReassignmentConstant.TASK_SEARCH_TRXID.equals(aForm.getSearchType())) {
				if (!(errorCode = Validator.checkString(aForm.getCccTrxID(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
					errors.add("cccTrxID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							40 + ""));
				}
			}
			else {
				if (!(errorCode = Validator.checkNumber(aForm.getCcChecklistID(), true, 0, Long.MAX_VALUE, 0, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("ccChecklistID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", Long.MAX_VALUE + ""));
				}
			}
		}
		else if (ReassignmentConstant.TASK_TYPE_SCC.equals(aForm.getReassignmentType())) {
			if (ReassignmentConstant.TASK_SEARCH_TRXID.equals(aForm.getSearchType())) {
				if (!(errorCode = Validator.checkString(aForm.getSccTrxID(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
					errors.add("sccTrxID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							40 + ""));
				}
			}
			else {
				if (!(errorCode = Validator.checkNumber(aForm.getScChecklistID(), true, 0, Long.MAX_VALUE, 0, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("scChecklistID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", Long.MAX_VALUE + ""));
				}
			}
		}
		else if (ReassignmentConstant.TASK_TYPE_DEAL.equals(aForm.getReassignmentType())) {
			if (ReassignmentConstant.TASK_SEARCH_TRXID.equals(aForm.getSearchType())) {
				if (!(errorCode = Validator.checkString(aForm.getDealTrxID(), true, 0, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("dealTrxID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 40 + ""));
				}
			}
			else {
				if (!(errorCode = Validator.checkString(aForm.getDealNo(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
					errors.add("dealNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							40 + ""));
				}
			}
		}

		return errors;
	}
}
