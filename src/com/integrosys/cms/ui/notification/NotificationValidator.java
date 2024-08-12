/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/notification/NotificationValidator.java,v 1.3 2005/10/18 06:56:04 lini Exp $
 */
package com.integrosys.cms.ui.notification;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/10/18 06:56:04 $ Tag: $Name: $
 */
public class NotificationValidator {

	public static ActionErrors validateInput(NotificationForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();

		final String event = form.getEvent();

		if (event.equals(NotificationAction.EVENT_DELETE_NOTIFICATION)) {

			try {
				String[] notificationIDs = form.getNotificationIDs();
				if ((notificationIDs == null) || (notificationIDs.length == 0)) {
					DefaultLogger.debug("com.integrosys.cms.ui.notification.NotificationValidator",
							"no notification selected");
					errors.add("deleteNotificationError", new ActionMessage("error.delete.notification"));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		// skip validation if not entered
		if (isNull(form.getSearchLegalName()) && isNull(form.getSearchLeID())) {
			return errors;
		}
		String errorCode;
		errorCode = Validator.checkNumber(form.getSearchLeID(), false, 0, 9999999999l, 0, locale);
		if (!Validator.ERROR_NONE.equals(errorCode)) {
			errors.add("leIDError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"9999999999"));
		}
		if (!(errorCode = Validator.checkString(form.getSearchLegalName(), false, 1, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("legalNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					"40"));
		}
		return errors;
	}

	public static boolean isNull(String s) {
		return ((s == null) || (s.trim().length() == 0));
	}

}
