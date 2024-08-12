/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBDebtorLocal.java,v 1.2 2005/03/17 03:20:09 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.EJBLocalObject;

/*
 * This is EBGeneralChargeDetailsLocal interface
 */

public interface EBGeneralChargeDetailsLocal extends EJBLocalObject {

	/**
	 * Get the general charge details id
	 * 
	 * @return long
	 */
	public long getGeneralChargeDetailsID();

	/**
	 * Return an object representation of the general charge details.
	 * 
	 * @return IContact
	 */
	public IGeneralChargeDetails getValue();

	/**
	 * Persist general charge details
	 * 
	 * @param value is of type IGeneralChargeDetails
	 */
	public void setValue(IGeneralChargeDetails value);
}
