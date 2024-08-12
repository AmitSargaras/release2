/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/LimitDetailsComparator.java,v 1.2 2006/09/15 08:30:24 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Comparator;

import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/09/15 08:30:24 $ Tag: $Name: $
 */
public class LimitDetailsComparator implements Comparator {
	/**
	 * Default constructor.
	 */
	public LimitDetailsComparator() {
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
	 * Default comparison for LoanAgencyComparator. It compares using common
	 * reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		if ((o1 instanceof CompareResult) && (o2 instanceof CompareResult)) {
			Long id1 = getID(((ICollateralLimitMap) ((CompareResult) o1).getObj()));
			Long id2 = getID(((ICollateralLimitMap) ((CompareResult) o2).getObj()));
			return id1.compareTo(id2);
		}
		else {
			Long id1 = getID((ICollateralLimitMap) o1);
			Long id2 = getID((ICollateralLimitMap) o2);
			return id1.compareTo(id2);
		}
	}

	private Long getID(ICollateralLimitMap obj) {
		if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(obj.getCustomerCategory())) {
			return new Long(obj.getSCILimitID());
		}

		if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(obj.getCustomerCategory())) {
			return new Long(obj.getSCICoBorrowerLimitID());
		}
		return null;
	}
}
