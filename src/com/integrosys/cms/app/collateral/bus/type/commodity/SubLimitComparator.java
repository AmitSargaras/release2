/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/SubLimitComparator.java,v 1.1 2004/06/25 01:31:20 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Comparator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/25 01:31:20 $ Tag: $Name: $
 */
public class SubLimitComparator implements Comparator {
	public static final String BY_SL_REF_ID = "ID";

	public static final String BY_PK = "PK";

	private String compareBy;

	/**
	 * Default constructor, compared using
	 * WarehouseReceiptComparator.BY_WR_REF_ID.
	 */
	public SubLimitComparator() {
		compareBy = BY_SL_REF_ID;
	}

	/**
	 * Constructs warehouse receipt comparator based on
	 * WarehouseReceiptComparator.BY_WR_REF_ID or
	 * WarehouseReceiptComparator.BY_ORIG_WR_NO.
	 * 
	 * @param compareBy of type String
	 */
	public SubLimitComparator(String compareBy) {
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

		if (compareBy.equals(BY_PK)) {
			return compareByPK(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for WarehouseReceiptComparator. It compares using
	 * warehouse common reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		Long id1 = new Long(((ISubLimit) o1).getCommonRef());
		Long id2 = new Long(((ISubLimit) o2).getCommonRef());
		return id1.compareTo(id2);
	}

	/**
	 * Helper method to compare using warehouse original receipt number.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByPK(Object o1, Object o2) {
		Long id1 = new Long(((ISubLimit) o1).getSubLimitID());
		Long id2 = new Long(((ISubLimit) o2).getSubLimitID());

		return id1.compareTo(id2);
	}

}
