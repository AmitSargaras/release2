/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/StockComparator.java,v 1.1 2005/03/31 07:45:24 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.util.Comparator;

import com.integrosys.base.techinfra.diff.CompareResult;

/**
 * This comparator compares buyer using its name.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/03/31 07:45:24 $ Tag: $Name: $
 */
public class GCStockDetComparator implements Comparator {
	public static final String COMPARE_BY_STOCK_ID = "S";

	public static final String COMPARE_BY_PRIMARY_KEY = "K";

	private String compareBy;

	/**
	 * Default constructor, compared using StockComparator.COMPARE_BY_STOCK_ID.
	 */
	public GCStockDetComparator() {
		compareBy = COMPARE_BY_STOCK_ID;
	}

	/**
	 * Constructs Stock comparator based on COMPARE_BY_STOCK_ID or
	 * COMPARE_BY_PRIMARY_KEY.
	 * 
	 * @param compareBy of type String
	 */
	public GCStockDetComparator(String compareBy) {
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

		if (compareBy.equals(COMPARE_BY_STOCK_ID)) {
			return defaultCompare(o1, o2);
		}
		else {
		//	return compareByPrimaryKey(o1, o2);
			return compareBySequance(o1, o2);
			
		}
	}

	/**
	 * Default comparison for StockComparator. It compares using Stock ID.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		if ((o1 instanceof CompareResult) && (o2 instanceof CompareResult)) {
			CompareResult obj1 = (CompareResult) o1;
			CompareResult obj2 = (CompareResult) o2;

			long id1 = ((IGeneralChargeStockDetails) (obj1.getObj())).getGeneralChargeStockDetailsID();
			long id2 = ((IGeneralChargeStockDetails) (obj2.getObj())).getGeneralChargeStockDetailsID();
			return String.valueOf(id1).compareTo(String.valueOf(id2));

		}
		else {
			IGeneralChargeStockDetails obj1 = (IGeneralChargeStockDetails) o1;
			IGeneralChargeStockDetails obj2 = (IGeneralChargeStockDetails) o2;

			long id1 = obj1.getGeneralChargeStockDetailsID();
			long id2 = obj2.getGeneralChargeStockDetailsID();
		//	return id1.compareTo(id2);
			return String.valueOf(id1).compareTo(String.valueOf(id2));
		}
	}

	/**
	 * Default comparison for StockComparator. It compares using CMS Primary key
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByPrimaryKey(Object o1, Object o2) {
		if ((o1 instanceof CompareResult) && (o2 instanceof CompareResult)) {
			CompareResult obj1 = (CompareResult) o1;
			CompareResult obj2 = (CompareResult) o2;

			Long id1 = new Long(((IGeneralChargeStockDetails) (obj1.getObj())).getGeneralChargeStockDetailsID());
			Long id2 = new Long(((IGeneralChargeStockDetails) (obj2.getObj())).getGeneralChargeStockDetailsID());
			return id1.compareTo(id2);

		}
		else {
			IGeneralChargeStockDetails obj1 = (IGeneralChargeStockDetails) o1;
			IGeneralChargeStockDetails obj2 = (IGeneralChargeStockDetails) o2;

			Long id1 = new Long(obj1.getGeneralChargeStockDetailsID());
			Long id2 = new Long(obj2.getGeneralChargeStockDetailsID());
			return id1.compareTo(id2);
		}
	}
	
	private int compareBySequance(Object o1, Object o2) {
		if ((o1 instanceof CompareResult) && (o2 instanceof CompareResult)) {
			CompareResult obj1 = (CompareResult) o1;
			CompareResult obj2 = (CompareResult) o2;

			Long id1 = new Long(((IGeneralChargeStockDetails) (obj1.getObj())).getCmsRefId());
			Long id2 = new Long(((IGeneralChargeStockDetails) (obj2.getObj())).getCmsRefId());
			return id1.compareTo(id2);

		}
		else {
			IGeneralChargeStockDetails obj1 = (IGeneralChargeStockDetails) o1;
			IGeneralChargeStockDetails obj2 = (IGeneralChargeStockDetails) o2;

			Long id1 = new Long(obj1.getCmsRefId());
			Long id2 = new Long(obj2.getCmsRefId());
			return id1.compareTo(id2);
		}
	}
}
