/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/TATComparator.java,v 1.1 2003/08/06 06:24:13 phtan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Comparator;
import java.util.Date;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This comparator compares ITATEntries using the <code>getTATStamp</code>
 * field.
 * 
 * @author $Author: phtan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 06:24:13 $ Tag: $Name: $
 */
public class TATComparator implements Comparator {
	/**
	 * Default constructor
	 */
	public TATComparator() {
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
				ITATEntry obj1 = (ITATEntry) o1;
				ITATEntry obj2 = (ITATEntry) o2;

				Date d1 = obj1.getTATStamp();
				Date d2 = obj2.getTATStamp();
				return d1.compareTo(d2);
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Caught Exception in TATComparator!", e);
				throw new RuntimeException(e.toString());
			}
		}
	}
}
