/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralSubTypeLocal.java,v 1.5 2003/08/15 06:00:31 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Local interface to EBCollateralSubTypeBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/15 06:00:31 $ Tag: $Name: $
 */
public interface EBCollateralSubTypeLocal extends EJBLocalObject {
	/**
	 * Get the collateral type business object.
	 * 
	 * @return collateral type object
	 */
	public ICollateralSubType getValue();

	/**
	 * Set the collateral type to this entity.
	 * 
	 * @param colType is of type ICollateralSubType
	 * @throws VersionMismatchException if the collateral subtype is invalid
	 */
	public void setValue(ICollateralSubType colType) throws VersionMismatchException;

	/**
	 * Set the max value, standardised approach, foundation IRB, advanced IRB
	 * for security subtype.
	 * @param colType of type ICollateralSubType
	 * @throws VersionMismatchException if the subtype is invalid
	 */
	public void setSubTypeValue(ICollateralSubType colType) throws VersionMismatchException;
}