package com.integrosys.cms.ui.common;

import java.util.Comparator;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Jun 20, 2006 Time: 2:13:29 PM
 * To change this template use File | Settings | File Templates.
 */

public class StringComparator implements Comparator {

	/**
	 * Default constructor
	 */

	public StringComparator() {
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
				String s1 = o1.toString();
				String s2 = o2.toString();
				return s1.compareTo(s2);
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Caught Exception in StringComparator!", e);
				throw new RuntimeException(e.toString());
			}
		}
	}
}
