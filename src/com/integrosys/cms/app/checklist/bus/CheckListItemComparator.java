/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CheckListItemComparator.java,v 1.1 2005/01/06 02:35:50 wltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import java.util.Comparator;

/**
 * This comparator compares deal search using TP Deal Reference no or deal no.
 * 
 * @author $Author<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/01/06 02:35:50 $ Tag: $Name: $
 */
public class CheckListItemComparator implements Comparator {
	public static final String BY_ITEM_REF = "1";

	private String compareBy;

	/**
	 * Default constructor, compared using CheckListItemComparator.BY_ITEM_REF.
	 */
	public CheckListItemComparator() {
		compareBy = BY_ITEM_REF;
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
	 * Default comparison for CheckListItemComparator. It compares checklist
	 * item reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		Long id1 = new Long(((ICheckListItem) o1).getCheckListItemRef());
		Long id2 = new Long(((ICheckListItem) o2).getCheckListItemRef());
		return id1.compareTo(id2);
	}

}
