/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/ProfileComparator.java,v 1.8 2004/08/19 02:08:00 hshii Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

import java.util.Comparator;

import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;

/**
 * This comparator compares profile in the following order 1. Commodity Category
 * 2. Commodity Product Type 3. Commodity Sub-type
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.8 $
 * @since $Date: 2004/08/19 02:08:00 $ Tag: $Name: $
 */
public class ProfileComparator implements Comparator {

	public static final String BY_REF_ID = "ID";

	public static final String BY_CAT_PDTTYPE_SUBTYPE = "TP";

	private String compareBy;

	/**
	 * Default constructor, compared using ProfileComparator.BY_REF_ID.
	 */
	public ProfileComparator() {
		compareBy = BY_REF_ID;
	}

	/**
	 * Constructs profile comparator based on ProfileComparator.BY_REF_ID or
	 * ProfileComparator.BY_CAT_PDTTYPE_SUBTYPE.
	 * 
	 * @param compareBy of type String
	 */
	public ProfileComparator(String compareBy) {
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
		if (compareBy.equals(BY_CAT_PDTTYPE_SUBTYPE)) {
			return compareByCategoryPdtTypePdtSubType(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for ProfileComparator. It compares using profile
	 * common reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		Long id1 = null;
		Long id2 = null;
		if (o1 instanceof CompareResult) {
			IProfile profile1 = (IProfile) ((CompareResult) o1).getObj();
			id1 = new Long(profile1.getCommonRef());
		}
		else {
			id1 = new Long(((IProfile) o1).getCommonRef());
		}

		if (o2 instanceof CompareResult) {
			IProfile profile2 = (IProfile) ((CompareResult) o2).getObj();
			id2 = new Long(profile2.getCommonRef());
		}
		else {
			id2 = new Long(((IProfile) o2).getCommonRef());
		}
		return id1.compareTo(id2);
	}

	/**
	 * Helper method to compare in the following order 1. Commodity Category 2.
	 * Commodity Product Type 3. Commodity Sub-type
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByCategoryPdtTypePdtSubType(Object o1, Object o2) {
		IProfile obj1 = null;
		IProfile obj2 = null;
		if (o1 instanceof CompareResult) {
			obj1 = (IProfile) ((CompareResult) o1).getObj();
		}
		else {
			obj1 = (IProfile) o1;
		}

		if (o2 instanceof CompareResult) {
			obj2 = (IProfile) ((CompareResult) o2).getObj();
		}
		else {
			obj2 = (IProfile) o2;
		}

		int result = 0;

		CommodityCategoryList cmdtList = CommodityCategoryList.getInstance();
		cmdtList.getCommCategoryItem(obj1.getCategory());

		String category1 = cmdtList.getCommCategoryItem(obj1.getCategory());
		String category2 = cmdtList.getCommCategoryItem(obj2.getCategory());
		if ((result = category1.compareTo(category2)) != 0) {
			return result;
		}

		String pdtType1 = cmdtList.getCommProductItem(obj1.getCategory(), obj1.getProductType());
		String pdtType2 = cmdtList.getCommProductItem(obj2.getCategory(), obj2.getProductType());
		if ((result = pdtType1.compareTo(pdtType2)) != 0) {
			return result;
		}

		return obj1.getProductSubType().compareTo(obj2.getProductSubType());
	}
}
