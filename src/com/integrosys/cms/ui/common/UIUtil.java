/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/UIUtil.java,v 1.33 2006/09/06 10:01:29 czhou Exp $
 */
package com.integrosys.cms.ui.common;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.locale.converters.BigDecimalLocaleConverter;
import org.apache.commons.beanutils.locale.converters.DoubleLocaleConverter;
import org.apache.commons.beanutils.locale.converters.IntegerLocaleConverter;
import org.apache.commons.beanutils.locale.converters.LongLocaleConverter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyFormatter;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.converter.EnhancedStringLocaleConverter;
import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.businfra.LabelValue;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.base.techinfra.converter.EnhancedStringLocaleConverter;

public class UIUtil {
	private static String LOGOBJ = "UIUtil";

	private static final BigDecimal MAX_ROUNDED_DEC = new BigDecimal("1");

	public static String FOREX_ERROR = "Forex Error";
	
	public static final String defaultDateFormat = "dd/MMM/yyyy";
	
	
public static String convertToOrdinal(int i){
		
		int k=i%100;
		if(k>=11 && k<=13){
			return i+"th";
		}
		
		
		int val=i%10;
		
		if(val ==1){
			return i+"st";
		}
		
		if(val ==2){
			return i+"nd";
		}
		
		
		if(val ==3){
			return i+"rd";
		}
		
		
		 return i+"th";
		
	}

	public static String formatBigDecimalToStr(BigDecimal aValue) {
		String aNumStr = "";

		if (aValue == null) {
			return aNumStr;
		}

		Method m;

		try {
			m = BigDecimal.class.getMethod("toPlainString", (Class[])null);

		}
		catch (NoSuchMethodException e) {
			m = null;
		}

		if (m == null) {
			aNumStr = aValue.toString();
		}
		else {
			try {
				aNumStr = (String) m.invoke(aValue, (Object[])null);
			}
			catch (Exception e) {
			}
		}
		return aNumStr;
	}

	public static String numberToWordsIndia(String number) {

		NumToWordsIndia converter = new NumToWordsIndia(number);

		String result =  " Rupees "+converter.getWords(converter.rupee, null, false);
		if (converter.paise != null)
			result = result + " and "
					+ converter.getWords(converter.paise, null, false)
					+ " Paise";

		return result;
	}
	
	public static String numberToWords(String number) {

		NumToWordsIndia converter = new NumToWordsIndia(number);

		String result =  converter.getWords(converter.rupee, null, false);
//		if (converter.paise != null)
//			result = result + " and "
//					+ converter.getWords(converter.paise, null, false)
//					+ " Paise";

		return result;
	}


	/**
	 * Format the big decimal with or without currency to String representation
	 * of the Locale. This method will handle the integral part of the
	 * BigDecimal aValue as long primitive type and the decimal part of it as
	 * double primitive type. If the integral and decimal part size is more than
	 * the respective type, the result will lose some precision or even wrong
	 * value.
	 * 
	 * @param ccyCode currency code
	 * @param aValue a number represented in BigDecimal object
	 * @param decPlaces decimal places required to format the aValue
	 * @param locale client locale
	 * @return formatted aValue in String representation
	 * @throws Exception on any errors
	 */
	public static String formatAmount(String ccyCode, BigDecimal aValue, int decPlaces, Locale locale) throws Exception {
		if (aValue == null) {
			return null;
		}
		NumberFormat ff = null;
		StringBuffer formattedStr = new StringBuffer();

		if ((ccyCode == null) || (ccyCode.length() == 0)) {
			ff = NumberFormat.getInstance(locale);
		}
		else {
			CurrencyFormatter cf = CurrencyManager.getCurrencyFormatter(ccyCode);
			ff = cf.getDisplayFormat(locale);
			// append the currency
			formattedStr.append(cf.getDisplayCode(locale)).append(" ");
		}

		String aNumStr = formatBigDecimalToStr(aValue);

		// System.out.println("1.formatAmount,displayAmount="+aNumStr);

		int decPtIdx = aNumStr.indexOf(".");
		String anIntStr = null, aDecStr = null;

		if (decPtIdx == -1) {
			anIntStr = new String(aNumStr);
		}
		else {
			anIntStr = aNumStr.substring(0, decPtIdx);
			aDecStr = aNumStr.substring(decPtIdx);
		}

		 //System.out.println("1.formatAmount,anIntStr="+anIntStr);
		 //System.out.println("1.formatAmount,aDecStr="+aDecStr);
		BigDecimal intBD = new BigDecimal(anIntStr);

		BigDecimal decBD = new BigDecimal(.0d);
		if (aDecStr != null) {
			decBD = new BigDecimal(aDecStr);
			decBD = roundAmount(decBD, decPlaces);
			if (decBD.compareTo(MAX_ROUNDED_DEC) == 0) {
				if (intBD.signum() < 0) {
					intBD = intBD.subtract(MAX_ROUNDED_DEC);
				}
				else {
					intBD = intBD.add(MAX_ROUNDED_DEC);
				}
			}
		}
		// System.out.println("2.formatAmount,intBD="+intBD);

		// append the integral value of the amount
		String formatStr = ff.format(intBD.longValue());

		// System.out.println("3.formatAmount,intBD.longValue()="+intBD.longValue
		// ());
		// System.out.println("4.formatAmount,formatStr="+formatStr);

		decPtIdx = formatStr.indexOf(".");

		if (decPtIdx >= 0) {
			formatStr = formatStr.substring(0, decPtIdx);
		}
		formattedStr.append(formatStr);
		// formattedStr.append ( ff.format(intBD.longValue()) );

		if ((decBD != null) && !decBD.equals(MAX_ROUNDED_DEC)) {
			ff.setMaximumFractionDigits(decPlaces);
			if ((decPlaces > 0) && (decPlaces != 4)) { // todo: exclude 4 dec
				// for quantity. The
				// clean way is to add
				// another method!!!
				ff.setMinimumFractionDigits(ICMSUIConstant.PRICE_DEC_PLACES);
			}
			// append the decimal value of the amount
			formattedStr.append((ff.format(decBD.doubleValue())).substring(1));
		}

		//special handling for values that are between -0.xx to -1 since anIntStr=-0 will result in intBD=0 
		if(aValue.doubleValue() < 0 && aValue.doubleValue() > -1) { 
			formattedStr.insert(0, "-"); 
		}
		
		return formattedStr.toString();
	}

