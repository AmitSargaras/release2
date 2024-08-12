/*
 * Created on Dec 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator <p/> TODO To change the template for this generated
 *         type comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class DB2DateConverter {

	public static final String TS_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	// for 12/Jan/2006 dateFormat dd/MMM/yyyy
	public static String convertToTsFormat(String dateString, String dateFormat) {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat);
			Date d = sdf1.parse(dateString);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf2.format(d);
		}
		catch (Exception ex) {
		}
		return "";
	}

	/**
	 * 
	 * @param dateString
	 * @param dateFormat
	 * @return String
	 */
	public static String getDateTimeString(String dateString, String dateFormat) {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat);
			Date d = sdf1.parse(dateString);
			SimpleDateFormat sdf2 = new SimpleDateFormat(TS_FORMAT);
			return "timestamp('" + sdf2.format(d) + "')";
		}
		catch (Exception ex) {
		}
		return "";
	}

	/**
	 * 
	 * @param date
	 * @return String
	 */

	public static String formatDate(Date date) {
		try {
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(TS_FORMAT);
				return sdf.format(date);
			}
		}
		catch (Exception ex) {
		}
		return "";
	}

	/**
	 * Used in Report Scheduler
	 * @param date
	 * @return String
	 */
	public static String getDateAsString(Date date) {
		try {
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				return sdf.format(date);
			}
		}
		catch (Exception ex) {
		}
		return "";
	}

}
