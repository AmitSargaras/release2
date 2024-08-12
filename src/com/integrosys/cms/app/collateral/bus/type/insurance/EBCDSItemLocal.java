/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/EBCDSItemLocal.java,v 1.1 2005/09/29 09:39:37 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface for Credit Default Swaps item
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/29 09:39:37 $ Tag: $Name: $
 */
public interface EBCDSItemLocal extends EJBLocalObject {
	/**
	 * Get the credit default swaps item business object.
	 * 
	 * @return ICDSItem
	 */
	public ICDSItem getValue();

	/**
	 * Set the credit default swaps item to this entity.
	 * 
	 * @param equity is of type IMarketableEquity
	 */
	public void setValue(ICDSItem cdsItem);

	/**
	 * Set the item status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}