	/**
	 * Helper method to round the BigDecimal value using
	 * BigDecimal.ROUND_HALF_UP.
	 * 
	 * @param aNum of type BigDecimal
	 * @param scale of type int
	 * @return rounded number
	 */
	private static BigDecimal roundAmount(BigDecimal aNum, int scale) {
		// add 0.0000000000001 because we want 0.4999999999999 to be rounded as
		// 1.
		BigDecimal bd = aNum;
		if (scale == 0) {
			bd = aNum.add(new BigDecimal("0.0000000000001"));
		}
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return bd;
	}

	/**
	 * Format the amount object in String representation in the format of [CCY+
	 * " " + Amount].
	 * 
	 * @param amt of type Amount
	 * @param decPlaces the number of decimal places required in formatting
	 * @param locale caller locale
	 * @return formated amount
	 * @throws Exception on any errors
	 */
	public static String formatAmount(Amount amt, int decPlaces, Locale locale) throws Exception {
		if (amt == null) {
			return null;
		}
		return formatAmount(amt.getCurrencyCode(), amt.getAmountAsBigDecimal(), decPlaces, locale);
	}

	/**
	 * Format the amount object in String representation in the format of [CCY+
	 * " " + Amount] depending on parameter withCCY.
	 * 
	 * @param amt of type Amount
	 * @param decPlaces the number of decimal places required in formatting
	 * @param locale caller locale
	 * @param withCCY the indicator with display currency code if true, else
	 *        without currency code
	 * @return formated amount
	 * @throws Exception on any errors
	 */
	public static String formatAmount(Amount amt, int decPlaces, Locale locale, boolean withCCY) throws Exception {
		if (amt == null) {
			return null;
		}
		if (withCCY) {
			return formatAmount(amt.getCurrencyCode(), amt.getAmountAsBigDecimal(), decPlaces, locale);
		}

		return formatAmount(null, amt.getAmountAsBigDecimal(), decPlaces, locale);
	}

	/**
	 * Format the amount object in String representation in the format of [CCY+
	 * " " + Amount] depending on parameter withCCY.
	 * 
	 * @param amt of type Amount
	 * @param decPlaces the number of decimal places required in formatting
	 * @param locale caller locale
	 * @param withCCY the indicator with display currency code if true, else
	 *        without currency code
	 * @return formated amount
	 * @throws Exception on any errors
	 */
	public static String formatAmountPositiveVal(Amount amt, int decPlaces, Locale locale, boolean withCCY)
			throws Exception {
		if (amt == null) {
			return null;
		}
		if (withCCY) {
			return formatAmountPositiveVal(amt.getCurrencyCode(), amt.getAmountAsBigDecimal(), decPlaces, locale);
		}

		return formatAmountPositiveVal(null, amt.getAmountAsBigDecimal(), decPlaces, locale);
	}

	/**
	 * Format the amount object in String representation in the format of [CCY+
	 * " " + Amount] depending on parameter withCCY.
	 * 
	 * @param amt of type Amount
	 * @param decPlaces the number of decimal places required in formatting
	 * @param locale caller locale
	 * @param withCCY the indicator with display currency code if true, else
	 *        without currency code
	 * @return formated amount
	 * @throws Exception on any errors
	 */
	public static String formatAmountPositiveVal(String ccyCode, BigDecimal aValue, int decPlaces, Locale locale)
			throws Exception {
		if (aValue == null) {
			return null;
		}

		if (aValue.signum() < 0) {
			return "-";
		}

		return formatAmount(ccyCode, aValue, decPlaces, locale);
	}

	/**
	 * Format the big decimal to String representation of the Locale. This
	 * method will handle the integral part of the BigDecimal aValue as long
	 * primitive type and the decimal part of it as double primitive type. If
	 * the integral and decimal part size is more than the respective type, the
	 * result will lose some precision or even wrong value.
	 * 
	 * @param aValue a number represented in BigDecimal object
	 * @param decPlaces decimal places required to format the aValue
	 * @param locale client locale
	 * @return formatted aValue in String representation
	 * @throws Exception on any errors
	 */
	public static String formatNumber(BigDecimal aValue, int decPlaces, Locale locale) throws Exception {
		return formatAmount(null, aValue, decPlaces, locale);
	}

	/**
	 * Format the big decimal to String representation of the Locale. This
	 * method will handle the integral part of the BigDecimal aValue as long
	 * primitive type and the decimal part of it as double primitive type. If
	 * the integral and decimal part size is more than the respective type, the
	 * result will lose some precision or even wrong value.
	 * 
	 * @param aValue a number represented in BigDecimal object
	 * @param decPlaces decimal places required to format the aValue
	 * @param locale client locale
	 * @return formatted aValue in String representation
	 * @throws Exception on any errors
	 */
	public static String formatNumberPositiveVal(BigDecimal aValue, int decPlaces, Locale locale) throws Exception {
		return formatAmountPositiveVal(null, aValue, decPlaces, locale);
	}

	/**
	 * Maps a String to double. Spaces and ','s are accepted.
	 */
	public static BigDecimal mapStringToBigDecimal(String aString) {
		BigDecimal bd = null;
		if (aString != null) {
			String ss = removeCharFromString(aString, ' '); // remove spaces
			ss = removeCommas(ss); // remove commas
			bd = (ss.length() == 0) ? null : new BigDecimal(ss);
		}
		return bd;
	}

