/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ValuationComparator.java,v 1.2 2003/09/16 10:50:02 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Comparator;
import java.util.Date;

/**
 * This comparator compares Valuation using either valuation id or valuation
 * date.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/16 10:50:02 $ Tag: $Name: $
 */
public class ValuationComparator implements Comparator {
	public static final String COMPARE_BY_VAL_DATE = "DATE";

	public static final String COMPARE_BY_VAL_ID = "VALID";

	private String compareBy;

	/**
	 * Default constructor, compared using valuation id.
	 */
	public ValuationComparator() {
		compareBy = COMPARE_BY_VAL_ID;
	}

	/**
	 * Constructs Valuation comparator based on COMPARE_BY_DATE or
	 * COMPARE_BY_VALUATION_ID.
	 * 
	 * @param compareBy of type String
	 */
	public ValuationComparator(String compareBy) {
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

		if (compareBy.equals(COMPARE_BY_VAL_DATE)) {
			return compareByDate(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for ValuationComparator. It compares using valuation
	 * id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		IValuation obj1 = (IValuation) o1;
		IValuation obj2 = (IValuation) o2;

		Long id1 = new Long(obj1.getValuationID());
		Long id2 = new Long(obj2.getValuationID());
		return id1.compareTo(id2);
	}

	/**
	 * Helper method to compare using valuation id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByDate(Object o1, Object o2) {
		Date date1 = ((IValuation) o1).getValuationDate();
		Date date2 = ((IValuation) o2).getValuationDate();

		if ((date1 == null) && (date2 == null)) {
			// return 0;
			return defaultCompare(o1, o2);
		}
		else if (date1 == null) {
			return -1;
		}
		else if (date2 == null) {
			return 1;
		}
		else {
			int result = date1.compareTo(date2);
			if (result == 0) {
				return defaultCompare(o1, o2);
			}
			else {
				return result;
			}
		}
	}
}
