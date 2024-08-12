/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/StockExchangeComparator.java,v 1.1 2005/07/29 10:51:08 hshii Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import java.util.Comparator;

/**
 * This comparator compares buyer using its name.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/07/29 10:51:08 $ Tag: $Name: $
 */
public class StockExchangeComparator implements Comparator {
	/**
	 * Default constructor, compared using StockExchangeComparator.BY_NAME.
	 */
	public StockExchangeComparator() {
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
	 * Default comparison for StockExchangeComparator. It compares using buyer
	 * name.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		IStockExchange obj1 = (IStockExchange) o1;
		IStockExchange obj2 = (IStockExchange) o2;

		return obj1.getStockExchangeName().compareToIgnoreCase(obj2.getStockExchangeName());
	}
}
