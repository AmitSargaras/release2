package com.integrosys.cms.batch.reports;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/CalendarHelper.java,v 1.5 2006/09/05 05:39:42 hshii Exp $
 */

/**
 * Description: Helper class to obtain information on the day of week and day of
 * the month
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/05 05:39:42 $ Tag: $Name: $
 */
public class CalendarHelper {

	private static Calendar calendar;

	private static Vector MONTH = new Vector();

	private static final String DELIMITERS = "/- ";

	static {
		TimeZone zone = TimeZone.getDefault();
		calendar = new GregorianCalendar(zone);
		java.util.Date currentTime = new java.util.Date();
		calendar.setTime(currentTime);
	}

	/**
	 * returns the day of the month, if it is first day of the month, the value
	 * returned is 1.
	 * @return int - day of month
	 */
	public static int getDayOfMonth() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * returns the day of the week, if it is a Sunday, the value returned is 1.
	 * @return int - day of week
	 */
	public static int getDayOfWeek() {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * returns the day of the year, if it is the first day of the year, the
	 * value returned is 1.
	 * @return int - day of year
	 */
	public static int getDayOfYear() {
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * returns the day of the month, if it is first day of the month, the value
	 * returned is 1.
	 * @param cal - calendar of the searched date
	 * @return int - day of month
	 */
	public static int getDayOfMonth(Calendar cal) {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * returns the day of the week, if it is a Sunday, the value returned is 1.
	 * @param cal - calendar of the searched date
	 * @return int - day of week
	 */
	public static int getDayOfWeek(Calendar cal) {
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * convert date in dd/mm/yyyy, dd-mm-yyyy and dd MMM yyyy format into
	 * java.util.Date this method will return null if the date pass in is
	 * invalid.
	 */
	public static Date convertDate(String sDate) {
		setMonth();

		Vector vDate = new Vector();
		int iYear = 0;
		int iMonth = 0;
		int iDay = 0;

		StringTokenizer st = new StringTokenizer(sDate, DELIMITERS);
		while (st.hasMoreTokens()) {
			vDate.add(st.nextToken());
		}

		if (vDate.size() != 3) {
			return null; // invalide date
		}

		String sDay = (String) vDate.elementAt(0);
		if ((sDay.length() != 1) && (sDay.length() != 2)) {
			return null; // invalide date
		}

		String sMonth = (String) vDate.elementAt(1);
		if ((sMonth.length() != 1) && (sMonth.length() != 2) && (sMonth.length() != 3)) {
			return null; // invalide date
		}

		String sYear = (String) vDate.elementAt(2);
		if (sYear.length() != 4) {
			return null; // invalide date
		}

		if (sMonth.length() == 3) {
			sMonth = sMonth.toUpperCase();

			if (MONTH.contains(sMonth)) {
				int iTemp = (MONTH.indexOf(sMonth)) + 1;
				sMonth = new Integer(iTemp).toString();
			}
			else {
				return null;
			}
		}

		try {
			iYear = Integer.parseInt(sYear);
			iMonth = Integer.parseInt(sMonth);
			iDay = Integer.parseInt(sDay);
		}
		catch (NumberFormatException nfe) {
			return null; // invalide date
		}

		// Calendar cal = new GregorianCalendar(iYear, iMonth, iDay);
		Calendar cal = Calendar.getInstance();
		cal.set(iYear, getCalendarMonth(iMonth), iDay);

		return cal.getTime();
	}

	/**
	 * Helper method to initialize the MONTH vector
	 */
	private static void setMonth() {
		if (MONTH.size() > 0) {
			return;
		}

		MONTH.addElement("JAN");
		MONTH.addElement("FEB");
		MONTH.addElement("MAR");
		MONTH.addElement("APR");
		MONTH.addElement("MAY");
		MONTH.addElement("JUN");
		MONTH.addElement("JUL");
		MONTH.addElement("AUG");
		MONTH.addElement("SEP");
		MONTH.addElement("OCT");
		MONTH.addElement("NOV");
		MONTH.addElement("DEC");
	}

	/**
	 * Helper method to get calendar month
	 */
	private static int getCalendarMonth(int iMonth) {
		switch (iMonth) {
		case 1:
			return Calendar.JANUARY;
		case 2:
			return Calendar.FEBRUARY;
		case 3:
			return Calendar.MARCH;
		case 4:
			return Calendar.APRIL;
		case 5:
			return Calendar.MAY;
		case 6:
			return Calendar.JUNE;
		case 7:
			return Calendar.JULY;
		case 8:
			return Calendar.AUGUST;
		case 9:
			return Calendar.SEPTEMBER;
		case 10:
			return Calendar.OCTOBER;
		case 11:
			return Calendar.NOVEMBER;
		case 12:
			return Calendar.DECEMBER;
		}
		return 0;
	}

	/**
	 * Get previous date for the date given
	 * @param currentDate - Date
	 * @return Date
	 */
	public static Date getPreviousDate(Date currentDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, -1);
		Date prevDate = cal.getTime();
		return prevDate;
	}
}
