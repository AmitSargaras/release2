/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/WarehouseComparator.java,v 1.4 2005/05/10 07:03:51 hshii Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

import java.util.Comparator;

import com.integrosys.base.techinfra.diff.CompareResult;

/**
 * This comparator compares warehouse by common reference id or warehouse name
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/05/10 07:03:51 $ Tag: $Name: $
 */
public class WarehouseComparator implements Comparator {

	public static final String BY_REF_ID = "ID";

	public static final String BY_NAME = "NM";

	private String compareBy;

	/**
	 * Default constructor, compared using WarehouseComparator.BY_REF_ID.
	 */
	public WarehouseComparator() {
		compareBy = BY_REF_ID;
	}

	/**
	 * Constructs profile comparator based on WarehouseComparator.BY_REF_ID or
	 * WarehouseComparator.BY_NAME.
	 * 
	 * @param compareBy of type String
	 */
	public WarehouseComparator(String compareBy) {
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
		if (compareBy.equals(BY_NAME)) {
			return compareByName(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for WarehouseComparator. It compares using warehouse
	 * common reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		Long id1 = null;
		Long id2 = null;

		if (o1 instanceof CompareResult) {
			IWarehouse obj1 = (IWarehouse) ((CompareResult) o1).getObj();
			id1 = new Long(obj1.getCommonRef());
		}
		else {
			id1 = new Long(((IWarehouse) o1).getCommonRef());
		}

		if (o2 instanceof CompareResult) {
			IWarehouse obj2 = (IWarehouse) ((CompareResult) o2).getObj();
			id2 = new Long(obj2.getCommonRef());
		}
		else {
			id2 = new Long(((IWarehouse) o2).getCommonRef());
		}

		return id1.compareTo(id2);
	}

	/**
	 * Helper method to compare warehouse by warehouse name.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByName(Object o1, Object o2) {
		IWarehouse obj1 = null;
		IWarehouse obj2 = null;

		if (o1 instanceof CompareResult) {
			obj1 = (IWarehouse) ((CompareResult) o1).getObj();
		}
		else {
			obj1 = (IWarehouse) o1;
		}

		if (o2 instanceof CompareResult) {
			obj2 = (IWarehouse) ((CompareResult) o2).getObj();
		}
		else {
			obj2 = (IWarehouse) o2;
		}

		return obj1.getName().compareToIgnoreCase(obj2.getName());
	}
}
