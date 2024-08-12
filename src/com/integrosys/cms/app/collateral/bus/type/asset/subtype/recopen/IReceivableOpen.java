/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/recopen/IReceivableOpen.java,v 1.2 2003/07/28 05:18:09 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.recopen;

import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IReceivableCommon;

/**
 * This interface represents Asset of type Receivables Assigned - Open.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/28 05:18:09 $ Tag: $Name: $
 */
public interface IReceivableOpen extends IReceivableCommon {
	/**
	 * Get approved buyer.
	 * 
	 * @return String
	 */
	public String getApprovedBuyer();

	/**
	 * Set approved buyer.
	 * 
	 * @param approvedBuyer is of type String
	 */
	public void setApprovedBuyer(String approvedBuyer);

	/**
	 * Get approved buyer location.
	 * 
	 * @return String
	 */
	public String getApprovedBuyerLocation();

	/**
	 * Set approved buyer location.
	 * 
	 * @param approvedBuyerLocation is of type String
	 */
	public void setApprovedBuyerLocation(String approvedBuyerLocation);
	
}
