/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralPledgorComparator.java,v 1.1 2004/07/26 10:41:18 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Comparator;

/**
 * This comparator compares Peldgor using sci pledgor id or pledgor name.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/26 10:41:18 $ Tag: $Name: $
 */
public class CollateralPledgorComparator implements Comparator {
	public static final int COMPARE_BY_SCI_PLEDGOR_ID = 1;

	public static final int COMPARE_BY_PLEDGOR_NAME = 2;

	private int compareBy;

	/**
	 * Default constructor, compared using SCI pledgor id.
	 */
	public CollateralPledgorComparator() {
		compareBy = COMPARE_BY_SCI_PLEDGOR_ID;
	}

	/**
	 * Constructs Valuation comparator based on
	 * CollateralPledgorComparator.COMPARE_BY_SCI_PLEDGOR_ID or
	 * CollateralPledgorComparator.COMPARE_BY_PLEDGOR_NAME.
	 * 
	 * @param compareBy of type String
	 */
	public CollateralPledgorComparator(int compareBy) {
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

		if (compareBy == COMPARE_BY_PLEDGOR_NAME) {
			return compareByPledgorName(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for Collateral Pledgor Comparator. It compares using
	 * SCI pledgor id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		ICollateralPledgor obj1 = (ICollateralPledgor) o1;
		ICollateralPledgor obj2 = (ICollateralPledgor) o2;

		Long id1 = new Long(obj1.getSCIPledgorID());
		Long id2 = new Long(obj2.getSCIPledgorID());
		return id1.compareTo(id2);
	}

	/**
	 * Helper method to compare using pledgor name.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByPledgorName(Object o1, Object o2) {
		String name1 = ((ICollateralPledgor) o1).getPledgorName();
		String name2 = ((ICollateralPledgor) o2).getPledgorName();

		if ((name1 == null) && (name2 == null)) {
			return 0;
		}
		else if (name1 == null) {
			return -1;
		}
		else if (name2 == null) {
			return 1;
		}
		else {
			return name1.compareTo(name2);
		}
	}
}
