/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralLimitMapLocal.java,v 1.2 2003/07/30 11:16:07 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

/**
 * Local interface for EBCollateralLimitMapBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/30 11:16:07 $ Tag: $Name: $
 */
public interface EBCollateralLimitMapLocal extends EJBLocalObject {
	/**
	 * Get the collateral limit map object.
	 * 
	 * @return collateral limit map
	 */
	public ICollateralLimitMap getValue();

	/**
	 * Set the collateral limit map.
	 * 
	 * @param limitMap of type ICollateralLimitMap
	 */
	public void setValue(ICollateralLimitMap limitMap);
}