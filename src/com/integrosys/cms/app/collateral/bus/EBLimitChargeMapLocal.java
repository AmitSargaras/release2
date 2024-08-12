/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBLimitChargeMapLocal.java,v 1.2 2003/11/04 11:20:40 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

/**
 * Local interface for EBLimitChargeMapBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/11/04 11:20:40 $ Tag: $Name: $
 */
public interface EBLimitChargeMapLocal extends EJBLocalObject {
	/**
	 * Get the mapping between limit and charge.
	 * 
	 * @return ILimitChargeMap
	 */
	public ILimitChargeMap getValue();

	/**
	 * Set the mapping between limit and charge.
	 * 
	 * @param chargeMap of type ILimitChargeMap
	 */
	public void setValue(ILimitChargeMap chargeMap);

	/**
	 * Set the status of the limit charge map.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}