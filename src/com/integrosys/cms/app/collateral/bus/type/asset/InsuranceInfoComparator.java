/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/InsuranceInfoComparator.java,v 1.2 2005/08/12 08:21:33 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset;

import java.util.Comparator;

import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;

/**
 * This comparator compares Insurance Info at asset based.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/12 08:21:33 $ Tag: $Name: $
 */
public class InsuranceInfoComparator implements Comparator {
	public static final String COMPARE_BY_REF_ID = "R";

	public static final String COMPARE_BY_POLICY_NO = "P";

	private String compareBy;

	/**
	 * Default constructor, compared using charge id.
	 */
	public InsuranceInfoComparator() {
		compareBy = COMPARE_BY_REF_ID;
	}

	/**
	 * Constructs insurance info comparator based on COMPARE_BY_REF_ID or
	 * COMPARE_BY_POLICY_NO.
	 * 
	 * @param compareBy of type String
	 */
	public InsuranceInfoComparator(String compareBy) {
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

		if (compareBy.equals(COMPARE_BY_POLICY_NO)) {
			return compareByPolicyNo(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for ValuationComparator. It compares using valuation
	 * id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		if ((o1 instanceof CompareResult) && (o2 instanceof CompareResult)) {
			CompareResult obj1 = (CompareResult) o1;
			CompareResult obj2 = (CompareResult) o2;

			String id1 = ((IInsurancePolicy) (obj1.getObj())).getRefID();
			String id2 = ((IInsurancePolicy) (obj2.getObj())).getRefID();
			return id1.compareTo(id2);

		}
		else {
			IInsurancePolicy obj1 = (IInsurancePolicy) o1;
			IInsurancePolicy obj2 = (IInsurancePolicy) o2;

			String id1 = obj1.getRefID();
			String id2 = obj2.getRefID();
			return id1.compareTo(id2);
		}
	}

	/**
	 * Helper method to compare using sci map id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByPolicyNo(Object o1, Object o2) {
		IInsurancePolicy obj1 = (IInsurancePolicy) o1;
		IInsurancePolicy obj2 = (IInsurancePolicy) o2;

		String id1 = obj1.getRefID();
		String id2 = obj2.getRefID();
		return id1.compareTo(id2);
	}
}