	/**
	 * Return an Amount object given a Currency Code string and Amount value
	 * String
	 * 
	 * @param aLocale locale of currency entry format (different locale has
	 *        differenct meaning for '.', ',', and spaces.
	 * @param currencyCode the ISO currency code as String
	 * @param amount the scalar amount (without the currency code) in string
	 *        representation
	 * @return Amount
	 */
	public static Amount convertToAmount(Locale aLocale, String currencyCode, String amount) {
		try {
			return convertToAmount(aLocale, currencyCode, amount, true);
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * Return an Amount object given a Currency Code string and Amount value
	 * String
	 * 
	 * @param aLocale locale of currency entry format (different locale has
	 *        differenct meaning for '.', ',', and spaces.
	 * @param currencyCode the ISO currency code as String
	 * @param amount the scalar amount (without the currency code) in string
	 *        representation
	 * @return Amount
	 */
	public static Amount convertToAmount(Locale aLocale, String currencyCode, String amount, boolean byBigDecimal)
			throws Exception {
		Amount returnAmt = null;
		if ((null == amount) || amount.trim().equals("") || (currencyCode == null) || currencyCode.trim().equals("")) {
			return null;
		}
		else {
			if (byBigDecimal) {
				BigDecimal num = mapStringToBigDecimal(amount);
				returnAmt = new Amount(num, new CurrencyCode(currencyCode));
			}
			else {
				returnAmt = CurrencyManager.convertToAmount(aLocale, currencyCode, amount);
			}
		}
		return returnAmt;
	}

	/**
	 * removes given character from the given string.
	 * 
	 * @param input String from which the given character to be removed.
	 * @param removeChar character to be removed
	 * @return String after removing the given character.
	 */
	private static String removeCharFromString(String input, char removeChar) {
		String output = null;

		if (input != null) {
			StringBuffer buffer = new StringBuffer("");
			char[] allChars = input.toCharArray();
			for (int i = 0; i < allChars.length; i++) {
				if (allChars[i] != removeChar) {
					buffer.append(allChars[i]);
				}
			}
			output = buffer.toString();
		}

		return output;
	}

	// following method not published to public for the time being

	/**
	 * removes comma ',' characters from the given string.
	 * 
	 * @param input String from which "," is to be removed.
	 * @return String without commas
	 */
	private static String removeCommas(String input) {
		return removeCharFromString(input, ',');
	}

	/**
	 * Compare two date, if date change, return dateStage. If both dates are
	 * same, use dateOrigin
	 * 
	 * @param locale Locale
	 * @param dateOrigin original date
	 * @param dateStage staging date
	 * @return Date If date are changed, return dateStage, else dateOrigin
	 */
	public static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}

		return returnDate;
	}

	/**
	 * Return the later of the 2 dates passed in.
	 * 
	 * @param date1
	 * @param date2
	 * @return Date - the later date
	 */
	public static Date getLaterDate(Date date1, Date date2) {
		if ((date1 == null) && (date2 == null)) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.compareTo(date2) >= 0) {
			return date1;
		}
		return date2;
	}

	public static boolean isAmountUnChanged(Amount newAmt, Amount oldAmt) {
		return isAmountUnChanged(newAmt, oldAmt, false);
	}

	public static boolean isAmountUnChanged(Amount newAmt, Amount oldAmt, boolean checkCurrencyCode) {
		boolean unchanged = true;
		if ((newAmt == null) && (oldAmt != null)) { // for case delete staging
			// record
			unchanged = false;
		}
		else if ((newAmt != null) && (oldAmt == null)) { // for case create new
			// record
			if (newAmt.getAmount() != 0) {
				unchanged = false;
			}
		}
		else if ((newAmt != null) && (oldAmt != null)) { // for case update
			// record
			try {
				unchanged = CompareOBUtil.compOB(newAmt, oldAmt, "amount");
				if (unchanged && checkCurrencyCode) {
					unchanged = CompareOBUtil.compOB(newAmt, oldAmt, "currencyCode");
				}
			}
			catch (Exception e) {
				DefaultLogger.error("UIUtil.isAmountChange", "Error at CompareOBUtil", e);
			}
		}
		return unchanged;
	}

	/**
	 * Get the frequency code of the frequency unit in String
	 * 
	 * @param freqUnit String
	 */
	public static int getFreqCode(String freqUnit) {
		if (freqUnit != null) {
			if (freqUnit.equals(ICMSConstant.TIME_FREQ_DAY) || freqUnit.equals(ICMSConstant.FREQ_UNIT_DAYS)) {
				return Calendar.DAY_OF_MONTH;
			}
			else if (freqUnit.equals(ICMSConstant.TIME_FREQ_WEEK) || freqUnit.equals(ICMSConstant.FREQ_UNIT_WEEKS)) {
				return Calendar.WEEK_OF_MONTH;
			}
			else if (freqUnit.equals(ICMSConstant.TIME_FREQ_MONTH) || freqUnit.equals(ICMSConstant.FREQ_UNIT_MONTHS)) {
				return Calendar.MONTH;
			}
			else if (freqUnit.equals(ICMSConstant.TIME_FREQ_YEAR) || freqUnit.equals(ICMSConstant.FREQ_UNIT_YEARS)) {
				return Calendar.YEAR;
			}
		}
		return 0;
	}

	/**
	 * Calculate the date by frequency number and frequency code
	 * 
	 * @param freqNum int
	 * @param freqCode int
	 * @param originDate Date return type Date
	 */
	public static Date calculateDate(int freqNum, int freqCode, Date originDate) {
		if ((originDate != null) && (freqNum > 0) && (freqCode > 0)) {
			GregorianCalendar mcal = new GregorianCalendar();
			mcal.setTime(originDate);
			mcal.add(freqCode, freqNum);
			originDate = mcal.getTime();
		}
		return originDate;
	}

	/**
	 * Calculate the date by frequency number and frequency code
	 * 
	 * @param freqNum int
	 * @param freqUnit String
	 * @param originDate Date return type Date
	 */
	public static Date calculateDate(int freqNum, String freqUnit, Date originDate) {
		int freqCode = getFreqCode(freqUnit);

		return calculateDate(freqNum, freqCode, originDate);
	}

