/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/sublimittype/SubLimitTypeComparator.java,v 1.2 2005/11/12 04:40:09 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.sublimittype;

import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.techinfra.diff.CompareResult;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-19
 * @Tag com.integrosys.cms.app.commodity.main.bus.sublimittype.
 *      SubLimitTypeComparator.java
 */
public class SubLimitTypeComparator implements Comparator {
	public static final String BY_LIMIT_TYPE = "BY_LIMIT_TYPE";

	public static final String BY_SUB_LIMIT_TYPE = "BY_SUB_LIMIT_TYPE";

	private String compareBy = null;

	private HashMap limitTypeMap = null;

	public SubLimitTypeComparator() {
		super();
	}

	public SubLimitTypeComparator(String compareBy) {
		super();
		this.compareBy = compareBy;
	}

	public SubLimitTypeComparator(HashMap limitTypeMap) {
		this.limitTypeMap = limitTypeMap;
	}

	/*
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		if ((arg0 == null) && (arg1 == null)) {
			return 0;
		}
		else if (arg0 == null) {
			return -1;
		}
		else if (arg1 == null) {
			return 1;
		}
		if ((compareBy == null) || "".equals(compareBy.trim())) {
			return defaultCompare(arg0, arg1);
		}
		else if (BY_LIMIT_TYPE.equals(compareBy)) {
			return compareByLimitType(arg0, arg1);
		}
		else {
			return compareBySubLimitType(arg0, arg1);
		}
	}

	private int defaultCompare(Object o1, Object o2) {
		ISubLimitType obj1 = convertObj2SLT(o1);
		ISubLimitType obj2 = convertObj2SLT(o2);
		String lt1 = obj1.getLimitType();
		String lt2 = obj2.getLimitType();
		if ((limitTypeMap != null) && !limitTypeMap.isEmpty()) {
			lt1 = (String) limitTypeMap.get(lt1);
			lt2 = (String) limitTypeMap.get(lt2);
		}
		int result = lt1.compareToIgnoreCase(lt2);
		if (result == 0) {
			result = obj1.getSubLimitType().compareToIgnoreCase(obj2.getSubLimitType());
		}
		return result;
	}

	private int compareByLimitType(Object o1, Object o2) {
		ISubLimitType obj1 = convertObj2SLT(o1);
		ISubLimitType obj2 = convertObj2SLT(o2);
		return obj1.getLimitType().compareToIgnoreCase(obj2.getLimitType());
	}

	private int compareBySubLimitType(Object o1, Object o2) {
		ISubLimitType obj1 = convertObj2SLT(o1);
		ISubLimitType obj2 = convertObj2SLT(o2);
		return obj1.getSubLimitType().compareToIgnoreCase(obj2.getSubLimitType());
	}

	private ISubLimitType convertObj2SLT(Object obj) {
		if (obj instanceof CompareResult) {
			return (ISubLimitType) ((CompareResult) obj).getObj();
		}
		else {
			return (ISubLimitType) obj;
		}
	}
}
