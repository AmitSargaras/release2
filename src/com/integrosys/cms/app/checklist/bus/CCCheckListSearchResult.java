/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CCCheckListSearchResult.java,v 1.2 2006/08/01 12:50:25 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * @author $Author: czhou $
 * @version $Revision: 1.2 $
 * @since $Date: 2006/08/01 12:50:25 $ Tag: $Name: $
 */
public class CCCheckListSearchResult extends CheckListSearchResult {

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Get the customer ID. This can be a borrower or a pledgor ID depending on
	 * the customer type specified.
	 * 
	 * @return long - the customer ID
	 */
	public long getCustomerID() {
		return this.customerID;
	}

	/**
	 * Set the customer ID. This can be a borrower or a pledgor ID depending on
	 * the customer type specified.
	 * 
	 * @param customerID of long type
	 */
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	/**
	 * Prints a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
