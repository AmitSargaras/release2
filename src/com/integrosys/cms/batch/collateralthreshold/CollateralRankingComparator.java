/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/collateralthreshold/CollateralRankingComparator.java,v 1.1 2003/09/11 10:55:43 kllee Exp $
 */
package com.integrosys.cms.batch.collateralthreshold;

import java.util.Comparator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;

/**
 * This comparator sort on ILimitCharge.getSecurityRanking
 * 
 * @author $Author: kllee $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 10:55:43 $ Tag: $Name: $
 */
public class CollateralRankingComparator implements Comparator {
	/**
	 * Default constructor
	 */
	public CollateralRankingComparator() {
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
		else {
			try {
				ILimitCharge obj1 = (ILimitCharge) o1;
				ILimitCharge obj2 = (ILimitCharge) o2;

				int d1 = obj1.getSecurityRank();
				int d2 = obj2.getSecurityRank();

				if (d1 == d2) {
					return 0;
				}
				else if (d1 < d2) {
					return -1;
				}
				else {
					return 1;
				}
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Caught Exception in CollateralRankingComparator!", e);
				throw new RuntimeException(e.toString());
			}
		}
	}
}
