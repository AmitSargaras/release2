package com.integrosys.cms.ui.collateral;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CollateralSearchFormValidator {
	public static String EVENT_REFRESH_EVENT_SEARCH = "refresh_search";

	public static ActionErrors validateInput(CollateralSearchForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		//String event = aForm.getEvent();
		boolean advanceSearch = aForm.getAdvanceSearch();

		DefaultLogger.debug(" In validator of collateral search", "--------->" + errors.size());
		DefaultLogger.debug(" advanceSearch ", "--------->" + advanceSearch);
		DefaultLogger.debug(" AdvanceSearchType ", "--------->" + aForm.getAdvanceSearchType());

		try {
			if (!(advanceSearch)) {
				if (StringUtils.isBlank(aForm.getSecurityId())) {
					errors.add("securityID", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(" ERROR occured , securityID is  mandatory", "");
				} /*else if (StringUtils.isBlank(aForm.getSecuritySearchType())) {
					errors.add("securitySearchType", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(" ERROR occured , securitySearchType is  mandatory", "");					
				}*/ else if (ICMSConstant.SEARCH_TYPE_CMS.equals(aForm.getSecuritySearchType()) &&
						!(errorCode = Validator.checkNumber(aForm.getSecurityId(), false, 0, 99999999999999999d))
						.equals(Validator.ERROR_NONE)) {
					errors.add("securityID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", "99999999999999999"));					
				}
				else {
					char singleQuote = '\'';
					int check = countChars(aForm.getSecurityId(), singleQuote);
					if (check > 0) {
						errors.add("securityID", new ActionMessage("error.source.security.id.format"));
						DefaultLogger.debug(" ERROR occured , Special Characters", "");
					}
				}
			}
			else {
				if (StringUtils.isBlank(aForm.getSecurityType())) {
					errors.add("securityType", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(" ERROR occured , securityType is  mandatory", "");
				}
				if (StringUtils.isBlank(aForm.getSecuritySubType())) {
					errors.add("securitySubType", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(" ERROR occured , securitySubType is  mandatory", "");
				}
				
				if (!StringUtils.isBlank(aForm.getFromExpDate())
						&& !StringUtils.isBlank(aForm.getToExpDate())) {
					Date fromExpDate = DateUtil.convertDate(locale, aForm.getFromExpDate());
					Date toExpDate = DateUtil.convertDate(locale, aForm.getToExpDate());
					int a = fromExpDate.compareTo(toExpDate);
					if (a > 0) {
						errors.add("toExpDate", new ActionMessage("error.date.compareDate", "To Expiry Date ",
								"From Expiry Date"));
					}
				}
				if (!StringUtils.isBlank(aForm.getAaNumber()) &&
						StringUtils.isBlank(aForm.getAaSearchType())) {
					errors.add("aaSearchType", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(" ERROR occured , aaSearchType is  mandatory", "");					
				}

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