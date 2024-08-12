package com.integrosys.cms.app.creditriskparam.trx.policycap;

import java.util.Comparator;

import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;

/**
 * Purpose: Policy Cap Group Command Class which prepare transaction object
 * 
 * @author $Author: siewkheat $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 3/SEP/2007 $ Tag: $Name: $
 */
public class PolicyCapComparator implements Comparator {

	/**
	 * Comparing 2 IPolicyCap objects
	 */
	public int compare(Object ob1, Object ob2) {
		IPolicyCap pc1 = (IPolicyCap) ob1;
		IPolicyCap pc2 = (IPolicyCap) ob2;

		return pc1.getBoard().compareTo(pc2.getBoard());
	}

}
