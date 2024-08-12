/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/GuarantorComparator.java,v 1.1 2004/06/25 01:31:20 hshii Exp $
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
public class GuarantorComparator implements Comparator {
	/**
	 * Default constructor.
	 */
	public GuarantorComparator() {
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
		IGuarantor obj1 = (IGuarantor) o1;
		IGuarantor obj2 = (IGuarantor) o2;

		return obj1.getName().compareTo(obj2.getName());
	}

}
