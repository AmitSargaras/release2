/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ShareCounterValidator
 *
 * Created on 9:51:23 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.creditriskparam.sharecounter;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 16, 2007 Time: 9:51:23 AM
 */
public class ShareCounterValidator {
	public static String DEBUG_CLASS = ShareCounterValidator.class.getName();

	private ShareCounterValidator() {

	}

	public static ActionErrors validate(ActionForm actionForm, Locale locale) {
		DefaultLogger.debug(DEBUG_CLASS, "Now validating form");

		ActionErrors errors = new ActionErrors();

		ShareCounterForm form = (ShareCounterForm) actionForm;

		String shareStatus[] = form.getParamShareStatus();
		String boardType[] = form.getParamBoardType();
		String marginOfAdvance[] = form.getMarginOfAdvance();

		String error1 = "shareStatus";
		String error2 = "boardType";
		String error3 = "marginOfAdvance";
		
		String errorCode = null;
		if (ShareCounterConstants.SHARE_COUNTER_MAKER_TYPE_SELECTED.equals(form.getEvent())
				||ShareCounterConstants.SHARE_COUNTER_VIEW.equals(form.getEvent())) {
			if (!(errorCode = Validator.checkString(form.getStockExchange(), true, 1, 20))
					.equals(Validator.ERROR_NONE)) {
				errors.add(ShareCounterConstants.GROUP_SUBTYPE, new ActionMessage("error.string.mandatory", "1", "20"));				
			}
			if (!(errorCode = Validator.checkString(form.getStockType(), true, 1, 20))
					.equals(Validator.ERROR_NONE)) {
				errors.add(ShareCounterConstants.GROUP_STOCK_TYPE, new ActionMessage("error.string.mandatory", "1", "20"));				
			}
		}
		if ((shareStatus != null) && (boardType != null)) {
			for (int loop = 0; loop < shareStatus.length; loop++) {
				if ((shareStatus[loop] == null) || shareStatus[loop].trim().equals("")) {
					DefaultLogger.debug(DEBUG_CLASS, "shareStatus[ " + loop + " ] : " + shareStatus[loop]);

					errors.add(error1 + loop, new ActionMessage("error.feeds.no.selection"));
				}

				if ((boardType[loop] == null) || boardType[loop].trim().equals("")) {
					DefaultLogger.debug(DEBUG_CLASS, "boardType[ " + loop + " ] : " + boardType[loop]);

					errors.add(error2 + loop, new ActionMessage("error.feeds.no.selection"));
				}

				if ((boardType[loop] == null) || boardType[loop].trim().equals("")) {
					DefaultLogger.debug(DEBUG_CLASS, "boardType[ " + loop + " ] : " + boardType[loop]);

					errors.add(error2 + loop, new ActionMessage("error.feeds.no.selection"));
				}

				if ((marginOfAdvance != null) && (marginOfAdvance[loop] != null)) {
					if (!Validator.validateNumber(marginOfAdvance[loop], false, 0, 100)) {

						DefaultLogger.debug(DEBUG_CLASS, "marginOfAdvance[ " + loop + " ] : " + marginOfAdvance[loop]);
						errors.add(error3 + loop, new ActionMessage("error.feeds.moa.invalid"));
					}
				}
			}
		}



		DefaultLogger.debug(DEBUG_CLASS, "Number of errors : " + errors.size());

		return errors;
	}

}
