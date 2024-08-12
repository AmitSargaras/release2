/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/CreditStatusComparator.java,v 1.1 2003/08/28 04:27:41 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Comparator;
import java.util.Date;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This comparator compares ICreditStatus using the
 * <code>getEffectiveDate</code> field.
 * 
 * @author $Author: kllee $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/28 04:27:41 $ Tag: $Name: $
 */
public class CreditStatusComparator implements Comparator {
	/**
	 * Default constructor
	 */
	public CreditStatusComparator() {
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
		else {
			try {
				ICreditStatus obj1 = (ICreditStatus) o1;
				ICreditStatus obj2 = (ICreditStatus) o2;

				Date d1 = obj1.getEffectiveDate();
				Date d2 = obj2.getEffectiveDate();
				return d1.compareTo(d2);
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Caught Exception in CreditStatusComparator!", e);
				throw new RuntimeException(e.toString());
			}
		}
	}
}
