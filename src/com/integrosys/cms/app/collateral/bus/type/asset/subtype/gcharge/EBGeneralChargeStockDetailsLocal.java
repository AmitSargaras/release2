/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBDebtorLocal.java,v 1.2 2005/03/17 03:20:09 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.EJBLocalObject;

/*
 * This is EBGeneralChargeStockDetailsLocal interface
 */

public interface EBGeneralChargeStockDetailsLocal extends EJBLocalObject {

	/**
	 * Get the general charge stock details id
	 * 
	 * @return long
	 */
	public long getGeneralChargeStockDetailsID();

	/**
	 * Return an object representation of the general charge stock details.
	 * 
	 * @return IContact
	 */
	public IGeneralChargeStockDetails getValue();

	/**
	 * Persist general charge stock details
	 * 
	 * @param value is of type IGeneralChargeStockDetails
	 */
	public void setValue(IGeneralChargeStockDetails value);
}
