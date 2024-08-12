/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/LoanAgencyComparator.java,v 1.1 2004/07/20 04:08:47 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Comparator;

/**
 * This comparator compares loan agency details using its common reference id.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/20 04:08:47 $ Tag: $Name: $
 */
public class LoanAgencyComparator implements Comparator {

	/**
	 * Default constructor.
	 */
	public LoanAgencyComparator() {
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
	 * Default comparison for LoanAgencyComparator. It compares using common
	 * reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		Long id1 = new Long(((ILoanAgency) o1).getCommonRef());
		Long id2 = new Long(((ILoanAgency) o2).getCommonRef());
		return id1.compareTo(id2);
	}
}