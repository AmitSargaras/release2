/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBInsurancePolicyLocal.java,v 1.2 2005/08/11 03:08:38 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface for insurance.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/11 03:08:38 $ Tag: $Name: $
 */
public interface EBInsurancePolicyLocal extends EJBLocalObject {
	/**
	 * Get the collateral insurance business object.
	 * 
	 * @return insurance
	 */
	public IInsurancePolicy getValue();

	/**
	 * Set the collateral insurance to this entity.
	 * 
	 * @param insurance is of type IInsurance
	 */
	public void setValue(IInsurancePolicy insurance);

	/**
	 * Delete the insurance policy.
	 */
	public void delete();
}