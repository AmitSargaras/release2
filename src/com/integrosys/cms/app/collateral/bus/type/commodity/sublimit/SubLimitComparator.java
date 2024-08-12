/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/sublimit/SubLimitComparator.java,v 1.1 2005/10/15 06:03:33 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity.sublimit;

import java.util.Comparator;

import com.integrosys.base.techinfra.diff.CompareResult;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-19
 * @Tag 
 *      com.integrosys.cms.app.commodity.main.bus.sublimit.SubLimitComparator.java
 */
public class SubLimitComparator implements Comparator {
	public SubLimitComparator() {
		super();
	}

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

	private int defaultCompare(Object o1, Object o2) {
		ISubLimit obj1 = convertObj2SLT(o1);
		ISubLimit obj2 = convertObj2SLT(o2);
		return obj1.getSubLimitType().compareTo(obj2.getSubLimitType());
	}

	private ISubLimit convertObj2SLT(Object obj) {
		if (obj instanceof CompareResult) {
			return (ISubLimit) ((CompareResult) obj).getObj();
		}
		else {
			return (ISubLimit) obj;
		}
	}
}
