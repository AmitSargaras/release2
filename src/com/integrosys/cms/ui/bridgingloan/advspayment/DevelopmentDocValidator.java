/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Development Document Validation
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class DevelopmentDocValidator {
	public static ActionErrors validateInput(com.integrosys.cms.ui.bridgingloan.advspayment.DevelopmentDocForm form,
			Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errMsg = null;

		try {
			DefaultLogger.debug("in DevelopmentDocValidator", "form.getEvent()=" + form.getEvent());

			if (!DevelopmentDocAction.EVENT_CREATE.equals(form.getEvent())) {
				String docName = form.getDocName();
				String docRef = form.getDocRef();
				String receiveDate = form.getReceiveDate();
				String docDate = form.getDocDate();
				String remarks = form.getRemarks();

				if (!(errMsg = Validator.checkString(docName, true, 0, 100)).equals(Validator.ERROR_NONE)) {
					errors.add("docName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
							"100"));
				}
				if (!(errMsg = Validator.checkString(docRef, false, 0, 50)).equals(Validator.ERROR_NONE)) {
					errors.add("docRef",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "50"));
				}
				if (!(errMsg = Validator.checkDate(receiveDate, true, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("receiveDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
				}
				if (!(errMsg = Validator.checkDate(docDate, true, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("docDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
				}
				if (!(errMsg = Validator.checkString(remarks, false, 0, 500)).equals(Validator.ERROR_NONE)) {
					errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
							"500"));
				}
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}