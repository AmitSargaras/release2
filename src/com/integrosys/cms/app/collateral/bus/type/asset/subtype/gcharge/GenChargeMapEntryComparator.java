/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/GenChargeMapEntryComparator.java,v 1.1 2005/03/21 02:33:34 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.util.Comparator;

/**
 * This comparator compares buyer using its name.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/03/21 02:33:34 $ Tag: $Name: $
 */
public class GenChargeMapEntryComparator implements Comparator {
	public static final String COMPARE_BY_INSURANCE_ID = "I";

	public static final String COMPARE_BY_ENTRY_ID = "E";

	private String compareBy;

	/**
	 * Default constructor, compared using
	 * GenChargeMapEntryComparator.COMPARE_BY_INSURANCE_ID.
	 */
	public GenChargeMapEntryComparator() {
		compareBy = COMPARE_BY_INSURANCE_ID;
	}

	/**
	 * Constructs General Charge map entry comparator based on
	 * COMPARE_BY_INSURANCE_ID or COMPARE_BY_ENTRY_ID.
	 * 
	 * @param compareBy of type String
	 */
	public GenChargeMapEntryComparator(String compareBy) {
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

		if (compareBy.equals(COMPARE_BY_INSURANCE_ID)) {
			return defaultCompare(o1, o2);
		}
		else {
			return compareByEntryValue(o1, o2);
		}
	}

	/**
	 * Default comparison for GenChargeMapEntryComparator. It compares using
	 * insurance ID.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		IGenChargeMapEntry obj1 = (IGenChargeMapEntry) o1;
		IGenChargeMapEntry obj2 = (IGenChargeMapEntry) o2;

		return obj1.getInsuranceID().compareTo(obj2.getInsuranceID());
	}

	/**
	 * Default comparison for GenChargeMapEntryComparator. It compares using
	 * entry value id (either stock id or fao id).
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByEntryValue(Object o1, Object o2) {
		IGenChargeMapEntry obj1 = (IGenChargeMapEntry) o1;
		IGenChargeMapEntry obj2 = (IGenChargeMapEntry) o2;

		return obj1.getEntryValueID().compareTo(obj2.getEntryValueID());
	}
}
