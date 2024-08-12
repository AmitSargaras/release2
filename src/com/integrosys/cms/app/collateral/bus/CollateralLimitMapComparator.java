/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralLimitMapComparator.java,v 1.2 2005/03/28 06:23:48 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Comparator;

/**
 * This comparator compares Collateral Limit Maps.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/28 06:23:48 $ Tag: $Name: $
 */
public class CollateralLimitMapComparator implements Comparator {
	public static final String COMPARE_BY_CHARGE_ID = "C";

	public static final String COMPARE_BY_SCI_MAP_ID = "M";

	private String compareBy;

	/**
	 * Default constructor, compared using charge id.
	 */
	public CollateralLimitMapComparator() {
		compareBy = COMPARE_BY_SCI_MAP_ID;
	}

	/**
	 * Constructs limit map comparator based on COMPARE_BY_CHARGE_ID or
	 * COMPARE_BY_SCI_MAP_ID.
	 * 
	 * @param compareBy of type String
	 */
	public CollateralLimitMapComparator(String compareBy) {
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

		if (compareBy.equals(COMPARE_BY_SCI_MAP_ID)) {
			return defaultCompare(o1, o2);
		}
		else {
			return companyByCMSChargeID(o1, o2);
		}
	}

	/**
	 * Default comparison for CollateralLimitMapComparator. It compares
	 * limit-security linkage reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		if ((o1 instanceof ICollateralSearchResult) && (o2 instanceof ICollateralSearchResult)) {
			ICollateralSearchResult obj1 = (ICollateralSearchResult) o1;
			ICollateralSearchResult obj2 = (ICollateralSearchResult) o2;

			Long id1 = new Long(obj1.getSCILimitSecMapID());
			Long id2 = new Long(obj2.getSCILimitSecMapID());
			return id1.compareTo(id2);
		}
		else {
			ICollateralLimitMap obj1 = (ICollateralLimitMap) o1;
			ICollateralLimitMap obj2 = (ICollateralLimitMap) o2;

			Long id1 = new Long(obj1.getSCISysGenID());
			Long id2 = new Long(obj2.getSCISysGenID());
			return id1.compareTo(id2);
		}
	}

	/**
	 * Helper method to compare using cms charge id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int companyByCMSChargeID(Object o1, Object o2) {
		if ((o1 instanceof ICollateralSearchResult) && (o2 instanceof ICollateralSearchResult)) {
			ICollateralSearchResult obj1 = (ICollateralSearchResult) o1;
			ICollateralSearchResult obj2 = (ICollateralSearchResult) o2;

			Long id1 = new Long(obj1.getLimitSecMapID());
			Long id2 = new Long(obj2.getLimitSecMapID());
			return id1.compareTo(id2);
		}
		else {
			ICollateralLimitMap obj1 = (ICollateralLimitMap) o1;
			ICollateralLimitMap obj2 = (ICollateralLimitMap) o2;

			Long id1 = new Long(obj1.getChargeID());
			Long id2 = new Long(obj2.getChargeID());
			return id1.compareTo(id2);
		}
	}
}