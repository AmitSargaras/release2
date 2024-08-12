/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/WarehouseReceiptComparator.java,v 1.7 2006/09/19 12:50:46 hshii Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import java.util.Comparator;
import java.util.Date;

import com.integrosys.base.techinfra.diff.CompareResult;

/**
 * This comparator compares warehouse receipt using its common reference id
 * orpatch ma
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/09/19 12:50:46 $ Tag: $Name: $
 */
public class WarehouseReceiptComparator implements Comparator {
	public static final String BY_WR_REF_ID = "ID";

	public static final String BY_ORIG_WR_NO = "NO";

	public static final String BY_WR_ISSUE_DATE = "DATE";

	private String compareBy;

	/**
	 * Default constructor, compared using
	 * WarehouseReceiptComparator.BY_WR_REF_ID.
	 */
	public WarehouseReceiptComparator() {
		compareBy = BY_WR_REF_ID;
	}

	/**
	 * Constructs warehouse receipt comparator based on
	 * WarehouseReceiptComparator.BY_WR_REF_ID or
	 * WarehouseReceiptComparator.BY_ORIG_WR_NO.
	 * 
	 * @param compareBy of type String
	 */
	public WarehouseReceiptComparator(String compareBy) {
		this.compareBy = compareBy;
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

		if (compareBy.equals(BY_ORIG_WR_NO)) {
			return compareByOrigWRNo(o1, o2);
		}
		else if (compareBy.equals(BY_WR_ISSUE_DATE)) {
			return compareByWRIssueDate(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for WarehouseReceiptComparator. It compares using
	 * warehouse common reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		Long id1 = new Long(((IWarehouseReceipt) o1).getRefID());
		Long id2 = new Long(((IWarehouseReceipt) o2).getRefID());
		return id1.compareTo(id2);
	}

	/**
	 * Helper method to compare using warehouse original receipt number.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByOrigWRNo(Object o1, Object o2) {
		IWarehouseReceipt obj1 = null;
		IWarehouseReceipt obj2 = null;

		if ((o1 instanceof CompareResult) && (o2 instanceof CompareResult)) {
			obj1 = (IWarehouseReceipt) ((CompareResult) o1).getObj();
			obj2 = (IWarehouseReceipt) ((CompareResult) o2).getObj();
		}
		else {
			obj1 = (IWarehouseReceipt) o1;
			obj2 = (IWarehouseReceipt) o2;
		}

		return obj1.getOrigReceiptNo().compareTo(obj2.getOrigReceiptNo());
	}

	/**
	 * Helper method to compare using warehouse receipt date.
	 * 
	 * @return a negative integer, zero or a positive integer if the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByWRIssueDate(Object o1, Object o2) {
		Date date1 = null;
		Date date2 = null;

		if ((o1 instanceof IWarehouseReceipt) && (o2 instanceof IWarehouseReceipt)) {
			date1 = ((IWarehouseReceipt) o1).getIssueDate();
			date2 = ((IWarehouseReceipt) o2).getIssueDate();
		}
		else if ((o1 instanceof CompareResult) && (o2 instanceof CompareResult)) {
			date1 = ((IWarehouseReceipt) ((CompareResult) o1).getObj()).getIssueDate();
			date2 = ((IWarehouseReceipt) ((CompareResult) o2).getObj()).getIssueDate();
		}

		if ((date1 == null) && (date2 == null)) {
			return compareByOrigWRNo(o1, o2);
		}
		if ((date1 == null) && (date2 != null)) {
			return 1;
		}
		if ((date1 != null) && (date2 == null)) {
			return -1;
		}

		return date1.compareTo(date2);
	}

	/**
	 * Helper method to list warehouse receipts with date!=null first and then
	 * rest.
	 * 
	 * @return array of IWarehouseReceipt
	 */
	// Added by Pratheepa for CR129
	public static IWarehouseReceipt[] listByDateFirst(IWarehouseReceipt[] receiptList) {
		IWarehouseReceipt[] receiptReturnList = null;
		int recipeListWithDate = 0;
		for (int i = 0; i < receiptList.length; i++) {
			if (receiptList[i].getIssueDate() != null) {
				recipeListWithDate++;
			}
		}

		receiptReturnList = new IWarehouseReceipt[receiptList.length];
		for (int i = receiptList.length - recipeListWithDate, recipeListWithDateIndex = 0; i < receiptList.length; i++, recipeListWithDateIndex++) {
			receiptReturnList[recipeListWithDateIndex] = receiptList[i];
		}
		for (int i = 0, recipeListWithDateIndex = recipeListWithDate; i < receiptList.length - recipeListWithDate; i++, recipeListWithDateIndex++) {
			receiptReturnList[recipeListWithDateIndex] = receiptList[i];
		}

		return receiptReturnList;
	}
}
