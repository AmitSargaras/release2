/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/UOMWrapperComparator.java,v 1.1 2004/10/20 08:54:44 hshii Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.util.Comparator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/10/20 08:54:44 $ Tag: $Name: $
 */
public class UOMWrapperComparator implements Comparator {
	public UOMWrapperComparator() {
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
	 * Default comparison for UOMWrapperComparator. It compares using UOMWrapper
	 * label.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		String id1 = ((UOMWrapper) o1).getLabel();
		String id2 = ((UOMWrapper) o2).getLabel();
		return id1.compareToIgnoreCase(id2);
	}
}
