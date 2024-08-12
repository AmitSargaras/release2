/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/OrdinalNumberList.java,v 1.2 2005/10/04 07:06:55 lyng Exp $
 */
package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class displays numbers as ordinal numbers.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/10/04 07:06:55 $ Tag: $Name: $
 */
public class OrdinalNumberList {
	private static HashMap ordinalNumberList = null; // number and its ordinal
														// number

	private static OrdinalNumberList thisInstance;

	private static Collection ordinalNumberLabel = null;

	private static Collection ordinalNumberValue = null;

	/**
	 * Constructs an ordinal number list given the count of numbers.
	 * 
	 * @param count count of numbers
	 */
	public synchronized static OrdinalNumberList getInstance(int count) {
		if ((thisInstance == null) || (ordinalNumberList.size() != count)) {
			thisInstance = new OrdinalNumberList(count);
		}
		return thisInstance;
	}

	/**
	 * Constructs ordinal number list given the count of numbers.
	 */
	private OrdinalNumberList(int count) {
		ordinalNumberList = new HashMap();
		ordinalNumberValue = new ArrayList();
		ordinalNumberLabel = new ArrayList();

		String value = "", label = "";
		for (int i = 1; i <= count; i++) {
			value = String.valueOf(i);
			label = i + numSuffix(i);
			ordinalNumberValue.add(value);
			ordinalNumberLabel.add(label);
			ordinalNumberList.put(value, label);
		}
	}

	/**
	 * Get the ordinal suffix for the given number.
	 * 
	 * @param num of type int
	 * @return String
	 */
	public static String numSuffix(int num) {
		if (num < 0) {
			num = -num;
		}
		String suffix;
		int mod100 = num % 100;
		int mod10 = num % 10;

		if ((mod100 > 10) && (mod100 < 14)) {
			suffix = "th";
		}
		else {
			switch (mod10) {
			case 1:
				suffix = "st";
				break;
			case 2:
				suffix = "nd";
				break;
			case 3:
				suffix = "rd";
				break;
			default:
				suffix = "th";
			}
		}
		return suffix;
	}

	/**
	 * Get ordinal number label.
	 * 
	 * @return a Collection of numbers
	 */
	public Collection getOrdinalNumberLabel() {
		return ordinalNumberLabel;
	}

	/**
	 * Get ordinal number.
	 * 
	 * @return a Collection of ordinal numbers
	 */
	public Collection getOrdinalNumberValue() {
		return ordinalNumberValue;
	}

	/**
	 * Method to return the country name based on supplied key
	 * 
	 * @param num number to get the corresponding ordinal number
	 * @return ordinal number in String format
	 */

	public String getOrdinalNumber(int num) {
		if (!ordinalNumberList.isEmpty()) {
			return (String) ordinalNumberList.get(String.valueOf(num));
		}
		else {
			return "";
		}
	}
}