	/**
	 * Convert amount to another currency code value
	 * 
	 * @param amt Amount
	 * @param ccy CurrencyCode
	 * @param locale Locale return type String
	 */
	public static String getConvertAmountStr(Amount amt, CurrencyCode ccy, Locale locale) {
		String returnAmt = null;
		ForexHelper fr = new ForexHelper();
		if ((amt != null) && (amt.getCurrencyCode() != null) && (ccy != null)) {
			try {
				double convertValue = fr.convertAmount(amt, ccy);
				returnAmt = MapperUtil.mapDoubleToString(convertValue, 0, locale);
			}
			catch (Exception e) {
				DefaultLogger.error("UIUtil.getConvertAmountStr", "ForexException Throw", e);
				returnAmt = FOREX_ERROR;
			}
		}
		return returnAmt;
	}

	public static String mapAmountToString(Amount amt, Locale locale, boolean withCurrency) throws Exception {
		String returnStr = "";
		if (amt != null) {
			if (withCurrency) {
				returnStr = amt.getCurrencyCode() + " ";
			}
			if (amt.getCurrencyCode() != null) {
				returnStr += CurrencyManager.convertToString(locale, amt);
			}
		}
		return returnStr;
	}

	public static Date convertDate(Locale locale, String dateStr) {
		Date returnDate = DateUtil.convertDate(locale, dateStr);
		if (returnDate != null) {
			returnDate = DateUtil.clearTime(returnDate);
		}
		return returnDate;
	}

	public static Date getDate() {
		Date returnDate = DateUtil.getDate();
		returnDate = DateUtil.clearTime(returnDate);
		return returnDate;
	}

	public static String convertRTFWrap(String inStr) {
		String outStr = null;
		if (inStr != null) {

			StringBuffer buffer = new StringBuffer();
			char[] allChars = inStr.toCharArray();
			int subStringIndex = 0;
			for (int x = 0; x < allChars.length; x++) {
				char aChar = allChars[x];
				if (aChar != '\r') {
					if (aChar == '\n') {
						String temp = inStr.substring(subStringIndex, x);
						subStringIndex = x;
						temp = temp + "\\par";
						buffer.append(temp);
					}
				}
			}
			buffer.append(inStr.substring(subStringIndex));
			outStr = buffer.toString();
		}

		return outStr;
	}

	/**
	 * This method is to help to check whether the user is in Country by
	 * ILimitProfile
	 */
	public static boolean isInCountry(IContext anIContext, ILimitProfile anILimitProfile) {
		ITeam team = anIContext.getTeam();
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}
		String bkgLocation = anILimitProfile.getOriginatingLocation().getCountryCode();
		for (int ii = 0; ii < countryList.length; ii++) {
			if (countryList[ii].equals(bkgLocation)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is to help to check whether the user is in Country by
	 * ICMSCustomer
	 */
	public static boolean isInCountry(IContext anIContext, ICMSCustomer customer) {
		ITeam team = anIContext.getTeam();
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}
		String bkgLocation = customer.getOriginatingLocation().getCountryCode();
		for (int ii = 0; ii < countryList.length; ii++) {
			if (countryList[ii].equals(bkgLocation)) {
				return true;
			}
		}
		return false;
	}

	public static String trimDecimal(String value) {
		int endIndex = value.indexOf(".");
		if (endIndex == -1) {
			return value;
		}

		return value.substring(0, endIndex);
	}

	/**
	 * Format a Date to String in format dd/MM/yyyy
	 * 
	 * @param date a Date
	 * @return formatted Date in String dd/MM/yyyy
	 */
	public static String formatDate(Date date) {
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
			return sdf2.format(date);
		}
		catch (Exception ex) {
		}
		return "";
	}


	public static String dateToString(Date date, String format) {
		if(date==null)
			return null;
		format = StringUtils.isEmpty(format) ? defaultDateFormat : format ;
		SimpleDateFormat sdf2 = new SimpleDateFormat(format);
		return sdf2.format(date);
	}
	
	/**
	 * @param value
	 * @return String
	 */

	public static String convertBooleanToStr(boolean value) {
		if (value) {
			return ICMSConstant.TRUE_VALUE;
		}
		return ICMSConstant.FALSE_VALUE;
	}

	/**
	 * @param input
	 * @return String
	 */
	public static String replaceSpecialCharForXml(String input) {
		Map toReplace = new HashMap();
		toReplace.put("<", "&lt");
		toReplace.put(">", "&gt");
		toReplace.put("&", "and ");
		toReplace.put("\"", "&quot");
		toReplace.put("\'", "&apos");
		String temp = input;

		int index = 0;
		while (index < temp.length()) {
			String curChar = temp.substring(index, index + 1);
			if (toReplace.containsKey(curChar)) {
				String afterReplace = (String) (toReplace.get(curChar));
				temp = temp.substring(0, index) + afterReplace + temp.substring(index + 1);
				index = index + afterReplace.length();
			}
			else {
				index = index + 1;
			}
		}
		return temp;
	}

	/**
	 * @return List
	 */
	public static List getSourceSystemCountryList() {
		List lbValList = new ArrayList();
		List idList = (List) (CountryList.getInstance().getCountryValues());
		List valList = (List) (CountryList.getInstance().getCountryLabels());
		for (int i = 0; i < idList.size(); i++) {
			String id = idList.get(i).toString();
			String val = valList.get(i).toString();
			LabelValueBean lvBean = new LabelValueBean(val, id);
			lbValList.add(lvBean);
		}
		return lbValList;
	}

