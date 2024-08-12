/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/LoanScheduleComparator.java,v 1.1 2004/06/25 01:31:20 hshii Exp $
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
public class LoanScheduleComparator implements Comparator {
	/**
	 * Default constructor.
	 */
	public LoanScheduleComparator() {
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
		ILoanSchedule obj1 = (ILoanSchedule) o1;
		ILoanSchedule obj2 = (ILoanSchedule) o2;

		return obj1.getPaymentDate().compareTo(obj2.getPaymentDate());
	}
}
