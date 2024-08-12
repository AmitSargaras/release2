package com.integrosys.cms.ui.collateral;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;

public class CollateralPrepareCreateFormValidator {
	public static String EVENT_REFRESH_EVENT_SEARCH = "refresh_search";

	public static ActionErrors validateInput(CollateralSearchForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String event = aForm.getEvent();
		boolean advanceSearch = aForm.getAdvanceSearch();

		DefaultLogger.debug(" In validator of collateral search", "--------->" + errors.size());
		DefaultLogger.debug(" advanceSearch ", "--------->" + advanceSearch);
		DefaultLogger.debug(" AdvanceSearchType ", "--------->" + aForm.getAdvanceSearchType());

		try {
				if (AbstractCommonMapper.isEmptyOrNull(aForm.getSecurityType())) {
					errors.add("securityType", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(" ERROR occured , securityType is  mandatory", "");
				}
				if (AbstractCommonMapper.isEmptyOrNull(aForm.getSecuritySubType())) {
					errors.add("securitySubType", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(" ERROR occured , securitySubType is  mandatory", "");
				}

			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
		}
		return errors;

	}

	/**
	 * Count the number of times searchChar occurs in String and return the
	 * result.
	 * @param str
	 * @param searchChar
	 * @return int
	 */

	static int countChars(String str, char searchChar) {
		int i;
		char ch;
		int count = 0;
		for (i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == searchChar) {
				count++;
			}
		}
		return count;
	}

}