/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/CommodityTitleDocComparator.java,v 1.5 2004/07/16 07:18:14 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This comparator compares Commodity Title Document using either transaction
 * date or title document id.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/07/16 07:18:14 $ Tag: $Name: $
 */
public class CommodityTitleDocComparator implements Comparator {
	public static final String COMPARE_BY_TRX_DATE = "T";

	public static final String COMPARE_BY_TITLEDOC_ID = "I"; // Note: it is
																// actually by
																// common
																// reference id

	public static final String COMPARE_BY_TR_DATE_TD_ID = "A"; // by trx date
																// and reference
																// id

	private String compareBy;

	/**
	 * Default constructor, compared using transaction date.
	 */
	public CommodityTitleDocComparator() {
		compareBy = COMPARE_BY_TRX_DATE;
	}

	/**
	 * Constructs Title Doc comparator based on COMPARE_BY_TRX_DATE or
	 * COMPARE_BY_TITLEDOC_ID.
	 * 
	 * @param compareBy of type String
	 */
	public CommodityTitleDocComparator(String compareBy) {
		this.compareBy = compareBy;
	}

	/**
	 * Compares o1 and o2. It returns a negative integer, zero, or a positive
	 * integer as the first argument is less than, equal to, or greater than the
	 * second.
	 * 
	 * @return a negative integer, zero, or a positive integer
	 */
	public int compare(Object o1, Object o2) {
		if ((o1 == null) && (o2 == null)) {
			return 0;
		}
		else if (o1 == null) {
			return -1;
		}
		else if (o2 == null) {
			return 1;
		}

		if (compareBy.equals(COMPARE_BY_TITLEDOC_ID)) {
			return compareByTitleDocID(o1, o2);
		}
		else if (compareBy.equals(COMPARE_BY_TR_DATE_TD_ID)) {
			return compareByTrDateAndTDID(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Helper method to compare using title doc transaction id and its title doc
	 * id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByTrDateAndTDID(Object o1, Object o2) {
		int result = defaultCompare(o1, o2);
		if (result == 0) {
			result = compareByTitleDocID(o1, o2);
		}
		return result;
	}

	/**
	 * Helper method to compare using commodity title document id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByTitleDocID(Object o1, Object o2) {
		ICommodityTitleDocument obj1 = (ICommodityTitleDocument) o1;
		ICommodityTitleDocument obj2 = (ICommodityTitleDocument) o2;

		Long id1 = new Long(obj1.getRefID());
		Long id2 = new Long(obj2.getRefID());
		return id1.compareTo(id2);
	}

	/**
	 * Default comparison for CommodityTitleDocComparator. It compares using
	 * transaction date.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		Date date1 = ((ICommodityTitleDocument) o1).getTransactionDate();
		Date date2 = ((ICommodityTitleDocument) o2).getTransactionDate();

		if ((date1 == null) && (date2 == null)) {
			return 0;
		}
		else if (date1 == null) {
			return 1;
		}
		else if (date2 == null) {
			return 1;
		}
		else {
			date1 = getDateWithoutTime(date1);
			date2 = getDateWithoutTime(date2);
			return date1.compareTo(date2);
		}
	}

	/**
	 * Helper method to get date without timestamp.
	 * 
	 * @param date a date with timestamp
	 * @return date without timestamp
	 */
	private Date getDateWithoutTime(Date date) {
		GregorianCalendar g = new GregorianCalendar();
		g.setTime(date);
		g.set(Calendar.HOUR_OF_DAY, 0);
		g.set(Calendar.MINUTE, 0);
		g.set(Calendar.SECOND, 0);
		g.set(Calendar.MILLISECOND, 0);
		return g.getTime();
	}
}
