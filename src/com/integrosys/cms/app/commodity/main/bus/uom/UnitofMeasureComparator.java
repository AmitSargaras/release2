/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/UnitofMeasureComparator.java,v 1.4 2004/08/20 02:21:07 hshii Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

import java.util.Comparator;

import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileComparator;

/**
 * This comparator compares unit of measure.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/20 02:21:07 $ Tag: $Name: $
 */
public class UnitofMeasureComparator implements Comparator {

	public static final String BY_REF_ID = "ID";

	public static final String BY_PROFILE = "PF";

	public static final String BY_NAME = "NM";

	private String compareBy;

	private ProfileComparator profileComp;

	/**
	 * Default constructor, compared using UnitofMeasureComparator.BY_REF_ID.
	 */
	public UnitofMeasureComparator() {
		compareBy = BY_REF_ID;
	}

	/**
	 * Constructs unit of measure comparator based on
	 * UnitofMeasureComparator.BY_REF_ID or UnitofMeasureComparator.BY_PROFILE
	 * or UnitofMeasureComparator.BY_NAME.
	 * 
	 * @param compareBy of type String
	 */
	public UnitofMeasureComparator(String compareBy) {
		this.compareBy = compareBy;
		profileComp = (!compareBy.equals(BY_PROFILE)) ? null : new ProfileComparator(
				ProfileComparator.BY_CAT_PDTTYPE_SUBTYPE);
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
		if (compareBy.equals(BY_PROFILE)) {
			return compareByProfile(o1, o2);
		}
		else if (compareBy.equals(BY_NAME)) {
			return compareByName(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for UnitofMeasureComparator. It compares using unit of
	 * measure common reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		Long id1 = null;
		Long id2 = null;
		if (o1 instanceof CompareResult) {
			IUnitofMeasure uom1 = (IUnitofMeasure) ((CompareResult) o1).getObj();
			id1 = new Long(uom1.getCommonReferenceID());
		}
		else {
			id1 = new Long(((IUnitofMeasure) o1).getCommonReferenceID());
		}

		if (o2 instanceof CompareResult) {
			IUnitofMeasure uom2 = (IUnitofMeasure) ((CompareResult) o2).getObj();
			id2 = new Long(uom2.getCommonReferenceID());
		}
		else {
			id2 = new Long(((IUnitofMeasure) o2).getCommonReferenceID());
		}

		return id1.compareTo(id2);
	}

	/**
	 * Helper method to compare unit of measure by name.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByName(Object o1, Object o2) {
		String name1 = null;
		String name2 = null;
		if (o1 instanceof CompareResult) {
			name1 = ((IUnitofMeasure) ((CompareResult) o1).getObj()).getName();
		}
		else {
			name1 = ((IUnitofMeasure) o1).getName();
		}

		if (o2 instanceof CompareResult) {
			name2 = ((IUnitofMeasure) ((CompareResult) o2).getObj()).getName();
		}
		else {
			name2 = ((IUnitofMeasure) o2).getName();
		}

		return name1.compareTo(name2);
	}

	/**
	 * Helper method to compare unit of measure by profile and name.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByProfile(Object o1, Object o2) {
		IUnitofMeasure obj1 = null;
		IUnitofMeasure obj2 = null;
		if (o1 instanceof CompareResult) {
			obj1 = (IUnitofMeasure) ((CompareResult) o1).getObj();
		}
		else {
			obj1 = (IUnitofMeasure) o1;
		}

		if (o2 instanceof CompareResult) {
			obj2 = (IUnitofMeasure) ((CompareResult) o2).getObj();
		}
		else {
			obj2 = (IUnitofMeasure) o2;
		}

		Long profileID1 = new Long(obj1.getProfileID());
		Long profileID2 = new Long(obj2.getProfileID());

		int result = 0;
		// compare profile if ID is different
		if ((profileID1.compareTo(profileID2) != 0)
				&& ((result = profileComp.compare(obj1.getCommodityProfile(), obj2.getCommodityProfile())) != 0)) {
			return result;
		}

		return compareByName(obj1, obj2);
	}

}
