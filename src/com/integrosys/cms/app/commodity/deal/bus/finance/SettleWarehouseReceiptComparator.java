/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/SettleWarehouseReceiptComparator.java,v 1.2 2004/06/18 11:43:54 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.util.Comparator;

import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;

/**
 * This comparator compares settlement warehouse receipt using its common
 * reference id or original receipt number.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/18 11:43:54 $ Tag: $Name: $
 */
public class SettleWarehouseReceiptComparator implements Comparator {
	public static final String BY_WR_REF_ID = "ID";

	public static final String BY_ORIG_WR_NO = "NO";

	private String compareBy;

	/**
	 * Default constructor, compared using
	 * SettleWarehouseReceiptComparator.BY_WR_REF_ID.
	 */
	public SettleWarehouseReceiptComparator() {
		compareBy = BY_WR_REF_ID;
	}

	/**
	 * Constructs warehouse receipt comparator based on
	 * SettleWarehouseReceiptComparator.BY_WR_REF_ID or
	 * SettleWarehouseReceiptComparator.BY_ORIG_WR_NO.
	 * 
	 * @param compareBy of type String
	 */
	public SettleWarehouseReceiptComparator(String compareBy) {
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

		if (compareBy.equals(BY_ORIG_WR_NO)) {
			return compareByOrigWRNo(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for SettleWarehouseReceiptComparator. It compares
	 * using common reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		Long id1 = new Long(((ISettleWarehouseReceipt) o1).getRefID());
		Long id2 = new Long(((ISettleWarehouseReceipt) o2).getRefID());
		return id1.compareTo(id2);
	}

	/**
	 * Helper method to compare using warehouse original receipt number.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByOrigWRNo(Object o1, Object o2) {
		IWarehouseReceipt obj1 = ((ISettleWarehouseReceipt) o1).getWarehouseReceipt();
		IWarehouseReceipt obj2 = ((ISettleWarehouseReceipt) o2).getWarehouseReceipt();

		return obj1.getOrigReceiptNo().compareTo(obj2.getOrigReceiptNo());
	}
}