	/**
	 * @param country
	 * @return String
	 */
	public static List getSourceSystemAccountNameList(String country) {
		List lbValList = new ArrayList();
		if ((country != null) && !country.trim().equals("")) {
			HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_SOURCE_SYSTEM, null,
					country);
			Object[] keyArr = map.keySet().toArray();
			for (int i = 0; i < keyArr.length; i++) {
				Object nextKey = keyArr[i];
				LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
				lbValList.add(lvBean);
			}
		}
		return lbValList;
	}

	/**
	 * mapOBInteger_FormString
	 * 
	 * @param input
	 * @return String
	 */

	public static String mapOBString_FormString(String input) {
		String returnValue = "";
		if (!AbstractCommonMapper.isEmptyOrNull(input)) {
			returnValue = input.trim();
		}
		return returnValue;
	}

	/**
	 * mapOBInteger_FormString
	 * 
	 * @param input
	 * @return String
	 */

	public static String mapOBInteger_FormString(int input) {
		String returnValue = "";
		if (input >= 0) {
			returnValue = String.valueOf(input);
		}
		return returnValue;
	}

	/**
	 * mapOBInteger_FormString
	 * 
	 * @param input
	 * @return String
	 */

	public static String mapIntegerToString(int input) {
		String returnValue = "";
		if (input >= 0) {
			returnValue = String.valueOf(input);
		}
		return returnValue;
	}

	/**
	 * mapOBAmount_FormString
	 * 
	 * @param locale
	 * @param input
	 * @return String
	 */
	public static String mapOBAmount_FormString(Locale locale, Amount input) {

		String returnValue = "";
		try {
			if ((input != null) && (input.getAmount() >= 0) && (input.getCurrencyCode() != null)) {
				returnValue = CurrencyManager.convertToString(locale, input);
			}
		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapOBAmount_FormString", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * mapOBAmount_FormString
	 * 
	 * @param locale
	 * @param input
	 * @return String
	 */
	public static String mapAmountToString(Locale locale, Amount input) {

		String returnValue = "";
		try {
			if ((input != null) && (input.getAmount() >= 0) && (input.getCurrencyCode() != null)) {
				returnValue = CurrencyManager.convertToString(locale, input);
			}
		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapOBAmount_FormString", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * mapOBDouble_FormString
	 * 
	 * @param input
	 * @return String
	 */
	public static String mapOBDouble_FormString(Double input) {

		String returnValue = "";
		try {
			if (input != null) {
				returnValue = String.valueOf(input);
			}
		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapOBDouble_FormString", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * mapOBDate_FormString
	 * 
	 * @param local
	 * @param input
	 * @return String
	 */
	public static String mapOBDate_FormString(Locale local, Date input) {

		String returnValue = "";
		try {
			if (input != null) {
				returnValue = DateUtil.formatDate(local, input);
			}
		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapOBDate_FormString", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * mapOBDate_FormString
	 * 
	 * @param input
	 * @return String
	 */
	public static int mapFormString_OBInteger(String input) {

		int returnValue = 0;
		try {
			if (AbstractCommonMapper.isEmptyOrNull(input)) {
				returnValue = 0;
			}
			else {
				returnValue = Integer.parseInt(input.trim());
			}
		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapFormString_OBInteger", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * mapOBDate_FormString
	 * 
	 * @param input
	 * @return String
	 */
	public static int mapStringToInteger(String input) {

		int returnValue = -1;
		try {
			if (!AbstractCommonMapper.isEmptyOrNull(input)) {
				returnValue = Integer.parseInt(input.trim());
			}
		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapFormString_OBInteger", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * mapFormString_OBAmount
	 * 
	 * @param formInput
	 * @param objAmount
	 * @param currencyCode
	 * @param locale
	 * @return Amount
	 */
	public static Amount mapFormString_OBAmount(Locale locale, String currencyCode, String formInput, Amount objAmount) {

		Amount returnValue = null;
		try {
			if (formInput.equals("") && (objAmount != null) && (objAmount.getAmount() > 0)) {
				returnValue = CurrencyManager.convertToAmount(locale, currencyCode, "0");
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(formInput)) {
				returnValue = CurrencyManager.convertToAmount(locale, currencyCode, formInput);
			}

		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapFormString_OBAmount", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * mapFormString_OBAmount
	 * 
	 * @param formInput
	 * @param currencyCode
	 * @param locale
	 * @return Amount
	 */
	public static Amount mapFormString_OBAmount1(Locale locale, String currencyCode, String formInput, Amount objAmount) {

		Amount returnValue = null;
		try {
			if (!AbstractCommonMapper.isEmptyOrNull(formInput)) {
				returnValue = CurrencyManager.convertToAmount(locale, currencyCode, formInput);
			}
			else {
				// returnValue = CurrencyManager.convertToAmount(locale,
				// currencyCode,
				// String.valueOf(ICMSConstant.LONG_INVALID_VALUE));
			}

		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapFormString_OBAmount", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * convert form input in decimal value format to Amount object, if the input
	 * is <b>empty string or blank</b>, <b>null</b> will be returned.
	 * 
	 * @param locale locale of the client
	 * @param currencyCode the currency code for the amount.
	 * @param formInput the input of the form, normally by the user
	 * @param objAmount amount object <b>not in used</b>, can be null
	 * @return Amount object represent the decimal value user input,
	 *         <code>null</code> will be the result if user input nothing.
	 */
	public static Amount mapStringToAmount(Locale locale, String currencyCode, String formInput, Amount objAmount) {

		Amount returnValue = null;
		try {
			if (!AbstractCommonMapper.isEmptyOrNull(formInput)) {
				returnValue = CurrencyManager.convertToAmount(locale, currencyCode, formInput);
			}
		}
		catch (Exception e) {
			DefaultLogger.warn("UIUtil.mapStringToAmount", "failed to convert [" + formInput
					+ "] to Amount object, return 'null'.", e);
		}

		return returnValue;
	}

	/**
	 * mapFormString_OBDouble
	 * 
	 * @param formInput
	 * @return Double
	 */
	public static Double mapFormString_OBDouble(String formInput) {

		Double returnValue = null;
		try {
			if (("").equals(formInput)) {
				returnValue = Double.valueOf(String.valueOf(ICMSConstant.LONG_INVALID_VALUE));
			}
			else {
				returnValue = Double.valueOf(formInput);
			}

		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapFormString_OBDouble", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * mapFormString_OBDouble
	 * 
	 * @param dateOrigin
	 * @return Double
	 */
	public static Date mapFormString_OBDate(Locale locale, Date dateOrigin, String dateStage) {

		Date returnValue = null;
		try {
			if (!AbstractCommonMapper.isEmptyOrNull(dateStage)) {
				returnValue = CollateralMapper.compareDate(locale, dateOrigin, dateStage);
			}
		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapFormString_OBDate", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * mapFormString_OBDouble
	 * 
	 * @param dateOrigin
	 * @return Double
	 */
	public static Date mapStringToDate(Locale locale, Date dateOrigin, String dateStage) {

		Date returnValue = null;
		try {
			if (!AbstractCommonMapper.isEmptyOrNull(dateStage)) {
				returnValue = CollateralMapper.compareDate(locale, dateOrigin, dateStage);
			}
		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapFormString_OBDate", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * mapFormString_OBBoolean
	 * 
	 * @param formInput
	 * @return boolean
	 */
	public static boolean mapFormString_OBBoolean(String formInput) {

		boolean returnValue = false;
		try {
			if (!AbstractCommonMapper.isEmptyOrNull(formInput)) {
				returnValue = (Boolean.valueOf(formInput).booleanValue());
			}
		}
		catch (Exception e) {
			DefaultLogger.error("UIUtil.mapFormString_OBBoolean", "Error at UIUtil", e);
		}
		return returnValue;
	}

	/**
	 * looks up entries in property file
	 * @param key
	 * @return String - value in key
	 */
	public static String readPropertyFile(String key) {
		if (key == null) {
			return null;
		}
		String retVal = PropertyManager.getValue(key).trim();
		return retVal;
	}

	/**
	 * helper method to get default pagination count from property file
	 * @return int - pagination count
	 */
	public static int getDefaultPaginationCount() {
		int records_per_page = 10;
		final String PAGINATION_KEY = "customer.pagination.nitems";
		String recordsString = readPropertyFile(PAGINATION_KEY);
		try {
			records_per_page = Integer.parseInt(recordsString);
		}
		catch (NumberFormatException e) {
			records_per_page = 10;
		}
		return records_per_page;
	}

	/**
	 * helper method to check work in progress while doing update
	 * @param event
	 * @param value
	 * @return
	 */
	public static boolean checkWip(String event, ITrxValue value) {
		if ("prepare_update".equals(event) || "maker_prepare_edit".equals(event)) {
			String status = value.getStatus();
			if (ICMSConstant.STATE_DRAFT.equals(status) || ICMSConstant.STATE_PENDING_UPDATE.equals(status)
					|| ICMSConstant.STATE_REJECTED.equals(status) || ICMSConstant.STATE_PENDING_DELETE.equals(status)
					|| ICMSConstant.STATE_REJECTED_UPDATE.equals(status)
					|| ICMSConstant.STATE_REJECTED_CREATE.equals(status)
					|| ICMSConstant.STATE_REJECTED_DELETE.equals(status)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * helper method to check work in progress while doing delete
	 * @param event
	 * @param value
	 * @return
	 */
	public static boolean checkDeleteWip(String event, ITrxValue value) {
		if ("prepare_delete".equals(event) || "maker_prepare_delete".equals(event)) {
			String status = value.getStatus();
			if (ICMSConstant.STATE_DRAFT.equals(status) || ICMSConstant.STATE_PENDING_UPDATE.equals(status)
					|| ICMSConstant.STATE_REJECTED.equals(status) || ICMSConstant.STATE_PENDING_DELETE.equals(status)
					|| ICMSConstant.STATE_REJECTED_DELETE.equals(status)) {
				return true;
			}
		}
		return false;
	}
	
	public static Double mapStringToDouble(String strValue, Locale locale) {
		if (strValue == null)
			return null;

		return (Double)(new DoubleLocaleConverter (locale)).convert(strValue);
	}
	
	public static BigDecimal mapStringToBigDecimal(String strValue, Locale locale) {
		if (strValue == null)
			return null;
		
		return (BigDecimal) (new BigDecimalLocaleConverter (locale)).convert(strValue);
	}
	
	public static Long mapStringToLong(String strValue, Locale locale) {
		if (strValue == null)
			return null;
		
		return (Long) (new LongLocaleConverter(locale)).convert(strValue);
	}
	
	public static Integer mapStringToInteger(String strValue, Locale locale) {
		if (strValue == null)
			return null;
		
		return (Integer)(new IntegerLocaleConverter(locale)).convert(strValue);
	}
	
	public static String mapNumberObjectToString(Object obj, Locale locale, int decimalPlaces) {
		if (obj == null)
			return null;
	
		return (String)(new EnhancedStringLocaleConverter(locale, decimalPlaces)).convert(obj);
	}
	
	public static Boolean mapStringToBoolean(String strValue) {
		if (strValue == null)
			return null;
		
		if (ICMSConstant.TRUE_VALUE.equals(strValue) ||
				Boolean.TRUE.toString().equals(strValue)) {
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	public static String mapBooleanToString(Boolean b) {
		if (b == null)
			return null;
		
		if (b.booleanValue())
			return ICMSConstant.TRUE_VALUE;
		
		return ICMSConstant.FALSE_VALUE;
	}

	// Added By Dayananda Laishram on 10/03/2015 || PRO_FIX_ROUNDUP_VALUE | Start

	public static String customDecimalFormater(double d)
	{
		DecimalFormat dft = new DecimalFormat("#0.00");
		boolean secondDecimalflag = false;
		boolean thirdDecimalflag = false;
		String strDecimalValue = String.valueOf(d);
			String decimalPart = String.valueOf(d).substring(strDecimalValue.indexOf(".")+1, strDecimalValue.length());
			decimalPart = null!= decimalPart && !"".equals(decimalPart) ? decimalPart : "0.0";
			String numericPart = String.valueOf(d).substring(0, strDecimalValue.indexOf("."));
		char[] decimalChar = decimalPart.toCharArray();
		double dblFirstDigit = 0.0d;
		double dblSecondDigit = 0.0d;
		double dblThirdDigit = 0.0d;
		double addOnValue = 0.0d;
			if(null!=decimalChar && decimalChar.length>=3 && Integer.parseInt(String.valueOf(decimalChar[2]))==5)
			{
				for(int index =0; index<3;index++)
				{
					if(index==0)
					{
					char firstDigit = decimalChar[index];
						dblFirstDigit = Double.parseDouble(String.valueOf(firstDigit));
					dblFirstDigit = dblFirstDigit / 10;
				}
					if(index==1)
					{
					char secondDigit = decimalChar[index];
						dblSecondDigit = Double.parseDouble(String.valueOf(secondDigit));
					secondDecimalflag = true;
				}
					if(index==2)
					{
					char thirdDigit = decimalChar[index];
						dblThirdDigit = Double.parseDouble(String.valueOf(thirdDigit));
					thirdDecimalflag = true;
				}
					if(thirdDecimalflag && secondDecimalflag && dblThirdDigit==5)
					{
					dblSecondDigit = dblSecondDigit + 1;
					dblSecondDigit = dblSecondDigit / 100;
						numericPart = null!=numericPart && !"".equals(numericPart) ? numericPart : "0.0";
						addOnValue = Double.parseDouble(numericPart)+dblFirstDigit+dblSecondDigit;
					strDecimalValue = dft.format(addOnValue);
				}

			}
			}
			else
			strDecimalValue = dft.format(d);

		return (strDecimalValue);
	}

	// Added By Dayananda Laishram on 10/03/2015 || PRO_FIX_ROUNDUP_VALUE | End
	
	//for phase 3 CR
	public static String formatWithCommaAndDecimal(String value){
		String returnValue="";
		if(null!=value && !value.isEmpty() && !value.replaceAll(",", "").isEmpty()){
		if(value.contains(",")){
			value=value.replaceAll(",", "");
		}
		if("0.00".equals(value)){
			return "0.00";
		}
		else{
			boolean isNumber=ASSTValidator.isValidDecimalNumberWithComma(value);
			if(isNumber){
				DecimalFormat df =new DecimalFormat("##,##0.00");
				String[] split = value.split("\\.");
				if(split.length > 1){
					if(split[1].length() > 2){
						String hashCount="";
						for(int i=0; i<split[1].length() ; i++){
							hashCount=hashCount+"#";
						}
						
						 df = new DecimalFormat("##,###."+hashCount);
					}
				}
			double amount = Double.parseDouble(value);
			
			return df.format(amount);
			}else{
				return value;
			}
		}
		}
		
		return value;
}
	
	public static String formatWithComma(String value){
		String returnValue="";
		if(null!=value && !value.isEmpty() && !value.replaceAll(",", "").isEmpty()){
		if(value.contains(",")){
			value=value.replaceAll(",", "");
		}
		if("0".equals(value)){
			return "0";
		}
		else{
			boolean isNumber=ASSTValidator.isValidDecimalNumberWithComma(value);
			if(isNumber){
				DecimalFormat df =new DecimalFormat("##,##0");
			
				double amount = Double.parseDouble(value);
			
			return df.format(amount);
			}else{
				return value;
			}
		}
		}
		
		return value;
}
	public static String removeComma(String value){
		if(null!=value && !value.isEmpty()){
			value=value.replaceAll(",", "");
		}
		return value;
		
	}
	
	public static String formatWithCommaAndDecimalUptoTwoDecimal(String value){
		String returnValue="";
		if(null!=value && !value.isEmpty() && !value.replaceAll(",", "").isEmpty()){
		if(value.contains(",")){
			value=value.replaceAll(",", "");
		}
		if("0.00".equals(value)){
			return "0.00";
		}
		else{
			boolean isNumber=ASSTValidator.isValidDecimalNumberWithComma(value);
			if(isNumber){
				DecimalFormat df =new DecimalFormat("##,##0.00");
				double amount = Double.parseDouble(value);
				return df.format(amount);
			}else{
				return value;
			}
		}
		}
		
		return value;
}
	
	public static String formatWithCommaAndDecimalNew(String value){
		String returnValue="";
		if(null!=value && !value.isEmpty() && !value.replaceAll(",", "").isEmpty()){
		if(value.contains(",")){
			value=value.replaceAll(",", "");
		}
		if("0.00".equals(value)){
			return "0.00";
		}
		else{
			boolean isNumber=ASSTValidator.isValidDecimalNumberWithComma(value);
			if(isNumber){
				DecimalFormat df =new DecimalFormat("##,##0.00");
				String[] split = value.split("\\.");
				if(split.length > 1){
					if(split[1].length() > 2){
						String hashCount="";
						for(int i=0; i<split[1].length() ; i++){
							hashCount=hashCount+"#";
						}
						
						 df = new DecimalFormat("##,###."+hashCount);
					}
				}
				BigDecimal bd = new BigDecimal(value);
			
			return df.format(bd);
			}else{
				return value;
			}
		}
		}
		
		return value;
}

	public static String formatTime(Date date) {
		try {
			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			return time.format(date);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static <T> String getDelimitedStringFromMap(Map<T,T> map, String delimitter, String keyValueSeparator) {
		
		if(map != null && !map.isEmpty() && StringUtils.isNotBlank(delimitter) &&  StringUtils.isNotBlank(keyValueSeparator) ) {
			String str = "";
			boolean isFirst = false;
			
			for (Map.Entry<T,T> entry : map.entrySet()) {
				if(entry != null) {
					if (!isFirst) {
						str = entry.getKey()+keyValueSeparator+entry.getValue();
						isFirst = true;
					} else {
						str = str + delimitter + entry.getKey()+keyValueSeparator+entry.getValue();
					}
				}
			}
			return str;
		}
		
		return null;
	}
	
	//TO BE ONLY USED WHILE REVERSING getDelimitedStringFromMap
	public static Map<String,String> getMapFromDelimitedString(String delimittedString, String delimitter, String keyValueSeparator) {
		
		if(StringUtils.isNotBlank(delimittedString) && StringUtils.isNotBlank(delimitter) && StringUtils.isNotBlank(keyValueSeparator)) {
			String[] splittedKeyValuePair =  delimittedString.split(delimitter);
			Map hashmap = new HashMap<String,String>();
			
			for(String str : splittedKeyValuePair) {
				String[] actualSplittedKeyValuePair =  str.split(keyValueSeparator);
				
				if(actualSplittedKeyValuePair!=null && actualSplittedKeyValuePair.length==2) {
					hashmap.put(actualSplittedKeyValuePair[0], actualSplittedKeyValuePair[1]);
				}
			}
			
			return hashmap;
		}

		return null;
	}
	
	public static <T> List<String> getListFromDelimitedString(String delimittedString, String delimitter) {
		
		if(StringUtils.isNotBlank(delimitter) &&  StringUtils.isNotBlank(delimittedString)) {
			List<String> delimitedList = new ArrayList<String>();
			String[] splittedStringArray =  delimittedString.split(delimitter);
			
			for(String str : splittedStringArray) {
				delimitedList.add(str.trim());
			}
			
			return delimitedList;
		}
		
		return Collections.emptyList();
	}
		public static <T> Map<String,String> getMapFromDelimitedString(String delimittedString, String delimitter) {
		Map<String,String> delimitedMap = new HashMap<String, String>();
		if(StringUtils.isNotBlank(delimitter) &&  StringUtils.isNotBlank(delimittedString)) {
			String[] splittedStringArray =  delimittedString.split(delimitter);
			
			for(String str : splittedStringArray) {
				if(StringUtils.isNotBlank(str))
					delimitedMap.put(str, str);
			}
		}
		
		return delimitedMap;
	}
	
	public static <T> String getDelimitedStringFromList(List<T> list, String delimiter) {
		if (list != null && list.size() > 0) {
			String str = "";
			boolean isFirst = false;
			for (T obj : list) {
				if (obj != null) {
					if (!isFirst) {
						str = obj.toString();
						isFirst = true;
					} else {
						str = str + delimiter + obj.toString();
					}
				}
			}
			return str;
		}
		return null;
	}
	
	public static <E, T> List<E> getPropertyListFromObjectList(List<T> objectList, String property) {
		List<E> propertyList = new ArrayList<E>();
		if (objectList != null && objectList.size() > 0) {
			for (Object obj : objectList) {
				Object propValue;
				try {
					propValue = PropertyUtils.getProperty(obj, property);
					if(propValue!=null){
						propertyList.add((E) propValue);
					}
				}
				catch (Exception e) {
					throw new IllegalArgumentException("an error thrown while trying to retrieve the property '"
							+ property + "' from the Object '" + (obj==null?obj:obj.getClass().getName()) + "'");
				}
			}
		}
		return propertyList;
	}

	public static Date addDate(Date inputDate, int dateFrequencyType , int inputNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);
		if(Calendar.DATE == dateFrequencyType) {
			cal.add(Calendar.DATE, inputNum);
		}
		else if(Calendar.MONTH == dateFrequencyType) {
			cal.add(Calendar.MONTH, inputNum);
		} 
		else if(Calendar.YEAR == dateFrequencyType) {
			cal.add(Calendar.YEAR, inputNum);
		}
		return cal.getTime();
	}
	
	public static String compareExponentialValue(String leftValue, String rightValue) {
		if(StringUtils.isEmpty(leftValue)) {
			return Validator.ERROR_NONE;
		}
		BigDecimal left = mapStringToBigDecimal(leftValue);
		BigDecimal right = mapStringToBigDecimal(rightValue);
		if(left.compareTo(right) == 1) {
			return Validator.ERROR_GREATER_THAN;
		}
		
		return Validator.ERROR_NONE;
	}
	
	public static String removeTrailingZeros(String num) {
		return num.indexOf(".") < 0 ? num : num.replaceAll("0*$", "").replaceAll("\\.$", "");
	}
	
    public static String stripCharacter(String original, char [] charToStrips) {
    	if (original==null) {
    		return null;
    	} else {
    		StringBuffer stripped = new StringBuffer();
    		for (int idx = 0; idx < original.length(); idx++) {
    			char curr = original.charAt(idx);    			
    			boolean flag = true;
    			for(int i=0;charToStrips!=null&&i<charToStrips.length;i++) {
    				if (curr == charToStrips[i]) {
    					flag = false;
    					break;
    				}
    			}    			
    			if(flag==true) {
    				stripped.append(curr);
    			}    			
    		}
    		return stripped.toString();
    	}
    }
    
	public static BigDecimal getAmountAsBigDecimal(Amount amt, CurrencyCode ccy) {
		BigDecimal returnAmt = null;

		if (amt != null && (amt.getCurrencyCode() != null) && (ccy != null)) {
			try {
				Amount newAmt = ForexHelper.getInstance().convert(amt, ccy);
				returnAmt = newAmt.getAmountAsBigDecimal();
			}
			catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.error(UIUtil.class.getName() , "Exception caught in getAmountAsBigDecimal : "+e.getMessage(), e);
			}
		}
		return returnAmt;
	}
	
	public static <T> List<T> removeElements(List<T> inputList,  String[] indices){
		if(ArrayUtils.isEmpty(indices) || CollectionUtils.isEmpty(inputList))
			return inputList;
		
		List<T> removableElements = new ArrayList<T>();
		
		for(String index : indices) {
			int idx = Integer.parseInt(index)-1;
			if(idx < inputList.size())
				removableElements.add(inputList.get(idx));
		}
		inputList.removeAll(removableElements);
		return inputList;
	}
	
	public static Map<String, String> convertLabelValueListToMap(List<LabelValue> list){
		Map<String, String> map = new HashMap<String, String>();
		if(!CollectionUtils.isEmpty(list)) {
			for(LabelValue lv : list)
				map.put(lv.getValue(), lv.getLabel());
		}
		return map;
	}
	
	public static Map<String, String> convertLabelValueBeanListToMap(List<LabelValueBean> list){
		Map<String, String> map = new HashMap<String, String>();
		if(!CollectionUtils.isEmpty(list)) {
			for(LabelValueBean lv : list)
				map.put(lv.getValue(), lv.getLabel());
		}
		return map;
	}
		
    public static <T> String getJSONStringFromList(List<T> list, String delimiter) {
		if (list != null && list.size() > 0) {
			String str = "";
			String jsonStr= "\"";
			boolean isFirst = false;
			for (T obj : list) {
				if (obj != null) {
					if (!isFirst) {
						
						str =  str.concat(jsonStr.concat(obj.toString().concat(jsonStr)));
						
						isFirst = true;
					} else {
						
						str = str + delimiter + jsonStr.concat(obj.toString().concat(jsonStr));
					}
				}
			}
		//	str= str.concat("}");
			return str.trim();
		}
		return null;
	}
}
