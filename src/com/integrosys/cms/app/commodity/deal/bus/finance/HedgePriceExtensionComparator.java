/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/HedgePriceExtensionComparator.java,v 1.1 2004/06/22 06:54:37 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.util.Comparator;

/**
 * This comparator compares hedge price extension using its expiry date and
 * common reference id.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/22 06:54:37 $ Tag: $Name: $
 */
public class HedgePriceExtensionComparator implements Comparator {
	public static final String BY_END_DATE = "D";

	public static final String BY_REF_ID = "I";

	private String compareBy;

	/**
	 * Default constructor, compared using
	 * HedgePriceExtensionComparator.BY_END_DATE.
	 */
	public HedgePriceExtensionComparator() {
		compareBy = BY_END_DATE;
	}

	/**
	 * Constructs hedge price extension comparator based on
	 * HedgePriceExtensionComparator.BY_END_DATE or
	 * HedgePriceExtensionComparator.BY_REF_ID.
	 * 
	 * @param compareBy of type String
	 */
	public HedgePriceExtensionComparator(String compareBy) {
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

		if (compareBy.equals(BY_REF_ID)) {
			return compareByCommonRefID(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for HedgePriceExtensionComparator. It compares using
	 * end date and common reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		IHedgePriceExtension obj1 = (IHedgePriceExtension) o1;
		IHedgePriceExtension obj2 = (IHedgePriceExtension) o2;

		int result = obj1.getEndDate().compareTo(obj2.getEndDate());

		if (result == 0) {
			result = compareByCommonRefID(o1, o2);
		}
		return result;
	}

	/**
	 * Helper method to compare using common reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByCommonRefID(Object o1, Object o2) {
		IHedgePriceExtension obj1 = (IHedgePriceExtension) o1;
		IHedgePriceExtension obj2 = (IHedgePriceExtension) o2;

		Long id1 = new Long(obj1.getCommonReferenceID());
		Long id2 = new Long(obj2.getCommonReferenceID());

		return id1.compareTo(id2);
	}
}