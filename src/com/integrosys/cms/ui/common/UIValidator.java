/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/UIValidator.java,v 1.10 2006/05/30 10:19:38 czhou Exp $
 */
package com.integrosys.cms.ui.common;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/05/30 10:19:38 $ Tag: $Name: $
 */
public class UIValidator {

	public static final String AMOUNT_STR = "AMT";

	public static final String CURRENCY_STR = "CCY";

	public static final String ERROR_COMPAREDATE_LATER = "compareDate";

	public static final String ERROR_COMPAREDATE_EARLIER = "compareDate.more";

	public static String checkNumber(String input, boolean check, double min, double max, int decimalPlaces,
			Locale locale) {
		String errorCode = null;
		errorCode = Validator.checkNumber(input, check, min, max, decimalPlaces, locale);

		if ((decimalPlaces > 0) && (errorCode != null) && errorCode.equals(Validator.ERROR_DECIMAL_EXCEEDED)) {
			errorCode = ICMSUIConstant.VALIDATION_NUMBER_EXCEED_ERR;
		}
		return errorCode;
	}

	public static String[] checkAmount(String ccyCode, String amt, boolean check, double min, double max,
			int decimalPlaces, Locale locale) {
		String[] returnError = null;
		String errorCode = null;

		// amount input is mandatory
		if (!(errorCode = checkNumber(amt, check, min, max, decimalPlaces, locale)).equals(Validator.ERROR_NONE)) {
			returnError = new String[2];
			returnError[0] = errorCode;
			returnError[1] = AMOUNT_STR;
			return returnError;
		}

		// amount field is input, currency code must be input
		if ((amt != null) && (amt.length() > 0)) {
			if (!(errorCode = Validator.checkString(ccyCode, true, 0, 3)).equals(Validator.ERROR_NONE)) {
				returnError = new String[2];
				returnError[0] = errorCode;
				returnError[1] = CURRENCY_STR;
				return returnError;
			}
		}

		// currency code is not null, amt must be input
		if (!check && (ccyCode != null) && (ccyCode.length() > 0)) {
			if (!(errorCode = checkNumber(amt, true, min, max, decimalPlaces, locale)).equals(Validator.ERROR_NONE)) {
				returnError = new String[2];
				returnError[0] = errorCode;
				returnError[1] = AMOUNT_STR;
			}
		}

		return returnError;
	}

