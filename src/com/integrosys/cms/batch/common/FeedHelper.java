/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/common/FeedHelper.java,v 1.1.2.1 2007/03/27 07:39:13 jychong Exp $
 */

package com.integrosys.cms.batch.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.base.techinfra.util.SimpleReflectHelper;

/**
 * Purpose: to serve the reading of any feed file Description: To read feed and
 * return a List to caller, eg. Stock, Unit Trust, Bond, CA_Sharesoutstanding,
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.1.2.1 $
 * @since $Date: 2007/03/27 07:39:13 $ Tag: $Name: cms_prod_r_1_5 $
 */
public class FeedHelper {
	private PropertyUtil prop = null;

	private final String NULL_STRING = "null";

	private final String INVALID_NUMBER = "-9";

	private final String CSV_DELIM_STR = ",";

	private final String feedLayoutPropFileName = "/feedlayout.properties";

	private final String propLineNoKey = ".lineno";

	private final String propColumnKey = ".column.";

	private final String propTypeKey = ".type";

	private final String classNameKey = ".classname";

	private final String defaultDateFormat = "yyyyMMdd";

	private final int defaultFeedDataLineNo = 2;

	private final String footerIndicator = "TTTTT";

	public FeedHelper() {
		init();
	}

	/**
	 * Description: to get a List from a file based on the feed type
	 * 
	 * @param filename the file name of the feed
	 * @param feedType feed type, eg feed.stock, feed.bond
	 * @see /feedlayout.properties
	 * @return java.util.List the list contains of feed Obj OB
	 */
	public List getListForFeed(String filename, String feedType) throws IOException {
		Class feedClazz = getFeedClassByType(feedType);
//		System.out.println("feedClazz = " + feedClazz);
		ArrayList aStrList = new ArrayList();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		for (String s = br.readLine(); s != null; s = br.readLine()) {
			if ((s != null) && s.startsWith(footerIndicator)) {
				DefaultLogger.debug(this, "Footer Reached " + s);
				break;
			}
			else {
				aStrList.add(s);
			}
			DefaultLogger.debug(this, "s = " + s);
		}

		ArrayList feedList = new ArrayList();
		int numFeedDataLineNo = prop.getInt(feedType + propLineNoKey, defaultFeedDataLineNo);
		String[] allLinesStr = (String[]) aStrList.toArray(new String[0]);
		for (int i = numFeedDataLineNo; i < allLinesStr.length; i++) {
			String[] aTokensArray = getTokensArrayForCSVFile(allLinesStr[i]);

			Object aFeed = getNewInstanceForClass(feedClazz);
			for (int j = 0; j < aTokensArray.length; j++) {
				String setMethodName = prop.get(feedType + propColumnKey + (j + 1), "");
				if ((setMethodName.length() > 0) && isValidValue(aTokensArray[j])) {
					DefaultLogger.debug(this, "before set " + aTokensArray[j]);
				}
				SimpleReflectHelper.setValueInObByMethodName(aTokensArray[j], aFeed, setMethodName);
			}
			feedList.add(aFeed);
			DefaultLogger.debug(this, "aFeed = " + aFeed);
		}
		DefaultLogger.debug(this, "Closing Feed !!!!!!!!!!");
		br.close();
		return feedList;
	}

	/**
	 * Description: to get a String array which consist of a feed data separated
	 * by comma
	 * 
	 * @param aStr a comma separated String, which is feed data
	 * @return arry of java.lang.String token array of feed data
	 */
	public String[] getTokensArrayForCSVFile(String aStr) {
		ArrayList aTokensList = new ArrayList();
		/*
		 * int indexOfComma = aStr.indexOf(CSV_DELIM_STR); int indexOfFirstChar
		 * = 0; while (indexOfComma >= 0) { String aToken =
		 * aStr.substring(indexOfFirstChar, indexOfComma); aToken =
		 * aToken.trim(); if (aToken.length() == 0) aToken = ""; // aToken =
		 * NULL_STRING;
		 * 
		 * aTokensList.add(aToken); indexOfFirstChar = indexOfComma + 1; if
		 * (indexOfFirstChar == aStr.length()) { //last comma
		 * aTokensList.add(NULL_STRING); } indexOfComma =
		 * aStr.indexOf(CSV_DELIM_STR, indexOfFirstChar); }
		 * 
		 * return (String[]) aTokensList.toArray(new String[0]);
		 */
		Vector v = csvToVector(aStr, CSV_DELIM_STR);
		return (String[]) v.toArray(new String[0]);

	}

