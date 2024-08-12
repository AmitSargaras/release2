/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/VelocityNotificationUtil.java,v 1.18 2006/10/26 04:51:57 jzhan Exp $
 */
package com.integrosys.cms.app.eventmonitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.mapper.MapperUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * This interface defines the list of actions that should be part of event
 * monitoring..
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.18 $
 * @since $Date: 2006/10/26 04:51:57 $ Tag: $Name: $
 */

public class VelocityNotificationUtil {

	private static final VelocityNotificationUtil INSTANCE = new VelocityNotificationUtil();

	public static VelocityNotificationUtil getInstance() {
		return INSTANCE;
	}

	public static Collection getCollection(Object objArray[]) {
		if (objArray == null) {
			return new ArrayList();
		}

		return Arrays.asList(objArray);
	}

	public static int sizeOf(Collection cc) {
		if (cc != null) {
			return cc.size();
		}
		else {
			return 0;
		}

	}

	public String formatAmount(Amount amt) {
		String ss = null;
		String ccy = null;
		try {
			ss = CurrencyManager.convertToDisplayString(Locale.US, amt);
			ccy = amt.getCurrencyCode();
		}
		catch (Exception e) {
			return "-";
		}
		return ccy + " " + ss;
	}

	public String formatAndBracketAmount(Amount amt) {

		String ss = null;
		String ccy = null;
		try {
			ss = CurrencyManager.convertToDisplayString(Locale.US, amt);
			ccy = amt.getCurrencyCode();
		}
		catch (Exception e) {
			return "-";
		}

		return ccy + " " + "(" + ss + ")";

	}

	/**
	 * Returns a string representation of the absolute value of the input
	 * integer.
	 * 
	 * @param i A positive or negative integer.
	 * @return The absolute value of the input integer.
	 */
	public String abs(int i) {

		DefaultLogger.debug(this, "the integer passed in = " + i);

		return (new Integer(Math.abs(i))).toString();
	}

	/**
	 * Gets the name of country based on the country code. Eg: SG = Singapore.
	 * 
	 * @param countryCode The two-letter country code.
	 * @return The corresponding country name.
	 */
	public String getCountryName(String countryCode) {

		CountryList countryList = CountryList.getInstance();
		return countryList.getCountryName(countryCode);

	}

