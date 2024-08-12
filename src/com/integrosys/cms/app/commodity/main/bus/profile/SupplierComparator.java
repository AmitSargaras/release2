/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/SupplierComparator.java,v 1.3 2006/09/06 09:47:45 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

import java.util.Comparator;

/**
 * This comparator compares supplier using its name.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/09/06 09:47:45 $ Tag: $Name: $
 */
public class SupplierComparator implements Comparator {
	/**
	 * Default constructor.
	 */
	public SupplierComparator() {
		super();
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

		return defaultCompare(o1, o2);
	}

	/**
	 * Default comparison for SupplierComparator. It compares using supplier
	 * name.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		ISupplier obj1 = (ISupplier) o1;
		ISupplier obj2 = (ISupplier) o2;

		return obj1.getName().compareTo(obj2.getName());
	}
}