	public boolean isValidValue(String value) {
		return !value.startsWith(INVALID_NUMBER) && !NULL_STRING.equals(value);
	}

	protected void init() {
		prop = PropertyUtil.getInstance(feedLayoutPropFileName);
	}

	protected Class getFeedClassByType(String feedType) {
		String feedClzName = prop.get(feedType + classNameKey, "");
		if (feedClzName.length() == 0) {
			throw new IllegalArgumentException("Can't find corresponding feed class for [" + feedType + "].");
		}

		try {
			return Class.forName(feedClzName);
		}
		catch (ClassNotFoundException cnfe) {
			throw new IllegalArgumentException("Can't locate feed class: " + feedClzName + ". Please check classpath.");
		}
	}

	protected Object getNewInstanceForClass(Class clazz) {
		try {
			return clazz.newInstance();
		}
		catch (InstantiationException ie) {
			throw new IllegalArgumentException("Failed to instantiate a new instance for class: " + clazz.getName()
					+ "| " + ie.toString());
		}
		catch (IllegalAccessException iae) {
			throw new IllegalArgumentException("Failed to instantiate a new instance for class: " + clazz.getName()
					+ "| " + iae.toString());
		}
	}

	// Copied from com.mbb.cms.app.common.filereader.CSVReader
	public Vector csvToVector(String row, String delimStr) {
		int c;
		java.util.Vector v;
		boolean doubleQoutedFieldStarted;
		String fieldValue;
		v = new java.util.Vector();
		doubleQoutedFieldStarted = false;
		fieldValue = "";
		c = 0;

		char delim = delimStr.charAt(0);

		while (c < row.length()) {
			L424: {
				if (row.charAt(c) == 34) {
					if (((c + 1) < row.length()) && (row.charAt(c + 1) == 34)) {
						if (((c + 2) < row.length()) && (row.charAt(c + 2) == 34)) {
							if (doubleQoutedFieldStarted == false) {
								doubleQoutedFieldStarted = true;
							}
							else {
								doubleQoutedFieldStarted = false;
							}
							fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c + 2)).toString();
							c = c + 2;
						}
						else {
							if (((c + 2) == row.length()) || (((c + 2) < row.length()) && (row.charAt(c + 2) == delim))) {
								if (fieldValue.equals("")) {
									if (doubleQoutedFieldStarted == false) {
										doubleQoutedFieldStarted = true;
									}
									else {
										doubleQoutedFieldStarted = false;
									}
								}
								else {
									fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c)).toString();
								}
								break L424;
							}
							fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c + 1)).toString();
							c++;
						}
					}
					else {
						if (doubleQoutedFieldStarted) {
							if (((c + 1) == row.length()) || (((c + 1) < row.length()) && (row.charAt(c + 1) == delim))) {
								doubleQoutedFieldStarted = false;
							}
							else {
								fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c)).toString();
							}
							break L424;
						}
						doubleQoutedFieldStarted = true;
					}
					break L424;
				}
				if (row.charAt(c) == delim) {
					if (doubleQoutedFieldStarted) {
						fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c)).toString();
					}
					else {
						// System.out.println("fieldValue="+fieldValue);
						if ((fieldValue != null) && (fieldValue.length() > 0)) {
							fieldValue = fieldValue.trim();
						}
						v.addElement(fieldValue);
						fieldValue = "";
					}
					break L424;
				}
				fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c)).toString();
			}
			c++;
		}// end while
		if ((fieldValue != null) && (fieldValue.length() > 0)) {
			fieldValue = fieldValue.trim();
		}

		v.addElement(fieldValue);

		return v;
	}
}