	public String getOrgCodeDesc(String anOrgCode) {
		return CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.ORG_CODE, anOrgCode);
		// OrgCodeList orgList = OrgCodeList.getInstance();
		// return orgList.getOrgCodeLabel(anOrgCode);
	}

	/**
	 * The resultant string will have <b>no</b> fraction digits and will be
	 * formatted according the US locale.
	 * 
	 * @param f
	 * @return
	 */
	public String mapToString(float f) {
		return MapperUtil.mapDoubleToString((double) f, 0, Locale.US);
	}

	public String mapToCoverageString(float f) {
		if (f < 0) {
			return "-";
		}
		else {
			return MapperUtil.mapDoubleToString((double) f, 0, Locale.US);
		}
	}

	/**
	 * Truncate a String to 350 chars.. Used because Max column size is 4000 for
	 * Notification message. Big varchar fields cannot be displayed, hence
	 * truncating
	 */

	public String truncateString(String inStr, String fieldName) {
		String outStr = null;
		if (inStr != null) {
			outStr = convertHTMLWrap(inStr);
		}
		if ((outStr != null) && (outStr.length() > 350)) {
			return outStr.substring(0, 350) + "<br>" + fieldName
					+ " has been truncated. Please refer to report for details.";
		}
		else {
			return outStr;
		}
	}

	public String convertHTMLWrap(String inStr) {
		String outStr = null;
		if (inStr != null) {
			inStr = formatString(inStr, 70/* Line Length */);
			StringBuffer buffer = new StringBuffer();
			char[] allChars = inStr.toCharArray();
			for (int x = 0; x < allChars.length; x++) {
				char aChar = allChars[x];
				if (aChar != '\r') {
					if (aChar == '\n') {
						buffer.append("<br>");
					}
					else if (aChar == '<') {
						buffer.append("&lt;");
					}
					else if (aChar == '>') {
						buffer.append("&gt;");
					}
					else if (aChar == ' ') {
						buffer.append("&nbsp;");
					}
					else {
						buffer.append(aChar);
					}
				}
			}
			outStr = buffer.toString();
		}

		return outStr;
	}

	// TextAreaFormatter
	private static final String CARRIAGE_RETURN = "\n\r";

	private static final String SPACE = " ";

	/**
	 * To format a String with carriage return based on the length of each line
	 * accepted
	 * @param aLine - String
	 * @param aLineLength - int
	 * @return String - the formatted String
	 */
	public static String formatString(String aLine, int aLineLength) {
		String[] lineList = formatLine(aLine, aLineLength);
		StringBuffer buf = new StringBuffer();
		if (lineList != null) {
			for (int ii = 0; ii < lineList.length; ii++) {
				if (ii != 0) {
					buf.append(CARRIAGE_RETURN);
				}
				buf.append(lineList[ii]);
			}
		}
		return buf.toString();
	}

	/**
	 * To formamt a String into a list of Strings based on the length of each
	 * line allowed
	 * @param aLine - String
	 * @param aLineLength - int
	 * @return String[] - the list of Strings
	 */
	public static String[] formatLine(String aLine, int aLineLength) {
		String[] lineList = splitByCarriageReturn(aLine);
		ArrayList formattedList = new ArrayList();

		boolean lastLineIndicator = false;
		for (int ii = 0; ii < lineList.length; ii++) {
			if (ii == lineList.length - 1) {
				lastLineIndicator = true;
			}
			String[] rowList = formatRow(lineList[ii], aLineLength, lastLineIndicator);
			for (int jj = 0; jj < rowList.length; jj++) {
				formattedList.add(rowList[jj]);
			}
		}
		return (String[]) formattedList.toArray(new String[formattedList.size()]);
	}

	public static List convertMap2List(Map aMap) {
		List aList = new ArrayList();
		if (aMap == null) {
			return aList;
		}
		for (Iterator iter = aMap.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			aList.add(aMap.get(key));
		}
		return aList;
	}

	/**
	 * To format a String into a list of String based on the length allowed.
	 * This is to cater for a String without any carriage return and that exceed
	 * the required length.
	 * @param aLine - String
	 * @param aLineLength - int
	 * @return String[] - the list of formatted String
	 */
	private static String[] formatRow(String aLine, int aLineLength, boolean aLastLineIndicator) {
		ArrayList rowList = new ArrayList();

		int tempLineLength = aLineLength;
		if (aLastLineIndicator) {
			tempLineLength++;
		}
		if (aLine.length() <= tempLineLength) {
			rowList.add(aLine);
		}
		else {
			String[] wordList = splitBySpace(aLine);
			String tempRow = "";
			for (int ii = 0; ii < wordList.length; ii++) {
				if (wordList[ii].length() > aLineLength) {
					if (tempRow.length() > 0) {
						rowList.add(tempRow);
					}
					String[] subRowList = spiltIntoLine(wordList[ii], aLineLength);
					if ((subRowList[subRowList.length - 1]).length() < aLineLength) {
						tempRow = subRowList[subRowList.length - 1];
						addToList(rowList, subRowList, 1);
					}
					else {
						addToList(rowList, subRowList, 0);
					}
				}
				else {
					if (((tempRow + SPACE + wordList[ii]).length() < tempLineLength)) {
						if (tempRow.length() > 0) {
							tempRow = tempRow + SPACE + wordList[ii];
						}
						else {
							tempRow = wordList[ii];
						}
					}
					else {
						rowList.add(tempRow);
						tempRow = wordList[ii];
					}
				}
			}
			if (tempRow.trim().length() > 0) {
				rowList.add(tempRow);
			}
		}
		return (String[]) rowList.toArray(new String[rowList.size()]);
	}

	/**
	 * Helper method to add the list of String into another list based on the
	 * value of the offset
	 * @param aList - ArrayList
	 * @param strList - String[]
	 * @param offset - int
	 */
	private static void addToList(ArrayList aList, String[] strList, int offset) {
		for (int ii = 0; ii < (strList.length - offset); ii++) {
			aList.add(strList[ii]);
		}
	}

	/**
	 * To split a String into a list of String by carriage return
	 * @param aLine - String
	 * @return String[] - the list of Strings
	 */
	private static String[] splitByCarriageReturn(String aLine) {
		ArrayList aList = new ArrayList();
		StringTokenizer stValueStr = new StringTokenizer(aLine, CARRIAGE_RETURN);
		while (stValueStr.hasMoreTokens()) {
			aList.add(stValueStr.nextToken());
		}
		return (String[]) aList.toArray(new String[aList.size()]);
	}

	/**
	 * To split a String into a list of words (split by space)
	 * @param aLine - String
	 * @return String[] - the list of words
	 */
	private static String[] splitBySpace(String aLine) {
		ArrayList aList = new ArrayList();
		StringTokenizer stValueStr = new StringTokenizer(aLine, SPACE);
		while (stValueStr.hasMoreTokens()) {
			aList.add(stValueStr.nextToken());
		}
		return (String[]) aList.toArray(new String[aList.size()]);
	}

	/**
	 * To split a String (without any carriage return and spaces) into a list of
	 * Strings of the line length
	 * @param aLine - String
	 * @param aLineLength - int
	 * @param aLine String[] object - the list of Strings
	 */
	private static String[] spiltIntoLine(String aLine, int aLineLength) {
		ArrayList aList = new ArrayList();
		String temp = aLine;
		while (temp.length() > 0) {
			if (temp.length() < aLineLength) {
				aList.add(temp);
			}
			else {
				aList.add(temp.substring(0, aLineLength));
			}
			if (temp.length() > aLineLength) {
				temp = temp.substring(aLineLength, temp.length());
			}
			else {
				temp = "";
			}
		}
		return (String[]) aList.toArray(new String[aList.size()]);
	}
}