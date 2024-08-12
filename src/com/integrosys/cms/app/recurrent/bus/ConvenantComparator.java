/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ConvenantComparator.java
 */
package com.integrosys.cms.app.recurrent.bus;

import com.integrosys.base.techinfra.diff.CompareResult;

import java.util.Comparator;

/**
 * This comparator compares IConvenant using
 * ItemType(Convenant,RiskTrigger,Fee).
 * 
 * @author $Author<br>
 * @version $Revision:
 * @since $Date: Tag: $Name: $
 */
public class ConvenantComparator implements Comparator {
	public static final String BY_ITEM_TYPE = "1";

	private String compareBy;

	/**
	 * Default constructor
	 */
	public ConvenantComparator() {
		compareBy = BY_ITEM_TYPE;
	}

	/**
	 * Compares o1 and o2. It returns a negative integer, zero, or a positive
	 * integer as the first argument is less than, equal to, or greater than the
	 * second.
	 * 
	 * @return a negative integer, zero, or a positive integer
	 */
	public int compare(Object o1, Object o2) {
		int returnValue = 0;
		IConvenant obj1 = null;
		IConvenant obj2 = null;
		if (o1 instanceof CompareResult) {
			obj1 = (IConvenant) ((CompareResult) o1).getObj();
		}
		else {
			obj1 = (IConvenant) o1;
		}

		if (o2 instanceof CompareResult) {
			obj2 = (IConvenant) ((CompareResult) o2).getObj();
		}
		else {
			obj2 = (IConvenant) o2;
		}

		String type1 = null;
		String type2 = null;

		boolean covenant1 = false;
		boolean fee1 = obj1.getFee();
		boolean riskTrigger1 = obj1.getRiskTrigger();
		if (!fee1 && !riskTrigger1) {
			covenant1 = true;
		}
		boolean covenant2 = false;
		boolean fee2 = obj2.getFee();
		boolean riskTrigger2 = obj2.getRiskTrigger();
		if (!fee2 && !riskTrigger2) {
			covenant2 = true;
		}
		if (covenant1) {
			type1 = "acovenant";
		}
		else if (riskTrigger1) {
			type1 = "briskTrigger1";
		}
		else if (fee1) {
			type1 = "cfee";
		}

		if (covenant2) {
			type2 = "acovenant";
		}
		else if (riskTrigger2) {
			type2 = "briskTrigger1";
		}
		else if (fee2) {
			type2 = "cfee";
		}

		return type1.compareTo(type2);

	}
}
