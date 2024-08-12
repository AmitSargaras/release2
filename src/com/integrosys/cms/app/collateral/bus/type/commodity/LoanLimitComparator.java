/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/LoanLimitComparator.java,v 1.2 2006/09/15 12:44:09 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Comparator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/09/15 12:44:09 $ Tag: $Name: $
 */
public class LoanLimitComparator implements Comparator {
	/**
	 * Default constructor.
	 */
	public LoanLimitComparator() {
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
	 * Default comparison for LoanLimitComparator. It compares using limitID and
	 * coBorrowerLimitID.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		ILoanLimit obj1 = (ILoanLimit) o1;
		ILoanLimit obj2 = (ILoanLimit) o2;
		if (obj1.getLimitID() - obj2.getLimitID() > 0) {
			return 1;
		}
		else if (obj1.getLimitID() - obj2.getLimitID() < 0) {
			return -1;
		}
		else if (obj1.getLimitID() == obj2.getLimitID()) {
			if (obj1.getCoBorrowerLimitID() - obj2.getCoBorrowerLimitID() > 0) {
				return 1;
			}
			else if (obj1.getCoBorrowerLimitID() - obj2.getCoBorrowerLimitID() < 0) {
				return -1;
			}
		}
		return 0;
	}
}
