/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralPledgorLocal.java,v 1.2 2003/06/25 06:00:09 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

/**
 * Local interface to EBCollateralPledgorBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/25 06:00:09 $ Tag: $Name: $
 */
public interface EBCollateralPledgorLocal extends EJBLocalObject {
	/**
	 * Get the collateral pledgor.
	 * 
	 * @return collateral pledgor
	 */
	public ICollateralPledgor getValue();

	/**
	 * Set the collateral pledgor to this entity.
	 * 
	 * @param pledgor is of type ICollateralPledgor
	 */
	public void setValue(ICollateralPledgor pledgor);
}