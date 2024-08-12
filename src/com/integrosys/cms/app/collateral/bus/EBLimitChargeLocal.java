/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBLimitChargeLocal.java,v 1.5 2003/11/05 09:44:24 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

/**
 * Local interface for EBLimitChargeBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/11/05 09:44:24 $ Tag: $Name: $
 */
public interface EBLimitChargeLocal extends EJBLocalObject {
	/**
	 * Get the collateral limit charge business object.
	 * 
	 * @return collateral limit charge
	 */
	public ILimitCharge getValue();

	/**
	 * Get the reference id.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Set the limit charge to the entity.
	 * 
	 * @param charge is of type ILimitCharge
	 */
	public void setValue(ILimitCharge charge);

	/**
	 * Delete the limit charge.
	 */
	public void delete();

	/**
	 * Create the limit charge map reference
	 * 
	 * @param lmtChargeMap limit charge map details of type ILimitChargeMap
	 */
	public void setChargeMapsRef(ILimitChargeMap lmtChargeMap);

	/**
	 * Remove the limit charge map reference
	 * 
	 * @param lmtChargeMap limit charge map details of type ILimitChargeMap
	 */
	public void removeChargeMapsRef(ILimitChargeMap lmtChargeMap);

}