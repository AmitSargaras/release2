/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBValuationLocal.java,v 1.4 2003/08/06 06:33:26 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface for valuation.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/06 06:33:26 $ Tag: $Name: $
 */
public interface EBValuationLocal extends EJBLocalObject {
	/**
	 * Get the collateral valuation business object.
	 * 
	 * @return valuation
	 */
	public IValuation getValue();

	/**
	 * Set the collateral valuation to this entity.
	 * 
	 * @param valuation is of type IValuation
	 */
	public void setValue(IValuation valuation);
}