/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/DealSearchComparator.java,v 1.1 2004/07/16 06:15:09 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.util.Comparator;

/**
 * This comparator compares deal search using TP Deal Reference no or deal no.
 * 
 * @author $Author<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/16 06:15:09 $ Tag: $Name: $
 */
public class DealSearchComparator implements Comparator {
	public static final String BY_TP_DEAL_REF = "1";

	public static final String BY_DEAL_NO = "2";

	private String compareBy;

	/**
	 * Default constructor, compared using DealSearchComparator.BY_TP_DEAL_REF.
	 */
	public DealSearchComparator() {
		compareBy = BY_TP_DEAL_REF;
	}

	/**
	 * Constructs deal search comparator based on
	 * DealSearchComparator.BY_TP_DEAL_REF or DealSearchComparator.BY_DEAL_NO.
	 * 
	 * @param compareBy of type String
	 */
	public DealSearchComparator(String compareBy) {
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

		if (compareBy.equals(BY_DEAL_NO)) {
			return compareByDealNo(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for DealSearchComparator. It compares TP Deal
	 * Reference No.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		String id1 = new String(((ICommodityDealSearchResult) o1).getDealReferenceNo());
		String id2 = new String(((ICommodityDealSearchResult) o2).getDealReferenceNo());
		return id1.compareTo(id2);
	}

	/**
	 * Helper method to compare using deal no.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByDealNo(Object o1, Object o2) {
		ICommodityDealSearchResult obj1 = (ICommodityDealSearchResult) o1;
		ICommodityDealSearchResult obj2 = (ICommodityDealSearchResult) o2;

		return obj1.getDealNo().compareTo(obj2.getDealNo());
	}
}
