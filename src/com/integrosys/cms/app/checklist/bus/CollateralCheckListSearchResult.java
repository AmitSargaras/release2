/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CollateralCheckListSearchResult.java,v 1.1 2004/10/10 08:12:06 wltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/10/10 08:12:06 $ Tag: $Name: $
 */
public class CollateralCheckListSearchResult extends CheckListSearchResult {

	private long collateralID = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Get the collateral ID.
	 * 
	 * @return long - the customer ID
	 */
	public long getCollateralID() {
		return this.collateralID;
	}

	/**
	 * Set the collateral ID.
	 * 
	 * @param collateralID of long type
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}
}