	public static ActionErrors checkAmount(ActionErrors errors, String fieldCcy, String fieldAmt, String ccyCode,
			String amt, boolean check, double min, double max, int decimalPlaces, Locale locale, String maxStr) {
		String errorCode = null;
		if (!(errorCode = checkNumber(amt, check, min, max, decimalPlaces, locale)).equals(Validator.ERROR_NONE)) {
			errors.add(fieldAmt, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), String
					.valueOf(min), maxStr, String.valueOf(decimalPlaces - 1)));
		}
		else if ((amt != null) && (amt.length() > 0)) {
			if (!(errorCode = Validator.checkString(ccyCode, true, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add(fieldCcy, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						3 + ""));
			}
		}
		if (errorCode.equals(Validator.ERROR_NONE) && !check && (ccyCode != null) && (ccyCode.length() > 0)) {
			if (!(errorCode = checkNumber(amt, true, min, max, decimalPlaces, locale)).equals(Validator.ERROR_NONE)) {
				errors.add(fieldAmt, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), String
						.valueOf(min), maxStr, String.valueOf(decimalPlaces - 1)));
			}
		}
		return errors;
	}

	public static ActionErrors validateFutureDate(ActionErrors errors, String dateStr, String fieldName, String desc,
			Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.before(DateUtil.getDate())) {
				errors.add(fieldName, new ActionMessage("error.collateral.commodity.futuredate", desc));
			}
		}
		return errors;
	}

	public static ActionErrors validatePreviousDate(ActionErrors errors, String dateStr, String fieldName, String desc,
			Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.after(DateUtil.getDate())) {
				errors.add(fieldName, new ActionMessage("error.collateral.commodity.not.futuredate", desc));
			}
		}
		return errors;
	}

	/**
	 * To check whether dateStr is future date - dateStr is after system date
	 * @return boolean: true if dateStr is after system date, else false
	 */
	public static boolean isFutureDate(String dateStr, Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.before(DateUtil.getDate())) {
				return false;
			}
		}
		return true;
	}

	public static boolean isFutureDate(Date dateObj) {
		if (dateObj != null) {
			if (dateObj.before(DateUtil.getDate())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * To check whether dateStr is expiry date - dateStr is before system date
	 * @return boolean: true if dateStr is before system date, else false
	 */
	public static boolean isExpiryDate(String dateStr, Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			return isExpiryDate(dateObj);
		}
		return true;
	}

	public static boolean isExpiryDate(Date dateObj) {
		if (dateObj != null) {
			dateObj = DateUtil.clearTime(dateObj);
			Date currentDate = UIUtil.getDate();
			if (dateObj.equals(currentDate) || dateObj.after(currentDate)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Compare if the 2nd date is later than the 1st date.
	 * 
	 * @param errors ActionErrors
	 * @param dateStr1 1st Date String
	 * @param dateStr2 2nd Date String (to be validated to be later than 1st
	 *        date)
	 * @param fieldNameKey Key of the field that is requesting for this
	 *        validation
	 * @param param1 First parameter for the error message
	 * @param param2 Second parameter for the error message
	 * @param locale1 Locale of 1st Date
	 * @param locale2 Locale of 2nd Date
	 * @return ActionErrors, with error added to it if 2nd date is not later
	 *         than the 1st date
	 */
	public static ActionErrors compareIsLaterDate(ActionErrors errors, String dateStr1, String dateStr2,
			String fieldNameKey, String errorKey, String param1, String param2, Locale locale1, Locale locale2) {
		if ((dateStr1 != null) && (dateStr1.length() > 0) && (dateStr2 != null) && (dateStr2.length() > 0)) {
			Date dateObj1 = convertToDate(locale1, dateStr1);
			Date dateObj2 = convertToDate(locale2, dateStr2);

			if (!(dateObj2.after(dateObj1))) { // do not use
												// "if(dateObj2.before(dateObj1))"
												// 'cos if its the same date, it
												// will return false. It checks
												// for strictly before.
				errors.add(fieldNameKey, new ActionMessage(errorKey, param1, param2));
			}
		}
		return errors;
	}

	public static ActionErrors addAmountErrors(ActionErrors errors, String fieldCcy, String fieldAmt, String[] errObj,
			String minStr, String maxStr) {
		if (errObj != null) {
			if (AMOUNT_STR.equals(errObj[1])) {
				errors.add(fieldAmt, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errObj[0]), minStr,
						maxStr));
			}
			if (CURRENCY_STR.equals(errObj[1])) {
				errors.add(fieldCcy, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errObj[0]), "0",
						3 + ""));
			}
		}

		return errors;
	}

	/**
	 * Compare if date1 is earlier than date2
	 * @param dateStr1 of type String
	 * @param dateStr2 of type String
	 * @return String - validation constant
	 */
	public static String compareDateEarlier(String dateStr1, String dateStr2, Locale locale) {
		Date date1 = convertToDate(locale, dateStr1);
		Date date2 = convertToDate(locale, dateStr2);
		return compareDateEarlier(date1, date2);
	}

	/**
	 * Compare if date1 is earlier than date2
	 * @param date1 of type Date
	 * @param date2 of type Date
	 * @return String - validation constant
	 */
	public static String compareDateEarlier(Date date1, Date date2) {
		if ((date1 != null) && (date2 != null)) {
			if (date1.after(date2)) {
				return ERROR_COMPAREDATE_EARLIER;
			}
		}

		return Validator.ERROR_NONE;
	}

	/**
	 * Compare if date1 is later than date2
	 * @param dateStr1 of type String
	 * @param dateStr2 of type String
	 * @return String - validation constant
	 */
	public static String compareDateLater(String dateStr1, String dateStr2, Locale locale) {
		Date date1 = convertToDate(locale, dateStr1);
		Date date2 = convertToDate(locale, dateStr2);
		return compareDateLater(date1, date2);
	}

	/**
	 * Compare if date1 is later than date2
	 * @param date1 of type Date
	 * @param date2 of type Date
	 * @return String - validation constant
	 */
	public static String compareDateLater(Date date1, Date date2) {
		if ((date1 != null) && (date2 != null)) {
			if (date1.before(date2)) {
				return ERROR_COMPAREDATE_LATER;
			}
		}

		return Validator.ERROR_NONE;
	}

	/**
	 * Helper to convert a date String to date Object
	 * @param locale of type Locale
	 * @param dateStr of type String - Date String
	 * @return Date
	 */
	private static Date convertToDate(Locale locale, String dateStr) {
		if ((dateStr != null) && (dateStr.trim().length() > 0)) {
			return DateUtil.convertDate(locale, dateStr);
		}

		return null;
	}

	/**
	 * Helper to determine if a String is empty. Empty is defined as either null
	 * or "" (after trimming)
	 * 
	 * @param str String
	 * @return true if empty, false otherwise
	 */
	public static boolean isEmpty(String str) {
		if ((str == null) || (str.trim()).equals("")) {
			return true;
		}
		return false;
	}
	
	//added by santosh UBS CR
	public static long calculateDateDiff(String dateStr1, String dateStr2, Locale locale) {
		Date date1 = convertToDate(locale, dateStr1);
		Date date2 = convertToDate(locale, dateStr2);
		return calculateDateDiff(date1, date2);
	}
	public static long calculateDateDiff(Date date1, Date date2) {
		if ((date1 != null) && (date2 != null)) {
			 long diff = date2.getTime() - date1.getTime();
			 return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		}
		return -1;
	}
	//end santosh
	//auto upload CR
	/**
	 * Compare if date1 is equal to date2
	 * @param dateStr1 of type String
	 * @param dateStr2 of type String
	 * @return String - validation constant
	 */
	public static String compareDate(String dateStr1, String dateStr2, Locale locale) {
		Date date1 = convertToDate(locale, dateStr1);
		Date date2 = convertToDate(locale, dateStr2);
		return compareDate(date1, date2);
	}

	/**
	 * Compare if date1 is equal to date2
	 * @param date1 of type Date
	 * @param date2 of type Date
	 * @return String - validation constant
	 */
	public static String compareDate(Date date1, Date date2) {
		if ((date1 != null) && (date2 != null)) {
			if (!date1.equals(date2)) {
				return "compareDate.equal";
			}
		}

		return Validator.ERROR_NONE;
	}
	
	/**
	 * Compare if date1 is greater than todays date
	 * @param date1 of type Date
	 * @param date2 of type Date
	 * @return String - validation constant
	 */
	public static String compareFutureDate(String dateStr1, Locale locale) {
		Date date1 = convertToDate(locale, dateStr1);
		Date date2 = new Date();
		return compareFutureDate(date1, date2);
	}
	/**
	 * Compare if date1 is greater than todays date
	 * @param date1 of type Date
	 * @param date2 of type Date
	 * @return String - validation constant
	 */
	public static String compareFutureDate(Date date1, Date date2) {
		if ((date1 != null) && (date2 != null)) {
			if (date1.after(date2)) {
				return "fromDate.later.currentDate";
			}
		}

		return Validator.ERROR_NONE;
	}
	//auto upload CR
	
	public static String compareFutureDateWithRange(String dateStr1, Locale locale) {

			Date date1 = convertToDate(locale, dateStr1);

			// current date after 6 months
			Calendar currentDateAfter3Months = Calendar.getInstance();
			currentDateAfter3Months.add(Calendar.MONTH, 6);

			// current date before 6 months
//			Calendar currentDateBefore3Months = Calendar.getInstance();
//			currentDateBefore3Months.add(Calendar.MONTH, -6);

			if (date1.before(currentDateAfter3Months.getTime())
					) {

				return Validator.ERROR_NONE;

			} else {

				return "error.report.fromDate.later.currentDate";

			}
}
	
	public static String compareBackDateWithRange(String dateStr1, Locale locale) {

		Date date1 = convertToDate(locale, dateStr1);

		// current date after 6 months
//		Calendar currentDateAfter3Months = Calendar.getInstance();
//		currentDateAfter3Months.add(Calendar.MONTH, 6);

		// current date before 6 months
		Calendar currentDateBefore3Months = Calendar.getInstance();
		currentDateBefore3Months.add(Calendar.MONTH, -6);

		if (date1.after(currentDateBefore3Months.getTime())) {

			return Validator.ERROR_NONE;

		} else {

			return "error.report.fromDate.before.currentDate";

		}
}
	
	public static String compareDateWithRange(String fromDate, String toDate, Locale locale) {

//		Date date1 = convertToDate(locale, fromDate);

		// current date after 6 months
		Calendar currentDateAfter3Months = Calendar.getInstance();
		currentDateAfter3Months.add(Calendar.MONTH, 6);

		// current date before 6 months
//		Calendar currentDateBefore3Months = Calendar.getInstance();
//		currentDateBefore3Months.add(Calendar.MONTH, -6);
		
		
		
		Calendar date1 = Calendar.getInstance();
		date1.setTime(DateUtil.convertDate(locale, fromDate));
		date1.add(Calendar.MONTH, 6);
		Calendar date2 = Calendar.getInstance();
		//Calendar date3 = Calendar.getInstance();
		date2.setTime(DateUtil.convertDate(locale, toDate));
		//date3.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
//		date3.add(Calendar.DATE,1);
//		date2.add(Calendar.YEAR,2);
//		date2.add(Calendar.DATE,-1);
//		if(date1.after(date2)) {

		if (date2.before(date1)
				) {

			return Validator.ERROR_NONE;

		} else {

			return "error.report.fromDate.difference";

		}
}
	
	
	public static String compareDateWithRangeSevenDays(String fromDate, String toDate, Locale locale) {

//		Date date1 = convertToDate(locale, fromDate);

		// current date after 6 months
		Calendar currentDateAfter7Days = Calendar.getInstance();
		currentDateAfter7Days.add(Calendar.DATE, 7);

		// current date before 6 months
//		Calendar currentDateBefore3Months = Calendar.getInstance();
//		currentDateBefore3Months.add(Calendar.MONTH, -6);
		
		
		
		Calendar date1 = Calendar.getInstance();
		date1.setTime(DateUtil.convertDate(locale, fromDate));
		date1.add(Calendar.DATE, 7);
		Calendar date2 = Calendar.getInstance();
		//Calendar date3 = Calendar.getInstance();
		date2.setTime(DateUtil.convertDate(locale, toDate));
		//date3.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
//		date3.add(Calendar.DATE,1);
//		date2.add(Calendar.YEAR,2);
//		date2.add(Calendar.DATE,-1);
//		if(date1.after(date2)) {

		if (date2.before(date1)
				) {

			return Validator.ERROR_NONE;

		} else {

			return "error.report.fromDate.difference";

		}
}
	
	

}
