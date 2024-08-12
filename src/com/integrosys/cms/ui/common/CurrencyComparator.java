/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/CurrencyComparator.java,v 1.1 2003/09/09 06:07:28 kllee Exp $
 */
package com.integrosys.cms.ui.common;

import java.util.Comparator;

import com.integrosys.base.businfra.currency.ICurrency;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This comparator sort on currency
 * 
 * @author $Author: kllee $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/09 06:07:28 $ Tag: $Name: $
 */
public class CurrencyComparator implements Comparator {
	/**
	 * Default constructor
	 */
	public CurrencyComparator() {
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
				ICurrency obj1 = (ICurrency) o1;
				ICurrency obj2 = (ICurrency) o2;

				String d1 = obj1.getCurrencyCode();
				String d2 = obj2.getCurrencyCode();
				return d1.compareTo(d2);
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Caught Exception in CurrencyComparator!", e);
				throw new RuntimeException(e.toString());
			}
		}
	}
}
