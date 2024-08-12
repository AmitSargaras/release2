/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/FixedAssetOthersComparator.java,v 1.1 2005/03/31 07:45:24 hshii Exp $
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
public class FixedAssetOthersComparator implements Comparator {
	public static final String COMPARE_BY_FAO_ID = "F";

	public static final String COMPARE_BY_PRIMARY_KEY = "K";

	private String compareBy;

	/**
	 * Default constructor, compared using
	 * FixedAssetOthersComparator.COMPARE_BY_FAO_ID.
	 */
	public FixedAssetOthersComparator() {
		compareBy = COMPARE_BY_FAO_ID;
	}

	/**
	 * Constructs FAO comparator based on COMPARE_BY_FAO_ID or
	 * COMPARE_BY_PRIMARY_KEY.
	 * 
	 * @param compareBy of type String
	 */
	public FixedAssetOthersComparator(String compareBy) {
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

		if (compareBy.equals(COMPARE_BY_FAO_ID)) {
			return defaultCompare(o1, o2);
		}
		else {
			return compareByPrimaryKey(o1, o2);
		}
	}

	/**
	 * Default comparison for FixedAssetOthersComparator. It compares using FAO
	 * ID.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		if ((o1 instanceof CompareResult) && (o2 instanceof CompareResult)) {
			CompareResult obj1 = (CompareResult) o1;
			CompareResult obj2 = (CompareResult) o2;

			String id1 = ((IFixedAssetOthers) (obj1.getObj())).getFAOID();
			String id2 = ((IFixedAssetOthers) (obj2.getObj())).getFAOID();
			return id1.compareTo(id2);

		}
		else {
			IFixedAssetOthers obj1 = (IFixedAssetOthers) o1;
			IFixedAssetOthers obj2 = (IFixedAssetOthers) o2;

			String id1 = obj1.getFAOID();
			String id2 = obj1.getFAOID();
			return id1.compareTo(id2);
		}
	}

	/**
	 * Default comparison for FixedAssetOthersComparator. It compares using CMS
	 * Primary key
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByPrimaryKey(Object o1, Object o2) {
		if ((o1 instanceof CompareResult) && (o2 instanceof CompareResult)) {
			CompareResult obj1 = (CompareResult) o1;
			CompareResult obj2 = (CompareResult) o2;

			Long id1 = new Long(((IFixedAssetOthers) (obj1.getObj())).getAssetGCFixedAssetOthersID());
			Long id2 = new Long(((IFixedAssetOthers) (obj2.getObj())).getAssetGCFixedAssetOthersID());
			return id1.compareTo(id2);

		}
		else {
			IFixedAssetOthers obj1 = (IFixedAssetOthers) o1;
			IFixedAssetOthers obj2 = (IFixedAssetOthers) o2;

			Long id1 = new Long(obj1.getAssetGCFixedAssetOthersID());
			Long id2 = new Long(obj1.getAssetGCFixedAssetOthersID());
			return id1.compareTo(id2);
		}
	}
}
